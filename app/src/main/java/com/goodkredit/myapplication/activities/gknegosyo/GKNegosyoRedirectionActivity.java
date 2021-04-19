package com.goodkredit.myapplication.activities.gknegosyo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.GKNegosyPackage;
import com.goodkredit.myapplication.model.GKNegosyoPendingApplication;
import com.goodkredit.myapplication.model.GKNegosyoResellerInfo;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.GKNegosyoApplicationStatus;
import com.goodkredit.myapplication.model.GKNegosyoResellerInformation;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetResellerInformationResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKNegosyoRedirectionActivity extends BaseActivity implements View.OnClickListener {

    private GKService mGKService;
    private GKNegosyoResellerInformation mInfo;


    private GKNegosyoPendingApplication PendingApplication = null;
    private GKNegosyoResellerInfo ResellerInfo = null;
    private GKNegosyPackage ResellerPackage = null;


    private TextView notes2;
    private TextView tv_more_less;
    private ImageView img_more_less;

    private String sessionid;

    //NEW VARIABLES FOR SECURITY
    private String index = "";
    private String authenticationid = "";
    private String keyEncryption = "";
    private String param = "";
    List<GKNegosyoResellerInfo> resellerInfos;
    List<GKNegosyoPendingApplication> pendingApplications;
    List<GKNegosyPackage> gkNegosyPackages;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_left_in, R.anim.fade_out);
        setContentView(R.layout.activity_gk_negosyo_redirection);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());


        resellerInfos = new ArrayList<>();
        pendingApplications = new ArrayList<>();
        gkNegosyPackages = new ArrayList<>();


        mGKService = getIntent().getParcelableExtra(GKService.KEY_SERVICE_OBJ);

        notes2 = findViewById(R.id.notes2);
        tv_more_less = findViewById(R.id.tv_more_less);
        img_more_less = findViewById(R.id.img_more_less);

        findViewById(R.id.btn_cancel_request).setOnClickListener(this);
        findViewById(R.id.btn_paynow_request).setOnClickListener(this);
        findViewById(R.id.btn_show_more_less).setOnClickListener(this);
        findViewById(R.id.proceed_to_gk_negosyo).setOnClickListener(this);
        findViewById(R.id.btn_activate).setOnClickListener(this);
        findViewById(R.id.btn_close).setOnClickListener(this);
        findViewById(R.id.btn_refer_a_reseller).setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            //getResellerInformation();
            getResellerInformationV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getResellerInformation() {
        Call<GetResellerInformationResponse> call = RetrofitBuild.getGKNegosyoAPIService(getViewContext())
                .getResellerInformation(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        sessionid
                );

        call.enqueue(getResellerInformationCallback);
    }

    private Callback<GetResellerInformationResponse> getResellerInformationCallback = new Callback<GetResellerInformationResponse>() {
        @Override
        public void onResponse(Call<GetResellerInformationResponse> call, Response<GetResellerInformationResponse> response) {

                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        GKNegosyoResellerInformation info = response.body().getData();

                        mInfo = info;
                        if (info.getPendingApplication() != null) {
                            if (!info.getPendingApplication().getPaymentType().contains("GK"))
                                findViewById(R.id.layout_req_via_payment_channel).setVisibility(View.VISIBLE);
                        } else {
                            findViewById(R.id.layout_req_via_payment_channel).setVisibility(View.GONE);
                            if (info.getResellerPackage() != null && info.getResellerInfo() != null) {
                                GKNegosyoDashboardActivity.start(getViewContext(), mGKService);
                            } else {
                                GKNegosyoDistributorsActivity.start(getViewContext(), mGKService);
                            }
                        }
                    } else if (response.body().getStatus().equals("005")) {
                        findViewById(R.id.layout_reseller_deactivated).setVisibility(View.VISIBLE);
                        findViewById(R.id.layout_req_via_payment_channel).setVisibility(View.GONE);
                        findViewById(R.id.layout_reseller_blocked).setVisibility(View.GONE);
                    } else if (response.body().getStatus().equals("006")) {
                        findViewById(R.id.layout_reseller_deactivated).setVisibility(View.GONE);
                        findViewById(R.id.layout_req_via_payment_channel).setVisibility(View.GONE);
                        findViewById(R.id.layout_reseller_blocked).setVisibility(View.VISIBLE);
                    } else {
                        showError();
                    }

                } else {
                    showError();
                }
        }

        @Override
        public void onFailure(Call<GetResellerInformationResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

    public static void start(Context context, GKService gkService) {
        Intent intent = new Intent(context, GKNegosyoRedirectionActivity.class);
        intent.putExtra(GKService.KEY_SERVICE_OBJ, gkService);
        context.startActivity(intent);
    }

    private void getCancelRequestSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            //cancelRequest();
            cancelPackageApplicationV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void cancelRequest() {
        Call<GenericResponse> call = RetrofitBuild.getGKNegosyoAPIService(getViewContext())
                .cancelApplicationRequest(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        sessionid,
                        PendingApplication.getRegistrationID(),
                        mGKService.getServiceCode()
                );

        call.enqueue(cancelRequestCallback);
    }

    private Callback<GenericResponse> cancelRequestCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        showCancellationSuccessfulDialog();
                    } else {
                        showError();
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

    private void showCancellationSuccessfulDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("You have cancelled your application for reseller.")
                .cancelable(false)
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        GKNegosyoRedirectionActivity.start(getViewContext(), mGKService);
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel_request: {
                showCancelRequestWarningDialog();
                break;
            }
            case R.id.btn_paynow_request: {
                if (PendingApplication.getPaymentType().contains("PARTNERS")) {
                    GKNegosyoPackageBillinReferenceActivity.start(
                            getViewContext(),
                            new GKNegosyoApplicationStatus(
                                    PendingApplication.getRegistrationID(),
                                    PendingApplication.getRegistrationID()),
                            String.valueOf(PendingApplication.getTotalAmount()));
                } else if (PendingApplication.getPaymentType().contains("VIA GK")) {
                    GKNegosyoDistributorsActivity.start(getViewContext(), mGKService);
                }
                break;
            }
            case R.id.btn_show_more_less: {
                if (notes2.getVisibility() == View.GONE) {
                    notes2.setVisibility(View.VISIBLE);
                    tv_more_less.setText("less");
                    img_more_less.setRotation(180);
                } else {
                    notes2.setVisibility(View.GONE);
                    tv_more_less.setText("more");
                    img_more_less.setRotation(360);
                }
                break;
            }
            case R.id.proceed_to_gk_negosyo: {
                if (!PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID).equals(".") &&
                        !PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER).equals(".")) {
                    getSession();
                } else {
                    if (isGPSEnabled(getViewContext())) {
                        getSession();
                    } else {
                        //open settings
                        gpsNotEnabledDialog();
                    }
                }
                break;
            }
            case R.id.btn_activate: {
                GKNegosyoDistributorsActivity.start(getViewContext(), mGKService);
                break;
            }

            case R.id.btn_close: {
                findViewById(R.id.layout_req_via_payment_channel).setVisibility(View.GONE);
                break;
            }
            case R.id.btn_refer_a_reseller: {
                GKNegosyoReferAResellerActivity.start(getViewContext());
                break;
            }
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
                        finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        showToast("GPS must be enabled to avail the service.", GlobalToastEnum.WARNING);
                        finish();
                    }
                })
                .show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mDialog.getView());
    }

    private void showCancelRequestWarningDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("Are you sure you want to cancel your package request?")
                .negativeText("Cancel")
                .positiveText("Proceed")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getCancelRequestSession();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        if (findViewById(R.id.layout_req_via_payment_channel).getVisibility() == View.VISIBLE) {
            findViewById(R.id.layout_req_via_payment_channel).setVisibility(View.GONE);
        } else {
            finish();
        }
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */
    private void getResellerInformationV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getResellerInformationV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getResellerInformationV2Object(getResellerInformationV2Callback);

        }else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private void getResellerInformationV2Object(Callback<GenericResponse> getResellerInfo) {
        Call<GenericResponse> call = RetrofitBuilder.getGkNegosyoV2API(getViewContext())
                .getResellerInformationV2(
                        authenticationid,sessionid,param
                );

        call.enqueue(getResellerInfo);
    }
    private Callback<GenericResponse> getResellerInformationV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                        String resinfo = CommonFunctions.parseJSON(data,"ResellerInfo");
                        String pendingapp = CommonFunctions.parseJSON(data,"PendingApplication");
                        String respackage = CommonFunctions.parseJSON(data,"ResellerPackage");

                        Logger.debug("okhttp","GKNEGOSYO: : : "+data);

                        resellerInfos  = new Gson().fromJson(resinfo, new TypeToken<List<GKNegosyoResellerInfo>>(){}.getType());
                        pendingApplications  = new Gson().fromJson(pendingapp, new TypeToken<List<GKNegosyoPendingApplication>>(){}.getType());
                        gkNegosyPackages  = new Gson().fromJson(respackage, new TypeToken<List<GKNegosyPackage>>(){}.getType());


                        if(resellerInfos.size() > 0 && resellerInfos != null){
                            for(GKNegosyoResellerInfo resellerInfo : resellerInfos){
                                ResellerInfo = resellerInfo;
                            }
                        }

                         if(gkNegosyPackages.size() > 0 && gkNegosyPackages != null){
                             for(GKNegosyPackage gkNegosyPackage : gkNegosyPackages){
                                 ResellerPackage = gkNegosyPackage;
                             }
                         }

                        if (pendingApplications.size() > 0 && pendingApplications != null) {

                            for(GKNegosyoPendingApplication pendingApplication : pendingApplications){
                                PendingApplication = pendingApplication;
                            }
                            if (!PendingApplication.getPaymentType().contains("GK"))
                                findViewById(R.id.layout_req_via_payment_channel).setVisibility(View.VISIBLE);

                        } else {
                            findViewById(R.id.layout_req_via_payment_channel).setVisibility(View.GONE);
                            if (gkNegosyPackages.size() > 0  && resellerInfos.size() > 0) {
                                GKNegosyoDashboardActivity.start(getViewContext(), mGKService);
                            } else {
                                GKNegosyoDistributorsActivity.start(getViewContext(), mGKService);
                            }
                        }

                    } else if (response.body().getStatus().equals("012")) {
                        findViewById(R.id.layout_reseller_deactivated).setVisibility(View.VISIBLE);
                        findViewById(R.id.layout_req_via_payment_channel).setVisibility(View.GONE);
                        findViewById(R.id.layout_reseller_blocked).setVisibility(View.GONE);
                    } else if (response.body().getStatus().equals("006")) {
                        findViewById(R.id.layout_reseller_deactivated).setVisibility(View.GONE);
                        findViewById(R.id.layout_req_via_payment_channel).setVisibility(View.GONE);
                        findViewById(R.id.layout_reseller_blocked).setVisibility(View.VISIBLE);
                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showErrorToast(message);
                    }

                } else {
                    showError();
                }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

    private void cancelPackageApplicationV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("registrationid",PendingApplication.getRegistrationID());
            parameters.put("servicecode",mGKService.getServiceCode());


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "cancelPackageApplicationV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            cancelPackageApplicationV2Object(cancelPackageApplicationV2Callback);

        }else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private void cancelPackageApplicationV2Object(Callback<GenericResponse> callback) {
        Call<GenericResponse> call = RetrofitBuilder.getGkNegosyoV2API(getViewContext())
                .cancelPackageApplicationV2(
                    authenticationid,sessionid,param
                );

        call.enqueue(callback);
    }

    private Callback<GenericResponse>cancelPackageApplicationV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                   switch ((response.body().getStatus())){

                       case "000":
                           showCancellationSuccessfulDialog();
                           break;
                       case "002" : case "003":
                           showAutoLogoutDialog(getString(R.string.logoutmessage));
                           break;
                       default:
                           showError();
                           break;

                   }
                } else {
                    showError();
                }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };




}
