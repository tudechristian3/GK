package com.goodkredit.myapplication.fragments.whatsnew;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;
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

public class GKAdsCommonFragment extends BaseFragment implements OnClickListener {

    private GKAds mAds;

    private ImageView mImgViewAdsImage;
    private TextView mTvAdTitle;
    private TextView mTvAdDescription;

    private Intent intent = null;

    //NEW VARIABLES
    private String index;
    private String authenticationid;
    private String param;
    private String keyEncryption;

    private String index1;
    private String authenticationid1;
    private String param1;
    private String keyEncryption1;

    private String sessionid;

    public static GKAdsCommonFragment newInstance(GKAds ads) {
        GKAdsCommonFragment fragment = new GKAdsCommonFragment();
        Bundle b = new Bundle();
        b.putParcelable(GKAds.KEY_OBJECT, ads);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gk_ads_common, container, false);

        mAds = getArguments().getParcelable(GKAds.KEY_OBJECT);

        mImgViewAdsImage = view.findViewById(R.id.imgV_ad_image);
        mTvAdTitle = view.findViewById(R.id.tv_ad_title);
        mTvAdDescription = view.findViewById(R.id.tv_ad_description);

        view.findViewById(R.id.adLink).setOnClickListener(this);

        sessionid = PreferenceUtils.getSessionID(getViewContext());

        populateView(mAds);
        return view;
    }

    private void populateView(GKAds ads) {
        Glide.with(mContext)
                .load(ads.getURL())
                .apply(RequestOptions.placeholderOf(R.drawable.generic_placeholder_gk_background))
                .into(mImgViewAdsImage);
        mTvAdTitle.setText(CommonFunctions.replaceEscapeData(ads.getName()));
        String desc = CommonFunctions.replaceEscapeData(ads.getDescription());
        mTvAdDescription.setText(desc);
        if (!ads.getRedirectionURL().isEmpty() && !ads.getRedirectionURL().equals(".")) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ads.getRedirectionURL()));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && mAds != null) {
            updateAdViews(mAds.getGKAdID());
        }
    }

    private void updateAdViews(String adID) {

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
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
        }else{
            showNoInternetToast();
        }

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

    private void updateAdClicks(String adID) {

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("adID", adID);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index1 = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index1);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid1 = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption1 = CommonFunctions.getSha1Hex(authenticationid1 + sessionid + "updateAdClicksV2");
            param1 = CommonFunctions.encryptAES256CBC(keyEncryption1, String.valueOf(paramJson));

            Call<GenericResponse> call = RetrofitBuilder.getWhatsNewV2API(getViewContext())
                    .updateAdClicksV2(
                            authenticationid1,sessionid,param1
                    );
            call.enqueue(updateAdClicksCallback);
        }else{
            showNoInternetToast();
        }
    }
    private Callback<GenericResponse> updateAdClicksCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            if(response.errorBody() == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption1,response.body().getMessage());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adLink: {
                if (!mAds.getRedirectionURL().isEmpty() && !mAds.getRedirectionURL().equals(".") && intent != null) {
                    updateAdClicks(mAds.getGKAdID());
                    startActivity(intent);
                }
                break;
            }
        }
    }
}
