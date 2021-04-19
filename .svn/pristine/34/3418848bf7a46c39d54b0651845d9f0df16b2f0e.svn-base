package com.goodkredit.myapplication.activities.merchants;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.BranchesAdapter;
import com.goodkredit.myapplication.adapter.ProductionAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Merchant;
import com.goodkredit.myapplication.bean.Outlets;
import com.goodkredit.myapplication.bean.PaymentChannels;
import com.goodkredit.myapplication.bean.Promotions;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.v2Models.OutletsV2;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.view.WorkaroundMapFragment;
import com.goodkredit.myapplication.responses.GetMerchantPromosResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
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
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androidquery.util.AQUtility.getHandler;


public class MerchantDetailsActivity extends BaseActivity implements OnMapReadyCallback, SwipeRefreshLayout.OnRefreshListener {

    private Merchant merchant;

    private AQuery aq;

    private DatabaseHandler db;

    private String headermerchantname = "";

    private String headermerchantlogo = "";

    private ImageView mImageView;

    private LinearLayout merchantdescheader;
    private LinearLayout branchesdescheader;
    private LinearLayout productdescheader;

    private TextView desmerchantname;
    private TextView desaddress;
    private TextView descity;
    private TextView despronvince;
    private TextView deszip;
    private TextView descountry;
    private TextView desemail;
    private TextView desnatureofbusiness;
    private TextView desnoofbranches;
    private TextView mobileno;
    private TextView emptypromo;
    private TextView emptybranch;

    private String merchantnamestr;
    private String addressstr;
    private String citystr;
    private String pronvincestr;
    private String zipstr;
    private String countrystr;
    private String emailstr;
    private String natureofbusinessstr;
    private String noofbranchesstr;

    private ImageView merchantdropbtn;
    private ImageView branchesdropbtn;
    private ImageView productdropbtn;

    private GoogleMap mMap;

    private double currentlat;
    private double currentlon;

    private NestedScrollView mScrollView;

    private Button buttonmapviewchanges;

    private int pressedbuttonmapviewchanges;

    private LinearLayout branchesdesbody;


    private RecyclerView recyclerView;
    private BranchesAdapter adapter;

    private String sessionidval;
    private String imei;
    private String userid;
    private String borroweridval;
    private String authcode;


    private LinearLayout merchantheader;
    private LinearLayout branchesheader;
    private LinearLayout productheader;

    private String merchantinitials = "";
    private TextView noLogoPicview;


    private ImageView staticmap;
    private LinearLayout staticmapcontainer;
    private LinearLayout mapcontainer;

    private RecyclerView productrecyclerView;
    private ProductionAdapter productadapter;

    private String merchantid;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private SwipeRefreshLayout swipeRefreshLayout;

