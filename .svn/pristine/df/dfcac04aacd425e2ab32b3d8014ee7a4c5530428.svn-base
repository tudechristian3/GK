package com.goodkredit.myapplication.activities.gknegosyo;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gknegosyo.GKNegosyoPackagesAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.model.GKNegosyPackage;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetGKNegosyoPackagesResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.SpacesItemDecoration;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKNegosyoPackagesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static String KEY_GKSERVICE = "gkservice";
    private static String KEY_DISTRIBUTOR_ID = "distributor_id";

    private RecyclerView mRvGKNegosyoPackages;
    private List<GKNegosyPackage> gkNegosyPackages;
    private GKNegosyoPackagesAdapter mGKNegosyoAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private GKService mGKService;

    private String mDistributorID;
    private String mDistributorName;

    private Address mAddress;

    private String sessionid;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_packages);
        init();
    }

    public static void start(Context context, GKService service, String distributorID, Address address, String distributorName) {
        Intent intent = new Intent(context, GKNegosyoPackagesActivity.class);
        intent.putExtra(KEY_GKSERVICE, service);
        intent.putExtra(KEY_DISTRIBUTOR_ID, distributorID);
        intent.putExtra("KEY_DISTRO_NAME", distributorName);
        intent.putExtra("KEY_ADDRESS", address);
        context.startActivity(intent);
    }

    private void init() {
        setupToolbar();

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mGKService = getIntent().getParcelableExtra(KEY_GKSERVICE);
        mDistributorID = getIntent().getStringExtra(KEY_DISTRIBUTOR_ID);
        mDistributorName = getIntent().getStringExtra("KEY_DISTRO_NAME");
        mAddress = getIntent().getParcelableExtra("KEY_ADDRESS");

        gkNegosyPackages = CacheManager.getInstance().getGKNegosyPackage();

        mGKNegosyoAdapter = new GKNegosyoPackagesAdapter(getViewContext(), mGKService, gkNegosyPackages, mAddress);
        mGKNegosyoAdapter.updateDistributorID(mDistributorID);
        mGKNegosyoAdapter.updateDistributorName(mDistributorName);

        mRvGKNegosyoPackages = findViewById(R.id.rv_gk_negosyo_packages);
        mRvGKNegosyoPackages.setLayoutManager(new LinearLayoutManager(getViewContext()));
        mRvGKNegosyoPackages.addItemDecoration(new SpacesItemDecoration(40));
        mRvGKNegosyoPackages.setAdapter(mGKNegosyoAdapter);

        mSwipeRefreshLayout = findViewById(R.id.swp_rfrsh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        createSession();

        //checkIfPromoActive();
        checkIfPromoActiveV2();
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
        super.onBackPressed();
    }


    private void createSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            getGKNegosyoPackages();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getGKNegosyoPackages() {
        Call<GetGKNegosyoPackagesResponse> call = RetrofitBuild.getGKNegosyoAPIService(getViewContext())
                .getGKNegosyoPackages(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        sessionid,
                        mDistributorID
                );

        call.enqueue(getGKNegosyoPackagesCallback);
    }

    private Callback<GetGKNegosyoPackagesResponse> getGKNegosyoPackagesCallback = new Callback<GetGKNegosyoPackagesResponse>() {
        @Override
        public void onResponse(Call<GetGKNegosyoPackagesResponse> call, Response<GetGKNegosyoPackagesResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        CacheManager.getInstance().saveGKNegosyPackagesAndDetails(response.body().getGetGKNegosyoPackagesAndDetails());
                        CacheManager.getInstance().saveGKNegosyPackages(response.body().getGetGKNegosyoPackagesAndDetails().getDistributorPackages());
                        mGKNegosyoAdapter.updatePackages(response.body().getGetGKNegosyoPackagesAndDetails().getDistributorPackages());
                    } else {
                        showError(response.body().getMessage());
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetGKNegosyoPackagesResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        //checkIfPromoActive();
        checkIfPromoActiveV2();
        createSession();
    }

    private void checkIfPromoActive() {
        Call<GenericResponse> call = RetrofitBuild.getReferralAPIService(getViewContext())
                .checkIfPromoActive(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid),
                        borrowerid
                );

        call.enqueue(checkIfPromoActiveCallback);
    }

    private Callback<GenericResponse> checkIfPromoActiveCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_IS_REFER_RESSELER_PROMO, true);
                    } else {
                        PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_IS_REFER_RESSELER_PROMO, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
        }
    };

    /**
     * SECURITY UPDATE
     *  AS OF
     *  OCTOBER 2019
     * */

    private void checkIfPromoActiveV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "validateResellerReferralIfAvailable");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            checkIfPromoActiveV2Object(checkIfPromoActiveV2Callback);

        }else{
            showNoInternetToast();
        }
    }
    private void checkIfPromoActiveV2Object(Callback<GenericResponse> validateResellerReferral) {
        Call<GenericResponse> call = RetrofitBuilder.getAccountV2API(getViewContext())
                .validateResellerReferralIfAvailable(
                        authenticationid,sessionid,param
                );

        call.enqueue(validateResellerReferral);
    }
    private Callback<GenericResponse> checkIfPromoActiveV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_IS_REFER_RESSELER_PROMO, true);
                    }else if(response.body().getStatus().equals("003")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
                        PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_IS_REFER_RESSELER_PROMO, false);
                    }
                }
            } catch (Exception e) {
                showError();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            showError();
        }
    };


}
