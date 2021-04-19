package com.goodkredit.myapplication.fragments;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.github.jrejaud.viewpagerindicator2.CirclePageIndicator;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.payviaqrcode.PayViaQRCodeActivity;
import com.goodkredit.myapplication.activities.prepaidrequest.VoucherPrepaidRequestActivity;
import com.goodkredit.myapplication.activities.transfer.TransferThruBorrower;
import com.goodkredit.myapplication.activities.vouchers.grouping.GroupVoucherActivity;
import com.goodkredit.myapplication.adapter.VoucherSummeryAdapter;
import com.goodkredit.myapplication.adapter.gkservices.GKServicesRecyclerViewAdapter;
import com.goodkredit.myapplication.adapter.gkservices.GKServicesVPagerAdapter;
import com.goodkredit.myapplication.adapter.vouchers.VouchersRecyclerAdapter;
import com.goodkredit.myapplication.adapter.vouchers.payoutone.VoucherPayoutOneAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.bean.VoucherSummary;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.prepaidrequest.VirtualVoucherProductFragment;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.EqualSpacingItemDecoration;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.SpacesItemDecoration;
import com.goodkredit.myapplication.view.WrapContentHeightViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

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

public class VouchersFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private DatabaseHandler db;

    private View view;

    private RecyclerView recyclerView;
    private VouchersRecyclerAdapter mAdapter;

    private ArrayList<Voucher> mGridData;
    private FrameLayout emptyvoucher;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled1;
    private Button btn_buy_vocher;
    private ImageView novouchers;

    private TextView lastupdate;
    private RelativeLayout scrollindicator;
    private ImageView dragdown;

    private String borroweridval = "";
    private String firstnameval = "";
    private String lastnameval = "";
    private String imei = "";
    private String userid = "";

    private SwipeRefreshLayout swipeRefreshLayout;

    private String selectedsort = "";
    private String sortoption = "ASC";

    //for the voucher summary
    private VoucherSummeryAdapter mlistAdapter;
    private ArrayList<VoucherSummary> mlistData;
    private RelativeLayout vouchersummarywrap;
    private ListView vouchersummarylist;


    private LinearLayout voucherLayout;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private Handler handler;

    private WrapContentHeightViewPager gks_vpager;
    private CirclePageIndicator gks_vpagerindicator;
    private RecyclerView gView;
    private List<GKService> gkServices = new ArrayList<>();
    private GKServicesRecyclerViewAdapter mGKSAdapter;
    private GKServicesRecyclerViewAdapter mGoodAppsAdapter;
    private GKServicesRecyclerViewAdapter mRecentlyUsedAdapter;

    private GKServicesVPagerAdapter gks_adapter;

    private EditText edtSearchBox;


    private Button btn_refer;
    private TextView tv_or;
    private SwipeRefreshLayout emptyswiperefresh;

    private boolean isLoading = false;
    private boolean isfirstload = true;
    private boolean isloadmore = false;
    private boolean isbottomscroll = false;
    private int limit = 0;

    private LinearLayout btnFunctionsLayout;
    private LinearLayout linearSortVoucherLayout;
    private LinearLayout linearGroupVoucherLayout;
    private LinearLayout linearViewVoucherLayout;
    private LinearLayout linearTransferVoucherLayout;
    private LinearLayout linearMoreLayout;
    private LinearLayout linearCloseDialogLayout;

    //UNIFIED SESSION
    private String sessionid = "";

    //PAYOUT ONE VOUCHERS
    private RecyclerView rv_vouchers_payoutone;
    private VoucherPayoutOneAdapter mAdapter_PayoutOne;
    private ArrayList<Voucher> mGridData_PayoutOne;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private AlertDialog alertDialog = null;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        setUpMenu(menu);
    }

    private void setUpMenu(Menu menu) {
        menu.findItem(R.id.action_notification_0).setVisible(false);
        menu.findItem(R.id.action_notification_1).setVisible(false);
        menu.findItem(R.id.action_notification_2).setVisible(false);
        menu.findItem(R.id.action_notification_3).setVisible(false);
        menu.findItem(R.id.action_notification_4).setVisible(false);
        menu.findItem(R.id.action_notification_5).setVisible(false);
        menu.findItem(R.id.action_notification_6).setVisible(false);
        menu.findItem(R.id.action_notification_7).setVisible(false);
        menu.findItem(R.id.action_notification_8).setVisible(false);
        menu.findItem(R.id.action_notification_9).setVisible(false);
        menu.findItem(R.id.action_notification_9plus).setVisible(false);

        switch (CommonVariables.notifCount) {
            case 0: {
                menu.findItem(R.id.action_notification_0).setVisible(true);
                break;
            }
            case 1: {
                menu.findItem(R.id.action_notification_1).setVisible(true);
                break;
            }
            case 2: {
                menu.findItem(R.id.action_notification_2).setVisible(true);
                break;
            }
            case 3: {
                menu.findItem(R.id.action_notification_3).setVisible(true);
                break;
            }
            case 4: {
                menu.findItem(R.id.action_notification_4).setVisible(true);
                break;
            }
            case 5: {
                menu.findItem(R.id.action_notification_5).setVisible(true);
                break;
            }
            case 6: {
                menu.findItem(R.id.action_notification_6).setVisible(true);
                break;
            }
            case 7: {
                menu.findItem(R.id.action_notification_7).setVisible(true);
                break;
            }
            case 8: {
                menu.findItem(R.id.action_notification_8).setVisible(true);
                break;
            }
            case 9: {
                menu.findItem(R.id.action_notification_9).setVisible(true);
                break;
            }
            default: {
                menu.findItem(R.id.action_notification_9plus).setVisible(true);
                break;
            }
        }

        menu.findItem(R.id.action_process_queue).setVisible(false);
        menu.findItem(R.id.sortitem).setVisible(false);
        menu.findItem(R.id.summary).setVisible(false);
        menu.findItem(R.id.group_voucher).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_available, container, false);

        try {
            CommonVariables.ISNEWVOUCHER = false;
            setHasOptionsMenu(true);

            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void init(View view) {

        btnFunctionsLayout = view.findViewById(R.id.btnFunctionsLayout);
        linearGroupVoucherLayout = view.findViewById(R.id.linearGroupVoucherLayout);
        linearTransferVoucherLayout = view.findViewById(R.id.linearTransferVoucherLayout);
        linearMoreLayout = view.findViewById(R.id.linearMoreLayout);


        linearGroupVoucherLayout.setOnClickListener(this);
        linearTransferVoucherLayout.setOnClickListener(this);
        linearMoreLayout.setOnClickListener(this);

        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_animation);

        int columns = CommonFunctions.calculateNoOfColumns(getViewContext(), Voucher.KEY_MAIN_VOUCHER);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getViewContext(), columns));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(8));
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new VouchersRecyclerAdapter(getViewContext());
        recyclerView.setAdapter(mAdapter);

        LayoutAnimationController controller1 =
                AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_animation);

        //payout one
        rv_vouchers_payoutone = view.findViewById(R.id.rv_vouchers_payoutone);
        int columns2 = CommonFunctions.calculateNoOfColumns(getViewContext(), Voucher.KEY_PAYOUTONE_VOUCHER);
        rv_vouchers_payoutone.setLayoutManager(new GridLayoutManager(getViewContext(), columns2));
        rv_vouchers_payoutone.addItemDecoration(new SpacesItemDecoration(8));
        rv_vouchers_payoutone.setLayoutAnimation(controller1);
        rv_vouchers_payoutone.setNestedScrollingEnabled(false);
        mAdapter_PayoutOne = new VoucherPayoutOneAdapter(getViewContext());
        rv_vouchers_payoutone.setAdapter(mAdapter_PayoutOne);

        emptyvoucher = view.findViewById(R.id.emptyvoucher);
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet = view.findViewById(R.id.refreshnointernet);
        refreshdisabled1 = view.findViewById(R.id.refreshdisabled1);
        btn_buy_vocher = view.findViewById(R.id.btn_buy_vocher);
        btn_buy_vocher.setOnClickListener(this);
        novouchers = view.findViewById(R.id.novoucher);
        dragdown = view.findViewById(R.id.dragdown);

        lastupdate = view.findViewById(R.id.lastupdate);
        scrollindicator = view.findViewById(R.id.scrollindicator);

        Glide.with(getViewContext())
                .load(R.drawable.novouchersyet)
                .into(novouchers);

        vouchersummarywrap = view.findViewById(R.id.vouchersummarywrap);
        vouchersummarywrap.setOnClickListener(this);

        voucherLayout = view.findViewById(R.id.voucherLayout);

        mLlLoader = view.findViewById(R.id.loaderLayout);
        mTvFetching = view.findViewById(R.id.fetching);
        mTvWait = view.findViewById(R.id.wait);

        btn_refer = view.findViewById(R.id.btn_refer);
        btn_refer.setOnClickListener(this);
        tv_or = view.findViewById(R.id.tv_or);
        emptyswiperefresh = view.findViewById(R.id.emptyswiperefresh);
        emptyswiperefresh.setOnRefreshListener(this);

        //voucher summary
        vouchersummarylist = view.findViewById(R.id.vouchersummarylist);

        //refresh event
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        final ImageView backgroundOne = view.findViewById(R.id.background_one);
        final ImageView backgroundTwo = view.findViewById(R.id.background_two);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(750);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        //show dragdown to refresh
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                hideDragIndicator();
            }
        }.start();
    }

    private void initData() {
        db = new DatabaseHandler(getViewContext());
        handler = new Handler();
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        mlistData = new ArrayList<>();
        mGridData = new ArrayList<>();
        mGridData_PayoutOne = new ArrayList<>();


        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        //Set up Menu Services
        setupGKMenuServices();

        //Set up Voucher Summary
        mlistAdapter = new VoucherSummeryAdapter(getViewContext(), R.layout.activity_voucher_summary_item);
        vouchersummarylist.setAdapter(mlistAdapter);

        //get account information
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borroweridval = cursor.getString(cursor.getColumnIndex("borrowerid"));
                firstnameval = cursor.getString(cursor.getColumnIndex("firstname"));
                lastnameval = cursor.getString(cursor.getColumnIndex("lastname"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));


            } while (cursor.moveToNext());

            PreferenceUtils.removePreference(getViewContext(), borroweridval);
            PreferenceUtils.removePreference(getViewContext(), userid);
            PreferenceUtils.removePreference(getViewContext(), imei);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_BORROWER_NAME);

            PreferenceUtils.saveBorrowerID(getViewContext(), borroweridval);
            PreferenceUtils.saveUserID(getViewContext(), userid);
            PreferenceUtils.saveImeiID(getViewContext(), imei);
            PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_BORROWER_NAME, firstnameval + " " + lastnameval);

            //load local voucher
            //preloadVoucher();

            //1.STATUS
            int status = CommonFunctions.getConnectivityStatus(getViewContext());
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            }

            //refresh in no internet connection indicator
            refreshnointernet.setOnClickListener(view -> {
                limit = 0;
                isloadmore = false;
                isbottomscroll = false;
                disableRefresh();
                getSession();
            });

            //SCROLL INDICATOR
            scrollindicator.setOnClickListener(view -> scrollindicator.setVisibility(View.GONE));

        }
        cursor.close();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {

                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);


                if (!recyclerView.canScrollVertically(1)) {
                    onScrolledToBottom();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });

        if (!CommonVariables.VOUCHERISFIRSTLOAD) {
            new LongFirstLoadOperation().execute();

            final Handler handler = new Handler();
            handler.postDelayed(() -> new LongFirstLoadPayOutOneOperation().execute(), 1000);
        }

        validateReferralIfAvailable();
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

    private final Runnable refreshing = new Runnable() {
        public void run() {
            try {
                /* TODO : isRefreshing should be attached to your data request status */
                if (swipeRefreshLayout.isRefreshing()) {
                    // re run the verification after 1 second
                    handler.postDelayed(this, 1000);
                } else {
                    // stop the animation after the data is fully loaded
                    swipeRefreshLayout.setRefreshing(false);
                    emptyswiperefresh.setRefreshing(false);
                    // TODO : update your list with the new data
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub

        try {
            limit = 0;
            isloadmore = false;
            handler.post(refreshing);
            isfirstload = false;
            isbottomscroll = false;
            mGridData.clear();
            mGridData_PayoutOne.clear();
            getSession();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStop() {
        if (mLoaderTimer != null)
            mLoaderTimer.cancel();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String permissions[], @NotNull int[] grantResults) {
        try {
            if (requestCode == 111) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getViewContext(), PayViaQRCodeActivity.class);
                    // fab.close(true);
                    startActivity(intent);
                } else {
                    showToast("Grant camera permission to scan QR code.", GlobalToastEnum.NOTICE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            hideSoftKeyboard();

            //get account information
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borroweridval = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                } while (cursor.moveToNext());
            }

            cursor.close();
            ((BaseActivity) getViewContext()).setupOverlay();
            ((BaseActivity) getViewContext()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getViewContext()));

            if (CommonVariables.VOUCHERISFIRSTLOAD) {
                limit = 0;
                isfirstload = false;
                isbottomscroll = false;
                isloadmore = false;
                getSession();
            }

            if (db.getGKServices(db).size() > 0) {
                view.findViewById(R.id.gks_pay_by_qr).setOnClickListener(this);
            }

            if (db.getRecentlyUsedGKS(db).size() > 0) {
                if (edtSearchBox.getText().toString().trim().length() == 0)
                    view.findViewById(R.id.gks_recent).setVisibility(View.VISIBLE);
                //  rv_recently_used.setAdapter(new GKServicesRecyclerViewAdapter(getViewContext(), db.getGKSERVICES_MAINMENU(db), true));
                mRecentlyUsedAdapter.updateData(db.getRecentlyUsedGKS(db));
            } else {
                view.findViewById(R.id.gks_recent).setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortitem:
                //open sort option
                Cursor cr = db.getVouchers(db);
                if (cr.getCount() > 0) {
                    //openSortOption();
                    openSortOptionNew();
                } else {
                    showToast("No voucher available", GlobalToastEnum.NOTICE);
                }

                return true;

            case R.id.summary:

                Cursor c = db.getVouchers(db);

                if (vouchersummarylist.getVisibility() == View.VISIBLE) {
                    Animation LeftSwipe = AnimationUtils.loadAnimation(getViewContext(), R.anim.slide_left_out);
                    vouchersummarylist.startAnimation(LeftSwipe);
                    vouchersummarylist.setVisibility(View.GONE);
                    vouchersummarywrap.setVisibility(View.GONE);
                } else {
                    if (c.getCount() > 0) {
                        vouchersummarywrap.setVisibility(View.VISIBLE);
                        vouchersummarylist.setVisibility(View.VISIBLE);
                        Animation RightSwipe = AnimationUtils.loadAnimation(getViewContext(), R.anim.slide_left_in);
                        vouchersummarylist.startAnimation(RightSwipe);
                        populateSummary();
                    } else {
                        showToast("No voucher available", GlobalToastEnum.NOTICE);
                    }
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**********************
     * FUNCTIONS
     *********************/
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
            Logger.debug("ANN", "ANYANG" + e.toString());
        }

    }

    public void checkVoucherLastUpdated(String lastupdatedval) {

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
//                    showGlobalDialogs("It seems that you have not refreshed your vouchers for a long time.", "Refresh",
//                            ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.WARNING,
//                            false, this);

                    GlobalDialogs dialog = new GlobalDialogs(getViewContext());

                    dialog.createDialog("WARNING", "It seems that you have not refreshed " +
                                    "your vouchers for a long time.", "Refresh", "",
                            ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.WARNING);

                    View closebtn = dialog.btnCloseImage();
                    closebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    View singlebtn = dialog.btnSingle();
                    singlebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            onRefresh();
                        }
                    });


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


    }

    public static String convertDate(String dateInMilliseconds, String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }

    public void openSortOptionNew() {

        try {
            GlobalDialogs dialog = new GlobalDialogs(getViewContext());

            dialog.createDialog("Sort Voucher", "",
                    "CANCEL", "SORT", ButtonTypeEnum.DOUBLE,
                    false, false, DialogTypeEnum.RADIO);

            View closebtn = dialog.btnCloseImage();
            closebtn.setOnClickListener(view -> dialog.dismiss());

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
                    for (int i = 0; i < voucherorder.size(); i++) {
                        String tag = voucherOrderGroup.getChildAt(i).getTag().toString();
                        if (tag.equals("Ascending")) {
                            voucherOrderGroup.check(voucherOrderGroup.getChildAt(i).getId());
                            break;
                        }
                    }
                    break;
                case "DESC":
                    for (int i = 0; i < voucherorder.size(); i++) {
                        String tag = voucherOrderGroup.getChildAt(i).getTag().toString();
                        if (tag.equals("Descending")) {
                            voucherOrderGroup.check(voucherOrderGroup.getChildAt(i).getId());
                            break;
                        }
                    }
                    break;
            }

            voucherOrderGroup.setOnCheckedChangeListener((group, checkedId) -> {
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
            });

            View v = new View(getViewContext());
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
                    for (int i = 0; i < sortVoucher.size(); i++) {
                        String tag = sortVoucherGroup.getChildAt(i).getTag().toString();
                        if (tag.equals("By Date")) {
                            sortVoucherGroup.check(sortVoucherGroup.getChildAt(i).getId());
                            break;
                        }
                    }
                    break;
                case "amount":
                    for (int i = 0; i < sortVoucher.size(); i++) {
                        String tag = sortVoucherGroup.getChildAt(i).getTag().toString();
                        if (tag.equals("By Denom")) {
                            sortVoucherGroup.check(sortVoucherGroup.getChildAt(i).getId());
                            break;
                        }
                    }
                    break;
                case "remainingamount":
                    for (int i = 0; i < sortVoucher.size(); i++) {
                        String tag = sortVoucherGroup.getChildAt(i).getTag().toString();
                        if (tag.equals("By Balance")) {
                            sortVoucherGroup.check(sortVoucherGroup.getChildAt(i).getId());
                            break;
                        }
                    }
                    break;
            }

            sortVoucherGroup.setOnCheckedChangeListener((group, checkedId) -> {
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
            });


            View btndoubleone = dialog.btnDoubleOne();
            btndoubleone.setOnClickListener(view -> dialog.dismiss());

            View btndoubletwo = dialog.btnDoubleTwo();
            btndoubletwo.setOnClickListener(view -> {
                if (!selectedsort.equals("")) {
                    dialog.dismiss();
                    sortVouchers(selectedsort, sortoption);
                } else {
                    showToast("Please select sort option", GlobalToastEnum.WARNING);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    private void sortVouchers(String selected, String option) {
        mGridData = db.getSortedVouchers(db, selected, option);
        mAdapter.clear();
        mAdapter.addAll(mGridData);
        mAdapter_PayoutOne.clear();
        mAdapter_PayoutOne.addAll(mGridData_PayoutOne);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gks_pay_by_qr: {
                if (ActivityCompat.checkSelfPermission(getViewContext(), android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.CAMERA}, 111);
                } else {
                    Intent intent = new Intent(getViewContext(), PayViaQRCodeActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.linearSortVoucherLayout: {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                openSortOptionNew();
                break;
            }
            case R.id.linearViewVoucherLayout: {
                Cursor c = db.getVouchers(db);
                if (vouchersummarylist.getVisibility() == View.VISIBLE) {
                    Animation LeftSwipe = AnimationUtils.loadAnimation(getViewContext(), R.anim.slide_left_out);
                    vouchersummarylist.startAnimation(LeftSwipe);
                    vouchersummarylist.setVisibility(View.GONE);
                    vouchersummarywrap.setVisibility(View.GONE);
                } else {

                    if (c.getCount() > 0) {
                        if (alertDialog != null) {
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
                Cursor cs = db.getVouchers(db);
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

            case R.id.linearMoreLayout: {
                showCustomDialog(getViewContext());
                break;
            }

            case R.id.linearTransferVoucherLayout: {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                startActivity(new Intent(getViewContext(), TransferThruBorrower.class).putExtra("from", "MULTIPLE"));
                break;
            }
            case R.id.linearCloseDialogLayout: {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                break;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LongInsertVoucherSummaryOperation extends AsyncTask<ArrayList<VoucherSummary>, Void, ArrayList<VoucherSummary>> {


        @Override
        protected final ArrayList<VoucherSummary> doInBackground(ArrayList<VoucherSummary>... lists) {
            if (db != null) {
                for (VoucherSummary summary : lists[0]) {
                    db.insertVouchersSummary(db, summary);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<VoucherSummary> vouchers) {
            super.onPostExecute(vouchers);


        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LongInsertOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {


        @Override
        protected final ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {

            if (db != null) {
                for (Voucher voucher : lists[0]) {
                    db.insertVouchers(db, voucher);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> vouchers) {
            super.onPostExecute(vouchers);

            String miliseconds = "";
            long time = System.currentTimeMillis();
            miliseconds = Long.toString(time);
            checkVoucherLastUpdated(miliseconds);

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LongFirstLoadOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {


        @Override
        protected final ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
            mGridData.clear();
            mGridData = db.getAllVouchers(db);
            return mGridData;
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> voucherData) {
            if (isAdded()) {
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
    }

    @SuppressLint("StaticFieldLeak")
    private class LongFirstLoadPayOutOneOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {


        @Override
        protected final ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
            mGridData_PayoutOne.clear();
            mGridData_PayoutOne = db.getChequeVoucher(db);
            return mGridData_PayoutOne;
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> voucherData) {
            if (isAdded()) {
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
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            isLoading = true;

            mTvFetching.setText("Fetching available vouchers...");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            getVouchersV3withSecurity();
        } else {
            emptyswiperefresh.setRefreshing(false);
            swipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showError(getString(R.string.generic_internet_error_message));
            showWarningToast("Seems you are not connected to the internet. Please check your connection " +
                    "and try again. Thank you.");
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class LongOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {


        @Override
        protected final ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
            return db.getAllVouchers(db);
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> voucherData) {

            if (isAdded()) {
                if (voucherData.size() > 0) {
                    if (!isLoading) {
                        mAdapter.clear();
                        mAdapter.addAll(voucherData);
                    }
                } else {
                    mAdapter.clear();
                }
            }
        }

        @Override
        protected void onPreExecute() {
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LongPayOutOneOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {


        @Override
        protected final ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
            mGridData_PayoutOne = db.getChequeVoucher(db);
            return mGridData_PayoutOne;
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> voucherData) {

            if (isAdded()) {
                if (voucherData.size() > 0) {
                    if (!isLoading) {
                        mAdapter_PayoutOne.clear();
                        mAdapter_PayoutOne.addAll(mGridData_PayoutOne);
                    }
                } else {
                    mAdapter_PayoutOne.clear();
                }
            }
        }

        @Override
        protected void onPreExecute() {
        }
    }

    private void populateSummary() {
        mlistData.clear();
        Cursor cursor = db.getVoucherSummary(db);
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

    public void disableRefresh() {
        try {
            refreshdisabled1.setVisibility(View.VISIBLE);
            refreshnointernet.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void inableRefresh() {
        try {
            refreshdisabled1.setVisibility(View.GONE);
            refreshnointernet.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showNoInternetConnection() {
        try {
            Cursor c = db.getVouchers(db);
            if (c.getCount() <= 0) {
                nointernetconnection.setVisibility(View.VISIBLE);
            }
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void hideNoInternetConnection() {
        try {
            inableRefresh();
            nointernetconnection.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void setupGKMenuServices() {

        gkServices = db.getGKServicesForKeyword(db, "");

        //NEW RVs
        rv_goodapps_gks = view.findViewById(R.id.rv_goodapps_gks);
        rv_goodapps_gks.setLayoutManager(new GridLayoutManager(getViewContext(), 4));
        rv_goodapps_gks.addItemDecoration(new SpacesItemDecoration(0));
        rv_goodapps_gks.setNestedScrollingEnabled(false);

        rv_goodapps_gks.setItemAnimator(new DefaultItemAnimator());
        Objects.requireNonNull(rv_goodapps_gks.getItemAnimator()).setAddDuration(1500);
        rv_goodapps_gks.getItemAnimator().setMoveDuration(1500);

        mGoodAppsAdapter = new GKServicesRecyclerViewAdapter(getViewContext(), getGoodAppsServices(db.getGKServices(db)), false);
        rv_goodapps_gks.setAdapter(mGoodAppsAdapter);

        rv_recently_used = view.findViewById(R.id.rv_recently_used);
        rv_recently_used.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));

        rv_recently_used.addItemDecoration(new SpacesItemDecoration(0));
        rv_recently_used.setNestedScrollingEnabled(false);

        mRecentlyUsedAdapter = new GKServicesRecyclerViewAdapter(getViewContext(), db.getRecentlyUsedGKS(db), true);
        rv_recently_used.setAdapter(mRecentlyUsedAdapter);

        if (db.getRecentlyUsedGKS(db).size() > 0) {
            view.findViewById(R.id.gks_recent).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.gks_recent).setVisibility(View.GONE);
        }

        //services layout
        edtSearchBox = view.findViewById(R.id.edt_search_box);

        gks_vpager = view.findViewById(R.id.gks_vpager);
        gks_adapter = new GKServicesVPagerAdapter(getChildFragmentManager(), getViewContext());
        //final int EXCLUDE_SERVICES = 11;
        final int EXCLUDE_SERVICES = db.getCountGKServicesCount(db);
        float nofPages = 0;
        try {
            nofPages = ((float) db.getGKServices(db).size() - EXCLUDE_SERVICES) / (float) 12;

            if (db.getGKServices(db).size() - EXCLUDE_SERVICES > 12) {
                nofPages = (int) Math.ceil(nofPages);
            } else {
                nofPages = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        gks_vpager.setOffscreenPageLimit((int) nofPages);
        gks_vpager.setAdapter(gks_adapter);

        gks_vpagerindicator = view.findViewById(R.id.indicator);
        gks_vpagerindicator.setViewPager(gks_vpager);

        gView = view.findViewById(R.id.gks_gridView);
        gView.setLayoutManager(new GridLayoutManager(getViewContext(), 4));
        gView.setNestedScrollingEnabled(false);

        gView.addItemDecoration(new SpacesItemDecoration(0));
        gView.setItemAnimator(new DefaultItemAnimator());
        Objects.requireNonNull(gView.getItemAnimator()).setAddDuration(1500);
        gView.getItemAnimator().setMoveDuration(1500);

        gkServices = db.getGKServicesForKeyword(db, "");
        mGKSAdapter = new GKServicesRecyclerViewAdapter(getViewContext(), gkServices, false);
        gView.setAdapter(mGKSAdapter);

        edtSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    view.findViewById(R.id.gks_search_results).setVisibility(View.VISIBLE);
                    gks_vpager.setVisibility(View.GONE);
                    gks_vpagerindicator.setVisibility(View.GONE);
                    view.findViewById(R.id.gks_main).setVisibility(View.GONE);
                    view.findViewById(R.id.gks_recent).setVisibility(View.GONE);
                    view.findViewById(R.id.gks_all).setVisibility(View.GONE);
                    LayoutAnimationController controller =
                            AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_to_left);
                    gView.setLayoutAnimation(controller);
                    gView.scheduleLayoutAnimation();
                    // mGKSAdapter.updateData(db.getGKServicesForKeyword(db, s.toString().trim()));
                    gView.setAdapter(new GKServicesRecyclerViewAdapter(getViewContext(), db.getGKServicesForKeyword(db, s.toString().trim()), false));

                } else {
                    view.findViewById(R.id.gks_search_results).setVisibility(View.GONE);
                    gks_vpager.setVisibility(View.VISIBLE);
                    gks_vpagerindicator.setVisibility(View.VISIBLE);
                    view.findViewById(R.id.gks_main).setVisibility(View.VISIBLE);
                    if (db.getRecentlyUsedGKS(db).size() > 0) {
                        view.findViewById(R.id.gks_recent).setVisibility(View.VISIBLE);
                        mRecentlyUsedAdapter.updateData(db.getRecentlyUsedGKS(db));
                    } else {
                        view.findViewById(R.id.gks_recent).setVisibility(View.GONE);
                    }
                    view.findViewById(R.id.gks_all).setVisibility(View.VISIBLE);
                    LayoutAnimationController controller =
                            AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_to_left);
                    gView.setLayoutAnimation(controller);
                    gView.scheduleLayoutAnimation();
//                    mGKSAdapter.updateData(db.getGKServicesForKeyword(db, ""));
                    gView.setAdapter(new GKServicesRecyclerViewAdapter(getViewContext(), db.getGKServicesForKeyword(db, ""), false));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    @Override
    public void onPause() {
        super.onPause();
        hideSoftKeyboard();
    }

    //NEW GK SERVICE MENU BANBAN

    private RecyclerView rv_goodapps_gks;

    private RecyclerView rv_recently_used;

    private List<GKService> getGoodAppsServices(List<GKService> gkServices) {
        List<GKService> goodAppsServices = new ArrayList<>();
        for (GKService service : gkServices) {
            switch (service.getServiceCode()) {
                case "BUYPREPAIDLOAD":
                case "PAYBILLS":
                case "BUY_VOUCHER":
                case "GKNEGOSYO":
                case "PROMO":
                case "DROPOFF":
                case "FREESMS":
                case "GKEARN": {

                    goodAppsServices.add(service);
                    break;
                }
            }
        }

        return goodAppsServices;
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
        public void onResponse(@NotNull Call<GenericResponse> call, @NotNull Response<GenericResponse> response) {
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

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * OCTOBER 2019    *
     * *****************/
    private void getVouchersV3withSecurity() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
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

        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getVouchersV3withSecurityObject(Callback<GenericResponse> getVoucherV3Callback) {
        Call<GenericResponse> getvouchers = RetrofitBuilder.getVoucherV2API(getViewContext())
                .getVouchersV3(authenticationid, sessionid, param);
        getvouchers.enqueue(getVoucherV3Callback);
    }

    private final Callback<GenericResponse> getVouchersV3withSecurityCallback = new Callback<GenericResponse>() {

        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            emptyswiperefresh.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            isLoading = false;
            isfirstload = false;

            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    CommonVariables.VOUCHERISFIRSTLOAD = false;

                    String decrypteddata = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    ArrayList<Voucher> voucher = new Gson().fromJson(CommonFunctions.parseJSON(decrypteddata, "Voucher"), new TypeToken<ArrayList<Voucher>>() {
                    }.getType());

                    if (!isbottomscroll) {
                        db.truncateTable(db, DatabaseHandler.VOUCHERS);
                    }

                    if (voucher.size() > 0 && voucher != null) {

                        new LongInsertOperation().execute(voucher);

                        isloadmore = true;
                        btnFunctionsLayout.setVisibility(View.VISIBLE);
                        voucherLayout.setVisibility(View.VISIBLE);
                        emptyswiperefresh.setVisibility(View.GONE);
                        mGridData.addAll(voucher);
                        emptyvoucher.setVisibility(View.GONE);

                        new LongOperation().execute();
                        new LongPayOutOneOperation().execute();

                        ArrayList<VoucherSummary> voucherSummary = new Gson().fromJson(CommonFunctions.parseJSON(decrypteddata, "VoucherSummary"), new TypeToken<ArrayList<VoucherSummary>>() {
                        }.getType());
                        //getVoucherSummary
                        if (voucherSummary.size() > 0) {
                            db.deleteVoucherSummary(db);
                            new LongInsertVoucherSummaryOperation().execute(voucherSummary);
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

                } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else {
                    //showError(response.body().getMessage());
                    showErrorGlobalDialogs(decryptedMessage);
                }
            } else {
                //showError();
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(@NotNull Call<GenericResponse> call, Throwable t) {
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            emptyswiperefresh.setRefreshing(false);
            swipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    public void showCustomDialog(final Context context) {

        View dialogView = LayoutInflater.from(context).inflate(R.layout.optionmenu_layout, null, false);
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
