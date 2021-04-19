package com.goodkredit.myapplication.activities.csbrewards;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CSBSettingsChangeMobileConfimationActivity extends BaseActivity implements View.OnClickListener {

    private boolean isResend = false;
    private boolean hasAlreadyRequested = false;

    private static final String KEY_FLAG_REQUESTED = "requested";
    private static final String KEY_NEW_MOBILE = "new_mobile";
    private static final String KEY_REQUEST_TYPE = "request_type";

    private TextView tv_code;

    private String str_new_mob_num;
    private String str_code;
    private DatabaseHandler db;

    private String requestType;

    private String borrowerName;
    private String borrowerEmail;

    private String sessionid = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csb_settings_confirmation);
        init();
    }

    private void init() {

        db = new DatabaseHandler(getViewContext());

        //get account information
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));
                borrowerName = cursor.getString(cursor.getColumnIndex("firstname")) + " " + cursor.getString(cursor.getColumnIndex("lastname"));
                borrowerEmail = cursor.getString(cursor.getColumnIndex("email"));
            } while (cursor.moveToNext());
        }

        sessionid = PreferenceUtils.getSessionID(getViewContext());

        tv_code = findViewById(R.id.tv_code);

        str_new_mob_num = getIntent().getStringExtra(KEY_NEW_MOBILE);
        hasAlreadyRequested = getIntent().getBooleanExtra(KEY_FLAG_REQUESTED, false);
        requestType = getIntent().getStringExtra(KEY_REQUEST_TYPE);

        setupToolbar();
        findViewById(R.id.btn_csb_settings_update).setOnClickListener(this);
        findViewById(R.id.btn_csb_settings_resend).setOnClickListener(this);

        if (hasAlreadyRequested)
            findViewById(R.id.layout_request_exist).setVisibility(View.VISIBLE);
    }

    public static void start(Context context, boolean hasRequested, String mobile, String requestType) {
        Intent intent = new Intent(context, CSBSettingsChangeMobileConfimationActivity.class);
        intent.putExtra(KEY_FLAG_REQUESTED, hasRequested);
        intent.putExtra(KEY_NEW_MOBILE, mobile);
        intent.putExtra(KEY_REQUEST_TYPE, requestType);
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean isCodeValidLength() {
        boolean bool = false;
        str_code = tv_code.getText().toString();
        if (str_code.length() == 4) {
            bool = true;
        }
        return bool;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_csb_settings_update: {
                if (isCodeValidLength()) {
                    isResend = false;
                    getSession();
                } else {
                    showError("Verification code is either empty or not lacking. Please check.");
                }
                break;
            }
            case R.id.btn_csb_settings_resend: {
                isResend = true;
                getSession();
                break;
            }
        }
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            createSession(getSessionCallback);
//            if (isResend) {
//                showProgressDialog(getViewContext(), "Resending code.", "Please wait...");
//            } else {
//                showProgressDialog(getViewContext(), "Validating code.", "Please wait...");
//            }
//        } else {
//            showError("You are not connected to the internet.");
//        }
//    }
//
//    private Callback<String> getSessionCallback = new Callback<String>() {
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
//                        if (isResend) {
//                            resendCSBChangeNoVerificationCode();
//                        } else {
//                            validateCSBChangeMobile();
//                        }
//                    } else {
//                        hideProgressDialog();
//                        showError();
//                    }
//                } else {
//                    hideProgressDialog();
//                    showError();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            if (isResend) {
                showProgressDialog(getViewContext(), "Resending code.", "Please wait...");
                resendCSBChangeNoVerificationCode();
            } else {
                showProgressDialog(getViewContext(), "Validating code.", "Please wait...");
                validateCSBChangeMobile();
            }

        } else {
            hideProgressDialog();
            showError("You are not connected to the internet.");
        }
    }



    private void validateCSBChangeMobile() {
        Call<GenericResponse> call = RetrofitBuild.getRewardsAPIService(getViewContext())
                .validateCSBChangeMobile(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        sessionid,
                        borrowerid,
                        str_new_mob_num,
                        str_code,
                        borrowerName,
                        borrowerEmail
                );

        call.enqueue(validateCSBChangeMobileCallback);
    }

    private Callback<GenericResponse> validateCSBChangeMobileCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        if (requestType.equals("MOBILE VERIFICATION")) {
                            showLogoutWarning();
                        } else {
                            showRequestToCSBSucessDialog();
                        }
                    } else if (response.body().getStatus().equals("1")) {
                        showPendingRequestExisting();
                    } else {
                        showError(response.body().getMessage());
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
        }
    };

    private void resendCSBChangeNoVerificationCode() {
        Call<GenericResponse> call = RetrofitBuild.getRewardsAPIService(getViewContext())
                .resendCSBChangeNoVerificationCode(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        sessionid,
                        borrowerid,
                        str_new_mob_num
                );

        call.enqueue(resendCSBChangeNoVerificationCodeCallback);
    }

    private Callback<GenericResponse> resendCSBChangeNoVerificationCodeCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        showError("Verification code has been sent to your mobile number. Please check. Thanks.");
                    } else {
                        showError(response.body().getMessage());
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

    private void showPendingRequestExisting() {
        new MaterialDialog.Builder(getViewContext())
                .title("City Saving Bank")
                .content("You already have an existing pending request. Please wait for the confirmation. Thank you.")
                .positiveText("OK")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(getViewContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        startActivity(intent);
                    }
                })
                .show();
    }

    private void showRequestToCSBSucessDialog() {
        new MaterialDialog.Builder(getViewContext())
                .title("City Saving Bank")
                .content("You have successfully requested to change mobile number in CSB. Transaction is pending for approval. Thank you.")
                .positiveText("OK")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(getViewContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        startActivity(intent);
                    }
                })
                .show();
    }

    private void showLogoutWarning() {
        new MaterialDialog.Builder(getViewContext())
                .title("City Saving Bank")
                .content("You are about to be logged out from the app. Please use the new username and password sent to your new mobile number. Thank you.")
                .positiveText("OK")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(getViewContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        startActivity(intent);
                    }
                })
                .show();
    }
}
