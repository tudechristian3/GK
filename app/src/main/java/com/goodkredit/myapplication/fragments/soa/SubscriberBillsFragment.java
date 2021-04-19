package com.goodkredit.myapplication.fragments.soa;

import android.animation.ValueAnimator;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.goodkredit.myapplication.adapter.SubscriberBillsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.PaymentSummary;
import com.goodkredit.myapplication.bean.SubscriberBillSummary;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
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
 * Created by Ban_Lenovo on 7/24/2017.
 */

public class SubscriberBillsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private DatabaseHandler db;

    private SwipeRefreshLayout swipeRefreshLayout;

    private SubscriberBillsAdapter mAdapter;
    private RecyclerView mRvBills;

    private ImageView imgVRefresh;

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
    private LinearLayout optionwrap;
    private ScrollView filterwrap;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soa_subscriber_bills, container, false);

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

        mLlLoader = view.findViewById(R.id.loaderLayout);
        mTvFetching = view.findViewById(R.id.fetching);
        mTvWait = view.findViewById(R.id.wait);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mBtnMore = view.findViewById(R.id.btn_more);
        mBtnMore.setOnClickListener(this);

        mAdapter = new SubscriberBillsAdapter(getContext(), db.getSubscriberBills(db));
        mRvBills = view.findViewById(R.id.billsRecyclerView);
        mRvBills.addItemDecoration(new RecyclerViewListItemDecorator(getContext(), null));
        mRvBills.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvBills.setAdapter(mAdapter);
        mRvBills.addOnScrollListener(scrollListener);

        emptyLayout = view.findViewById(R.id.emptyLayout);
        viewarchive = view.findViewById(R.id.viewarchive);
        refresh = view.findViewById(R.id.refresh);
        emptyvoucherfilter = view.findViewById(R.id.emptyvoucherfilter);
        filteroption = view.findViewById(R.id.filteroption);
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet = view.findViewById(R.id.refreshnointernet);
        textView11 = view.findViewById(R.id.textView11);

        textView11.setText("No payments made for this month.");

        viewarchive.setOnClickListener(this);
        refresh.setOnClickListener(this);
        filteroption.setOnClickListener(this);
        refreshnointernet.setOnClickListener(this);

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        currentyear = Calendar.getInstance().get(Calendar.YEAR);
        currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };


        updateList(db.getSubscriberBills(db));

        if (CommonVariables.BILLS_FIRSTLOAD) {
            getSession();
            CommonVariables.BILLS_FIRSTLOAD = false;
        }

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

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && getView() != null) {
            if (CommonVariables.BILLS_FIRSTLOAD) {
                getSession();
                CommonVariables.BILLS_FIRSTLOAD = false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null && db != null) {
            updateList(db.getSubscriberBills(db));
        }
    }

    private void updateList(List<SubscriberBillSummary> data) {
        if (data.size() > 0) {
            mBtnMore.setVisibility(View.VISIBLE);
            showHasData();
            mRvBills.setVisibility(View.VISIBLE);
            mAdapter.updateData(data);
            if (isyearselected)
                mBtnMore.setText("FILTER OPTIONS");
            else
                mBtnMore.setText("VIEW ARCHIVE");
        } else {
            mBtnMore.setVisibility(View.GONE);
            if (isyearselected)
                showEmptyFiltered();
            else
                showEmptyLayout();
            mRvBills.setVisibility(View.GONE);
        }

    }

    private void getSession() {
        mLoaderTimer.cancel();
        mLoaderTimer.start();

        mTvFetching.setText("Fetching bills.");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        if (CommonFunctions.getConnectivityStatus(getContext()) > 0){
            getBillingSummary();

        } else {
            showNoInternet();
            mLlLoader.setVisibility(View.GONE);
            showError(getString(R.string.generic_internet_error_message));
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
            swipeRefreshLayout.setRefreshing(false);
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

                    updateList(db.getSubscriberBills(db));

//                    if (isyearselected) {
//                        if (bills.size() > 0)
//                            showHasData();
//                        else {
//                            viewarchiveemptyscreen.setText("FILTER OPTIONS");
//                            showEmptyFiltered();
//                        }
//                    }

                } else if (responseData.contains("<!DOCTYPE html>")) {
                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    showToast("" + response.body().getMessage(), GlobalToastEnum.NOTICE);
                }
            } else {
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<GetBillingSummaryResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    };


    @Override
    public void onRefresh() {
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        getSession();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLoaderTimer.cancel();
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
                if (mBtnMore.getText().equals("FILTER OPTIONS")) {
                    mBtnMore.setText("VIEW ARCHIVE");
                }
                if (isyearselected) {
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
                if (isyearselected) {
                    mBtnMore.setText("FILTER OPTIONS");
                } else {
                    mBtnMore.setText("VIEW ARCHIVE");
                }
            }
        }
    };

    private void showEmptyLayout() {
        emptyLayout.setVisibility(View.VISIBLE);
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
