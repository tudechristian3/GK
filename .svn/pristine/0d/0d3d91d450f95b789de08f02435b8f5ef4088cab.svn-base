package com.goodkredit.myapplication.activities.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoReferAResellerActivity;
import com.goodkredit.myapplication.base.BaseActivity;

public class NotificationResellerReferralSuccessActivity extends BaseActivity implements View.OnClickListener {

    private String mStr_message;
    private String mStr_subject;

    private TextView tv_message;

    private ImageView test;
    private ImageView imgVoucher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_reseller_referral_success);
        init();
    }

    public static void start(Context context, String message, String subject) {
        Intent intent = new Intent(context, NotificationResellerReferralSuccessActivity.class);
        intent.putExtra("str", message);
        intent.putExtra("subject", subject);
        context.startActivity(intent);
    }


    private void init() {
        try {
            mStr_message = getIntent().getStringExtra("str");
            mStr_subject = getIntent().getStringExtra("subject");
            tv_message = findViewById(R.id.tv_message);
            tv_message.setText(mStr_message);
            findViewById(R.id.btn_close).setOnClickListener(this);
            findViewById(R.id.btn_refer_more).setOnClickListener(this);
            test = findViewById(R.id.test);
            imgVoucher = findViewById(R.id.imgVoucher);

            Glide.with(getViewContext())
                    .load(R.drawable.giphy)
                    .into(test);
            if (mStr_subject.equals("RESELLERREFERRALPROMO")) {
                Glide.with(getViewContext())
                        .load(R.drawable.genericvoucher)
                        .into(imgVoucher);
            } else {
                Glide.with(getViewContext())
                        .load(R.drawable.genericvoucher)
                        .into(imgVoucher);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close: {
                onBackPressed();
                break;
            }
            case R.id.btn_refer_more: {
                GKNegosyoReferAResellerActivity.start(getViewContext());
                finish();
                break;
            }
        }
    }
}
