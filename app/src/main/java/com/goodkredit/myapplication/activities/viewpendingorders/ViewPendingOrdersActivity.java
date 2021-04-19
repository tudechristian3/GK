package com.goodkredit.myapplication.activities.viewpendingorders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.processqueue.V2VirtualVoucherRequestProcessQueueAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.V2VirtualVoucherRequestQueue;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.V2GetProcessQueueResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
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

public class ViewPendingOrdersActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private List<V2VirtualVoucherRequestQueue> mGridData = new ArrayList<>();
    private V2VirtualVoucherRequestProcessQueueAdapter mAdapterVirtualVoucherRequest;
    private DatabaseHandler mdb;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;

    private TextView txvRequestNote;

    private NestedScrollView nested_scroll;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pending_orders);

        try {

            setupToolbar();

            setTitle("Pending Orders");

            //UNIFIED SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            mdb = new DatabaseHandler(getViewContext());
            mGridData = mdb.getVirtualVoucherRequestQueue(mdb);

            imei = PreferenceUtils.getImeiId(getViewContext());
            userid = PreferenceUtils.getUserId(getViewContext());
            borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

            //swipe refresh
            mSwipeRefreshLayout = findViewById(R.id.swipe_container);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mSwipeRefreshLayout.setEnabled(true);

            //loader
            mLlLoader = findViewById(R.id.loaderLayout);
            mTvFetching = findViewById(R.id.fetching);
            mTvWait = findViewById(R.id.wait);

            //no internet connection
            nointernetconnection = findViewById(R.id.nointernetconnection);
            refreshnointernet = findViewById(R.id.refreshnointernet);
            refreshnointernet.setOnClickListener(this);

            //empty layout
            emptyLayout = findViewById(R.id.emptyLayout);
            textView11 = findViewById(R.id.textView11);
            refresh = findViewById(R.id.refresh);
            refresh.setOnClickListener(this);
            refresh.setEnabled(false);

            recyclerView = findViewById(R.id.voucherorders_process_queue_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
            recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
            recyclerView.setNestedScrollingEnabled(false);
            mAdapterVirtualVoucherRequest = new V2VirtualVoucherRequestProcessQueueAdapter(getViewContext());
            recyclerView.setAdapter(mAdapterVirtualVoucherRequest);
            mAdapterVirtualVoucherRequest.update(mGridData);

            txvRequestNote = findViewById(R.id.txvRequestNote);

            nested_scroll = findViewById(R.id.nested_scroll);
            nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll DOWN");
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                    if (scrollY < oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll UP");
                        mSwipeRefreshLayout.setEnabled(false);
                    }

                    if (scrollY == 0) {
//                    Logger.debug("antonhttp", "TOP SCROLL");
                        mSwipeRefreshLayout.setEnabled(true);
                    }

                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    Logger.debug("antonhttp", "======BOTTOM SCROLL=======");
                        mSwipeRefreshLayout.setEnabled(false);

                    }
                }
            });

            mLoaderTimer = new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    mLlLoader.setVisibility(View.GONE);
                }
            };

            if (mGridData.size() == 0) {
                txvRequestNote.setVisibility(View.GONE);
                getSession();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getSession() {
        refresh.setEnabled(false);
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            mTvFetching.setText("Fetching pending orders.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            //getProcessQueue();
            getProcessQueueV2();

        } else {
            refresh.setEnabled(true);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            hideProgressDialog();
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getProcessQueue() {
        Call<V2GetProcessQueueResponse> call = RetrofitBuild.getTransactionsApi(getViewContext())
                .getProcessQueue(
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        sessionid);

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0)
            call.enqueue(processQueueCallback);
        else
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
                    mGridData.clear();
                    mGridData = response.body().getVirtualVoucherRequest();

                    if (mdb != null) {
                        mdb.truncateTable(mdb, DatabaseHandler.VIRTUAL_VOUCHER_REQUEST);

                        for (V2VirtualVoucherRequestQueue request : mGridData) {
                            mdb.insertVirtualVoucherRequestQueue(mdb, request);
                        }
                    }
                    updateList(mdb.getVirtualVoucherRequestQueue(mdb));

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

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ViewPendingOrdersActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh:
            case R.id.refreshnointernet: {
                getSession();
                break;
            }
        }
    }

    private void updateList(List<V2VirtualVoucherRequestQueue> data) {
        showNoInternetConnection(false);
        if (data.size() > 0) {
            txvRequestNote.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            mAdapterVirtualVoucherRequest.update(data);
        } else {
            txvRequestNote.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onRefresh() {
        if (mdb != null) {
            mGridData.clear();
            mdb.truncateTable(mdb, DatabaseHandler.VIRTUAL_VOUCHER_REQUEST);
            mSwipeRefreshLayout.setRefreshing(true);
            getSession();
        }
    }


    /**
    * SECURITY UPDATE
    * AS OF
    * JANUARY 2020
    * */

    private void getProcessQueueV2(){
            if(CommonFunctions.getConnectivityStatus(getApplicationContext())  > 0){

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

            }else{
                refresh.setEnabled(true);
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                hideProgressDialog();
                showNoInternetConnection(true);
                showNoInternetToast();
            }

    }
    private void getProcessQueueObjectV2() {
        Call<GenericResponse> call = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getProcessQueueV2(authenticationid,sessionid,param);

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            call.enqueue(processQueueCallbackV2);
        }else {
            refresh.setEnabled(true);
            showError(getString(R.string.generic_internet_error_message));
        }
    }
    private Callback<GenericResponse> processQueueCallbackV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            refresh.setEnabled(true);
            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    Logger.debug("okhttp","DATA: "+data);

                    String loaddata = CommonFunctions.parseJSON(data,"loaddata");
                    String billspaydata =  CommonFunctions.parseJSON(data,"billspaydata");
                    String smartreloadqueue = CommonFunctions.parseJSON(data,"smartreloadqueue");
                    String buyvoucherqueue = CommonFunctions.parseJSON(data,"buyvoucherqueue");

                    Logger.debug("okhttp","LOAD DATA: "+loaddata);
                    Logger.debug("okhttp","billspaydata: "+billspaydata);
                    Logger.debug("okhttp","smartreloadqueue: "+smartreloadqueue);
                    Logger.debug("okhttp","buyvoucherqueue: "+buyvoucherqueue);

                    //success
                    mGridData = new Gson().fromJson(buyvoucherqueue,new TypeToken<List<V2VirtualVoucherRequestQueue>>(){}.getType());

                    mdb.truncateTable(mdb, DatabaseHandler.VIRTUAL_VOUCHER_REQUEST);

                    if (mGridData.size() > 0 && mGridData != null) {
                        for (V2VirtualVoucherRequestQueue request : mGridData) {
                            mdb.insertVirtualVoucherRequestQueue(mdb, request);
                        }
                    }
                    updateList(mdb.getVirtualVoucherRequestQueue(mdb));
                    mAdapterVirtualVoucherRequest.notifyDataSetChanged();

                } else {
                    if(response.body().getStatus().equals("error")){
                        showErrorToast();
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else{
                        showErrorToast(decryptedMessage);
                    }
                }
            } else {
                showError(getString(R.string.generic_error_message));
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            refresh.setEnabled(true);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showError(getString(R.string.generic_error_message));
        }
    };

}
