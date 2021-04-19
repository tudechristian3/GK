package com.goodkredit.myapplication.activities.account;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.settings.TermsAndConditions;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.gkapplication.ReCaptchaResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdk;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionEvent;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*
 * ALGO:
 * 1. Check internet connection
 * 2. On click, get all input and validate
 * 3. Post URL- send data to rest api
 * 4. If result is corrent, save data in local database
 * */
public class SignUpActivity extends BaseActivity implements FreenetSdkConnectionListener {

    //Declaration

    private DatabaseHandler db;
    private CommonFunctions cf;
    private CommonVariables cv;
    private Context mcontext;
    private EditText country;
    private EditText mobile;
    private EditText countrycode;
    private EditText code;
    private Button signin;
    private TextView loginlink;
    private TextView forgetpassnote;

    private String countryval = "";
    private String countrycodeval = "";
    private String mobileval = "";
    private String sessionidval = "";
    private String imei = "";
    private static final int PERMISSION_READ_STATE = 1;

    private String passedfrom = "";
    private String passedmobile = "";
    private String passedprefix = "";

    // Progress Dialog
    ProgressDialog pDialog;


    //NEW VARIABLES FOR SECURITY UPDATE
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupToolbar();

        try {

            V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, findViewById(R.id.mainLayout));

            mcontext = this;
            //Initialize database
            db = new DatabaseHandler(this);
            //get country code and name
            String currentcountry = GetCountryZipCode();

            imei = CommonFunctions.getImei(getViewContext());
            // String countryCodeValue = tm.getNetworkCountryIso();

            country = findViewById(R.id.country);
            countrycode = findViewById(R.id.countrycode);
            mobile = findViewById(R.id.mobileno);
            code = findViewById(R.id.code);
            signin = findViewById(R.id.signin);
            loginlink = findViewById(R.id.loginlink);
            forgetpassnote = findViewById(R.id.forgetpassnote);

            country.setText("Philippines");
            code.setText("+63");
            countrycode.setText("63");

