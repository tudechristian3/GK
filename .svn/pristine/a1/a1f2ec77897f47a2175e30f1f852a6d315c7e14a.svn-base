package com.goodkredit.myapplication.activities.gkservices.medpadala;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 2/19/2018.
 */

public class MedPadalaConfirmationActivity extends BaseActivity implements View.OnClickListener {

    private static final String RCPNT_MBLE = "RCPNT_MBLE";
    private static final String AMNT = "AMNT";
    private static final String SRVCCHRGE = "SRVCCHRGE";
    private static final String AMNTPAY = "AMNTPAY";
    private static final String AMNTTNDRD = "AMNTTNDRD";
    private static final String CHNG = "CHNG";
    private static final String VCHERSESH = "VCHERSESH";

    private static final String PROCESS_TRANSACTION = "PROCESS_TRANSACTION";
    private static final String CHECK_STATUS = "CHECK_STATUS";

    private DatabaseHandler db;

    private TextView mTvRecipientMobile;
    private TextView mTvAmount;
    private TextView mTvServiceCharge;
    private TextView mTvAmountToPay;
    private TextView mTvAmountTendered;
    private TextView mTvChange;

    private String strRecipientMobile;
    private String strAmount;
    private String strServiceCharge;
    private String strAmountToPay;
    private String strAmountTendered;
    private String strChange;
    private String strVoucherSession;

    private Intent mIntent = null;

    private String mID = "";

    private boolean isStatusChecked = false;
    private int totaldelaytime = 10000;
    private int currentdelaytime = 0;

//    private MaterialDialog mLoaderDialog;
//    private TextView mLoaderDialogMessage;
//    private TextView mLoaderDialogTitle;
//    private ImageView mLoaderDialogImage;
//    private TextView mLoaderDialogClose;
//    private TextView mLoaderDialogRetry;
//    private RelativeLayout buttonLayout;

    private String sessionid = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medpadala_confirmation);
        init();
    }

    private void init() {

        setupToolbar();

        db = new DatabaseHandler(getViewContext());

        //get account information
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
            } while (cursor.moveToNext());
        }
        //imei = V2Utils.getIMEI(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mIntent = getIntent();

        strRecipientMobile = mIntent.getStringExtra(RCPNT_MBLE);
        strAmount = mIntent.getStringExtra(AMNT);
        strServiceCharge = mIntent.getStringExtra(SRVCCHRGE);
        strAmountToPay = mIntent.getStringExtra(AMNTPAY);
        strAmountTendered = mIntent.getStringExtra(AMNTTNDRD);
        strChange = mIntent.getStringExtra(CHNG);
        strVoucherSession = mIntent.getStringExtra(VCHERSESH);

        mTvRecipientMobile = (TextView) findViewById(R.id.tv_recipient_mobile);
        mTvAmount = (TextView) findViewById(R.id.tv_amount);
        mTvServiceCharge = (TextView) findViewById(R.id.tv_service_charge);
        mTvAmountToPay = (TextView) findViewById(R.id.tv_amount_to_pay);
        mTvAmountTendered = (TextView) findViewById(R.id.tv_amount_tendered);
        mTvChange = (TextView) findViewById(R.id.tv_amount_change);

        mTvRecipientMobile.setText("+" + strRecipientMobile);
        mTvAmount.setText(CommonFunctions.currencyFormatter(strAmount));
        mTvServiceCharge.setText(CommonFunctions.currencyFormatter(strServiceCharge));
        mTvAmountToPay.setText(CommonFunctions.currencyFormatter(strAmountToPay));
        mTvAmountTendered.setText(CommonFunctions.currencyFormatter(strAmountTendered));
        mTvChange.setText(CommonFunctions.currencyFormatter(strChange));

        findViewById(R.id.confirm).setOnClickListener(this);

//        setupLoaderDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, String recipientsMobile, String amount, String serviceCharge, String amountToPay, String amountTendered, String change, String voucherSession) {
        Intent intent = new Intent(context, MedPadalaConfirmationActivity.class);
        intent.putExtra(RCPNT_MBLE, recipientsMobile);
        intent.putExtra(AMNT, amount);
        intent.putExtra(SRVCCHRGE, serviceCharge);
        intent.putExtra(AMNTPAY, amountToPay);
        intent.putExtra(AMNTTNDRD, amountTendered);
        intent.putExtra(CHNG, change);
        intent.putExtra(VCHERSESH, voucherSession);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm) {
            createSession(PROCESS_TRANSACTION);
        }
