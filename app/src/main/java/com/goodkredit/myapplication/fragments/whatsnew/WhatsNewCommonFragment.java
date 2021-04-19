package com.goodkredit.myapplication.fragments.whatsnew;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.whatsnew.GKAdsActivity;
import com.goodkredit.myapplication.adapter.whatsnew.NewUpdatesAdapter;
import com.goodkredit.myapplication.adapter.whatsnew.PromotionsAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.GenericObject;
import com.goodkredit.myapplication.model.gkads.GKAds;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.whatsnew.GetGKAdsResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.SpacesItemDecoration;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WhatsNewCommonFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout srl_generic;
    private RecyclerView rv_generic;

    private LinearLayout mEmptyLayout;
    private ImageView mImgVEmptyImage;
    private TextView mTvEmptyMessage;

    private NewUpdatesAdapter mNewUpdatesAdapter;
    private PromotionsAdapter mPromotionsAdapter;

    private String mType;

    private int POS = -1;

    private GPSTracker mGPSTracker;
    private double mLongitude;
    private double mLatitude;

    private MaterialDialog mDialog;

    private GlobalDialogs globalDialogs;

    //NEW VARIABLES
    private String index;
    private String authenticationid;
    private String param;
    private String keyEncryption;
    
    private String sessionid ;


    private String getGKAdsIndex;
    private String getGKAdsAuthID;
    private String getGKAdsKey;
    private String getGKAdsParam;

    public static WhatsNewCommonFragment newInstance(int pos) {
        WhatsNewCommonFragment fragment = new WhatsNewCommonFragment();
        Bundle b = new Bundle();
        b.putInt("position", pos);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generic_list, container, false);

        mGPSTracker = new GPSTracker(getViewContext());

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mImgVEmptyImage = view.findViewById(R.id.imgv_empty);
        mTvEmptyMessage = view.findViewById(R.id.tv_empty);
        mEmptyLayout = view.findViewById(R.id.emptyView);

        srl_generic = view.findViewById(R.id.srl_generic);
        srl_generic.setOnRefreshListener(this);

        rv_generic = view.findViewById(R.id.rv_generic);
        rv_generic.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_generic.addItemDecoration(new SpacesItemDecoration(40));

        mNewUpdatesAdapter = new NewUpdatesAdapter(getViewContext());
        mPromotionsAdapter = new PromotionsAdapter(getViewContext());

        POS = getArguments().getInt("position");

        if (POS == 0) {
            mType = "WHATSNEW";
            rv_generic.setAdapter(mNewUpdatesAdapter);
            Glide.with(getViewContext())
                    .load(R.drawable.ic_updates)
                    .into(mImgVEmptyImage);
            mTvEmptyMessage.setText("No new updates yet.");
        } else if (POS == 1) {
            mType = "PROMOTIONS";
            rv_generic.setAdapter(mPromotionsAdapter);
            Glide.with(getViewContext())
                    .load(R.drawable.ic_promotions)
                    .into(mImgVEmptyImage);
            mTvEmptyMessage.setText("No promotions yet.");
        }

//        if (isGPSEnabled(getViewContext())) {
            getGKAds();
