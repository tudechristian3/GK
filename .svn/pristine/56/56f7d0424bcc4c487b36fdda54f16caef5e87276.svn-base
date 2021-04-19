package com.goodkredit.myapplication.fragments.skycable;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.adapter.skycable.SkycablePayPerViewActionRequiredAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.SkycablePPVHistory;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.skycable.GetSkycablePpvHistoryResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkycablePayPerViewHistoryFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private DatabaseHandler mdb;
//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;

    private boolean isloadmore;
    private boolean isbottomscroll;
    private boolean isfirstload = true;
    private boolean isLoading = false;
    private int limit = 0;
    private int year = 2018;

    private RecyclerView recyclerView;
    //private ViewSkycablePPVHistoryAdapter mAdapter;
    private SkycablePayPerViewActionRequiredAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //loader
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

    private NestedScrollView nested_scroll;

    //VIEW ARCHIVE
    private LinearLayout viewarchivelayoutv2;
    private TextView viewarchivev2;
    private LinearLayout viewarchivelayout;
    private TextView viewarchive;
    private MaterialDialog mViewArchiveDialog;
    private MaterialDialog mFilterOptionDialog;
    private List<String> MONTHS = new ArrayList<>();
    private List<String> YEARS = new ArrayList<>();
    private int MIN_MONTH = 1;
    private int MIN_YEAR = 2018;
    //    private MaterialEditText edtMonth;
    private MaterialEditText edtYear;

    //UNIFIED SESSION
    private String sessionid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_pay_per_view_history, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        //swipe refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new SkycablePayPerViewActionRequiredAdapter(getViewContext(), false);
        recyclerView.setAdapter(mAdapter);

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //empty layout
        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        //VIEW ARCHIVE
        viewarchivelayoutv2 = (LinearLayout) view.findViewById(R.id.viewarchivelayoutv2);
        viewarchivev2 = (TextView) view.findViewById(R.id.viewarchivev2);
        viewarchivev2.setOnClickListener(this);
        viewarchivev2.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", getString(R.string.string_view_archive)));
        viewarchivelayout = (LinearLayout) view.findViewById(R.id.viewarchivelayout);
        viewarchive = (TextView) view.findViewById(R.id.viewarchive);
        viewarchive.setOnClickListener(this);
        viewarchive.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", getString(R.string.string_view_archive)));

        //SET UP ARCHIVE DIALOG
        setUpViewArchiveDialog();
        setUpFilterOptions();

        nested_scroll = (NestedScrollView) view.findViewById(R.id.nested_scroll);
        nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
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

                    isbottomscroll = true;

                    if (isloadmore) {
                        if (!isfirstload) {
                            limit = limit + 30;
                        }
                        getSession();
                    }

                }
            }
        });
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        year = Calendar.getInstance().get(Calendar.YEAR);

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        getSession();
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            //mLoaderTimer.cancel();
            //mLoaderTimer.start();

            isLoading = true;

//            SKYCABLE
            mTvFetching.setText("Fetching SKYCABLE PPV history.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getSkycablePpvHistory(getSkycablePpvHistorySession);

        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }

    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    hideProgressDialog();
