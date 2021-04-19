package com.goodkredit.myapplication.activities.transactions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.processqueue.V2BillsPaymentProcessQueueAdapter;
import com.goodkredit.myapplication.adapter.processqueue.V2PrepaidLoadProcessQueueAdapter;
import com.goodkredit.myapplication.adapter.processqueue.V2SmartRetailWalletProcessQueueAdapter;
import com.goodkredit.myapplication.adapter.processqueue.V2VirtualVoucherRequestProcessQueueAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.V2VirtualVoucherRequestQueue;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.V2BillPaymentQueue;
import com.goodkredit.myapplication.model.V2PrepaidLoadQueue;
import com.goodkredit.myapplication.model.V2SmartRetailWalletQueue;
import com.goodkredit.myapplication.responses.V2GetProcessQueueResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 7/29/2017.
 */

public class V2ProcessQueueActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRvLoad;
    private RecyclerView mRvBills;
    private RecyclerView mRvSmartRetail;
    private RecyclerView mRvVoucherOrders;

    private List<V2PrepaidLoadQueue> mLoadList = new ArrayList<>();
    private List<V2BillPaymentQueue> mBillsList = new ArrayList<>();
    private List<V2SmartRetailWalletQueue> mSmartRetailList = new ArrayList<>();
    private List<V2VirtualVoucherRequestQueue> mVirtualVoucherRequestList;

    private V2PrepaidLoadProcessQueueAdapter mAdapterLoad;
    private V2BillsPaymentProcessQueueAdapter mAdapterPayment;
    private V2SmartRetailWalletProcessQueueAdapter mAdapterSmartRetailWallet;
    private V2VirtualVoucherRequestProcessQueueAdapter mAdapterVirtualVoucherRequest;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private DatabaseHandler mdb;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_queue);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        init();
    }

    private void init() {

        setupToolbar();

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

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

        mVirtualVoucherRequestList = new ArrayList<>();

        mLlLoader = findViewById(R.id.loaderLayout);
        mTvFetching = findViewById(R.id.fetching);
        mTvWait = findViewById(R.id.wait);

        mSwipeRefreshLayout = findViewById(R.id.process_queue_swiperefreshlayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRvLoad = findViewById(R.id.prepaidload_process_queue_recyclerview);
        mRvBills = findViewById(R.id.billspayment_process_queue_recyclerview);
        mRvSmartRetail = findViewById(R.id.smartretail_process_queue_recyclerview);
        mRvVoucherOrders = findViewById(R.id.voucherorders_process_queue_recyclerview);

        mRvLoad.setLayoutManager(new LinearLayoutManager(getViewContext()));
        mRvBills.setLayoutManager(new LinearLayoutManager(getViewContext()));
        mRvSmartRetail.setLayoutManager(new LinearLayoutManager(getViewContext()));
        mRvVoucherOrders.setLayoutManager(new LinearLayoutManager(getViewContext()));

        mRvLoad.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        mRvBills.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        mRvSmartRetail.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        mRvVoucherOrders.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));

        mRvLoad.setNestedScrollingEnabled(false);
        mRvBills.setNestedScrollingEnabled(false);
        mRvSmartRetail.setNestedScrollingEnabled(false);
        mRvVoucherOrders.setNestedScrollingEnabled(false);

        mAdapterLoad = new V2PrepaidLoadProcessQueueAdapter(getViewContext(), mLoadList);
        mRvLoad.setAdapter(mAdapterLoad);
        mAdapterPayment = new V2BillsPaymentProcessQueueAdapter(getViewContext(), mBillsList);
        mRvBills.setAdapter(mAdapterPayment);
        mAdapterSmartRetailWallet = new V2SmartRetailWalletProcessQueueAdapter(getViewContext(), mSmartRetailList);
        mRvSmartRetail.setAdapter(mAdapterSmartRetailWallet);
        mAdapterVirtualVoucherRequest = new V2VirtualVoucherRequestProcessQueueAdapter(getViewContext());
        mRvVoucherOrders.setAdapter(mAdapterVirtualVoucherRequest);

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        findViewById(R.id.btn_view_load).setOnClickListener(this);
        findViewById(R.id.btn_view_bills).setOnClickListener(this);
        findViewById(R.id.btn_view_smartretail).setOnClickListener(this);
        findViewById(R.id.btn_view_voucherorders).setOnClickListener(this);

        getSession();
    }

    private void getSession() {
        mLoaderTimer.cancel();
        mLoaderTimer.start();

        mTvFetching.setText("Fetching process queue.");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            //getProcessQueue();
            getProcessQueueV2();
        } else {
            mLlLoader.setVisibility(View.GONE);
            showError(getString(R.string.generic_internet_error_message));
        }
    }

    private void getProcessQueue() {
        Call<V2GetProcessQueueResponse> call = RetrofitBuild.getTransactionsApi(getViewContext())
                .getProcessQueue(
                        imei,
                        userid,
                        borrowerid,
                        V2Utils.getSha1Hex(imei + userid + sessionid),
                        sessionid);

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0)
            call.enqueue(processQueueCallback);
        else
            mLlLoader.setVisibility(View.GONE);
        showError(getString(R.string.generic_internet_error_message));
    }

    private Callback<V2GetProcessQueueResponse> processQueueCallback = new Callback<V2GetProcessQueueResponse>() {
        @Override
        public void onResponse(Call<V2GetProcessQueueResponse> call, Response<V2GetProcessQueueResponse> response) {
            ResponseBody errBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);

            if (errBody == null) {
                if (response.body().getStatus().equals("000")) {
                    //success
                    mLoadList = response.body().getPrepaidLoadQueueList();
                    mBillsList = response.body().getBillsPaymentQueueList();
                    mSmartRetailList = response.body().getSmartRetailWalletQueueList();
                    mVirtualVoucherRequestList = response.body().getVirtualVoucherRequest();

                    mAdapterLoad.update(mLoadList);
                    mAdapterPayment.update(mBillsList);
                    mAdapterSmartRetailWallet.update(mSmartRetailList);
                    mAdapterVirtualVoucherRequest.update(mVirtualVoucherRequestList);

                    if (mdb != null) {
                        mdb.truncateTable(mdb, DatabaseHandler.VIRTUAL_VOUCHER_REQUEST);

                        for (V2VirtualVoucherRequestQueue request : mVirtualVoucherRequestList) {
                            mdb.insertVirtualVoucherRequestQueue(mdb, request);
                        }
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError(getString(R.string.generic_error_message));
            }
        }

        @Override
        public void onFailure(Call<V2GetProcessQueueResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showError(getString(R.string.generic_error_message));
        }
    };

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        getSession();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLoaderTimer.cancel();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, V2ProcessQueueActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_view_load:
                ViewOthersTransactionsActivity.start(getViewContext(), 4);
                break;
            case R.id.btn_view_bills:
                ViewOthersTransactionsActivity.start(getViewContext(), 5);
                break;
            case R.id.btn_view_smartretail:
                ViewOthersTransactionsActivity.start(getViewContext(), 6);
                break;
            case R.id.btn_view_voucherorders:
                ViewOthersTransactionsActivity.start(getViewContext(), 1);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }


    /*
     * SECURITY UPDATE
     * AS OF
     * JANUARY 2020
     * */

    private void getProcessQueueV2(){
        try{

            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

                LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("year",".");
                parameters.put("limit", ".");
                parameters.put("from",".");
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getProcessQueue");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getProcessQueueObjectV2();

            } else {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showNoInternetToast();
            }

        } catch (Exception e) {
            e.printStackTrace();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetToast();
        }
    }

    private void getProcessQueueObjectV2() {
        Call<GenericResponse> call = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getProcessQueueV2(authenticationid,sessionid,param);

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            call.enqueue(processQueueCallbackV2);
        }else {
            showError(getString(R.string.generic_internet_error_message));
        }
    }


    private Callback<GenericResponse> processQueueCallbackV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    String loaddata = CommonFunctions.parseJSON(data,"loaddata");
                    String billspaydata =  CommonFunctions.parseJSON(data,"billspaydata");
                    String smartreloadqueue = CommonFunctions.parseJSON(data,"smartreloadqueue");
                    String buyvoucherqueue = CommonFunctions.parseJSON(data,"buyvoucherqueue");

                    //success
                    mLoadList = new Gson().fromJson(loaddata, new TypeToken<List<V2PrepaidLoadQueue>>() {}.getType());
                    mBillsList = new Gson().fromJson(billspaydata, new TypeToken<List<V2BillPaymentQueue>>() {}.getType());
                    mSmartRetailList = new Gson().fromJson(smartreloadqueue, new TypeToken<List<V2SmartRetailWalletQueue>>() {}.getType());
                    mVirtualVoucherRequestList = new Gson().fromJson(buyvoucherqueue, new TypeToken<List<V2VirtualVoucherRequestQueue>>() {}.getType());

                    mAdapterLoad.update(mLoadList);
                    mAdapterPayment.update(mBillsList);
                    mAdapterSmartRetailWallet.update(mSmartRetailList);
                    mAdapterVirtualVoucherRequest.update(mVirtualVoucherRequestList);

                    if (mdb != null) {
                        mdb.truncateTable(mdb, DatabaseHandler.VIRTUAL_VOUCHER_REQUEST);

                        for (V2VirtualVoucherRequestQueue request : mVirtualVoucherRequestList) {
                            mdb.insertVirtualVoucherRequestQueue(mdb, request);
                        }
                    }

                } else {
                    if(response.body().getStatus().equals("error")){
                        showErrorToast();
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else{
                        showError(decryptedMessage);
                    }
                }
            } else {
                showError(getString(R.string.generic_error_message));
            }
        }
        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showError(getString(R.string.generic_error_message));
        }
    };


}
