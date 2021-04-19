package com.goodkredit.myapplication.activities.gknegosyo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.GKNegosyPackage;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKNegosyoEnterReferralCodeActivity extends BaseActivity implements View.OnClickListener {

    private GKNegosyPackage mGKNegosyoPackage;

    private String strReferralCode;

    private EditText edt_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_enter_code);
        init();
    }

    private void init() {
        setupToolbar();

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        session = PreferenceUtils.getSessionID(getViewContext());

        edt_code = findViewById(R.id.edt_code);

        mGKNegosyoPackage = getIntent().getParcelableExtra("OBJ");
        findViewById(R.id.btn_proceed).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }

    private void getSession() {
        showProgressDialog(getViewContext(), "", "Please wait...");
        validateResellerReferralCode();
    }

    private void validateResellerReferralCode() {
        Call<GenericResponse> call = RetrofitBuild.getReferralAPIService(getViewContext())
                .validateResellerReferralCode(
                        imei,
                        userid,
                        session,
                        CommonFunctions.getSha1Hex(imei + userid + session),
                        borrowerid,
                        mGKNegosyoPackage.getPackageID(),
                        strReferralCode,
                        "ANDROID"
                );

        call.enqueue(validateResellerReferralCodeCallback);
    }

    private Callback<GenericResponse> validateResellerReferralCodeCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        showToast("Referral code entered is VALID.", GlobalToastEnum.INFORMATION);
                        Intent intent = new Intent();
                        intent.putExtra("code", strReferralCode.toUpperCase());
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        showError(response.body().getMessage());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_proceed: {
                if (!edt_code.getText().toString().trim().isEmpty()) {
                    strReferralCode = edt_code.getText().toString().trim();
                    getSession();
                } else {
                    showError("Referral code is empty. Please fill up to proceed.");
                }
                break;
            }
        }
    }
}
