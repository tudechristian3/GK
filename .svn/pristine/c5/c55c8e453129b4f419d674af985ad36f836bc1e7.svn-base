package com.goodkredit.myapplication.activities.egame;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.egame.EGameProducts;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EGameActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_EGAME_SERVICE_CODE = "KEY_EGAME_SERVICE_CODE";

    private DatabaseHandler mdb;

    private EditText edt_countrycode;
    private EditText edt_mobileno;
    private EditText edt_productcode;
    private EditText edt_amount;
    private ImageView img_contacts;
    private Button btn_proceed;
    private LinearLayout btn_egame_transaction_history;
    private LinearLayout layout_amount;

    private EGameProducts egameProducts = null;

    private static final int PICK_CONTACT = 214;
    private static final int PICK_EGAME_PRODUCT = 100;

    //api parameters
    private String imei;
    private String borrowerid;
    private String userid;
    private String sessionid;

    //VARIABLES FOR SECURITY
    private String param;
    private String keyEncryption;
    private String authenticationid;
    private String index;

    private String servicecode;
    private String vouchersession;
    private String networkprefix;
    private String network;

    //check if reseller
    private double resellerDiscount = 0;
    private double resellerAmount = 0;
    private double resellerTotalAmount = 0;
    private int hasDiscount = 0;

    private GPSTracker gpsTracker;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egame);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
        initData();
    }

    private void init() {
        setupToolbarWithTitle("E-Games");

        edt_countrycode = findViewById(R.id.edt_countrycode);
        edt_mobileno = findViewById(R.id.edt_mobileno);
        edt_productcode = findViewById(R.id.edt_productcode);
        edt_amount = findViewById(R.id.edt_amount);
        img_contacts = findViewById(R.id.img_contacts);
        btn_proceed = findViewById(R.id.btn_proceed);
        btn_egame_transaction_history = findViewById(R.id.btn_egame_transaction_history);
        layout_amount = findViewById(R.id.layout_amount);

        img_contacts.setOnClickListener(this);
        edt_productcode.setOnClickListener(this);
        btn_egame_transaction_history.setOnClickListener(this);
        btn_proceed.setOnClickListener(this);

        edt_amount.setFocusable(false);
    }

    private void initData() {
        edt_countrycode.setText("+63");

        imei = PreferenceUtils.getImeiId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), KEY_EGAME_SERVICE_CODE);
        mdb = new DatabaseHandler(getViewContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_contacts: {

                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, PICK_CONTACT);

                break;
            }
            case R.id.edt_productcode: {

                Intent intent = new Intent(getViewContext(), EGameProductsActivity.class);
                startActivityForResult(intent, PICK_EGAME_PRODUCT);
                hideProgressDialog();

                break;
            }
            case R.id.btn_egame_transaction_history: {

                Intent intent = new Intent(getViewContext(), EGameTransactionsActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.btn_proceed: {

                if(!edt_mobileno.getText().toString().trim().isEmpty() && !edt_amount.getText().toString().trim().isEmpty()
                        && !edt_productcode.getText().toString().trim().isEmpty()){

                    if(edt_mobileno.getText().toString().trim().length() == 10){
                        networkprefix = edt_mobileno.getText().toString().trim().substring(0,3);
                        network = mdb.getNetworkPrefix(mdb, networkprefix);
                        getSession();
                    } else{
                        showToast("Invalid mobile number.", GlobalToastEnum.WARNING);
                    }
                }else{
                    showToast("Please fill all fields.", GlobalToastEnum.WARNING);
                }
                break;
            }
        }
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            isCalculateSession();
        } else{
            showNoInternetToast();
        }
    }

    private void isCalculateSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            CommonFunctions.showDialog(getViewContext(), "", "Processing. Please wait ...", false);
            calculateDiscountForReseller();
        } else {
            CommonFunctions.hideDialog();
            showNoInternetToast();
        }
    }

    private void calculateDiscountForReseller() {
        try{
            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
                gpsTracker = new GPSTracker(getViewContext());

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("merchantid",".");
                parameters.put("amountpaid",String.valueOf(Double.valueOf(edt_amount.getText().toString().trim())));
                parameters.put("servicecode", servicecode);
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

                Logger.debug("vanhttp", "security authid: " + authenticationid);
                calculateDiscountForResellerObject(calculateDiscountForResellerSession);
            } else{
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }
        }catch (Exception e){
            e.printStackTrace();
            CommonFunctions.hideDialog();
            showErrorToast();
        }
    }

    private void calculateDiscountForResellerObject(Callback<GenericResponse> calculateDiscountForResellerCallback) {
        Call<GenericResponse> resellerdiscount = RetrofitBuilder.getCommonV2API(getViewContext())
                .calculateDiscountForResellerV3(authenticationid,sessionid,param);
        resellerdiscount.enqueue(calculateDiscountForResellerCallback);
    }

    private final Callback<GenericResponse> calculateDiscountForResellerSession = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            CommonFunctions.hideDialog();

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());

                if (response.body().getStatus().equals("000")) {

                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    Logger.debug("vanhttp", "calculatediscounte decrypted data: " + decryptedData);
                    if(decryptedData.equalsIgnoreCase("error") || decryptedMessage.equalsIgnoreCase("error")){
                        showErrorToast();
                    }else{
                        resellerAmount = Double.valueOf(edt_amount.getText().toString().trim());
                        resellerDiscount = Double.valueOf(decryptedData);
                        resellerTotalAmount = Double.valueOf(edt_amount.getText().toString().trim()) - resellerDiscount;

                        if (resellerDiscount > 0) {
                            showDiscountDialog();
                        } else {
                            callPrePurchase();
                            hasDiscount = 0;
                        }
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

                                    resellerAmount = Double.valueOf(edt_amount.getText().toString().trim());
                                    resellerDiscount = 0;
                                    resellerTotalAmount = Double.valueOf(edt_amount.getText().toString().trim()) - resellerDiscount;
                                    hasDiscount = 0;

                                    callPrePurchase();

                                }
                            })
                            .show();

                } else {
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
                    dialog.dismiss();
                    hasDiscount = 1;
                    callPrePurchase();
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callPrePurchase() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("amountpurchase", edt_amount.getText().toString().trim());
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index =CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "prePurchaseV3");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            prePurchaseV3Object(prePurchaseV3Session);

        } else {
            showNoInternetToast();
        }
    }

    private void prePurchaseV3Object( Callback<GenericResponse> prePurchaseV3Callback){
        Call<GenericResponse> prepurchasev2 = RetrofitBuilder.getCommonV2API(getViewContext())
                .prePurchaseV3(
                        authenticationid, sessionid, param
                );
        prepurchasev2.enqueue(prePurchaseV3Callback);
    }

    private final Callback<GenericResponse> prePurchaseV3Session = new Callback<GenericResponse>() {
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

                            vouchersession = CommonFunctions.parseJSON(decryptedData, "value");
                            if(!vouchersession.equals("")){
                                proceedtoPayments();
                            } else{
                                showToast(decryptedMessage, GlobalToastEnum.WARNING);
                            }
                        }

                    }  else {
                        if (response.body().getStatus().equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            showToast(decryptedMessage, GlobalToastEnum.WARNING);
                        }
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
        }
    };

    //PROCEEDING TO PAYMENTS
    private void proceedtoPayments() {
        hideProgressDialog();
        setUpProgressLoaderDismissDialog();

        double passedamount;
        if(hasDiscount == 1){
            passedamount = resellerTotalAmount;
        }else{
            passedamount = Double.valueOf(edt_amount.getText().toString().trim());
        }

        Intent intent = new Intent(getViewContext(), Payment.class);
        intent.putExtra("FROMEGAME", "EGAME");
        intent.putExtra("EGAMEAMOUNT", String.valueOf(passedamount));
        intent.putExtra("EGAMEPRODUCTCODE", edt_productcode.getText().toString().trim());
        intent.putExtra("EGAMEMOBILENO", "63".concat(edt_mobileno.getText().toString().trim()));
        intent.putExtra("EGAMERESELLERDISCOUNT", String.valueOf(resellerDiscount));
        intent.putExtra("EGAMERESELLERGROSSAMOUNT", String.valueOf(resellerAmount));
        intent.putExtra("EGAMEVOUCHERSESSION", vouchersession);
        intent.putExtra("EGAMEHASDISCOUNT", hasDiscount);
        intent.putExtra("EGAMELONGITUDE", gpsTracker.getLongitude());
        intent.putExtra("EGAMELATITUDE", gpsTracker.getLatitude());
        intent.putExtra("EGAMEORIGINALAMOUNT", edt_amount.getText().toString().trim());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT) {
            if(resultCode == RESULT_OK){
                contactpicked(data);
            }
        } else if(requestCode == PICK_EGAME_PRODUCT){
            if(resultCode == RESULT_OK){
                egameProducts = data.getParcelableExtra(EGameProducts.KEY_EGAMEPRODUCT_RESULT);

                edt_productcode.setText(CommonFunctions.replaceEscapeData(egameProducts.getProductCode()));
                edt_amount.setText(CommonFunctions.currencyFormatter(String.valueOf(egameProducts.getAmount())));
                layout_amount.setVisibility(View.VISIBLE);
            } else{

                if(edt_productcode.getText().toString().trim().isEmpty()){
                    layout_amount.setVisibility(View.GONE);
                } else{
                    layout_amount.setVisibility(View.VISIBLE);
                }

            }
        }
    }

    private void contactpicked(Intent data) {
        Cursor cursor = null;
        try{
            String phoneNo = null;
            String name = null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo = cursor.getString(phoneIndex);
            edt_mobileno.setText(CommonFunctions.userMobileNumber(phoneNo, false));
            edt_mobileno.setSelection(edt_mobileno.getText().length());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
