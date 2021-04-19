package com.goodkredit.myapplication.activities.payviaqrcode;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Merchant;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.generateqr.DialogGenerateQRFragment;
import com.goodkredit.myapplication.model.MerchantPayExpress;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 1/4/2017.
 */
public class PayViaQRCodeActivity extends BaseActivity implements View.OnClickListener {

    private final String DB_COL_BORROWERID = "borrowerid";
    private final String DB_COL_MOBILE = "mobile";
    private final String DB_COL_IMEI = "imei";

    private final String JSON_KEY_MERCHANTID = "merchantID";
    private final String JSON_KEY_BRANCHID = "branchID";
    private final String JSON_KEY_SECURITY = "securityKey";

    private CompoundBarcodeView bc;
    private Button btnGenerateQrCode;
    private Merchant merchant;
    private CommonFunctions cf;

    static String mID = null;
    static String bID = null;
    static String secKey = null;

    private DatabaseHandler db;

    private BeepManager beepManager;

    private String sessionid = "";

    //New Variables for SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_via_qrcode);
        try {
            setupToolbar();
            db = new DatabaseHandler(this);
            beepManager = new BeepManager(this);
            bc = findViewById(R.id.barcode);
            btnGenerateQrCode = findViewById(R.id.btnGenerateQrCode);

            btnGenerateQrCode.setOnClickListener(this);

            sessionid = PreferenceUtils.getSessionID(getViewContext());

            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borrowerid = cursor.getString(cursor.getColumnIndex(DB_COL_BORROWERID));
                    userid = cursor.getString(cursor.getColumnIndex(DB_COL_MOBILE));
                    imei = cursor.getString(cursor.getColumnIndex(DB_COL_IMEI));
                } while (cursor.moveToNext());
            }
            cursor.close();
            scanQRCode();

        } catch (Exception e) {
            Toast.makeText(getViewContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void scanQRCode() {
        try {
            bc.decodeSingle(new BarcodeCallback() {
                @Override
                public void barcodeResult(BarcodeResult result) {
                    try {
                        bc.pause();
                        beepManager.playBeepSoundAndVibrate();

                        if (!result.toString().contentEquals("")) {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            String mID = jsonObject.getString(JSON_KEY_MERCHANTID);
                            String bID = jsonObject.getString(JSON_KEY_BRANCHID);
                            String secKey = null;
                            if (result.toString().contains(JSON_KEY_SECURITY))
                                secKey = jsonObject.getString(JSON_KEY_SECURITY);

                            PayViaQRCodeActivity.mID = mID;
                            PayViaQRCodeActivity.bID = bID;

                            if (secKey == null)
                                secKey = ".";

                            PayViaQRCodeActivity.secKey = secKey;
                            validateQRPartialV2();

                        } else {
                            showToast("Something went wrong. Please check the QR code.", GlobalToastEnum.NOTICE);
                            final Handler scanBCHandler = new Handler();
                            scanBCHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    bc.resume();
                                    scanQRCode();
                                }
                            }, 1000);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showToast("Something went wrong. Please check the QR code.", GlobalToastEnum.NOTICE);
                        final Handler scanBCHandler = new Handler();
                        scanBCHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bc.resume();
                                scanQRCode();
                            }
                        }, 1000);
                    }
                }

                @Override
                public void possibleResultPoints(List<ResultPoint> resultPoints) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(getViewContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        bc.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        bc.pause();
        if (mDialog != null) {
            mDialog.dismiss();
        }
        super.onPause();
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

    private void validateQRPartialV2() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("merchantid", CommonFunctions.getQRDataElement(mID));
            parameters.put("branchid",  CommonFunctions.getQRDataElement(bID));
            parameters.put("securitykey",  CommonFunctions.getQRDataElement(secKey));

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "validateQRPartial");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            validateQRCode(validateQRCodeCallback);

        } else {
            hideProgressDialog();
            showNoInternetToast();
            final Handler scanBCHandler = new Handler();
            scanBCHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bc.resume();
                    scanQRCode();
                }
            }, 1000);
        }
    }

    private void validateQRCode(Callback<GenericResponse> validateQRPartial) {
        Call<GenericResponse> call = RetrofitBuilder.getPayByQRCodeV2API(getViewContext())
                .validateQRPartial(
                      authenticationid,sessionid,param
                );
          call.enqueue(validateQRPartial);
    }

    private Callback<GenericResponse> validateQRCodeCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errBody = response.errorBody();
            if (errBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<MerchantPayExpress> payExpresses = new Gson().fromJson(decryptedData, new TypeToken<List<MerchantPayExpress>>(){}.getType());
                    for (MerchantPayExpress merchantPayExpress : payExpresses) {
                        if (merchantPayExpress.getBranchID().equals(bID)
                                && merchantPayExpress.getMerchantID().equals(mID)
                                && merchantPayExpress.getSecurityKey().equals(secKey)) {
                            validateQRData();
                        }
                    }
                } else {
                    final Handler scanBCHandler = new Handler();
                    scanBCHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bc.resume();
                            scanQRCode();
                        }
                    }, 1000);
                    hideProgressDialog();
                    showError(decryptedMessage);
                }
            } else {
                final Handler scanBCHandler = new Handler();
                scanBCHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bc.resume();
                        scanQRCode();
                    }
                }, 1000);
                hideProgressDialog();
                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            final Handler scanBCHandler = new Handler();
            scanBCHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bc.resume();
                    scanQRCode();
                }
            }, 1000);
            hideProgressDialog();
            showError();
        }
    };

    private void validateQRData() {

        LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
        parameters.put("imei",imei);
        parameters.put("userid",userid);
        parameters.put("borrowerid",borrowerid);
        parameters.put("merchantid",mID);
        parameters.put("branchid",  bID);
        parameters.put("securitykey", secKey);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "validateQRDataV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        Call<GenericResponse> call = RetrofitBuilder.getPayByQRCodeV2API(getViewContext())
                .validateQRDataV2(
                     authenticationid,sessionid,param
                );

        call.enqueue(validateQRDataCallback);
    }

    private Callback<GenericResponse> validateQRDataCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            ResponseBody errBody = response.errorBody();
            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    MerchantPayExpress payExpress = new Gson().fromJson(decryptedData,MerchantPayExpress.class);
                    merchant = new Merchant(payExpress.getMerchantName(), mID, payExpress.getBranchName(), bID, payExpress.getMerchantLogo());
                    Intent intent = new Intent(getApplicationContext(), PayMerchantActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("MerchantObjectFromQR", merchant);
                    intent.putExtra("FROMMERCHANTEXPRESS", "FROMMERCHANTEXPRESS");
                    finish();
                    startActivity(intent);
                } else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else {
                    final Handler scanBCHandler = new Handler();
                    scanBCHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bc.resume();
                            scanQRCode();
                        }
                    }, 1000);
                    showError(decryptedMessage);
                }
            } else {
                final Handler scanBCHandler = new Handler();
                scanBCHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bc.resume();
                        scanQRCode();
                    }
                }, 1000);
                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            final Handler scanBCHandler = new Handler();
            scanBCHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bc.resume();
                    scanQRCode();
                }
            }, 1000);
            hideProgressDialog();
            showError();
        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnGenerateQrCode){
            new DialogGenerateQRFragment().show(
                    getSupportFragmentManager(), DialogGenerateQRFragment.TAG);
        }
    }
}
