package com.goodkredit.myapplication.fragments.gkstore;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.delivery.SetupDeliveryAddressActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.CustomMapFragment;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.android.gms.common.api.Status;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GKStoreSetUpMapLocationFragment extends BaseFragment implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, CustomMapFragment.OnTouchListener, GoogleMap.OnMapLongClickListener {

    private LinearLayout hintlayout;
    private LinearLayout hintsetlayout;
    private TextView txvaddress;
    private Button btnsetaddress;

    private GPSTracker gpsTracker;
    private Geocoder geocoder;
    private GoogleMap mMap;

    private final int marker_height = 60;
    private final int marker_width = 40;

    //Subscriber Marker
    private Marker subsMarker;
    private List<Address> currentsubsAddresslist;
    private Address subsmarkerAddress;

    //Merchant/Store Marker
    private Marker mercMarker;
    private List<Address> currentmercAddresslist;
    private Address mercmarkerAddress;

    private String intentType = "";

    //Subscriber Long Lat
    double checksubslatitude = 0.00;
    double checksubslongitude = 0.00;

    //Passed LatLongs of Merchant/Store
    private double checkstorelatitude = 0.00;
    private double checkstorelongitude = 0.00;
    private double checkstoreradius = 0.00;
    private Circle circle;

    private MaterialDialog mDialog = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;

        try {
            view = inflater.inflate(R.layout.fragment_select_map_location, container, false);

            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
            returnstoMainPageIfError();
        }
        return view;
    }

    private void init(View view) {
//        intentType = getArguments().getString("intenttype");
        currentsubsAddresslist = new ArrayList<>();
        hintlayout = (LinearLayout) view.findViewById(R.id.hintlayout);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 5, 0, 0);

        TextView gkstoremarkerhint = new TextView(getViewContext());
        gkstoremarkerhint.setText("Note: The goodkredit marker indicates the range the store can deliver your goods.");
        gkstoremarkerhint.setTextSize(14);
        gkstoremarkerhint.setPadding(0, 0, 0, 0);
        gkstoremarkerhint.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        gkstoremarkerhint.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
        gkstoremarkerhint.setLayoutParams(layoutParams);
        gkstoremarkerhint.setTextColor(getResources().getColor(R.color.colorwhite));
        hintlayout.addView(gkstoremarkerhint, layoutParams);

        hintlayout.setVisibility(View.VISIBLE);
        hintsetlayout = (LinearLayout) view.findViewById(R.id.hintsetlayout);
        txvaddress = (TextView) view.findViewById(R.id.txvaddress);
        btnsetaddress = (Button) view.findViewById(R.id.btnsetaddress);
        btnsetaddress.setOnClickListener(this);
    }

    private void initData() {
        ((SetupDeliveryAddressActivity) getViewContext()).setActionBarTitle("Add Address");

        if (!Places.isInitialized()) {
            Places.initialize(getViewContext(), CommonVariables.placesAPIKey);
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setCountry("PH");

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG,
                Place.Field.ADDRESS));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                // TODO: Get info about the selected place.
                hintlayout.setVisibility(View.GONE);
                hintsetlayout.setVisibility(View.VISIBLE);
                txvaddress.setText(place.getAddress());
                setPlaceMarker(place);
            }

            @Override
            public void onError(@NotNull Status status) {
                // TODO: Handle the error.
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isAdded()) {
                    setUpMapView();
                }

            }
        }, 400);
    }

    private void setUpMapView() {
        try {
            //=============================================================
            // SET UP MAPS
            // * if precise address, append code .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
            // * country to PH
            //=============================================================
            gpsTracker = new GPSTracker(getViewContext());
            geocoder = new Geocoder(getViewContext());
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            if (mMap == null) {
                CustomMapFragment mapFragment = (CustomMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.setListener(this);
                mapFragment.getMapAsync(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnstoMainPageIfError();
        }
    }

    private Bitmap resizeBitmapImage(int drawable, int height, int width) {

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(drawable);
        Bitmap b = bitmapdraw.getBitmap();

        return Bitmap.createScaledBitmap(b, width, height, false);
    }

    //RETURNS TO THE PREVIOUS PAGE IF AN ERROR HAS OCCURED WHEN CREATING THE VIEW
    private void returnstoMainPageIfError() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .content("Something went wrong. Please input your location manually or try again.")
                .cancelable(false)
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("MapsError", true);
                        getActivity().setResult(Activity.RESULT_CANCELED, returnIntent);
                        getActivity().finish();
                    }
                })

                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsetaddress: {
                if (subsmarkerAddress != null) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("MarkerAddress", subsmarkerAddress);
                    getActivity().setResult(Activity.RESULT_OK, returnIntent);
                    getActivity().finish();

                } else {
                    showError("No Latitude and Longitude is detected. Please check your Network and GPS if enabled or restart your phone.");
                }

                break;
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
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

            String strchecksubslatitude = getArguments().getString("latitude");
            String strchecksubslongitude = getArguments().getString("longitude");
            String storelat = getArguments().getString("merclatitude");
            String storelong = getArguments().getString("merclongitude");
            String radius = getArguments().getString("mercradius");

            strchecksubslatitude = longlatLimiter(strchecksubslatitude);
            strchecksubslongitude = longlatLimiter(strchecksubslongitude);

            if (!strchecksubslatitude.equals("") && !strchecksubslatitude.equals(".") &&
                    !strchecksubslongitude.equals("") && !strchecksubslongitude.equals(".")) {

                checksubslatitude = Double.parseDouble(strchecksubslatitude);
                checksubslongitude = Double.parseDouble(strchecksubslongitude);

                //sets the marker address for the subs
                setSubsMarkerAddress(checksubslatitude, checksubslongitude);
                subsMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(checksubslatitude, checksubslongitude))
                        .title(getSubsMarkerAddress(subsmarkerAddress))
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_maps_pin_subscriber, marker_height, marker_width))));
                mMap.setOnMyLocationButtonClickListener(this);
                mMap.setOnMapLongClickListener(this);

            }

            if (!storelat.equals("") && !storelat.equals(".") && !storelat.equals("NONE") &&
                    !storelong.equals("") && !storelong.equals(".") && !storelong.equals("NONE") &&
                    !radius.equals("") && !radius.equals(".") && !radius.equals("NONE")) {

                checkstorelatitude = Double.parseDouble(storelat);
                checkstorelongitude = Double.parseDouble(storelong);
                checkstoreradius = Double.parseDouble(radius);


                setMercMarkerAddress(checkstorelatitude, checkstorelongitude);

                mercMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(checkstorelatitude, checkstorelongitude))
                        .title(getMercMarkerAddress(mercmarkerAddress))
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_maps_pin_merchants, marker_height, marker_width))));

                //Convert the passed km to meters
                double converttometers = checkstoreradius * 1000;

                circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(checkstorelatitude, checkstorelongitude))
                        .radius(converttometers)
                        .strokeWidth(10)
                        .strokeColor(Color.argb(100, 255, 0, 0))
                        .fillColor(Color.argb(50, 255, 0, 0))
                        .clickable(true));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(checkstorelatitude, checkstorelongitude), 12));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(checkstorelatitude, checkstorelongitude), 15.0f));
            }

        } catch (Exception e) {
            e.printStackTrace();
            returnstoMainPageIfError();
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        setSubsMarkerAddress(gpsTracker.getLatitude(), gpsTracker.getLongitude());

        if (subsMarker == null) {
            subsMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()))
                    .title(getSubsMarkerAddress(subsmarkerAddress))
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_maps_pin_subscriber, marker_height, marker_width))));
        } else {
            subsMarker.setPosition(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
        }


        hintlayout.setVisibility(View.GONE);
        hintsetlayout.setVisibility(View.VISIBLE);
        txvaddress.setText(CommonFunctions.replaceEscapeData(getSubsMarkerAddress(subsmarkerAddress)));

        return false;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //sets the marker address
        setSubsMarkerAddress(latLng.latitude, latLng.longitude);

        subsMarker.setTitle(getSubsMarkerAddress(subsmarkerAddress));

        if (subsMarker == null) {
            // Marker was not set yet. Add marker:
            subsMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(getSubsMarkerAddress(subsmarkerAddress))
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_maps_pin_subscriber, marker_height, marker_width))));
        } else {
            subsMarker.setPosition(latLng);
            subsMarker.setTitle(getSubsMarkerAddress(subsmarkerAddress));
        }


        hintlayout.setVisibility(View.GONE);
        hintsetlayout.setVisibility(View.VISIBLE);
        txvaddress.setText(getSubsMarkerAddress(subsmarkerAddress));
    }

    public void setPlaceMarker(Place place) {
        if (subsMarker == null) {
            // Marker was not set yet. Add marker:
            subsMarker = mMap.addMarker(new MarkerOptions()
                    .position(Objects.requireNonNull(place.getLatLng()))
                    .title(String.valueOf(place.getAddress()))
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_maps_pin_subscriber, marker_height, marker_width))));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
        } else {
            subsMarker.setTitle(String.valueOf(place.getAddress()));
            subsMarker.setPosition(Objects.requireNonNull(place.getLatLng()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
        }

        LatLng latLng = place.getLatLng();
        try {
            currentsubsAddresslist = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (currentsubsAddresslist.size() > 0) {
            subsmarkerAddress = currentsubsAddresslist.get(0);
        }
    }

    //For subscriber setting its Marker
    private void setSubsMarkerAddress(double latitude, double longitude) {
        try {
            currentsubsAddresslist = geocoder.getFromLocation(latitude, longitude, 1);
            if (currentsubsAddresslist != null) {
                if (currentsubsAddresslist.size() > 0) {
                    subsmarkerAddress = currentsubsAddresslist.get(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //Getting the Marker Address for Subscriber
    private String getSubsMarkerAddress(Address address) {
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

    //For Merchant setting its Marker
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

    //Getting the Marker Address for Merchant
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

    private static String longlatLimiter(String number) {
        String pattern = "#.#######";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        return decimalFormat.format(Double.parseDouble(number));
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onTouch() {

    }


}
