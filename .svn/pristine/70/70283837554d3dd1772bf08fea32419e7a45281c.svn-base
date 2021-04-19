package com.goodkredit.myapplication.fragments.skycable;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.goodkredit.myapplication.adapter.skycable.SkycableNewApplicationPlansAdapter;
import com.goodkredit.myapplication.adapter.skycable.SkycableNewApplicationRegistrationsAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.SkycableDictionary;
import com.goodkredit.myapplication.bean.SkycableRegistration;
import com.goodkredit.myapplication.bean.SkycableServiceArea;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.skycable.GetSkycableDictionariesResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkycableNewApplicationPlansFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView txvNewApp;
    private TextView txvRegistrations;

    //ACTION REQUIRED
    private LinearLayout linearActionRequiredLayout;
    private TextView txvActionRequiredWarning;

    private RecyclerView recyclerView;
    private SkycableNewApplicationRegistrationsAdapter mAdapter;
    private List<SkycableRegistration> skycableRegistrations;

    private DatabaseHandler mdb;
//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private List<SkycableDictionary> skycableDictionaries;
    private List<SkycableServiceArea> skycableServiceAreas;

    //PLANS
    private RecyclerView recyclerViewPlans;
    private SkycableNewApplicationPlansAdapter mPlansAdapter;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    private LinearLayout mainLayout;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    //UNIFIED SESSION
    private String sessionid = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_new_application_plans, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        txvNewApp = (TextView) view.findViewById(R.id.txvNewApp);
        txvNewApp.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "NEW APPLICATION"));
        txvRegistrations = (TextView) view.findViewById(R.id.txvRegistrations);
        txvRegistrations.setOnClickListener(this);
        linearActionRequiredLayout = (LinearLayout) view.findViewById(R.id.linearActionRequiredLayout);
        txvActionRequiredWarning = (TextView) view.findViewById(R.id.txvActionRequiredWarning);
        txvActionRequiredWarning.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "ACTION REQUIRED"));

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new SkycableNewApplicationRegistrationsAdapter(getViewContext(), true);
        recyclerView.setAdapter(mAdapter);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        //PLANS
        recyclerViewPlans = (RecyclerView) view.findViewById(R.id.recyclerViewPlans);
        recyclerViewPlans.setLayoutManager(new GridLayoutManager(getViewContext(), 2));
        recyclerViewPlans.setNestedScrollingEnabled(false);
        mPlansAdapter = new SkycableNewApplicationPlansAdapter(getViewContext(), SkycableNewApplicationPlansFragment.this);
        recyclerViewPlans.setAdapter(mPlansAdapter);

        //empty layout
        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        mainLayout = (LinearLayout) view.findViewById(R.id.mainLayout);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        skycableDictionaries = new ArrayList<>();
        skycableRegistrations = new ArrayList<>();
        skycableServiceAreas = new ArrayList<>();

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        getSession();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txvRegistrations: {
                ((SkyCableActivity) getViewContext()).displayView(10, null);
                break;
            }
            case R.id.refresh: {
                getSession();
                break;
            }
            case R.id.refreshnointernet: {
                getSession();
                break;
            }
        }
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {

            mTvFetching.setText("Fetching plans..");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getSkycableDictionaries(getSkycableDictionariesSession);
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
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    getSkycableDictionaries(getSkycableDictionariesSession);
//
//                }
//            } else {
//                mSwipeRefreshLayout.setRefreshing(false);
//                mLlLoader.setVisibility(View.GONE);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            mSwipeRefreshLayout.setRefreshing(false);
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSkycableDictionaries(Callback<GetSkycableDictionariesResponse> getSkycableDictionariesCallback) {
        Call<GetSkycableDictionariesResponse> getskycabledictionary = RetrofitBuild.getSkycableDictionariesService(getViewContext())
                .getSkycableDictionariesCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode);
        getskycabledictionary.enqueue(getSkycableDictionariesCallback);
    }

    private final Callback<GetSkycableDictionariesResponse> getSkycableDictionariesSession = new Callback<GetSkycableDictionariesResponse>() {

        @Override
        public void onResponse(Call<GetSkycableDictionariesResponse> call, Response<GetSkycableDictionariesResponse> response) {

            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    skycableDictionaries.clear();
                    skycableDictionaries = response.body().getSkycableDictionaries();

                    skycableServiceAreas.clear();
                    skycableServiceAreas = response.body().getSkycableServiceAreas();
                    mPlansAdapter.setServiceArea(skycableServiceAreas);

                    skycableRegistrations.clear();
                    skycableRegistrations = response.body().getSkycableRegistrations();

                    if (skycableDictionaries.size() > 0) {
                        mPlansAdapter.update(skycableDictionaries);
                        emptyLayout.setVisibility(View.GONE);
                        //mainLayout.setVisibility(View.VISIBLE);
                    } else {
                        mPlansAdapter.clear();
                        emptyLayout.setVisibility(View.VISIBLE);
                        //mainLayout.setVisibility(View.GONE);
                    }

                    if (skycableRegistrations.size() > 0) {
                        linearActionRequiredLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        if (mdb != null) {
                            mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_REGISTRATIONS_FORPAYMENT);
                            for (SkycableRegistration skycableRegistration : skycableRegistrations) {
                                mdb.insertSkycableRegistrationsForPayment(mdb, skycableRegistration);
                            }
                            mAdapter.update(mdb.getSkycableRegistrationsForPayment(mdb));
                        }
                    } else {
                        linearActionRequiredLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        mAdapter.clear();
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetSkycableDictionariesResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
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

    @Override
    public void onRefresh() {

        if (mdb != null) {
            mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_REGISTRATIONS_FORPAYMENT);
        }

        if (mPlansAdapter != null) {
            mPlansAdapter.clear();
        }

        if (mAdapter != null) {
            mAdapter.clear();
        }

        mSwipeRefreshLayout.setRefreshing(true);

        getSession();
    }
}
