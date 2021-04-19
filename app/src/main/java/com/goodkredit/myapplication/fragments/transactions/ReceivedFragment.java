package com.goodkredit.myapplication.fragments.transactions;

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
import com.goodkredit.myapplication.adapter.transactions.ReceivedVouchersRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.TransactionsVouchers;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.v2Models.TransactionModel;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User-PC on 8/2/2017.
 */

public class ReceivedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private ReceivedVouchersRecyclerAdapter mAdapter;

    private String borrowerid;
//    private String sessionid;
    private String imei;
    private String userid;
    private String authcode;

    private Calendar c;
    private DatabaseHandler db;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<TransactionModel> mGridData;
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

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES
    private String index;
    private String authenticationid;
    private String param;
    private String keyEncryption;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_received_vouchers, container, false);

        db = new DatabaseHandler(getViewContext());

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        pagetitle = view.findViewById(R.id.pagetitle);
        logowatermarklayout = view.findViewById(R.id.logowatermarklayout);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mBtnMore = view.findViewById(R.id.btn_more);
        mBtnMore.setOnClickListener(this);

        mGridData = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view_received_vouchers);

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
        mAdapter = new ReceivedVouchersRecyclerAdapter(getViewContext(), mGridData);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnScrollListener(scrollListener);

        view.findViewById(R.id.viewarchive).setOnClickListener(this);
        view.findViewById(R.id.refresh).setOnClickListener(this);
        textView11 = view.findViewById(R.id.textView11);
        textView11.setText("No received vouchers for this month.");

        getSession();

        return view;
    }

    private void getReceivedVouchers(Callback<String> getReceivedVouchersCallback) {
        Call<String> getreceivedvouchers = RetrofitBuild.getReceivedVouchersService(getViewContext())
                .getReceivedVouchersCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        String.valueOf(year),
                        String.valueOf(month));
        getreceivedvouchers.enqueue(getReceivedVouchersCallback);
    }

    private final Callback<String> getReceivedVouchersSession = new Callback<String>() {

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            String responseData = response.body();
            if (!responseData.isEmpty()) {
                if (responseData.equals("001")) {
                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
                } else if (responseData.equals("error")) {
                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                } else if (responseData.contains("<!DOCTYPE html>")) {
                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    db.truncateTable(db, DatabaseHandler.RECEIVED_VOUCHERS);
                    List<TransactionsVouchers> receivedvouchers = new ArrayList<>();
                    try {
                        JSONArray jsonArr = new JSONArray(response.body());
                        if (jsonArr.length() > 0) {
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);
                                String transfertype = jsonObj.getString("TransferType");
                                String sourceborrowerid = jsonObj.getString("SourceBorrowerID");
                                String sourceborrowername = jsonObj.getString("SourceBorrowerName");
                                String recipientborrowerid = jsonObj.getString("RecipientBorrowerID");
                                String recipientborrowername = jsonObj.getString("RecipientBorrowerName");
                                String recipientmobile = jsonObj.getString("RecipientMobileNo");
                                String recipientemail = jsonObj.getString("RecipientEmailAddress");
                                String serialno = jsonObj.getString("VoucherSerialNo");
                                String vouchercode = jsonObj.getString("VoucherCode");
                                String denoms = jsonObj.getString("VoucherDenoms");
                                String date = jsonObj.getString("DateTimeCompleted");
                                String processID = jsonObj.getString("ProcessID");
                                String status = jsonObj.getString("Status");
                                String transfertxn = jsonObj.getString("TransferTxnNo");

                                Logger.debug("ANNSS","TRANSFERTXN "+transfertxn);

                                receivedvouchers.add(new TransactionsVouchers(date, transfertype, sourceborrowerid, sourceborrowername, recipientborrowerid, recipientborrowername, recipientmobile, recipientemail, serialno, vouchercode, denoms, processID, status,transfertxn));
                            }

                            for (TransactionsVouchers voucher : receivedvouchers) {
                                db.insertReceivedVouchers(db, voucher);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    updateList(db.getAllReceivedVouchers(db, borrowerid));
//                    mAdapter.setReceivedVouchersData(db.getAllReceivedVouchers(db,borrowerid));
                }
            } else {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showNoInternetConnection(true);
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    @Override
    public void onRefresh() {
        db.truncateTable(db, DatabaseHandler.RECEIVED_VOUCHERS);
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_body, new ReceivedFragment())
                .commit();
    }

    private void getSession() {

        hidealllayouts();

        mLoaderTimer.cancel();
        mLoaderTimer.start();

        mTvFetching.setText("Fetching received vouchers.");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getReceivedVouchers(getReceivedVouchersSession);
            getReceivedVouchers();

        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
            showNoInternetConnection(true);
            //showError(getString(R.string.generic_internet_error_message));
        }

    }

    private void updateList(List<TransactionModel> data) {
        if (data.size() > 0) {
            mSwipeRefreshLayout.setEnabled(true);
            logowatermarklayout.setVisibility(View.VISIBLE);
            pagetitle.setVisibility(View.VISIBLE);
            mBtnMore.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            emptyvoucherfilter.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mAdapter.setReceivedVouchersData(data);
        } else {
            mSwipeRefreshLayout.setEnabled(true);
            logowatermarklayout.setVisibility(View.GONE);
            pagetitle.setVisibility(View.GONE);
            mBtnMore.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            if (isyearselected && ismonthselected) {
                emptyvoucherfilter.setVisibility(View.VISIBLE);
            } else {
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

    private void hidealllayouts(){
        emptyLayout.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.GONE);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (db.getAllReceivedVouchers(db, borrowerid).size() > 0) {
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

//        spinType.setVisibility(View.GONE);
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
                if (i >= registrationmonth - 1) {
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
                db.truncateTable(db, DatabaseHandler.RECEIVED_VOUCHERS);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_body, new ReceivedFragment())
                        .commit();
                break;
            }
            case R.id.filteroption: {
                showFilterOptionDialog();
                break;
            }
            case R.id.refreshnointernet: {
                db.truncateTable(db, DatabaseHandler.RECEIVED_VOUCHERS);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_body, new ReceivedFragment())
                        .commit();
                }
                break;
                case R.id.refresh:
                    onRefresh();
                    break;
            }
    }

    /**
     *SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getReceivedVouchers() {

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
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getTransferedVoucherV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getReceivedVouchersObject(getReceivedVouchersV2Session);

            } else {
                mSwipeRefreshLayout.setEnabled(false);
                showNoInternetConnection(true);
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            }

    }

    private void getReceivedVouchersObject(Callback<GenericResponse> getReceivedVouchersCallback) {
        Call<GenericResponse> getreceivedvouchers = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getTransferedVoucher(authenticationid,
                        sessionid,
                        param);
        getreceivedvouchers.enqueue(getReceivedVouchersCallback);
    }

    private final Callback<GenericResponse> getReceivedVouchersV2Session = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);

            if(response.errorBody() == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                switch (response.body().getStatus()){
                    case "000":
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                        db.truncateTable(db, DatabaseHandler.RECEIVED_VOUCHERS);
                        List<TransactionsVouchers> receivedvouchers = new ArrayList<>();
                        try {
                            JSONArray jsonArr = new JSONArray(data);
                            if (jsonArr.length() > 0) {
                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                                    String transfertype = jsonObj.getString("TransferType");
                                    String sourceborrowerid = jsonObj.getString("SourceBorrowerID");
                                    String sourceborrowername = jsonObj.getString("SourceBorrowerName");
                                    String recipientborrowerid = jsonObj.getString("RecipientBorrowerID");
                                    String recipientborrowername = jsonObj.getString("RecipientBorrowerName");
                                    String recipientmobile = jsonObj.getString("RecipientMobileNo");
                                    String recipientemail = jsonObj.getString("RecipientEmailAddress");
                                    String serialno = jsonObj.getString("VoucherSerialNo");
                                    String vouchercode = jsonObj.getString("VoucherCode");
                                    String denoms = jsonObj.getString("VoucherDenoms");
                                    String date = jsonObj.getString("DateTimeCompleted");
                                    String processID = jsonObj.getString("ProcessID");
                                    String status = jsonObj.getString("Status");
                                    String transfertxn = jsonObj.getString("TransferTxnNo");

                                    Logger.debug("okhttp","TRANSFERTXN "+transfertxn);

                                    receivedvouchers.add(new TransactionsVouchers(date, transfertype, sourceborrowerid, sourceborrowername, recipientborrowerid, recipientborrowername, recipientmobile, recipientemail, serialno, vouchercode, denoms, processID, status,transfertxn));
                                }

                                for (TransactionsVouchers voucher : receivedvouchers) {
                                    db.insertReceivedVouchers(db, voucher);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        updateList(db.getAllReceivedVouchers(db, borrowerid));
                        break;

                    case "003": case "002":
                         showAutoLogoutDialog(getString(R.string.logoutmessage));
                        break;

                    default:
                        showErrorToast(message);
                        break;
                }
            }else{
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                mSwipeRefreshLayout.setEnabled(false);
                showNoInternetConnection(true);
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
            showNoInternetConnection(true);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

}
