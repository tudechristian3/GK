package com.goodkredit.myapplication.activities.vouchers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.prepaidrequest.VoucherPrepaidRequestActivity;
import com.goodkredit.myapplication.activities.transfer.TransferThruBorrower;
import com.goodkredit.myapplication.activities.vouchers.grouping.GroupVoucherActivity;
import com.goodkredit.myapplication.adapter.VoucherSummeryAdapter;
import com.goodkredit.myapplication.adapter.vouchers.VouchersRecyclerAdapter;
import com.goodkredit.myapplication.adapter.vouchers.payoutone.VoucherPayoutOneAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.bean.VoucherSummary;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.fragments.VouchersFragment;
import com.goodkredit.myapplication.fragments.VouchersFragment;
import com.goodkredit.myapplication.utilities.EqualSpacingItemDecoration;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.fragments.prepaidrequest.VirtualVoucherProductFragment;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.responses.GetVoucherResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.SpacesItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private VouchersRecyclerAdapter mAdapter;
    private ArrayList<Voucher> mGridData;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRefreshLayout emptyswiperefresh;

    private DatabaseHandler mdb;
    private boolean isLoading = false;
    private boolean isfirstload = true;
    private boolean isloadmore = false;
    private boolean isbottomscroll = false;
    private boolean isScroll = true;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String guarantorid;
    private int limit = 0;

    private TextView lastupdate;
    private RelativeLayout scrollindicator;
    private ImageView dragdown;

    private LinearLayout voucherLayout;
    private LinearLayout btnFunctionsLayout;

    private LinearLayout linearSortVoucherLayout;
    private LinearLayout linearGroupVoucherLayout;
    private LinearLayout linearViewVoucherLayout;
    private LinearLayout linearTransferVoucherLayout;
    private LinearLayout linearMoreLayout;
    private LinearLayout linearCloseDialogLayout;


    private Dialog dialog;
    private String selectedsort = "";
    private String sortoption = "ASC";

    //view voucher summary
    private ListView vouchersummarylist;
    private VoucherSummeryAdapter mlistAdapter;
    private RelativeLayout vouchersummarywrap;
    private ArrayList<VoucherSummary> mlistData;

    private FrameLayout emptyvoucher;
    private Button btn_buy_vocher;
    private Button btn_refer;
    private TextView tv_or;

    //UNIFIED SESSION
    private String sessionid = "";

    //PAYOUT ONE VOUCHERS
    private RecyclerView rv_vouchers_payoutone;
    private VoucherPayoutOneAdapter mAdapter_PayoutOne;
    private ArrayList<Voucher> mGridData_PayoutOne;

    //NEW VARIABLE FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private AlertDialog alertDialog = null;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        init();
        initData();

    }

    private void init() {

        Logger.debug("roneldayanan","INIT START");

        setupToolbar();
        setTitle("Vouchers");
        recyclerView = findViewById(R.id.recyclerView);

        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_animation);

        int columns = CommonFunctions.calculateNoOfColumns(getViewContext(), Voucher.KEY_MAIN_VOUCHER);
        recyclerView.setLayoutManager(new GridLayoutManager(getViewContext(), columns));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(8));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
        mAdapter = new VouchersRecyclerAdapter(getViewContext());
        recyclerView.setAdapter(mAdapter);

        //swipe refresh
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        //empty swipe refresh
        emptyswiperefresh = findViewById(R.id.emptyswiperefresh);
        emptyswiperefresh.setOnRefreshListener(this);

        //no internet connection
        nointernetconnection = findViewById(R.id.nointernetconnection);
        refreshnointernet = findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //loader
        mLlLoader = findViewById(R.id.loaderLayout);
        mTvFetching = findViewById(R.id.fetching);
        mTvWait = findViewById(R.id.wait);

        lastupdate = findViewById(R.id.lastupdate);
        scrollindicator = findViewById(R.id.scrollindicator);
        dragdown = findViewById(R.id.dragdown);

        voucherLayout = findViewById(R.id.voucherLayout);
        btnFunctionsLayout = findViewById(R.id.btnFunctionsLayout);

        linearGroupVoucherLayout = findViewById(R.id.linearGroupVoucherLayout);
        linearTransferVoucherLayout = findViewById(R.id.linearTransferVoucherLayout);
        linearMoreLayout = findViewById(R.id.linearMoreLayout);

        linearGroupVoucherLayout.setOnClickListener(this);
        linearTransferVoucherLayout.setOnClickListener(this);
        linearMoreLayout.setOnClickListener(this);

        //voucher summary
        vouchersummarylist = findViewById(R.id.vouchersummarylist);
        mlistAdapter = new VoucherSummeryAdapter(getViewContext(), R.layout.activity_voucher_summary_item);
        vouchersummarylist.setAdapter(mlistAdapter);
        vouchersummarywrap = findViewById(R.id.vouchersummarywrap);
        vouchersummarywrap.setOnClickListener(this);

        emptyvoucher = findViewById(R.id.emptyvoucher);
        btn_buy_vocher = findViewById(R.id.btn_buy_vocher);
        btn_buy_vocher.setOnClickListener(this);
        btn_refer = findViewById(R.id.btn_refer);
        btn_refer.setOnClickListener(this);
        tv_or = findViewById(R.id.tv_or);

        LayoutAnimationController controller1 =
                AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_animation);

        //payoutone
        rv_vouchers_payoutone = findViewById(R.id.rv_vouchers_payoutone);
        int columns2 = CommonFunctions.calculateNoOfColumns(getViewContext(), Voucher.KEY_PAYOUTONE_VOUCHER);
        rv_vouchers_payoutone.setLayoutManager(new GridLayoutManager(getViewContext(), columns2));
        rv_vouchers_payoutone.addItemDecoration(new SpacesItemDecoration(8));
        rv_vouchers_payoutone.setNestedScrollingEnabled(false);
        rv_vouchers_payoutone.setLayoutAnimation(controller1);
        rv_vouchers_payoutone.scheduleLayoutAnimation();
        mAdapter_PayoutOne = new VoucherPayoutOneAdapter(this);
        rv_vouchers_payoutone.setAdapter(mAdapter_PayoutOne);

        //show dragdown to refresh
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                hideDragIndicator();
            }
        }.start();

        Logger.debug("roneldayanan","INIT END");
    }

    private void initData() {

        Logger.debug("roneldayanan","initData START");

        mdb = new DatabaseHandler(getViewContext());
        V2Subscriber v2Subscriber = mdb.getSubscriber(mdb);
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        guarantorid = v2Subscriber.getGuarantorID();
        mGridData = new ArrayList<>();
        mlistData = new ArrayList<>();

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);

                if (!recyclerView.canScrollVertically(-1)) {
                    //onScrolledToTop();
                } else if (!recyclerView.canScrollVertically(1)) {
                    onScrolledToBottom();
                }
                if (dy < 0) {
                    //onScrolledUp();
                } else {
                    //onScrolledDown();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });

        if (!CommonVariables.VOUCHERISFIRSTLOAD) {
            Logger.debug("roneldayanan","initData COMMONVARIABLES : "+ CommonVariables.VOUCHERISFIRSTLOAD);
            new LongFirstLoadOperation().execute();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    new LongFirstLoadPayOutOneOperation().execute();
                }
            }, 1000);
        }


