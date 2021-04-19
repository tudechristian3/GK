package com.goodkredit.myapplication.fragments.vouchers.payoutone;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.vouchers.payoutone.VoucherPayoutOneBankDepositHistoryQueueAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.gkearn.GKEarnConversionsPointsDB;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.vouchers.BankDepositHistoryQueue;
import com.goodkredit.myapplication.responses.vouchers.payoutone.BankDepositHistoryQueueResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherPayoutOneBankDepositHistoryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView rv_deposit_history;
    private VoucherPayoutOneBankDepositHistoryQueueAdapter mAdapter;
    private List<BankDepositHistoryQueue> mGridData = new ArrayList<>();

    private String sessionid;
    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;
    private int year;

    private SwipeRefreshLayout swipe_container;
    private RelativeLayout emptyvoucher;
    private TextView txv_nodata;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //VIEW ARCHIVE
    private LinearLayout btn_view_archive;
    private TextView txv_view_archive;
    private TextView editsearches;
    private TextView clearsearch;
    private Spinner yearspinType;
    private boolean isyearselected = false;

    //GLOBAL DIALOGS
    private GlobalDialogs mGlobalDialogs;

    //DATE
    private int MIN_MONTH = 1;
    private int MAX_MONTH = 12;
    private String passedmonth = ".";
    private String passedyear = ".";
    private int month = 0;
    private int currentmonth = 0;
    private int currentyear = 0;
    private int registrationmonth;
    private int registrationyear;
    private String dateregistered = "";

    public static VoucherPayoutOneBankDepositHistoryFragment newInstance(String value) {
        VoucherPayoutOneBankDepositHistoryFragment fragment = new VoucherPayoutOneBankDepositHistoryFragment();
        Bundle b = new Bundle();
        b.putString("title", value);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voucher_payoutone_bank_deposit_history_queue, container, false);

        init(view);
        initData();

        return view;
    }

    private void init(View view) {
        rv_deposit_history = view.findViewById(R.id.rv_deposit_history_queue);

        mAdapter = new VoucherPayoutOneBankDepositHistoryQueueAdapter(getViewContext(), "HISTORY");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rv_deposit_history.setNestedScrollingEnabled(false);
        rv_deposit_history.setLayoutManager(layoutManager);
        rv_deposit_history.setAdapter(mAdapter);

        swipe_container = view.findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(this);
        emptyvoucher = (RelativeLayout) view.findViewById(R.id.emptyvoucher);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);

        txv_nodata = view.findViewById(R.id.txv_nodata);
        refresh.setOnClickListener(this);
        refreshnointernet.setOnClickListener(this);

        //ARCHIVE
        btn_view_archive = view.findViewById(R.id.btn_view_archive);
        btn_view_archive.setOnClickListener(this);
        txv_view_archive = view.findViewById(R.id.txv_view_archive);
    }

    private void initData() {
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
        year = Calendar.getInstance().get(Calendar.YEAR);

        Calendar c = Calendar.getInstance();
        passedyear = ".";
        passedmonth = ".";
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        currentyear = Calendar.getInstance().get(Calendar.YEAR);
        currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        getSession();
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            getBankDepositDetails(getBankDepositDetailsSession);
        } else{
            swipe_container.setRefreshing(false);
//            showNoInternetToast();
            showNoInternetConnection(true);
        }
    }

    private void getBankDepositDetails (Callback<BankDepositHistoryQueueResponse> getBankDepositDetailsCallback ){
        Call<BankDepositHistoryQueueResponse> getBankDepositDetails = RetrofitBuild.getVoucherV3Service(getViewContext())
                .getBankDepositDetailsCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        "DEPOSIT_HISTORY",
                        String.valueOf(year),
                        CommonVariables.devicetype);

        getBankDepositDetails.enqueue(getBankDepositDetailsCallback);
    }

    private final Callback<BankDepositHistoryQueueResponse> getBankDepositDetailsSession = new Callback<BankDepositHistoryQueueResponse>() {
        @Override
        public void onResponse(Call<BankDepositHistoryQueueResponse> call, Response<BankDepositHistoryQueueResponse> response) {
            swipe_container.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    try{
                        CacheManager.getInstance().saveBankDepositHistory(response.body().getBankDepositHistoryQueueList());
                        mGridData = CacheManager.getInstance().getBankDepositHistory();
                        updateList(mGridData);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else if(response.body().getStatus().equals("104")) {
                    showAutoLogoutDialog(response.body().getMessage());
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }

            } else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<BankDepositHistoryQueueResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            swipe_container.setRefreshing(false);
        }
    };

    private void updateList(List<BankDepositHistoryQueue> data){

        if (data.size() > 0){
            txv_nodata.setVisibility(View.GONE);
            emptyvoucher.setVisibility(View.GONE);
            mAdapter.updateList(data);
            mAdapter.notifyDataSetChanged();
            showNoInternetConnection(false);
        } else{
            txv_nodata.setText("No bank deposit history yet.");
            txv_nodata.setVisibility(View.VISIBLE);
            emptyvoucher.setVisibility(View.VISIBLE);
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
            showNoInternetConnection(false);
        }
    }

    //show no internet connection page
    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyvoucher.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        swipe_container.setRefreshing(true);
        getSession();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.refreshnointernet:
            case R.id.refresh:{
                onRefresh();
                break;
            }
            case R.id.btn_view_archive:{
                if (txv_view_archive.getText().toString().equals("View Archive"))
                    showViewArchiveDialogNew();
                else
                    showFilterOptionDialogNew();
                break;
            }
        }
    }

    private void showViewArchiveDialogNew() {
        mGlobalDialogs = new GlobalDialogs(getViewContext());

        mGlobalDialogs.createDialog("View Archive", "",
                "CANCEL", "FILTER", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.SPINNER);

        View closebtn = mGlobalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        ArrayList<String> spinyearlist = new ArrayList<String>();
        spinyearlist = yearList();

        LinearLayout yearContainer = mGlobalDialogs.setContentSpinner(spinyearlist, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.CENTER));

        final int countyear = yearContainer.getChildCount();
        for (int i = 0; i < countyear; i++) {
            View spinnerView = yearContainer.getChildAt(i);
            if (spinnerView instanceof Spinner) {
                yearspinType = (Spinner) spinnerView;
                yearspinType.setOnItemSelectedListener(yearItemListenerNew);
            }
        }

        View btndoubleone = mGlobalDialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        View btndoubletwo = mGlobalDialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
                filterOptions();
            }
        });
    }

    private void showFilterOptionDialogNew() {
        mGlobalDialogs = new GlobalDialogs(getViewContext());

        mGlobalDialogs.createDialog("Filter Options", "",
                "CLOSE", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.TEXTVIEW);

        mGlobalDialogs.hideTextButtonAction();

        View closebtn = mGlobalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        ArrayList<String> textviewlist = new ArrayList<String>();
        textviewlist.add("EDIT SEARCHES");
        textviewlist.add("CLEAR SEARCHES");

        LinearLayout textViewContainer = mGlobalDialogs.setContentTextView(textviewlist, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 18, 0));

        final int count = textViewContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View textView = textViewContainer.getChildAt(i);

            if (textView instanceof TextView) {
                String tag = String.valueOf(textView.getTag());

                if (tag.equals("EDIT SEARCHES")) {
                    editsearches = (TextView) textView;
                    editsearches.setPadding(20, 20, 20, 20);
                    editsearches.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_information_blue));
                    editsearches.setOnClickListener(editsearchListener);
                } else if (tag.equals("CLEAR SEARCHES")) {
                    clearsearch = (TextView) textView;
                    clearsearch.setPadding(20, 20, 20, 20);
                    clearsearch.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_information_lightblue));
                    clearsearch.setOnClickListener(clearSearchListener);
                }
            }
        }

        View singlebtn = mGlobalDialogs.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });
    }

    //create year list
    private ArrayList<String> yearList() {
        ArrayList<String> mYear = new ArrayList<String>();
        mYear.add("Select Year");
        for (int i = registrationyear; i <= currentyear; i++) {
            mYear.add(Integer.toString(i));
        }

        return mYear;
    }

    private AdapterView.OnItemSelectedListener yearItemListenerNew = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                String spinyear = parent.getItemAtPosition(position).toString();
                if (!spinyear.equals("Select Year")) {
                    year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    passedyear = String.valueOf(year);
                    isyearselected = true;
                } else {
                    isyearselected = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            isyearselected = false;
        }
    };

    //FILTER OPTIONS
    private void filterOptions() {
        if (isyearselected) {
            if (mAdapter != null) {
                mAdapter.clear();

                btn_view_archive.setVisibility(View.GONE);
                year = Calendar.getInstance().get(Calendar.YEAR);

                if (txv_view_archive.getText().equals("View Archive")) {
                    txv_view_archive.setText("Filter Options");
                }

                getSession();
            }
        } else {
            showToast("Please select a year.", GlobalToastEnum.WARNING);
        }
    }

    private View.OnClickListener editsearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mGlobalDialogs != null) {
                editSearchOption();
            }
        }
    };

    private View.OnClickListener clearSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mGlobalDialogs != null) {
                clearSearchOption();
            }
        }
    };

    private void editSearchOption() {
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;
        showViewArchiveDialogNew();
    }

    private void clearSearchOption() {
        txv_view_archive.setText("View Archive");
        year = Calendar.getInstance().get(Calendar.YEAR);
        passedyear = ".";
        isyearselected = false;
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;
    }

}
