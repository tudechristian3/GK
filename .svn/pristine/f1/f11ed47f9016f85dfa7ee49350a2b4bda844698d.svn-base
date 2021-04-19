package com.goodkredit.myapplication.activities.whatsnew;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.github.jrejaud.viewpagerindicator2.CirclePageIndicator;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.whatsnew.GKAdsVPagerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.model.gkads.GKAds;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKAdsActivity extends BaseActivity implements View.OnClickListener {

    private static final String KEY_SOURCE = "source";

    private List<GKAds> mAds;

    private boolean mIsPopUp = true;

    private ViewPager mVPager;
    private CirclePageIndicator mVPagerIndicator;

    private String sessionid ;

    //NEW VARIABLES
    private String index;
    private String authenticationid;
    private String param;
    private String keyEncryption;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gkads);
        overridePendingTransition(R.anim.fade_in_200, R.anim.fade_out_200);
        init();
    }

    private void init() {

        mIsPopUp = getIntent().getBooleanExtra(KEY_SOURCE, true);

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mVPager = findViewById(R.id.vpager);
        mVPager.setAdapter(new GKAdsVPagerAdapter(getSupportFragmentManager(), getViewContext()));
        mVPagerIndicator = findViewById(R.id.indicator);
        mVPagerIndicator.setViewPager(mVPager);

        mAds = CacheManager.getInstance().getPromotions();
        updateAdViews(mAds.get(0).getGKAdID());

        findViewById(R.id.btn_close_ad).setOnClickListener(this);
        findViewById(R.id.btn_state).setOnClickListener(this);

        if (mIsPopUp) {
            findViewById(R.id.btn_state).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.btn_state).setVisibility(View.INVISIBLE);
        }

    }

    public static void start(Context context, boolean isPopUp) {
        Intent intent = new Intent(context, GKAdsActivity.class);
        intent.putExtra(KEY_SOURCE, isPopUp);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_ad: {
                onBackPressed();
                break;
            }
            case R.id.btn_state: {
                PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_GK_ADS_DISALLOW_POP_UP, true);
                onBackPressed();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in_200, R.anim.fade_out_200);
    }

    private void updateAdViews(String adID) {

        LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
        parameters.put("imei",imei);
        parameters.put("userid",userid);
        parameters.put("borrowerid",borrowerid);
        parameters.put("adID", adID);
        parameters.put("devicetype", CommonVariables.devicetype);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "updateAdViewsV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        Call<GenericResponse> call = RetrofitBuilder.getWhatsNewV2API(getViewContext())
                .updateAdViewsV2(
                       authenticationid,sessionid,param
                );
        call.enqueue(updateAdViewsCallback);

    }
    private Callback<GenericResponse> updateAdViewsCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            if(response.errorBody() == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    //success
                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else{
                    showErrorToast(message);
                }
            }else{
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            showErrorToast();
        }
    };
}
