package com.goodkredit.myapplication.activities.transfer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.PreferenceUtils;

import org.json.JSONObject;

import java.text.DecimalFormat;

/*
ALGO:
1. Validate entry
2. Open pop up confirmation modal
3. Check network status
4. Create Session
5. Send request to http transfer voucher
6. Process result
* */
public class TransferThruEmail extends BaseActivity {
    DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;

    String vouchercodeval = "";
    String amountval = "";
    String receivername = "";
    String borroweridval = "";
    String emailval = "";
    String sessionid = "";
    String imei = "";
    String verificationcodeval = "";
    String currentrequest = "";
    String userid = "";


    Dialog dialog;

    TextView popname;
    TextView popvoucher;
    TextView popamount;
    TextView popemail;
    TableRow mobilerow;
    TextView popsuccessclose;
    TextView popsuccessconfirmation;
    ScrollView popconfirmationwrap;
    LinearLayout popsuccesswrap;


    EditText name;
    EditText verificationcode;
    Button btnproceed;
    TextView confirmationlbl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_thru_email);

        mcontext = this;
        db = new DatabaseHandler(this);


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
            emailval = extras.getString("EMAIL");
        }

        //get account information
        sessionid = PreferenceUtils.getSessionID(getViewContext());

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


        //initialize elements
        name = findViewById(R.id.receivername);
        verificationcode = findViewById(R.id.code);
        btnproceed = findViewById(R.id.proceed);


        //create a dialog confirmation
        dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pop_confirmation);
        confirmationlbl = dialog.findViewById(R.id.confirmationlbl);
        confirmationlbl.setText("Transfer Thru Email");
        popconfirmationwrap = dialog.findViewById(R.id.confirmationwrap);
        popsuccesswrap = dialog.findViewById(R.id.successwrap);
        popsuccessclose = dialog.findViewById(R.id.popok);
        popsuccessconfirmation = dialog.findViewById(R.id.succesconfirmationlbl);
        popsuccessconfirmation.setText("Transfer Thru Email");


        popname = dialog.findViewById(R.id.popname);
        popvoucher = dialog.findViewById(R.id.popvouchercode);
        popamount = dialog.findViewById(R.id.popamount);
        popemail = dialog.findViewById(R.id.popemail);
        mobilerow = dialog.findViewById(R.id.mobilerow);


        /************
         * TRIGGERS
         * **********/

        //1.
        verificationcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4) {
                    currentrequest = "VERIFYCODE"; //this is to determine which process to do after creating of session
                    verificationcodeval = verificationcode.getText().toString().trim();
                    int status = CommonFunctions.getConnectivityStatus(getBaseContext());
                    if (status == 0) { //no connection
                        showToast("No internet connection.", GlobalToastEnum.NOTICE);
                    } else { //has connection proceed
                        //4.
                        CommonFunctions.showDialog(mcontext, "", "Verifying Code. Please wait ...", false);
                        verifySession();
                    }

                } else {
                    btnproceed.setEnabled(false);
                }

            }
        });


        //click confirm transaction
        TextView confirm = dialog.findViewById(R.id.popconfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //3.
                int status = CommonFunctions.getConnectivityStatus(getBaseContext());
                if (status == 0) { //no connection
                    showToast("No internet connection.", GlobalToastEnum.NOTICE);
                } else { //has connection proceed
                    //4.

                    currentrequest = "PROCESSTRANSFER";
                    CommonFunctions.showDialog(mcontext, "", "Processing Transfer. Please wait ...", false);
                    verifySession();
                }


            }
        });

        //click cancel
        TextView canceltxn = dialog.findViewById(R.id.canceltxn);
        canceltxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }

        });

        //click close

        popsuccessclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                CommonVariables.VOUCHERISFIRSTLOAD = true; //make it true to reload the vouchers
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        sessionid = PreferenceUtils.getSessionID(getViewContext());
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

    /**************
     * FUNCTIONS
     ***********/

    public void tranferThruEmail(View view) {

        try {
            //1.
            //get input
            receivername = name.getText().toString().trim();
            verificationcodeval = verificationcode.getText().toString().trim();

            //check input
            if (receivername.equals("") || verificationcodeval.equals("")) {
                showToast("All fields are mandatory.", GlobalToastEnum.WARNING);
            } else {
                //2.
                final String vcode = vouchercodeval.substring(0, 2) + "-" + vouchercodeval.substring(2, 6) + "-" + vouchercodeval.substring(6, 11) + "-" + vouchercodeval.substring(11, 12);
                DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
                //set data in confirmation pop up
                popemail.setText(emailval);
                popname.setText(receivername);
                popvoucher.setText(vcode);
                popamount.setText(formatter.format(Double.parseDouble(amountval)));
                mobilerow.setVisibility(View.GONE);
                dialog.show();
            }


        } catch (Exception e) {
        }
    }

    //4.
