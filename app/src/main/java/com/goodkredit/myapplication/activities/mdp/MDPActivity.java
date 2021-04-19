package com.goodkredit.myapplication.activities.mdp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.dropoff.PaymentRequestActivity;
import com.goodkredit.myapplication.activities.gkstore.GKStoreDetailsActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.dropoff.PaymentRequestPendingDB;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.dropoff.PaymentRequest;
import com.goodkredit.myapplication.responses.dropoff.GetPaymentRequestPendingResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MDPActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layout_pendingorders;

    private LinearLayout layout_mdpstore;
    private LinearLayout layout_bulletin;
    private LinearLayout layout_support;

    private DatabaseHandler mdb;
    private String imei;
    private String sessionid;
    private String userid;
    private String authcode;
    private String borrowerid;
    private String merchantid;
    private String limit;
    private String devicetype;

    private TextView txvpendingorderscount;

    private GKService service;

    private String from;

    private ImageView imgpending1;
    private ImageView imgpending2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdp);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        try {
            from = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_MDPTYPE);
            if(from.equals("fromDROPOFF")){
                merchantid = ".";
            } else if(from.equals("fromMDP")){
                merchantid = "MDP";
            }

            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setupToolbar();

        layout_pendingorders = (RelativeLayout) findViewById(R.id.layout_mdp_pendingorders);
        layout_mdpstore = (LinearLayout) findViewById(R.id.layout_mdp_store);
        layout_bulletin = (LinearLayout) findViewById(R.id.layout_mdp_bulletin);
        layout_support = (LinearLayout) findViewById(R.id.layout_mdp_support);

        layout_pendingorders.setOnClickListener(this);
        layout_mdpstore.setOnClickListener(this);
        layout_bulletin.setOnClickListener(this);
        layout_support.setOnClickListener(this);

        txvpendingorderscount = (TextView) findViewById(R.id.mdp_pendingorders_count);

        service = getIntent().getParcelableExtra(GKService.KEY_SERVICE_OBJ);

        imgpending1 = (ImageView) findViewById(R.id.img_mdp_pendingrequest1);
        imgpending2 = (ImageView) findViewById(R.id.img_mdp_pendingrequest2);
    }

    private void initData() {

        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
//        merchantid = "MDP";
        limit = "0";
        devicetype = "ANDROID";
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        if (mdb.getPaymentRequestPending(mdb).size() > 0) {
            imgpending1.setVisibility(View.VISIBLE);
            imgpending2.setVisibility(View.GONE);
            txvpendingorderscount.setVisibility(View.VISIBLE);
            txvpendingorderscount.setText(String.valueOf(mdb.getPaymentRequestPending(mdb).size()));
            getSession();

        } else {
            imgpending1.setVisibility(View.GONE);
            imgpending2.setVisibility(View.VISIBLE);
            txvpendingorderscount.setVisibility(View.GONE);
//            txvpendingorderscount.setText(String.valueOf(mdb.getPaymentRequestPending(mdb).size()));

            getSession();
        }
//


    }

//    private void getSession() {
//        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//            call.enqueue(callsession);
//        } else{
//            showError(getString(R.string.generic_internet_error_message));
//        }
//    }
//
//    private final Callback<String> callsession = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//
//            Logger.debug("vanhttp", "callsession1");
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    getCollectPendingOrders(getCollectPendingOrdersSession);
//                }
//            } else {
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getCollectPendingOrders(getCollectPendingOrdersSession);
        } else{
            showError(getString(R.string.generic_internet_error_message));
        }
    }

    private void getCollectPendingOrders(Callback<GetPaymentRequestPendingResponse> getCollectPendingOrdersCallback) {
        Call<GetPaymentRequestPendingResponse> getCollectPendingOrders = RetrofitBuild.getDropOffAPIService(getViewContext())
                .getPaymentRequestPendingCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        merchantid,
                        limit,
                        devicetype);
        getCollectPendingOrders.enqueue(getCollectPendingOrdersCallback);
    }

    private final Callback<GetPaymentRequestPendingResponse> getCollectPendingOrdersSession = new Callback<GetPaymentRequestPendingResponse>() {
        @Override
        public void onResponse(Call<GetPaymentRequestPendingResponse> call, Response<GetPaymentRequestPendingResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                if(response.body().getStatus().equals("000")){

                    mdb.truncateTable(mdb, PaymentRequestPendingDB.TABLE_NAME);

                    List<PaymentRequest> mdppendingordersList = response.body().getPaymentRequestPendingList();
                    for(PaymentRequest mdppendingorders : mdppendingordersList){
                        mdb.insertPaymentRequestPending(mdb, mdppendingorders);
                    }

//                    txvpendingorderscount.setText(String.valueOf(mdb.getPaymentRequestPending(mdb).size()));
//                    Logger.debug("vanhttp", "count: " + String.valueOf(mdb.getPaymentRequestPending(mdb).size()));

                    if(mdb.getPaymentRequestPending(mdb).size() > 0){
                        imgpending1.setVisibility(View.VISIBLE);
                        imgpending2.setVisibility(View.GONE);
                        txvpendingorderscount.setVisibility(View.VISIBLE);
                        txvpendingorderscount.setText(String.valueOf(mdb.getPaymentRequestPending(mdb).size()));


                    } else{
                        imgpending1.setVisibility(View.GONE);
                        imgpending2.setVisibility(View.VISIBLE);
                        txvpendingorderscount.setVisibility(View.GONE);
//            txvpendingorderscount.setText(String.valueOf(mdb.getPaymentRequestPending(mdb).size()));

                    }
                } else{
                    showError(response.body().getMessage());
                }
            } else{
                showError();
            }
        }

        @Override
        public void onFailure(Call<GetPaymentRequestPendingResponse> call, Throwable t) {
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
        Bundle args = new Bundle();
        switch (v.getId()){
            case R.id.layout_mdp_pendingorders:{
                PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_MDPTYPE);
                PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_MDPTYPE, "fromMDP");
                PaymentRequestActivity.start(getViewContext(), 1, service, args);
                break;
            }

            case R.id.layout_mdp_store: {
                Intent intent = new Intent(getViewContext(), GKStoreDetailsActivity.class);
                intent.putExtra("GKSERVICE_OBJECT", service);
                startActivity(intent);
                break;
            }
            case R.id.layout_mdp_bulletin: {
                Intent intent = new Intent(getViewContext(), MDPBulletinActivity.class);
                intent.putExtra(GKService.KEY_SERVICE_OBJ, service);
                startActivity(intent);
                break;
            }
            case R.id.layout_mdp_support: {
                MDPSupportActivity.start(getViewContext(), 1, service, args);
                break;
            }
        }
    }
}
