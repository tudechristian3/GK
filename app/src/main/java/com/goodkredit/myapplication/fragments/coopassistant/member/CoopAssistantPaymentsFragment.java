package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.coopassistant.member.CoopAssistantPaymentsAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.GenericBulletin;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantPayments;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.coopassistant.CoopAssistantPaymentsResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantPaymentsFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rv_payments;
    private CoopAssistantPaymentsAdapter madapter;
    private List<CoopAssistantPayments> mGridData = new ArrayList<>();

    private String sessionid;
    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;
    private String servicecode;
    private String month;
    private String year;
    private String limit;

    private RelativeLayout emptyvoucher;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private Button refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;
    private TextView textView;

    Calendar c = Calendar.getInstance();

    //VIEW ARCHIVE
    private LinearLayout lineararchivecontainer;
    private TextView txvarchive;
    private LinearLayout empty_archive_container;
    private TextView txv_empty_archive;

    private MaterialDialog mDialog;
    private ScrollView filterwrap;
    private LinearLayout optionwrap;
    private TextView editsearches;
    private TextView clearsearch;
    private Spinner monthspinType;
    private Spinner yearspinType;
    private TextView popfilter;
    private TextView popcancel;
    private boolean isyearselected = false;
    private boolean ismonthselected = false;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    public static CoopAssistantPaymentsFragment newInstance(String value) {
        CoopAssistantPaymentsFragment fragment = new CoopAssistantPaymentsFragment();
        Bundle b = new Bundle();
        b.putString("title", value);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_bills_and_payments, container, false);
        init(view);
        initData();

        return view;
    }

    private void init(View view) {

        rv_payments = view.findViewById(R.id.rv_coopassistant_soa_bills);

        madapter = new CoopAssistantPaymentsAdapter(getViewContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rv_payments.setNestedScrollingEnabled(false);
        rv_payments.setLayoutManager(layoutManager);
        rv_payments.setAdapter(madapter);

        //initialize refresh
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        emptyvoucher = view.findViewById(R.id.emptyvoucher);
        refresh = view.findViewById(R.id.refresh);
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet = view.findViewById(R.id.refreshnointernet);
        refreshdisabled = view.findViewById(R.id.refreshdisabled);
        refreshdisabled1 = view.findViewById(R.id.refreshdisabled1);

        mLlLoader = view.findViewById(R.id.loaderLayout);
        mTvFetching = view.findViewById(R.id.fetching);
        mTvWait = view.findViewById(R.id.wait);

        textView = view.findViewById(R.id.textView);

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

        sessionid = PreferenceUtils.getSessionID(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

        year = String.valueOf(c.get(Calendar.YEAR));
        month = String.valueOf(c.get(Calendar.MONTH) + 1);

        if(Integer.valueOf(month) < 10){
            month = "0" + month;
        }
        limit = "0";

        textView.setText("No payments yet.");

        //getSession();
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            mTvFetching.setText("Fetching Payments. ");
            mTvWait.setText("Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getCoopAssistantPayments(getCoopAssistantPaymentsSession);
            getCoopPaymentV2();
        } else{
            swipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getCoopAssistantPayments (Callback<CoopAssistantPaymentsResponse> getCoopAssistantPaymentsCallback ){
        Call<CoopAssistantPaymentsResponse> getcooppayments = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .getCoopAssistantPaymentsCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        servicecode,
                        month,
                        year,
                        limit,
                        CommonVariables.devicetype);
        getcooppayments.enqueue(getCoopAssistantPaymentsCallback);
    }

    private final Callback<CoopAssistantPaymentsResponse> getCoopAssistantPaymentsSession = new Callback<CoopAssistantPaymentsResponse>() {
        @Override
        public void onResponse(Call<CoopAssistantPaymentsResponse> call, Response<CoopAssistantPaymentsResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    try{
                        CacheManager.getInstance().saveCoopAssistantPayments(response.body().getCoopAssistantPaymentList());
                        mGridData = CacheManager.getInstance().getCoopAssistantPayments();
                        updateList(mGridData);
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                } else if(response.body().getStatus().equals("104")) {
                    showAutoLogoutDialog(response.body().getMessage());
                } else if (response.body().getStatus().equals("106")) {
                    showErrorGlobalDialogs(response.body().getMessage());

                    CacheManager.getInstance().saveCoopAssistantPayments(response.body().getCoopAssistantPaymentList());
                    mGridData = CacheManager.getInstance().getCoopAssistantPayments();
                    updateList(mGridData);
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }

            } else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<CoopAssistantPaymentsResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorGlobalDialogs();
        }
    };

    private void updateList(List<CoopAssistantPayments> data){
        if (data.size() > 0){
            emptyvoucher.setVisibility(View.GONE);
            madapter.updateList(data);
            madapter.notifyDataSetChanged();
        } else{
            emptyvoucher.setVisibility(View.VISIBLE);
            madapter.clear();
            madapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        getSession();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refreshnointernet: {
                onRefresh();
                break;
            }
            case R.id.refresh: {
                onRefresh();
                break;
            }
        }
    }
    //show no internet connection page
    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyvoucher.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
            mLlLoader.setVisibility(View.GONE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (isVisibleToUser) {//dosomething when the fragment is visible
                if (getView() != null) {
                    if(CacheManager.getInstance().getCoopAssistantPayments().size() > 0) {
                        mGridData = CacheManager.getInstance().getCoopAssistantPayments();
                        updateList(mGridData);
                    } else {
                        checkMemberStatus();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkMemberStatus() {
        List<CoopAssistantMembers> coopMembersList = PreferenceUtils.
                getCoopMembersListPreference(getViewContext(), CoopAssistantMembers.KEY_COOPMEMBERINFORMATION);

        boolean memberStatus = false;

        for(CoopAssistantMembers coopAssistantMembers : coopMembersList) {
            String status = coopAssistantMembers.getStatus();
            memberStatus = status.equals("ACTIVE");
        }

        if(memberStatus) {
            getSession();
        } else {
            mGridData = CacheManager.getInstance().getCoopAssistantPayments();
            updateList(mGridData);
        }
    }

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    private void getCoopPaymentV2(){

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("month",month);
        parameters.put("year",year);
        parameters.put("limit", limit);
        parameters.put("devicetype", CommonVariables.devicetype);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getCoopPaymentV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        getCoopPaymentV2Object(getCoopPaymentV2Callback);


    }

    private void getCoopPaymentV2Object(Callback<GenericResponse> genericResponseCallback){
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
            .getCoopPaymentV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  getCoopPaymentV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                if(response.body().getStatus().equals("000")){
                    try{
                        List<CoopAssistantPayments> copAssistantPaymentList =
                                new Gson().fromJson(data,new TypeToken<List<CoopAssistantPayments>>(){}.getType());
                        CacheManager.getInstance().saveCoopAssistantPayments(copAssistantPaymentList);
                        mGridData = CacheManager.getInstance().getCoopAssistantPayments();
                        updateList(mGridData);
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                } else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else if (response.body().getStatus().equals("106")) {
                    showErrorGlobalDialogs(message);
                    List<CoopAssistantPayments> copAssistantPaymentList =
                            new Gson().fromJson(data,new TypeToken<List<CoopAssistantPayments>>(){}.getType());
                    CacheManager.getInstance().saveCoopAssistantPayments(copAssistantPaymentList);
                    mGridData = CacheManager.getInstance().getCoopAssistantPayments();
                    updateList(mGridData);
                } else {
                    showErrorGlobalDialogs(message);
                }

            } else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorGlobalDialogs();
        }
    };


}