//        createSession(getSessionCallback);
        validateReferralIfAvailable();

        Logger.debug("roneldayanan","initData END");
    }

    private void onScrolledToBottom() {
        if (!isLoading) {
            if (isloadmore) {
                if (!isfirstload) {
                    isbottomscroll = true;
                    limit = limit + 1000;
                    getSession();
                }
            }
        }
    }

    private void getSession() {
        Logger.debug("roneldayanan","getSession start");
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            Logger.debug("roneldayanan","getSession positive");
            isLoading = true;

            mTvFetching.setText("Fetching available vouchers...");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            //getVoucherV3(getVoucherV3Session);
            getVouchersV3withSecurity();
        } else {
            Logger.debug("roneldayanan","getSession negashit");
            emptyswiperefresh.setRefreshing(false);
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showError(getString(R.string.generic_internet_error_message));
        }
    }

    private void getVoucherV3(Callback<GetVoucherResponse> getVoucherV3Callback) {

        Call<GetVoucherResponse> getvouchers = RetrofitBuild.getVoucherV3Service(getViewContext())
                .getVoucherV3Call(borrowerid,
                        guarantorid,
                        sessionid,
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        String.valueOf(limit));
        getvouchers.enqueue(getVoucherV3Callback);
    }

    private final Callback<GetVoucherResponse> getVoucherV3Session = new Callback<GetVoucherResponse>() {

        @Override
        public void onResponse(Call<GetVoucherResponse> call, Response<GetVoucherResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            emptyswiperefresh.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            isLoading = false;
            isfirstload = false;
            isScroll = true;

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    CommonVariables.VOUCHERISFIRSTLOAD = false;

                    ArrayList<Voucher> voucherList = response.body().getVouchers();

                    isloadmore = voucherList.size() > 0;

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.VOUCHERS);
                    }

                    new LongInsertOperation().execute(voucherList);

                    if (voucherList.size() > 0) {
                        btnFunctionsLayout.setVisibility(View.VISIBLE);
                        voucherLayout.setVisibility(View.VISIBLE);
                        emptyswiperefresh.setVisibility(View.GONE);
                        mGridData.addAll(voucherList);
                        emptyvoucher.setVisibility(View.GONE);
                        new LongOperation().execute();
                        new LongPayOutOneOperation().execute();

                        //getVoucherSummary
                        if (response.body().getVouchersSummary().size() > 0) {
                            mdb.deleteVoucherSummary(mdb);
                            new LongInsertVoucherSummaryOperation().execute(response.body().getVouchersSummary());
                        }

                    } else {
                        if (mGridData.size() == 0 || limit == 0) {
                            mAdapter.clear();
                            mAdapter_PayoutOne.clear();
                            emptyvoucher.setVisibility(View.VISIBLE);
                            btnFunctionsLayout.setVisibility(View.GONE);
                            voucherLayout.setVisibility(View.GONE);
                            emptyswiperefresh.setVisibility(View.VISIBLE);
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
        public void onFailure(Call<GetVoucherResponse> call, Throwable t) {
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            emptyswiperefresh.setRefreshing(false);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private class LongInsertVoucherSummaryOperation extends AsyncTask<ArrayList<VoucherSummary>, Void, ArrayList<VoucherSummary>> {

        @Override
        protected ArrayList<VoucherSummary> doInBackground(ArrayList<VoucherSummary>... lists) {
            if (mdb != null) {
                for (VoucherSummary summary : lists[0]) {
                    mdb.insertVouchersSummary(mdb, summary);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<VoucherSummary> vouchers) {
            super.onPostExecute(vouchers);


        }
    }

    private class LongFirstLoadOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {

        @Override
        protected ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
            mGridData = mdb.getAllVouchers(mdb);
            return mGridData;
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> voucherData) {
            if (voucherData.size() > 0) {
                if (CommonVariables.VOUCHERISFIRSTLOAD) {
                    limit = 0;
                } else {
                    limit = getLimit(mGridData.size(), 1000);
                }
                emptyvoucher.setVisibility(View.GONE);
                btnFunctionsLayout.setVisibility(View.VISIBLE);
                voucherLayout.setVisibility(View.VISIBLE);
                emptyswiperefresh.setVisibility(View.GONE);
                mAdapter.clear();
                mAdapter.addAll(voucherData);
            }
        }
    }

    private class LongFirstLoadPayOutOneOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {

        @Override
        protected ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
            mGridData_PayoutOne = mdb.getChequeVoucher(mdb);
            return mGridData_PayoutOne;
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> voucherData) {
            if (voucherData.size() > 0) {
                if (CommonVariables.VOUCHERISFIRSTLOAD) {
                    limit = 0;
                } else {
                    limit = getLimit(mGridData_PayoutOne.size(), 1000);
                }
                mAdapter_PayoutOne.clear();
                mAdapter_PayoutOne.addAll(voucherData);
            }
        }
    }

    private class LongOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {

        @Override
        protected ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
            Logger.debug("roneldayanan","LongOperation CALLED");
            return mdb.getAllVouchers(mdb);
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> voucherData) {
            Logger.debug("roneldayanan","LongOperation POST");
            if (voucherData.size() > 0) {
                Logger.debug("roneldayanan","LongOperation POST > 0");
                if (!isLoading) {
                    Logger.debug("roneldayanan","LongOperation ADD DATA");
                    mAdapter.clear();
                    mAdapter.addAll(voucherData);
                }
            } else {
                Logger.debug("roneldayanan","LongOperation POST < 0");
                mAdapter.clear();
            }
        }
    }

    private class LongPayOutOneOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {

        @Override
        protected ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
//            mGridData_PayoutOne.clear();
            Logger.debug("roneldayanan","LongPayOutOneOperation DoINBACKGROUND");
            mGridData_PayoutOne = mdb.getChequeVoucher(mdb);
            return mGridData_PayoutOne;

        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> voucherData) {
            Logger.debug("roneldayanan","LongPayOutOneOperation onPostExecute");
//            isScroll = false;
//            if (isAdded()) {
//            if (voucherData.size() > 0) {
//                if (!isLoading) {
//                    mAdapter_PayoutOne.clear();
//                    mAdapter_PayoutOne.addAll(mGridData_PayoutOne);
//                }
//            } else {
//                mAdapter_PayoutOne.clear();
//            }
//            }

            if (voucherData.size() > 0) {
                Logger.debug("roneldayanan","LongPayOutOneOperation onPostExecute > 0");
                if (!isLoading) {
                    Logger.debug("roneldayanan","LongPayOutOneOperation ADD ALL");
                    mAdapter_PayoutOne.clear();
                    mAdapter_PayoutOne.addAll(voucherData);
                }
            } else {
                Logger.debug("roneldayanan","LongPayOutOneOperation CLEAR ALL");
                mAdapter_PayoutOne.clear();
            }
        }
    }

    private class LongInsertOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {

        @Override
        protected ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
            if (mdb != null) {
                for (Voucher voucher : lists[0]) {
                    Logger.debug("roneldayanan","LongInsertOperation INSERT DATA : ");
                    mdb.insertVouchers(mdb, voucher);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> vouchers) {
            super.onPostExecute(vouchers);
            Logger.debug("roneldayanan","LongInsertOperation  DATA INSERTED : ");
            String miliseconds = "";
            long time = System.currentTimeMillis();
            miliseconds = Long.toString(time);
            checkVoucherLastUpdated(miliseconds);

        }
    }


    public void checkVoucherLastUpdated(String lastupdatedval) {
        Logger.debug("roneldayanan","checkVoucherLastUpdated  START: ");
        try {
            //check if vouchers last updated
            long curtime = System.currentTimeMillis(); //current date
            long voucherdate = Long.parseLong(lastupdatedval); //voucher last updated
            long difference = curtime - voucherdate; //difference of two date
            int min = Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(difference)).intValue(); //difference in minute
            int hours = Long.valueOf(TimeUnit.MILLISECONDS.toHours(difference)).intValue(); //difference in hour
            if (min >= 30) {
                if (hours > 0 && hours < 24) {
                    lastupdate.setText("LAST UPDATED " + hours + " hours ago. Drag down to refresh.");
                } else if (hours >= 24) {
                    showGlobalDialogs("It seems that you have not refreshed your vouchers for a long time.", "Close", ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.NOTICE);
                    lastupdate.setText("LAST UPDATED " + convertDate(Long.toString(curtime), "dd/MM/yyyy hh:mm:ss") + ". Drag down to refresh");
                } else {
                    lastupdate.setText("LAST UPDATED " + min + " minutes ago. Drag down to refresh.");

                }
                lastupdate.setVisibility(View.VISIBLE);
                dragdown.setVisibility(View.GONE);
            } else {
                lastupdate.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Logger.debug("roneldayanan","checkVoucherLastUpdated  END: ");
    }

    public static String convertDate(String dateInMilliseconds, String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyswiperefresh.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    public void hideDragIndicator() {
        try {

            Animation fadeOut = new AlphaAnimation(1, 0);
            fadeOut.setInterpolator(new AccelerateInterpolator());
            fadeOut.setDuration(1000);

            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationEnd(Animation animation) {
                    dragdown.setVisibility(View.GONE);
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            });

            dragdown.startAnimation(fadeOut);
        } catch (Exception e) {
        }

    }

    @Override
    public void onRefresh() {

        Logger.debug("roneldayanan","onRefresh");

        limit = 0;
        isScroll = true;
        isfirstload = false;
        isbottomscroll = false;
        isloadmore = false;
        mSwipeRefreshLayout.setRefreshing(true);
        mGridData.clear();
        getSession();
    }

    public void openSortOption() {

        try {
            //create a Filter confirmation
            dialog = new Dialog(new ContextThemeWrapper(getViewContext(), android.R.style.Theme_Holo_Light));
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_sort);
            dialog.show();

            final RadioGroup sortvaluebtn = dialog.findViewById(R.id.sortwrap);
            RadioGroup sortoptionbtn = dialog.findViewById(R.id.sortoptn);
            TextView sortbtn = dialog.findViewById(R.id.sortbtn);
            TextView cancelsort = dialog.findViewById(R.id.cancelsort);

            //set selected
            switch (selectedsort) {
                case "id":
                    sortvaluebtn.check(R.id.lastupdated);
                    break;
                case "amount":
                    sortvaluebtn.check(R.id.amount);
                    break;
                case "remainingamount":
                    sortvaluebtn.check(R.id.remainingamount);
                    break;
            }
            switch (sortoption) {
                case "ASC":
                    sortoptionbtn.check(R.id.asc);
                    break;
                case "DESC":
                    sortoptionbtn.check(R.id.desc);
                    break;
            }

            sortvaluebtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int childCount = group.getChildCount();
                    for (int x = 0; x < childCount; x++) {
                        RadioButton btn = (RadioButton) group.getChildAt(x);
                        if (btn.getId() == checkedId) {
                            String selectedid = btn.getText().toString();
                            // Check which radio button was clicked
                            switch (selectedid.trim()) {
                                case "By Date":
                                    selectedsort = "id";
                                    break;
                                case "By Denom":
                                    selectedsort = "amount";
                                    break;
                                case "By Balance":
                                    selectedsort = "remainingamount";
                                    break;
                            }

                        }
                    }
                }


            });

            sortoptionbtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int childCount = group.getChildCount();
                    for (int x = 0; x < childCount; x++) {
                        RadioButton btn = (RadioButton) group.getChildAt(x);
                        if (btn.getId() == checkedId) {
                            String selectedsortoption = btn.getText().toString();
                            // Check which radio button was clicked
                            switch (selectedsortoption.trim()) {
                                case "Ascending":
                                    sortoption = "ASC";
                                    Logger.debug("SORT", "selected" + sortoption);
                                    break;
                                case "Descending":
                                    sortoption = "DESC";
                                    Logger.debug("SORT", "selected" + sortoption);
                                    break;
                            }
                        }
                    }
                }
            });

            //Cancel in Dialog
            cancelsort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.hide();
                }
            });

            //Sort  in Dialog
            sortbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!selectedsort.equals("")) {
                        dialog.dismiss();
                        // reloadSortedVoucher();
                        sortVouchers(selectedsort, sortoption);
                    } else {
                        showToast("Please select sort option", GlobalToastEnum.WARNING);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openSortOptionNew() {

        try {
            GlobalDialogs dialog = new GlobalDialogs(getViewContext());

            dialog.createDialog("Sort Voucher", "",
                    "CANCEL", "SORT", ButtonTypeEnum.DOUBLE,
                    false, false, DialogTypeEnum.RADIO);

            View closebtn = dialog.btnCloseImage();
            closebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            //VOUCHER ORDER
            List<String> voucherorder = new ArrayList<>();
            voucherorder.add("Ascending");
            voucherorder.add("Descending");

            LinearLayout voucherOrderGroupContainer = dialog.setContentRadio(voucherorder, RadioGroup.HORIZONTAL,
                    new GlobalDialogsObject(R.color.colorTextGrey, 15, 0));

            RadioGroup voucherOrderGroup = new RadioGroup(getViewContext());

            int radCount = voucherOrderGroupContainer.getChildCount();
            for (int i = 0; i < radCount; i++) {
                View view = voucherOrderGroupContainer.getChildAt(i);
                if (view instanceof RadioGroup) {
                    voucherOrderGroup = (RadioGroup) view;
                }
            }

            switch (sortoption) {
                case "ASC":
                    for(int i =  0; i < voucherorder.size(); i++) {
                        String tag = voucherOrderGroup.getChildAt(i).getTag().toString();
                        if(tag.equals("Ascending")) {
                            voucherOrderGroup.check(voucherOrderGroup.getChildAt(i).getId());
                            break;
                        }
                    }
                    break;
                case "DESC":
                    for(int i =  0; i < voucherorder.size(); i++) {
                        String tag = voucherOrderGroup.getChildAt(i).getTag().toString();
                        if(tag.equals("Descending")) {
                            voucherOrderGroup.check(voucherOrderGroup.getChildAt(i).getId());
                            break;
                        }
                    }
                    break;
            }

            voucherOrderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int childCount = group.getChildCount();
                    for (int x = 0; x < childCount; x++) {
                        RadioButton btn = (RadioButton) group.getChildAt(x);
                        if (btn.getId() == checkedId) {
                            String selectedsortoption = btn.getText().toString();
                            // Check which radio button was clicked
                            switch (selectedsortoption.trim()) {
                                case "Ascending":
                                    sortoption = "ASC";
                                    Logger.debug("SORT", "selected" + sortoption);
                                    break;
                                case "Descending":
                                    sortoption = "DESC";
                                    Logger.debug("SORT", "selected" + sortoption);
                                    break;
                            }
                        }
                    }
                }
            });

            View v = new View(this);
            v.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2
            ));
            v.setBackgroundColor(Color.parseColor("#B3B3B3"));

            voucherOrderGroupContainer.addView(v);

            //SORT VOUCHER ORDER BY BY...
            List<String> sortVoucher = new ArrayList<>();
            sortVoucher.add("By Date");
            sortVoucher.add("By Denom");
            sortVoucher.add("By Balance");

            LinearLayout sortVoucherGroupContainer = dialog.setContentRadio(sortVoucher, RadioGroup.VERTICAL,
                    new GlobalDialogsObject(R.color.colorTextGrey, 15, 0));

            RadioGroup sortVoucherGroup = new RadioGroup(getViewContext());

            int count = sortVoucherGroupContainer.getChildCount();
            for (int i = 0; i < count; i++) {
                View view = sortVoucherGroupContainer.getChildAt(i);
                if (view instanceof RadioGroup) {
                    sortVoucherGroup = (RadioGroup) view;
                }
            }

            sortVoucherGroup.setPadding(25, 25, 25, 25);

            //set selected
            switch (selectedsort) {
                case "id":
                    for(int i =  0; i < sortVoucher.size(); i++) {
                        String tag = sortVoucherGroup.getChildAt(i).getTag().toString();
                        if(tag.equals("By Date")) {
                            sortVoucherGroup.check(sortVoucherGroup.getChildAt(i).getId());
                            break;
                        }
                    }
                    break;
                case "amount":
                    for(int i =  0; i < sortVoucher.size(); i++) {
                        String tag = sortVoucherGroup.getChildAt(i).getTag().toString();
                        if(tag.equals("By Denom")) {
                            sortVoucherGroup.check(sortVoucherGroup.getChildAt(i).getId());
                            break;
                        }
                    }
                    break;
                case "remainingamount":
                    for(int i =  0; i < sortVoucher.size(); i++) {
                        String tag = sortVoucherGroup.getChildAt(i).getTag().toString();
                        if(tag.equals("By Balance")) {
                            sortVoucherGroup.check(sortVoucherGroup.getChildAt(i).getId());
                            break;
                        }
                    }
                    break;
            }

            sortVoucherGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int childCount = group.getChildCount();
                    for (int x = 0; x < childCount; x++) {
                        RadioButton btn = (RadioButton) group.getChildAt(x);

                        if (btn.getId() == checkedId) {
                            String selectedid = btn.getText().toString();
                            // Check which radio button was clicked
                            switch (selectedid.trim()) {
                                case "By Date":
                                    selectedsort = "id";
                                    break;
                                case "By Denom":
                                    selectedsort = "amount";
                                    break;
                                case "By Balance":
                                    selectedsort = "remainingamount";
                                    break;
                            }

                        }
                    }
                }

            });


            View btndoubleone = dialog.btnDoubleOne();
            btndoubleone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            View btndoubletwo = dialog.btnDoubleTwo();
            btndoubletwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!selectedsort.equals("")) {
                        dialog.dismiss();
                        // reloadSortedVoucher();
                        sortVouchers(selectedsort, sortoption);
                    } else {
                        showToast("Please select sort option", GlobalToastEnum.WARNING);
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    private void sortVouchers(String selected, String option) {
        mGridData = mdb.getSortedVouchers(mdb, selected, option);
        mAdapter.clear();
        mAdapter.addAll(mGridData);
    }

    private void populateSummary() {
        mlistData.clear();
        Cursor cursor = mdb.getVoucherSummary(mdb);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String productid = cursor.getString(cursor.getColumnIndex("productid"));
                String totalnumber = cursor.getString(cursor.getColumnIndex("totalvoucher"));
                String totaldenom = cursor.getString(cursor.getColumnIndex("totaldenom"));
                String totalbalance = cursor.getString(cursor.getColumnIndex("totalbalance"));

                mlistData.add(new VoucherSummary(0, productid, Integer.parseInt(totalnumber), Double.parseDouble(totalbalance), Double.parseDouble(totaldenom)));
            } while (cursor.moveToNext());
        }
        mlistAdapter.setData(mlistData);
    }

    private void validateReferralIfAvailable() {
        Call<GenericResponse> call = RetrofitBuild.getReferralAPIService(getViewContext())
                .validateReferralIfAvailable(
                        imei,
                        userid,
                        sessionid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        "ANDROID"
                );

        call.enqueue(validateReferralIfAvailableCallback);
    }

    private Callback<GenericResponse> validateReferralIfAvailableCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_IS_REFER_FRIEND_PROMO, true);
                        tv_or.setVisibility(View.VISIBLE);
                        btn_refer.setVisibility(View.VISIBLE);
                    } else {
                        PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_IS_REFER_FRIEND_PROMO, false);
                        tv_or.setVisibility(View.GONE);
                        btn_refer.setVisibility(View.GONE);
                    }
                } else {
                    //showError();
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearSortVoucherLayout: {
                //openSortOption();
                if(alertDialog != null){
                    alertDialog.dismiss();
                }
                openSortOptionNew();
                break;
            }
            case R.id.linearViewVoucherLayout: {
                Cursor c = mdb.getVouchers(mdb);

                if (vouchersummarylist.getVisibility() == View.VISIBLE) {
                    if(alertDialog != null){
                        alertDialog.dismiss();
                    }
                    Animation LeftSwipe = AnimationUtils.loadAnimation(getViewContext(), R.anim.slide_left_out);
                    vouchersummarylist.startAnimation(LeftSwipe);
                    vouchersummarylist.setVisibility(View.GONE);
                    vouchersummarywrap.setVisibility(View.GONE);
                } else {
                    if (c.getCount() > 0) {
                        if(alertDialog != null){
                            alertDialog.dismiss();
                        }
                        vouchersummarywrap.setVisibility(View.VISIBLE);
                        vouchersummarylist.setVisibility(View.VISIBLE);
                        Animation RightSwipe = AnimationUtils.loadAnimation(getViewContext(), R.anim.slide_left_in);
                        vouchersummarylist.startAnimation(RightSwipe);
                        populateSummary();
                    } else {
                        showToast("No voucher available", GlobalToastEnum.NOTICE);
                    }
                }
                break;
            }
            case R.id.vouchersummarywrap: {
                vouchersummarywrap.setVisibility(View.GONE);
                vouchersummarylist.setVisibility(View.GONE);
                break;
            }
            case R.id.linearGroupVoucherLayout: {
                Cursor cs = mdb.getVouchers(mdb);
                if (cs.getCount() > 0) {
                    GroupVoucherActivity.start(getViewContext());
                } else {
                    showToast("No voucher available", GlobalToastEnum.NOTICE);
                }
                break;
            }
            case R.id.btn_buy_vocher: {
                VoucherPrepaidRequestActivity.start(getViewContext(), 0, VirtualVoucherProductFragment.BY_REQUEST_TO_SPONSOR, 0);
                break;
            }
            case R.id.btn_refer: {
                MainActivity.start(getViewContext(), 7);
                break;
            }
            case R.id.linearMoreLayout:{
                showCustomDialog(getViewContext());
                break;
            }
            case R.id.linearTransferVoucherLayout:{
                if(alertDialog != null){
                    alertDialog.dismiss();
                }
                startActivity(new Intent(getViewContext(), TransferThruBorrower.class).putExtra("from","MULTIPLE"));
                break;
            }
            case R.id.linearCloseDialogLayout:{
                if(alertDialog != null){
                    alertDialog.dismiss();
                }
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.debug("roneldayanan","ONRESUME COMMONVARIABLES : "+ CommonVariables.VOUCHERISFIRSTLOAD);
        if (CommonVariables.VOUCHERISFIRSTLOAD) {
            Logger.debug("roneldayanan","ONRESUME TRUE");
            limit = 0;
            isScroll = true;
            isfirstload = false;
            isbottomscroll = false;
            isbottomscroll = false;
            isloadmore = false;
            getSession();
        }
    }


    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * OCTOBER 2019    *
     * *****************/
    private void getVouchersV3withSecurity(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            Logger.debug("roneldayanan","getVouchersV3withSecurity positive");
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("limit", String.valueOf(limit));
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getVouchersV3");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getVouchersV3withSecurityObject(getVouchersV3withSecurityCallback);

        }else{
            Logger.debug("roneldayanan","getVouchersV3withSecurity negashit");
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private void getVouchersV3withSecurityObject(Callback<GenericResponse> getVoucherV3Callback) {
        Call<GenericResponse> getvouchers = RetrofitBuilder.getVoucherV2API(getViewContext())
                .getVouchersV3(authenticationid,sessionid,param);
        getvouchers.enqueue(getVoucherV3Callback);
    }
    private final Callback<GenericResponse> getVouchersV3withSecurityCallback = new Callback<GenericResponse>() {

    @Override
    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
        mLlLoader.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        emptyswiperefresh.setRefreshing(false);
        ResponseBody errorBody = response.errorBody();
        isLoading = false;
        isfirstload = false;
        isScroll = true;

        if (errorBody == null) {
            String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
            if (response.body().getStatus().equals("000")) {
                Logger.debug("roneldayanan","getVouchersV3withSecurity success");
                String decrypteddata = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                Logger.debug("roneldayanan","getVouchersV3withSecurity DATA : "+decrypteddata);

                ArrayList<Voucher> voucherList = new Gson().fromJson(CommonFunctions.parseJSON(decrypteddata,"Voucher"),new TypeToken<ArrayList<Voucher>>(){}.getType());
                if (!isbottomscroll) {
                    Logger.debug("roneldayanan","getVouchersV3withSecurity DELETE TABLE : ");
                    mdb.truncateTable(mdb, DatabaseHandler.VOUCHERS);
                }

                Logger.debug("roneldayanan","LongInsertOperation CALLED: ");
                new LongInsertOperation().execute(voucherList);

                if (voucherList.size() > 0) {
                    Logger.debug("roneldayanan","getVOUCHERS : voucherList.size() > 0");
                    isloadmore = true;
                    btnFunctionsLayout.setVisibility(View.VISIBLE);
                    voucherLayout.setVisibility(View.VISIBLE);
                    emptyswiperefresh.setVisibility(View.GONE);
                    mGridData.addAll(voucherList);
                    emptyvoucher.setVisibility(View.GONE);

                    Logger.debug("roneldayanan","LongOperation CALLED");
                    new LongOperation().execute();

                    Logger.debug("roneldayanan","LongPayOutOneOperation CALLED");
                    new LongPayOutOneOperation().execute();

                    ArrayList<VoucherSummary> vouchersSummary =  new Gson().fromJson(CommonFunctions.parseJSON(decrypteddata,"VoucherSummary"),new TypeToken<ArrayList<VoucherSummary>>(){}.getType());
                    //getVoucherSummary
                    if (vouchersSummary.size() > 0) {
                        mdb.deleteVoucherSummary(mdb);
                        new LongInsertVoucherSummaryOperation().execute(vouchersSummary);
                    }

                } else {
                    if (mGridData.size() == 0 || limit == 0) {
                        mAdapter.clear();
                        mAdapter_PayoutOne.clear();
                        emptyvoucher.setVisibility(View.VISIBLE);
                        btnFunctionsLayout.setVisibility(View.GONE);
                        voucherLayout.setVisibility(View.GONE);
                        emptyswiperefresh.setVisibility(View.VISIBLE);
                    }
                }

            }else if(response.body().getStatus().equals("002") || response.body().getStatus().equals("003")){
                   showAutoLogoutDialog(getString(R.string.logoutmessage));
            }else {
                showError(decryptedMessage);
            }
        } else {
            showError();
        }

    }

    @Override
    public void onFailure(Call<GenericResponse> call, Throwable t) {
        isLoading = false;
        mLlLoader.setVisibility(View.GONE);
        emptyswiperefresh.setRefreshing(false);
        mSwipeRefreshLayout.setRefreshing(false);
        showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
    }
};

    public void showCustomDialog(final Context context) {

        View dialogView = LayoutInflater.from(context).inflate(R.layout.optionmenu_layout,null , false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        linearSortVoucherLayout = dialogView.findViewById(R.id.linearSortVoucherLayout);
        linearViewVoucherLayout = dialogView.findViewById(R.id.linearViewVoucherLayout);
        linearCloseDialogLayout = dialogView.findViewById(R.id.linearCloseDialogLayout);

        linearSortVoucherLayout.setOnClickListener(this);
        linearViewVoucherLayout.setOnClickListener(this);
        linearCloseDialogLayout.setOnClickListener(this);

    }

}
