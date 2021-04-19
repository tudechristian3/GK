package com.goodkredit.myapplication.fragments.soa;

import android.animation.ValueAnimator;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
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
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.SubscriberPaymentsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.PaymentSummary;
import com.goodkredit.myapplication.bean.SubscriberBillSummary;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GetBillingSummaryResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 7/25/2017.
 */

public class SubscriberPaymentsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private DatabaseHandler db;

    private SwipeRefreshLayout swipeRefreshLayout;

    private SubscriberPaymentsAdapter mAdapter;
    private RecyclerView mRvBills;

    private RelativeLayout emptyLayout;
    private ImageView imgVRefresh;
    private TextView textView11;

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
    private ImageView refresh;

    private Button mBtnMore;

    private boolean isyearselected = false;
    private boolean ismonthselected = false;

    private TextView viewarchiveemptyscreen;
    private RelativeLayout emptyvoucherfilter;
    private TextView filteroption;

    private RelativeLayout nointernetconnection;

    //UNIFIED SESSION
    private String sessionid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soa_subscriber_bills,null);

        db = new DatabaseHandler(getContext());

        imei = PreferenceUtils.getImeiId(getContext());
        userid = PreferenceUtils.getUserId(getContext());
        borrowerid = PreferenceUtils.getBorrowerId(getContext());

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());


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

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mBtnMore = view.findViewById(R.id.btn_more);
        mBtnMore.setOnClickListener(this);

        mAdapter = new SubscriberPaymentsAdapter(getContext(), db.getSubscriberPayments(db));
        mRvBills = view.findViewById(R.id.billsRecyclerView);
        mRvBills.addItemDecoration(new RecyclerViewListItemDecorator(getContext(), null));
        mRvBills.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvBills.setAdapter(mAdapter);
        mRvBills.addOnScrollListener(scrollListener);

        emptyLayout = view.findViewById(R.id.emptyLayout);
        view.findViewById(R.id.viewarchive).setOnClickListener(this);
        refresh = view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        textView11 = view.findViewById(R.id.textView11);
        textView11.setText("No payments made for this month.");

        emptyvoucherfilter = view.findViewById(R.id.emptyvoucherfilter);
        viewarchiveemptyscreen = view.findViewById(R.id.viewarchive);
        filteroption = view.findViewById(R.id.filteroption);
        filteroption.setOnClickListener(this);

        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        view.findViewById(R.id.refreshnointernet).setOnClickListener(this);

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        currentyear = Calendar.getInstance().get(Calendar.YEAR);
        currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

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

        updateList(db.getSubscriberPayments(db));

        if (CommonVariables.PAYMENTS_FIRSTLOAD) {
            getSession();
            CommonVariables.PAYMENTS_FIRSTLOAD = false;
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && getView() != null) {
            if (CommonVariables.PAYMENTS_FIRSTLOAD) {
                getSession();
                CommonVariables.PAYMENTS_FIRSTLOAD = false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null && db != null) {
            updateList(db.getSubscriberPayments(db));
        }
    }

    private void getSession() {

        showEmpty();
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("","Fetching payments.. Please wait...");
//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(sessionCallback);

            getBillingSummary();

        } else
            showNoInternet();
//            showError(getString(R.string.generic_internet_error_message));

    }

