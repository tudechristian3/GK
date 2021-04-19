package com.goodkredit.myapplication.fragments.skycable;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.delivery.SetupDeliveryAddressActivity;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.SkycablePPV;
import com.goodkredit.myapplication.bean.SkycablePPVSubscription;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.responses.skycable.CheckIfConfigIsAvailableResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycablePPVCustomerServiceChargeResponse;
import com.goodkredit.myapplication.responses.skycable.SubscribeSkycablePpvResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkycableSubscribePayPerViewFragment extends BaseFragment implements View.OnClickListener {

    private String merchantid = "";
    private Button btnProceed;

    private V2Subscriber v2Subscriber = null;
    private boolean isLoading = false;
    private DatabaseHandler mdb;
//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private double amount = 0;
    private double totalAmount = 0;
    private double serviceCharge = 0;
    private String longitude = "0.00";
    private String latitude = "0.00";
    private String mobileno = "";
    private String servicecode;
    private String borrowername;
    private String transactionno;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private MaterialDialog mDialog;
    private TextView txvAmount;
    private TextView txvTotalAmount;
    private TextView txvServiceCharge;

    private SkycablePPV skycablePPV = null;

    private boolean isProceed = false;
    private boolean isServiceCharge = false;
    private boolean isConfigAvailable = false;

    private EditText edtSkycableAccountNo;
    private EditText edtFirstName;
    private EditText edtLastName;
    private EditText edtMobileNo;
    private EditText edtEmailAddress;
    private EditText edtAddress;
    private EditText edtCity;

    //MAP ICONS
    private ImageView imgAddress;

    //MAP
    private double getlatitude = 0;
    private double getlongitude = 0;

    //FIELDS
    private TextView txvSkycableAccountNo;
    private TextView txvFirstName;
    private TextView txvLastName;
    private TextView txvMobileNumber;
    private TextView txvEmailAddress;
    private TextView txvAddress;
    private TextView txvCity;

    //MATERIAL DIALOG
    private TextView txvMessage;
    private MaterialDialog mSuccessDialog;

    //UNIFIED SESSION
    private String sessionid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_subscribe_pay_per_view, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (imgAddress != null) {
            imgAddress.setEnabled(true);
        }
    }

    public void init(View view) {
        if (isAdded()) {
            getActivity().setTitle("Subscribe");
        }
        btnProceed = (Button) view.findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(this);

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        edtSkycableAccountNo = (EditText) view.findViewById(R.id.edtSkycableAccountNo);
        edtFirstName = (EditText) view.findViewById(R.id.edtFirstName);
        edtLastName = (EditText) view.findViewById(R.id.edtLastName);
        edtMobileNo = (EditText) view.findViewById(R.id.edtMobileNo);
        edtEmailAddress = (EditText) view.findViewById(R.id.edtEmailAddress);
        edtAddress = (EditText) view.findViewById(R.id.edtAddress);
        edtCity = (EditText) view.findViewById(R.id.edtCity);

        //MAP ICONS
        imgAddress = (ImageView) view.findViewById(R.id.imgAddress);
        imgAddress.setEnabled(true);
        imgAddress.setOnClickListener(this);

        txvSkycableAccountNo = (TextView) view.findViewById(R.id.txvSkycableAccountNo);
        txvFirstName = (TextView) view.findViewById(R.id.txvFirstName);
        txvLastName = (TextView) view.findViewById(R.id.txvLastName);
        txvMobileNumber = (TextView) view.findViewById(R.id.txvMobileNumber);
        txvEmailAddress = (TextView) view.findViewById(R.id.txvEmailAddress);
        txvAddress = (TextView) view.findViewById(R.id.txvAddress);
        txvCity = (TextView) view.findViewById(R.id.txvCity);

        //SETUP DIALOGS
        setUpConfirmDialog();
//        setUpSuccessDialog();
    }

    private void setUpConfirmDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_success_confirm_order_payment, false)
                .build();
        View view = mDialog.getCustomView();
        TextView txvCloseDialog = (TextView) view.findViewById(R.id.txvCloseDialog);
        txvCloseDialog.setOnClickListener(this);
        TextView txvProceed = (TextView) view.findViewById(R.id.txvProceed);
        txvProceed.setOnClickListener(this);

        txvAmount = (TextView) view.findViewById(R.id.txvAmount);
        txvTotalAmount = (TextView) view.findViewById(R.id.txvTotalAmount);
        txvServiceCharge = (TextView) view.findViewById(R.id.txvServiceCharge);
    }

    public void initData() {
        mdb = new DatabaseHandler(getViewContext());
        v2Subscriber = mdb.getSubscriber(mdb);
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        skycablePPV = getArguments().getParcelable("skycablePPV");
        amount = skycablePPV.getDiscountedRate();
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), "skycablemerchantidppv");
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "skycablebillcode");
        borrowername = v2Subscriber.getBorrowerName();

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {

                new MaterialDialog.Builder(getViewContext())
                        .content("You are about to leave your subscription.")
                        .cancelable(false)
                        .negativeText("Close")
                        .positiveText("OK")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                ((SkyCableActivity) getViewContext()).displayView(4, null);

                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        })
                        .show();

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProceed: {

                if (CommonFunctions.isValidEmail(edtEmailAddress.getText().toString().trim()) &&
                        edtEmailAddress.getText().toString().trim().length() > 0 &&
                        edtSkycableAccountNo.getText().toString().trim().length() > 0 &&
                        edtFirstName.getText().toString().trim().length() > 0 &&
                        edtLastName.getText().toString().trim().length() > 0 &&
                        edtMobileNo.getText().toString().trim().length() > 0 &&
                        edtAddress.getText().toString().trim().length() > 0 &&
                        edtCity.getText().toString().trim().length() > 0) {

                    mobileno = getMobileNumber(edtMobileNo.getText().toString().trim());
                    if (mobileno.equals("INVALID")) {
//                        showError("Invalid Mobile Number");
                        txvMobileNumber.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
                        edtMobileNo.setError("Invalid Mobile Number");
                        edtMobileNo.requestFocus();
                    } else {

//                        LatLng latLng = getLatitudeLongitudeFromAddress(edtAddress.getText().toString().trim());
//                        latitude = String.valueOf(latLng.latitude);
//                        longitude = String.valueOf(latLng.longitude);
//                        isServiceCharge = true;
//                        getSession();

                        isProceed = false;
                        isServiceCharge = false;
                        isConfigAvailable = true;
                        getSession();
                    }
                } else {

                    checkFieldErrors();

                }

                break;
            }
            case R.id.refreshnointernet: {

                break;
            }
            case R.id.txvCloseDialog: {
                mDialog.dismiss();
                break;
            }
            case R.id.txvProceed: {
                isProceed = true;
                isServiceCharge = false;
                isConfigAvailable = false;
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                getSession();
                break;
            }
            case R.id.imgAddress: {

                if (isAirplaneModeOn(getViewContext())) {
                    showError("Airplane mode is enabled. Please disable Airplane mode and enable GPS or Internet to proceed.");
                } else {
                    if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                        if (isGPSModeOn()) {

                            imgAddress.setEnabled(false);

                            LatLng latLng = getLatitudeLongitudeFromAddress(edtAddress.getText().toString().trim());

                            Bundle args = new Bundle();
                            args.putString("latitude", String.valueOf(latLng.latitude));
                            args.putString("longitude", String.valueOf(latLng.longitude));
                            args.putBoolean("isServiceArea", false);

                            Intent intent = new Intent(getViewContext(), SetupDeliveryAddressActivity.class);
                            intent.putExtra("index", 1);
                            intent.putExtra("args", args);
                            startActivityForResult(intent, 1);

                        } else {
                            showGPSDisabledAlertToUser();
                        }
                    } else {
                        showError(getString(R.string.generic_internet_error_message));
                    }
                }
                break;
            }
            case R.id.txvSubscribeAgain: {
                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                Intent intent = new Intent(getViewContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                startActivity(intent);
                break;
            }
            case R.id.txvGoToHistory: {
//                if (mSuccessDialog != null) {
//                    mSuccessDialog.dismiss();
//                }

                getActivity().finish();

                Intent intent = new Intent(getViewContext(), SkyCableActivity.class);
                intent.putExtra("index", 8);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;
            }
        }
    }

    private void checkFieldErrors() {
        if (edtSkycableAccountNo.getText().toString().trim().length() == 0) {
            txvSkycableAccountNo.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtSkycableAccountNo.setError("This field can not be blank");
            edtSkycableAccountNo.requestFocus();
        } else {
            txvSkycableAccountNo.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtSkycableAccountNo.setError(null);
        }

        if (edtFirstName.getText().toString().trim().length() == 0) {
            txvFirstName.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtFirstName.setError("This field can not be blank");
            edtFirstName.requestFocus();
        } else {
            txvFirstName.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtFirstName.setError(null);
        }

        if (edtLastName.getText().toString().trim().length() == 0) {
            txvLastName.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtLastName.setError("This field can not be blank");
            edtLastName.requestFocus();
        } else {
            txvLastName.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtLastName.setError(null);
        }

        if (edtMobileNo.getText().toString().trim().length() == 0) {
            txvMobileNumber.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtMobileNo.setError("This field can not be blank");
            edtMobileNo.requestFocus();
        } else {
            txvMobileNumber.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtMobileNo.setError(null);
        }

        if (edtEmailAddress.getText().toString().trim().length() == 0) {
            txvEmailAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtEmailAddress.setError("This field can not be blank");
            edtEmailAddress.requestFocus();
        } else if (!CommonFunctions.isValidEmail(edtEmailAddress.getText().toString().trim())) {
            txvEmailAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtEmailAddress.setError("Please input a valid email address.");
            edtEmailAddress.requestFocus();
        } else {
            txvEmailAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtEmailAddress.setError(null);
        }

        if (edtAddress.getText().toString().trim().length() == 0) {
            txvAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtAddress.setError("This field can not be blank");
            edtAddress.requestFocus();
        } else {
            txvAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtAddress.setError(null);
        }

        if (edtCity.getText().toString().trim().length() == 0) {
            txvCity.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtCity.setError("This field can not be blank");
            edtCity.requestFocus();
        } else {
            txvCity.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtCity.setError(null);
        }
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            if (isServiceCharge) {
                mTvFetching.setText("Calculating Service Charge..");
            } else if (isProceed) {
//                mTvFetching.setText("Fetching Pre-Purchase Session..");
                mTvFetching.setText("Sending Request..");
            } else if (isConfigAvailable) {
                mTvFetching.setText("Checking Config..");
            }

            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            isLoading = true;

//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isServiceCharge) {
                getSkycablePPVCustomerServiceCharge(getSkycablePPVCustomerServiceChargeSession);
            } else if (isProceed) {
//                        prePurchase(prePurchaseSession);
                subscribeSkycablePpv(subscribeSkycablePpvSession);
            } else if (isConfigAvailable) {
                checkIfConfigIsAvailable(checkIfConfigIsAvailableSession);
            }

        } else {
            isServiceCharge = false;
            mLlLoader.setVisibility(View.GONE);
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
//                    isServiceCharge = false;
//                    isLoading = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    isServiceCharge = false;
//                    isLoading = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    isServiceCharge = false;
//                    isLoading = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    if (isServiceCharge) {
//                        getSkycablePPVCustomerServiceCharge(getSkycablePPVCustomerServiceChargeSession);
//                    } else if (isProceed) {
////                        prePurchase(prePurchaseSession);
//                        subscribeSkycablePpv(subscribeSkycablePpvSession);
//                    } else if (isConfigAvailable) {
//                        checkIfConfigIsAvailable(checkIfConfigIsAvailableSession);
//                    }
//
//                }
//            } else {
//                isServiceCharge = false;
//                isLoading = false;
//                mLlLoader.setVisibility(View.GONE);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void subscribeSkycablePpv(Callback<SubscribeSkycablePpvResponse> addParamountQueueCallback) {

        String ppvAddress = edtAddress.getText().toString().trim();
        String ppvCity = edtCity.getText().toString().trim();

        if(ppvAddress.length() == 0){
            ppvAddress = ".";
        }

        if(ppvCity.length() == 0){
            ppvCity = ".";
        }

        Call<SubscribeSkycablePpvResponse> subscribeskycableppv = RetrofitBuild.subscribeSkycablePpvService(getViewContext())
                .subscribeSkycablePpvCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        skycablePPV.getPPVID(),
                        edtSkycableAccountNo.getText().toString().trim(),
                        borrowername,
                        edtFirstName.getText().toString().trim(),
                        edtLastName.getText().toString().trim(),
                        mobileno,
                        edtEmailAddress.getText().toString().trim(),
                        ppvAddress,
                        ppvCity,
                        String.valueOf(amount),
                        merchantid,
                        longitude,
                        latitude,
                        ".",
                        PreferenceUtils.getStringPreference(getViewContext(), "skycablebillcode"));
        subscribeskycableppv.enqueue(addParamountQueueCallback);
    }

    private final Callback<SubscribeSkycablePpvResponse> subscribeSkycablePpvSession = new Callback<SubscribeSkycablePpvResponse>() {

        @Override
        public void onResponse(Call<SubscribeSkycablePpvResponse> call, Response<SubscribeSkycablePpvResponse> response) {
            ResponseBody errorBody = response.errorBody();

            mLlLoader.setVisibility(View.GONE);

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    if (mdb != null) {

//                        mLoaderDialog.dismiss();

//                        transactionno = response.body().getSubscriptionTxnID();
//
//                        mSuccessDialog.show();
//
////                        SKYCABLE
//                        String msg = "Your SKYCABLE Pay-Per-View subscription is now ON QUEUE with the <b>Approval Code</b> " + transactionno + ". You will receive a notification once it has been change to FOR PAYMENT. Please monitor your request in subscription history. <br> <br> Thank you for using GoodKredit.";
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            txvMessage.setText(Html.fromHtml(msg, Html.FROM_HTML_MODE_COMPACT));
//                        } else {
//                            txvMessage.setText(Html.fromHtml(msg));
//                        }

                        transactionno = response.body().getSubscriptionTxnID();

                        String msg = "Your SKYCABLE Pay-Per-View subscription is now ON QUEUE with the <b>Approval Code</b> "
                                + transactionno + ". You will receive a notification once it has been change to FOR PAYMENT. " +
                                "Please monitor your request in subscription history. <br> <br> Thank you for using GoodKredit.";

                        String btndoubletype = "GOTOHISTORY";
                        PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SKY_BUTTON, btndoubletype);

                        showGlobalDialogs(msg, "close",
                                ButtonTypeEnum.DOUBLE, true, true, DialogTypeEnum.NOTICE);
                    }

                } else {
//                    mLoaderDialog.dismiss();
                    showError(response.body().getMessage());
                }
            } else {
//                mLoaderDialog.dismiss();
                showError();
            }

        }

        @Override
        public void onFailure(Call<SubscribeSkycablePpvResponse> call, Throwable t) {
//            mLoaderDialog.dismiss();
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void prePurchase(Callback<String> prePurchaseCallback) {
        Call<String> prepurchase = RetrofitBuild.prePurchaseService(getViewContext())
                .prePurchaseCall(borrowerid,
                        String.valueOf(totalAmount),
                        userid,
                        imei,
                        sessionid,
                        authcode);
        prepurchase.enqueue(prePurchaseCallback);
    }

    private final Callback<String> prePurchaseSession = new Callback<String>() {

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                mLlLoader.setVisibility(View.GONE);
                String responseData = response.body().toString();

                if (!responseData.isEmpty()) {
                    if (responseData.equals("001")) {
                        CommonFunctions.hideDialog();
                        showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
                    } else if (responseData.equals("error")) {
                        CommonFunctions.hideDialog();
                        showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                    } else if (responseData.contains("<!DOCTYPE html>")) {
                        CommonFunctions.hideDialog();
                        showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                    } else {
                        if (responseData.length() > 0) {

                            SkycablePPVSubscription skycablePPVSubscription = new SkycablePPVSubscription(edtSkycableAccountNo.getText().toString().trim(),
                                    edtFirstName.getText().toString().trim(),
                                    edtLastName.getText().toString().trim(),
                                    mobileno,
                                    edtEmailAddress.getText().toString().trim(),
                                    edtAddress.getText().toString().trim(),
                                    edtCity.getText().toString().trim(),
                                    amount,
                                    merchantid,
                                    longitude,
                                    latitude,
                                    skycablePPV.getPPVID(),
                                    skycablePPV.getPPVName(),
                                    skycablePPV.getPPVDescription(),
                                    skycablePPV.getImageURL());

                            if (mDialog != null) {
                                mDialog.dismiss();
                            }

                            Intent intent = new Intent(getViewContext(), Payment.class);
                            intent.putExtra("AMOUNT", String.valueOf(amount));
                            intent.putExtra("SERVICECHARGE", String.valueOf(serviceCharge));
                            intent.putExtra("AMOUNTPAID", String.valueOf(totalAmount));
                            intent.putExtra("SKYCABLEPPVQUEUE", "true");
                            intent.putExtra("VOUCHERSESSION", responseData);
                            intent.putExtra("PPVSUBSCRIPTION", skycablePPVSubscription);
                            startActivity(intent);

                        } else {
                            showError("Invalid Voucher Session.");
                        }
                    }
                } else {
                    CommonFunctions.hideDialog();
                    showNoInternetConnection(true);
                    showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                }

            }

        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            CommonFunctions.hideDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };


    private void getSkycablePPVCustomerServiceCharge(Callback<GetSkycablePPVCustomerServiceChargeResponse> getSkycablePPVCustomerServiceChargeCallback) {
        Call<GetSkycablePPVCustomerServiceChargeResponse> getsycableppvsc = RetrofitBuild.getSkycablePPVCustomerServiceChargeService(getViewContext())
                .getSkycablePPVCustomerServiceChargeCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        String.valueOf(amount),
                        "PPV");
        getsycableppvsc.enqueue(getSkycablePPVCustomerServiceChargeCallback);
    }

    private final Callback<GetSkycablePPVCustomerServiceChargeResponse> getSkycablePPVCustomerServiceChargeSession = new Callback<GetSkycablePPVCustomerServiceChargeResponse>() {

        @Override
        public void onResponse(Call<GetSkycablePPVCustomerServiceChargeResponse> call, Response<GetSkycablePPVCustomerServiceChargeResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            ResponseBody errorBody = response.errorBody();
            isServiceCharge = false;

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    mDialog.show();
                    txvAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(skycablePPV.getDiscountedRate())));
                    txvServiceCharge.setText(CommonFunctions.currencyFormatter(String.valueOf(response.body().getCustomerServiceCharge())));
                    totalAmount = skycablePPV.getDiscountedRate() + Double.valueOf(response.body().getCustomerServiceCharge());
                    serviceCharge = Double.valueOf(response.body().getCustomerServiceCharge());
                    txvTotalAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(totalAmount)));

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetSkycablePPVCustomerServiceChargeResponse> call, Throwable t) {
            isServiceCharge = false;
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
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

    private LatLng getLatitudeLongitudeFromAddress(String address) {
        Geocoder geocoder = new Geocoder(getViewContext());
        GPSTracker tracker = new GPSTracker(getViewContext());

        if (address.trim().length() > 0) {
            List<Address> addresses = new ArrayList<>();
            try {
                addresses = geocoder.getFromLocationName(address, 1);
                if (addresses.size() > 0) {
                    getlatitude = addresses.get(0).getLatitude();
                    getlongitude = addresses.get(0).getLongitude();
                } else {
                    getlatitude = tracker.getLatitude();
                    getlongitude = tracker.getLongitude();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            getlatitude = tracker.getLatitude();
            getlongitude = tracker.getLongitude();
        }

        return new LatLng(getlatitude, getlongitude);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Logger.debug("antonhttp", "FRAGMENT requestCode = " + requestCode);

        if (resultCode == Activity.RESULT_OK) {
            Bundle args = data.getExtras();
            Address checkaddress = null;
            if (args != null) {
                checkaddress = args.getParcelable("MarkerAddress");
            }

            if (checkaddress != null) {
                double checklat = checkaddress.getLatitude();
                double checklong = checkaddress.getLongitude();

                latitude = String.valueOf(checklat);
                longitude = String.valueOf(checklong);
            }

            if (requestCode == 1) {
//                edtAddress.setText(getMarkerAddress(checkaddress));
                edtAddress.setText(args.getString("placeAddress"));
                if (checkaddress.getLocality() != null) {
                    if (checkaddress.getLocality().trim().length() > 0 && !checkaddress.getLocality().equals("null")) {
                        edtCity.setText(checkaddress.getLocality());
                    }
                }
            }
        }

    }

    private String getMarkerAddress(Address address) {
        String display_address = "";
        if (address != null) {

            display_address += address.getAddressLine(0) + ", ";

            for (int i = 1; i < address.getMaxAddressLineIndex(); i++) {
                display_address += address.getAddressLine(i) + ", ";
            }

            display_address = display_address.substring(0, display_address.length() - 2);
        }

        return display_address;
    }

    private void checkIfConfigIsAvailable(Callback<CheckIfConfigIsAvailableResponse> checkIfConfigIsAvailableCallback) {
        Call<CheckIfConfigIsAvailableResponse> checkconfig = RetrofitBuild.checkIfConfigIsAvailableService(getViewContext())
                .checkIfConfigIsAvailableCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        servicecode,
                        "Pay Per View");
        checkconfig.enqueue(checkIfConfigIsAvailableCallback);
    }

    private final Callback<CheckIfConfigIsAvailableResponse> checkIfConfigIsAvailableSession = new Callback<CheckIfConfigIsAvailableResponse>() {

        @Override
        public void onResponse(Call<CheckIfConfigIsAvailableResponse> call, Response<CheckIfConfigIsAvailableResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);


            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    LatLng latLng = getLatitudeLongitudeFromAddress(edtAddress.getText().toString().trim());
                    latitude = String.valueOf(latLng.latitude);
                    longitude = String.valueOf(latLng.longitude);
                    isServiceCharge = true;
                    isConfigAvailable = false;
                    isProceed = false;
                    getSession();

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<CheckIfConfigIsAvailableResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            isConfigAvailable = false;
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void setUpSuccessDialog() {
        mSuccessDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_success_order_payment, false)
                .build();
        View view = mSuccessDialog.getCustomView();

        TextView txvTitle = (TextView) view.findViewById(R.id.title);
        txvTitle.setText("Request For Approval");
        TextView txvSubscribeAgain = (TextView) view.findViewById(R.id.txvSubscribeAgain);
        txvSubscribeAgain.setOnClickListener(this);
        TextView txvGoToHistory = (TextView) view.findViewById(R.id.txvGoToHistory);
        txvGoToHistory.setOnClickListener(this);

        txvMessage = (TextView) view.findViewById(R.id.message);

    }
}
