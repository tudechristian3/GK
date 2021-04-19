package com.goodkredit.myapplication.activities.fairchild;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.fairchild.FairChildVotesAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.fairchild.FairChildMembers;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FairChildVoteActivity extends BaseActivity implements View.OnClickListener {

    //COMMON
    private DatabaseHandler mdb;
    private String sessionid;
    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;
    private String devicetype;

    private List<String> participantidlist = new ArrayList<>();

    private List<FairChildMembers> mDirectorsList = new ArrayList<>();
    private FairChildVotesAdapter mDirectorsAdapter;
    private RecyclerView rv_directors;

    private List<FairChildMembers> mAuditList = new ArrayList<>();
    private FairChildVotesAdapter mAuditAdapter;
    private RecyclerView rv_audit;

    private List<FairChildMembers> mElectionList = new ArrayList<>();
    private FairChildVotesAdapter mElectionAdapter;
    private RecyclerView rv_election;

    private TextView btn_fairchild_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fairchild_vote);

            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    //METHODS
    private void init() {
        setupToolbar();

        btn_fairchild_submit = (TextView) findViewById(R.id.btn_fairchild_submit);
        btn_fairchild_submit.setOnClickListener(this);

        rv_directors = (RecyclerView) findViewById(R.id.rv_fairchild_directors);
        mDirectorsAdapter = new FairChildVotesAdapter(mDirectorsList, getViewContext(), 4, "boardofdirectors");
        RecyclerView.LayoutManager layoutManager_directors = new LinearLayoutManager(getViewContext());
        rv_directors.setNestedScrollingEnabled(false);
        rv_directors.setLayoutManager(layoutManager_directors);
        rv_directors.setAdapter(mDirectorsAdapter);

        rv_audit = (RecyclerView) findViewById(R.id.rv_fairchild_audit);
        mAuditAdapter = new FairChildVotesAdapter(mAuditList, getViewContext(),2, "auditcommittee");
        RecyclerView.LayoutManager layoutManager_audit = new LinearLayoutManager(getViewContext());
        rv_audit.setNestedScrollingEnabled(false);
        rv_audit.setLayoutManager(layoutManager_audit);
        rv_audit.setAdapter(mAuditAdapter);

        rv_election = (RecyclerView) findViewById(R.id.rv_fairchild_election);
        mElectionAdapter = new FairChildVotesAdapter(mElectionList, getViewContext(), 2, "electioncommittee");
        RecyclerView.LayoutManager layoutManager_election = new LinearLayoutManager(getViewContext());
        rv_election.setNestedScrollingEnabled(false);
        rv_election.setLayoutManager(layoutManager_election);
        rv_election.setAdapter(mElectionAdapter);
    }

    private void initData() {
        //SETUP TOOLBAR TITLE
        setUpToolBarTitle();

        //COMMON
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        devicetype = "ANDROID";

        //DB
        mdb = new DatabaseHandler(getViewContext());

        updateFairChildListAdapter();
    }

    private void setUpToolBarTitle() {
        String title = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceName");

        if(!title.trim().equals("")) {
            setTitle(CommonFunctions.replaceEscapeData(title));
        } else {
            setTitle("FAIR CHILD");
        }
    }

    private void updateFairChildListAdapter() {
        mDirectorsList = mdb.getFairChildBoardOfDirectors(mdb);
        mAuditList = mdb.getFairChildAuditCom(mdb);
        mElectionList = mdb.getFairChildElectionCom(mdb);

        mDirectorsAdapter.clear();
        mAuditAdapter.clear();
        mElectionAdapter.clear();

        mDirectorsAdapter.updateList(mDirectorsList);
        mAuditAdapter.updateList(mAuditList);
        mElectionAdapter.updateList(mElectionList);
    }

    private void clearFairChildVoteListPreference() {
        PreferenceUtils.removePreference(getViewContext(), "fairchild_directors");
        PreferenceUtils.removePreference(getViewContext(), "fairchild_audit");
        PreferenceUtils.removePreference(getViewContext(), "fairchild_election");
    }

    //API
//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            showProgressDialog(getViewContext(), "Processing request", "Please wait...");
//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//            call.enqueue(callsession);
//        } else {
//            showWarningToast();
//        }
//    }
//
//    private final Callback<String> callsession = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    hideProgressDialog();
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    hideProgressDialog();
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("<!DOCTYPE html>")) {
//                    hideProgressDialog();
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = responseData;
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                    voteFairChildCandidates(voteFairChildCandidatesSession);
//                }
//            } else {
//                hideProgressDialog();
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Processing request", "Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            voteFairChildCandidates(voteFairChildCandidatesSession);
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void voteFairChildCandidates (Callback<GenericResponse> voteFairChildCandidatesCallback) {
        Call<GenericResponse> voteFairChildCandidates = RetrofitBuild.getFairChildAPIService(getViewContext())
                .voteFairChildCandidatesCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        devicetype,
                        participantidlist);

        voteFairChildCandidates.enqueue(voteFairChildCandidatesCallback);
    }

    private final Callback<GenericResponse> voteFairChildCandidatesSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            clearFairChildVoteListPreference();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    finish();
                    if(FairChildVoteInstructionsActivity.finishfairInstructionsActivity != null) {
                        FairChildVoteInstructionsActivity.finishfairInstructionsActivity.finish();
                    }

                    if(FairChildAttendanceActivity.finishfairChildAttendanceActivity != null) {
                        FairChildAttendanceActivity.finishfairChildAttendanceActivity.finish();
                    }

                    updateFairChildListAdapter();

                    Bundle args = new Bundle();
                    args.putString("FROM","JUSTVOTED");
                    FairChildDeadEndActivity.start(getViewContext(),  args);
                } else{
                    hideProgressDialog();
                    showErrorGlobalDialogs(response.body().getMessage());
                    updateFairChildListAdapter();
                }
            } else{
                hideProgressDialog();
                showErrorGlobalDialogs(response.body().getMessage());
                updateFairChildListAdapter();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            hideProgressDialog();
            showNoInternetToast();
            clearFairChildVoteListPreference();
            updateFairChildListAdapter();
        }
    };

    //OTHERS
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_fairchild_submit: {
                participantidlist.clear();

                List<String> list0 = PreferenceUtils.getStringListPreference(getViewContext(), "fairchild_directors");
                List<String> list1 = PreferenceUtils.getStringListPreference(getViewContext(), "fairchild_audit");
                List<String> list2 = PreferenceUtils.getStringListPreference(getViewContext(), "fairchild_election");

                if(list0 != null){
                    participantidlist.addAll(list0);
                }

                if(list1 != null){
                    participantidlist.addAll(list1);
                }

                if(list2 != null){
                    participantidlist.addAll(list2);
                }

                if(participantidlist == null || participantidlist.size() == 0){
                    showToast("Please select your chosen candidates.", GlobalToastEnum.WARNING);
                } else{
                    getSession();
                }

                break;
            }
        }
    }
}
