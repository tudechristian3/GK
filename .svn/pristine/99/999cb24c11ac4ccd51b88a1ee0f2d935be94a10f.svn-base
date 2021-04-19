package com.goodkredit.myapplication.fragments.paramount;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.paramount.ParamountInsuranceActivity;
import com.goodkredit.myapplication.adapter.paramount.ParamountDialogAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.AddressList;
import com.goodkredit.myapplication.bean.ParamountQueue;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.DiscountResponse;
import com.goodkredit.myapplication.responses.GetCitiesResponse;
import com.goodkredit.myapplication.responses.GetProvincesResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User-PC on 11/29/2017.
 */

public class ContactInformationFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private DatabaseHandler mdb;
//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String guarantorid;
    private String currentyear = "";
    private String requestID = "";
    private String province = "";
    private boolean isCity = false;
    private boolean isNewInsert = false;
    private boolean isResellerDiscount = false;

    private EditText edtProvince;
    private EditText edtMunicipalityCity;
    private EditText edtHouseNumber;
    private EditText edtStreetName;
    private EditText edtBuildingName;
    private EditText edtBarangay;
    private EditText edtMobileNumber;
    private EditText edtTelephoneNumber;
    private EditText edtZipCode;

    private List<String> arrProvinces;
    private List<String> arrCities;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private NestedScrollView nested_scroll;

    private ParamountQueue paramountQueue = null;

    private SearchView searchView;
    private MaterialDialog mParamountDialog;
    private TextView txvDialogTitle;
    private ParamountDialogAdapter mParamountDialogAdapter;

    private List<AddressList> addressLists;

    private double resellerDiscount = 0;
    private double resellerAmount = 0;
    private double resellerTotalAmount = 0;

    //UNIFIED SESSION
    private String sessionid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_information, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        view.findViewById(R.id.btnNext).setOnClickListener(this);
        edtProvince = (EditText) view.findViewById(R.id.edtProvince);
        edtProvince.setOnClickListener(this);
        edtMunicipalityCity = (EditText) view.findViewById(R.id.edtMunicipalityCity);
        edtMunicipalityCity.setOnClickListener(this);

        edtHouseNumber = (EditText) view.findViewById(R.id.edtHouseNumber);
        edtStreetName = (EditText) view.findViewById(R.id.edtStreetName);
        edtBuildingName = (EditText) view.findViewById(R.id.edtBuildingName);
        edtBarangay = (EditText) view.findViewById(R.id.edtBarangay);
        edtMobileNumber = (EditText) view.findViewById(R.id.edtMobileNumber);
        edtTelephoneNumber = (EditText) view.findViewById(R.id.edtTelephoneNumber);
        edtZipCode = (EditText) view.findViewById(R.id.edtZipCode);
        edtZipCode.setBackgroundColor(ContextCompat.getColor(getViewContext(), R.color.color_CCCCCC));
        edtZipCode.setEnabled(false);

        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        nested_scroll = (NestedScrollView) view.findViewById(R.id.nested_scroll);

        nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    //Logger.debug("antonhttp", "Scroll DOWN");
                    mSwipeRefreshLayout.setEnabled(false);
                }
                if (scrollY < oldScrollY) {
                    //Logger.debug("antonhttp", "Scroll UP");
                    mSwipeRefreshLayout.setEnabled(false);
                }

                if (scrollY == 0) {
                    //Logger.debug("antonhttp", "TOP SCROLL");
                    mSwipeRefreshLayout.setEnabled(true);
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    mSwipeRefreshLayout.setEnabled(false);

                }
            }
        });
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        guarantorid = mdb.getGuarantorID(mdb);
        currentyear = getArguments().getString("currentyear");
        requestID = getArguments().getString("generatedid");

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        addressLists = new ArrayList<>();
        arrProvinces = new ArrayList<>();
        arrCities = new ArrayList<>();

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        setUpProvinceDialog();

        getSession();
    }

    private void setUpProvinceDialog() {
        mParamountDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(true)
                .customView(R.layout.dialog_vehicle_types, false)
                .build();
        View view = mParamountDialog.getCustomView();

        txvDialogTitle = (TextView) view.findViewById(R.id.txvDialogTitle);
        txvDialogTitle.setText("Province");

        searchView = (SearchView) view.findViewById(R.id.searchbox);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setOnSearchClickListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_dialog_vehicle_types);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        recyclerView.setNestedScrollingEnabled(false);
        mParamountDialogAdapter = new ParamountDialogAdapter(getViewContext());
        recyclerView.setAdapter(mParamountDialogAdapter);
        mParamountDialogAdapter.clear();

        mParamountDialogAdapter.setOnItemClickListener(new ParamountDialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, String name) {

                edtProvince.setText(name);
                province = name;
                edtMunicipalityCity.setText("");
                edtZipCode.setText("");
                arrCities.clear();
                addressLists.clear();
                isCity = true;
                isNewInsert = false;
                getSession();

                mParamountDialog.dismiss();

            }
        });

    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            String fetchingmsg = "";

            if (isNewInsert || isResellerDiscount) {
                fetchingmsg = "Processing request.";
            } else {
                if (!isCity) {
                    fetchingmsg = "Fetching provinces.";
                } else {
                    fetchingmsg = "Fetching cities.";
                }
            }

            mTvFetching.setText(fetchingmsg);
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isNewInsert) {
                prePurchase(prePurchaseSession);
            } else if (isResellerDiscount) {
                calculateDiscountForReseller(calculateDiscountForResellerSession);
            } else {
                if (!isCity) {
                    getProvinces(getProvincesSession);
                } else {
                    getCities(getCitiesSession);
                }
            }

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mdb != null) {

            ParamountQueue paramountQueue = mdb.getParamountQueue(mdb);
            if (paramountQueue != null) {


                if (paramountQueue.getProvince() != null) {
                    edtProvince.setText(paramountQueue.getProvince());
                }

                if (paramountQueue.getMunicipality() != null) {
                    edtMunicipalityCity.setText(paramountQueue.getMunicipality());
                }

                if (paramountQueue.getHouseNumber() != null) {
                    edtHouseNumber.setText(paramountQueue.getHouseNumber());
                }

                if (paramountQueue.getStreetName() != null) {
                    edtStreetName.setText(paramountQueue.getStreetName());
                }

                if (paramountQueue.getBuildingName() != null) {
                    edtBuildingName.setText(paramountQueue.getBuildingName());
                }

                if (paramountQueue.getBarangay() != null) {
                    edtBarangay.setText(paramountQueue.getBarangay());
                }

                if (paramountQueue.getMobileNumber() != null) {
                    edtMobileNumber.setText(paramountQueue.getMobileNumber());
                }

                if (paramountQueue.getZipcode() != null) {
                    edtZipCode.setText(paramountQueue.getZipcode());
                }

                if (paramountQueue.getTelephoneNo() != null) {
                    edtTelephoneNumber.setText(paramountQueue.getTelephoneNo());
                }

            }

        }

    }

