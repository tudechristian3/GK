package com.goodkredit.myapplication.activities.whatsnew;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.model.gkads.GKAds;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKWhatsNewCommonDetailsActivity extends BaseActivity {

    private GKAds mAds;

    private ImageView mImgVAdImage;
    private TextView mTvAdTitle;
    private TextView mTvDate;
    private TextView mTvAdDescription;
    private String sessionid;

    //NEW VARIABLES
    private String index;
    private String authenticationid;
    private String param;
    private String keyEncryption;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_gkads_details);
        init();

    }

    private void init() {

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        mAds = getIntent().getParcelableExtra(GKAds.KEY_OBJECT);

        if (mAds.getType().equals("PROMOTIONS")) {
            setupToolbarWithTitle("Promotions");
        } else {
            setupToolbar();
        }

        mImgVAdImage = findViewById(R.id.imgV_ad_image);
        mTvAdTitle = findViewById(R.id.tv_ad_title);
        mTvDate = findViewById(R.id.tv_date);
        mTvAdDescription = findViewById(R.id.tv_ad_description);

        populateData(mAds);

        updateAdViews(mAds.getGKAdID());
    }

    private void populateData(GKAds ads) {
        Glide.with(getViewContext())
                .load(ads.getURL())
                .apply(RequestOptions.placeholderOf(R.drawable.generic_placeholder_gk_background).fitCenter())
                .into(mImgVAdImage);
        mTvAdTitle.setText(CommonFunctions.replaceEscapeData(ads.getName()));
        mTvDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(ads.getDurationFrom())));
        mTvAdDescription.setText(CommonFunctions.replaceEscapeData(ads.getDescription()));
    }


    public static void start(Context context, GKAds ads) {
        Intent intent = new Intent(context, GKWhatsNewCommonDetailsActivity.class);
        intent.putExtra(GKAds.KEY_OBJECT, ads);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
