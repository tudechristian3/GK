package com.goodkredit.myapplication.activities.merchants;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.merchants.MerchantOrganizationTypeHorizontalRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Merchants;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;

public class ViewAllMerchantsByOrganizationTypeActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private MerchantOrganizationTypeHorizontalRecyclerAdapter mAdapter;
    private DatabaseHandler mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_merchants_by_organization_type);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        setupToolbar();

        init();

        initData();

    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_all_merchants);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        Bundle args = getIntent().getBundleExtra("args");
        Merchants merchants = args.getParcelable("morganizations");

        setTitle(CommonFunctions.replaceEscapeData(capitalizeWord(merchants.getOrganizationType())));

        recyclerView.setLayoutManager(new GridLayoutManager(getViewContext(), 3));
       // recyclerView.addItemDecoration(new BillsSpacesItemDecoration(20));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new MerchantOrganizationTypeHorizontalRecyclerAdapter(getViewContext());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setMerchantsData(mdb.getMerchantsByOrganiationType(mdb, merchants.getOrganizationType()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, ViewAllMerchantsByOrganizationTypeActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }
}
