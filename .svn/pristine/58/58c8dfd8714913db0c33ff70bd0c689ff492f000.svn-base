package com.goodkredit.myapplication.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoResellerLocationActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.CustomMapFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class ResellerLocationMapFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, View.OnClickListener {

    private View view;

    private LinearLayout hintlayout;
    private LinearLayout hintsetlayout;
    private TextView txvaddress;

    private Geocoder geocoder;
    private GoogleMap mMap;

    private final int marker_height = 60;
    private final int marker_width = 40;

    //Subscriber Marker
    private Marker subsMarker;
    private Address mAddress;

    private TextView lblyourloc;
    private TextView lbltosetyourloc;
    private TextView lblsearchforyour;
    private TextView lblpressandhold;

    private FusedLocationProviderClient mFusedLocationClient;
    private double gpsLong = 500;
    private double gpsLat = 500;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getViewContext());

        if (ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getViewContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                gpsLong = location.getLongitude();
                                gpsLat = location.getLatitude();
                            }
                        }
                    });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            if (view != null) {
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null)
                    parent.removeView(view);
            }
            view = inflater.inflate(R.layout.fragment_select_map_location, container, false);
            init(view);
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getViewContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMapLongClickListener(this);

        subsMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(gpsLat, gpsLong))
                .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_maps_pin_subscriber, marker_height, marker_width))));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsLat, gpsLong), 15.0f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsLat, gpsLong), 15.0f));
    }

    private void init(View view) {
        hintlayout = (LinearLayout) view.findViewById(R.id.hintlayout);
        hintsetlayout = (LinearLayout) view.findViewById(R.id.hintsetlayout);
        txvaddress = (TextView) view.findViewById(R.id.txvaddress);
        lblyourloc = (TextView) view.findViewById(R.id.lblyourloc);
        lbltosetyourloc = (TextView) view.findViewById(R.id.lbltosetyourloc);
        lblsearchforyour = (TextView) view.findViewById(R.id.lblsearchforyour);
        lblpressandhold = (TextView) view.findViewById(R.id.lblpressandhold);

        lblyourloc.setText("Your location is important so that we can recommend you a distributor.");
        lblyourloc.setVisibility(View.VISIBLE);
        hintlayout.setVisibility(View.VISIBLE);

        view.findViewById(R.id.btnsetaddress).setOnClickListener(this);
    }

    private void initData() {
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
                if (mMap != null) {
                    mMap.clear();
                    subsMarker = mMap.addMarker(new MarkerOptions()
                            .position(place.getLatLng())
                            .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_maps_pin_subscriber, marker_height, marker_width))));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15.0f));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15.0f));

                    if (getAddressFromLatLng(place.getLatLng()) != null) {
                        setAddress(getAddressFromLatLng(place.getLatLng()));
                    }
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
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

        geocoder = new Geocoder(getViewContext());

        if (mMap == null) {
            CustomMapFragment mapFragment = (CustomMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    private Bitmap resizeBitmapImage(int drawable, int height, int width) {

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(drawable);
        Bitmap b = bitmapdraw.getBitmap();

        return Bitmap.createScaledBitmap(b, width, height, false);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (mMap != null) {
            mMap.clear();
            subsMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmapImage(R.drawable.ic_maps_pin_subscriber, marker_height, marker_width))));

            if (getAddressFromLatLng(latLng) != null) {
                setAddress(getAddressFromLatLng(latLng));
            }
        }
    }

    private Address getAddressFromLatLng(LatLng latLng) {
        Address address = null;
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList.size() > 0) {
                address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
                CommonFunctions.log(String.valueOf(new Gson().toJson(address)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }


    private void setAddress(Address address) {
        mAddress = address;
        hintlayout.setVisibility(View.GONE);
        hintsetlayout.setVisibility(View.VISIBLE);
        if (address.getMaxAddressLineIndex() >= 0)
            txvaddress.setText(address.getAddressLine(0));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsetaddress: {
                ((GKNegosyoResellerLocationActivity) getViewContext()).setData(mAddress);
                break;
            }
        }
    }
}
