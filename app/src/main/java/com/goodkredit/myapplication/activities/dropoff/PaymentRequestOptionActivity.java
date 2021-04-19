package com.goodkredit.myapplication.activities.dropoff;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.dropoff.PaymentRequest;
import com.goodkredit.myapplication.responses.DiscountResponse;
import com.goodkredit.myapplication.responses.dropoff.ProcessDropOffPayViaPartnerResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRequestOptionActivity extends BaseActivity implements View.OnClickListener {

    private PaymentRequest paymentrequest = null;
    private GKService service = null;

    private DatabaseHandler mdb;
    private String imei;
    private String userid;
    private String authcode;
    private String sessionid;
    private String borrowerid;
    private String merchantid;
    private double amountpaid;
    private String servicecode;
    private String longitude;
    private String latitude;

    private String branchid;
    private String ordertxnid;
    private String gktxnno;
    private String hasdiscount;
    private String grossamount;

    private RadioGroup rg_paymentoptions;
    private RadioButton rb_gkvoucher;
    private RadioButton rb_partner;
    private Button btn_proceed;

    private TextView txv_pr_ordertxnid;
    private TextView txv_pr_merchantname;
    private TextView txv_pr_totalamount;

    Bundle bundle = new Bundle();

    private String totalamount;

    private double resellerDiscount = 0;
    private double resellerAmount = 0;
    private double resellerTotalAmount = 0;

    private String type;
    private String vouchersession;

    private int totaldelaytime = 10000;
    private int currentdelaytime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropoff_paymentoption);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        Logger.debug("vanhttp", "okay, paymentrequestactivity");

        try {
            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setupToolbar();
        rg_paymentoptions = (RadioGroup) findViewById(R.id.rg_pr_paymentoptions);
        rb_gkvoucher = (RadioButton) findViewById(R.id.rb_pr_gkvoucher);
        rb_partner = (RadioButton) findViewById(R.id.rb_pr_channel);
        btn_proceed = (Button) findViewById(R.id.btn_pr_proceed);

        btn_proceed.setOnClickListener(this);

        txv_pr_ordertxnid = (TextView) findViewById(R.id.txv_pr_itemname);
        txv_pr_merchantname = (TextView) findViewById(R.id.txv_pr_itemamount);
        txv_pr_totalamount = (TextView) findViewById(R.id.txv_pr_totalamount);
    }

    private void initData() {
        GPSTracker gpsTracker = new GPSTracker(getViewContext());

        paymentrequest = getIntent().getParcelableExtra(PaymentRequest.KEY_PAYMENTREQUEST);

        totalamount = String.valueOf(paymentrequest.getTotalAmount());

        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        merchantid = paymentrequest.getMerchantID();
        amountpaid = Double.valueOf(totalamount);
        servicecode = "DROPOFF";
        longitude = String.valueOf(gpsTracker.getLongitude());
        latitude = String.valueOf(gpsTracker.getLatitude());

        branchid = paymentrequest.getMerchantBranchID();
        ordertxnid = paymentrequest.getOrderTxnID();
        gktxnno = paymentrequest.getGKTxnNo();

        txv_pr_ordertxnid.setText("Txn#: ".concat(paymentrequest.getOrderTxnID()));
        txv_pr_totalamount.setText("₱".concat(CommonFunctions.currencyFormatter(String.valueOf(paymentrequest.getTotalAmount()))));
        txv_pr_merchantname.setText(CommonFunctions.replaceEscapeData(paymentrequest.getMerchantName()));
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            if(type.equals("payviapartner")){
                processDropOffPayViaPartner(processDropOffPayViaPartnerSession);
            } else{
                calculateDiscountForReseller(calculateDiscountForResellerSession);
            }
        } else{
            showError(getString(R.string.generic_internet_error_message));
        }
    }

    private void calculateDiscountForReseller(Callback<DiscountResponse> calculateDiscountForResellerCallback){
        Call<DiscountResponse> resellerdiscount = RetrofitBuild.getDiscountService(getViewContext())
                .calculateDiscountForReseller(userid,
                        imei,
                        authcode,
                        sessionid,
                        borrowerid,
                        merchantid,
                        amountpaid,
                        servicecode,
                        longitude,
                        latitude);

        resellerdiscount.enqueue(calculateDiscountForResellerCallback);
    }

    private final Callback<DiscountResponse> calculateDiscountForResellerSession = new Callback<DiscountResponse>() {

        @Override
        public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if(response.body().getStatus().equals("000")){
                    resellerAmount = amountpaid;
                    resellerDiscount = response.body().getDiscount();
                    resellerTotalAmount = resellerAmount - resellerDiscount;

                    if(resellerDiscount > 0){
                        showDialogDiscount();
                    } else{
                        prePurchase(prePurchaseSession);
                    }
                }
            } else {
                showError();
            }
        }

        @Override
        public void onFailure(Call<DiscountResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void processDropOffPayViaPartner (Callback<ProcessDropOffPayViaPartnerResponse> processDropOffPayViaPartnerCallback){

        if(resellerDiscount > 0){
            hasdiscount = "1";
        } else{
            hasdiscount = "0";
        }

        grossamount = String.valueOf(resellerTotalAmount);

        Call<ProcessDropOffPayViaPartnerResponse> processdropoffpayviapartner = RetrofitBuild.getDropOffAPIService(getViewContext())
                .processDropOffPayViaPartnerCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        merchantid,
                        branchid,
                        ordertxnid,
                        gktxnno,
                        hasdiscount,
                        servicecode,
                        grossamount,
                        longitude,
                        latitude);

        processdropoffpayviapartner.enqueue(processDropOffPayViaPartnerCallback);
    }

    private final Callback<ProcessDropOffPayViaPartnerResponse> processDropOffPayViaPartnerSession = new Callback<ProcessDropOffPayViaPartnerResponse>() {
        @Override
        public void onResponse(Call<ProcessDropOffPayViaPartnerResponse> call, Response<ProcessDropOffPayViaPartnerResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){

                    ((BaseActivity)getViewContext()).setUpProgressLoaderDismissDialog();
                    String data = response.body().getData();
                    bundle.putParcelable(PaymentRequest.KEY_PAYMENTREQUEST, paymentrequest);
                    bundle.putString("resellertotalamount", String.valueOf(resellerTotalAmount));
                    bundle.putDouble("resellerdiscount", resellerDiscount);
                    bundle.putString("billingreferenceno",data);
                    bundle.putString("from", "fromPaymentOptions");
//                    ((PaymentRequestActivity)getViewContext()).displayView(4, bundle);
                    Intent intent = new Intent(getViewContext(), PaymentRequestPayViaPartnerActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else{
                    showError(response.body().getMessage());
                }
            } else{
                showError();
            }
        }

        @Override
        public void onFailure(Call<ProcessDropOffPayViaPartnerResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void prePurchase (Callback<String> prePurchaseCallback){
        Call<String> prepurchase = RetrofitBuild.prePurchaseService(getViewContext())
                .prePurchaseCall(borrowerid,
                        String.valueOf(resellerTotalAmount),
                        userid,
                        imei,
                        sessionid,
                        authcode);

        prepurchase.enqueue(prePurchaseCallback);
    }

    private final Callback<String> prePurchaseSession = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try{
                ResponseBody errorBody = response.errorBody();
                if(errorBody == null){

                    vouchersession = response.body().toString();

                    if(!vouchersession.isEmpty()){
                        if(vouchersession.equals("001")){
                            showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
                        } else if(vouchersession.equals("error")){
                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                        } else if(vouchersession.contains("<!DOCTYPE html>")){
                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                        } else{

                            Intent intent = new Intent(getViewContext(), Payment.class);
                            intent.putExtra("PAYMENTREQUEST", "PAYMENTREQUEST");
                            intent.putExtra(PaymentRequest.KEY_PAYMENTREQUEST, paymentrequest);
                            intent.putExtra("vouchersession", vouchersession);
                            intent.putExtra("totalamount", String.valueOf(resellerTotalAmount));
                            intent.putExtra("resellerdiscount", String.valueOf(resellerDiscount));
                            Logger.debug("vanhttp", "total amount: " + String.valueOf(resellerTotalAmount));
                            startActivity(intent);
                        }
                    }
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };
    private void showDialogDiscount(){
        try{
            TextView popconfirm;
            TextView popcancel;
            TextView popamountpaid;
            TextView popservicecharge;
            TextView poptotalamount;
            TextView confirmationlbl;

            final Dialog dialog = new Dialog(new ContextThemeWrapper(getViewContext(), android.R.style.Theme_Holo_Light));
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_discount_dialog);

            popamountpaid = (TextView) dialog.findViewById(R.id.popamounttopayval);
            popservicecharge = (TextView) dialog.findViewById(R.id.popservicechargeval);
            poptotalamount = (TextView) dialog.findViewById(R.id.poptotalval);
            popconfirm = (TextView) dialog.findViewById(R.id.popok);
            popcancel = (TextView) dialog.findViewById(R.id.popcancel);
            confirmationlbl = (TextView) dialog.findViewById(R.id.confirmationlbl);

            //set value
            popamountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerAmount)));
            popservicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerDiscount)));
            poptotalamount.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerTotalAmount)));

            dialog.show();

            //click close
            popcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }

            });

            popconfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    prePurchase(prePurchaseSession);

