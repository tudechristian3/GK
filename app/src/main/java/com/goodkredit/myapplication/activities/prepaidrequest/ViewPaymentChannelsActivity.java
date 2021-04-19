package com.goodkredit.myapplication.activities.prepaidrequest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.prepaidrequest.BranchesRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Branches;
import com.goodkredit.myapplication.bean.PaymentChannels;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.CustomMapFragment;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GetPaymentPartnerBranchesResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPaymentChannelsActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, CustomMapFragment.OnTouchListener, OnMapReadyCallback {
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private ImageView imgLogoAccordion;
    private ImageView imgBranchesAccordion;

    private LinearLayout detailsChildLayout;
    private LinearLayout branchChildLayout;

    private BranchesRecyclerAdapter mAdapter;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private LinearLayout mainLayout;
    private LinearLayout branchesLayout;

    private String sessionid;
    private String imei;
    private String authcode;
    private String userid;
    private String partnerid;
    private int limit;

    private DatabaseHandler mdb;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private NestedScrollView nested_scroll;
    private boolean isloadmore;
    private boolean isbottomscroll;
    private boolean isfirstload = true;

    private GPSTracker gpsTracker;
    private Geocoder geocoder;
    private GoogleMap mMap;
    private double current_latitude;
    private double current_longitude;
    private final int marker_height = 60;
    private final int marker_width = 60;

    private Marker globalMarker;
    private List<Address> currentAddresslist;
    private Address markerAddress;

    private HashMap<Marker, Branches> branchMarker;

    private List<Branches> branchesList;

    private FrameLayout thumbnailLayout;
    private FrameLayout mapLayout;
    private ImageView thumbnail;
    private TextView txvHideShowMap;

    private ImageView imgPaymentChannelLogo;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    //private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payment_channels);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        setupToolbar();

        Bundle args = getIntent().getBundleExtra("args");
        PaymentChannels paymentChannels = args.getParcelable("channel");

        Logger.debug("okhttp", "paymentChannels " + new Gson().toJson(paymentChannels));

        //==============
        //SET TITLE
        //==============
        setTitle(paymentChannels.getNetworkName());

        init(paymentChannels);

        initData(paymentChannels);

    }

    private void setUpMapView() {
        //=============================================================
        // SET UP MAPS
        //
        //=============================================================
        gpsTracker = new GPSTracker(getViewContext());
        geocoder = new Geocoder(getViewContext());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (mMap == null) {
            CustomMapFragment mapFragment = (CustomMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.setListener(this);
            mapFragment.getMapAsync(this);

            mapFragment.setListener(new CustomMapFragment.OnTouchListener() {
                @Override
                public void onTouch() {
                    nested_scroll.requestDisallowInterceptTouchEvent(true);
                }
            });
        }

    }

    private void init(PaymentChannels paymentChannels) {

        //scrollView = (ScrollView) findViewById(R.id.scrollView);

        imgPaymentChannelLogo = findViewById(R.id.imgPaymentChannelLogo);

        Glide.with(getViewContext())
                .load(paymentChannels.getLogo())
                .into(imgPaymentChannelLogo);

        thumbnailLayout = findViewById(R.id.thumbnailLayout);
        mapLayout = findViewById(R.id.mapLayout);
        thumbnail = findViewById(R.id.thumbnail);

        txvHideShowMap = findViewById(R.id.txvHideShowMap);
        txvHideShowMap.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "FULL VIEW"));
        txvHideShowMap.setOnClickListener(this);

        nested_scroll = findViewById(R.id.nested_scroll);

        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mainLayout = findViewById(R.id.mainLayout);
        branchesLayout = findViewById(R.id.branchesLayout);

        mLlLoader = findViewById(R.id.loaderLayout);
        mTvFetching = findViewById(R.id.fetching);
        mTvWait = findViewById(R.id.wait);

        emptyLayout = findViewById(R.id.emptyLayout);
        textView11 = findViewById(R.id.textView11);
        textView11.setText("No Branches.");

        nointernetconnection = findViewById(R.id.nointernetconnection);
        refreshnointernet = findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        imgLogoAccordion = findViewById(R.id.imgLogoAccordion);
        imgBranchesAccordion = findViewById(R.id.imgBranchesAccordion);

        detailsChildLayout = findViewById(R.id.detailsChildLayout);
        branchChildLayout = findViewById(R.id.branchChildLayout);

        TextView txvBranchLabel = findViewById(R.id.txvBranchLabel);
        txvBranchLabel.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "Channel Name: "));
        TextView txvBranchName = findViewById(R.id.txvBranchName);
        txvBranchName.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", paymentChannels.getNetworkName()));

        TextView txvAddressLabel = findViewById(R.id.txvAddressLabel);
        txvAddressLabel.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "Address: "));
        TextView txvAddress = findViewById(R.id.txvAddress);
        String detailedaddress = paymentChannels.getStreetAddress() + ", " + paymentChannels.getCity() + ", " + paymentChannels.getProvince() + ", " + paymentChannels.getZip() + ", " + paymentChannels.getCountry();
        txvAddress.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", detailedaddress));

        TextView txvMobileNumberLabel = findViewById(R.id.txvMobileNumberLabel);
        txvMobileNumberLabel.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "Mobiler Number: "));
        TextView txvMobilerNumber = findViewById(R.id.txvMobilerNumber);
        txvMobilerNumber.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", paymentChannels.getAuthorisedMobileNo()));

        TextView txvBranchesLabel = findViewById(R.id.txvBranchesLabel);
        txvBranchesLabel.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "BRANCHES"));

        RecyclerView recycler_view_branches = findViewById(R.id.recycler_view_branches);
        recycler_view_branches.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recycler_view_branches.setNestedScrollingEnabled(false);
        mAdapter = new BranchesRecyclerAdapter(getViewContext());
        recycler_view_branches.setAdapter(mAdapter);

        findViewById(R.id.paymentChannelHeaderLayout).setOnClickListener(this);
        findViewById(R.id.branchesHeaderLayout).setOnClickListener(this);

        detailsChildLayout.setVisibility(View.VISIBLE);
        branchChildLayout.setVisibility(View.VISIBLE);
    }

    private void initData(PaymentChannels paymentChannels) {
        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        branchMarker = new HashMap<>();
        currentAddresslist = new ArrayList<>();
        branchesList = new ArrayList<>();
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        partnerid = paymentChannels.getNetworkID();
        branchesList = mdb.getPaymentBranches(mdb, partnerid);
        limit = getLimit(branchesList.size(), 30);
        isbottomscroll = false;
        isloadmore = true;


        gpsTracker = new GPSTracker(getViewContext());

        //GET CURRENT LATITUDE AND LONGITUDE
        current_latitude = gpsTracker.getLatitude();
        current_longitude = gpsTracker.getLongitude();

        if (mdb.isPaymentBranchExist(mdb, partnerid)) {
            updateList(branchesList);
        } else {
            getSession();
        }

        nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    isbottomscroll = true;
                    if (isloadmore) {
                        if (!isfirstload) {
                            limit = limit + 30;
                        }
                        getSession();
                    }
                }
            }
        });

        try {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //===========================================
                    //check & execute if activity is still active
                    //===========================================
                    setUpMapView();
                }
            }, 400);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectBranchMarker(final Branches item) {

        thumbnailLayout.setVisibility(View.GONE);
        mapLayout.setVisibility(View.VISIBLE);
        txvHideShowMap.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "HIDE MAP VIEW"));

        //scrollView.fullScroll(ScrollView.FOCUS_UP);
        nested_scroll.fullScroll(ScrollView.FOCUS_UP);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mMap != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(item.getLatitude()), Double.valueOf(item.getLongitude())), 15));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(item.getLatitude()), Double.valueOf(item.getLongitude()))));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }, 400);
    }

    private Marker placeMarker(Branches item, int height, int width) {
        Marker m = null;
        try {
            if (mMap != null) {
                m = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.valueOf(item.getLatitude()), Double.valueOf(item.getLongitude())))
                        .title(item.getOutletName())
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_blue_marker, height, width))));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return m;
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Fetching branches.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getPaymentPartnerBranches(getPaymentPartnerBranchesSession);
            getPaymentPartnerBranchesV2();

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getPaymentPartnerBranches(Callback<GetPaymentPartnerBranchesResponse> getPaymentPartnerBranchesCallback) {
        Call<GetPaymentPartnerBranchesResponse> getpaymentpartnerbranches = RetrofitBuild.getPaymentPartnerBranchesService(getViewContext())
                .getPaymentPartnerBranchesCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        String.valueOf(limit),
                        borrowerid,
                        partnerid);
        getpaymentpartnerbranches.enqueue(getPaymentPartnerBranchesCallback);
    }

    private final Callback<GetPaymentPartnerBranchesResponse> getPaymentPartnerBranchesSession = new Callback<GetPaymentPartnerBranchesResponse>() {

        @Override
        public void onResponse(Call<GetPaymentPartnerBranchesResponse> call, Response<GetPaymentPartnerBranchesResponse> response) {
            try {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                isfirstload = false;

                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        isloadmore = response.body().getPaymentBranches().size() > 0;
                        if (!isbottomscroll) {
                            mdb.truncateTable(mdb, DatabaseHandler.PAYMENT_BRANCHES);
                        }

                        List<Branches> listBranches = response.body().getPaymentBranches();
                        if (listBranches.size() > 0) {

                            String urlImage = "https://maps.googleapis.com/maps/api/staticmap?center=+"
                                    + current_latitude + "," + current_longitude +
                                    "&markers=color:red|" + current_latitude + "," + current_longitude;

                            for (Branches branches : listBranches) {

                                if (branches != null) {

                                    urlImage = urlImage.concat("&markers=color:blue|" + branches.getLatitude() + "," + branches.getLongitude());

                                    //add markers on branches
                                    branchMarker.put(placeMarker(branches, 60, 60), branches);
                                }

                                //insert data to db
                                mdb.insertPaymentBranches(mdb, branches, getDistance(current_latitude, current_longitude, branches));
                            }

                            urlImage = urlImage.concat("&zoom=14&size=600x300");

                            Glide.with(getViewContext())
                                    .load(urlImage)
                                    .into(thumbnail);

                        }

                        updateList(mdb.getPaymentBranches(mdb, partnerid));

                    } else {
                        showError(response.body().getMessage());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<GetPaymentPartnerBranchesResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void updateList(List<Branches> data) {
        showNoInternetConnection(false);
        if (data.size() > 0) {
            branchesLayout.setVisibility(View.VISIBLE);
            if (mAdapter != null) {
                mAdapter.setPaymentBranches(data);
            }
        } else {
            branchesLayout.setVisibility(View.GONE);
            if (mAdapter != null) {
                mAdapter.clear();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, ViewPaymentChannelsActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.paymentChannelHeaderLayout: {
                if (detailsChildLayout.getVisibility() == View.VISIBLE) {
                    detailsChildLayout.setVisibility(View.GONE);
                    imgLogoAccordion.setRotation(180);
                } else {
                    detailsChildLayout.setVisibility(View.VISIBLE);
                    imgLogoAccordion.setRotation(360);
                }
                break;
            }
            case R.id.branchesHeaderLayout: {
                if (branchChildLayout.getVisibility() == View.VISIBLE) {
                    branchChildLayout.setVisibility(View.GONE);
                    imgBranchesAccordion.setRotation(180);
                } else {
                    branchChildLayout.setVisibility(View.VISIBLE);
                    imgBranchesAccordion.setRotation(360);
                }
                break;
            }
            case R.id.txvHideShowMap: {
                if (txvHideShowMap.getText().toString().equals("FULL VIEW")) {
                    thumbnailLayout.setVisibility(View.GONE);
                    mapLayout.setVisibility(View.VISIBLE);
                    txvHideShowMap.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "HIDE MAP VIEW"));
                } else {
                    thumbnailLayout.setVisibility(View.VISIBLE);
                    mapLayout.setVisibility(View.GONE);
                    txvHideShowMap.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "FULL VIEW"));
                }

                break;
            }
            case R.id.refreshnointernet: {
                getSession();
                break;
            }
        }
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    private float getDistance(double latitude, double longitude, Branches item) {
        float distance;
        Location startpoint = new Location("startpoint");
        startpoint.setLatitude(latitude);
        startpoint.setLongitude(longitude);

        try {
            Location endpoint = new Location("newlocation");
            endpoint.setLatitude(Double.valueOf(item.getLatitude()));
            endpoint.setLongitude(Double.valueOf(item.getLongitude()));
            distance = startpoint.distanceTo(endpoint) / 1000;
        } catch (NumberFormatException e) {
            distance = 0;
            e.printStackTrace();
        }

        return distance;
    }

    @Override
    public void onRefresh() {
        if (mdb != null) {
            if (mAdapter != null) {
                mdb.truncateTable(mdb, DatabaseHandler.PAYMENT_BRANCHES);
                mAdapter.clear();
            }
        }

        isfirstload = false;
        isbottomscroll = false;
        isloadmore = true;

        emptyLayout.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);

        detailsChildLayout.setVisibility(View.VISIBLE);
        imgLogoAccordion.setRotation(360);

        branchChildLayout.setVisibility(View.VISIBLE);
        imgBranchesAccordion.setRotation(360);

        limit = 0;
        getSession();
    }

    @Override
    public void onTouch() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            mMap.setMyLocationEnabled(true);
        }

        //sets the marker address
        setMarkerAddress(gpsTracker.getLatitude(), gpsTracker.getLongitude());

        //SET MARKER CURRENT LOCATION
        globalMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()))
                .title("You are here")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_red_marker, marker_height, marker_width))));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), 15));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), 12.0f));

        String urlImage = "https://maps.googleapis.com/maps/api/staticmap?center=+"
                + current_latitude + "," + current_longitude +
                "&markers=color:red|" + current_latitude + "," + current_longitude;

        if (branchesList.size() > 0) {
            for (int i = 0; i < branchesList.size(); i++) {

                urlImage = urlImage.concat("&markers=color:blue|" + branchesList.get(i).getLatitude() + "," + branchesList.get(i).getLongitude());

                //add markers on branches
                branchMarker.put(placeMarker(branchesList.get(i), 60, 60), branchesList.get(i));
            }
        }

        urlImage = urlImage.concat("&zoom=14&size=600x300");

        Glide.with(getViewContext())
                .load(urlImage)
                .into(thumbnail);

        //mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getViewContext(), R.raw.))

    }

    private Bitmap resizeBitmapImage(int drawable, int height, int width) {

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(drawable);
        Bitmap b = bitmapdraw.getBitmap();

        return Bitmap.createScaledBitmap(b, width, height, false);
    }

    private void setMarkerAddress(double latitude, double longitude) {
        try {
            currentAddresslist = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (currentAddresslist != null) {
            if (currentAddresslist.size() > 0) {
                markerAddress = currentAddresslist.get(0);
            }
        }
    }


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getPaymentPartnerBranchesV2(){

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("limit",  String.valueOf(limit));
        parameters.put("partnerid", partnerid);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getPaymentPartnerBranchesV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        getPaymentPartnerBranchesV2Object(getPaymentPartnerBranchesV2Session);

    }

    private void getPaymentPartnerBranchesV2Object(Callback<GenericResponse> partnerBranchesCallback){
        Call<GenericResponse> partnerBranches = RetrofitBuilder.getPaymentV2API(getViewContext())
                .getPaymentPartnerBranchesV2(authenticationid, sessionid, param);
        partnerBranches.enqueue(partnerBranchesCallback);
    }

    private final Callback<GenericResponse> getPaymentPartnerBranchesV2Session = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                isfirstload = false;

                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                        List<Branches> listBranches =  new Gson().fromJson(data,new TypeToken<List<Branches>>(){}.getType());

                        isloadmore = listBranches.size() > 0;
                        if (!isbottomscroll) {
                            mdb.truncateTable(mdb, DatabaseHandler.PAYMENT_BRANCHES);
                        }

                        if (listBranches.size() > 0) {

                            String urlImage = "https://maps.googleapis.com/maps/api/staticmap?center=+"
                                    + current_latitude + "," + current_longitude +
                                    "&markers=color:red|" + current_latitude + "," + current_longitude;

                            for (Branches branches : listBranches) {

                                if (branches != null) {

                                    urlImage = urlImage.concat("&markers=color:blue|" + branches.getLatitude() + "," + branches.getLongitude());

                                    //add markers on branches
                                    branchMarker.put(placeMarker(branches, 60, 60), branches);
                                }

                                //insert data to db
                                assert branches != null;
                                mdb.insertPaymentBranches(mdb, branches, getDistance(current_latitude, current_longitude, branches));
                            }

                            urlImage = urlImage.concat("&zoom=14&size=600x300");

                            Glide.with(getViewContext())
                                    .load(urlImage)
                                    .into(thumbnail);

                        }
                        updateList(mdb.getPaymentBranches(mdb, partnerid));

                    } else if(response.body().getStatus().equals("003")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
                        showError(message);
                    }
                }else{
                    showError();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };



}
