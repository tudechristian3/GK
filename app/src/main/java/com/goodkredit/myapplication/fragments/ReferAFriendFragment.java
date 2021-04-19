package com.goodkredit.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.Referral;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.referafriend.GenerateReferralCodeResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferAFriendFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_code;
    private TextView tv_message;
    private TextView lbl_code;
    private TextView btn_customize_code;

    private String str_invite = "";

    private FrameLayout err_conn;
    private ScrollView scroll;

    private MaterialDialog dialog;

    private EditText edt_code;

    private String strCode;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String customizeIndex;
    private String customizeAuthenticationid;
    private String customizeKeyEncryption;
    private String customizeParam;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_notification_0).setVisible(false);
        menu.findItem(R.id.action_notification_1).setVisible(false);
        menu.findItem(R.id.action_notification_2).setVisible(false);
        menu.findItem(R.id.action_notification_3).setVisible(false);
        menu.findItem(R.id.action_notification_4).setVisible(false);
        menu.findItem(R.id.action_notification_5).setVisible(false);
        menu.findItem(R.id.action_notification_6).setVisible(false);
        menu.findItem(R.id.action_notification_7).setVisible(false);
        menu.findItem(R.id.action_notification_8).setVisible(false);
        menu.findItem(R.id.action_notification_9).setVisible(false);
        menu.findItem(R.id.action_notification_9plus).setVisible(false);
        menu.findItem(R.id.action_process_queue).setVisible(false);
        menu.findItem(R.id.sortitem).setVisible(false);
        menu.findItem(R.id.summary).setVisible(false);
        menu.findItem(R.id.group_voucher).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_refer_a_friend, container, false);
        view.findViewById(R.id.btn_refer).setOnClickListener(this);
        view.findViewById(R.id.btn_refresh).setOnClickListener(this);
        view.findViewById(R.id.btn_customize_code).setOnClickListener(this);

        tv_code = view.findViewById(R.id.tv_code);
        tv_message = view.findViewById(R.id.tv_message);
        lbl_code = view.findViewById(R.id.lbl_code);
        btn_customize_code = view.findViewById(R.id.btn_customize_code);

        err_conn = view.findViewById(R.id.err_conn);
        scroll = view.findViewById(R.id.scroll);

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        getSession();
        return view;
    }

    private void getSession() {
        //generateReferralCode();
        generateReferralCodeV2();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_refer: {
                referFriend(str_invite);
                break;
            }
            case R.id.btn_refresh: {
                getSession();
                break;
            }
            case R.id.btn_customize_code: {
                showCustomizeCodeDialogNew();
                break;
            }
        }
    }

    private void referFriend(String invite) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, invite);
        startActivity(Intent.createChooser(intent, "Refer a Friend"));
    }

    private void showCustomizeCodeDialog() {
        try {
            dialog = new MaterialDialog.Builder(getViewContext())
                    .customView(R.layout.dialog_customize_code, false)
                    .positiveText("Submit")
                    .negativeText("Cancel")
                    .cancelable(true)
                    .show();

            View view = dialog.getCustomView();
            View positive = dialog.getActionButton(DialogAction.POSITIVE);

            edt_code = view.findViewById(R.id.edt_code);
            edt_code.addTextChangedListener(codeTextWatcher);

            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edt_code.getText().length() > 2) {
                        dialog.dismiss();
                        showProgressDialog("", "Please wait...");
                        customizeReferralCode();
                    } else {
                        showError("Code must have 3-7 characters. Please try again.");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCustomizeCodeDialogNew() {
        try {

            GlobalDialogs dialog = new GlobalDialogs(getViewContext());

            dialog.createDialog("Notice", "Make it more personal! " +
                            "Customize your code and share it with your friends!",
                    "CANCEL", "SUBMIT", ButtonTypeEnum.DOUBLE,
                    false, false, DialogTypeEnum.EDITTEXT);

            View closebtn = dialog.btnCloseImage();
            closebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.showContentMessage();

            List<String> editTextDataType = new ArrayList<>();
            editTextDataType.add(String.valueOf(GlobalDialogsEditText.VARCHAR));

            LinearLayout editTextContainer = dialog.setContentEditText(editTextDataType,
                    LinearLayout.VERTICAL,
                    new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.CENTER,
                            R.drawable.border, 12, "Code"));

            final int count = editTextContainer.getChildCount();
            for (int i = 0; i < count; i++) {
                View editView = editTextContainer.getChildAt(i);
                if (editView instanceof EditText) {
                    EditText editItem = (EditText) editView;
                    editItem.setPadding(15, 30, 15, 30);
                    editItem.setHintTextColor(ContextCompat.getColor(mContext, R.color.colorsilver));
                    String taggroup = editItem.getTag().toString();
                    if (taggroup.equals("TAG 0")) {
                        edt_code = editItem;
                        edt_code.addTextChangedListener(codeTextWatcher);
                    }
                }
            }

            View btndoubleone = dialog.btnDoubleOne();
            btndoubleone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });

            View btndoubletwo = dialog.btnDoubleTwo();
            btndoubletwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edt_code.getText().length() > 2) {
                        dialog.dismiss();
                        showProgressDialog("", "Please wait...");
                        //customizeReferralCode();
                        customizeReferralCodeV2();
                    } else {
                        showToast("Code must have 3-7 characters. Please try again.", GlobalToastEnum.WARNING);
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateReferralCode() {
        Call<GenerateReferralCodeResponse> call = RetrofitBuild.getReferralAPIService(getViewContext())
                .generateReferralCode(
                        imei,
                        userid,
                        sessionid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        CommonVariables.gkphreferral,
                        CommonVariables.devicetype
                );
        call.enqueue(generateReferralCodeCallback);
    }
    private Callback<GenerateReferralCodeResponse> generateReferralCodeCallback = new Callback<GenerateReferralCodeResponse>() {
        @Override
        public void onResponse(Call<GenerateReferralCodeResponse> call, Response<GenerateReferralCodeResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().contains("000")) {
                        scroll.setVisibility(View.VISIBLE);
                        err_conn.setVisibility(View.GONE);
                        String statusOfReferral = response.body().getData().getReferralPromo();
                        str_invite = response.body().getData().getInvitationMessage();
                        tv_message.setText(response.body().getData().getReferralDescription());
                        if (statusOfReferral.equals("ACTIVE")) {
                            tv_code.setText(response.body().getData().getReferralCode());
                            tv_code.setVisibility(View.VISIBLE);
                            lbl_code.setVisibility(View.VISIBLE);
                            btn_customize_code.setVisibility(View.VISIBLE);
                        } else {
                            tv_code.setVisibility(View.GONE);
                            lbl_code.setVisibility(View.GONE);
                            btn_customize_code.setVisibility(View.GONE);
                        }
                    } else {
                        //showError(response.body().getMessage());
                        showErrorGlobalDialogs(response.body().getMessage());
                        scroll.setVisibility(View.GONE);
                        err_conn.setVisibility(View.VISIBLE);
                    }
                } else {
                    //showError();
                    showErrorGlobalDialogs();
                    scroll.setVisibility(View.GONE);
                    err_conn.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenerateReferralCodeResponse> call, Throwable t) {
            hideProgressDialog();
            scroll.setVisibility(View.GONE);
            err_conn.setVisibility(View.VISIBLE);
            showErrorGlobalDialogs();
        }
    };

    private void customizeReferralCode() {
        Call<GenericResponse> call = RetrofitBuild.getReferralAPIService(getViewContext())
                .customizeReferralCode(
                        imei,
                        userid,
                        sessionid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        strCode,
                        "ANDROID"
                );
        call.enqueue(customizeReferralCodeCallback);
    }

    private Callback<GenericResponse> customizeReferralCodeCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().contains("000")) {
//                        createSession(getSessionCallback);
                        getSession();
                    } else {
                        //showError(response.body().getMessage());
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    //showError();
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            //showError();
            showErrorGlobalDialogs();
        }
    };

    private TextWatcher codeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            strCode = s.toString().toUpperCase();
            //edt_code.setText(strCode);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */
    private void generateReferralCodeV2(){

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("generationtype", CommonVariables.gkphreferral);
            parameters.put("devicetype",CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "generateReferralCodeV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            generateReferralCodeV2Object(generateReferralCodeV2Callback);

        }else{
            showNoInternetToast();
        }
    }
    private void generateReferralCodeV2Object(Callback<GenericResponse> generateCodeCallback){
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .generateReferralCodeV2(authenticationid,sessionid,param);
        call.enqueue(generateCodeCallback);
    }
    private Callback<GenericResponse> generateReferralCodeV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().contains("000")) {

                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                        Referral referral = new Gson().fromJson(data,Referral.class);

                        scroll.setVisibility(View.VISIBLE);
                        err_conn.setVisibility(View.GONE);
                        String statusOfReferral = referral.getReferralPromo();
                        str_invite = referral.getInvitationMessage();
                        tv_message.setText(referral.getReferralDescription());
                        if (statusOfReferral.equals("ACTIVE")) {
                            tv_code.setText(referral.getReferralCode());
                            tv_code.setVisibility(View.VISIBLE);
                            lbl_code.setVisibility(View.VISIBLE);
                            btn_customize_code.setVisibility(View.VISIBLE);
                        } else {
                            tv_code.setVisibility(View.GONE);
                            lbl_code.setVisibility(View.GONE);
                            btn_customize_code.setVisibility(View.GONE);
                        }
                    }else if(response.body().getStatus().equals("003")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
                        showErrorGlobalDialogs(message);
                        scroll.setVisibility(View.GONE);
                        err_conn.setVisibility(View.VISIBLE);
                    }
                } else {
                    showErrorGlobalDialogs();
                    scroll.setVisibility(View.GONE);
                    err_conn.setVisibility(View.VISIBLE);
                }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            hideProgressDialog();
            scroll.setVisibility(View.GONE);
            err_conn.setVisibility(View.VISIBLE);
            showErrorGlobalDialogs();
        }
    };

    private void  customizeReferralCodeV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("devicetype",CommonVariables.devicetype);
            parameters.put("referralcode", strCode);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            customizeIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", customizeIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            customizeAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            customizeKeyEncryption = CommonFunctions.getSha1Hex(customizeAuthenticationid + sessionid + "customizeReferralCodeV2");
            customizeParam = CommonFunctions.encryptAES256CBC(customizeKeyEncryption, String.valueOf(paramJson));

            customizeReferralCodeV2Object(customizeReferralCodeV2Callback);

        }else{
            showNoInternetToast();
        }
    }
    private void customizeReferralCodeV2Object(Callback<GenericResponse> customizeReferralCodeCallback){
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .customizeReferralCode(customizeAuthenticationid,sessionid,customizeParam);
        call.enqueue(customizeReferralCodeCallback);
    }
    private Callback<GenericResponse> customizeReferralCodeV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(customizeKeyEncryption,response.body().getMessage());
                    if (response.body().getStatus().contains("000")) {
                        getSession();
                    }else if(response.body().getStatus().equals("003")){
                        showAutoLogoutDialog(message);
                    }else {
                        showErrorGlobalDialogs(message);
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };


}