//                    isLoading = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    hideProgressDialog();
//                    isLoading = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    hideProgressDialog();
//                    isLoading = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                    getSkycablePpvHistory(getSkycablePpvHistorySession);
//                }
//            } else {
//                hideProgressDialog();
//                isLoading = false;
//                mLlLoader.setVisibility(View.GONE);
//                mSwipeRefreshLayout.setRefreshing(false);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSkycablePpvHistory(Callback<GetSkycablePpvHistoryResponse> getSkycablePpvHistoryCallback) {
        Call<GetSkycablePpvHistoryResponse> getskyableppvhistory = RetrofitBuild.getSkycablePpvHistoryService(getViewContext())
                .getSkycablePpvHistoryCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        String.valueOf(limit),
                        String.valueOf(year));
        getskyableppvhistory.enqueue(getSkycablePpvHistoryCallback);
    }

    private final Callback<GetSkycablePpvHistoryResponse> getSkycablePpvHistorySession = new Callback<GetSkycablePpvHistoryResponse>() {

        @Override
        public void onResponse(Call<GetSkycablePpvHistoryResponse> call, Response<GetSkycablePpvHistoryResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    showNoInternetConnection(false);
                    isloadmore = response.body().getSkycablePPVHistory().size() > 0;
                    isLoading = false;
                    isfirstload = false;

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PPV_HISTORY);
                    }

                    List<SkycablePPVHistory> skycablePPVHistory = response.body().getSkycablePPVHistory();
                    if (skycablePPVHistory.size() > 0) {
                        if (mdb != null) {
                            for (SkycablePPVHistory ppvHistory : skycablePPVHistory) {
                                mdb.insertSkycablePPVHistory(mdb, ppvHistory);
                            }
                        }
                    }

                    updateList(mdb.getSkycablePPVHistory(mdb));


                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetSkycablePpvHistoryResponse> call, Throwable t) {
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void updateList(List<SkycablePPVHistory> data) {
        if (data.size() > 0) {
            emptyLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            viewarchivelayoutv2.setVisibility(View.GONE);
            viewarchivelayout.setVisibility(View.VISIBLE);

            mAdapter.update(data);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            viewarchivelayoutv2.setVisibility(View.VISIBLE);
            viewarchivelayout.setVisibility(View.GONE);

            mAdapter.clear();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ((SkyCableActivity) getViewContext()).displayView(4, null);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshnointernet: {
                getSession();
                break;
            }
            case R.id.refresh: {
                getSession();
                break;
            }
            case R.id.viewarchivev2: {
                if (viewarchivev2.getText().toString().equals(getString(R.string.string_view_archive))) {

                    mViewArchiveDialog.show();

                } else if (viewarchivev2.getText().toString().equals(getString(R.string.string_filter_options))) {

                    mFilterOptionDialog.show();

                }
                break;
            }
            case R.id.viewarchive: {
                if (viewarchive.getText().toString().equals(getString(R.string.string_view_archive))) {

                    mViewArchiveDialog.show();

                } else if (viewarchive.getText().toString().equals(getString(R.string.string_filter_options))) {

                    mFilterOptionDialog.show();

                }
                break;
            }
            case R.id.edtYear: {
                new MaterialDialog.Builder(getViewContext())
                        .title("Select Year")
                        .items(YEARS)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                year = Integer.parseInt(text.toString());
//                                MONTHS.clear();
//                                int minMonth = year == MIN_YEAR ? MIN_MONTH : 1;
//                                MONTHS = setUpMonth(minMonth);
                                edtYear.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", text.toString()));
                            }
                        })
                        .show();
                break;
            }
            case R.id.txvDateClose: {
                mViewArchiveDialog.dismiss();
                break;
            }
            case R.id.txvFilter: {
                if (!edtYear.getText().toString().isEmpty()) {

                    mViewArchiveDialog.dismiss();

                    if (viewarchivev2.getText().toString().equals(getString(R.string.string_view_archive))) {
                        viewarchivev2.setText(getString(R.string.string_filter_options));
                    }

                    if (viewarchive.getText().toString().equals(getString(R.string.string_view_archive))) {
                        viewarchive.setText(getString(R.string.string_filter_options));
                    }

                    viewarchivelayoutv2.setVisibility(View.GONE);
                    viewarchivelayout.setVisibility(View.GONE);

                    if (mdb != null) {
                        if (mAdapter != null) {
                            mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PPV_HISTORY);
                            mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PAYMENT_VOUCHERS);
                            mAdapter.clear();
                        }

                        isfirstload = false;
                        limit = 0;
                        isbottomscroll = false;
                        isloadmore = true;
                        getSession();
                    }

                }
                break;
            }
            case R.id.txvEdtSearches: {
                mFilterOptionDialog.dismiss();
                mViewArchiveDialog.show();
                break;
            }
            case R.id.txvClearSearches: {

                edtYear.setText("");

                mFilterOptionDialog.dismiss();
                viewarchivelayoutv2.setVisibility(View.GONE);
                viewarchivelayout.setVisibility(View.GONE);

                viewarchive.setText(getString(R.string.string_view_archive));
                viewarchivev2.setText(getString(R.string.string_view_archive));

                if (mdb != null) {
                    if (mAdapter != null) {
                        mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PPV_HISTORY);
                        mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PAYMENT_VOUCHERS);
                        mAdapter.clear();
                    }

                    year = Calendar.getInstance().get(Calendar.YEAR);
                    isfirstload = false;
                    limit = 0;
                    isbottomscroll = false;
                    isloadmore = true;
                    getSession();
                }
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        if (mdb != null) {
            if (mAdapter != null) {
                mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PPV_HISTORY);
                mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PAYMENT_VOUCHERS);
                mAdapter.clear();
            }

            year = Calendar.getInstance().get(Calendar.YEAR);
            mSwipeRefreshLayout.setRefreshing(true);

            isfirstload = false;
            limit = 0;
            isbottomscroll = false;
            isloadmore = true;
            getSession();
        }
    }

    private void setUpViewArchiveDialog() {
        mViewArchiveDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(true)
                .customView(R.layout.dialog_date_view_archive, false)
                .build();

        //SET UP MONTH
//        MONTHS = setUpMonth(MIN_MONTH);

        //SET UP YEAR
        YEARS = setUpYear(MIN_YEAR);

        View view = mViewArchiveDialog.getCustomView();
        TextView txvDateClose = (TextView) view.findViewById(R.id.txvDateClose);
        txvDateClose.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "CANCEL"));
        txvDateClose.setOnClickListener(this);
        TextView txvFilter = (TextView) view.findViewById(R.id.txvFilter);
        txvFilter.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "FILTER"));
        txvFilter.setOnClickListener(this);

//        edtMonth = (MaterialEditText) view.findViewById(R.id.edtMonth);
//        edtMonth.setVisibility(View.VISIBLE);
//        edtMonth.setOnClickListener(this);
        edtYear = (MaterialEditText) view.findViewById(R.id.edtYear);
        edtYear.setOnClickListener(this);
    }

    private void setUpFilterOptions() {
        mFilterOptionDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(true)
                .customView(R.layout.dialog_date_filter_options, false)
                .build();
        View view = mFilterOptionDialog.getCustomView();
        TextView txvEdtSearches = (TextView) view.findViewById(R.id.txvEdtSearches);
        txvEdtSearches.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "EDIT SEARCHES"));
        txvEdtSearches.setOnClickListener(this);
        TextView txvClearSearches = (TextView) view.findViewById(R.id.txvClearSearches);
        txvClearSearches.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "CLEAR SEARCHES"));
        txvClearSearches.setOnClickListener(this);
    }

    private List<String> setUpYear(int minYear) {
        List<String> mYears = new ArrayList<>();
        int currYear = Calendar.getInstance().get(Calendar.YEAR);

        int numYears = currYear - minYear;

        for (int i = 0; i <= numYears; i++) {
            mYears.add(String.valueOf(minYear));
            minYear = minYear + 1;
        }

        return mYears;
    }
}
