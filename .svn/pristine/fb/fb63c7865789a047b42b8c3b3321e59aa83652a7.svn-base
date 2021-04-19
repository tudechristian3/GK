package com.goodkredit.myapplication.activities.gkearn.stockist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gkearn.GKEarnStockistPackagesAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.gkearn.GKEarnStockistPackage;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.gkearn.GKEarnPackagesResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKEarnStockistPackagesActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String borrowername = "";
    private String borroweremail = "";
    private String borrowermobileno = "";

    private String servicecode = "";
    private String merchantid = "";
    private String merchantname = "";

    private GKService gkService;

    //SWIPE REFRESH
    private SwipeRefreshLayout swipe_container;

    private NestedScrollView home_body;

    //PACKAGES
    private RecyclerView rv_stockistpackages;
    private List<GKEarnStockistPackage> gkEarnStockistPackageList = new ArrayList<>();
    private GKEarnStockistPackagesAdapter rv_stockistpackagesadapter;
    private ImageView imv_gkearn_aboutstockist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gkearn_stockist_packages);

            init();

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() {
        swipe_container = findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(this);

        home_body = (NestedScrollView) findViewById(R.id.home_body);

        rv_stockistpackages = findViewById(R.id.rv_stockistpackages);
    }

    private void initData() {
        setupToolbarWithTitle("APPLY AS STOCKIST");

        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        //PACKAGES
        rv_stockistpackages.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_stockistpackages.setNestedScrollingEnabled(false);

        rv_stockistpackagesadapter = new GKEarnStockistPackagesAdapter(getViewContext());
        rv_stockistpackages.setAdapter(rv_stockistpackagesadapter);

        imv_gkearn_aboutstockist = findViewById(R.id.imv_gkearn_aboutstockist);

        callMainAPI();
    }

    //API
    private void callMainAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Fetching GK Packages.", "Please wait...");
            getEarnPackages();
            getMerchantVirtualAssigned();
        } else {
            swipe_container.setRefreshing(false);
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getEarnPackages() {
        Call<GKEarnPackagesResponse> getEarnPackages = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .getGKEarnPackages(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid)
                );

        getEarnPackages.enqueue(getEarnPackagesCallBack);
    }

    private final Callback<GKEarnPackagesResponse> getEarnPackagesCallBack = new Callback<GKEarnPackagesResponse>() {
        @Override
        public void onResponse(Call<GKEarnPackagesResponse> call, Response<GKEarnPackagesResponse> response) {
            try {
                hideProgressDialog();
                swipe_container.setRefreshing(false);
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        CacheManager.getInstance().saveGKEarnStockistPackage(response.body().getData());
                        updateList(CacheManager.getInstance().getGKEarnStockistPackage());
                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                swipe_container.setRefreshing(false);
                hideProgressDialog();
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GKEarnPackagesResponse> call, Throwable t) {
            swipe_container.setRefreshing(false);
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

    private void updateList(List<GKEarnStockistPackage> data) {
        if (data.size() > 0) {
            rv_stockistpackagesadapter.updateData(data);
            Glide.with(getViewContext())
                    .load(R.drawable.gkearn_stockist_logo)
                    .apply(new RequestOptions()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    )
                    .into(imv_gkearn_aboutstockist);
            imv_gkearn_aboutstockist.setVisibility(View.VISIBLE);
            swipe_container.setVisibility(View.VISIBLE);
            swipe_container.setEnabled(true);
        } else {
            rv_stockistpackagesadapter.clear();
            imv_gkearn_aboutstockist.setVisibility(View.GONE);
        }
    }

    private void getMerchantVirtualAssigned() {
        Call<GenericResponse> getMerchantVirtualAssigned = RetrofitBuild.getRFIDAPIService(getViewContext())
                .getMerchantVirtualAssigned(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        "GKEARNSTOCKIST",
                        CommonVariables.devicetype
                );
        getMerchantVirtualAssigned.enqueue(getMerchantVirtualAssignedCallBack);
    }

    private final Callback<GenericResponse> getMerchantVirtualAssignedCallBack =
            new Callback<GenericResponse>() {

                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    try {
                        hideProgressDialog();
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {

                                String data = response.body().getData();

                                merchantid = CommonFunctions.parseJSON(data, "MerchantID");

                                servicecode = CommonFunctions.parseJSON(data, "Type");

                                PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_GKEARNSTOCKISTMERCHANTID);
                                PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_GKEARNSTOCKISTMERCHANTID, merchantid);

                                PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_GKEARNSTOCKISTSERVICECODE);
                                PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_GKEARNSTOCKISTSERVICECODE, servicecode);

                            } else if (response.body().getStatus().equals("104")) {
                                showAutoLogoutDialog(response.body().getMessage());
                            } else {
                                showErrorGlobalDialogs();
                            }

                        } else {
                            showErrorGlobalDialogs();
                        }
                    } catch (Exception e) {
                        hideProgressDialog();
                        showNoInternetToast();
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    hideProgressDialog();
                    showNoInternetToast();
                }
            };

    //-----------------------------OTHERS-------------------------------------
    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, GKEarnStockistPackagesActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        swipe_container.setRefreshing(true);
        callMainAPI();
    }
}
