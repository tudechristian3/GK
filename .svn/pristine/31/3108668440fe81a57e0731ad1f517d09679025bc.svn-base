package com.goodkredit.myapplication.fragments.mdp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.mdp.MDPSupportNewCaseAdapter;
import com.goodkredit.myapplication.adapter.mdp.MDPSupportThreadAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.mdp.MDPSupportHelpTopics;
import com.goodkredit.myapplication.model.mdp.MDPSupportThread;
import com.goodkredit.myapplication.responses.mdp.GetMDPSupportThreadResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MDPSupportFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private LinearLayout slideView;
    private Button supportFab;
    private ImageView xbutton;
    private boolean isTopicOpen = false;

    private RecyclerView recyclerView;
    private MDPSupportThreadAdapter mAdapterThread;
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

    private List<MDPSupportHelpTopics> mdpSupportHelpTopics;

    private RecyclerView helpTopicList;
    private MDPSupportNewCaseAdapter mAdapterHelpTopic;

    private String schoolid;
    private String servicecode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mdp_support, container, false);

        try{
            init(view);
            initData();
        } catch (Exception e){
            e.printStackTrace();
        }
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
        mAdapterThread = new MDPSupportThreadAdapter(getViewContext());
        recyclerView.setAdapter(mAdapterThread);

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
        mAdapterHelpTopic = new MDPSupportNewCaseAdapter(getViewContext());
        helpTopicList.setAdapter(mAdapterHelpTopic);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        year = Calendar.getInstance().get(Calendar.YEAR);
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        mdpSupportHelpTopics = new ArrayList<>();
        schoolid = ".";
        servicecode = "MDP";

        getSession();
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//
//            mTvFetching.setText("Fetching MDP Support Threads.");
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
////                    getSkycableSupportThread(getSkycableSupportThreadSession);
//
//                    getMDPSupportThread(getMDPSupportThreadSession);
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
            mTvFetching.setText("Fetching MDP Support Threads.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getMDPSupportThread(getMDPSupportThreadSession);

        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }
    }

    private void getMDPSupportThread (Callback<GetMDPSupportThreadResponse> getMDPSupportThreadCallback){
        Call<GetMDPSupportThreadResponse> getmdpsupportthread = RetrofitBuild.getMDPAPIService(getViewContext())
                .getMDPSupportThreadCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        String.valueOf(year),
                        schoolid,
                        servicecode);
        getmdpsupportthread.enqueue(getMDPSupportThreadCallback);
    }

    private final Callback<GetMDPSupportThreadResponse> getMDPSupportThreadSession = new Callback<GetMDPSupportThreadResponse>() {
        @Override
        public void onResponse(Call<GetMDPSupportThreadResponse> call, Response<GetMDPSupportThreadResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    updateThreadList(response.body().getMDPSupportThreadList());
                    updateSupportHelpList(response.body().getMDPSupportHelpTopicsList());
                } else{
                    showError(response.body().getMessage());
                }
            } else{
                showError();
            }
        }

        @Override
        public void onFailure(Call<GetMDPSupportThreadResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };


    private void updateThreadList(List<MDPSupportThread> threaddata) {
        if (threaddata.size() > 0) {
            mAdapterThread.update(threaddata);
            emptyLayout.setVisibility(View.GONE);
        } else {
            mAdapterThread.clear();
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    private void updateSupportHelpList(List<MDPSupportHelpTopics> helptopicsdata) {
        if (helptopicsdata.size() > 0) {
            mAdapterHelpTopic.update(helptopicsdata);
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
            if (mAdapterThread != null) {

                year = Calendar.getInstance().get(Calendar.YEAR);
                mSwipeRefreshLayout.setRefreshing(true);
                mAdapterThread.reset();
                mAdapterThread.clear();

                getSession();
            }
        }
    }
}
