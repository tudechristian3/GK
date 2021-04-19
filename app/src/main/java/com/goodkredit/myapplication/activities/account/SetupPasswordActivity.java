package com.goodkredit.myapplication.activities.account;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
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
public class SetupPasswordActivity extends BaseActivity implements FreenetSdkConnectionListener {

    //Declaration
    private DatabaseHandler db;
    private CommonFunctions cf;
    private CommonVariables cv;
    private Context mcontext;

    private EditText password;
    private EditText confirmpassword;
    private EditText fistname;
    private EditText lastname;
    private TextView passwordmesage;
    private Button signin;

    private String passwordval = "";
    private String confirmpasswordval = "";
    private String firstnameval = "";
    private String lastnameval = "";
    private String mobileval = "";
    private String imei = "";
    private String sessionid = "";
    private String borrowerid = "";
    private String userid = "";
    private String passedfrom = "";
    private String request = "";


    //NEW VARIABLES FOR SECURITY
    private String param;
    private String keyEncryption;
    private String authenticationid;
    private String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_set_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            //setting context
            mcontext = this;

            //Initialize db
            db = new DatabaseHandler(this);

            //initialize elements
            passwordmesage = findViewById(R.id.passwordmesage);
            signin = findViewById(R.id.signin);
            password = findViewById(R.id.password);
            confirmpassword = findViewById(R.id.confirmpassword);
            fistname = findViewById(R.id.firstname);
            lastname = findViewById(R.id.lastname);

            //get the passed parameter from sharedPREF
            passedfrom =PreferenceUtils.getFromValue(getViewContext());
            request = "";

            //check if its from forget password
            if (passedfrom.equals("FORGETPASSWORD")) {
                signin.setText("CHANGE PASSWORD");
                password.setHint("New Password");
                confirmpassword.setHint("Confirm New Password");
                passwordmesage.setText("Enter your new account password. Your new password will be used to login to another device.");
                fistname.setVisibility(View.GONE);
                lastname.setVisibility(View.GONE);

            }

