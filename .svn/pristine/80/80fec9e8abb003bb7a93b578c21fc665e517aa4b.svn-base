package com.goodkredit.myapplication.fragments.gkearn;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gkearn.GKEarnHomeActivity;
import com.goodkredit.myapplication.adapter.gkearn.GKEarnReferralRandomAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.decoration.DividerItemDecoration;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.EventItem;
import com.goodkredit.myapplication.model.ListItem;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.model.gkearn.GKEarnReferralRandom;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKEarnRegisterFragment extends BaseFragment {

    //API Parameters
    private String sessionid;
    private String imei;
    private String borrowerid;
    private String userid;
    private String authcode;
    private String borrowername;
    private String referralcode;

    private ScrollView scrollView;

    //Register
    public EditText edt_referredby;

    private DatabaseHandler mdb;

    //NEW REGISTRATION
    private String registrationfee;
    private String vouchersession;

    private String servicecode;
    private String merchantid;

    //VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    //validate referral
    private String validateIndex;
    private String validateKey;
    private String validateAuth;
    private String validateParam;

    //get earn reg fee
    private String regFeeIndex;
    private String regFeeAuth;
    private String regFeeKey;
    private String regFeeParam;


    //RANDOM REFERRAL
    public Dialog referralRandomDialog;

    private RecyclerView rv_referral_random_code;
    private GKEarnReferralRandomAdapter gkEarnReferralRandomAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gkearn_register, container, false);
        setHasOptionsMenu(true);
        try {
            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_proceed, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.proceed){
            if(edt_referredby.getText().toString().trim().equals("")){
                referralcode = ".";
                //getSession("getGKEarnRegistrationFee");
                showProgressDialog("Processing request.", "Please wait...");
                getSubscribersRandomReferCode();
            } else{
                    referralcode = edt_referredby.getText().toString().trim();
                    showRegisterConfirmationDialog();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(View view) {
        scrollView = view.findViewById(R.id.scrollView);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        edt_referredby = view.findViewById(R.id.edt_enter_referralcode);
        edt_referredby.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(12),
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z 0-9 , .]+")) {
                            return cs;
                        }
                        return "";
                    }
                }
        });

        rv_referral_random_code = view.findViewById(R.id.rv_referral_random_code);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        V2Subscriber v2Subscriber = mdb.getSubscriber(mdb);

        sessionid = PreferenceUtils.getSessionID(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        borrowername = v2Subscriber.getBorrowerName();

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");
    }

    private void getSession(String from) {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            switch (from) {
                case "getGKEarnRegistrationFee":
                    showProgressDialog("Processing request.", "Please wait...");

                    if(CommonVariables.GKEARNBETATEST){
                        getGKEarnRegistrationFeeV2();
                    }else{
                        getGKEarnRegistrationFee(getGKEarnRegistrationFeeSession);
                    }

                    break;
                case "registerGKEarn":
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            registerGKEarn(registerGKEarnSession);
                        }
                    }, 2000);
                    break;
                case "validateGKEarnReferralCode":
                    showProgressDialog("Processing request.", "Please wait...");
                    if(CommonVariables.GKEARNBETATEST){
                        validateGKEarnReferralCodeV2();
                    }else{
                        validateGKEarnReferralCode();
                    }
                    break;
            }
        } else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void registerGKEarn (retrofit2.Callback<GenericResponse> registerGKEarnCallback) {
        Call<GenericResponse> registergkearn = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .registerGKEarnCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        borrowername,
                        authcode,
                        referralcode,
                        "GKEARN",
                        ".",
                        ".",
                        servicecode,
                        merchantid,
                        CommonVariables.devicetype);

        registergkearn.enqueue(registerGKEarnCallback);
    }

    private final retrofit2.Callback<GenericResponse> registerGKEarnSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            okhttp3.ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            try{
                if(errorBody == null) {
                    if(response.body().getStatus().equals("000")){
                        Bundle args = new Bundle();
                        args.putString(GKEarnHomeActivity.GKEARN_KEY_FROM, GKEarnHomeActivity.GKEARN_VALUE_FROMREGISTRATION);
                        GKEarnHomeActivity.start(getViewContext(), GKEarnHomeActivity.GKEARN_FRAGMENT_INVITE_A_FRIEND, args);
                    } else{
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else{
                    showErrorGlobalDialogs();
                }
            }catch (Exception e){
                e.printStackTrace();
                hideProgressDialog();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        getActivity().overridePendingTransition(0, 0);
    }

    private void showRegisterConfirmationDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("You are about to apply as GK Earn subscriber. Do you want to proceed?")
                .negativeText("Cancel")
                .positiveText("Proceed")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getSession("validateGKEarnReferralCode");
                    }
                })
                .show();
    }

    private void getGKEarnRegistrationFee(Callback<GenericResponse> getGKEarnRegistrationFeeCallback){
        Call<GenericResponse> getgkearnregistrationfee = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .getGKEarnRegistrationFeeCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        "GKEARN",
                        CommonVariables.devicetype);
        getgkearnregistrationfee.enqueue(getGKEarnRegistrationFeeCallback);
    }

    private final Callback<GenericResponse> getGKEarnRegistrationFeeSession = new Callback<GenericResponse>()  {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    registrationfee = response.body().getData();
                    hideProgressDialog();
                    callPrePurchase();
                } else if(response.body().getStatus().equals("105")){
                    hideProgressDialog();
                    getSession("registerGKEarn");
                }
                else{
                    hideProgressDialog();
                    showError(response.body().getMessage());
                }
            } else{
                hideProgressDialog();
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorToast();
        }
    };

    private void validateGKEarnReferralCode(){
        Call<GenericResponse> validateGKEarnReferralCode = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .validateGKEarnReferralCode(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        referralcode,
                        CommonVariables.devicetype);
        validateGKEarnReferralCode.enqueue(validateGKEarnReferralCodeCallBack);
    }

    private final Callback<GenericResponse> validateGKEarnReferralCodeCallBack = new Callback<GenericResponse>()  {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            hideProgressDialog();

            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    if(CommonVariables.GKEARNBETATEST){
                        getGKEarnRegistrationFeeV2();
                    }else{
                        getGKEarnRegistrationFee(getGKEarnRegistrationFeeSession);
                    }
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else{
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showNoInternetToast();
            t.printStackTrace();
        }
    };

    //PROCEEDING TO PAYMENTS
    private void proceedtoPayments() {
        hideProgressDialog();
        setUpProgressLoaderDismissDialog();

        Intent intent = new Intent(getViewContext(), Payment.class);
        intent.putExtra("FROMGKEARNREGISTRATION", "GKEARNREGISTRATION");
        intent.putExtra("GKEARNAMOUNT", registrationfee);
        intent.putExtra("GKEARNREGISTRATIONVOUCHERSESSION", vouchersession);
        intent.putExtra("GKEARNREGISTRATIONFEE",registrationfee);
        intent.putExtra("GKEARNREFERRALCODE",referralcode);
        startActivity(intent);
    }


    /*
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * */

    private void callPrePurchase() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("amountpurchase", registrationfee);
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

            prePurchaseV3Object();

        } else {
            showNoInternetToast();
        }

    }
    private void prePurchaseV3Object(){
        Call<GenericResponse> prepurchasev2 = RetrofitBuilder.getCommonV2API(getViewContext())
                .prePurchaseV3(
                        authenticationid, sessionid, param
                );
        prepurchasev2.enqueue(prePurchaseV3Callback);
    }
    private final Callback<GenericResponse> prePurchaseV3Callback = new Callback<GenericResponse>() {
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
    private void showReferralRandomCodeDialog() {
        referralRandomDialog = new Dialog(getViewContext());
        referralRandomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(referralRandomDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        } else {
            referralRandomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        referralRandomDialog.setContentView(R.layout.gkearn_referral_random_dialog);
        referralRandomDialog.setCancelable(false);

        LinearLayout btn_sarado = referralRandomDialog.findViewById(R.id.btn_close);
        btn_sarado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referralRandomDialog.dismiss();
            }
        });

        LinearLayout btn_gimok = referralRandomDialog.findViewById(R.id.btn_action);
        btn_gimok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referralRandomDialog.dismiss();
                getSession("getGKEarnRegistrationFee");
            }
        });

        rv_referral_random_code = referralRandomDialog.findViewById(R.id.rv_referral_random_code);
        rv_referral_random_code.setNestedScrollingEnabled(false);
        rv_referral_random_code.setLayoutManager(new LinearLayoutManager(getViewContext()));

        //ANIMATION
        rv_referral_random_code.setItemAnimator(new DefaultItemAnimator());
        Objects.requireNonNull(rv_referral_random_code.getItemAnimator()).setAddDuration(500);
        rv_referral_random_code.getItemAnimator().setMoveDuration(500);
        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_animation);
        rv_referral_random_code.setLayoutAnimation(controller);
        rv_referral_random_code.scheduleLayoutAnimation();

        gkEarnReferralRandomAdapter = new GKEarnReferralRandomAdapter(getViewContext(),GKEarnRegisterFragment.this);
        rv_referral_random_code.setAdapter(gkEarnReferralRandomAdapter);

        gkEarnReferralRandomAdapter.updateData(CacheManager.getInstance().getGKEarnReferralRandom());

        referralRandomDialog.show();
    }

    private void getSubscribersRandomReferCode() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("servicecode", servicecode);
                parameters.put("devicetype", CommonVariables.devicetype);


                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getSubscribersRandomReferCode");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getSubscribersRandomReferCodeObject();

            } else {
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getSubscribersRandomReferCodeObject() {
        Call<GenericResponse> call = RetrofitBuilder.getAccountV2API(getViewContext())
                .getSubscribersRandomReferCode(
                        authenticationid, sessionid, param
                );
        call.enqueue(getSubscribersRandomReferCodeCallBack);
    }
    private final Callback<GenericResponse> getSubscribersRandomReferCodeCallBack = new Callback<GenericResponse>() {
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
                       CacheManager.getInstance().removeGKEarnReferralRandom();

                       List<GKEarnReferralRandom> gkEarnReferralRandomList = new Gson().fromJson(decryptedData, new TypeToken<List<GKEarnReferralRandom>>(){}.getType());

                        if (gkEarnReferralRandomList.size() > 0) {
                            CacheManager.getInstance().saveGKEarnReferralRandom(gkEarnReferralRandomList);
                        }
                        showReferralRandomCodeDialog();

                    }
                } else {
                    if (response.body().getStatus().equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else {
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            } else {
                hideProgressDialog();
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorToast();
        }
    };


    //validate gkearn referral code
    private void validateGKEarnReferralCodeV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("referralcode",referralcode);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        validateIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", validateIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        validateAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        validateKey = CommonFunctions.getSha1Hex(validateAuth + sessionid + "validateGKEarnReferralCodeV2");
        validateParam = CommonFunctions.encryptAES256CBC(validateKey,String.valueOf(paramJson));

        validateGKEarnReferralCodeV2Object(validateGKEarnReferralCodeV2Callback);


    }
    private void validateGKEarnReferralCodeV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getGkEarnV2API(getViewContext())
                .validateGKEarnReferralCodeV2(validateAuth,sessionid,validateParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse> validateGKEarnReferralCodeV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(validateKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                      if(CommonVariables.GKEARNBETATEST){
                          getGKEarnRegistrationFeeV2();
                      }else{
                          getGKEarnRegistrationFee(getGKEarnRegistrationFeeSession);
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
            showErrorGlobalDialogs();
            t.printStackTrace();
        }
    };


    //get earn registration fee
    private void getGKEarnRegistrationFeeV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicetype","GKEARN");
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        regFeeIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", regFeeIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API
        regFeeAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        regFeeKey = CommonFunctions.getSha1Hex(regFeeAuth + sessionid + "getGKEarnRegistrationFeeV2");
        regFeeParam = CommonFunctions.encryptAES256CBC(regFeeKey, String.valueOf(paramJson));

        getGKEarnRegistrationFeeV2Object(getGKEarnRegistrationFeeV2Callback);


    }
    private void getGKEarnRegistrationFeeV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getGkEarnV2API(getViewContext())
                .getGKEarnRegistrationFeeV2(regFeeAuth,sessionid,regFeeParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  getGKEarnRegistrationFeeV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(regFeeKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                     String data = CommonFunctions.decryptAES256CBC(regFeeKey,response.body().getData());
                     Logger.debug("okhttp","REGFREE: "+data);
                     registrationfee = data;
                     callPrePurchase();
                    Logger.debug("okhttp","REGFREE: "+registrationfee);

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else if(response.body().getStatus().equals("105")){
                    getSession("registerGKEarn");
                }else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            t.printStackTrace();
            showErrorToast();
        }
    };



}