            signin.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_BOLD, signin.getText().toString()));
            loginlink.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_BOLD, loginlink.getText().toString()));

            //get the passed parameter from prev activity
            Bundle b = getIntent().getExtras();
            passedfrom = PreferenceUtils.getFromValue(getViewContext());
            passedprefix = b.getString("PREFIX");
            passedmobile = b.getString("MOBILE");

            //if its from forget password
            if (passedfrom.equals("FORGETPASSWORD")) {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Forgot Password");
                processForgetPassword();
            }

            //load local if it go back from the code verification
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                String countryvalue = "";
                do {
                    mobileval = cursor.getString(cursor.getColumnIndex("mobile"));
                    countryvalue = cursor.getString(cursor.getColumnIndex("country"));
                } while (cursor.moveToNext());

                if (mobileval != null && !mobileval.equals("")) {
                    String curcountryval = getCountryCodeHasCountry(countryvalue);
                    mobile.setText(mobileval.replace(countrycodeval, ""));
                    country.setText(curcountryval);
                    code.setText("+" + countrycodeval);
                    countrycode.setText(countrycodeval);
                }
            }
            cursor.close();

            if (isFreeModeEnabled()) {
                log("signup reg act");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            try {
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(this);
                }
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // Check which request we're responding to
            if (requestCode == 1) {
                if (resultCode == Activity.RESULT_OK) {
                    countryval = data.getStringExtra("Country");
                    countrycodeval = data.getStringExtra("CountryPrefix");
                    country.setText(countryval);
                    countrycode.setText(countrycodeval);
                    if (!countrycodeval.equals("")) {
                        code.setText("+" + countrycodeval);
                    }
                }
            } else if (requestCode == 405) {
                Logger.debug(TAG, "onActivityRes: " + String.valueOf(resultCode));
                if (resultCode == RESULT_OK) {
                    db.insertAccountInfoTeeCee(db, "1");
                    if (isFreeModeEnabled()) {
                        deactivateFreeNet();
                        FreenetSdk.unregisterConnectionListener(this);
                    }
                    Intent intent = new Intent(this, CodeVerificationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("FROM", passedfrom);
                    intent.putExtra("PREFIX", countrycodeval);
                    intent.putExtra("MOBILE", mobileval);
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        try {
            switch (requestCode) {
                case PERMISSION_READ_STATE: {
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            imei = tm.getImei();
                        } else {
                            imei = tm.getDeviceId();
                        }
                    } else {
                        imei = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    }
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /****************
     * FUNCTIONS
     **************/

    private void processForgetPassword() {
        forgetpassnote.setVisibility(View.VISIBLE);
        signin.setText("VERIFY MOBILE NUMBER");
        loginlink.setVisibility(View.GONE);

        if (passedmobile != null && !passedmobile.trim().equals("")) {
            mobile.setText(passedmobile);
        }

        if (passedprefix != null && passedprefix.equals("")) {
            String countryv = getCountry(passedprefix);
            country.setText(countryv);
            code.setText("+" + countrycodeval);
            countrycode.setText(countrycodeval);
        }
    }

    public void gotoSignIn(View view) {
        try {
            if (isFreeModeEnabled()) {
                deactivateFreeNet();
                FreenetSdk.unregisterConnectionListener(this);
            }
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openCountryList(View view) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openVerificationCodePage(View view) {
        //1.
        int status = CommonFunctions.getConnectivityStatus(this);
        if (status == 0) { //no connection
            showToast("You have no internet connection. Please check.", GlobalToastEnum.NOTICE);
        } else { //has connection proceed
            if(mobile.getText().length() < 10){
                mobile.setError("Invalid mobile number.");
            }else{
                getReCaptchaToken();
            }

        }
    }

    private void getReCaptchaToken() {
        SafetyNet.getClient(this).verifyWithRecaptcha(getString(R.string.str_sitekey))
                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                        if (!response.getTokenResult().isEmpty()) {
                            verifyReCaptchaToken(response.getTokenResult());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            Logger.debug(TAG, "Error message: " +
                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                        } else {
                            Logger.debug(TAG, "Unknown type of error: " + e.getMessage());
                        }
                    }
                });
    }

    private void verifyReCaptchaToken(String token) {
        showProgressDialog(getViewContext(), "", "Please wait...");
        Call<ReCaptchaResponse> call = RetrofitBuild.getReCaptchaAPIService(getViewContext())
                .verifyReCaptchaToken(
                        getString(R.string.str_secretkey),
                        token
                );

        call.enqueue(verifyReCaptchaTokenCallback);
    }

    private Callback<ReCaptchaResponse> verifyReCaptchaTokenCallback = new Callback<ReCaptchaResponse>() {
        @Override
        public void onResponse(Call<ReCaptchaResponse> call, Response<ReCaptchaResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().isSuccess()) {
                        try {
                            //2
                            mobile = findViewById(R.id.mobileno);
                            countrycode = findViewById(R.id.countrycode);

                            mobileval = mobile.getText().toString();
                            countrycodeval = countrycode.getText().toString();

                            //Validate Inputs
                            if (mobileval.trim().equals("") || countrycodeval.trim().equals("")) {
                                showToast("All fields are mandatory.", GlobalToastEnum.WARNING);
                            } else {

                                //this is to remove 0 when the user input zero before prefix
                                if (countrycodeval.equals("63")) {
                                    String first = mobileval.trim().substring(0, 1);
                                    if (first.equals("0")) {
                                        mobileval = mobileval.substring(1);
                                    }
                                }
                                if (passedfrom.equals("FORGETPASSWORD")) {
                                    verifySession();
                                } else {
                                    confirmMobilenNew();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        showWarningToast("It seems that your connection is slow or an authentication failure occured. Please try again.");
                    }
                } else {
                    showWarningToast("It seems that your connection is slow or an authentication failure occured. Please try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ReCaptchaResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };


    private boolean termsAccepted = false;

    private void confirmMobile() {
        try {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_REGULAR, "Phone Number Verification"));
            alertDialog.setMessage(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_REGULAR, "We will be verifying your phone number \n\n +" + countrycodeval + mobileval + " \n\n Is this OK or would you like to edit the number?"));
            alertDialog.setPositiveButton(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_REGULAR, "OK"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    verifySession();
                }
            });

            alertDialog.setNegativeButton(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_REGULAR, "EDIT"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmMobilenNew() {
        try {
            GlobalDialogs dialog = new GlobalDialogs(getViewContext());

            dialog.createDialog("Phone Number Verification", "We will be verifying your phone number" +
                            "\n\n +" + countrycodeval + mobileval + " \n\n Is this OK or would you like to edit the number?",
                    "EDIT", "OK", ButtonTypeEnum.DOUBLE,
                    false, false, DialogTypeEnum.NOTICE);

            View closebtn = dialog.btnCloseImage();
            closebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            View btndoubleone = dialog.btnDoubleOne();
            btndoubleone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            View btndoubletwo = dialog.btnDoubleTwo();
            btndoubletwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    verifySession();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }


    private void verifySession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            CommonFunctions.showDialog(mcontext, "", "Processing, please wait ...", false);

            imei = CommonFunctions.checkImeiIfNull(imei, mobileval);

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("mobile", mobileval);
            parameters.put("countrycode", countrycodeval);
            parameters.put("imei", imei);
            parameters.put("from", passedfrom);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthenticationID(parameters);

            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");
            parameters.put("index", index);

            String paramJson = new Gson().toJson(parameters, Map.class);

            authenticationid = CommonFunctions.getSha1Hex(CommonFunctions.parseJSON(jsonString, "authenticationid"));

            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + "partialRegisterV3");

            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));
            partialRegisterV2(partialRegisterV2Session);

        } else {
            CommonFunctions.hideDialog();
            showNoInternetToast();
        }
    }

    private void partialRegisterV2(Callback<GenericResponse> partialSignUpV2Callback) {
        Call<GenericResponse> partialregisterv2 = RetrofitBuilder.getAccountV2API(getViewContext())
                .partialRegisterV3(authenticationid, param);
        partialregisterv2.enqueue(partialSignUpV2Callback);
    }

    private final Callback<GenericResponse> partialRegisterV2Session = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            CommonFunctions.hideDialog();

            String message = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    proceedVerification(authenticationid);
                    Logger.debug("okhttp", "============ PARTIAL REGISTER DATA : " + data);

                } else {
                    showToast(message, GlobalToastEnum.WARNING);
                }
            } else {
                assert message != null;
                showToast(message, GlobalToastEnum.WARNING);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            showNoInternetToast();
        }
    };

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


    //3.
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";

            try {
                //String authcode = CommonFunctions.getSha1Hex(imei + sessionidval);
                String authcode = CommonFunctions.getSha1Hex(imei + mobileval + countrycodeval + passedfrom);
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("mobile", mobileval);
                jsonObject.accumulate("countrycode", countrycodeval);
                jsonObject.accumulate("imei", imei);
                //jsonObject.accumulate("sessionid", sessionidval);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("from", passedfrom);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                CommonFunctions.hideDialog();
            }


            return CommonFunctions.POST(urls[0], json); //function is in the common function class
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {
                String data = CommonFunctions.parseJSON(result, "data");
                String status = CommonFunctions.parseJSON(result, "status");
                String message = CommonFunctions.parseJSON(result, "message");

                CommonFunctions.hideDialog();

                if (status == null || status.equals("")) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (status.equals("001")) {
                    showToast("Invalid Entry.", GlobalToastEnum.ERROR);
                } else if (status.equals("002")) {
                    showToast("Invalid Session.", GlobalToastEnum.ERROR);
                } else if (status.equals("003")) {
                    showToast("This account already exists.", GlobalToastEnum.ERROR);
                } else if (status.equals("004")) {
                    showToast("Invalid Authentication.", GlobalToastEnum.ERROR);
                } else if (status.equals("error")) {
                    showToast("Something wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
                } else if (status.equals("005")) {
                    showToast("This account does not exist.", GlobalToastEnum.ERROR);
                } else if (status.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    proceedVerification(result);
                }
            } catch (Exception e) {
                CommonFunctions.hideDialog();
                showToast("Something wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
            }
        }
    }

    private void proceedVerification(String result) {
        try {
            String stat = "";
            countryval = country.getText().toString();
            if (passedfrom.equals("FORGETPASSWORD")) {
                stat = "FORVERIFICATIONFORGETPASSWORD";
            } else {
                stat = "FORVERIFICATION";
            }

            imei = CommonFunctions.checkImeiIfNull(imei, mobileval);
            db.insertAccountInfoInLocal(db, mobileval, countrycodeval, countryval, result, imei, stat);

            if (!passedfrom.equals("FORGETPASSWORD")) {
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(SignUpActivity.this);
                }
                Intent intent = new Intent(getApplicationContext(), TermsAndConditions.class);
                intent.putExtra("FROM", passedfrom);
                intent.putExtra("PREFIX", countrycodeval);
                intent.putExtra("MOBILE", mobileval);
                startActivityForResult(intent, 405);
            } else {
                db.insertAccountInfoTeeCee(db, "1");
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(SignUpActivity.this);
                }

                Intent intent = new Intent(SignUpActivity.this, CodeVerificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("FROM", passedfrom);
                intent.putExtra("PREFIX", countrycodeval);
                intent.putExtra("MOBILE", mobileval);

                startActivity(intent);
            }
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String GetCountryZipCode() {
        String CountryID = "";
        String CountryZipCode = "";
        try {
            TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            //getNetworkCountryIso
            CountryID = manager.getSimCountryIso().toUpperCase();
            String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
            for (int i = 0; i < rl.length; i++) {
                String[] g = rl[i].split(",");
                if (g[1].trim().equals(CountryID.trim())) {
                    countrycodeval = g[0];
                    countryval = g[2];
                    CountryZipCode = countryval;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CountryZipCode;
    }

    //get countrycode based on the country
    private String getCountryCodeHasCountry(String country) {
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[2].trim().equals(country.trim())) {
                countrycodeval = g[0];
                countryval = g[2];
                break;
            }
        }
        return countryval;
    }

    private String getCountry(String countryprefix) {
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(countryprefix.trim())) {
                countrycodeval = g[0];
                countryval = g[2];
                break;
            }
        }
        return countryval;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*|************************SECURITY UPDATE AS OF OCTOBER,2019***********************************************|*/


}