    //UNIFIED SESSION
    private String sessionid = "";


    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String promoIndex;
    private String promoAuthenticationid;
    private String promoKeyEncryption;
    private String promoParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_details);

        Toolbar merchantDetailsToolbar = findViewById(R.id.merchantDetailsToolbar);
        setSupportActionBar(merchantDetailsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        try {
            aq = new AQuery(this);

            merchant = (Merchant) getIntent().getSerializableExtra("MERCHANT_OBJECT");

            db = new DatabaseHandler(getViewContext());

            //UNIFIED SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            mScrollView = findViewById(R.id.scrollmaincontainer);

            swipeRefreshLayout = findViewById(R.id.swipe_container);
            swipeRefreshLayout.setOnRefreshListener(this);

            merchantdescheader = findViewById(R.id.merchantdescheader);
            branchesdescheader = findViewById(R.id.branchesdescheader);
            productdescheader = findViewById(R.id.productdescheader);
            branchesdesbody = findViewById(R.id.branchesdesbody);

            emptypromo = findViewById(R.id.emptypromo);
            emptybranch = findViewById(R.id.emptybranch);

            mLlLoader = findViewById(R.id.loaderLayout);
            mTvFetching = findViewById(R.id.fetching);
            mTvWait = findViewById(R.id.wait);


            imei = PreferenceUtils.getImeiId(getViewContext());
            userid = PreferenceUtils.getUserId(getViewContext());
            merchantid = merchant.getMerchantID();
            borroweridval = PreferenceUtils.getBorrowerId(getViewContext());

            if (mMap == null) {
                WorkaroundMapFragment mapFragment =
                        (WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);


                ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                        .setListener(new WorkaroundMapFragment.OnTouchListener() {
                            @Override
                            public void onTouch() {
                                mScrollView.requestDisallowInterceptTouchEvent(true);
                            }
                        });
            }

            GPSTracker tracker = new GPSTracker(this);
            tracker = new GPSTracker(getViewContext());

            if (!tracker.canGetLocation()) {

            } else {
                currentlat = tracker.getLatitude();
                currentlon = tracker.getLongitude();
            }

            recyclerView = findViewById(R.id.branches_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null));
//            adapter = new BranchesAdapter(getViewContext(), db.getOutlets(merchant.getMerchantID()));
            adapter = new BranchesAdapter(getViewContext(), db.getOutlets(merchant.getMerchantGroup()));
            recyclerView.setAdapter(adapter);


            productrecyclerView = findViewById(R.id.product_list);
            productrecyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
            productrecyclerView.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null));
            productadapter = new ProductionAdapter(getViewContext(), db.getPromotion(db, merchant.getMerchantID()));
            productrecyclerView.setAdapter(productadapter);

            gettingMarketName();

            gettingMarketLogo();

            gettingMarketDetails();

            gettingStaticMap();

            listenerButtonFunction();

            scrollonTop();

            final ImageView backgroundOne = findViewById(R.id.background_one);
            final ImageView backgroundTwo = findViewById(R.id.background_two);

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
        Intent intent = new Intent(context, MerchantDetailsActivity.class);
        intent.putExtra("MERCHANT_OBJECT", merchant);
        context.startActivity(intent);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void gettingMarketName() {
        headermerchantname = merchant.getMerchantName();

        getSupportActionBar().setTitle("" + headermerchantname);
    }

    private void gettingMarketLogo() {

        headermerchantlogo = merchant.getMerchantLogo();
        merchantinitials = merchant.getMerchatInitials();

        mImageView = findViewById(R.id.mLogoPic);
        noLogoPicview = findViewById(R.id.noLogoPic);

        if (headermerchantlogo.contentEquals(".") || headermerchantlogo.contentEquals("")) {
            mImageView.setVisibility(View.GONE);
            noLogoPicview.setVisibility(View.VISIBLE);
            noLogoPicview.setText(merchantinitials);
        } else {
            mImageView.setVisibility(View.VISIBLE);
            noLogoPicview.setVisibility(View.GONE);
            Glide.with(getViewContext())
                    .load(headermerchantlogo)
                    .into(mImageView);
        }
    }

    private void gettingMarketDetails() {

        desmerchantname = findViewById(R.id.desmerchantname);
        desaddress = findViewById(R.id.desaddress);
        descity = findViewById(R.id.descity);
        despronvince = findViewById(R.id.despronvince);
        deszip = findViewById(R.id.deszip);
        descountry = findViewById(R.id.descountry);
        desemail = findViewById(R.id.desemail);
        desnatureofbusiness = findViewById(R.id.desnatureofbusiness);
        desnoofbranches = findViewById(R.id.desnoofbranches);
        mobileno = findViewById(R.id.mobilenoval);


        merchantnamestr = merchant.getMerchantName();
        addressstr = merchant.getStreetAddress();
        citystr = merchant.getCity();
        pronvincestr = merchant.getProvince();
        zipstr = merchant.getZip();
        countrystr = merchant.getCountry();
        emailstr = merchant.getEmail();
        natureofbusinessstr = merchant.getNatureofBusiness();
        noofbranchesstr = merchant.getNumOfOutlets();
        String mobilenostr = merchant.getMobile();


        if (zipstr.equals("") || zipstr.equals(".")) {
            zipstr = "N/A";
        }
        if (countrystr.equals("") || countrystr.equals(".")) {
            countrystr = "N/A";
        }
        if (emailstr.equals("") || emailstr.equals(".")) {
            emailstr = "N/A";
        }
        if (natureofbusinessstr.equals("") || natureofbusinessstr.equals(".")) {
            natureofbusinessstr = "N/A";
        }

        desmerchantname.setText(CommonFunctions.replaceEscapeData(merchantnamestr));
        desaddress.setText(CommonFunctions.replaceEscapeData(addressstr));
        descity.setText(CommonFunctions.replaceEscapeData(citystr));
        despronvince.setText(CommonFunctions.replaceEscapeData(pronvincestr));
        deszip.setText(zipstr);
        descountry.setText(countrystr);
        desemail.setText(emailstr);
        desnatureofbusiness.setText(natureofbusinessstr);
        desnoofbranches.setText(noofbranchesstr);
        mobileno.setText("+" + mobilenostr);
    }

    private void listenerButtonFunction() {

        merchantheader = findViewById(R.id.merchantheader);
        merchantdropbtn = findViewById(R.id.merchantdropbtn);
        merchantheader.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (merchantdescheader.getVisibility() == View.GONE) {
                    merchantdescheader.setVisibility(View.VISIBLE);
                    scrolltoPositionLayout(merchantheader);
                } else {
                    merchantdescheader.setVisibility(View.GONE);
                }
                merchantdropbtn.setRotation(merchantdropbtn.getRotation() + 180);

            }
        });

        merchantheader.performClick();

        branchesheader = findViewById(R.id.branchesheader);
        branchesdropbtn = findViewById(R.id.branchesdropbtn);

        branchesheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (branchesdescheader.getVisibility() == View.GONE) {
                    branchesdescheader.setVisibility(View.VISIBLE);
                    scrolltoPositionLayout(branchesheader);
                } else {
                    branchesdescheader.setVisibility(View.GONE);
                }

                branchesdropbtn.setRotation(branchesdropbtn.getRotation() + 180);
            }
        });

        branchesheader.performClick();
        productheader = findViewById(R.id.productheader);
        productdropbtn = findViewById(R.id.productdropbtn);
        productheader.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (productdescheader.getVisibility() == View.GONE) {
                    productdescheader.setVisibility(View.VISIBLE);
                    scrolltoPositionLayout(productheader);
                } else {
                    productdescheader.setVisibility(View.GONE);
                }
                productdropbtn.setRotation(productdropbtn.getRotation() + 180);

            }
        });

        productheader.performClick();
        buttonmapviewchanges = findViewById(R.id.buttonmapviewchanges);
        buttonmapviewchanges.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (buttonmapviewchanges.isPressed()) {
                    staticmapcontainer = findViewById(R.id.staticmapcontainer);
                    mapcontainer = findViewById(R.id.mapcontainer);

                    if (pressedbuttonmapviewchanges == 0) {
                        buttonmapviewchanges.setText("HIDE MAP VIEW");
                        staticmapcontainer.setVisibility(View.GONE);
                        mapcontainer.setVisibility(View.VISIBLE);

                        pressedbuttonmapviewchanges = 1;

                    } else {
                        buttonmapviewchanges.setText("FULL MAP VIEW");
                        staticmapcontainer.setVisibility(View.VISIBLE);
                        mapcontainer.setVisibility(View.GONE);
                        pressedbuttonmapviewchanges = 0;
                    }
                }
            }
        });
    }

    private void gettingStaticMap() {
        staticmap = findViewById(R.id.staticmap);
        String urlcontainer = "https://maps.googleapis.com/maps" +
                "/api/staticmap?center=+" + currentlat + "," + currentlon + "&zoom=10&size=600x300";

        Glide.with(getViewContext())
                .load(urlcontainer)
                .into(staticmap);
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

//            int size = db.getOutlets(merchant.getMerchantID()).size();
            int size = db.getOutlets(merchant.getMerchantGroup()).size();

            if (size > 0) {
                for (int loop = 0; loop < size; loop++) {
//                    Outlets outlet = db.getOutlets(merchant.getMerchantID()).get(loop);
                    Outlets outlet = db.getOutlets(merchant.getMerchantGroup()).get(loop);
                    if (!outlet.getOutletLatitude().equals(".")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(outlet.getOutletLatitude()),
                                Double.parseDouble(outlet.getOutletLongitude()))).title(outlet.getOutletBranchName())
                                .icon(BitmapDescriptorFactory.fromBitmap(merchantMarker)));
                    }
                }
            }

            mMap.addMarker(new
                    MarkerOptions().position(new LatLng(currentlat, currentlon))
                    .title("You're here.")
                    .icon(BitmapDescriptorFactory.fromBitmap(subscriberMarker))).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentlat, currentlon), 14));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