//    private Callback<String> sessionCallback = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                    mLlLoader.setVisibility(View.GONE);
//                    swipeRefreshLayout.setRefreshing(false);
//                } else if (responseData.equals("error")) {
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    mLlLoader.setVisibility(View.GONE);
//                    swipeRefreshLayout.setRefreshing(false);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    mLlLoader.setVisibility(View.GONE);
//                    swipeRefreshLayout.setRefreshing(false);
//                } else {
//                    session = response.body().toString();
//
//                    getBillingSummary();
//                }
//            } else {
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                mLlLoader.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            mLlLoader.setVisibility(View.GONE);
//            swipeRefreshLayout.setRefreshing(false);
//        }
//    };

    private void getBillingSummary() {
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
    }

    private Callback<GetBillingSummaryResponse> getBillingSummaryResponseCallback = new Callback<GetBillingSummaryResponse>() {
        @Override
        public void onResponse(Call<GetBillingSummaryResponse> call, Response<GetBillingSummaryResponse> response) {
            swipeRefreshLayout.setRefreshing(false);
            hideProgressDialog();
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String responseData = response.body().getStatus();
                if (responseData.equals("000")) {

                    Logger.debug("vanhttp", "getbillings1");

                    java.util.List<SubscriberBillSummary> bills = response.body().getSubscriberBillSummaryList();
                    db.deleteSubscriberBillSummary(db);

                    if(bills.size() > 0 && bills != null){
                        for (SubscriberBillSummary bill : bills) {
                            db.insertSubscriberBillSummary(db, bill);
                        }
                    }

                    List<PaymentSummary> payments = response.body().getPaymentSummaryList();
                    db.deleteSubscriberPaymentSummary(db);
                    CommonFunctions.log(payments.size() + "size sa payments.");

                    if(payments.size() > 0 && payments != null){
                        for (PaymentSummary payment : payments){
                            db.insertSubscriberPaymentSummary(db, payment);
                        }
                    }

                    updateList(db.getSubscriberPayments(db));

//                    if (ismonthselected == true && isyearselected == true) {
//                        viewarchiveemptyscreen.setText("FILTER OPTIONS");
//                        emptyvoucherfilter.setVisibility(View.VISIBLE);
//                        emptyLayout.setVisibility(View.GONE);
//                    } else {
//                        viewarchiveemptyscreen.setText("VIEW ARCHIVE");
//                        emptyLayout.setVisibility(View.VISIBLE);
//                        emptyvoucherfilter.setVisibility(View.GONE);
//                    }

                } else {
                    showEmptyLayout();
                    showToast("" + response.body().getMessage(), GlobalToastEnum.NOTICE);
                }
            } else {
                showNoInternet();
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<GetBillingSummaryResponse> call, Throwable t) {
            hideProgressDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    private void showViewArchiveDialog() {
        mDialog = new MaterialDialog.Builder(getContext())
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
        spinType = dialog.findViewById(R.id.month);
        spinType1 = dialog.findViewById(R.id.year);
        popfilter = dialog.findViewById(R.id.filter);
        popcancel = dialog.findViewById(R.id.cancel);

        filterwrap.setVisibility(View.VISIBLE);
        optionwrap.setVisibility(View.GONE);

        createMonthSpinnerAddapter();
        createYearSpinnerAddapter();

        spinType1.setOnItemSelectedListener(yearItemListener);
        spinType.setOnItemSelectedListener(monthItemListener);

        popfilter.setOnClickListener(this);
        popcancel.setOnClickListener(this);

    }

    private void showFilterOptionDialog() {
        mDialog = new MaterialDialog.Builder(getContext())
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
        spinType = dialog.findViewById(R.id.month);
        spinType1 = dialog.findViewById(R.id.year);
        popfilter = dialog.findViewById(R.id.filter);
        popcancel = dialog.findViewById(R.id.cancel);

        createMonthSpinnerAddapter();
        createYearSpinnerAddapter();

        filterwrap.setVisibility(View.GONE);
        optionwrap.setVisibility(View.VISIBLE);

        spinType.setOnItemSelectedListener(monthItemListener);
        spinType1.setOnItemSelectedListener(yearItemListener);

        clearsearch.setOnClickListener(this);
        editsearches.setOnClickListener(this);
        popfilter.setOnClickListener(this);
        popcancel.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        getSession();
    }

    @Override
    public void onClick(View v) {
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
                if (mBtnMore.getText().equals("VIEW ARCHIVE")) {
                    mBtnMore.setText("FILTER OPTIONS");
                }
                if (isyearselected && ismonthselected) {
                    getSession();
                    mDialog.dismiss();
                } else {
                    ((BaseActivity) getActivity()).showToast("Please select a date.", GlobalToastEnum.WARNING);
                }
                break;
            }
            case R.id.refreshnointernet: {

            }
            case R.id.refresh: {
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                getSession();
                break;
            }
            case R.id.btn_more: {
                if (mBtnMore.getText().equals("VIEW ARCHIVE"))
                    showViewArchiveDialog();
                else
                    showFilterOptionDialog();
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
                ismonthselected = false;
                getSession();
                mDialog.dismiss();
                break;
            }
            case R.id.filteroption: {
                showFilterOptionDialog();
                break;
            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mLoaderTimer.cancel();
    }

    private void updateList(List<PaymentSummary> data) {
        if (data.size() > 0) {
            showHasData();
            mBtnMore.setVisibility(View.VISIBLE);
            mRvBills.setVisibility(View.VISIBLE);
            mAdapter.updateData(data);
            if (isyearselected && ismonthselected) {
                mBtnMore.setText("FILTER OPTIONS");
            }else {
                mBtnMore.setText("VIEW ARCHIVE");
            }
        } else {
            mBtnMore.setVisibility(View.GONE);
            if (isyearselected && ismonthselected) {
                showEmptyFiltered();
            }
            else {
                showEmptyLayout();
                mRvBills.setVisibility(View.GONE);
            }
        }
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (db.getSubscriberBills(db).size() > 0) {
                mBtnMore.setVisibility(View.VISIBLE);
                if (isyearselected && ismonthselected) {
                    mBtnMore.setText("FILTER OPTIONS");
                } else {
                    mBtnMore.setText("VIEW ARCHIVE");
                }
            }
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

    private AdapterView.OnItemSelectedListener monthItemListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                if (position > 0) {
                    String monthstring = parent.getItemAtPosition(position).toString();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new SimpleDateFormat("MMM").parse(monthstring));
                    month = cal.get(Calendar.MONTH) + 1;
                    ismonthselected = month > 0;
                } else {
                    ismonthselected = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            ismonthselected = false;
        }
    };

    private void showEmptyLayout() {
        emptyLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.GONE);
    }

    private void showEmpty() {
        emptyLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.GONE);
    }


    private void showEmptyFiltered() {
        emptyLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.VISIBLE);
        nointernetconnection.setVisibility(View.GONE);
    }

    private void showNoInternet() {
        emptyLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.VISIBLE);

    }

    private void showHasData() {
        emptyLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        emptyvoucherfilter.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.GONE);

    }
}
