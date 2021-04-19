package com.goodkredit.myapplication.activities.egame;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.egame.EGameProductsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.egame.EGameProducts;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class EGameProductsActivity extends BaseActivity {

    private DatabaseHandler mdb;

    private RecyclerView rv_egame_products;
    private List<EGameProducts> mGridData = new ArrayList<>();
    private EGameProductsAdapter mAdapter;

    private EditText edt_search_box;
    private NestedScrollView home_body;

    //SCROLLLIMIT
    private boolean isloadmore = false;
    private boolean isbottomscroll = false;
    private boolean isfirstload = true;
    private int limit = 30;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egame_product_codes);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
    }

    private void init() {
        setupToolbarWithTitle("E-Games Products");

        mdb = new DatabaseHandler(getViewContext());
        rv_egame_products = (RecyclerView) findViewById(R.id.rv_egame_products);

        mAdapter = new EGameProductsAdapter(mGridData, getViewContext(), this, mdb);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rv_egame_products.setNestedScrollingEnabled(false);
        rv_egame_products.setLayoutManager(layoutManager);
        rv_egame_products.setAdapter(mAdapter);

        home_body = findViewById(R.id.home_body);
        home_body.setOnScrollChangeListener(scrollOnChangedListener);

        edt_search_box = findViewById(R.id.edt_search_box);
        edt_search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (count == 0) {
//                    getEGameProductsData();
                } else {
                    showSearchProduct();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        getEGameProductsData();
    }

    private void showSearchProduct(){
        if(edt_search_box.getText().length() > 0){
            List<EGameProducts> eGameProductsList = mdb.getSearchedEGameProducts(mdb, edt_search_box.getText().toString().trim());
            mAdapter.updateList(eGameProductsList);
            Logger.debug("vanhttp",  "searched products: " + new Gson().toJson(eGameProductsList));
        }
    }

    private void getEGameProductsData() {

        List<EGameProducts> eGameProductsList = mdb.getEGameProducts(mdb, limit);

        Logger.debug("checkhttp","VALUES IN LIFE" + new Gson().toJson(eGameProductsList));
        if(eGameProductsList != null) {
            if(eGameProductsList.size() > 0) {
                mAdapter.updateList(eGameProductsList);
                isloadmore = true;
            } else{
                isloadmore = false;
            }
        }

        isfirstload = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setEgameProductDetails (EGameProducts egameProducts) {
        Intent intent = new Intent();
        intent.putExtra(EGameProducts.KEY_EGAMEPRODUCT_RESULT, egameProducts);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private NestedScrollView.OnScrollChangeListener scrollOnChangedListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                isbottomscroll = true;

                if (isloadmore) {
                    if (!isfirstload) {
                        limit = limit + 30;
                    }
                    getEGameProductsData();
                }
            }
        }
    };

}
