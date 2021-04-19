package com.goodkredit.myapplication.fragments.prepaidrequest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.coopassistant.member.CoopAssistantTransactionHistoryActivity;
import com.goodkredit.myapplication.activities.gkearn.GKEarnBuyActivationHistoryActivity;
import com.goodkredit.myapplication.activities.prepaidrequest.VirtualVoucherProductConfirmationActivity;
import com.goodkredit.myapplication.activities.prepaidrequest.VoucherPrepaidRequestActivity;
import com.goodkredit.myapplication.activities.viewpendingorders.ViewPendingOrdersActivity;
import com.goodkredit.myapplication.adapter.coopassistant.member.CoopAssistantMemberCreditsAdapter;
import com.goodkredit.myapplication.adapter.prepaidrequest.PaymentChannelsRecyclerAdapter;
import com.goodkredit.myapplication.adapter.prepaidrequest.VirtualVoucherProductDialogRecyclerAdapter;
import com.goodkredit.myapplication.adapter.prepaidrequest.VirtualVoucherProductRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.PaymentChannels;
import com.goodkredit.myapplication.bean.PrepaidRequest;
import com.goodkredit.myapplication.bean.V2VirtualVoucherRequestQueue;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.decoration.DividerItemDecoration;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.GKPaymentOptions;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMemberCredits;
import com.goodkredit.myapplication.model.gkearn.GKEarnSubscribers;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetPaymentPartnersResponse;
import com.goodkredit.myapplication.responses.GetVirtualVoucherProductResponse;
import com.goodkredit.myapplication.responses.V2GetProcessQueueResponse;
import com.goodkredit.myapplication.responses.coopassistant.CoopAssistantMemberCreditsResponse;
import com.goodkredit.myapplication.responses.gkearn.GKEarnSubscribersResponse;
import com.goodkredit.myapplication.responses.prepaidrequest.RequestVoucherGenerationResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User-PC on 10/24/2017.
 */

public class VirtualVoucherProductFragment extends BaseFragment implements View.OnClickListener {

    public static final String KEY_MODE = "key_mode";

    public static final String BY_REQUEST_TO_SPONSOR = "requestToSponsor";
    public static final String BY_CSB_REWARDS = "csbRewards";
    public static final String BY_UNO_REWARDS = "unoRewards";
    public static final String BY_GKEARN = "gkearn";
    public static final String BY_COOPGETVOUCHER = "coopgetvoucher";
    public static final String BY_GKEARNACTIVATION = "gkearnactivation";

    private String MODE = "";

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private int limit = 0;

    private TextView txvOrderVouchersLabel;
    private TextView txvTotalVouchers;
    private TextView txvTotalVouchersValue;
    private TextView txvTotaAmount;
    private TextView txvTotalAmountValue;
    private Button btnGenerate;

    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String borrowername;
    private String borrowermobileno;
    private String vouchers;

    //SERVICES
    private String servicecode = "";

    private RecyclerView recyclerView;
    private RecyclerView rv_payment_channels;
    private TextView tv_payment_channels_note;
    private VirtualVoucherProductRecyclerAdapter mAdapter;
    private PaymentChannelsRecyclerAdapter paymentChannelsAdapter;

    private MaterialDialog mConfirmRequestDialog;
    private TextView txvConfirm;

    private int mOrderQuantity = 0;
    private double mOrderTotal = 0;

    private ArrayList<PrepaidRequest> mVoucherData;

    private boolean isGenerate = false;

    private DatabaseHandler mdb;

    //EMPTY
    private RelativeLayout emptyLayout;
    private TextView txv_empty_content;
    private ImageView imv_empty_box;
    private ImageView imv_empty_refresh;
    private ImageView imv_empty_box_watermark;
    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private RelativeLayout mainLayout;

    private CoordinatorLayout coordinatorLayout;

    //POINTS
    private double mPoints;

    //BUY VOUCHER
    private LinearLayout header_buy_voucher;
    private List<PrepaidRequest> prepaidRequest = new ArrayList<>();

    //PENDING ORDER
    private LinearLayout header_points;
    private ImageView refresh_points;
    private TextView txvPendingOrders;
    private TextView pendingOrderBadge;
    private LinearLayout btnViewPendingOrders;
    private List<V2VirtualVoucherRequestQueue> mVirtualVoucherRequestList;
    private RelativeLayout layout_badge;

    //POINTS VIEW
    private TextView tv_points;
    private String serviceType = "";

    //DELAY ON CLICKS
    private long mLastClickTime = 0;

    //PAYMENT OPTIONS
    private List<GKPaymentOptions> gkPaymentOptionsList = new ArrayList<>();
    private LinearLayout payment_container;
    private RecyclerView rv_paymentoptions;
    private ArrayList<GKPaymentOptions> passedgkPaymentOptionsList;
    private LinearLayout layout_payment_options;

    //COOP (GET VOUCHERS)
    private CoopAssistantMemberCreditsAdapter rv_paymentoptionsadapter;
    private List<CoopAssistantMemberCredits> coopMemberCreditsList = new ArrayList<>();
    private double mCredits;

    //CARD NOTE
    private TextView txv_cardnote;
    private LinearLayout linear_cardlogo_container;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String keyEncryption;
    private String authenticationid;
    private String param;

    private String virtualVoucherAuthID;
    private String virtualVoucherIndex;
    private String virtualVoucherKey;
    private String virtualVoucherParam;


    private String processQueueIndex;
    private String processQueueAuthID;
    private String processQueueKey;
    private String processQueueParam;

    private String v2Index;
    private String v2KeyEncryption;
    private String v2Authenticationid;
    private String v2Param;

    //BUY ACTIVATION
    private String servicefrom = "";

