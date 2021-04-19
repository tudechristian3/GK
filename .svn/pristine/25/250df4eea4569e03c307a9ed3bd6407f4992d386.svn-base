package com.goodkredit.myapplication.fragments.voting;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.voting.VotesPaymentOptionActivity;

import com.goodkredit.myapplication.adapter.votes.VotesParticipantsAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.votes.VotesParticipantsDB;
import com.goodkredit.myapplication.decoration.DividerItemDecoration;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.votes.VotesParticipants;
import com.goodkredit.myapplication.model.votes.VotesPostEvent;
import com.goodkredit.myapplication.responses.votes.VotesParticipantsResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VotesParticipantsFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //MAIN CONTAINER
    private NestedScrollView home_body;
    private LinearLayout maincontainer;

    //VOTE EVENTS
    private VotesPostEvent votesPostObject;

    //VOTES PARTICIPANTS
    private String servicecode = "";
    private String merchantid = "";
    private String status = "";
    private String eventid = "";
    private String eventlogo = "";
    private String eventend = "";
    private String eventname = "";

    private LinearLayout rv_participants_container;
    private RecyclerView rv_participants;
    private VotesParticipantsAdapter votes_participantsadapter;
    private List<VotesParticipants> voteparticipantlist = new ArrayList<>();

    private CheckBox cbx_sortbyvotes;
    private TextView txv_vote_dateclose;
    private TextView txv_event_name;
    private ImageView mPicLogo;

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //NO RESULT
    private LinearLayout noresult;
    private TextView txv_noresult;

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    //DONATE
    private LinearLayout btn_donate_container;
    private LinearLayout btn_donate;
    private TextView txv_donate;
    private List<VotesParticipants> donatelist = new ArrayList<>();

    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //VOTES (PAYMENT OPTION)
    private EditText edtDonate;
    private String strdonate = "";


    //NEW VARIABLES
    private String authenticationid;
    private String index;
    private String keyEncryption;
    private String param;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_votes_participants, container, false);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
        try {
            init(view);

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_view_history).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    private void init(View view) {
        //MAIN CONTAINER
        home_body = (NestedScrollView) view.findViewById(R.id.home_body);
        maincontainer = (LinearLayout) view.findViewById(R.id.maincontainer);

        //PARTICIPANTS VOTES
        rv_participants_container = (LinearLayout) view.findViewById(R.id.rv_participants_container);
        rv_participants = (RecyclerView) view.findViewById(R.id.rv_participants);

        cbx_sortbyvotes = (CheckBox) view.findViewById(R.id.cbx_sortbyvotes);
        txv_vote_dateclose = (TextView) view.findViewById(R.id.txv_vote_dateclose);
        txv_event_name = (TextView) view.findViewById(R.id.txv_event_name);
        mPicLogo = (ImageView) view.findViewById(R.id.mPicLogo);

        //NO RESULT
        noresult = (LinearLayout) view.findViewById(R.id.noresult);
        txv_noresult = (TextView) view.findViewById(R.id.txv_noresult);

        //DONATE
        btn_donate_container = (LinearLayout) view.findViewById(R.id.btn_donate_container);
        btn_donate = (LinearLayout) view.findViewById(R.id.btn_donate);
        btn_donate.setOnClickListener(this);
        txv_donate = (TextView) view.findViewById(R.id.txv_donate);


        //initialize refresh
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshdisabled = (ImageView) view.findViewById(R.id.refreshdisabled);
        refreshdisabled1 = (ImageView) view.findViewById(R.id.refreshdisabled1);

        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

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

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");
        status = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantStatus");

        if (getArguments() != null) {
            votesPostObject = getArguments().getParcelable("VotesPostObject");

            eventid = votesPostObject.getEventID();
            eventlogo = votesPostObject.getEventPictureURL();
            eventend = votesPostObject.getEventDateTimeEnd();
            eventname = votesPostObject.getEventName();
        }

        rv_participants.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_participants.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider_darkgrey)));
        rv_participants.setNestedScrollingEnabled(false);

        votes_participantsadapter = new VotesParticipantsAdapter(getViewContext(), this);
        rv_participants.setAdapter(votes_participantsadapter);

        cbx_sortbyvotes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    votes_participantsadapter.update(mdb.getVotesParticipantsByVotes(mdb));
                } else {
                    votes_participantsadapter.update(mdb.getVotesParticipants(mdb));
                }
            }
        });

        txv_event_name.setText(CommonFunctions.replaceEscapeData(eventname));

        //API CALL
        getSession();

        votesEventPost();
    }

    private void votesEventPost() {
        Glide.with(mContext)
                .load(eventlogo)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.generic_placeholder_gk_background)
                        .fitCenter()
                )
                .into(mPicLogo);

        txv_vote_dateclose.setText(CommonFunctions.getDateTimeFromDateTime(
                CommonFunctions.convertDate(eventend)));
    }

    private void showDonateToPayValue() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "",
                "Proceed", "", ButtonTypeEnum.SINGLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //NUMBER OF VOTES
        //TEXTVIEW
        LinearLayout txtNumberOfVotesContainer = dialog.getTextViewMessageContainer();
        txtNumberOfVotesContainer.setPadding(35, 20, 35, 0);

        List<String> numberOfVotesList = new ArrayList<>();
        numberOfVotesList.add("Amount to Pay: ");

        dialog.setContentCustomMultiTextView(numberOfVotesList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_878787, 16, Gravity.TOP | Gravity.START));

        //EDITTEXT
        LinearLayout editTextMainContainer = dialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(35, 0, 35, 20);

        List<String> editTextDataType = new ArrayList<>();
        editTextDataType.add(String.valueOf(GlobalDialogsEditText.CUSTOMNUMBER));

        LinearLayout editTextContainer = dialog.setContentCustomMultiEditText(editTextDataType,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.START,
                        R.drawable.border, 19, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;
                editItem.setPadding(20, 20, 20, 20);
                String taggroup = editItem.getTag().toString();
                if (taggroup.equals("TAG 0")) {
                    edtDonate = editItem;
                    edtDonate.addTextChangedListener(donateTextWatcher);
                }
            }
        }

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                hideProgressDialog();

            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkamount = strdonate;
                if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                    showToast("Please input a value.", GlobalToastEnum.WARNING);
                } else {
                    dialog.dismiss();
                    if (donatelist.size() > 0) {
                        VotesParticipants participants = null;
                        for (VotesParticipants votesParticipants : donatelist) {
                            participants = votesParticipants;
                        }

                        Intent intent = new Intent(getViewContext(), VotesPaymentOptionActivity.class);
                        intent.putExtra(VotesParticipants.KEY_VOTESPARTICIPANTS, participants);
                        intent.putExtra("VotesPostObject", votesPostObject);
                        intent.putExtra("VoteAmount", String.valueOf(strdonate));
                        getViewContext().startActivity(intent);

                    } else {
                        showErrorToast("Something went wrong. Please try again.");
                    }
                }
            }
        });
    }


    //---------------API---------------
