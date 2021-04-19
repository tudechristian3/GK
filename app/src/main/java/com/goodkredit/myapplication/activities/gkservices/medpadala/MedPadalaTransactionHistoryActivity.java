package com.goodkredit.myapplication.activities.gkservices.medpadala;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.goodkredit.myapplication.adapter.gkservices.medpadala.MedPadalaTransactionsHistoryAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.MedPadalaTransaction;
import com.goodkredit.myapplication.responses.medpadala.GetMedPadalaTransactionsResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 2/26/2018.
 */

public class MedPadalaTransactionHistoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private DatabaseHandler db;

    int year = 2018;
    int limit = 0;

    private boolean isyearselected = false;
    private int registrationyear = 2018;

    private RecyclerView mRvMedpadalaHistory;
    private MedPadalaTransactionsHistoryAdapter mAdapter;
    private Button mBtnMore;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<MedPadalaTransaction> medPadalaTransactionList = new ArrayList<>();

    private RelativeLayout emptyLayout;
    private RelativeLayout emptyvoucherfilter;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView imgVRefresh;
    private TextView filteroption;

    private MaterialDialog mDialog;
    private ScrollView filterwrap;
    private LinearLayout optionwrap;
    private TextView editsearches;
    private TextView clearsearch;
    private Spinner spinType;
    private Spinner spinType1;
    private TextView popfilter;
    private TextView popcancel;

    private int currentyear = 0;//make this not changeable for (filter condition)

    private String sessionid = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medpadala_transaction_history);
        init();
    }

    private void init() {
        setupToolbar();
        hideSoftKeyboard();
        db = new DatabaseHandler(getViewContext());

        //get account information
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
            } while (cursor.moveToNext());
        }
        //imei = V2Utils.getIMEI(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mRvMedpadalaHistory = (RecyclerView) findViewById(R.id.rvMedpadalaHistory);
        mRvMedpadalaHistory.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));

        mAdapter = new MedPadalaTransactionsHistoryAdapter(getViewContext(), medPadalaTransactionList);
        mRvMedpadalaHistory.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        emptyLayout = (RelativeLayout) findViewById(R.id.emptyLayout);
        emptyvoucherfilter = (RelativeLayout) findViewById(R.id.emptyvoucherfilter);
        nointernetconnection = (RelativeLayout) findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);
        filteroption = (TextView) findViewById(R.id.filteroption);
        filteroption.setOnClickListener(this);

        findViewById(R.id.viewarchive).setOnClickListener(this);
        findViewById(R.id.refresh).setOnClickListener(this);

        mBtnMore = (Button) findViewById(R.id.btn_more);
        mBtnMore.setOnClickListener(this);
        mBtnMore.setText("VIEW ARCHIVE");

        currentyear = Calendar.getInstance().get(Calendar.YEAR);

        createSession();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MedPadalaTransactionHistoryActivity.class);
        context.startActivity(intent);
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
//
//    private void createSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            showProgressDialog(getViewContext(), "Fetching transaction history...", "Please wait.");
//            createSession(createSessionCallback);
//        } else {
//            mSwipeRefreshLayout.setRefreshing(false);
//            showNoInternetConnection(true);
//            showError(getString(R.string.generic_internet_error_message));
//        }
//    }
//
//    private Callback<String> createSessionCallback = new Callback<String>() {
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
//                        getHistory();
//                    } else {
//                        mSwipeRefreshLayout.setRefreshing(false);
//                        hideProgressDialog();
//                        showError();
//                    }
//                } else {
//                    mSwipeRefreshLayout.setRefreshing(false);
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
//            mSwipeRefreshLayout.setRefreshing(false);
//            hideProgressDialog();
//            showError();
//        }
//    };

    private void createSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Fetching transaction history...", "Please wait.");
            getHistory();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getHistory() {
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
        Call<GetMedPadalaTransactionsResponse> call = RetrofitBuild.getMedPadalaApiService(getViewContext())
                .getHistory(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        Calendar.getInstance().get(Calendar.YEAR),
                        limit
                );

        call.enqueue(getHistoryCallback);
    }

    private Callback<GetMedPadalaTransactionsResponse> getHistoryCallback = new Callback<GetMedPadalaTransactionsResponse>() {
        @Override
        public void onResponse(Call<GetMedPadalaTransactionsResponse> call, Response<GetMedPadalaTransactionsResponse> response) {
            try {
                hideProgressDialog();
                mSwipeRefreshLayout.setRefreshing(false);
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    List<MedPadalaTransaction> transactions = response.body().getTransactions();
                    updateUI(transactions);
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetMedPadalaTransactionsResponse> call, Throwable t) {
            hideProgressDialog();
            mSwipeRefreshLayout.setRefreshing(false);
            showError();
        }
    };

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        createSession();
    }

    private void updateUI(List<MedPadalaTransaction> transactions) {
        showNoInternetConnection(false);
        if (transactions.size() > 0) {
            findViewById(R.id.pagetitle).setVisibility(View.VISIBLE);
            db.trucateMedPadalaTable(db);
            for (MedPadalaTransaction transaction : transactions) {
                db.insertMedPadalaTransaction(db, transaction);
            }
            mAdapter.update(db.getAllMedPadalaTransactions(db));
        } else {
            findViewById(R.id.pagetitle).setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
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
                    // mLlLoader.setVisibility(View.VISIBLE);
                    createSession();
                    mDialog.dismiss();
                } else {
                    showToast("Please select a year.", GlobalToastEnum.WARNING);
                }
                break;
            }
            case R.id.refresh: {
                year = Calendar.getInstance().get(Calendar.YEAR);
                // mLlLoader.setVisibility(View.VISIBLE);
                createSession();
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
                isyearselected = false;
                // mLlLoader.setVisibility(View.VISIBLE);
                createSession();
                mDialog.dismiss();
                break;
            }
            case R.id.filteroption: {
                showFilterOptionDialog();
                break;
            }
            case R.id.refreshnointernet: {
                // mLlLoader.setVisibility(View.VISIBLE);
                createSession();
                break;
            }
        }
