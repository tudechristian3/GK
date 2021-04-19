package com.goodkredit.myapplication.activities.gkearn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.eghl.sdk.EGHL;
import com.eghl.sdk.params.PaymentParams;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.gkearn.stockist.GKEarnStockistPackagesDetailsActivity;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniBillingReferenceActivity;
import com.goodkredit.myapplication.adapter.gkearn.GKEarnPaymentAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.bean.prepaidrequest.EGHLPayment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.decoration.DividerItemDecoration;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.GKPaymentOptions;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.model.gkearn.GKEarnStockistPackage;
import com.goodkredit.myapplication.model.gkearn.GKEarnSubscribers;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetChargeResponse;
import com.goodkredit.myapplication.responses.prepaidrequest.CalculateEGHLServiceChargeResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.NumberTextWatcherForThousand;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKEarnPaymentOptionsActivity extends BaseActivity implements View.OnClickListener {
    //PUBLIC ACTIVITY
    public static GKEarnPaymentOptionsActivity paymentOptions;

    public static final String GKEARN_PAYMENTOPTION_ACTIVITY = "GKEarnPaymentOptionsActivity";

    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String borrowername = "";
    private String borroweremail = "";
    private String borrowermobileno = "";

    private String servicecode = "";
    private String merchantid = "";
    private String merchantname = "";
    private String grossamount = "";

    private GKService gkService;

    //GK NEGOSYO
    private String distributorid = "";
    private String resellerid = "";
    private boolean checkIfReseller = false;

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    private NestedScrollView home_body;

    //LOCATION
    private GPSTracker tracker;
    private String latitude = "";
    private String longitude = "";

    //FROM
    private String strFrom = "";

    //PAYMENT TYPE
    private String strPaymentType = "";

    //PAYMENT OPTION
    private String strPaymentOption = "";

    //PAYMENT OPTIONS
    private List<GKPaymentOptions> gkPaymentOptionsList = new ArrayList<>();
    private LinearLayout payment_container;
    private RecyclerView rv_paymentoptions;
    private GKEarnPaymentAdapter rv_paymentoptionsadapter;
    private ArrayList<GKPaymentOptions> passedgkPaymentOptionsList;
    private LinearLayout layout_payment_options_note;
    private LinearLayout layout_payment_options;

    //PAYMENTS
    private double totalamount = 0.0;
    private double totalamounttopay = 0.0;
    private String strtotalamount = "";
    private double totalamountforpcp = 0.0;

    //VOUCHER SESSION
    private String vouchersession = "";
    private double totalamountcheck = 0.00;

    //CUSTOMER SERVICE CHARGE
    private double customerservicecharge = 0.00;
    private String strcustomerservicecharge = "";

    //DISCOUNT
    private double discount = 0.00;
    private String strdiscount = "";
    private int hasdiscount = 0;
    private String discountmessage = "";
    private MaterialDialog mMaterialDialog = null;

    //EGHL
    private EGHLPayment eghlPayment;
    private EGHL eghl;
    private PaymentParams.Builder params;
    private double eghlservicecharge = 0.00;
    private boolean isEGHLCheckStatus = false;
    private boolean isEGHLCancelPayment = false;
    private int totaldelaytime = 10000;
    private int currentdelaytime = 0;
    private String merchantreferenceno = "";
    private String paymentreferenceno = "";
    private String eghlmessage = "";

    //GK PACKAGES
    private GKEarnStockistPackage gkEarnStockistPackage;
    private String packageID = "";
    private String packageName = "";

    //API ACTION COUNTERS
    private boolean isApplyasStockist = false;
    private boolean isTopUpStockist = false;

    //WITH AMOUNT
    private LinearLayout layout_amount_container;
    private EditText edt_amount;

    //WITH AMOUNT TO PAY
    private LinearLayout layout_amounttopay;
    private TextView txv_amountopay;

    //NO RESULT
    private LinearLayout noresult;
    private TextView txv_noresult;

    //BUTTON ACTION
    private LinearLayout btn_action_container;
    private TextView btn_action;

    //TOPUP
    private String checkamount;

    private String earntype = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gkearn_paymentoptions);

            paymentOptions = this;

            init();

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() {
        home_body = findViewById(R.id.home_body);
        layout_payment_options_note = findViewById(R.id.layout_payment_options_note);
        layout_payment_options = findViewById(R.id.layout_payment_options);

        payment_container = findViewById(R.id.payment_container);
        rv_paymentoptions = findViewById(R.id.rv_paymentoptions);

        layout_amount_container = findViewById(R.id.layout_amount_container);
        edt_amount = findViewById(R.id.edt_amount);
        edt_amount.addTextChangedListener(new NumberTextWatcherForThousand(edt_amount));

        layout_amounttopay = findViewById(R.id.layout_amounttopay);
        txv_amountopay = findViewById(R.id.txv_amountopay);

        noresult = findViewById(R.id.noresult);
        txv_noresult = findViewById(R.id.txv_noresult);

        btn_action_container = findViewById(R.id.btn_action_container);
        btn_action_container.setVisibility(View.VISIBLE);
        btn_action = findViewById(R.id.btn_action);
        btn_action.setText("PROCEED");
        btn_action.setBackgroundColor(ContextCompat.getColor(getViewContext(), R.color.color_gkearn_blue));
        btn_action.setOnClickListener(this);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");

        mdb = new DatabaseHandler(getViewContext());
        V2Subscriber v2Subscriber = mdb.getSubscriber(mdb);

        borrowername = v2Subscriber.getBorrowerName();

        String noresulttext = "NO Payment Scheme Available at the moment. Please try again later.";
        txv_noresult.setText(noresulttext);

        rv_paymentoptions.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_paymentoptions.setNestedScrollingEnabled(false);
        rv_paymentoptions.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getViewContext(), R.drawable.custom_paymentoption_divider)));

        rv_paymentoptionsadapter = new GKEarnPaymentAdapter(getViewContext());
        rv_paymentoptions.setAdapter(rv_paymentoptionsadapter);

        getInformation();
    }

    //GET INFORMATION FOR ALL
    private void getInformation() {
        gkService = PreferenceUtils.getGKServicesPreference(getViewContext(), "GKSERVICE_OBJECT");

        Bundle args = new Bundle();
        args = getIntent().getBundleExtra("args");

        strFrom = args.getString(GKEarnHomeActivity.GKEARN_KEY_FROM);

        if (strFrom.equals(GKEarnStockistPackagesDetailsActivity.KEY_GKEARN_VALUE_PACKAGE)) {
            setupToolbarWithTitle("APPLY AS STOCKIST");
            servicecode = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_GKEARNSTOCKISTSERVICECODE);
            merchantid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_GKEARNSTOCKISTMERCHANTID);
            getEarnSubscriber();
            getPackageOption();
        } else if(strFrom.equals(GKEarnHomeActivity.GKEARN_VALUE_FROMTOPUPBUTTON)){
            setupToolbarWithTitle("TOP-UP");
            layout_amount_container.setVisibility(View.VISIBLE);
            getTopUpPaymentOptions();
        }
    }

    private void getPackageOption() {
        gkEarnStockistPackage = PreferenceUtils.getGKEarnSelectedPackagePreference(getViewContext(), PreferenceUtils.KEY_GKEARNSELECTEDPACKAGE);

        if (gkEarnStockistPackage != null) {
            String paymentoption = gkEarnStockistPackage.getXMLDetails();
            packageID = gkEarnStockistPackage.getPackageID();
            packageName = gkEarnStockistPackage.getPackageName();
            totalamount = gkEarnStockistPackage.getPrice() + gkEarnStockistPackage.getServiceCharge();
            convertXMLtoPaymentList(paymentoption);
        }
    }

    private void getEarnSubscriber() {
        List<GKEarnSubscribers> earnSubscribersList = PreferenceUtils.getGKEarnSubscribersListPreference
                (getViewContext(), GKEarnSubscribers.KEY_GKEARNSUBCRIBERS);
        if (earnSubscribersList != null) {
            if (earnSubscribersList.size() > 0) {
                for (GKEarnSubscribers earnsubscribers : earnSubscribersList) {
                    borrowername = earnsubscribers.getBorrowerName();
                }
            }
        }
    }

    private void getTopUpPaymentOptions() {
        String strPaymentOption = "[{\"PaymentDescription\":\"Payment Channel\",\"PaymentImageUrl\":\"imageurl\",\"PaymentName\":\"PCP\",\"PaymentStatus\":\"ACTIVE\"},{\"PaymentDescription\":\"Credit/Debit Card\",\"PaymentImageUrl\":\"imageurl\",\"PaymentName\":\"EGHL\",\"PaymentStatus\":\"ACTIVE\"}]";

        gkPaymentOptionsList = new Gson().fromJson(strPaymentOption, new TypeToken<ArrayList<GKPaymentOptions>>() {
        }.getType());

        getPaymentOptions();
    }

    //PAYMENT OPTIONS
    private void convertXMLtoPaymentList(String paymentoption) {
        if (!paymentoption.trim().equals("")) {
            paymentoption = CommonFunctions.parseXML(paymentoption, "paymentoption");

            String count = CommonFunctions.parseXML(paymentoption, "count");

            if (!count.equals("") && !count.equals(".") && !count.equals("NONE")) {
                if (Integer.parseInt(count) > 0) {

                    for (int i = 1; i <= Integer.parseInt(count); i++) {
                        String field = CommonFunctions.parseXML(paymentoption, String.valueOf(i));
                        String[] fieldarr = field.split(":::");

                        try {
                            if (!field.equals("NONE") && !field.equals("")) {
                                if (fieldarr.length > 0) {
                                    String name = fieldarr[0];
                                    String description = fieldarr[1];
                                    String imageurl = fieldarr[2];
                                    String status = fieldarr[3];

                                    if (status.equals("ACTIVE")) {
                                        gkPaymentOptionsList.add(new GKPaymentOptions(name, description, imageurl, status));
                                    }
                                }
                            }
                        } catch (Error e) {
                            e.printStackTrace();
                        }
                    }
                }
                getPaymentOptions();
            }
        }
    }

    private void getPaymentOptions() {
        if (gkPaymentOptionsList.size() > 0) {
            layout_payment_options_note.setVisibility(View.VISIBLE);
            layout_payment_options.setVisibility(View.VISIBLE);
            btn_action_container.setVisibility(View.VISIBLE);
            noresult.setVisibility(View.GONE);
            rv_paymentoptionsadapter.updateData(gkPaymentOptionsList);
        } else {
            layout_payment_options_note.setVisibility(View.GONE);
            layout_payment_options.setVisibility(View.GONE);
            btn_action_container.setVisibility(View.GONE);
            noresult.setVisibility(View.VISIBLE);
        }
    }

    public void selectPaymentOptions(ArrayList<GKPaymentOptions> data) {
        Collections.reverse(data);
        passedgkPaymentOptionsList = data;
    }

    private void checkPaymentSelection() {
        try {

            String title = "";
            for (GKPaymentOptions gkPaymentOptions : passedgkPaymentOptionsList) {
                title = gkPaymentOptions.getPaymentName();
            }

            if (title.contains("GKVOUCHER")) {
                strPaymentType = "PAY VIA GK";
            } else if (title.contains("PCP")) {
                strPaymentType = "PAY VIA PARTNER";
            } else if (title.contains("EGHL")) {
                strPaymentType = "PAY VIA CARD";
            }

            if (strPaymentType.contains("CARD")) {
                strPaymentOption = "Visa/Mastercard";
            } else {
                strPaymentOption = ".";
            }

            if (strFrom.equals(GKEarnStockistPackagesDetailsActivity.KEY_GKEARN_VALUE_PACKAGE)) {
                isApplyasStockist = true;

            } else if(strFrom.equals(GKEarnHomeActivity.GKEARN_VALUE_FROMTOPUPBUTTON)){
                isTopUpStockist = true;
                totalamount = Double.parseDouble(checkamount);
            }

            callMainAPI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------API----------------------------------
    private void callMainAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            //APPLY AS STOCKIST
            if (isApplyasStockist) {
                showProgressDialog(getViewContext(), "Processing payment request", "Please wait...");
                checkServiceCharge();
            } else if(isTopUpStockist){
                showProgressDialog(getViewContext(), "Processing payment request", "Please wait...");
                checkServiceCharge();
            }

        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void checkServiceCharge() {
        if (distributorid.equals("") || distributorid.equals(".")
                || resellerid.equals("") || resellerid.equals(".")) {
            if (latitude.equals("") || longitude.equals("")
                    || latitude.equals("null") || longitude.equals("null")) {
                latitude = "0.0";
                longitude = "0.0";
            }
            calculateServiceCharge(calculateServiceChargeCallBack);
        } else {
            checkGPSforDiscount();
            if (latitude.equals("") || longitude.equals("")
                    || latitude.equals("null") || longitude.equals("null")) {
                latitude = "0.0";
                longitude = "0.0";
            }
            calculateServiceCharge(calculateServiceChargeCallBack);
        }
    }

    //LOCATION
    private void checkGPSforDiscount() {
        Double currentlat = null;
        Double currentlon = null;

        tracker = new GPSTracker(getViewContext());

        if (!tracker.canGetLocation()) {
            currentlat = 0.0;
            currentlon = 0.0;
        } else {
            currentlat = tracker.getLatitude();
            currentlon = tracker.getLongitude();
        }
        //DISCOUNT LAT AND LONG
        latitude = String.valueOf(currentlat);
        longitude = String.valueOf(currentlon);
    }

    //---------------------------------CALCULATE CHARGES-------------------------------------
    //CALCULATE CHARGE
    private void calculateServiceCharge(Callback<GetChargeResponse> calculateServiceChargeCallBack) {
        //Limits the two decimal places
        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(totalamount));
        totalamounttopay = Double.parseDouble(valuecheck);

        Call<GetChargeResponse> calculateresponse = RetrofitBuild.getDiscountService(getViewContext())
                .calculateServiceCharge(
                        sessionid,
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        totalamounttopay,
                        servicecode,
                        merchantid
                );

        calculateresponse.enqueue(calculateServiceChargeCallBack);
    }

    private final Callback<GetChargeResponse> calculateServiceChargeCallBack =
            new Callback<GetChargeResponse>() {

                @Override
                public void onResponse(Call<GetChargeResponse> call, Response<GetChargeResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                customerservicecharge = response.body().getCharge();
                                strcustomerservicecharge = String.valueOf(customerservicecharge);
                                strtotalamount = String.valueOf(totalamounttopay + customerservicecharge);
                                hasdiscount = 0;
                                if (strPaymentType.contains("CARD")) {
                                    calculateEGHLServiceCharge(calculateEGHLServiceChargeSession);
                                } else {
                                    showChargeDialogNew();
                                }
                            } else {
                                showErrorGlobalDialogs(response.body().getMessage());
                                hideProgressDialog();
                            }
                        } else {
                            showErrorGlobalDialogs();
                            hideProgressDialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideProgressDialog();
                    }
                }

                @Override
                public void onFailure(Call<GetChargeResponse> call, Throwable t) {
                    showErrorGlobalDialogs();
                    hideProgressDialog();
                    CommonFunctions.hideDialog();
                }
            };

    //CALCULATE EGHL SERVICE CHARGE
    private void calculateEGHLServiceCharge(Callback<CalculateEGHLServiceChargeResponse> calculateEGHLServiceChargeCallback) {
        Call<CalculateEGHLServiceChargeResponse> calculatesc = RetrofitBuild.calculateEGHLServiceChargeService(getViewContext())
                .calculateEGHLServiceChargeCall(sessionid,
                        imei,
                        borrowerid,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        "ANDROID",
                        "Visa/Mastercard",
                        String.valueOf(totalamounttopay));
        calculatesc.enqueue(calculateEGHLServiceChargeCallback);
    }

    private final Callback<CalculateEGHLServiceChargeResponse> calculateEGHLServiceChargeSession = new Callback<CalculateEGHLServiceChargeResponse>() {

        @Override
        public void onResponse(Call<CalculateEGHLServiceChargeResponse> call, Response<CalculateEGHLServiceChargeResponse> response) {
            hideProgressDialog();
            setUpProgressLoaderDismissDialog();

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                    eghlservicecharge = Double.valueOf(response.body().getServiceCharge());
                    strcustomerservicecharge = String.valueOf(customerservicecharge + eghlservicecharge);
                    Double amountcheck = Double.parseDouble(strtotalamount);
                    strtotalamount = String.valueOf(amountcheck + eghlservicecharge);
                    showChargeDialogNew();
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<CalculateEGHLServiceChargeResponse> call, Throwable t) {
            hideProgressDialog();
            setUpProgressLoaderDismissDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    //DIALOG FOR DISCOUNT (NEW)
    private void showChargeDialogNew() {
        GlobalDialogs globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("DISCOUNT", "",
                "CANCEL", "CONFIRM", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.DOUBLETEXTVIEW);

        View closebtn = globalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
            }
        });
        List<String> titleList = new ArrayList<>();

        titleList.add("Amount");
        titleList.add("Service Fee");

        List<String> contentList = new ArrayList<>();

        contentList.add(CommonFunctions.currencyFormatter(String.valueOf(totalamounttopay)));
        contentList.add(CommonFunctions.currencyFormatter(String.valueOf(strcustomerservicecharge)));

        View v = new View(this);
        v.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2
        ));
        v.setBackgroundColor(Color.parseColor("#B3B3B3"));

        List<String> totalList = new ArrayList<>();
        totalList.add(String.valueOf(Html.fromHtml("<b>Total</b>")));

        List<String> totalContentList = new ArrayList<>();
        totalContentList.add(CommonFunctions.currencyFormatter(String.valueOf(Html.fromHtml("<b>" + strtotalamount + "</b>"))));

        LinearLayout linearLayout = globalDialogs.setContentDoubleTextView(
                titleList,
                new GlobalDialogsObject(R.color.color_908F90, 18, Gravity.START),
                contentList,
                new GlobalDialogsObject(R.color.color_23A8F6, 18, Gravity.END)
        );

        linearLayout.addView(v);

        LinearLayout linearLayout1 = globalDialogs.setContentDoubleTextView(
                totalList,
                new GlobalDialogsObject(V2Utils.ROBOTO_BOLD, R.color.color_908F90, 18, Gravity.START),
                totalContentList,
                new GlobalDialogsObject(V2Utils.ROBOTO_BOLD, R.color.color_23A8F6, 18, Gravity.END)
        );

        View btndoubleone = globalDialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();

            }
        });

        View btndoubletwo = globalDialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payNow();
                globalDialogs.dismiss();
            }
        });
    }

    //PAY NOW AFTER VALIDATION
    private void payNow() {
        if (strPaymentType.contains("GK")) {
            prePurchase(prePurchaseSession);
        } else if (strPaymentType.contains("PARTNER")) {
            hideProgressDialog();

            if (vouchersession.trim().equals("")) {
                vouchersession = ".";
            }

            if (strFrom.equals(GKEarnStockistPackagesDetailsActivity.KEY_GKEARN_VALUE_PACKAGE)) {
                showProgressDialog(getViewContext(), "Processing request.", " Please wait...");
                registerGKEarnStockistRequest();
            } else if(strFrom.equals(GKEarnHomeActivity.GKEARN_VALUE_FROMTOPUPBUTTON)){
                showProgressDialog(getViewContext(), "Processing request.", " Please wait...");
                if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
                    topupStockist(topupStockistSession);
                } else{
                    hideProgressDialog();
                    showNoInternetToast();
                }
            }

        } else {
            hideProgressDialog();
            showProgressDialog(getViewContext(), "Processing request.", " Please wait...");
            if (vouchersession.trim().equals("")) {
                vouchersession = ".";
            }
            if (strFrom.equals(GKEarnStockistPackagesDetailsActivity.KEY_GKEARN_VALUE_PACKAGE)) {
                registerGKEarnStockistRequest();
            } else if(strFrom.equals(GKEarnHomeActivity.GKEARN_VALUE_FROMTOPUPBUTTON)){
                if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
                    topupStockist(topupStockistSession);
                } else{
                    hideProgressDialog();
                    showNoInternetToast();
                }
            }
        }
    }

    //PAYMENT VIA GK
    private void prePurchase(Callback<String> prePurchaseCallback) {
        //Limits the two decimal places
        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(strtotalamount));
        strtotalamount = valuecheck;

        Call<String> prepurchase = RetrofitBuild.prePurchaseService(getViewContext())
                .prePurchaseCall(borrowerid,
                        strtotalamount,
                        userid,
                        imei,
                        sessionid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid));
        prepurchase.enqueue(prePurchaseCallback);
    }

    private final Callback<String> prePurchaseSession = new Callback<String>() {

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {

                    vouchersession = response.body();
                    String discountcheckifequal = String.valueOf(discount);
                    String discounttotalamounttopay = String.valueOf(totalamounttopay);

                    if (!vouchersession.isEmpty()) {
                        if (vouchersession.equals("001")) {
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                            showToast("Session: Invalid sessionid.", GlobalToastEnum.ERROR);
                        } else if (vouchersession.equals("error")) {
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.ERROR);
                        } else if (vouchersession.contains("<!DOCTYPE html>")) {
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.ERROR);
                        } else {
                            if (vouchersession.length() > 0) {
                                totalamountcheck = Double.parseDouble(strtotalamount);
                                proceedtoPayments();
                            } else {
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                showWarningGlobalDialogs("Invalid Voucher Session.");
                            }
                        }
                    } else {
                        hideProgressDialog();
                        setUpProgressLoaderDismissDialog();
                        showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            hideProgressDialog();
            setUpProgressLoaderDismissDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
        }
    };

    //PROCEEDING TO PAYMENTS
    private void proceedtoPayments() {
        hideProgressDialog();
        setUpProgressLoaderDismissDialog();

        Intent intent = new Intent(getViewContext(), Payment.class);
        intent.putExtra("GKEARNBORROWERNAME", borrowername);
        intent.putExtra("GKEARNPACKAGEID", packageID);
        intent.putExtra("GKEARNPACKAGENAME", packageName);
        intent.putExtra("GROSSPRICE", String.valueOf(totalamounttopay));
        intent.putExtra("DISCOUNT", strdiscount);
        intent.putExtra("VOUCHERSESSION", vouchersession);
        intent.putExtra("AMOUNT", String.valueOf(totalamountcheck));
        intent.putExtra("GKHASDISCOUNT", hasdiscount);
        intent.putExtra("GKCUSTOMERSERVICECHARGE", strcustomerservicecharge);
        intent.putExtra("FROM", strFrom);
        startActivity(intent);
    }

    //REGISTER EARN STOCKIST REQUEST
    private void registerGKEarnStockistRequest() {
        Call<GenericResponse> registerGKEarnStockistRequest = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .registerGKEarnStockist(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        borrowername,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        vouchersession,
                        merchantid,
                        servicecode,
                        packageID,
                        String.valueOf(totalamounttopay),
                        strcustomerservicecharge,
                        strPaymentType,
                        strPaymentOption
                );

        registerGKEarnStockistRequest.enqueue(registerGKEarnStockistRequestCallBack);
    }

    private final Callback<GenericResponse> registerGKEarnStockistRequestCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        if (strPaymentType.contains("PARTNER")) {
                            String data = response.body().getData();

                            String registrationID = CommonFunctions.parseJSON(data, "MerchantReferenceNo");
                            String receiveamount = CommonFunctions.parseJSON(data, "Amount");

                            Double totalamountcheck = Double.parseDouble(receiveamount);

                            PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, gkService.getServiceName());
                            SchoolMiniBillingReferenceActivity.start(getViewContext(), new SchoolMiniPayment(".",
                                    registrationID, totalamountcheck), receiveamount, GKEARN_PAYMENTOPTION_ACTIVITY);

                        } else if (strPaymentType.contains("CARD")) {
                            String streghlPayment = response.body().getData();

                            eghlPayment = new Gson().fromJson(streghlPayment, new TypeToken<EGHLPayment>() {
                            }.getType());

                            PreferenceUtils.removePreference(getViewContext(), "EGHL_PAYMENT_TXN_NO");
                            PreferenceUtils.removePreference(getViewContext(), "EGHL_ORDER_TXN_NO");

                            PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_PAYMENT_TXN_NO", eghlPayment.getPaymenttxnno());
                            PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_ORDER_TXN_NO", eghlPayment.getOrdertxnno());

                            merchantreferenceno = eghlPayment.getOrdertxnno();
                            paymentreferenceno = eghlPayment.getPaymenttxnno();


                            eghl = EGHL.getInstance();

                            String paymentMethod = "";

                            switch (strPaymentType) {
                                case "BancNet": {
                                    paymentMethod = "DD";
                                    break;
                                }
                                default: {
                                    paymentMethod = "CC";
                                    break;
                                }
                            }

                            params = new PaymentParams.Builder()
                                    .setTriggerReturnURL(true)
                                    .setMerchantCallbackUrl(eghlPayment.getMerchantReturnurl())
                                    .setMerchantReturnUrl(eghlPayment.getMerchantReturnurl())
                                    .setServiceId(eghlPayment.getServiceid())
                                    .setPassword(eghlPayment.getPassword())
                                    .setMerchantName(eghlPayment.getMerchantname())
                                    .setAmount(String.valueOf(strtotalamount))
                                    .setPaymentDesc("GoodKredit Voucher Payment")
                                    .setCustName(borrowername)
                                    .setCustEmail(borroweremail)
                                    .setCustPhone(borrowermobileno)
                                    .setPaymentId(eghlPayment.getPaymenttxnno())
                                    .setOrderNumber(eghlPayment.getOrdertxnno())
                                    .setCurrencyCode("PHP")
                                    .setLanguageCode("EN")
                                    .setPageTimeout("600")
                                    .setTransactionType("SALE")
                                    .setPaymentMethod(paymentMethod)
                                    .setPaymentGateway(eghlPayment.getPaymentgateway());

                            if (strPaymentType.equals("BancNet")) {
                                final String[] url = {
                                        "secure2pay.ghl.com:8443",
                                        "bancnetonline.com"
                                };
                                params.setURlExclusion(url);
                                params.setIssuingBank("BancNet");
                            }
                            Bundle paymentParams = params.build();
                            eghl.executePayment(paymentParams, GKEarnPaymentOptionsActivity.this);
                        }
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
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            hideProgressDialog();
        }
    };

    //TOPUP STOCKIST
    private void topupStockist (Callback<GenericResponse> topupStockistCallback) {
        Call<GenericResponse> topupstockist = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .stockistTopUPCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowername,
                        merchantid,
                        servicecode,
                        CommonFunctions.totalamountlimiter(String.valueOf(totalamount)),
                        strcustomerservicecharge,
                        strPaymentType,
                        strPaymentOption,
                        CommonVariables.devicetype);

        topupstockist.enqueue(topupStockistCallback);
    }

    private final Callback<GenericResponse> topupStockistSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            okhttp3.ResponseBody errorBody = response.errorBody();
            hideProgressDialog();
            if(errorBody == null) {
                if(response.body().getStatus().equals("000")){
                    if (strPaymentType.contains("PARTNER")) {
                        String data = response.body().getData();

                        String registrationID = CommonFunctions.parseJSON(data, "TopTxnNo");
                        String receiveamount = CommonFunctions.parseJSON(data, "Amount");

                        double totalamountcheck = Double.parseDouble(receiveamount);

                        PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, gkService.getServiceName());
                        SchoolMiniBillingReferenceActivity.start(getViewContext(), new SchoolMiniPayment(".",
                                registrationID, totalamountcheck), receiveamount, GKEARN_PAYMENTOPTION_ACTIVITY);

                    } else if (strPaymentType.contains("CARD")) {
                        String streghlPayment = response.body().getData();

                        eghlPayment = new Gson().fromJson(streghlPayment, new TypeToken<EGHLPayment>() {
                        }.getType());

                        PreferenceUtils.removePreference(getViewContext(), "EGHL_PAYMENT_TXN_NO");
                        PreferenceUtils.removePreference(getViewContext(), "EGHL_ORDER_TXN_NO");

                        PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_PAYMENT_TXN_NO", eghlPayment.getPaymenttxnno());
                        PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_ORDER_TXN_NO", eghlPayment.getOrdertxnno());

                        merchantreferenceno = eghlPayment.getOrdertxnno();
                        paymentreferenceno = eghlPayment.getPaymenttxnno();


                        eghl = EGHL.getInstance();

                        String paymentMethod = "";

                        switch (strPaymentType) {
                            case "BancNet": {
                                paymentMethod = "DD";
                                break;
                            }
                            default: {
                                paymentMethod = "CC";
                                break;
                            }
                        }

                        params = new PaymentParams.Builder()
                                .setTriggerReturnURL(true)
                                .setMerchantCallbackUrl(eghlPayment.getMerchantReturnurl())
                                .setMerchantReturnUrl(eghlPayment.getMerchantReturnurl())
                                .setServiceId(eghlPayment.getServiceid())
                                .setPassword(eghlPayment.getPassword())
                                .setMerchantName(eghlPayment.getMerchantname())
                                .setAmount(CommonFunctions.totalamountlimiter(String.valueOf(strtotalamount)))
                                .setPaymentDesc("GoodKredit Voucher Payment")
                                .setCustName(borrowername)
                                .setCustEmail(borroweremail)
                                .setCustPhone(borrowermobileno)
                                .setPaymentId(eghlPayment.getPaymenttxnno())
                                .setOrderNumber(eghlPayment.getOrdertxnno())
                                .setCurrencyCode("PHP")
                                .setLanguageCode("EN")
                                .setPageTimeout("600")
                                .setTransactionType("SALE")
                                .setPaymentMethod(paymentMethod)
                                .setPaymentGateway(eghlPayment.getPaymentgateway());

                        if (strPaymentType.equals("BancNet")) {
                            final String[] url = {
                                    "secure2pay.ghl.com:8443",
                                    "bancnetonline.com"
                            };
                            params.setURlExclusion(url);
                            params.setIssuingBank("BancNet");
                        }

                        Bundle paymentParams = params.build();
                        eghl.executePayment(paymentParams, GKEarnPaymentOptionsActivity.this);
                    }
                } else if(response.body().getStatus().equals("104")){
                    showAutoLogoutDialog(response.body().getMessage());
                } else{
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

    //---------------------------------------VALIDATE EGHL STATUS--------------------------------------------------
    private void validateEGHLTransaction() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            setUpProgressLoader();
            setUpProgressLoaderMessageDialog("Processing request, Please wait...");
            if (isEGHLCheckStatus) {
                checkEarnEGHLTransactions();
            } else if (isEGHLCancelPayment) {
                //ADD CANCEL FOR COOP EGHL HERE.
                if(strFrom.equals(GKEarnStockistPackagesDetailsActivity.KEY_GKEARN_VALUE_PACKAGE)){
                    earntype = "APPLY AS STOCKIST";
                } else {
                    earntype = "TOP UP";
                }
                cancelEarnRequest();
            }
        } else {
            setUpProgressLoaderDismissDialog();
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void checkEarnEGHLTransactions() {
        Call<GenericResponse> checkEGHLTransactions = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .checkEarnEGHLTransactions(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        servicecode,
                        merchantreferenceno,
                        paymentreferenceno,
                        strPaymentType,
                        CommonVariables.devicetype
                );
        checkEGHLTransactions.enqueue(checkEGHLTransactionsCallBack);
    }

    private final Callback<GenericResponse> checkEGHLTransactionsCallBack =
            new Callback<GenericResponse>() {

                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                isEGHLCheckStatus = false;
                                isEGHLCancelPayment = false;
                                currentdelaytime = 0;
                                String message = response.body().getMessage();

                                checkIfReseller = false;

                                if(strFrom.equals(GKEarnStockistPackagesDetailsActivity.KEY_GKEARN_VALUE_PACKAGE)){
                                    showEghlSuccessDialog("You have successfully registered as GK Earn Stockist");
                                } else {
                                    showEghlSuccessDialog(message);
                                }
                            } else if (response.body().getStatus().equals("005")) {
                                if (currentdelaytime < totaldelaytime) {
                                    //check transaction status here
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            currentdelaytime = currentdelaytime + 1000;
                                            isEGHLCheckStatus = true;
                                            validateEGHLTransaction();
                                        }
                                    }, 1000);
                                } else {
                                    isEGHLCheckStatus = false;
                                    isEGHLCancelPayment = false;
                                    currentdelaytime = 0;
                                    hideProgressDialog();
                                    setUpProgressLoaderDismissDialog();
                                    showEghlOnProcessDialog(response.body().getMessage());
                                }
                            } else {
                                isEGHLCheckStatus = false;
                                isEGHLCancelPayment = false;
                                currentdelaytime = 0;
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                showErrorGlobalDialogs(response.body().getMessage());
                            }
                        } else {
                            isEGHLCheckStatus = false;
                            isEGHLCancelPayment = false;
                            currentdelaytime = 0;
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                            showErrorGlobalDialogs();
                        }
                    } catch (Exception e) {
                        isEGHLCheckStatus = false;
                        isEGHLCancelPayment = false;
                        currentdelaytime = 0;
                        hideProgressDialog();
                        setUpProgressLoaderDismissDialog();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    isEGHLCheckStatus = false;
                    isEGHLCancelPayment = false;
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                    showErrorGlobalDialogs();
                }
            };

    private void showEghlSuccessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        boolean isGKNegosyoModal = false;

        dialog.createDialog("SUCCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                isGKNegosyoModal, false, DialogTypeEnum.SUCCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                proceedToGKEarnHome();
            }
        });

    }

    private void proceedToGKEarnHome(){
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(getViewContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        getViewContext().startActivity(intent);
        GKEarnHomeActivity.start(getViewContext(),
                GKEarnHomeActivity.GKEARN_FRAGMENT_REGISTERED_MEMBER,
                null);
    }

    private void showEghlFailedDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("FAILED", message,
                "Retry", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
            }
        });
    }

    private void showEghlTimeOutDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("TIMEOUT", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.TIMEOUT);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
            }
        });
    }

    private void showEghlOnProcessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("ON PROCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.ONPROCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
            }
        });
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
                        merchantreferenceno,
                        eghlmessage,
                        earntype,
                        CommonVariables.devicetype
                );

        cancelEarnRequest.enqueue(cancelEarnRequestCallBack);
    }

    private final Callback<GenericResponse> cancelEarnRequestCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                setUpProgressLoaderDismissDialog();
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        isEGHLCheckStatus = false;
                        isEGHLCancelPayment = false;
                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else {
                        isEGHLCheckStatus = false;
                        isEGHLCancelPayment = false;
                        showErrorGlobalDialogs();
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                setUpProgressLoaderDismissDialog();
                hideProgressDialog();
                isEGHLCheckStatus = false;
                isEGHLCancelPayment = false;
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isEGHLCheckStatus = false;
            isEGHLCancelPayment = false;
            showErrorGlobalDialogs();
            setUpProgressLoaderDismissDialog();
            hideProgressDialog();
        }
    };

    //-----------------------------OTHERS-------------------------------------
    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, GKEarnPaymentOptionsActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    public void returntoHome() {
        PreferenceUtils.removePreference(getViewContext(), "GKServiceCode");
        PreferenceUtils.removePreference(getViewContext(), "GKMerchantID");
        PreferenceUtils.removePreference(getViewContext(), "GKMerchantStatus");

        PreferenceUtils.saveStringPreference(getViewContext(), "GKServiceCode", gkService.getServiceCode());
        PreferenceUtils.saveStringPreference(getViewContext(), "GKMerchantID", gkService.getMerchantID());
        PreferenceUtils.saveStringPreference(getViewContext(), "GKMerchantStatus", gkService.getGKStoreStatus());

        PreferenceUtils.removePreference(getViewContext(), "GKSERVICE_OBJECT");
        PreferenceUtils.saveGKServicesPreference(getViewContext(), "GKSERVICE_OBJECT", gkService);

        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(getViewContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getViewContext().startActivity(intent);

        GKEarnHomeActivity.start(getViewContext(), GKEarnHomeActivity.GKEARN_FRAGMENT_REGISTERED_MEMBER, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EGHL.REQUEST_PAYMENT) {
            switch (resultCode) {
                case EGHL.TRANSACTION_SUCCESS:
                    String status = data.getStringExtra(EGHL.TXN_STATUS);
                    String message = data.getStringExtra(EGHL.TXN_MESSAGE);

                    //check transaction status here
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentdelaytime = currentdelaytime + 1000;
                            isEGHLCheckStatus = true;
                            isEGHLCancelPayment = false;
                            validateEGHLTransaction();
                        }
                    }, 1000);

                    break;
                case EGHL.TRANSACTION_CANCELLED:
                    eghlmessage = "Payment Transaction Cancelled";
                    showEghlFailedDialog(eghlmessage);

                    isEGHLCheckStatus = false;
                    isEGHLCancelPayment = true;
                    validateEGHLTransaction();
                    break;
                case EGHL.TRANSACTION_FAILED:
                    eghlmessage = "Payment Transaction Failed";
                    showEghlFailedDialog(eghlmessage);

                    isEGHLCheckStatus = false;
                    isEGHLCancelPayment = true;
                    validateEGHLTransaction();
                    break;
                default:
                    eghlmessage = "Payment Transaction Failed";
                    showEghlFailedDialog(eghlmessage);

                    isEGHLCheckStatus = false;
                    isEGHLCancelPayment = true;
                    validateEGHLTransaction();
                    break;
            }

        }
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
        finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action: {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                if (passedgkPaymentOptionsList != null) {
                    if (passedgkPaymentOptionsList.size() > 0) {
                        if(strFrom.equals(GKEarnStockistPackagesDetailsActivity.KEY_GKEARN_VALUE_PACKAGE)){
                            checkPaymentSelection();
                        } else if(strFrom.equals(GKEarnHomeActivity.GKEARN_VALUE_FROMTOPUPBUTTON)){
                            checkamount = NumberTextWatcherForThousand.trimCommaOfString(edt_amount.getText().toString().trim());

                            if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                                showToast("Please enter a valid amount.", GlobalToastEnum.WARNING);
                            } else {
                                checkPaymentSelection();
                            }
                        }
                    } else {
                        showToast("Please select an option.", GlobalToastEnum.NOTICE);
                    }
                } else {
                    showToast("Please select an option.", GlobalToastEnum.NOTICE);
                }


                break;
            }

        }
    }
}

