package com.goodkredit.myapplication.fragments.coopassistant.support;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.adapter.coopassistant.support.CoopAssistantSupportFAQAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.SupportFAQ;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.responses.support.GetSupportFAQResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantFAQFragment extends BaseFragment implements View.OnClickListener, ExpandableListView.OnGroupExpandListener {

    private DatabaseHandler mdb;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String helptopic;

    private String servicecode = "";

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

    private TextView txvHelpTopic;

    private ArrayList<String> expandableListTitle;
    private HashMap<String, String> expandableListDetail;

    private CoopAssistantSupportFAQAdapter mAdapter;
    private ExpandableListView listview;

    private Button btnMessageUs;

    //NO RESULT
    private LinearLayout noresult;
    private TextView txv_noresult;

    //MEMBER LIST
    private List<CoopAssistantMembers> coopMembersList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmini_support_new_case, container, false);

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

        //NO RESULT
        noresult = (LinearLayout) view.findViewById(R.id.noresult);
        txv_noresult = (TextView) view.findViewById(R.id.txv_noresult);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

        coopMembersList = PreferenceUtils.getCoopMembersListPreference(mContext, CoopAssistantMembers.KEY_COOPMEMBERINFORMATION);

        helptopic = getArguments().getString("HELPTOPIC");

        expandableListTitle = new ArrayList<>();
        expandableListDetail = new HashMap<>();

        txvHelpTopic.setText(helptopic);

        getMemberInformation();

        callApi();
    }

    private void getMemberInformation() {
        if(!coopMembersList.isEmpty()){
            btnMessageUs.setVisibility(View.VISIBLE);
        } else {
            btnMessageUs.setVisibility(View.GONE);
        }
    }

    private void callApi() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mTvFetching.setText("Fetching FAQ's.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getUniversalSupportFAQ(getUniversalSupportFAQSession);

        } else {
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getUniversalSupportFAQ(Callback<GetSupportFAQResponse> getUniversalSupportFAQCallBack) {
        Call<GetSupportFAQResponse> getuniversalsupportfaq = RetrofitBuild.getSupportAPIService(getViewContext())
                .getUniversalSupportFAQ(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        helptopic,
                        "coop",
                        servicecode
                );
        getuniversalsupportfaq.enqueue(getUniversalSupportFAQCallBack);
    }

    private final Callback<GetSupportFAQResponse> getUniversalSupportFAQSession = new Callback<GetSupportFAQResponse>() {

        @Override
        public void onResponse(Call<GetSupportFAQResponse> call, Response<GetSupportFAQResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    List<SupportFAQ> supportFAQList = new ArrayList<>();
                    supportFAQList = response.body().getSupportFAQList();

                    expandableListTitle.clear();
                    expandableListDetail.clear();

                    if(supportFAQList.size() > 0) {
                        listview.setVisibility(View.VISIBLE);
                        noresult.setVisibility(View.GONE);

                        for (SupportFAQ supportFAQ : supportFAQList) {
                            expandableListTitle.add(supportFAQ.getQuestion());
                            expandableListDetail.put(supportFAQ.getQuestion(), supportFAQ.getAnswer());
                        }

                        mAdapter = new CoopAssistantSupportFAQAdapter(getViewContext(), expandableListTitle, expandableListDetail);
                        listview.setAdapter(mAdapter);
                    }
                    else {
                        listview.setVisibility(View.GONE);
                        noresult.setVisibility(View.VISIBLE);
                        txv_noresult.setText("NO FAQ POSTED YET.");
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetSupportFAQResponse> call, Throwable t) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                //((SchoolMiniActivity) getViewContext()).displayView(7, null);
                ((CoopAssistantHomeActivity) getViewContext()).displayView(6, null);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
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
                //((SchoolMiniActivity) getViewContext()).displayView(9, args);
                ((CoopAssistantHomeActivity) getViewContext()).displayView(8, args);
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
