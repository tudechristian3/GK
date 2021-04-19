package com.goodkredit.myapplication.activities.voting;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.votes.VotesHistoryAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.votes.VotesHistory;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.votes.VotesHistoryResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VotesHistoryActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private List<VotesHistory> mGridData;
    private VotesHistoryAdapter mAdapter;
    private RecyclerView recyclerView;

    private String imei;
    private String userid;
    private String borrowerid;
    private String sessionid;
    private String authcode;
    private String devicetype;
    private String year;
    private String month;
    private String limit;

    private RelativeLayout emptyvoucher;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private Calendar cal;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    //
    List<VotesHistory> votesHistories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_votes_history);

            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    private void init() {
        setupToolbar();

        recyclerView = findViewById(R.id.recycler_view_votes_history);

        mAdapter = new VotesHistoryAdapter(getViewContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getViewContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        //initialize refresh
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        emptyvoucher = findViewById(R.id.emptyvoucher);
        refresh = findViewById(R.id.refresh);

        nointernetconnection = findViewById(R.id.nointernetconnection);
        refreshnointernet = findViewById(R.id.refreshnointernet);
        refreshdisabled = findViewById(R.id.refreshdisabled);
        refreshdisabled1 = findViewById(R.id.refreshdisabled1);

        mLlLoader = findViewById(R.id.loaderLayout);
        mTvFetching = findViewById(R.id.fetching);
        mTvWait = findViewById(R.id.wait);

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        refresh.setOnClickListener(this);
        refresh.setEnabled(false);
        refreshnointernet.setOnClickListener(this);

    }

    private void initData() {
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        limit = "0";

        cal = Calendar.getInstance();
        year = String.valueOf(cal.get(Calendar.YEAR));
        month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        devicetype = "ANDROID";

        getSession();
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            mTvFetching.setText("Fetching Votes History. ");
//            mTvWait.setText("Please wait...");
//            mLlLoader.setVisibility(View.VISIBLE);
//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//            call.enqueue(callsession);
//        } else {
//            showError(getString(R.string.generic_internet_error_message));
//            swipeRefreshLayout.setRefreshing(false);
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//        }
//    }
//
//    private Callback<String> callsession = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            ResponseBody errBody = response.errorBody();
//
//            if (errBody == null) {
//                sessionid = response.body().toString();
//                authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                if (!sessionid.isEmpty()) {
//                    getVotesHistory(getVotesHistorySession);
//                } else {
//                    swipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showErrorToast();
//                    hideProgressDialog();
//                }
//            } else {
//                swipeRefreshLayout.setRefreshing(false);
//                mLlLoader.setVisibility(View.GONE);
//                showNoInternetConnection(true);
//                showErrorToast();
//                hideProgressDialog();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            swipeRefreshLayout.setRefreshing(false);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//            hideProgressDialog();
//        }
//    };

    private void getSession() {
        refresh.setEnabled(false);
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mTvFetching.setText("Fetching Votes History. ");
            mTvWait.setText("Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getVotesHistory(getVotesHistorySession);
            getVotingTransactionsV2();

        } else {
            refresh.setEnabled(true);
            swipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getVotesHistory (Callback<VotesHistoryResponse> getVotesHistoryCallback) {
        Call<VotesHistoryResponse> getVotesHistory = RetrofitBuild.getVotesAPIService(getViewContext())
                .getVotesHistoryCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        devicetype,
                        year,
                        month,
                        limit
                );

        getVotesHistory.enqueue(getVotesHistoryCallback);
    }

    public final Callback<VotesHistoryResponse> getVotesHistorySession = new Callback<VotesHistoryResponse>() {
        @Override
        public void onResponse(Call<VotesHistoryResponse> call, Response<VotesHistoryResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                if(response.body().getStatus().equals("000")){

                    CacheManager.getInstance().saveVotesHistory(response.body().getVotesHistoryList());
                    mGridData = CacheManager.getInstance().getVotesHistory();
                    mAdapter.updateList(CacheManager.getInstance().getVotesHistory());

                    if(mGridData.size() == 0){
                        emptyvoucher.setVisibility(View.VISIBLE);
                    } else{
                        emptyvoucher.setVisibility(View.GONE);
                    }

                } else{
                    showError(response.body().getMessage());
                }
            } else{
                showError(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<VotesHistoryResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.refreshnointernet:
            case R.id.refresh: {
                onRefresh();
                break;
            }
        }

    }

    @Override
    public void onRefresh() {

        try {

            if(mAdapter != null){
                mAdapter.clear();
            }

            mAdapter.updateList(CacheManager.getInstance().getVotesHistory());

            swipeRefreshLayout.setRefreshing(true);
            getSession();

        } catch (Exception e) {
        }
    }

    //show no internet connection page
    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyvoucher.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
            mLlLoader.setVisibility(View.GONE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }


    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    private void getVotingTransactionsV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("year",year);
        parameters.put("month",month);
        parameters.put("limit",limit);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getVotingTransactionsV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        getVotingTransactionsV2Object(getVotingTransactionsV2Callback);


    }
    private void getVotingTransactionsV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getVotesV2API(getViewContext())
                .getVotingTransactionsV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  getVotingTransactionsV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            refresh.setEnabled(true);
            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    votesHistories = new Gson().fromJson(data,new TypeToken<List<VotesHistory>>(){}.getType());
                    assert  votesHistories != null;

                    CacheManager.getInstance().saveVotesHistory(votesHistories);
                    mGridData = CacheManager.getInstance().getVotesHistory();
                    mAdapter.updateList(CacheManager.getInstance().getVotesHistory());

                    if(mGridData.size() == 0){
                        emptyvoucher.setVisibility(View.VISIBLE);
                    } else{
                        emptyvoucher.setVisibility(View.GONE);
                    }

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }

        }
        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorGlobalDialogs();
            t.printStackTrace();
            refresh.setEnabled(true);
        }
    };



}
