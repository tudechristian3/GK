package com.goodkredit.myapplication.activities.transfer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
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
* 1. Check mobile and country
* 2. CHeck status of the network
* 3. Create Session
* 4. Validate mobile send http request
* 5. Process Result
* */
public class TransferVerifySMS extends BaseActivity {
    CommonFunctions cf;
    CommonVariables cv;
    DatabaseHandler db;
    Context mcontext;

    EditText country;
    EditText countrycode;
    EditText mobile;
    EditText code;

    static String imei = "";
    //static String sessionid = "";
    static String countryval = "";
    static String countryprefixval = "";
    static String mobileval = "";
    static String borroweridval = "";
    static String vouchercodeval = "";
    static String amountval = "";
    static String userid = "";

    private String sessionid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_verify_sms);

        //get the passed value
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            vouchercodeval = extras.getString("VOUCHERCODE");
            amountval = extras.getString("AMOUNT");
        }


        mcontext = this;

        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initialize Database
        db = new DatabaseHandler(this);


        //get country code and name
        String currentcountry = GetCountryZipCode();

        //initialize elements
        country = findViewById(R.id.country);
        countrycode = findViewById(R.id.countrycode);
        mobile = findViewById(R.id.receivermobile);
        code = findViewById(R.id.code);

        if (currentcountry != null && !currentcountry.equals("")) {
            country.setText(currentcountry);
            countrycode.setText(countryprefixval);
            code.setText("+" + countryprefixval);
        } else {
            country.setText("Philippines");
            countrycode.setText("63");
            code.setText("+63");
        }


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


        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (count < 10 || count > 10) {
//
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String countryval = data.getStringExtra("Country");
                String countryprefixval = data.getStringExtra("CountryPrefix");
                country.setText(countryval);
                countrycode.setText(countryprefixval);
                code.setText("+" + countryprefixval);
            }
        }
    }

    /***************
     * FUNCTIONS
     **********/
    public void openCountryList(View view) {
//        Intent intent = new Intent(this, CountryCodeListActivity.class);
//        startActivityForResult(intent, 1);
//        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    public void validateMobile(View view) {
        try {
            countryprefixval = countrycode.getText().toString();
            mobileval = mobile.getText().toString();
            if (mobileval.length() < 10 || mobileval.length() > 10) {
                showToast("Invalid mobile number. Please check.", GlobalToastEnum.WARNING);
            } else {
                //1.
                if (countryprefixval.equals("") || mobileval.equals("")) {
                    showToast("All fields are mandatory.", GlobalToastEnum.WARNING);
                } else {
                    //2.
                    int status = CommonFunctions.getConnectivityStatus(this);
                    if (status == 0) { //no connection
                        showToast("No internet connection.", GlobalToastEnum.NOTICE);
                    } else { //has connection proceed

                        //this is to remove 0 when the user input zero before prefix
                        if (countryprefixval.equals("63")) {
                            String first = mobileval.trim().substring(0, 1);
                            if (first.equals("0")) {
                                mobileval = mobileval.substring(1);
                            }
                        }

                        //3.
                        verifySession();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3.
//    public void verifySession() {
//        CommonFunctions.showDialog(mcontext, "", "Validating mobile number. Please wait ...", false);
//
//        CreateSession newsession = new CreateSession(this);
//        newsession.setQueryListener(new CreateSession.QueryListener() {
//            @SuppressWarnings("unchecked")
//            public void QuerySuccessFul(String data) {
//                if (data == null) {
//                    CommonFunctions.hideDialog();
//                    showToast("No internet connection. Please try again.", GlobalToastEnum.ERROR);
//                } else if (data.equals("001")) {
//                    CommonFunctions.hideDialog();
//                    showToast("Invalid Entry. Please check..", GlobalToastEnum.ERROR);
//                } else if (data.equals("002")) {
//                    CommonFunctions.hideDialog();
//                    showToast("Invalid User", GlobalToastEnum.ERROR);
//                } else if (data.equals("error")) {
//                    CommonFunctions.hideDialog();
//                    showToast("Something went wrong with your connection. Please check.", GlobalToastEnum.ERROR);
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
            CommonFunctions.showDialog(mcontext, "", "Validating mobile number. Please wait ...", false);
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
                jsonObject.accumulate("mobile", mobileval);
                jsonObject.accumulate("countrycode", countryprefixval);
                jsonObject.accumulate("transfertype", "SMS");
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("userid", userid);

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

    public void processResult(String result) {
        CommonVariables.TRANSFERAUTHENTICATION = result;
        Intent intent = new Intent(this, TransferThruSMS.class);
        intent.putExtra("VOUCHERCODE", vouchercodeval);
        intent.putExtra("AMOUNT", amountval);
        intent.putExtra("MOBILE", countryprefixval + mobileval);
        startActivity(intent);


    }

    public String GetCountryZipCode() {
        String CountryID = "";
        String CountryZipCode = "";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID = manager.getSimCountryIso().toUpperCase();
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(CountryID.trim())) {
                countryprefixval = g[0];
                countryval = g[2];
                CountryZipCode = countryval;
                break;
            }
        }
        return CountryZipCode;
    }


}
