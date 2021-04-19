package com.goodkredit.myapplication.fragments.gkearn;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gkearn.GKEarnHomeActivity;
import com.goodkredit.myapplication.activities.gkearn.GKEarnPaymentOptionsActivity;
import com.goodkredit.myapplication.activities.gkearn.stockist.GKEarnStockistPackagesActivity;
import com.goodkredit.myapplication.activities.prepaidrequest.VoucherPrepaidRequestActivity;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniBillingReferenceActivity;
import com.goodkredit.myapplication.adapter.gkearn.GKEarnConversionAdapter;
import com.goodkredit.myapplication.adapter.gkearn.GKEarnTopUpHistoryAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.gkearn.GKEarnConversionsPointsDB;
import com.goodkredit.myapplication.decoration.DividerItemDecoration;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.prepaidrequest.VirtualVoucherProductFragment;
import com.goodkredit.myapplication.model.Referral;
import com.goodkredit.myapplication.model.gkearn.GKEarnConversionPoints;
import com.goodkredit.myapplication.model.gkearn.GKEarnSubscribers;
import com.goodkredit.myapplication.model.gkearn.GKEarnTopUpHistory;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.gkearn.GKEarnConversionPointsResponse;
import com.goodkredit.myapplication.responses.gkearn.GKEarnSubscribersResponse;
import com.goodkredit.myapplication.responses.gkearn.GKEarnTopUpHistoryResponse;
import com.goodkredit.myapplication.responses.referafriend.GenerateReferralCodeResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKEarnRegisteredMemberFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    private String servicecode = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    private SwipeRefreshLayout swipe_container;

    private NestedScrollView home_body;
    private LinearLayout home_body_container;

    //POINTS
    private GKEarnSubscribers gKEarnSubscribers = null;
    private LinearLayout points_body_container;
    private LinearLayout points_layout;
    private ImageView imv_available_credits;
    private TextView txv_available_points_lbl;
    private TextView txv_available_points;
    private LinearLayout btn_convert_to_voucher;

    private double mPoints = 0.00;

    //DOWNLINES
    private LinearLayout downlines_body_container;
    private LinearLayout downlines_points_layout;
    private ImageView imv_downlines;
    private TextView txv_downlines;
    private LinearLayout btn_invite_friend;

    private int mDownLines = 0;

    //STOCKIST
    private LinearLayout btn_apply_stockist;

    //CONVERSION HISTORY
    private LinearLayout conversion_history_container;
    private TextView txv_conversion_history;
    private RecyclerView rv_conversion_history;
    private GKEarnConversionAdapter gkEarnConversionAdapter;

    //SCROLLLIMIT
    private boolean isloadmore = false;
    private boolean isbottomscroll = false;
    private boolean isfirstload = true;
    private int limit = 0;

    //TOPUP SCROLLLIMIT
    private boolean isloadmore_topup = false;
    private boolean isbottomscroll_topup = false;
    private boolean isfirstload_topup = true;
    private int limit_topup = 0;

    //DATE
    private int MIN_YEAR = 1995;
    private int MAX_YEAR = 2060;
    private int MIN_MONTH = 1;
    private int MAX_MONTH = 12;
    private String passedmonth = ".";
    private String passedyear = ".";
    private int month = 0;
    private int year = 0;
    private int currentmonth = 0;
    private int currentyear = 0;
    private int registrationmonth;
    private int registrationyear;
    private String dateregistered = "";

    //VIEW ARCHIVE
    private LinearLayout btn_view_archive;
    private TextView txv_view_archive;
    private TextView editsearches;
    private TextView clearsearch;
    private Spinner yearspinType;
    private boolean isyearselected = false;

    //GLOBAL DIALOGS
    private GlobalDialogs mGlobalDialogs;

    //EMPTY
    private RelativeLayout emptyLayout;
    private TextView txv_empty_content;
    private ImageView imv_empty_box;
    private ImageView imv_empty_refresh;
    private ImageView imv_empty_box_watermark;

    //temp topup
    private TextView txv_topup_history;
    private LinearLayout btn_topup_view_archive;
    private TextView txv_topup_view_archive;
    private RecyclerView rv_topup_history;
    private GKEarnTopUpHistoryAdapter gkEarnTopUpHistoryAdapter;
    private List<GKEarnTopUpHistory> gkEarnTopUpHistoryList = new ArrayList<>();

    private LinearLayout layout_topup_history;
    private LinearLayout layout_conversion_history;
    private TextView txv_topup_empty;
    private LinearLayout btn_topup;

    private Spinner monthspinType;
    private boolean ismonthselected = false;

    private FrameLayout frame_conversion;
    private FrameLayout frame_topup;

    private int isstockist = 0;

    private GKService gkService;

    //PENDING REQUEST
    private TextView txv_pending_content;
    private RelativeLayout layout_pending_req_via_payment_channel;
    private LinearLayout layout_pending_paynow;
    private LinearLayout layout_pending_cancelrequest;
    private TextView btn_pending_paynow;
    private TextView btn_pending_cancelrequest;
    private ImageView btn_pending_close;
    private String billingid = "";

    //PAYMENT TYPE
    private String paymenttype = "";

    //CANCEL DIALOG
    private EditText dialogEdt;
    private String dialogString = "";
    private String remarks = "";

    //EDIT REFERRAL
    private TextView txv_referral;
    private ImageView imv_edit_code;

    private EditText edt_code;
    private String strCode = "";

    //BTN ACTIVATION VOUCHER OF LIFE
    private LinearLayout btn_activation_voucher;

    List<GKEarnSubscribers> data = new ArrayList<>();

    //VARIABLES FOR SECURITY
    private String getIndex;
    private String getAuth;
    private String getKey;
    private String getParam;

    //generate referral code
    private String generateIndex;
    private String generateKey;
    private String generateAuth;
    private String generateParam;

    //get topup history
    private String topupIndex;
    private String topupKey;
    private String topupAuth;
    private String topupParam;

    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gkearn_registered_member, container, false);
        try {
            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        swipe_container = view.findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(this);
        home_body = view.findViewById(R.id.home_body);
        home_body_container = view.findViewById(R.id.home_body_container);

        //POINTS
        points_body_container = view.findViewById(R.id.points_body_container);
        points_layout = view.findViewById(R.id.points_layout);
        imv_available_credits = view.findViewById(R.id.imv_available_credits);
        txv_available_points = view.findViewById(R.id.txv_available_points);
        txv_available_points.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
        btn_convert_to_voucher = view.findViewById(R.id.btn_convert_to_voucher);
        btn_convert_to_voucher.setOnClickListener(this);

        //DOWNLINES
        downlines_body_container = view.findViewById(R.id.downlines_body_container);
        downlines_points_layout = view.findViewById(R.id.downlines_points_layout);
        imv_downlines = view.findViewById(R.id.imv_downlines);
        txv_downlines = view.findViewById(R.id.txv_downlines);
        txv_downlines.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
        btn_invite_friend = view.findViewById(R.id.btn_invite_friend);
        btn_invite_friend.setOnClickListener(this);

        //STOCKIST
        btn_apply_stockist = view.findViewById(R.id.btn_apply_stockist);
        btn_apply_stockist.setOnClickListener(this);

        //CONVERSION HISTORY
//        conversion_history_container = view.findViewById(R.id.conversion_history_container);
        txv_conversion_history = view.findViewById(R.id.txv_conversion_history);
        txv_conversion_history.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
        rv_conversion_history = view.findViewById(R.id.rv_conversion_history);

        //ARCHIVE
        btn_view_archive = view.findViewById(R.id.btn_view_archive);
        btn_view_archive.setOnClickListener(this);
        txv_view_archive = view.findViewById(R.id.txv_view_archive);

        //EMPTY
        emptyLayout = view.findViewById(R.id.emptyLayout);
        txv_empty_content = view.findViewById(R.id.txv_empty_content);
        txv_empty_content.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_ITALIC, "No conversion history yet."));
        imv_empty_box = view.findViewById(R.id.imv_empty_box);
        imv_empty_box.setVisibility(View.GONE);
        imv_empty_refresh = view.findViewById(R.id.imv_empty_refresh);
        imv_empty_refresh.setVisibility(View.GONE);
        imv_empty_box_watermark = view.findViewById(R.id.imv_empty_box_watermark);
        imv_empty_box_watermark.setVisibility(View.GONE);

        //TOPUP HISTORY
        rv_topup_history = view.findViewById(R.id.rv_topup_history);
        rv_topup_history.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_topup_history.setNestedScrollingEnabled(false);
        rv_topup_history.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getViewContext(), R.drawable.recycler_divider)));
        gkEarnTopUpHistoryAdapter = new GKEarnTopUpHistoryAdapter(getViewContext());
        rv_topup_history.setAdapter(gkEarnTopUpHistoryAdapter);

        txv_topup_history = view.findViewById(R.id.txv_topup_history);
        btn_topup_view_archive = view.findViewById(R.id.btn_topup_view_archive);
        txv_topup_view_archive = view.findViewById(R.id.txv_topup_view_archive);
        btn_topup_view_archive.setOnClickListener(this);

        layout_topup_history = view.findViewById(R.id.layout_topup_history);
        layout_conversion_history = view.findViewById(R.id.layout_conversion_history);
        txv_topup_empty = view.findViewById(R.id.txv_topup_empty);
        btn_topup = view.findViewById(R.id.btn_topup);
        btn_topup.setOnClickListener(this);

        frame_conversion = view.findViewById(R.id.frame_conversion);
        frame_topup = view.findViewById(R.id.frame_topup);
        frame_conversion.setOnClickListener(this);
        frame_topup.setOnClickListener(this);

        //set default view for conversion and top up history
        frame_conversion.setBackground(getViewContext().getResources().getDrawable(R.drawable.gkearn_tab_left_selected));
        txv_conversion_history.setTextColor(getResources().getColor(R.color.color_gkearn_blue));
        txv_topup_history.setTextColor(getResources().getColor(R.color.whitePrimary));

        //PENDING REQUEST
        txv_pending_content = (TextView) view.findViewById(R.id.txv_content);
        layout_pending_req_via_payment_channel = (RelativeLayout) view.findViewById(R.id.layout_req_via_payment_channel);
        layout_pending_paynow = (LinearLayout) view.findViewById(R.id.layout_payment_channel_paynow);
        layout_pending_cancelrequest = (LinearLayout) view.findViewById(R.id.layout_payment_channel_cancel);
        btn_pending_cancelrequest = (TextView) view.findViewById(R.id.btn_cancel_request);
        btn_pending_paynow = (TextView) view.findViewById(R.id.btn_paynow_request);
        btn_pending_close = (ImageView) view.findViewById(R.id.btn_close);
        btn_pending_paynow.setOnClickListener(this);
        btn_pending_close.setOnClickListener(this);
        btn_pending_cancelrequest.setOnClickListener(this);

        //EDIT REFERRAL
        txv_referral = (TextView) view.findViewById(R.id.txv_referral);
        txv_referral.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
        txv_referral.addTextChangedListener(codeTextWatcher);
        imv_edit_code = (ImageView) view.findViewById(R.id.imv_edit_code);
        imv_edit_code.setOnClickListener(this);

        //BTN ACTIVATION VOUCHER OF LIFE
        btn_activation_voucher = (LinearLayout) view.findViewById(R.id.btn_activation_voucher);
        btn_activation_voucher.setOnClickListener(this);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        gkService = PreferenceUtils.getGKServicesPreference(getViewContext(), "GKSERVICE_OBJECT");

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

        mdb = new DatabaseHandler(getViewContext());

        //CONVERSION HISTORY
        rv_conversion_history.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_conversion_history.setNestedScrollingEnabled(false);
        rv_conversion_history.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getViewContext(), R.drawable.recycler_divider)));
        gkEarnConversionAdapter = new GKEarnConversionAdapter(getViewContext());
        rv_conversion_history.setAdapter(gkEarnConversionAdapter);

        home_body.setOnScrollChangeListener(scrollOnChangedListener);

        Calendar c = Calendar.getInstance();
        passedyear = ".";
        passedmonth = ".";
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        currentyear = Calendar.getInstance().get(Calendar.YEAR);
        currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        callMainAPI();
    }

    private NestedScrollView.OnScrollChangeListener scrollOnChangedListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                isbottomscroll = true;
                isbottomscroll_topup = true;

                if (isloadmore) {
                    if (!isfirstload) {
                        limit = limit + 30;
                    }
                    callEarnConversionPointsAPI();
                }

                if (isloadmore_topup) {
                    if (!isfirstload_topup) {
                        limit_topup = limit_topup + 30;
                    }
                    callEarnTopUPHistoryAPI();
                }

            } else {
                if (mdb.getGKEarnConversionsPoints(mdb).size() > 0) {
                    btn_view_archive.setVisibility(View.VISIBLE);
                    if (isyearselected) {
                        txv_view_archive.setText("Filter Options");
                    } else {
                        txv_view_archive.setText("View Archive");
                    }
                }

                if (CacheManager.getInstance().getGKEarnTopUpHistory().size() > 0) {
                    btn_topup_view_archive.setVisibility(View.VISIBLE);
                    if (isyearselected) {
                        txv_topup_view_archive.setText("Filter Options");
                    } else {
                        txv_topup_view_archive.setText("View Archive");
                    }
                }

                if (nestedScrollViewAtTop(home_body)) {
                    swipe_container.setEnabled(true);
                } else {
                    swipe_container.setEnabled(false);
                }
            }
        }
    };

    private boolean nestedScrollViewAtTop(NestedScrollView nestedScrollView) {
        return nestedScrollView.getChildCount() == 0 || nestedScrollView.getChildAt(0).getTop() == 0;
    }

    //API
    private void callMainAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Processing request.", "Please wait...");
            if(CommonVariables.GKEARNBETATEST){
                getEarnSubscribersV2();
                generateReferralCodeV2();
            }else{
                getEarnSubscribers();
                generateReferralCode();
            }

        } else {
            swipe_container.setRefreshing(false);
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getEarnSubscribers() {
        Call<GKEarnSubscribersResponse> getEarnSubscribers = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .getEarnSubscribers(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid)
                );

        getEarnSubscribers.enqueue(getEarnSubscribersCallBack);
    }

    private final Callback<GKEarnSubscribersResponse> getEarnSubscribersCallBack = new Callback<GKEarnSubscribersResponse>() {
        @Override
        public void onResponse(Call<GKEarnSubscribersResponse> call, Response<GKEarnSubscribersResponse> response) {
            try {
                swipe_container.setRefreshing(false);
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        updateSubscriberList(response.body().getData());
                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else if (response.body().getStatus().equals("105")) {
                        updateSubscriberList(response.body().getData());
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                swipe_container.setRefreshing(false);
                hideProgressDialog();
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GKEarnSubscribersResponse> call, Throwable t) {
            swipe_container.setRefreshing(false);
            hideProgressDialog();
            showErrorGlobalDialogs();
            t.printStackTrace();
        }
    };

    private void updateSubscriberList(List<GKEarnSubscribers> data) {
        if (data.size() > 0) {
            home_body_container.setVisibility(View.VISIBLE);
            for (GKEarnSubscribers subscribers : data) {
                gKEarnSubscribers = subscribers;
            }

            if (gKEarnSubscribers != null) {
                mPoints = gKEarnSubscribers.getTotalPoints();
                mDownLines = gKEarnSubscribers.getTotalDownLines();

                txv_available_points.setText(CommonFunctions.commaFormatterWithDecimals(String.valueOf(mPoints)));

                txv_downlines.setText(String.valueOf(mDownLines));

                btn_convert_to_voucher.setVisibility(View.VISIBLE);
                btn_invite_friend.setVisibility(View.VISIBLE);

                isstockist = gKEarnSubscribers.getIsStockist();

                if (isstockist > 0) {
                    btn_apply_stockist.setVisibility(View.GONE);
//                    layout_topup_history.setVisibility(View.VISIBLE);
                    btn_activation_voucher.setVisibility(View.VISIBLE);
                    frame_topup.setVisibility(View.VISIBLE);
                    btn_topup.setVisibility(View.VISIBLE);
                } else {
                    btn_apply_stockist.setVisibility(View.VISIBLE);
//                    layout_topup_history.setVisibility(View.GONE);
                    btn_activation_voucher.setVisibility(View.GONE);
                    frame_topup.setVisibility(View.INVISIBLE);
                    btn_topup.setVisibility(View.INVISIBLE);
                }

                PreferenceUtils.removePreference(getViewContext(), GKEarnSubscribers.KEY_GKEARNSUBCRIBERS);
                PreferenceUtils.saveGKEarnSubscribersListPreference(getViewContext(), GKEarnSubscribers.KEY_GKEARNSUBCRIBERS, data);

                callEarnConversionPointsAPI();
                callEarnTopUPHistoryAPI();
            } else {
                txv_available_points.setText("0");
                txv_downlines.setText("0");
                btn_convert_to_voucher.setVisibility(View.GONE);
                btn_invite_friend.setVisibility(View.GONE);
                btn_apply_stockist.setVisibility(View.GONE);
            }
        } else {
            home_body_container.setVisibility(View.GONE);
            Objects.requireNonNull(getActivity()).finish();
            GKEarnHomeActivity.start(getViewContext(), GKEarnHomeActivity.GKEARN_FRAGMENT_UNREGISTERED_MEMBER, null);
        }
    }

    private void callEarnConversionPointsAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            if (isstockist > 0) {
                showProgressDialog("Fetching conversion and top-up history.", "Please wait...");
            } else {
                showProgressDialog("Fetching conversion history.", "Please wait...");
            }

            if (gKEarnSubscribers != null) {
                //DATE TIME REGISTERED
                dateregistered = gKEarnSubscribers.getDateTimeIN();
                String CurrentString = dateregistered;
                String[] separated = CurrentString.split("-");
                registrationyear = Integer.parseInt(separated[0]);
                registrationmonth = Integer.parseInt(separated[1]);

                MIN_YEAR = registrationyear;
                MIN_MONTH = registrationmonth;
                MAX_YEAR = currentyear;
                MAX_MONTH = currentmonth;

                if(CommonVariables.GKEARNBETATEST){
                    getEarnConversionPointsV2();
                }else {
                    getEarnConversionPoints();
                }
            }
        } else {
            swipe_container.setRefreshing(false);
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getEarnConversionPoints() {
        Call<GKEarnConversionPointsResponse> getEarnSubscribers = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .getEarnConversionPoints(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        passedyear,
                        limit
                );

        getEarnSubscribers.enqueue(getEarnConversionPointsCallBack);
    }

    private final Callback<GKEarnConversionPointsResponse> getEarnConversionPointsCallBack = new Callback<GKEarnConversionPointsResponse>() {
        @Override
        public void onResponse(Call<GKEarnConversionPointsResponse> call, Response<GKEarnConversionPointsResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        if (response.body().getData().size() > 0) {
                            isloadmore = true;
                        } else {
                            isloadmore = false;
                        }

                        isfirstload = false;

                        if (!isbottomscroll) {
                            mdb.truncateTable(mdb, GKEarnConversionsPointsDB.TABLE_GKEARN_CONVERSIONS);
                        }

                        List<GKEarnConversionPoints> gkEarnConversionPointsList = response.body().getData();

                        if (gkEarnConversionPointsList.size() > 0) {
                            for (GKEarnConversionPoints gkEarnConversionPoints : gkEarnConversionPointsList) {
                                mdb.insertGKEarnConversions(mdb, gkEarnConversionPoints);
                            }
                        }

                        checkhistorylist(mdb.getGKEarnConversionsPoints(mdb));

                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                hideProgressDialog();
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GKEarnConversionPointsResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            hideProgressDialog();
        }
    };

    private void checkhistorylist(List<GKEarnConversionPoints> data) {
        if (data.size() > 0) {
            emptyLayout.setVisibility(View.GONE);
            rv_conversion_history.setVisibility(View.VISIBLE);
            gkEarnConversionAdapter.updateData(data);
            btn_view_archive.setVisibility(View.VISIBLE);
            swipe_container.setVisibility(View.VISIBLE);
            swipe_container.setEnabled(true);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            rv_conversion_history.setVisibility(View.GONE);
            btn_view_archive.setVisibility(View.VISIBLE);
            swipe_container.setVisibility(View.VISIBLE);
            swipe_container.setEnabled(false);
        }
    }

    private void showViewArchiveDialogNew() {
        mGlobalDialogs = new GlobalDialogs(getViewContext());

        mGlobalDialogs.createDialog("View Archive", "",
                "CANCEL", "FILTER", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.SPINNER);

        View closebtn = mGlobalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        ArrayList<String> spinyearlist = new ArrayList<String>();
        spinyearlist = yearList();

        LinearLayout yearContainer = mGlobalDialogs.setContentSpinner(spinyearlist, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.CENTER));

        final int countyear = yearContainer.getChildCount();
        for (int i = 0; i < countyear; i++) {
            View spinnerView = yearContainer.getChildAt(i);
            if (spinnerView instanceof Spinner) {
                yearspinType = (Spinner) spinnerView;
                yearspinType.setOnItemSelectedListener(yearItemListenerNew);
            }
        }

        View btndoubleone = mGlobalDialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        View btndoubletwo = mGlobalDialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
                filterOptions();
            }
        });
    }

    private void showFilterOptionDialogNew() {
        mGlobalDialogs = new GlobalDialogs(getViewContext());

        mGlobalDialogs.createDialog("Filter Options", "",
                "CLOSE", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.TEXTVIEW);

        mGlobalDialogs.hideTextButtonAction();

        View closebtn = mGlobalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        ArrayList<String> textviewlist = new ArrayList<String>();
        textviewlist.add("EDIT SEARCHES");
        textviewlist.add("CLEAR SEARCHES");

        LinearLayout textViewContainer = mGlobalDialogs.setContentTextView(textviewlist, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 18, 0));

        final int count = textViewContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View textView = textViewContainer.getChildAt(i);

            if (textView instanceof TextView) {
                String tag = String.valueOf(textView.getTag());

                if (tag.equals("EDIT SEARCHES")) {
                    editsearches = (TextView) textView;
                    editsearches.setPadding(20, 20, 20, 20);
                    editsearches.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_information_blue));
                    editsearches.setOnClickListener(editsearchListener);
                } else if (tag.equals("CLEAR SEARCHES")) {
                    clearsearch = (TextView) textView;
                    clearsearch.setPadding(20, 20, 20, 20);
                    clearsearch.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_information_lightblue));
                    clearsearch.setOnClickListener(clearSearchListener);
                }
            }
        }

        View singlebtn = mGlobalDialogs.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });
    }

    private AdapterView.OnItemSelectedListener yearItemListenerNew = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                String spinyear = parent.getItemAtPosition(position).toString();
                if (!spinyear.equals("Select Year")) {
                    year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    passedyear = String.valueOf(year);
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

    //FILTER OPTIONS
    private void filterOptions() {
        if (isyearselected) {
            if (mdb != null) {
                mdb.truncateTable(mdb, GKEarnConversionsPointsDB.TABLE_GKEARN_CONVERSIONS);

                if (gkEarnConversionAdapter != null) {
                    gkEarnConversionAdapter.clear();
                }

                btn_view_archive.setVisibility(View.GONE);
                year = Calendar.getInstance().get(Calendar.YEAR);
                limit = 0;
                if (txv_view_archive.getText().equals("View Archive")) {
                    txv_view_archive.setText("Filter Options");
                }

                callEarnConversionPointsAPI();
            }
        } else {
            showToast("Please select a date.", GlobalToastEnum.WARNING);
        }
    }

    //create year list
    private ArrayList<String> yearList() {
        ArrayList<String> mYear = new ArrayList<String>();
        mYear.add("Select Year");
        for (int i = registrationyear; i <= currentyear; i++) {
            mYear.add(Integer.toString(i));
        }

        return mYear;
    }

    private View.OnClickListener editsearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mGlobalDialogs != null) {
                editSearchOption();
            }
        }
    };

    private View.OnClickListener clearSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mGlobalDialogs != null) {
                clearSearchOption();
            }
        }
    };

    private void editSearchOption() {
        limit = 0;
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;
        showViewArchiveDialogNew();
    }

    private void clearSearchOption() {
        limit = 0;
        txv_view_archive.setText("View Archive");
        year = Calendar.getInstance().get(Calendar.YEAR);
        passedyear = ".";
        isyearselected = false;
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;
    }

    //OTHERS
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_convert_to_voucher: {
                if (mPoints > 0)
                    VoucherPrepaidRequestActivity.start(getViewContext(), 0, VirtualVoucherProductFragment.BY_GKEARN, mPoints);
                else
                    showWarningToast("You do not have any points to convert. Thank you.");
                break;
            }

            case R.id.btn_invite_friend: {
                Bundle args = new Bundle();
                args.putString(GKEarnHomeActivity.GKEARN_KEY_FROM, GKEarnHomeActivity.GKEARN_VALUE_FROMHOME);
                GKEarnHomeActivity.start(getViewContext(), GKEarnHomeActivity.GKEARN_FRAGMENT_INVITE_A_FRIEND, args);
                break;
            }

            case R.id.btn_view_archive: {
                limit = 0;
                if (txv_view_archive.getText().toString().equals("View Archive"))
                    showViewArchiveDialogNew();
                else
                    showFilterOptionDialogNew();
                break;
            }

            case R.id.btn_apply_stockist: {
                GKEarnStockistPackagesActivity.start(getViewContext(), null);
                break;
            }

            case R.id.frame_topup: {
                layout_conversion_history.setVisibility(View.GONE);
                layout_topup_history.setVisibility(View.VISIBLE);
                frame_topup.setBackground(getViewContext().getResources().getDrawable(R.drawable.gkearn_tab_right_selected));
                frame_conversion.setBackgroundColor(getResources().getColor(R.color.color_transparent));
                txv_topup_history.setTextColor(getResources().getColor(R.color.color_gkearn_blue));
                txv_conversion_history.setTextColor(getResources().getColor(R.color.whitePrimary));
                break;
            }
            case R.id.frame_conversion: {
                layout_conversion_history.setVisibility(View.VISIBLE);
                layout_topup_history.setVisibility(View.GONE);
                frame_conversion.setBackground(getViewContext().getResources().getDrawable(R.drawable.gkearn_tab_left_selected));
                frame_topup.setBackgroundColor(getResources().getColor(R.color.color_transparent));
                txv_conversion_history.setTextColor(getResources().getColor(R.color.color_gkearn_blue));
                txv_topup_history.setTextColor(getResources().getColor(R.color.whitePrimary));
                break;
            }

            case R.id.btn_topup_view_archive: {
                limit_topup = 0;
                if (txv_topup_view_archive.getText().toString().equals("View Archive"))
                    showTopUpViewArchiveDialog();
                else
                    topupShowFilterOptionDialog();
                break;
            }

            case R.id.btn_topup: {
                validateStockistTopUp();
                break;
            }

            case R.id.btn_close: {
                layout_pending_req_via_payment_channel.setVisibility(View.GONE);
                break;
            }

            case R.id.btn_cancel_request: {
                cancelDialog();
                break;
            }

            case R.id.imv_btn_close_image: {
                getActivity().finish();
                break;
            }

            case R.id.imv_edit_code: {
                showCustomizeCodeDialogNew();
                break;
            }

            case R.id.btn_activation_voucher: {
                VoucherPrepaidRequestActivity.start(mContext, 0, VirtualVoucherProductFragment.BY_GKEARNACTIVATION, 0.00);
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        if (mdb != null) {
//            mdb.truncateTable(mdb, GKEarnConversionsPointsDB.TABLE_GKEARN_CONVERSIONS);
//            if (gkEarnConversionAdapter != null) {
//                gkEarnConversionAdapter.clear();
//            }
            btn_view_archive.setVisibility(View.GONE);
            year = Calendar.getInstance().get(Calendar.YEAR);
            swipe_container.setRefreshing(true);
            isfirstload = false;
            limit = 0;
            limit_topup = 0;
            txv_view_archive.setText("View Archive");
            isyearselected = false;
            isbottomscroll = false;
            isbottomscroll_topup = false;
            isloadmore = false;
            isloadmore_topup = false;
            passedyear = ".";
            callMainAPI();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().overridePendingTransition(0, 0);
    }

    @Override
    public void onResume() {
        boolean status = PreferenceUtils.getBooleanPreference(getViewContext(), PreferenceUtils.KEY_GKEARNBUYVOUCHER);
        if (status) {
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_GKEARNBUYVOUCHER);
            callMainAPI();
        }

        super.onResume();
    }

    private void callEarnTopUPHistoryAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            if(CommonVariables.GKEARNBETATEST){
                getGKEarnTopUpHistoryV2();
            }else{
                getGKEarnTopUpHistory();
            }
        } else {
            showNoInternetToast();
        }
    }

    private void getGKEarnTopUpHistory() {
        Call<GKEarnTopUpHistoryResponse> gettopuphistory = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .getGKEarnTopUpHistoryCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        passedyear,
                        passedmonth,
                        limit_topup,
                        CommonVariables.devicetype);

        gettopuphistory.enqueue(getGKEarnTopUpHistoryCallback);
    }

    private final Callback<GKEarnTopUpHistoryResponse> getGKEarnTopUpHistoryCallback = new Callback<GKEarnTopUpHistoryResponse>() {
        @Override
        public void onResponse(Call<GKEarnTopUpHistoryResponse> call, Response<GKEarnTopUpHistoryResponse> response) {
            hideProgressDialog();
            ResponseBody errorBody = response.errorBody();
            try {
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {

                        if (response.body().getGkEarnTopUpHistoryList().size() > 0) {
                            isloadmore_topup = true;
                        } else {
                            isloadmore_topup = false;
                        }

                        isfirstload_topup = false;

                        if (!isbottomscroll_topup) {
                            CacheManager.getInstance().removeGKEarnTopUpHistory();
                        }

                        gkEarnTopUpHistoryList = response.body().getGkEarnTopUpHistoryList();

                        if (gkEarnTopUpHistoryList.size() > 0) {
                            CacheManager.getInstance().saveGKEarnTopUpHistory(gkEarnTopUpHistoryList);
                        }

                        checkTopUpHistory(CacheManager.getInstance().getGKEarnTopUpHistory());

                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
                hideProgressDialog();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GKEarnTopUpHistoryResponse> call, Throwable t) {
            t.printStackTrace();
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

    private void checkTopUpHistory(List<GKEarnTopUpHistory> data) {
        if (data.size() > 0) {
            txv_topup_empty.setVisibility(View.GONE);
            rv_topup_history.setVisibility(View.VISIBLE);
            gkEarnTopUpHistoryAdapter.updateData(data);
            btn_topup_view_archive.setVisibility(View.VISIBLE);
            swipe_container.setVisibility(View.VISIBLE);
            swipe_container.setEnabled(true);
        } else {
            txv_topup_empty.setVisibility(View.VISIBLE);
            rv_topup_history.setVisibility(View.GONE);
            btn_topup_view_archive.setVisibility(View.VISIBLE);
            swipe_container.setVisibility(View.VISIBLE);
            swipe_container.setEnabled(false);
        }
    }

    private void showTopUpViewArchiveDialog() {
        mGlobalDialogs = new GlobalDialogs(getViewContext());

        mGlobalDialogs.createDialog("View Archive", "",
                "CANCEL", "FILTER", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.SPINNER);

        View closebtn = mGlobalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        ArrayList<String> spinyearlist = new ArrayList<String>();
        spinyearlist = yearList();

        ArrayList<String> spinmonthlist = new ArrayList<>();
        spinmonthlist = monthlist();

        Logger.debug("vanhttp", new Gson().toJson(spinmonthlist));

        LinearLayout yearContainer = mGlobalDialogs.setContentSpinner(spinyearlist, LinearLayout.HORIZONTAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, 0));

        final int countyear = yearContainer.getChildCount();
        for (int i = 0; i < countyear; i++) {
            View spinnerView = yearContainer.getChildAt(i);
            if (spinnerView instanceof Spinner) {
                yearspinType = (Spinner) spinnerView;
                yearspinType.setOnItemSelectedListener(topupYearItemListener);
            }
        }

        LinearLayout monthContainer = mGlobalDialogs.setContentSpinner(spinmonthlist, LinearLayout.HORIZONTAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, 0));

        final int countmonth = monthContainer.getChildCount();
        for (int i = 0; i < countmonth; i++) {
            View spinnerView = monthContainer.getChildAt(i);
            if (spinnerView instanceof Spinner) {
                monthspinType = (Spinner) spinnerView;
                monthspinType.setOnItemSelectedListener(topupMonthItemListener);
            }
        }

        View btndoubleone = mGlobalDialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        View btndoubletwo = mGlobalDialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
                topupFilterOptions();
            }
        });
    }

    //make the number month to month name
    private ArrayList<String> monthlist() {
        String[] months = new DateFormatSymbols().getMonths();

        ArrayList<String> mMonths = new ArrayList<String>();
        mMonths.add("Select Month");

        int max = 0;
        for (int i = 0; i < months.length; i++) {
            if (registrationyear == year && year != currentyear) {
                max = max + 1;
            } else if (year != currentyear) {
                max = max + 1;
            } else {
                if (i < MAX_MONTH) {
                    max = max + 1;
                } else {
                    break;
                }
            }
        }

        if (registrationyear == year) {
            mMonths.addAll(Arrays.asList(months).subList(MIN_MONTH - 1, max));
        } else {
            mMonths.addAll(Arrays.asList(months).subList(0, max));
        }
        return mMonths;
    }

    private AdapterView.OnItemSelectedListener topupYearItemListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                String spinyear = parent.getItemAtPosition(position).toString();
                if (!spinyear.equals("Select Year")) {
                    year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    passedyear = String.valueOf(year);

                    ArrayList<String> finalMonthList = new ArrayList<>();
                    finalMonthList = monthlist();
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                            (getViewContext(), android.R.layout.simple_spinner_dropdown_item, finalMonthList);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.dialog_global_messages_spinner_arrow);
                    monthspinType.setAdapter(spinnerArrayAdapter);

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

    private AdapterView.OnItemSelectedListener topupMonthItemListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                if (position > 0) {
                    String monthstring = parent.getItemAtPosition(position).toString();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new SimpleDateFormat("MMM").parse(monthstring));
                    month = cal.get(Calendar.MONTH) + 1;
                    if (month <= 9) {
                        passedmonth = "0" + String.valueOf(month);
                    } else {
                        passedmonth = String.valueOf(month);
                    }

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

    // TOPUP FILTER OPTIONS
    private void topupFilterOptions() {
        if (isyearselected && ismonthselected) {
            if (gkEarnTopUpHistoryList != null) {
                if (gkEarnTopUpHistoryAdapter != null) {
                    gkEarnTopUpHistoryAdapter.clear();
                }

                btn_topup_view_archive.setVisibility(View.GONE);
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                limit_topup = 0;
                if (txv_topup_view_archive.getText().equals("View Archive")) {
                    txv_topup_view_archive.setText("Filter Options");
                }

                callEarnTopUPHistoryAPI();
            }
        } else {
            showToast("Please select a date.", GlobalToastEnum.WARNING);
        }
    }

    private View.OnClickListener topupEditsearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mGlobalDialogs != null) {
                topupEditSearchOption();
            }
        }
    };

    private View.OnClickListener topupClearSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mGlobalDialogs != null) {
                topupClearSearchOption();
            }
        }
    };

    private void topupEditSearchOption() {
        limit = 0;
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;
        showTopUpViewArchiveDialog();
    }

    private void topupClearSearchOption() {
        limit = 0;
        txv_topup_view_archive.setText("View Archive");
        year = Calendar.getInstance().get(Calendar.YEAR);
        passedyear = ".";
        isyearselected = false;
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;
    }

    private void topupShowFilterOptionDialog() {
        mGlobalDialogs = new GlobalDialogs(getViewContext());

        mGlobalDialogs.createDialog("Filter Options", "",
                "CLOSE", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.TEXTVIEW);

        mGlobalDialogs.hideTextButtonAction();

        View closebtn = mGlobalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        ArrayList<String> textviewlist = new ArrayList<String>();
        textviewlist.add("EDIT SEARCHES");
        textviewlist.add("CLEAR SEARCHES");

        LinearLayout textViewContainer = mGlobalDialogs.setContentTextView(textviewlist, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 18, 0));

        final int count = textViewContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View textView = textViewContainer.getChildAt(i);

            if (textView instanceof TextView) {
                String tag = String.valueOf(textView.getTag());

                if (tag.equals("EDIT SEARCHES")) {
                    editsearches = (TextView) textView;
                    editsearches.setPadding(20, 20, 20, 20);
                    editsearches.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_information_blue));
                    editsearches.setOnClickListener(topupEditsearchListener);
                } else if (tag.equals("CLEAR SEARCHES")) {
                    clearsearch = (TextView) textView;
                    clearsearch.setPadding(20, 20, 20, 20);
                    clearsearch.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_information_lightblue));
                    clearsearch.setOnClickListener(topupClearSearchListener);
                }
            }
        }

        View singlebtn = mGlobalDialogs.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });
    }

    //VALIDATE IF HAS PENDING TOP UP
    private void validateStockistTopUp() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            validateIfHasPendingTopUp(validateIfHasPendingTopUpSession);
        } else {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    }

    private void validateIfHasPendingTopUp(Callback<GenericResponse> validateIfHasPendingTopUpCallback) {
        Call<GenericResponse> validateifhaspendingtopup = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .validateIfEarnHasPendingPaymentRequest(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        GKEarnHomeActivity.GKEARN_VALUE_TYPE_TOPUP,
                        CommonVariables.devicetype);

        validateifhaspendingtopup.enqueue(validateIfHasPendingTopUpCallback);
    }

    private final Callback<GenericResponse> validateIfHasPendingTopUpSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            okhttp3.ResponseBody errorBody = response.errorBody();
            hideProgressDialog();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    Bundle args = new Bundle();
                    args.putString(GKEarnHomeActivity.GKEARN_KEY_FROM, GKEarnHomeActivity.GKEARN_VALUE_FROMTOPUPBUTTON);
                    GKEarnPaymentOptionsActivity.start(getViewContext(), args);
                } else if (response.body().getStatus().equals("104")) {
                    showAutoLogoutDialog(response.body().getMessage());
                } else if (response.body().getStatus().equals("105")) {
                    String data = response.body().getData();
                    if (data != null) {
                        if (!data.equals("")) {
                            String registrationID = CommonFunctions.parseJSON(data, "MerchantReferenceNo");
                            String receiveamount = CommonFunctions.parseJSON(data, "Amount");
                            paymenttype = CommonFunctions.parseJSON(data, "PaymentType");

                            showPendingLayout(data, registrationID, receiveamount);

                        } else {
                            proceedtoTopUp();
                        }
                    } else {
                        proceedtoTopUp();
                    }
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
            Logger.debug("vanhttp", "omg3");
        }
    };

    private void showPendingLayout(String data, String txnno, String amount) {

        billingid = txnno;

        if (data != null) {
            if (!data.equals("")) {
                if (txnno.substring(0, 3).equals("771") || txnno.substring(0, 3).equals("991")
                        || txnno.substring(0, 3).equals("551")) {
                    layout_pending_paynow.setVisibility(View.GONE);
                    txv_pending_content.setText("You have a pending payment transaction. If you think you completed this transaction, please contact support,if not just cancel the request.");

                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                    );
                    layout_pending_cancelrequest.setLayoutParams(param);

                } else {
                    layout_pending_req_via_payment_channel.setVisibility(View.VISIBLE);

                    if (paymenttype.contains("PARTNER")) {
                        layout_pending_paynow.setVisibility(View.VISIBLE);
                        txv_pending_content.setText("You have a pending payment request, kindly settle it first or cancel your previous request.");
                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f
                        );
                        layout_pending_cancelrequest.setLayoutParams(param);

                    } else {
                        layout_pending_paynow.setVisibility(View.GONE);
                        txv_pending_content.setText("You have a pending payment transaction. If you think you completed his transaction, please contact support,if not just cancel the request.");

                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                        );
                        layout_pending_cancelrequest.setLayoutParams(param);
                    }
                }
            } else {
                layout_pending_req_via_payment_channel.setVisibility(View.GONE);
            }
        }

        btn_pending_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymenttype.contains("PARTNER")) {
                    layout_pending_req_via_payment_channel.setVisibility(View.GONE);
                    PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, gkService.getServiceName());
                    SchoolMiniBillingReferenceActivity.start(getViewContext(), new SchoolMiniPayment(".",
                            txnno, Double.valueOf(amount)), String.valueOf(amount), GKEarnHomeActivity.GKEARN_VALUE_FROMTOPUPBUTTON);
                }

            }

        });
    }

    private void proceedtoTopUp() {
        Bundle args = new Bundle();
        args.putString(GKEarnHomeActivity.GKEARN_KEY_FROM, GKEarnHomeActivity.GKEARN_VALUE_FROMTOPUPBUTTON);
        GKEarnPaymentOptionsActivity.start(getViewContext(), args);
    }

    //CANCELLATION
    private void cancelDialog() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "",
                "Cancel", "OK", ButtonTypeEnum.DOUBLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //TEXTVIEW
        LinearLayout textViewMainContainer = dialog.getTextViewMessageContainer();
        textViewMainContainer.setPadding(15, 15, 15, 0);

        List<String> declineList = new ArrayList<>();
        declineList.add("Reason for Cancellation:");

        LinearLayout textViewContainer = dialog.setContentTextView(declineList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.CENTER));

        //EDITTEXT
        LinearLayout editTextMainContainer = dialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(15, 0, 15, 15);

        List<String> editTextDataType = new ArrayList<>();
        editTextDataType.add(String.valueOf(GlobalDialogsEditText.CUSTOMVARCHAR));

        LinearLayout editTextContainer = dialog.setContentEditText(editTextDataType,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.CENTER,
                        R.drawable.border, 300, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;
                editItem.setScroller(new Scroller(getViewContext()));
                editItem.setSingleLine(false);

                editItem.setLines(5);
                editItem.setMinLines(3);
                editItem.setMaxLines(5);
                editItem.setVerticalScrollBarEnabled(true);
                editItem.setMovementMethod(new ScrollingMovementMethod());

                editItem.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                        }

                        return false;
                    }
                });
                editItem.setBackgroundResource(R.drawable.border);
                String taggroup = editItem.getTag().toString();
                if (taggroup.equals("TAG 0")) {
                    dialogEdt = editItem;
                    dialogEdt.addTextChangedListener(dialogTextWatcher);
                }
            }
        }

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View dblbtnone = dialog.btnDoubleOne();
        dblbtnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View dblbtntwo = dialog.btnDoubleTwo();
        dblbtntwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialogEdt.getText().toString().equals("")) {
                    dialog.dismiss();
                    remarks = dialogString;
                    cancelRequest();
                } else {
                    showToast("Reason for cancellation is required.", GlobalToastEnum.WARNING);
                }
            }
        });
    }

    //OTHERS
    private TextWatcher dialogTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            dialogString = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void cancelRequest() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Processing Request.", "Please wait...");
            cancelEarnRequest();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //VALIDATE IF EARN HAS PENDING PAYMENT REQUEST
    private void cancelEarnRequest() {
        Call<GenericResponse> cancelEarnRequest = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .cancelEarnRequest(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        billingid,
                        remarks,
                        GKEarnHomeActivity.GKEARN_VALUE_TYPE_TOPUP,
                        CommonVariables.devicetype
                );

        cancelEarnRequest.enqueue(cancelEarnRequestCallBack);
    }

    private final Callback<GenericResponse> cancelEarnRequestCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        showCancellationSuccessfulDialog();
                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else {
                        showCancellationErrorDialog(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                hideProgressDialog();
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            hideProgressDialog();
        }
    };

    private void showCancellationSuccessfulDialog() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "Your request has been successfully cancelled.",
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.SUCCESS);

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                layout_pending_req_via_payment_channel.setVisibility(View.GONE);
            }
        });
    }

    private void showCancellationErrorDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", message,
                "OK", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    //REFERRAL CODE
    private void generateReferralCode() {
        Call<GenerateReferralCodeResponse> call = RetrofitBuild.getReferralAPIService(getViewContext())
                .generateReferralCode(
                        imei,
                        userid,
                        sessionid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        CommonVariables.gkearnreferral,
                        CommonVariables.devicetype
                );
        call.enqueue(generateReferralCodeCallback);
    }

    private Callback<GenerateReferralCodeResponse> generateReferralCodeCallback = new Callback<GenerateReferralCodeResponse>() {
        @Override
        public void onResponse(Call<GenerateReferralCodeResponse> call, Response<GenerateReferralCodeResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().contains("000")) {
                        strCode = response.body().getData().getReferralCode();
                        txv_referral.setText(strCode);
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenerateReferralCodeResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

    private void customizeReferralCode() {
        Call<GenericResponse> call = RetrofitBuild.getReferralAPIService(getViewContext())
                .customizeReferralCode(
                        imei,
                        userid,
                        sessionid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        strCode,
                        "ANDROID"
                );
        call.enqueue(customizeReferralCodeCallback);
    }

    private Callback<GenericResponse> customizeReferralCodeCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().contains("000")) {
                        if(CommonVariables.GKEARNBETATEST){
                            generateReferralCodeV2();
                        }else{
                            generateReferralCode();
                        }
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                hideProgressDialog();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

    private void showCustomizeCodeDialogNew() {
        try {

            GlobalDialogs dialog = new GlobalDialogs(getViewContext());

            dialog.createDialog("Notice", "Make it more personal! " +
                            "Customize your code and share it with your friends!",
                    "CANCEL", "SUBMIT", ButtonTypeEnum.DOUBLE,
                    false, false, DialogTypeEnum.EDITTEXT);

            View closebtn = dialog.btnCloseImage();
            closebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.showContentMessage();

            List<String> editTextDataType  = new ArrayList<>();
            editTextDataType.add(String.valueOf(GlobalDialogsEditText.VARCHAR));

            LinearLayout editTextContainer = dialog.setContentEditText(editTextDataType,
                    LinearLayout.VERTICAL,
                    new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.CENTER,
                            R.drawable.border, 12, "Code"));

            final int count = editTextContainer.getChildCount();
            for (int i = 0; i < count; i++) {
                View editView = editTextContainer.getChildAt(i);
                if (editView instanceof EditText) {
                    EditText editItem = (EditText) editView;
                    editItem.setPadding(15,30,15,30);
                    editItem.setHintTextColor(ContextCompat.getColor(mContext, R.color.colorsilver));
                    String taggroup = editItem.getTag().toString();
                    if(taggroup.equals("TAG 0")) {
                        edt_code = editItem;
                        edt_code.addTextChangedListener(codeTextWatcher);
                    }
                }
            }

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
                    if (edt_code.getText().length() > 2) {
                        dialog.dismiss();
                        showProgressDialog("", "Please wait...");
                        customizeReferralCode();
                    } else {
                        showToast("Code must have 3-7 characters. Please try again.", GlobalToastEnum.WARNING);
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextWatcher codeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            strCode = s.toString().toUpperCase();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    /**
     * SECURITY UPDATE
     * AS OF
     * MARCH 2020
     */

    private void  getEarnSubscribersV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        getIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", getIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        getAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        getKey = CommonFunctions.getSha1Hex(getAuth + sessionid + "getEarnSubscribersV2");
        getParam = CommonFunctions.encryptAES256CBC(getKey, String.valueOf(paramJson));

        getEarnSubscribersV2Object(getEarnSubscribersV2Callback);


    }
    private void getEarnSubscribersV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getGkEarnV2API(getViewContext())
                .getEarnSubscribersV2(getAuth,sessionid,getParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse> getEarnSubscribersV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {
            Logger.debug("okhttp","GETEARN SUBS RES : "+ new Gson().toJson(response.body()));
                swipe_container.setRefreshing(false);
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(getKey,response.body().getMessage());
                    if (response.body().getStatus().equals("000") || response.body().getStatus().equals("105")) {
                        String decryptedData =  CommonFunctions.decryptAES256CBC(getKey,response.body().getData());

                        data = new Gson().fromJson(decryptedData,
                                new TypeToken<List<GKEarnSubscribers>>(){}.getType());

                        Logger.debug("okhttp","GETEARN SUBS : "+ decryptedData);
                        updateSubscriberList(data);
                    } else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else{
                        showErrorGlobalDialogs(message);
                    }
                } else {
                    showErrorGlobalDialogs();
                }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            swipe_container.setRefreshing(false);
            hideProgressDialog();
            showErrorGlobalDialogs();
            t.printStackTrace();
            Logger.debug("okhttp","GETEARN SUBS FAILED : "+ new Gson().toJson(t.getMessage()));
        }
    };

    //generate referral code
    private void generateReferralCodeV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("generationtype", CommonVariables.gkearnreferral);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        generateIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", generateIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        generateAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        generateKey = CommonFunctions.getSha1Hex(generateAuth + sessionid + "generateReferralCodeV2");
        generateParam = CommonFunctions.encryptAES256CBC(generateKey, String.valueOf(paramJson));

        generateReferralCodeV2Object(generateReferralCodeV2Callback);


    }
    private void generateReferralCodeV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .generateReferralCodeV2(generateAuth,sessionid,generateParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  generateReferralCodeV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(generateKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    String data= CommonFunctions.decryptAES256CBC(generateKey,response.body().getData());
                    Referral referral = new Gson().fromJson(data,Referral.class);
                    Logger.debug("okhttp","GENERATE REFERRAL CODE : "+ data);
                    strCode =referral.getReferralCode();
                    txv_referral.setText(strCode);
                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }
            }else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
            t.printStackTrace();
        }
    };

    //get topup history
    private void getGKEarnTopUpHistoryV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("year",passedyear);
        parameters.put("month",passedmonth);
        parameters.put("limit", String.valueOf(limit_topup));
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        topupIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", topupIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        topupAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        topupKey = CommonFunctions.getSha1Hex(topupAuth + sessionid + "getGKEarnTopUpHistoryV2");
        topupParam = CommonFunctions.encryptAES256CBC(topupKey, String.valueOf(paramJson));

        getGKEarnTopUpHistoryV2Object(getGKEarnTopUpHistoryV2Callback);


    }
    private void getGKEarnTopUpHistoryV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getGkEarnV2API(getViewContext())
                .getGKEarnTopUpHistoryV2(topupAuth,sessionid,topupParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  getGKEarnTopUpHistoryV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(topupKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String data = CommonFunctions.decryptAES256CBC(topupKey,response.body().getData());
                    gkEarnTopUpHistoryList = new Gson().fromJson(data,
                            new TypeToken<List<GKEarnTopUpHistory>>(){}.getType());
                    if (gkEarnTopUpHistoryList.size() > 0) {
                        isloadmore_topup = true;
                    } else {
                        isloadmore_topup = false;
                    }

                    isfirstload_topup = false;

                    if (!isbottomscroll_topup) {
                        CacheManager.getInstance().removeGKEarnTopUpHistory();
                    }

                    if (gkEarnTopUpHistoryList.size() > 0) {
                        CacheManager.getInstance().saveGKEarnTopUpHistory(gkEarnTopUpHistoryList);
                    }
                    checkTopUpHistory(CacheManager.getInstance().getGKEarnTopUpHistory());

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
          hideProgressDialog();
          showErrorGlobalDialogs();
          t.printStackTrace();
        }
    };

    //get earn conversion points
    private void getEarnConversionPointsV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("year",passedyear);
        parameters.put("limit", String.valueOf(limit));
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getEarnConversionPointsV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        getEarnConversionPointsV2Object(getEarnConversionPointsV2Callback);


    }
    private void getEarnConversionPointsV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getGkEarnV2API(getViewContext())
                .getEarnConversionPointsV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  getEarnConversionPointsV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    List<GKEarnConversionPoints> gkEarnConversionPointsList = new Gson().fromJson(data,
                            new TypeToken<List<GKEarnConversionPoints>>(){}.getType());

                    isloadmore = gkEarnConversionPointsList.size() > 0;
                    isfirstload = false;

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, GKEarnConversionsPointsDB.TABLE_GKEARN_CONVERSIONS);
                    }

                    if (gkEarnConversionPointsList.size() > 0) {
                        for (GKEarnConversionPoints gkEarnConversionPoints : gkEarnConversionPointsList) {
                            mdb.insertGKEarnConversions(mdb, gkEarnConversionPoints);
                        }
                    }

                    checkhistorylist(mdb.getGKEarnConversionsPoints(mdb));

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };




}
