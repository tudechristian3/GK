package com.goodkredit.myapplication.activities.paramount;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoRedirectionActivity;
import com.goodkredit.myapplication.adapter.paramount.ParamountPaymentsVoucherRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.ParamountQueue;
import com.goodkredit.myapplication.bean.ParamountVouchers;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.paramount.AddParamountQueueResponse;
import com.goodkredit.myapplication.responses.CheckTransactionStatusResponse;
import com.goodkredit.myapplication.responses.paramount.GetParamountPaymentVouchersResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParamountInsuranceConfirmationActivity extends BaseActivity implements View.OnClickListener {

    private TextView txvCorporateName;
    private TextView txvName;
    private TextView txvAddress;
    private TextView txvZip;
    private TextView txvMobileNo;
    private TextView txvEmailAddress;
    private TextView txvTelephoneNumber;
    private TextView txvYearModel;
    private TextView txvColor;
    private TextView txvVehicleMaker;
    private TextView txvSeries;
    private TextView txvMVFileNumber;
    private TextView txvPlateNumber;
    private TextView txvSerialChassis;
    private TextView txvMotorNumber;
    private TextView txvMVType;
    private TextView txvRegistrationType;

    private DatabaseHandler mdb;
//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String guarantorid;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private String vouchersession;

    private boolean isStatusChecked = false;
    private String transactionno = "";
    private int totaldelaytime = 10000;
    private int currentdelaytime = 0;

//    private MaterialDialog mLoaderDialog;
//    private TextView mLoaderDialogMessage;
//    private TextView mLoaderDialogTitle;
//    private ImageView mLoaderDialogImage;
//    private TextView mLoaderDialogClose;
//    private TextView mLoaderDialogRetry;
//    private RelativeLayout buttonLayout;
//    private ImageView cancelbtn;

    private ParamountQueue paramountQueue;
    private String origin;

    private LinearLayout layoutRegistrationID;
    private LinearLayout layoutAuthNo;
    private LinearLayout layoutCOCNo;
    private TextView txvRegistrationID;
    private TextView txvAuthNo;
    private TextView txvCocNo;

    private TextView txvAmount;
    private TextView txvOtherCharge;
    private TextView txvAmountPaid;
    private TextView txvApprovalCode;

    private boolean isPaymentsCall = false;

    private RecyclerView recyclerViewVouchers;
    private ProgressBar pbrecyclerProgress;
    private ParamountPaymentsVoucherRecyclerAdapter mVouchersAdapter;

    private double resellerDiscount = 0;
    private LinearLayout linearResellerDiscount;
    private TextView txvResellerDiscount;

    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;

    private LinearLayout linearChangeLayout;
    private TextView txvChange;

    private LinearLayout linearAmountTenderedLayout;
    private TextView txvAmountTendered;

    private LinearLayout linearOtherChargesLayout;

    private double amontToPay = 0;
    private double amountTendered = 0;
    private double change = 0;

    private boolean checkIfReseller = false;

    private String distributorid;
    private String resellerid;

    //UNIFIED SESSIONID
    private String sessionid = "";

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button proceed = (Button) toolbar.findViewById(R.id.confirm);
        ImageView confirmArrow = (ImageView) toolbar.findViewById(R.id.confirmArrow);

        if (origin.equals("HISTORY")) {
            confirmArrow.setVisibility(View.GONE);
            proceed.setVisibility(View.GONE);
        } else {
            confirmArrow.setVisibility(View.VISIBLE);
            proceed.setVisibility(View.VISIBLE);
            proceed.setOnClickListener(this);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramount_insurance_confirmation);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        setupToolbar();

        try {

            init();

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() {
        txvCorporateName = (TextView) findViewById(R.id.txvCorporateName);
        txvName = (TextView) findViewById(R.id.txvName);
        txvAddress = (TextView) findViewById(R.id.txvAddress);
        txvZip = (TextView) findViewById(R.id.txvZip);
        txvMobileNo = (TextView) findViewById(R.id.txvMobileNo);
        txvEmailAddress = (TextView) findViewById(R.id.txvEmailAddress);
        txvTelephoneNumber = (TextView) findViewById(R.id.txvTelephoneNumber);
        txvYearModel = (TextView) findViewById(R.id.txvYearModel);
        txvColor = (TextView) findViewById(R.id.txvColor);
        txvVehicleMaker = (TextView) findViewById(R.id.txvVehicleMaker);
        txvSeries = (TextView) findViewById(R.id.txvSeries);
        txvMVFileNumber = (TextView) findViewById(R.id.txvMVFileNumber);
        txvPlateNumber = (TextView) findViewById(R.id.txvPlateNumber);
        txvSerialChassis = (TextView) findViewById(R.id.txvSerialChassis);
        txvMotorNumber = (TextView) findViewById(R.id.txvMotorNumber);
        txvMVType = (TextView) findViewById(R.id.txvMVType);
        txvRegistrationType = (TextView) findViewById(R.id.txvRegistrationType);

        nointernetconnection = (RelativeLayout) findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        layoutRegistrationID = (LinearLayout) findViewById(R.id.layoutRegistrationID);
        layoutAuthNo = (LinearLayout) findViewById(R.id.layoutAuthNo);
        layoutCOCNo = (LinearLayout) findViewById(R.id.layoutCOCNo);
        txvRegistrationID = (TextView) findViewById(R.id.txvRegistrationID);
        txvAuthNo = (TextView) findViewById(R.id.txvAuthNo);
        txvCocNo = (TextView) findViewById(R.id.txvCocNo);

        txvAmount = (TextView) findViewById(R.id.txvAmount);
        txvOtherCharge = (TextView) findViewById(R.id.txvOtherCharge);
        txvAmountPaid = (TextView) findViewById(R.id.txvAmountPaid);
        txvApprovalCode = (TextView) findViewById(R.id.txvApprovalCode);

        linearChangeLayout = (LinearLayout) findViewById(R.id.linearChangeLayout);
        txvChange = (TextView) findViewById(R.id.txvChange);
        linearAmountTenderedLayout = (LinearLayout) findViewById(R.id.linearAmountTenderedLayout);
        txvAmountTendered = (TextView) findViewById(R.id.txvAmountTendered);
        linearOtherChargesLayout = (LinearLayout) findViewById(R.id.linearOtherChargesLayout);

        //SETUP LOADER DIALOG
//        setupLoaderDialog();
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        guarantorid = mdb.getGuarantorID(mdb);

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        origin = getIntent().getStringExtra("origin");
        if (origin.equals("HISTORY")) {
            findViewById(R.id.approvalCodeLayout).setVisibility(View.VISIBLE);
            layoutRegistrationID.setVisibility(View.VISIBLE);
            layoutAuthNo.setVisibility(View.VISIBLE);
            layoutCOCNo.setVisibility(View.VISIBLE);
            findViewById(R.id.noteLayout).setVisibility(View.GONE);
            paramountQueue = getIntent().getParcelableExtra("queue");
            isPaymentsCall = true;

            recyclerViewVouchers = (RecyclerView) findViewById(R.id.recyclerViewVouchers);
            recyclerViewVouchers.setLayoutManager(new LinearLayoutManager(getViewContext()));
            recyclerViewVouchers.setNestedScrollingEnabled(false);
            mVouchersAdapter = new ParamountPaymentsVoucherRecyclerAdapter(getViewContext());
            recyclerViewVouchers.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null));
            recyclerViewVouchers.setAdapter(mVouchersAdapter);
            pbrecyclerProgress = (ProgressBar) findViewById(R.id.pbrecyclerProgress);

            List<ParamountVouchers> paramountVouchers = mdb.getParamountPaymentVouchers(mdb, paramountQueue.getTransactionNo());
            if (paramountVouchers.size() == 0) {
                //call
                getSession();
            } else {
                //update adapter data here
                updatePaymentsData(paramountVouchers);
            }
        } else {
            findViewById(R.id.approvalCodeLayout).setVisibility(View.GONE);
            layoutRegistrationID.setVisibility(View.GONE);
            layoutAuthNo.setVisibility(View.GONE);
            layoutCOCNo.setVisibility(View.GONE);
            findViewById(R.id.noteLayout).setVisibility(View.VISIBLE);
            vouchersession = getIntent().getStringExtra("vouchersessionid");
            paramountQueue = mdb.getParamountQueue(mdb);
            isPaymentsCall = false;
            resellerDiscount = Double.valueOf(getIntent().getStringExtra("PARAMOUNTRESELLERDISCOUNT"));
            if (resellerDiscount > 0) {
                linearResellerDiscount = (LinearLayout) findViewById(R.id.linearResellerDiscount);
                linearResellerDiscount.setVisibility(View.VISIBLE);
                txvResellerDiscount = (TextView) findViewById(R.id.txvResellerDiscount);
                txvResellerDiscount.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerDiscount)));
            }

            linearOtherChargesLayout.setVisibility(View.GONE);
            linearChangeLayout.setVisibility(View.VISIBLE);
            linearAmountTenderedLayout.setVisibility(View.VISIBLE);

            amontToPay = Double.valueOf(getIntent().getStringExtra("AMOUNTTOPAY"));
            amountTendered = Double.valueOf(getIntent().getStringExtra("AMOUNTENDERED"));
            change = Double.valueOf(getIntent().getStringExtra("CHANGE"));

        }

        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

        if(distributorid.equals("") || distributorid.equals(".")
                || resellerid.equals("") || resellerid.equals(".")){
            checkIfReseller = false;
        } else{
            checkIfReseller = true;
        }

        updateData(paramountQueue);

    }

    public static void start(Context context, String sessionid) {
        Intent intent = new Intent(context, ParamountInsuranceConfirmationActivity.class);
        intent.putExtra("vouchersessionid", sessionid);
        intent.putExtra("origin", "QUEUE");
        context.startActivity(intent);
    }

    public static void start(Context context, ParamountQueue paramountQueue, String origin) {
        Intent intent = new Intent(context, ParamountInsuranceConfirmationActivity.class);
        intent.putExtra("queue", paramountQueue);
        intent.putExtra("origin", origin);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm: {

//                if (isAirplaneModeOn(getViewContext())) {
//                    showError("Airplane mode is enabled. Please disable Airplane mode and enable GPS or Internet to proceed.");
//                } else {
//                    if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//                        if (isGPSModeOn()) {

                            MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                                    .content("You are about to pay your request.")
                                    .cancelable(false)
                                    .negativeText("Close")
                                    .positiveText("Proceed")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                            if (mdb.getParamountQueue(mdb) != null) {

                                                currentdelaytime = 0;
                                                isPaymentsCall = false;
                                                isStatusChecked = false;
                                                getSession();

                                            } else {

                                                showError("There's something wrong with your data. Please refresh the page.");

                                            }

                                        }
                                    })
                                    .dismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {

                                        }
                                    })
                                    .show();

                            V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());

