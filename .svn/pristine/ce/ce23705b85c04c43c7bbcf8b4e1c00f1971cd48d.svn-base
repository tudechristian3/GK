package com.goodkredit.myapplication.activities.gknegosyo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gknegosyo.GKNegosyoDiscountTransactionAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.model.DiscountPerTransactionType;
import com.goodkredit.myapplication.model.GKNegosyoTransactionDetailPerServiceType;
import com.goodkredit.myapplication.responses.gknegosyo.GetDiscountTransactionsResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKNegosyoEarningDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String KEY_YEAR = "year";
    private static final String KEY_MONTH = "month";

    private DiscountPerTransactionType mDiscountPerTransactionType;

    private int limit = 0;
    private boolean hasMore = false;

    private int mYear = 2018;
    private int mMonth = 8;

    private RecyclerView rv_transactions;
    private GKNegosyoDiscountTransactionAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_earning_details);
        init();
    }

    private void init() {

        setupToolbar();

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        session = PreferenceUtils.getSessionID(getViewContext());

        mDiscountPerTransactionType = getIntent().getParcelableExtra(DiscountPerTransactionType.OBJ);
        mYear = getIntent().getIntExtra(KEY_YEAR, mYear);
        mMonth = getIntent().getIntExtra(KEY_MONTH, mMonth);

        rv_transactions = findViewById(R.id.rv_transactions);
        rv_transactions.setLayoutManager(new LinearLayoutManager(getViewContext()));
//        rv_transactions.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white)));
        mAdapter = new GKNegosyoDiscountTransactionAdapter(getViewContext());

        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        rv_transactions.setAdapter(mAdapter);

        rv_transactions.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!rv_transactions.canScrollVertically(1)) {
                    if (mAdapter.getItemCount() >= 30) {
                        if (hasMore)
                            getSession();
                    }
                }
            }
        });

        getSession();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, DiscountPerTransactionType discountPerTransactionType, int year, int month) {
        Intent intent = new Intent(context, GKNegosyoEarningDetailsActivity.class);
        intent.putExtra(DiscountPerTransactionType.OBJ, discountPerTransactionType);
        intent.putExtra(KEY_YEAR, year);
        intent.putExtra(KEY_MONTH, month);
        context.startActivity(intent);
    }

    private void getSession() {
        showProgressDialog(getViewContext(), "", "Please wait...");
        getDiscountTransactions();
    }

    private void getDiscountTransactions() {
        Call<GetDiscountTransactionsResponse> call = RetrofitBuild.getGKNegosyoAPIService(getViewContext())
                .getDiscountTransactions(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + session),
                        borrowerid,
                        session,
                        mDiscountPerTransactionType.getTransactionType(),
                        String.valueOf(mYear),
                        String.valueOf(mMonth),
                        String.valueOf(limit)
                );

        call.enqueue(getDiscountTransactionsCallback);
    }


    private Callback<GetDiscountTransactionsResponse> getDiscountTransactionsCallback = new Callback<GetDiscountTransactionsResponse>() {
        @Override
        public void onResponse(Call<GetDiscountTransactionsResponse> call, Response<GetDiscountTransactionsResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        hideProgressDialog();
                        List<GKNegosyoTransactionDetailPerServiceType> data = response.body().getData();
                        if (data.size() < 30) {
                            hasMore = false;
                        } else {
                            hasMore = true;
                            limit += 30;
                        }
                        mAdapter.update(data);
                    } else {
                        showError(response.body().getMessage());
                    }
                } else {
                    showError("Seems you are not connected to the internet. Please check your connection and try again. Thank you.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<GetDiscountTransactionsResponse> call, Throwable t) {
            hideProgressDialog();
            showError("Seems you are not connected to the internet. Please check your connection and try again. Thank you.");
        }
    };

    @Override
    public void onRefresh() {
        limit = 0;
        mSwipeRefreshLayout.setRefreshing(false);
        getSession();
    }
}
