package com.goodkredit.myapplication.fragments.sponsors;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.publicsponsor.PublicSponsorPromoRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.PublicSponsorPromos;
import com.goodkredit.myapplication.bean.Sponsor;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GetPublicSponsorPromoResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicPonsorPromos extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private TextView txnsponsorpromo;
    private RecyclerView recyclerView;
    private PublicSponsorPromoRecyclerAdapter mAdapter;


    private Toolbar toolbar;
    private DatabaseHandler mdb;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private int limit = 0;
    private boolean isloadmore;
    private boolean isbottomscroll;
    private boolean isfirstload = true;
    private boolean isLoading = false;
    private String guarantorid;

    private TextView name;
    private TextView address;
    private TextView email;
    private TextView telephone;
    private TextView mobile;
    private ImageView sponsorlogo;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NestedScrollView nested_scroll;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    private LinearLayout sponsorpromos;
    Sponsor sponsor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_public_ponsor_promos);

        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            name = (TextView) findViewById(R.id.sponsorDetailsName);
            address = (TextView) findViewById(R.id.sponsorAddress);
            telephone = (TextView) findViewById(R.id.sponsorDetailsTelephone);
            mobile = (TextView) findViewById(R.id.sponsorDetailsMobile);
            email = (TextView) findViewById(R.id.sponsorDetailsEmail);
            sponsorlogo = (ImageView) findViewById(R.id.sponsorlogo);

            sponsor = (Sponsor) getIntent().getSerializableExtra("SPONSOR_OBJECT");
            sponsorpromos = (LinearLayout) findViewById(R.id.sponsorpromos);


            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
            //recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
            recyclerView.setNestedScrollingEnabled(false);

            mAdapter = new PublicSponsorPromoRecyclerAdapter(getViewContext());
            recyclerView.setAdapter(mAdapter);

            //empty layout
            emptyLayout = (RelativeLayout) findViewById(R.id.emptyLayout);
            textView11 = (TextView) findViewById(R.id.textView11);
            refresh = (ImageView) findViewById(R.id.refresh);
            refresh.setOnClickListener(this);


            //no internet connection
            nointernetconnection = (RelativeLayout) findViewById(R.id.nointernetconnection);
            refreshnointernet = (ImageView) findViewById(R.id.refreshnointernet);
            refreshnointernet.setOnClickListener(this);

            //loader
            mLlLoader = (RelativeLayout) findViewById(R.id.loaderLayout);
            mTvFetching = (TextView) findViewById(R.id.fetching);
            mTvWait = (TextView) findViewById(R.id.wait);

            nested_scroll = (NestedScrollView) findViewById(R.id.nested_scroll);
            nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY) {
                    }

                    if (scrollY < oldScrollY) {
                    }

                    if (scrollY == 0) {
                    }

                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        isbottomscroll = true;
                        if (isloadmore) {
                            if (!isfirstload) {
                                limit = limit + 30;
                            }

                            getSession();
                        }
                    }
                }
            });

            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mSwipeRefreshLayout.setEnabled(true);

            //SET DATA
            name.setText("(" + sponsor.getSponsorID() + ") " + CommonFunctions.replaceEscapeData(sponsor.getSponsorName()));
            address.setText(CommonFunctions.replaceEscapeData(sponsor.getStreet()) + ", " + CommonFunctions.replaceEscapeData(sponsor.getCity()) + ", " + CommonFunctions.replaceEscapeData(sponsor.getProvince()) + ", " + CommonFunctions.replaceEscapeData(sponsor.getCountry()));
            telephone.setText(sponsor.getTelephoneNumber());
            mobile.setText(sponsor.getMobileNo());
            email.setText(sponsor.getEmail());


            Glide.with(this)
                    .asBitmap()
                    .load(sponsor.getSponsorLogo())
                    .apply(new RequestOptions()
                            .fitCenter())
                    .into(new BaseTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            Logger.debug("ANN", "SPONSORLOGO" + resource);
                            sponsorlogo.setImageBitmap(resource);
                        }

                        @Override
                        public void getSize(SizeReadyCallback cb) {
                            cb.onSizeReady(CommonFunctions.getScreenWidthPixel(getViewContext()), 150);
                        }

                        @Override
                        public void removeCallback(SizeReadyCallback cb) {

                        }
                    });

            if (sponsor.getEmail().equals(".") || sponsor.getEmail().equals("")) {
                email.setVisibility(View.GONE);
            } else {
                email.setVisibility(View.VISIBLE);
            }

            if (sponsor.getTelephoneNumber().equals(".") || sponsor.getTelephoneNumber().equals("")) {
                telephone.setVisibility(View.GONE);
            } else {
                telephone.setVisibility(View.VISIBLE);
            }

            if (sponsor.getMobileNo().equals(".") || sponsor.getMobileNo().equals("")) {
                mobile.setVisibility(View.GONE);
            } else {
                mobile.setVisibility(View.VISIBLE);
            }

            initData();

        } catch (Exception e) {
            e.printStackTrace();


        }
        ;
    }


    private void initData() {

        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());


        getSession();
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mTvFetching.setText("Fetching Public Sponsor Promos..");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            isLoading = true;
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getPublicSponsorPromos(getPublicSponsorPromoSession);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }
    }

    private void getPublicSponsorPromos(Callback<GetPublicSponsorPromoResponse> getPublicSponsorPromoResponseCallback) {
        Call<GetPublicSponsorPromoResponse> getpublicsponsorpromos = RetrofitBuild.getPublicSponsorAPI(getViewContext())
                .getPublicSponsorPromoCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        limit,
                        sponsor.getSponsorID());
        getpublicsponsorpromos.enqueue(getPublicSponsorPromoResponseCallback);
    }


    private final Callback<GetPublicSponsorPromoResponse> getPublicSponsorPromoSession = new Callback<GetPublicSponsorPromoResponse>() {

        @Override
        public void onResponse(Call<GetPublicSponsorPromoResponse> call, Response<GetPublicSponsorPromoResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    if (mdb != null) {

                        isloadmore = response.body().getPublicSponsorPromos().size() > 0;
                        isLoading = false;
                        isfirstload = false;

                        if (!isbottomscroll) {
                            mdb.truncateTable(mdb, DatabaseHandler.PUBLIC_SPONSOR_PROMOS);
                        }

                        List<PublicSponsorPromos> publicsponsorList = new ArrayList<>();
                        publicsponsorList = response.body().getPublicSponsorPromos();
                        for (PublicSponsorPromos pspromo : publicsponsorList) {
                            mdb.insertPublicSponsorPromos(mdb, pspromo);
                        }

                        updateList(mdb.getPublicSponsorPromoDB(mdb));

                    }


                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetPublicSponsorPromoResponse> call, Throwable t) {
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    private void updateList(List<PublicSponsorPromos> data) {
        showNoInternetConnection(false);
        if (data.size() > 0) {

            //    sponsorpromos.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            mAdapter.update(data);
        } else {

            //  sponsorpromos.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            mAdapter.clear();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        if (mdb != null) {
            mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PPV_CATALOGS);
            if (mAdapter != null) {
                mAdapter.clear();
            }

            mSwipeRefreshLayout.setRefreshing(true);
            isfirstload = false;
            limit = 0;
            isbottomscroll = false;
            isloadmore = true;

            getSession();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshnointernet: {
                getSession();
                break;
            }
            case R.id.refresh: {
                getSession();
                break;
            }

        }
    }
}
