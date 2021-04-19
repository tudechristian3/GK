package com.goodkredit.myapplication.activities.paramount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.paramount.ViewParamountHistoryAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.ParamountQueue;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.paramount.GetParamountHistoryResponse;
import com.goodkredit.myapplication.responses.SendEmailForBillsPaymentResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewParamountHistoryActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private List<ParamountQueue> mGridData;
    private ViewParamountHistoryAdapter mViewParamountHistoryAdapter;
    private DatabaseHandler mdb;
    private RecyclerView recyclerView;
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

//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
//    private String year;

    private NestedScrollView nested_scroll;

    private boolean isloadmore;
    private boolean isbottomscroll;
    private boolean isfirstload = true;
    private boolean isLoading = false;
    private int limit = 0;

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
    private int year = 0;
    private int month = 0;

    //EMAIL
    private MaterialDialog emailDialog;
    private String emailaddress = "";
    private String registrationid = "";
    private String transactionno = "";
    private String mobileno = "";

    //UNIFIED SESSION
    private String sessionid = "";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paramount_history);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setupToolbar();

        setTitle("Paramount History");

        init();

        initData();
    }

    private void init() {
        //swipe refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        //loader
        mLlLoader = (RelativeLayout) findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) findViewById(R.id.fetching);
        mTvWait = (TextView) findViewById(R.id.wait);

        //no internet connection
        nointernetconnection = (RelativeLayout) findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //empty layout
        emptyLayout = (RelativeLayout) findViewById(R.id.emptyLayout);
        textView11 = (TextView) findViewById(R.id.textView11);
        refresh = (ImageView) findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        recyclerView.setNestedScrollingEnabled(false);
        mViewParamountHistoryAdapter = new ViewParamountHistoryAdapter(getViewContext());
        recyclerView.setAdapter(mViewParamountHistoryAdapter);

        //VIEW ARCHIVE
        viewarchivelayoutv2 = (LinearLayout) findViewById(R.id.viewarchivelayoutv2);
        viewarchivev2 = (TextView) findViewById(R.id.viewarchivev2);
        viewarchivev2.setOnClickListener(this);
        viewarchivev2.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", getString(R.string.string_view_archive)));
        viewarchivelayout = (LinearLayout) findViewById(R.id.viewarchivelayout);
        viewarchive = (TextView) findViewById(R.id.viewarchive);
        viewarchive.setOnClickListener(this);
        viewarchive.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", getString(R.string.string_view_archive)));

        //SET UP ARCHIVE DIALOG
        setUpViewArchiveDialog();
        setUpFilterOptions();

        nested_scroll = (NestedScrollView) findViewById(R.id.nested_scroll);
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

