package com.goodkredit.myapplication.activities.loadmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.loadmessenger.TransactionsPagerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.loadmessenger.BorrowerFB;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.ViewLoadTxnDialogFragment;
import com.goodkredit.myapplication.fragments.loadmessenger.LoadTransactionsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Objects;

public class ViewLoadTransactionsActivity extends BaseActivity {

    private Toolbar toolbar;

    TabLayout tabLayout;
    ViewPager2 viewPager;
    private BorrowerFB borrowerFB;
    private MonthYearPickerDialogFragment dialogFragment = null;
    private Calendar c;
    private int currentYear;
    private int currentMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_load_transactions);

        if(getIntent() != null){
            borrowerFB = new Gson().fromJson(getIntent().getStringExtra("borrowerFB"),BorrowerFB.class);
        }

        init();

    }

    private void init() {

        c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH) + 1;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("View Load Transactions");

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);

        viewPager.setAdapter(createPagerAdapter());
        viewPager.setUserInputEnabled(false);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if(position == 0){
                tab.setText("Load Transactions");
            }else if(position == 1){
                tab.setText("Replenish Logs");
            }
        }).attach();



    }

    private TransactionsPagerAdapter createPagerAdapter() {
        return new TransactionsPagerAdapter(this,borrowerFB);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}