package com.goodkredit.myapplication.activities.gknegosyo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gknegosyo.GKNegosyoDashboardEarningsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.sponsors.SponsorDetails;
import com.goodkredit.myapplication.model.DiscountPerTransactionType;
import com.goodkredit.myapplication.model.GKNegosyPackage;
import com.goodkredit.myapplication.model.GKNegosyoPackagesAndDetails;
import com.goodkredit.myapplication.model.GKNegosyoPendingApplication;
import com.goodkredit.myapplication.model.GKNegosyoResellerInfo;
import com.goodkredit.myapplication.model.GKNegosyoResellerInformation;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetGKNegosyoPackagesResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetGKNegosyoTransactionResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetResellerInformationResponse;
import com.goodkredit.myapplication.responses.gknegosyo.ValidateDistributorResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKNegosyoDashboardActivity extends BaseActivity implements View.OnClickListener {

    private String limit = "0";
    private RecyclerView mRecyclerView;
    private GKNegosyoDashboardEarningsAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private List<DiscountPerTransactionType> mData;

    private TextView tvPackageName;
    private TextView tvDistributorID;
    private TextView tvDescription;
    private TextView tvValidity;

    private GKNegosyPackage resellerPackage;
    private GKNegosyoResellerInfo resellerInfo;

    private GKNegosyoResellerInformation mInfo;

    private GKService mService;

    private Dialog dialog;

    private int mYear = 2018;
    private int mMonth = 8;
    private TextView tv_month_year;

    private boolean ismonthselected = false;
    private boolean isyearselected = false;
    private AppCompatSpinner mSpnrMonth;
    private AppCompatSpinner mSpnrYear;
    private int EST_YEAR = 2018;
    private int EST_MONTH = 8;
    private int year;
    private int month;
    private int currentyear = 0;
    private int currentmonth = 0;
    private EditText mEdtEmail;
    private EditText mEdtNote;

    private String strEmail;
    private String strNote;

    private String sessionid;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String param;
    private String keyEncryption;

    List<GKNegosyoResellerInfo> resellerInfos;
    List<GKNegosyoPendingApplication> pendingApplications;
    List<GKNegosyPackage> gkNegosyPackages;

    //
    private String gkNegosyoIndex;
    private String gkNegosyoAuth;
    private String gkNegosyoKey;
    private String gkNegosyoParam;

    MonthYearPickerDialogFragment dialogFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_dashboard);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gk_negosyo_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        setupToolbar();

        PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_IS_SUBS_RESELLER, true);

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        tvPackageName = findViewById(R.id.gkn_db_package_name);
        tvDescription = findViewById(R.id.gkn_db_description);
        tvDistributorID = findViewById(R.id.gkn_db_lbl_distributorid);
        tvValidity = findViewById(R.id.gkn_db_lbl_validity);
        tv_month_year = findViewById(R.id.tv_month_year);

        mAdapter = new GKNegosyoDashboardEarningsAdapter(getViewContext());

        mLinearLayoutManager = new LinearLayoutManager(getViewContext());

        mRecyclerView = findViewById(R.id.rv_gk_negosyo_dashboard_earning_source);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null));
        mRecyclerView.setAdapter(mAdapter);

        mData = new ArrayList<>();
        resellerInfos = new ArrayList<>();
        pendingApplications = new ArrayList<>();
        gkNegosyPackages = new ArrayList<>();

        getSession();

        mYear = Calendar.getInstance().get(Calendar.YEAR);
        mMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        tv_month_year.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Bold.ttf", CommonFunctions.getMonthYear(String.valueOf(mYear) + "-" + String.valueOf(mMonth))));

        mService = getIntent().getParcelableExtra(GKService.KEY_SERVICE_OBJ);

        findViewById(R.id.btn_direct_distributordetails).setOnClickListener(this);
        findViewById(R.id.btn_direct_packagedetails).setOnClickListener(this);
        findViewById(R.id.btn_gkai).setOnClickListener(this);
        findViewById(R.id.imgv_down).setOnClickListener(this);
        findViewById(R.id.btn_open_gkai).setOnClickListener(this);
        findViewById(R.id.btnSendToEmail).setOnClickListener(this);

        currentyear = Calendar.getInstance().get(Calendar.YEAR);
        currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    private void setupGUI(GKNegosyPackage resellerPackage,
                          GKNegosyoResellerInfo resellerInfo) {
        tvPackageName.setText(resellerPackage.getPackageName());
        tvDescription.setText(resellerPackage.getPackageDescription());
        tvDistributorID.setText(resellerPackage.getDistributorName());
        tvValidity.setText(CommonFunctions.getDateShortenFromDateTime(CommonFunctions.convertDate(resellerPackage.getExpiryDate())));
        SponsorDetails.makeTextViewResizable(getViewContext(), tvDescription, 3, " ...See More", true);
    }

    public static void start(Context context, GKService service) {
        Intent intent = new Intent(context, GKNegosyoDashboardActivity.class);
        intent.putExtra(GKService.KEY_SERVICE_OBJ, service);
        context.startActivity(intent);
    }

    private void getSession() {
        showProgressDialog(getViewContext(), "", "Please wait...");
        //getResellerInformation();
        getResellerInformationV2();
        //  createSession(getSessionCallback);
    }

