package com.goodkredit.myapplication.fragments.projectcoop;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.projectcoop.ProjectCoopActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.SkycableConfig;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.skycable.GetSkycableConfigResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectCoopHomeFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private LinearLayout linearCoopPlayToSave;
    private TextView txvPlayToSave;

    private LinearLayout linearCoopSupport;
    private TextView txvSupport;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String servicecode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_coop_home, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        linearCoopPlayToSave = (LinearLayout) view.findViewById(R.id.linearCoopPlayToSave);
        linearCoopPlayToSave.setOnClickListener(this);
        txvPlayToSave = (TextView) view.findViewById(R.id.txvPlayToSave);
        linearCoopSupport = (LinearLayout) view.findViewById(R.id.linearCoopSupport);
        linearCoopSupport.setOnClickListener(this);
        txvSupport = (TextView) view.findViewById(R.id.txvSupport);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);
    }

    private void initData() {
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "projectcoopservicecode");
        txvPlayToSave.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "PLAY TO SAVE"));
        txvSupport.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "SUPPORT"));
        getSession();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                getActivity().finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearCoopPlayToSave: {
                ((ProjectCoopActivity) getViewContext()).displayView(2, null);
                break;
            }
            case R.id.linearCoopSupport: {
                ((ProjectCoopActivity) getViewContext()).displayView(5, null);
                break;
            }
            case R.id.refreshnointernet: {
                getSession();
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);

        getSession();
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
//
//            mTvFetching.setText("Fetching Configs..");
//            mTvWait.setText(" Please wait...");
//            mLlLoader.setVisibility(View.VISIBLE);
//
//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);
//        } else {
//            mSwipeRefreshLayout.setRefreshing(false);
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showError(getString(R.string.generic_internet_error_message));
//        }
//
//    }
//
//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    getSkycableConfig(getSkycableConfigSession);
//                }
//            } else {
//                mSwipeRefreshLayout.setRefreshing(false);
//                mLlLoader.setVisibility(View.GONE);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            mSwipeRefreshLayout.setRefreshing(false);
//            mLlLoader.setVisibility(View.GONE);
////            hideProgressDialog();
////            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {

            mTvFetching.setText("Fetching Configs..");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            getSkycableConfig(getSkycableConfigSession);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }

    }

    private void getSkycableConfig(Callback<GetSkycableConfigResponse> getSkycableConfigCallback) {
        Call<GetSkycableConfigResponse> getskycableconfig = RetrofitBuild.getSkycableConfigService(getViewContext())
                .getSkycableConfigCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        servicecode);
        getskycableconfig.enqueue(getSkycableConfigCallback);
    }

    private final Callback<GetSkycableConfigResponse> getSkycableConfigSession = new Callback<GetSkycableConfigResponse>() {

        @Override
        public void onResponse(Call<GetSkycableConfigResponse> call, Response<GetSkycableConfigResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    List<SkycableConfig> skycableConfigList = response.body().getSkycableConfigs();
                    for (SkycableConfig skycableConfig : skycableConfigList) {

                        switch (skycableConfig.getServiceConfigName()) {
                            case "P2S Play to Save": {
                                if (skycableConfig.getStatus().equals("ACTIVE")) {
                                    linearCoopPlayToSave.setVisibility(View.VISIBLE);
                                } else {
                                    linearCoopPlayToSave.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case "Support": {
                                if (skycableConfig.getStatus().equals("ACTIVE")) {
                                    linearCoopSupport.setVisibility(View.VISIBLE);
                                } else {
                                    linearCoopSupport.setVisibility(View.GONE);
                                }
                                break;
                            }
                        }
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetSkycableConfigResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }
}