            sessionid = PreferenceUtils.getSessionID(getViewContext());
            //get account information
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                } while (cursor.moveToNext());
            }
            cursor.close();

            if (isFreeModeEnabled()) {
                FreenetSdk.registerConnectionListener(this);
                activateFreeNet();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                } while (cursor.moveToNext());

            }
            cursor.close();


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
    /***********************
     * FUNCTIONS
     **********************/
    public void procceedPasswordSetUp(View view) {
        showProgressDialog(getViewContext(),"","Processing your request.. Please wait.");
        try {
            //1.
            int status = CommonFunctions.getConnectivityStatus(this);
            if (status == 0) { //no connection
                hideProgressDialog();
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else { //has connection proceed

                //2.
                passwordval = password.getText().toString();
                confirmpasswordval = confirmpassword.getText().toString();
                firstnameval = fistname.getText().toString();
                lastnameval = lastname.getText().toString();

                if(!passedfrom.equals("FORGETPASSWORD")){
                    if(firstnameval.isEmpty()||lastnameval.isEmpty() || passwordval.isEmpty() || confirmpasswordval.isEmpty()){
                        hideProgressDialog();
                        showToast("All fields are mandatory", GlobalToastEnum.WARNING);
                    }else{

                        if (passwordval.equals(confirmpasswordval)) {
                            Cursor cursor = db.getAccountInformation(db);
                            if (cursor.getCount() > 0) {
                                cursor.moveToFirst();
                                do {
                                    mobileval = cursor.getString(cursor.getColumnIndex("mobile"));
                                    borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                                } while (cursor.moveToNext());
                            }
                            cursor.close();
                            //3.
                            Logger.debug("OKHTTTTPPPP", "FROM : " + passedfrom);
                            verifySession(passedfrom);

                        } else {
                            hideProgressDialog();
                            showToast("Password did not match.", GlobalToastEnum.WARNING);
                        }
                    }
                }else{
                    if(passwordval.isEmpty() || confirmpasswordval.isEmpty()){
                        hideProgressDialog();
                        showToast("All fields are mandatory", GlobalToastEnum.WARNING);
                    }else{
                        if (passwordval.equals(confirmpasswordval)) {
                            Cursor cursor = db.getAccountInformation(db);
                            if (cursor.getCount() > 0) {
                                cursor.moveToFirst();
                                do {
                                    mobileval = cursor.getString(cursor.getColumnIndex("mobile"));
                                    borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                                } while (cursor.moveToNext());
                            }
                            cursor.close();
                            //3.
                            Logger.debug("OKHTTTTPPPP", "FROM : " + passedfrom);
                            verifySession(passedfrom);

                        } else {
                            hideProgressDialog();
                            showToast("Password did not match.", GlobalToastEnum.WARNING);
                        }
                    }
                }

            }
        } catch (Exception e) {
            hideProgressDialog();
            e.printStackTrace();
        }
    }


    private void verifySession(final String process) {
        if (CommonFunctions.getConnectivityStatus(getApplicationContext()) > 0) {
            if (process.equals(".")) {
                setUpPassword(process);
            } else {
                setUpPassword(process);
            }

        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    @Override
    public void onConnectionChange(FreenetSdkConnectionEvent freenetSdkConnectionEvent) {
        Logger.debug("getStatus", "STATUS: " + freenetSdkConnectionEvent.getStatus());
        Logger.debug("getErrorDescription", "Error Desc: " + freenetSdkConnectionEvent.getErrorDescription());
        Logger.debug("getErrorCode", "Error Code: " + freenetSdkConnectionEvent.getErrorCode());
        Logger.debug("toString", "toString: " + freenetSdkConnectionEvent.toString());

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
                String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("password", passwordval);
                jsonObject.accumulate("mobile", mobileval);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("from", passedfrom);
                jsonObject.accumulate("firstname", firstnameval.toUpperCase());
                jsonObject.accumulate("lastname", lastnameval.toUpperCase());

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
                if (result == null) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("001")) {
                    showToast("Invalid Entry.", GlobalToastEnum.ERROR);
                } else if (result.equals("002")) {
                    showToast("Invalid Session.", GlobalToastEnum.ERROR);
                } else if (result.equals("003")) {
                    showToast("Invalid Authentication.", GlobalToastEnum.ERROR);
                } else if (result.equals("error")) {
                    showToast("Something wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    //check if its from forget password
                    if (passedfrom.equals("FORGETPASSWORD")) {
                        // request = "FORGETPASSWORD";
                        verifySession("FORGETPASSWORD");
                    } else {
                        proceedStart();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //5.
    public void proceedStart() {
        try {

            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_BORROWER_ID);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_USER_ID);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_IMEI);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_BORROWER_NAME);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_SESSIONID);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_VERSION_CODE);
            PreferenceUtils.removePreference(getViewContext(),PreferenceUtils.KEY_FROM);


            PreferenceUtils.saveBorrowerID(getViewContext(), borrowerid);
            PreferenceUtils.saveUserID(getViewContext(), mobileval);
            PreferenceUtils.saveImeiID(getViewContext(), imei);
            PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_BORROWER_NAME, firstnameval + " " + lastnameval);
            PreferenceUtils.saveSessionID(getViewContext(), sessionid);
            PreferenceUtils.saveGKVersionCodePreference(getViewContext(),
                    getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);


            CommonVariables.VOUCHERISFIRSTLOAD = true;

            db.updateRegistrationStatus(db, ".", borrowerid);
            db.updateProfile(db, mobileval, firstnameval, ".", lastnameval, ".", ".", ".", ".", ".", ".", ".");
            if (isFreeModeEnabled()) {
                deactivateFreeNet();
                FreenetSdk.unregisterConnectionListener(this);
            }
            //createSession(getSessionCallback);
            getSession();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";

            try {
                String authcode = CommonFunctions.getSha1Hex(imei + passwordval + mobileval);

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("username", mobileval);
                jsonObject.accumulate("password", passwordval);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("type", "FORGETPASSWORD");

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                CommonFunctions.hideDialog();
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 3. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            CommonFunctions.hideDialog();
            try {

                String data = CommonFunctions.parseJSON(result, "data");
                String status = CommonFunctions.parseJSON(result, "status");
                String message = CommonFunctions.parseJSON(result, "message");

                if (status == null) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (status.equals("001")) {
                    showToast("Invalid Entry. Please check..", GlobalToastEnum.ERROR);
                } else if (status.equals("003")) {
                    showToast("Username or password is invalid. Please check.", GlobalToastEnum.ERROR);
                } else if (status.equals("002")) {
                    showToast("Invalid Session.", GlobalToastEnum.ERROR);
                } else if (status.equals("error")) {
                    showToast("Something went wrong with your connection. Please check..", GlobalToastEnum.NOTICE);
                } else if (status.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    String profile = CommonFunctions.parseJSON(data, "profile");
                    sessionid = CommonFunctions.parseJSON(data, "sessionid");
                    proceedMainPageV2(profile);
                }
            } catch (Exception e) {
                showToast("Error.", GlobalToastEnum.ERROR);
            }
        }
    }

    private void proceedMainPageV2(String profilejson) {

            String borrowerid = CommonFunctions.parseJSON(profilejson,"BorrowerID");
            String firstname =CommonFunctions.parseJSON(profilejson,"FirstName");
            String middlename = CommonFunctions.parseJSON(profilejson,"MiddleName");
            String lastname = CommonFunctions.parseJSON(profilejson,"LastName");
            String birthdate = CommonFunctions.parseJSON(profilejson,"BirthDate");
            String gender = CommonFunctions.parseJSON(profilejson,"Gender");
            String occupation = CommonFunctions.parseJSON(profilejson,"Occupation");
            String email = CommonFunctions.parseJSON(profilejson,"EmailAddress");
            String mobilenumber = CommonFunctions.parseJSON(profilejson,"MobileNo");
            String streetaddress = CommonFunctions.parseJSON(profilejson,"StreetAddress");
            String city = CommonFunctions.parseJSON(profilejson,"City");
            String dateregistered = CommonFunctions.parseJSON(profilejson,"DateRegistration");
            String country = CommonFunctions.parseJSON(profilejson,"Country");
            String guarantorid = CommonFunctions.parseJSON(profilejson,"GuarantorID");
            String profilepic = CommonFunctions.parseJSON(profilejson,"ProfilePicURL");
            String borrowertype = CommonFunctions.parseJSON(profilejson,"BorrowerType");
            String guarantorregistrationstatus = CommonFunctions.parseJSON(profilejson,"Extra1");

            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_BORROWER_ID);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_USER_ID);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_IMEI);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_BORROWER_NAME);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_SESSIONID);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_VERSION_CODE);
            PreferenceUtils.removePreference(getViewContext(),PreferenceUtils.KEY_FROM);

            PreferenceUtils.saveBorrowerID(getViewContext(), borrowerid);
            PreferenceUtils.saveUserID(getViewContext(), mobilenumber);
            PreferenceUtils.saveImeiID(getViewContext(), imei);
            PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_BORROWER_NAME, firstname + " " + lastname);
            PreferenceUtils.saveSessionID(getViewContext(), sessionid);
        try {
            PreferenceUtils.saveGKVersionCodePreference(getViewContext(),
                    getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //save to local database
            db.insertAccountDetails(db, borrowerid, mobilenumber, firstname, middlename, lastname, email, streetaddress, city, dateregistered, country, guarantorid, gender, profilepic, guarantorregistrationstatus, imei);

            if (firstname.equals(".") && lastname.equals(".")) {
                db.updateAccount(db, borrowerid, "GUARANTORSETUP", mobilenumber);
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(this);
                }
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(this);
                }
                Intent intent = new Intent(this, GuarantorVerificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else {
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(this);
                }
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                Intent intent = new Intent(getViewContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

    }

    private Callback<String> getSessionCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String responseData = response.body();
                    if (!responseData.isEmpty()) {
                        if (responseData.equals("001") || responseData.equals("002")) {
                            hideProgressDialog();
                            showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
                        } else if (responseData.equals("error")) {
                            hideProgressDialog();
                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                        } else if (responseData.contains("<!DOCTYPE html>")) {
                            hideProgressDialog();
                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                        } else {
                            sessionid = responseData;
                            validateReferralIfAvailableV2();
                        }
                    } else {
                        hideProgressDialog();
                        showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            hideProgressDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getApplicationContext(), "", "Please wait...");
            validateReferralIfAvailableV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    /*|************************SECURITY UPDATE AS OF OCT 14,2019***********************************************|*/
    private void setUpPassword(String process) {
        try {
            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                if (process.equals(".") || process.equals("FORGETPASSWORD")) {

                    LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

                    if (process.equals(".")) {

                        parameters.put("password", passwordval);
                        parameters.put("mobile", mobileval);
                        parameters.put("imei", imei);
                        parameters.put("borrowerid", borrowerid);
                        parameters.put("userid", mobileval);
                        parameters.put("from", passedfrom);
                        parameters.put("firstname", firstnameval.toUpperCase());
                        parameters.put("lastname", lastnameval.toUpperCase());
                        parameters.put("devicetype",CommonVariables.devicetype);

                    } else if (process.equals("FORGETPASSWORD")) {
                        parameters.put("password", passwordval);
                        parameters.put("mobile", mobileval);
                        parameters.put("imei", imei);
                        parameters.put("borrowerid", borrowerid);
                        parameters.put("userid", mobileval);
                        parameters.put("from", passedfrom);
                        parameters.put("devicetype",CommonVariables.devicetype);

                    }

                    LinkedHashMap indexAuthMapObject;
                    //ENCRYPTION
                    indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSessioNoKEY(parameters,sessionid);
                    String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);

                    index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");
                    parameters.put("index", index);

                    String paramJson = new Gson().toJson(parameters, Map.class);
                    authenticationid =CommonFunctions.parseJSON(String.valueOf(jsonString), "authenticationid");
                    keyEncryption = CommonFunctions.getSha1Hex(authenticationid  +sessionid+ "setupPasswordV3");
                    param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                    setUpPasswordV2(setUpPasswordSessionV2);

                } else {
                    LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                    parameters.put("imei", imei);
                    parameters.put("userid", mobileval);
                    parameters.put("password", passwordval);
                    parameters.put("devicetype", CommonVariables.devicetype);
                    parameters.put("type", "LOGIN");
                    parameters.put("isalwayssignin","0");

                    LinkedHashMap indexAuthMapObject;
                    indexAuthMapObject = CommonFunctions.getIndexAndAuthenticationID(parameters);
                    String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                    index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");
                    parameters.put("index", index);

                    final String paramJson = new Gson().toJson(parameters, Map.class);

                    authenticationid = CommonFunctions.getSha1Hex(CommonFunctions.parseJSON(String.valueOf(jsonString), "authenticationid"));
                    keyEncryption = CommonFunctions.getSha1Hex(authenticationid + "loginV2");
                    param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));
                    getSigninObjectV2(getVerifyLoginSessionV2);

                }
            }else{
                hideProgressDialog();
                showNoInternetToast();
            }
        } catch (Exception e) {
            hideProgressDialog();
            e.printStackTrace();
        }
    }

    private void validateReferralIfAvailable() {
        Call<GenericResponse> call = RetrofitBuild.getReferralAPIService(getViewContext())
                .validateReferralIfAvailable(
                        imei,
                        userid,
                        sessionid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        "ANDROID"
                );

        call.enqueue(validateReferralIfAvailableCallback);
    }

    private Callback<GenericResponse> validateReferralIfAvailableCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        ReferralCodeActivity.start(getViewContext(), firstnameval.toUpperCase() + " " + lastnameval.toUpperCase());
                    } else {
                        Intent intent = new Intent(getApplicationContext(), WelcomePageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("OTHERS", "");
                        intent.putExtra("SUBJECT", "");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

    //for setup password
    private void setUpPasswordV2(Callback<GenericResponse> getSigninObjectCallback) {

        Call<GenericResponse> getSigninObject = RetrofitBuilder.getAccountV2API(getViewContext())
                .setupPasswordV2(authenticationid,
                        sessionid,
                        param);

        getSigninObject.enqueue(getSigninObjectCallback);
    }

    private final Callback<GenericResponse> setUpPasswordSessionV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();
            String decryptedmessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
            if (errorBody == null) {
                assert response.body() != null;
                switch (response.body().getStatus()) {
                    case "000":
                        if (passedfrom.equals("FORGETPASSWORD")) {
                            verifySession("");
                        } else {
                            proceedStart();
                        }
                        break;
                    default:
                        if (response.body().getStatus().equals("error")) {
                            showErrorToast("Something went wrong. Please try again.");
                        } else {
                            showErrorToast(decryptedmessage);
                        }
                        break;
                }
            } else {
                assert response.body() != null;
                showErrorToast(decryptedmessage);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorToast("Oops! We failed to connect to the service. Please check your internet connection and try again.");
        }
    };


    private void getSigninObjectV2(Callback<GenericResponse> getSigninObjectCallback) {

        Call<GenericResponse> getSigninObject = RetrofitBuilder.getAccountV2API(getViewContext())
                .loginV2(authenticationid,
                        param);

        getSigninObject.enqueue(getSigninObjectCallback);
    }

    private final Callback<GenericResponse> getVerifyLoginSessionV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    String decryptedmessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                    if (response.body().getStatus().equals("000")) {
                        String decrypteddata = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decrypteddata.equals("error") || decryptedmessage.equals("error")) {
                            showErrorGlobalDialogs();
                        } else {

                            PreferenceUtils.removePreference(getViewContext(),"sha1otp");

                            String profile = CommonFunctions.parseJSON(decrypteddata, "profile");
                            sessionid = CommonFunctions.parseJSON(decrypteddata, "sessionid");

                            String privatekey = CommonFunctions.getSha1Hex(CommonFunctions.parseJSON(profile,"RecentOTP"));
                            List<String> strSplitPrivateKey;

                            Logger.debug("okhttp","OTP: "+CommonFunctions.parseJSON(profile,"RecentOTP"));

                            strSplitPrivateKey = CommonFunctions.splitOtpList(privatekey);
                            PreferenceUtils.saveListPreference(getViewContext(), "sha1otp", strSplitPrivateKey);

                            proceedMainPageV2(profile);
                        }
                    } else {
                        showErrorToast(decryptedmessage);
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorToast("Oops! We failed to connect to the service. Please check your internet connection and try again.");
        }
    };

    //validateReferralIfAvailable with Security
    private void validateReferralIfAvailableV2() {
        try {
            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                //PARAMETERS
                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", mobileval);
                parameters.put("borrowerid", borrowerid);
                parameters.put("devicetype", CommonVariables.devicetype);
                parameters.put("promoid", "REFERRAL");

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(jsonString, "index");
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                parameters.put("index", index);

                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                //AUTHENTICATIONID
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "validateReferralIfAvailable");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                validateReferralIfAvailableV2(validateReferralIfAvailableSessionV2);

            } else {
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void validateReferralIfAvailableV2(Callback<GenericResponse> validateReferralIfAvailableCallback) {

        Call<GenericResponse> validateReferralIfAvailable = RetrofitBuilder.getAccountV2API(getApplicationContext())
                .validateReferralIfAvailableV2Callback(authenticationid,
                        sessionid,
                        param);

        validateReferralIfAvailable.enqueue(validateReferralIfAvailableCallback);
    }

    private final Callback<GenericResponse> validateReferralIfAvailableSessionV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            CommonFunctions.hideDialog();
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedmessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                if (response.body().getStatus().equals("000")) {
                    String decrypteddata = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    if (decrypteddata.equals("error") || decryptedmessage.equals("error")) {
                        showErrorGlobalDialogs();
                    } else {
                        ReferralCodeActivity.start(getViewContext(), firstnameval.toUpperCase() + " " + lastnameval.toUpperCase());
                    }
                } else if (response.body().getStatus().equals("201")) {
                    Intent intent = new Intent(getApplicationContext(), WelcomePageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("OTHERS", "");
                    intent.putExtra("SUBJECT", "");
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorGlobalDialogs();
                    } else {
                        showErrorGlobalDialogs(decryptedmessage);
                    }
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showNoInternetToast();
        }
    };


}
