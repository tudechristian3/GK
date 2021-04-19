package com.goodkredit.myapplication.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.publicsponsor.PublicSponsorAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.publicsponsor.PublicSponsorDBHelper;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.publicsponsor.PublicSponsor;
import com.goodkredit.myapplication.responses.publicsponsor.GetPublicSponsorsResponse;
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

public class V2PublicSponsorsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private List<PublicSponsor> mGridData = new ArrayList<>();
    private PublicSponsorAdapter mAdapter;
    private RecyclerView rv_publicsponsor;

    private DatabaseHandler mdb;
    private String imei;
    private String authcode;
    private String userid;
    private String borrowerid;
    private String limit;
    private String devicetype;

    private RelativeLayout emptyvoucher;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private Button refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout mLlLoader;

    //UNIFIED SESSION
    private String sessionid;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    //Shimmering effect
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayout skeleton_public_sponsor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_notification_0).setVisible(false);
        menu.findItem(R.id.action_notification_1).setVisible(false);
        menu.findItem(R.id.action_notification_2).setVisible(false);
        menu.findItem(R.id.action_notification_3).setVisible(false);
        menu.findItem(R.id.action_notification_4).setVisible(false);
        menu.findItem(R.id.action_notification_5).setVisible(false);
        menu.findItem(R.id.action_notification_6).setVisible(false);
        menu.findItem(R.id.action_notification_7).setVisible(false);
        menu.findItem(R.id.action_notification_8).setVisible(false);
        menu.findItem(R.id.action_notification_9).setVisible(false);
        menu.findItem(R.id.action_notification_9plus).setVisible(false);
        menu.findItem(R.id.action_process_queue).setVisible(false);
        menu.findItem(R.id.sortitem).setVisible(false);
        menu.findItem(R.id.summary).setVisible(false);
        menu.findItem(R.id.group_voucher).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //inflate view
        View view = inflater.inflate(R.layout.fragment_public_sponsors, container, false);

        init(view);
        initData();

        return view;

    }

    private void init(View view) {
        rv_publicsponsor = view.findViewById(R.id.rv_publicsponsors);

        mAdapter = new PublicSponsorAdapter(mGridData, getViewContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rv_publicsponsor.setNestedScrollingEnabled(false);
        rv_publicsponsor.setLayoutManager(layoutManager);
        rv_publicsponsor.setAdapter(mAdapter);

        //initialize refresh
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        emptyvoucher = view.findViewById(R.id.emptyvoucher);
        refresh = view.findViewById(R.id.refresh);
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet = view.findViewById(R.id.refreshnointernet);
        refreshdisabled = view.findViewById(R.id.refreshdisabled);
        refreshdisabled1 = view.findViewById(R.id.refreshdisabled1);


        refresh.setOnClickListener(this);
        refreshnointernet.setOnClickListener(this);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        skeleton_public_sponsor = view.findViewById(R.id.skeleton_public_sponsor);
        skeleton_public_sponsor.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();

    }

    private void initData() {

        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        limit = "0";
        devicetype = "ANDROID";
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        getSession();
    }

    private void getSession() {

        showNoInternetConnection(false);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid);
            //getPublicSponsor(getPublicSponsorsSession);
            getPublicSponsorsV2();

        } else {
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
        }
    }