//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    hideProgressDialog();
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    hideProgressDialog();
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    hideProgressDialog();
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    if (isNewInsert) {
//                        prePurchase(prePurchaseSession);
//                    } else if (isResellerDiscount) {
//                        calculateDiscountForReseller(calculateDiscountForResellerSession);
//                    } else {
//                        if (!isCity) {
//                            getProvinces(getProvincesSession);
//                        } else {
//                            getCities(getCitiesSession);
//                        }
//                    }
//
//                }
//            } else {
//                hideProgressDialog();
//                mSwipeRefreshLayout.setRefreshing(false);
//                mLlLoader.setVisibility(View.GONE);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//            mSwipeRefreshLayout.setRefreshing(false);
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getProvinces(Callback<GetProvincesResponse> getProvincesCallback) {
        Call<GetProvincesResponse> getprovinces = RetrofitBuild.getProvincesService(getViewContext())
                .getProvincesCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode);
        getprovinces.enqueue(getProvincesCallback);
    }

    private final Callback<GetProvincesResponse> getProvincesSession = new Callback<GetProvincesResponse>() {

        @Override
        public void onResponse(Call<GetProvincesResponse> call, Response<GetProvincesResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    if (mdb != null) {

                        arrProvinces.clear();

                        for (AddressList add : response.body().getProvinces()) {
                            arrProvinces.add(add.getProvince());
                        }

                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetProvincesResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void getCities(Callback<GetCitiesResponse> getCitiesCallback) {
        Call<GetCitiesResponse> getcities = RetrofitBuild.getCitiesService(getViewContext())
                .getCitiesCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        province);
        getcities.enqueue(getCitiesCallback);
    }

    private final Callback<GetCitiesResponse> getCitiesSession = new Callback<GetCitiesResponse>() {

        @Override
        public void onResponse(Call<GetCitiesResponse> call, Response<GetCitiesResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    if (mdb != null) {

                        arrCities.clear();
                        addressLists.clear();

                        for (AddressList city : response.body().getCities()) {
                            addressLists.add(city);
                            arrCities.add(CommonFunctions.capitalizeWord(city.getCity()));
                        }

                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetCitiesResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void prePurchase(Callback<String> prePurchaseCallback) {

        Call<String> prepurchase = RetrofitBuild.prePurchaseService(getViewContext())
                .prePurchaseCall(borrowerid,
                        String.valueOf(resellerTotalAmount),
                        userid,
                        imei,
                        sessionid,
                        authcode);
        prepurchase.enqueue(prePurchaseCallback);
    }

    private final Callback<String> prePurchaseSession = new Callback<String>() {

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                mLlLoader.setVisibility(View.GONE);

                String responseData = response.body().toString();

                if (!responseData.isEmpty()) {
                    if (responseData.equals("001")) {
                        CommonFunctions.hideDialog();
                        showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
                    } else if (responseData.equals("error")) {
                        CommonFunctions.hideDialog();
                        showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                    } else if (responseData.contains("<!DOCTYPE html>")) {
                        CommonFunctions.hideDialog();
                        showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                    } else {
                        if (responseData.length() > 0) {

                            Logger.debug("antonhttp", "PARAMQUEUE: " + new Gson().toJson(paramountQueue));

                            String vehicleClassificationCode = "";

                            if (paramountQueue.getLTOMVType().equals("Car") ||
                                    paramountQueue.getLTOMVType().equals("Sports Utility Vehicle") ||
                                    paramountQueue.getLTOMVType().equals("Utility Vehicle (Private)")) {
                                vehicleClassificationCode = "VC_01";
                            } else if (paramountQueue.getLTOMVType().equals("Utility Vehicle (Commercial)") ||
                                    paramountQueue.getLTOMVType().equals("Non-Conventional MV")) {
                                vehicleClassificationCode = "VC_02";
                            } else if (paramountQueue.getLTOMVType().equals("Truck") ||
                                    paramountQueue.getLTOMVType().equals("Shuttle Bus")) {
                                vehicleClassificationCode = "VC_03";
                            } else if (paramountQueue.getLTOMVType().equals("Motorcycle") ||
                                    paramountQueue.getLTOMVType().equals("Motorcycle with Sidecar") ||
                                    paramountQueue.getLTOMVType().equals("Trailer") ||
                                    paramountQueue.getLTOMVType().equals("Tricycle")) {
                                vehicleClassificationCode = "VC_04";
                            }

                            Intent intent = new Intent(getViewContext(), Payment.class);
                            intent.putExtra("AMOUNT", String.valueOf(resellerTotalAmount));
                            intent.putExtra("PARAMOUNTRESELLERDISCOUNT", String.valueOf(resellerDiscount));
                            intent.putExtra("PARAMOUNTQUEUE", "true");
                            intent.putExtra("PARAMOUNTVEHICLECLASSIFICATIONCODE", vehicleClassificationCode);
                            intent.putExtra("VOUCHERSESSION", responseData);
                            startActivity(intent);

                        } else {
                            showError("Invalid Voucher Session.");
                        }
                    }
                } else {
                    CommonFunctions.hideDialog();
                    showNoInternetConnection(true);
                    showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                }

            }

        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            CommonFunctions.hideDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };


    private void openProvinceDialog() {
        if (mdb != null) {
            new MaterialDialog.Builder(getViewContext())
                    .title("Province")
                    .items(arrProvinces)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int position, CharSequence text) {
                            edtProvince.setText(text.toString());
                            province = text.toString();
                            edtMunicipalityCity.setText("");
                            arrCities.clear();
                            isCity = true;
                            getSession();
                        }
                    })
                    .show();
        }
    }

    private void openCitiesDialog() {
        if (mdb != null) {
            new MaterialDialog.Builder(getViewContext())
                    .title("City")
                    .items(arrCities)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int position, CharSequence text) {
                            isNewInsert = false;
                            if (addressLists.size() > 0) {
                                String zipcode = addressLists.get(position).getZipcode();
                                if (zipcode.length() > 0) {
                                    edtZipCode.setBackgroundColor(ContextCompat.getColor(getViewContext(), R.color.color_CCCCCC));
                                    edtZipCode.setEnabled(false);
                                    edtZipCode.setText(addressLists.get(position).getZipcode());
                                } else {
                                    edtZipCode.setBackgroundColor(ContextCompat.getColor(getViewContext(), R.color.colorwhite));
                                    edtZipCode.setEnabled(true);
                                }
                            }
                            edtMunicipalityCity.setText(text.toString());
                        }
                    })
                    .show();
        }
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                hideKeyboard(getViewContext());

                String strsql = "UPDATE " + DatabaseHandler.PARAMOUNT_QUEUE + " SET HouseNumber=?, StreetName=?, BuildingName=?, Barangay=?, Province=?, Municipality=?, Zipcode=?, MobileNumber=?, TelephoneNo=? WHERE RequestID=?";

                String[] whereArgs = new String[]{edtHouseNumber.getText().toString().trim(),
                        edtStreetName.getText().toString().trim(),
                        edtBuildingName.getText().toString().trim(),
                        edtBarangay.getText().toString().trim(),
                        edtProvince.getText().toString().trim(),
                        edtMunicipalityCity.getText().toString().trim(),
                        edtZipCode.getText().toString().trim(),
                        edtMobileNumber.getText().toString().trim(),
                        edtTelephoneNumber.getText().toString().trim(),
                        requestID};

                mdb.updateParamountQueue(mdb, strsql, whereArgs);

                Bundle args = new Bundle();
                args.putString("currentyear", currentyear);
                args.putString("generatedid", requestID);

                ((ParamountInsuranceActivity) getViewContext()).displayView(4, args);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtMunicipalityCity: {
                if (arrCities.size() > 0) {
                    openCitiesDialog();
                }
                break;
            }
            case R.id.edtProvince: {
                if (arrProvinces.size() > 0) {

                    if (mParamountDialog != null) {

                        mParamountDialogAdapter.setDialogData(arrProvinces);

                        mParamountDialog.show();

                        searchView.setQuery("", false);

                        searchView.clearFocus();

                    }

                } else {

                    showError("No provinces found.");

                }
                break;
            }
            case R.id.refreshnointernet: {
                getSession();
                break;
            }
            case R.id.searchbox: {

                txvDialogTitle.setVisibility(View.GONE);

                break;
            }
            case R.id.btnNext: {
                if (edtHouseNumber.getText().toString().trim().length() > 0 &&
                        edtStreetName.getText().toString().trim().length() > 0 &&
                        edtProvince.getText().toString().trim().length() > 0 &&
                        edtMunicipalityCity.getText().toString().trim().length() > 0 &&
                        edtZipCode.getText().toString().trim().length() > 0 &&
                        edtTelephoneNumber.getText().toString().trim().length() > 0 &&
                        edtMobileNumber.getText().toString().trim().length() > 0) {

                    String mobileno = getMobileNumber(edtMobileNumber.getText().toString().trim());
                    if (mobileno.equals("INVALID")) {
                        showError("Invalid Mobile Number");
                    } else {

//                        if (isAirplaneModeOn(getViewContext())) {
//                            showError("Airplane mode is enabled. Please disable Airplane mode and enable GPS or Internet to proceed.");
//                        } else {
//                            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//                                if (isGPSModeOn()) {

                        String strsql = "UPDATE " + DatabaseHandler.PARAMOUNT_QUEUE + " SET HouseNumber=?, StreetName=?, BuildingName=?, Barangay=?, Province=?, Municipality=?, Zipcode=?, MobileNumber=?, TelephoneNo=? WHERE RequestID=?";

                        String[] whereArgs = new String[]{edtHouseNumber.getText().toString().trim(),
                                edtStreetName.getText().toString().trim(),
                                edtBuildingName.getText().toString().trim(),
                                edtBarangay.getText().toString().trim(),
                                edtProvince.getText().toString().trim(),
                                edtMunicipalityCity.getText().toString().trim(),
                                edtZipCode.getText().toString().trim(),
                                edtMobileNumber.getText().toString().trim(),
                                edtTelephoneNumber.getText().toString().trim(),
                                requestID};

                        mdb.updateParamountQueue(mdb, strsql, whereArgs);

                        if (mdb != null) {
                            paramountQueue = mdb.getParamountQueue(mdb);
                        }


                        resellerDiscount = 0;
                        isCity = false;
                        isNewInsert = false;
                        isResellerDiscount = true;
                        getSession();

//                                } else {
//                                    showGPSDisabledAlertToUser();
//                                }
//                            } else {
//                                showError(getString(R.string.generic_internet_error_message));
//                            }
//                        }

                    }

                } else {

                    showError("Please input all required fields.");

                }
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        isCity = false;
        isNewInsert = false;
        getSession();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        queryText(newText);
        return false;
    }

    private void queryText(String query) {
        if (mParamountDialogAdapter != null) {
            if (query.length() > 0) {

                mParamountDialogAdapter.filter(query);

            } else {

                mParamountDialogAdapter.clear();
                mParamountDialogAdapter.setDialogData(arrProvinces);

            }
        }
    }

    @Override
    public boolean onClose() {
        txvDialogTitle.setVisibility(View.VISIBLE);
        return false;
    }

    private void calculateDiscountForReseller(Callback<DiscountResponse> calculateDiscountForResellerCallback) {
        GPSTracker gpsTracker = new GPSTracker(getViewContext());

        Call<DiscountResponse> resellerdiscount = RetrofitBuild.getDiscountService(getViewContext())
                .calculateDiscountForReseller(userid,
                        imei,
                        authcode,
                        sessionid,
                        borrowerid,
                        ".",
                        paramountQueue.getAmountPaid(),
                        PreferenceUtils.getStringPreference(getViewContext(), "paramount_service_code"),
                        String.valueOf(gpsTracker.getLongitude()),
                        String.valueOf(gpsTracker.getLatitude()));
        resellerdiscount.enqueue(calculateDiscountForResellerCallback);
    }

    private final Callback<DiscountResponse> calculateDiscountForResellerSession = new Callback<DiscountResponse>() {

        @Override
        public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {

            mLlLoader.setVisibility(View.GONE);

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    resellerAmount = paramountQueue.getAmountPaid();
                    resellerDiscount = response.body().getDiscount();
                    resellerTotalAmount = paramountQueue.getAmountPaid() - resellerDiscount;

                    if (resellerDiscount > 0) {

                        showDiscountDialog();

                    } else {

                        isCity = false;
                        isNewInsert = true;
                        isResellerDiscount = false;
                        getSession();

                    }

                } else if (response.body().getStatus().equals("005")) {

                    new MaterialDialog.Builder(getViewContext())
                            .content(response.body().getMessage())
                            .cancelable(false)
                            .negativeText("Cancel")
                            .positiveText("OK")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    resellerAmount = paramountQueue.getAmountPaid();
                                    resellerDiscount = 0;
                                    resellerTotalAmount = paramountQueue.getAmountPaid() - resellerDiscount;

                                    isCity = false;
                                    isNewInsert = true;
                                    isResellerDiscount = false;
                                    getSession();

                                }
                            })
                            .dismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {

//                                    isCity = false;
//                                    isNewInsert = false;
//                                    isResellerDiscount = true;

                                }
                            })
                            .show();

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<DiscountResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showDiscountDialog() {

        try {
            TextView popok;
            TextView popcancel;
            TextView popamountpaid;
            TextView popservicecharge;
            TextView poptotalamount;

            final Dialog dialog = new Dialog(new ContextThemeWrapper(getViewContext(), android.R.style.Theme_Holo_Light));
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_discount_dialog);

            popamountpaid = (TextView) dialog.findViewById(R.id.popamounttopayval);
            popservicecharge = (TextView) dialog.findViewById(R.id.popservicechargeval);
            poptotalamount = (TextView) dialog.findViewById(R.id.poptotalval);
            popok = (TextView) dialog.findViewById(R.id.popok);
            popcancel = (TextView) dialog.findViewById(R.id.popcancel);

            //set value
            popamountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerAmount)));
            popservicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerDiscount)));
            poptotalamount.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerTotalAmount)));


            dialog.show();

            //click close
            popcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }

            });

            popok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    isCity = false;
                    isNewInsert = true;
                    isResellerDiscount = false;
                    getSession();

                }

            });

        } catch (Exception e) {

        }

    }
}