//        } else {
//            gpsNotEnabledDialog();
//        }

        return view;
    }


    @Override
    public void onRefresh() {
        srl_generic.setRefreshing(false);
        srl_generic.setEnabled(false);
        if (isGPSEnabled(getViewContext())) {
            getGKAds();
        } else {
            gpsNotEnabledDialogNew();
        }
    }

    private void getGKAds() {
        mLongitude = mGPSTracker.getLongitude();
        mLatitude = mGPSTracker.getLatitude();
        showProgressDialog("", "Please wait...");

        if (mType.equals("PROMOTIONS")) {
//            Call<GetGKAdsResponse> call = RetrofitBuild.getWhatsNewAPIService(getViewContext())
//                    .getGKPromotions(
//                            imei,
//                            userid,
//                            CommonFunctions.getSha1Hex(imei + userid),
//                            borrowerid,
//                            mType,
//                            "ANDROID",
//                            mLongitude,
//                            mLatitude
//                    );
//
//            call.enqueue(getGKAdsCallback);
            getGKPromotionsV2();
        } else {
//            Call<GetGKAdsResponse> call = RetrofitBuild.getWhatsNewAPIService(getViewContext())
//                    .getGKNewUpdates(
//                            imei,
//                            userid,
//                            CommonFunctions.getSha1Hex(imei + userid),
//                            borrowerid,
//                            mType,
//                            "ANDROID",
//                            mLongitude,
//                            mLatitude
//                    );
//
//            call.enqueue(getGKAdsCallback);
            getGKNewUpdatesV2();
        }


    }

    private Callback<GetGKAdsResponse> getGKAdsCallback = new Callback<GetGKAdsResponse>() {
        @Override
        public void onResponse(Call<GetGKAdsResponse> call, Response<GetGKAdsResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        if (POS == 0) {
                            CacheManager.getInstance().saveNewUpdates(response.body().getAds());
                            mNewUpdatesAdapter.update(CacheManager.getInstance().getNewUpdates());
                        } else {
                            CacheManager.getInstance().savePromotions(response.body().getAds());
                            mPromotionsAdapter.update(CacheManager.getInstance().getPromotions());
                        }
                        if (response.body().getAds().size() > 0) {
                            mEmptyLayout.setVisibility(View.GONE);
                            rv_generic.setVisibility(View.VISIBLE);
                        } else {
                            mEmptyLayout.setVisibility(View.VISIBLE);
                            rv_generic.setVisibility(View.GONE);
                        }
                    } else {
                        //showError(response.body().getMessage());
                        showErrorGlobalDialogs(response.body().getMessage());
                        hideProgressDialog();
                    }
                } else {
                    //showError();
                    showErrorGlobalDialogs(response.body().getMessage());
                    hideProgressDialog();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetGKAdsResponse> call, Throwable t) {
            hideProgressDialog();
            //showError();
            showErrorGlobalDialogs();
        }
    };

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void setUserVisibleHint(boolean isConsummationVisibleToUser) {
        super.setUserVisibleHint(isConsummationVisibleToUser);
        try {
            if (mNewUpdatesAdapter != null) {
                if (POS == 0) {
                    if (CacheManager.getInstance().getNewUpdates().size() == 0)
                        getGKAds();
                } else {
                    if (CacheManager.getInstance().getPromotions().size() == 0)
                        getGKAds();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gpsNotEnabledDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .content("GPS is disabled in your device.\nWould you like to enable it?")
                .cancelable(false)
                .positiveText("Go to Settings")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        showToast("GPS must be enabled to avail the service.", GlobalToastEnum.WARNING);
                    }
                })
                .show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mDialog.getView());
    }

    private void gpsNotEnabledDialogNew() {
        globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("Notice", "GPS is disabled in your device.\nWould you like to enable it?",
                "Cancel", "Go to Settings", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        View closebtn = globalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                showToast("GPS must be enabled to avail the service.", GlobalToastEnum.WARNING);
            }
        });

        View btndoubleone = globalDialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                showToast("GPS must be enabled to avail the service.", GlobalToastEnum.WARNING);
            }
        });

        View btndoubletwo = globalDialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getGKNewUpdatesV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("type",mType);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getGKNewUpdatesV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getGKNewUpdatesV2Object(getGKNewUpdatesV2Callback);

        }else{
            srl_generic.setEnabled(true);
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private void getGKNewUpdatesV2Object(Callback<GenericResponse> updatesCallback){
        Call<GenericResponse> call = RetrofitBuilder.getWhatsNewV2API(getViewContext())
                .getGKNewUpdatesV2(
                      authenticationid,sessionid,param
                );
        call.enqueue(updatesCallback);
    }
    private Callback<GenericResponse>getGKNewUpdatesV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message  = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                        List<GKAds> gkAds = new Gson().fromJson(data,new TypeToken<List<GKAds>>(){}.getType());
                        if (POS == 0) {
                            CacheManager.getInstance().saveNewUpdates(gkAds);
                            mNewUpdatesAdapter.update(CacheManager.getInstance().getNewUpdates());
                        } else {
                            CacheManager.getInstance().savePromotions(gkAds);
                            mPromotionsAdapter.update(CacheManager.getInstance().getPromotions());
                        }
                        if (gkAds.size() > 0) {
                            mEmptyLayout.setVisibility(View.GONE);
                            rv_generic.setVisibility(View.VISIBLE);
                        } else {
                            mEmptyLayout.setVisibility(View.VISIBLE);
                            rv_generic.setVisibility(View.GONE);
                        }
                    } else if(response.body().getStatus().equals("003")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
                        showErrorGlobalDialogs(message);
                        hideProgressDialog();
                    }
                } else {
                    showErrorGlobalDialogs();
                    hideProgressDialog();
                }
            srl_generic.setEnabled(true);
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            //showError();
            showErrorGlobalDialogs();
            srl_generic.setEnabled(true);
        }
    };

    //GET GKPROMOTIONS V2
    private void getGKPromotionsV2(){

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            mLongitude = mGPSTracker.getLongitude();
            mLatitude = mGPSTracker.getLatitude();

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("type", "PROMOTIONS");
            parameters.put("longitude", String.valueOf(mLongitude));
            parameters.put("latitude", String.valueOf(mLatitude));
            parameters.put("devicetype", CommonVariables.devicetype);


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            getGKAdsIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", getGKAdsIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            getGKAdsAuthID = CommonFunctions.parseJSON(jsonString, "authenticationid");
            getGKAdsKey = CommonFunctions.getSha1Hex(getGKAdsAuthID + sessionid + "getGKPromotionsV2");
            getGKAdsParam = CommonFunctions.encryptAES256CBC(getGKAdsKey, String.valueOf(paramJson));


            Call<GenericResponse> call = RetrofitBuilder.getWhatsNewV2API(getViewContext())
                    .getGKPromotionsV2(
                            getGKAdsAuthID,sessionid,getGKAdsParam
                    );

            call.enqueue(getGKPromotionsV2Callback);

        }else{
            srl_generic.setEnabled(true);
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private Callback<GenericResponse> getGKPromotionsV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            ResponseBody errBody = response.errorBody();
            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(getGKAdsKey,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(getGKAdsKey,response.body().getData());
                    Logger.debug("okhttp","GETGKADS ::::::"+decryptedData);
                    if(decryptedData.equals("error") || decryptedMessage.equals("error")){
                        showErrorGlobalDialogs("Something went wrong. Please try again.");
                    }else {
                        List<GKAds> gkAds = new Gson().fromJson(decryptedData, new TypeToken<List<GKAds>>(){}.getType());
                        if (POS == 0) {
                            CacheManager.getInstance().saveNewUpdates(gkAds);
                            mNewUpdatesAdapter.update(CacheManager.getInstance().getNewUpdates());
                        } else {
                            CacheManager.getInstance().savePromotions(gkAds);
                            mPromotionsAdapter.update(CacheManager.getInstance().getPromotions());
                        }
                        if (gkAds.size() > 0) {
                            mEmptyLayout.setVisibility(View.GONE);
                            rv_generic.setVisibility(View.VISIBLE);
                        } else {
                            mEmptyLayout.setVisibility(View.VISIBLE);
                            rv_generic.setVisibility(View.GONE);
                        }
                    }
                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else if(response.body().getStatus().equals("005")){
                    showErrorToast(decryptedMessage);
                }else if(response.body().getStatus().equals("500")){
                    showErrorGlobalDialogs();
                }
            }
            srl_generic.setEnabled(true);
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            t.printStackTrace();
            showErrorToast();
            srl_generic.setEnabled(true);
        }
    };
}
