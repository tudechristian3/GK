package com.goodkredit.myapplication.fragments.dropoff;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.dropoff.PaymentRequestDetailsActivity;
import com.goodkredit.myapplication.adapter.dropoff.PaymentRequestAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.dropoff.PaymentRequestCompletedDB;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.dropoff.PaymentRequest;
import com.goodkredit.myapplication.responses.dropoff.GetPaymentRequestCompletedResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class PaymentRequestCompletedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private List<PaymentRequest> mGridData = new ArrayList<>();
    private PaymentRequestAdapter madapter;

//    private List<PaymentRequestOrderDetails> morderdetails = new ArrayList<>();

    private StickyListHeadersListView stickyList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout emptyvoucher;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;
    private TextView textView;

    private DatabaseHandler mdb;
    private String imei;
    private String userid;
    private String sessionid;
    private String borrowerid;
    private String authcode;
    private String merchantid;
    private String limit;
    private String devicetype;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private String from;

    Bundle bundle = new Bundle();


    public static PaymentRequestCompletedFragment newInstance(String value) {
        PaymentRequestCompletedFragment fragment = new PaymentRequestCompletedFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", value);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dropoff_paymentrequestpendingcompleted, container, false);

        try{
            init(view);
            initData();
        } catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    private void init(View view) {
        //initialize elements of the fragment
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        emptyvoucher = (RelativeLayout) view.findViewById(R.id.emptyvoucher);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshdisabled = (ImageView) view.findViewById(R.id.refreshdisabled);
        refreshdisabled1 = (ImageView) view.findViewById(R.id.refreshdisabled1);

        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        stickyList = (StickyListHeadersListView) view.findViewById(R.id.consumedListView);
        stickyList.setOnItemClickListener(fetchItemListener);

        madapter = new PaymentRequestAdapter(mGridData, getViewContext());
        stickyList.setAdapter(madapter);

        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("No completed request yet.");
        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        //Scroll to view filter option or view archive
        stickyList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
            }

        });

        setNestedScrollingDisabled();
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        limit = "0";
        devicetype = "ANDROID";
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        from = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_MDPTYPE);
        if(from.equals("fromDROPOFF")){
            merchantid = ".";
        } else if(from.equals("fromMDP")){
            merchantid = "MDP";
        }

//        mtransactions = db.getPurchasesTransactions(db);
        mGridData = mdb.getPaymentRequestCompleted(mdb);

        getSession();
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            mTvFetching.setText("Fetching Pending Requests.");
            mTvWait.setText("Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
//            isLoading = true;
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getPaymentRequestCompleted(getPaymentRequestCompletedSession);

        } else{
            swipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }
    }

    private void getPaymentRequestCompleted (Callback<GetPaymentRequestCompletedResponse> getPaymentRequestPendingCallback) {
        Call<GetPaymentRequestCompletedResponse> getpaymentrequestpending = RetrofitBuild.getDropOffAPIService(getViewContext())
                .getPaymentRequestCompletedCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        merchantid,
                        limit,
                        devicetype);

        getpaymentrequestpending.enqueue(getPaymentRequestPendingCallback);
    }

    private final Callback<GetPaymentRequestCompletedResponse> getPaymentRequestCompletedSession = new Callback<GetPaymentRequestCompletedResponse>() {
        @Override
        public void onResponse(Call<GetPaymentRequestCompletedResponse> call, Response<GetPaymentRequestCompletedResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    if(mdb != null){
                        mdb.truncateTable(mdb, PaymentRequestCompletedDB.TABLE_NAME);
                    }

                    List<PaymentRequest> prcompletedlist = new ArrayList<>();
                    prcompletedlist = response.body().getPaymentRequestCompletedList();
                    for (PaymentRequest prcompleted : prcompletedlist){
                        mdb.insertPaymentRequestCompleted(mdb, prcompleted);
                    }

                    updateList(mdb.getPaymentRequestCompleted(mdb));
                } else{
                    showError(response.body().getMessage());
                }
            } else{
                showError();
            }
        }

        @Override
        public void onFailure(Call<GetPaymentRequestCompletedResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refresh:{
                onRefresh();
                break;
            }
        }
    }

    private AdapterView.OnItemClickListener fetchItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            PaymentRequest completedrequest = mGridData.get(position);

            bundle.putParcelable(PaymentRequest.KEY_PAYMENTREQUEST, completedrequest);
            bundle.putString("from", "CompletedAdapter");

            Intent intent = new Intent(getViewContext(), PaymentRequestDetailsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    @Override
    public void onRefresh() {
        try{
            if(mdb != null){
                mdb.truncateTable(mdb, PaymentRequestCompletedDB.TABLE_NAME);
                if(madapter != null){
                    madapter.clear();
                }

                swipeRefreshLayout.setRefreshing(true);
                getSession();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setNestedScrollingDisabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stickyList.setNestedScrollingEnabled(false);
        } else {
            ViewCompat.setNestedScrollingEnabled(stickyList, false);
        }
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyvoucher.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    private void updateList(List<PaymentRequest> data) {
        showNoInternetConnection(false);
        if (data.size() > 0) {
            emptyvoucher.setVisibility(View.GONE);
            madapter.updateList(data);
            madapter.notifyDataSetChanged();
        } else {
            emptyvoucher.setVisibility(View.VISIBLE);
            madapter.clear();
            madapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        onRefresh();
    }
}
