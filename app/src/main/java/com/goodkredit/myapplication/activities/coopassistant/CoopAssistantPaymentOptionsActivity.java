package com.goodkredit.myapplication.activities.coopassistant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.eghl.sdk.EGHL;
import com.eghl.sdk.params.PaymentParams;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.nonmember.CoopAssistantApplyMemberActivity;
import com.goodkredit.myapplication.activities.coopassistant.nonmember.CoopAssistantTermsandConditionsActivity;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniBillingReferenceActivity;
import com.goodkredit.myapplication.adapter.coopassistant.CoopAssistantPaymentOptionsAdapter;
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
import com.goodkredit.myapplication.model.coopassistant.CoopRequestInfo;
import com.goodkredit.myapplication.model.GKPaymentOptions;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantInformation;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantMembershipType;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantAccounts;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantBills;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.responses.DiscountResponse;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetChargeResponse;
import com.goodkredit.myapplication.responses.prepaidrequest.CalculateEGHLServiceChargeResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.NumberTextWatcherForThousand;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantPaymentOptionsActivity extends BaseActivity implements View.OnClickListener {
    //PUBLIC ACTIVITY
    public static CoopAssistantPaymentOptionsActivity coopPaymentOptions;

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

    private GKService gkService;

    //GK NEGOSYO
    private String distributorid = "";
    private String resellerid = "";
    private boolean checkIfReseller = false;

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    private NestedScrollView home_body;
    private LinearLayout layout_payment_options_note;
    private LinearLayout layout_payment_options;
    private ImageView imv_info;

    //COOP INFORMATION
    private List<CoopAssistantInformation> coopInformationList = new ArrayList<>();
    private String coopid = "";
    private String coopname = "";

    //MEMBERS
    private List<CoopAssistantMembers> memberInfoList = new ArrayList<>();
    private String strmembername = "";
    private String strmembermobilenumber = "";
    private String strmemberemailadress = "";
    private String memberreferralname = "";

    //FROM
    private String strFrom = "";

    //PAYMENT TYPE
    private String strPaymentType = "";

    //PAYMENT OPTION
    private String strPaymentOption = "";

    //VALIDATE PENDING TYPE
    private String strValidatePendingType = "";

    //APPLICATION FOR MEMBERSHIP
    private ArrayList<CoopAssistantMembershipType> coopAssistantMembershipList;
    private String strmembershiptype = "";

    //PAYMENT MEMBERSHIP
    private List<CoopRequestInfo> requestInfoList = new ArrayList<>();
    private String strrequestid = "";

    //ACCOUNTS
    private CoopAssistantAccounts coopAssistantAccounts;
    private String accountname = "";

    //LOCATION
    private GPSTracker tracker;
    private String latitude = "";
    private String longitude = "";

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

    //PAYMENT OPTIONS
    private List<GKPaymentOptions> gkPaymentOptionsList = new ArrayList<>();
    private LinearLayout payment_container;
    private RecyclerView rv_paymentoptions;
    private CoopAssistantPaymentOptionsAdapter rv_paymentoptionsadapter;
    private ArrayList<GKPaymentOptions> passedgkPaymentOptionsList;

    //WITH AMOUNT
    private LinearLayout layout_amount_container;
    private EditText edt_amount;

    //WITH AMOUNT TO PAY
    private LinearLayout layout_amounttopay;
    private TextView txv_amountopay;

    //API ACTION COUNTERS
    private boolean isApplyasMember = false;
    private boolean isPayforMember = false;
    private boolean isPayforAccounts = false;
    private boolean isLoanPayment = false;

    //NO RESULT
    private LinearLayout noresult;
    private TextView txv_noresult;

    //BUTTON ACTION
    private LinearLayout btn_action_container;
    private TextView btn_action;

    //BILLS PAYMENT
    private CoopAssistantBills coopAssistantBills = null;
    private String soaid = "";
    private String year = "'";
    private String month = "";
    private Calendar c = Calendar.getInstance();

    private List<CoopAssistantBills> cooplistvalidation = new ArrayList<>();
    private String transactiontype = "";
    private String txnno = "";
    private String amountaftervalidation = "";
    private String remarks = "";


    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String cancelIndex;
    private String cancelAuth;
    private String cancelKey;
    private String cancelParam;

    //for pay soa
    private String payAuth;
    private String payKey;
    private String payParam;
    private String payIndex;
    //PAY MEMBERSHIP
    private String payMemberIndex;
    private String payMemberAuth;
    private String payMemberKey;
    private String payMemberParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_coopassistant_payment_option);

            coopPaymentOptions = this;

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
        btn_action.setText("PAY");
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

        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

        mdb = new DatabaseHandler(getViewContext());

        String noresulttext = "NO Payment Scheme Available at the moment. Please try again later.";
        txv_noresult.setText(noresulttext);

        rv_paymentoptions.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_paymentoptions.setNestedScrollingEnabled(false);
        rv_paymentoptions.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getViewContext(), R.drawable.custom_paymentoption_divider)));

        rv_paymentoptionsadapter = new CoopAssistantPaymentOptionsAdapter(getViewContext());
        rv_paymentoptions.setAdapter(rv_paymentoptionsadapter);

        getInformation();
    }

    //GET INFORMATION FOR ALL
    private void getInformation() {
        gkService = PreferenceUtils.getGKServicesPreference(getViewContext(), "GKSERVICE_OBJECT");

        Bundle args = new Bundle();
        args = getIntent().getBundleExtra("args");

        strFrom = args.getString("FROM");

        getCoopInformation();

        getCoopMembers();

        if (strFrom.equals(CoopAssistantInformation.KEY_COOPTERMS) || strFrom.equals(CoopAssistantInformation.KEY_COOPPMES) || strFrom.equals("ApplicationProfile")) {
            coopAssistantMembershipList = args.getParcelableArrayList("MemberShipSelected");

            getMemberShipType();

            strValidatePendingType = "MEMBERSHIP";
            transactiontype = "MEMBERSHIP";
            setupToolbarWithTitle("Membership Payment");

        } else if (strFrom.equals("MemberShipPayment")) {
            setupToolbarWithTitle("Membership Payment");
            getRequestInfo();
            strValidatePendingType = "MEMBERSHIP";
            transactiontype = "MEMBERSHIP";

        } else if (strFrom.equals("MemberShipAccounts")) {
            setupToolbarWithTitle("Account Payment");
            coopAssistantAccounts = args.getParcelable("CoopAssistantAccounts");
            layout_amount_container.setVisibility(View.VISIBLE);
            getPaymentAccount();
            strValidatePendingType = "MEMBER ACCOUNT";
            transactiontype = "MEMBER ACCOUNT";
        } else if (strFrom.equals("LoanBillsPayment")) {
            setupToolbarWithTitle("Bills Payment");
            coopAssistantBills = args.getParcelable(CoopAssistantBills.KEY_COOPBILLS);
            String inputtedamount = args.getString(CoopAssistantBills.KEY_COOP_BILLS_INPUTTED_AMOUNT);
            totalamount = Double.valueOf(inputtedamount);
            soaid = CommonFunctions.replaceEscapeData(coopAssistantBills.getSOAID());
            coopname = CommonFunctions.replaceEscapeData(coopAssistantBills.getCoopName());

            year = String.valueOf(c.get(Calendar.YEAR));
            month = String.valueOf(c.get(Calendar.MONTH) + 1);

            if (Integer.valueOf(month) < 10) {
                month = "0" + month;
            }

            strValidatePendingType = "BILL";
            transactiontype = "BILL";
        }
    }

    //COOP INFORMATION
    private void getCoopInformation() {
        coopInformationList = PreferenceUtils.getCoopInformationListPreference(getViewContext(), CoopAssistantInformation.KEY_COOPINFORMATION);

        if (coopInformationList != null) {
            if (coopInformationList.size() > 0) {
                String paymentoption = "";

                for (CoopAssistantInformation information : coopInformationList) {
                    coopid = information.getCoopID();
                    coopname = information.getCoopName();

                    paymentoption = information.getXMLDetails();

                }

                convertXMLtoPaymentList(paymentoption);
            }
        }
    }

    private void getCoopMembers() {
        memberInfoList = PreferenceUtils.getCoopMembersListPreference(getViewContext(), CoopAssistantMembers.KEY_COOPMEMBERINFORMATION);

        if (memberInfoList != null) {
            if (memberInfoList.size() > 0) {
                for (CoopAssistantMembers coopAssistantMembers : memberInfoList) {
                    strmembername = coopAssistantMembers.getFirstName() + " " +
                            coopAssistantMembers.getMiddleName() + " " + coopAssistantMembers.getLastName();
                    strmembermobilenumber = coopAssistantMembers.getMobileNumber();
                    strmemberemailadress = coopAssistantMembers.getEmailAddress();
                    memberreferralname = coopAssistantMembers.getReferredByName();
                }
            }
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

    public void selectCoopDetails(ArrayList<GKPaymentOptions> coopdata) {
        Collections.reverse(coopdata);
        passedgkPaymentOptionsList = coopdata;
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

            if (strFrom.equals(CoopAssistantInformation.KEY_COOPTERMS) || strFrom.equals(CoopAssistantInformation.KEY_COOPPMES) || strFrom.equals("ApplicationProfile")) {
                isApplyasMember = true;
                isPayforMember = false;
                isPayforAccounts = false;
                isLoanPayment = false;
                checkIfApplicationForMemberShip();
            } else if (strFrom.equals("MemberShipPayment")) {
                isApplyasMember = false;
                isPayforMember = true;
                isPayforAccounts = false;
                isLoanPayment = false;
                checkIfApplicationForMemberShip();
            } else if (strFrom.equals("MemberShipAccounts")) {
                isApplyasMember = false;
                isPayforMember = false;
                isPayforAccounts = true;
                isLoanPayment = false;

                String checkamount = NumberTextWatcherForThousand.trimCommaOfString(edt_amount.getText().toString().trim());
                if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                    showToast("Please input an amount to proceed", GlobalToastEnum.WARNING);
                } else {
                    totalamount = Double.parseDouble(checkamount);
                    checkIfApplicationForMemberShip();
                }
            } else if (strFrom.equals("LoanBillsPayment")) {
                isApplyasMember = false;
                isPayforMember = false;
                isPayforAccounts = false;
                isLoanPayment = true;
                checkIfApplicationForMemberShip();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //APPLICATION FOR MEMBERSHIP
    private void getMemberShipType() {
        for (CoopAssistantMembershipType coopAssistantMembershipType : coopAssistantMembershipList) {
            strmembershiptype = coopAssistantMembershipType.getName();
        }
    }

    private void checkIfApplicationForMemberShip() {
        if (strPaymentType.contains("CARD")) {
            strPaymentOption = "Visa/Mastercard";
        } else {
            strPaymentOption = ".";
        }

        if (isApplyasMember) {
            proceedtoApplyMemberPage();
        } else {
            callMainAPI();
        }
    }

    private void proceedtoApplyMemberPage() {
        Bundle args = new Bundle();
        args = getIntent().getBundleExtra("args");
        args.putString("PaymentType", strPaymentType);
        args.putString("PaymentOption", strPaymentOption);

        CoopAssistantApplyMemberActivity.start(getViewContext(), args);
    }

    //PAYMENT MEMBERSHIP
    private void getRequestInfo() {
        requestInfoList = PreferenceUtils.getCoopRequestInfoListPreference(getViewContext(), CoopRequestInfo.KEY_COOPREQUESTINFO);

        for (CoopRequestInfo coopRequestInfo : requestInfoList) {
            strPaymentType = coopRequestInfo.getPaymentType();
            totalamount = coopRequestInfo.getAmount();
            strmembername = coopRequestInfo.getMemberName();
            strmembermobilenumber = coopRequestInfo.getMemberMobileNumber();
            strmemberemailadress = coopRequestInfo.getMemberEmailAddress();
            strrequestid = coopRequestInfo.getRequestID();
        }
    }

    //PAYMENT ACCOUNTS
    private void getPaymentAccount() {
        if (coopAssistantAccounts != null) {
            accountname = coopAssistantAccounts.getAccountName();
        }
    }

    //---------------------------------------API----------------------------------
    private void callMainAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            if (isPayforMember || isPayforAccounts) {
                showProgressDialog(getViewContext(), "Processing payment request", "Please wait...");
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
            //PAY LOAN BILLS
            else if (isLoanPayment) {
                showProgressDialog(getViewContext(), "Processing loan payment", "please wait...");

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
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //---------------------------------CALCULATE CHARGES-------------------------------------
    //CALCULATE CHARGE
    private void calculateServiceCharge(Callback<GetChargeResponse> calculateServiceChargeCallBack) {
        //Limits the two decimal places
        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(totalamount));
        totalamounttopay = Double.parseDouble(valuecheck);

        Logger.debug("okhttp", "totalamount ====" + totalamount);
        Logger.debug("okhttp", "totalamounttopay ====" + totalamounttopay);
        Logger.debug("okhttp", "valuecheck ====" + valuecheck);

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

                                Logger.debug("okhttp","CUSTOMER SERVICE CHARGE: "+ customerservicecharge);

                                calculateDiscountForReseller(calculateDiscountForResellerCallBack);
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

    //CALCULATE RESELLER DISCOUNT
    private void calculateDiscountForReseller(Callback<DiscountResponse> calculateDiscountForResellerCallBack) {
        //Limits the two decimal places
        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(totalamount));
        totalamounttopay = Double.parseDouble(valuecheck);

        Call<DiscountResponse> discountresponse = RetrofitBuild.getDiscountService(getViewContext())
                .calculateDiscountForReseller(
                        userid,
                        imei,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        sessionid,
                        borrowerid,
                        merchantid,
                        totalamounttopay,
                        servicecode,
                        latitude,
                        longitude
                );

        discountresponse.enqueue(calculateDiscountForResellerCallBack);
    }

    private final Callback<DiscountResponse> calculateDiscountForResellerCallBack =
            new Callback<DiscountResponse>() {

                @Override
                public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                discount = response.body().getDiscount();

                                strdiscount = String.valueOf(discount);
                                strcustomerservicecharge = String.valueOf(customerservicecharge);

                                //SERVICE CHARGE AND DISCOUNT
                                if (discount <= 0) {
                                    strtotalamount = String.valueOf(totalamounttopay + customerservicecharge);
                                    hasdiscount = 0;
                                    if (strPaymentType.contains("CARD")) {
                                        calculateEGHLServiceCharge(calculateEGHLServiceChargeSession);
                                    } else {
                                        showChargeDialogNew();
                                    }
                                } else {
                                    strtotalamount = String.valueOf((totalamounttopay + customerservicecharge - discount));
                                    if (Double.parseDouble(String.valueOf(totalamounttopay)) > 0) {
                                        hasdiscount = 1;
                                        if (strPaymentType.contains("CARD")) {
                                            calculateEGHLServiceCharge(calculateEGHLServiceChargeSession);
                                        } else {
                                            showChargeDialogNew();
                                        }
                                    }
                                }
                            } else if (response.body().getStatus().equals("005")) {
                                discountmessage = response.body().getMessage();
                                discount = response.body().getDiscount();
                                strdiscount = String.valueOf(discount);
                                strtotalamount = String.valueOf(totalamounttopay);
                                noDiscountResellerServiceArea();
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
                    }
                }

                @Override
                public void onFailure(Call<DiscountResponse> call, Throwable t) {
                    showErrorGlobalDialogs();
                    hideProgressDialog();
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
                    eghlservicecharge = Double.parseDouble(response.body().getServiceCharge());
                    strcustomerservicecharge = String.valueOf(customerservicecharge + eghlservicecharge);
                    double amountcheck = Double.parseDouble(strtotalamount);
                    strtotalamount = String.valueOf(amountcheck + eghlservicecharge);

                    Logger.debug("okhttp","EGHL SERVICE CHARGE: "+ eghlservicecharge);
                    Logger.debug("okhttp","EGHL CUSTOMER SERVICE CHARGE: "+ strcustomerservicecharge);
                    Logger.debug("okhttp","amountcheck: "+ amountcheck);
                    Logger.debug("okhttp","strtotalamount: "+ strtotalamount);

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

    //IF BORROWER IS A RESELLER AND NOT IN SERVICE AREA.
    private void noDiscountResellerServiceArea() {
        mMaterialDialog = new MaterialDialog.Builder(getViewContext())
                .content(discountmessage)
                .cancelable(false)
                .positiveText("OK")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        hasdiscount = 0;
                        if (strPaymentType.contains("CARD")) {
                            calculateEGHLServiceCharge(calculateEGHLServiceChargeSession);
                        } else {
                            showChargeDialogNew();
                        }
                        mMaterialDialog.dismiss();
                        mMaterialDialog = null;
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        mMaterialDialog.dismiss();
                        mMaterialDialog = null;
                        hideProgressDialog();
                        setUpProgressLoaderDismissDialog();
                    }
                })
                .show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mMaterialDialog.getView());
    }

    //------------------------------------------ PAYMENTS -----------------------------------------

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
                                if (discounttotalamounttopay.equals(discountcheckifequal)) {
                                    showWarningGlobalDialogs("Invalid totalamount. Cannot Proceed.");
                                    hideProgressDialog();
                                    setUpProgressLoaderDismissDialog();
                                } else {
                                    totalamountcheck = Double.parseDouble(strtotalamount);
                                    proceedtoPayments();
                                }
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

        intent.putExtra("COOPMEMBERNAME", strmembername);
        intent.putExtra("COOPMEMBERMOBILENO", strmembermobilenumber);
        intent.putExtra("COOPMEMBEREMAILADD", strmemberemailadress);
        intent.putExtra("GROSSPRICE", String.valueOf(totalamounttopay));
        intent.putExtra("DISCOUNT", strdiscount);
        intent.putExtra("VOUCHERSESSION", vouchersession);
        intent.putExtra("AMOUNT", String.valueOf(totalamountcheck));
        intent.putExtra("GKHASDISCOUNT", hasdiscount);
        intent.putExtra("GKCUSTOMERSERVICECHARGE", strcustomerservicecharge);

        if (strFrom.equals("MemberShipPayment")) {
            intent.putExtra("COOPREQUESTID", strrequestid);
        } else if (strFrom.equals("MemberShipAccounts")) {
            intent.putExtra("COOPACCOUNTNAME", accountname);
        } else if (strFrom.equals("LoanBillsPayment")) {
            intent.putExtra(CoopAssistantBills.KEY_COOPBILLS, coopAssistantBills);
        }

        intent.putExtra("FROM", strFrom);

        startActivity(intent);
    }

