package com.goodkredit.myapplication.fragments.transactions;

import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.transactions.PurchasesRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.PrepaidVoucherTransaction;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetPurchasesResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
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
 * Created by User-PC on 8/2/2017.
 */

public class PurchasesFragmentV2 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private PurchasesRecyclerAdapter mAdapter;

    private String borrowerid = "";
    private String imei = "";
    //    private String sessionid = "";
    private String userid = "";
    private String authcode;

    private Calendar c;
    private DatabaseHandler db;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<PrepaidVoucherTransaction> mGridData;
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

    private NestedScrollView nested_scroll_logs_prepaid;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLE FOR SECURITY
    private String authenticationid;
    private String index;
    private String param;
    private String keyEncryption;

    private List<PrepaidVoucherTransaction> prepaidvoucher;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_purchases, container, false);

        db = new DatabaseHandler(getViewContext());


        nested_scroll_logs_prepaid = view.findViewById(R.id.nested_scroll_logs_prepaid);

        pagetitle = view.findViewById(R.id.pagetitle);
        logowatermarklayout = view.findViewById(R.id.logowatermarklayout);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(false);

        mBtnMore = view.findViewById(R.id.btn_more);
        mBtnMore.setOnClickListener(this);

        mGridData = new ArrayList<>();
        prepaidvoucher = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_view_purchases);

        emptyLayout = view.findViewById(R.id.emptyLayout);
        emptyvoucherfilter = view.findViewById(R.id.emptyvoucherfilter);
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet =  view.findViewById(R.id.refreshnointernet);
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
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new PurchasesRecyclerAdapter(getViewContext(), mGridData, PurchasesFragmentV2.this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnScrollListener(scrollListener);

        view.findViewById(R.id.viewarchive).setOnClickListener(this);
        view.findViewById(R.id.refresh).setOnClickListener(this);
        textView11 = view.findViewById(R.id.textView11);
        textView11.setText("No purchases for this month.");

        nested_scroll_logs_prepaid.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll DOWN");
                    mSwipeRefreshLayout.setEnabled(false);
                }
                if (scrollY < oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll UP");
                    mSwipeRefreshLayout.setEnabled(false);
                }

                if (scrollY == 0) {
//                    Logger.debug("antonhttp", "TOP SCROLL");
                    mSwipeRefreshLayout.setEnabled(true);
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    Logger.debug("antonhttp", "======BOTTOM SCROLL=======");
                    mSwipeRefreshLayout.setEnabled(false);

                }
            }
        });


        getSession();

        return view;
    }

    private void getPurchases(Callback<GetPurchasesResponse> getPurchasesCallback) {
        Call<GetPurchasesResponse> getpurchases = RetrofitBuild.getPurchasesService(getViewContext())
                .getPurchasesCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        String.valueOf(year),
                        String.valueOf(month));
        getpurchases.enqueue(getPurchasesCallback);
    }

    private final Callback<GetPurchasesResponse> getPurchasesSession = new Callback<GetPurchasesResponse>() {

        @Override
        public void onResponse(Call<GetPurchasesResponse> call, Response<GetPurchasesResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    db.truncateTable(db, DatabaseHandler.PURCHASES);

                    prepaidvoucher = response.body().getTransaction();

                    if(prepaidvoucher.size() >0 && prepaidvoucher != null){
                        for (PrepaidVoucherTransaction voucher : prepaidvoucher) {
                            db.insertPrepaidVoucherTransaction(db, voucher);
                        }
                    }

                    updateList(db.getAllPrepaidVoucherTransactions(db));
                }
            } else {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showNoInternetConnection(true);
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<GetPurchasesResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    @Override
    public void onRefresh() {
        db.truncateTable(db, DatabaseHandler.PURCHASES);
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_body, new PurchasesFragmentV2())
                .commit();
    }

    private void getSession() {

        hidealllayouts();

        mLoaderTimer.cancel();
        mLoaderTimer.start();

        mTvFetching.setText("Fetching purchases.");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);
            getPrepaidTransactionsV2();

        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
            showNoInternetConnection(true);
            //showError(getString(R.string.generic_internet_error_message));
        }
    }

    private void updateList(List<PrepaidVoucherTransaction> data) {
        if (data.size() > 0) {
            mSwipeRefreshLayout.setEnabled(true);
            logowatermarklayout.setVisibility(View.VISIBLE);
            pagetitle.setVisibility(View.VISIBLE);
            mBtnMore.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            emptyvoucherfilter.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mAdapter.setPurchasesData(data);
        } else {
            mSwipeRefreshLayout.setEnabled(false);
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

            if (db.getAllPrepaidVoucherTransactions(db).size() > 0) {
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
                db.truncateTable(db, DatabaseHandler.PURCHASES);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_body, new PurchasesFragmentV2())
                        .commit();
                mDialog.dismiss();
                break;
            }
            case R.id.filteroption: {
                showFilterOptionDialog();
                break;
            }
            case R.id.refreshnointernet: {
                db.truncateTable(db, DatabaseHandler.PURCHASES);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_body, new PurchasesFragmentV2())
                        .commit();
                }
                break;
                case R.id.refresh:
                    onRefresh();
                    break;
            }
        }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getPrepaidTransactionsV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("year",String.valueOf(year));
            parameters.put("month",String.valueOf(month));
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getPrepaidTransactionV3");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getPrepaidTransactionsV2Object(getPrepaidTransactionsV2Session);

        }else{
            mSwipeRefreshLayout.setEnabled(false);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }
    }
    private void getPrepaidTransactionsV2Object(Callback<GenericResponse> getPrepaidTransactions) {
        Call<GenericResponse> call = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getPrepaidTransactionV2(
                        authenticationid,sessionid,param
                );
        call.enqueue(getPrepaidTransactions);
    }
    private final Callback<GenericResponse>getPrepaidTransactionsV2Session = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    Type type = new TypeToken<List<PrepaidVoucherTransaction>>(){}.getType();
                    db.truncateTable(db, DatabaseHandler.PURCHASES);

                    List<PrepaidVoucherTransaction> prepaidVoucherTransactions = new Gson().fromJson(decryptedData, type);
                    for (PrepaidVoucherTransaction voucher : prepaidVoucherTransactions) {
                        db.insertPrepaidVoucherTransaction(db, voucher);
                    }
                    updateList(db.getAllPrepaidVoucherTransactions(db));

                }else if (response.body().getStatus().equals("003")|| response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else{
                    showErrorToast(decryptedMessage);
                }
            } else {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setEnabled(false);
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                showNoInternetConnection(true);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            showNoInternetConnection(true);
        }
    };

}
