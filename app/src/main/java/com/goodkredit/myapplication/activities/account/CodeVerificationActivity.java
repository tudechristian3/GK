package com.goodkredit.myapplication.activities.account;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.OtpModel.BorrowerOtp;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdk;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionEvent;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*
 * ALGO:
 * 1. On click of the button, get the code input and validate, then get the info from local db
 * 2. Send http request
 * 3. Verify session
 * 4. Process request result
 * 5. Open guarantor code page
 * */

public class CodeVerificationActivity extends BaseActivity implements FreenetSdkConnectionListener {

    //Declaration
    private DatabaseHandler db;
    private CommonFunctions cf;
    private CommonVariables cv;
    private Context mcontext;
    private EditText code;
    private String codeval = "";
    private String mobileval = "";
    private String imei = "";
    private String authentication = "";
    private String sessionid = "";
    private String authcode = "";

    private String passedfrom = "";
    private String passedprefix = "";
    private String passedmobile = "";

    private Button mBtnVerify;
    private RelativeLayout codeverificationlayout;

    //NEW VARIABLES FOR SECURITY UPDATE
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

//    private CodeVerificationSMSBroadcastListener smsBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);
        try {
            setupToolbar();
            //setting context
            mcontext = this;

            mBtnVerify = findViewById(R.id.signin);
            code = findViewById(R.id.code);
            codeverificationlayout = findViewById(R.id.codeverificationlayout);

            code.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            mBtnVerify.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_BOLD, mBtnVerify.getText().toString()));

            //Initialize db
            db = new DatabaseHandler(this);

            //get the parameter passed from prev activity
            Bundle b = getIntent().getExtras();
            passedfrom = b.getString("FROM");
            passedprefix = b.getString("PREFIX");
            passedmobile = b.getString("MOBILE");

            if (isFreeModeEnabled()) {
                FreenetSdk.registerConnectionListener(this);
                activateFreeNet();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (isFreeModeEnabled()) {
            deactivateFreeNet();
            FreenetSdk.unregisterConnectionListener(this);
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            if (isFreeModeEnabled()) {
                deactivateFreeNet();
                FreenetSdk.unregisterConnectionListener(this);
            }
            Intent intent = new Intent(this, SignUpActivity.class);
            intent.putExtra("FROM", passedfrom);
            intent.putExtra("PREFIX", passedprefix);
            intent.putExtra("MOBILE", passedmobile);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 1);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        return super.onOptionsItemSelected(item);
    }

    /****************
     * FUNCTIONS
     * -------------
     */
    public void openGuarantorVerification(View view) {
        showProgressDialog(getViewContext(),"OTP Verification","Verifying OTP.. Please wait.");
        try {
            //1.
            int status = CommonFunctions.getConnectivityStatus(this);
            if (status == 0) { //no connection
                hideProgressDialog();
                CommonFunctions.showSnackbar(getViewContext(),codeverificationlayout,"No internet connection.","WARNING");
            } else { //has connection proceed
                //2.
                codeval = code.getText().toString();
                if (codeval.length() == 6) {
                    //get data in local
                    Cursor cursor = db.getAccountInformation(db);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        do {
                            mobileval = cursor.getString(cursor.getColumnIndex("mobile"));
                            authentication = cursor.getString(cursor.getColumnIndex("authentication"));
                            imei = cursor.getString(cursor.getColumnIndex("imei"));
                        } while (cursor.moveToNext());
                        //3.
                        verifyOTP();
                    }
                    cursor.close();
                } else {
                    hideProgressDialog();
                    code.setError("Invalid Verification Code.");
                    code.requestFocus();
                }
            }
        } catch (Exception e) {
            hideProgressDialog();
            e.printStackTrace();
        }
    }


    public void verifySession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            CommonFunctions.showDialog(mcontext, "", "Verifying Code. Please wait ...", false);
            //new HttpAsyncTask().execute(CommonVariables.VERIFICATIONURL);
        } else {
            showNoInternetToast();
        }
    }

    @Override
    public void onConnectionChange(FreenetSdkConnectionEvent freenetSdkConnectionEvent) {
        Logger.debug("getStatus", "STATUS: " + String.valueOf(freenetSdkConnectionEvent.getStatus()));
        Logger.debug("getErrorDescription", "Error Desc: " + String.valueOf(freenetSdkConnectionEvent.getErrorDescription()));
        Logger.debug("getErrorCode", "Error Code: " + String.valueOf(freenetSdkConnectionEvent.getErrorCode()));
        Logger.debug("toString", "toString: " + String.valueOf(freenetSdkConnectionEvent.toString()));

        if (freenetSdkConnectionEvent.getStatus() == 1) {
        } else if (freenetSdkConnectionEvent.getStatus() == 0) {
        } else {
            switch (freenetSdkConnectionEvent.getErrorCode()) {
                case 4022:
                    showToast("Please check network configuration on your device.", GlobalToastEnum.WARNING);
                    break;
                case 4020:
                    showToast("Unsupported network settings on the device.", GlobalToastEnum.WARNING);
                    break;
                case 4023:
                    showToast("Android version of the device is not supported", GlobalToastEnum.WARNING);
                    break;
                case 4001:
                case 4002:
                case 4011:
                    showToast("Internal error occurred. (Params)", GlobalToastEnum.ERROR);
                    break;
                case 4012:
                    showToast("Internal error occurred. (Expired) ", GlobalToastEnum.ERROR);
                    break;
                case 4021:
                    showToast("Internal error occurred. (SDK_Exception) ", GlobalToastEnum.ERROR);
                    break;
            }
        }
    }

    //4.
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            try {
                //authcode = CommonFunctions.getSha1Hex(imei + sessionid);
                authcode = CommonFunctions.getSha1Hex(imei + codeval + mobileval + passedfrom);
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("code", codeval);
                jsonObject.accumulate("mobile", mobileval);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("auth", authentication);
                //jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("from", passedfrom);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                CommonFunctions.hideDialog();
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 4. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            CommonFunctions.hideDialog();

            try {
                String data = CommonFunctions.parseJSON(result, "data");
                String status = CommonFunctions.parseJSON(result, "status");
                String message = CommonFunctions.parseJSON(result, "message");

                if(status.equals("")){
                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                } else if(status.equals(null)){
                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                } else if (status.equals("000")){

                    //meaning validation of code is from registration (result = borrowerid)
                    String strborrowerid = CommonFunctions.parseJSON(data, "borrowerid");
                    sessionid = CommonFunctions.parseJSON(data, "sessionid");

                    PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_SESSIONID);
                    PreferenceUtils.saveSessionID(getViewContext(), sessionid);

                    PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_VERSION_CODE);
                    PreferenceUtils.saveGKVersionCodePreference(getViewContext(),
                            getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);

                    proceedPasswordSetting(strborrowerid);

                } else{
                    showToast(message, GlobalToastEnum.ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //5.
    public void proceedPasswordSetting(String borrowerid) {
        try {
            if (passedfrom.equals("FORGETPASSWORD")) {
                db.updateAccount(db, borrowerid, "PASSWORDSETUPFORGETPASSWORD", mobileval);
            } else {
                db.updateAccount(db, borrowerid, "PASSWORDSETUP", mobileval);
            }
            if (isFreeModeEnabled()) {
                deactivateFreeNet();
                FreenetSdk.unregisterConnectionListener(this);
            }

            PreferenceUtils.saveFromValue(getViewContext(),passedfrom);
            Intent intent = new Intent(this, SetupPasswordActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        //unregisterReceiver(smsBroadcastReceiver);
        super.onDestroy();
    }

    /**
    *SECURITY UPDATE
    *AS OF
    *October 2019
    **/
    private void verifyOTP() {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                String paramJson = "";
                String jsonString = "";

                LinkedHashMap indexAuthMapObject = null;

                //PARAMETERS
                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", mobileval);
                parameters.put("usertype", CommonVariables.usertype);
                parameters.put("devicetype", CommonVariables.devicetype);
                parameters.put("from", passedfrom);
                parameters.put("otp", codeval);


                //ENCRYPTION
                 indexAuthMapObject = CommonFunctions.getIndexAndAuthenticationID(parameters);
                 jsonString = new Gson().toJson(indexAuthMapObject, Map.class);

                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");
                parameters.put("index", index);

                paramJson = new Gson().toJson(parameters, Map.class);
                authenticationid = CommonFunctions.getSha1Hex(CommonFunctions.parseJSON(String.valueOf(jsonString), "authenticationid"));
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + "verifyMobileV3");

                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));
                getVerifyLoginV2(getVerifyLoginSessionV2);

            }else {
                hideProgressDialog();
                showNoInternetToast();
            }

    }

    private void getVerifyLoginV2 (Callback<GenericResponse> getVerifyLoginV2Callback) {

        Call<GenericResponse> getVerifyLoginObject = RetrofitBuilder.getAccountV2API(getViewContext())
                .verifyMobileV3(authenticationid,
                        param);

        getVerifyLoginObject.enqueue(getVerifyLoginV2Callback);
    }

    private final Callback<GenericResponse> getVerifyLoginSessionV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
             hideProgressDialog();
            String decryptedmessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
            if(errorBody == null){
                switch(response.body().getStatus()){
                    case "000":
                        String decrypteddata  = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        String strborrowerid = CommonFunctions.parseJSON(decrypteddata, "borrowerid");
                        sessionid = CommonFunctions.parseJSON(decrypteddata, "sessionid");

                        if (decrypteddata.equals("error") || decryptedmessage.equals("error")) {
                            showErrorGlobalDialogs();
                        }else{
                            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_SESSIONID);
                            PreferenceUtils.saveSessionID(getViewContext(), sessionid);
                            try{
                                    if(matchUserID(mobileval)){
                                        BorrowerOtp sponsorOTP = new BorrowerOtp(mobileval,codeval);
                                        Logger.debug("okhttp","==============================: USER ID MATCH - UPDATE BORROWER DATA "+new Gson().toJson(sponsorOTP));
                                        db.updateOTP(db,mobileval,codeval);

                                        //ENCRYPTION
                                        String privatekey = CommonFunctions.getSha1Hex(codeval);
                                        List<String> strSplitPrivateKey;

                                        assert privatekey != null;

                                        strSplitPrivateKey = CommonFunctions.splitOtpList(privatekey);
                                        PreferenceUtils.saveListPreference(getViewContext(), "sha1otp", strSplitPrivateKey);

                                    }else{
                                        Logger.debug("okhttp","==============================: USER ID NOT MATCH - INSERT TO DB");
                                        db.insertBorrowersOTP(db, new BorrowerOtp(mobileval,codeval));

                                        //ENCRYPTION
                                        String privatekey = CommonFunctions.getSha1Hex(codeval);
                                        List<String> strSplitPrivateKey;

                                        assert privatekey != null;

                                        strSplitPrivateKey = CommonFunctions.splitOtpList(privatekey);
                                        PreferenceUtils.saveListPreference(getViewContext(), "sha1otp", strSplitPrivateKey);

                                    }
                                proceedPasswordSetting(strborrowerid);

                                }catch (SQLiteException e){
                                 e.printStackTrace();
                                }
                        }
                        break;

                    default:

                        if(response.body().getStatus().equals("error") || decryptedmessage.equals("error")) {
                            showErrorToast("Something went wrong. Please try again.");
                        }else if (response.body().getStatus().equals("003")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        }else {
                            showErrorToast(decryptedmessage);
                        }
                        break;
                }
            } else {
                hideProgressDialog();
                showErrorToast(decryptedmessage);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            t.printStackTrace();
            showErrorToast("Oops! We failed to connect to the service. Please check your internet connection and try again.");
        }
    };

    public boolean matchUserID(String username) {
        Logger.debug("okhttp","MATCH USER ID : "+username);
        boolean ismatch =false;
        Cursor cursor = db.getData(db,username);
        Logger.debug("okhttp","CURSOR COUNT: "+cursor.getCount());
        if (cursor.getCount() > 0) {
            ismatch = true;
        }
        return ismatch;
    }
}
