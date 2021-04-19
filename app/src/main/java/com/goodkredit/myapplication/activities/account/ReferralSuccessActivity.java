package com.goodkredit.myapplication.activities.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.VoucherObject;

public class ReferralSuccessActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgVoucher;
    private TextView tv_message;

    private VoucherObject mVoucherObject;

    private ImageView test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_success);
        init();
    }

    private void init() {

        findViewById(R.id.btn_thanks).setOnClickListener(this);

        mVoucherObject = getIntent().getParcelableExtra("OBJ");
        imgVoucher = findViewById(R.id.imgVoucher);
        tv_message = findViewById(R.id.tv_message);
        test = findViewById(R.id.test);

        Glide.with(getViewContext())
                .load(CommonFunctions.buildVoucherURL(mVoucherObject.getVoucherProductID()))
                .into(imgVoucher);

        tv_message.setText(mVoucherObject.getMessage());

        Glide.with(getViewContext())
                .load(R.drawable.giphy)
                .into(test);

    }

    public static void start(Context context, VoucherObject voucherObject) {
        Intent intent = new Intent(context, ReferralSuccessActivity.class);
        intent.putExtra("OBJ", voucherObject);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_thanks: {
                Intent intent = new Intent(getApplicationContext(), WelcomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("OTHERS", "");
                intent.putExtra("SUBJECT", "");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            }
        }
    }
}
