package com.goodkredit.myapplication.activities.gkstore;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.delivery.SetupDeliveryAddressActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.enums.GlobalToastEnum;

import hk.ids.gws.android.sclick.SClick;

/**
 * Created by GoodApps on 26/03/2018.
 */

public class GKStoreInformationActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;

    private MaterialDialog mDialog;
    private GPSTracker tracker;

    //GKSTORE INFORMATION
    private TextView txtGKStoreName;
    private TextView txtGkStoreDesc;
    private TextView txtGKStoreCategory;
    private TextView txtGKStoreRep;
    private TextView txtGKStoreMobileNo;
    private TextView txtGKStoreTelNo;
    private TextView txtGKStoreEmailAdd;
    private TextView txtGKStoreStoreAdd;
    private TextView btnshowmore;
    private ImageView gkstoremap;

    private String gkstorename = "";
    private String gkstoredesc = "";
    private String gkstorecat = "";
    private String gkstorerep = "";
    private String gkstoremobileno = "";
    private String gkstoretelno = "";
    private String gkstoremailadd = "";
    private String gkstoremercadd  = "";
    private String strmerchantlat  = "";
    private String strmerchantlong  = "";
    private String strradius  = "";

    private String latitude = "";
    private String longitude = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gkstore_information);
            //All Initializations
            init();

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        txtGKStoreName = (TextView) findViewById(R.id.txtGKStoreName);
        txtGkStoreDesc = (TextView) findViewById(R.id.txtGKStoreDesc);
        txtGKStoreCategory = (TextView) findViewById(R.id.txtGKStoreCategory);
        txtGKStoreRep = (TextView) findViewById(R.id.txtGKStoreRep);
        txtGKStoreMobileNo = (TextView) findViewById(R.id.txtGKStoreMobileNo);
        txtGKStoreTelNo = (TextView) findViewById(R.id.txtGKStoreTelNo);
        txtGKStoreEmailAdd = (TextView) findViewById(R.id.txtGKStoreEmailAdd);
        txtGKStoreStoreAdd = (TextView) findViewById(R.id.txtGKStoreStoreAdd);

        btnshowmore = (TextView) findViewById(R.id.btnshowmore);
        btnshowmore.setOnClickListener(this);

        gkstoremap = (ImageView) findViewById(R.id.gkstoremap);
        gkstoremap.setOnClickListener(this);
    }

    private void initData() {
        setupToolbar();

        setTitle("Store Information");

        Bundle b = getIntent().getExtras();

        gkstorename = b.getString("GKSTORENAME");
        gkstoredesc = b.getString("GKSTOREDESC");
        gkstorecat = b.getString("GKSTORECAT");
        gkstorerep = b.getString("GKSTOREREP");
        gkstoremobileno = b.getString("GKSTOREMOBILENO");
        gkstoretelno = b.getString("GKSTORETELNO");
        gkstoremailadd = b.getString("GKSTOREEMAILADD");
        gkstoremercadd = b.getString("GKSTOREMERCADD");
        strmerchantlat = b.getString("GKSTOREMERCHANTLAT");
        strmerchantlong = b.getString("GKSTOREMERCHANTLONG");
        strradius = b.getString("GKSTOREMERCHANTRAD");

        txtGKStoreName.setText(CommonFunctions.replaceEscapeData(gkstorename));
        txtGkStoreDesc.setText(CommonFunctions.replaceEscapeData(gkstoredesc));
        txtGKStoreCategory.setText(CommonFunctions.replaceEscapeData(gkstorecat));
        txtGKStoreRep.setText(CommonFunctions.replaceEscapeData(gkstorerep));
        txtGKStoreMobileNo.setText(CommonFunctions.replaceEscapeData("+" + gkstoremobileno));
        txtGKStoreTelNo.setText(CommonFunctions.replaceEscapeData(gkstoretelno));
        txtGKStoreEmailAdd.setText(CommonFunctions.replaceEscapeData(gkstoremailadd));
        txtGKStoreStoreAdd.setText(CommonFunctions.replaceEscapeData(gkstoremercadd));

        checkLines();

        checkGPS();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkLines() {
        txtGkStoreDesc.post(new Runnable() {
            @Override
            public void run() {
                int lines = txtGkStoreDesc.getLineCount();
                if(lines >= 5) {
                    btnshowmore.setVisibility(View.VISIBLE);
                }
                else {
                    btnshowmore.setVisibility(View.GONE);
                }
            }
        });
    }

    private void checkGPS() {
        Double currentlat = null;
        Double currentlon = null;

        tracker = new GPSTracker(getViewContext());

        if (!tracker.canGetLocation()) {
            gpsNotEnabledDialog();
        } else {
            currentlat = tracker.getLatitude();
            currentlon = tracker.getLongitude();
        }

        latitude = String.valueOf(currentlat);
        longitude = String.valueOf(currentlon);
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
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        showToast("GPS must be enabled to avail the service.", GlobalToastEnum.WARNING);
//                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnshowmore: {
                txtGkStoreDesc.setEllipsize(null);
                txtGkStoreDesc.setMaxLines(Integer.MAX_VALUE);
                btnshowmore.setVisibility(View.GONE);
                break;
            }

            case R.id.gkstoremap: {
                if (!SClick.check(SClick.BUTTON_CLICK)) return;
                tracker = new GPSTracker(getViewContext());

                if (!tracker.canGetLocation()) {
                    gpsNotEnabledDialog();
                }
                else {
                    if (!strmerchantlat.equals("") && !strmerchantlat.equals(".") && !strmerchantlat.equals("NONE") &&
                            !strmerchantlong.equals("") && !strmerchantlong.equals(".") && !strmerchantlong.equals("NONE")) {
                        Bundle args = new Bundle();
                        args.putString("latitude", String.valueOf(latitude));
                        args.putString("longitude", String.valueOf(longitude));
                        args.putString("merclatitude", String.valueOf(strmerchantlat));
                        args.putString("merclongitude", String.valueOf(strmerchantlong));
                        args.putString("mercradius", String.valueOf(strradius));

                        Intent intent = new Intent(getViewContext(), SetupDeliveryAddressActivity.class);
                        intent.putExtra("index", 2);
                        intent.putExtra("args", args);
                        startActivityForResult(intent, 1);
                    } else {
                        showWarningGlobalDialogs("There's something wrong with the store setup, please contact support");
                    }
                }
                break;
            }
        }
    }
}
