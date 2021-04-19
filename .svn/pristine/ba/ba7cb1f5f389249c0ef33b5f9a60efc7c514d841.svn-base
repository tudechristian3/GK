package com.goodkredit.myapplication.activities.publicsponsor;

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
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.publicsponsor.PublicSponsor;
import com.goodkredit.myapplication.responses.GetPublicSponsorPromoResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class V2PublicSponsorPromotionsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

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
    PublicSponsor sponsor;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_public_ponsor_promos);

        try {

            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            name = findViewById(R.id.sponsorDetailsName);
            address = findViewById(R.id.sponsorAddress);
            telephone = findViewById(R.id.sponsorDetailsTelephone);
            mobile = findViewById(R.id.sponsorDetailsMobile);
            email = findViewById(R.id.sponsorDetailsEmail);
            sponsorlogo = findViewById(R.id.sponsorlogo);

            sponsor = getIntent().getParcelableExtra(PublicSponsor.KEY_PUBLICSPONSOR);
            sponsorpromos = findViewById(R.id.sponsorpromos);

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
            //recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
            recyclerView.setNestedScrollingEnabled(false);

            mAdapter = new PublicSponsorPromoRecyclerAdapter(getViewContext());
            recyclerView.setAdapter(mAdapter);

            //empty layout
            emptyLayout = findViewById(R.id.emptyLayout);
            textView11 = findViewById(R.id.textView11);
            refresh = findViewById(R.id.refresh);
            refresh.setOnClickListener(this);


            //no internet connection
            nointernetconnection = findViewById(R.id.nointernetconnection);
            refreshnointernet = findViewById(R.id.refreshnointernet);
            refreshnointernet.setOnClickListener(this);

            //loader
            mLlLoader = findViewById(R.id.loaderLayout);
            mTvFetching = findViewById(R.id.fetching);
            mTvWait = findViewById(R.id.wait);

            nested_scroll = findViewById(R.id.nested_scroll);
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

            mSwipeRefreshLayout = findViewById(R.id.swipe_container);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mSwipeRefreshLayout.setEnabled(true);

            //SET DATA


            name.setText("(" + sponsor.getGuarantorID() + ") " + CommonFunctions.replaceEscapeData(sponsor.getGuarantorName()));
            address.setText(CommonFunctions.replaceEscapeData(sponsor.getStreetAddress().toUpperCase()) + ", " + CommonFunctions.replaceEscapeData(sponsor.getCity().toUpperCase()) + ", " + CommonFunctions.replaceEscapeData(sponsor.getProvince().toUpperCase()) + ", " + CommonFunctions.replaceEscapeData(sponsor.getCountry().toUpperCase()));
//            address.setText(CommonFunctions.buildAddress(new String[] {sponsor.getStreetAddress(), sponsor.getCity(), sponsor.getProvince(), sponsor.getCountry().toUpperCase()}));
            telephone.setText(sponsor.getAuthorisedTelNo());
            mobile.setText("+".concat(sponsor.getAuthorisedMobileNo()));
            email.setText(sponsor.getAuthorisedEmailAddress());

            log("address: " + address.getText().toString());
            log("street: " + sponsor.getStreetAddress());
            log("adressparams: " + CommonFunctions.buildAddress(new String[] {sponsor.getStreetAddress(), sponsor.getCity(), sponsor.getProvince(), sponsor.getCountry()}));
            String str_sponsorlogo = CommonFunctions.parseXML(sponsor.getNotes1(), "logo");

            Glide.with(this)
                    .asBitmap()
                    .load(str_sponsorlogo)
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

            if (sponsor.getAuthorisedEmailAddress().equals(".") || sponsor.getAuthorisedEmailAddress().equals("")) {
                email.setVisibility(View.GONE);
            } else {
                email.setVisibility(View.VISIBLE);
            }

            if (sponsor.getAuthorisedTelNo().equals(".") || sponsor.getAuthorisedTelNo().equals("")) {
                telephone.setVisibility(View.GONE);
            } else {
                telephone.setVisibility(View.VISIBLE);
            }

            if (sponsor.getAuthorisedMobileNo().equals(".") || sponsor.getAuthorisedMobileNo().equals("")) {
                mobile.setVisibility(View.GONE);
            } else {
                mobile.setVisibility(View.VISIBLE);
            }

            initData();

        } catch (Exception e) {
            e.printStackTrace();


        }
    }


    private void initData() {

        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());


        getSession();
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//
//            mTvFetching.setText("Fetching Public Sponsor Promos..");
//            mTvWait.setText(" Please wait...");
//            mLlLoader.setVisibility(View.VISIBLE);
//
//            isLoading = true;
//
//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);
//        } else {
//
//            mSwipeRefreshLayout.setRefreshing(false);
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            //showError(getString(R.string.generic_internet_error_message));
//            showWarningToast("Seems you are not connected to the internet. Please check your connection and try again. Thank you.");
//        }
//
//    }
//
//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    isLoading = false;
//                    mSwipeRefreshLayout.setRefreshing(false);
////                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    isLoading = false;
//                    mSwipeRefreshLayout.setRefreshing(false);
////                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    isLoading = false;
//                    mSwipeRefreshLayout.setRefreshing(false);
////                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    getPublicSponsorPromos(getPublicSponsorPromoSession);
//                }
//            } else {
//                isLoading = false;
//                mSwipeRefreshLayout.setRefreshing(false);
////                hideProgressDialog();
//                mLlLoader.setVisibility(View.GONE);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            mSwipeRefreshLayout.setRefreshing(false);
////            hideProgressDialog();
////            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            mTvFetching.setText("Fetching Public Sponsor Promos..");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            isLoading = true;

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            //getPublicSponsorPromos(getPublicSponsorPromoSession);
            getPublicSponsorPromosV2();

        } else {

            refreshnointernet.setEnabled(true);
            refresh.setEnabled(true);

            isLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
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
                        sponsor.getGuarantorID());
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
                    //showError(response.body().getMessage());
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                //showError();
                showErrorGlobalDialogs();
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
            case R.id.refresh: {
                refresh.setEnabled(false);
                getSession();
                break;
            }
            case R.id.refreshnointernet:
                refreshnointernet.setEnabled(false);
                getSession();
                break;

        }
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getPublicSponsorPromosV2(){
        try{

            if(CommonFunctions.getConnectivityStatus(getViewContext())> 0){

                LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("limit",String.valueOf(limit));
                parameters.put("guarantorid",sponsor.getGuarantorID());

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getPublicSponsorPromos");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getPublicSponsorPromosV2Object(getPublicSponsorPromoSessionV2);

            }else{
                refreshnointernet.setEnabled(true);
                refresh.setEnabled(true);
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showNoInternetToast();
            }


        }catch (Exception e){
            refreshnointernet.setEnabled(true);
            refresh.setEnabled(true);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showError();
            e.printStackTrace();
        }
    }

    private void getPublicSponsorPromosV2Object(Callback<GenericResponse> getPublicSponsorsPromoCallback) {
        Call<GenericResponse> getpublicsponsorspromo = RetrofitBuilder.getPublicSponsorV2API(getViewContext())
                .getPublicSponsorPromos(authenticationid,sessionid,param);
        getpublicsponsorspromo.enqueue(getPublicSponsorsPromoCallback);
    }

    private final Callback<GenericResponse> getPublicSponsorPromoSessionV2 = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            refreshnointernet.setEnabled(true);
            refresh.setEnabled(true);
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());

                if (response.body().getStatus().equals("000")) {
                    String decrypteData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    if (mdb != null) {

                        List<PublicSponsorPromos> list = new Gson().fromJson(decrypteData,
                                new TypeToken<List<PublicSponsorPromos>>() {}.getType());

                        isloadmore = list.size() > 0;
                        isLoading = false;
                        isfirstload = false;

                        if (!isbottomscroll) {
                            mdb.truncateTable(mdb, DatabaseHandler.PUBLIC_SPONSOR_PROMOS);
                        }

                        for (PublicSponsorPromos pspromo : list) {
                            mdb.insertPublicSponsorPromos(mdb, pspromo);
                        }

                        updateList(mdb.getPublicSponsorPromoDB(mdb));

                    }

                } else {
                    //showError(response.body().getMessage());
                    if(response.body().getStatus().equalsIgnoreCase("error")){
                        showError();
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else{
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            } else {
                //showError();
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            refreshnointernet.setEnabled(true);
            refresh.setEnabled(true);
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };



}
