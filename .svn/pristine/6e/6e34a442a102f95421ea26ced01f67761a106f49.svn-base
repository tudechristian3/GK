package com.goodkredit.myapplication.activities.promo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.fairchild.FairChildAttendanceActivity;
import com.goodkredit.myapplication.activities.fairchild.FairChildDeadEndActivity;
import com.goodkredit.myapplication.activities.fairchild.FairChildVoteInstructionsActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.PromoQRDetails;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.database.fairchild.FairChildMembersDB;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.model.fairchild.FairChildMembers;
import com.goodkredit.myapplication.responses.fairchild.FairChildMembersResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.promo.RedeemPromoQRResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 12/15/2017.
 */

public class ScanPromoActivity extends BaseActivity {

    private CompoundBarcodeView bc;
    private BeepManager beepManager;

    private DatabaseHandler mdb;

    private String promoId;
    private String gender;
    private String latitude;
    private String longitude;
    private String userguarantorid;

    private V2Subscriber mSubscriber;
    private GPSTracker gpsTracker;

    private MaterialDialog mDialog;

	//CHECK STATUS
    private String ordertxnno = "";
    private boolean isStatusChecked = false;
    private int totaldelaytime = 10000;
    private int currentdelaytime = 0;

    private String statusfrom = "";
	
