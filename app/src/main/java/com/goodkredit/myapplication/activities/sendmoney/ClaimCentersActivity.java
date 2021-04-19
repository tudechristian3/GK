package com.goodkredit.myapplication.activities.sendmoney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.sendmoney.ClaimCentersAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.utilities.SpacesItemDecoration;

public class ClaimCentersActivity extends BaseActivity {

    private RecyclerView rvClaimCenters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money_claim_centers);
        init();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ClaimCentersActivity.class);
        context.startActivity(intent);
    }

    private void init() {
        setupToolbar();

        rvClaimCenters = (RecyclerView) findViewById(R.id.rv_centers);
        rvClaimCenters.setLayoutManager(new GridLayoutManager(getViewContext(), 3));
        rvClaimCenters.addItemDecoration(new SpacesItemDecoration(5));
        rvClaimCenters.setAdapter(new ClaimCentersAdapter(getViewContext()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
