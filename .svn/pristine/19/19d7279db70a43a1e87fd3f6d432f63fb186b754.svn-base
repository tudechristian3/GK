package com.goodkredit.myapplication.fragments.skycable;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.adapter.skycable.SkycablePayPerViewActionRequiredAdapter;
import com.goodkredit.myapplication.adapter.skycable.SkycablePayPerViewRecylerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.SkycablePPV;
import com.goodkredit.myapplication.bean.SkycablePPVHistory;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.skycable.GetSkycablePPVCatalogsResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkycablePayPerViewFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private TextView txvPpv;
    private RecyclerView recyclerView;
    private SkycablePayPerViewRecylerAdapter mAdapter;

    private DatabaseHandler mdb;
//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private int limit = 0;
    private boolean isloadmore;
    private boolean isbottomscroll;
    private boolean isfirstload = true;
    private boolean isLoading = false;

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

    private LinearLayout skycablePpv;

    private TextView txvViewHistory;

    //ACTION REQUIRED
    private LinearLayout linearActionRequiredLayout;
    private TextView txvActionRequiredWarning;
    private RecyclerView recyclerViewSubscriptions;
    private SkycablePayPerViewActionRequiredAdapter mSubscriptionAdapter;

    //UNIFIED SESSION
    private String sessionid = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_pay_per_view, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        if (isAdded()) {
//            Skycable
            getActivity().setTitle("SKYCABLE");
        }

        txvViewHistory = (TextView) view.findViewById(R.id.txvViewHistory);
        txvViewHistory.setOnClickListener(this);

        skycablePpv = (LinearLayout) view.findViewById(R.id.skycablePpv);
        txvPpv = (TextView) view.findViewById(R.id.txvPpv);
        txvPpv.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "PAY PER VIEW"));
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        //recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new SkycablePayPerViewRecylerAdapter(getViewContext());
        recyclerView.setAdapter(mAdapter);

        //empty layout
        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        nested_scroll = (NestedScrollView) view.findViewById(R.id.nested_scroll);
        nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll DOWN");
                }

                if (scrollY < oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll UP");
                }

                if (scrollY == 0) {
//                    Logger.debug("antonhttp", "TOP SCROLL");
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    Logger.debug("antonhttp", "======BOTTOM SCROLL=======");
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

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        linearActionRequiredLayout = (LinearLayout) view.findViewById(R.id.linearActionRequiredLayout);
        txvActionRequiredWarning = (TextView) view.findViewById(R.id.txvActionRequiredWarning);
        txvActionRequiredWarning.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "ACTION REQUIRED"));

        recyclerViewSubscriptions = (RecyclerView) view.findViewById(R.id.recyclerViewSubscriptions);
        recyclerViewSubscriptions.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerViewSubscriptions.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        recyclerViewSubscriptions.setNestedScrollingEnabled(false);
        mSubscriptionAdapter = new SkycablePayPerViewActionRequiredAdapter(getViewContext(), true);
        recyclerViewSubscriptions.setAdapter(mSubscriptionAdapter);
    }

    private void initData() {

        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

//        List<SkycablePPV> mGridData = new ArrayList<>();
//        mGridData.add(new SkycablePPV("http://images-mysky.com.ph.s3.amazonaws.com/production/media-upload/1521180238_ftblsp-carouselpromo-stadium.jpg", "Football Season Pass featuring the 2018 FIFA World Cup Russia", "Get the special early bird rate of P799 instead of P999 from March 9 to May 31", 799));
//        mGridData.add(new SkycablePPV("http://images-mysky.com.ph.s3.amazonaws.com/production/media-upload/1521695104_header-revenger-squad-desktop.jpg", "Gandarrapiddo! The Revenger Squad back-to-back with Unexpectedly Yours", "Available in HIGH and STANDARD DEFINITION", 99));
//        mAdapter.update(mGridData);


        getSession();
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
//            mLoaderTimer.cancel();
//            mLoaderTimer.start();
//
            mTvFetching.setText("Fetching Catalogs..");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
//
            isLoading = true;

//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getSkycablePpvCatalogs(getSkycablePpvCatalogsSession);

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }

    }

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
//                    getSkycablePpvCatalogs(getSkycablePpvCatalogsSession);
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

    private void getSkycablePpvCatalogs(Callback<GetSkycablePPVCatalogsResponse> getSkycablePPVCatalogsCallback) {
        Call<GetSkycablePPVCatalogsResponse> getskycableppvcatalogs = RetrofitBuild.getSkycablePpvCatalogsService(getViewContext())
                .getSkycablePpvCatalogsCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        String.valueOf(limit));
        getskycableppvcatalogs.enqueue(getSkycablePPVCatalogsCallback);
    }

    private final Callback<GetSkycablePPVCatalogsResponse> getSkycablePpvCatalogsSession = new Callback<GetSkycablePPVCatalogsResponse>() {

        @Override
        public void onResponse(Call<GetSkycablePPVCatalogsResponse> call, Response<GetSkycablePPVCatalogsResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    if (mdb != null) {

                        isloadmore = response.body().getSkycablePpvCatalogs().size() > 0;
                        isLoading = false;
                        isfirstload = false;

                        if (!isbottomscroll) {
                            mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PPV_CATALOGS);
                        }

                        List<SkycablePPV> skycablePPVList = new ArrayList<>();
                        skycablePPVList = response.body().getSkycablePpvCatalogs();
                        for (SkycablePPV skycablePPV : skycablePPVList) {
                            mdb.insertSkycablePpvCatalogs(mdb, skycablePPV);
                        }

                        updateList(mdb.getSkycablePpvCatalogs(mdb));


                        List<SkycablePPVHistory> skycablePPVHistory = response.body().getSkycablePPVHistory();
                        if(skycablePPVHistory.size() > 0){
                            linearActionRequiredLayout.setVisibility(View.VISIBLE);
                            mSubscriptionAdapter.update(skycablePPVHistory);
                            recyclerViewSubscriptions.setVisibility(View.VISIBLE);
                        } else {
                            linearActionRequiredLayout.setVisibility(View.GONE);
                            mSubscriptionAdapter.clear();
                            recyclerViewSubscriptions.setVisibility(View.GONE);
                        }

                    }


                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetSkycablePPVCatalogsResponse> call, Throwable t) {
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

    private void updateList(List<SkycablePPV> data) {
        showNoInternetConnection(false);
        if (data.size() > 0) {
            txvViewHistory.setVisibility(View.VISIBLE);
            skycablePpv.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            mAdapter.update(data);
        } else {
            txvViewHistory.setVisibility(View.GONE);
            skycablePpv.setVisibility(View.GONE);
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
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ((SkyCableActivity) getViewContext()).displayView(1, null);
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

            if(mSubscriptionAdapter != null){
                linearActionRequiredLayout.setVisibility(View.GONE);
                mSubscriptionAdapter.clear();
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
            case R.id.txvViewHistory: {
                ((SkyCableActivity) getViewContext()).displayView(8, null);
                break;
            }
        }
    }
}
