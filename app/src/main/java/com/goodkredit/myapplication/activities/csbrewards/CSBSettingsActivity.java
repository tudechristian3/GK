package com.goodkredit.myapplication.activities.csbrewards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;

public class CSBSettingsActivity extends BaseActivity implements View.OnClickListener {


    private GKService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csb_settings);
        init();
    }

    private void init() {
        setupToolbar();
        findViewById(R.id.csb_settings_change_mobile_number).setOnClickListener(this);
        findViewById(R.id.csb_settings_contact_us).setOnClickListener(this);
        findViewById(R.id.csb_settings_about_us).setOnClickListener(this);
        mService = getIntent().getParcelableExtra(GKService.KEY_SERVICE_OBJ);
    }

    public static void start(Context context, GKService service) {
        Intent intent = new Intent(context, CSBSettingsActivity.class);
        intent.putExtra(GKService.KEY_SERVICE_OBJ, service);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.csb_settings_change_mobile_number: {
                CSBSettingsCommonActivity.start(getViewContext(), CSBSettingsCommonActivity.CSB_REWARDS_CHANGE_MOBILE, mService);
                break;
            }
            case R.id.csb_settings_contact_us: {
                showError("Will be available soon.");
                //CSBSettingsCommonActivity.start(getViewContext(), CSBSettingsCommonActivity.CSB_REWARDS_CHANGE_MOBILE, mService);
                break;
            }
            case R.id.csb_settings_about_us: {
                showError("Will be available soon.");
              //  CSBSettingsCommonActivity.start(getViewContext(), CSBSettingsCommonActivity.CSB_REWARDS_CHANGE_MOBILE, mService);
                break;
            }
            default: {
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
