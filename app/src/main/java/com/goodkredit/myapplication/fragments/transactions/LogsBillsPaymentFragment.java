package com.goodkredit.myapplication.fragments.transactions;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.transactions.LogsBillsPaymentRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.BillsPaymentLogs;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GetBillsPaymentLogsResponse;
import com.goodkredit.myapplication.responses.SendEmailForBillsPaymentResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User-PC on 8/1/2017.
 */

/**
* Modified by User-Ronel on October 2019
* */

public class LogsBillsPaymentFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private LogsBillsPaymentRecyclerAdapter mAdapter;

    private String borrowerid = "";
    private String imei = "";
    //    private String sessionid = "";
    private String userid = "";
    private String authcode;

    private String emailaddress = "";
    private String currency = "";
    private String transactionrefno = "";
    private String transactiondate = "";
    private String biller = "";
    private String amount = "";
    private String accountno = "";
    private String accountname = "";
    private String billertransactionno = "";

    private double totalservicecharge = 0;
    private String discount = "";
    private double totalpaid = 0;

    private Calendar c;
    private DatabaseHandler db;
    private List<BillsPaymentLogs> mGridData;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView pagetitle;
    private RelativeLayout logowatermarklayout;

    private RelativeLayout emptyLayout;
    private RelativeLayout emptyvoucherfilter;
    private RelativeLayout nointernetconnection;
    private Button refreshnointernet;
    private ImageView imgVRefresh;
    private TextView textView11;
    private TextView filteroption;

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
    private boolean ismonthselected = false;

    private MaterialDialog emailDialog;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String sendEmailIndex;
    private String sendEmailAuthenticationid;
    private String sendEmailKeyEncryption;
    private String sendEmailParam;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions_logs_bills_payment, container, false);

        db = new DatabaseHandler(getViewContext());

        //UNIFIED SESSIONID
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        pagetitle = view.findViewById(R.id.pagetitle);
        logowatermarklayout = view.findViewById(R.id.logowatermarklayout);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mBtnMore = view.findViewById(R.id.btn_more);
        mBtnMore.setOnClickListener(this);

        mGridData = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view_logs_bills_payment);

        emptyLayout = view.findViewById(R.id.emptyLayout);
        emptyvoucherfilter = view.findViewById(R.id.emptyvoucherfilter);
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet = view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);
        filteroption = view.findViewById(R.id.filteroption);
        filteroption.setOnClickListener(this);

        mLlLoader = view.findViewById(R.id.loaderLayout);
        mTvFetching = view.findViewById(R.id.fetching);
        mTvWait = view.findViewById(R.id.wait);

        //initialize date
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        currentyear = Calendar.getInstance().get(Calendar.YEAR);
        currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

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

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        //get account information
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new LogsBillsPaymentRecyclerAdapter(getViewContext(), mGridData, LogsBillsPaymentFragment.this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnScrollListener(scrollListener);


        view.findViewById(R.id.viewarchive).setOnClickListener(this);
        view.findViewById(R.id.refresh).setOnClickListener(this);
        textView11 = view.findViewById(R.id.textView11);
        textView11.setText("No bills payment for this month.");

        getSession();

        return view;
    }

    private void getSession() {

        showNoInternetConnection(false);

        mLoaderTimer.cancel();
        mLoaderTimer.start();

        mTvFetching.setText("Fetching bills payment.");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getBillsPaymentLogs(getBillsPaymentLogsSession);
            getBillsPaymentLogsV2();

        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
            showNoInternetConnection(true);
            //showError(getString(R.string.generic_internet_error_message));
        }

    }


    private void getBillsPaymentLogs(Callback<GetBillsPaymentLogsResponse> getPrepaidLogsCallback) {
        Call<GetBillsPaymentLogsResponse> getTransaction = RetrofitBuild.getBillsPaymentLogsService(getViewContext())
                .getBillsPaymentLogsCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        String.valueOf(year),
                        String.valueOf(month));
        getTransaction.enqueue(getPrepaidLogsCallback);
    }

    private final Callback<GetBillsPaymentLogsResponse> getBillsPaymentLogsSession = new Callback<GetBillsPaymentLogsResponse>() {

        @Override
        public void onResponse(Call<GetBillsPaymentLogsResponse> call, Response<GetBillsPaymentLogsResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    db.truncateTable(db, DatabaseHandler.BILLS_PAYMENT_LOGS);
                    List<BillsPaymentLogs> billspaymentlogs = response.body().getBillsPaymentLogs();
                    for (BillsPaymentLogs bills : billspaymentlogs) {
                        db.insertBillsPaymentLogs(db, bills);
                    }
                    updateList(db.getBillsPaymentLogsDetails(db));
//                    mAdapter.setBillsPaymentLogsData(db.getBillsPaymentLogsDetails(db));
                }
            } else {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<GetBillsPaymentLogsResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    @Override
    public void onRefresh() {
        db.truncateTable(db,DatabaseHandler.BILLS_PAYMENT_LOGS);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_body, new LogsBillsPaymentFragment())
                .commit();
    }

    public void sendEmailForBillsPayment(BillsPaymentLogs item) {
        emailaddress = db.getEmail(db);
        currency = db.getCurrencyID(db).equals("") ? "." : db.getCurrencyID(db);

        transactionrefno = item.getGKTransactionNo();
        transactiondate = item.getDateTimeCompleted();
        biller = item.getBillerName();
        amount = String.valueOf(item.getAmountPaid());
        accountno = item.getAccountNo();
        accountname = item.getAccountName();
        billertransactionno = item.getBillerTransactionNo();

        totalservicecharge = item.getOtherCharges() + item.getCustomerServiceCharge();
        discount = item.getExtra2().equals("") || item.getExtra2().equals(".") || item.getExtra2().isEmpty() ? "." : item.getExtra2();
        totalpaid = item.getTotalAmountPaid();

        // Set `EditText` to `dialog`. You can add `EditText` from `xml` too.
        final EditText input = new EditText(getActivity());
        input.setHint("Add Email Address");
        input.setEnabled(false);
        input.setText(emailaddress);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            input.setTooltipText("Navigate to profile to edit your email address");
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        input.setLayoutParams(lp);


        emailDialog = new MaterialDialog.Builder(getViewContext())
                .autoDismiss(false)
                .cancelable(false)
                .title("Email")
                .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                .customView(input,true)

                .positiveText("Send")
                .negativeText("Cancel")
                .onPositive((dialog, which) -> {
                    if (emailaddress.length() > 0) {
                        getCreateSession();
                    } else {
                        showToast("Please input an email address", GlobalToastEnum.WARNING);
                    }
                }).onNegative((dialog, which) -> dialog.dismiss())
                .show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, emailDialog.getView());
    }

    public void getCreateSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Sending email.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            CommonFunctions.showDialog(getViewContext(), "Sending email", "Please wait...", false);
            //sendEmailForBillsPayment(sendEmailForBillsPaymentSession);
            sendEmailForBillsPaymentObject();

        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showError(getString(R.string.generic_internet_error_message));
        }
    }


    private void sendEmailForBillsPayment(Callback<SendEmailForBillsPaymentResponse> sendEmailForBillsPaymentCallback) {
        Call<SendEmailForBillsPaymentResponse> sendemail = RetrofitBuild.sendEmailForBillsPaymentService(getViewContext())
                .sendEmailForBillsPaymentCallv2(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        transactionrefno,
                        transactiondate,
                        biller,
                        amount,
                        accountno,
                        accountname,
                        emailaddress,
                        currency,
                        billertransactionno,
                        String.valueOf(totalservicecharge),
                        discount,
                        String.valueOf(totalpaid));
        sendemail.enqueue(sendEmailForBillsPaymentCallback);
    }

    private final Callback<SendEmailForBillsPaymentResponse> sendEmailForBillsPaymentSession = new Callback<SendEmailForBillsPaymentResponse>() {

        @Override
        public void onResponse(Call<SendEmailForBillsPaymentResponse> call, Response<SendEmailForBillsPaymentResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            CommonFunctions.hideDialog();
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());



            } else {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<SendEmailForBillsPaymentResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private void evaluateResponse(Response<SendEmailForBillsPaymentResponse> response) {
        switch (response.body().getStatus()) {
            case "000": {
                emailDialog.dismiss();
                MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                        .title("Email")
                        .content("We successfully sent the transaction copy to " + emailaddress + ". Thank you.")
                        .positiveText("Close")
                        .show();
                V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());
                break;
            }
            default: {
                MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                        .title("Email")
                        .content(response.body().getMessage())
                        .positiveText("Close")
                        .show();
                V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());
                break;
            }
        }
    }

    private void updateList(List<BillsPaymentLogs> data) {
        if (data.size() > 0) {
            mSwipeRefreshLayout.setEnabled(true);
            logowatermarklayout.setVisibility(View.VISIBLE);
            pagetitle.setVisibility(View.VISIBLE);
            mBtnMore.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mAdapter.setBillsPaymentLogsData(data);
        } else {
            mSwipeRefreshLayout.setEnabled(false);
            logowatermarklayout.setVisibility(View.GONE);
            pagetitle.setVisibility(View.GONE);
            mBtnMore.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            if(isyearselected && ismonthselected){
                emptyvoucherfilter.setVisibility(View.VISIBLE);
            }else{
                emptyLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            emptyvoucherfilter.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            emptyLayout.setVisibility(View.GONE);
            emptyvoucherfilter.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.GONE);
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

            if (db.getBillsPaymentLogsDetails(db).size() > 0) {
                mBtnMore.setVisibility(View.VISIBLE);
                if (isyearselected && ismonthselected) {
                    mBtnMore.setText("FILTER OPTIONS");
                } else {
                    mBtnMore.setText("VIEW ARCHIVE");
                }
            }

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
        mDialog = new MaterialDialog.Builder(Objects.requireNonNull(getContext()))
                .customView(R.layout.pop_filtering, false)
                .cancelable(true)
                .backgroundColorRes(R.color.zxing_transparent)
                .show();

        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawableResource(R.color.zxing_transparent);

        View dialog = mDialog.getCustomView();

        filterwrap = Objects.requireNonNull(dialog).findViewById(R.id.filterwrap);
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

    //create spinner for month list
    private void createMonthSpinnerAddapter() {
        try {
            ArrayAdapter<String> monthadapter;
            ArrayList<String> spinmonthlist;
            spinmonthlist = monthlist();
            monthadapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, spinmonthlist);
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
            yearadapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, yearList());
            yearadapter.setDropDownViewResource(R.layout.spinner_arrow);
            spinType1.setAdapter(yearadapter);
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

    //create year list
    private ArrayList<String> yearList() {

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Select Year");
        for (int i = registrationyear; i <= currentyear; i++) {
            xVals.add(Integer.toString(i));
        }

        return xVals;
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
                if (isyearselected && ismonthselected) {
                    if (mBtnMore.getText().equals("VIEW ARCHIVE")) {
                        mBtnMore.setText("FILTER OPTIONS");
                    }
                    getSession();
                    mDialog.dismiss();
                } else {
                    showToast("Please select a date.", GlobalToastEnum.WARNING);
                }
                break;
            }
            case R.id.refresh: {
                db.truncateTable(db,DatabaseHandler.BILLS_PAYMENT_LOGS);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_body, new LogsBillsPaymentFragment())
                        .commit();
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
                mDialog.dismiss();
                db.truncateTable(db,DatabaseHandler.BILLS_PAYMENT_LOGS);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_body, new LogsBillsPaymentFragment())
                        .commit();

                break;
            }
            case R.id.filteroption: {
                showFilterOptionDialog();
                break;
            }
            case R.id.refreshnointernet: {
                db.truncateTable(db,DatabaseHandler.BILLS_PAYMENT_LOGS);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_body, new LogsBillsPaymentFragment())
                        .commit();
                break;
            }
        }
    }


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void sendEmailForBillsPaymentObject() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

                if(billertransactionno.equals("") || billertransactionno.equals(null)){
                    billertransactionno = ".";
                }

                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("transactionrefno",transactionrefno);
                parameters.put("transactiondate",transactiondate);
                parameters.put("biller",biller);
                parameters.put("amount",amount);
                parameters.put("accountno",accountno);
                parameters.put("accountname",accountname);
                parameters.put("emailaddress",emailaddress);
                parameters.put("currency",currency);
                parameters.put("billertransactionno",billertransactionno);
                parameters.put("totalservicecharge",String.valueOf(totalservicecharge));
                parameters.put("discount",discount);
                parameters.put("totalpaid",String.valueOf(totalpaid));
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                sendEmailIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", sendEmailIndex);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                sendEmailAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                sendEmailKeyEncryption = CommonFunctions.getSha1Hex(sendEmailAuthenticationid + sessionid + "sendEmailForBillsPaymentV3");
                sendEmailParam = CommonFunctions.encryptAES256CBC(sendEmailKeyEncryption, String.valueOf(paramJson));

                getSendEmailForBillsPaymentObject(sendEmailForBillsPaymentCallback);

            } else {
                showNoInternetConnection(true);
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            }

        } catch (Exception e) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();
        }

    }

    //SendEmailForBillsPaymentResponse - Model
    private void getSendEmailForBillsPaymentObject(Callback<GenericResponse> sendEmailForBillsPaymentCallback) {
        Call<GenericResponse> sendemail = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .sendEmailForBillsPayment(sendEmailAuthenticationid,sessionid,sendEmailParam);
        sendemail.enqueue(sendEmailForBillsPaymentCallback);
    }

    private final Callback<GenericResponse> sendEmailForBillsPaymentCallback = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            CommonFunctions.hideDialog();
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(sendEmailKeyEncryption,response.body().getMessage());

                switch (response.body().getStatus()) {
                    case "000": {

                         String decryptedData = CommonFunctions.decryptAES256CBC(sendEmailKeyEncryption,response.body().getData());

                        if(decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")){
                            showErrorToast();
                        }else{
                            emailDialog.dismiss();
                            MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                                    .title("Email")
                                    .content("We successfully sent the transaction copy to " + emailaddress + ". Thank you.")
                                    .positiveText("Close")
                                    .show();
                            V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());
                        }
                        break;
                    }
                    default: {
                        if(response.body().getStatus().equalsIgnoreCase("error")){
                            showErrorToast();
                        }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        }else{
                            MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                                    .title("Email")
                                    .content(decryptedMessage)
                                    .positiveText("Close")
                                    .show();
                            V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());
                        }
                        break;
                    }
                }

            } else {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private void getBillsPaymentLogsV2(){

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("year", String.valueOf(year));
                parameters.put("month", String.valueOf(month));
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getBillsPaymentLogsV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getBillsPaymentLogsObjectV2(getBillsPaymentLogsSessionV2);

            } else {
                mSwipeRefreshLayout.setEnabled(false);
                showNoInternetConnection(true);
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            }

    }

    private void getBillsPaymentLogsObjectV2(Callback<GenericResponse> getPrepaidLogsCallback) {
        Call<GenericResponse> getTransaction = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getBillsPaymentLogs(authenticationid,sessionid,param);
        getTransaction.enqueue(getPrepaidLogsCallback);
    }

    private final Callback<GenericResponse> getBillsPaymentLogsSessionV2 = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());

                if (response.body().getStatus().equals("000")) {

                    String decryptData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    if(decryptData.equals("error") || decryptedMessage.equals("error")){
                        showErrorToast();
                    }else{
                        db.truncateTable(db, DatabaseHandler.BILLS_PAYMENT_LOGS);
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<BillsPaymentLogs>>(){}.getType();
                        List<BillsPaymentLogs> responses = gson.fromJson(decryptData, type);

                        Logger.debug("okhttp","DDDDAAAAAAATTTTTTAAA : "+decryptData);

                        if(responses.size() > 0){
                            for (BillsPaymentLogs bills : responses) {
                                db.insertBillsPaymentLogs(db, bills);
                            }
                        }
                        updateList(db.getBillsPaymentLogsDetails(db));
                    }
                }else{
                    if(response.body().getStatus().equals("error")){
                        showErrorToast();
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else{
                        showErrorToast(decryptedMessage);
                    }
                }
            } else {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                mSwipeRefreshLayout.setEnabled(false);
                showNoInternetConnection(true);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
            showNoInternetConnection(true);
        }
    };

}
