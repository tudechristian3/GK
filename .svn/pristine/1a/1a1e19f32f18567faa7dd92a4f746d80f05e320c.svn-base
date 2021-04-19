package com.goodkredit.myapplication.activities.gknegosyo;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.model.GKNegosyPackage;
import com.goodkredit.myapplication.model.GKNegosyoApplicationStatus;
import com.goodkredit.myapplication.responses.gknegosyo.ApplyGKNegosyoPackagesResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKNegosyoPackageConfirmationActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_GK_NEGOSYO_PACKAGE = "gknegoobj";
    public static final String KEY_DISTRIBUTOR_ID = "gknegosyo_distributor";
    private GKNegosyPackage mGKNegosyoPackage;

    private TextView tv_gknp_confirmation_packagename;
    private TextView tv_gknp_confirmation_packageprice;
    private TextView tv_gknp_confirmation_packagetotal;
    private CheckBox checkbox_gknp_confirmation;

    private String mDistroID;
    private String mDistroName;
    private String mPaymentType;

    private RadioGroup rg_payment_options;
    private RadioButton rb_choosed_option;

    private RelativeLayout rootView;

    private String voucherSession;
    private GKService mGKService;
    private Address mAddress = null;

    private GKNegosyoApplicationStatus mApplicationStatus;

    private LinearLayout btn_gknp_termsandconditions;
    private TextView txv_gknp_termsandconditions;
    private String strtermsandcondition = "";

    private MaterialDialog mDialog = null;

    private EditText mCode;

    private String strCode = ".";

    private String sessionid;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String param;
    private String keyEncryption;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_package_confirmation);
        init();
    }

    private void init() {
        setupToolbar();

        mGKNegosyoPackage = getIntent().getParcelableExtra(KEY_GK_NEGOSYO_PACKAGE);
        mDistroID = getIntent().getStringExtra(KEY_DISTRIBUTOR_ID);
        mGKService = getIntent().getParcelableExtra(GKService.KEY_SERVICE_OBJ);
        mAddress = getIntent().getParcelableExtra("KEY_ADDRESS");
        mDistroName = getIntent().getStringExtra("KEY_DISTRO_NAME");

        if (mGKNegosyoPackage.getExtra1().contains("1") && PreferenceUtils.getBooleanPreference(getViewContext(), PreferenceUtils.KEY_IS_REFER_RESSELER_PROMO)) {
            findViewById(R.id.referralLayout).setVisibility(View.VISIBLE);
        }

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        tv_gknp_confirmation_packagename = findViewById(R.id.tv_gknp_confirmation_packagename);
        tv_gknp_confirmation_packageprice = findViewById(R.id.tv_gknp_confirmation_packageprice);
        tv_gknp_confirmation_packagetotal = findViewById(R.id.tv_gknp_confirmation_packagetotal);
        checkbox_gknp_confirmation = findViewById(R.id.checkbox_gknp_confirmation);

        tv_gknp_confirmation_packagename.setText(mGKNegosyoPackage.getPackageName());
        tv_gknp_confirmation_packageprice.setText("₱" + CommonFunctions.commaFormatter(String.valueOf(mGKNegosyoPackage.getPrice())));
        tv_gknp_confirmation_packagetotal.setText("₱" + CommonFunctions.commaFormatter(String.valueOf(mGKNegosyoPackage.getPrice())));

        rg_payment_options = findViewById(R.id.rg_payment_options);
        rootView = findViewById(R.id.rootView);

        findViewById(R.id.btn_gknp_confirmation_apply).setOnClickListener(this);

        btn_gknp_termsandconditions = findViewById(R.id.btn_gknp_termsandconditions);
        btn_gknp_termsandconditions.setOnClickListener(this);

        txv_gknp_termsandconditions = findViewById(R.id.txv_gknp_termsandconditions);
        strtermsandcondition = "I agree to the Terms and Conditions of GK Negosyo.";

        mCode = findViewById(R.id.btn_code);
        mCode.setOnClickListener(this);

        SpannableString content = new SpannableString(strtermsandcondition);
        content.setSpan(new UnderlineSpan(), 0, strtermsandcondition.length(), 0);
        txv_gknp_termsandconditions.setText(content);
    }

    public static void start(Context context, GKNegosyPackage gkNegosyPackage, String distributorID, GKService gkService, Address address, String distroName) {
        Intent intent = new Intent(context, GKNegosyoPackageConfirmationActivity.class);
        intent.putExtra(KEY_GK_NEGOSYO_PACKAGE, gkNegosyPackage);
        intent.putExtra(KEY_DISTRIBUTOR_ID, distributorID);
        intent.putExtra(GKService.KEY_SERVICE_OBJ, gkService);
        intent.putExtra("KEY_ADDRESS", address);
        intent.putExtra("KEY_DISTRO_NAME", distroName);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gknp_confirmation_apply: {
                try {
                    int selectedId = rg_payment_options.getCheckedRadioButtonId();
                    rb_choosed_option = findViewById(selectedId);
                    String strChoosedOption = rb_choosed_option.getText().toString().trim().toUpperCase();

                    if (checkbox_gknp_confirmation.isChecked() && !strChoosedOption.isEmpty()) {
                        if (strChoosedOption.contains("VOUCHER")) {
                            mPaymentType = "PAY VIA GK";
                        } else {
                            mPaymentType = "PAY VIA PARTNERS";
                        }
                        showPackageApplicationDialog();
                    } else {
                        showError("In order to proceed, you must accept the Terms and Conditions of GK Negosyo. Please check and try again. Thanks.");
                        checkbox_gknp_confirmation.requestFocus();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Please choose a payment option.");
                }
                break;
            }

            case R.id.btn_gknp_termsandconditions: {
                Bundle args = new Bundle();
                args.putString("distributorid", mDistroID);
                GKNegosyoPackageTermsandCondition.start(getViewContext(), args);
                break;
            }

            case R.id.btn_code: {
                Intent intent = new Intent(getViewContext(), GKNegosyoEnterReferralCodeActivity.class);
                intent.putExtra("OBJ", mGKNegosyoPackage);
                startActivityForResult(intent, 111);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                strCode = data.getStringExtra("code");
                mCode.setText(strCode);
            }
        }
    }

    private void showPackageApplicationDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .customView(R.layout.dialog_gk_negosyo_package_confirmation, false)
                .cancelable(false)
                .negativeText("Cancel")
                .negativeColorRes(R.color.color_AEAEAE)
                .positiveText("Confirm")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getSession();
                    }
                })
                .show();

        View view = mDialog.getView();

        TextView tv_packge_name = view.findViewById(R.id.tv_packge_name);
        TextView tv_amount = view.findViewById(R.id.tv_amount);
        TextView tv_validity = view.findViewById(R.id.tv_validity);
        TextView tv_code = view.findViewById(R.id.tv_code);
        TextView V_REQUEST_NOTE = view.findViewById(R.id.V_REQUEST_NOTE);

        if (mGKNegosyoPackage.getExtra1().contains("1") && PreferenceUtils.getBooleanPreference(getViewContext(), PreferenceUtils.KEY_IS_REFER_RESSELER_PROMO)) {
            if (!strCode.isEmpty()) {
                view.findViewById(R.id.codelayout).setVisibility(View.VISIBLE);
                tv_code.setText(strCode);
            }
        } else {
            view.findViewById(R.id.codelayout).setVisibility(View.GONE);
        }

        tv_packge_name.setText("You are about to apply for\nGK Negosyo " + mGKNegosyoPackage.getPackageName());
        tv_amount.setText("₱" + CommonFunctions.currencyFormatter(String.valueOf(mGKNegosyoPackage.getPrice())));
        tv_validity.setText(validity(mGKNegosyoPackage.getNumberMonthExpiry()));
        if (mPaymentType.contains("PARTNERS"))
            V_REQUEST_NOTE.setVisibility(View.VISIBLE);
        else
            V_REQUEST_NOTE.setVisibility(View.GONE);
    }

