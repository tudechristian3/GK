package com.goodkredit.myapplication.activities.dropoff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.dropoff.PaymentRequest;
import com.goodkredit.myapplication.responses.dropoff.ProcessGKDropOffConsummationResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRequestConfirmationActivity extends BaseActivity implements View.OnClickListener {

    private PaymentRequest paymentrequest;
    private GKService service;

    private TextView confirmtransaction;
    private TextView txvmerchantname;
    private TextView txvamount;
    private TextView txvsercharge;
    private TextView txvothercharge;
    private TextView txvresellerdiscount;
    private TextView txvamounttopay;
    private TextView txvamounttendered;
    private TextView txvchange;

    private MaterialDialog mDialog;
    private Button btnconfirm;

    private double amounttopay;
    private double amounttendered;
    private double resellerdiscount;

    private String sessionid;
    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;
    private String merchantid;
    private String branchid;
    private String ordertxnid;
    private String gktxnno;
    private String hasdiscount;
    private String servicecode;
    private String grossamount;
    private String vouchersession;
    private double longitude;
    private double latitude;

    //GK NEGOSYO
    private String distributorid = "";
    private String resellerid = "";
    private boolean checkIfReseller = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropoff_paymentrequestconfirmation);
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

        confirmtransaction = (TextView) findViewById(R.id.mdp_confirmtransaction);
        txvmerchantname = (TextView) findViewById(R.id.txv_pr_merchantname);
        txvamount = (TextView) findViewById(R.id.txv_pr_amount);
        txvsercharge = (TextView) findViewById(R.id.txv_pr_sercharge);
        txvothercharge = (TextView) findViewById(R.id.txv_pr_othercharge);
        txvresellerdiscount = (TextView) findViewById(R.id.txv_pr_resellerdiscount);
        txvamounttopay = (TextView) findViewById(R.id.txv_pr_amounttopay);
        txvamounttendered = (TextView) findViewById(R.id.txv_pr_amounttendered);
        txvchange = (TextView) findViewById(R.id.txv_pr_change);

        confirmtransaction.setTypeface(Typeface.DEFAULT_BOLD);
        txvmerchantname.setTypeface(Typeface.DEFAULT_BOLD);
        txvamount.setTypeface(Typeface.DEFAULT_BOLD);
        txvsercharge.setTypeface(Typeface.DEFAULT_BOLD);
        txvothercharge.setTypeface(Typeface.DEFAULT_BOLD);
        txvresellerdiscount.setTypeface(Typeface.DEFAULT_BOLD);
        txvamounttopay.setTypeface(Typeface.DEFAULT_BOLD);
        txvamounttendered.setTypeface(Typeface.DEFAULT_BOLD);
        txvchange.setTypeface(Typeface.DEFAULT_BOLD);

        btnconfirm = (Button) findViewById(R.id.mdp_confirm);
        btnconfirm.setOnClickListener(this);

    }

    private void initData() {
        Bundle args = getIntent().getExtras();
        paymentrequest = args.getParcelable(PaymentRequest.KEY_PAYMENTREQUEST);

        amounttopay = Double.valueOf(args.getString("amounttopay"));
        amounttendered = args.getDouble("amounttendered");
        resellerdiscount = args.getDouble("resellerdiscount");

        GPSTracker gpsTracker = new GPSTracker(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        merchantid = paymentrequest.getMerchantID();
        branchid = paymentrequest.getMerchantBranchID();
        ordertxnid = paymentrequest.getOrderTxnID();
        gktxnno = paymentrequest.getGKTxnNo();
        vouchersession = args.getString("vouchersession");
        servicecode = "DROPOFF";
        longitude = gpsTracker.getLongitude();
        latitude = gpsTracker.getLatitude();
        hasdiscount = "0";
        grossamount = String.valueOf(amounttopay);

        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

        double amount = (paymentrequest.getTotalAmount() - (paymentrequest.getCustomerServiceCharge() + paymentrequest.getOtherCharge() + resellerdiscount));

        txvmerchantname.setText(CommonFunctions.replaceEscapeData(paymentrequest.getMerchantName()));
        txvamount.setText("₱ ".concat(CommonFunctions.currencyFormatter(String.valueOf(amount))));
        txvsercharge.setText("₱ ".concat(CommonFunctions.currencyFormatter(String.valueOf(paymentrequest.getCustomerServiceCharge()))));
        txvothercharge.setText("₱ ".concat(CommonFunctions.currencyFormatter(String.valueOf(paymentrequest.getOtherCharge()))));
        txvresellerdiscount.setText("₱ ".concat(CommonFunctions.currencyFormatter(String.valueOf(resellerdiscount))));
        txvamounttopay.setText("₱ ".concat(CommonFunctions.currencyFormatter(String.valueOf(amounttopay))));
        txvamounttendered.setText("₱ ".concat(CommonFunctions.currencyFormatter(String.valueOf(amounttendered))));
        txvchange.setText("₱ ".concat(CommonFunctions.currencyFormatter(String.valueOf(amounttendered-amounttopay))));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//        }
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mdp_confirm: {
                getSession();
            }
        }
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            setUpProgressLoader();
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            processGKDropOffConsummation(processGKDropOffConsummationSession);
        } else{
            setUpProgressLoaderDismissDialog();
            showError(getString(R.string.generic_internet_error_message));
        }
    }

    private void processGKDropOffConsummation(Callback<ProcessGKDropOffConsummationResponse> processDropOffConsummationCallback) {
        Call<ProcessGKDropOffConsummationResponse> processdropoffconsummation = RetrofitBuild.getDropOffAPIService(getViewContext())
                .processGKDropOffConsummationCall(sessionid,
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
                        vouchersession,
                        String.valueOf(longitude),
                        String.valueOf(latitude));

        processdropoffconsummation.enqueue(processDropOffConsummationCallback);
    }

    private final Callback<ProcessGKDropOffConsummationResponse> processGKDropOffConsummationSession = new Callback<ProcessGKDropOffConsummationResponse>() {
        @Override
        public void onResponse(Call<ProcessGKDropOffConsummationResponse> call, Response<ProcessGKDropOffConsummationResponse> response) {
            ResponseBody errorBody = response.errorBody();

            setUpProgressLoaderDismissDialog();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    String referenceno = response.body().getData();

                    if(distributorid.equals("") || distributorid.equals(".")
                            || resellerid.equals("") || resellerid.equals(".")){
                        checkIfReseller = false;
                    } else{
                        checkIfReseller = true;
                    }
//                    showGlobalDialogs("You have successfully paid "
//                                    + CommonFunctions.currencyFormatter(String.valueOf(amounttopay))
//                                    + " of your Payment Request with the Order Transaction # "
//                                    + referenceno + "." + "You can check your transaction under Usage Menu. "
//                                    + "Thank you for using GoodKredit.",
//                            "close", ButtonTypeEnum.SINGLE,
//                            checkIfReseller, false, DialogTypeEnum.SUCCESS);

                    String message = "You have successfully paid "
                                    + CommonFunctions.currencyFormatter(String.valueOf(amounttopay))
                                    + " of your Payment Request with the Order Transaction # "
                                    + referenceno + "." + "You can check your transaction under Usage Menu. "
                                    + "Thank you for using GoodKredit.";
                    showSuccessDialog(message);
                }
            } else{
//                showGlobalDialogs(response.body().getMessage(), "retry",
//                        ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);

                 showFailedDialog(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<ProcessGKDropOffConsummationResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            hideProgressDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showSuccessDialog(String message) {
        //SHOW SUCCESS DIALOG
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("SUCCESS", message, "OK", "",
                ButtonTypeEnum.SINGLE, checkIfReseller, false, DialogTypeEnum.SUCCESS);

        View closebtn = dialog.btnCloseImage();
        closebtn.setVisibility(View.GONE);

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });
    }

    private void showFailedDialog(String message) {
        //SHOW SUCCESS DIALOG
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("FAILED", message, "Retry", "",
                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);

        View closebtn = dialog.btnCloseImage();
        closebtn.setVisibility(View.GONE);

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });
    }

    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, PaymentRequestConfirmationActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }
}
