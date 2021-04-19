package com.goodkredit.myapplication.activities.transfer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.PreferenceUtils;

import org.json.JSONObject;

/*
* ALGO:
* 1.Check email
* 2.Check network status
* 3.Create session
* 4. Process Email Validation send http request
* 5. Process result of the request
* */
public class TransferVerifyEmail extends BaseActivity {

    CommonFunctions cf;
    CommonVariables cv;
    DatabaseHandler db;
    Context mcontext;

    String imei = "";
    String vouchercodeval = "";
    String amountval = "";
    String emailval = "";
    String borroweridval = "";
    String userid = "";

    EditText email;

    private String sessionid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_verify_email);

        mcontext = this;

        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //get the passed value
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            vouchercodeval = extras.getString("VOUCHERCODE");
            amountval = extras.getString("AMOUNT");
        }

        //Initialize Database
        db = new DatabaseHandler(this);


        //initialize elements
        email = findViewById(R.id.receiveremail);

        sessionid = PreferenceUtils.getSessionID(getViewContext());
        //get account information
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borroweridval = cursor.getString(cursor.getColumnIndex("borrowerid"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));
            } while (cursor.moveToNext());
        }
        cursor.close();

    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borroweridval = cursor.getString(cursor.getColumnIndex("borrowerid"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));
            } while (cursor.moveToNext());

        }
        cursor.close();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /*************
     * FUNCTIONS
     ***********/

    public void validateEmail(View view) {
        //1.
        emailval = email.getText().toString();
        if (emailval.equals("")) {
            showToast("Email Address is mandatory.", GlobalToastEnum.WARNING);
        } else {
            if (!CommonFunctions.isValidEmail(emailval)) {
                showToast("Invalid Email Address.", GlobalToastEnum.WARNING);
            } else {
                //2.
                int status = CommonFunctions.getConnectivityStatus(this);
                if (status == 0) { //no connection
                    showToast("No internet connection.", GlobalToastEnum.NOTICE);
                } else { //has connection proceed
                    //3.
                    verifySession();
                }
            }
        }
    }

    //3.
//    public void verifySession() {
//        CommonFunctions.showDialog(mcontext, "", "Validating Email Address. Please wait ...", false);
//        CreateSession newsession = new CreateSession(this);
//        newsession.setQueryListener(new CreateSession.QueryListener() {
//            @SuppressWarnings("unchecked")
//            public void QuerySuccessFul(String data) {
//                if (data == null) {
//                    CommonFunctions.hideDialog();
//                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (data.equals("001")) {
//                    CommonFunctions.hideDialog();
//                    showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
//                } else if (data.equals("002")) {
//                    CommonFunctions.hideDialog();
//                    showToast("Invalid User", GlobalToastEnum.NOTICE);
//                } else if (data.equals("error")) {
//                    CommonFunctions.hideDialog();
//                    showToast("Something went wrong with your connection. Please check.", GlobalToastEnum.NOTICE);
//                } else if (data.contains("<!DOCTYPE html>")) {
//                    CommonFunctions.hideDialog();
//                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = data;
//                    //call AsynTask to perform network operation on separate thread
//                    //4.
//                    new HttpAsyncTask().execute(CommonVariables.TRANSFERVALIDATESMSEMAL);
//                }
//            }
//
//        });
//        newsession.execute(CommonVariables.CREATESESSION, imei, userid);
//    }

    private void verifySession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            CommonFunctions.showDialog(mcontext, "", "Validating Email Address. Please wait ...", false);
            new HttpAsyncTask().execute(CommonVariables.TRANSFERVALIDATESMSEMAL);
        } else {
            CommonFunctions.hideDialog();
            showNoInternetToast();
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
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("email", emailval);
                jsonObject.accumulate("transfertype", "EMAIL");
                jsonObject.accumulate("borrowerid", borroweridval);
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
            if (result == null) {
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else if (result.equals("001")) {
                showToast("Invalid Entry.", GlobalToastEnum.ERROR);
            } else if (result.equals("002")) {
                showToast("Invalid Session.", GlobalToastEnum.ERROR);
            } else if (result.equals("003")) {
                showToast("Invalid Authentication.", GlobalToastEnum.ERROR);
            } else if (result.equals("error")) {
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else if (result.contains("<!DOCTYPE html>")) {
                showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                //4.
                processResult(result);
            }
        }
    }

    /******************
     * FUnCTIONS
     ************/
    public void processResult(String result) {
        CommonVariables.TRANSFERAUTHENTICATION = result;
        Intent intent = new Intent(this, TransferThruEmail.class);
        intent.putExtra("VOUCHERCODE", vouchercodeval);
        intent.putExtra("AMOUNT", amountval);
        intent.putExtra("EMAIL", emailval);
        startActivity(intent);


    }


}
