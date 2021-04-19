package com.goodkredit.myapplication.fragments.prepaidrequest;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.prepaidrequest.VoucherPrepaidRequestActivity;
import com.goodkredit.myapplication.adapter.prepaidrequest.PaymentChannelsRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.PaymentChannels;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GetPaymentPartnersResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User-PC on 10/24/2017.
 */

public class PaymentChannelsFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private TextView txvPaymentChannels;
    private RecyclerView recyclerView;
    private PaymentChannelsRecyclerAdapter mAdapter;

    private String totalamount;
    private String BillingID;
    private String sessionid;
    private String imei;
    private String authcode;
    private String userid;
    private String borrowerid;
    private int limit;

    private DatabaseHandler mdb;
    private List<PaymentChannels> paymentChannelsList;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private RelativeLayout mainLayout;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private NestedScrollView nested_scroll;
    private boolean isloadmore;
    private boolean isbottomscroll;

    private boolean isfirstload = true;

    //FOR SECURITY
    private String index;
    private String authenticationid;
    private String param;
    private String keyEncryption;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_channels, container, false);
        setHasOptionsMenu(true);

        //set title
        ((VoucherPrepaidRequestActivity) getViewContext()).setActionBarTitle("Order Vouchers");

        init(view);

        initData();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                ((VoucherPrepaidRequestActivity) getViewContext()).displayView(1, BillingID, totalamount);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init(View view) {
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);
        txvPaymentChannels = (TextView) view.findViewById(R.id.txvPaymentChannels);
        txvPaymentChannels.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "GK Payment Channels"));
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_payment_channels);
        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        textView11.setText("No Payment Channels yet.");
        refresh = (ImageView) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        mainLayout = (RelativeLayout) view.findViewById(R.id.mainLayout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        nested_scroll = (NestedScrollView) view.findViewById(R.id.nested_scroll);
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);
    }

    private void initData() {
        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        mdb = new DatabaseHandler(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        BillingID = getArguments().getString("billingid");
        totalamount = getArguments().getString("totalamount");
        paymentChannelsList = new ArrayList<>();
        paymentChannelsList = mdb.getPaymentChannels(mdb);
        limit = getLimit(paymentChannelsList.size(), 30);
        isbottomscroll = false;
        isloadmore = true;

        recyclerView.setLayoutManager(new GridLayoutManager(getViewContext(), CommonFunctions.calculateNoOfColumns(getViewContext(),"PAYMENT CHANNELS")));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new PaymentChannelsRecyclerAdapter(getViewContext());
        recyclerView.setAdapter(mAdapter);

        if (paymentChannelsList.size() > 0) {
            mainLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            mAdapter.setPaymentsChannelsData(paymentChannelsList);
        } else {
            mainLayout.setVisibility(View.GONE);
            getSession();
        }

        nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    isbottomscroll = true;
                    if (isloadmore) {
                        if (!isfirstload) {
                            limit = limit + 30;
                        }
                        getSession();
                    }
                }
            }
        });

    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Fetching payment channels.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getPaymentPartners(getPaymentPartnersSession);
            getPaymentPartnersV2();

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }

    }

    private void getPaymentPartners(Callback<GetPaymentPartnersResponse> getPaymentPartnersCallback) {
        Call<GetPaymentPartnersResponse> getPaymentPartners = RetrofitBuild.getPaymentPartnersService(getViewContext())
                .getPaymentPartnersCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        String.valueOf(limit));
        getPaymentPartners.enqueue(getPaymentPartnersCallback);
    }

    private final Callback<GetPaymentPartnersResponse> getPaymentPartnersSession = new Callback<GetPaymentPartnersResponse>() {

        @Override
        public void onResponse(Call<GetPaymentPartnersResponse> call, Response<GetPaymentPartnersResponse> response) {
            mLlLoader.setVisibility(View.GONE);

            mSwipeRefreshLayout.setRefreshing(false);
            isfirstload = false;

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    isloadmore = response.body().getPaymentChannels().size() > 0;
                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.PAYMENT_CHANNELS);
                    }

                    List<PaymentChannels> paymentChannels = response.body().getPaymentChannels();

                    for (PaymentChannels channel : paymentChannels) {
                        mdb.insertPaymentChannels(mdb, channel);
                    }

                    updateList(mdb.getPaymentChannels(mdb));
                } else {
                    showError(response.body().getMessage());
                }
            }

        }

        @Override
        public void onFailure(Call<GetPaymentPartnersResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void updateList(List<PaymentChannels> data) {
        showNoInternetConnection(false);
        if (data.size() > 0) {
            mainLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            if (mAdapter != null) {
                mAdapter.setPaymentsChannelsData(data);
            }
        } else {
            mainLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh:
            case R.id.refreshnointernet: {
                if (mdb != null) {
                    isfirstload = false;
                    limit = getLimit(mdb.getPaymentChannels(mdb).size(), 30);
                    isbottomscroll = false;
                    isloadmore = true;
                    getSession();
                }
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        if (mdb != null) {
            if (mAdapter != null) {
                mdb.truncateTable(mdb, DatabaseHandler.PAYMENT_CHANNELS);
                mainLayout.setVisibility(View.GONE);
                mAdapter.clear();
            }
        }

        isfirstload = false;
        limit = 0;
        isbottomscroll = false;
        isloadmore = true;
        mSwipeRefreshLayout.setRefreshing(true);
        getSession();
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * **/
    //GET PAYMENT PARTNERS
    private void getPaymentPartnersV2(){

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("limit", String.valueOf(limit));

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getPaymentPartnersV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getPaymentPartnersV2Object(getPaymentPartnersV2Callback);

        }else{
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }
    private void getPaymentPartnersV2Object(Callback<GenericResponse> getPaymentPartners){
        Call<GenericResponse> call = RetrofitBuilder.getPaymentV2API(getViewContext())
                .getPaymentPartnersV2(authenticationid,sessionid,param);
        call.enqueue(getPaymentPartners);
    }
    private final Callback<GenericResponse> getPaymentPartnersV2Callback = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            isfirstload = false;

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData  =  CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<PaymentChannels> paymentChannels = new Gson().fromJson(decryptedData, new TypeToken<List<PaymentChannels>>(){}.getType());
                    isloadmore = paymentChannels.size() > 0;
                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.PAYMENT_CHANNELS);
                    }
                    for (PaymentChannels channel : paymentChannels) {
                        mdb.insertPaymentChannels(mdb, channel);
                    }
                    updateList(mdb.getPaymentChannels(mdb));

                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }
                else {
                    showErrorGlobalDialogs(decryptedMessage);
                }
            }else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showErrorToast("Something went wrong.Please try again. ");
        }
    };



}