    private String sessionid;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String releaseKey;
    private String releaseIndex;
    private String releaseAuth;
    private String releaseParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_promo);
        init();
    }

    private void init() {
        mdb = new DatabaseHandler(getViewContext());

        sessionid = PreferenceUtils.getSessionID(getViewContext());
        //get account information
        Cursor cursor = mdb.getAccountInformation(mdb);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));
                userguarantorid = cursor.getString(cursor.getColumnIndex("guarantorid"));
            } while (cursor.moveToNext());
        }


        mSubscriber = mdb.getSubscriber(mdb);
        gender = mSubscriber.getGender().toUpperCase();

        gpsTracker = new GPSTracker(getViewContext());
        latitude = String.valueOf(gpsTracker.getLatitude());
        longitude = String.valueOf(gpsTracker.getLongitude());

        beepManager = new BeepManager(this);
        bc = (CompoundBarcodeView) findViewById(R.id.barcode);
        setupToolbar();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA}, 6325);
        } else {
            if (isGPSEnabled(getViewContext())) {
                scanQRCode();
            } else {
                //open settings
                gpsNotEnabledDialog();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            switch (requestCode) {
                case 6325: {
                    if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults.length > 0
                            && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                        if (isGPSEnabled(getViewContext())) {
                            scanQRCode();
                        } else {
                            //open settings
                            gpsNotEnabledDialog();
                        }
                    } else if (grantResults.length > 0
                            && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                        showToast("Permission to use camera must be enabled to redeem promo.", GlobalToastEnum.NOTICE);
                        finish();
                    } else if (grantResults.length > 0
                            && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        showToast("Permission to use current location must be enabled to redeem promo.", GlobalToastEnum.NOTICE);
                        finish();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gpsNotEnabledDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .content("GPS is disabled in your device.\nWould you like to enable it?")
                .cancelable(false)
                .positiveText("Go to Settings")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        showToast("GPS must be enabled to redeem promo.", GlobalToastEnum.NOTICE);
                        finish();
                    }
                })
                .show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mDialog.getView());
    }


    private void scanQRCode() {
        try {

            bc.decodeSingle(new BarcodeCallback() {

                @Override
                public void barcodeResult(BarcodeResult result) {
                    try {
                        bc.pause();
                        beepManager.playBeepSoundAndVibrate();
                        JSONObject obj = new JSONObject(String.valueOf(result));

                        Log.d("checkhttp","HELLO WORLD AKO SI BUDOY" + new Gson().toJson(obj));

                        if (obj.has("PromoID")) {
                            guarantorid = obj.getString("GuarantorID");
                            promoId = obj.getString("PromoID");
                            showProgressDialog(getViewContext(), "Redeeming promo.", "Please wait.");
                            getSession();
                            //createSession(getSessionCallback);
                        } else {
                            hideProgressDialog();
                            showError("You have scanned an invalid promo code. Please try again.");
                            scanAgain();
                        }
                    } catch (Exception e) {
                        Log.d("checkhttp","HELLO WORLD AKO SI MATTT" + result);

                        e.printStackTrace();
                        e.getLocalizedMessage();
                        hideProgressDialog();
                        showError("You have scanned an invalid promo code. Please try again.");
                        scanAgain();
                    }
                }

                @Override
                public void possibleResultPoints(List<ResultPoint> resultPoints) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scanAgain() {
        try {
            final Handler scanBCHandler = new Handler();
            scanBCHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bc.resume();
                    scanQRCode();
                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showFairChildSuccessDialog() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("SUCCESS", "Your attendance has been recorded " +
                        "and you get <b> FREE GoodKredit voucher(s).</b> " +
                        "Please proceed to the voting area to elect your chosen candidate. " +
                        "Thank you for your presence!"
                , "Close", "", ButtonTypeEnum.SINGLE,
                false, true, DialogTypeEnum.SUCCESS);

        dialog.showContentTitle();
        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
                FairChildAttendanceActivity.finishfairChildAttendanceActivity.finish();

                if(statusfrom.equals("NOTYETSTARTED")) {
                    statusfrom = "";
                    Bundle args = new Bundle();
                    args.putString("FROM","NOTYETSTARTED");
                    FairChildDeadEndActivity.start(getViewContext(),  args);
                } else if(statusfrom.equals("ALREADYENDED")) {
                    statusfrom = "";
                    Bundle args = new Bundle();
                    args.putString("FROM","ALREADYENDED");
                    FairChildDeadEndActivity.start(getViewContext(),  args);
                } else {
                    Intent intent = new Intent(getViewContext(), FairChildVoteInstructionsActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void showFairChildErrorGlobalDialogs(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", message
                , "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ScanPromoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        if (bc != null)
            bc.resume();
        if (mDialog != null && !mDialog.isShowing()) {
            if (!isGPSEnabled(getViewContext())) {
                gpsNotEnabledDialog();
            } else {
                scanQRCode();
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (bc != null)
            bc.pause();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan_promo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.action_view_history) {
            ScanPromoHistoryActivity.start(getViewContext());
        }
        return super.onOptionsItemSelected(item);
    }

    private Callback<String> getSessionCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (!response.body().isEmpty()
                            && !response.body().contains("<!DOCTYPE html>")
                            && !response.body().equals("001")
                            && !response.body().equals("002")
                            && !response.body().equals("003")
                            && !response.body().equals("004")
                            && !response.body().equals("error")) {
                        sessionid = response.body();
                        validatePromoQRCode();
                    } else {
                        hideProgressDialog();
                        scanAgain();
                        showError();
                    }
                } else {
                    hideProgressDialog();
                    scanAgain();
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            hideProgressDialog();
            showError();
            scanAgain();
        }
    };

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            //validatePromoQRCode();
            preValidatePromoQRCodeV2();
        } else {
            hideProgressDialog();
            scanAgain();
            showNoInternetToast();
        }
    }

    private void validatePromoQRCode() {
        String theGuarantorID = CommonFunctions.getQRDataElement(guarantorid);
        String thePromoID = CommonFunctions.getQRDataElement(promoId);
        Call<GenericResponse> call = RetrofitBuild.getPromoQRApiService(getViewContext())
                .preValidatePromoQRCode(
                        sessionid,
                        imei,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        userid,
                        borrowerid,
                        theGuarantorID,
                        thePromoID,
                        CommonFunctions.getQRDataElement(CommonFunctions.getSha1Hex(theGuarantorID + thePromoID))
                );

        call.enqueue(validatePromoQRCodeCallback);
    }

    private Callback<GenericResponse> validatePromoQRCodeCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    Logger.debug("okhttp","RESPONSE: "+new Gson().toJson(response));
                    if (response.body().getStatus().equals("000")) {
                        //releasePromoReward();
                        //createSession(getReleasePromoSessionCallback);
                        releasePromoRewardV2();
                    } else {
                        hideProgressDialog();
                        showError(response.body().getMessage());
                        scanAgain();
                    }

                } else {

                    hideProgressDialog();
                    showError();
                    scanAgain();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
            t.printStackTrace();
            Logger.debug("okhttp","RESPONSE: "+new Gson().toJson(t));
            scanAgain();
        }
    };

    private Callback<String> getReleasePromoSessionCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (!response.body().isEmpty()
                            && !response.body().contains("<!DOCTYPE html>")
                            && !response.body().equals("001")
                            && !response.body().equals("002")
                            && !response.body().equals("003")
                            && !response.body().equals("004")
                            && !response.body().equals("error")) {
                        sessionid = response.body();
                        releasePromoReward();
                    } else {
                        hideProgressDialog();
                        scanAgain();
                        showError();
                    }
                } else {
                    hideProgressDialog();
                    scanAgain();
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            hideProgressDialog();
            showError();
            scanAgain();
        }
    };

    private void releasePromoReward() {
        Call<RedeemPromoQRResponse> call = RetrofitBuild.getPromoQRApiService(getViewContext())
                .releasePromoReward(
                        sessionid,
                        imei,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        userid,
                        borrowerid,
                        guarantorid,
                        promoId,
                        gender,
                        latitude,
                        longitude,
                        CommonFunctions.getSha1Hex(guarantorid + promoId),
                        userguarantorid
                );

        call.enqueue(releasePromoRewardCallback);
    }

    private Callback<RedeemPromoQRResponse> releasePromoRewardCallback = new Callback<RedeemPromoQRResponse>() {
        @Override
        public void onResponse(Call<RedeemPromoQRResponse> call, Response<RedeemPromoQRResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        PromoQRDetails mPromoQRDetails = response.body().getPromoQRDetails();
                        Boolean isFairChild = PreferenceUtils.getBooleanPreference(getViewContext(), PreferenceUtils.KEY_FAIRCHILD_FROM);
                        if (isFairChild) {
                            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_FAIRCHILD_FROM);
                            callValidateFairChildMembers();
                        } else {
                            SuccessPromoActivity.start(getViewContext(), mPromoQRDetails);
                        }
                    } else {
                        showError(response.body().getMessage());
                        scanAgain();
                    }
                } else {
                    showError();
                    scanAgain();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<RedeemPromoQRResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
            scanAgain();
        }
    };

    private void callValidateFairChildMembers() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Processing request.", "Please wait.");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            validateFairChildMembers();
        } else {
            showWarningToast();
        }
    }

    private void validateFairChildMembers() {
        Call<FairChildMembersResponse> validateFairChildMemmber = RetrofitBuild.getFairChildAPIService(getViewContext())
                .validateFairChildMembers(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode
                );

        validateFairChildMemmber.enqueue(validateFairChildMembersCallBack);

    }

    private final Callback<FairChildMembersResponse> validateFairChildMembersCallBack =
            new Callback<FairChildMembersResponse>() {

                @Override
                public void onResponse(Call<FairChildMembersResponse> call, Response<FairChildMembersResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        hideProgressDialog();

                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                mdb.truncateTable(mdb, FairChildMembersDB.TABLE_FAIRCHILDMEMBERS);

                                List<FairChildMembers> fairChildMembersList = response.body().getFairChildMembersList();
                                for(FairChildMembers fairChildMembers : fairChildMembersList) {
                                    mdb.insertFairChildMembers(mdb, fairChildMembers);
                                }
                                showFairChildSuccessDialog();
                            } else if (response.body().getStatus().equals("202")) {
                                finish();
                                FairChildAttendanceActivity.finishfairChildAttendanceActivity.finish();
                                Bundle args = new Bundle();
                                args.putString("FROM","WHITELIST");
                                FairChildDeadEndActivity.start(getViewContext(),  args);
                            } else if (response.body().getStatus().equals("203")) {
                                finish();
                                FairChildAttendanceActivity.finishfairChildAttendanceActivity.finish();
                                Bundle args = new Bundle();
                                args.putString("FROM","ALREADYVOTED");
                                FairChildDeadEndActivity.start(getViewContext(),  args);
                            } else if (response.body().getStatus().equals("204")) {
                                statusfrom = "NOTYETSTARTED";
                                showFairChildSuccessDialog();
                            } else if (response.body().getStatus().equals("205")) {
                                statusfrom = "ALREADYENDED";
                                showFairChildSuccessDialog();
                            } else if (response.body().getStatus().equals("206")) {
                                finish();
                                FairChildAttendanceActivity.finishfairChildAttendanceActivity.finish();
                                Bundle args = new Bundle();
                                args.putString("FROM","NOTAMEMBER");
                                FairChildDeadEndActivity.start(getViewContext(),  args);
                            } else {
                                PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_PROMO_FAIR_STATUS, true);
                                showFairChildErrorGlobalDialogs(response.body().getMessage());
                                finish();
                            }
                        } else {
                            PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_PROMO_FAIR_STATUS, true);
                            showWarningToast();
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_PROMO_FAIR_STATUS, true);
                        showWarningToast();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<FairChildMembersResponse> call, Throwable t) {
                    t.printStackTrace();
                    PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_PROMO_FAIR_STATUS, true);
                    hideProgressDialog();
                    showWarningToast();
                    finish();
                }
            };


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */
    private void releasePromoRewardV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();

            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("promoid", promoId);
            parameters.put("guarantorid", guarantorid);
            parameters.put("securitykey", CommonFunctions.getSha1Hex(guarantorid + promoId));
            parameters.put("gender",gender);
            parameters.put("latitude", String.valueOf(latitude));
            parameters.put("longitude", String.valueOf(longitude));
            parameters.put("userguarantorid", userguarantorid);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            releaseIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", releaseIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            releaseAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
            releaseKey = CommonFunctions.getSha1Hex(releaseAuth + sessionid + "releasePromoRewardV2");
            releaseParam = CommonFunctions.encryptAES256CBC(releaseKey, String.valueOf(paramJson));

            releasePromoRewardV2Object(releasePromoRewardV2Callback);

        }else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private void releasePromoRewardV2Object(Callback<GenericResponse> releasePromoRewardV2Object) {

        Call<GenericResponse> call = RetrofitBuilder.getPromoQRCodeV2API(getViewContext())
                .releasePromoRewardV2(
                        releaseAuth,sessionid,releaseParam
                );
        call.enqueue(releasePromoRewardV2Object);
    }
    private Callback<GenericResponse> releasePromoRewardV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(releaseKey,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String data = CommonFunctions.decryptAES256CBC(releaseKey,response.body().getData());

                        List<PromoQRDetails> list =  new Gson().fromJson(data,new TypeToken<List<PromoQRDetails>>(){}.getType());
                        PromoQRDetails mPromoQRDetails = list.get(0);
                        boolean isFairChild = PreferenceUtils.getBooleanPreference(getViewContext(), PreferenceUtils.KEY_FAIRCHILD_FROM);
                        if (isFairChild) {
                            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_FAIRCHILD_FROM);
                            callValidateFairChildMembers();
                        } else {
                            SuccessPromoActivity.start(getViewContext(), mPromoQRDetails);
                        }
                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
                        showError(message);
                        scanAgain();
                    }
                } else {
                    showError();
                    scanAgain();
                }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
            scanAgain();
        }
    };


    //pre validate QR code
    private void preValidatePromoQRCodeV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            String theGuarantorID = CommonFunctions.getQRDataElement(guarantorid);
            String thePromoID = CommonFunctions.getQRDataElement(promoId);

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();

            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("promoid", thePromoID);
            parameters.put("guarantorid", theGuarantorID);
            parameters.put("securitykey", CommonFunctions.getQRDataElement(CommonFunctions.getSha1Hex(theGuarantorID + thePromoID)));

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "preValidatePromoQRCodeV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            preValidatePromoQRCodeV2Object(preValidatePromoQRCodeV2Callback);

        }else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private void preValidatePromoQRCodeV2Object(Callback<GenericResponse> prevalidateqrcode) {

        Call<GenericResponse> call = RetrofitBuilder.getPromoQRCodeV2API(getViewContext())
                .preValidatePromoQRCodeV2(
                        authenticationid,sessionid,param
                );
        call.enqueue(prevalidateqrcode);
    }

    private Callback<GenericResponse> preValidatePromoQRCodeV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        //releasePromoReward();
                        //createSession(getReleasePromoSessionCallback);
                        releasePromoRewardV2();
                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
                        hideProgressDialog();
                        showError(message);
                        scanAgain();
                    }

                } else {
                    hideProgressDialog();
                    showError();
                    scanAgain();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
            t.printStackTrace();
            Logger.debug("okhttp","RESPONSE: "+new Gson().toJson(t));
            scanAgain();
        }
    };
}
