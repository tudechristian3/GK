package com.goodkredit.myapplication.fragments.coopassistant.support;

import android.os.Bundle;
import androidx.annotation.NonNull;
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
import com.goodkredit.myapplication.adapter.coopassistant.support.CoopAssistantSupportHelpTopicsAdapter;
import com.goodkredit.myapplication.adapter.coopassistant.support.CoopAssistantSupportThreadAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.SupportHelpTopics;
import com.goodkredit.myapplication.model.SupportThread;
import com.goodkredit.myapplication.responses.support.GetSupportThreadResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantSupportFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private LinearLayout slideView;
    private Button supportFab;
    private ImageView xbutton;
    private TextView no_help_topic;
    private boolean isTopicOpen = false;

    private RecyclerView recyclerView;
    private CoopAssistantSupportThreadAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String servicecode = "";

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

    private List<SupportHelpTopics> supportHelpTopicsList;

    private RecyclerView helpTopicList;
    private CoopAssistantSupportHelpTopicsAdapter mAdapterHelpTopic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmini_support, container, false);

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
        no_help_topic = (TextView) view.findViewById(R.id.no_help_topic);
        slideView = (LinearLayout) view.findViewById(R.id.slideView);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null, false, true));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new CoopAssistantSupportThreadAdapter(getViewContext());
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
        mAdapterHelpTopic = new CoopAssistantSupportHelpTopicsAdapter(getViewContext());
        helpTopicList.setAdapter(mAdapterHelpTopic);

    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        year = Calendar.getInstance().get(Calendar.YEAR);

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        supportHelpTopicsList = new ArrayList<>();

        callApi();
    }

    private void callApi() {

        showNoInternetConnection(false);

        mTvFetching.setText("Fetching Support Threads.");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getUniversalSupportThread(getUniversalSupportThreadCallBack);

        } else {

            refresh.setEnabled(true);
            refreshnointernet.setEnabled(true);

            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getUniversalSupportThread(Callback<GetSupportThreadResponse> getUniversalSupportThreadCallBack) {
        Call<GetSupportThreadResponse> getuniversalsupportthread = RetrofitBuild.getSupportAPIService(getViewContext())
                .getUniversalSupportThread(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        String.valueOf(year),
                        "coop",
                        servicecode
                );
        getuniversalsupportthread.enqueue(getUniversalSupportThreadCallBack);
    }

    private final Callback<GetSupportThreadResponse> getUniversalSupportThreadCallBack = new Callback<GetSupportThreadResponse>() {

        @Override
        public void onResponse(Call<GetSupportThreadResponse> call, Response<GetSupportThreadResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            refresh.setEnabled(true);
            refreshnointernet.setEnabled(true);
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    updateThreadList(response.body().getSupportThreadList());

                    List<SupportHelpTopics> supportHelpTopicsList = new ArrayList<>();
                    if(response.body().getHelpTopicsList().size() > 0){
                        for(SupportHelpTopics topics : response.body().getHelpTopicsList()){
                            if(topics.getExtra1().equals("ACTIVE")){
                                supportHelpTopicsList.add(topics);
                            }
                        }
                    }
                    updateSupportHelpList(supportHelpTopicsList);

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetSupportThreadResponse> call, Throwable t) {
            refresh.setEnabled(true);
            refreshnointernet.setEnabled(true);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void updateThreadList(List<SupportThread> supportThreadList) {
        if (supportThreadList.size() > 0) {
            mAdapter.update(supportThreadList);
            emptyLayout.setVisibility(View.GONE);
        } else {
            mAdapter.clear();
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    private void updateSupportHelpList(List<SupportHelpTopics> supportHelpTopicsList) {
        if (supportHelpTopicsList.size() > 0) {
            mAdapterHelpTopic.update(supportHelpTopicsList);
            no_help_topic.setVisibility(View.GONE);
            helpTopicList.setVisibility(View.VISIBLE);
        } else {
            mAdapterHelpTopic.clear();
            no_help_topic.setVisibility(View.VISIBLE);
            helpTopicList.setVisibility(View.GONE);
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
            case R.id.refresh:
                refresh.setEnabled(false);

                year = Calendar.getInstance().get(Calendar.YEAR);
                mSwipeRefreshLayout.setRefreshing(true);
                mAdapter.reset();
                mAdapter.clear();

                callApi();

                break;

            case R.id.refreshnointernet:
                refreshnointernet.setEnabled(false);

                year = Calendar.getInstance().get(Calendar.YEAR);
                mSwipeRefreshLayout.setRefreshing(true);
                mAdapter.reset();
                mAdapter.clear();

                callApi();
                break;
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

                callApi();
            }
        }
    }
}
