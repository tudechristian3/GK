package com.goodkredit.myapplication.activities.gknegosyo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.ResellerLocationMapFragment;
import com.goodkredit.myapplication.utilities.V2Utils;

public class GKNegosyoResellerLocationActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_location);
        setupToolbar();

        if (ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getViewContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA}, 6325);
        } else {
            if (!isGPSEnabled(getViewContext())) {
                gpsNotEnabledDialog();
            }
        }
        displayView(0);
    }

    public void setData(Address address) {
        Intent intent = new Intent();
        intent.putExtra("address", address);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void gpsNotEnabledDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .content("GPS is disabled in your device.\nWould you like to enable it?")
                .cancelable(false)
                .positiveText("Go to Settings")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        showToast("GPS must be enabled to avail the service.", GlobalToastEnum.WARNING);
                        finish();
                    }
                })
                .show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mDialog.getView());
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
        setResult(Activity.RESULT_CANCELED);
    }

    private void displayView(int position) {
        try {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new ResellerLocationMapFragment();
                    break;
                default:
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.gk_negosyo_location_container, fragment);
                fragmentTransaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