//    //PAYMENT MEMBERSHIP
//    private void payMembershipRequest() {
//        Call<GenericResponse> payMembershipRequest = RetrofitBuild.getCoopAssistantAPI(getViewContext())
//                .payMembershipRequest(
//                        sessionid,
//                        imei,
//                        userid,
//                        borrowerid,
//                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
//                        servicecode,
//                        vouchersession,
//                        strtotalamount,
//                        merchantid,
//                        strrequestid,
//                        hasdiscount,
//                        String.valueOf(totalamounttopay),
//                        strPaymentType,
//                        strPaymentOption
//                );
//
//        payMembershipRequest.enqueue(payMembershipRequestCallBack);
//    }
//
//    private final Callback<GenericResponse> payMembershipRequestCallBack = new Callback<GenericResponse>() {
//        @Override
//        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
//            try {
//                hideProgressDialog();
//                ResponseBody errorBody = response.errorBody();
//                if (errorBody == null) {
//                    if (response.body().getStatus().equals("000")) {
//                        if (strPaymentType.contains("PARTNER")) {
//                            String data = response.body().getData();
//
//                            String billingid = CommonFunctions.parseJSON(data, "billingid");
//                            String receiveamount = CommonFunctions.parseJSON(data, "amount");
//
//                            Double totalamountcheck = Double.parseDouble(receiveamount);
//
//                            PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, coopname);
//                            SchoolMiniBillingReferenceActivity.start(getViewContext(), new SchoolMiniPayment(".",
//                                    billingid, totalamountcheck), receiveamount, "CoopAssistantPaymentOptionsActivity");
//
//                        } else if (strPaymentType.contains("CARD")) {
//                            String streghlPayment = response.body().getData();
//
//                            eghlPayment = new Gson().fromJson(streghlPayment, new TypeToken<EGHLPayment>() {
//                            }.getType());
//
//                            PreferenceUtils.removePreference(getViewContext(), "EGHL_PAYMENT_TXN_NO");
//                            PreferenceUtils.removePreference(getViewContext(), "EGHL_ORDER_TXN_NO");
//
//                            PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_PAYMENT_TXN_NO", eghlPayment.getPaymenttxnno());
//                            PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_ORDER_TXN_NO", eghlPayment.getOrdertxnno());
//
//                            merchantreferenceno = eghlPayment.getOrdertxnno();
//                            paymentreferenceno = eghlPayment.getPaymenttxnno();
//
//
//                            eghl = EGHL.getInstance();
//
//                            String paymentMethod = "";
//
//                            switch (strPaymentType) {
//                                case "BancNet": {
//                                    paymentMethod = "DD";
//                                    break;
//                                }
//                                default: {
//                                    paymentMethod = "CC";
//                                    break;
//                                }
//                            }
//
//                            params = new PaymentParams.Builder()
//                                    .setTriggerReturnURL(true)
//                                    .setMerchantCallbackUrl(eghlPayment.getMerchantReturnurl())
//                                    .setMerchantReturnUrl(eghlPayment.getMerchantReturnurl())
//                                    .setServiceId(eghlPayment.getServiceid())
//                                    .setPassword(eghlPayment.getPassword())
//                                    .setMerchantName(eghlPayment.getMerchantname())
//                                    .setAmount(String.valueOf(strtotalamount))
//                                    .setPaymentDesc("GoodKredit Voucher Payment")
//                                    .setCustName(borrowername)
//                                    .setCustEmail(borroweremail)
//                                    .setCustPhone(borrowermobileno)
//                                    .setPaymentId(eghlPayment.getPaymenttxnno())
//                                    .setOrderNumber(eghlPayment.getOrdertxnno())
//                                    .setCurrencyCode("PHP")
//                                    .setLanguageCode("EN")
//                                    .setPageTimeout("600")
//                                    .setTransactionType("SALE")
//                                    .setPaymentMethod(paymentMethod)
//                                    .setPaymentGateway(eghlPayment.getPaymentgateway());
//
//                            if (strPaymentType.equals("BancNet")) {
//                                final String[] url = {
//                                        "secure2pay.ghl.com:8443",
//                                        "bancnetonline.com"
//                                };
//                                params.setURlExclusion(url);
//                                params.setIssuingBank("BancNet");
//                            }
//
//                            Bundle paymentParams = params.build();
//                            eghl.executePayment(paymentParams, CoopAssistantPaymentOptionsActivity.this);
//                        }
//                    } else if (response.body().getStatus().equals("104")) {
//                        showAutoLogoutDialog(response.body().getMessage());
//                    } else {
//                        showErrorGlobalDialogs(response.body().getMessage());
//                    }
//                } else {
//                    showErrorGlobalDialogs();
//                }
//            } catch (Exception e) {
//                hideProgressDialog();
//                showErrorGlobalDialogs();
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            showErrorGlobalDialogs();
//            hideProgressDialog();
//        }
//    };

    //PAYMENT ACCOUNTS
