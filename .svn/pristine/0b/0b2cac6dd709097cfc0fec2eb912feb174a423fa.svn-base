package com.goodkredit.myapplication.activities.prepaidrequest;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.eghl.sdk.EGHL;
import com.eghl.sdk.params.PaymentParams;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.adapter.prepaidrequest.VirtualVoucherProductDialogRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.PrepaidRequest;
import com.goodkredit.myapplication.bean.VirtualVoucherRequest;
import com.goodkredit.myapplication.bean.prepaidrequest.EGHLPayment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.GKNegosyoResellerInfo;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.responses.prepaidrequest.CalculateEGHLServiceChargeResponse;
import com.goodkredit.myapplication.responses.prepaidrequest.CheckEGHLPaymentTransactionStatusResponse;
import com.goodkredit.myapplication.responses.prepaidrequest.RequestVoucherGenerationResponse;
import com.goodkredit.myapplication.responses.prepaidrequest.RequestVoucherViaEghlPaymentResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VirtualVoucherProductConfirmationActivity extends BaseActivity implements View.OnClickListener {

    private DatabaseHandler mdb;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String vouchers = "";
    private int mOrderQuantity;
    private double mOrderTotal;
    private double mEGHLOrderTotal;
    private String borrowername;
    private String borrowermobileno;
    private String borroweremail;
    private String serviceType;
    private boolean isServiceCharge = false;
    private boolean isCancelPayment = false;
    private boolean isEGHL = false;
    private boolean isStatus = false;
    private EGHLPayment eghlPayment;
    private int totaldelaytime = 10000;
    private int currentdelaytime = 0;

    private TextView txvTotalVouchers;
    private TextView txvOrderDetailsTotalAmount;
    private TextView txvTotalAmountToPay;
    private RecyclerView recyclerView;
    private RadioGroup radioGroupPaymentOptions;
    private RadioButton radioPaymentButton;
    private Button btnContinue;

    private VirtualVoucherProductDialogRecyclerAdapter mDialogAdapter;
    private MaterialDialog mConfirmRequestDialog;
    private ArrayList<PrepaidRequest> arrList;

    private EGHL eghl;
    private PaymentParams.Builder params;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //loader dialog
    private MaterialDialog mLoaderDialog;
    private TextView mLoaderDialogMessage;
    private TextView mLoaderDialogTitle;
    private ImageView mLoaderDialogImage;
    private TextView mLoaderDialogClose;
    private TextView mLoaderDialogRetry;
    private RelativeLayout buttonLayout;
    private ImageView cancelbtn;
    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;

    private MaterialDialog mDialog;
    private TextView txvAmount;
    private TextView txvTotalAmount;
    private TextView txvServiceCharge;
    private String paymentoption;

    //PROCESSING FEE BREAKDOWN
    private TextView txv_card_fee;
    private TextView txv_gateway_fee;

    //NEW VARIABLES FOR SECURITY
    private String authenticationid;
    private String keyEncryption;
    private String param;
    private String index;

    //EGHL
    private String eghlAuthenticationid;
    private String eghlKeyEncryption;
    private String eghlParam;
    private String eghlIndex;

    private String requestAuthenticationid;
    private String requestKeyEncryption;
    private String requestParam;
    private String requestIndex;

    private String statusAuthenticationid;
    private String statusKeyEncryption;
    private String statusParam;
    private String statusIndex;


    //DISCOUNT
    private LinearLayout linear_discountcontainer;
    private TextView txvDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_voucher_product_confirmation);
        try {
            init();
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setupToolbar();
        setTitle("Confirm Payment");
        txvTotalVouchers = findViewById(R.id.txvTotalVouchers);
        txvOrderDetailsTotalAmount = findViewById(R.id.txvTotalAmount);
        txvTotalAmountToPay = findViewById(R.id.txvTotalAmountToPay);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.setNestedScrollingEnabled(false);
        mDialogAdapter = new VirtualVoucherProductDialogRecyclerAdapter(getViewContext());
        recyclerView.setAdapter(mDialogAdapter);
        radioGroupPaymentOptions = findViewById(R.id.radioGroupPaymentOptions);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);
        mLlLoader = findViewById(R.id.loaderLayout);
        mTvFetching = findViewById(R.id.fetching);
        mTvWait = findViewById(R.id.wait);
        linear_discountcontainer = findViewById(R.id.linear_discountcontainer);
        txvDiscount = findViewById(R.id.txvDiscount);

        //SETUP LOADER DIALOG
        setupLoaderDialog();
        setUpConfirmDialog();
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
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        V2Subscriber mSubscriber = mdb.getSubscriber(mdb);
        borroweremail = mSubscriber.getEmailAddress();
        borrowername = mSubscriber.getBorrowerName();
        borrowermobileno = mSubscriber.getMobileNumber();
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        arrList = new ArrayList<>();

        serviceType = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_BUYVOUCHER_SERVICETYPE);

        if (PreferenceUtils.getStringPreference(getViewContext(), "EGHL_ORDER_QUANTITY").length() > 0) {
            mOrderQuantity = Integer.parseInt(PreferenceUtils.getStringPreference(getViewContext(), "EGHL_ORDER_QUANTITY"));
            mOrderTotal = Double.parseDouble(PreferenceUtils.getStringPreference(getViewContext(), "EGHL_ORDER_TOTAL"));
            arrList.clear();
            arrList.addAll(PreferenceUtils.getPrepaidRequestArrayList(getViewContext(), "EGHL_PREPAID_REQUEST_ARRAYLIST"));
        } else {
            mOrderQuantity = Integer.parseInt(getIntent().getStringExtra("TOTAL_QUANTITY"));
            mOrderTotal = Double.parseDouble(getIntent().getStringExtra("TOTAL_AMOUNT"));
            arrList.clear();
            arrList.addAll(getIntent().getParcelableArrayListExtra("VOUCHERS"));
        }

        txvTotalVouchers.setText(String.valueOf(CommonFunctions.commaFormatter(String.valueOf(mOrderQuantity))));
        txvOrderDetailsTotalAmount.setText(String.valueOf(CommonFunctions.currencyFormatter(String.valueOf(mOrderTotal))));
        txvTotalAmountToPay.setText(String.valueOf(CommonFunctions.currencyFormatter(String.valueOf(mOrderTotal))));
        mDialogAdapter.clear();
        mDialogAdapter.setVirtualVoucher(arrList);

        checkIfVoucherHasFees();
    }

    private void checkIfVoucherHasFees() {
        String fees = "";
        double basefee = 0.00;
        double variablefee = 0.00;
        int voucherdenom = 0;
        int quantity = 0;
        double discount = 0.00;

        for(PrepaidRequest prepaidRequest : arrList) {
            String productid = prepaidRequest.getProductID();
            if(productid.equals("GKACTIVATION")) {
                fees = prepaidRequest.getFees();
                voucherdenom = prepaidRequest.getVoucherDenom();
                quantity = prepaidRequest.getOrderQuantity();
                break;
            }
        }


        if(fees != null) {
            if(!fees.equals("") || !fees.equals(".")) {
                basefee = Double.parseDouble(CommonFunctions.parseXML(fees, "BaseFee"));
                variablefee = Double.parseDouble(CommonFunctions.parseXML(fees, "VariableFee"));
                //Assumed percentage value, convert to decimal value
                variablefee = variablefee / 100;

                discount = Double.parseDouble(String.valueOf(voucherdenom)) * variablefee + basefee;

                discount = discount * quantity;

                if(discount > 0) {
                    linear_discountcontainer.setVisibility(View.VISIBLE);
                    txvDiscount.setText(String.valueOf(CommonFunctions.currencyFormatter(String.valueOf(discount))));
                    mOrderTotal = mOrderTotal - discount;
                    txvTotalAmountToPay.setText(String.valueOf(CommonFunctions.currencyFormatter(String.valueOf(mOrderTotal))));
                }
            }
        }
    }

    private void setUpConfirmRequestDialog(String qty, String total, List<PrepaidRequest> mData) {
        mConfirmRequestDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(true)
                .customView(R.layout.dialog_confirm_virtual_voucher_request, true)
                .build();

        View view = mConfirmRequestDialog.getCustomView();
        TextView txvTitle = view.findViewById(R.id.txvTitle);
        txvTitle.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "Confirm Voucher Order"));

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_dialog_voucher);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.setNestedScrollingEnabled(false);
        VirtualVoucherProductDialogRecyclerAdapter mDialogAdapter = new VirtualVoucherProductDialogRecyclerAdapter(getViewContext());
        recyclerView.setAdapter(mDialogAdapter);
        mDialogAdapter.clear();
        mDialogAdapter.setVirtualVoucher(mData);

        TextView txvTotalVouchers = view.findViewById(R.id.txvTotalVouchersDialog);
        txvTotalVouchers.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "TOTAL VOUCHERS"));
        TextView txvTotalVouchersValue = view.findViewById(R.id.txvTotalVouchersValueDialog);
        txvTotalVouchersValue.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", qty));

        TextView txvTotaAmount = view.findViewById(R.id.txvTotaAmountDialog);
        txvTotaAmount.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "TOTAL AMOUNT"));
        TextView txvTotalAmountValue = view.findViewById(R.id.txvTotalAmountValueDialog);
        txvTotalAmountValue.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", CommonFunctions.currencyFormatter(total)));

        TextView txvClose = view.findViewById(R.id.txvClose);
        txvClose.setOnClickListener(this);
        TextView txvConfirm = view.findViewById(R.id.txvConfirm);
        txvConfirm.setOnClickListener(this);

        view.findViewById(R.id.V_REQUEST_NOTE).setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txvClose: {
                mConfirmRequestDialog.dismiss();
                break;
            }
            case R.id.txvCloseDialog: {
                mDialog.dismiss();
                break;
            }
            case R.id.txvConfirm: {
                isEGHL = false;
                isStatus = false;
                isCancelPayment = false;
                generateSession();
                break;
            }
            case R.id.txvProceed: {
                mDialog.dismiss();
                currentdelaytime = 0;
                isEGHL = true;
                isServiceCharge = false;
                isStatus = false;
                isCancelPayment = false;
                generateSession();
                break;
            }
            case R.id.btnContinue: {
                mEGHLOrderTotal = 0;
                int selectedId = radioGroupPaymentOptions.getCheckedRadioButtonId();
                radioPaymentButton = findViewById(selectedId);
                setUpRequestedVouchers();
                paymentoption = radioPaymentButton.getText().toString().trim();
                if (paymentoption.equals("Payment Channel")) {
                    setUpConfirmRequestDialog(String.valueOf(mOrderQuantity), String.valueOf(mOrderTotal), arrList);
                    mConfirmRequestDialog.show();
                } else {
                    currentdelaytime = 0;
                    isEGHL = false;
                    isServiceCharge = true;
                    isStatus = false;
                    isCancelPayment = false;
                    generateSession();
                }
                break;
            }
            case R.id.mLoaderDialogClose:
            case R.id.cancelbtn:
            case R.id.mLoaderDialogRetry: {
                mLoaderDialog.dismiss();
                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                Intent intent = new Intent(getViewContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                startActivity(intent);
                break;
            }
        }
    }

    private void setUpRequestedVouchers() {
        JSONArray mVouchers = new JSONArray();
        if (!arrList.isEmpty()) {
            for (PrepaidRequest prepaidRequest : arrList) {
                if (prepaidRequest.getOrderQuantity() > 0) {
                    org.json.JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("productid",prepaidRequest.getProductID());
                        jsonObject.put("voucherdenom", String.valueOf(prepaidRequest.getVoucherDenom()));
                        jsonObject.put("quantity", String.valueOf(prepaidRequest.getOrderQuantity()));
                        mVouchers.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Logger.debug("okhttp","DATAAAAAAAAAAAAAAAAA : "+mVouchers.toString());
                }
            }
        }
        vouchers = mVouchers.toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EGHL.REQUEST_PAYMENT) {
            Logger.debug("antonhttp", "EGHL ResultCode: " + resultCode);

            switch (resultCode) {
                case EGHL.TRANSACTION_SUCCESS:
                    String status = data.getStringExtra(EGHL.TXN_STATUS);
                    String message = data.getStringExtra(EGHL.TXN_MESSAGE);

                    //check transaction status here
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentdelaytime = currentdelaytime + 1000;
                            isStatus = true;
                            isEGHL = false;
                            isCancelPayment = false;
                            generateSession();
                        }
                    }, 1000);

                    break;
                case EGHL.TRANSACTION_CANCELLED:
                    Logger.debug("antonhttp", "onActivityResult: payment cancelled");
                    showGlobalDialogs("Payment Transaction Cancelled",
                            "Close",
                            ButtonTypeEnum.SINGLE,
                            true,
                            false,
                            DialogTypeEnum.FAILED,
                            false);

                    isServiceCharge = false;
                    isStatus = false;
                    isEGHL = false;
                    isCancelPayment = true;
                    generateSession();
                    break;
                case EGHL.TRANSACTION_FAILED:
                    Logger.debug("antonhttp", "onActivityResult: payment failure");
                    showGlobalDialogs("Payment Transaction Failed",
                            "Close",
                            ButtonTypeEnum.SINGLE,
                            true,
                            false,
                            DialogTypeEnum.FAILED,
                            false);

                    isServiceCharge = false;
                    isStatus = false;
                    isEGHL = false;
                    isCancelPayment = true;
                    generateSession();
                    break;
                default:
                    Logger.debug("antonhttp", "onActivityResult: " + resultCode);
                    showGlobalDialogs("Payment Transaction Failed",
                            "Close",
                            ButtonTypeEnum.SINGLE,
                            true,
                            false,
                            DialogTypeEnum.FAILED,
                            false);

                    isServiceCharge = false;
                    isStatus = false;
                    isEGHL = false;
                    isCancelPayment = true;
                    generateSession();
                    break;
            }

        }
    }

    private void generateSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Sending request.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            Logger.debug("okhttp","VOUCHERS TO SEND : "+ new Gson().toJson(arrList));

            if (isStatus) {
                mLoaderDialog.show();
            } else {
                showProgressDialog(getViewContext(), "Sending request.", "Please wait...");
            }
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            if (isEGHL) {
                //requestVoucherViaEghlPayment(requestVoucherViaEghlPaymentSession);
                requestVoucherViaEghlPaymentV2();
            } else if (isStatus) {
                //checkEGHLPaymentTransactionStatus(checkEGHLPaymentTransactionStatusSession);
                checkEGHLPaymentTransactionStatusV2();
            } else if (isServiceCharge) {
                //calculateEGHLServiceCharge(calculateEGHLServiceChargeSession);
                calculateEGHLServiceChargeV2();
            } else if (isCancelPayment) {
                //cancelRequestVoucherViaEghlPayment(cancelRequestVoucherViaEghlPaymentSession);
                cancelRequestVoucherViaEghlPaymentV2();
            } else {
                //requestVoucherGeneration(requestVoucherGenerationSession);
                requestVoucherGenerationV2();
            }

        } else {
            isCancelPayment = false;
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }

    private void requestVoucherGeneration(Callback<RequestVoucherGenerationResponse> requestVoucherGenerationCallback) {
        Call<RequestVoucherGenerationResponse> requestVoucherGeneration = RetrofitBuild.requestVoucherGenerationService(getViewContext())
                .requestVoucherGenerationCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        borrowername,
                        borrowermobileno,
                        vouchers,
                        serviceType);

        requestVoucherGeneration.enqueue(requestVoucherGenerationCallback);
    }

    private final Callback<RequestVoucherGenerationResponse> requestVoucherGenerationSession = new Callback<RequestVoucherGenerationResponse>() {

        @Override
        public void onResponse(Call<RequestVoucherGenerationResponse> call, Response<RequestVoucherGenerationResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mConfirmRequestDialog.dismiss();
            hideProgressDialog();
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    String billingid = response.body().getBillingid();
                    Intent intent = getIntent();
                    intent.putExtra("BILLINGID", billingid);
                    intent.putExtra("TOTALORDER", String.valueOf(mOrderTotal));
                    setResult(RESULT_OK, intent);
                    finish();
                    // VoucherPrepaidRequestActivity.displayView(1, billingid, String.valueOf(mOrderTotal));
                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<RequestVoucherGenerationResponse> call, Throwable t) {
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            mConfirmRequestDialog.dismiss();
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void cancelRequestVoucherViaEghlPayment(Callback<RequestVoucherViaEghlPaymentResponse> cancelRequestVoucherCallback) {
        Call<RequestVoucherViaEghlPaymentResponse> eghlpayment = RetrofitBuild.cancelRequestVoucherViaEghlPaymentService(getViewContext())
                .cancelRequestVoucherViaEghlPaymentCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        "ANDROID",
                        eghlPayment.getPaymenttxnno(),
                        eghlPayment.getOrdertxnno());
        eghlpayment.enqueue(cancelRequestVoucherCallback);
    }

    private final Callback<RequestVoucherViaEghlPaymentResponse> cancelRequestVoucherViaEghlPaymentSession = new Callback<RequestVoucherViaEghlPaymentResponse>() {

        @Override
        public void onResponse(Call<RequestVoucherViaEghlPaymentResponse> call, Response<RequestVoucherViaEghlPaymentResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
            isCancelPayment = false;
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
        }

        @Override
        public void onFailure(Call<RequestVoucherViaEghlPaymentResponse> call, Throwable t) {
            isCancelPayment = false;
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            if (mConfirmRequestDialog != null) {
                mConfirmRequestDialog.dismiss();
            }
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void requestVoucherViaEghlPayment(Callback<RequestVoucherViaEghlPaymentResponse> requestVoucherViaEghlPaymentCallback) {
        Call<RequestVoucherViaEghlPaymentResponse> eghlpayment = RetrofitBuild.requestVoucherGenerationService(getViewContext())
                .requestVoucherViaEghlPaymentCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        borrowername,
                        borrowermobileno,
                        vouchers,
                        serviceType,
                        paymentoption);
        eghlpayment.enqueue(requestVoucherViaEghlPaymentCallback);
    }

    private final Callback<RequestVoucherViaEghlPaymentResponse> requestVoucherViaEghlPaymentSession = new Callback<RequestVoucherViaEghlPaymentResponse>() {

        @Override
        public void onResponse(Call<RequestVoucherViaEghlPaymentResponse> call, Response<RequestVoucherViaEghlPaymentResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    eghlPayment = response.body().getEghlPayment();
                    PreferenceUtils.removePreference(getViewContext(), "EGHL_PAYMENT_TXN_NO");
                    PreferenceUtils.removePreference(getViewContext(), "EGHL_ORDER_TXN_NO");

                    PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_PAYMENT_TXN_NO", eghlPayment.getPaymenttxnno());
                    PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_ORDER_TXN_NO", eghlPayment.getOrdertxnno());

                    eghl = EGHL.getInstance();

                    String paymentMethod;

                    switch (paymentoption) {
                        case "BancNet": {
                            paymentMethod = "DD";
                            break;
                        }
                        default: {
                            paymentMethod = "CC";
                            break;
                        }
                    }

                    params = new PaymentParams.Builder()
                            .setTriggerReturnURL(true)
                            .setMerchantCallbackUrl(eghlPayment.getMerchantReturnurl())
                            .setMerchantReturnUrl(eghlPayment.getMerchantReturnurl())
                            .setServiceId(eghlPayment.getServiceid())
                            .setPassword(eghlPayment.getPassword())
                            .setMerchantName(eghlPayment.getMerchantname())
                            .setAmount(String.valueOf(mEGHLOrderTotal))
                            .setPaymentDesc("GoodKredit Voucher Payment")
                            .setCustName(borrowername)
                            .setCustEmail(borroweremail)
                            .setCustPhone(borrowermobileno)
                            .setPaymentId(eghlPayment.getPaymenttxnno())
                            .setOrderNumber(eghlPayment.getOrdertxnno())
                            .setCurrencyCode("PHP")
                            .setLanguageCode("EN")
                            .setPageTimeout("600")
                            .setTransactionType("SALE")
                            .setPaymentMethod(paymentMethod)
                            .setPaymentGateway(eghlPayment.getPaymentgateway());

                    if (paymentoption.equals("BancNet")) {
                        final String[] url = {
                                "secure2pay.ghl.com:8443",
                                "bancnetonline.com"
                        };
                        params.setURlExclusion(url);
                        params.setIssuingBank("BancNet");
                    }

                    Bundle paymentParams = params.build();
                    eghl.executePayment(paymentParams, VirtualVoucherProductConfirmationActivity.this);

                } else {
                    //showError(response.body().getMessage());
                    showGlobalDialogs(response.body().getMessage(), "Close", ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.WARNING, false);
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<RequestVoucherViaEghlPaymentResponse> call, Throwable t) {
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            if (mConfirmRequestDialog != null) {
                mConfirmRequestDialog.dismiss();
            }
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void checkEGHLPaymentTransactionStatus(Callback<CheckEGHLPaymentTransactionStatusResponse> checkStatusCallback) {
        Call<CheckEGHLPaymentTransactionStatusResponse> checkstatus = RetrofitBuild.checkEGHLPaymentTransactionStatusService(getViewContext())
                .checkEGHLPaymentTransactionStatusCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        PreferenceUtils.getStringPreference(getViewContext(), "EGHL_ORDER_TXN_NO"),
                        PreferenceUtils.getStringPreference(getViewContext(), "EGHL_PAYMENT_TXN_NO"));
        checkstatus.enqueue(checkStatusCallback);
    }

    private final Callback<CheckEGHLPaymentTransactionStatusResponse> checkEGHLPaymentTransactionStatusSession = new Callback<CheckEGHLPaymentTransactionStatusResponse>() {

        @Override
        public void onResponse(Call<CheckEGHLPaymentTransactionStatusResponse> call, Response<CheckEGHLPaymentTransactionStatusResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    mLoaderDialog.dismiss();
                    if (response.body().getTxnStatus().equals("COMPLETED")) {
                        showGlobalDialogs(response.body().getRemarks(), "Close", ButtonTypeEnum.SINGLE, false, true, DialogTypeEnum.SUCCESS, true);
                    } else {
                        showGlobalDialogs(response.body().getRemarks(), "Close", ButtonTypeEnum.SINGLE, false, true, DialogTypeEnum.FAILED, true);
                    }

                } else if (response.body().getStatus().equals("005")) {

                    if (currentdelaytime < totaldelaytime) {
                        //check transaction status here
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentdelaytime = currentdelaytime + 1000;
                                isStatus = true;
                                isEGHL = false;
                                generateSession();
                            }
                        }, 1000);
                    } else {

                        mLoaderDialog.dismiss();
                        showGlobalDialogs(response.body().getRemarks(), "Close", ButtonTypeEnum.SINGLE, false, true, DialogTypeEnum.ONPROCESS, true);

                    }

                } else {
                    mLoaderDialog.dismiss();
                    showError(response.body().getMessage());
                }
            } else {
                mLoaderDialog.dismiss();
                showError();
            }

        }

        @Override
        public void onFailure(Call<CheckEGHLPaymentTransactionStatusResponse> call, Throwable t) {
            mLoaderDialog.dismiss();
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void setupLoaderDialog() {
        mLoaderDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_custom_animated, false)
                .build();

        View view = mLoaderDialog.getCustomView();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mLoaderDialog.getView());

        if (view != null) {
            cancelbtn = view.findViewById(R.id.cancelbtn);
            cancelbtn.setOnClickListener(this);
            mLoaderDialogMessage = view.findViewById(R.id.mLoaderDialogMessage);
            mLoaderDialogTitle = view.findViewById(R.id.mLoaderDialogTitle);
            mLoaderDialogImage = view.findViewById(R.id.mLoaderDialogImage);
            mLoaderDialogClose = view.findViewById(R.id.mLoaderDialogClose);
            mLoaderDialogClose.setOnClickListener(this);
            mLoaderDialogRetry = view.findViewById(R.id.mLoaderDialogRetry);
            mLoaderDialogRetry.setVisibility(View.GONE);
            mLoaderDialogRetry.setOnClickListener(this);
            buttonLayout = view.findViewById(R.id.buttonLayout);
            buttonLayout.setVisibility(View.GONE);
            linearGkNegosyoLayout = view.findViewById(R.id.linearGkNegosyoLayout);
            txvGkNegosyoDescription = view.findViewById(R.id.txvGkNegosyoDescription);
            txvGkNegosyoDescription.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/RobotoCondensed-Regular.ttf", getResources().getString(R.string.str_gk_negosyo_prompt)));
            txvGkNegosyoRedirection = view.findViewById(R.id.txvGkNegosyoRedirection);
            txvGkNegosyoRedirection.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/RobotoCondensed-Regular.ttf", "I WANT THIS!"));
            txvGkNegosyoRedirection.setOnClickListener(this);

            mLoaderDialogTitle.setText("Processing...");

            Glide.with(getViewContext())
                    .load(R.drawable.progressloader)
                    .into(mLoaderDialogImage);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpConfirmDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_success_confirm_buy_voucher, false)
                .build();
        View view = mDialog.getCustomView();
        TextView txvCloseDialog = view.findViewById(R.id.txvCloseDialog);
        txvCloseDialog.setOnClickListener(this);
        TextView txvProceed = view.findViewById(R.id.txvProceed);
        txvProceed.setOnClickListener(this);
        TextView txvServiceChargeLabel = view.findViewById(R.id.txvServiceChargeLabel);
        txvServiceChargeLabel.setText("PROCESSING FEE");

        //PROCESSING FEE BREAKDOWN
        txv_card_fee = view.findViewById(R.id.txv_buy_voucher_card_fee);
        txv_gateway_fee = view.findViewById(R.id.txv_buy_voucher_gateway_fee);


        txvAmount = view.findViewById(R.id.txvAmount);
        txvTotalAmount = view.findViewById(R.id.txvTotalAmount);
        txvServiceCharge = view.findViewById(R.id.txvServiceCharge);
    }

    private void calculateEGHLServiceCharge(Callback<CalculateEGHLServiceChargeResponse> calculateEGHLServiceChargeCallback) {
        Call<CalculateEGHLServiceChargeResponse> calculatesc = RetrofitBuild.calculateEGHLServiceChargeService(getViewContext())
                .calculateEGHLServiceChargeCall(sessionid,
                        imei,
                        borrowerid,
                        userid,
                        authcode,
                        "ANDROID",
                        paymentoption,
                        String.valueOf(mOrderTotal));
        calculatesc.enqueue(calculateEGHLServiceChargeCallback);
    }
    private final Callback<CalculateEGHLServiceChargeResponse> calculateEGHLServiceChargeSession = new Callback<CalculateEGHLServiceChargeResponse>() {

        @Override
        public void onResponse(Call<CalculateEGHLServiceChargeResponse> call, Response<CalculateEGHLServiceChargeResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    try {
                        mDialog.show();
                        txvAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(mOrderTotal)));
                        txvServiceCharge.setText(CommonFunctions.currencyFormatter(String.valueOf(response.body().getServiceCharge())));
                        mEGHLOrderTotal = mOrderTotal + Double.valueOf(response.body().getServiceCharge());
                        txvTotalAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(mEGHLOrderTotal)));

                        txv_card_fee.setText(CommonFunctions.currencyFormatter(response.body().getCardFee()));
                        txv_gateway_fee.setText(CommonFunctions.currencyFormatter(response.body().getPaymentGatewayFee()));

                        Logger.debug("vanhttp", "cardfee: " + response.body().getCardFee());
                        Logger.debug("vanhttp", "gateway: " + response.body().getPaymentGatewayFee());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<CalculateEGHLServiceChargeResponse> call, Throwable t) {
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     **/
    private void requestVoucherGenerationV2() {
        if (CommonFunctions.getConnectivityStatus(getApplicationContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("borrowername", borrowername);
            parameters.put("borrowermobileno", borrowermobileno);
            parameters.put("vouchers", vouchers);
            parameters.put("servicetype", serviceType);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "requestVoucherGenerationV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            requestVoucherGenerationV2Object(requestVoucherGenerationV2Callback);

        } else {
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            mConfirmRequestDialog.dismiss();
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }

    private void requestVoucherGenerationV2Object(Callback<GenericResponse> requestVoucherGenerationCallback) {
        Call<GenericResponse> requestVoucherGeneration = RetrofitBuilder.getVoucherV2API(getViewContext())
                .requestVoucherGenerationV2(authenticationid, sessionid, param);
        requestVoucherGeneration.enqueue(requestVoucherGenerationCallback);
    }

    private final Callback<GenericResponse> requestVoucherGenerationV2Callback = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mConfirmRequestDialog.dismiss();
            hideProgressDialog();
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String message = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String billingid = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    Intent intent = getIntent();
                    intent.putExtra("BILLINGID", billingid);
                    intent.putExtra("TOTALORDER", String.valueOf(mOrderTotal));
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (response.body().getStatus().equals("003")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else {
                    showError(message);
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            mConfirmRequestDialog.dismiss();
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    //Calculate EGHL Service Charge
    private void calculateEGHLServiceChargeV2() {
        if (CommonFunctions.getConnectivityStatus(getApplicationContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("devicetype", CommonVariables.devicetype);
            parameters.put("paymentoption", paymentoption);
            parameters.put("amount",   String.valueOf(mOrderTotal));


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            eghlIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", eghlIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            eghlAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            eghlKeyEncryption = CommonFunctions.getSha1Hex(eghlAuthenticationid + sessionid + "calculateEGHLServiceChargeV2");
            eghlParam = CommonFunctions.encryptAES256CBC(eghlKeyEncryption, String.valueOf(paramJson));

            calculateEGHLServiceChargeV2Object(calculateEGHLServiceChargeCallback);

        } else {
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            mConfirmRequestDialog.dismiss();
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }
    private void calculateEGHLServiceChargeV2Object(Callback<GenericResponse> calculateEGHLServiceChargeCallback) {
        Call<GenericResponse> calculatesc = RetrofitBuilder.getVoucherV2API(getViewContext())
                .calculateEGHLServiceChargeV2(eghlAuthenticationid,sessionid,eghlParam);
        calculatesc.enqueue(calculateEGHLServiceChargeCallback);
    }
    private final Callback<GenericResponse> calculateEGHLServiceChargeCallback = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(eghlKeyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(eghlKeyEncryption,response.body().getData());
                    try {
                        mDialog.show();

                        String servicecharge = CommonFunctions.parseJSON(decryptedData,"servicecharge");
                        String cardfee = CommonFunctions.parseJSON(decryptedData,"cardfee");
                        String gateway = CommonFunctions.parseJSON(decryptedData,"gateway");


                        txvAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(mOrderTotal)));
                        txvServiceCharge.setText(CommonFunctions.currencyFormatter(servicecharge));
                        mEGHLOrderTotal = mOrderTotal + Double.parseDouble(servicecharge);
                        txvTotalAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(mEGHLOrderTotal)));

                        txv_card_fee.setText(CommonFunctions.currencyFormatter(cardfee));
                        txv_gateway_fee.setText(CommonFunctions.currencyFormatter(gateway));

                        Logger.debug("okhttp", "cardfee: " + cardfee);
                        Logger.debug("okhttp", "gateway: " + gateway);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else {
                    showError(decryptedMessage);
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    //Request Voucher via EGHL Payment
    private void requestVoucherViaEghlPaymentV2() {
        if (CommonFunctions.getConnectivityStatus(getApplicationContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("servicetype",serviceType);
            parameters.put("borrowername", borrowername);
            parameters.put("borrowermobileno",borrowermobileno);
            parameters.put("vouchers",vouchers);
            parameters.put("paymentoption", paymentoption);
            parameters.put("devicetype", CommonVariables.devicetype);


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            requestIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", requestIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            requestAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            requestKeyEncryption = CommonFunctions.getSha1Hex(requestAuthenticationid + sessionid + "requestVoucherViaEghlPaymentV2");
            requestParam = CommonFunctions.encryptAES256CBC(requestKeyEncryption, String.valueOf(paramJson));

            requestVoucherViaEghlPaymentV2Object(requestVoucherViaEghlPaymentV2Callback);

        } else {
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            if (mConfirmRequestDialog != null) {
                mConfirmRequestDialog.dismiss();
            }
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }
    private void requestVoucherViaEghlPaymentV2Object(Callback<GenericResponse> requestVoucherViaEghlPaymentCallback) {
        Call<GenericResponse> eghlpayment = RetrofitBuilder.getVoucherV2API(getViewContext())
                .requestVoucherViaEghlPaymentV2(requestAuthenticationid,sessionid,requestParam);
        eghlpayment.enqueue(requestVoucherViaEghlPaymentCallback);
    }
    private final Callback<GenericResponse> requestVoucherViaEghlPaymentV2Callback = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(requestKeyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(requestKeyEncryption,response.body().getData());
                    eghlPayment = new Gson().fromJson(decryptedData,EGHLPayment.class);

                    PreferenceUtils.removePreference(getViewContext(), "EGHL_PAYMENT_TXN_NO");
                    PreferenceUtils.removePreference(getViewContext(), "EGHL_ORDER_TXN_NO");

                    PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_PAYMENT_TXN_NO", eghlPayment.getPaymenttxnno());
                    PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_ORDER_TXN_NO", eghlPayment.getOrdertxnno());

                    eghl = EGHL.getInstance();
                    String paymentMethod;
                    switch (paymentoption) {
                        case "BancNet": {
                            paymentMethod = "DD";
                            break;
                        }
                        default: {
                            paymentMethod = "CC";
                            break;
                        }
                    }
                    params = new PaymentParams.Builder()
                            .setTriggerReturnURL(true)
                            .setMerchantCallbackUrl(eghlPayment.getMerchantReturnurl())
                            .setMerchantReturnUrl(eghlPayment.getMerchantReturnurl())
                            .setServiceId(eghlPayment.getServiceid())
                            .setPassword(eghlPayment.getPassword())
                            .setMerchantName(eghlPayment.getMerchantname())
                            .setAmount(String.valueOf(mEGHLOrderTotal))
                            .setPaymentDesc("GoodKredit Voucher Payment")
                            .setCustName(borrowername)
                            .setCustEmail(borroweremail)
                            .setCustPhone(borrowermobileno)
                            .setPaymentId(eghlPayment.getPaymenttxnno())
                            .setOrderNumber(eghlPayment.getOrdertxnno())
                            .setCurrencyCode("PHP")
                            .setLanguageCode("EN")
                            .setPageTimeout("600")
                            .setTransactionType("SALE")
                            .setPaymentMethod(paymentMethod)
                            .setPaymentGateway(eghlPayment.getPaymentgateway());

                    if (paymentoption.equals("BancNet")) {
                        final String[] url = {
                                "secure2pay.ghl.com:8443",
                                "bancnetonline.com"
                        };
                        params.setURlExclusion(url);
                        params.setIssuingBank("BancNet");
                    }
                    Bundle paymentParams = params.build();
                    eghl.executePayment(paymentParams, VirtualVoucherProductConfirmationActivity.this);

                }else if (response.body().getStatus().equals("003")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else {
                    //showError(response.body().getMessage());
                    showGlobalDialogs(decryptedMessage, "Close", ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.WARNING, false);
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            if (mConfirmRequestDialog != null) {
                mConfirmRequestDialog.dismiss();
            }
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    //Check EGHL Payment Transaction Status
    private void checkEGHLPaymentTransactionStatusV2() {
        if (CommonFunctions.getConnectivityStatus(getApplicationContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("vouchergenerationid", PreferenceUtils.getStringPreference(getViewContext(), "EGHL_ORDER_TXN_NO"));
            parameters.put("paymenttxnno", PreferenceUtils.getStringPreference(getViewContext(), "EGHL_PAYMENT_TXN_NO"));
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            statusIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", statusIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            statusAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            statusKeyEncryption = CommonFunctions.getSha1Hex(statusAuthenticationid + sessionid + "checkEGHLPaymentTransactionStatusV2");
            statusParam = CommonFunctions.encryptAES256CBC(statusKeyEncryption, String.valueOf(paramJson));

            checkEGHLPaymentTransactionStatusV2Object(checkEGHLPaymentTransactionStatusCallback);

        } else {
            mLoaderDialog.dismiss();
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }
    private void checkEGHLPaymentTransactionStatusV2Object(Callback<GenericResponse> status) {
        Call<GenericResponse> eghlpayment = RetrofitBuilder.getVoucherV2API(getViewContext())
                .checkEGHLPaymentTransactionStatusV2(statusAuthenticationid,sessionid,statusParam);
        eghlpayment.enqueue(status);
    }
    private final Callback<GenericResponse> checkEGHLPaymentTransactionStatusCallback = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage  = CommonFunctions.decryptAES256CBC(statusKeyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decrytedData = CommonFunctions.decryptAES256CBC(statusKeyEncryption,response.body().getData());

                    String txnstatus = CommonFunctions.parseJSON(decrytedData,"status");
                    String remarks = CommonFunctions.parseJSON(decrytedData,"remarks");

                    mLoaderDialog.dismiss();
                    if (txnstatus.equals("COMPLETED")) {
                        showGlobalDialogs(remarks, "Close", ButtonTypeEnum.SINGLE, false, true, DialogTypeEnum.SUCCESS, true);
                    } else {
                        showGlobalDialogs(remarks, "Close", ButtonTypeEnum.SINGLE, false, true, DialogTypeEnum.FAILED, true);
                    }
                } else if (response.body().getStatus().equals("012")) {
                    String decrytedData = CommonFunctions.decryptAES256CBC(statusKeyEncryption,response.body().getData());
                    if (currentdelaytime < totaldelaytime) {
                        //check transaction status here
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentdelaytime = currentdelaytime + 1000;
                                isStatus = true;
                                isEGHL = false;
                                generateSession();
                            }
                        }, 1000);
                    } else {
                        mLoaderDialog.dismiss();
                        showGlobalDialogs(decrytedData, "Close", ButtonTypeEnum.SINGLE, false, true, DialogTypeEnum.ONPROCESS, true);
                    }

                }else if (response.body().getStatus().equals("003")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }
                else {
                    mLoaderDialog.dismiss();
                    showError(decryptedMessage);
                }
            } else {
                mLoaderDialog.dismiss();
                showError();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLoaderDialog.dismiss();
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };


    private void cancelRequestVoucherViaEghlPaymentV2() {

        index = "";
        authenticationid = "";
        keyEncryption = "";
        param = "";

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("paymenttxnno", eghlPayment.getPaymenttxnno());
        parameters.put("ordertxnno", eghlPayment.getOrdertxnno());
        parameters.put("devicetype", CommonVariables.devicetype);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "cancelRequestVoucherViaEghlPaymentV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        cancelRequestVoucherViaEghlPaymentV2Object(cancelRequestVoucherViaEghlPaymentV2Session);
    }
    private void cancelRequestVoucherViaEghlPaymentV2Object(Callback<GenericResponse> cancelCallback) {
        Call<GenericResponse> canceleghlpayment = RetrofitBuilder.getVoucherV2API(getViewContext())
                .cancelRequestVoucherViaEghlPaymentV2(authenticationid,sessionid,param);
        canceleghlpayment.enqueue(cancelCallback);
    }

    private final Callback<GenericResponse> cancelRequestVoucherViaEghlPaymentV2Session = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();

            if(response.errorBody() == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    isCancelPayment = false;
                    isEGHL = false;
                    isServiceCharge = false;
                    isStatus = false;
                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else{
                    showError(message);
                }

            }else{
                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isCancelPayment = false;
            isEGHL = false;
            isServiceCharge = false;
            isStatus = false;
            hideProgressDialog();
            if (mConfirmRequestDialog != null) {
                mConfirmRequestDialog.dismiss();
            }
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };



}
