package com.goodkredit.myapplication.fragments.skycable;

import android.content.Intent;
import android.os.Bundle;
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
import com.goodkredit.myapplication.activities.billspayment.BillsPaymentBillerDetailsActivity;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.SkycableConfig;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.skycable.GetSkycableConfigResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkycableHomeFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private LinearLayout skycableNewApplication;
    private LinearLayout skycableSoa;
    private LinearLayout skycablePpv;
    private LinearLayout skycableBillsPay;
    private LinearLayout skycableSupport;

    private TextView txvNewApp;
    private TextView txvSoa;
    private TextView txvPpv;
    private TextView txvBillsPay;
    private TextView txvSupport;

    private DatabaseHandler mdb;
//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String servicecode;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //UNIFIED SESSION
    private String sessionid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_home, container, false);

        setHasOptionsMenu(true);

        hideKeyboard(getViewContext());

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        if (isAdded()) {
            //Skycable
            getActivity().setTitle("SKYCABLE");
        }

        skycableNewApplication = (LinearLayout) view.findViewById(R.id.skycableNewApplication);
        skycableNewApplication.setOnClickListener(this);
        skycableSoa = (LinearLayout) view.findViewById(R.id.skycableSoa);
        skycableSoa.setOnClickListener(this);
        skycablePpv = (LinearLayout) view.findViewById(R.id.skycablePpv);
        skycablePpv.setOnClickListener(this);
        skycableBillsPay = (LinearLayout) view.findViewById(R.id.skycableBillsPay);
        skycableBillsPay.setOnClickListener(this);
        skycableSupport = (LinearLayout) view.findViewById(R.id.skycableSupport);
        skycableSupport.setOnClickListener(this);

        txvNewApp = (TextView) view.findViewById(R.id.txvNewApp);
        txvSoa = (TextView) view.findViewById(R.id.txvSoa);
        txvPpv = (TextView) view.findViewById(R.id.txvPpv);
        txvBillsPay = (TextView) view.findViewById(R.id.txvBillsPay);
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
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "skycablebillcode");

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        txvNewApp.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "NEW APPLICATION"));
        txvSoa.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "STATEMENT OF ACCOUNT"));
        txvPpv.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "PAY PER VIEW"));
        txvBillsPay.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "BILLS PAYMENT"));
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
            case R.id.skycableNewApplication: {
                ((SkyCableActivity) getViewContext()).displayView(16, null);
                break;
            }
            case R.id.skycableSoa: {
                ((SkyCableActivity) getViewContext()).displayView(3, null);
                break;
            }
            case R.id.skycablePpv: {
                ((SkyCableActivity) getViewContext()).displayView(4, null);
                break;
            }
            case R.id.skycableBillsPay: {
                Intent intent = new Intent(getViewContext(), BillsPaymentBillerDetailsActivity.class);
                intent.putExtra("BILLCODE", PreferenceUtils.getStringPreference(getViewContext(), "skycablebillcode"));
                intent.putExtra("SPBILLCODE", PreferenceUtils.getStringPreference(getViewContext(), "skycablespbillcode"));
                intent.putExtra("SPBILLERACCOUNTNO", "");
                intent.putExtra("BILLNAME", PreferenceUtils.getStringPreference(getViewContext(), "skycablebillname"));
                intent.putExtra("FROM", "BILLERS");
                intent.putExtra("SKYCABLEBILLSPAYMENT", true);
                getViewContext().startActivity(intent);

                break;
            }
            case R.id.skycableSupport: {
                ((SkyCableActivity) getViewContext()).displayView(12, null);
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

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {

            mTvFetching.setText("Fetching Configs..");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getSkycableConfig(getSkycableConfigSession);

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }

    }

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
////            hideProgressDialog();
////            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//
//        }
//    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
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
                            case "New Application": {
                                if (skycableConfig.getStatus().equals("ACTIVE")) {
                                    skycableNewApplication.setVisibility(View.VISIBLE);
                                } else {
                                    skycableNewApplication.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case "Statement of Accounts": {
                                if (skycableConfig.getStatus().equals("ACTIVE")) {
                                    skycableSoa.setVisibility(View.VISIBLE);
                                } else {
                                    skycableSoa.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case "Pay Per View": {
                                if (skycableConfig.getStatus().equals("ACTIVE")) {
                                    skycablePpv.setVisibility(View.VISIBLE);
                                } else {
                                    skycablePpv.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case "Bills Payment": {
                                if (skycableConfig.getStatus().equals("ACTIVE")) {
                                    skycableBillsPay.setVisibility(View.VISIBLE);
                                } else {
                                    skycableBillsPay.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case "Support": {
                                if (skycableConfig.getStatus().equals("ACTIVE")) {
                                    skycableSupport.setVisibility(View.VISIBLE);
                                } else {
                                    skycableSupport.setVisibility(View.GONE);
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
}
