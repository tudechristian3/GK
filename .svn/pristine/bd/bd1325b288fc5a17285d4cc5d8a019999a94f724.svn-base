package com.goodkredit.myapplication.activities.gknegosyo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.claudiodegio.msv.MaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gknegosyo.GKNegosyoDistributorsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.gknegosyo.GKNegosyoDistributorsDBHelper;
import com.goodkredit.myapplication.model.gknegosyo.Distributor;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetDistributorsResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKNegosyoDistributorsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnSearchViewListener {

    private GKService mService;
    private MaterialSearchView mSearchView;

    private SwipeRefreshLayout mSrlDistributors;
    private RecyclerView mRvDistributors;
    private GKNegosyoDistributorsAdapter mAdapter;

    private LinearLayout mLlEmpltyView;
    private ImageView mImgvImage;
    private TextView mTvEmptyText;

    private DatabaseHandler mDb;

    private String sessionid;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String param;
    private String keyEncryption;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_distributors);
        init();
    }

    public static void start(Context context, GKService service) {
        Intent intent = new Intent(context, GKNegosyoDistributorsActivity.class);
        intent.putExtra(GKService.KEY_SERVICE_OBJ, service);
        context.startActivity(intent);
    }

    private void init() {

        setupToolbar();
        mDb = new DatabaseHandler(getViewContext());

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mService = getIntent().getParcelableExtra(GKService.KEY_SERVICE_OBJ);

        mSrlDistributors = findViewById(R.id.srl_generic);
        mSrlDistributors.setOnRefreshListener(this);
        mRvDistributors = findViewById(R.id.rv_generic);
        mRvDistributors.setLayoutManager(new LinearLayoutManager(getViewContext()));
        mRvDistributors.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null, false, true));
        mAdapter = new GKNegosyoDistributorsAdapter(getViewContext(), mService);
        mRvDistributors.setAdapter(mAdapter);

        mSearchView = findViewById(R.id.sv_distro);
        mSearchView.setOnSearchViewListener(this);

        mLlEmpltyView = findViewById(R.id.emptyView);
        mImgvImage = findViewById(R.id.imgv_empty);
        mImgvImage.setImageAlpha(50);
        mTvEmptyText = findViewById(R.id.tv_empty);

        getSession();
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

//    private void getSession() {
//        showProgressDialog(getViewContext(), "", "Please wait...");
//        createSession(getSessionCallback);
//    }
//
//    private Callback<String> getSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            try {
//                ResponseBody errBody = response.errorBody();
//                if (errBody == null) {
//                    String responseData = response.body();
//                    if (!responseData.isEmpty()) {
//                        if (responseData.equals("001") || responseData.equals("002")) {
//                            hideProgressDialog();
//                            showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                        } else if (responseData.equals("error")) {
//                            hideProgressDialog();
//                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                        } else if (responseData.contains("<!DOCTYPE html>")) {
//                            hideProgressDialog();
//                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                        } else {
//                            sessionid = responseData;
//                            getDistributors();
//                        }
//                    } else {
//                        hideProgressDialog();
//                        showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    }
//                } else {
//                    showError();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//            showError();
//        }
//    };

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            //getDistributors();
            getDistributorsV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getDistributors() {
        Call<GetDistributorsResponse> call = RetrofitBuild.getGKNegosyoAPIService(getViewContext())
                .getDistributors(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        sessionid,
                        "ANDROID"
                );
        call.enqueue(getDistributorsCallback);
    }

    private Callback<GetDistributorsResponse> getDistributorsCallback = new Callback<GetDistributorsResponse>() {
        @Override
        public void onResponse(Call<GetDistributorsResponse> call, Response<GetDistributorsResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                hideProgressDialog();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        List<Distributor> distributors = response.body().getGetDistributors();
                        mDb.truncateTable(mDb, GKNegosyoDistributorsDBHelper.TABLE_NAME);
                        for (Distributor distributor : distributors)
                            mDb.insertDistributor(mDb, distributor);
                        if (distributors.size() > 0) {
                            mLlEmpltyView.setVisibility(View.VISIBLE);
                            mAdapter.setData(mDb.getDistributors(mDb, ""));
                        } else {
                            setEmptyView();
                        }
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
        public void onFailure(Call<GetDistributorsResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

    private void setEmptyView() {
        mLlEmpltyView.setVisibility(View.VISIBLE);
        mTvEmptyText.setText("No available distributor as of the moment. Please swipe down to refresh.");
        Glide.with(getViewContext())
                .load(R.drawable.ic_gk_negosyo_package)
                .into(mImgvImage);
    }

    @Override
    public void onRefresh() {
        mSrlDistributors.setRefreshing(false);
        getSession();
    }

    @Override
    public void onSearchViewShown() {
    }

    @Override
    public void onSearchViewClosed() {
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public void onQueryTextChange(String s) {
        mAdapter.setData(mDb.getDistributors(mDb, s));
    }

    /**
     *  SECURITY UPDATE
     *  AS OF
     *  OCTOBER 2019
     * */

    private void getDistributorsV2(){
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
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getDistributorsV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getDistributorsV2Object(getDistributorsV2Callback);

        }else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getDistributorsV2Object(Callback<GenericResponse> getGKNegosyoTransaction) {
        Call<GenericResponse> call = RetrofitBuilder.getGkNegosyoV2API(getViewContext())
                .getDistributorsV2(
                        authenticationid,sessionid,param
                );

        call.enqueue(getGKNegosyoTransaction);
    }

    private Callback<GenericResponse> getDistributorsV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                hideProgressDialog();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                        List<Distributor> distributors = new Gson().fromJson(data, new TypeToken<List<Distributor>>(){}.getType());
                        mDb.truncateTable(mDb, GKNegosyoDistributorsDBHelper.TABLE_NAME);
                        for (Distributor distributor : distributors)
                            mDb.insertDistributor(mDb, distributor);
                        if (distributors.size() > 0) {
                            mLlEmpltyView.setVisibility(View.VISIBLE);
                            mAdapter.setData(mDb.getDistributors(mDb, ""));

                        } else {
                            setEmptyView();
                        }
                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
                        showError(message);
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

}