//    private void addCoopAccountWallet() {
//
//        Call<GenericResponse> addCoopAccountWallet = RetrofitBuild.getCoopAssistantAPI(getViewContext())
//                .addCoopAccountWallet(
//                        sessionid,
//                        imei,
//                        userid,
//                        borrowerid,
//                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
//                        servicecode,
//                        vouchersession,
//                        strtotalamount,
//                        merchantid,
//                        accountname,
//                        hasdiscount,
//                        String.valueOf(totalamounttopay),
//                        strPaymentType,
//                        strPaymentOption
//                );
//
//        addCoopAccountWallet.enqueue(addCoopAccountWalletRequestCallBack);
//    }
//    private final Callback<GenericResponse> addCoopAccountWalletRequestCallBack = new Callback<GenericResponse>() {
//        @Override
//        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
//            try {
//                hideProgressDialog();
//                ResponseBody errorBody = response.errorBody();
//                if (errorBody == null) {
//                    if (response.body().getStatus().equals("000")) {
//                        if (strPaymentType.contains("PARTNER")) {
//                            String billingid = response.body().getData();
//                            Double totalamountcheck = Double.parseDouble(strtotalamount);
//                            PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, coopname);
//                            SchoolMiniBillingReferenceActivity.start(getViewContext(), new SchoolMiniPayment(
//                                            ".", billingid, totalamountcheck), String.valueOf(strtotalamount),
//                                    "CoopAssistantPaymentOptionsActivity");
//                        } else if (strPaymentType.contains("CARD")) {
//                            String streghlPayment = response.body().getData();
//
//                            eghlPayment = new Gson().fromJson(streghlPayment, new TypeToken<EGHLPayment>() {
//                            }.getType());
//
//                            PreferenceUtils.removePreference(getViewContext(), "EGHL_PAYMENT_TXN_NO");
//                            PreferenceUtils.removePreference(getViewContext(), "EGHL_ORDER_TXN_NO");
//
//                            PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_PAYMENT_TXN_NO", eghlPayment.getPaymenttxnno());
//                            PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_ORDER_TXN_NO", eghlPayment.getOrdertxnno());
//
//                            merchantreferenceno = eghlPayment.getOrdertxnno();
//                            paymentreferenceno = eghlPayment.getPaymenttxnno();
//
//
//                            eghl = EGHL.getInstance();
//
//                            String paymentMethod = "";
//
//                            switch (strPaymentType) {
//                                case "BancNet": {
//                                    paymentMethod = "DD";
//                                    break;
//                                }
//                                default: {
//                                    paymentMethod = "CC";
//                                    break;
//                                }
//                            }
//
//                            params = new PaymentParams.Builder()
//                                    .setTriggerReturnURL(true)
//                                    .setMerchantCallbackUrl(eghlPayment.getMerchantReturnurl())
//                                    .setMerchantReturnUrl(eghlPayment.getMerchantReturnurl())
//                                    .setServiceId(eghlPayment.getServiceid())
//                                    .setPassword(eghlPayment.getPassword())
//                                    .setMerchantName(eghlPayment.getMerchantname())
//                                    .setAmount(String.valueOf(strtotalamount))
//                                    .setPaymentDesc("GoodKredit Voucher Payment")
//                                    .setCustName(borrowername)
//                                    .setCustEmail(borroweremail)
//                                    .setCustPhone(borrowermobileno)
//                                    .setPaymentId(eghlPayment.getPaymenttxnno())
//                                    .setOrderNumber(eghlPayment.getOrdertxnno())
//                                    .setCurrencyCode("PHP")
//                                    .setLanguageCode("EN")
//                                    .setPageTimeout("600")
//                                    .setTransactionType("SALE")
//                                    .setPaymentMethod(paymentMethod)
//                                    .setPaymentGateway(eghlPayment.getPaymentgateway());
//
//                            if (strPaymentType.equals("BancNet")) {
//                                final String[] url = {
//                                        "secure2pay.ghl.com:8443",
//                                        "bancnetonline.com"
//                                };
//                                params.setURlExclusion(url);
//                                params.setIssuingBank("BancNet");
//                            }
//
//                            Bundle paymentParams = params.build();
//                            eghl.executePayment(paymentParams, CoopAssistantPaymentOptionsActivity.this);
//                        }
//                    } else if (response.body().getStatus().equals("104")) {
//                        showAutoLogoutDialog(response.body().getMessage());
//                    } else {
//                        showErrorGlobalDialogs(response.body().getMessage());
//                    }
//                } else {
//                    showErrorGlobalDialogs();
//                }
//            } catch (Exception e) {
//                hideProgressDialog();
//                showErrorGlobalDialogs();
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            showErrorGlobalDialogs();
//            hideProgressDialog();
//        }
//    };

    //LOAN PAYMENT
    private void payLoanBills() {
        Call<GenericResponse> payCoopSOA = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .payCoopSOACall(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        servicecode,
                        vouchersession,
                        merchantid,
                        strtotalamount,
                        soaid,
                        String.valueOf(hasdiscount),
                        String.valueOf(totalamounttopay),
                        strPaymentType,
                        month,
                        year,
                        strPaymentOption,
                        CommonVariables.devicetype
                );

        payCoopSOA.enqueue(payCoopSOASession);
    }

    private final Callback<GenericResponse> payCoopSOASession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {

                        if (strPaymentType.contains("PARTNER")) {

                            String billingid = response.body().getData();

                            PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, coopname);
                            SchoolMiniBillingReferenceActivity.start(getViewContext(), new SchoolMiniPayment(".", billingid, totalamount),
                                    String.valueOf(strtotalamount), "CoopAssistantPaymentOptionsActivity");

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
                            eghl.executePayment(paymentParams, CoopAssistantPaymentOptionsActivity.this);
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

    //PAY NOW AFTER VALIDATION
    private void payNow() {
        if (strPaymentType.contains("GK")) {
            prePurchase(prePurchaseSession);
        } else if (strPaymentType.contains("PARTNER")) {
            hideProgressDialog();

            if (vouchersession.trim().equals("")) {
                vouchersession = ".";
            }

            if (strFrom.equals("MemberShipPayment")) {
                showProgressDialog(getViewContext(), "Processing request", " Please wait...");
                //payMembershipRequest();
                payMembershipRequestV2();
            } else if (strFrom.equals("MemberShipAccounts")) {
                showProgressDialog(getViewContext(), "Processing account request", " Please wait...");
                //addCoopAccountWallet();
                addCoopAccountWalletV2();
            } else if (strFrom.equals("LoanBillsPayment")) {
                showProgressDialog(getViewContext(), "Processing loan payment", "Please wait...");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 3s = 5000ms
                        //payLoanBills();
                        payCoopSOAV2();
                    }
                }, 1000);
            }

        } else {
            hideProgressDialog();
            showProgressDialog(getViewContext(), "Processing request", " Please wait...");
            if (vouchersession.trim().equals("")) {
                vouchersession = ".";
            }
            if (strFrom.equals("MemberShipPayment")) {
                //payMembershipRequest();
                payMembershipRequestV2();
            } else if (strFrom.equals("MemberShipAccounts")) {
                //addCoopAccountWallet();
                addCoopAccountWalletV2();
            } else if (strFrom.equals("LoanBillsPayment")) {
                //payLoanBills();
                payCoopSOAV2();
            }
        }
    }

    //---------------------------------------VALIDATE EGHL STATUS--------------------------------------------------
    private void validateEGHLTransaction() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            setUpProgressLoader();
            setUpProgressLoaderMessageDialog("Processing request, Please wait...");
            if (isEGHLCheckStatus) {
                checkEGHLTransactions();
            } else if (isEGHLCancelPayment) {
                if (strValidatePendingType.equals("MEMBERSHIP")) {
                    cancelCoopEGHLRequest();
                } else {
                    //cancelCoopPaymentChannelRequest();
                    cancelRequestPaymentsV2();
                }
            }
        } else {
            setUpProgressLoaderDismissDialog();
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void checkEGHLTransactions() {
        Call<GenericResponse> checkEGHLTransactions = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .checkEGHLTransactions(
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

                                checkIfReseller = !distributorid.equals("") && !distributorid.equals(".")
                                        && !resellerid.equals("") && !resellerid.equals(".");

                                showEghlSuccessDialog(message);
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

    private void cancelCoopEGHLRequest() {
        Call<GenericResponse> cancelCoopEGHLRequest = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .cancelCoopEGHLRequest(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        servicecode,
                        paymentreferenceno,
                        eghlmessage
                );

        cancelCoopEGHLRequest.enqueue(cancelCoopEGHLRequestCallBack);
    }

    private final Callback<GenericResponse> cancelCoopEGHLRequestCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();

                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        isEGHLCheckStatus = false;
                        isEGHLCancelPayment = false;
                    } else {
                        isEGHLCheckStatus = false;
                        isEGHLCancelPayment = false;
                    }
                } else {
                    isEGHLCheckStatus = false;
                    isEGHLCancelPayment = false;
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();

                isEGHLCheckStatus = false;
                isEGHLCancelPayment = false;
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            setUpProgressLoaderDismissDialog();

            t.printStackTrace();
            t.getLocalizedMessage();

            isEGHLCheckStatus = false;
            isEGHLCancelPayment = false;
            showErrorGlobalDialogs();
        }
    };

