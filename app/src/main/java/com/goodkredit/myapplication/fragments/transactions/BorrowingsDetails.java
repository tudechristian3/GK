package com.goodkredit.myapplication.fragments.transactions;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.BorrowingsDetailsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowingsDetails extends BaseActivity {

    //Declaration
    DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;
    ListView listView;

    static String borrowerid = "";
    static String imei = "";
    static String sessionidval = "";
    static String userid = "";
    static String transactionno = "";
    static String datetransaction = "";
    ArrayList<Voucher> arrayList;

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
        setContentView(R.layout.activity_borrowings_details);
        try {
            //set toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            mcontext = this;

            //initialized local database
            db = new DatabaseHandler(this);

            //UNIFIED SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            // Hashmap for ListView
            arrayList = new ArrayList<>();
            listView = findViewById(R.id.borrowinglist);

            //get the passed value
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                transactionno = extras.getString("ID");
                datetransaction = extras.getString("DATE");

                toolbar.setTitle("Borrowing Details");
                setSupportActionBar(toolbar);

                Log.v("TAGS", datetransaction);
            }

            //get account information
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                } while (cursor.moveToNext());
                verifySession();

            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

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
    public void onResume() {
        super.onResume();

        try {
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                } while (cursor.moveToNext());

            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }


    }


    /**************
     * FUNCTIONS
     ************/
    public void verifySession() {

        try {
            int status = CommonFunctions.getConnectivityStatus(getBaseContext());
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {

                //call AsynTask to perform network operation on separate thread
                //new HttpAsyncTask().execute(CommonVariables.GETBORROWINGSDETAILSURL);
                 getBorrowingDetails();

            }
        } catch (Exception e) {
            e.printStackTrace();
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
            try {
                String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("year", CommonFunctions.getCalendarFromDate(datetransaction).get(Calendar.YEAR));
                jsonObject.accumulate("month", CommonFunctions.getCalendarFromDate(datetransaction).get(Calendar.MONTH) + 1);
                jsonObject.accumulate("transactionno", transactionno);
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
            if (result == null || result.contentEquals("error")) {
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else if (result.contains("<!DOCTYPE html>")) {
                showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                processList(result);
            }
        }
    }

    public void processList(String result) {

        try {

            JSONArray jsonArr = new JSONArray(result);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                String productid = jsonObj.getString("ProductID");
                String vouchercode = jsonObj.getString("VoucherCode");
                String voucherdenom = jsonObj.getString("VoucherDenom");
                String voucherserial = jsonObj.getString("VoucherSerialNo");

                arrayList.add(new Voucher(productid, vouchercode, Double.parseDouble(voucherdenom), voucherserial));
            }
            showBorrowingList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBorrowingList() {
        try {
            BorrowingsDetailsAdapter adapter = new BorrowingsDetailsAdapter(getApplicationContext(), arrayList);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getBorrowingDetails() {
        try {

            if (CommonFunctions.getConnectivityStatus(getApplicationContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("year", String.valueOf(CommonFunctions.getCalendarFromDate(datetransaction).get(Calendar.YEAR)));
                parameters.put("month", String.valueOf(CommonFunctions.getCalendarFromDate(datetransaction).get(Calendar.MONTH) + 1));
                parameters.put("transactionno", transactionno);
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getBorrowingsDetailsV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getBorrowingDetailsObject(getBorrowingDetailsSessionV2);

            } else {
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getBorrowingDetailsObject( Callback<GenericResponse> getBorrowingDetailsCallback){
        Call<GenericResponse> getBorrowingDetails = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getBorrowingsDetails(
                        authenticationid, sessionid, param
                );
        getBorrowingDetails.enqueue(getBorrowingDetailsCallback);
    }

    private final Callback<GenericResponse> getBorrowingDetailsSessionV2 = new Callback<GenericResponse>() {
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
                        processList(decryptedData);
                    }

                } else {
                    if (response.body().getStatus().equalsIgnoreCase("error")) {
                        showErrorToast();
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showToast(decryptedMessage, GlobalToastEnum.WARNING);
                    }
                }
            } else {
                showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
        }
    };
}
