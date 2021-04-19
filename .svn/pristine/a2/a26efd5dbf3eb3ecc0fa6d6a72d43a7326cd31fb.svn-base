package com.goodkredit.myapplication.activities.dropoff;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.adapter.dropoff.PaymentRequestOrderDetailsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.dropoff.PaymentRequestCompletedDB;
import com.goodkredit.myapplication.database.dropoff.PaymentRequestPendingDB;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.dropoff.PaymentRequest;
import com.goodkredit.myapplication.model.dropoff.PaymentRequestOrderDetails;
import com.goodkredit.myapplication.responses.dropoff.CancelPaymentViaPartnerResponse;
import com.goodkredit.myapplication.responses.dropoff.GetPaymentRequestCompletedResponse;
import com.goodkredit.myapplication.responses.dropoff.GetPaymentRequestPendingResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRequestDetailsActivity extends BaseActivity implements View.OnClickListener {

    private PaymentRequest paymentrequest = null;

    private RecyclerView rvorderdetails;
    private List<PaymentRequestOrderDetails> mGridData = new ArrayList<>();
    private PaymentRequestOrderDetailsAdapter madapter;

    private DatabaseHandler mdb;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String merchantid;
    private String limit;
    private String devicetype;
    private String gktxnno;

    private Button btncancel;
    private Button btnpaynow;
    private TextView pr_orderinformation;
    private TextView pr_orderdetails;

    private TextView txvordertxnid;
    private TextView txvcustmobile;
    private TextView txvcustname;
    private TextView txvcustaddress;
    private TextView txvtotalnoofitems;
    private TextView txvtotalitemamount;
    private TextView txvothercharges;
    private TextView txvtotalamount;
    private TextView text_pr_totalamounttopay;

    static RelativeLayout emptyvoucher;
    static ImageView refresh;
    static RelativeLayout nointernetconnection;
    static ImageView refreshnointernet;
    static ImageView refreshdisabled;
    static ImageView refreshdisabled1;

    private Button btn_pr_pendingpayments;
    private MaterialDialog mDialog;

    private boolean isCancelClicked = false;
    Bundle bundle = new Bundle();
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropoff_paymentrequestdetails);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        try {
            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setupToolbar();

        rvorderdetails =(RecyclerView) findViewById(R.id.rv_pr_orderdetails);
        madapter = new PaymentRequestOrderDetailsAdapter (mGridData, getViewContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rvorderdetails.setNestedScrollingEnabled(false);
        rvorderdetails.setLayoutManager(layoutManager);
        rvorderdetails.setAdapter(madapter);

        pr_orderinformation = (TextView) findViewById(R.id.pr_orderinformation);
        pr_orderinformation.setTypeface(Typeface.DEFAULT_BOLD);
        pr_orderdetails = (TextView) findViewById(R.id.pr_orderdetails);
        pr_orderdetails.setTypeface(Typeface.DEFAULT_BOLD);

        btncancel = (Button) findViewById(R.id.btn_pr_cancel);
        btncancel.setTypeface(Typeface.DEFAULT_BOLD);
        btncancel.setOnClickListener(this);

        btnpaynow = (Button) findViewById(R.id.btn_pr_paynow);
        btnpaynow.setTypeface(Typeface.DEFAULT_BOLD);
        btnpaynow.setOnClickListener(this);

        txvordertxnid = (TextView) findViewById(R.id.txv_pr_ordertxnid);
        txvcustmobile = (TextView) findViewById(R.id.txv_pr_custmobile);
        txvcustname = (TextView) findViewById(R.id.txv_pr_custname);
        txvcustaddress = (TextView) findViewById(R.id.txv_pr_custaddress);
        txvtotalnoofitems = (TextView) findViewById(R.id.txv_pr_totalnoofitems);
        txvtotalitemamount = (TextView) findViewById(R.id.txv_pr_totalitemamount);
        txvothercharges = (TextView) findViewById(R.id.txv_pr_othercharges);

        txvtotalamount = (TextView) findViewById(R.id.txv_pr_totalamount);
        txvtotalamount.setTypeface(Typeface.DEFAULT_BOLD);
        text_pr_totalamounttopay = (TextView) findViewById(R.id.text_pr_totalamounttopay);
        text_pr_totalamounttopay.setTypeface(Typeface.DEFAULT_BOLD);

        //initialize refresh
        emptyvoucher = (RelativeLayout) findViewById(R.id.emptyvoucher);
        refresh = (ImageView) findViewById(R.id.refresh);
        nointernetconnection = (RelativeLayout) findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) findViewById(R.id.refreshnointernet);
        refreshdisabled = (ImageView) findViewById(R.id.refreshdisabled);
        refreshdisabled1 = (ImageView) findViewById(R.id.refreshdisabled1);

        refresh.setOnClickListener(this);

        btn_pr_pendingpayments = (Button) findViewById(R.id.btn_pr_pendingpayments);

//        ((BaseActivity)getViewContext()).setUpProgressLoader();
    }

    private void initData() {
        bundle = getIntent().getExtras();

        paymentrequest = getIntent().getParcelableExtra(PaymentRequest.KEY_PAYMENTREQUEST);

        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        merchantid = ".";
        limit = "0";
        devicetype = "ANDROID";
        gktxnno = paymentrequest.getGKTxnNo();

        txvordertxnid.setText(paymentrequest.getOrderTxnID());
        txvcustmobile.setText("+".concat(paymentrequest.getCustomerMobileNumber()));
        txvcustname.setText(CommonFunctions.replaceEscapeData(paymentrequest.getCustomerFirstName() + " " + paymentrequest.getCustomerLastName()));
        txvcustaddress.setText(CommonFunctions.replaceEscapeData(paymentrequest.getCustomerAddress()));
        txvtotalnoofitems.setText(String.valueOf(paymentrequest.getTotalItems()));
        txvtotalitemamount.setText("₱ ".concat(CommonFunctions.currencyFormatter(String.valueOf(paymentrequest.getTotalItemAmount()))));
        txvothercharges.setText("₱ ".concat(CommonFunctions.currencyFormatter(String.valueOf(paymentrequest.getOtherCharge()))));
        txvtotalamount.setText("₱ ".concat(CommonFunctions.currencyFormatter(String.valueOf(paymentrequest.getTotalAmount()))));

        from = bundle.getString("from");
        if(from !=null){
            if(from.equals("PendingAdapter")){

                btn_pr_pendingpayments.setVisibility(View.VISIBLE);
                btnpaynow.setVisibility(View.VISIBLE);
                btncancel.setVisibility(View.VISIBLE);
                txvtotalamount.setVisibility(View.VISIBLE);
                text_pr_totalamounttopay.setVisibility(View.VISIBLE);

//                if(!(paymentrequest.getGKTxnNo() != null
//                        || paymentrequest.getGKTxnNo().equals("."))){
//                    gktxnno = paymentrequest.getGKTxnNo();
//                }

//                if(mGridData.size()>0){
//
//                }

                if(paymentrequest.getPaymentType().isEmpty() || paymentrequest.getPaymentType().equals(".")){
                    btn_pr_pendingpayments.setVisibility(View.GONE);
                    btnpaynow.setVisibility(View.VISIBLE);
                    btncancel.setVisibility(View.GONE);
                } else{
                    if(paymentrequest.getPaymentType().equalsIgnoreCase("PAY VIA PARTNER")){
                        btn_pr_pendingpayments.setVisibility(View.VISIBLE);
                        btn_pr_pendingpayments.setOnClickListener(this);
                        btnpaynow.setVisibility(View.GONE);
                        btncancel.setVisibility(View.VISIBLE);
                    } else{
                        btn_pr_pendingpayments.setVisibility(View.GONE);
                        btnpaynow.setVisibility(View.VISIBLE);
                        btncancel.setVisibility(View.GONE);
                    }
                }

            } else{
                btn_pr_pendingpayments.setVisibility(View.GONE);
                btnpaynow.setVisibility(View.GONE);
                btncancel.setVisibility(View.GONE);
                txvtotalamount.setVisibility(View.GONE);
                text_pr_totalamounttopay.setVisibility(View.GONE);
            }
        }

        getSession();
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            if(isCancelClicked){
                ((BaseActivity)getViewContext()).setUpProgressLoader();
            }

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if(from != null){
                if(from.equals("PendingAdapter")){
                    if(isCancelClicked){
                        cancelPaymentViaPartner(cancelPaymentViaPartnerSession);
                        isCancelClicked = false;
                    } else{
                        getPaymentRequestPending(getPaymentRequestPendingSession);
                    }
                } else if(from.equals("CompletedAdapter")){
                    getPaymentRequestCompleted(getPaymentRequestCompletedSession);
                }
            }

        } else{
            showError(getString(R.string.generic_internet_error_message));
            ((BaseActivity) getViewContext()).setUpProgressLoaderDismissDialog();
        }
    }

    private void getPaymentRequestPending (Callback<GetPaymentRequestPendingResponse> getPaymentRequestPendingCallback) {
        Call<GetPaymentRequestPendingResponse> getpaymentrequestpending = RetrofitBuild.getDropOffAPIService(getViewContext())
                .getPaymentRequestPendingCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        merchantid,
                        limit,
                        devicetype);

        getpaymentrequestpending.enqueue(getPaymentRequestPendingCallback);
    }

    private final Callback<GetPaymentRequestPendingResponse> getPaymentRequestPendingSession = new Callback<GetPaymentRequestPendingResponse>() {
        @Override
        public void onResponse(Call<GetPaymentRequestPendingResponse> call, Response<GetPaymentRequestPendingResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    if(mdb != null){
                        mdb.truncateTable(mdb, PaymentRequestPendingDB.TABLE_NAME);

                        List<PaymentRequest> paymentrequestpendinglist = new ArrayList<>();
                        paymentrequestpendinglist = response.body().getPaymentRequestPendingList();
                        for(PaymentRequest paymentrequestpending : paymentrequestpendinglist){
                            mdb.insertPaymentRequestPending(mdb, paymentrequestpending);
                        }

                        List<PaymentRequest> prpList = mdb.getPaymentRequestPendingDetails(mdb, paymentrequest.getOrderTxnID());
                        String orderdetails = "";
                        for(PaymentRequest paymentRequestPending: prpList) {
                            orderdetails = paymentRequestPending.getOrderDetails();
                        }

                        String orderdetailsjson = CommonFunctions.parseJSON(orderdetails, "data");

                        ArrayList<PaymentRequestOrderDetails> paymentRequestOrderDetailslist = new ArrayList<>();
                        paymentRequestOrderDetailslist = new Gson().fromJson(orderdetailsjson, new TypeToken<List<PaymentRequestOrderDetails>>() {
                        }.getType());

                        madapter.update(paymentRequestOrderDetailslist);

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

    private void getPaymentRequestCompleted (Callback<GetPaymentRequestCompletedResponse> getPaymentRequestCompletedCallback) {
        Call<GetPaymentRequestCompletedResponse> getpaymentrequestcompleted = RetrofitBuild.getDropOffAPIService(getViewContext())
                .getPaymentRequestCompletedCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        merchantid,
                        limit,
                        devicetype);
        getpaymentrequestcompleted.enqueue(getPaymentRequestCompletedCallback);
    }

    private final Callback<GetPaymentRequestCompletedResponse> getPaymentRequestCompletedSession = new Callback<GetPaymentRequestCompletedResponse>() {
        @Override
        public void onResponse(Call<GetPaymentRequestCompletedResponse> call, Response<GetPaymentRequestCompletedResponse> response) {
            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    if(mdb != null){
                        mdb.truncateTable(mdb, PaymentRequestCompletedDB.TABLE_NAME);

                        List<PaymentRequest> paymentrequestcompletedlist = new ArrayList<>();
                        paymentrequestcompletedlist = response.body().getPaymentRequestCompletedList();
                        for(PaymentRequest paymentrequestcompleted : paymentrequestcompletedlist){
                            mdb.insertPaymentRequestCompleted(mdb, paymentrequestcompleted);
                        }

                        List<PaymentRequest> prcList = mdb.getPaymentRequestCompletedDetails(mdb, paymentrequest.getOrderTxnID());
                        String orderdetails = "";
                        for(PaymentRequest paymentRequestCompleted : prcList){
                            orderdetails = paymentRequestCompleted.getOrderDetails();
                        }

                        String orderdetailsjson = CommonFunctions.parseJSON(orderdetails, "data");

                        ArrayList<PaymentRequestOrderDetails> paymentRequestOrderDetailslist = new ArrayList<>();
                        paymentRequestOrderDetailslist = new Gson().fromJson(orderdetailsjson, new TypeToken<List<PaymentRequestOrderDetails>>() {
                        }.getType());

                        madapter.update(paymentRequestOrderDetailslist);
                    }
                } else{
                    showError(response.body().getMessage());
                }
            } else{
                showError();
            }
        }

        @Override
        public void onFailure(Call<GetPaymentRequestCompletedResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };


    private void cancelPaymentViaPartner (Callback<CancelPaymentViaPartnerResponse> cancelPaymentViaPartnerCallback){
        Call<CancelPaymentViaPartnerResponse> cancelpaymentviapartner = RetrofitBuild.getDropOffAPIService(getViewContext())
                .cancelPaymentViaPartnerCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        gktxnno,
                        devicetype);

        Logger.debug("vanhttp", "gktxnno: " + gktxnno);

        cancelpaymentviapartner.enqueue(cancelPaymentViaPartnerCallback);
    }

    private final Callback<CancelPaymentViaPartnerResponse> cancelPaymentViaPartnerSession = new Callback<CancelPaymentViaPartnerResponse>() {
        @Override
        public void onResponse(Call<CancelPaymentViaPartnerResponse> call, Response<CancelPaymentViaPartnerResponse> response) {
            ResponseBody errorBody = response.errorBody();

            ((BaseActivity) getViewContext()).setUpProgressLoaderDismissDialog();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    showCancellationSuccessfulDialog();
                } else{
                    showError();
                }
            } else{
                showError();
            }
        }

        @Override
        public void onFailure(Call<CancelPaymentViaPartnerResponse> call, Throwable t) {
            t.getLocalizedMessage();
            Logger.debug("vanhttp", "msg: " + t.getLocalizedMessage());
            ((BaseActivity) getViewContext()).setUpProgressLoaderDismissDialog();
            showError();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCancellationSuccessfulDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("You have cancelled your request.")
                .cancelable(false)
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //proceedtoMainActivity();
                        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        Intent intent = new Intent(getViewContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {

        Bundle args = new Bundle();

        switch (v.getId()){
            case R.id.btn_pr_cancel: {

                isCancelClicked = true;
                showCancelRequestWarningDialog();

                break;
            }

            case R.id.btn_pr_paynow: {
                args.putParcelable(PaymentRequest.KEY_PAYMENTREQUEST, paymentrequest);
                Intent intent = new Intent(getViewContext(), PaymentRequestOptionActivity.class);
                intent.putExtras(args);
                startActivity(intent);
                break;
            }

            case R.id.refresh: {
                disableRefresh();
                getSession();
                break;
            }
            case R.id.btn_pr_pendingpayments: {
                args.putParcelable(PaymentRequest.KEY_PAYMENTREQUEST, paymentrequest);
                args.putString("from", "fromPendingRequestDetails");
                Intent intent = new Intent(getViewContext(), PaymentRequestPayViaPartnerActivity.class);
                intent.putExtras(args);
                startActivity(intent);
                break;
            }
        }
    }

    private void showCancelRequestWarningDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("Are you sure you want to cancel your payment request?")
                .negativeText("Cancel")
                .positiveText("Proceed")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getSession();
                    }
                })
                .show();
    }

    //disable refresh button in empty screen
    private void disableRefresh() {
        try {
            refreshdisabled.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            refreshdisabled1.setVisibility(View.VISIBLE);
            refreshnointernet.setVisibility(View.GONE);
        } catch (Exception e) {
        }

    }

    //enable refresh button in empty screen
    private void inableRefresh() {
        try {
            refreshdisabled.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
            refreshdisabled1.setVisibility(View.GONE);
            refreshnointernet.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }

    }

    //show no internet connection page
    private void showNoInternetConnection() {
        try {
            nointernetconnection.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }

    }

    //hide no internet connection page
    private void hideNoInternetConnection() {
        try {
            nointernetconnection.setVisibility(View.GONE);
            inableRefresh();
        } catch (Exception e) {
        }

    }
}
