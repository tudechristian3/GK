package com.goodkredit.myapplication.fragments.dropoff;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.dropoff.DropOffOrderAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.dropoff.DropOffOrder;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.LayoutVisibilityEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.dropoff.GetDropOffPendingOrdersResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DropOffCompletedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private DropOffOrderAdapter mAdapter;
    private RecyclerView recyclerView;
    private DatabaseHandler mdb;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private boolean isLoading = false;
    private boolean isfirstload = true;
    private boolean isloadmore = false;
    private boolean isbottomscroll = false;
    private int year = 0;
    private int month = 0;

    private boolean isScroll = true;
    private List<DropOffOrder> dropOffOrderList;

    private int limit = 0;

    //swipe refresh
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NestedScrollView nested_scroll;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private Button refreshnointernet;

    //empty layout
    private RelativeLayout emptyLayout;
    private ImageView refreshdisabled;
    private ImageView refresh;

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
    private MaterialEditText edtMonth;
    private MaterialEditText edtYear;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    public static DropOffCompletedFragment newInstance(String value) {
        DropOffCompletedFragment fragment = new DropOffCompletedFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", value);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dropoff_completed, container, false);

        init(view);
        initData();
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DropOffOrderAdapter(getViewContext());
//        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.ic_dropoff_divider), false, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);

        viewarchivelayoutv2 = view.findViewById(R.id.viewarchivelayoutv2);
        viewarchivev2 = view.findViewById(R.id.viewarchivev2);
        viewarchivev2.setOnClickListener(this);
        viewarchivev2.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", getString(R.string.string_view_archive)));
        viewarchivelayout = view.findViewById(R.id.viewarchivelayout);
        viewarchive = view.findViewById(R.id.viewarchive);
        viewarchive.setOnClickListener(this);
        viewarchive.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", getString(R.string.string_view_archive)));

        nested_scroll = view.findViewById(R.id.nested_scroll);
        nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll DOWN");
                }

                if (scrollY < oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll UP");
                }

                if (scrollY == 0) {
//                    Logger.debug("antonhttp", "TOP SCROLL");
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    Logger.debug("antonhttp", "======BOTTOM SCROLL=======");
                    isbottomscroll = true;
                    if (isloadmore) {
                        limit = limit + 30;
                        getLoadMoreSession();
                    }
                }
            }
        });

        //swipe refresh
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        //empty layout
        emptyLayout = view.findViewById(R.id.emptyLayout);
        refreshdisabled = view.findViewById(R.id.refreshdisabled);
        refresh = view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        //no internet connection
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet =  view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //loader
        mLlLoader = view.findViewById(R.id.loaderLayout);
        mTvFetching = view.findViewById(R.id.fetching);
        mTvWait = view.findViewById(R.id.wait);

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        //SET UP ARCHIVE DIALOG
        setUpViewArchiveDialog();
        setUpFilterOptions();
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        dropOffOrderList = new ArrayList<>();
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        getSession();
    }

    private class LongFirstOperation extends AsyncTask<List<DropOffOrder>, Void, List<DropOffOrder>> {

        @Override
        protected void onPreExecute() {
            visibilityType(LayoutVisibilityEnum.HIDE);

            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Fetching pending request");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<DropOffOrder> doInBackground(List<DropOffOrder>... lists) {

            try {
                Thread.sleep(1000);
                Logger.debug("antonhttp", "FETCHING DATA IN ORDER COMPLETED");

            } catch (InterruptedException e) {
                Thread.interrupted();
            }

            return mdb.getDropOffOrderCompleted(mdb);
        }

        @Override
        protected void onPostExecute(List<DropOffOrder> dropOffOrders) {
            dropOffOrderList = dropOffOrders;
            if (dropOffOrderList.size() == 0) {
                limit = 0;
                getSession();
            } else {
                limit = getLimit(dropOffOrderList.size(), 30);
                new LongOperation().execute(dropOffOrderList);
            }
        }
    }

    private void getSession() {

        visibilityType(LayoutVisibilityEnum.HIDE);

        mLoaderTimer.cancel();
        mLoaderTimer.start();

        mTvFetching.setText("Fetching pending request");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            //getDropOffOrders(getDropOffOrdersSession);
            getDropOffPaymentHistoryV2(getDropOffPaymentHistorySessionV2);
        } else {
            isLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            visibilityType(LayoutVisibilityEnum.NOINTERNET);
        }
    }

    private void getLoadMoreSession(){

        mLoaderTimer.cancel();
        mLoaderTimer.start();

        mTvFetching.setText("Fetching pending request");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            //getDropOffOrders(getDropOffOrdersSession);
            getDropOffPaymentHistoryV2(getDropOffPaymentHistoryLoreMoreSessionV2);
        } else {
            isLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            visibilityType(LayoutVisibilityEnum.NOINTERNET);
        }
    }
    @Override
    public void onRefresh() {
        if (mdb != null) {
            mdb.truncateTable(mdb, DatabaseHandler.DROPOFF_ORDERS_COMPLETED);
        }
        mAdapter.clear();
        isScroll = true;
        isfirstload = false;
        limit = 0;
        isbottomscroll = false;
        isloadmore = true;
        mSwipeRefreshLayout.setRefreshing(true);
        dropOffOrderList.clear();
        getSession();
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshnointernet:
            case R.id.refresh: {
                isScroll = true;
                isfirstload = false;
                limit = 0;
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                isbottomscroll = false;
                isloadmore = false;
                dropOffOrderList.clear();
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
                                MONTHS.clear();
                                int minMonth = year == MIN_YEAR ? MIN_MONTH : 1;
                                MONTHS = setUpMonth(minMonth);
                                edtYear.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", text.toString()));
                            }
                        })
                        .show();
                break;
            }
            case R.id.edtMonth: {
                new MaterialDialog.Builder(getViewContext())
                        .title("Select Month")
                        .items(MONTHS)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                month = Integer.valueOf(CommonFunctions.getMonthNumber(text.toString()));
                                edtMonth.setText(text.toString());
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
                if (!edtMonth.getText().toString().isEmpty() &&
                        !edtYear.getText().toString().isEmpty()) {

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

                            mAdapter.clear();
                        }

                        isScroll = true;
                        isfirstload = false;
                        limit = 0;
                        isbottomscroll = false;
                        isloadmore = false;
                        dropOffOrderList.clear();
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

                edtMonth.setText("");
                edtYear.setText("");

                mFilterOptionDialog.dismiss();
                viewarchivelayoutv2.setVisibility(View.GONE);
                viewarchivelayout.setVisibility(View.GONE);

                viewarchive.setText(getString(R.string.string_view_archive));
                viewarchivev2.setText(getString(R.string.string_view_archive));

                if (mdb != null) {
                    if (mAdapter != null) {

                        mAdapter.clear();
                    }

                    year = Calendar.getInstance().get(Calendar.YEAR);
                    month = Calendar.getInstance().get(Calendar.MONTH) + 1;

                    isScroll = true;
                    isfirstload = false;
                    limit = 0;
                    isbottomscroll = false;
                    isloadmore = false;
                    dropOffOrderList.clear();
                    getSession();
                }
                break;
            }
        }
    }

    private void getDropOffOrders(Callback<GetDropOffPendingOrdersResponse> getDropOffPendingOrdersCallback) {
        Call<GetDropOffPendingOrdersResponse> getdropoffpending = RetrofitBuild.getDropOffPendingOrdersService(getViewContext())
                .getDropOffPaymentHistoryCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        ".",
                        String.valueOf(limit),
                        "ANDROID",
                        String.valueOf(month),
                        String.valueOf(year)
                );
        getdropoffpending.enqueue(getDropOffPendingOrdersCallback);
    }

    private final Callback<GetDropOffPendingOrdersResponse> getDropOffOrdersSession = new Callback<GetDropOffPendingOrdersResponse>() {

        @Override
        public void onResponse(Call<GetDropOffPendingOrdersResponse> call, Response<GetDropOffPendingOrdersResponse> response) {
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    isLoading = false;
                    isfirstload = false;

                    List<DropOffOrder> dropOffOrders = response.body().getPendingDropOffs();

                    isloadmore = dropOffOrders.size() > 0;

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.DROPOFF_ORDERS_COMPLETED);
                    }

                    new LongInsertOperation().execute(dropOffOrders);

                    if (dropOffOrders.size() > 0) {
                        dropOffOrderList.addAll(dropOffOrders);
                        new LongOperation().execute(dropOffOrderList);
                    } else {
                        visibilityType(LayoutVisibilityEnum.NODATA);
                        if (dropOffOrderList.size() == 0) {
                            viewarchivelayoutv2.setVisibility(View.VISIBLE);
                            mAdapter.clear();
                        } else {
                            viewarchivelayoutv2.setVisibility(View.GONE);
                        }
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetDropOffPendingOrdersResponse> call, Throwable t) {
            isLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private class LongInsertOperation extends AsyncTask<List<DropOffOrder>, Void, List<DropOffOrder>> {

        @Override
        protected List<DropOffOrder> doInBackground(List<DropOffOrder>... lists) {
            try {
                Thread.sleep(1000);

                if (mdb != null) {

                    for (DropOffOrder dropOffOrder : lists[0]) {
                        mdb.insertDropOffOrderCompleted(mdb, dropOffOrder);
                    }

                }

            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<DropOffOrder> dropOffOrders) {
            super.onPostExecute(dropOffOrders);
        }
    }

    private class LongOperation extends AsyncTask<List<DropOffOrder>, Void, List<DropOffOrder>> {

        @Override
        protected List<DropOffOrder> doInBackground(List<DropOffOrder>... lists) {
            try {
                Thread.sleep(1000);
                return lists[0];

            } catch (InterruptedException e) {
                Thread.interrupted();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<DropOffOrder> dropOffOrders) {
            updateList(dropOffOrders);
            isScroll = false;
        }
    }

    private void updateList(final List<DropOffOrder> data) {
        if (data.size() > 0) {
            visibilityType(LayoutVisibilityEnum.HASDATA);
            mAdapter.addAll(data);
            viewarchivelayoutv2.setVisibility(View.GONE);
            viewarchivelayout.setVisibility(View.VISIBLE);
        } else {
            visibilityType(LayoutVisibilityEnum.NODATA);
            viewarchivelayoutv2.setVisibility(View.VISIBLE);
            viewarchivelayout.setVisibility(View.GONE);
            mAdapter.clear();
        }
    }

    private void setUpViewArchiveDialog() {
        mViewArchiveDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(true)
                .customView(R.layout.dialog_date_view_archive, false)
                .build();

        //SET UP MONTH
        MONTHS = setUpMonth(MIN_MONTH);

        //SET UP YEAR
        YEARS = setUpYear(MIN_YEAR);

        View view = mViewArchiveDialog.getCustomView();
        TextView txvDateClose = view.findViewById(R.id.txvDateClose);
        txvDateClose.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "CANCEL"));
        txvDateClose.setOnClickListener(this);
        TextView txvFilter = view.findViewById(R.id.txvFilter);
        txvFilter.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "FILTER"));
        txvFilter.setOnClickListener(this);

        edtMonth = view.findViewById(R.id.edtMonth);
        edtMonth.setVisibility(View.VISIBLE);
        edtMonth.setOnClickListener(this);
        edtYear = view.findViewById(R.id.edtYear);
        edtYear.setOnClickListener(this);
    }

    private void setUpFilterOptions() {
        mFilterOptionDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(true)
                .customView(R.layout.dialog_date_filter_options, false)
                .build();
        View view = mFilterOptionDialog.getCustomView();
        TextView txvEdtSearches = view.findViewById(R.id.txvEdtSearches);
        txvEdtSearches.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "EDIT SEARCHES"));
        txvEdtSearches.setOnClickListener(this);
        TextView txvClearSearches = view.findViewById(R.id.txvClearSearches);
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

    private List<String> setUpMonth(int minMonth) {
        //==================================
        // DateFormatSymbols().getMonths()
        // - Gets months strings.
        // e.g ( ["January","February","March","April","May","June","July","August","September","October","November","December"] )
        //==================================

        String[] months = new DateFormatSymbols().getMonths();
        List<String> mMonths = new ArrayList<>();
        mMonths.addAll(Arrays.asList(months).subList(minMonth - 1, months.length));

        return mMonths;
    }

    /**
     *  SECURITY UPDATE
     *  AS OF
     *  OCTOBER 2019
     * */

    private void getDropOffPaymentHistoryV2(Callback<GenericResponse> session){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("merchantid", ".");
            parameters.put("devicetype", CommonVariables.devicetype);
            parameters.put("limit",  String.valueOf(limit));
            parameters.put("month",  String.valueOf(month));
            parameters.put("year",  String.valueOf(year));

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getDropOffPaymentHistoryV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getDropOffPaymentHistoryV2Object(session);

        }else{
            isLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            visibilityType(LayoutVisibilityEnum.NODATA);
        }
    }

    private void getDropOffPaymentHistoryV2Object(Callback<GenericResponse> getDropOffOrdersCallback) {
        Call<GenericResponse> getdropofforders = RetrofitBuilder.getDropOffV2API(getViewContext())
                .getDropOffPaymentHistoryV2(authenticationid,sessionid,param
                );
        getdropofforders.enqueue(getDropOffOrdersCallback);
    }

    private final Callback<GenericResponse> getDropOffPaymentHistorySessionV2 = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
           mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    mdb.truncateTable(mdb,DatabaseHandler.DROPOFF_ORDERS_COMPLETED);

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<DropOffOrder> dropOffOrders = new Gson().fromJson(data, new TypeToken<List<DropOffOrder>>(){}.getType());

                    if(dropOffOrders.size() > 0 && dropOffOrders != null) {
                        isloadmore = true;
                        for (DropOffOrder dropOffOrder : dropOffOrders) {
                            mdb.insertDropOffOrderCompleted(mdb, dropOffOrder);
                        }
                    }
                    updateList(mdb.getDropOffOrderCompleted(mdb));

                }else if (response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    visibilityType(LayoutVisibilityEnum.NODATA);
                    showError(message);
                }
            } else {
                visibilityType(LayoutVisibilityEnum.NODATA);
                showError();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            visibilityType(LayoutVisibilityEnum.NODATA);
        }
    };

    private final Callback<GenericResponse> getDropOffPaymentHistoryLoreMoreSessionV2 = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<DropOffOrder> dropOffOrders = new Gson().fromJson(data, new TypeToken<List<DropOffOrder>>(){}.getType());

                    if(dropOffOrders.size() > 0 && dropOffOrders != null){
                        isloadmore = true;
                    }

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.DROPOFF_ORDERS_COMPLETED);
                    }

                    new LongInsertOperation().execute(dropOffOrders);

                    updateList(mdb.getDropOffOrderCompleted(mdb));
                    mAdapter.notifyDataSetChanged();

                }else if (response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    visibilityType(LayoutVisibilityEnum.NODATA);
                    showError(message);
                }
            } else {
                visibilityType(LayoutVisibilityEnum.NODATA);
                showError();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            visibilityType(LayoutVisibilityEnum.NODATA);
        }
    };

    //
    private void enableRefresh(){
        refreshdisabled.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);
        mLlLoader.setVisibility(View.GONE);
    }
    private void disableRefresh(){
        refreshdisabled.setVisibility(View.VISIBLE);
        refresh.setVisibility(View.GONE);
    }

    private void shownoInternet(boolean show){
        if(show){
            nointernetconnection.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);
        }else{
            nointernetconnection.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }



    //layouts
    private void visibilityType(LayoutVisibilityEnum visibilityEnum) {
        switch (visibilityEnum) {
            case HASDATA:
                nointernetconnection.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
                mLlLoader.setVisibility(View.GONE);
                break;
            case NOINTERNET:
                nointernetconnection.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.GONE);
                mLlLoader.setVisibility(View.GONE);

                break;
            case NODATA:
                nointernetconnection.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
                mLlLoader.setVisibility(View.GONE);
                break;

            case HIDE:
                nointernetconnection.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.GONE);
                break;
        }
    }

}