//                    if (mdb.getParamountQueue(mdb).size() > 0) {
//                        mBtnMore.setVisibility(View.VISIBLE);
//                        if (isyearselected) {
//                            mBtnMore.setText("FILTER OPTIONS");
//                        } else {
//                            mBtnMore.setText("VIEW ARCHIVE");
//                        }
//                    }

                }
            }
        });

    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        mGridData = new ArrayList<>();

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        mobileno = mdb.getSubscriber(mdb).getMobileNumber();
        year = Calendar.getInstance().get(Calendar.YEAR);

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        List<ParamountQueue> mData = mdb.getParamountHistory(mdb);
        if (mData.size() > 0) {
            mViewParamountHistoryAdapter.update(mData);
        }

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

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            isLoading = true;

            mTvFetching.setText("Fetching paramount history.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getParamountHistory(getParamountHistorySession);

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
//                    getParamountHistory(getParamountHistorySession);
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

    private void getParamountHistory(Callback<GetParamountHistoryResponse> getParamountHistoryCallback) {
        Call<GetParamountHistoryResponse> getparamounthistory = RetrofitBuild.getParamountHistoryService(getViewContext())
                .getParamountHistoryCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        String.valueOf(limit),
                        String.valueOf(year));
        getparamounthistory.enqueue(getParamountHistoryCallback);
    }

    private final Callback<GetParamountHistoryResponse> getParamountHistorySession = new Callback<GetParamountHistoryResponse>() {

        @Override
        public void onResponse(Call<GetParamountHistoryResponse> call, Response<GetParamountHistoryResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    showNoInternetConnection(false);
                    isloadmore = response.body().getParamountHistory().size() > 0;
                    isLoading = false;
                    isfirstload = false;

                    if (!isbottomscroll) {

                        mdb.truncateTable(mdb, DatabaseHandler.PARAMOUNT_HISTORY);

                    }

                    List<ParamountQueue> paramountHistory = response.body().getParamountHistory();
                    if (paramountHistory.size() > 0) {

                        for (ParamountQueue paramountQueue : paramountHistory) {
                            mdb.insertParamountHistory(mdb, paramountQueue);
                        }

                    }

                    updateList(mdb.getParamountHistory(mdb));

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetParamountHistoryResponse> call, Throwable t) {
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void updateList(List<ParamountQueue> data) {
        showNoInternetConnection(false);
        if (data.size() > 0) {
            emptyLayout.setVisibility(View.GONE);
            mViewParamountHistoryAdapter.update(data);
            viewarchivelayoutv2.setVisibility(View.GONE);
            viewarchivelayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            viewarchivelayoutv2.setVisibility(View.VISIBLE);
            viewarchivelayout.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
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
                                //MONTHS.clear();
                                //int minMonth = year == MIN_YEAR ? MIN_MONTH : 1;
                                //MONTHS = setUpMonth(minMonth);
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

                    if (mViewArchiveDialog != null) {
                        mViewArchiveDialog.dismiss();
                    }

                    if (viewarchivev2.getText().toString().equals(getString(R.string.string_view_archive))) {
                        viewarchivev2.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", getString(R.string.string_filter_options)));
                    }

                    if (viewarchive.getText().toString().equals(getString(R.string.string_view_archive))) {
                        viewarchive.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", getString(R.string.string_filter_options)));
                    }

                    if (mdb != null) {
                        mdb.truncateTable(mdb, DatabaseHandler.PARAMOUNT_HISTORY);
                    }

                    if (mViewParamountHistoryAdapter != null) {
                        mViewParamountHistoryAdapter.clear();
                    }

                    recyclerView.setVisibility(View.GONE);
                    limit = 0;
                    isbottomscroll = false;
                    isloadmore = false;
                    isfirstload = false;
                    getSession();

                }

                break;
            }
            case R.id.txvEdtSearches: {
                mFilterOptionDialog.dismiss();
                mViewArchiveDialog.show();
                break;
            }
            case R.id.txvClearSearches: {

                //edtMonth.setText("");
                edtYear.setText("");

                if (mdb != null) {
                    mdb.truncateTable(mdb, DatabaseHandler.PARAMOUNT_HISTORY);
                }

                if (mViewParamountHistoryAdapter != null) {
                    mViewParamountHistoryAdapter.clear();
                }

                mFilterOptionDialog.dismiss();

                recyclerView.setVisibility(View.GONE);
                limit = 0;
                isbottomscroll = false;
                isloadmore = true;
                isfirstload = false;
                year = Calendar.getInstance().get(Calendar.YEAR);
                getSession();

                viewarchive.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", getString(R.string.string_view_archive)));
                viewarchivev2.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", getString(R.string.string_view_archive)));

                break;
            }
        }
    }

    private void setUpViewArchiveDialog() {
        mViewArchiveDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(true)
                .customView(R.layout.dialog_date_view_archive, false)
                .build();

        //SET UP MONTH
        //MONTHS = setUpMonth(MIN_MONTH);

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

    @Override
    public void onRefresh() {
        if (mdb != null) {
            mdb.truncateTable(mdb, DatabaseHandler.PARAMOUNT_HISTORY);
            mdb.truncateTable(mdb, DatabaseHandler.PARAMOUNT_PAYMENT_VOUCHERS);
            if (mViewParamountHistoryAdapter != null) {
                mViewParamountHistoryAdapter.clear();
            }
            viewarchivelayoutv2.setVisibility(View.GONE);
            viewarchivelayout.setVisibility(View.GONE);
            year = Calendar.getInstance().get(Calendar.YEAR);
            mSwipeRefreshLayout.setRefreshing(true);
            isfirstload = false;
            limit = 0;
            isbottomscroll = false;
            isloadmore = true;
            getSession();
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ViewParamountHistoryActivity.class);
        context.startActivity(intent);
    }

    public void sendParamountEmail(ParamountQueue paramountQueue) {
        emailaddress = mdb.getEmail(mdb);

        registrationid = paramountQueue.getRegistrationID();
        transactionno = paramountQueue.getTransactionNo();

        String email = paramountQueue.getEmailAddress() == null ? emailaddress : paramountQueue.getEmailAddress();

        emailDialog = new MaterialDialog.Builder(getViewContext())
                .autoDismiss(false)
                .cancelable(false)
                .title("Email")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Add email address", email, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        emailaddress = input.toString();
                    }
                })
                .positiveText("Send")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (emailaddress.length() > 0) {
                            getCreateSession();
                        } else {
                            showToast("Please input an email address", GlobalToastEnum.WARNING);
                        }
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void getCreateSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Sending email.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            CommonFunctions.showDialog(getViewContext(), "Sending email", "Please wait...", false);

//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callcreatesession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            sendEmailForParamount(sendEmailForBillsPaymentSession);

        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showError(getString(R.string.generic_internet_error_message));
        }
    }

//    private final Callback<String> callcreatesession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    CommonFunctions.hideDialog();
//                } else if (responseData.equals("error")) {
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    CommonFunctions.hideDialog();
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    CommonFunctions.hideDialog();
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                    sendEmailForParamount(sendEmailForBillsPaymentSession);
//                }
//            } else {
//                mLlLoader.setVisibility(View.GONE);
//                mSwipeRefreshLayout.setRefreshing(false);
//                CommonFunctions.hideDialog();
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            mLlLoader.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setRefreshing(false);
//        }
//    };

    private void sendEmailForParamount(Callback<SendEmailForBillsPaymentResponse> sendEmailForParamountCallback) {
        Call<SendEmailForBillsPaymentResponse> sendemail = RetrofitBuild.sendEmailForParamountService(getViewContext())
                .sendEmailForParamountCall(imei,
                        authcode,
                        sessionid,
                        userid,
                        registrationid,
                        transactionno,
                        emailaddress,
                        mobileno,
                        borrowerid);
        sendemail.enqueue(sendEmailForParamountCallback);
    }

    private final Callback<SendEmailForBillsPaymentResponse> sendEmailForBillsPaymentSession = new Callback<SendEmailForBillsPaymentResponse>() {

        @Override
        public void onResponse(Call<SendEmailForBillsPaymentResponse> call, Response<SendEmailForBillsPaymentResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            CommonFunctions.hideDialog();
            if (errorBody == null) {

                evaluateResponse(response);

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
                break;
            }
            default: {
                MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                        .title("Email")
                        .content(response.body().getMessage())
                        .positiveText("Close")
                        .show();
                break;
            }
        }
    }
}
