package com.goodkredit.myapplication.fragments.voting;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.voting.VotesPendingActivity;
import com.goodkredit.myapplication.adapter.votes.VotesPostEventAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.votes.VotesPostEventDB;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.votes.VotesPending;
import com.goodkredit.myapplication.model.votes.VotesPostEvent;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.votes.VotesGetPostEventResponse;
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

public class VotesPostEventFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //MAIN CONTAINER
    private NestedScrollView home_body;
    private LinearLayout maincontainer;

    //VOTES POSTING
    private String servicecode;
    private String merchantid;
    private String status;

    private LinearLayout rv_postvotes_container;
    private RecyclerView rv_postvotes;
    private VotesPostEventAdapter votes_postadapter;
    List<VotesPostEvent> votepostlist = new ArrayList<>();

    //SEARCH
    private LinearLayout edt_search_box_container;
    private EditText edt_search_box;
    private LinearLayout noresultsfound;
    private LinearLayout edt_search_icon_image_container;
    private ImageView edt_search_icon_image;
    private int searchiconselected = 0;

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //NO RESULT
    private LinearLayout noresult;
    private TextView txv_noresult;

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    //PENDING VOTES
    private LinearLayout btnViewPendingOrders;
    private TextView pendingOrderBadge;

    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private List<VotesPending> pendinglist;
    private List<VotesPending> votesPendingList;

    //NEW VARIABLES FOR SECURITY
    private String keyEncryption;
    private String authenticationid;
    private String param;
    private String index;

    private String postIindex;
    private String postAuth;
    private String postKey;
    private String postParam;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_postvotes_fragment, container, false);
        try {
            init(view);

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_view_history).setVisible(true);
    }

    private void init(View view) {
        //MAIN CONTAINER
        home_body = view.findViewById(R.id.home_body);
        maincontainer = view.findViewById(R.id.maincontainer);

        //SEARCH
        edt_search_box_container = view.findViewById(R.id.edt_search_box_container);
        edt_search_box = view.findViewById(R.id.edt_search_box);
        noresultsfound = view.findViewById(R.id.noresultsfound);
        edt_search_icon_image_container = view.findViewById(R.id.edt_search_icon_image_container);
        edt_search_icon_image_container.setOnClickListener(this);
        edt_search_icon_image = view.findViewById(R.id.edt_search_icon_image);

        //POST EVENT VOTES
        rv_postvotes_container = view.findViewById(R.id.rv_postvotes_container);
        rv_postvotes = view.findViewById(R.id.rv_postvotes);

        //NO RESULT
        noresult = view.findViewById(R.id.noresult);
        txv_noresult = view.findViewById(R.id.txv_noresult);

        btnViewPendingOrders = view.findViewById(R.id.btnViewPendingOrders);
        pendingOrderBadge = view.findViewById(R.id.pendingOrderBadge);

        btnViewPendingOrders.setOnClickListener(this);

        //initialize refresh
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet = view.findViewById(R.id.refreshnointernet);
        refreshdisabled = view.findViewById(R.id.refreshdisabled);
        refreshdisabled1 = view.findViewById(R.id.refreshdisabled1);

        mLlLoader = view.findViewById(R.id.loaderLayout);
        mTvFetching = view.findViewById(R.id.fetching);
        mTvWait = view.findViewById(R.id.wait);

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        refreshnointernet.setOnClickListener(this);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mdb = new DatabaseHandler(getViewContext());

        votesPendingList = new ArrayList<>();

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");
        status = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantStatus");

        rv_postvotes.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_postvotes.setNestedScrollingEnabled(false);

        votes_postadapter = new VotesPostEventAdapter(getViewContext());
        rv_postvotes.setAdapter(votes_postadapter);

        //API CALL
        getSession();
        getPendingSession();

        //SEARCH
        votesPostEventSearch();
    }

