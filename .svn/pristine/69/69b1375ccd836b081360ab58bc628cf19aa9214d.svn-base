package com.goodkredit.myapplication.fragments.schoolmini;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniActivity;
import com.goodkredit.myapplication.adapter.schoolmini.SchoolMiniPaymentsAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniPaymentLogsDB;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDetails;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPaymentLogs;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniPaymentLogsResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class SchoolMiniPaymentsLogsFragment extends BaseFragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    //DB
    private DatabaseHandler mdb;

    private String sessionid;

    //ListView
    private StickyListHeadersListView stickListLV;
    private SchoolMiniPaymentsAdapter stickyLVAdapter;

    //VIEW ARCHIVE
    private LinearLayout viewarchivelayout;
    private TextView viewarchive;
    private LinearLayout viewarchivelayoutv2;
    private TextView viewarchivev2;
    private MaterialDialog mFilterOptionDialog;
    private List<String> YEARS = new ArrayList<>();
    private int MIN_YEAR = 2018;
    private int MAX_YEAR = 2018;
    private MaterialEditText edtMonth;
    private MaterialEditText edtYear;

    private MaterialDialog mDialog;
    private ScrollView filterwrap;
    private LinearLayout optionwrap;
    private TextView editsearches;
    private TextView clearsearch;
    private Spinner monthspinType;
    private Spinner yearspinType;
    private TextView popfilter;
    private TextView popcancel;
    private boolean isyearselected = false;

    //FILTER
    private RelativeLayout emptyvoucherfilter;
    private TextView filteroption;

    //DATE
    private String stryear = ".";
    private int year = 0;
    private int currentyear = 0;
    private int registrationyear;
    private String dateregistered = "";
    private Button mBtnMore;

    //View Archieve and View Filter Switch
    private LinearLayout view_more_container;
    private TextView view_more;

    //LOADER
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    //SCROLLLIMIT
    private boolean isloadmore = true;
    private boolean isbottomscroll = false;
    private boolean isfirstload = true;
    private boolean isLoading = false;
    private int limit = 0;
    private StickyScrollView nested_scroll;

    //SCHOOL
    private String schoolid = "";
    private String studentid = "";
    private String course = "";
    private String yearlevel = "";
    private String firstname = "";
    private String middlename = "";
    private String lastname = "";
    private String mobilenumber = "";
    private String emailaddress = "";
    private String schoolyear = "";
    private String semester = "";
    private String semestralfee = "";
    private String soaid = "";

    private List<SchoolMiniPaymentLogs> schoolpaymentlogslist = new ArrayList<>();

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmini_paymentlogs, container, false);
        try {
            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        //swipe refresh
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        //loader
        mLlLoader = view.findViewById(R.id.loaderLayout);
        mTvFetching = view.findViewById(R.id.fetching);
        mTvWait = view.findViewById(R.id.wait);

        //no internet connection
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet = view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //Filter Option
        emptyLayout = view.findViewById(R.id.emptyLayout);
        emptyvoucherfilter = view.findViewById(R.id.emptyvoucherfilter);
        filteroption = view.findViewById(R.id.filteroption);
        filteroption.setOnClickListener(this);

        //empty layout
        emptyLayout = view.findViewById(R.id.emptyLayout);
        textView11 = view.findViewById(R.id.textView11);
        refresh = view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);


        view_more_container = view.findViewById(R.id.view_more_container);
        view_more = view.findViewById(R.id.view_more);
        view_more_container.setOnClickListener(this);

        stickListLV = view.findViewById(R.id.stickyListHeaderListView);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mdb = new DatabaseHandler(getViewContext());

        schoolid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCSERVICECODE);

        stickListLV.setOnItemClickListener(fetchItemListener);
        stickyLVAdapter = new SchoolMiniPaymentsAdapter(getViewContext(), schoolpaymentlogslist);
        stickListLV.setAdapter(stickyLVAdapter);

        if (getArguments() != null) {
            studentid = getArguments().getString("STUDENTID");
            course = getArguments().getString("COURSE");
            yearlevel = getArguments().getString("YEARLEVEL");
            firstname = getArguments().getString("FIRSTNAME");
            middlename = getArguments().getString("MIDDLENAME");
            lastname = getArguments().getString("LASTNAME");
            mobilenumber = getArguments().getString("MOBILENUMBER");
            emailaddress = getArguments().getString("EMAILADDRESS");
            schoolyear = getArguments().getString("SCHOOLYEAR");
            semester = getArguments().getString("SEMESTER");
            semestralfee = getArguments().getString("SEMESTRALFEE");
            soaid = getArguments().getString("SOAID");
        }

        loaderTimer();

        setNestedScrollingDisabled();

        getCurrentYear();

        getRegistrationYear();

        //Listener for ListView
        stickListLV.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (stickListLV.getLastVisiblePosition() - stickListLV.getHeaderViewsCount() -
                        stickListLV.getFooterViewsCount()) >= (stickListLV.getCount() - 1)) {

                    isbottomscroll = true;

                    if (isloadmore) {
                        if (!isfirstload) {
                            limit = limit + 30;
                        }
                        getSession();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mdb.getSchoolMiniPaymentLogs(mdb, soaid).size() > 0) {
                    view_more_container.setVisibility(View.VISIBLE);
                    if (isyearselected) {
                        view_more.setText("FILTER OPTIONS");
                    } else {
                        view_more.setText("VIEW ARCHIVE");
                    }
                }
                if (firstVisibleItem == 0 && StickylistViewAtTop(stickListLV)) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });

