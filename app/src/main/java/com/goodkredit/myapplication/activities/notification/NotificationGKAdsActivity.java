package com.goodkredit.myapplication.activities.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;

public class NotificationGKAdsActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TITLE = "title";
    public static final String KEY_URL = "url";

    private ImageView mImgAdImage;
    private TextView mAdTitle;
    private TextView mAdDescription;

    private String mMessage;
    private String mTitle;
    private String mURL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaiton_gkads);
        overridePendingTransition(R.anim.fade_in_200, R.anim.fade_out_200);
        init();
    }

    private void init() {
        mMessage = getIntent().getStringExtra(KEY_MESSAGE);
        mTitle = getIntent().getStringExtra(KEY_TITLE);
        mURL = getIntent().getStringExtra(KEY_URL);

        mAdDescription = findViewById(R.id.tv_ad_description);
        mAdTitle = findViewById(R.id.tv_ad_title);
        mImgAdImage = findViewById(R.id.imgV_ad_image);

        findViewById(R.id.btn_close_ad).setOnClickListener(this);

        mAdDescription.setText(CommonFunctions.replaceEscapeData(mMessage));
        mAdTitle.setText(CommonFunctions.replaceEscapeData(mTitle));
        Glide.with(getViewContext())
                .load(mURL)
                .apply(RequestOptions.placeholderOf(R.drawable.generic_placeholder_gk_background).fitCenter())
                .into(mImgAdImage);
    }

    public static void start(Context context, String message, String title, String imageURL) {
        Intent intent = new Intent(context, NotificationGKAdsActivity.class);
        intent.putExtra(KEY_MESSAGE, message);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_URL, imageURL);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_ad: {
                onBackPressed();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.fade_in_200, R.anim.fade_out_200);
        super.onBackPressed();
    }
}
