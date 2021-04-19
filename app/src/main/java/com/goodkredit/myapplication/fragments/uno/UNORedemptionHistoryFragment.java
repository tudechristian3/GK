package com.goodkredit.myapplication.fragments.uno;

import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.goodkredit.myapplication.adapter.uno.UNORedemptionHistoryAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.UNORedemption;
import com.goodkredit.myapplication.responses.uno.GetUNORedemptionHistoryResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UNORedemptionHistoryFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private DatabaseHandler db;

    private TextView pagetitle;
    private RelativeLayout logowatermarklayout;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Button mBtnMore;

    private List<UNORedemption> mGridData;
    private RecyclerView recyclerView;
    private UNORedemptionHistoryAdapter mAdapter;

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
    private ImageView refreshnointernet;
    private ImageView imgVRefresh;
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

    private String sessionid = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uno_redemption_history, container, false);

        db = new DatabaseHandler(getViewContext());

        sessionid = PreferenceUtils.getSessionID(getViewContext());
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
        cursor.close();

        nested_scroll_logs_retailer_reloading = (NestedScrollView) view.findViewById(R.id.nested_scroll_logs_retailer_reloading);

        pagetitle = (TextView) view.findViewById(R.id.pagetitle);
        logowatermarklayout = (RelativeLayout) view.findViewById(R.id.logowatermarklayout);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mBtnMore = (Button) view.findViewById(R.id.btn_more);
        mBtnMore.setOnClickListener(this);
        mBtnMore.setText("VIEW ARCHIVE");

        mGridData = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_retailer_reloading);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getViewContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new UNORedemptionHistoryAdapter(getViewContext());
        recyclerView.setAdapter(mAdapter);

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

        view.findViewById(R.id.viewarchive).setOnClickListener(this);
        view.findViewById(R.id.refresh).setOnClickListener(this);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        textView11.setText("No UNO redemption for this month.");

        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);
        emptyvoucherfilter = (RelativeLayout) view.findViewById(R.id.emptyvoucherfilter);
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);
        filteroption = (TextView) view.findViewById(R.id.filteroption);
        filteroption.setOnClickListener(this);

        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

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

        if (CacheManager.getInstance().getUNORedemptions().size() > 0) {
            mAdapter.update(CacheManager.getInstance().getUNORedemptions());
        } else {
            fetchRedemptionHistory();
        }

        return view;
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
                    fetchRedemptionHistory();
                    mDialog.dismiss();
                } else {
                    showToast("Please select a date.", GlobalToastEnum.WARNING);
                }
                break;
            }
            case R.id.refresh: {
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                fetchRedemptionHistory();
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
                fetchRedemptionHistory();
                mDialog.dismiss();
                break;
            }
            case R.id.filteroption: {
                showFilterOptionDialog();
                break;
            }
            case R.id.refreshnointernet: {
                fetchRedemptionHistory();
                break;
            }
        }
    }

    private void showViewArchiveDialog() {
        mDialog = new MaterialDialog.Builder(getContext())
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
        spinType1 = (Spinner) dialog.findViewById(R.id.year);
        popfilter = (TextView) dialog.findViewById(R.id.filter);
        popcancel = (TextView) dialog.findViewById(R.id.cancel);

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

    @Override
    public void onRefresh() {
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        currentlimit = 0;
        mSwipeRefreshLayout.setRefreshing(true);
        fetchRedemptionHistory();
    }

    private void fetchRedemptionHistory() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Fetching UNO redemption history.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            showProgressDialog("Fetching UNO Redemption History.", "Please wait...");
            //createSession(getSessionCallback);
            getRedemptionHistory();
        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showNoInternetToast();
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

//    private Callback<String> getSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            ResponseBody errBody = response.errorBody();
//
//            if (errBody == null) {
//                sessionid = response.body().toString();
//                if (!sessionid.isEmpty()) {
//                    getRedemptionHistory();
//                } else {
//                    hideProgressDialog();
//                    showError();
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    mLlLoader.setVisibility(View.GONE);
//                }
//            } else {
//                hideProgressDialog();
//                showError();
//                mSwipeRefreshLayout.setRefreshing(false);
//                mLlLoader.setVisibility(View.GONE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//            showError();
//            mSwipeRefreshLayout.setRefreshing(false);
//            mLlLoader.setVisibility(View.GONE);
//
//        }
//    };

    public void getRedemptionHistory() {
        Call<GetUNORedemptionHistoryResponse> call = RetrofitBuild.getUnoRewardsAPIService(getViewContext())
                .getUNORedemptionHistory(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid),
                        borrowerid
                );

        call.enqueue(getRedemptionHistoryCallback);
    }

    private Callback<GetUNORedemptionHistoryResponse> getRedemptionHistoryCallback = new Callback<GetUNORedemptionHistoryResponse>() {
        @Override
        public void onResponse(Call<GetUNORedemptionHistoryResponse> call, Response<GetUNORedemptionHistoryResponse> response) {
            hideProgressDialog();
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            ResponseBody errBody = response.errorBody();
            if (errBody == null) {
                if (response.body().getStatus().equals("000")) {
                    mGridData = response.body().getData();
                    CacheManager.getInstance().saveUNORedemptions(mGridData);
                    mAdapter.update(mGridData);
                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }
        }

        @Override
        public void onFailure(Call<GetUNORedemptionHistoryResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
        }
    };

    private void showFilterOptionDialog() {
        mDialog = new MaterialDialog.Builder(getContext())
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
        spinType1 = (Spinner) dialog.findViewById(R.id.year);
        popfilter = (TextView) dialog.findViewById(R.id.filter);
        popcancel = (TextView) dialog.findViewById(R.id.cancel);

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
                    if (month > 0) {
                        ismonthselected = true;
                    } else {
                        ismonthselected = false;
                    }
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
}