//    private final Callback<String> callsession = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("101")) {
//                    swipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Something went wrong. Please try again, and if problem persists, contact support. (Error101)", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("102")) {
//                    swipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Something went wrong. Please try again, and if problem persists, contact support. (Error102)", GlobalToastEnum.NOTICE);
//                } else if(responseData.equals("103")){
//                    swipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Something went wrong. Please try again, and if problem persists, contact support. (Error103)", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("505")) {
//                    swipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Something went wrong. Please try again, and if problem persists, contact support. (Error505)", GlobalToastEnum.NOTICE);
//                } else {
//                    authcode = CommonFunctions.getSha1Hex(imei + userid);
//                    getPublicSponsor(getPublicSponsorsSession);
//                }
//            } else {
//                swipeRefreshLayout.setRefreshing(false);
//                mLlLoader.setVisibility(View.GONE);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            swipeRefreshLayout.setRefreshing(false);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getPublicSponsor(Callback<GetPublicSponsorsResponse> getPublicSponsorsCallback) {
        Call<GetPublicSponsorsResponse> getpublicsponsors = RetrofitBuild.getPublicSponsorAPI(getViewContext())
                .getPublicSponsorsCall(imei,
                        authcode,
                        userid,
                        borrowerid,
                        limit,
                        devicetype);

        getpublicsponsors.enqueue(getPublicSponsorsCallback);
    }

    private final Callback<GetPublicSponsorsResponse> getPublicSponsorsSession = new Callback<GetPublicSponsorsResponse>() {
        @Override
        public void onResponse(Call<GetPublicSponsorsResponse> call, Response<GetPublicSponsorsResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {

                if (response.body().getStatus().equals("000")) {

                    if(mdb != null) {
                        mdb.truncateTable(mdb, PublicSponsorDBHelper.TABLE_NAME);
                    }

                    List<PublicSponsor> list;
                    list = response.body().getPublicSponsorList();
                    for (PublicSponsor publicSponsor : list) {
                        mdb.insertPublicSponsors(mdb, publicSponsor);
                    }

                    updateList(mdb.getPublicSponsors(mdb));

                } else {
                    //showError(response.body().getMessage());
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                //showError();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GetPublicSponsorsResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void updateList(List<PublicSponsor> data){
        if (data.size() > 0){
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            emptyvoucher.setVisibility(View.GONE);
            mAdapter.updateList(data);
            mAdapter.notifyDataSetChanged();
        } else{
            emptyvoucher.setVisibility(View.VISIBLE);
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        try {
            if(mdb != null){
                mdb.truncateTable(mdb, PublicSponsorDBHelper.TABLE_NAME);
                if(mAdapter != null){
                    mAdapter.clear();
                }
                swipeRefreshLayout.setRefreshing(true);
                getSession();
            }

        } catch (Exception e) {
            swipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.refresh:{
                disableRefresh();
                getSession();
                break;
            }
            case R.id.refreshnointernet: {
                disableRefresh();
                getSession();
            }
        }
    }

    //disable refresh button in empty screen
    private void disableRefresh() {
        try {
            refreshdisabled.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
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
            refresh.setVisibility(View.VISIBLE);
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
            swipeRefreshLayout.setVisibility(View.GONE);
            emptyvoucher.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
            mShimmerViewContainer.setVisibility(View.GONE);
        } else {
            swipeRefreshLayout.setVisibility(View.GONE);
            emptyvoucher.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.GONE);
            mShimmerViewContainer.setVisibility(View.GONE);
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

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
    * */

    private void getPublicSponsorsV2(){

            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

                LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("limit",limit);
                parameters.put("devicetype",devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getPublicSponsors");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getPublicSponsorV2Object(getPublicSponsorsSessionV2);

            }else{
                mShimmerViewContainer.stopShimmer();
                swipeRefreshLayout.setRefreshing(false);
                showNoInternetConnection(true);
                enableRefresh();
            }

    }
    private void getPublicSponsorV2Object(Callback<GenericResponse> getPublicSponsorsCallback) {
        Call<GenericResponse> getpublicsponsors = RetrofitBuilder.getPublicSponsorV2API(getViewContext())
                .getPublicSponsors(authenticationid,sessionid,param);
        getpublicsponsors.enqueue(getPublicSponsorsCallback);
    }

    private final Callback<GenericResponse> getPublicSponsorsSessionV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());

                if (response.body().getStatus().equals("000")) {

                    String decrypteData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    if(decrypteData.equalsIgnoreCase("error") || decryptedMessage.equalsIgnoreCase("error")){
                        showError();
                    }else{
                        if(mdb != null) {
                            mdb.truncateTable(mdb, PublicSponsorDBHelper.TABLE_NAME);
                        }

                        List<PublicSponsor> list = new Gson().fromJson(decrypteData,
                                new TypeToken<List<PublicSponsor>>() {}.getType());

                        for (PublicSponsor publicSponsor : list) {
                            mdb.insertPublicSponsors(mdb, publicSponsor);
                        }
                        updateList(mdb.getPublicSponsors(mdb));
                    }

                } else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else {
                    //showError(response.body().getMessage());
                    showError(decryptedMessage);
                }
            } else {
                //showError();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            showNoInternetConnection(true);
            swipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };


}
