package com.goodkredit.myapplication.activities.gknegosyo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.utilities.CustomMapFragment;
import com.goodkredit.myapplication.model.gknegosyo.Distributor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class GKNegosyoDistributorDetailsActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;

    private Distributor mGKNegosyoDistributor;
    private GKService mGKService;

    private double gpsLong = 123.900063;
    private double gpsLat = 10.321155;

    private LinearLayout layout_distro_details;

    private TextView tv_distro_fullname;
    private TextView tv_distro_address;
    private TextView tv_distro_mobile;
    private TextView tv_distro_phone;
    private TextView tv_distro_email;
    private TextView tv_distro_lbl;

    private String str_distro_fullname;
    private String str_distro_adress;

    private Address mAddress;

    private boolean mIsReseller = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_distributor_details);
        init();
    }

    private void init() {
        try {
            setupToolbar();

            if (getIntent().hasExtra("KEY_IS_RESELLER")) {
                mIsReseller = getIntent().getBooleanExtra("KEY_IS_RESELLER", false);
            } else {
                mGKService = getIntent().getParcelableExtra(GKService.KEY_SERVICE_OBJ);
                mAddress = getIntent().getParcelableExtra("KEY_ADDRESS");
            }

            mGKNegosyoDistributor = getIntent().getParcelableExtra(Distributor.KEY_GK_NEG_DISTRO);

            layout_distro_details = findViewById(R.id.layout_distro_details);
            layout_distro_details.setVisibility(View.VISIBLE);

            tv_distro_fullname = findViewById(R.id.tv_distro_fullname);
            tv_distro_address = findViewById(R.id.tv_distro_address);
            tv_distro_mobile = findViewById(R.id.tv_distro_mobile);
            tv_distro_phone = findViewById(R.id.tv_distro_phone);
            tv_distro_email = findViewById(R.id.tv_distro_email);
            tv_distro_lbl = findViewById(R.id.tv_distro_lbl);

            findViewById(R.id.btn_proceed).setOnClickListener(this);

            if (!mIsReseller) {
                findViewById(R.id.btn_proceed).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.btn_proceed).setVisibility(View.GONE);
                tv_distro_lbl.setText("You are under this distributor.");
            }

            gpsLat = Double.parseDouble(mGKNegosyoDistributor.getLatitude());
            gpsLong = Double.parseDouble(mGKNegosyoDistributor.getLongitude());

            if (mMap == null) {
                CustomMapFragment mapFragment = (CustomMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }

            setDistributorDetailsDisplay(mGKNegosyoDistributor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setDistributorDetailsDisplay(Distributor gkNegosyoDistributor) {
        tv_distro_fullname.setText(gkNegosyoDistributor.getFullName());
        tv_distro_address.setText(gkNegosyoDistributor.getFullAddress());
        tv_distro_mobile.setText("+" + gkNegosyoDistributor.getAuthorizedMobile());
        tv_distro_phone.setText(gkNegosyoDistributor.getAuthorizedTelNo());
        tv_distro_email.setText(gkNegosyoDistributor.getAuthorizedEmail());
    }

    public static void start(Context context, Distributor gkNegosyoDistributor, GKService gkService, Address address) {
        Intent intent = new Intent(context, GKNegosyoDistributorDetailsActivity.class);
        intent.putExtra(Distributor.KEY_GK_NEG_DISTRO, gkNegosyoDistributor);
        intent.putExtra(GKService.KEY_SERVICE_OBJ, gkService);
        intent.putExtra("KEY_ADDRESS", address);
        context.startActivity(intent);
    }

    public static void start(Context context, Distributor gkNegosyoDistributor, boolean mIsReseller) {
        Intent intent = new Intent(context, GKNegosyoDistributorDetailsActivity.class);
        intent.putExtra(Distributor.KEY_GK_NEG_DISTRO, gkNegosyoDistributor);
        intent.putExtra("KEY_IS_RESELLER", mIsReseller);
        context.startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getViewContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            mMap.setMyLocationEnabled(true);
        }

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(gpsLat, gpsLong))
                .radius(mGKNegosyoDistributor.getServiceArea())
                .strokeWidth(8)
                .strokeColor(Color.argb(100, 255, 0, 0))
                .fillColor(Color.argb(50, 255, 0, 0))
                .clickable(true));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsLat, gpsLong), 5));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsLat, gpsLong), 5));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_proceed: {
                GKNegosyoPackagesActivity.start(getViewContext(), mGKService, mGKNegosyoDistributor.getDistributorID(), mAddress, mGKNegosyoDistributor.getFullName());
                finish();
                break;
            }
        }
    }
}
