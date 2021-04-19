package com.goodkredit.myapplication.activities.publicsponsor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.CustomMapFragment;
import com.goodkredit.myapplication.fragments.sponsors.SponsorTermsAndConditions;
import com.goodkredit.myapplication.model.V2SubscriberDetails;
import com.goodkredit.myapplication.model.publicsponsor.PublicSponsor;
import com.goodkredit.myapplication.model.publicsponsor.VerifySponsorData;
import com.goodkredit.myapplication.responses.profile.GetSubscriberDetailsResponse;
import com.goodkredit.myapplication.responses.publicsponsor.VerifySponsorCodeV2Response;
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

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicSponsorDetailsActivity extends BaseActivity implements View.OnClickListener, CustomMapFragment.OnTouchListener, OnMapReadyCallback {

    private PublicSponsor sponsor = null;
    private VerifySponsorData verifysponsordata = null;

    private ScrollView spscrollview;

    private ImageView img_sponsorlogo;
    private TextView txv_sponsorname;
    private TextView txv_sponsoraddress;
    private TextView txv_sponsoremail;
    private TextView txv_sponsortel;
    private TextView txv_sponsormobile;
    private TextView txv_aboutus;

    private TextView txv_option_website;
    private TextView txv_option_promotions;
    private TextView txv_option_terms;

    private Button btn_apply;

    private GPSTracker gpsTracker;
    private Geocoder geocoder;
    private GoogleMap mMap;

    //    Passed LatLongs of Merchant/Store
    private double checkstorelatitude = 0.00;
    private double checkstorelongitude = 0.00;

    //Merchant/Store Marker
    private Marker mercMarker;
    private List<Address> currentmercAddresslist;
    private Address mercmarkerAddress;
    private final int marker_height = 60;
    private final int marker_width = 40;

    private DatabaseHandler mdb;

    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;
    private String sponsorcode;
    private String devicetype;

    private V2SubscriberDetails subDetails;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_sponsor_detailsv2);

        init();
        initData();
    }

    private void init() {
        setupToolbar();

        spscrollview = (ScrollView) findViewById(R.id.spscrollview);
        img_sponsorlogo = (ImageView) findViewById(R.id.img_sponsorlogo);
        btn_apply = (Button) findViewById(R.id.btn_apply);

        txv_sponsorname = (TextView) findViewById(R.id.txv_sponsorname);
        txv_sponsoraddress = (TextView) findViewById(R.id.txv_sponsoraddress);
        txv_sponsoremail = (TextView) findViewById(R.id.txv_sponsoremail);
        txv_sponsortel = (TextView) findViewById(R.id.txv_sponsortel);
        txv_sponsormobile = (TextView) findViewById(R.id.txv_sponsormobile);

        txv_aboutus = (TextView) findViewById(R.id.aboutusval);

        txv_option_website = (TextView) findViewById(R.id.txv_option_website);
        txv_option_promotions = (TextView) findViewById(R.id.txv_option_promotions);
        txv_option_terms = (TextView) findViewById(R.id.txv_option_terms);

        btn_apply.setOnClickListener(this);
    }

    private void initData() {
        sponsor = getIntent().getParcelableExtra(PublicSponsor.KEY_PUBLICSPONSOR);

        mdb = new DatabaseHandler(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sponsorcode = sponsor.getGuarantorID();
        devicetype = "ANDROID";

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        try{
            String sponsorlogo = CommonFunctions.parseXML(sponsor.getNotes1(), "logo");
            String sponsoraddress = CommonFunctions.replaceEscapeData(sponsor.getStreetAddress().toUpperCase()
                    .concat(", ").concat(sponsor.getCity().toUpperCase()).concat(", ").concat(sponsor.getCountry().toUpperCase()));

            if(sponsorlogo == null || sponsorlogo.equals("") || sponsorlogo.equals("NONE")){
                Glide.with(getViewContext())
                        .load(R.drawable.emptylogdefault)
                        .into(img_sponsorlogo);
            } else{
                Glide.with(getViewContext())
                        .load(CommonFunctions.parseXML(sponsor.getNotes1(), "logo"))
                        .into(img_sponsorlogo);
            }

            if(sponsor.getAuthorisedTelNo().equals("") || sponsor.getAuthorisedTelNo().equals(".")){
                txv_sponsortel.setVisibility(View.GONE);
            } else{
                txv_sponsortel.setVisibility(View.VISIBLE);
                txv_sponsortel.setText(sponsor.getAuthorisedTelNo());
            }
            if(sponsor.getAuthorisedMobileNo().equals("") || sponsor.getAuthorisedMobileNo().equals(".")){
                txv_sponsormobile.setVisibility(View.GONE);
            } else{
                txv_sponsormobile.setVisibility(View.VISIBLE);
                txv_sponsormobile.setText("+".concat(sponsor.getAuthorisedMobileNo()));
            }

            txv_sponsorname.setText(CommonFunctions.replaceEscapeData(sponsor.getGuarantorName()));
            txv_sponsoraddress.setText(sponsoraddress);
            txv_sponsoremail.setText(sponsor.getAuthorisedEmailAddress());
            txv_aboutus.setText(CommonFunctions.replaceEscapeData(CommonFunctions.parseXML(sponsor.getNotes1(), "about")));

            txv_option_website.setOnClickListener(this);
            txv_option_promotions.setOnClickListener(this);
            txv_option_terms.setOnClickListener(this);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    setUpMapView();

                }
            }, 400);

            txv_aboutus.post(new Runnable() {
                @Override
                public void run() {

                    int line = txv_aboutus.getLineCount();
                    if (line >= 3) {
                        makeTextViewResizable(getViewContext(), txv_aboutus, 3, ".. See More", true);
                    }

                }
            });

            getSession();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void makeTextViewResizable(final Context mContext, final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(mContext, CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(mContext, CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(mContext, CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);

//                    tv.setText(CommonFunctions.setTypeFace(mContext, "fonts/robotolight.ttf", tv.getTag().toString()));
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Context mContext, final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {

            ssb.setSpan(new MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(mContext, tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(mContext, tv, 3, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    public static class MySpannable extends ClickableSpan {

        private boolean isUnderline = true;

        /**
         * Constructor
         */
        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(isUnderline);
            ds.setColor(Color.parseColor("#1b76d3"));
        }

        @Override
        public void onClick(View widget) {


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txv_option_website: {
                String website = CommonFunctions.parseXML(sponsor.getNotes1(), "website");

                if(website == null || website.equals("") || website.equals("NONE")){
                    showToast("This sponsor has no website.Please contact sponsor.", GlobalToastEnum.WARNING);
                } else if(website.contains("http")){
                    openWebsite(website);
                } else{
                    showToast("Sponsor website does not exist.  Please contact sponsor.", GlobalToastEnum.WARNING);
                }

                break;
            }
            case R.id.txv_option_promotions: {
                openPublicSponsorPromotions();
                break;
            }
            case R.id.txv_option_terms: {
                openPublicSponsorTerms();
                break;
            }
            case R.id.btn_apply: {
                getSession();
                if (!SClick.check(SClick.BUTTON_CLICK, 500)) return;
                if(isRegisterToSponsor()){
                    showToast("You already enrolled to a sponsor or you have pending request.", GlobalToastEnum.NOTICE);
                } else{
                    verifySponsorSession();
                }

                break;
            }
        }
    }

    private boolean isRegisterToSponsor(){

        //get all local data
        boolean result = false;

        try{
//        String status = mdb.hasGuarantorStatus();
            String status = subDetails.getGuarantorApprovalStatus();

            if(status.equals(".") || status.equals("")){
                result = false;
            } else if(status.equals("PENDING") || status.equals("APPROVED")){
                result = true;
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    private void verifySponsor(){

        try{
            if(sponsor.getGuarantorID().equals(verifysponsordata.getGuarantorID())){
                String sponsorNotes1 = sponsor.getNotes1();
                String iscustomized = CommonFunctions.parseXML(sponsorNotes1, "iscustomized");

                if(!sponsorNotes1.equals("")){
                    if(sponsorNotes1.contains(iscustomized)){
                        if(iscustomized.trim().equals("YES")){
//                        Intent intent = new Intent(getViewContext(), PublicSponsorWebViewCostumizedActivity.class);
//                        intent.putExtra(PublicSponsor.KEY_PUBLICSPONSOR, sponsor);
//                        startActivity(intent);

                            String sponsorid = sponsor.getGuarantorID();
                            String borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
                            String mobileno = PreferenceUtils.getUserId(getViewContext());

                            String requestformurl = CommonFunctions.replaceEscapeData(CommonFunctions.parseXML(sponsor.getNotes1(), "requestformurl"));

                            String shaurl = CommonFunctions.getSha1Hex("60a54d645173cf7c2cd53a403cb717b7de449e72"
                                    + sponsorid + borrowerid + mobileno);
                            String newurl =  requestformurl + shaurl +"/" + sponsorid + "/"
                                    + borrowerid + "/" + mobileno;

                            if(newurl.equals("") || newurl.equals(".")){
                                showToast("Application to this sponsor is not available at the moment. Please contact support.", GlobalToastEnum.WARNING);
                            } else if(newurl.contains("http")){
                                openWebsite(newurl);
                            } else{
                                showToast("Application to this sponsor is not available at the moment. Please contact support.", GlobalToastEnum.WARNING);
                            }
                        } else if (iscustomized.trim().equals("NO")){

                            Intent intent = new Intent(getViewContext(), RegisterToPublicSponsorActivity.class);
                            intent.putExtra(PublicSponsor.KEY_PUBLICSPONSOR, sponsor);
                            intent.putExtra(V2SubscriberDetails.KEY_V2SUBSCRIBERDETAILS, subDetails);
                            intent.putExtra(VerifySponsorData.KEY_VERIFYSPONSORDATA, verifysponsordata);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    } else{

                        Intent intent = new Intent(getViewContext(), RegisterToPublicSponsorActivity.class);
                        intent.putExtra(PublicSponsor.KEY_PUBLICSPONSOR, sponsor);
                        intent.putExtra(V2SubscriberDetails.KEY_V2SUBSCRIBERDETAILS, subDetails);
                        intent.putExtra(VerifySponsorData.KEY_VERIFYSPONSORDATA, verifysponsordata);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }

                } else{

                    Intent intent = new Intent(getViewContext(), RegisterToPublicSponsorActivity.class);
                    intent.putExtra(PublicSponsor.KEY_PUBLICSPONSOR, sponsor);
                    intent.putExtra(V2SubscriberDetails.KEY_V2SUBSCRIBERDETAILS, subDetails);
                    intent.putExtra(VerifySponsorData.KEY_VERIFYSPONSORDATA, verifysponsordata);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            } else{
                showToast("Sponsor Code is invalid.", GlobalToastEnum.WARNING);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void verifySponsorSession() {
        //authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("sponsorcode",sponsorcode);
            parameters.put("devicetype",devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "verifySponsorCodeV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            verifySponsorCodeV2(verifySponsorCodeV2Session);

        }else{
            showNoInternetToast();
        }


    }

    private void verifySponsorCodeV2 (Callback<GenericResponse> verifySponsorCodeV2Callback) {
        Call<GenericResponse> verifySponsorCode = RetrofitBuilder.getPublicSponsorV2API(getViewContext())
                .verifySponsorCodeV2(authenticationid,sessionid,param);
        verifySponsorCode.enqueue(verifySponsorCodeV2Callback);
    }

    private final Callback<GenericResponse> verifySponsorCodeV2Session = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());

                if(response.body().getStatus().equals("000")){
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    verifysponsordata = new Gson().fromJson(decryptedData,VerifySponsorData.class);
                    verifySponsor();
                } else{
                    //showError(response.body().getMessage());
                    if(response.body().getStatus().equalsIgnoreCase("error")){
                        showError();
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else{
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            } else{
                //showError();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void openWebsite(String url){
        Intent intent = null;
        try{
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e){
            e.printStackTrace();
            e.getLocalizedMessage();
        }
        this.startActivity(intent);
    }

    private void openPublicSponsorPromotions(){
        //CHECK IF
        Intent intent = new Intent(this, V2PublicSponsorPromotionsActivity.class);
        intent.putExtra(PublicSponsor.KEY_PUBLICSPONSOR, sponsor);

        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    private void openPublicSponsorTerms(){
        Intent intent = new Intent(getViewContext(), SponsorTermsAndConditions.class);
        intent.putExtra(PublicSponsor.KEY_PUBLICSPONSOR, sponsor);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    private void getSession() {
//        Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                .getRegisteredSession(imei, userid);
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            call.enqueue(callsession);
            getSubscriberDetails(getSubscriberDetailsSession);

        } else {

            //showError(getString(R.string.generic_internet_error_message));
            showWarningToast("Seems you are not connected to the internet. Please check your connection and try again. Thank you.");
        }
    }

//    private Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    session = response.body().toString();
//                    getSubscriberDetails(getSubscriberDetailsSession);
//                }
//            } else {
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSubscriberDetails(Callback<GetSubscriberDetailsResponse> getSubscriberDetailsCallback) {
        Call<GetSubscriberDetailsResponse> getsubscriberdetails = RetrofitBuild.getSubscriberAPIService(getViewContext())
                .getSubscribersProfile(imei,
                        CommonFunctions.getSha1Hex(imei + userid),
                        userid,
                        borrowerid);

        getsubscriberdetails.enqueue(getSubscriberDetailsCallback);
    }

    private final Callback<GetSubscriberDetailsResponse> getSubscriberDetailsSession = new Callback<GetSubscriberDetailsResponse>() {
        @Override
        public void onResponse(Call<GetSubscriberDetailsResponse> call, Response<GetSubscriberDetailsResponse> response) {

            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {

                if (response.body().getStatus().equals("000")) {

                    subDetails = response.body().getSubscriberDetails();

                } else {

                    //showError(response.body().getMessage());
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                //showError();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GetSubscriberDetailsResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getViewContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        String storelat = sponsor.getLatitude();
        String storelong = sponsor.getLongitude();

        if (!storelat.equals("") && !storelat.equals(".") && !storelat.equals("NONE") &&
                !storelong.equals("") && !storelong.equals(".") && !storelong.equals("NONE")) {

            checkstorelatitude = Double.parseDouble(storelat);
            checkstorelongitude = Double.parseDouble(storelong);

            setMercMarkerAddress(checkstorelatitude, checkstorelongitude);

            mercMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(checkstorelatitude, checkstorelongitude))
                    .title(getMercMarkerAddress(mercmarkerAddress))
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_maps_pin_merchants, marker_height, marker_width))));


            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(checkstorelatitude, checkstorelongitude), 12));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(checkstorelatitude, checkstorelongitude), 15.0f));
        }

    }

    private void setMercMarkerAddress(double latitude, double longitude) {
        try {
            currentmercAddresslist = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (currentmercAddresslist != null) {
            if (currentmercAddresslist.size() > 0) {
                mercmarkerAddress = currentmercAddresslist.get(0);
            }
        }
    }

    private String getMercMarkerAddress(Address address) {
        String display_address = "";
        if (address != null) {

            display_address += address.getAddressLine(0) + ", ";

            for (int i = 1; i < address.getMaxAddressLineIndex(); i++) {
                display_address += address.getAddressLine(i) + ", ";
            }

            display_address = display_address.substring(0, display_address.length() - 2);
        }

        return display_address;
    }

    private Bitmap resizeBitmapImage(int drawable, int height, int width) {

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(drawable);
        Bitmap b = bitmapdraw.getBitmap();

        return Bitmap.createScaledBitmap(b, width, height, false);
    }

    private void setUpMapView() {
        //=============================================================
        // SET UP MAPS
        // * if precise address, append code .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
        // * country to PH
        //=============================================================
        gpsTracker = new GPSTracker(getViewContext());
        geocoder = new Geocoder(getViewContext());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (mMap == null) {
            CustomMapFragment mapFragment = (CustomMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.setListener(this);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onTouch() {
        spscrollview.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSession();
    }
}