//        else if (v.getId() == R.id.mLoaderDialogClose || v.getId() == R.id.mLoaderDialogRetry) {
//            mLoaderDialog.dismiss();
//            CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
//            Intent intent = new Intent(getViewContext(), MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            CommonVariables.VOUCHERISFIRSTLOAD = true;
//            startActivity(intent);
//        }
    }

//    private void createSession(String ID) {
////        if (!mLoaderDialog.isShowing())
////            mLoaderDialog.show();
//        setUpProgressLoader();
//        mID = ID;
//        switch (ID) {
//            case PROCESS_TRANSACTION: {
//                createSession(createSessionCallback);
//                break;
//            }
//            case CHECK_STATUS: {
//                createSession(createSessionCallback);
//                break;
//            }
//        }
//    }
//
//    private Callback<String> createSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            try {
//                ResponseBody errBody = response.errorBody();
//                if (errBody == null) {
//                    if (!response.body().isEmpty()
//                            && !response.body().contains("<!DOCTYPE html>")
//                            && !response.body().equals("001")
//                            && !response.body().equals("002")
//                            && !response.body().equals("003")
//                            && !response.body().equals("004")
//                            && !response.body().equals("error")) {
//                        sessionid = response.body();
//                        switch (mID) {
//                            case PROCESS_TRANSACTION: {
//                                //call check status
//                                processMedPadalaTransaction();
//                                break;
//                            }
//                            case CHECK_STATUS: {
//                                //call check status
//                                break;
//                            }
//                        }
//                    } else {
////                        mLoaderDialog.dismiss();
//                        setUpProgressLoaderDismissDialog();
//                        showError();
//                    }
//                } else {
////                    mLoaderDialog.dismiss();
//                    setUpProgressLoaderDismissDialog();
//                    showError();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
////            mLoaderDialog.dismiss();
//            setUpProgressLoaderDismissDialog();
//            showError();
//        }
//    };

    private void createSession(String ID) {
//        if (!mLoaderDialog.isShowing())
//            mLoaderDialog.show();

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            setUpProgressLoader();
            mID = ID;
            switch (ID) {
                case PROCESS_TRANSACTION: {
                    processMedPadalaTransaction();
                    break;
                }
                case CHECK_STATUS: {
                    break;
                }
            }
        } else {
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }
    }

    public void processMedPadalaTransaction() {
        Call<GenericResponse> call = RetrofitBuild.getMedPadalaApiService(getViewContext())
                .processMedPadalaTransaction(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        strAmount,
                        strRecipientMobile,
                        strVoucherSession
                );

        call.enqueue(processMedPadalaTransactionCallback);
    }

    private Callback<GenericResponse> processMedPadalaTransactionCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        //check status
                        checkMedPadalaTransactionStatus();
                    } else {
//                        mLoaderDialog.dismiss();
                        setUpProgressLoaderDismissDialog();
//                      showError(response.body().getMessage());
//                        showGlobalDialogs(response.body().getMessage(), "retry",
//                                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);
                        showFailedDialog(response.body().getMessage());
                    }
                } else {
//                    mLoaderDialog.dismiss();
                    setUpProgressLoaderDismissDialog();
                    showError();
                }
            } catch (Exception e) {
//                mLoaderDialog.dismiss();
                setUpProgressLoaderDismissDialog();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            mLoaderDialog.dismiss();
            setUpProgressLoaderDismissDialog();
            showError();
        }
    };

    private void checkMedPadalaTransactionStatus() {
        Call<GenericResponse> call = RetrofitBuild.getMedPadalaApiService(getViewContext())
                .checkMedPadalaTransactionStatus(
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid),
                        String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
                        strVoucherSession
                );

        call.enqueue(checkMedPadalaTransactionStatusCallback);
    }

    private Callback<GenericResponse> checkMedPadalaTransactionStatusCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        if (currentdelaytime < totaldelaytime) {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    currentdelaytime = currentdelaytime + 1000;
                                    checkMedPadalaTransactionStatus();
                                }
                            }, 1000);
                        } else {
                            setUpProgressLoaderDismissDialog();
//                            buttonLayout.setVisibility(View.VISIBLE);
//                            mLoaderDialogRetry.setVisibility(View.GONE);
//                            mLoaderDialogImage.setVisibility(View.GONE);
//                            mLoaderDialogTitle.setText("TRANSACTION ON PROCESS");
//                            mLoaderDialogMessage.setText(response.body().getMessage());
//                            showGlobalDialogs(response.body().getMessage(), "close",
//                                    ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.ONPROCESS);
                            showProgressDialog(response.body().getMessage());
                        }
                    } else if (response.body().getStatus().equals("555")) {
                         setUpProgressLoaderDismissDialog();
//                        buttonLayout.setVisibility(View.VISIBLE);
//                        mLoaderDialogRetry.setVisibility(View.GONE);
//                        mLoaderDialogTitle.setText("SUCCESSFUL TRANSACTION");
//                        mLoaderDialogMessage.setText(response.body().getMessage());
//                        mLoaderDialogImage.setVisibility(View.GONE);
//                        mLoaderDialogClose.setVisibility(View.VISIBLE);
//                        showGlobalDialogs(response.body().getMessage(), "close",
//                                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.SUCCESS);
                        showSuccessDialog(response.body().getMessage());
                    } else if (response.body().getStatus().equals("666")) {
                        setUpProgressLoaderDismissDialog();
//                        buttonLayout.setVisibility(View.VISIBLE);
//                        mLoaderDialogRetry.setVisibility(View.GONE);
//                        mLoaderDialogTitle.setText("TIMEOUT");
//                        mLoaderDialogMessage.setText(response.body().getMessage());
//                        mLoaderDialogImage.setVisibility(View.GONE);
//                        mLoaderDialogClose.setVisibility(View.VISIBLE);
//                        showGlobalDialogs(response.body().getMessage(), "close",
//                                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.TIMEOUT);
                        showTimeOutDialog(response.body().getMessage());
                    } else {
                            setUpProgressLoaderDismissDialog();
//                        buttonLayout.setVisibility(View.VISIBLE);
//                        mLoaderDialogRetry.setVisibility(View.VISIBLE);
//                        mLoaderDialogTitle.setText("FAILED TRANSACTION");
//                        mLoaderDialogMessage.setText(response.body().getMessage());
//                        mLoaderDialogImage.setVisibility(View.GONE);
//                        mLoaderDialogClose.setVisibility(View.VISIBLE);
//                        showGlobalDialogs(response.body().getMessage(), "retry",
//                                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);
                        showFailedDialog(response.body().getMessage());
                    }
                } else {
//                    mLoaderDialog.dismiss();
                    setUpProgressLoaderDismissDialog();
                    showError();
                }
            } catch (Exception e) {
//                mLoaderDialog.dismiss();
                setUpProgressLoaderDismissDialog();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            mLoaderDialog.dismiss();
            setUpProgressLoaderDismissDialog();
            showError();
        }
    };

    private void showSuccessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("SUCCESS", message, "Close", "",
                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.SUCCESS);

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

    private void showProgressDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("ON PROGRESS", message, "Close", "",
                ButtonTypeEnum.SINGLE, true, false, DialogTypeEnum.ONPROCESS);

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

    private void showTimeOutDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("TIMEOUT", message, "Close", "",
                ButtonTypeEnum.SINGLE, true, false, DialogTypeEnum.TIMEOUT);

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



//    private void setupLoaderDialog() {
//        mLoaderDialog = new MaterialDialog.Builder(getViewContext())
//                .cancelable(false)
//                .customView(R.layout.dialog_custom_animated, true)
//                .build();
//
//        View view = mLoaderDialog.getCustomView();
//        if (view != null) {
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
//
//            mLoaderDialogTitle.setText("Processing...");
//
//            Glide.with(getViewContext())
//                    .load(R.drawable.progressloader)
//                    .into(mLoaderDialogImage);
//        }
//    }

}
