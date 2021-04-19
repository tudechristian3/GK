package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantReferAFriendFragment extends BaseFragment implements View.OnClickListener {

    private Button btn_refer;
    private EditText edt_name;
    private EditText edt_mobileno;
    private EditText edt_email;
    private EditText edt_address;

    private String sessionid;
    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;
    private String servicecode;
    private String referredname;
    private String referredmobile;
    private String referredemail;
    private String referredaddress;

    //RESELLER
    private String resellerid = "";
    private String distributorid = "";
    private boolean checkIfReseller = false;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_refer_a_friend, container, false);

        init(view);
        initData();

        return view;
    }

    private void init(View view) {
        btn_refer = view.findViewById(R.id.btn_coopassistant_refer);
        edt_name = view.findViewById(R.id.edt_coop_refer_name);
        edt_mobileno = view.findViewById(R.id.edt_coop_refer_mobileno);
        edt_email = view.findViewById(R.id.edt_coop_refer_email);
        edt_address = view.findViewById(R.id.edt_coop_refer_address);

        btn_refer.setOnClickListener(this);
    }

    private void initData() {
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_coopassistant_refer: {

                referredname = edt_name.getText().toString().trim();
                referredmobile = edt_mobileno.getText().toString().trim();
                referredemail = edt_email.getText().toString().trim();
                referredaddress = edt_address.getText().toString().trim();

                if (referredaddress.equals("")) {
                    referredaddress = "N/A";
                }

                if (referredname.equals("") || referredmobile.equals("") || referredemail.equals("")) {
                    showToast("Please fill all required fields.", GlobalToastEnum.WARNING);
                } else if (getMobileNumber(referredmobile).equals("INVALID")) {
                    edt_mobileno.setError("Invalid Mobile number.");
                    edt_mobileno.requestFocus();
                } else if(!CommonFunctions.isValidEmail(referredemail)) {
                    edt_email.setError("Invalid Email address.");
                    edt_email.requestFocus();
                }else {
                    referredmobile = CommonFunctions.convertoPHCountryCodeNumber(referredmobile);
                    getSession();
                }

                break;
            }
        }
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //referCoopToAFriend(referCoopToAFriendSession);

            btn_refer.setEnabled(false);
            showProgressDialog("","Processing request... Please wait ...");

            referCoopToAFriendV2();
        } else {
            showErrorGlobalDialogs();
        }
    }

    private void referCoopToAFriend(Callback<GenericResponse> referCoopToAFriendCallback) {
        Call<GenericResponse> refercoop = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .referCoopToAFriendCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        servicecode,
                        referredname,
                        referredmobile,
                        referredemail,
                        referredaddress,
                        CommonVariables.devicetype);
        refercoop.enqueue(referCoopToAFriendCallback);
    }

    private final Callback<GenericResponse> referCoopToAFriendSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    try {
                        if (distributorid.equals("") || distributorid.equals(".")
                                || resellerid.equals("") || resellerid.equals(".")) {
                            checkIfReseller = false;
                        } else {
                            checkIfReseller = true;
                        }

                        showConfirmSuccessDialog(response.body().getMessage());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (response.body().getStatus().equals("203")) {
                    showToast(response.body().getMessage(), GlobalToastEnum.WARNING);
                } else if (response.body().getStatus().equals("104")) {
                    showAutoLogoutDialog(response.body().getMessage());
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());

                }

            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorGlobalDialogs();
        }
    };

    private void showConfirmSuccessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        boolean isGKNegosyoModal;
        if (checkIfReseller) {
            isGKNegosyoModal = false;
        } else {
            isGKNegosyoModal = true;
        }

        dialog.createDialog("SUCCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                isGKNegosyoModal, false, DialogTypeEnum.SUCCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.proceedtoCoopHome();
            }
        });
    }

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    private void referCoopToAFriendV2(){

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("referredname",referredname);
        parameters.put("referredmobileno",referredmobile);
        parameters.put("referredemail", referredemail);
        parameters.put("referredaddress", referredaddress);
        parameters.put("devicetype", CommonVariables.devicetype);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "referCoopToAFriendV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        referCoopToAFriendV2Object(referCoopToAFriendV2Callback);

    }
    private void referCoopToAFriendV2Object(Callback<GenericResponse> genericResponseCallback){
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .referCoopToAFriendV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse> referCoopToAFriendV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();
            btn_refer.setEnabled(true);
            if (errorBody == null) {
                String message  = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    try {
                        if (distributorid.equals("") || distributorid.equals(".")
                                || resellerid.equals("") || resellerid.equals(".")) {
                            checkIfReseller = false;
                        } else {
                            checkIfReseller = true;
                        }
                        showConfirmSuccessDialog(message);
                    } catch (Exception e) {
                        showErrorGlobalDialogs();
                        e.printStackTrace();
                    }

                } else if (response.body().getStatus().equals("203")) {
                    showToast(message, GlobalToastEnum.WARNING);
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
            hideProgressDialog();
            btn_refer.setEnabled(true);
            showErrorGlobalDialogs();
        }
    };




}