//                        } else {
//                            showGPSDisabledAlertToUser();
//                        }
//                    } else {
//                        showError(getString(R.string.generic_internet_error_message));
//                    }
//                }

                break;
            }
            case R.id.refreshnointernet: {

                break;
            }
//            case R.id.mLoaderDialogClose: {
//                mLoaderDialog.dismiss();
//                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
//                Intent intent = new Intent(getViewContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                CommonVariables.VOUCHERISFIRSTLOAD = true;
//                startActivity(intent);
//                break;
//            }
//            case R.id.cancelbtn: {
//                mLoaderDialog.dismiss();
//                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
//                Intent intent = new Intent(getViewContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                CommonVariables.VOUCHERISFIRSTLOAD = true;
//                startActivity(intent);
//                break;
//            }
//            case R.id.mLoaderDialogRetry: {
//                mLoaderDialog.dismiss();
//                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
//                Intent intent = new Intent(getViewContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                CommonVariables.VOUCHERISFIRSTLOAD = true;
//                startActivity(intent);
//                break;
//            }
            case R.id.txvGkNegosyoRedirection: {
                GKNegosyoRedirectionActivity.start(getViewContext(), mdb.getGkServicesData(mdb, "GKNEGOSYO"));
                break;
            }
        }
    }

