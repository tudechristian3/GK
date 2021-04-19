package com.goodkredit.myapplication.activities.egame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EGameConfirmationActivity extends BaseActivity {

    private TextView txv_mobileno;
    private TextView txv_productcode;
    private TextView txv_amount;
    private TextView txv_resellerdiscount;
    private TextView txv_amounttopay;
    private TextView txv_amounttendered;
    private TextView txv_change;

    //local variables
    private String mobileno;
    private String productcode;
    private String vouchersession;
    private double longitude;
    private double latitude;
    private int hasdiscount;
    private double amount;
    private double amounttendered;
    private double resellerdiscount;
    private double amounttopay;

    //api parameters
    private String imei;
    private String userid;
    private String borrowerid;
    private String sessionid;
    private String servicecode;
    private String year;
    private String month;

    //VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private int currentdelaytime = 0;
    private int totaldelaytime = 10000;
    private String transactionno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egame_confirmation);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.confirm){
            processEGameConsummation();

        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        setupToolbarWithTitle("");

        txv_mobileno = findViewById(R.id.txv_mobileno);
        txv_productcode = findViewById(R.id.txv_productcode);
        txv_amount = findViewById(R.id.txv_amount);
        txv_resellerdiscount = findViewById(R.id.txv_resellerdiscount);
        txv_amounttopay = findViewById(R.id.txv_amounttopay);
        txv_amounttendered = findViewById(R.id.txv_amounttendered);
        txv_change = findViewById(R.id.txv_change);
    }

    private void initData() {
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), EGameActivity.KEY_EGAME_SERVICE_CODE);

        year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        if(Integer.valueOf(month) < 10){
            month = "0" + month;
        }

        //get passed value from payments
        Bundle b = getIntent().getExtras();

        try{

            if(b!=null){
                amount = b.getDouble("originalamount");
                amounttendered = b.getDouble("amounttendered");
                resellerdiscount = b.getDouble("resellerdiscount");
                amounttopay = b.getDouble("amounttopay");
                double change = amounttendered - amounttopay;

                vouchersession = b.getString("vouchersession");
                mobileno = b.getString("mobileno");
                productcode = b.getString("productcode");
                hasdiscount = b.getInt("hasdiscount");
                longitude = b.getDouble("longitude");
                latitude = b.getDouble("latitude");

                txv_mobileno.setText("+".concat(mobileno));
                txv_productcode.setText(b.getString("productcode"));
                txv_amount.setText(CommonFunctions.currencyFormatter(String.valueOf(amount)));
                txv_amounttendered.setText(CommonFunctions.currencyFormatter(String.valueOf(amounttendered)));
                txv_resellerdiscount.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerdiscount)));

                txv_amounttopay.setText(CommonFunctions.currencyFormatter(String.valueOf(amounttopay)));
                txv_change.setText(CommonFunctions.currencyFormatter(String.valueOf(change)));

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void processEGameConsummation() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("vouchersession", vouchersession);
            parameters.put("mobile", mobileno);
            parameters.put("productcode", productcode);
            parameters.put("amount", String.valueOf(amounttopay));
            parameters.put("hasdiscount", String.valueOf(hasdiscount));
            parameters.put("servicecode", servicecode);
            parameters.put("grossamount", String.valueOf(amount));
            parameters.put("longitude", String.valueOf(longitude));
            parameters.put("latitude", String.valueOf(latitude));
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index =CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "processEGameConsummation");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            processEGameConsummationObject(processEGameConsummationSession);
        } else{
            showNoInternetToast();
        }
    }

    private void processEGameConsummationObject( Callback<GenericResponse> processEGameConsummationCallback){
        setUpProgressLoader();
        setUpProgressLoaderMessageDialog("Processing request... Please wait.");
        Call<GenericResponse> processegameconsummation = RetrofitBuilder.geteGameAPI(getViewContext())
                .processEGameConsummationCall(
                        authenticationid, sessionid, param
                );
        processegameconsummation.enqueue(processEGameConsummationCallback);
    }

    private final Callback<GenericResponse> processEGameConsummationSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {

                    String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            //check if consummation success - call 10 times if not yet success
                            transactionno = decryptedData;
                            Logger.debug("vanhttp", "decrypted transactionno: " + transactionno);
                            checkEGameTransactionStatus();
                        }

                    }  else {
                        setUpProgressLoaderDismissDialog();
                        if (response.body().getStatus().equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            showToast(decryptedMessage, GlobalToastEnum.WARNING);

                        }

                    }
                } else {
                    setUpProgressLoaderDismissDialog();
                    showErrorGlobalDialogs();
                }
            } catch (Exception e){
                e.printStackTrace();
                setUpProgressLoaderDismissDialog();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            showErrorToast();
        }
    };

    private void checkEGameTransactionStatus(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            setUpProgressLoader();
            setUpProgressLoaderMessageDialog("Checking request... Please wait.");
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("transactionno", transactionno);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index =CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "checkEGameTransactionStatus");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            checkEGameTransactionStatusObject(checkEGameTransactionStatusSession);
        } else{
            showNoInternetToast();
        }
    }


    private void checkEGameTransactionStatusObject( Callback<GenericResponse> processEGameConsummationCallback){

        Call<GenericResponse> processegameconsummation = RetrofitBuilder.geteGameAPI(getViewContext())
                .checkEGameTransactionStatusCall(
                        authenticationid, sessionid, param
                );
        processegameconsummation.enqueue(processEGameConsummationCallback);
    }

    private final Callback<GenericResponse> checkEGameTransactionStatusSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {

                    String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                    if (response.body().getStatus().equals("000")) {
                        setUpProgressLoaderDismissDialog();
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            showSuccessDialog(decryptedMessage);
                        }

                    } else if(response.body().getStatus().equals("202")){
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            if(currentdelaytime < totaldelaytime){
                                //check transaction status here
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        currentdelaytime = currentdelaytime + 1000;
                                        //Do something after 1sec
                                        checkEGameTransactionStatus();
                                    }
                                }, 1000);
                            } else{
                                setUpProgressLoaderDismissDialog();

                                String passedmessage = "Your prepaid load transaction is still in process." +
                                        "You will receive a notification once it's done." + "\n" + "Thank you for using GoodKredit.";

                                showOnProcessDialog(passedmessage);
                            }
                        }
                    } else if (response.body().getStatus().equals("204")) {
                        setUpProgressLoaderDismissDialog();
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            showFailedDialog(decryptedMessage);
                        }
                    } else {
                        if (response.body().getStatus().equalsIgnoreCase("error")) {
                            setUpProgressLoaderDismissDialog();
                            showErrorToast();
                        } else {
                            setUpProgressLoaderDismissDialog();
                            showToast(decryptedMessage, GlobalToastEnum.WARNING);
                        }
                    }
                } else {
                    setUpProgressLoaderDismissDialog();
                    showErrorGlobalDialogs();
                }
            } catch (Exception e){
                e.printStackTrace();
                setUpProgressLoaderDismissDialog();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            showErrorToast();
        }
    };

    private void showSuccessDialog(String message) {

        //SHOW SUCCESS DIALOG
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("SUCCESS", message, "OK", "",
                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.SUCCESS);

        View closebtn = dialog.btnCloseImage();
        closebtn.setVisibility(View.GONE);

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getViewContext(), EGameActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void showFailedDialog(String message) {

        //SHOW FAILED DIALOG
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("FAILED", message, "Retry", "",
                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);

        View closebtn = dialog.btnCloseImage();
        closebtn.setVisibility(View.GONE);

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showProgressDialog(getViewContext(),"Processing Request.", "Please wait...");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressDialog();
                        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        Payment.paymentfinishActivity.finish();
                        finish();
                    }
                }, 2000);
            }
        });
    }

    private void showOnProcessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("ON PROCESS", message, "OK", "",
                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.ONPROCESS);

        View closebtn = dialog.btnCloseImage();
        closebtn.setVisibility(View.GONE);

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
