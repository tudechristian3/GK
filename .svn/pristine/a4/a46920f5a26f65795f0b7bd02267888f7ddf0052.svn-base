package com.goodkredit.myapplication.activities.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.referafriend.ProcessReferralResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralCodeActivity extends BaseActivity implements View.OnClickListener {

    private EditText edt_code;
    private String strCode;
    private String strName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_code);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        init();
    }

    public static void start(Context context, String name) {
        Intent intent = new Intent(context, ReferralCodeActivity.class);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Enter Friend's Code");

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        session = PreferenceUtils.getSessionID(getViewContext());

        strName = getIntent().getStringExtra("name");

        edt_code = findViewById(R.id.edt_code);

        findViewById(R.id.btn_proceed).setOnClickListener(this);
        findViewById(R.id.btn_skip).setOnClickListener(this);
    }

    private boolean isEntryValid(String str) {
        if (str.trim().isEmpty() || str.trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_proceed: {
                if (isEntryValid(edt_code.getText().toString())) {
                    strCode = edt_code.getText().toString().toUpperCase().trim();
                    createSession();
                } else {
                    edt_code.setError("Enter your friend's code to get a voucher and proceed, or you may skip this step.");
                    edt_code.requestFocus();
                }
                break;
            }
            case R.id.btn_skip: {
                showConfirmationDialog();
                break;
            }
        }
    }

    private void showConfirmationDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("You will no longer get free vouchers if you skip this step. Are you sure?")
                .cancelable(true)
                .positiveText("SKIP ANYWAY")
                .negativeText("CANCEL")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(getApplicationContext(), WelcomePageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("OTHERS", "");
                        intent.putExtra("SUBJECT", "");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                })
                .show();
    }

    private void createSession() {
        showProgressDialog(getViewContext(), "", "Please wait...");
        processReferral();
    }

    private void processReferral() {
        Call<ProcessReferralResponse> call = RetrofitBuild.getReferralAPIService(getViewContext())
                .processReferral(
                        imei,
                        userid,
                        session,
                        CommonFunctions.getSha1Hex(imei + userid + session),
                        borrowerid,
                        strName,
                        strCode,
                        "ANDROID"
                );

        call.enqueue(processReferralCallback);
    }

    private Callback<ProcessReferralResponse> processReferralCallback = new Callback<ProcessReferralResponse>() {
        @Override
        public void onResponse(Call<ProcessReferralResponse> call, Response<ProcessReferralResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        ReferralSuccessActivity.start(getViewContext(), response.body().getData());
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    } else {
                        showErrorToast(response.body().getMessage());
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ProcessReferralResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };
}