//    private Callback<String> getSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            try {
//                ResponseBody errBody = response.errorBody();
//                if (errBody == null) {
//                    if (!response.body().isEmpty()
//                            && !response.body().contains("<!DOCTYPE html>")
//                            && !response.body().equals("001")
//                            && !response.body().equals("002")
//                            && !response.body().equals("003")
//                            && !response.body().equals("004")
//                            && !response.body().equals("error")) {
//                        sessionid = response.body();
//                        getResellerInformation();
//                    } else {
//                        hideProgressDialog();
//                        showError();
//                    }
//                } else {
//                    hideProgressDialog();
//                    showError();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//            showError();
//        }
//    };

    private void getNegosyoTransaction() {

        Call<GetGKNegosyoTransactionResponse> call = RetrofitBuild.getGKNegosyoAPIService(getViewContext())
                .getGKNegosyoTransaction(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        sessionid,
                        limit,
                        "MONTHLY",
                        String.valueOf(mYear),
                        String.valueOf(mMonth)

                );

        call.enqueue(getNegosyoTransactionCallback);
    }

    private Callback<GetGKNegosyoTransactionResponse> getNegosyoTransactionCallback = new Callback<GetGKNegosyoTransactionResponse>() {
        @Override
        public void onResponse(Call<GetGKNegosyoTransactionResponse> call, Response<GetGKNegosyoTransactionResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                hideProgressDialog();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        if (response.body().getData().size() > 0) {
                            findViewById(R.id.tv_empty).setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
                        }
                        mAdapter.update(response.body().getData(), mYear, mMonth);
                    } else {
                        showError(response.body().getMessage());
                    }
                } else {
                    showError();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetGKNegosyoTransactionResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.gk_negosyo_refer_a_reseller) {
            GKNegosyoReferAResellerActivity.start(getViewContext());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_direct_packagedetails: {
                GKNegosyoPackageDetailsActivity.start(getViewContext(), resellerPackage, CacheManager.getInstance().getGKNegosyoPackagesAndDetails(), true);
                break;
            }
            case R.id.btn_direct_distributordetails: {
                createSession();
                break;
            }
            case R.id.btn_open_gkai:
            case R.id.btn_gkai: {
                GKNegosyoRedirectionActivity.start(getViewContext(), mService);
                break;
            }
            case R.id.imgv_down: {
                Calendar calendar = Calendar.getInstance();;

                Calendar minCal = Calendar.getInstance();
                minCal.set(2016, 0,1); // Set maximum date to show in dialog
                long minDate = minCal.getTimeInMillis();

                Calendar maxCal = Calendar.getInstance();
                maxCal.set(currentyear, currentmonth,31); // Set maximum date to show in dialog
                long maxDate = maxCal.getTimeInMillis(); // Get milliseconds of the modified date

                dialogFragment = MonthYearPickerDialogFragment.getInstance(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR),minDate,maxDate,"Filter Earnings");
                dialogFragment.setOnDateSetListener((year, monthOfYear) -> {
                    if (year < Calendar.getInstance().get(Calendar.YEAR)) {
                        mMonth = monthOfYear + 1;
                        mYear = year;
                        tv_month_year.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Bold.ttf", CommonFunctions.getMonthYear(String.valueOf(mYear) + "-" + String.valueOf(mMonth))));
                        getSession();
                    } else if (year == Calendar.getInstance().get(Calendar.YEAR)) {
                        if (monthOfYear + 1 <= Calendar.getInstance().get(Calendar.MONTH) + 1) {
                            mMonth = monthOfYear + 1;
                            mYear = year;
                            tv_month_year.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Bold.ttf", CommonFunctions.getMonthYear(String.valueOf(mYear) + "-" + String.valueOf(mMonth))));
                            getSession();
                        } else {
                            showError("Please check filter. Thanks.");
                        }
                    } else if (year > Calendar.getInstance().get(Calendar.YEAR) || monthOfYear + 1 > Calendar.getInstance().get(Calendar.MONTH) + 1) {
                        showError("Please check filter. Thanks.");
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), null);
                break;
            }
            case R.id.btnSendToEmail: {
                showSendToEmailDialog();
                break;
            }

        }
    }


    private void getGKNegosyoDistributorDetails() {
        showProgressDialog(getViewContext(), "", "Please wait...");
        Call<ValidateDistributorResponse> call = RetrofitBuild.getGKNegosyoAPIService(getViewContext())
                .getGKNegosyoDistributorDetails(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        sessionid,
                        resellerInfo.getDistributorID().toUpperCase()
                );

        call.enqueue(getGKNegosyoDistributorDetailsCallback);
    }

    private Callback<ValidateDistributorResponse> getGKNegosyoDistributorDetailsCallback = new Callback<ValidateDistributorResponse>() {
        @Override
        public void onResponse(Call<ValidateDistributorResponse> call, Response<ValidateDistributorResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                hideProgressDialog();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        GKNegosyoDistributorDetailsActivity.start(getViewContext(), response.body().getData(), true);
                    } else {
                        showError(response.body().getMessage());
                    }
                } else {
                    showError();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ValidateDistributorResponse> call, Throwable t) {
            hideProgressDialog();
            log(t.getMessage());
            showError();
        }
    };

    private void createSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            getGKNegosyoDistributorDetails();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getGKNegosyoPackages() {
        Call<GetGKNegosyoPackagesResponse> call = RetrofitBuild.getGKNegosyoAPIService(getViewContext())
                .getGKNegosyoPackages(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        sessionid,
                        resellerInfo.getDistributorID()
                );

        call.enqueue(getGKNegosyoPackagesCallback);
    }

    private Callback<GetGKNegosyoPackagesResponse> getGKNegosyoPackagesCallback = new Callback<GetGKNegosyoPackagesResponse>() {
        @Override
        public void onResponse(Call<GetGKNegosyoPackagesResponse> call, Response<GetGKNegosyoPackagesResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        CacheManager.getInstance().saveGKNegosyPackagesAndDetails(response.body().getGetGKNegosyoPackagesAndDetails());
                    } else {
//                        showError(response.body().getMessage());
                    }
                } else {
//                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetGKNegosyoPackagesResponse> call, Throwable t) {
//            showError();
        }
    };

    private void createSessionForResellerInfoAndPackage() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            getGKNegosyoDistributorDetails();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }


    private void getResellerInformation() {
        Call<GetResellerInformationResponse> call = RetrofitBuild.getGKNegosyoAPIService(getViewContext())
                .getResellerInformation(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        sessionid
                );

        call.enqueue(getResellerInformationCallback);
    }

    private Callback<GetResellerInformationResponse> getResellerInformationCallback = new Callback<GetResellerInformationResponse>() {
        @Override
        public void onResponse(Call<GetResellerInformationResponse> call, Response<GetResellerInformationResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        GKNegosyoResellerInformation info = response.body().getData();

                        mInfo = info;
                        resellerPackage = mInfo.getResellerPackage();
                        resellerInfo = mInfo.getResellerInfo();
                        if (!mInfo.getResellerInfo().getStatus().equals("ACTIVE")) {
                            hideProgressDialog();
                            GKNegosyoRedirectionActivity.start(getViewContext(), mService);
                            finish();
                        } else {
                            hideProgressDialog();
                            setupGUI(resellerPackage, resellerInfo);
                            getGKNegosyoPackages();
                            getNegosyoTransaction();
                        }
                    } else {
                        hideProgressDialog();
                        GKNegosyoRedirectionActivity.start(getViewContext(), mService);
                        finish();
                    }
                } else {
                    hideProgressDialog();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetResellerInformationResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

    private void showSendToEmailDialog() {


        dialog = new Dialog(getViewContext());
        dialog.setContentView(R.layout.dialog_send_to_email);
        dialog.setCancelable(true);

        mSpnrYear = dialog.findViewById(R.id.spnrYear);
        mSpnrMonth = dialog.findViewById(R.id.spnrMonth);
        mEdtEmail = dialog.findViewById(R.id.edt_email_address);
        mEdtNote = dialog.findViewById(R.id.edt_notes);

        createMonthSpinnerAddapter();
        createYearSpinnerAddapter();

        //Spinner for MONTH
        mSpnrMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            protected Adapter initializedAdapter = null;

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    String monthstring = parentView.getItemAtPosition(position).toString();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new SimpleDateFormat("MMM").parse(monthstring));
                    month = cal.get(Calendar.MONTH) + 1;
                    if (month > 0) {
                        ismonthselected = true;
                    } else {
                        ismonthselected = false;
                    }
                } catch (Exception e) {
                    ismonthselected = false;
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                ismonthselected = false;
            }
        });

        //Spinner for YEAR
        mSpnrYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String spinyear = parentView.getItemAtPosition(position).toString();
                if (!spinyear.equals("Select Year")) {
                    createMonthSpinnerAddapter();
                    year = Integer.parseInt(parentView.getItemAtPosition(position).toString());
                    isyearselected = true;
                } else {
                    isyearselected = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                isyearselected = false;
            }
        });


        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btnProceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ismonthselected && isyearselected && CommonFunctions.isValidEmail(mEdtEmail.getText().toString().trim())) {
                    strEmail = mEdtEmail.getText().toString().trim();
                    strNote = mEdtNote.getText().toString().trim();
                    createSessionForSendToEmail();
                    dialog.dismiss();
                    mSpnrMonth.setSelection(0); //set to default value of spinner
                    mSpnrYear.setSelection(0);
                    mEdtEmail.setText("");
                    mEdtNote.setText("");
                } else {
                    showToast("Please select year, month and enter a valid email address.", GlobalToastEnum.WARNING);
                }
            }
        });


        dialog.show();
    }

    //create spinner for month list
    private void createMonthSpinnerAddapter() {
        try {
            ArrayList<String> spinmonthlist = new ArrayList<>();
            spinmonthlist = monthlist();
            ArrayAdapter<String> monthadapter = new ArrayAdapter<>(getViewContext(), android.R.layout.simple_spinner_dropdown_item, spinmonthlist);
            monthadapter.setDropDownViewResource(R.layout.spinner_arrow);
            mSpnrMonth.setAdapter(monthadapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //create spinner for year list
    private void createYearSpinnerAddapter() {
        try {
            ArrayAdapter<String> yearadapter;
            yearadapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_spinner_dropdown_item, yearList());
            yearadapter.setDropDownViewResource(R.layout.spinner_arrow);
            mSpnrYear.setAdapter(yearadapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //make the number month to month name
    private ArrayList<String> monthlist() {

        int[] months = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("Select Month");

        for (int i = 0; i < months.length; i++) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            cal.set(Calendar.MONTH, months[i]);
            String month_name = month_date.format(cal.getTime());
            if (EST_YEAR == year && year != currentyear) {
                if (i >= EST_MONTH - 1 && (EST_YEAR - year) == 0) {
                    xVals.add(month_name);
                }
            } else if (year == currentyear) {
                if (i <= currentmonth - 1) {
                    xVals.add(month_name);
                }
            } else {
                xVals.add(month_name);
            }
        }
        return xVals;
    }

    private ArrayList<String> yearList() {
        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("Select Year");
        for (int i = EST_YEAR; i <= currentyear; i++) {
            xVals.add(Integer.toString(i));
        }
        return xVals;
    }

    private void createSessionForSendToEmail() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            sendToEmail();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void sendToEmail() {
        Call<GenericResponse> call = RetrofitBuild.getGKNegosyoAPIService(getViewContext())
                .sendToEmail(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        sessionid,
                        strEmail,
                        year,
                        month,
                        strNote
                );

        call.enqueue(sendToEmailCallback);
    }

    private Callback<GenericResponse> sendToEmailCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
//                        showGlobalDialogs(
//                                response.body().getMessage(),
//                                "OK",
//                                ButtonTypeEnum.SINGLE,
//                                true,
//                                false,
//                                DialogTypeEnum.SUCCESS,
//                                false);
                        showSuccessDialog(response.body().getMessage());
                    } else {
//                        showGlobalDialogs(
//                                response.body().getMessage(),
//                                "OK",
//                                ButtonTypeEnum.SINGLE,
//                                true,
//                                false,
//                                DialogTypeEnum.FAILED,
//                                false);
                        showFailedDialog(response.body().getMessage());
                    }
                } else {
//                    showGlobalDialogs(
//                            getString(R.string.generic_error_message),
//                            "OK",
//                            ButtonTypeEnum.SINGLE,
//                            true,
//                            false,
//                            DialogTypeEnum.FAILED,
//                            false);
                    showErrorToast();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
//            showGlobalDialogs(
//                    getString(R.string.generic_error_message),
//                    "OK",
//                    ButtonTypeEnum.SINGLE,
//                    false,
//                    false,
//                    DialogTypeEnum.FAILED,
//                    false);
            showErrorToast();
        }
    };

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    //get reseller information
    private void getResellerInformationV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getResellerInformationV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getResellerInformationV2Object(getResellerInformationV2Callback);

        }else{
            hideProgressDialog();
           showNoInternetToast();
        }
    }
    private void getResellerInformationV2Object(Callback<GenericResponse> getResellerInfo) {
        Call<GenericResponse> call = RetrofitBuilder.getGkNegosyoV2API(getViewContext())
                .getResellerInformationV2(
                        authenticationid,sessionid,param
                        );

        call.enqueue(getResellerInfo);
    }
    private Callback<GenericResponse> getResellerInformationV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                        Logger.debug("okhttp","DATTTAAA :"+data);

                        String resellerinf = CommonFunctions.parseJSON(data,"ResellerInfo");
                        String pending  = CommonFunctions.parseJSON(data,"PendingApplication");
                        String resellerpck = CommonFunctions.parseJSON(data,"ResellerPackage");

                        Logger.debug("okhttp","RESELLERINFO : "+resellerinf);
                        Logger.debug("okhttp","PENDING : "+pending);
                        Logger.debug("okhttp","RESELLERPACKAGE : "+resellerpck);

                        resellerInfos  = new Gson().fromJson(resellerinf, new TypeToken<List<GKNegosyoResellerInfo>>(){}.getType());
                        pendingApplications  = new Gson().fromJson(pending, new TypeToken<List<GKNegosyoPendingApplication>>(){}.getType());
                        gkNegosyPackages  = new Gson().fromJson(resellerpck, new TypeToken<List<GKNegosyPackage>>(){}.getType());

                        if(resellerInfos.size() > 0 && resellerInfos != null){
                            for(GKNegosyoResellerInfo info : resellerInfos){
                                resellerInfo = info;
                                Logger.debug("okhttp","RESELLERINFORMATION : "+new Gson().toJson(resellerInfo));
                            }
                        }

                        if(gkNegosyPackages.size() > 0 && gkNegosyPackages != null){
                            for(GKNegosyPackage gkNegosyPackage : gkNegosyPackages){
                                resellerPackage = gkNegosyPackage;
                                Logger.debug("okhttp","PACKAGES : "+new Gson().toJson(resellerPackage));
                            }
                        }

                       if(resellerInfo == null){
                           hideProgressDialog();
                           GKNegosyoRedirectionActivity.start(getViewContext(), mService);
                           finish();
                       }else{
                           if (!resellerInfo.getStatus().equals("ACTIVE") || resellerInfo == null) {
                               hideProgressDialog();
                               GKNegosyoRedirectionActivity.start(getViewContext(), mService);
                               finish();
                           } else {
                               hideProgressDialog();
                               setupGUI(resellerPackage, resellerInfo);

                               //getGKNegosyoPackages();
                               //getNegosyoTransaction();

                               getGKNegosyoPackagesV2();
                               getGKNegosyoTransactionV2();

                           }
                       }


                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                           showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
                        showErrorToast(message);
                        hideProgressDialog();
                        GKNegosyoRedirectionActivity.start(getViewContext(), mService);
                        finish();
                    }
                } else {
                    showErrorGlobalDialogs();
                    hideProgressDialog();
                }
        }
        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

    //get gknegosyo packages
    private void getGKNegosyoPackagesV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("distributorid",resellerInfo.getDistributorID());

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            gkNegosyoIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", gkNegosyoIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            gkNegosyoAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
            gkNegosyoKey = CommonFunctions.getSha1Hex(gkNegosyoAuth + sessionid + "getGKNegosyoPackagesV2");
            gkNegosyoParam = CommonFunctions.encryptAES256CBC(gkNegosyoKey, String.valueOf(paramJson));

            getGKNegosyoPackagesV2Object(getGKNegosyoPackagesV2Callback);

        }else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private void getGKNegosyoPackagesV2Object(Callback<GenericResponse> getnegosyoPackages) {
        Call<GenericResponse> call = RetrofitBuilder.getGkNegosyoV2API(getViewContext())
                .getGKNegosyoPackagesV2(
                        gkNegosyoAuth,sessionid,gkNegosyoParam
                );

        call.enqueue(getnegosyoPackages);
    }
    private Callback<GenericResponse> getGKNegosyoPackagesV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(gkNegosyoKey,response.body().getData());
                        GKNegosyoPackagesAndDetails data = new Gson().fromJson(decryptedData, GKNegosyoPackagesAndDetails.class);
                        CacheManager.getInstance().saveGKNegosyPackagesAndDetails(data);

                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