//                    if(type.equals("payviapartner")){
//                        btn_proceed.setVisibility(View.GONE);
//                        dialog.dismiss();
//
//                        ((BaseActivity)getViewContext()).setUpProgressLoader();
//
//                        final Handler handlerstatus = new Handler();
//                        handlerstatus.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                ((BaseActivity)getViewContext()).setUpProgressLoaderMessageDialog("Checking status, Please wait...");
//                            }
//                        }, 1000);
//
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                currentdelaytime = currentdelaytime + 1000;
//                                ((BaseActivity) getViewContext()).setUpProgressLoaderMessageDialog("Processing request, Please wait...");
//                                processDropOffPayViaPartner(processDropOffPayViaPartnerSession);
//                            }
//                        }, 1000);
//
//
//                    } else{
//                        dialog.dismiss();
//
////                        Intent intent = new Intent(getViewContext(), Payment.class);
////                        intent.putExtra("vouchersession", vouchersession);
////                        startActivity(intent);
//                        prePurchase(prePurchaseSession);
//                    }

                }

            });


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pr_proceed:{

                try{
                    int selectedID = rg_paymentoptions.getCheckedRadioButtonId();

                    if(selectedID == -1){
                        showError("Please choose a payment option");

                    } else{

                        if(selectedID == rb_gkvoucher.getId()){
                            type = "payviagkvoucher";
                            btn_proceed.setVisibility(View.GONE);
                            getSession();

                        } else if(selectedID == rb_partner.getId()){
                            type = "payviapartner";
                            btn_proceed.setVisibility(View.GONE);
                            getSession();

                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
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
    protected void onResume() {
        super.onResume();
        btn_proceed.setVisibility(View.VISIBLE);
    }
}
