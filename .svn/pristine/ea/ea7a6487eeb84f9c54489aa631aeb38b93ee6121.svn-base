package com.goodkredit.myapplication.activities.voting;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.votes.VotesPendingAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.votes.VotesPending;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.votes.VotesPendingResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
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

public class VotesPendingActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private List<VotesPending> mGridData;
    private VotesPendingAdapter mAdapter;
    private RecyclerView recyclerView;

    private String imei;
    private String userid;
    private String borrowerid;
    private String sessionid;
    private String authcode;
    private String devicetype;
    private String limit;

    private RelativeLayout emptyLayout;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private TextView txvRequestNote;

    //NEW VARIABLES
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    //
    private ArrayList<VotesPending> votesPendingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_votes_pending);

            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    private void init() {
        setupToolbar();


        recyclerView = findViewById(R.id.rv_votes_pending);

        mAdapter = new VotesPendingAdapter(getViewContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getViewContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        //initialize refresh
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        emptyLayout = findViewById(R.id.emptyLayout);
        refresh = findViewById(R.id.refresh);
        refresh.setEnabled(false);
        nointernetconnection = findViewById(R.id.nointernetconnection);
        refreshnointernet = findViewById(R.id.refreshnointernet);
        refreshdisabled = findViewById(R.id.refreshdisabled);
        refreshdisabled1 = findViewById(R.id.refreshdisabled1);

        mLlLoader = findViewById(R.id.loaderLayout);
        mTvFetching = findViewById(R.id.fetching);
        mTvWait = findViewById(R.id.wait);

        txvRequestNote = findViewById(R.id.txvRequestNote);

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        refresh.setOnClickListener(this);
        refreshnointernet.setOnClickListener(this);
    }

    private void initData() {
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        limit = "0";
        devicetype = "ANDROID";

        getSession();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//            call.enqueue(callsession);
//        } else {
//            showError(getString(R.string.generic_internet_error_message));
//            swipeRefreshLayout.setRefreshing(false);
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//        }
//    }
//
//    private Callback<String> callsession = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            ResponseBody errBody = response.errorBody();
//
//            if (errBody == null) {
//                sessionid = response.body().toString();
//                authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                if (!sessionid.isEmpty()) {
//                    getVotesPending(getVotesPendingSession);
//                } else {
//                    swipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showErrorToast();
//                    hideProgressDialog();
//                }
//            } else {
//                swipeRefreshLayout.setRefreshing(false);
//                mLlLoader.setVisibility(View.GONE);
//                showNoInternetConnection(true);
//                showErrorToast();
//                hideProgressDialog();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            swipeRefreshLayout.setRefreshing(false);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//            hideProgressDialog();
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getVotesPending(getVotesPendingSession);
            showProgressDialog(getViewContext(),"","Fetching pending votes... Please wait...");
            getVotingTransactionPendingRequestV2();
        } else {
            refresh.setEnabled(true);
            swipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getVotesPending (Callback<VotesPendingResponse> getVotesPendingCallback){
        Call<VotesPendingResponse> getVotesPending = RetrofitBuild.getVotesAPIService(getViewContext())
                .getVotesPendingCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        devicetype,
                        limit);

        getVotesPending.enqueue(getVotesPendingCallback);
    }

    private final Callback<VotesPendingResponse> getVotesPendingSession = new Callback<VotesPendingResponse>() {
        @Override
        public void onResponse(Call<VotesPendingResponse> call, Response<VotesPendingResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                if(response.body().getStatus().equals("000")){

                    CacheManager.getInstance().saveVotesPending(response.body().getVotesPendingList());
                    mGridData = CacheManager.getInstance().getVotesPending();
                    mAdapter.updateList(CacheManager.getInstance().getVotesPending());

                    if(mGridData.size() == 0){
                        emptyLayout.setVisibility(View.VISIBLE);
                        txvRequestNote.setVisibility(View.GONE);
                    } else{
                        emptyLayout.setVisibility(View.GONE);
                        txvRequestNote.setVisibility(View.VISIBLE);
                    }

                } else{
                    showError(response.body().getMessage());
                }
            } else{
                showError(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<VotesPendingResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.refreshnointernet:
            case R.id.refresh: {
                onRefresh();
                break;
            }
        }

    }

    @Override
    public void onRefresh() {
        refresh.setEnabled(false);
        try {

            if(mAdapter != null){
                mAdapter.clear();
            }

            mAdapter.updateList(CacheManager.getInstance().getVotesPending());

            mTvFetching.setText("Fetching Pending Votes. ");
            mTvWait.setText("Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(true);
            getSession();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //show no internet connection page
    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
            mLlLoader.setVisibility(View.GONE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }



    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    private void getVotingTransactionPendingRequestV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("limit",limit);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getVotingTransactionPendingRequestV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        getVotingTransactionPendingRequestV2Object(getVotingTransactionPendingRequestV2Callback);


    }
    private void getVotingTransactionPendingRequestV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getVotesV2API(getViewContext())
                .getVotingTransactionPendingRequestV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  getVotingTransactionPendingRequestV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            refresh.setEnabled(true);
            hideProgressDialog();
            try {
                ResponseBody errorBody = response.errorBody();
                if(errorBody == null){
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if(response.body().getStatus().equals("000")){
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                        votesPendingList = new Gson().fromJson(data,new TypeToken<ArrayList<VotesPending>>(){}.getType());

                        if(votesPendingList.size() > 0 && votesPendingList != null){
                            CacheManager.getInstance().saveVotesPending(votesPendingList);
                            mGridData = CacheManager.getInstance().getVotesPending();
                            mAdapter.updateList(CacheManager.getInstance().getVotesPending());
                        }

                        if(mGridData.size() == 0){
                            emptyLayout.setVisibility(View.VISIBLE);
                            txvRequestNote.setVisibility(View.GONE);
                        } else{
                            emptyLayout.setVisibility(View.GONE);
                            txvRequestNote.setVisibility(View.VISIBLE);
                        }

                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else{
                        showErrorGlobalDialogs(message);
                    }

                }else{
                    showErrorGlobalDialogs();
                }
            }catch (Exception e){
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            refresh.setEnabled(true);
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorGlobalDialogs();
            t.printStackTrace();
        }
    };





}