//    private void getPendingSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//            call.enqueue(callsession);
//        } else {
//            showError(getString(R.string.generic_internet_error_message));
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
//                    showErrorToast();
//                }
//            } else {
//                showErrorToast();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//        }
//    };

    private void getPendingSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            showProgressDialog("","Processing request... Please wait...");
            //getVotesPending(getVotesPendingSession);
            getVotingTransactionPendingRequestV2();
        } else {
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
                        "ANDROID",
                        "0");

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

                    //pending votes badge
                    pendinglist = CacheManager.getInstance().getVotesPending();
                    pendingOrderBadge.setText(String.valueOf(pendinglist.size()));
                    btnViewPendingOrders.setVisibility(View.VISIBLE);

                } else{
                    showError(response.body().getMessage());
                }
            } else{
                showError(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<VotesPendingResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    //SEARCH LISTENER
    private void votesPostEventSearch() {
        //CLEARS THE FOCUS ON SEARCH ON START
        edt_search_box.clearFocus();

        edt_search_box.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {

                    votes_postadapter.clear();
                    votepostlist.clear();

                    votes_postadapter.update(mdb.getVotesPostEventByKeyWord(mdb, s.toString().trim()));
                    votepostlist = mdb.getVotesPostEventByKeyWord(mdb, s.toString().trim());

                    edt_search_icon_image.setImageResource(R.drawable.ic_close_grey600_24dp);
                    searchiconselected = 1;

                    if (votepostlist.isEmpty()) {
                        noresultsfound.setVisibility(View.VISIBLE);
                        rv_postvotes_container.setVisibility(View.GONE);

                    } else {
                        noresultsfound.setVisibility(View.GONE);
                        rv_postvotes_container.setVisibility(View.VISIBLE);
                    }
                } else {
                    votes_postadapter.clear();
                    votepostlist.clear();
                    edt_search_icon_image.setImageResource(R.drawable.ic_search);
                    votes_postadapter.update(mdb.getVotesPostEventByKeyWord(mdb, ""));
                    searchiconselected = 0;
                    noresultsfound.setVisibility(View.GONE);
                    rv_postvotes_container.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //---------------API---------------
//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            mTvFetching.setText("Fetching Events. ");
//            mTvWait.setText("Please wait...");
//            mLlLoader.setVisibility(View.VISIBLE);
//            createSession(getSessionCallback);
//        } else {
//            swipeRefreshLayout.setRefreshing(false);
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showToast("Seems you are not connected to the internet. Please check your connection and try again. Thank you.", GlobalToastEnum.WARNING);
//        }
//    }
//
//    private Callback<String> getSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            ResponseBody errBody = response.errorBody();
//
//            if (errBody == null) {
//                sessionid = response.body().toString();
//                authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                if (!sessionid.isEmpty()) {
//                    getPostEventVotes(getPostEventVotesCallBack);
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
//            showErrorToast();
//            hideProgressDialog();
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mTvFetching.setText("Fetching Events. ");
            mTvWait.setText("Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getPostEventVotes(getPostEventVotesCallBack);
            getPostEventVotesV2();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    //GET POST EVENTS
    private void getPostEventVotes(Callback<VotesGetPostEventResponse> getPostEventVotesCallBack) {
        Call<VotesGetPostEventResponse> getpostevents = RetrofitBuild.getVotesAPIService(getViewContext())
                .getPostEventVotes(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        merchantid
                );

        getpostevents.enqueue(getPostEventVotesCallBack);
    }

    private final Callback<VotesGetPostEventResponse> getPostEventVotesCallBack =
            new Callback<VotesGetPostEventResponse>() {
                @Override
                public void onResponse(Call<VotesGetPostEventResponse> call, Response<VotesGetPostEventResponse> response) {
                    try {
                        hideProgressDialog();
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                mdb.truncateTable(mdb, VotesPostEventDB.TABLE_VOTESEVENTS);

                                votepostlist = response.body().getVotesPostEventList();

                                for (VotesPostEvent votespostevent : votepostlist) {
                                    mdb.insertVotesPostEvent(mdb, votespostevent);
                                }

                                if (mdb.getVotesPostEvent(mdb).size() > 0) {
                                    maincontainer.setVisibility(View.VISIBLE);
                                    rv_postvotes_container.setVisibility(View.VISIBLE);
                                    noresult.setVisibility(View.GONE);

                                    votes_postadapter.update(votepostlist);
                                } else {
                                    maincontainer.setVisibility(View.GONE);
                                    rv_postvotes_container.setVisibility(View.GONE);
                                    noresult.setVisibility(View.VISIBLE);
                                    txv_noresult.setText("NO EVENT POSTED YET");
                                }

                            } else {
                                showErrorToast(response.body().getMessage());
                            }
                        } else {
                            showErrorToast();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<VotesGetPostEventResponse> call, Throwable t) {
                    showErrorToast();
                    mLlLoader.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_search_icon_image_container: {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if (searchiconselected == 1) {
                    edt_search_icon_image.setImageResource(R.drawable.ic_search);
                    edt_search_box.setText("");
                    searchiconselected = 0;
                    hideKeyboard(getViewContext());
                }
                break;
            }
            case R.id.btnViewPendingOrders: {
                Intent intent = new Intent(getViewContext(), VotesPendingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.refreshnointernet: {
                onRefresh();
                break;
            }
        }
    }

    @Override
    public void onRefresh() {

        try {
            if(mdb != null){
                mdb.truncateTable(mdb, VotesPostEventDB.TABLE_VOTESEVENTS);
                if(votes_postadapter != null){
                    votes_postadapter.clear();
                }

                swipeRefreshLayout.setRefreshing(true);
                getSession();
            }

            btnViewPendingOrders.setVisibility(View.GONE);
            getPendingSession();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //show no internet connection page
    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
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
        parameters.put("limit","0");
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
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
            hideProgressDialog();
            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    votesPendingList = new Gson().fromJson(data,new TypeToken<List<VotesPending>>(){}.getType());
                    assert  votesPendingList != null;

                    CacheManager.getInstance().saveVotesPending(votesPendingList);

                    //pending votes badge
                    pendinglist = CacheManager.getInstance().getVotesPending();
                    pendingOrderBadge.setText(String.valueOf(pendinglist.size()));
                    btnViewPendingOrders.setVisibility(View.VISIBLE);

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorGlobalDialogs();
            t.printStackTrace();
        }
    };


    private void getPostEventVotesV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("merchantid",merchantid);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        postIindex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", postIindex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        postAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        postKey = CommonFunctions.getSha1Hex(postAuth + sessionid + "getPostEventVotesV2");
        postParam = CommonFunctions.encryptAES256CBC(postKey, String.valueOf(paramJson));

        getPostEventVotesV2Object(getPostEventVotesV2Callback);


    }
    private void getPostEventVotesV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getVotesV2API(getViewContext())
                .getPostEventVotesV2(postAuth,sessionid,postParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  getPostEventVotesV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(postKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String data = CommonFunctions.decryptAES256CBC(postKey,response.body().getData());
                    mdb.truncateTable(mdb, VotesPostEventDB.TABLE_VOTESEVENTS);

                    votepostlist = new Gson().fromJson(data,new TypeToken<List<VotesPostEvent>>(){}.getType());

                    if(votepostlist.size() >0 && votepostlist !=null){
                        for (VotesPostEvent votespostevent : votepostlist) {
                            mdb.insertVotesPostEvent(mdb, votespostevent);
                        }

                    }

                    if (mdb.getVotesPostEvent(mdb).size() > 0) {
                        maincontainer.setVisibility(View.VISIBLE);
                        rv_postvotes_container.setVisibility(View.VISIBLE);
                        noresult.setVisibility(View.GONE);
                        votes_postadapter.update(votepostlist);
                    } else {
                        maincontainer.setVisibility(View.GONE);
                        rv_postvotes_container.setVisibility(View.GONE);
                        noresult.setVisibility(View.VISIBLE);
                        txv_noresult.setText("NO EVENT POSTED YET");
                    }

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }

        }
        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
            t.printStackTrace();
        }
    };



}
