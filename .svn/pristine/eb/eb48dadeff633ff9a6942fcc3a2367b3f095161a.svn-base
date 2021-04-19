package com.goodkredit.myapplication.fragments.soa;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.soa.UnpaidBillsAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.PaymentSummary;
import com.goodkredit.myapplication.bean.SubscriberBillSummary;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.fragments.StatementOfAccountFragment;
import com.goodkredit.myapplication.fragments.transactions.BorrowingsFragment;
import com.goodkredit.myapplication.model.publicsponsor.PublicSponsor;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GetBillingSummaryResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 10/10/2017.
 */

public class Billings extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ExpandableListView.OnChildClickListener,View.OnClickListener {
    private AQuery aq;
    private DatabaseHandler db;
    private ProgressDialog mProgressDialog;

    private ExpandableListView listView;
    private UnpaidBillsAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    public static boolean isActive = false;
    public static UnpaidBillsAdapter staticAdapter;
    public static SwipeRefreshLayout staticRefreshLayout;

    private ArrayList<String> title;
    private List<SubscriberBillSummary> bills ;
    private List<PaymentSummary> payments;
    private HashMap<String, ArrayList<SubscriberBillSummary>> details;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private int currentyear = 0;//make this not changeable for (filter condition)
    private int currentmonth = 0; //make this not changeable for (filter condition)
    private int year;
    private int month;
    private int registrationyear;
    private int registrationmonth;
    private String dateregistered = "";

    private MaterialDialog mDialog;
    private ScrollView filterwrap;
    private LinearLayout optionwrap;
    private TextView editsearches;
    private TextView clearsearch;
    private Spinner spinType;
    private Spinner spinType1;
    private TextView popfilter;
    private TextView popcancel;
    private Button mBtnMore;
    private boolean isyearselected = false;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index = "";
    private String authenticationid= "";
    private String keyEncryption="";
    private String param="";


    //Empty layout variables GLOBAL
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private TextView viewarchive;
    private ImageView box;
    private ImageView refresh;

    //Empty Filtering variables GLOBAL
    private RelativeLayout emptyvoucherfilter;
    private TextView textView3;
    private TextView filteroption;


