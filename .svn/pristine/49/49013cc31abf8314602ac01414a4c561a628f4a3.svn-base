package com.goodkredit.myapplication.activities.gknegosyo;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gknegosyo.GKNegosyoPackageDiscountPerServiceAdapter;
import com.goodkredit.myapplication.adapter.gknegosyo.GKNegosyoPackageVoucherInclusionsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.model.GKNegosyPackage;
import com.goodkredit.myapplication.model.GKNegosyoPackagesAndDetails;
import com.google.gson.Gson;

public class GKNegosyoPackageDetailsActivity extends BaseActivity {

    public static final String KEY_GKSERVICE = "gkservice";
    public static final String KEY_GKPACKAGE = "gknegosyopackage";
    public static final String KEY_GKNEGOSYO_DISTRO = "gknegosyodistro";

    private GKService mGKService;
    private GKNegosyPackage mGKNegosyoPackage;
    private String mGKNegosyoDistroID;
    private String mGKNegosyoDistroName;
    private Address mAddress;
    private boolean mIsReseller = false;
    private GKNegosyoPackagesAndDetails mGKNPDetails;

    private TextView tv_packagename;
    private TextView tv_packageprice;
    private TextView tv_gk_negosy_package_vouchercredits;
    private TextView tv_gk_negosyo_package_validity;
    private TextView tv_gk_negosyo_package_details;
    private ImageView imgv_package_image;

    private RecyclerView rv_gk_negosyo_package_voucher_inclusions;
    private RecyclerView rv_gk_negosyo_package_discounts_per_service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_package_details);
        init();
    }

    public static void start(Context context, GKService service, GKNegosyPackage packge, String distroID, Address address, GKNegosyoPackagesAndDetails gknpDetails, String distroName) {
        Intent intent = new Intent(context, GKNegosyoPackageDetailsActivity.class);
        intent.putExtra(KEY_GKSERVICE, service);
        intent.putExtra(KEY_GKPACKAGE, packge);
        intent.putExtra(KEY_GKNEGOSYO_DISTRO, distroID);
        intent.putExtra("KEY_DISTRO_NAME", distroName);
        intent.putExtra("KEY_ADDRESS", address);
        intent.putExtra("KEY_GKNP_DETAILS", gknpDetails);
        context.startActivity(intent);
    }

    public static void start(Context context, GKNegosyPackage pckage, GKNegosyoPackagesAndDetails details, boolean isReseller) {
        Intent intent = new Intent(context, GKNegosyoPackageDetailsActivity.class);
        intent.putExtra(KEY_GKPACKAGE, pckage);
        intent.putExtra("KEY_IS_RESELLER", isReseller);
        intent.putExtra("KEY_GKNP_DETAILS", details);
        context.startActivity(intent);
    }

    private void init() {



        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        if (getIntent().hasExtra("KEY_IS_RESELLER")) {
            mIsReseller = getIntent().getBooleanExtra("KEY_IS_RESELLER", false);
        } else {
            mGKService = getIntent().getParcelableExtra(KEY_GKSERVICE);
            mGKNegosyoDistroID = getIntent().getStringExtra(KEY_GKNEGOSYO_DISTRO);
            mGKNegosyoDistroName = getIntent().getStringExtra("KEY_DISTRO_NAME");
            mAddress = getIntent().getParcelableExtra("KEY_ADDRESS");
        }
        mGKNegosyoPackage = getIntent().getParcelableExtra(KEY_GKPACKAGE);

        mGKNPDetails = getIntent().getParcelableExtra("KEY_GKNP_DETAILS");

        Logger.debug("okhttp","GKNEGOSYO PACKAGE DETAILS : "+ new Gson().toJson(mGKNPDetails));

        tv_packagename = findViewById(R.id.tv_packagename);
        tv_packageprice = findViewById(R.id.tv_packageprice);
        tv_gk_negosy_package_vouchercredits = findViewById(R.id.tv_gk_negosy_package_vouchercredits);
        tv_gk_negosyo_package_validity = findViewById(R.id.tv_gk_negosyo_package_validity);
        tv_gk_negosyo_package_details = findViewById(R.id.tv_gk_negosyo_package_details);
        imgv_package_image = findViewById(R.id.imgv_package_image);

        rv_gk_negosyo_package_voucher_inclusions = findViewById(R.id.rv_gk_negosyo_package_voucher_inclusions);
        rv_gk_negosyo_package_voucher_inclusions.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));
        rv_gk_negosyo_package_voucher_inclusions.setAdapter(new GKNegosyoPackageVoucherInclusionsAdapter(getViewContext(), mGKNegosyoPackage.getVoucherInclusions().getProducts()));
        rv_gk_negosyo_package_voucher_inclusions.setNestedScrollingEnabled(false);

        rv_gk_negosyo_package_discounts_per_service = findViewById(R.id.rv_gk_negosyo_package_discounts_per_service);
        rv_gk_negosyo_package_discounts_per_service.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));
        rv_gk_negosyo_package_discounts_per_service.setAdapter(new GKNegosyoPackageDiscountPerServiceAdapter(getViewContext(), mGKNPDetails.getDiscountedServices(mGKNegosyoPackage.getPackageID())));
        rv_gk_negosyo_package_discounts_per_service.setNestedScrollingEnabled(false);

        setupToolbar();
        tv_packagename.setText(mGKNegosyoPackage.getPackageName());
        tv_packageprice.setText("₱" + CommonFunctions.commaFormatter(String.valueOf(mGKNegosyoPackage.getPrice())));
        tv_gk_negosy_package_vouchercredits.setText("₱" + CommonFunctions.commaFormatter(String.valueOf(mGKNegosyoPackage.getVoucherInclusions().getVoucherCredits())));
        tv_gk_negosyo_package_validity.setText(validity((float) mGKNegosyoPackage.getNumberMonthExpiry()));
        tv_gk_negosyo_package_details.setText(mGKNegosyoPackage.getPackageDescription());
        Glide.with(getViewContext())
                .load(mGKNegosyoPackage.getURL())
                .into(imgv_package_image);

        if (!mIsReseller) {
            findViewById(R.id.gk_negosyo_proceed).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GKNegosyoPackageConfirmationActivity.start(getViewContext(), mGKNegosyoPackage, mGKNegosyoDistroID, mGKService, mAddress, mGKNegosyoDistroName);
                }
            });
        } else {
            findViewById(R.id.gk_negosyo_proceed).setVisibility(View.GONE);
        }

    }

    private String validity(float nofMonths) {
        String str = "Resellership Fee good for ";
        if ((nofMonths / 12) < 1) {
            if (nofMonths > 1)
                str = str + String.valueOf(nofMonths) + " months";
            else
                str = str + String.valueOf(nofMonths) + " month";
        } else {
            if ((nofMonths / 12) > 1)
                str = str + String.valueOf(nofMonths / 12) + " years";
            else
                str = str + String.valueOf(nofMonths / 12) + " year";
        }
        return str;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
