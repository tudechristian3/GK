package com.goodkredit.myapplication.activities.gknegosyo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gknegosyo.GKNegosyoPackageMerchantsWithDiscountPQRAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.utilities.SpacesItemDecoration;

public class GKNegosyoPackageMerchantsWithDiscountPQR extends BaseActivity {

    private RecyclerView rv_merchants_with_discount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_package_merchants_discount_pqr);
        init();
    }

    private void init() {

        setupToolbar();

        rv_merchants_with_discount = findViewById(R.id.rv_merchants_with_discount);
        rv_merchants_with_discount.setLayoutManager(new GridLayoutManager(getViewContext(), 2, RecyclerView.VERTICAL, false));
        rv_merchants_with_discount.addItemDecoration(new SpacesItemDecoration(10));
        rv_merchants_with_discount.setAdapter(new GKNegosyoPackageMerchantsWithDiscountPQRAdapter(getViewContext()));
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, GKNegosyoPackageMerchantsWithDiscountPQR.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
