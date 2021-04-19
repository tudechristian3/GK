package com.goodkredit.myapplication.fragments.projectcoop;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.projectcoop.ProjectCoopActivity;
import com.goodkredit.myapplication.adapter.projectcoop.ProjectCoopSupportNewCaseAdapter;
import com.goodkredit.myapplication.adapter.projectcoop.ProjectCoopSupportThreadAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.SkycableSupportHelpTopics;
import com.goodkredit.myapplication.bean.SkycableSupportThread;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.skycable.GetSkycableSupportThreadResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectCoopSupportFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private LinearLayout slideView;
    private Button supportFab;
    private ImageView xbutton;
    private boolean isTopicOpen = false;

    private RecyclerView recyclerView;
    private ProjectCoopSupportThreadAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private DatabaseHandler mdb;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private int year = 2018;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private List<SkycableSupportHelpTopics> skycableSupportHelpTopicsList;

    private RecyclerView helpTopicList;
    private ProjectCoopSupportNewCaseAdapter mAdapterHelpTopic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_support, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        //swipe refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        supportFab = (Button) view.findViewById(R.id.supportFab);
        supportFab.setOnClickListener(this);
        xbutton = (ImageView) view.findViewById(R.id.xbutton);
        xbutton.setOnClickListener(this);
        slideView = (LinearLayout) view.findViewById(R.id.slideView);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null, false, true));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new ProjectCoopSupportThreadAdapter(getViewContext());
        recyclerView.setAdapter(mAdapter);

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //empty layout
        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        helpTopicList = (RecyclerView) view.findViewById(R.id.helpTopicList);
        helpTopicList.setLayoutManager(new LinearLayoutManager(getViewContext()));
        helpTopicList.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null, false, true));
        mAdapterHelpTopic = new ProjectCoopSupportNewCaseAdapter(getViewContext());
        helpTopicList.setAdapter(mAdapterHelpTopic);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        year = Calendar.getInstance().get(Calendar.YEAR);
        skycableSupportHelpTopicsList = new ArrayList<>();

        getSession();

    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//
//            mTvFetching.setText("Fetching Support Threads.");
//            mTvWait.setText(" Please wait...");
//            mLlLoader.setVisibility(View.VISIBLE);
//
//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);
//        } else {
//            mLlLoader.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setRefreshing(false);
//            showNoInternetConnection(true);
//            showError(getString(R.string.generic_internet_error_message));
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
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                    getProjectCoopSupportThread(getProjectCoopSupportThreadSession);
//                }
//            } else {
//                hideProgressDialog();
//                mLlLoader.setVisibility(View.GONE);
//                mSwipeRefreshLayout.setRefreshing(false);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mTvFetching.setText("Fetching Support Threads.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getProjectCoopSupportThread(getProjectCoopSupportThreadSession);

        } else {
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showNoInternetToast();
        }

    }

    private void getProjectCoopSupportThread(Callback<GetSkycableSupportThreadResponse> getSkycableSupportThreadCallback) {
        Call<GetSkycableSupportThreadResponse> getskycablesupportthread = RetrofitBuild.getGameSupportThreadService(getViewContext())
                .getProjectCoopSupportThreadCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        String.valueOf(year));
        getskycablesupportthread.enqueue(getSkycableSupportThreadCallback);
    }

    private final Callback<GetSkycableSupportThreadResponse> getProjectCoopSupportThreadSession = new Callback<GetSkycableSupportThreadResponse>() {

        @Override
        public void onResponse(Call<GetSkycableSupportThreadResponse> call, Response<GetSkycableSupportThreadResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    updateThreadList(response.body().getSkycableSupportThreadList());

                    updateSupportHelpList(response.body().getSkycableSupportHelpTopicsList());

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetSkycableSupportThreadResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void updateThreadList(List<SkycableSupportThread> skycableSupportThreadList) {
        if (skycableSupportThreadList.size() > 0) {
            mAdapter.update(skycableSupportThreadList);
            emptyLayout.setVisibility(View.GONE);
        } else {
            mAdapter.clear();
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    private void updateSupportHelpList(List<SkycableSupportHelpTopics> skycableSupportHelpTopicsList) {
        if (skycableSupportHelpTopicsList.size() > 0) {
            mAdapterHelpTopic.update(skycableSupportHelpTopicsList);
        } else {
            mAdapterHelpTopic.clear();
        }
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            supportFab.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            supportFab.setVisibility(View.VISIBLE);
            nointernetconnection.setVisibility(View.GONE);
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
                ((ProjectCoopActivity) getViewContext()).displayView(1, null);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.supportFab: {
                slideView.bringToFront();
                slideView.setVisibility(View.VISIBLE);
                isTopicOpen = true;
                slideView.startAnimation(AnimationUtils.loadAnimation(getViewContext(),
                        R.anim.slide_up));
                break;
            }
            case R.id.xbutton: {
                isTopicOpen = false;
                slideView.startAnimation(AnimationUtils.loadAnimation(getViewContext(),
                        R.anim.slide_down));
                slideView.setVisibility(View.GONE);
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        if (mdb != null) {
            if (mAdapter != null) {

                year = Calendar.getInstance().get(Calendar.YEAR);
                mSwipeRefreshLayout.setRefreshing(true);
                mAdapter.reset();
                mAdapter.clear();

                getSession();
            }
        }
    }

}
