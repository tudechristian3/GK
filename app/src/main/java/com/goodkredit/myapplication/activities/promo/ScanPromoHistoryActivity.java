package com.goodkredit.myapplication.activities.promo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.goodkredit.myapplication.adapter.promo.RedeemedPromoHistoryAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.PromoQRDetails;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.RedeemedPromo;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.promo.RedeemedPromoHistoryResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 2/7/2018.
 */

public class ScanPromoHistoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private DatabaseHandler db;

    private TextView pagetitle;
    private RelativeLayout logowatermarklayout;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Button mBtnMore;

    private List<RedeemedPromo> mRedeemedPromos;
    private RecyclerView recyclerView;
    private RedeemedPromoHistoryAdapter mAdapter;

    private NestedScrollView nested_scroll_logs_retailer_reloading;


    private boolean isyearselected = false;
    private boolean ismonthselected = false;

    private int currentlimit = 0;
    private boolean isloadmore = true;

    private TextView textView11;

    private int currentyear = 0;//make this not changeable for (filter condition)
    private int currentmonth = 0; //make this not changeable for (filter condition)
    private int year;
    private int month;
    private int registrationyear;
    private int registrationmonth;
    private String dateregistered = "";

    private RelativeLayout emptyLayout;
    private RelativeLayout emptyvoucherfilter;
    private RelativeLayout nointernetconnection;
    private Button refreshnointernet;
    private TextView filteroption;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private MaterialDialog mDialog;
    private ScrollView filterwrap;
    private LinearLayout optionwrap;
    private TextView editsearches;
    private TextView clearsearch;
    private Spinner spinType;
    private Spinner spinType1;
    private TextView popfilter;
    private TextView popcancel;

    private String sessionid;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_promo_history);
        init();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ScanPromoHistoryActivity.class);
        context.startActivity(intent);
    }

    private void init() {
        setupToolbar();
        db = new DatabaseHandler(getViewContext());

        sessionid = PreferenceUtils.getSessionID(getViewContext());
        //get account information
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));

                dateregistered = cursor.getString(cursor.getColumnIndex("dateregistration"));

                String CurrentString = dateregistered;
                String[] separated = CurrentString.split("-");
                registrationyear = Integer.parseInt(separated[0]);
                registrationmonth = Integer.parseInt(separated[1]);

            } while (cursor.moveToNext());
        }

        nested_scroll_logs_retailer_reloading = findViewById(R.id.nested_scroll_logs_retailer_reloading);

        pagetitle = findViewById(R.id.pagetitle);
        logowatermarklayout = findViewById(R.id.logowatermarklayout);

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mBtnMore = findViewById(R.id.btn_more);
        mBtnMore.setOnClickListener(this);
        mBtnMore.setText("VIEW ARCHIVE");

        mRedeemedPromos = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_promo_history);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getViewContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new RedeemedPromoHistoryAdapter(getViewContext(), mRedeemedPromos);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnScrollListener(scrollListener);

        nested_scroll_logs_retailer_reloading.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    mSwipeRefreshLayout.setEnabled(false);
                }
                if (scrollY < oldScrollY) {
                    mSwipeRefreshLayout.setEnabled(false);
                }

                if (scrollY == 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    mSwipeRefreshLayout.setEnabled(false);
                }

            }
        });

        findViewById(R.id.viewarchive).setOnClickListener(this);
        findViewById(R.id.refresh).setOnClickListener(this);
        textView11 = findViewById(R.id.textView11);
        textView11.setText("No promo redemption for this month.");

        emptyLayout = findViewById(R.id.emptyLayout);
        emptyvoucherfilter = findViewById(R.id.emptyvoucherfilter);
        nointernetconnection = findViewById(R.id.nointernetconnection);
        refreshnointernet = findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);
        filteroption = findViewById(R.id.filteroption);
        filteroption.setOnClickListener(this);

        mLlLoader = findViewById(R.id.loaderLayout);
        mTvFetching = findViewById(R.id.fetching);
        mTvWait = findViewById(R.id.wait);

        //initialize date
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

        getSession();
    }

    private void updateList(List<RedeemedPromo> data) {
        shownoInternet(false);
        if (data.size() > 0) {
            logowatermarklayout.setVisibility(View.VISIBLE);
            pagetitle.setVisibility(View.VISIBLE);
            mBtnMore.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            emptyvoucherfilter.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mAdapter.update(data);
        } else {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    private void getSession() {
        mLoaderTimer.cancel();
        mLoaderTimer.start();

        mTvFetching.setText("Fetching purchases.");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        shownoInternet(false);

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            getRedeemedPromoHistory();
             getPromoQRHistoryV2();
        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            shownoInternet(true);
        }
    }

    private void getRedeemedPromoHistory() {
        Call<RedeemedPromoHistoryResponse> call = RetrofitBuild.getPromoQRApiService(getViewContext())
                .getRedeemedPromoHistory(
                        sessionid,
                        imei,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        userid,
                        borrowerid,
                        year,
                        currentlimit
                );

        call.enqueue(getRedeemedPromoHistoryCallback);
    }

    private Callback<RedeemedPromoHistoryResponse> getRedeemedPromoHistoryCallback = new Callback<RedeemedPromoHistoryResponse>() {
        @Override
        public void onResponse(Call<RedeemedPromoHistoryResponse> call, Response<RedeemedPromoHistoryResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errBody = response.errorBody();
            try {
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        db.deletePromoHistory(db);
                        mRedeemedPromos = response.body().getRedeemedPromos();
                        for (RedeemedPromo promo : mRedeemedPromos) {
                            db.insertPromoHistory(db, promo);
                        }
                        updateList(db.getPromoHistory(db));
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
        public void onFailure(Call<RedeemedPromoHistoryResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showError();
        }
    };

    @Override
    public void onRefresh() {
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        mSwipeRefreshLayout.setRefreshing(true);
        mLlLoader.setVisibility(View.VISIBLE);
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
                if (isyearselected) {
                    if (mBtnMore.getText().equals("VIEW ARCHIVE")) {
                        mBtnMore.setText("FILTER OPTIONS");
                    }
                    mLlLoader.setVisibility(View.VISIBLE);
                    getSession();
                    mDialog.dismiss();
                } else {
                    showToast("Please select a year.", GlobalToastEnum.WARNING);
                }
                break;
            }
            case R.id.refresh: {
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                mLlLoader.setVisibility(View.VISIBLE);
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
                mBtnMore.setText("VIEW ARCHIVE");
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                isyearselected = false;
                ismonthselected = false;
                mLlLoader.setVisibility(View.VISIBLE);
                getSession();
                mDialog.dismiss();
                break;
            }
            case R.id.filteroption: {
                showFilterOptionDialog();
                break;
            }
            case R.id.refreshnointernet: {
                mLlLoader.setVisibility(View.VISIBLE);
                getSession();
                break;
            }
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
        spinType = dialog.findViewById(R.id.month);
        spinType.setVisibility(View.GONE);
        spinType1 = dialog.findViewById(R.id.year);
        popfilter = dialog.findViewById(R.id.filter);
        popcancel = dialog.findViewById(R.id.cancel);

        filterwrap.setVisibility(View.VISIBLE);
        optionwrap.setVisibility(View.GONE);

        createYearSpinnerAddapter();

        spinType1.setOnItemSelectedListener(yearItemListener);

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
        clearsearch = dialog.findViewById(R.id.clearsearch);
        spinType1 = dialog.findViewById(R.id.year);
        popfilter = dialog.findViewById(R.id.filter);
        popcancel = dialog.findViewById(R.id.cancel);
        spinType = dialog.findViewById(R.id.month);
        spinType.setVisibility(View.GONE);

        createYearSpinnerAddapter();

        filterwrap.setVisibility(View.GONE);
        optionwrap.setVisibility(View.VISIBLE);

        spinType1.setOnItemSelectedListener(yearItemListener);

        clearsearch.setOnClickListener(this);
        editsearches.setOnClickListener(this);
        popfilter.setOnClickListener(this);
        popcancel.setOnClickListener(this);
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

    //create spinner for year list
    private void createYearSpinnerAddapter() {
        try {
            ArrayAdapter<String> yearadapter;
            yearadapter = new ArrayAdapter<>(getViewContext(), android.R.layout.simple_spinner_dropdown_item, yearList());
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




    @Override
    protected void onStop() {
        super.onStop();
        if (mLoaderTimer != null) {
            mLoaderTimer.cancel();
        }
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */
    private void getPromoQRHistoryV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();

            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("year", String.valueOf(year));
            parameters.put("limit", String.valueOf(currentlimit));

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getPromoQRHistoryV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getPromoQRHistoryV2Object(getPromoQRHistoryV2Callback);

        }else{
            mSwipeRefreshLayout.setRefreshing(false);
            hideProgressDialog();
            shownoInternet(true);
        }
    }
    private void getPromoQRHistoryV2Object(Callback<GenericResponse> getPromoQRHistoryV2) {

        Call<GenericResponse> call = RetrofitBuilder.getPromoQRCodeV2API(getViewContext())
                .getPromoQRHistoryV2(
                        authenticationid,sessionid,param
                );
        call.enqueue(getPromoQRHistoryV2);
    }
    private Callback<GenericResponse> getPromoQRHistoryV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message =CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                        db.deletePromoHistory(db);
                        mRedeemedPromos = new Gson().fromJson(data, new TypeToken<List<RedeemedPromo>>(){}.getType());
                        Logger.debug("okhttp","::::::::::::::"+data);
                        if(mRedeemedPromos.size() > 0 ){
                            for (RedeemedPromo promo : mRedeemedPromos) {
                                db.insertPromoHistory(db, promo);
                            }
                        }
                        updateList(db.getPromoHistory(db));
                    }else if(response.body().getStatus().equals("002") || response.body().getStatus().equals("003")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
                        showError(message);
                    }
                } else {
                    showError();
                }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showError();
            shownoInternet(false);
            emptyLayout.setVisibility(View.VISIBLE);
        }
    };

    private void shownoInternet(boolean show){
        if(show){
            nointernetconnection.setVisibility(View.VISIBLE);
        }else{
            nointernetconnection.setVisibility(View.GONE);
        }
        emptyLayout.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.GONE);
    }

}