    //No Internet connection layout variables GLOBAL
    private RelativeLayout nointernetconnection;
    private Button refreshnointernet;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_unpaid_bills, container, false);

            aq = new AQuery(getViewContext());
            db = new DatabaseHandler(getViewContext());
            details = new HashMap<>();
            title = new ArrayList<>();
            bills = new ArrayList<>();
            payments = new ArrayList<>();
            title.clear();
            details.clear();

            //UNIFIED SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            mProgressDialog = new ProgressDialog(getViewContext());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setInverseBackgroundForced(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage("Fetching billings\nPlease wait...");

            refreshLayout = view.findViewById(R.id.swipe_container);
            refreshLayout.setOnRefreshListener(this);

            listView = view.findViewById(R.id.list);
            listView.setOnGroupClickListener(null);
            listView.setOnChildClickListener(this);
            listView.setGroupIndicator(null);

            View view1 = inflater.inflate(R.layout.footer_expandable, listView, false);
            LinearLayout footer_layout = view1.findViewById(R.id.footer_layout);
            mBtnMore = view1.findViewById(R.id.btn_more);
            mBtnMore.setOnClickListener(this);
            listView.addFooterView(footer_layout);

            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            currentyear = Calendar.getInstance().get(Calendar.YEAR);
            currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

            imei = PreferenceUtils.getImeiId(getContext());
            userid = PreferenceUtils.getUserId(getContext());
            borrowerid = PreferenceUtils.getBorrowerId(getContext());

            refresh = view.findViewById(R.id.refresh);
            refresh.setOnClickListener(this);
            refresh.setEnabled(false);

            //get account information
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    dateregistered = cursor.getString(cursor.getColumnIndex("dateregistration"));
                    String CurrentString = dateregistered;
                    String[] separated = CurrentString.split("-");
                    registrationyear = Integer.parseInt(separated[0]);
                    registrationmonth = Integer.parseInt(separated[1]);

                } while (cursor.moveToNext());
            }
            cursor.close();

            filteroption = view.findViewById(R.id.filteroption);
            viewarchive = view.findViewById(R.id.viewarchive);
            emptyvoucherfilter = view.findViewById(R.id.emptyvoucherfilter);
            refresh = view.findViewById(R.id.refresh);
            nointernetconnection = view.findViewById(R.id.nointernetconnection);
            refreshnointernet = view.findViewById(R.id.refreshnointernet);
            emptyLayout = view.findViewById(R.id.emptyLayout);
            textView11 = view.findViewById(R.id.textView11);

            textView11.setText("No bills yet.");

            filteroption.setOnClickListener(this);
            refresh.setOnClickListener(this);
            viewarchive.setOnClickListener(this);
            refreshnointernet.setOnClickListener(this);

            if (!db.getSubscriberBilling(db,"CURRENT").isEmpty()) {
                title.add("CURRENT BILLING");
                details.put("CURRENT BILLING", db.getSubscriberBilling(db,"CURRENT"));
            }
            if (!db.getSubscriberBilling(db,"PREVIOUS").isEmpty()) {
                title.add("PREVIOUS BILLING/S");
                details.put("PREVIOUS BILLING/S", db.getSubscriberBilling(db,"PREVIOUS"));
            }
            adapter = new UnpaidBillsAdapter(getViewContext(), title, details);
            listView.setAdapter(adapter);

            staticAdapter = adapter;
            staticRefreshLayout = refreshLayout;

            mLlLoader = view.findViewById(R.id.loaderLayout);
            mTvFetching = view.findViewById(R.id.fetching);
            mTvWait = view.findViewById(R.id.wait);


            final ImageView backgroundOne = view.findViewById(R.id.background_one);
            final ImageView backgroundTwo = view.findViewById(R.id.background_two);

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

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (listView.getChildAt(0) != null) {
                        refreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                    }
                }
            });

            if(CommonVariables.BILLS_FIRSTLOAD){
                getSession();
                CommonVariables.BILLS_FIRSTLOAD = false;
            }


        return view;
    }

    @Override
    public void onRefresh() {
        getSession();
    }

    @Override
    public void onPause() {
        isActive = false;
        super.onPause();
    }

    @Override
    public void onStop() {
        mLoaderTimer.cancel();
        super.onStop();
    }

    @Override
    public void onResume() {
        fetchBillings();
        super.onResume();
    }

    @Override
    public boolean onChildClick(@NonNull ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }

    void fetchBillings() {
        Map<String, Object> params = new HashMap<>();
        params.put("userid", PreferenceUtils.getUserId(getViewContext()));
        params.put("usermobile", PreferenceUtils.getUserId(getViewContext()));
        params.put("imei", PreferenceUtils.getImeiId(getViewContext()));
        params.put("borrowerid", PreferenceUtils.getBorrowerId(getViewContext()));

        title.clear();
        details.clear();

        if (!db.getSubscriberBilling(db,"CURRENT").isEmpty()) {
            title.add("CURRENT BILLING");
            details.put("CURRENT BILLING", db.getSubscriberBilling(db,"CURRENT"));

        }
        if (!db.getSubscriberBilling(db,"PREVIOUS").isEmpty()) {
            title.add("PREVIOUS BILLING/S");
            details.put("PREVIOUS BILLING/S", db.getSubscriberBilling(db,"PREVIOUS"));
        }
        adapter.update(title, details);
        //  fetchHelpTopics();
        refreshLayout.setRefreshing(false);

        if (!db.getSubscriberBilling(db,"PREVIOUS").isEmpty() || !db.getSubscriberBilling(db,"CURRENT").isEmpty() ) {
            showHasData();
            if (isyearselected)
                mBtnMore.setText("FILTER OPTIONS");
            else
                mBtnMore.setText("VIEW ARCHIVE");
        } else {
            if (isyearselected) {
                showEmptyFiltered();
            }else {
                showEmptyLayout();
            }
        }
    }

    private void getSession() {
        refresh.setEnabled(false);
        mLoaderTimer.cancel();
        mLoaderTimer.start();

        mTvFetching.setText("Fetching bills.");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        if (CommonFunctions.getConnectivityStatus(getContext()) > 0){
            //getBillingSummary();
            getBillingSummaryV2();
        } else {
            refresh.setEnabled(true);
            showNoInternet();
            mLlLoader.setVisibility(View.GONE);
            //showError(getString(R.string.generic_internet_error_message));
        }
    }

    private void getBillingSummary() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            Call<GetBillingSummaryResponse> call = RetrofitBuild.getSoaApiService(getContext())
                    .getBillingSummary(imei,
                            sessionid,
                            authcode,
                            userid,
                            borrowerid,
                            year,
                            month);

            call.enqueue(getBillingSummaryResponseCallback);
        } else {
            showNoInternet();
            mLlLoader.setVisibility(View.GONE);
            showError(getString(R.string.generic_internet_error_message));
        }
    }

    private Callback<GetBillingSummaryResponse> getBillingSummaryResponseCallback = new Callback<GetBillingSummaryResponse>() {
        @Override
        public void onResponse(Call<GetBillingSummaryResponse> call, Response<GetBillingSummaryResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            refreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String responseData = response.body().getStatus();
                if (responseData.equals("000")) {

                    List<SubscriberBillSummary> bills = response.body().getSubscriberBillSummaryList();
                    db.deleteSubscriberBillSummary(db);
                    for (SubscriberBillSummary bill : bills)
                        db.insertSubscriberBillSummary(db, bill);

                    List<PaymentSummary> payments = response.body().getPaymentSummaryList();
                    db.deleteSubscriberPaymentSummary(db);
                    for (PaymentSummary payment : payments)
                        db.insertSubscriberPaymentSummary(db, payment);

                    fetchBillings();

                    Logger.debug("MARYANN","SUCCESSS");

                } else if (responseData.contains("<!DOCTYPE html>")) {
                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    showToast("" + response.body().getMessage(), GlobalToastEnum.NOTICE);
                }
            } else {
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                showEmptyLayout();
            }
        }

        @Override
        public void onFailure(Call<GetBillingSummaryResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            mLlLoader.setVisibility(View.GONE);
            refreshLayout.setRefreshing(false);
        }
    };


    @Override
    public void onClick(View v) {

        try{
            switch (v.getId()) {
                case R.id.viewarchive: {
                    showViewArchiveDialog();
                    break;
                }
                case R.id.cancel: {
                    mDialog.dismiss();
                    break;
                }
                case R.id.filter: {
                    if (mBtnMore.getText().equals("FILTER OPTIONS")) {
                        mBtnMore.setText("VIEW ARCHIVE");
                    }
                    if (isyearselected) {
                        getSession();
                        mDialog.dismiss();
                    } else {
                        showToast("Please select a date.", GlobalToastEnum.WARNING);
                    }
                    break;
                }

                case R.id.refresh: case R.id.refreshnointernet :{
                    hidealllayouts();
                    year = Calendar.getInstance().get(Calendar.YEAR);
                    month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                    isyearselected = false;
                    getSession();

                    break;
                }
                case R.id.btn_more: {
                    if (mBtnMore.getText().toString().equals("FILTER OPTIONS"))
                        showFilterOptionDialog();
                    else
                        showViewArchiveDialog();
                    break;
                }
                case R.id.editsearches: {
                    filterwrap.setVisibility(View.VISIBLE);
                    optionwrap.setVisibility(View.GONE);
                    break;
                }
                case R.id.clearsearch: {
                    year = Calendar.getInstance().get(Calendar.YEAR);
                    month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                    isyearselected = false;
                    getSession();
                    mDialog.dismiss();
                    break;
                }
                case R.id.filteroption: {
                    showFilterOptionDialog();
                    break;
                }
            }
        }catch (Exception e){
            Logger.debug("MARYANN","ERRORCLICK"+e.toString());
        }

    }

    private void showViewArchiveDialog() {
        mDialog = new MaterialDialog.Builder(getContext())
                .customView(R.layout.pop_filtering, false)
                .cancelable(false)
                .backgroundColorRes(R.color.zxing_transparent)
                .show();

        mDialog.getWindow().setBackgroundDrawableResource(R.color.zxing_transparent);

        View dialog = mDialog.getCustomView();

        filterwrap = dialog.findViewById(R.id.filterwrap);
        optionwrap = dialog.findViewById(R.id.optionwrap);
        editsearches = dialog.findViewById(R.id.editsearches);
        clearsearch = dialog.findViewById(R.id.clearsearch);
        spinType = dialog.findViewById(R.id.month);
        spinType1 = dialog.findViewById(R.id.year);
        popfilter = dialog.findViewById(R.id.filter);
        popcancel = dialog.findViewById(R.id.cancel);

        filterwrap.setVisibility(View.VISIBLE);
        optionwrap.setVisibility(View.GONE);

        createMonthSpinnerAddapter();
        createYearSpinnerAddapter();

        spinType.setVisibility(View.GONE);
        spinType1.setOnItemSelectedListener(yearItemListener);

        popfilter.setOnClickListener(this);
        popcancel.setOnClickListener(this);

    }

    private void showFilterOptionDialog() {
        mDialog = new MaterialDialog.Builder(getContext())
                .customView(R.layout.pop_filtering, false)
                .cancelable(false)
                .backgroundColorRes(R.color.zxing_transparent)
                .show();

        mDialog.getWindow().setBackgroundDrawableResource(R.color.zxing_transparent);

        View dialog = mDialog.getCustomView();

        filterwrap = dialog.findViewById(R.id.filterwrap);
        optionwrap = dialog.findViewById(R.id.optionwrap);
        editsearches = dialog.findViewById(R.id.editsearches);
        clearsearch = dialog.findViewById(R.id.clearsearch);
        spinType = dialog.findViewById(R.id.month);
        spinType1 = dialog.findViewById(R.id.year);
        popfilter = dialog.findViewById(R.id.filter);
        popcancel = dialog.findViewById(R.id.cancel);

        createMonthSpinnerAddapter();
        createYearSpinnerAddapter();

        filterwrap.setVisibility(View.GONE);
        optionwrap.setVisibility(View.VISIBLE);

        spinType.setVisibility(View.GONE);
        spinType1.setOnItemSelectedListener(yearItemListener);

        clearsearch.setOnClickListener(this);
        editsearches.setOnClickListener(this);
        popcancel.setOnClickListener(this);
        popfilter.setOnClickListener(this);

    }


    private AdapterView.OnItemSelectedListener yearItemListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                String spinyear = parent.getItemAtPosition(position).toString();
                if (!spinyear.equals("Select Year")) {
                    year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    createMonthSpinnerAddapter();
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
    //create spinner for month list
    private void createMonthSpinnerAddapter() {
        try {
            ArrayAdapter<String> monthadapter;
            ArrayList<String> spinmonthlist = new ArrayList<String>();
            spinmonthlist = monthlist();
            monthadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinmonthlist);
            monthadapter.setDropDownViewResource(R.layout.spinner_arrow);
            spinType.setAdapter(monthadapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //create spinner for year list
    private void createYearSpinnerAddapter() {
        try {
            ArrayAdapter<String> yearadapter;
            yearadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, yearList());
            yearadapter.setDropDownViewResource(R.layout.spinner_arrow);
            spinType1.setAdapter(yearadapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //make the number month to month name
    private ArrayList<String> monthlist() {


        int[] months = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Select Month");

        for (int i = 0; i < months.length; i++) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            cal.set(Calendar.MONTH, months[i]);
            String month_name = month_date.format(cal.getTime());
            if (registrationyear == year && year != currentyear) { //meaning we need to filter the month range of the borrower
                if (i >= registrationmonth - 1 && (registrationyear - year) == 0) {
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

    //create year list
    private ArrayList<String> yearList() {

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Select Year");
        for (int i = registrationyear; i <= currentyear; i++) {
            xVals.add(Integer.toString(i));
        }

        return xVals;
    }

    void showEmptyLayout() {
        emptyLayout.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.GONE);
    }

    private void showEmptyFiltered() {
        emptyLayout.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.VISIBLE);
        nointernetconnection.setVisibility(View.GONE);
    }

    private void showNoInternet() {
        emptyLayout.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.VISIBLE);

    }

    private void showHasData() {
        refreshLayout.setVisibility(View.VISIBLE);
        emptyLayout.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.GONE);
    }
    private void hidealllayouts(){
        emptyLayout.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.GONE);
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */
    private void getBillingSummaryV2(){

            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("year", String.valueOf(year));
                parameters.put("month", String.valueOf(month));

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getBillingSummary");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getBillingSummaryV2Object(getBillingSummaryResponseCallbackV2);

            }else{
                refresh.setEnabled(true);
                showNoInternet();
                mLlLoader.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                showErrorToast();
            }
    }
    private void getBillingSummaryV2Object( Callback<GenericResponse> getBillingSummaryCallback) {
        Call<GenericResponse> call = RetrofitBuilder.getSoaV2API(getContext())
                .getBillingSummary(authenticationid,sessionid,param);
        call.enqueue(getBillingSummaryCallback);
    }
    private Callback<GenericResponse> getBillingSummaryResponseCallbackV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            refreshLayout.setRefreshing(false);
            refresh.setEnabled(true);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());

                String responseData = response.body().getStatus();
                if (responseData.equals("000")) {

                    String decrypteData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    db.deleteSubscriberBillSummary(db);
                    db.deleteSubscriberPaymentSummary(db);

                    String billss = CommonFunctions.parseJSON(decrypteData,"bills");
                    String paymentlogs = CommonFunctions.parseJSON(decrypteData,"paymentlogs");

                    Logger.debug("okhttp","DATA - payment logs : "+paymentlogs);
                    Logger.debug("okhttp","DATA  - bills: "+billss);


                    bills =  new Gson().fromJson(billss,
                            new TypeToken<List<SubscriberBillSummary>>() {}.getType());

                    payments =  new Gson().fromJson(paymentlogs,
                            new TypeToken<List<PaymentSummary>>() {}.getType());

                    if(bills != null || payments != null){
                        if(bills.size() > 0){
                            for (SubscriberBillSummary bill : bills)
                                db.insertSubscriberBillSummary(db, bill);
                        }
                        if(payments.size() > 0){
                            for (PaymentSummary payment : payments)
                                db.insertSubscriberPaymentSummary(db, payment);
                        }
                    }

                    fetchBillings();

                    Logger.debug("okhttp","SUCCESSS");
                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else if (responseData.contains("<!DOCTYPE html>")) {
                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                    showNoInternet();
                } else {
                    showEmptyLayout();
                    showToast(decryptedMessage, GlobalToastEnum.NOTICE);
                }
            } else {
                showNoInternet();
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            refresh.setEnabled(true);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            mLlLoader.setVisibility(View.GONE);
            refreshLayout.setRefreshing(false);
            showNoInternet();
        }
    };

}