//    private void getSession() {
//        showProgressDialog(getViewContext(), "", "Please wait...");
//        createSession(getSessionCallback);
//    }
//
//    private Callback<String> getSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            try {
//                ResponseBody errBody = response.errorBody();
//                if (errBody == null) {
//                    String responseData = response.body();
//                    if (!responseData.isEmpty()) {
//                        if (responseData.equals("001") || responseData.equals("002")) {
//                            hideProgressDialog();
//                            showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                        } else if (responseData.equals("error")) {
//                            hideProgressDialog();
//                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                        } else if (responseData.contains("<!DOCTYPE html>")) {
//                            hideProgressDialog();
//                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                        } else {
//                            sessionid = responseData;
//                            applyGKNegosyoPackage();
//                        }
//                    } else {
//                        hideProgressDialog();
//                        showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    }
//                } else {
//                    hideProgressDialog();
//                    showError();
//                }
//            } catch (Exception e) {
//                hideProgressDialog();
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            //applyGKNegosyoPackage();
            applyGKNegosyoPackagesV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private String checkAddress(String data) {
        try {
            String str = ".";
            if (data.isEmpty())
                return str;
            else
                return data;
        } catch (Exception e) {
            e.printStackTrace();
            return ".";
        }
    }

    private void applyGKNegosyoPackage() {
        Call<ApplyGKNegosyoPackagesResponse> call = RetrofitBuild.getGKNegosyoAPIService(getViewContext())
                .applyGKNegosyoPackages(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        sessionid,
                        mDistroID,
                        mGKNegosyoPackage.getPackageID(),
                        mPaymentType,
                        mGKService.getServiceCode(),
                        checkAddress(mAddress.getLocality() == null ? "." : mAddress.getLocality()),
                        checkAddress(mAddress.getThoroughfare() == null ? "." : mAddress.getThoroughfare()),
                        checkAddress(mAddress.getAdminArea() == null ? mAddress.getSubAdminArea() : mAddress.getAdminArea()),
                        mAddress.getLatitude(),
                        mAddress.getLongitude(),
                        strCode
                );
        call.enqueue(applyGKNegosyoPackageCallback);
    }

    private Callback<ApplyGKNegosyoPackagesResponse> applyGKNegosyoPackageCallback = new Callback<ApplyGKNegosyoPackagesResponse>() {
        @Override
        public void onResponse(Call<ApplyGKNegosyoPackagesResponse> call, Response<ApplyGKNegosyoPackagesResponse> response) {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        if (mPaymentType.contains("PARTNERS")) {
                            GKNegosyoPackageBillinReferenceActivity.start(getViewContext(), response.body().getData(), String.valueOf(mGKNegosyoPackage.getPrice()));
                            finish();
                        } else {
                            CacheManager.getInstance().saveGKNegosyResellerAddress(mAddress);
                            mApplicationStatus = response.body().getData();
                            voucherSession = response.body().getData().getDataResID();
                            Intent intent = new Intent(getViewContext(), Payment.class);
                            intent.putExtra("GKNEGOSYO", "GKNEGOSYO");
                            intent.putExtra("SERVICE", mGKService);
                            intent.putExtra("GKNEGOSYOPACKAGE", mGKNegosyoPackage);
                            intent.putExtra("VOUCHERSESSION", voucherSession);
                            intent.putExtra("DISTRIBUTORID", mDistroID);
                            intent.putExtra("DISTRIBUTORNAME", mDistroName);
                            intent.putExtra("APPLICATION_STATUS", mApplicationStatus);
                            startActivity(intent);
                        }
                    } else if (response.body().getStatus().equals("005")) {
                        showAlreadyPendingRequestDialog();
                    } else {
                        showError(response.body().getMessage());
                    }
                } else {
                    Logger.debug("okhttp","ERROR : applyGKNegosyoPackageCallback");
                    showError();
                }
        }

        @Override
        public void onFailure(Call<ApplyGKNegosyoPackagesResponse> call, Throwable t) {
            hideProgressDialog();
            Logger.debug("okhttp","ERROR : applyGKNegosyoPackageCallback:"+t.getMessage());
            showError();
        }
    };

    void showAlreadyPendingRequestDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("You already have a pending request. Please pay accordingly. Thank you.")
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(getViewContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        startActivity(intent);
                    }
                })
                .show();
    }

    private String validity(int nofMonths) {
        String str = "";
        if (((float) nofMonths / 12) < 1) {
            if (nofMonths > 1)
                str = str + String.valueOf(nofMonths) + " months";
            else
                str = str + String.valueOf(nofMonths) + " month";
        } else {
            if ((nofMonths / 12) > 1)
                str = str + String.valueOf(nofMonths / 12) + " years";
            else
                str = str + String.valueOf(nofMonths / 12) + " year";
        }
        return str;
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void applyGKNegosyoPackagesV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();

            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("packageid", mGKNegosyoPackage.getPackageID());
            parameters.put("paymenttype", mPaymentType);
            parameters.put("distributorid",mDistroID);
            parameters.put("servicecode", mGKService.getServiceCode());
            parameters.put("streetaddress", mAddress.getLocality() == null ? "." : mAddress.getLocality());
            parameters.put("city", mAddress.getThoroughfare() == null ? "." : mAddress.getThoroughfare());
            parameters.put("province", mAddress.getAdminArea() == null ? "." : mAddress.getAdminArea());
            parameters.put("longitude", String.valueOf(mAddress.getLongitude()));
            parameters.put("latitude", String.valueOf(mAddress.getLatitude()));
            parameters.put("referralcode", strCode == null || strCode.isEmpty() ? "." : strCode);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "applyGKNegosyoPackagesV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            applyGKNegosyoPackagesV2Object(applyGKNegosyoPackagesV2Callback);

        }else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void applyGKNegosyoPackagesV2Object(Callback<GenericResponse> applyGKNegosyoPackages) {

        Call<GenericResponse> call = RetrofitBuilder.getGkNegosyoV2API(getViewContext())
                .applyGKNegosyoPackagesV2(
                        authenticationid,sessionid,param
                );
        call.enqueue(applyGKNegosyoPackages);
    }

    private Callback<GenericResponse>applyGKNegosyoPackagesV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                        GKNegosyoApplicationStatus status = new Gson().fromJson(data,GKNegosyoApplicationStatus.class);
                        if (mPaymentType.contains("PARTNERS")) {
                            GKNegosyoPackageBillinReferenceActivity.start(getViewContext(), status, String.valueOf(mGKNegosyoPackage.getPrice()));
                            finish();
                        } else {
                            CacheManager.getInstance().saveGKNegosyResellerAddress(mAddress);
                            mApplicationStatus = status;
                            voucherSession = status.getDataResID();
                            Intent intent = new Intent(getViewContext(), Payment.class);
                            intent.putExtra("GKNEGOSYO", "GKNEGOSYO");
                            intent.putExtra("SERVICE", mGKService);
                            intent.putExtra("GKNEGOSYOPACKAGE", mGKNegosyoPackage);
                            intent.putExtra("VOUCHERSESSION", voucherSession);
                            intent.putExtra("DISTRIBUTORID", mDistroID);
                            intent.putExtra("DISTRIBUTORNAME", mDistroName);
                            intent.putExtra("APPLICATION_STATUS", mApplicationStatus);
                            startActivity(intent);
                        }
                    } else if (response.body().getStatus().equals("005") && !message.contains("Can not proceed")) {
                        showAlreadyPendingRequestDialog();
                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
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
            hideProgressDialog();
            showError();
        }
    };



}