//    private void setupLoaderDialog() {
//        mLoaderDialog = new MaterialDialog.Builder(getViewContext())
//                .cancelable(false)
//                .customView(R.layout.dialog_custom_animated, false)
//                .build();
//
//        View view = mLoaderDialog.getCustomView();
//
//        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mLoaderDialog.getView());
//
//        if (view != null) {
//            cancelbtn = (ImageView) view.findViewById(R.id.cancelbtn);
//            cancelbtn.setOnClickListener(this);
//            mLoaderDialogMessage = (TextView) view.findViewById(R.id.mLoaderDialogMessage);
//            mLoaderDialogTitle = (TextView) view.findViewById(R.id.mLoaderDialogTitle);
//            mLoaderDialogImage = (ImageView) view.findViewById(R.id.mLoaderDialogImage);
//            mLoaderDialogClose = (TextView) view.findViewById(R.id.mLoaderDialogClose);
//            mLoaderDialogClose.setOnClickListener(this);
//            mLoaderDialogRetry = (TextView) view.findViewById(R.id.mLoaderDialogRetry);
//            mLoaderDialogRetry.setVisibility(View.GONE);
//            mLoaderDialogRetry.setOnClickListener(this);
//            buttonLayout = (RelativeLayout) view.findViewById(R.id.buttonLayout);
//            buttonLayout.setVisibility(View.GONE);
//            linearGkNegosyoLayout = (LinearLayout) view.findViewById(R.id.linearGkNegosyoLayout);
//            txvGkNegosyoDescription = (TextView) view.findViewById(R.id.txvGkNegosyoDescription);
//            txvGkNegosyoDescription.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/robotocondensedregular.ttf", getResources().getString(R.string.str_gk_negosyo_prompt)));
//            txvGkNegosyoRedirection = (TextView) view.findViewById(R.id.txvGkNegosyoRedirection);
//            txvGkNegosyoRedirection.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/robotocondensedregular.ttf", "I WANT THIS!"));
//            txvGkNegosyoRedirection.setOnClickListener(this);
//
//            mLoaderDialogTitle.setText("Processing...");
//
//            Glide.with(getViewContext())
//                    .load(R.drawable.progressloader)
//                    .into(mLoaderDialogImage);
//        }
//    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            if (!isPaymentsCall) {
//                mLoaderDialog.show();

                setUpProgressLoader();
