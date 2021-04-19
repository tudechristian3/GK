package com.goodkredit.myapplication.activities.merchants;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.OutletsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Merchant;
import com.goodkredit.myapplication.bean.Outlets;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.CreateSession;
import com.goodkredit.myapplication.model.v2Models.OutletsV2;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.view.WorkaroundMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 2/21/2017.
 */

public class OutletsActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ScrollView mScrollView;

    private RecyclerView recyclerView;
    private OutletsAdapter adapter;
    private ArrayList<Outlets> arrayList;

    private Toolbar toolbar;

    private DatabaseHandler db;

    private Merchant merchant;

    private double lat;
    private double lon;

    private String sessionidval;
    private String imei;
    private String userid;
    private String borroweridval;
    private String guarantoridval;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //NEW VARIABLE FOR SECURITY
    private String authenticationid;
    private String param;
    private String keyEncryption;
    private String index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_outlets);
        try {
            setupToolbar();
            db = new DatabaseHandler(this);
            merchant = (Merchant) getIntent().getSerializableExtra("MERCHANT_OBJECT");

            if (mMap == null) {
                WorkaroundMapFragment mapFragment = (WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);


                mScrollView = (ScrollView) findViewById(R.id.scrollViewMapsOutlet); //parent scrollview in xml, give your scrollview id value


                ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                        .setListener(new WorkaroundMapFragment.OnTouchListener() {
                            @Override
                            public void onTouch() {
                                mScrollView.requestDisallowInterceptTouchEvent(true);
                            }
                        });
            }

            mLlLoader = (RelativeLayout) findViewById(R.id.loaderLayout);
            mTvFetching = (TextView) findViewById(R.id.fetching);
            mTvWait = (TextView) findViewById(R.id.wait);

            //get account information
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borroweridval = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    guarantoridval = cursor.getString(cursor.getColumnIndex("guarantorid"));
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                } while (cursor.moveToNext());
            }

            sessionidval = PreferenceUtils.getSessionID(getViewContext());

            GPSTracker tracker = new GPSTracker(this);
            if (tracker.canGetLocation()) {
                lat = tracker.getLatitude();
                lon = tracker.getLongitude();
            }

            recyclerView = (RecyclerView) findViewById(R.id.outlest_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(this, null));
            adapter = new OutletsAdapter(this, db.getOutlets(merchant.getMerchantID()));
            recyclerView.setAdapter(adapter);
            mScrollView.smoothScrollTo(0, 0);

            final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
            final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);

            final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(750);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    final float progress = (float) animation.getAnimatedValue();
                    final float width = backgroundOne.getWidth();
                    final float translationX = width * progress;
                    backgroundOne.setTranslationX(translationX);
                    backgroundTwo.setTranslationX(translationX - width);
                }
            });
            animator.start();

            mLoaderTimer = new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    mLlLoader.setVisibility(View.GONE);
                }
            };

            verifySession();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(Context context, Merchant merchant) {
        Intent intent = new Intent(context, OutletsActivity.class);
        intent.putExtra("MERCHANT_OBJECT", merchant);
        context.startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            int height = 90;
            int width = 60;
            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_maps_pin_merchants);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap merchantMarker = Bitmap.createScaledBitmap(b, width, height, false);

            BitmapDrawable bitmapdraw2 = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_maps_pin_subscriber);
            Bitmap b2 = bitmapdraw2.getBitmap();
            Bitmap subscriberMarker = Bitmap.createScaledBitmap(b2, width, height, false);

            mMap = googleMap;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 111);
            } else {
                mMap.setMyLocationEnabled(true);
            }
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            int size = db.getOutlets(merchant.getMerchantID()).size();
            if (size > 0) {
                for (int loop = 0; loop < size; loop++) {

                    Outlets outlet = db.getOutlets(merchant.getMerchantID()).get(loop);
                    if (!outlet.getOutletLatitude().equals(".")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(outlet.getOutletLatitude()),
                                Double.parseDouble(outlet.getOutletLongitude()))).title(outlet.getOutletBranchName())
                                .icon(BitmapDescriptorFactory.fromBitmap(merchantMarker)));
                    }
                }
            }

            mMap.addMarker(new
                    MarkerOptions().position(new LatLng(lat, lon))
                    .title("You're here.")
                    .icon(BitmapDescriptorFactory.fromBitmap(subscriberMarker))).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 14));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (android.R.id.home == item.getItemId()) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.updateData(db.getOutlets(merchant.getMerchantID()));
    }

    public void verifySession() {

        try {
            int status = CommonFunctions.getConnectivityStatus(getViewContext());
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {
                // CommonFunctions.showDialog(getViewContext(), "", "Fetching merchant outlets. Please wait ...", false);
                mTvFetching.setText("Fetching merchant branch details.");
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);

                mLoaderTimer.cancel();
                mLoaderTimer.start();

                //new HttpAsyncTask().execute(CommonVariables.GET_MERCHANT_BRANCHES);
                getMerchantBranchesV2();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionidval);
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("merchantid", merchant.getMerchantID());
                jsonObject.accumulate("sessionid", sessionidval);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("merchantgroup", merchant.getMerchantGroup());

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                CommonFunctions.hideDialog();
            }

            return CommonFunctions.POST(urls[0], json);
        }

        @Override
        protected void onPostExecute(String result) {
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            try {
                if (result == null) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("001")) {
                    showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    showToast("Invalid Session.", GlobalToastEnum.NOTICE);
                } else if (result.equals("003")) {
                    showToast("Invalid Guarantor ID.", GlobalToastEnum.WARNING);
                } else if (result.equals("error")) {
                    showToast("Cannot connect to server. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    processResult(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void processResult(String json) {

        ArrayList<OutletsV2> arrayList = new Gson().fromJson(json,new TypeToken<ArrayList<OutletsV2>>() {}.getType());
        if (arrayList.size() > 0) {
            db.deleteOutlets(db, merchant.getMerchantGroup());
            for(OutletsV2 outletsV2  : arrayList){
                db.insertOutlets(db, outletsV2);
            }
        }
        adapter.updateData(db.getOutlets(merchant.getMerchantGroup()));
    }

    @Override
    protected void onStop() {
        mLoaderTimer.cancel();
        super.onStop();
    }


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getMerchantBranchesV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("merchantid",merchant.getMerchantID());
            parameters.put("merchantgroup",merchant.getMerchantGroup());


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionidval);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionidval + "getMerchantBranchesV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getMerchantBranchesV2Object(getMerchantBranchesV2Callback);

        }else{
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }
    private void getMerchantBranchesV2Object(Callback<GenericResponse> getMerchantBranches){
        Call<GenericResponse> call = RetrofitBuilder.getMerchantV2API(getViewContext())
                .getMerchantBranchesV2(authenticationid,sessionidval,param);
        call.enqueue(getMerchantBranches);
    }
    private final Callback<GenericResponse> getMerchantBranchesV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();

            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);

            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData  =  CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    processResult(decryptedData);

                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }
                else {
                    showErrorGlobalDialogs(decryptedMessage);
                }
            }else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            showErrorToast("Something went wrong.Please try again. ");
        }
    };



}
