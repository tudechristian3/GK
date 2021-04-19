package com.goodkredit.myapplication.activities.payviaqrcode;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Merchant;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.service.newAPICalls.CommonV2API;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.DiscountResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 1/4/2017.
 */

public class PayMerchantActivity extends BaseActivity implements View.OnClickListener {

    private Intent intent;
    private Merchant merchant;

    private MaterialEditText merchantName;
    private MaterialEditText branchName;

    private ImageView merchantLogo;
    private TextView merchantLogoText;

    private EditText amountEDT;

    private Button cancel;
    private Button pay;

    private CommonFunctions cf;

    private Toolbar toolbar;

    //COMMON
    private String sessionid;
    private String borrowerid;
    private String userid;
    private String imei;

    private DatabaseHandler db;

    private String amount;

    private String source;

    private AQuery aq;

    private double resellerDiscount = 0;
    private double resellerAmount = 0;
    private double resellerTotalAmount = 0;

    private boolean isResellerDiscount = false;
    private boolean isPrePurchase = false;
    private String authcode;

    //NEW VARIABLES FOR SECURITY UPDATE
    //for calculate discount for reseller
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    //for prepurchase
    private String prePurchaseIndex;
    private String prePurchaseAuthenticationid;
    private String prePurchaseKeyEncryption;
    private String prePurchaseParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_merchant);
        try {

            aq = new AQuery(this);
            db = new DatabaseHandler(this);
            setupToolbar();

            merchantName = findViewById(R.id.merchantNameMet);
            branchName = findViewById(R.id.branchNameMet);
            amountEDT = findViewById(R.id.amountEDT);

            merchantLogo = findViewById(R.id.merchantLogo);
            merchantLogoText = findViewById(R.id.merchantLogoText);

            cancel = findViewById(R.id.payCancel);
            pay = findViewById(R.id.payPay);
            pay.setEnabled(false);
            pay.setBackgroundResource(R.drawable.buttongray);

            cancel.setOnClickListener(this);
            pay.setOnClickListener(this);

            intent = getIntent();
            merchant = (Merchant) intent.getSerializableExtra("MerchantObjectFromQR");
            source = intent.getStringExtra("FROMMERCHANTEXPRESS");

            sessionid = PreferenceUtils.getSessionID(getViewContext());

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


            merchantName.setText(merchant.getMerchantName());
            branchName.setText(merchant.getBranchName());

            if (merchant.getMerchantLogo().contentEquals(".") || merchant.getMerchantLogo().contentEquals("")) {
                merchantLogoText.setText(merchant.getMerchatInitials());
            } else {
                merchantLogo.setBackgroundColor(Color.TRANSPARENT);
                merchantLogoText.setVisibility(View.GONE);
                aq.id(merchantLogo).image(merchant.getMerchantLogo(), false, true);
            }


            amountEDT.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 0) {
                        pay.setEnabled(true);
                        pay.setBackgroundResource(R.drawable.button);
                    } else {
                        pay.setEnabled(false);
                        pay.setBackgroundResource(R.drawable.buttongray);

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case android.R.id.home:
            case R.id.payCancel: {
                finish();
                break;
            }
            case R.id.payPay: {
                amount = amountEDT.getText().toString();
                try {

                    if (!amount.contentEquals("")) {
                        if (amount.contentEquals(".")) {
                           amountEDT.setError("Please input valid amount.");
                           amountEDT.requestFocus();
                        } else {
                            if (Double.parseDouble(amount) > 0) {
                                isResellerDiscount = true;
                                isPrePurchase = false;
                                createSession();
                            } else {
                                amountEDT.setError("Please input valid amount.");
                                amountEDT.requestFocus();
                            }
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }

    private void createSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            CommonFunctions.showDialog(this, "", "Please wait ...", false);
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            if (isResellerDiscount) {
               // calculateDiscountForReseller(calculateDiscountForResellerSession);
                calculateDiscountForResellerV2();
            } else if (isPrePurchase) {
                //new HttpAsyncTask().execute(CommonVariables.PREPURCHASE);
                prePurchaseV2();

            }
        } else {
            CommonFunctions.hideDialog();
            showNoInternetToast();
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        CommonFunctions cf;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            try {

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("amountpurchase", String.valueOf(resellerTotalAmount));
                jsonObject.accumulate("borrowerid", borrowerid);

                Log.i("JSON  cancel", json);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

                Log.v("TOSERVER", json);

            } catch (Exception e) {
                e.printStackTrace();
                CommonFunctions.hideDialog();
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 3. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            CommonFunctions.hideDialog();
            try {
                if (result == null) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("001")) {
                    CommonFunctions.hideDialog();
                    showToast("Invalid entry.", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    CommonFunctions.hideDialog();
                    showToast("Invalid authentication.", GlobalToastEnum.NOTICE);
                } else if (result.equals("003")) {
                    CommonFunctions.hideDialog();
                    showToast("Session expired. Please restart transaction.", GlobalToastEnum.NOTICE);
                } else if (result.equals("004")) {
                    CommonFunctions.hideDialog();
                    showToast("Invalid QR Code/Data. Please try again.", GlobalToastEnum.WARNING);
                } else if (result.equals("error")) {
                    showToast("Connection error. Please check.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    proceedMainPage(result);
                    Logger.debug("FROMSERVER", result);
                }
            } catch (Exception e) {
                showToast("Application error. Please restart the application.", GlobalToastEnum.NOTICE);
                e.printStackTrace();
            }
        }

    }

    private void proceedMainPage(String _result) {

        try {
            Intent intent = new Intent(getApplicationContext(), Payment.class);
            intent.putExtra("MOBILE", userid);
            intent.putExtra("VOUCHERSESSION", _result);
            intent.putExtra("AMOUNT", String.valueOf(resellerTotalAmount));
            intent.putExtra("FROMMERCHANTEXPRESS", source);
            intent.putExtra("MERCHANT", merchant);
            intent.putExtra("GROSSAMOUNT", amount);
            intent.putExtra("PAYVIAQRRESELLERDISCOUNT", String.valueOf(resellerDiscount));
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateDiscountForReseller(Callback<DiscountResponse> calculateDiscountForResellerCallback) {
        GPSTracker gpsTracker = new GPSTracker(getViewContext());
        Call<DiscountResponse> resellerdiscount = RetrofitBuild.getDiscountService(getViewContext())
                .calculateDiscountForReseller(userid,
                        imei,
                        authcode,
                        sessionid,
                        borrowerid,
                        merchant.getMerchantID(),
                        Double.valueOf(amount),
                        "PAYVIAQR",
                        String.valueOf(gpsTracker.getLongitude()),
                        String.valueOf(gpsTracker.getLatitude()));
        resellerdiscount.enqueue(calculateDiscountForResellerCallback);
    }

    private final Callback<DiscountResponse> calculateDiscountForResellerSession = new Callback<DiscountResponse>() {

        @Override
        public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {

            CommonFunctions.hideDialog();

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    resellerAmount = Double.valueOf(amount);
                    resellerDiscount = response.body().getDiscount();
                    resellerTotalAmount = Double.valueOf(amount) - resellerDiscount;

                    if (resellerDiscount > 0) {

                        showDiscountDialog();

                    } else {

                        isPrePurchase = true;
                        isResellerDiscount = false;
                        createSession();

                    }

                } else if (response.body().getStatus().equals("005")) {

                    new MaterialDialog.Builder(getViewContext())
                            .content(response.body().getMessage())
                            .cancelable(false)
                            .negativeText("Cancel")
                            .positiveText("OK")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    resellerAmount = Double.valueOf(amount);
                                    resellerDiscount = 0;
                                    resellerTotalAmount = Double.valueOf(amount) - resellerDiscount;

                                    isPrePurchase = true;
                                    isResellerDiscount = false;
                                    createSession();
                                }
                            })
                            .dismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {

//                                    isPrePurchase = false;
//                                    isResellerDiscount = true;

                                }
                            })
                            .show();

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<DiscountResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showDiscountDialog() {

        try {
            TextView popok;
            TextView popcancel;
            TextView popamountpaid;
            TextView popservicecharge;
            TextView poptotalamount;

            final Dialog dialog = new Dialog(new ContextThemeWrapper(getViewContext(), android.R.style.Theme_Holo_Light));
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_discount_dialog);

            popamountpaid = dialog.findViewById(R.id.popamounttopayval);
            popservicecharge = dialog.findViewById(R.id.popservicechargeval);
            poptotalamount = dialog.findViewById(R.id.poptotalval);
            popok = dialog.findViewById(R.id.popok);
            popcancel = dialog.findViewById(R.id.popcancel);

            //set value
            popamountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerAmount)));
            popservicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerDiscount)));
            poptotalamount.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerTotalAmount)));


            dialog.show();

            //click close
            popcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }

            });

            popok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    isPrePurchase = true;
                    isResellerDiscount = false;
                    createSession();

                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * **/
    private void calculateDiscountForResellerV2(){
        try{

            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
                GPSTracker gpsTracker = new GPSTracker(getViewContext());

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("merchantid",merchant.getMerchantID());
                parameters.put("amountpaid", String.valueOf(Double.valueOf(amount)));
                parameters.put("servicecode","PAYVIAQR");
                parameters.put("longitude",String.valueOf(gpsTracker.getLongitude()));
                parameters.put("latitude",String.valueOf(gpsTracker.getLatitude()));
                parameters.put("devicetype",CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "calculateDiscountForResellerV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                calculateDiscountForResellerObjectV2(calculateDiscountForResellerSessionV2);

            }else{
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }

        }catch (Exception e){
            CommonFunctions.hideDialog();
            showErrorToast();
            e.printStackTrace();
        }
    }

    private void calculateDiscountForResellerObjectV2(Callback<GenericResponse> calculateDiscountForResellerCallback) {
        Call<GenericResponse> resellerdiscount = RetrofitBuilder.getCommonV2API(getViewContext())
                .calculateDiscountForResellerV3(authenticationid,sessionid,param);
        resellerdiscount.enqueue(calculateDiscountForResellerCallback);
    }

    private final Callback<GenericResponse> calculateDiscountForResellerSessionV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            CommonFunctions.hideDialog();

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    Logger.debug("okhttp","CALCULATE DISCOUNT :::::::: "+decryptedData);
                    resellerAmount = Double.parseDouble(amount);
                    resellerDiscount = Double.parseDouble(decryptedData);
                    resellerTotalAmount = Double.parseDouble(amount) - resellerDiscount;

                    if (resellerDiscount > 0) {
                        showDiscountDialog();
                    } else {
                        isPrePurchase = true;
                        isResellerDiscount = false;
                        createSession();
                    }

                } else if (response.body().getStatus().equals("500")) {

                    new MaterialDialog.Builder(getViewContext())
                            .content(response.body().getMessage())
                            .cancelable(false)
                            .negativeText("Cancel")
                            .positiveText("OK")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    resellerAmount = Double.valueOf(amount);
                                    resellerDiscount = 0;
                                    resellerTotalAmount = Double.valueOf(amount) - resellerDiscount;

                                    isPrePurchase = true;
                                    isResellerDiscount = false;
                                    createSession();

                                }
                            })
                            .dismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                }
                            })
                            .show();

                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else {
                    showError(decryptedMessage);
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
      };


    // PREPURCHASE
    private void prePurchaseV2(){
        try{

            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("amountpurchase", String.valueOf(resellerTotalAmount));
                parameters.put("devicetype",CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                prePurchaseIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", prePurchaseIndex);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                prePurchaseAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                prePurchaseKeyEncryption = CommonFunctions.getSha1Hex(prePurchaseAuthenticationid + sessionid + "prePurchaseV3");
                prePurchaseParam = CommonFunctions.encryptAES256CBC(prePurchaseKeyEncryption, String.valueOf(paramJson));

                prePurchaseV2Object(prePurchaseV2Callback);

            }else{
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }

        }catch (Exception e){
            CommonFunctions.hideDialog();
            showErrorToast();
            e.printStackTrace();
        }
    }

    private void prePurchaseV2Object(Callback<GenericResponse> prepurchase) {
        Call<GenericResponse> prePurchaseV2 = RetrofitBuilder.getCommonV2API(getViewContext())
                .prePurchaseV3(prePurchaseAuthenticationid,sessionid,prePurchaseParam);
        prePurchaseV2.enqueue(prepurchase);
    }

    private final Callback<GenericResponse> prePurchaseV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            CommonFunctions.hideDialog();

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(prePurchaseKeyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(prePurchaseKeyEncryption,response.body().getData());
                    Logger.debug("okhttp","TXNOOOOOO ::::::::::::: "+decryptedData);
                    proceedMainPage(CommonFunctions.parseJSON(decryptedData,"value"));
                } else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else {
                    showError(decryptedMessage);
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };


}