//        if (schoolpaymentlogslist.isEmpty()) {
//            getSession();
//        }

    }

    private void loaderTimer() {
        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };
    }

    private void getCurrentYear() {
        year = Calendar.getInstance().get(Calendar.YEAR);
        currentyear = Calendar.getInstance().get(Calendar.YEAR);

        MAX_YEAR = currentyear;
        stryear = String.valueOf(year);
    }

    private void getRegistrationYear() {
        List<SchoolMiniDetails> schoolMiniDetailsList = CacheManager.getInstance().getSchoolDetailsList();

        for (SchoolMiniDetails schoolminiDetails : schoolMiniDetailsList) {
            dateregistered = schoolminiDetails.getDateTimeAdded();
        }

        String CurrentString = dateregistered;
        String[] separated = CurrentString.split("-");
        registrationyear = Integer.parseInt(separated[0]);
    }

    private void setNestedScrollingDisabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stickListLV.setNestedScrollingEnabled(false);
        } else {
            ViewCompat.setNestedScrollingEnabled(stickListLV, false);
        }
    }

    //Checks if StickyListView is At top
    private boolean StickylistViewAtTop(StickyListHeadersListView stickyListView) {
        return stickyListView.getChildCount() == 0 || stickyListView.getChildAt(0).getTop() == 0;
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showNoInternetConnection(false);

            isLoading = true;

            mTvFetching.setText("Fetching payment logs.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            //getSchoolStudentPaymentLogs(getSchoolStudentPaymentLogsCallBack);
            getSchoolStudentPaymentLogsV2();
        } else {
            refresh.setEnabled(true);
            refreshnointernet.setEnabled(true);
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getSchoolStudentPaymentLogs(Callback<SchoolMiniPaymentLogsResponse> getSchoolStudentPaymentLogsCallBack) {
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        Call<SchoolMiniPaymentLogsResponse> getSchoolStudentPaymentLogs = RetrofitBuild.getSchoolAPIService(getViewContext())
                .getSchoolStudentPaymentLogs(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        mobilenumber,
                        authcode,
                        limit,
                        stryear
                );

        getSchoolStudentPaymentLogs.enqueue(getSchoolStudentPaymentLogsCallBack);
    }

    private final Callback<SchoolMiniPaymentLogsResponse> getSchoolStudentPaymentLogsCallBack = new Callback<SchoolMiniPaymentLogsResponse>() {

        @Override
        public void onResponse(Call<SchoolMiniPaymentLogsResponse> call, Response<SchoolMiniPaymentLogsResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                //000 - list of process
                if (response.body().getStatus().equals("000")) {
                    mLlLoader.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);

                    isloadmore = response.body().getSchoolminipaymentlogsList().size() > 0;

                    isLoading = false;
                    isfirstload = false;

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, SchoolMiniPaymentLogsDB.TABLE_SC_PAYMENTLOGS);
                    }

                    List<SchoolMiniPaymentLogs> schoolMiniPaymentLogsList = response.body().getSchoolminipaymentlogsList();
                    if (schoolMiniPaymentLogsList.size() > 0) {
                        for (SchoolMiniPaymentLogs schoolMiniPaymentLogs : schoolMiniPaymentLogsList) {
                            mdb.insertSchoolMiniPaymentLogs(mdb, schoolMiniPaymentLogs);
                        }
                    }

                    checkhistorylist(mdb.getSchoolMiniPaymentLogs(mdb, soaid));

                } else {
                    mLlLoader.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                    showError(response.body().getMessage());
                }
            } else {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showError();
            }
        }

        @Override
        public void onFailure(Call<SchoolMiniPaymentLogsResponse> call, Throwable t) {
            showError();
            CommonFunctions.hideDialog();
            t.printStackTrace();
            t.getLocalizedMessage();
        }
    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    private void showViewArchiveDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .customView(R.layout.pop_filtering, false)
                .cancelable(true)
                .backgroundColorRes(R.color.zxing_transparent)
                .show();

        mDialog.getWindow().setBackgroundDrawableResource(R.color.zxing_transparent);

        View dialog = mDialog.getCustomView();

        filterwrap = dialog.findViewById(R.id.filterwrap);
        optionwrap = dialog.findViewById(R.id.optionwrap);
        editsearches = dialog.findViewById(R.id.editsearches);
        clearsearch = dialog.findViewById(R.id.clearsearch);
        monthspinType = dialog.findViewById(R.id.month);
        monthspinType.setVisibility(View.GONE);
        yearspinType = dialog.findViewById(R.id.year);
        popfilter = dialog.findViewById(R.id.filter);
        popcancel = dialog.findViewById(R.id.cancel);

        filterwrap.setVisibility(View.VISIBLE);
        optionwrap.setVisibility(View.GONE);

        createYearSpinnerAddapter();

        yearspinType.setOnItemSelectedListener(yearItemListener);

        popfilter.setOnClickListener(this);
        popcancel.setOnClickListener(this);
    }

    private void showFilterOptionDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .customView(R.layout.pop_filtering, false)
                .cancelable(true)
                .backgroundColorRes(R.color.zxing_transparent)
                .show();

        mDialog.getWindow().setBackgroundDrawableResource(R.color.zxing_transparent);

        View dialog = mDialog.getCustomView();

        filterwrap = dialog.findViewById(R.id.filterwrap);
        optionwrap = dialog.findViewById(R.id.optionwrap);
        editsearches = dialog.findViewById(R.id.editsearches);
        monthspinType = dialog.findViewById(R.id.month);
        monthspinType.setVisibility(View.GONE);
        clearsearch = dialog.findViewById(R.id.clearsearch);
        yearspinType = dialog.findViewById(R.id.year);
        popfilter = dialog.findViewById(R.id.filter);
        popcancel = dialog.findViewById(R.id.cancel);

        createYearSpinnerAddapter();

        filterwrap.setVisibility(View.GONE);
        optionwrap.setVisibility(View.VISIBLE);

        yearspinType.setOnItemSelectedListener(yearItemListener);

        clearsearch.setOnClickListener(this);
        editsearches.setOnClickListener(this);
        popfilter.setOnClickListener(this);
        popcancel.setOnClickListener(this);
    }

    //create spinner for year list
    private void createYearSpinnerAddapter() {
        try {
            ArrayAdapter<String> yearadapter;
            yearadapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_spinner_dropdown_item, yearList());
            yearadapter.setDropDownViewResource(R.layout.spinner_arrow);
            yearspinType.setAdapter(yearadapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private AdapterView.OnItemSelectedListener yearItemListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                String spinyear = parent.getItemAtPosition(position).toString();
                if (!spinyear.equals("Select Year")) {
                    year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    stryear = String.valueOf(year);
                    isyearselected = true;
                } else {
                    isyearselected = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            isyearselected = false;
        }
    };

    //create year list
    private ArrayList<String> yearList() {

        ArrayList<String> mYear = new ArrayList<String>();
        mYear.add("Select Year");
        for (int i = registrationyear; i <= currentyear; i++) {
            mYear.add(Integer.toString(i));
        }

        return mYear;
    }

    private void checkhistorylist(List<SchoolMiniPaymentLogs> data) {
        showNoInternetConnection(false);
        if (data.size() > 0) {
            emptyLayout.setVisibility(View.GONE);
            view_more_container.setVisibility(View.VISIBLE);

            stickListLV.setVisibility(View.VISIBLE);
            stickyLVAdapter.updateData(data);

            schoolpaymentlogslist = data;

            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setEnabled(true);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            stickListLV.setVisibility(View.GONE);
            view_more_container.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.bringToFront();
            mSwipeRefreshLayout.invalidate();
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    //OrderDetails
    private AdapterView.OnItemClickListener fetchItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            try {

                String paymenttxn = schoolpaymentlogslist.get(position).getPaymentTxnID();
                String billingid = schoolpaymentlogslist.get(position).getBillingID();
                double amount = schoolpaymentlogslist.get(position).getAmount();
                double merchantservicecharge = schoolpaymentlogslist.get(position).getMerchantServiceCharge();
                double customerservicecharge = schoolpaymentlogslist.get(position).getCustomerServiceCharge();
                double resellerDiscount = schoolpaymentlogslist.get(position).getResellerDiscount();
                double totalamount = schoolpaymentlogslist.get(position).getTotalAmount();
                String transactionMedium = schoolpaymentlogslist.get(position).getTransactionMedium();
                String remarks = schoolpaymentlogslist.get(position).getExtra1();

                Bundle args = new Bundle();
                args.putString("PAYMENTTXN", paymenttxn);
                args.putString("BILLINGID", billingid);
                args.putString("AMOUNT", String.valueOf(amount));
                args.putString("MERCHANTSERVICECHARGE", String.valueOf(merchantservicecharge));
                args.putString("CUSTOMERSERVICECHARGE", String.valueOf(customerservicecharge));
                args.putString("RESELLERDISCOUNT", String.valueOf(resellerDiscount));
                args.putString("TOTALAMOUNT", String.valueOf(totalamount));
                args.putString("TRANSACTIONMEDIUM", String.valueOf(transactionMedium));
                args.putString("REMARKS", CommonFunctions.replaceEscapeData(String.valueOf(remarks)));

                Log.d("vanhttp", "remarks1: =======" + remarks);

                SchoolMiniActivity.start(getViewContext(), 11, args);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewarchive: {
                limit = 0;
                showViewArchiveDialog();
                break;
            }
            case R.id.cancel: {
                mDialog.dismiss();
                break;
            }
            case R.id.filter: {
                if (isyearselected) {
                    if (mdb != null) {
                        mdb.truncateTable(mdb, SchoolMiniPaymentLogsDB.TABLE_SC_PAYMENTLOGS);

                        if (stickyLVAdapter != null) {
                            stickyLVAdapter.clear();
                        }
                        view_more_container.setVisibility(View.GONE);
                        year = Integer.parseInt(stryear);
                        limit = 0;
                        if (view_more.getText().equals("VIEW ARCHIVE")) {
                            view_more.setText("FILTER OPTIONS");
                        }
                        getSession();
                        mDialog.dismiss();
                    }
                } else {
                    showToast("Please select a date.", GlobalToastEnum.WARNING);
                }
                break;
            }
            case R.id.refresh: {
                limit = 0;
                year = Calendar.getInstance().get(Calendar.YEAR);
                stryear = String.valueOf(year);
                refresh.setEnabled(false);
                getSession();
                break;
            }
            case R.id.view_more_container: {
                limit = 0;
                if (view_more.getText().equals("VIEW ARCHIVE"))
                    showViewArchiveDialog();
                else
                    showFilterOptionDialog();
                break;
            }
            case R.id.editsearches: {
                limit = 0;
                filterwrap.setVisibility(View.VISIBLE);
                optionwrap.setVisibility(View.GONE);
                break;
            }
            case R.id.clearsearch: {
                limit = 0;
                view_more.setText("VIEW ARCHIVE");
                year = Calendar.getInstance().get(Calendar.YEAR);
                stryear = String.valueOf(year);
                isyearselected = false;
                getSession();
                mDialog.dismiss();
                break;
            }
            case R.id.filteroption: {
                limit = 0;
                showFilterOptionDialog();
                break;
            }
            case R.id.refreshnointernet: {
                limit = 0;
                refreshnointernet.setEnabled(false);
                getSession();
                break;
            }
        }
    }

    @Override
    public void onResume() {
        if (mdb != null) {
            mdb.truncateTable(mdb, SchoolMiniPaymentLogsDB.TABLE_SC_PAYMENTLOGS);
            if (stickyLVAdapter != null) {
                stickyLVAdapter.clear();
            }
            view_more_container.setVisibility(View.GONE);
            year = Calendar.getInstance().get(Calendar.YEAR);
            mSwipeRefreshLayout.setRefreshing(true);
            isfirstload = false;
            limit = 0;
            isbottomscroll = false;
            isloadmore = true;
            getSession();
        }
        super.onResume();
    }

    @Override
    public void onRefresh() {
        if (mdb != null) {
            mdb.truncateTable(mdb, SchoolMiniPaymentLogsDB.TABLE_SC_PAYMENTLOGS);
            if (stickyLVAdapter != null) {
                stickyLVAdapter.clear();
            }
            view_more_container.setVisibility(View.GONE);
            year = Calendar.getInstance().get(Calendar.YEAR);
            mSwipeRefreshLayout.setRefreshing(true);
            isfirstload = false;
            limit = 0;
            view_more.setText("VIEW ARCHIVE");
            isyearselected = false;
            isbottomscroll = false;
            isloadmore = true;
            stryear = String.valueOf(year);
            getSession();
        }
    }
    /*
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * */
    private void getSchoolStudentPaymentLogsV2() {
            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("customermobileno", mobilenumber);
                parameters.put("year", String.valueOf(year));
                parameters.put("limit", String.valueOf(limit));
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getSchoolStudentPaymentLogsV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getSchoolStudentPaymentLogsV2Object();

            } else {
                refresh.setEnabled(true);
                refreshnointernet.setEnabled(true);
                hideProgressDialog();
                showNoInternetToast();
                showNoInternetConnection(true);
            }
    }

    private void getSchoolStudentPaymentLogsV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getSchoolV2API(getViewContext())
                .getSchoolStudentPaymentLogsV2(authenticationid, sessionid, param);

        call.enqueue(getSchoolStudentPaymentLogsV2CallBack);
    }

    private Callback<GenericResponse> getSchoolStudentPaymentLogsV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            refresh.setEnabled(true);
            refreshnointernet.setEnabled(true);
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else {
                        isLoading = false;
                        isfirstload = false;

                        if (!isbottomscroll) {
                            mdb.truncateTable(mdb, SchoolMiniPaymentLogsDB.TABLE_SC_PAYMENTLOGS);
                        }

                        List<SchoolMiniPaymentLogs> schoolMiniPaymentLogsList = new Gson().fromJson(decryptedData, new TypeToken<List<SchoolMiniPaymentLogs>>() {}.getType());

                        if (schoolMiniPaymentLogsList.size() > 0) {
                            isloadmore = true;
                            for (SchoolMiniPaymentLogs schoolMiniPaymentLogs : schoolMiniPaymentLogsList) {
                                mdb.insertSchoolMiniPaymentLogs(mdb, schoolMiniPaymentLogs);
                            }
                        } else {
                            isloadmore = false;
                        }
                        checkhistorylist(mdb.getSchoolMiniPaymentLogs(mdb, soaid));
                    }
                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            } else {
                showNoInternetConnection(true);
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showNoInternetToast();
            showNoInternetConnection(true);
            refresh.setEnabled(true);
            refreshnointernet.setEnabled(true);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };
}