//                mLoaderDialogMessage.setText("");
            } else {
                pbrecyclerProgress.setVisibility(View.VISIBLE);
            }

//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isPaymentsCall) {
                getParamountPaymentVouchers(getParamountPaymentVouchersSession);
            } else {
                if (isStatusChecked) {
//                            mLoaderDialogMessage.setText("Checking status, Please wait...");
                    setUpProgressLoaderMessageDialog("Checking status, Please wait...");
                    checkTransactionStatus(checkTransactionStatusSession);
                } else {
//                            mLoaderDialogMessage.setText("Processing request, Please wait...");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentdelaytime = currentdelaytime + 1000;
                            setUpProgressLoaderMessageDialog("Processing request, Please wait...");
                            addParamountQueue(addParamountQueueSession);
                        }
                    }, 1000);

                }
            }

        } else {
//            if (mLoaderDialog != null) {
//                mLoaderDialog.dismiss();
//            }
            setUpProgressLoaderDismissDialog();
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }

    }

//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
////                    if (mLoaderDialog != null) {
////                        mLoaderDialog.dismiss();
////                    }
//                    setUpProgressLoaderDismissDialog();
//
//                    if (isPaymentsCall) {
//                        pbrecyclerProgress.setVisibility(View.GONE);
//                    }
//
////                    hideProgressDialog();
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
////                    if (mLoaderDialog != null) {
////                        mLoaderDialog.dismiss();
////                    }
//                    setUpProgressLoaderDismissDialog();
//                    if (isPaymentsCall) {
//                        pbrecyclerProgress.setVisibility(View.GONE);
//                    }
//
////                    hideProgressDialog();
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
////                    if (mLoaderDialog != null) {
////                        mLoaderDialog.dismiss();
////                    }
//                    setUpProgressLoaderDismissDialog();
//                    if (isPaymentsCall) {
//                        pbrecyclerProgress.setVisibility(View.GONE);
//                    }
//
////                    hideProgressDialog();
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    if (isPaymentsCall) {
//                        getParamountPaymentVouchers(getParamountPaymentVouchersSession);
//                    } else {
//                        if (isStatusChecked) {
////                            mLoaderDialogMessage.setText("Checking status, Please wait...");
//                            setUpProgressLoaderMessageDialog("Checking status, Please wait...");
//                            checkTransactionStatus(checkTransactionStatusSession);
//                        } else {
////                            mLoaderDialogMessage.setText("Processing request, Please wait...");
//                            final Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    currentdelaytime = currentdelaytime + 1000;
//                                    setUpProgressLoaderMessageDialog("Processing request, Please wait...");
//                                    addParamountQueue(addParamountQueueSession);
//                                }
//                            }, 1000);
//
//                        }
//                    }
//
//                }
//            } else {
////                if (mLoaderDialog != null) {
////                    mLoaderDialog.dismiss();
////                }
//                currentdelaytime = 0;
//                setUpProgressLoaderDismissDialog();
//                if (isPaymentsCall) {
//                    pbrecyclerProgress.setVisibility(View.GONE);
//                }
//
////                hideProgressDialog();
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
////            if (mLoaderDialog != null) {
////                mLoaderDialog.dismiss();
////            }
//
//            setUpProgressLoaderDismissDialog();
//            if (isPaymentsCall) {
//                pbrecyclerProgress.setVisibility(View.GONE);
//            }
//
////            hideProgressDialog();
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void checkTransactionStatus(Callback<CheckTransactionStatusResponse> checkTransactionStatusCallback) {
        Call<CheckTransactionStatusResponse> checktxnstatus = RetrofitBuild.checkTransactionStatusService(getViewContext())
                .checkTransactionStatusCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        transactionno);
        checktxnstatus.enqueue(checkTransactionStatusCallback);
    }

    private final Callback<CheckTransactionStatusResponse> checkTransactionStatusSession = new Callback<CheckTransactionStatusResponse>() {

        @Override
        public void onResponse(Call<CheckTransactionStatusResponse> call, Response<CheckTransactionStatusResponse> response) {
            ResponseBody errorBody = response.errorBody();

            try {
                if (errorBody == null) {

                    if (response.body().getStatus().equals("000")) {
//                        setUpProgressLoaderDismissDialog();
//                        Logger.debug("vanhttp", "txnstatus: " +response.body().getTxnStatus());
//                        if (resellerDiscount > 0) {
//                            linearGkNegosyoLayout.setVisibility(View.GONE);
//                        } else {
//                            if (PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER).equals("") ||
//                                    PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID).equals("") ||
//                                    PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER).equals(".") ||
//                                    PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID).equals(".")) {
//                                linearGkNegosyoLayout.setVisibility(View.VISIBLE);
//                            } else {
//                                linearGkNegosyoLayout.setVisibility(View.GONE);
//                            }
//                        }

//                        buttonLayout.setVisibility(View.VISIBLE);

                        Logger.debug("vanhttp", "txnstatus: " +response.body().getTxnStatus());
                        if (response.body().getTxnStatus().equals("SUCCESS")) {
                            setUpProgressLoaderDismissDialog();
//                            mLoaderDialogRetry.setVisibility(View.GONE);
//                            mLoaderDialog.dismiss();
//                            mLoaderDialogTitle.setText("SUCCESSFUL TRANSACTION");
//                            showGlobalDialogs(response.body().getRemarks(),
//                                    "close", ButtonTypeEnum.SINGLE, checkIfReseller, true, DialogTypeEnum.SUCCESS);
                            showSuccessTransactionDialog(response.body().getRemarks());

                        } else if (response.body().getTxnStatus().equals("TIMEOUT")) {
                            setUpProgressLoaderDismissDialog();
//                            mLoaderDialogRetry.setVisibility(View.GONE);
////                            mLoaderDialogTitle.setText("TIMEOUT");
//                            mLoaderDialog.dismiss();
//                            showGlobalDialogs(response.body().getRemarks(), "close", ButtonTypeEnum.SINGLE,
//                                    checkIfReseller, true, DialogTypeEnum.TIMEOUT);

                            showTimeOutTransactionDialog(response.body().getRemarks());
                        } else {
                            setUpProgressLoaderDismissDialog();
//                            mLoaderDialogRetry.setVisibility(View.VISIBLE);
////                            mLoaderDialogTitle.setText("FAILED TRANSACTION");
//                            mLoaderDialog.dismiss();
//                            showGlobalDialogs(response.body().getRemarks(), "close", ButtonTypeEnum.SINGLE,
//                                    checkIfReseller, true, DialogTypeEnum.FAILED);
                            showFailedTransactioDialog(response.body().getRemarks());
                        }

//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            mLoaderDialogMessage.setText(Html.fromHtml(response.body().getRemarks(), Html.FROM_HTML_MODE_COMPACT));
//                        } else {
//                            mLoaderDialogMessage.setText(Html.fromHtml(response.body().getRemarks()));
//                        }

//                        mLoaderDialogImage.setVisibility(View.GONE);
//                        mLoaderDialogClose.setVisibility(View.VISIBLE);

                    } else if (response.body().getStatus().equals("005")) {

                        if (currentdelaytime < totaldelaytime) {

                            //check transaction status here
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    currentdelaytime = currentdelaytime + 1000;
                                    checkTransactionStatus(checkTransactionStatusSession);
                                }
                            }, 1000);

                        } else {
                            setUpProgressLoaderDismissDialog();
//                            buttonLayout.setVisibility(View.VISIBLE);
//                            mLoaderDialogRetry.setVisibility(View.GONE);
//                            mLoaderDialogImage.setVisibility(View.GONE);
//                            mLoaderDialogTitle.setText("TRANSACTION ON PROCESS");
//                            mLoaderDialog.dismiss();
//                            showGlobalDialogs(response.body().getRemarks(),
//                                    "close", ButtonTypeEnum.SINGLE, checkIfReseller, true, DialogTypeEnum.ONPROCESS);
                            showOnProgressTransactionDialog(response.body().getRemarks());

//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                mLoaderDialogMessage.setText(Html.fromHtml(response.body().getRemarks(), Html.FROM_HTML_MODE_COMPACT));
//                            } else {
//                                mLoaderDialogMessage.setText(Html.fromHtml(response.body().getRemarks()));
//                            }

                        }

                    } else {

//                        mLoaderDialog.dismiss();
                        currentdelaytime = 0;
                        setUpProgressLoaderDismissDialog();
//                        showError(response.body().getMessage());
//                        showGlobalDialogs(response.body().getRemarks(), "retry", ButtonTypeEnum.SINGLE,
//                                checkIfReseller, true, DialogTypeEnum.FAILED);

                        showFailedTransactioDialog(response.body().getRemarks());

                    }
                } else {
//                    mLoaderDialog.dismiss();
                    setUpProgressLoaderDismissDialog();
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<CheckTransactionStatusResponse> call, Throwable t) {
//            mLoaderDialog.dismiss();
            setUpProgressLoaderDismissDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showSuccessTransactionDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("SUCCESS", message, "Close", "",
                ButtonTypeEnum.SINGLE, checkIfReseller, true, DialogTypeEnum.SUCCESS);

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

    private void showTimeOutTransactionDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("TIMEOUT", message, "Close", "",
                ButtonTypeEnum.SINGLE, checkIfReseller, true, DialogTypeEnum.TIMEOUT);

        View closebtn = dialog.btnCloseImage();
        closebtn.setVisibility(View.GONE);

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                hideProgressDialog();
                dialog.proceedtoMainActivity();
            }
        });
    }

    private void showFailedTransactioDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("FAILED", message, "Retry", "",
                ButtonTypeEnum.SINGLE, checkIfReseller, true, DialogTypeEnum.FAILED);

        View closebtn = dialog.btnCloseImage();
        closebtn.setVisibility(View.GONE);

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                hideProgressDialog();
                dialog.proceedtoMainActivity();
            }
        });
    }

    private void showOnProgressTransactionDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("FAILED", message, "Close", "",
                ButtonTypeEnum.SINGLE, checkIfReseller, true, DialogTypeEnum.ONPROCESS);

        View closebtn = dialog.btnCloseImage();
        closebtn.setVisibility(View.GONE);

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                hideProgressDialog();
                dialog.proceedtoMainActivity();
            }
        });
    }



    private void getParamountPaymentVouchers(Callback<GetParamountPaymentVouchersResponse> getParamountPaymentVouchersCallback) {
        Call<GetParamountPaymentVouchersResponse> paymentvouchers = RetrofitBuild.getParamountPaymentVouchersService(getViewContext())
                .getParamountPaymentVouchersCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        CommonFunctions.getYearFromDate(paramountQueue.getDateTimeCompleted()),
                        CommonFunctions.getMonthFromDate(paramountQueue.getDateTimeCompleted()),
                        paramountQueue.getTransactionNo());
        paymentvouchers.enqueue(getParamountPaymentVouchersCallback);
    }

    private final Callback<GetParamountPaymentVouchersResponse> getParamountPaymentVouchersSession = new Callback<GetParamountPaymentVouchersResponse>() {

        @Override
        public void onResponse(Call<GetParamountPaymentVouchersResponse> call, Response<GetParamountPaymentVouchersResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (isPaymentsCall) {
                pbrecyclerProgress.setVisibility(View.GONE);
            }

            try {
                if (errorBody == null) {

                    if (response.body().getStatus().equals("000")) {

                        if (mdb != null) {

                            //mdb.truncateTable(mdb, DatabaseHandler.PARAMOUNT_PAYMENT_VOUCHERS);
                            List<ParamountVouchers> paramountVouchersList = response.body().getParamountVouchers();
                            for (ParamountVouchers paramountVouchers : paramountVouchersList) {
                                mdb.insertParamountVouchers(mdb, paramountVouchers);
                            }

                            updatePaymentsData(mdb.getParamountPaymentVouchers(mdb, paramountQueue.getTransactionNo()));

                        }

                    }

                } else {
                    setUpProgressLoaderDismissDialog();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<GetParamountPaymentVouchersResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    private void addParamountQueue(Callback<AddParamountQueueResponse> addParamountQueueCallback) {

        GPSTracker gpsTracker = new GPSTracker(getViewContext());

        Call<AddParamountQueueResponse> addparamountqueue = RetrofitBuild.addParamountQueueService(getViewContext())
                .addParamountQueueCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        guarantorid,
                        authcode,
                        vouchersession,
                        new Gson().toJson(mdb.getParamountQueue(mdb)),
                        resellerDiscount > 0 ? "1" : "0",
                        PreferenceUtils.getStringPreference(getViewContext(), "paramount_service_code"),
                        String.valueOf(paramountQueue.getAmountPaid()),
                        String.valueOf(gpsTracker.getLongitude()),
                        String.valueOf(gpsTracker.getLatitude()));
        addparamountqueue.enqueue(addParamountQueueCallback);
    }

    private final Callback<AddParamountQueueResponse> addParamountQueueSession = new Callback<AddParamountQueueResponse>() {

        @Override
        public void onResponse(Call<AddParamountQueueResponse> call, Response<AddParamountQueueResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    if (mdb != null) {
                        transactionno = response.body().getTransactionNo();

                        //check transaction status here
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentdelaytime = currentdelaytime + 1000;
                                isStatusChecked = true;
                                getSession();
                            }
                        }, 1000);

                    }

                } else {
//                    mLoaderDialog.dismiss();
                    currentdelaytime = 0;
                    setUpProgressLoaderDismissDialog();
//                    showError(response.body().getMessage());
//                    showGlobalDialogs(response.body().getMessage(), "retry", ButtonTypeEnum.SINGLE,
//                            false, false, DialogTypeEnum.FAILED);
                    showFailedParamountQueueDialog(response.body().getMessage());
                }
            } else {
//                mLoaderDialog.dismiss();
                setUpProgressLoaderDismissDialog();
                showError();
            }

        }

        @Override
        public void onFailure(Call<AddParamountQueueResponse> call, Throwable t) {
//            mLoaderDialog.dismiss();
            setUpProgressLoaderDismissDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showFailedParamountQueueDialog(String message) {
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
                hideProgressDialog();
                dialog.proceedtoMainActivity();
            }
        });
    }


    public void updateData(ParamountQueue paramountQueue) {
        if (paramountQueue != null) {

            switch (paramountQueue.getType()) {
                case "Individual": {

                    findViewById(R.id.corporateLayout).setVisibility(View.GONE);
                    findViewById(R.id.nameLayout).setVisibility(View.VISIBLE);

                    String name = paramountQueue.getMiddleName().isEmpty() ? paramountQueue.getFirstName() + " " + paramountQueue.getLastName() : paramountQueue.getFirstName() + " " + paramountQueue.getMiddleName() + " " + paramountQueue.getLastName();
                    txvName.setText(name);

                    break;
                }
                case "Corporate without assignee": {

                    findViewById(R.id.corporateLayout).setVisibility(View.VISIBLE);
                    findViewById(R.id.nameLayout).setVisibility(View.GONE);

                    txvCorporateName.setText(paramountQueue.getCorporateName());

                    break;
                }
                case "Corporate with assignee": {

                    findViewById(R.id.corporateLayout).setVisibility(View.VISIBLE);
                    findViewById(R.id.nameLayout).setVisibility(View.VISIBLE);

                    txvCorporateName.setText(paramountQueue.getCorporateName());

                    String name = paramountQueue.getMiddleName().isEmpty() ? paramountQueue.getFirstName() + " " + paramountQueue.getLastName() : paramountQueue.getFirstName() + " " + paramountQueue.getMiddleName() + " " + paramountQueue.getLastName();
                    txvName.setText(name);

                    break;
                }
            }

            String address = "";

            if (CommonFunctions.replaceEscapeData(paramountQueue.getHouseNumber()).trim().length() > 0) {
                address = address.concat(CommonFunctions.replaceEscapeData(paramountQueue.getHouseNumber()) + " ");
            }

            if (CommonFunctions.replaceEscapeData(paramountQueue.getStreetName()).trim().length() > 0) {
                address = address.concat(" " + CommonFunctions.replaceEscapeData(paramountQueue.getStreetName()));
            }

            if (CommonFunctions.replaceEscapeData(paramountQueue.getBuildingName()).trim().length() > 0) {
                address = address.concat(", " + CommonFunctions.replaceEscapeData(paramountQueue.getBuildingName()));
            }

            if (CommonFunctions.replaceEscapeData(paramountQueue.getBarangay()).trim().length() > 0) {
                address = address.concat(", " + CommonFunctions.replaceEscapeData(paramountQueue.getBarangay()));
            }

            if (CommonFunctions.replaceEscapeData(paramountQueue.getMunicipality()).trim().length() > 0) {
                address = address.concat(", " + CommonFunctions.replaceEscapeData(paramountQueue.getMunicipality()));
            }

            if (CommonFunctions.replaceEscapeData(paramountQueue.getProvince()).trim().length() > 0) {
                address = address.concat(", " + CommonFunctions.replaceEscapeData(paramountQueue.getProvince()));
            }

            txvAddress.setText(address);
            txvZip.setText(paramountQueue.getZipcode());
            txvMobileNo.setText(paramountQueue.getMobileNumber());
            txvEmailAddress.setText(paramountQueue.getEmailAddress());
            txvTelephoneNumber.setText(paramountQueue.getTelephoneNo());
            txvYearModel.setText(paramountQueue.getYearModel());
            txvColor.setText(paramountQueue.getColor());
            txvVehicleMaker.setText(paramountQueue.getVehicleMaker());
            txvSeries.setText(paramountQueue.getSeries());
            txvMVFileNumber.setText(paramountQueue.getMVFileNumber());
            txvPlateNumber.setText(paramountQueue.getPlateNumber());
            txvSerialChassis.setText(paramountQueue.getSerial());
            txvMotorNumber.setText(paramountQueue.getEngineMotorNumber());
            txvMVType.setText(paramountQueue.getLTOMVType());
            if (origin.equals("HISTORY")) {
                txvRegistrationID.setText(paramountQueue.getRegistrationID());
                txvAuthNo.setText(paramountQueue.getAuthNo());
                txvCocNo.setText(paramountQueue.getCOCNo());

                txvAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(paramountQueue.getPrice())));
                txvAmountPaid.setText(CommonFunctions.currencyFormatter(String.valueOf(paramountQueue.getAmountPaid() - resellerDiscount)));

            } else {

                Logger.debug("antonhttp", "amountTendered: " + String.valueOf(amountTendered));
                Logger.debug("antonhttp", "amontToPay: " + String.valueOf(amontToPay));
                Logger.debug("antonhttp", "resellerDiscount: " + String.valueOf(resellerDiscount));

                txvAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(amontToPay + resellerDiscount)));
                txvAmountPaid.setText(CommonFunctions.currencyFormatter(String.valueOf(paramountQueue.getAmountPaid() - resellerDiscount)));
                txvChange.setText(CommonFunctions.currencyFormatter(String.valueOf(amountTendered - (paramountQueue.getAmountPaid() - resellerDiscount))));
                txvAmountTendered.setText(CommonFunctions.currencyFormatter(String.valueOf(amountTendered)));
            }
//            txvRegistrationType.setText(paramountQueue.getType());
            txvRegistrationType.setText("Renewal");

            txvApprovalCode.setText(paramountQueue.getTransactionNo());
            txvOtherCharge.setText(CommonFunctions.currencyFormatter(String.valueOf(paramountQueue.getOtherCharges())));


        } else {

            showError("There's something wrong with your data. Please refresh the page.");

        }
    }

    private void updatePaymentsData(List<ParamountVouchers> paramountVouchers) {
        if (paramountVouchers.size() > 0) {

            mVouchersAdapter.setVouchersData(paramountVouchers);

        } else {

            mVouchersAdapter.clear();

        }
    }

}