//            adapter.updateData(db.getOutlets(merchant.getMerchantID()));
            adapter.updateData(db.getOutlets(merchant.getMerchantGroup()));
        productadapter.setPromotions(db.getPromotion(db, merchant.getMerchantID()));
    }

    public void verifySession() {
        try {
            int status = CommonFunctions.getConnectivityStatus(getViewContext());
            if (status == 0) {
                swipeRefreshLayout.setRefreshing(false);
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {
                mTvFetching.setText("Fetching merchant details.");
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);

                mLoaderTimer.cancel();
                mLoaderTimer.start();

                CommonFunctions.hideDialog();
                //new HttpAsyncTask().execute(CommonVariables.GET_MERCHANT_BRANCHES);
                getMerchantBranchesV2();
                //getMerchantPromos(getMerchantPromosSession);
                getMerchantPromosV2();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        try {
            swipeRefreshLayout.setRefreshing(true);
            verifySession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("StaticFieldLeak")
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
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("merchantid", merchant.getMerchantID());
                jsonObject.accumulate("sessionid", sessionid);
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
                    swipeRefreshLayout.setRefreshing(false);
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("001")) {
                    swipeRefreshLayout.setRefreshing(false);
                    showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    swipeRefreshLayout.setRefreshing(false);
                    showToast("Invalid Session.", GlobalToastEnum.NOTICE);
                } else if (result.equals("003")) {
                    swipeRefreshLayout.setRefreshing(false);
                    showToast("Invalid Guarantor ID.", GlobalToastEnum.NOTICE);
                } else if (result.equals("error")) {
                    swipeRefreshLayout.setRefreshing(false);
                    showToast("Cannot connect to server. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    swipeRefreshLayout.setRefreshing(false);
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
        mLlLoader.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);

        ArrayList<OutletsV2> arrayList = new Gson().fromJson(json, new TypeToken<ArrayList<OutletsV2>>() {}.getType());
        if (arrayList.size() > 0) {
            emptybranch.setVisibility(View.GONE);
            db.deleteOutlets(db, merchant.getMerchantGroup());

            for (OutletsV2 outletsV2 : arrayList) {
                db.insertOutlets(db, outletsV2);
            }

            if (mMap == null) {
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                int height = 90;
                int width = 60;
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_maps_pin_merchants);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap merchantMarker = Bitmap.createScaledBitmap(b, width, height, false);

                int size = db.getOutlets(merchant.getMerchantGroup()).size();

                if (size > 0) {
                    for (int loop = 0; loop < size; loop++) {
                        Outlets outlet = db.getOutlets(merchant.getMerchantGroup()).get(loop);
                        if (!outlet.getOutletLatitude().equals(".")) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(outlet.getOutletLatitude()),
                                    Double.parseDouble(outlet.getOutletLongitude()))).title(outlet.getOutletBranchName())
                                    .icon(BitmapDescriptorFactory.fromBitmap(merchantMarker)));
                        }
                    }
                }
            }
        } else {
            emptybranch.setVisibility(View.VISIBLE);
        }
        adapter.updateData(db.getOutlets(merchant.getMerchantGroup()));
    }

    private void scrollonTop() {
        final NestedScrollView scrollmaincontainer = findViewById(R.id.scrollmaincontainer);
        scrollmaincontainer.post(new Runnable() {
            public void run() {
                scrollmaincontainer.smoothScrollTo(0, 0);
            }
        });
    }

    public void scrolltoPositionLayout(final LinearLayout layoutpassed) {

        final NestedScrollView scrollmaincontainer = findViewById(R.id.scrollmaincontainer);

        getHandler().post(new Runnable() {
            @Override
            public void run() {
                scrollmaincontainer.smoothScrollTo(0, layoutpassed.getTop());
            }
        });
    }


    private void getMerchantPromos(Callback<GetMerchantPromosResponse> getMerchantPromosCallback) {
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
        Call<GetMerchantPromosResponse> getmerchantpromo = RetrofitBuild.getMerchantPromoService(getViewContext())
                .getMerchantPromosCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borroweridval,
                        merchantid);
        getmerchantpromo.enqueue(getMerchantPromosCallback);
    }

    private final Callback<GetMerchantPromosResponse> getMerchantPromosSession = new Callback<GetMerchantPromosResponse>() {

        @Override
        public void onResponse(Call<GetMerchantPromosResponse> call, Response<GetMerchantPromosResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    db.deletePromotion(db, merchantid);
                    List<Promotions> promotionsList = response.body().getPromotions();
                    for (Promotions promotions : promotionsList) {
                        db.insertPromotion(db, promotions);

                    }
                    if (db.getPromotion(db, merchantid).size() > 0) {
                        productadapter.setPromotions(db.getPromotion(db, merchantid));
                        emptypromo.setVisibility(View.GONE);
                    } else {
                        emptypromo.setVisibility(View.VISIBLE);
                    }

                } else {
                    //showError(response.body().getMessage());
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            }

        }

        @Override
        public void onFailure(Call<GetMerchantPromosResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    @Override
    protected void onStop() {
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
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getMerchantBranchesV2");
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
                .getMerchantBranchesV2(authenticationid,sessionid,param);
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
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            showErrorToast("Something went wrong.Please try again. ");
        }
    };

    //Get Merchant Promos
    private void getMerchantPromosV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borroweridval);
            parameters.put("merchantid",merchantid);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            promoIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", promoIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            promoAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            promoKeyEncryption = CommonFunctions.getSha1Hex(promoAuthenticationid + sessionid + "getMerchantPromosV2");
            promoParam = CommonFunctions.encryptAES256CBC(promoKeyEncryption, String.valueOf(paramJson));

            getMerchantPromosV2Object(getMerchantPromosV2Session);

        }else{
            showNoInternetToast();
        }

    }

    private void getMerchantPromosV2Object(Callback<GenericResponse> getMerchantPromosCallback) {
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
        Call<GenericResponse> getmerchantpromo = RetrofitBuilder.getMerchantV2API(getViewContext())
                .getMerchantPromosV2(promoAuthenticationid,sessionid,promoParam);
        getmerchantpromo.enqueue(getMerchantPromosCallback);
    }

    private final Callback<GenericResponse> getMerchantPromosV2Session = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    db.deletePromotion(db, merchantid);
                    String data = CommonFunctions.decryptAES256CBC(promoKeyEncryption,response.body().getData());
                    List<Promotions> promotionsList = new Gson().fromJson(data, new TypeToken<List<Promotions>>(){}.getType());
                    for (Promotions promotions : promotionsList) {
                        db.insertPromotion(db, promotions);
                    }
                    if (db.getPromotion(db, merchantid).size() > 0) {
                        productadapter.setPromotions(db.getPromotion(db, merchantid));
                        emptypromo.setVisibility(View.GONE);
                    } else {
                        emptypromo.setVisibility(View.VISIBLE);
                    }

                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };
}
