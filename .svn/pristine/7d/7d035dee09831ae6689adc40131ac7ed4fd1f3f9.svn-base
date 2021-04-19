package com.goodkredit.myapplication.fragments.mdp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.mdp.MDPSupportActivity;
import com.goodkredit.myapplication.adapter.mdp.MDPSupportFAQAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.mdp.MDPSupportFAQ;
import com.goodkredit.myapplication.responses.mdp.GetMDPSupportFAQResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MDPSupportFAQFragment extends BaseFragment implements View.OnClickListener, ExpandableListView.OnGroupExpandListener {

    //    private SkycableSupportHelpTopics skycableSupportHelpTopics;
    private DatabaseHandler mdb;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String helptopic;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

//    private SwipeRefreshLayout mSwipeRefreshLayout;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private TextView txvHelpTopic;

    private ArrayList<String> expandableListTitle;
    private HashMap<String, String> expandableListDetail;

    private MDPSupportFAQAdapter mAdapter;
    private ExpandableListView listview;

    private Button btnMessageUs;

    private String schoolid;
    private String servicecode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_support_new_case, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        //swipe refresh
//        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setEnabled(true);

        //empty layout
        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        txvHelpTopic = (TextView) view.findViewById(R.id.txvHelpTopic);

        listview = (ExpandableListView) view.findViewById(R.id.faq_list);
        listview.setOnGroupExpandListener(this);
        listview.setChildIndicator(null);
        listview.setGroupIndicator(null);

        btnMessageUs = (Button) view.findViewById(R.id.btnMessageUs);
        btnMessageUs.setOnClickListener(this);

    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

//        skycableSupportHelpTopics = getArguments().getParcelable("skycableSupportHelpTopics");
//        helptopic = skycableSupportHelpTopics.getHelpTopic();

        helptopic = getArguments().getString("HELPTOPIC");
        schoolid = ".";
        servicecode = "MDP";

        expandableListTitle = new ArrayList<>();
        expandableListDetail = new HashMap<>();

        txvHelpTopic.setText(helptopic);

        getSession();
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//
//            mTvFetching.setText("Fetching MDP FAQ's.");
//            mTvWait.setText(" Please wait...");
//            mLlLoader.setVisibility(View.VISIBLE);
//
//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);
//        } else {
//            mLlLoader.setVisibility(View.GONE);
////            mSwipeRefreshLayout.setRefreshing(false);
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
////                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
////                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
////                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                    getMDPSupportFAQ(getMDPSupportFAQSession);
//                }
//            } else {
//                hideProgressDialog();
//                mLlLoader.setVisibility(View.GONE);
////                mSwipeRefreshLayout.setRefreshing(false);
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
            mTvFetching.setText("Fetching MDP FAQ's.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getMDPSupportFAQ(getMDPSupportFAQSession);

        } else {
            mLlLoader.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }
    }

    private void getMDPSupportFAQ (Callback<GetMDPSupportFAQResponse> getMDPSupportFAQCallback){
        Call<GetMDPSupportFAQResponse> getmdpsupportfaq = RetrofitBuild.getMDPAPIService(getViewContext())
                .getMDPSupportFAQCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        helptopic,
                        schoolid,
                        servicecode);

        getmdpsupportfaq.enqueue(getMDPSupportFAQCallback);
    }

    private final Callback<GetMDPSupportFAQResponse> getMDPSupportFAQSession = new Callback<GetMDPSupportFAQResponse>() {
        @Override
        public void onResponse(Call<GetMDPSupportFAQResponse> call, Response<GetMDPSupportFAQResponse> response) {
            mLlLoader.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null) {
                if(response.body().getStatus().equals("000")){
                    List<MDPSupportFAQ> mdpsupportfaqlist = new ArrayList<>();
                    mdpsupportfaqlist = response.body().getMDPSupportFAQList();

                    expandableListTitle.clear();
                    expandableListDetail.clear();

                    for(MDPSupportFAQ mdpsupportfaq : mdpsupportfaqlist){
                        expandableListTitle.add(mdpsupportfaq.getQuestion());
                        expandableListDetail.put(mdpsupportfaq.getQuestion(), mdpsupportfaq.getAnswer());
                    }

                    mAdapter = new MDPSupportFAQAdapter(getViewContext(), expandableListTitle, expandableListDetail);
                    listview.setAdapter(mAdapter);
                }else {
                    showError(response.body().getMessage());
                }
            }else {
                showError();
            }
        }

        @Override
        public void onFailure(Call<GetMDPSupportFAQResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setRefreshing(false);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMessageUs: {
                Bundle args = new Bundle();
                args.putString("HELPTOPIC", helptopic);
                args.putString("STATUS", "OPEN");
                args.putString("THREADID", ".");
                args.putString("SUBJECT", "-");
//                ((SkyCableActivity) getViewContext()).displayView(15, args);
                ((MDPSupportActivity) getViewContext()).displayView(3, args);
                break;
            }
        }
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        try {
            listview.getChildAt(groupPosition).setBackgroundResource(R.color.bluegray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
