package com.goodkredit.myapplication.activities.loadmessenger;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.loadmessenger.BorrowerFB;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFBDetailsActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private BorrowerFB borrowerFB;

    //View
    ImageView fb_profile;
    TextView fb_name;
    TextView fb_mobile;
    TextView fb_address;
    TextView fb_email;

    TextView fb_credits;
    TextView fb_usedCredits;
    TextView fb_availablebalance;

    EditText fb_amountreplenish;
    Button fb_replenishBtn;

    TextView fb_totalTxnToday;
    TextView fb_totalTxnTodayAmount;
    TextView fb_totalTxnMonth;
    TextView fb_totalTxnMonthAmount;

    TextView fb_viewTransactions;
    TextView fb_filterTransactions;
    TextView fb_filterTodayLabel;
    TextView fb_filterMonthLabel;

    SwitchCompat fb_setAdmin;
    SwitchCompat fb_deactivateAccount;

    //
    private String authenticationid;
    private String index;
    private String param;
    private String keyEncryption;

    private String configIndex;
    private String configParam;
    private String configAuth;
    private String configKey;

    private String replenishindex;
    private String replenishAuth;
    private String replenishKey;
    private String replenishParam;

    private Calendar c = null;
    private int currentyear;
    private int currentmonth;

    private int filteryear;
    private int filtermonth;

    private DatabaseHandler db;

    private int isAdmin;
    private boolean deactivateAccount;
    private String switchCmdType = "";

    //
    AlertDialog alert;
    
    private String replenishAmount = "0";
    private MonthYearPickerDialogFragment dialogFragment = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fb_details);

        if(getIntent() != null){
            borrowerFB = new Gson().fromJson(getIntent().getStringExtra("borrowerFB"),BorrowerFB.class);
        }

        init();
        initData();

    }


    private void init() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Account");

        //init views
        fb_profile = findViewById(R.id.fb_profile);
        fb_name = findViewById(R.id.fb_name);
        fb_mobile = findViewById(R.id.fb_mobile);
        fb_address = findViewById(R.id.fb_address);
        fb_email = findViewById(R.id.fb_email);

        fb_credits  = findViewById(R.id.fb_credits);
        fb_usedCredits = findViewById(R.id.fb_usedcredits);
        fb_availablebalance = findViewById(R.id.fb_availablecredits);

        fb_amountreplenish = findViewById(R.id.fb_replenishAmount);
        fb_replenishBtn = findViewById(R.id.fb_replenishBtn);
        fb_replenishBtn.setOnClickListener(this);

        fb_totalTxnToday = findViewById(R.id.fb_totalTxnToday);
        fb_totalTxnTodayAmount = findViewById(R.id.fb_totalTxnTodayAmount);

        fb_totalTxnMonth = findViewById(R.id.fb_totalTxnMonth);
        fb_totalTxnMonthAmount = findViewById(R.id.fb_totalTxnMonthAmount);

        fb_setAdmin = findViewById(R.id.fb_setAdmin);
        fb_deactivateAccount = findViewById(R.id.fb_deactivateAccount);

        fb_setAdmin.setOnClickListener(this);
        fb_deactivateAccount.setOnClickListener(this);

        fb_setAdmin.setOnCheckedChangeListener((compoundButton, b) -> {
            switchCmdType = "ADMINPRIV";
        });

        fb_deactivateAccount.setOnCheckedChangeListener((compoundButton, b) -> {
            switchCmdType = "ACCOUNTSTATUS";
        });

        fb_filterTodayLabel = findViewById(R.id.fb_filterTodayLabel);
        fb_filterMonthLabel = findViewById(R.id.fb_filterMonthLabel);

        fb_viewTransactions = findViewById(R.id.fb_viewLoadTxn);
        fb_viewTransactions.setOnClickListener(this);

        fb_filterTransactions = findViewById(R.id.fb_filterTransaction);
        fb_filterTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter();
            }
        });

        //initialize date
        c = Calendar.getInstance();
        currentyear = c.get(Calendar.YEAR);
        currentmonth = c.get(Calendar.MONTH) + 1;

        db = new DatabaseHandler(this);
    }

    private void filter(){
        Calendar calendar = Calendar.getInstance();;
        Calendar minCal = Calendar.getInstance();
        minCal.set(2016, 0,1); // Set maximum date to show in dialog
        long minDate = minCal.getTimeInMillis();

        Calendar maxCal = Calendar.getInstance();
        maxCal.set(currentyear, currentmonth,31); // Set maximum date to show in dialog
        long maxDate = maxCal.getTimeInMillis(); // Get milliseconds of the modified date

        dialogFragment = MonthYearPickerDialogFragment.getInstance(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR),minDate,maxDate,"Filter Transactions");
        dialogFragment.setOnDateSetListener((year, monthOfYear) -> {
            if (year < Calendar.getInstance().get(Calendar.YEAR)) {
                filtermonth = monthOfYear + 1;
                filteryear = year;
                fb_filterMonthLabel.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Bold.ttf", CommonFunctions.getMonthYear(year + "-" + filtermonth)));
                callTransactionSummary();
            } else if (year == Calendar.getInstance().get(Calendar.YEAR)) {
                if (monthOfYear + 1 <= Calendar.getInstance().get(Calendar.MONTH) + 1) {
                    filtermonth = monthOfYear + 1;
                    filteryear = year;
                    fb_filterMonthLabel.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Bold.ttf", CommonFunctions.getMonthYear(String.valueOf(year) + "-" + String.valueOf(filtermonth))));


                    currentmonth = filtermonth;
                    currentyear = filteryear;
                    callTransactionSummary();
                } else {
                    showError("Please check filter. Thank you.");
                }
            } else if (year > Calendar.getInstance().get(Calendar.YEAR) || monthOfYear + 1 > Calendar.getInstance().get(Calendar.MONTH) + 1) {
                showError("Please check filter. Thanks.");
            }
        });
        dialogFragment.show(getSupportFragmentManager(), null);
    }

    private void initData() {

        imei = CommonFunctions.getImei(getViewContext());
        session = PreferenceUtils.getSessionID(getViewContext());


        //get account information
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));

            } while (cursor.moveToNext());
        }
        cursor.close();


        Glide.with(getApplicationContext())
                .load(borrowerFB.getFBPic())
                .error(R.drawable.broken_image_placeholder)
                .centerCrop()
                .into(fb_profile);

        fb_name.setText(borrowerFB.getFBName());
        fb_mobile.setText(borrowerFB.getFBMobileNumber());
        fb_email.setText(borrowerFB.getFBEmail());

        if(borrowerFB.getXMLDetails() == null || borrowerFB.getXMLDetails().equals(".") || borrowerFB.getXMLDetails().isEmpty()){
            fb_address.setText(".");
        }else{
            fb_address.setText(CommonFunctions.parseXML(borrowerFB.getXMLDetails(),"address"));
        }

        fb_credits.setText(CommonFunctions.currencyFormatter(borrowerFB.getCredits()));
        fb_usedCredits.setText(CommonFunctions.currencyFormatter(borrowerFB.getUsedCredit()));
        fb_availablebalance.setText(CommonFunctions.currencyFormatter(borrowerFB.getAvailableCredit()));


        fb_deactivateAccount.setChecked(!borrowerFB.getStatus().equalsIgnoreCase("ACTIVE"));
        fb_setAdmin.setChecked(borrowerFB.getIsAdmin().equals("1"));

        callTransactionSummary();

    }

    private void callTransactionSummary(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            showProgressDialog(getViewContext(),"","Getting transaction summary... please wait...");
            transactionSummary();
        }else{
            showErrorToast("Unable to fetch data from server. Please try again.");
        }
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.menu_loadmessenger, paramMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }else if(item.getItemId() == R.id.generic_refresh){
            c = Calendar.getInstance();
            currentyear = c.get(Calendar.YEAR);
            currentmonth = c.get(Calendar.MONTH) + 1;
            fb_filterMonthLabel.setText("This Month");
           callTransactionSummary();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getViewContext(),ConnectedAccountsActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDialog(String title,String message){
        if(alert == null){
            AlertDialog.Builder  builder = new AlertDialog.Builder(ViewFBDetailsActivity.this);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setCancelable(true);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    dialogInterface.dismiss();
                    alert = null;
                }
            });

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   if(CommonFunctions.getConnectivityStatus(getViewContext())> 0){
                       if(switchCmdType.equals("REPLENISH")){
                           showProgressDialog(getViewContext(),"","Processing...Please wait....");
                           replenishAmount();
                       }else{
                           showProgressDialog(getViewContext(),"","Setting Configuration... Please wait...");
                           accountConfig();
                       }

                   }else{
                       showNoInternetToast();
                   }
                }
            });

           builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.dismiss();
                   alert = null;
                   if(switchCmdType.equals("ACCOUNTSTATUS")){
                       fb_deactivateAccount.setChecked(!fb_deactivateAccount.isChecked());
                   }else if(switchCmdType.equals("ADMINPRIV")){
                       fb_setAdmin.setChecked(!fb_setAdmin.isChecked());
                   }
               }
           });
            alert = builder.create();
            alert.show();
        }
    }

    //network calls
    private void transactionSummary(){


        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("memberBorrowerid",borrowerFB.getBorrowerID());
        parameters.put("senderID",borrowerFB.getSenderID());
        parameters.put("month", String.valueOf(currentmonth));
        parameters.put("year", String.valueOf(currentyear));
        parameters.put("devicetype", CommonVariables.devicetype);

        Logger.debug("okhttp","DATA : "+ new Gson().toJson(parameters));

        //depends on the authentication type
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, session);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + session + "transactionSummary");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        transactionSummaryObject(transactionSummaryCallback);


    }
    private void transactionSummaryObject(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getLoadMessengerV2API(getViewContext())
                .transactionSummary(authenticationid,session,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  transactionSummaryCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                        String today = CommonFunctions.parseJSON(data,"today");
                        String month = CommonFunctions.parseJSON(data,"month");

                        Logger.debug("okhttp","TODAY : "+ today);
                        Logger.debug("okhttp","MONTH : "+ month);

                        try{

                            JSONArray todayArray = new JSONArray(today);
                            JSONArray monthArray = new JSONArray(month);

                            for (int i = 0; i < todayArray.length(); i++) {
                                JSONObject jsonobject = todayArray.getJSONObject(i);
                                String totalToday = jsonobject.getString("NumberTxn");
                                String totalAmountToday = jsonobject.getString("TotalTxn");

                                fb_totalTxnToday.setText(totalToday.isEmpty() || totalToday.equals("null") ? "0": totalToday);
                                fb_totalTxnTodayAmount.setText(CommonFunctions.currencyFormatter((totalAmountToday.isEmpty() || totalAmountToday.equals("null") ? "0" : totalAmountToday)));

                            }

                            for (int i = 0; i < monthArray.length(); i++) {
                                JSONObject jsonobject = monthArray.getJSONObject(i);

                                String totalMonth = jsonobject.getString("NumberTxn");
                                String totalMonthAmount = jsonobject.getString("TotalTxn");

                                fb_totalTxnMonth.setText(totalMonth.isEmpty() || totalMonth.equals("null") ? "0": totalMonth);
                                fb_totalTxnMonthAmount.setText(CommonFunctions.currencyFormatter(totalMonthAmount.isEmpty() || totalMonthAmount.equals("null") ? "0" : totalMonthAmount));

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    
                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorToast();
            t.printStackTrace();
            hideProgressDialog();
        }
    };


    //account config
    private void accountConfig(){
        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("memberBorrowerid",borrowerFB.getBorrowerID());
        parameters.put("senderID",borrowerFB.getSenderID());
        parameters.put("type", switchCmdType);

        if(switchCmdType.equals("ADMINPRIV")){
            parameters.put("isAdmin", String.valueOf(isAdmin));
        }else if(switchCmdType.equals("ACCOUNTSTATUS")){
            parameters.put("accountstat",(deactivateAccount) ? "INACTIVE" : "ACTIVE");
        }
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, session);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        configIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", configIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API
        configAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        configKey = CommonFunctions.getSha1Hex(configAuth + session + "accountConfig");
        configParam = CommonFunctions.encryptAES256CBC(configKey, String.valueOf(paramJson));

        accountConfigObject(accountConfigCallback);


    }
    private void accountConfigObject(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getLoadMessengerV2API(getViewContext())
                .accountConfig(configAuth,session,configParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  accountConfigCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(configKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    String data = CommonFunctions.decryptAES256CBC(configKey,response.body().getData());

                    if(switchCmdType.equals("ACCOUNTSTATUS")){
                        fb_deactivateAccount.setChecked((data.equals("INACTIVE")));
                        finish();
                    }else if(switchCmdType.equals("ADMINPRIV")){
                        fb_setAdmin.setChecked((data.equals("1")));
                    }

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorToast();
            t.printStackTrace();
        }
    };


    //replenish
    private void replenishAmount(){
        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("memberBorrowerid",borrowerFB.getBorrowerID());
        parameters.put("senderID",borrowerFB.getSenderID());
        parameters.put("replenishAmount", replenishAmount);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, session);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        replenishindex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", replenishindex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API
        replenishAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        replenishKey = CommonFunctions.getSha1Hex(replenishAuth + session + "replenishAmount");
        replenishParam = CommonFunctions.encryptAES256CBC(replenishKey, String.valueOf(paramJson));

        replenishAmountObject(replenishAmountCallback);


    }
    private void replenishAmountObject(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getLoadMessengerV2API(getViewContext())
                .replenishAmount(replenishAuth,session,replenishParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  replenishAmountCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(replenishKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    String data = CommonFunctions.decryptAES256CBC(replenishKey,response.body().getData());
                    showGlobalDialogs("Amount Successfully replenished", "Close", ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.SUCCESS, false);
                    fb_amountreplenish.setText("");
                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorToast();
            t.printStackTrace();
        }
    };


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fb_setAdmin: {
                String message = "";
                boolean b = fb_setAdmin.isChecked();
                if (b) {
                    isAdmin = 1;
                    message = "Set " + borrowerFB.getFBName() + " as Admin?";
                } else {
                    isAdmin = 0;
                    message = "Remove Admin config for " + borrowerFB.getFBName();
                }
                showDialog("Admin Config", message);
                break;
            }
            case R.id.fb_deactivateAccount:{
                String message = "";
                boolean b = fb_deactivateAccount.isChecked();
                if(b){
                    deactivateAccount = true;
                    message = "Deactivate "+borrowerFB.getFBName()+ "?";
                }else{
                    deactivateAccount = false;
                    message = "Activate "+borrowerFB.getFBName()+ "?";
                }
                showDialog("Account Status",message);
            }
            break;
            case R.id.fb_replenishBtn: {

                replenishAmount = fb_amountreplenish.getText().toString();
                if(replenishAmount.isEmpty() || Integer.parseInt(replenishAmount) <= 0){
                    fb_amountreplenish.setError("Invalid Input.");
                }else{
                    switchCmdType = "REPLENISH";
                    showDialog("Replenish Amount","Replenish "+CommonFunctions.currencyFormatter(replenishAmount)+" to "+borrowerFB.getFBName()+"?");
                }

                break;
            }
            case R.id.fb_viewLoadTxn:{
                Intent intent = new Intent(getViewContext(),ViewLoadTransactionsActivity.class);
                intent.putExtra("borrowerFB",new Gson().toJson(borrowerFB));
                startActivity(intent);
            }

        }
    }
}