//    private void cancelCoopPaymentChannelRequest() {
//        Call<GenericResponse> cancelpayment = RetrofitBuild.getCoopAssistantAPI(getViewContext())
//                .cancelCoopPaymentChannelRequestCall(
//                        sessionid,
//                        imei,
//                        userid,
//                        borrowerid,
//                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
//                        servicecode,
//                        strValidatePendingType,
//                        paymentreferenceno,
//                        eghlmessage,
//                        CommonVariables.devicetype
//                );
//
//        cancelpayment.enqueue(cancelCoopPaymentChannelRequestSession);
//    }
//    private final Callback<GenericResponse> cancelCoopPaymentChannelRequestSession = new Callback<GenericResponse>() {
//        @Override
//        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
//            try {
//                hideProgressDialog();
//                setUpProgressLoaderDismissDialog();
//
//                ResponseBody errorBody = response.errorBody();
//                if (errorBody == null) {
//                    if (response.body().getStatus().equals("000")) {
//                        isEGHLCheckStatus = false;
//                        isEGHLCancelPayment = false;
//                    } else {
//                        isEGHLCheckStatus = false;
//                        isEGHLCancelPayment = false;
//                    }
//                } else {
//                    isEGHLCheckStatus = false;
//                    isEGHLCancelPayment = false;
//                    showErrorGlobalDialogs();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                hideProgressDialog();
//                setUpProgressLoaderDismissDialog();
//                isEGHLCheckStatus = false;
//                isEGHLCancelPayment = false;
//                showErrorGlobalDialogs();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            hideProgressDialog();
//            setUpProgressLoaderDismissDialog();
//            isEGHLCheckStatus = false;
//            isEGHLCancelPayment = false;
//            showErrorGlobalDialogs();
//        }
//    };

    private void showEghlSuccessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        boolean isGKNegosyoModal;
        isGKNegosyoModal = !checkIfReseller;

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
                returntoHomeFragment();
            }
        });

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

    //-----------------------------OTHERS-------------------------------------
    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, CoopAssistantPaymentOptionsActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    public void returntoHomeFragment() {
        PreferenceUtils.removePreference(getViewContext(), "GKServiceCode");
        PreferenceUtils.removePreference(getViewContext(), "GKMerchantID");
        PreferenceUtils.removePreference(getViewContext(), "GKMerchantStatus");

        PreferenceUtils.saveStringPreference(getViewContext(), "GKServiceCode", gkService.getServiceCode());
        PreferenceUtils.saveStringPreference(getViewContext(), "GKMerchantID", gkService.getMerchantID());
        PreferenceUtils.saveStringPreference(getViewContext(), "GKMerchantStatus", gkService.getGKStoreStatus());

        PreferenceUtils.removePreference(getViewContext(), "GKSERVICE_OBJECT");
        PreferenceUtils.saveGKServicesPreference(getViewContext(), "GKSERVICE_OBJECT", gkService);

        CoopAssistantTermsandConditionsActivity coopTermsActivity = CoopAssistantTermsandConditionsActivity.coopAssistantTermsandConditionsActivity;

        if (coopTermsActivity != null) {
            coopTermsActivity.finish();
        }

        finish();

        proceedtoCoopHome();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.debug("checkhttp", "VALUE" + requestCode + resultCode);

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
//                        validateIfHasPendingPaymentRequest();
                        checkPaymentSelection();
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


    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/
    private void addCoopAccountWalletV2() {

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("vouchersession", vouchersession);
        parameters.put("merchantid", merchantid);
        parameters.put("amount", CommonFunctions.totalamountlimiter(strtotalamount));
        parameters.put("accountname", accountname);
        parameters.put("hasdiscount", String.valueOf(hasdiscount));
        parameters.put("grossamount",  String.valueOf(totalamounttopay));
        parameters.put("paymenttype", strPaymentType);
        parameters.put("paymentoption", strPaymentOption);
        parameters.put("devicetype", CommonVariables.devicetype);


        //depends on the authentication type should check it
        LinkedHashMap indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API , always check this
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "addCoopAccountWalletV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        addCoopAccountWalletV2Object(addCoopAccountWalletV2Callback);

    }
    private void addCoopAccountWalletV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .addCoopAccountWalletV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse> addCoopAccountWalletV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    if (strPaymentType.contains("PARTNER")) {
                        String billingid = data;
                        Double totalamountcheck = Double.parseDouble(strtotalamount);
                        PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, coopname);
                        SchoolMiniBillingReferenceActivity.start(getViewContext(), new SchoolMiniPayment(
                                        ".", billingid, totalamountcheck), String.valueOf(strtotalamount),
                                "CoopAssistantPaymentOptionsActivity");
                    } else if (strPaymentType.contains("CARD")) {
                        String streghlPayment = data;

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
                                .setAmount(CommonFunctions.totalamountlimiter(strtotalamount))
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
                        eghl.executePayment(paymentParams, CoopAssistantPaymentOptionsActivity.this);
                    }
                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            } else{
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

    private void cancelRequestPaymentsV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("paymenttype", strValidatePendingType);
        parameters.put("txnno", paymentreferenceno);
        parameters.put("remarks", eghlmessage);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        cancelIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", cancelIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        cancelAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        cancelKey = CommonFunctions.getSha1Hex(cancelAuth + sessionid + "cancelRequestPaymentsV2");
        cancelParam = CommonFunctions.encryptAES256CBC(cancelKey, String.valueOf(paramJson));

        cancelRequestPaymentsV2Object(cancelRequestPaymentsV2Callback);


    }
    private void cancelRequestPaymentsV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .cancelRequestPaymentsV2(cancelAuth,sessionid,cancelParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  cancelRequestPaymentsV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();
            setUpProgressLoaderDismissDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(cancelKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    isEGHLCheckStatus = false;
                    isEGHLCancelPayment = false;
                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    isEGHLCheckStatus = false;
                    isEGHLCancelPayment = false;
                    showErrorGlobalDialogs(message);
                }

            } else{
                isEGHLCheckStatus = false;
                isEGHLCancelPayment = false;
                showErrorGlobalDialogs();
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

    //PAY COOP SOA
    private void payCoopSOAV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("vouchersession", vouchersession);
        parameters.put("merchantid", merchantid);
        parameters.put("amount", CommonFunctions.totalamountlimiter(strtotalamount));
        parameters.put("soaid", soaid);
        parameters.put("hasdiscount", String.valueOf(hasdiscount));
        parameters.put("servicecode", servicecode);
        parameters.put("grossamount", String.valueOf(totalamounttopay));
        parameters.put("paymenttype", strPaymentType);
        parameters.put("paymentoption", strPaymentOption);
        parameters.put("month", month);
        parameters.put("year", year);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        payIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", payIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        payAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        payKey = CommonFunctions.getSha1Hex(payAuth + sessionid + "payCoopSOAV2");
        payParam = CommonFunctions.encryptAES256CBC(payKey, String.valueOf(paramJson));

        payCoopSOAV2Object(payCoopSOAV2Callback);


    }
    private void payCoopSOAV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .payCoopSOAV2(payAuth,sessionid,payParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse> payCoopSOAV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(payKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String data = CommonFunctions.decryptAES256CBC(payKey,response.body().getData());
                    if (strPaymentType.contains("PARTNER")) {

                        String billingid = data;

                        PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, coopname);
                        SchoolMiniBillingReferenceActivity.start(getViewContext(), new SchoolMiniPayment(".", billingid, totalamount),
                                String.valueOf(strtotalamount), "CoopAssistantPaymentOptionsActivity");

                    } else if (strPaymentType.contains("CARD")) {
                        String streghlPayment = data;

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
                                .setAmount(CommonFunctions.totalamountlimiter(strtotalamount))
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
                        eghl.executePayment(paymentParams, CoopAssistantPaymentOptionsActivity.this);
                    }

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


    //PAY MEMBERSHIP V2
    private void payMembershipRequestV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("vouchersession", vouchersession);
        parameters.put("amount", CommonFunctions.totalamountlimiter(strtotalamount));
        parameters.put("merchantid", merchantid);
        parameters.put("requestid", strrequestid);
        parameters.put("hasdiscount", String.valueOf(hasdiscount));
        parameters.put("grossamount", String.valueOf(totalamounttopay));
        parameters.put("paymenttype", strPaymentType);
        parameters.put("paymentoption", strPaymentOption);
        parameters.put("devicetype", CommonVariables.devicetype);


        //depends on the authentication type
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        payMemberIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", payMemberIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API
        payMemberAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        payMemberKey = CommonFunctions.getSha1Hex(payMemberAuth + sessionid + "payMembershipRequestV2");
        payMemberParam = CommonFunctions.encryptAES256CBC(payMemberKey, String.valueOf(paramJson));

        payMembershipRequestV2Object(payMembershipRequestV2Callback);


    }
    private void payMembershipRequestV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .payMembershipRequestV2(payMemberAuth,sessionid,payMemberParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  payMembershipRequestV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(payMemberKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    if (strPaymentType.contains("PARTNER")) {
                        String data = response.body().getData();

                        String billingid = CommonFunctions.parseJSON(data, "billingid");
                        String receiveamount = CommonFunctions.parseJSON(data, "amount");

                        double totalamountcheck = Double.parseDouble(receiveamount);

                        PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, coopname);
                        SchoolMiniBillingReferenceActivity.start(getViewContext(), new SchoolMiniPayment(".",
                                billingid, totalamountcheck), receiveamount, "CoopAssistantPaymentOptionsActivity");

                    } else if (strPaymentType.contains("CARD")) {

                        String streghlPayment = CommonFunctions.decryptAES256CBC(payMemberKey,response.body().getData());

                        Logger.debug("ronel","EGHL: "+streghlPayment);

                        eghlPayment = new Gson().fromJson(streghlPayment,EGHLPayment.class);

                        PreferenceUtils.removePreference(getViewContext(), "EGHL_PAYMENT_TXN_NO");
                        PreferenceUtils.removePreference(getViewContext(), "EGHL_ORDER_TXN_NO");

                        PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_PAYMENT_TXN_NO", eghlPayment.getPaymenttxnno());
                        PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_ORDER_TXN_NO", eghlPayment.getOrdertxnno());

                        merchantreferenceno = eghlPayment.getOrdertxnno();
                        paymentreferenceno = eghlPayment.getPaymenttxnno();

                        eghl = EGHL.getInstance();

                        String paymentMethod = "";

                        if ("BancNet".equals(strPaymentType)) {
                            paymentMethod = "DD";
                        } else {
                            paymentMethod = "CC";
                        }

                        params = new PaymentParams.Builder()
                                .setTriggerReturnURL(true)
                                .setMerchantCallbackUrl(eghlPayment.getMerchantReturnurl())
                                .setMerchantReturnUrl(eghlPayment.getMerchantReturnurl())
                                .setServiceId(eghlPayment.getServiceid())
                                .setPassword(eghlPayment.getPassword())
                                .setMerchantName(eghlPayment.getMerchantname())
                                .setAmount(CommonFunctions.totalamountlimiter(strtotalamount))
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
                        eghl.executePayment(paymentParams, CoopAssistantPaymentOptionsActivity.this);
                    }

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
            showErrorGlobalDialogs();
            hideProgressDialog();
            t.printStackTrace();
        }
    };



}
