
package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.app.MediaRouteButton;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.coopassistant.member.CoopAssistantSOABillsAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantBills;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.coopassistant.CoopAssistantSOABillsResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.Logger;
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

public class CoopAssistantBillsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView rv_bills;
    private CoopAssistantSOABillsAdapter madapter;
    private List<CoopAssistantBills> mGridData;

    private String sessionid;
    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;
    private String servicecode;
    private String month;
    private String year;
    private int limit = 0;

    private RelativeLayout emptyvoucher;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private Button refreshnointernet;
    private NestedScrollView coop_nested_scrollview;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;
    private TextView textView;

    private Calendar c = Calendar.getInstance();

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;
    private RelativeLayout emptyLayout;
    private boolean isbottomscroll = false;
    private boolean isloadmore =false;

    public static CoopAssistantBillsFragment newInstance(String value) {
        CoopAssistantBillsFragment fragment = new CoopAssistantBillsFragment();
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

    private void init(View view){
        rv_bills = view.findViewById(R.id.rv_coopassistant_soa_bills);
        coop_nested_scrollview = view.findViewById(R.id.coop_soa_nested);

        madapter = new CoopAssistantSOABillsAdapter(getViewContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rv_bills.setNestedScrollingEnabled(false);
        rv_bills.setLayoutManager(layoutManager);
        rv_bills.setAdapter(madapter);

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


        coop_nested_scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == ( v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() )) {
                    isbottomscroll = true;
                    if (isloadmore) {
                        limit += 30;
                        getSession();
                    }
                }
            }
        });

    }

    private void initData() {
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

        year = String.valueOf(c.get(Calendar.YEAR));
        month = String.valueOf(c.get(Calendar.MONTH) + 1);


        textView.setText("No bills yet.");

        getSession();
    }
    private void getSession() {

        mTvFetching.setText("Fetching Bills. ");
        mTvWait.setText("Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getCoopAssistantSOABills(getCoopAssistantSOABillsSession);
            getCOOPSOAV2();
        } else{
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showNoInternet();
        }
    }

    private void getCoopAssistantSOABills (Callback<CoopAssistantSOABillsResponse> getCoopAssistantSOABillsCallback ){
        Call<CoopAssistantSOABillsResponse> getcoopassistantsoabills = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .getCoopAssistantSOABillsCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        servicecode,
                        month,
                        year,
                        String.valueOf(limit),
                        CommonVariables.devicetype);
        getcoopassistantsoabills.enqueue(getCoopAssistantSOABillsCallback);
    }

    private final Callback<CoopAssistantSOABillsResponse> getCoopAssistantSOABillsSession = new Callback<CoopAssistantSOABillsResponse>() {
        @Override
        public void onResponse(Call<CoopAssistantSOABillsResponse> call, Response<CoopAssistantSOABillsResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    try{
                        CacheManager.getInstance().saveCoopAssistantSOABills(response.body().getCoopAssistantSOABillsList());
                        mGridData = CacheManager.getInstance().getCoopAssistantSOABills();
                        updateList(mGridData);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else if(response.body().getStatus().equals("104")) {
                    showAutoLogoutDialog(response.body().getMessage());
                } else if (response.body().getStatus().equals("106")) {
                    showErrorGlobalDialogs(response.body().getMessage());
                    CacheManager.getInstance().saveCoopAssistantSOABills(response.body().getCoopAssistantSOABillsList());
                    mGridData = CacheManager.getInstance().getCoopAssistantSOABills();
                    updateList(mGridData);
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }

            } else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<CoopAssistantSOABillsResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorGlobalDialogs();
        }
    };

    private void updateList(List<CoopAssistantBills> data){
        showHasData();
        madapter.updateList(data);
        madapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        isloadmore = false;
        isbottomscroll = false;
        limit = 0;
        getSession();
    }

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
                    if(CacheManager.getInstance().getCoopAssistantSOABills().size() > 0) {
                        mGridData = CacheManager.getInstance().getCoopAssistantSOABills();
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
            mGridData = CacheManager.getInstance().getCoopAssistantSOABills();
            updateList(mGridData);
        }
    }

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    private void getCOOPSOAV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("servicecode", servicecode);
            parameters.put("month", String.valueOf(month));
            parameters.put("year", String.valueOf(year));
            parameters.put("limit", String.valueOf(limit));
            parameters.put("devicetype", CommonVariables.devicetype);


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getCOOPSOAV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            if(isloadmore){
                getCOOPSOAV2Object(getCOOPSOAV2LoadMoreCallback);
            }else{
                getCOOPSOAV2Object(getCOOPSOAV2Callback);
            }

        }else{
            refresh.setEnabled(true);
            showNoInternet();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorToast();
        }
    }

    private void getCOOPSOAV2Object( Callback<GenericResponse> genericResponseCallback) {
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getContext())
                .getCOOPSOAV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }

    private final Callback<GenericResponse> getCOOPSOAV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<CoopAssistantBills> coopAssistantSOABillsList =
                            new Gson().fromJson(data,new TypeToken<List<CoopAssistantBills>>(){}.getType());

                    Logger.debug("okhttp","SOA SIZE : "+ coopAssistantSOABillsList.size());


                    if(coopAssistantSOABillsList.size() > 0){
                        isloadmore = true;
                        CacheManager.getInstance().clearCoopAssistantSOABills();
                        CacheManager.getInstance().saveCoopAssistantSOABills(coopAssistantSOABillsList);
                        mGridData = CacheManager.getInstance().getCoopAssistantSOABills();
                        updateList(mGridData);
                    }else{
                        showEmptyLayout();
                    }

                } else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else if (response.body().getStatus().equals("106")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<CoopAssistantBills> coopAssistantSOABillsList =
                            new Gson().fromJson(data,new TypeToken<List<CoopAssistantBills>>(){}.getType());

                    showErrorGlobalDialogs(message);
                    CacheManager.getInstance().saveCoopAssistantSOABills(coopAssistantSOABillsList);
                    mGridData = CacheManager.getInstance().getCoopAssistantSOABills();
                    updateList(mGridData);
                } else {
                    showEmptyLayout();
                    showErrorGlobalDialogs(message);
                }

            } else{
                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                showErrorToast();
                showNoInternet();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorToast();
            showNoInternet();
        }
    };


    private final Callback<GenericResponse> getCOOPSOAV2LoadMoreCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<CoopAssistantBills> coopAssistantSOABillsList =
                            new Gson().fromJson(data,new TypeToken<List<CoopAssistantBills>>(){}.getType());

                    Logger.debug("okhttp","SOA SIZE2 : "+ coopAssistantSOABillsList.size());

                    isloadmore = true;
                    CacheManager.getInstance().saveCoopAssistantSOABills(coopAssistantSOABillsList);
                    mGridData = CacheManager.getInstance().getCoopAssistantSOABills();
                    updateList(mGridData);

                    Logger.debug("okhttp","SOA SIZE3 : "+ mGridData.size());


                } else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else if (response.body().getStatus().equals("106")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<CoopAssistantBills> coopAssistantSOABillsList =
                            new Gson().fromJson(data,new TypeToken<List<CoopAssistantBills>>(){}.getType());

                    showErrorGlobalDialogs(message);
                    CacheManager.getInstance().saveCoopAssistantSOABills(coopAssistantSOABillsList);
                    mGridData = CacheManager.getInstance().getCoopAssistantSOABills();
                    updateList(mGridData);

                } else {
                    showEmptyLayout();
                    showErrorGlobalDialogs(message);
                }

            } else{
                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                showErrorToast();
                showNoInternet();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorToast();
            showNoInternet();
        }
    };


    private void showEmptyLayout() {
        emptyvoucher.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.GONE);
    }


    private void showEmpty() {
        emptyvoucher.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.GONE);
    }


    private void showNoInternet() {
        emptyvoucher.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.VISIBLE);

    }

    private void showHasData() {
        emptyvoucher.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        nointernetconnection.setVisibility(View.GONE);

    }



}
