package com.goodkredit.myapplication.fragments.gkearn;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.gkearn.GKEarnHomeActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.referafriend.GenerateReferralCodeResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKEarnInviteAFriendFragment extends BaseFragment implements View.OnClickListener {

    //Parameters
    private String sessionid;
    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;

    private TextView txv_customize_code;
    private TextView txv_referralcode;
    private LinearLayout layout_referralcode;
    private LinearLayout btn_inviteafriend;
    private TextView txv_skip;
    private String str_invite;
    private EditText edt_code;
    private String strCode;
    private String strFrom = "";

    private TextView txv_description;
    private ImageView img_close;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gkearn_invite_a_friend, container, false);
        try {
            init(view);
            initData();

            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if(keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                        if(i == KeyEvent.KEYCODE_BACK){
                            proceedToGKEarnHome();
                            return true;
                        }
                    }
                    return false;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        txv_referralcode = view.findViewById(R.id.txv_referralcode);
        layout_referralcode = view.findViewById(R.id.layout_referralcode);

        txv_customize_code = view.findViewById(R.id.btn_customize_code);
        btn_inviteafriend = view.findViewById(R.id.btn_inviteafriend);
        txv_skip = view.findViewById(R.id.txv_skip);

        txv_customize_code.setOnClickListener(this);
        btn_inviteafriend.setOnClickListener(this);
        txv_skip.setOnClickListener(this);

        txv_description = view.findViewById(R.id.txv_description);
        img_close = view.findViewById(R.id.img_close);
        img_close.setOnClickListener(this);
    }

    private void initData() {
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        String appPackageName = getViewContext().getPackageName();

        str_invite = "Hi, I’m inviting you to join my network! Download GoodKredit app now click this link " +
                "https://play.google.com/store/apps/details?id=" + appPackageName + "." +
                " Register to GK EARN mini app and get points everytime you buy prepaid load through the " +
                " GoodKredit app! Registration is FREE. TRY IT NOW!";

        if (getArguments() != null) {
            strFrom = getArguments().getString(GKEarnHomeActivity.GKEARN_KEY_FROM);
        }

        if(strFrom.equals(GKEarnHomeActivity.GKEARN_VALUE_FROMHOME)) {
            txv_skip.setVisibility(View.GONE);
            txv_description.setText(R.string.str_gkearn_invite);
        } else {
            txv_description.setText(R.string.str_gkearn_welcome);
        }

        getSession();
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_customized_back, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.customized_back){
//            proceedToGKEarnHome();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_customize_code: {
                showCustomizeCodeDialogNew();
                break;
            }

            case R.id.btn_inviteafriend:{
                referFriend();
                break;
            }

            case R.id.txv_skip:
                case R.id.img_close: {
                proceedToGKEarnHome();
                break;
            }

        }
    }

    private void proceedToGKEarnHome(){
        if(strFrom.equals(GKEarnHomeActivity.GKEARN_VALUE_FROMHOME)) {
            getActivity().finish();
        } else {
            CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
            CommonVariables.VOUCHERISFIRSTLOAD = true;
            Intent intent = new Intent(getViewContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            getViewContext().startActivity(intent);
            GKEarnHomeActivity.start(getViewContext(), GKEarnHomeActivity.GKEARN_FRAGMENT_REGISTERED_MEMBER, null);
        }
    }

    private void getSession() {
        generateReferralCode();
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

            List<String> editTextDataType  = new ArrayList<>();
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
                    editItem.setPadding(15,30,15,30);
                    editItem.setHintTextColor(ContextCompat.getColor(mContext, R.color.colorsilver));
                    String taggroup = editItem.getTag().toString();
                    if(taggroup.equals("TAG 0")) {
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
                        customizeReferralCode();
                    } else {
                        showToast("Code must have 3-7 characters. Please try again.", GlobalToastEnum.WARNING);
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    private void generateReferralCode() {
        Call<GenerateReferralCodeResponse> call = RetrofitBuild.getReferralAPIService(getViewContext())
                .generateReferralCode(
                        imei,
                        userid,
                        sessionid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        CommonVariables.gkearnreferral,
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
                        String statusOfReferral = response.body().getData().getReferralPromo();
//                        if (statusOfReferral.equals("ACTIVE")) {
//                            strCode = response.body().getData().getReferralCode();
//                            txv_referralcode.setText(response.body().getData().getReferralCode());
//                            layout_referralcode.setVisibility(View.VISIBLE);
//                            btn_inviteafriend.setVisibility(View.VISIBLE);
//                        } else {
//                            layout_referralcode.setVisibility(View.GONE);
//                            btn_inviteafriend.setVisibility(View.GONE);
//                        }

                        strCode = response.body().getData().getReferralCode();
//                        txv_referralcode.setText(response.body().getData().getReferralCode());
//                        layout_referralcode.setVisibility(View.VISIBLE);
//                        btn_inviteafriend.setVisibility(View.VISIBLE);
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
        public void onFailure(Call<GenerateReferralCodeResponse> call, Throwable t) {
            hideProgressDialog();
            //showError();
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

    private void referFriend() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, str_invite + " Use code " + strCode + ".");
        Logger.debug("vanhttp", "str_invite: " + str_invite + " Use code " + strCode + ".");
        startActivity(Intent.createChooser(intent, "Refer a Friend"));
    }


    @Override
    public void onResume() {
        super.onResume();
        ((GKEarnHomeActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((GKEarnHomeActivity)getActivity()).getSupportActionBar().show();
    }
    @Override
    public void onPause() {
        super.onPause();
        getActivity().overridePendingTransition(0, 0);
    }

}

