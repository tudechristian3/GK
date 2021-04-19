package com.goodkredit.myapplication.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.merchants.MerchantCategoryRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.Merchants;
import com.goodkredit.myapplication.bean.PaymentChannels;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.v2Models.MerchantObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User-PC on 11/13/2017.
 */

public class MerchantsCategoryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ViewPagerEx.OnPageChangeListener {

    private RecyclerView recycler_view_merchants_category;
    private MerchantCategoryRecyclerAdapter mAdapter;
    private DatabaseHandler mdb;

    private String borrowerid;
    private String userid;
    private String imei;
    private String sessionidval;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SliderLayout mSlider;

    private List<Merchants> mFeaturedMerchants;
    private List<Merchants> mAllMerchants;
    private List<Merchants> mNearestMerchants;
    private List<Merchants> mOrganizeMerchants;

    private double longitude;
    private double latitude;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_notification_0).setVisible(false);
        menu.findItem(R.id.action_notification_1).setVisible(false);
        menu.findItem(R.id.action_notification_2).setVisible(false);
        menu.findItem(R.id.action_notification_3).setVisible(false);
        menu.findItem(R.id.action_notification_4).setVisible(false);
        menu.findItem(R.id.action_notification_5).setVisible(false);
        menu.findItem(R.id.action_notification_6).setVisible(false);
        menu.findItem(R.id.action_notification_7).setVisible(false);
        menu.findItem(R.id.action_notification_8).setVisible(false);
        menu.findItem(R.id.action_notification_9).setVisible(false);
        menu.findItem(R.id.action_notification_9plus).setVisible(false);
        menu.findItem(R.id.action_process_queue).setVisible(false);
        menu.findItem(R.id.sortitem).setVisible(false);
        menu.findItem(R.id.summary).setVisible(false);
        menu.findItem(R.id.group_voucher).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchants_category, container, false);

        try {

            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        mFeaturedMerchants = new ArrayList<>();
        mAllMerchants = new ArrayList<>();
        mNearestMerchants = new ArrayList<>();
        mOrganizeMerchants = new ArrayList<>();

        mSlider = (SliderLayout) view.findViewById(R.id.sliderLayout);

        recycler_view_merchants_category = (RecyclerView) view.findViewById(R.id.recycler_view_merchants_category);
        recycler_view_merchants_category.setLayoutManager(new GridLayoutManager(getViewContext(), 3));
        recycler_view_merchants_category.setNestedScrollingEnabled(false);
        mAdapter = new MerchantCategoryRecyclerAdapter(getViewContext(), MerchantsCategoryFragment.this);
        recycler_view_merchants_category.setAdapter(mAdapter);

        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        GPSTracker gpsTracker = new GPSTracker(getContext());
        longitude = gpsTracker.getLongitude();
        latitude = gpsTracker.getLatitude();

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        updateListData();

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        if (CommonVariables.MERCHANTISFIRSTLOAD.equals("true")) {
            verifySession();
        }
    }

    private void setUpNearestMerchants() {

        if (!mAllMerchants.isEmpty()) {
            for (Merchants merchant : mAllMerchants) {
                if (!merchant.getLatitude().equals(".") &&
                        !merchant.getLongitude().equals(".")) {

                    double mLat = Double.parseDouble(merchant.getLatitude());
                    double mLon = Double.parseDouble(merchant.getLongitude());

                    double distance = (int) distance(latitude, longitude, mLat, mLon);
                    Logger.debug("OkHttp", "setUpNearestMerchants: " + String.valueOf(distance));
                    if (distance <= 2) {
                        mNearestMerchants.add(merchant);
                    }
                }
            }
        }
    }

    public double toRadians(double deg) {
        return deg * (Math.PI / 180);
    }

    //get distance between point in KM
    private double distance(double latitude, double longitude, double finalLatit, double finalLongit) {
        double d = 0;
        try {
            double R = 6371; // km
            double dlong = toRadians(finalLongit - longitude);
            double dlat = toRadians(finalLatit - latitude);

            double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                    Math.cos(toRadians(latitude)) * Math.cos(toRadians(finalLatit)) *
                            Math.sin(dlong / 2) * Math.sin(dlong / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            d = R * c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    private void verifySession() {

        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                mLoaderTimer.cancel();
                mLoaderTimer.start();
                mLlLoader.setVisibility(View.VISIBLE);
                mTvFetching.setText("Fetching merchants.");
                mTvWait.setText(" Please wait...");

                //new HttpAsyncTask().execute(CommonVariables.GETMERCHANTS);
                getMerchantsV3();;

            } else {
                swipeRefreshLayout.setRefreshing(false);
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        try {
            swipeRefreshLayout.setRefreshing(true);
            verifySession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            try {
                String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("result", 1);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                CommonFunctions.hideDialog();
            }

            return CommonFunctions.POST(urls[0], json);
        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Logger.debug("custom_check", "ressss " + result);
            mLlLoader.setVisibility(View.GONE);
            if (result == null) {
                swipeRefreshLayout.setRefreshing(false);
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else if (result.contains("<!DOCTYPE html>")) {
                swipeRefreshLayout.setRefreshing(false);
                showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                processList(result);
            }
        }
    }


    private void processList(String result) {

            swipeRefreshLayout.setRefreshing(false);
            mdb.deleteMerchants(mdb); //delete local data before inserting

             List<MerchantObject> merchants = new Gson().fromJson(result, new TypeToken<List<MerchantObject>>(){}.getType());

             for(MerchantObject merchantObject : merchants){
                 mdb.insertMerchants(mdb, merchantObject.getMerchantid(), merchantObject.getMerchantname(), merchantObject.getMerchantlogo(),
                         merchantObject.getMerchanttype(),merchantObject.getMerchantgroup(),
                         merchantObject.getMerchantstatus(), merchantObject.getStreetaddress(), merchantObject.getCity(),
                         merchantObject.getProvince(), merchantObject.getZip(), merchantObject.getCountry(),
                         merchantObject.getLongitude(), merchantObject.getLatitude(), merchantObject.getRepresentative(),
                         merchantObject.getAuthorizedemailaddress(), merchantObject.getAuthorisedtelno(), merchantObject.getAuthorisedmobileno(),
                         merchantObject.getFax(), merchantObject.getNatureofbusiness(), merchantObject.getTin(),
                         merchantObject.getOrganizationtype(), merchantObject.getNoofbranches(), merchantObject.getDateadded(),
                         merchantObject.getIsfeatured(),
                         merchantObject.getFeaturedaddspath(), merchantObject.getOrganizationlogo());
             }

            CommonVariables.MERCHANTISFIRSTLOAD = "false"; // make it false so that it wont fetch if not first load
            updateListData();

    }

    private void updateListData() {

        if (mdb != null) {

            mOrganizeMerchants.clear();

            //GET MERCHANTS BY TYPE
            mOrganizeMerchants = mdb.getOrganizationList(mdb);

            //GET ALL MERCHANTS
            mAllMerchants = mdb.getAllMerchants(mdb);

            //GET NEAREST MERCHANTS
            setUpNearestMerchants();

            mFeaturedMerchants.clear();
            mFeaturedMerchants = mdb.getFeaturedMerchants(mdb);

            if (!mFeaturedMerchants.isEmpty()) {
                mOrganizeMerchants.add(0, new Merchants("FEATURED"));
            }

            if (!mNearestMerchants.isEmpty()) {
                mOrganizeMerchants.add(0, new Merchants("NEAR"));
            }

            mOrganizeMerchants.add(0, new Merchants("ALL"));

            //SET UP SLIDER
            setUpSlider();

            mAdapter.clear();
            mAdapter.setMerchantCategoryData(mOrganizeMerchants);
        }

    }

    private void setUpSlider() {

        //======================
        //SET UP SLIDER
        //======================

        mSlider.removeAllSliders();

        DefaultSliderView defView = new DefaultSliderView(getViewContext());
        defView
                .image(R.drawable.gkadvert)
                .description("GoodKredit")
                .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
        mSlider.addSlider(defView);
        mSlider.stopAutoCycle();

        if (!mFeaturedMerchants.isEmpty()) {

            for (Merchants merchant : mFeaturedMerchants) {
                // initialize a SliderLayout
                DefaultSliderView defaultSliderView = new DefaultSliderView(getViewContext());
                defaultSliderView
                        .description(merchant.getMerchantName())
                        .image(merchant.getFeatureAddsPath())
                        .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
                mSlider.addSlider(defaultSliderView);
            }
        }

        mSlider.startAutoCycle();
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onStop() {
        if (mSlider != null)
            mSlider.stopAutoCycle();
        super.onStop();
    }

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * OCTOBER 2019    *
     * *****************/

    private void getMerchantsV3(){

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("limit", String.valueOf(1));

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getMerchantsV3");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getMerchantsV3Object(getMerchantsV3Callback);

        }else{
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }

    private void getMerchantsV3Object(Callback<GenericResponse> getMerchantCallback){
        Call<GenericResponse> call = RetrofitBuilder.getMerchantV2API(getViewContext())
                .getMerchantsV3(authenticationid,sessionid,param);
        call.enqueue(getMerchantCallback);
    }

    private final Callback<GenericResponse>getMerchantsV3Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData  =  CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    processList(decryptedData);
                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                    showErrorGlobalDialogs(decryptedMessage);
                }
            }else{
                swipeRefreshLayout.setRefreshing(false);
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            showErrorToast("Something went wrong.Please try again. ");
        }
    };

}
