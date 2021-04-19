package com.goodkredit.myapplication.activities.transactions;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.MenuItem;
import android.widget.ListView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.BorrowingsDetailsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.PrepaidVoucherTransaction;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 5/26/2017.
 */

public class PurchasesTransactionDetailsActivity extends BaseActivity {

    public static final String KEY_PREP_TRANS_OBJ = "prepaidtransactionobject";

    //Declaration
    private Gson gson;
    private DatabaseHandler db;
    private CommonFunctions cf;
    private CommonVariables cv;
    private Context mcontext;
    private ListView listView;

    private String borrowerid = "";
    private String imei = "";
    private String sessionidval = "";
    private String userid = "";
    private String transactionno = "";
    private String datetransaction = "";
    private ArrayList<Voucher> arrayList;

    private PrepaidVoucherTransaction mPrepaidVoucherTransaction;

    private BorrowingsDetailsAdapter adapter;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowings_details);
        init();
    }

    private void init() {
        setupToolbar();
        db = new DatabaseHandler(getViewContext());
        gson = new Gson();
        arrayList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.borrowinglist);
        mPrepaidVoucherTransaction = getIntent().getParcelableExtra(KEY_PREP_TRANS_OBJ);

        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        adapter = new BorrowingsDetailsAdapter(getViewContext(), arrayList, true);
        listView.setAdapter(adapter);

        verifySession();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, PrepaidVoucherTransaction prepaidVoucherTransaction) {
        Intent intent = new Intent(context, PurchasesTransactionDetailsActivity.class);
        intent.putExtra(KEY_PREP_TRANS_OBJ, prepaidVoucherTransaction);
        context.startActivity(intent);
    }

    public void verifySession() {

        try {
            int status = cf.getConnectivityStatus(getBaseContext());
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {
                showProgressDialog(getViewContext(), "Fetching purchases details.", "Please wait...");

                //call AsynTask to perform network operation on separate thread
                //new HttpAsyncTask().execute(cv.GETBORROWINGSDETAILSURL);
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
                String authcode = cf.getSha1Hex(imei + userid + sessionid);

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("year", cf.getCalendarFromDate(mPrepaidVoucherTransaction.getDateTimeCompleted()).get(Calendar.YEAR));
                jsonObject.accumulate("month", cf.getCalendarFromDate(mPrepaidVoucherTransaction.getDateTimeCompleted()).get(Calendar.MONTH) + 1);
                jsonObject.accumulate("transactionno", mPrepaidVoucherTransaction.getTransactionNo());
                jsonObject.accumulate("authcode", authcode);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
            }
            return cf.POST(urls[0], json);
        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            hideProgressDialog();
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
            Type type = new TypeToken<ArrayList<Voucher>>() {
            }.getType();

            arrayList = gson.fromJson(result, type);
            adapter.updateData(arrayList);

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
                hideProgressDialog();
                showNoInternetToast();
            }

        } catch (Exception e) {
            hideProgressDialog();
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
            hideProgressDialog();
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
            hideProgressDialog();
            showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
        }
    };
}
