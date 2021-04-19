package com.goodkredit.myapplication.activities.cooperative;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;

public class CoopLandingPageActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooplandingpage);
        init();
    }

    private void init() {
        setupToolbar();
    }

    private static void start(Context context) {
        Intent intent = new Intent(context, CoopLandingPageActivity.class);
        context.startActivity(intent);
    }
}
