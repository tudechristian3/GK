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
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.base.BaseActivity;

public class NotificationReferralSuccessActivity extends BaseActivity implements View.OnClickListener {

    private String mStr_message;

    private TextView tv_message;

    private ImageView test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_referral_success);
        init();
    }

    private void init() {
        mStr_message = getIntent().getStringExtra("str");
        tv_message = findViewById(R.id.tv_message);
        tv_message.setText(mStr_message);
        findViewById(R.id.btn_thanks).setOnClickListener(this);
        findViewById(R.id.btn_close).setOnClickListener(this);
        test = findViewById(R.id.test);

        Glide.with(getViewContext())
                .load(R.drawable.giphy)
                .into(test);
    }

    public static void start(Context context, String message) {
        Intent intent = new Intent(context, NotificationReferralSuccessActivity.class);
        intent.putExtra("str", message);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_thanks: {
                MainActivity.start(getViewContext(), 7);
                break;
            }
            case R.id.btn_close: {
                onBackPressed();
                break;
            }
        }
    }
}
