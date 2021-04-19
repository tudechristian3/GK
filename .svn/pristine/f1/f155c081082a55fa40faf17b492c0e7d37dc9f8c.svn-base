package com.goodkredit.myapplication.activities.dropoff;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.dropoff.DropOffMerchantsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.dropoff.DropOffMerchants;
import com.goodkredit.myapplication.responses.dropoff.SearchDropOffMerchantsResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.SpacesItemDecoration;
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

public class DropOffMerchantsActivity extends BaseActivity {

    private List<DropOffMerchants> mGridData = new ArrayList<>();
    private DropOffMerchantsAdapter mAdapter;
    private RecyclerView rv_dropoff_merchants;

    private DatabaseHandler mdb;
    private String imei;
    private String sessionid;
    private String authcode;
    private String borrowerid;
    private String userid;
    private String merchantid;
    private String merchantname;
    private String devicetype;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropoff_merchants);

        init();
        initData();
    }

    private void init() {
        setupToolbar();

        rv_dropoff_merchants = (RecyclerView) findViewById(R.id.rv_dropoff_merchants);

        mAdapter = new DropOffMerchantsAdapter(mGridData, getViewContext(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rv_dropoff_merchants.setNestedScrollingEnabled(false);
        rv_dropoff_merchants.setLayoutManager(layoutManager);
        rv_dropoff_merchants.addItemDecoration(new SpacesItemDecoration(40));
        rv_dropoff_merchants.setAdapter(mAdapter);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        merchantid = getIntent().getStringExtra("merchantid");
        merchantname = merchantid;
        devicetype = "ANDROID";

        getSession();
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //searchDropOffMerchants(searchDropOffMerchantsSession);
            searchDropOffMerchantsV2();
        } else {
            showNoInternetToast();
        }
    }

    private void searchDropOffMerchants(Callback<SearchDropOffMerchantsResponse> searchDropOffMerchantsCallback) {
        Call<SearchDropOffMerchantsResponse> searchdropoffmerchants = RetrofitBuild.getDropOffAPIService(getViewContext())
                .searchDropOffMerchantsCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        merchantid,
                        merchantname,
                        devicetype);

        searchdropoffmerchants.enqueue(searchDropOffMerchantsCallback);
    }

    private final Callback<SearchDropOffMerchantsResponse> searchDropOffMerchantsSession = new Callback<SearchDropOffMerchantsResponse>() {
        @Override
        public void onResponse(Call<SearchDropOffMerchantsResponse> call, Response<SearchDropOffMerchantsResponse> response) {

            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    List<DropOffMerchants> list = new ArrayList<>();
                    list = response.body().getDropOffMerchantsList();

                    for (DropOffMerchants dropoffmerchants : list) {
                        mdb.insertDropOffMerchants(mdb, dropoffmerchants);
                    }
                    mAdapter.updateList(list);
                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }
        }

        @Override
        public void onFailure(Call<SearchDropOffMerchantsResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    public void setMerchant(DropOffMerchants dropOffMerchants) {
        Intent intent = new Intent();
        intent.putExtra(DropOffMerchants.KEY_DROPOFFMERCHANTS, dropOffMerchants);
        setResult(RESULT_OK, intent);
        finish();
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

        setResult(RESULT_CANCELED);
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void searchDropOffMerchantsV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("merchantid", merchantid);
            parameters.put("merchantname",merchantname);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "searchDropOffMerchantV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            searchDropOffMerchantsV2Object(searchDropOffMerchantsV2Session);

        }else{
            showNoInternetToast();
        }
    }

    private void searchDropOffMerchantsV2Object(Callback<GenericResponse> searchDropOffMerchantsCallback) {
        Call<GenericResponse> searchdropoffmerchants = RetrofitBuilder.getDropOffV2API(getViewContext())
                .searchDropOffMerchantV2(authenticationid,sessionid,param);
        searchdropoffmerchants.enqueue(searchDropOffMerchantsCallback);
    }

    private final Callback<GenericResponse> searchDropOffMerchantsV2Session = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<DropOffMerchants> list = new Gson().fromJson(data, new TypeToken<List<DropOffMerchants>>(){}.getType());

                    for (DropOffMerchants dropoffmerchants : list) {
                        mdb.insertDropOffMerchants(mdb, dropoffmerchants);
                    }
                    mAdapter.updateList(list);
                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(message);
                }else {
                    showError(message);
                }
            } else {
                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

}