//        switch (v.getId()) {
//            case R.id.refresh: {
//                year = Calendar.getInstance().get(Calendar.YEAR);
//                limit = 0;
//                createSession();
//                break;
//            }
//            case R.id.viewarchive: {
//                showViewArchiveDialog();
//                break;
//            }
//        }
    }

    private void showViewArchiveDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .customView(R.layout.pop_filtering, false)
                .cancelable(true)
                .backgroundColorRes(R.color.zxing_transparent)
                .show();

        mDialog.getWindow().setBackgroundDrawableResource(R.color.zxing_transparent);

        View dialog = mDialog.getCustomView();

        filterwrap = (ScrollView) dialog.findViewById(R.id.filterwrap);
        optionwrap = (LinearLayout) dialog.findViewById(R.id.optionwrap);
        editsearches = (TextView) dialog.findViewById(R.id.editsearches);
        clearsearch = (TextView) dialog.findViewById(R.id.clearsearch);
        spinType = (Spinner) dialog.findViewById(R.id.month);
        spinType.setVisibility(View.GONE);
        spinType1 = (Spinner) dialog.findViewById(R.id.year);
        popfilter = (TextView) dialog.findViewById(R.id.filter);
        popcancel = (TextView) dialog.findViewById(R.id.cancel);

        filterwrap.setVisibility(View.VISIBLE);
        optionwrap.setVisibility(View.GONE);

        createYearSpinnerAddapter();

        spinType1.setOnItemSelectedListener(yearItemListener);

        popfilter.setOnClickListener(this);
        popcancel.setOnClickListener(this);
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

    //create year list
    private ArrayList<String> yearList() {

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Select Year");
        for (int i = registrationyear; i <= currentyear; i++) {
            xVals.add(Integer.toString(i));
        }

        return xVals;
    }

    private void showFilterOptionDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .customView(R.layout.pop_filtering, false)
                .cancelable(true)
                .backgroundColorRes(R.color.zxing_transparent)
                .show();

        mDialog.getWindow().setBackgroundDrawableResource(R.color.zxing_transparent);

        View dialog = mDialog.getCustomView();

        filterwrap = (ScrollView) dialog.findViewById(R.id.filterwrap);
        optionwrap = (LinearLayout) dialog.findViewById(R.id.optionwrap);
        editsearches = (TextView) dialog.findViewById(R.id.editsearches);
        clearsearch = (TextView) dialog.findViewById(R.id.clearsearch);
        spinType1 = (Spinner) dialog.findViewById(R.id.year);
        popfilter = (TextView) dialog.findViewById(R.id.filter);
        popcancel = (TextView) dialog.findViewById(R.id.cancel);
        spinType = (Spinner) dialog.findViewById(R.id.month);
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
}