//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            mTvFetching.setText("Fetching Participants. ");
//            mTvWait.setText("Please wait...");
//            mLlLoader.setVisibility(View.VISIBLE);
//            createSession(getSessionCallback);
//        } else {
//            hideProgressDialog();
//            showToast("Seems you are not connected to the internet. Please check your connection and try again. Thank you.", GlobalToastEnum.WARNING);
//            swipeRefreshLayout.setRefreshing(false);
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
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
//                    getParticipants(getParticipantsCallBack);
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
            mTvFetching.setText("Fetching Participants. ");
            mTvWait.setText("Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getParticipants(getParticipantsCallBack);
            getPostEventParticipantsV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
            swipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
        }
    }

    private void getParticipants(Callback<VotesParticipantsResponse> getParticipantsCallBack) {
        Call<VotesParticipantsResponse> getparticipants = RetrofitBuild.getVotesAPIService(getViewContext())
                .getPostEventParticipants(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        eventid
                );

        getparticipants.enqueue(getParticipantsCallBack);
    }

    private final Callback<VotesParticipantsResponse> getParticipantsCallBack =
            new Callback<VotesParticipantsResponse>() {

                @Override
                public void onResponse(Call<VotesParticipantsResponse> call, Response<VotesParticipantsResponse> response) {
                    try {

                        mLlLoader.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);

                        hideProgressDialog();
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                mdb.truncateTable(mdb, VotesParticipantsDB.TABLE_PARTICIPANTS);

                                voteparticipantlist = response.body().getVotesParticipantsList();

                                for (VotesParticipants votesParticipants : voteparticipantlist) {
                                    mdb.insertVotesParticipants(mdb, votesParticipants);
                                }

                                if (mdb.getVotesParticipants(mdb).size() > 0) {
                                    maincontainer.setVisibility(View.VISIBLE);
                                    rv_participants_container.setVisibility(View.VISIBLE);
                                    noresult.setVisibility(View.GONE);

                                    voteparticipantlist = mdb.getVotesParticipants(mdb);
                                    votes_participantsadapter.update(voteparticipantlist);
                                } else {
                                    maincontainer.setVisibility(View.GONE);
                                    rv_participants_container.setVisibility(View.GONE);
                                    noresult.setVisibility(View.VISIBLE);
                                    txv_noresult.setText("NO PARTICIPANTS POSTED FOR THIS EVENT YET.");
                                }

                                if (mdb.getDonateParticipant(mdb).size() > 0) {
                                    btn_donate_container.setVisibility(View.VISIBLE);
                                    btn_donate.setVisibility(View.VISIBLE);
                                    donatelist = mdb.getDonateParticipant(mdb);
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
                public void onFailure(Call<VotesParticipantsResponse> call, Throwable t) {
                    mLlLoader.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    showErrorToast();
                    hideProgressDialog();
                }
            };

    //--------------------ON START AND OVERRIDE-----------------------------
    private TextWatcher donateTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            strdonate = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_donate: {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                //showDonateToPayValue();
                if (donatelist.size() > 0) {
                    VotesParticipants participants = null;
                    for (VotesParticipants votesParticipants : donatelist) {
                        participants = votesParticipants;
                    }

                    Intent intent = new Intent(getViewContext(), VotesPaymentOptionActivity.class);
                    intent.putExtra(VotesParticipants.KEY_VOTESPARTICIPANTS, participants);
                    intent.putExtra("VotesPostObject", votesPostObject);
                    getViewContext().startActivity(intent);

                } else {
                    showErrorToast("Something went wrong. Please try again.");
                }
                break;
            }

			case R.id.refresh:
            case R.id.refreshnointernet: {
                disableRefresh();
                getSession();
                break;
            }
        }
    }


    @Override
    public void onRefresh() {
        try {
            if(mdb != null){
                mdb.truncateTable(mdb, VotesParticipantsDB.TABLE_PARTICIPANTS);
                if(votes_participantsadapter != null){
                    votes_participantsadapter.clear();
                }

                swipeRefreshLayout.setRefreshing(true);
                getSession();
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    //disable refresh button in empty screen
    private void disableRefresh() {
        try {
            refreshdisabled.setVisibility(View.VISIBLE);
            refreshdisabled1.setVisibility(View.VISIBLE);
            refreshnointernet.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }

    }

    //enable refresh button in empty screen
    private void enableRefresh() {
        try {
            refreshdisabled.setVisibility(View.GONE);
            refreshdisabled1.setVisibility(View.GONE);
            refreshnointernet.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
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

    //hide no internet connection page
    private void hideNoInternetConnection() {
        try {
            nointernetconnection.setVisibility(View.GONE);
            enableRefresh();
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }

    }

    @Override
    public void onResume() {
        Boolean votespaymentrequest = PreferenceUtils.getBooleanPreference(getViewContext(), PreferenceUtils.KEY_VOTES_FROM);
        if (votespaymentrequest) {
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_VOTES_FROM);
            onRefresh();
        }
        super.onResume();
    }

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    private void getPostEventParticipantsV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("eventid",eventid);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getPostEventParticipantsV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        getPostEventParticipantsV2Object(getPostEventParticipantsV2Callback);


    }
    private void getPostEventParticipantsV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getVotesV2API(getViewContext())
                .getPostEventParticipantsV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  getPostEventParticipantsV2Callback = new Callback<GenericResponse>() {
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

                    mdb.truncateTable(mdb, VotesParticipantsDB.TABLE_PARTICIPANTS);

                    voteparticipantlist = new Gson().fromJson(data,new TypeToken<List<VotesParticipants>>(){}.getType());
                    assert voteparticipantlist != null;

                   if(voteparticipantlist.size() > 0 && voteparticipantlist != null){
                       for (VotesParticipants votesParticipants : voteparticipantlist) {
                           mdb.insertVotesParticipants(mdb, votesParticipants);
                       }
                   }

                    if (mdb.getVotesParticipants(mdb).size() > 0) {
                        maincontainer.setVisibility(View.VISIBLE);
                        rv_participants_container.setVisibility(View.VISIBLE);
                        noresult.setVisibility(View.GONE);

                        voteparticipantlist = mdb.getVotesParticipants(mdb);
                        votes_participantsadapter.update(voteparticipantlist);
                    } else {
                        maincontainer.setVisibility(View.GONE);
                        rv_participants_container.setVisibility(View.GONE);
                        noresult.setVisibility(View.VISIBLE);
                        txv_noresult.setText("NO PARTICIPANTS POSTED FOR THIS EVENT YET.");
                    }

                    if (mdb.getDonateParticipant(mdb).size() > 0) {
                        btn_donate_container.setVisibility(View.VISIBLE);
                        btn_donate.setVisibility(View.VISIBLE);
                        donatelist = mdb.getDonateParticipant(mdb);
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
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            t.printStackTrace();
            hideProgressDialog();
        }
    };
}

