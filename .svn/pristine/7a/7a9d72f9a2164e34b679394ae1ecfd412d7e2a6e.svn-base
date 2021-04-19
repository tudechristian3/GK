package com.goodkredit.myapplication.fragments.sponsors;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.profile.SubscriberProfileActivity;
import com.goodkredit.myapplication.adapter.skycable.SkycablePayPerViewRecylerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Sponsor;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.CustomMapFragment;
import com.goodkredit.myapplication.utilities.Logger;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 1/17/2017.
 */

public class SponsorDetails extends BaseActivity implements View.OnClickListener, OnMapReadyCallback, CustomMapFragment.OnTouchListener {

    private Toolbar toolbar;

    private ScrollView spscrollview;
    private TextView name;
    private TextView address;
    private TextView email;
    private TextView telephone;
    private TextView mobile;
    private TextView about;
    private ImageView sponsorlogo;
    //private TextView website;
    private Sponsor sponsor;
  //  private TextView sponsorpromo;
    private RecyclerView horizontal_recycler_view;

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

    private OptionAdapter optionAdapter;
    private ArrayList<String> optionlist;


    private CommonFunctions cf;
    private DatabaseHandler db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_details);
        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            optionlist = new ArrayList<>();
            optionlist.add("WEBSITE");
            optionlist.add("|   PROMOTIONS   |");
            optionlist.add("TERMS AND CONDITIONS");


            db = new DatabaseHandler(this);
            spscrollview = (ScrollView) findViewById(R.id.spscrollview);
            name = (TextView) findViewById(R.id.sponsorDetailsName);
            address = (TextView) findViewById(R.id.sponsorAddress);
            telephone = (TextView) findViewById(R.id.sponsorDetailsTelephone);
            mobile = (TextView) findViewById(R.id.sponsorDetailsMobile);
            email = (TextView) findViewById(R.id.sponsorDetailsEmail);
            sponsorlogo = (ImageView) findViewById(R.id.guarantorlogo);
            about = (TextView) findViewById(R.id.aboutusval);
            // website =  (TextView) findViewById(R.id.viewwebsite);
            //  sponsorpromo =  (TextView) findViewById(R.id.viewpromotions);
            horizontal_recycler_view = (RecyclerView) findViewById(R.id.horizontal_recycler_view);



            //Initialize Recycler view
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
            optionAdapter = new OptionAdapter(optionlist);
            horizontal_recycler_view.setAdapter(optionAdapter);

           // website.setOnClickListener(this);
         //   sponsorpromo.setOnClickListener(this);

            sponsor = (Sponsor) getIntent().getSerializableExtra("SPONSOR_OBJECT");

            name.setText("(" + sponsor.getSponsorID() + ")\n" + cf.replaceEscapeData(sponsor.getSponsorName()));
            String addressval = CommonFunctions.replaceEscapeData(sponsor.getStreet()) + ", " + CommonFunctions.replaceEscapeData(sponsor.getCity()) + ", " + CommonFunctions.replaceEscapeData(sponsor.getProvince()) + ", " + sponsor.getCountry();
            address.setText(cf.replaceEscapeData(addressval));
            email.setText(sponsor.getEmail());
            telephone.setText(sponsor.getTelephoneNumber());
            mobile.setText("+" + sponsor.getMobileNo());
            //about.setText(CommonFunctions.replaceEscapeData(sponsor.getSponsorDescription()));
            final String desc = CommonFunctions.replaceEscapeData(sponsor.getSponsorDescription());
            about.setText(CommonFunctions.setTypeFace(this, "fonts/Roboto-Light.ttf", desc));


            about.post(new Runnable() {
                @Override
                public void run() {

                    int line = about.getLineCount();
                    if (line >= 3) {
                        makeTextViewResizable(getViewContext(), about, 3, ".. See More", true);
                    }

                }
            });

            Glide.with(this)
                    .asBitmap()
                    .load(sponsor.getSponsorLogo())
                    .apply(new RequestOptions()
                            .fitCenter())
                    .into(new BaseTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            Logger.debug("ANN", "SPONSORLOGO" + resource);
                            sponsorlogo.setImageBitmap(resource);
                        }

                        @Override
                        public void getSize(SizeReadyCallback cb) {
                            cb.onSizeReady(CommonFunctions.getScreenWidthPixel(getViewContext()), 150);
                        }

                        @Override
                        public void removeCallback(SizeReadyCallback cb) {

                        }
                    });

            if (sponsor.getEmail().equals(".") || sponsor.getEmail().equals("")) {
                email.setVisibility(View.GONE);
            } else {
                email.setVisibility(View.VISIBLE);
            }

            if (sponsor.getTelephoneNumber().equals(".") || sponsor.getTelephoneNumber().equals("")) {
                telephone.setVisibility(View.GONE);
            } else {
                telephone.setVisibility(View.VISIBLE);
            }

            if (sponsor.getMobileNo().equals(".") || sponsor.getMobileNo().equals("")) {
                mobile.setVisibility(View.GONE);
            } else {
                mobile.setVisibility(View.VISIBLE);
            }

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    setUpMapView();

                }
            }, 400);





        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.viewwebsite: {
//                if(sponsor.getWebsitelink()==null|| sponsor.getWebsitelink().equals("") || sponsor.getWebsitelink().equals("NONE")){
//                    showToast("This sponsor has no website.Please contact sponsor.");
//
//                }else if(sponsor.getWebsitelink().contains("http")){
//                    openWebsite(sponsor.getWebsitelink());
//                }else{
//                    showToast("Sponsor website does not exist.  Please contact sponsor.");
//
//                }
//
//                break;
//            }
//            case R.id.viewpromotions: {
//                openPublicSponsorPromotions();
//                break;
//            }


 //       }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


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

    public void RegisterToSponsor(View view) {
        try {

            if (isRegisterToSponsor() == true) {
                showToast("You already enrolled to a sponsor or you have pending request.", GlobalToastEnum.NOTICE);
            } else {
                //CHECK IF
                Intent intent = new Intent(this, SubscriberProfileActivity.class);
                intent.putExtra("GUARANTORID", sponsor.getSponsorID());
                intent.putExtra("GUARANTORNAME", sponsor.getSponsorName());
                intent.putExtra("GUARANTOREMAIL", sponsor.getEmail());
                intent.putExtra("FROM", "SPONSORDETAILS");
                intent.putExtra("PREVACTIVITY", "SPONSORDETAILS");
                intent.putExtra("ISSUBGUARANTOR", "0");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        } catch (Exception e) {
        }


    }

    private boolean isRegisterToSponsor() {
        //get all local data
        boolean result = false;

        String status = db.hasGuarantorStatus();

        if (status.equals("PENDING") || status.equals("APPROVED")) {

            result = true;
        } else {

            result = false;
        }

        return result;

    }

    private void openWebsite(String url) {
        Intent intent = null;
        try {
            // get the Twitter app if possible

            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {

        }
        this.startActivity(intent);
    }

    private void openPublicSponsorPromotions() {
        //CHECK IF
        Intent intent = new Intent(this, PublicPonsorPromos.class);
        intent.putExtra("SPONSOR_OBJECT", sponsor);

        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

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

    public void setActionBarTitle(String title) {
        setTitle(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", title));
    }

    @Override
    public void onTouch() {
        spscrollview.requestDisallowInterceptTouchEvent(true);
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

            ssb.setSpan(new SkycablePayPerViewRecylerAdapter.MySpannable(false) {
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


    public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.MyViewHolder> {

        private List<String> horizontalList;
        private int mPosition = 0;
        private Voucher mVoucher;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView txtView;


            public MyViewHolder(View view) {
                super(view);
                txtView = (TextView) view.findViewById(R.id.options);

            }
        }


        public OptionAdapter(List<String> horizontalList) {
            this.horizontalList = horizontalList;
        }

        public int getClickedPosition() {
            return mPosition;
        }

        private void setClickedPosition(int pos) {
            mPosition = pos;
        }

        private void setData(List<String> horizontalList) {
            this.horizontalList = horizontalList;
        }

        public List<String> getCurrentData(int currentPos) {
            horizontalList.remove(currentPos);
            notifyDataSetChanged();
            return horizontalList;
        }


        @Override
        public OptionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sponsoroptions, parent, false);

            return new OptionAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final OptionAdapter.MyViewHolder holder, final int position) {

            try {
                String option = optionlist.get(position);
                holder.txtView.setText(option);

                holder.txtView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = optionlist.get(position);

                        if(title.contains("WEBSITE")){
                            if (sponsor.getWebsitelink() == null || sponsor.getWebsitelink().equals("") || sponsor.getWebsitelink().equals("NONE")) {
                                showToast("This sponsor has no website.Please contact sponsor.", GlobalToastEnum.WARNING);

                            } else if (sponsor.getWebsitelink().contains("http")) {
                                openWebsite(sponsor.getWebsitelink());
                            } else {
                                showToast("Sponsor website does not exist.  Please contact sponsor.", GlobalToastEnum.WARNING);

                            }
                        }else if(title.contains("PROMOTIONS")){
                            openPublicSponsorPromotions();
                        }else if(title.contains("TERMS AND CONDITIONS")){
                            Intent intent = new Intent(getViewContext(), SponsorTermsAndConditions.class);
                            intent.putExtra("SPONSOR_OBJECT", sponsor);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                        }


                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }
}