//                        showError(response.body().getMessage());
                    }
                } else {
//                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            showError();
        }
    };

    //get negosyo transaction
    private void getGKNegosyoTransactionV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("limit",limit);
            parameters.put("datetype","MONTHLY");
            parameters.put("year", String.valueOf(mYear));
            parameters.put("month",  String.valueOf(mMonth));

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getGKNegosyoTransactionV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getGKNegosyoTransactionV2Object(getGKNegosyoTransactionV2Callback);

        }else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private void getGKNegosyoTransactionV2Object(Callback<GenericResponse> getGKNegosyoTransaction) {
        Call<GenericResponse> call = RetrofitBuilder.getGkNegosyoV2API(getViewContext())
                .getGKNegosyoTransactionV2(
                        authenticationid,sessionid,param
                );

        call.enqueue(getGKNegosyoTransaction);
    }
    private Callback<GenericResponse> getGKNegosyoTransactionV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                hideProgressDialog();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                        List<DiscountPerTransactionType> txn =
                                new Gson().fromJson(data,new TypeToken<List<DiscountPerTransactionType>>(){}.getType());

                        if (txn.size() > 0 && txn != null) {
                            findViewById(R.id.tv_empty).setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
                        }
                        mAdapter.update(txn, mYear, mMonth);
                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                          showAutoLogoutDialog(getString(R.string.logoutmessage));
                        }
                    else {
                        showError(message);
                    }
                } else {
                    showError();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };


    private void showSuccessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("SUCCESS", message, "OK", "",
                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.SUCCESS);

        View closebtn = dialog.btnCloseImage();
        closebtn.setVisibility(View.GONE);

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });
    }
    private void showFailedDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("FAILED", message, "Retry", "",
                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);

        View closebtn = dialog.btnCloseImage();
        closebtn.setVisibility(View.GONE);

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });
    }

}