//    public void verifySession() {
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
//                    showToast("Something went wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
//                } else if (data.contains("<!DOCTYPE html>")) {
//                    CommonFunctions.hideDialog();
//                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = data;
//                    if (currentrequest == "VERIFYCODE") {
//                        //5.//call AsynTask to perform network operation on separate thread
//                        new HttpAsyncTask().execute(CommonVariables.CHECKVERIFICATIONCODE);
//                    } else {
//                        //9.//call AsynTask to perform network operation on separate thread
//                        new HttpAsyncTask1().execute(CommonVariables.TRANSFERVOUCHER);
//                    }
//                }
//            }
//        });
//        newsession.execute(CommonVariables.CREATESESSION, imei, userid);
//    }

    private void verifySession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            if (currentrequest == "VERIFYCODE") {
                //5.//call AsynTask to perform network operation on separate thread
                new HttpAsyncTask().execute(CommonVariables.CHECKVERIFICATIONCODE);
            } else {
                //9.//call AsynTask to perform network operation on separate thread
                new HttpAsyncTask1().execute(CommonVariables.TRANSFERVOUCHER);
            }
        } else {
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
                jsonObject.accumulate("verificationcode", verificationcodeval);
                jsonObject.accumulate("authenticationcode", CommonVariables.TRANSFERAUTHENTICATION);
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

        //  onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            CommonFunctions.hideDialog();
            //6.
            if (result == null) {
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else if (result.equals("000")) {
                name.setVisibility(View.VISIBLE);
                name.requestFocus();
                verificationcode.setFocusable(false);
                verificationcode.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.check, 0);
                btnproceed.setEnabled(true);
            } else if (result.equals("001")) {
                showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
            } else if (result.equals("002")) {
                showToast("Invalid Session.", GlobalToastEnum.NOTICE);
            } else if (result.equals("003")) {
                showToast("Invalid Verification Code.", GlobalToastEnum.NOTICE);
                verificationcode.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.xred, 0);
            } else if (result.equals("004")) {
                showToast("Invalid Authentication.", GlobalToastEnum.NOTICE);
            } else if (result.contains("<!DOCTYPE html>")) {
                showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            }


        }
    }

    //5.
    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
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
                jsonObject.accumulate("vouchercode", vouchercodeval);
                jsonObject.accumulate("receivername", receivername);
                jsonObject.accumulate("receiveremail", emailval);
                jsonObject.accumulate("transfertype", "EMAIL");
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("verificationcode", verificationcodeval);
                jsonObject.accumulate("authenticationcode", CommonVariables.TRANSFERAUTHENTICATION);
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

        //  onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            CommonFunctions.hideDialog();
            //6.
            if (result == null) {
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                processResult(result);
            }

        }
    }

    //6.
    public void processResult(String result) {
        try {

            if (result.equals("000")) {
                popsuccesswrap.setVisibility(View.VISIBLE);
                popconfirmationwrap.setVisibility(View.GONE);

            } else if (result.equals("001")) {
                showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
            } else if (result.equals("002")) {
                showToast("Invalid Session.", GlobalToastEnum.NOTICE);
            } else if (result.equals("003")) {
                showToast("Invalid Voucher Code or voucher is partially used.", GlobalToastEnum.NOTICE);
            } else if (result.equals("004")) {
                showToast("Invalid Authentication.", GlobalToastEnum.NOTICE);
            } else if (result.equals("005")) {
                showToast("Voucher invalid. A pending transaction with this voucher is being processed.", GlobalToastEnum.NOTICE);
            } else if (result.contains("<!DOCTYPE html>")) {
                showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                showToast("No internet connection.Please try again.", GlobalToastEnum.NOTICE);
            }
        } catch (Exception e) {

        }
    }
}
