package com.goodkredit.myapplication.activities.freesms;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.freesms.FreeSMSHistoryAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.freesms.FreeSMSHistoryDBHelper;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.freesms.FreeSMSHistory;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.freesms.GetFreeSMSHistoryResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
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

public class FreeSMSHistoryActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private List<FreeSMSHistory> mGridData = new ArrayList<>();
    private FreeSMSHistoryAdapter mAdapter;
    private RecyclerView recyclerView;

    private DatabaseHandler mdb;
    private String imei;
    private String authcode;
    private String userid;
    private String sessionid;
    private String borrowerid;
    private String limit;
    private String devicetype;

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

    private LinearLayout layout_last3months;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_sms_history);

        init();
        initData();
    }

    private void init() {
        setupToolbar();

        recyclerView = findViewById(R.id.rv_free_sms_history);

        mAdapter = new FreeSMSHistoryAdapter(mGridData, getViewContext());
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
        refresh.setEnabled(false);
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
        refreshnointernet.setOnClickListener(this);

        layout_last3months = findViewById(R.id.layout_last3months);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        limit = "0";
        devicetype = "ANDROID";

        getSession();
    }

    private void getSession() {
        refresh.setEnabled(false);
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mTvFetching.setText("Fetching Sent History. ");
            mTvWait.setText("Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getFreeSMSHistory(getFreeSMSHistorySession);
            getFreeSMSHistoryV2();
        } else {
            refresh.setEnabled(true);
            swipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
        }
    }

    private void getFreeSMSHistory(Callback<GetFreeSMSHistoryResponse> getFreeSMSHistoryCallback){
        Call<GetFreeSMSHistoryResponse> getfreesmshistory = RetrofitBuild.getFreeSMSAPIService(getViewContext())
                .getFreeSMSHistoryCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        limit,
                        devicetype);
        getfreesmshistory.enqueue(getFreeSMSHistoryCallback);
    }

    private final Callback<GetFreeSMSHistoryResponse> getFreeSMSHistorySession = new Callback<GetFreeSMSHistoryResponse>() {
        @Override
        public void onResponse(Call<GetFreeSMSHistoryResponse> call, Response<GetFreeSMSHistoryResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    layout_last3months.setVisibility(View.VISIBLE);
                    if(mdb != null){
                        mdb.truncateTable(mdb, FreeSMSHistoryDBHelper.TABLE_NAME);
                    }

                    List<FreeSMSHistory> list;
                    list = response.body().getFreeSMSHistoryList();
                    for(FreeSMSHistory freesmshistory : list){
                        mdb.insertFreeSMSHistory(mdb, freesmshistory);
                    }

                    updateList(mdb.getFreeSMSHistory(mdb));
                } else{
                    showError(response.body().getMessage());
                }
            } else{
                showError(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<GetFreeSMSHistoryResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void updateList(List<FreeSMSHistory> data){
        if (data.size() > 0){
            emptyvoucher.setVisibility(View.GONE);
            mAdapter.updateList(data);
            mAdapter.notifyDataSetChanged();
        } else{
            emptyvoucher.setVisibility(View.VISIBLE);
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
        }
    }

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

        layout_last3months.setVisibility(View.GONE);

        try {
            if(mdb != null){
                mdb.truncateTable(mdb, FreeSMSHistoryDBHelper.TABLE_NAME);

                if(mAdapter != null){
                    mAdapter.clear();
                }

                swipeRefreshLayout.setRefreshing(true);
                getSession();
            }

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

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getFreeSMSHistoryV2(){

        LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
        parameters.put("imei",imei);
        parameters.put("userid",userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("limit", limit);
        parameters.put("devicetype",  CommonVariables.devicetype);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getFreeSMSHistoryV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        getFreeSMSHistoryV2Object(getFreeSMSHistoryV2Session);

    }

    private void getFreeSMSHistoryV2Object (Callback<GenericResponse> smshistorycallback) {
        Call<GenericResponse> getsms = RetrofitBuilder.getFreeSMSV2API(getViewContext())
                .getFreeSMSHistoryV2(authenticationid,sessionid,param);
        getsms.enqueue(smshistorycallback);
    }

    private final Callback<GenericResponse>getFreeSMSHistoryV2Session = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            refresh.setEnabled(true);

            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    layout_last3months.setVisibility(View.VISIBLE);
                    if(mdb != null){
                        mdb.truncateTable(mdb, FreeSMSHistoryDBHelper.TABLE_NAME);
                    }
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<FreeSMSHistory> list = new Gson().fromJson(data, new TypeToken<List<FreeSMSHistory>>(){}.getType());
                    for(FreeSMSHistory freesmshistory : list){
                        mdb.insertFreeSMSHistory(mdb, freesmshistory);
                    }
                    updateList(mdb.getFreeSMSHistory(mdb));
                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else{
                    showError(message);
                }
            } else{
                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            refresh.setEnabled(true);
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };



}
