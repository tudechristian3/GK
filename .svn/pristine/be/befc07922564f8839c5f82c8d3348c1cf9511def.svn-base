package com.goodkredit.myapplication.fragments.skycable;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.delivery.SetupDeliveryAddressActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.SkycableServiceArea;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.CustomMapFragment;
import com.goodkredit.myapplication.utilities.Logger;
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
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkycableMapLocationFragment extends BaseFragment implements View.OnClickListener, CustomMapFragment.OnTouchListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMapLongClickListener {

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

    private View view;

    private String intentType = "";

    //Subscriber Long Lat
    double checksubslatitude = 0.00;
    double checksubslongtitude = 0.00;

    //    Passed LatLongs of Merchant/Store
    private double checkstorelatitude = 0.00;
    private double checkstorelongtitude = 0.00;
    private double checkstoreradius = 0.00;
    private Circle circle;

    private String passLatitude = "";
    private String passLongitude = "";

    private TextView txvlblyourloc;

    private FrameLayout frameOverlayMapLayout;
    private FrameLayout framePlaceAutocompleteLayout;

    //INSTALLATION ADDRESS
    private ArrayList<SkycableServiceArea> skycableServiceAreas;
    private SkycableServiceArea skycableServiceAreaObj;

    private boolean isServiceArea;
    private boolean isShowMapDescription;

    private String placeAddress = "";
    private LatLng placeLatLng;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_map_location, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                getActivity().finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init(View view) {
        currentsubsAddresslist = new ArrayList<>();
        hintlayout = (LinearLayout) view.findViewById(R.id.hintlayout);
        hintlayout.setVisibility(View.VISIBLE);
        hintsetlayout = (LinearLayout) view.findViewById(R.id.hintsetlayout);
        txvaddress = (TextView) view.findViewById(R.id.txvaddress);
        btnsetaddress = (Button) view.findViewById(R.id.btnsetaddress);
        btnsetaddress.setOnClickListener(this);
        frameOverlayMapLayout = (FrameLayout) view.findViewById(R.id.frameOverlayMapLayout);
        framePlaceAutocompleteLayout = (FrameLayout) view.findViewById(R.id.framePlaceAutocompleteLayout);

        txvlblyourloc = (TextView) view.findViewById(R.id.lblyourloc);
        txvlblyourloc.setText("Your location is important so that we would know where to setup your SKYCABLE installation service.");
    }

    private void initData() {
        ((SetupDeliveryAddressActivity) getViewContext()).setActionBarTitle("Add Address");

        skycableServiceAreaObj = null;
        skycableServiceAreas = new ArrayList<>();
        isServiceArea = getArguments().getBoolean("isServiceArea", false);
        isShowMapDescription = getArguments().getBoolean("isShowMapDescription", false);

        if (isServiceArea) {
            skycableServiceAreas = getArguments().getParcelableArrayList("SKYCABLEAREALIST");
        }

        passLatitude = getArguments().getString("latitude");
        passLongitude = getArguments().getString("longitude");

        checksubslatitude = Double.valueOf(passLatitude);
        checksubslongtitude = Double.valueOf(passLongitude);

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
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                hintlayout.setVisibility(View.GONE);
                hintsetlayout.setVisibility(View.VISIBLE);
                txvaddress.setText(place.getAddress());
                setPlaceMarker(place);

                if (isServiceArea) {
                    if (circle != null) {
                        circle.remove();
                    }
                    //SET UP SERVICE AREA
                    setUpServiceArea(place);
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                if (isAdded()) {

                }
            }
        });

        AutocompleteSupportFragment autocompleteOverlayFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_overlay);

        autocompleteOverlayFragment.setCountry("PH");

        autocompleteOverlayFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG,
                Place.Field.ADDRESS));

        autocompleteOverlayFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                frameOverlayMapLayout.setVisibility(View.GONE);
                framePlaceAutocompleteLayout.setVisibility(View.VISIBLE);

                hintlayout.setVisibility(View.GONE);
                hintsetlayout.setVisibility(View.VISIBLE);
                txvaddress.setText(place.getAddress());
                setPlaceMarker(place);

                //SET UP SERVICE AREA
                setUpServiceArea(place);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                if (isAdded()) {

                }
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

    private void setUpServiceArea(Place place) {
        double latitude = 0;
        double longitude = 0;
        double radius = 0;

        SkycableServiceArea skycableServiceArea = null;
        LatLng latLng = place.getLatLng();

        double distance = getDistance(latLng.latitude, latLng.longitude, Double.valueOf(skycableServiceAreas.get(0).getLatitude()), Double.valueOf(skycableServiceAreas.get(0).getLongitude()));
        for (int i = 0; i < skycableServiceAreas.size(); i++) {

            double xdistance = getDistance(latLng.latitude, latLng.longitude, Double.valueOf(skycableServiceAreas.get(i).getLatitude()), Double.valueOf(skycableServiceAreas.get(i).getLongitude()));

            if (xdistance <= distance) {
                distance = xdistance;
                latitude = Double.valueOf(skycableServiceAreas.get(i).getLatitude());
                longitude = Double.valueOf(skycableServiceAreas.get(i).getLongitude());
                radius = Double.valueOf(skycableServiceAreas.get(i).getRadius());
                skycableServiceArea = skycableServiceAreas.get(i);
            }
        }

        skycableServiceAreaObj = skycableServiceArea;

        circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(radius)
                .strokeWidth(10)
                .strokeColor(Color.argb(100, 255, 0, 0))
                .fillColor(Color.argb(50, 255, 0, 0))
                .clickable(true));

    }

    public float getDistance(double latitude, double longitude, double xlatitude, double xlongitude) {
        float distance;
        Location startpoint = new Location("startpoint");
        startpoint.setLatitude(latitude);
        startpoint.setLongitude(longitude);

        try {
            Location endpoint = new Location("newlocation");
            endpoint.setLatitude(xlatitude);
            endpoint.setLongitude(xlongitude);
            distance = startpoint.distanceTo(endpoint) / 1000;
        } catch (NumberFormatException e) {
            distance = 0;
            e.printStackTrace();
        }

        return distance;
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
            CustomMapFragment mapFragment = (CustomMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.setListener(this);
            mapFragment.getMapAsync(this);
        }
    }

    public void setPlaceMarker(Place place) {
        if (subsMarker == null) {
            // Marker was not set yet. Add marker:
            subsMarker = mMap.addMarker(new MarkerOptions()
                    .position(place.getLatLng())
                    .title(String.valueOf(place.getAddress()))
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_maps_pin_subscriber, marker_height, marker_width))));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
        } else {
            subsMarker.setTitle(String.valueOf(place.getAddress()));
            subsMarker.setPosition(place.getLatLng());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
        }

        placeAddress = String.valueOf(place.getAddress());
        placeLatLng = place.getLatLng();

        LatLng latLng = place.getLatLng();
        try {
            currentsubsAddresslist = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (currentsubsAddresslist.size() > 0) {
            subsmarkerAddress = currentsubsAddresslist.get(0);
        }

        Logger.debug("antonhttp", "currentsubsAddresslist: " + new Gson().toJson(currentsubsAddresslist));
    }

    private Bitmap resizeBitmapImage(int drawable, int height, int width) {

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(drawable);
        Bitmap b = bitmapdraw.getBitmap();

        return Bitmap.createScaledBitmap(b, width, height, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsetaddress: {

                if (subsmarkerAddress != null) {

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("placeLatLng", placeLatLng);
                    returnIntent.putExtra("placeAddress", placeAddress);
                    returnIntent.putExtra("MarkerAddress", subsmarkerAddress);
                    returnIntent.putExtra("skycableServiceAreaObj", skycableServiceAreaObj);
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
    public void onTouch() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getViewContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getViewContext(),
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

        if (isShowMapDescription) {
            txvlblyourloc.setVisibility(View.VISIBLE);
            frameOverlayMapLayout.setVisibility(View.VISIBLE);
            framePlaceAutocompleteLayout.setVisibility(View.GONE);
        } else {
            txvlblyourloc.setVisibility(View.GONE);
            frameOverlayMapLayout.setVisibility(View.GONE);
            framePlaceAutocompleteLayout.setVisibility(View.VISIBLE);
        }

        //sets the marker address for the subs
        setSubsMarkerAddress(checksubslatitude, checksubslongtitude);
        subsMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(checksubslatitude, checksubslongtitude))
                .title(getSubsMarkerAddress(subsmarkerAddress))
                .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_maps_pin_subscriber, marker_height, marker_width))));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(checksubslatitude, checksubslongtitude), 15));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(checksubslatitude, checksubslongtitude), 12.0f));
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMapLongClickListener(this);

    }

    //For subscriber setting its Marker
    private void setSubsMarkerAddress(double latitude, double longitude) {
        try {
            currentsubsAddresslist = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (currentsubsAddresslist != null) {
            if (currentsubsAddresslist.size() > 0) {
                subsmarkerAddress = currentsubsAddresslist.get(0);
                placeAddress = getSubsMarkerAddress(subsmarkerAddress);
            }
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
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_maps_pin_subscriber, marker_height, marker_width))));
        } else {
            subsMarker.setPosition(latLng);
        }

        hintlayout.setVisibility(View.GONE);
        hintsetlayout.setVisibility(View.VISIBLE);
        txvaddress.setText(getSubsMarkerAddress(subsmarkerAddress));
    }
}