    //CONVERT GKEARN POINTS
    private String convertIndex;
    private String convertAuth;
    private String convertKey;
    private String convertParam;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_virtual_voucher_product, container, false);

        MODE = getArguments().getString(KEY_MODE);

        init(view);

        initData();

        return view;
    }

    public String getCurrentMODE() {
        return MODE;
    }

    private void init(View view) {
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        mLlLoader = view.findViewById(R.id.loaderLayout);
        mTvFetching = view.findViewById(R.id.fetching);
        mTvWait = view.findViewById(R.id.wait);
        txvOrderVouchersLabel = view.findViewById(R.id.txvOrderVouchersLabel);
        recyclerView = view.findViewById(R.id.recycler_view_virtual_vouchers);
        rv_payment_channels = view.findViewById(R.id.rv_payment_channels);
        tv_payment_channels_note = view.findViewById(R.id.tv_payment_channels_note);
        btnGenerate = view.findViewById(R.id.btnGenerate);
        btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "GENERATE"));
        btnGenerate.setOnClickListener(this);
        txvTotalVouchers = view.findViewById(R.id.txvTotalVouchers);
        txvTotalVouchers.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "TOTAL VOUCHERS"));
        txvTotalVouchersValue = view.findViewById(R.id.txvTotalVouchersValue);
        txvTotalVouchersValue.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "0"));
        txvTotaAmount = view.findViewById(R.id.txvTotaAmount);
        txvTotaAmount.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "TOTAL AMOUNT"));
        txvTotalAmountValue = view.findViewById(R.id.txvTotalAmountValue);
        txvTotalAmountValue.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", CommonFunctions.currencyFormatter("0")));
        emptyLayout = view.findViewById(R.id.emptyLayout);
        txv_empty_content = view.findViewById(R.id.txv_empty_content);
        imv_empty_refresh = view.findViewById(R.id.imv_empty_refresh);
        imv_empty_refresh.setOnClickListener(this);
        mainLayout = view.findViewById(R.id.mainLayout);
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet = view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);
        txvPendingOrders = view.findViewById(R.id.txvPendingOrders);
        pendingOrderBadge = view.findViewById(R.id.pendingOrderBadge);
        layout_badge = view.findViewById(R.id.layout_badge);
        btnViewPendingOrders = view.findViewById(R.id.btnViewPendingOrders);
        btnViewPendingOrders.setOnClickListener(this);

        //BUY VOUCHER
        header_buy_voucher = view.findViewById(R.id.header_buy_voucher);

        //POINTS
        tv_points = view.findViewById(R.id.tv_points);
        ImageView imgEghlLogoVisa = view.findViewById(R.id.eghlLogoVisa);
        ImageView imgEghlLogoMastercard = view.findViewById(R.id.eghlLogoMasterCard);
        header_points = view.findViewById(R.id.header_points);
        refresh_points = view.findViewById(R.id.refresh_points);
        refresh_points.setOnClickListener(this);

        Glide.with(getViewContext())
                .load(R.drawable.eghl_logo_visa)
                .apply(new RequestOptions()
                        .fitCenter()
                        .encodeFormat(Bitmap.CompressFormat.PNG)
                        .downsample(DownsampleStrategy.AT_LEAST)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imgEghlLogoVisa);

        Glide.with(getViewContext())
                .load(R.drawable.eghl_logo_mastercard)
                .apply(new RequestOptions()
                        .fitCenter()
                        .encodeFormat(Bitmap.CompressFormat.PNG)
                        .downsample(DownsampleStrategy.AT_LEAST)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imgEghlLogoMastercard);

        //PAYMENT OPTION
        layout_payment_options = view.findViewById(R.id.layout_payment_options);
        payment_container = view.findViewById(R.id.payment_container);
        rv_paymentoptions = view.findViewById(R.id.rv_paymentoptions);

        //CARD NOTE
        txv_cardnote = view.findViewById(R.id.txv_cardnote);
        linear_cardlogo_container = view.findViewById(R.id.linear_cardlogo_container);
    }

    private void initData() {
        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        mdb = new DatabaseHandler(getViewContext());
        V2Subscriber mSubscriber = mdb.getSubscriber(mdb);
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        borrowername = mSubscriber.getBorrowerName();
        borrowermobileno = mSubscriber.getMobileNumber();
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        if(servicecode.equals("")) {
            servicecode = ".";
        }

        mVirtualVoucherRequestList = new ArrayList<>();
        mVoucherData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.setNestedScrollingEnabled(false);
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        mAdapter = new VirtualVoucherProductRecyclerAdapter(getViewContext(), VirtualVoucherProductFragment.this, MODE);
        //recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getViewContext(), R.drawable.voucher_divider), true, false));
        recyclerView.setAdapter(mAdapter);

        rv_payment_channels.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_payment_channels.setNestedScrollingEnabled(false);
        paymentChannelsAdapter = new PaymentChannelsRecyclerAdapter(getViewContext());
        rv_payment_channels.setAdapter(paymentChannelsAdapter);

        pendingOrderBadge.setText(String.valueOf(mdb.getVirtualVoucherRequestQueue(mdb).size()));

        rv_paymentoptions.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_paymentoptions.setNestedScrollingEnabled(false);
        rv_paymentoptions.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getViewContext(), R.drawable.custom_paymentoption_divider)));

        rv_paymentoptionsadapter = new CoopAssistantMemberCreditsAdapter(getViewContext(), VirtualVoucherProductFragment.this);
        rv_paymentoptions.setAdapter(rv_paymentoptionsadapter);

        switch (MODE) {
            case BY_CSB_REWARDS:
                serviceType = "CSBREDEMPTION";
                txv_empty_content.setText("No Vouchers.");
                ((VoucherPrepaidRequestActivity) getViewContext()).setActionBarTitle("Convert CSB Rewards");
                txvOrderVouchersLabel.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "Convert CSB Rewards"));
                mPoints = getArguments() != null ? getArguments().getDouble("points", 0) : 0;
                btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "CONVERT TO VOUCHER"));
                header_points.setBackgroundResource(R.color.color_csb_purple);
                header_points.setVisibility(View.VISIBLE);
                refresh_points.setOnClickListener(this);
                tv_points.setText(CommonFunctions.pointsFormatter(String.valueOf(mPoints)));
                btnGenerate.setBackgroundColor(Color.parseColor("#4F2185"));
                break;
            case BY_UNO_REWARDS:
                serviceType = "UNO";
                txv_empty_content.setText("No Vouchers.");
                ((VoucherPrepaidRequestActivity) getViewContext()).setActionBarTitle("Convert UNO Rewards");
                txvOrderVouchersLabel.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "Convert UNO Rewards"));
                mPoints = getArguments() != null ? getArguments().getDouble("points", 0) : 0;
                btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "CONVERT TO VOUCHER"));
                header_points.setBackgroundResource(R.color.color_uno_yellow);
                header_points.setVisibility(View.VISIBLE);
                refresh_points.setOnClickListener(this);
                tv_points.setText(CommonFunctions.pointsFormatter(String.valueOf(mPoints)));
                btnGenerate.setBackgroundResource(R.drawable.btn_uno_button_rect);
                break;
            case BY_GKEARN:
                serviceType = "GKEARN";
                txv_empty_content.setText("No Vouchers.");
                ((VoucherPrepaidRequestActivity) getViewContext()).setActionBarTitle("Get Vouchers");
                txvOrderVouchersLabel.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "Convert to Voucher"));
                mPoints = getArguments() != null ? getArguments().getDouble("points", 0) : 0;
                btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "CONVERT TO VOUCHER"));
                header_points.setBackgroundResource(R.color.color_gkearn_blue);
                header_points.setVisibility(View.VISIBLE);
                refresh_points.setOnClickListener(this);
                tv_points.setText(CommonFunctions.currencyFormatter(String.valueOf(mPoints)));
                btnGenerate.setBackgroundColor(getResources().getColor(R.color.color_gkearn_blue));
                break;
            case BY_COOPGETVOUCHER:
                serviceType = ".";
                txv_empty_content.setText("No Vouchers.");
                txvOrderVouchersLabel.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "Order Vouchers"));
                ((VoucherPrepaidRequestActivity) getViewContext()).setActionBarTitle("Order Vouchers");
                btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "CONFIRM"));
                header_buy_voucher.setVisibility(View.VISIBLE);
                txvTotalVouchersValue.setTextColor(getResources().getColor(R.color.coopassist_green));
                txvTotalAmountValue.setTextColor(getResources().getColor(R.color.coopassist_green));
                btnGenerate.setBackgroundColor(getResources().getColor(R.color.coopassist_blue));
                txv_cardnote.setVisibility(View.GONE);
                linear_cardlogo_container.setVisibility(View.GONE);
                layout_badge.setVisibility(View.GONE);
                btnViewPendingOrders.setBackgroundResource(R.drawable.bg_btn_coopassistant_getvoucher);
                txvPendingOrders.setText("VIEW TRANSACTION HISTORY");
                break;
            case BY_GKEARNACTIVATION:
                serviceType = "GKEARN";
                servicefrom = BY_GKEARNACTIVATION;
                txv_empty_content.setText("No Vouchers.");
                txvOrderVouchersLabel.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "Buy Activation Vouchers"));
                ((VoucherPrepaidRequestActivity) getViewContext()).setActionBarTitle("Buy Activation Vouchers");
                btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "CONFIRM"));
                header_buy_voucher.setVisibility(View.VISIBLE);
                txvTotalVouchersValue.setTextColor(getResources().getColor(R.color.color_gkearn_blue));
                txvTotalAmountValue.setTextColor(getResources().getColor(R.color.color_gkearn_blue));
                btnGenerate.setBackgroundColor(getResources().getColor(R.color.color_gkearn_blue));
                txv_cardnote.setVisibility(View.GONE);
                linear_cardlogo_container.setVisibility(View.GONE);
                layout_badge.setVisibility(View.GONE);
                btnViewPendingOrders.setBackgroundResource(R.drawable.bg_btn_coopassistant_getvoucher);
                btnViewPendingOrders.setBackgroundColor(getResources().getColor(R.color.color_gkearn_blue));
                txvPendingOrders.setText("VIEW TRANSACTION HISTORY");
                break;
            default:
                serviceType = "BUYVOUCHER";
                txv_empty_content.setText("No Vouchers.");
                txvOrderVouchersLabel.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "Order Vouchers"));
                ((VoucherPrepaidRequestActivity) getViewContext()).setActionBarTitle("Order Vouchers");
                btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "GENERATE"));
                header_buy_voucher.setVisibility(View.VISIBLE);
                txv_cardnote.setVisibility(View.VISIBLE);
                linear_cardlogo_container.setVisibility(View.VISIBLE);
                layout_badge.setVisibility(View.VISIBLE);
                txvPendingOrders.setText("VIEW PENDING ORDERS");
                break;
        }

        getSession();
    }

    private void setUpConfirmRequestDialog(String qty, String total, List<PrepaidRequest> mData) {
        mConfirmRequestDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(true)
                .customView(R.layout.dialog_confirm_virtual_voucher_request, true)
                .build();

        View view = mConfirmRequestDialog.getCustomView();
        assert view != null;

        TextView txvTitle = view.findViewById(R.id.txvTitle);
        txvTitle.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "Confirm Voucher Order"));

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_dialog_voucher);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.setNestedScrollingEnabled(false);
        VirtualVoucherProductDialogRecyclerAdapter mDialogAdapter = new VirtualVoucherProductDialogRecyclerAdapter(getViewContext());
        recyclerView.setAdapter(mDialogAdapter);
        mDialogAdapter.clear();
        mDialogAdapter.setVirtualVoucher(mData);

        TextView txvTotalVouchers = view.findViewById(R.id.txvTotalVouchersDialog);
        txvTotalVouchers.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "TOTAL VOUCHERS"));
        TextView txvTotalVouchersValue = view.findViewById(R.id.txvTotalVouchersValueDialog);
        txvTotalVouchersValue.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", qty));

        TextView txvTotaAmount = view.findViewById(R.id.txvTotaAmountDialog);
        txvTotaAmount.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "TOTAL AMOUNT"));
        TextView txvTotalAmountValue = view.findViewById(R.id.txvTotalAmountValueDialog);
        txvTotalAmountValue.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", CommonFunctions.currencyFormatter(total)));

        TextView txvClose = view.findViewById(R.id.txvClose);
        txvClose.setOnClickListener(this);
        txvConfirm = view.findViewById(R.id.txvConfirm);
        txvConfirm.setOnClickListener(this);

        if (MODE.equals(BY_CSB_REWARDS) || MODE.equals(BY_UNO_REWARDS) || MODE.equals(BY_GKEARN)) {
            view.findViewById(R.id.CSB_NOTE).setVisibility(View.VISIBLE);
        } else if(MODE.equals(BY_COOPGETVOUCHER) ||  MODE.equals(BY_GKEARNACTIVATION)) {
            view.findViewById(R.id.CSB_NOTE).setVisibility(View.GONE);
            view.findViewById(R.id.V_REQUEST_NOTE).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.V_REQUEST_NOTE).setVisibility(View.VISIBLE);
        }

    }

    public void calculateVirtualVoucherSummary(List<PrepaidRequest> data) {
        int mQty = 0;
        double mTotal = 0;

        for (PrepaidRequest prepaidRequest : data) {
            mQty = mQty + prepaidRequest.getOrderQuantity();
            mTotal = mTotal + (prepaidRequest.getVoucherDenom() * prepaidRequest.getOrderQuantity());
        }

        mVoucherData.clear();
        mVoucherData.addAll(data);
        mOrderQuantity = mQty;
        mOrderTotal = mTotal;
        txvTotalVouchersValue.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", CommonFunctions.commaFormatter(String.valueOf(mQty))));
        txvTotalAmountValue.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", CommonFunctions.currencyFormatter(String.valueOf(mTotal))));
    }

    private void updateList(List<PrepaidRequest> data) {
        showNoInternetConnection(false);
        mVoucherData.clear();
        mVoucherData.addAll(data);
        prepaidRequest = data;
        if (data.size() > 0) {
            if (mAdapter != null) {
                mAdapter.setVirtualVoucher(data);
            }
        } else {
            if (mAdapter != null) {
                mAdapter.clear();
            }
        }

        checkModeType();
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            if (!isGenerate) {
                mLoaderTimer.cancel();
                mLoaderTimer.start();

                mTvFetching.setText("Fetching vouchers.");
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);

                //getVirtualVoucherProduct(getVirtualVoucherProductSession);
                getVirtualVoucherProductV4();
                // getProcessQueue();
                getProcessQueueV2();
                getProcessQueueV3();
            } else {
                isGenerate = false;
                switch (MODE) {
                    case BY_CSB_REWARDS:
                        showProgressDialog("Processing request.", "Please wait...");
                        redeemCSBRewards();
                        break;
                    case BY_UNO_REWARDS:
                        showProgressDialog("Processing request.", "Please wait...");
                        redeemUNORewards();
                        break;
                    case BY_GKEARN:
                        showProgressDialog("Processing request.", "Please wait...");
                        convertGkEarnPoints();
                        //convertGkEarnPointsV2();
                        break;
                    case BY_COOPGETVOUCHER:
                        setUpProgressLoader();
                        setUpProgressLoaderMessageDialog("Sending request. Please wait...");
                        requestCoopGetVoucherGeneration();
                        break;
                    case BY_GKEARNACTIVATION:
                        showProgressDialog("Processing request.", "Please wait...");
                        requestVoucherGeneration(requestVoucherGenerationSession);
                        break;
                    default:
                        showProgressDialog("Processing request.", "Please wait...");
                        requestVoucherGeneration(requestVoucherGenerationSession);
                        break;
                }
            }
        } else {
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void checkModeType() {
        switch (MODE) {
            case BY_CSB_REWARDS:
                checkVirtualRequestList();
                getCSBRewards();
                break;
            case BY_UNO_REWARDS:
                checkVirtualRequestList();
                getSessionUNOPoints();
                break;
            case BY_GKEARN:
                checkVirtualRequestList();
                getEarnSubscribers();
                break;
            case BY_COOPGETVOUCHER:
                getCoopMemberCredits();
                break;
            case BY_GKEARNACTIVATION:
                checkVirtualRequestList();
                getPaymentPartnersSession();
                break;
            default:
                checkVirtualRequestList();
                getPaymentPartnersSession();
                break;
        }
    }

    private void checkVirtualRequestList() {
        if(prepaidRequest.size() > 0) {
            mainLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        } else {
            mainLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    private void getProcessQueue() {
        Call<V2GetProcessQueueResponse> call = RetrofitBuild.getTransactionsApi(getViewContext())
                .getProcessQueue(
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        sessionid);

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0)
            call.enqueue(processQueueCallback);
        else
            showNoInternetToast();
    }

    private Callback<V2GetProcessQueueResponse> processQueueCallback = new Callback<V2GetProcessQueueResponse>() {
        @Override
        public void onResponse(Call<V2GetProcessQueueResponse> call, Response<V2GetProcessQueueResponse> response) {
            ResponseBody errBody = response.errorBody();
            if (errBody == null) {
                if (response.body().getStatus().equals("000")) {
                    //success
                    mVirtualVoucherRequestList = response.body().getVirtualVoucherRequest();
                    pendingOrderBadge.setText(String.valueOf(mVirtualVoucherRequestList.size()));

                    if (mdb != null) {
                        mdb.truncateTable(mdb, DatabaseHandler.VIRTUAL_VOUCHER_REQUEST);

                        for (V2VirtualVoucherRequestQueue request : mVirtualVoucherRequestList) {
                            mdb.insertVirtualVoucherRequestQueue(mdb, request);
                        }
                    }

                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<V2GetProcessQueueResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    };

    private void redeemCSBRewards() {
        Call<GenericResponse> call = RetrofitBuild.getRewardsAPIService(getViewContext())
                .redeemCSBRewards(
                        imei,
                        userid,
                        authcode,
                        sessionid,
                        borrowerid,
                        borrowermobileno,
                        vouchers,
                        "CSBREDEMPTION"
                );
        call.enqueue(redeemCSBRewardsCallback);
    }

    private Callback<GenericResponse> redeemCSBRewardsCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            txvConfirm.setEnabled(true);

            ResponseBody errBody = response.errorBody();
            if (errBody == null) {
                if (response.body().getStatus().equals("000")) {
                    showRedeemSuccessDialog("Success: CSB Points conversion to voucher.");
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
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
            txvConfirm.setEnabled(true);
        }
    };

    private void showRedeemSuccessDialog(String content) {

        if (MODE.equals(BY_CSB_REWARDS)) {
            new MaterialDialog.Builder(getViewContext())
                    .customView(R.layout.dialog_csb_redeem, false)
                    .cancelable(false)
                    .positiveText("CLOSE")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            Intent intent = new Intent(getViewContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            CommonVariables.VOUCHERISFIRSTLOAD = true;
                            startActivity(intent);
                        }
                    })
                    .show();
        } else if (MODE.equals(BY_GKEARN)) {
            new MaterialDialog.Builder(getViewContext())
                    .customView(R.layout.dialog_csb_redeem, false)
                    .cancelable(false)
                    .positiveText("CLOSE")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            getActivity().finish();
                        }
                    })
                    .show();
        } else {
            new MaterialDialog.Builder(getViewContext())
                    .content(content)
                    .cancelable(false)
                    .positiveText("CLOSE")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            Intent intent = new Intent(getViewContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            CommonVariables.VOUCHERISFIRSTLOAD = true;
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    }

    private void getVirtualVoucherProduct(Callback<GetVirtualVoucherProductResponse> getVirtualVoucherProductCallback) {
        Call<GetVirtualVoucherProductResponse> getvirtualvoucherproduct = RetrofitBuild.getVirtualVoucherProductService(getViewContext())
                .getVirtualVoucherProductCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        servicecode,
                        serviceType,
                        servicefrom
                );
        getvirtualvoucherproduct.enqueue(getVirtualVoucherProductCallback);
    }

    private final Callback<GetVirtualVoucherProductResponse> getVirtualVoucherProductSession = new Callback<GetVirtualVoucherProductResponse>() {

        @Override
        public void onResponse(Call<GetVirtualVoucherProductResponse> call, Response<GetVirtualVoucherProductResponse> response) {
            mLlLoader.setVisibility(View.GONE);

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    updateList(response.body().getPrepaidRequest());
                }  else if (response.body().getStatus().equals("403")) {
                    updateList(response.body().getPrepaidRequest());
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GetVirtualVoucherProductResponse> call, Throwable t) {
            t.printStackTrace();
            t.getLocalizedMessage();
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    };

    private void setUpRequestedVouchers() {
        JSONArray mVouchers = new JSONArray();
        if (!mVoucherData.isEmpty()) {
            for (PrepaidRequest prepaidRequest : mVoucherData) {
                if (prepaidRequest.getOrderQuantity() > 0) {
                    org.json.JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("productid",prepaidRequest.getProductID());
                        jsonObject.put("voucherdenom", String.valueOf(prepaidRequest.getVoucherDenom()));
                        jsonObject.put("quantity", String.valueOf(prepaidRequest.getOrderQuantity()));
                        mVouchers.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Logger.debug("okhttp","DATAAAAAAAAAAAAAAAAA : "+mVouchers.toString());
                }
            }
        }
        vouchers = mVouchers.toString();
    }

    private void requestVoucherGeneration(Callback<RequestVoucherGenerationResponse> requestVoucherGenerationCallback) {
        Call<RequestVoucherGenerationResponse> requestVoucherGeneration = RetrofitBuild.requestVoucherGenerationService(getViewContext())
                .requestVoucherGenerationCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        borrowername,
                        borrowermobileno,
                        vouchers,
                        serviceType);
        requestVoucherGeneration.enqueue(requestVoucherGenerationCallback);
    }

    private final Callback<RequestVoucherGenerationResponse> requestVoucherGenerationSession = new Callback<RequestVoucherGenerationResponse>() {

        @Override
        public void onResponse(Call<RequestVoucherGenerationResponse> call, Response<RequestVoucherGenerationResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mConfirmRequestDialog.dismiss();
            hideProgressDialog();
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    String billingid = response.body().getBillingid();
                    ((VoucherPrepaidRequestActivity) getViewContext()).displayView(1, billingid, String.valueOf(mOrderTotal));
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<RequestVoucherGenerationResponse> call, Throwable t) {
            hideProgressDialog();
            mConfirmRequestDialog.dismiss();
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    public void showSnackBar(String message) {
        Snackbar mySnackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }

    public void disableBtnGenerate() {

        boolean isEnabled = isBtnGenerateEnable();

        if (isEnabled) {
            if (MODE.equals(BY_CSB_REWARDS)) {
                btnGenerate.setBackgroundColor(Color.parseColor("#4F2185"));
                btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "CONVERT TO VOUCHER"));
            } else if (MODE.equals(BY_UNO_REWARDS)) {
                btnGenerate.setBackgroundResource(R.drawable.btn_uno_button_rect);
                btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "CONVERT TO VOUCHER"));
            } else if (MODE.equals(BY_GKEARN)) {
                btnGenerate.setBackgroundColor(getResources().getColor(R.color.color_gkearn_blue));
                btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "CONVERT TO VOUCHER"));
            } else if (MODE.equals(BY_GKEARNACTIVATION)) {
                btnGenerate.setBackgroundColor(getResources().getColor(R.color.color_gkearn_blue));
                btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "GENERATE"));
            }  else if (MODE.equals(BY_COOPGETVOUCHER)) {
                btnGenerate.setBackgroundColor(getResources().getColor(R.color.coopassist_blue));
                btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "CONFIRM"));
            } else {
                btnGenerate.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.bg_btn_accent_pay));
                btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "GENERATE"));
            }
        } else {
            btnGenerate.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.bg_btn_accent_invalid));
            btnGenerate.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "INVALID"));
        }
        btnGenerate.setEnabled(isEnabled);
    }

    private boolean isBtnGenerateEnable() {
        boolean isValid = true;

        for (PrepaidRequest rqst : mVoucherData) {
            isValid = isValid && (rqst.getOrderQuantity() <= rqst.getTotalNumberVoucher());
        }

        return isValid;
    }

    private void fetchCSBRewards() {
        showProgressDialog("Fetching CSB Rewards.", "Please wait...");
        getCSBRewards();
    }

    private void getCSBRewards() {
        Call<GenericResponse> call = RetrofitBuild.getRewardsAPIService(getViewContext())
                .getCSBRewards(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        sessionid,
                        borrowerid,
                        userid
                );

        call.enqueue(getCSBRewardsCallback);
    }

    private Callback<GenericResponse> getCSBRewardsCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            ResponseBody errBody = response.errorBody();
            if (errBody == null) {
                if (response.body().getData().isEmpty()) {

                } else {
                    setTvPoints(response.body().getData());
                    try {
                        mPoints = Double.parseDouble(response.body().getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showNoInternetToast();
        }
    };

    private void setTvPoints(String points) {
        if (serviceType.equals("GKEARN")) {
            tv_points.setText(CommonFunctions.currencyFormatter(points));
        } else {
            tv_points.setText(CommonFunctions.pointsFormatter(points));
        }
    }

    private void getSessionUNOPoints() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("", "Please wait...");
            getUnoMemberPoints();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getUnoMemberPoints() {
        Call<GenericResponse> call = RetrofitBuild.getUnoRewardsAPIService(getViewContext())
                .getUnoMemberPoints(
                        imei,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        userid,
                        borrowerid,
                        sessionid
                );
        call.enqueue(getUnoMemberPointsCallback);
    }

    private Callback<GenericResponse> getUnoMemberPointsCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            try {
                if (response.body().getStatus().equals("000")) {
                    setTvPoints(response.body().getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
        }
    };

    private void redeemUNORewards() {
        Call<GenericResponse> call = RetrofitBuild.getUnoRewardsAPIService(getViewContext())
                .redeemUNORewards(
                        imei,
                        userid,
                        authcode,
                        sessionid,
                        borrowerid,
                        borrowermobileno,
                        borrowername,
                        vouchers,
                        "UNO"
                );
        call.enqueue(redeemUNORewardsCallback);
    }

    private Callback<GenericResponse> redeemUNORewardsCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            txvConfirm.setEnabled(true);

            try {
                if (response.body().getStatus().equals("000")) {
                    mConfirmRequestDialog.dismiss();
                    showRedeemSuccessDialog(response.body().getMessage());
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                hideProgressDialog();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            txvConfirm.setEnabled(true);
            showNoInternetToast();
        }
    };

    private void getPaymentPartners(Callback<GetPaymentPartnersResponse> getPaymentPartnersCallback) {
        Call<GetPaymentPartnersResponse> getPaymentPartners = RetrofitBuild.getPaymentPartnersService(getViewContext())
                .getPaymentPartnersCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        String.valueOf(limit));
        getPaymentPartners.enqueue(getPaymentPartnersCallback);
    }

    private final Callback<GetPaymentPartnersResponse> getPaymentPartnersCallback = new Callback<GetPaymentPartnersResponse>() {

        @Override
        public void onResponse(Call<GetPaymentPartnersResponse> call, Response<GetPaymentPartnersResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    List<PaymentChannels> paymentChannels = response.body().getPaymentChannels();
                    if (paymentChannels.size() > 0) {
                        rv_payment_channels.setVisibility(View.VISIBLE);
                        tv_payment_channels_note.setVisibility(View.VISIBLE);
                        paymentChannelsAdapter.setPaymentsChannelsData(paymentChannels);
                    } else {
                        rv_payment_channels.setVisibility(View.GONE);
                    }
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            }
        }

        @Override
        public void onFailure(Call<GetPaymentPartnersResponse> call, Throwable t) {
            rv_payment_channels.setVisibility(View.GONE);
            showNoInternetToast();
        }
    };

    private final Callback<String> getPaymentPartnersSessionCallBack = new Callback<String>() {

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            String responseData = response.body();
            if (!responseData.isEmpty()) {
                if (responseData.equals("001")) {
                } else if (responseData.equals("error")) {
                } else if (responseData.contains("<!DOCTYPE html>")) {
                } else {
                    sessionid = response.body();
                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
                    getPaymentPartners(getPaymentPartnersCallback);
                }
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            t.printStackTrace();
        }
    };

    private void getPaymentPartnersSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getPaymentPartners(getPaymentPartnersCallback);
            getPaymentPartnersV2();

        } else {
            showNoInternetToast();
        }
    }

    //----------GK EARN-----------
    private void getEarnSubscribers() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Fetching earn subscribers", "Please wait...");
            Call<GKEarnSubscribersResponse> getEarnSubscribers = RetrofitBuild.getgkEarnAPIService(getViewContext())
                    .getEarnSubscribers(
                            sessionid,
                            imei,
                            userid,
                            borrowerid,
                            CommonFunctions.getSha1Hex(imei + userid + sessionid)
                    );

            getEarnSubscribers.enqueue(getEarnSubscribersCallBack);
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private final Callback<GKEarnSubscribersResponse> getEarnSubscribersCallBack = new Callback<GKEarnSubscribersResponse>() {
        @Override
        public void onResponse(Call<GKEarnSubscribersResponse> call, Response<GKEarnSubscribersResponse> response) {
            try {

                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        updateSubscriberList(response.body().getData());
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
        public void onFailure(Call<GKEarnSubscribersResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            hideProgressDialog();
        }
    };

    private void updateSubscriberList(List<GKEarnSubscribers> data) {
        if (data.size() > 0) {
            GKEarnSubscribers gKEarnSubscribers = null;
            for (GKEarnSubscribers subscribers : data) {
                gKEarnSubscribers = subscribers;
            }

            if (gKEarnSubscribers != null) {
                mPoints = gKEarnSubscribers.getTotalPoints();
                setTvPoints(String.valueOf(mPoints));
            } else {
                setTvPoints("0");
            }

        } else {
            setTvPoints("0");
        }
    }

    private void convertGkEarnPoints() {
        Call<GenericResponse> call = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .convertGkEarnPoints(
                        sessionid,
                        imei,
                        userid,
                        authcode,
                        borrowerid,
                        borrowername,
                        borrowermobileno,
                        vouchers,
                        "GKEARN"
                );
        call.enqueue(convertGkEarnPointsCallBack);
    }

    private Callback<GenericResponse> convertGkEarnPointsCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            mConfirmRequestDialog.dismiss();
            txvConfirm.setEnabled(true);

            try {
                if (response.body().getStatus().equals("000")) {
                    showConfirmSuccessDialog(response.body().getMessage());
                } else if (response.body().getStatus().equals("104")) {
                    showAutoLogoutDialog(response.body().getMessage());
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            hideProgressDialog();
            showNoInternetToast();
            txvConfirm.setEnabled(true);
        }
    };

    //------------GET VOUCHER (COOP)----------
    private void getCoopMemberCredits() {
        Call<CoopAssistantMemberCreditsResponse> coopinformation = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .getCoopMemberCredits(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        servicecode
                );

        coopinformation.enqueue(getCoopandMembersInformationCallBack);
    }

    private final Callback<CoopAssistantMemberCreditsResponse> getCoopandMembersInformationCallBack
            = new Callback<CoopAssistantMemberCreditsResponse>() {
        @Override
        public void onResponse(Call<CoopAssistantMemberCreditsResponse> call, Response<CoopAssistantMemberCreditsResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();

                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        coopMemberCreditsList = response.body().getData();
                        setToPaymentOption(coopMemberCreditsList);
                        saveCoopMemberCredits();
                        mainLayout.setVisibility(View.VISIBLE);
                        emptyLayout.setVisibility(View.GONE);
                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else if (response.body().getStatus().equals("105") || response.body().getStatus().equals("107")) {
                        showErrorGlobalDialogs(response.body().getMessage());
                        coopMemberCreditsList = response.body().getData();
                        setToPaymentOption(coopMemberCreditsList);
                        txv_empty_content.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_ITALIC, response.body().getMessage()));
                        saveCoopMemberCredits();
                        mainLayout.setVisibility(View.GONE);
                        emptyLayout.setVisibility(View.VISIBLE);
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
        public void onFailure(Call<CoopAssistantMemberCreditsResponse> call, Throwable t) {
            t.printStackTrace();
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

    private void saveCoopMemberCredits() {
        if(coopMemberCreditsList != null) {
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_GKCOOPMEMBERCREDITS);
            if(coopMemberCreditsList.size() > 0) {
                PreferenceUtils.saveCoopMemberCreditsListPreference(getViewContext(),
                        PreferenceUtils.KEY_GKCOOPMEMBERCREDITS, coopMemberCreditsList);
            }
        }
    }

    private void setToPaymentOption(List<CoopAssistantMemberCredits> data) {
        if (data != null) {
            if (data.size() > 0) {
                List<GKPaymentOptions> paymentOptionsList = new ArrayList<>();

                String name = "";
                String status = "";
                double price = 0.00;


                for (CoopAssistantMemberCredits coopAssistantMemberCredits : data) {
                    name = coopAssistantMemberCredits.getCoopName();
                    status = coopAssistantMemberCredits.getStatus();
                    price = coopAssistantMemberCredits.getAvailableCredits();
                }

                paymentOptionsList.add(new GKPaymentOptions(name, price, status));

                getPaymentOptions(paymentOptionsList);

            } else {
                layout_payment_options.setVisibility(View.GONE);
            }
        } else {
            layout_payment_options.setVisibility(View.GONE);
        }
    }

    private void getPaymentOptions(List<GKPaymentOptions> data) {
        if (data != null) {
            if (data.size() > 0) {
                layout_payment_options.setVisibility(View.VISIBLE);
                rv_paymentoptionsadapter.updateData(data);
                gkPaymentOptionsList = data;
            } else {
                layout_payment_options.setVisibility(View.GONE);
            }
        } else {
            layout_payment_options.setVisibility(View.GONE);
        }
    }

    public void selectPaymentOptions(ArrayList<GKPaymentOptions> data) {
        Collections.reverse(data);
        getSelectedCredits(data);
        passedgkPaymentOptionsList = data;
    }

    private void getSelectedCredits(List<GKPaymentOptions> data) {
        mCredits = 0.00;
        for (GKPaymentOptions gkPaymentOptions : data) {
            mCredits = gkPaymentOptions.getPaymentPrice();
        }
    }

    private void requestCoopGetVoucherGeneration() {
        Call<GenericResponse> requestCoopGetVoucherGeneration = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .requestCoopGetVoucherGeneration(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        vouchers,
                        servicecode,
                        serviceType
                );

        requestCoopGetVoucherGeneration.enqueue(requestCoopGetVoucherGenerationCallBack);
    }

    private final Callback<GenericResponse> requestCoopGetVoucherGenerationCallBack
            = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                setUpProgressLoaderDismissDialog();
                mConfirmRequestDialog.dismiss();
                txvConfirm.setEnabled(true);

                ResponseBody errorBody = response.errorBody();

                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        showConfirmSuccessDialog(response.body().getMessage());
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
                setUpProgressLoaderDismissDialog();
                showErrorGlobalDialogs();
                mConfirmRequestDialog.dismiss();
                txvConfirm.setEnabled(true);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            setUpProgressLoaderDismissDialog();
            showErrorGlobalDialogs();
            mConfirmRequestDialog.dismiss();
            txvConfirm.setEnabled(true);
        }
    };

    //----OTHERS-----
    private void showConfirmSuccessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("SUCCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.SUCCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getActivity().finish();
                if (MODE.equals(BY_GKEARN)) {
                    PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_GKEARNBUYVOUCHER, true);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGenerate: {
                switch (MODE) {
                    case BY_CSB_REWARDS:
                        if (mPoints < mOrderTotal) {
                            showWarningGlobalDialogs("You are converting beyond your CSB Point. You only have " + mPoints + ".");
                        } else {
                            if (mOrderTotal > 0) {
                                setUpRequestedVouchers();
                                setUpConfirmRequestDialog(String.valueOf(mOrderQuantity), String.valueOf(mOrderTotal), mVoucherData);
                                mConfirmRequestDialog.show();
                            } else {
                                showWarningToast("Please select a voucher to convert");
                            }
                        }
                        break;
                    case BY_UNO_REWARDS:
                        if (mPoints < mOrderTotal) {
                            showWarningGlobalDialogs("You are converting beyond your UNO Point. You only have " + CommonFunctions.pointsFormatter(String.valueOf(mPoints)) + ".");
                        } else {
                            if (mOrderTotal > 0) {
                                setUpRequestedVouchers();
                                setUpConfirmRequestDialog(String.valueOf(mOrderQuantity), String.valueOf(mOrderTotal), mVoucherData);
                                mConfirmRequestDialog.show();
                            } else {
                                showWarningToast("Please select a voucher to convert");
                            }
                        }
                        break;
                    case BY_GKEARN:
                        if (mPoints < mOrderTotal) {
                            showWarningGlobalDialogs("You are converting beyond your GK EARN Point. You only have " + CommonFunctions.currencyFormatter(String.valueOf(mPoints)) + ".");
                        } else {
                            if (mOrderTotal > 0) {
                                setUpRequestedVouchers();
                                setUpConfirmRequestDialog(String.valueOf(mOrderQuantity), String.valueOf(mOrderTotal), mVoucherData);
                                mConfirmRequestDialog.show();
                            } else {
                                showWarningToast("Please select a voucher to convert");
                            }
                        }
                        break;
                    case BY_COOPGETVOUCHER:
                        if (passedgkPaymentOptionsList != null) {
                            if (passedgkPaymentOptionsList.size() > 0) {
                                if (mCredits < mOrderTotal) {
                                    showWarningGlobalDialogs("You are converting beyond your coop credits. You only have " + CommonFunctions.currencyFormatter(String.valueOf(mCredits)) + ".");
                                } else {
                                    if (mOrderTotal > 0) {
                                        setUpRequestedVouchers();
                                        setUpConfirmRequestDialog(String.valueOf(mOrderQuantity), String.valueOf(mOrderTotal), mVoucherData);
                                        mConfirmRequestDialog.show();
                                    } else {
                                        showWarningToast("Please select a voucher to convert");
                                    }
                                }
                            } else {
                                showWarningToast("Please select an option.");
                            }
                        } else {
                            showWarningToast("Please select an option.");
                        }

                        break;
                    default:
                        if (mOrderTotal > 0) {

                            PreferenceUtils.removePreference(getViewContext(), "EGHL_ORDER_QUANTITY");
                            PreferenceUtils.removePreference(getViewContext(), "EGHL_ORDER_TOTAL");
                            PreferenceUtils.removePreference(getViewContext(), "EGHL_PREPAID_REQUEST_ARRAYLIST");
                            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_BUYVOUCHER_SERVICETYPE);

                            PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_ORDER_QUANTITY", String.valueOf(mOrderQuantity));
                            PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_ORDER_TOTAL", String.valueOf(mOrderTotal));
                            PreferenceUtils.savePrepaidRequestArrayListPreference(getViewContext(), "EGHL_PREPAID_REQUEST_ARRAYLIST", mVoucherData);
                            PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_BUYVOUCHER_SERVICETYPE, serviceType);

                            Logger.debug("okhttp","VOUCHERS : "+ mVoucherData.toString());

                            Intent intent = new Intent(getViewContext(), VirtualVoucherProductConfirmationActivity.class);
                            //intent.putExtra("TOTAL_QUANTITY", String.valueOf(mOrderQuantity));
                            //intent.putExtra("TOTAL_AMOUNT", String.valueOf(mOrderTotal));
                            //intent.putParcelableArrayListExtra("VOUCHERS", mVoucherData);
                            getActivity().startActivityForResult(intent, 1);

                            //setUpRequestedVouchers();
                            //setUpConfirmRequestDialog(String.valueOf(mOrderQuantity), String.valueOf(mOrderTotal), mVoucherData);
                            //mConfirmRequestDialog.show();
                        } else {
                            showWarningToast("Please select a voucher.");
                        }
                        break;
                }
                break;
            }
            case R.id.txvClose: {
                mConfirmRequestDialog.dismiss();
                break;
            }
            case R.id.txvConfirm: {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                isGenerate = true;

                txvConfirm.setEnabled(false);
                if (!txvConfirm.isEnabled()) {
                    getSession();
                }
                break;
            }
            case R.id.imv_empty_refresh:
            case R.id.refreshnointernet: {
                getSession();
                break;
            }
            case R.id.btnViewPendingOrders: {
                if(MODE.equals(BY_COOPGETVOUCHER)) {
                    CoopAssistantTransactionHistoryActivity.start(getViewContext());
                } else if (MODE.equals(BY_GKEARNACTIVATION)) {
                    GKEarnBuyActivationHistoryActivity.start(getViewContext());
                } else {
                    ViewPendingOrdersActivity.start(getViewContext());
                }

                break;
            }
            case R.id.refresh_points: {
                switch (MODE) {
                    case BY_CSB_REWARDS:
                        fetchCSBRewards();
                        break;
                    case BY_UNO_REWARDS:
                        getSessionUNOPoints();
                        break;
                    case BY_GKEARN:
                        getEarnSubscribers();
                        break;
                    default:
                        getSessionUNOPoints();
                        break;
                }
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        if (mConfirmRequestDialog != null)
            mConfirmRequestDialog.dismiss();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mdb != null) {

            pendingOrderBadge.setText(String.valueOf(mdb.getVirtualVoucherRequestQueue(mdb).size()));

        }
    }

    /*
     * SECURITY UPDATE
     * AS OF
     * JANUARY 2020
     * */
    //GET PAYMENT PARTNERS
    private void getPaymentPartnersV2(){

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("limit", String.valueOf(limit));

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getPaymentPartnersV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getPaymentPartnersV2Object(getPaymentPartnersV2Callback);

        }else{
            rv_payment_channels.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }
    private void getPaymentPartnersV2Object(Callback<GenericResponse> getPaymentPartners){
        Call<GenericResponse> call = RetrofitBuilder.getPaymentV2API(getViewContext())
                .getPaymentPartnersV2(authenticationid,sessionid,param);
        call.enqueue(getPaymentPartners);
    }
    private final Callback<GenericResponse> getPaymentPartnersV2Callback = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData  =  CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<PaymentChannels> paymentChannels = new Gson().fromJson(decryptedData, new TypeToken<List<PaymentChannels>>(){}.getType());
                    if (paymentChannels.size() > 0) {
                        rv_payment_channels.setVisibility(View.VISIBLE);
                        tv_payment_channels_note.setVisibility(View.VISIBLE);
                        paymentChannelsAdapter.setPaymentsChannelsData(paymentChannels);
                    } else {
                        rv_payment_channels.setVisibility(View.GONE);
                    }
                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }
                else {
                    showErrorGlobalDialogs(decryptedMessage);
                }
            }else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            rv_payment_channels.setVisibility(View.GONE);
            showNoInternetToast();
        }
    };

    //GET VIRTUAL VOUCHER PRODUCT
    private void getVirtualVoucherProductV4(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("borrowerid",borrowerid);
            parameters.put("userid",userid);
            parameters.put("devicetype",CommonVariables.devicetype);
            parameters.put("servicecode",servicecode);
            parameters.put("servicetype",serviceType);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            virtualVoucherIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", virtualVoucherIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            virtualVoucherAuthID  = CommonFunctions.parseJSON(jsonString, "authenticationid");
            virtualVoucherKey = CommonFunctions.getSha1Hex(virtualVoucherAuthID + sessionid + "getVirtualVoucherProductsV4");
            virtualVoucherParam = CommonFunctions.encryptAES256CBC(virtualVoucherKey, String.valueOf(paramJson));

            getVirtualVoucherProductV4Object(getVirtualVoucherProductV4Callback);

        }else{
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }
    private void getVirtualVoucherProductV4Object(Callback<GenericResponse> getVirtualVoucherProductCallback) {
        Call<GenericResponse> getvirtualvoucherproduct = RetrofitBuilder.getVoucherV2API(getViewContext())
                .getVirtualVoucherProductV4(
                        virtualVoucherAuthID,sessionid,virtualVoucherParam
                );
        getvirtualvoucherproduct.enqueue(getVirtualVoucherProductCallback);
    }
    private final Callback<GenericResponse> getVirtualVoucherProductV4Callback = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String message = CommonFunctions.decryptAES256CBC(virtualVoucherKey,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String data = CommonFunctions.decryptAES256CBC(virtualVoucherKey,response.body().getData());
                    Logger.debug("okhttp","WSB124 : "+ data);
                    updateList(new Gson().fromJson(data, new TypeToken<List<PrepaidRequest>>(){}.getType()));
                }  else if (response.body().getStatus().equals("403")) {
                    String data = CommonFunctions.decryptAES256CBC(virtualVoucherKey,response.body().getData());
                    updateList(new Gson().fromJson(data, new TypeToken<List<PrepaidRequest>>(){}.getType()));
                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else {
                    showErrorGlobalDialogs(message);
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            t.getLocalizedMessage();
            mLlLoader.setVisibility(View.GONE);
            showErrorToast();
        }
    };

    //GET PROCESS QUEUE
    private void getProcessQueueV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext())  > 0){

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            processQueueIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", processQueueIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            processQueueAuthID = CommonFunctions.parseJSON(jsonString, "authenticationid");
            processQueueKey = CommonFunctions.getSha1Hex(processQueueAuthID + sessionid + "getProcessQueueV3");
            processQueueParam = CommonFunctions.encryptAES256CBC(processQueueKey, String.valueOf(paramJson));

            getProcessQueueObjectV2(processQueueCallbackV2);

        }else{
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }

    }
    private void getProcessQueueObjectV2(Callback<GenericResponse> processQueue) {
        Call<GenericResponse> call = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getProcessQueue(processQueueAuthID,sessionid,processQueueParam);
        call.enqueue(processQueue);
    }
    private Callback<GenericResponse> processQueueCallbackV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(processQueueKey,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String data = CommonFunctions.decryptAES256CBC(processQueueKey,response.body().getData());

                    Logger.debug("okhttp","DATA: "+data);

                    String loaddata = CommonFunctions.parseJSON(data,"loaddata");
                    String billspaydata =  CommonFunctions.parseJSON(data,"billspaydata");
                    String smartreloadqueue = CommonFunctions.parseJSON(data,"smartreloadqueue");
                    String buyvoucherqueue = CommonFunctions.parseJSON(data,"buyvoucherqueue");

                    Logger.debug("okhttp","LOAD DATA: "+loaddata);
                    Logger.debug("okhttp","billspaydata: "+billspaydata);
                    Logger.debug("okhttp","smartreloadqueue: "+smartreloadqueue);
                    Logger.debug("okhttp","buyvoucherqueue: "+buyvoucherqueue);

                    mVirtualVoucherRequestList = new Gson().fromJson(buyvoucherqueue,new TypeToken<List<V2VirtualVoucherRequestQueue>>(){}.getType());

                    //success
                    //mVirtualVoucherRequestList = response.body().getVirtualVoucherRequest();
                    pendingOrderBadge.setText(String.valueOf(mVirtualVoucherRequestList.size()));

                    if (mdb != null) {
                        mdb.truncateTable(mdb, DatabaseHandler.VIRTUAL_VOUCHER_REQUEST);
                        for (V2VirtualVoucherRequestQueue request : mVirtualVoucherRequestList) {
                            mdb.insertVirtualVoucherRequestQueue(mdb, request);
                        }
                    }

                } else {
                    if(response.body().getStatus().equals("error")){
                        showErrorToast();
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else{
                        showErrorToast(decryptedMessage);
                    }
                }
            } else {
                showError(getString(R.string.generic_error_message));
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            showError(getString(R.string.generic_error_message));
        }
    };


    private void getProcessQueueV3() {
        try{

            if(CommonFunctions.getConnectivityStatus(getViewContext())  > 0){

                LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("year",".");
                parameters.put("limit", ".");
                parameters.put("from",".");
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                v2Index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", v2Index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                v2Authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                v2KeyEncryption = CommonFunctions.getSha1Hex(v2Authenticationid + sessionid + "getProcessQueue");
                v2Param = CommonFunctions.encryptAES256CBC(v2KeyEncryption, String.valueOf(paramJson));

                getProcessQueueObjectV2();

            } else {
                mLlLoader.setVisibility(View.GONE);
                showNoInternetToast();
            }

        } catch (Exception e) {
            e.printStackTrace();
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }
    private void getProcessQueueObjectV2() {
        Call<GenericResponse> call = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getProcessQueueV2(v2Authenticationid,sessionid,v2Param);

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            call.enqueue(processQueueCallbackV3);
        }else {
            showError(getString(R.string.generic_internet_error_message));
        }
    }
    private Callback<GenericResponse> processQueueCallbackV3 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(v2KeyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(v2KeyEncryption,response.body().getData());

                    Logger.debug("okhttp","WSB190 : "+data);

                    String loaddata = CommonFunctions.parseJSON(data,"loaddata");
                    String billspaydata =  CommonFunctions.parseJSON(data,"billspaydata");
                    String smartreloadqueue = CommonFunctions.parseJSON(data,"smartreloadqueue");
                    String buyvoucherqueue = CommonFunctions.parseJSON(data,"buyvoucherqueue");


                    if (mdb != null) {
                        mdb.truncateTable(mdb, DatabaseHandler.VIRTUAL_VOUCHER_REQUEST);

                        if(mVirtualVoucherRequestList.size() > 0){

                            //success
                            mVirtualVoucherRequestList.clear();
                            Type type = new TypeToken<List<V2VirtualVoucherRequestQueue>>(){}.getType();
                            mVirtualVoucherRequestList = new Gson().fromJson(buyvoucherqueue,type);
                            pendingOrderBadge.setText(String.valueOf(mVirtualVoucherRequestList.size()));

                            for (V2VirtualVoucherRequestQueue request : mVirtualVoucherRequestList) {
                                mdb.insertVirtualVoucherRequestQueue(mdb, request);
                            }
                        }
                    }

                } else {
                    if(response.body().getStatus().equals("error")){
                        showErrorToast();
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else{
                        showError(decryptedMessage);
                    }
                }
            } else {
                showError(getString(R.string.generic_error_message));
            }
        }
        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            showError(getString(R.string.generic_error_message));
        }
    };


    //convert points
    private void convertGkEarnPointsV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("borrowername",borrowername);
        parameters.put("mobileno",borrowermobileno);
        parameters.put("servicetype","GKEARN");
        parameters.put("vouchers",vouchers);
        parameters.put("devicetype", CommonVariables.devicetype);

        Logger.debug("okhttp","VOUCHERS PARAMS: "+ new Gson().toJson(parameters));

        //depends on the authentication type
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        convertIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", convertIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API
        convertAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        convertKey = CommonFunctions.getSha1Hex(convertAuth + sessionid + "convertGkEarnPointsV2");
        convertParam = CommonFunctions.encryptAES256CBC(convertKey, String.valueOf(paramJson));

        convertGkEarnPointsV2Object( convertGkEarnPointsV2Callback);


    }
    private void convertGkEarnPointsV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .convertGkEarnPointsV2(convertAuth,sessionid,convertParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  convertGkEarnPointsV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            mConfirmRequestDialog.dismiss();
            txvConfirm.setEnabled(true);


            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(convertKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    showConfirmSuccessDialog(message);
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
            t.printStackTrace();
            hideProgressDialog();
            showNoInternetToast();
            txvConfirm.setEnabled(true);
        }
    };

}
