package com.goodkredit.myapplication.activities.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends BaseActivity {

    //Declaration

    DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;
    TextView oldpassword;
    TextView newpassword;
    TextView confpassword;

    static String oldpasswordval = "";
    static String newpasswordval = "";
    static String confpasswordval = "";
    static String borroweridval = "";
    static String mobileval = "";
    static String sessionidval = "";
    static String imei = "";
    static String userid = "";

    // Progress Dialog
    ProgressDialog pDialog;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mcontext = this;

        //Initialize database
        db = new DatabaseHandler(this);

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getApplicationContext());

        //inflate view
        setContentView(R.layout.activity_change_password);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        //set toolbar
        setupToolbar();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        return super.onOptionsItemSelected(item);
    }


    public void changePassword(View view) {
        try {
            //1.
            int status = CommonFunctions.getConnectivityStatus(this);
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else { //has connection proceed

                //2. get input
                oldpassword = findViewById(R.id.oldpassword);
                newpassword = findViewById(R.id.newpassword);
                confpassword = findViewById(R.id.confpass);

                oldpasswordval = oldpassword.getText().toString();
                newpasswordval = newpassword.getText().toString();
                confpasswordval = confpassword.getText().toString();


                //Validate input
                if (oldpasswordval.equals("") || newpasswordval.equals("") || confpasswordval.equals("")) {
                    showToast("All fields are mandatory", GlobalToastEnum.WARNING);
                } else if (!newpasswordval.equals(confpasswordval)) {
                    showToast("New password did not match or empty", GlobalToastEnum.WARNING);
                } else if (oldpasswordval.equals(newpasswordval)) {
                    showToast("New password must not be equal to the old password", GlobalToastEnum.WARNING);
                } else {
                    //get account information
                    Cursor cursor = db.getAccountInformation(db);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        do {
                            borroweridval = cursor.getString(cursor.getColumnIndex("borrowerid"));
                            mobileval = cursor.getString(cursor.getColumnIndex("mobile"));
                            userid = cursor.getString(cursor.getColumnIndex("mobile"));
                            imei = cursor.getString(cursor.getColumnIndex("imei"));
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    //3.
                    verifySession();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /*---------------
     * FUNCTIONS
     * --------------*/

    public void verifySession() {
        int status = CommonFunctions.getConnectivityStatus(this);
        if (status == 0) { //no connection
            showToast("No internet connection.", GlobalToastEnum.WARNING);
        } else {
            CommonFunctions.showDialog(mcontext, "", "Changing Password. Please wait ...", false);

            //call AsynTask to perform network operation on separate thread
            //new HttpAsyncTask().execute(CommonVariables.UPDATEPASSWORDURL);
            changePassword();
        }

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("username", mobileval);
                jsonObject.accumulate("newpassword", newpasswordval);
                jsonObject.accumulate("oldpassword", oldpasswordval);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
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

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            CommonFunctions.hideDialog();
            if (result == null) {
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                processResult(result);
            }
        }
    }

    public void processResult(String result) {

        try {
            if (result.equals("000")) {
                showToast("Password was successfully changed.", GlobalToastEnum.INFORMATION);
                oldpassword.setText("");
                newpassword.setText("");
                confpassword.setText("");
            } else if (result.equals("001")) {
                showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
            } else if (result.equals("002")) {
                showToast("Invalid Session.", GlobalToastEnum.NOTICE);
            } else if (result.equals("003")) {
                showToast("Invalid Authentication.", GlobalToastEnum.NOTICE);
            } else if (result.equals("004")) {
                showToast("Old password did not match.", GlobalToastEnum.WARNING);
            } else if (result.contains("<!DOCTYPE html>")) {
                showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(intent);
    }

    /*
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void changePassword() {
        try {

            if (CommonFunctions.getConnectivityStatus(getApplicationContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borroweridval);
                parameters.put("username", mobileval);
                parameters.put("password", oldpasswordval);
                parameters.put("newpassword", newpasswordval);


                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "changePassword");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                changePasswordObject(changePasswordSession);

            } else {
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changePasswordObject(Callback<GenericResponse> getTransactionCallback) {
        Call<GenericResponse> getTransaction = RetrofitBuilder.getAccountV2API(getViewContext())
                .changePassword(
                        authenticationid, sessionid, param
                );
        getTransaction.enqueue(getTransactionCallback);
    }

    private final Callback<GenericResponse> changePasswordSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            CommonFunctions.hideDialog();
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else {
                        showToast("Password was successfully changed.", GlobalToastEnum.SUCCESS);
                        oldpassword.setText("");
                        newpassword.setText("");
                        confpassword.setText("");
                    }

                } else {
                    if (response.body().getStatus().equalsIgnoreCase("error")) {
                        showErrorToast();
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }
                    else {
                        showToast(decryptedMessage, GlobalToastEnum.WARNING);
                    }
                }
            } else {
                showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.ERROR);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
        }
    };
}
