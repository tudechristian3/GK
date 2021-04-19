package com.goodkredit.myapplication.activities.account;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.profile.SubscriberProfileActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.CreateSession;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.SubscribersSponsorRequestStatus;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.profile.GetSubscribersSponsorRequestStatusResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

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
 * 3. Process request result
 * 4. Open Profiling for guarantor information
 * */
public class GuarantorVerificationActivity extends BaseActivity implements FreenetSdkConnectionListener {
    //Declaration
    private DatabaseHandler db;
    private CommonFunctions cf;
    private CommonVariables cv;
    private Context mcontex;
    private EditText guarantorcode;
    private String guarantorcodeval = "";
    private String mobileval = "";
    private String imei = "";
    private String sessionidval = "";
    private String userid = "";
    private String guarantorname;
    private String guarantoremail;
    private String issubguarantor = "0";
    private String previousactivity = "";

    private String guarantorStatus;
    private String guarantorID;

    private TextView sponsor_code_note;

    //NEW VARIABLES FOR SECURITY
    private String param;
    private String keyEncryption;
    private String authenticationid;
    private String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarantor_verification);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            //setting context
            mcontex = this;

            //intialize database
            db = new DatabaseHandler(this);

            //get the passed value
            Bundle b = getIntent().getExtras();
            if (b != null) {
                previousactivity = b.getString("FROM");
            }
            Logger.debug("okhttp","FROM PREVIEOUS: "+previousactivity );

            //get account information
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    guarantorStatus = cursor.getString(cursor.getColumnIndex("guarantorregistrationstatus"));
                    guarantorID = cursor.getString(cursor.getColumnIndex("guarantorid"));
                } while (cursor.moveToNext());
            }
            cursor.close();

            sessionidval = PreferenceUtils.getSessionID(getViewContext());

            guarantorcode = findViewById(R.id.guarantorcode);
            sponsor_code_note = findViewById(R.id.sponsor_code_note);


            if (previousactivity.equals("SETTINGS")) {
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                Button skipbtn = findViewById(R.id.skip);
                skipbtn.setVisibility(View.INVISIBLE);

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);

                if (guarantorStatus.equals("APPROVED")) {
                    findViewById(R.id.signin).setVisibility(View.GONE);
                    guarantorcode.setText(guarantorID);
                    guarantorcode.setEnabled(false);
                    guarantorcode.setClickable(false);
                    guarantorcode.setFocusable(false);
                    getSupportActionBar().setTitle("Sponsor Code");
                    sponsor_code_note.setText("");
                } else if (guarantorStatus.equals("PENDING")) {
                    findViewById(R.id.signin).setVisibility(View.GONE);
                    guarantorcode.setText("Sponsor request for approval.");
                    guarantorcode.setEnabled(false);
                    guarantorcode.setClickable(false);
                    guarantorcode.setFocusable(false);
                    getSupportActionBar().setTitle("Sponsor Code");
                    getSession();
                } else if (guarantorStatus.equals("DECLINED") || guarantorStatus.equals(".")) {
                    // emptyvouchersdesc.setText("To get new vouchers, register to a sponsor or simply ask from a friend.");
                    if (guarantorStatus.equals("."))
                        sponsor_code_note.setText("A sponsor code is a unique code of the registered sponsor.\nIf you do not have the code, you may skip this and setup later under settings.");
                    else
                        sponsor_code_note.setText("A sponsor code is a unique code of the registered sponsor.");
                }

            }
            if (isFreeModeEnabled()) {
                FreenetSdk.registerConnectionListener(this);
                activateFreeNet();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            if (isFreeModeEnabled()) {
                deactivateFreeNet();
                FreenetSdk.unregisterConnectionListener(this);
            }
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /*
     * FUNCTIONS
     * */

    public void openTermsAndCondition(View v) {
        try {
            Uri uriUrl = Uri.parse(CommonVariables.TERMSGUARANTORSIGNUP);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openProfileRegistration(View view) {

        try {
            //1.
            int status = CommonFunctions.getConnectivityStatus(this);
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else { //has connection proceed

                //2.
                guarantorcodeval = guarantorcode.getText().toString();
                if (!guarantorcodeval.equals("")) {
                    Cursor cursor = db.getAccountInformation(db);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        do {
                            mobileval = cursor.getString(cursor.getColumnIndex("mobile"));
                        } while (cursor.moveToNext());
                        //3.
                        verifySession();
                    }
                    cursor.close();
                }else{
                    guarantorcode.setError("Invalid Sponsor Code.");
                    guarantorcode.requestFocus();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void verifySession() {

        try {
            CommonFunctions.showDialog(mcontex, "", "Verifying Sponsor Code. Please wait ...", false);
            //new HttpAsyncTask().execute(CommonVariables.GUARANTORREGISTRATION);
            registerGuarantor();
        } catch (Exception e) {
            e.printStackTrace();
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
                String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionidval);
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("guarantorcode", guarantorcodeval);
                jsonObject.accumulate("sessionid", sessionidval);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("authcode", authcode);
                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                CommonFunctions.hideDialog();
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            CommonFunctions.hideDialog();
            try {
                if (result == null) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("003")) {
                    showToast("Sponsor code does not exist.", GlobalToastEnum.ERROR);
                } else if (result.equals("002")) {
                    showToast("Invalid Session.", GlobalToastEnum.ERROR);
                } else if (result.equals("001")) {
                    showToast("Invalid Entry.", GlobalToastEnum.ERROR);
                } else if (result.equals("004")) {
                    showToast("Invalid Authentication.", GlobalToastEnum.ERROR);
                } else if (result.equals("error")) {
                    showToast("Something wrong with your internet connection. Please check.",GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    //5.

                    db.setGuarantorID(db, guarantorcodeval, mobileval);

                    StringTokenizer tokens = new StringTokenizer(result, "|");
                    String isguarantororsubguarantor = tokens.nextToken();
                    guarantoremail = tokens.nextToken();
                    guarantorname = tokens.nextToken();

                    if (isguarantororsubguarantor.equals("GUARANTORCODE")) {
                        issubguarantor = "0";
                    } else {
                        issubguarantor = "1";
                    }

                    proceedGuarantorVerification();
                }
            } catch (Exception e) {
                showToast("Error.", GlobalToastEnum.ERROR);
            }
        }
    }

    public void proceedGuarantorVerification() {
        try {
            Intent intent = new Intent(this, SubscriberProfileActivity.class);
            intent.putExtra("GUARANTORID", guarantorcodeval);
            intent.putExtra("GUARANTORNAME", guarantorname);
            intent.putExtra("GUARANTOREMAIL", guarantoremail);
            intent.putExtra("FROM", "GUARANTORVERIFICATION");
            intent.putExtra("PREVACTIVITY", previousactivity);
            intent.putExtra("ISSUBGUARANTOR", issubguarantor);

            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openProfilingFromSkip(View view) {
        try {
            if (isFreeModeEnabled()) {
                deactivateFreeNet();
                FreenetSdk.unregisterConnectionListener(this);
            }
            Intent intent = new Intent(this, SubscriberProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("GUARANTORID", ".");
            intent.putExtra("GUARANTORNAME", "");
            intent.putExtra("GUARANTOREMAIL", "");
            intent.putExtra("FROM", "SKIPVERIFICATION");
            intent.putExtra("PREVACTIVITY", previousactivity);
            intent.putExtra("ISSUBGUARANTOR", "0");
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } catch (Exception e) {
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

    public static void start(Context context, String source) {
        Intent intent = new Intent(context, GuarantorVerificationActivity.class);
        intent.putExtra("FROM", source);
        context.startActivity(intent);
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) == 0) {
            showToast("No internet connection. Please check.", GlobalToastEnum.NOTICE);
        } else {
            showProgressDialog(getViewContext(), "Checking sponsor request status.", "Please wait.");
            //getSponsorRequestStatus();
            getSponsorRequestStatusV2();
        }
    }

    private void getSponsorRequestStatus() {
        Call<GetSubscribersSponsorRequestStatusResponse> call = RetrofitBuild.getSubscriberAPIService(getViewContext())
                .getSubscriberSponsorRequestStatus(
                        imei,
                        CommonFunctions.getSha1Hex(imei + userid + sessionidval),
                        sessionidval,
                        userid,
                        borrowerid
                );

        call.enqueue(getSponsorRequestStatusCallback);
    }

    private Callback<GetSubscribersSponsorRequestStatusResponse> getSponsorRequestStatusCallback = new Callback<GetSubscribersSponsorRequestStatusResponse>() {
        @Override
        public void onResponse(Call<GetSubscribersSponsorRequestStatusResponse> call, Response<GetSubscribersSponsorRequestStatusResponse> response) {
            try {
               CommonFunctions.hideDialog();
                if (response.body().getStatus().equals("000")) {
                    // db.updateGuarantorStatus(db, response.body().getData().getStatus(), userid);
                    guarantorcode.setText(response.body().getData().getGuarantorID() + " - For Approval");
                    sponsor_code_note.setText("You have already sent a request to " + response.body().getData().getGuarantorID() + ". You will be notified once approved.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetSubscribersSponsorRequestStatusResponse> call, Throwable throwable) {
            hideProgressDialog();
        }
    };

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * **/

    private void registerGuarantor(){

        try{

            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

                LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("guarantorcode",guarantorcodeval);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionidval);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(jsonString, "index");
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                parameters.put("index", index);

                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                //AUTHENTICATIONID
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionidval + "registerGuarantor");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                registerGuarantorObject(getGuarantorCallback);

            }else{
                CommonFunctions.hideDialog();
                showError();
            }

        }catch (Exception e){
            showError();
            CommonFunctions.hideDialog();
            e.printStackTrace();
        }

    }

    private void registerGuarantorObject(Callback<GenericResponse> getGuarantorCallback){
        Call<GenericResponse> registerGuarantor = RetrofitBuilder.getSubscriberV2APIService(getViewContext())
                .registerGuarantor(authenticationid,sessionidval,param);
        registerGuarantor.enqueue(getGuarantorCallback);
    }

    private Callback<GenericResponse> getGuarantorCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            try {
                CommonFunctions.hideDialog();
                ResponseBody responseBody = response.errorBody();
                if(responseBody == null){
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                        if(decryptedData.equalsIgnoreCase("error") || message.equalsIgnoreCase("error")){
                            showErrorToast();
                        }else{
                            //5.
                            db.setGuarantorID(db, guarantorcodeval, mobileval);

                            String isguarantororsubguarantor = CommonFunctions.parseJSON(decryptedData,"isguarantororsubguarantor");
                            guarantoremail = CommonFunctions.parseJSON(decryptedData,"guarantoremail");
                            guarantorname = CommonFunctions.parseJSON(decryptedData,"guarantorname");

                            Logger.debug("okhttp","GUARANTOR INFO : "+decryptedData);

                            if (isguarantororsubguarantor.equals("GUARANTORCODE")) {
                                issubguarantor = "0";
                            } else {
                                issubguarantor = "1";
                            }
                            proceedGuarantorVerification();
                        }
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
                        showErrorToast(message);
                    }
                }else{
                    showError();
                }
            } catch (Exception e) {
                CommonFunctions.hideDialog();
                showError();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable throwable) {
            CommonFunctions.hideDialog();
            throwable.printStackTrace();
            showError();

        }
    };


    private void getSponsorRequestStatusV2(){
        try{

            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

                LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionidval);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(jsonString, "index");
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                parameters.put("index", index);

                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                //AUTHENTICATIONID
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionidval + "getSubscriberSponsorRequestStatus");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getSponsorRequestStatusObjectV2(getSponsorRequestStatusCallbackV2);

            }else {
                hideProgressDialog();
                showNoInternetToast();
            }

        }catch (Exception e){
            hideProgressDialog();
            e.printStackTrace();
        }
    }
    private void getSponsorRequestStatusObjectV2(Callback<GenericResponse> getSponsorRequestStatusCallback){
        Call<GenericResponse> getSponsorRequest = RetrofitBuilder.getSubscriberV2APIService(getViewContext())
                .getSubscriberSponsorRequestStatus(authenticationid,sessionidval,param);
        getSponsorRequest.enqueue(getSponsorRequestStatusCallback);
    }

    private Callback<GenericResponse> getSponsorRequestStatusCallbackV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            try {
                ResponseBody responseBody = response.errorBody();
                if(responseBody == null){
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                        if(decryptedData.equalsIgnoreCase("error") || message.equalsIgnoreCase("error")){
                            showErrorToast();
                        }else{

                            SubscribersSponsorRequestStatus sponsorRequestStatus = new Gson().fromJson(decryptedData,SubscribersSponsorRequestStatus.class);

                            guarantorcode.setText(sponsorRequestStatus.getGuarantorID() + " - For Approval");
                            sponsor_code_note.setText("You have already sent a request to " + sponsorRequestStatus.getGuarantorID() + ". You will be notified once approved.");

                        }
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }
                }else{
                    showError();
                }
            } catch (Exception e) {
                showError();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable throwable) {
            throwable.printStackTrace();
            showError();
            hideProgressDialog();
        }
    };
}
