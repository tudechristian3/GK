package com.goodkredit.myapplication.activities.gknegosyo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.Referral;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.referafriend.GenerateReferralCodeResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKNegosyoReferAResellerActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_code;
    private TextView tv_message;
    private TextView lbl_code;
    private TextView btn_customize_code;

    private String str_invite = "";

    private MaterialDialog dialog;
    private EditText edt_code;
    private String strCode;

    private String sessionid;

    //NEW VARIABLE FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_refer_a_reseller);
        init();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, GKNegosyoReferAResellerActivity.class);
        context.startActivity(intent);
    }

    private void init() {
        setupToolbar();

        findViewById(R.id.btn_refer).setOnClickListener(this);
        findViewById(R.id.btn_refresh).setOnClickListener(this);

        tv_message = findViewById(R.id.tv_message);
        tv_code = findViewById(R.id.tv_code);
        lbl_code = findViewById(R.id.lbl_code);
        btn_customize_code = findViewById(R.id.btn_customize_code);
        btn_customize_code.setOnClickListener(this);

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

//        showProgressDialog(getViewContext(), "", "Please wait...");
//        createSession(getSessionCallback);
        getReferralSession();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_refer: {
                referFriend(str_invite);
                break;
            }
            case R.id.btn_refresh: {
//                showProgressDialog(getViewContext(), "", "Please wait...");
//                createSession(getSessionCallback);
                getReferralSession();
                break;
            }
            case R.id.btn_customize_code: {
                showCustomizeCodeDialog();
                break;
            }
        }
    }

    private void referFriend(String invite) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, invite);
        startActivity(Intent.createChooser(intent, "Refer a Reseller"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private Callback<String> getSessionCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (!response.body().isEmpty()
                            && !response.body().contains("<!DOCTYPE html>")
                            && !response.body().equals("001")
                            && !response.body().equals("002")
                            && !response.body().equals("003")
                            && !response.body().equals("004")
                            && !response.body().equals("error")) {
                        sessionid = response.body();
                        generateResellerReferralCode();
                    } else {
                        hideProgressDialog();
                        showError();
                    }
                } else {
                    hideProgressDialog();
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

    private void getReferralSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            //generateResellerReferralCode();
            generateResellerReferralCodeV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void generateResellerReferralCode() {
        Call<GenerateReferralCodeResponse> call = RetrofitBuild.getReferralAPIService(getViewContext())
                .generateResellerReferralCode(
                        imei,
                        userid,
                        sessionid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        "ANDROID"
                );
        call.enqueue(generateResellerReferralCodeCallback);
    }

    private Callback<GenerateReferralCodeResponse> generateResellerReferralCodeCallback = new Callback<GenerateReferralCodeResponse>() {
        @Override
        public void onResponse(Call<GenerateReferralCodeResponse> call, Response<GenerateReferralCodeResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().contains("000")) {
                        String statusOfReferral = response.body().getData().getReferralPromo();
                        str_invite = response.body().getData().getInvitationMessage();
                        tv_message.setText(response.body().getData().getReferralDescription());
                        if (statusOfReferral.equals("ACTIVE")) {
                            tv_code.setText(response.body().getData().getReferralCode());
                            tv_code.setVisibility(View.VISIBLE);
                            lbl_code.setVisibility(View.VISIBLE);
                            btn_customize_code.setVisibility(View.VISIBLE);
                        } else {
                            tv_code.setVisibility(View.GONE);
                            lbl_code.setVisibility(View.GONE);
                            btn_customize_code.setVisibility(View.GONE);
                        }
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
        public void onFailure(Call<GenerateReferralCodeResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

    private void showCustomizeCodeDialog() {
        try {
            dialog = new MaterialDialog.Builder(getViewContext())
                    .customView(R.layout.dialog_customize_code, false)
                    .positiveText("Submit")
                    .negativeText("Cancel")
                    .cancelable(true)
                    .show();

            View view = dialog.getCustomView();
            View positive = dialog.getActionButton(DialogAction.POSITIVE);

            edt_code = view.findViewById(R.id.edt_code);
            edt_code.addTextChangedListener(codeTextWatcher);

            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edt_code.getText().length() > 2) {
                        dialog.dismiss();
//                        showProgressDialog(getViewContext(), "", "Please wait...");
//                        createSession(getSessionCallback2);
                        getCustomizeRefCodeSession();
                    } else {
                        showError("Code must have 3-7 characters. Please try again.");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextWatcher codeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            strCode = s.toString().toUpperCase();
            //edt_code.setText(strCode);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private Callback<String> getSessionCallback2 = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (!response.body().isEmpty()
                            && !response.body().contains("<!DOCTYPE html>")
                            && !response.body().equals("001")
                            && !response.body().equals("002")
                            && !response.body().equals("003")
                            && !response.body().equals("004")
                            && !response.body().equals("error")) {
                        sessionid = response.body();
                        customizeReferralCode();
                    } else {
                        hideProgressDialog();
                        showError();
                    }
                } else {
                    hideProgressDialog();
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

    private void getCustomizeRefCodeSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            customizeReferralCode();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void customizeReferralCode() {
        Call<GenericResponse> call = RetrofitBuild.getReferralAPIService(getViewContext())
                .customizeReferralCode(
                        imei,
                        userid,
                        sessionid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        strCode,
                        "ANDROID"
                );
        call.enqueue(customizeReferralCodeCallback);
    }

    private Callback<GenericResponse> customizeReferralCodeCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().contains("000")) {
                        //createSession(getSessionCallback);
                        getReferralSession();
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

    /**
     * SECURITY UPDATE
     *  AS OF
     *  OCTOBER 2019
     * */

    private void generateResellerReferralCodeV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "generateResellerReferralCodeV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            generateResellerReferralCodeV2Object(generateResellerReferralCodeV2Callback);

        }else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private void generateResellerReferralCodeV2Object(Callback<GenericResponse> genericResponseCallback) {
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .generateResellerReferralCodeV2(
                        authenticationid,sessionid,param
                );
        call.enqueue(genericResponseCallback);
    }


    private Callback<GenericResponse> generateResellerReferralCodeV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().contains("000")) {
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                        Referral referral = new Gson().fromJson(data,Referral.class);

                        String statusOfReferral =referral.getReferralPromo();
                        str_invite = referral.getInvitationMessage();
                        tv_message.setText(referral.getReferralDescription());
                        if (statusOfReferral.equals("ACTIVE")) {
                            tv_code.setText(referral.getReferralCode());
                            tv_code.setVisibility(View.VISIBLE);
                            lbl_code.setVisibility(View.VISIBLE);
                            btn_customize_code.setVisibility(View.VISIBLE);
                        } else {
                            tv_code.setVisibility(View.GONE);
                            lbl_code.setVisibility(View.GONE);
                            btn_customize_code.setVisibility(View.GONE);
                        }
                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
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
