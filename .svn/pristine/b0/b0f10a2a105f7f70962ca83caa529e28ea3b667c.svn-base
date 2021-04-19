package com.goodkredit.myapplication.activities.vouchers;

import android.animation.ValueAnimator;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailableVoucherDetailsTransaction extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    //Declaration
    private DatabaseHandler db;
    private CommonFunctions cf;
    private CommonVariables cv;
    private Context mcontext;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private  RelativeLayout emptyvoucher;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    private ArrayList<HashMap<String, String>> transactionList = new ArrayList<>();

    private String borrowerid = "";
    private String userid = "";
    private String imei = "";
    private String sessionidval = "";
    private String vouchercode = "";
    private String voucherserialval = "";

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    //RFID
    private String rfidCardNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_voucher_details_transaction);
        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Toast.makeText(getViewContext(), "Diri", Toast.LENGTH_SHORT).show();


        mcontext = this;

        try {
            db = new DatabaseHandler(this);
            // Hashmap for ListView
            transactionList = new ArrayList<HashMap<String, String>>();
            listView = findViewById(R.id.detailstransactionlist);
            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setOnRefreshListener(this);
            emptyvoucher = findViewById(R.id.emptyvoucher);
            refresh = findViewById(R.id.refresh);
            nointernetconnection = findViewById(R.id.nointernetconnection);
            refreshnointernet = findViewById(R.id.refreshnointernet);
            refreshdisabled = findViewById(R.id.refreshdisabled);
            refreshdisabled1 = findViewById(R.id.refreshdisabled1);

            mLlLoader = findViewById(R.id.loaderLayout);
            mTvFetching = findViewById(R.id.fetching);
            mTvWait = findViewById(R.id.wait);

            //UNIFIED SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                voucherserialval = extras.getString("SERIALNO");
                rfidCardNumber = extras.getString("RFIDCARDNUMBER");
            }

            final ImageView backgroundOne = findViewById(R.id.background_one);
            final ImageView backgroundTwo = findViewById(R.id.background_two);

            final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(750);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    final float progress = (float) animation.getAnimatedValue();
                    final float width = backgroundOne.getWidth();
                    final float translationX = width * progress;
                    backgroundOne.setTranslationX(translationX);
                    backgroundTwo.setTranslationX(translationX - width);
                }
            });
            animator.start();

            mLoaderTimer = new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    mLlLoader.setVisibility(View.GONE);
                }
            };

            //get account information
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


            //get voucher from local database
            Cursor c = db.getSpecificVoucher(db, voucherserialval);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    vouchercode = c.getString(c.getColumnIndex("vouchercode"));
                } while (c.moveToNext());
            }
            c.close();

            //setting the toolbar title with the voucher code
            String vcode = vouchercode.substring(0, 2) + "-" + vouchercode.substring(2, 6) + "-" + vouchercode.substring(6, 11) + "-" + vouchercode.substring(11, 12);
            getSupportActionBar().setTitle("Voucher ID: " + vcode);

            if(rfidCardNumber != null) {
                if(!rfidCardNumber.equals("")) {
                    getSupportActionBar().setTitle("CARD# " + CommonFunctions.addDashIntervals(rfidCardNumber, 4));
                }
            }

            verifySession();
            // new HttpAsyncTask().execute(cv.GETVOUCHERDETAILSLOGS);
            /*****************
             * TIGGERS
             * ****************/

            //REFRESH
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    disableRefresh();
                    verifySession();
                }
            });

            //refresh in no internet connection indicator
            refreshnointernet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    disableRefresh();
                    verifySession();
                }
            });


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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        mLoaderTimer.cancel();
        super.onStop();
    }

    /*---------------
     * FUNCTIONS
     * --------------*/

    public void verifySession() {

        try {

            int status = CommonFunctions.getConnectivityStatus(this);
            if (status == 0) { //no connection
                showNoInternetConnection();
                enableRefresh();
                swipeRefreshLayout.setRefreshing(false);
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {

                mTvFetching.setText("Fetching voucher transactions.");
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);

                mLoaderTimer.cancel();
                mLoaderTimer.start();

                getVoucherDetailsLogsV2();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRefresh() {
        verifySession();
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
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("vouchercode", vouchercode);
                jsonObject.accumulate("voucherserialno", voucherserialval);

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
            mLlLoader.setVisibility(View.GONE);
            if (result == null) {
                showNoInternetConnection();
                enableRefresh();
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else if (result.contains("<!DOCTYPE html>")) {
                showNoInternetConnection();
                enableRefresh();
                showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                processList(result);
            }
        }
    }


    public void processList(String result) {

        try {
            hideNoInternetConnection();
            JSONArray jsonArr = new JSONArray(result);
            if (jsonArr.length() > 0) {
                emptyvoucher.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                if(transactionList != null) {
                    transactionList.clear();

                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String merchantid = jsonObj.getString("MerchantID");
                        String date = jsonObj.getString("DateTimeIN");
                        String amount = jsonObj.getString("ConsummationAmount");
                        String activity = jsonObj.getString("Activity");


                        // creating new HashMap
                        HashMap<String, String> ls = new HashMap<String, String>();
                        // adding each child node to HashMap key => value
                        ls.put("MERCHANTID", merchantid);
                        ls.put("DATE", CommonFunctions.convertDate(date));
                        ls.put("AMOUNT", CommonFunctions.currencyFormatter(amount));
                        ls.put("ACTIVITY", activity);

                        // adding HashList to ArrayList
                        transactionList.add(ls);
                    }
                    showTransaction();
                }
            } else {
                emptyvoucher.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showTransaction() {
        try {
            ListAdapter adapter = new SimpleAdapter(getBaseContext(),
                    transactionList, R.layout.activity_available_voucher_details_transaction_item, new String[]{
                    "MERCHANTID", "DATE", "AMOUNT"},
                    new int[]{R.id.merchantid, R.id.date, R.id.totalamount});

            if(rfidCardNumber != null) {
                if(!rfidCardNumber.equals("")) {
                    adapter = new SimpleAdapter(getBaseContext(),
                            transactionList, R.layout.activity_available_voucher_details_transaction_item, new String[]{
                            "ACTIVITY", "DATE", "AMOUNT"},
                            new int[]{R.id.merchantid, R.id.date, R.id.totalamount});
                }
            }

            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disableRefresh() {
        try {
            refreshdisabled.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            refreshdisabled1.setVisibility(View.VISIBLE);
            refreshnointernet.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void enableRefresh() {
        try {
            refreshdisabled.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
            refreshdisabled1.setVisibility(View.GONE);
            refreshnointernet.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showNoInternetConnection() {
        try {
            nointernetconnection.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void hideNoInternetConnection() {
        try {
            nointernetconnection.setVisibility(View.GONE);
            enableRefresh();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*****
    * SECURITY UPDATE
    * AS OF
    * OCTOBER 2019
    * *******/

    private void getVoucherDetailsLogsV2() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("vouchercode", vouchercode);
            parameters.put("devicetype",CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getVoucherDetailsLogsV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getVoucherDetailsLogsV2Object(getVoucherDetailsLogsV2Callback);

        } else {
            CommonFunctions.hideDialog();
            showNoInternetToast();

        }
    }
    private void getVoucherDetailsLogsV2Object(Callback<GenericResponse> getvoucher) {
        Call<GenericResponse> call = RetrofitBuilder.getVoucherV2API(getViewContext())
                .getVoucherDetailsLogsV2(
                        authenticationid, sessionid, param
                );
        call.enqueue(getvoucher);
    }
    private Callback<GenericResponse> getVoucherDetailsLogsV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            if (errorBody == null) {
                String message = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    processList(data);

                } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else {
                    showErrorGlobalDialogs(message);
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            t.printStackTrace();
            showErrorGlobalDialogs();
        }
    };

}
