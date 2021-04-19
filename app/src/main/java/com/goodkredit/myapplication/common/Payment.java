package com.goodkredit.myapplication.common;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.billspayment.BillsPaymentConfirmationActivity;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantConfirmationActivity;
import com.goodkredit.myapplication.activities.dropoff.PaymentRequestConfirmationActivity;
import com.goodkredit.myapplication.activities.egame.EGameConfirmationActivity;
import com.goodkredit.myapplication.activities.gkearn.GKEarnConfirmationActivity;
import com.goodkredit.myapplication.activities.gkearn.GKEarnRegistrationConfirmationActivity;
import com.goodkredit.myapplication.activities.gkearn.stockist.GKEarnStockistPackagesDetailsActivity;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoPaymentConfirmationActivity;
import com.goodkredit.myapplication.activities.gkservices.medpadala.MedPadalaConfirmationActivity;
import com.goodkredit.myapplication.activities.gkstore.GKStoreConfirmationActivity;
import com.goodkredit.myapplication.activities.paramount.ParamountInsuranceConfirmationActivity;
import com.goodkredit.myapplication.activities.projectcoop.ProjectCoopActivity;
import com.goodkredit.myapplication.activities.reloadretailer.RetailerLoadConfirmationActivity;
import com.goodkredit.myapplication.activities.vouchers.rfid.RFIDConfirmationActivity;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniConfirmationActivity;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.activities.voting.VotesConfirmationActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.bean.Merchant;
import com.goodkredit.myapplication.bean.SkycablePPVSubscription;
import com.goodkredit.myapplication.bean.SkycableRegistration;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.bean.projectcoop.GameProduct;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.GKNegosyPackage;
import com.goodkredit.myapplication.model.GKNegosyoApplicationStatus;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.model.ValidatedVoucher;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantBills;
import com.goodkredit.myapplication.model.dropoff.PaymentRequest;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetVoucherResponse;
import com.goodkredit.myapplication.responses.ValidateVoucherResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Payment extends BaseActivity {

    DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;

    //COMMON
    private String sessionid = "";
    private String authcode = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";


    private RecyclerView horizontal_recycler_payment, horizontal_recycler_view;

    //First load list
    private AvailableVouchersAdapter availableVouchersAdapter;
    private ArrayList<Voucher> availableVouchersList;

    //Payment List
    private PaymentVouchersAdapter paymentVouchersAdapter;
    private ArrayList<Voucher> paymentVouchersList;
    TextView droparea;
    RelativeLayout dropareawrap;
    TextView dropareatext;
    TextView amountopayval;
    TextView amounttenval;
    double remaingbalance = 0.0;
    double amounttopay = 0.0;
    double totalamountpaid = 0.0;
    private String mobileval = "";
    private String networkval = "";
    private String producttypeval = "";
    private String productcodeval = "";
    private String amountopayvalue = "";
    private String vouchercodeval = "";
    private String voucherpinval = "";
    private String vouchersession = "";
    private String purchasestatus = "";
    private String source = "";
    private DecimalFormat formatter;
    private AQuery aq;
    int currentpossition = 0;
    static String PROCESS = "";
    private Merchant merchant;
    private RelativeLayout overlay;
    private TextView mTvEmpty;
    int currentlimit = 0;
    private Gson gson;

    private String spbillercode = "";
    private String billerparamdata = "";
    private String billername = "";
    private String servicecharge = "0";
    private String othercharges = "0";
    private String discount = "0";
    private String receiptmobileno = "";
    private String receiptemailadd = "";

    //GKSTORE
    private String firstname = "";
    private String lastname = "";
    private String mobileno = "";
    private String emailadd = "";
    private String customeradd = "";
    private String customerotherdetails = "";
    private String orderdetails = "";
    private String merchantid = "";
    private String storeid = "";
    private String latitude = "";
    private String longitude = "";
    private String borrowername = "";
    private String strgkstoredeliverytype = "";
    private String strmerchantlat = "";
    private String strmerchantlong = "";
    private String strmerchantaddress = "";
    private String strservicecharge = "";
    private String remarks = "";
    private String amount = "";
    private String grossprice = "";
    private String servicecode = "";
    private int hasdiscount = 0;

    //SCHOOLMINI
    private String studentid = "";
    private String studentcourse = "";
    private String studentyearlevel = "";
    private String studentmiddlename = "";
    private String studentmobilenumber = "";
    private String studentemailaddress = "";
    private String studenttuitionfee = "";
    private String schoolyear = "";
    private String semester = "";
    private String semestralfee = "";
    private String soaid = "";
    private String studentmerchantreferenceno = "";
    private String mPaymentType = "";
    private String strcustomerservicecharge = "";
    private String school_remarks = "";

    //SKYCABLE
    private SkycableRegistration skycableRegistration = null;
    private SkycablePPVSubscription skycablePPVSubscription = null;
    private String skycableamount = "";
    private String skycableservicecharge = "";

    //PROJECTCOOP
    private GameProduct gameProduct = null;
    private String coopAmount;
    private String coopServiceCharge;
    private String winningCombination = "";
    private String winningName = "";
    private String winningAccountNumber = "";

    public static Payment paymentfinishActivity;

    //PARAMOUNT
    private String vehicleClassificationCode = "";
    private boolean isNewApplication = false;
    private boolean isPPV = false;

    private GKService mGKService;
    private GKNegosyPackage mGKNegosyoPackage;
    private String mDistributorID;
    private String mDistributorName;
    private GKNegosyoApplicationStatus mGKNegosyoApplicationStatus;

    //DROPOFF
    private PaymentRequest mPaymentRequest;

    private double resellerDiscount = 0;
    private String resellerAmount = "0";

    private String guarantorid;

    //VOTES
    private String eventid = "";
    private String eventname = "";
    private String participantid = "";
    private int participantnumber = 0;
    private String participantname = "";

    //COOP ASSIST

    //PAYMENT MEMBERSHIP
    private String membername = "";
    private String membermobileno = "";
    private String memberemailadd = "";
    private String requestid = "";
    private String memberaccountname = "";

    private String strfrom = "";

    //LOAN PAYMENT
    private CoopAssistantBills coopAssistantBills = null;

    //GK EARN
    private String packageID = "";
    private String packageName = "";
    private String gkearnreferralcode = "";

    //RFID
    private String rfidNumber = "";
    private String rfidCardNumber = "";
    private String rfidvouchercode = "";
    private String rfidvoucherserialno = "";

    //VARIABLE FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String validateVoucherIndex;
    private String validateVoucherAuthenticationid;
    private String validateVoucherKeyEncryption;
    private String validateVoucherParam;

    private String deleteIndex;
    private String deleteAuthenticationid;
    private String deleteKeyEncryption;
    private String deleteParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airtime_payment1);
        try {

            //VERSION 2
            imei = PreferenceUtils.getImeiId(getViewContext());
            userid = PreferenceUtils.getUserId(getViewContext());
            borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
            //SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            //For finish an activiity
            paymentfinishActivity = this;

            overlay = findViewById(R.id.overlay);
            overlay.requestFocus();
            final Handler overlayFadeOut = new Handler();
            overlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    overlay.setVisibility(View.GONE);
                }
            });

            overlayFadeOut.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (overlay.getVisibility() == View.VISIBLE) {
                        Animation fadeOut = new AlphaAnimation(1, 0);
                        fadeOut.setInterpolator(new AccelerateInterpolator());
                        fadeOut.setDuration(1000);

                        fadeOut.setAnimationListener(new Animation.AnimationListener() {
                            public void onAnimationEnd(Animation animation) {
                                overlay.setVisibility(View.GONE);
                            }

                            public void onAnimationRepeat(Animation animation) {
                            }

                            public void onAnimationStart(Animation animation) {
                            }
                        });
                        overlay.startAnimation(fadeOut);
                    }
                }
            }, 5000);

            //set context
            mcontext = this;
            //intialize db
            db = new DatabaseHandler(this);
            //image loader
            aq = new AQuery(mcontext);

            gson = new Gson();

            V2Subscriber v2Subscriber = db.getSubscriber(db);
            guarantorid = v2Subscriber.getGuarantorID();

            //set toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_close_actionbar_icon);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //cancelPaymetTransactionDialog();
                    cancelPaymetTransactionDialogNew();
                }
            });
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            //initialize recycler view
            horizontal_recycler_view = findViewById(R.id.horizontal_recycler_view);
            horizontal_recycler_payment = findViewById(R.id.horizontal_recycler_view2);

            //initialize elements
            droparea = findViewById(R.id.droparea);
            dropareawrap = findViewById(R.id.dropareawrap);
            dropareatext = findViewById(R.id.dropareatext);
            amountopayval = findViewById(R.id.amountopayval);
            amounttenval = findViewById(R.id.amounttenval);
            mTvEmpty = findViewById(R.id.emptyvoucherPayment);

//            txvChangeValue = (TextView) findViewById(R.id.txvChangeValue);

            //Initialize first load of data
            availableVouchersList = new ArrayList<>();
            availableVouchersList = db.getAllVouchers(db);

            //if (availableVouchersList.size() == 0) {
            getSession();
            //}

            //initialize for payments
            paymentVouchersList = new ArrayList<>();
            formatter = new DecimalFormat("#,###,##0.00");

            //initialize adapter
            availableVouchersAdapter = new AvailableVouchersAdapter(availableVouchersList);
            paymentVouchersAdapter = new PaymentVouchersAdapter(paymentVouchersList);

            //Initialize Recycler view
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);

            LinearLayoutManager horizontalLayoutManagaer1
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

            horizontal_recycler_payment.setLayoutManager(horizontalLayoutManagaer1);

            //Get the passed Value
            Bundle b = getIntent().getExtras();

            if (getIntent().hasExtra("FROMMERCHANTEXPRESS")) {
                Intent intent = getIntent();
                mobileval = intent.getStringExtra("MOBILE");
                vouchersession = intent.getStringExtra("VOUCHERSESSION");
                amountopayvalue = intent.getStringExtra("AMOUNT");
                source = intent.getStringExtra("FROMMERCHANTEXPRESS");
                merchant = (Merchant) intent.getSerializableExtra("MERCHANT");
                resellerDiscount = Double.valueOf(intent.getStringExtra("PAYVIAQRRESELLERDISCOUNT"));
                resellerAmount = intent.getStringExtra("GROSSAMOUNT");

            } else if (getIntent().hasExtra("BILLERPARAMDATA")) {
                Intent intent = getIntent();
                source = "BILLSPAYMENT";
                amountopayvalue = intent.getStringExtra("AMOUNT");
                spbillercode = intent.getStringExtra("SPBILLERCODE");
                billerparamdata = intent.getStringExtra("BILLERPARAMDATA");
                vouchersession = intent.getStringExtra("VOUCHERSESSION");
                billername = intent.getStringExtra("BILLERNAME");
                servicecharge = intent.getStringExtra("SERVICECHARGE");
                othercharges = intent.getStringExtra("OTHERCHARGES");
                isNewApplication = intent.getBooleanExtra("NEWAPPLICATION", false);
                isPPV = intent.getBooleanExtra("PPV", false);
                discount = b.getString("DISCOUNT");
                grossprice = b.getString("GROSSPRICE");
                servicecode = b.getString("GKSERVICECODE");
                hasdiscount = b.getInt("GKHASDISCOUNT");
                receiptmobileno = b.getString("RECEIPTMOBILENO");
                receiptemailadd = b.getString("RECEIPTEMAILADD");
                latitude = b.getString("LATITUDE");
                longitude = b.getString("LONGITUDE");

            } else if (getIntent().hasExtra("FROMRETAILERLOADING")) {
                source = "RETAILERLOADING";
                mobileval = b.getString("MOBILE");
                networkval = b.getString("NETWORK");
                producttypeval = b.getString("PRODUCTTYPE");
                productcodeval = b.getString("PRODUCTCODE");
                vouchersession = b.getString("VOUCHERSESSION");
                amountopayvalue = b.getString("AMOUNT");
                discount = b.getString("DISCOUNT");
                grossprice = b.getString("GROSSPRICE");
                servicecode = b.getString("GKSERVICECODE");
                hasdiscount = b.getInt("GKHASDISCOUNT");
                latitude = b.getString("LATITUDE");
                longitude = b.getString("LONGITUDE");

            } else if (getIntent().hasExtra("STUDENTID")) {
                source = "SCHOOLMINI";
                merchantid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTID);
                studentid = b.getString("STUDENTID");
                studentcourse = b.getString("COURSE");
                studentyearlevel = b.getString("YEARLEVEL");
                firstname = b.getString("FIRSTNAME");
                studentmiddlename = b.getString("MIDDLENAME");
                lastname = b.getString("LASTNAME");
                mobileno = b.getString("MOBILENUMBER");
                emailadd = b.getString("EMAILADDRESS");
                schoolyear = b.getString("SCHOOLYEAR");
                semester = b.getString("SEMESTER");
                semestralfee = b.getString("SEMESTRALFEE");
                soaid = b.getString("SOAID");
                studentmerchantreferenceno = b.getString("MERCHANTREFERENCENO");
                grossprice = b.getString("GROSSPRICE");
                discount = b.getString("DISCOUNT");
                vouchersession = b.getString("VOUCHERSESSION");
                amountopayvalue = b.getString("AMOUNT");
                hasdiscount = b.getInt("GKHASDISCOUNT");
                mPaymentType = b.getString("GKMPAYMENTTYPE");
                strcustomerservicecharge = b.getString("GKCUSTOMERSERVICECHARGE");
                school_remarks = b.getString("REMARKS");

            } else if (getIntent().hasExtra("PARTICIPANTID")) {
                source = "VOTES";
                eventid = b.getString("EVENTID");
                eventname = b.getString("EVENTNAME");
                participantid = b.getString("PARTICIPANTID");
                servicecode = b.getString("SERVICECODE");
                participantnumber = b.getInt("PARTICIPANTNUMBER");
                participantname = b.getString("PARTICIPANTNAME");
                grossprice = b.getString("GROSSPRICE");
                discount = b.getString("DISCOUNT");
                vouchersession = b.getString("VOUCHERSESSION");
                amountopayvalue = b.getString("AMOUNT");
                hasdiscount = b.getInt("GKHASDISCOUNT");
                strcustomerservicecharge = b.getString("GKCUSTOMERSERVICECHARGE");

            } else if (getIntent().hasExtra("COOPMEMBERNAME")) {
                source = "COOPASSIST";
                membername = b.getString("COOPMEMBERNAME");
                membermobileno = b.getString("COOPMEMBERMOBILENO");
                memberemailadd = b.getString("COOPMEMBEREMAILADD");
                memberaccountname = b.getString("COOPACCOUNTNAME");
                requestid = b.getString("COOPREQUESTID");
                grossprice = b.getString("GROSSPRICE");
                discount = b.getString("DISCOUNT");
                vouchersession = b.getString("VOUCHERSESSION");
                amountopayvalue = b.getString("AMOUNT");
                hasdiscount = b.getInt("GKHASDISCOUNT");
                strcustomerservicecharge = b.getString("GKCUSTOMERSERVICECHARGE");
                strfrom = b.getString("FROM");
                coopAssistantBills = b.getParcelable(CoopAssistantBills.KEY_COOPBILLS);

            } else if (getIntent().hasExtra("GKEARNBORROWERNAME")) {
                source = "GKEARN";
                borrowername = b.getString("GKEARNBORROWERNAME");
                packageID = b.getString("GKEARNPACKAGEID");
                packageName = b.getString("GKEARNPACKAGENAME");
                grossprice = b.getString("GROSSPRICE");
                discount = b.getString("DISCOUNT");
                vouchersession = b.getString("VOUCHERSESSION");
                amountopayvalue = b.getString("AMOUNT");
                hasdiscount = b.getInt("GKHASDISCOUNT");
                strcustomerservicecharge = b.getString("GKCUSTOMERSERVICECHARGE");
                strfrom = b.getString("FROM");
            } else if ((getIntent().hasExtra("RFIDBORROWERNAME"))){
                source = "RFID";
                borrowername = b.getString("RFIDBORROWERNAME");
                rfidNumber = b.getString("RFIDNUMBER");
                rfidCardNumber = b.getString("RFIDCARDNUMBER");
                rfidvouchercode = b.getString("RFIDVOUCHERCODE");
                rfidvoucherserialno = b.getString("RFIDVOUCHERSERIAL");
                grossprice = b.getString("GROSSPRICE");
                discount = b.getString("DISCOUNT");
                vouchersession = b.getString("VOUCHERSESSION");
                amountopayvalue = b.getString("AMOUNT");
                hasdiscount = b.getInt("GKHASDISCOUNT");
                strcustomerservicecharge = b.getString("GKCUSTOMERSERVICECHARGE");
                strfrom = b.getString("FROM");
            } else if (getIntent().hasExtra("GKSTOREFIRSTNAME")) {
                source = "GKSTOREDETAILS";
                firstname = b.getString("GKSTOREFIRSTNAME");
                lastname = b.getString("GKSTORELASTNAME");
                mobileno = b.getString("GKSTOREMOBILENO");
                emailadd = b.getString("GKSTOREEMAILADD");
                customeradd = b.getString("GKSTORECUSTOMERADD");
                vouchersession = b.getString("VOUCHERSESSION");
                amountopayvalue = b.getString("AMOUNT");
                discount = b.getString("DISCOUNT");
                customerotherdetails = b.getString("CUSTOMEROTHERDETAILS");
                orderdetails = b.getString("ORDERDETAILS");
                merchantid = b.getString("MERCHANTID");
                storeid = b.getString("GKSTOREID");
                latitude = b.getString("LATITUDE");
                longitude = b.getString("LONGITUDE");
                borrowername = b.getString("GKSTOREBORROWERNAME");
                strgkstoredeliverytype = b.getString("GKSTOREDELIVERYTYPE");
                strmerchantlat = b.getString("GKSTOREMERCHANTLAT");
                strmerchantlong = b.getString("GKSTOREMERCHANTLONG");
                strmerchantaddress = b.getString("GKSTOREMERCHANTADD");
                strservicecharge = b.getString("GKSTOREMERCHANTSC");
                remarks = b.getString("GKSTOREREMARKS");
                grossprice = b.getString("GROSSPRICE");
                servicecode = b.getString("GKSERVICECODE");
                hasdiscount = b.getInt("GKHASDISCOUNT");

            } else if (getIntent().hasExtra("PARAMOUNTQUEUE")) {
                source = "PARAMOUNTQUEUE";
                Intent intent = getIntent();
                vouchersession = intent.getStringExtra("VOUCHERSESSION");
                amountopayvalue = intent.getStringExtra("AMOUNT");
                resellerDiscount = Double.valueOf(intent.getStringExtra("PARAMOUNTRESELLERDISCOUNT"));
                vehicleClassificationCode = intent.getStringExtra("PARAMOUNTVEHICLECLASSIFICATIONCODE");

            } else if (getIntent().hasExtra("SKYCABLEPPVQUEUE")) {
                source = "SKYCABLEPPVQUEUE";
                Intent intent = getIntent();
                vouchersession = intent.getStringExtra("VOUCHERSESSION");
                amountopayvalue = intent.getStringExtra("AMOUNTPAID");
                skycableamount = intent.getStringExtra("AMOUNT");
                skycableservicecharge = intent.getStringExtra("SERVICECHARGE");
                skycablePPVSubscription = intent.getParcelableExtra("PPVSUBSCRIPTION");

            } else if (getIntent().hasExtra("PROJECTCOOPP2S")) {
                source = "PROJECTCOOPP2S";
                Intent intent = getIntent();
                vouchersession = intent.getStringExtra("VOUCHERSESSION");
                amountopayvalue = intent.getStringExtra("AMOUNTPAID");
                coopAmount = intent.getStringExtra("AMOUNT");
                coopServiceCharge = intent.getStringExtra("SERVICECHARGE");
                gameProduct = intent.getParcelableExtra("GAMEP2SOBJ");
                winningCombination = intent.getStringExtra("P2SCOMBINATION");
                winningName = intent.getStringExtra("P2SNAME");
                winningAccountNumber = intent.getStringExtra("P2SACCOUNTNUMBER");

            } else if (getIntent().hasExtra("SKYCABLEREGISTRATION")) {
                source = "SKYCABLEREGISTRATION";
                Intent intent = getIntent();
                vouchersession = intent.getStringExtra("VOUCHERSESSION");
                amountopayvalue = intent.getStringExtra("AMOUNTPAID");
                skycableamount = intent.getStringExtra("AMOUNT");
                skycableservicecharge = intent.getStringExtra("SERVICECHARGE");
                skycableRegistration = intent.getParcelableExtra("SKYCABLEREGISTRATIONDATA");

            } else if (getIntent().hasExtra("MEDPADALA")) {

                source = b.getString("MEDPADALA");
                mobileval = b.getString("MOBILE");
                amount = b.getString("AMOUNT");
                amountopayvalue = b.getString("AMOUNTTOPAY");
                servicecharge = b.getString("SERVICECHARGE");
                vouchersession = b.getString("VOUCHERSESSION");
            } else if (getIntent().hasExtra("GKNEGOSYO")) {

                mGKService = b.getParcelable("SERVICE");
                mGKNegosyoPackage = b.getParcelable("GKNEGOSYOPACKAGE");
                source = b.getString("GKNEGOSYO");
                amount = String.valueOf(mGKNegosyoPackage.getPrice());
                amountopayvalue = String.valueOf(mGKNegosyoPackage.getPrice());
                vouchersession = b.getString("VOUCHERSESSION");
                mDistributorID = b.getString("DISTRIBUTORID");
                mDistributorName = b.getString("DISTRIBUTORNAME");
                mGKNegosyoApplicationStatus = b.getParcelable("APPLICATION_STATUS");

                log(vouchersession);
                log(mGKNegosyoApplicationStatus.getDataResID());

            } else if (getIntent().hasExtra("PAYMENTREQUEST")) {

                mPaymentRequest = getIntent().getParcelableExtra(PaymentRequest.KEY_PAYMENTREQUEST);
                source = getIntent().getStringExtra("PAYMENTREQUEST");
                amountopayvalue = getIntent().getStringExtra("totalamount");
                vouchersession = getIntent().getStringExtra("vouchersession");
                resellerDiscount = Double.valueOf(getIntent().getStringExtra("resellerdiscount"));

            } else if(getIntent().hasExtra("FROMEGAME")) {

                source = b.getString("FROMEGAME");
                mobileval = b.getString("EGAMEMOBILENO");
                amountopayvalue = b.getString("EGAMEAMOUNT");
                productcodeval = b.getString("EGAMEPRODUCTCODE");
                resellerDiscount = Double.valueOf(getIntent().getStringExtra("EGAMERESELLERDISCOUNT"));
                resellerAmount = getIntent().getStringExtra("EGAMERESELLERGROSSAMOUNT");
                vouchersession = b.getString("EGAMEVOUCHERSESSION");
                hasdiscount = b.getInt("EGAMEHASDISCOUNT");
                longitude = b.getString("EGAMELONGITUDE");
                latitude = b.getString("EGAMELATITUDE");
                amount = b.getString("EGAMEORIGINALAMOUNT");

            } else if(getIntent().hasExtra("FROMGKEARNREGISTRATION")){
                source = b.getString("FROMGKEARNREGISTRATION");
                amountopayvalue = b.getString("GKEARNAMOUNT");
                vouchersession = b.getString("GKEARNREGISTRATIONVOUCHERSESSION");
                amount = b.getString("GKEARNREGISTRATIONFEE");
                gkearnreferralcode= b.getString("GKEARNREFERRALCODE");

            } else {
                source = "PREPAID";
                mobileval = b.getString("MOBILE");
                networkval = b.getString("NETWORK");
                producttypeval = b.getString("PRODUCTTYPE");
                productcodeval = b.getString("PRODUCTCODE");
                vouchersession = b.getString("VOUCHERSESSION");
                //set amount to pay
                amountopayvalue = b.getString("AMOUNT");
                Intent intent = getIntent();
                resellerDiscount = Double.valueOf(intent.getStringExtra("PREPAIDLOADINGRESELLERDISCOUNT"));
                resellerAmount = intent.getStringExtra("GROSSAMOUNT");
            }
            Logger.debug("okhttp","GROSS AMOUNT : "+resellerAmount);
            amountopayval.setText(formatter.format(Double.parseDouble(amountopayvalue)));
            amounttopay = Double.parseDouble(amountopayvalue);

            //Load local data
            preLoadVoucher("");
            purchasestatus = "";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getVoucherV3(getVoucherV3Session);
            getVouchersV3withSecurity();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private final Callback<GetVoucherResponse> getVoucherV3Session = new Callback<GetVoucherResponse>() {

        @Override
        public void onResponse(Call<GetVoucherResponse> call, Response<GetVoucherResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    ArrayList<Voucher> voucherList = response.body().getVouchers();
                    db.truncateTable(db, DatabaseHandler.VOUCHERS);

                    //inserting vouchers to local db
                    new LongInsertOperation().execute(voucherList);

                } else {
                    hideProgressDialog();
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                hideProgressDialog();
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GetVoucherResponse> call, Throwable t) {
            t.printStackTrace();
            hideProgressDialog();
            showNoInternetToast();
        }
    };

    private class LongOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {

        @Override
        protected void onPreExecute() {
            availableVouchersList.clear();
        }

        @Override
        protected ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
            availableVouchersList = db.getAllVouchers(db);
            return availableVouchersList;
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> voucherData) {
            hideProgressDialog();
            preLoadVoucher("REFRESH");
        }
    }

    private class LongInsertOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {

        @Override
        protected ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
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
            //populating available voucher list
            if (db.getAllVouchers(db).size() > 0) {
                new LongOperation().execute();
            } else {
                hideProgressDialog();
            }
        }
    }

    private void getVoucherV3(Callback<GetVoucherResponse> getVoucherV3Callback) {
        Call<GetVoucherResponse> getvouchers = RetrofitBuild.getVoucherV3Service(getViewContext())
                .getVoucherV3Call(borrowerid,
                        guarantorid,
                        sessionid,
                        imei,
                        userid,
                        authcode,
                        String.valueOf(0));
        getvouchers.enqueue(getVoucherV3Callback);
    }

    @Override
    public void onBackPressed() {
        //cancelPaymetTransactionDialog();
        cancelPaymetTransactionDialogNew();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            //cancelPaymetTransactionDialog();
            cancelPaymetTransactionDialogNew();
        }
        return super.onOptionsItemSelected(item);
    }

    private void preLoadVoucher(String from) {
        try {
            if (db.getAllVouchers(db).size() > 0) {
                if (from.equals("REFRESH")) {
                    availableVouchersList.clear();
                }
                availableVouchersList = db.getAllVouchers(db);
                availableVouchersAdapter.setData(availableVouchersList);
                mTvEmpty.setVisibility(View.GONE);
                horizontal_recycler_view.setVisibility(View.VISIBLE);
            } else {
                mTvEmpty.setVisibility(View.VISIBLE);
                horizontal_recycler_view.setVisibility(View.GONE);
            }

            if (from.equals("REFRESH")) {
                for (int i = 0; i < paymentVouchersList.size(); i++) {
                    String serialno = paymentVouchersList.get(i).getVoucherSerialNo();

                    if (availableVouchersList.contains(serialno)) {
                        availableVouchersList.remove(paymentVouchersList.get(paymentVouchersList.indexOf(serialno)));
                    }
                }
            }

            //set adapter first load
            horizontal_recycler_view.setAdapter(availableVouchersAdapter);
            horizontal_recycler_payment.setAdapter(paymentVouchersAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class AvailableVouchersAdapter extends RecyclerView.Adapter<AvailableVouchersAdapter.MyViewHolder> {

        private List<Voucher> horizontalList;
        private int mPosition = 0;
        private Voucher mVoucher;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView txtView;
            public RelativeLayout wrap;
            public TextView ball;
            public ImageView prepaidtag;
            private AppCompatTextView groupBalance;
            private TextView groupName;

            public MyViewHolder(View view) {
                super(view);
                txtView = view.findViewById(R.id.txtView);
                ball = view.findViewById(R.id.bal);
                wrap = view.findViewById(R.id.wrapper);
                prepaidtag = view.findViewById(R.id.prepaid_tag);
                groupBalance = view.findViewById(R.id.group_voucher_balance);
                groupName = view.findViewById(R.id.group_voucher_title);
            }
        }


        public AvailableVouchersAdapter(List<Voucher> horizontalList) {
            this.horizontalList = horizontalList;
        }

        private void setData(List<Voucher> horizontalList) {
            this.horizontalList.clear();
            this.horizontalList.addAll(horizontalList);
            notifyDataSetChanged();
        }

        private void clear() {
            this.horizontalList.clear();
            notifyDataSetChanged();
        }

        public List<Voucher> getCurrentData(int currentPos) {
            horizontalList.remove(currentPos);
            notifyDataSetChanged();
            return horizontalList;
        }

        public int getClickedPosition() {
            return mPosition;
        }

        private void setClickedPosition(int pos) {
            mPosition = pos;
        }

        private void setClickedVoucher(Voucher voucher) {
            mVoucher = voucher;
        }

        public Voucher getClickedVoucher() {
            return mVoucher;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_airtime_payement1_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            Voucher voucher = horizontalList.get(position);
            String bal;
            String url = "";
            String remainingbal = "0";
            String isprepaid = "";

            if (voucher != null) {
                try {
                    if (voucher.getGroupVoucherCode().equals(".")) {
                        if (voucher.getExtra3().equals(".")) {
                            holder.prepaidtag.setVisibility(View.GONE);
                        } else {
                            holder.prepaidtag.setVisibility(View.VISIBLE);
                        }

                        Glide.with(mcontext)
                                .load(CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg")
                                .into(holder.txtView);

                        holder.ball.setVisibility(View.VISIBLE);
                        holder.groupBalance.setVisibility(View.GONE);
                        holder.groupName.setVisibility(View.GONE);

                        if (voucher.getRemainingBalance() % 1 == 0)
                            bal = String.valueOf((int) voucher.getRemainingBalance());
                        else
                            bal = String.valueOf(voucher.getRemainingBalance());

                    } else {
                        Glide.with(mcontext)
                                .load(CommonVariables.s3link + "group-vouchers.png")
                                .into(holder.txtView);

                        double doubleBalance = Double.parseDouble(voucher.getGroupBalance());

                        if (doubleBalance % 1 == 0)
                            bal = String.valueOf((int) doubleBalance);
                        else
                            bal = String.valueOf(doubleBalance);


                        holder.groupBalance.setVisibility(View.VISIBLE);
                        holder.groupName.setVisibility(View.VISIBLE);
                        holder.ball.setVisibility(View.GONE);
                        holder.prepaidtag.setVisibility(View.GONE);
                        holder.groupBalance.setText(CommonFunctions.currencyFormatter(voucher.getGroupBalance()));
                        if (!voucher.getGroupName().equals("."))
                            holder.groupName.setText(voucher.getGroupName());
                    }

                    holder.ball.setText(bal);
                    holder.wrap.setTag(voucher);

                    holder.wrap.setOnLongClickListener(new View.OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {
                            // TODO Auto-generated method stub
                            setClickedPosition(holder.getPosition());
                            setClickedVoucher(horizontalList.get(getClickedPosition()));
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
                            v.startDrag(data, shadow, null, 0);

                            droparea.setOnDragListener(new View.OnDragListener() {

                                @Override
                                public boolean onDrag(View v, DragEvent event) {

                                    // TODO Auto-generated method stub
                                    final int action = event.getAction();

                                    switch (action) {

                                        case DragEvent.ACTION_DRAG_STARTED:
                                            dropareawrap.setBackgroundResource(R.color.colorPrimaryDark);
                                            dropareatext.setText("Drop voucher here to pay");
                                            dropareatext.setTextColor(Color.parseColor("#25bed9"));
                                            break;

                                        case DragEvent.ACTION_DRAG_EXITED:
                                            dropareawrap.setBackgroundResource(R.color.superlightgray);
                                            dropareatext.setText("Drop voucher here to pay");
                                            dropareatext.setTextColor(Color.parseColor("#80000000"));
                                            break;
                                        case DragEvent.ACTION_DRAG_ENTERED:
                                            break;

                                        case DragEvent.ACTION_DROP: {
                                            if (purchasestatus.equals("PAID")) {
                                                showToast("Amount tendered is already enough for this transaction", GlobalToastEnum.NOTICE);
                                            } else {
                                                if(availableVouchersList.size() > 0) {
                                                    String serialno = availableVouchersList.get(position).getVoucherSerialNo();
                                                    Cursor c = db.getSpecificVoucher(db, serialno);
                                                    if (c.getCount() > 0) {
                                                        c.moveToFirst();
                                                        do {
                                                            if (c.getString(c.getColumnIndex("GroupVoucherCode")).equals(".")) {
                                                                vouchercodeval = c.getString(c.getColumnIndex("vouchercode"));
                                                                voucherpinval = c.getString(c.getColumnIndex("voucherpin"));
                                                            } else {
                                                                vouchercodeval = c.getString(c.getColumnIndex("GroupVoucherCode"));
                                                                voucherpinval = c.getString(c.getColumnIndex("GroupVoucherPIN"));
                                                            }
                                                        } while (c.moveToNext());
                                                    }

                                                    currentpossition = position;
                                                    PROCESS = "VALIDATEVOUCHER";
                                                    createSession();
                                                }
                                            }
                                            return (true);
                                        }

                                        case DragEvent.ACTION_DRAG_ENDED: {
                                            dropareawrap.setBackgroundResource(R.color.superlightgray);
                                            dropareatext.setText("Drop voucher here to pay");
                                            dropareatext.setTextColor(Color.parseColor("#80000000"));
                                            return (true);
                                        }

                                        default:
                                            break;
                                    }
                                    return true;
                                }
                            });
                            return false;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    public class PaymentVouchersAdapter extends RecyclerView.Adapter<PaymentVouchersAdapter.MyViewHolder> {

        private List<Voucher> horizontalListPayment;

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView txtView;
            public RelativeLayout wrap;
            public TextView ball;
            public ImageView cancelpayment;
            public ImageView prepaidtag;
            private AppCompatTextView groupBalance;
            private TextView groupName;


            public MyViewHolder(View view) {
                super(view);
                txtView = view.findViewById(R.id.txtView);
                ball = view.findViewById(R.id.bal);
                wrap = view.findViewById(R.id.wrapper);
                cancelpayment = view.findViewById(R.id.cancelpayment);
                cancelpayment.setOnClickListener(this);
                prepaidtag = view.findViewById(R.id.prepaid_tag);
                groupBalance = view.findViewById(R.id.group_voucher_balance);
                groupName = view.findViewById(R.id.group_voucher_title);
            }

            @Override
            public void onClick(View v) {

                int position = getAdapterPosition();

                switch (v.getId()) {
                    case R.id.cancelpayment: {
                        String serialno = paymentVouchersList.get(position).getVoucherSerialNo();
                        Cursor c = db.getSpecificVoucher(db, serialno);
                        if (c.getCount() > 0) {
                            c.moveToFirst();
                            do {
                                if (c.getString(c.getColumnIndex("GroupVoucherCode")).equals(".")) {
                                    vouchercodeval = c.getString(c.getColumnIndex("vouchercode"));
                                    voucherpinval = c.getString(c.getColumnIndex("voucherpin"));
                                } else {
                                    vouchercodeval = c.getString(c.getColumnIndex("GroupVoucherCode"));
                                    voucherpinval = c.getString(c.getColumnIndex("GroupVoucherPIN"));
                                }
                            } while (c.moveToNext());
                        }
                        currentpossition = position;
                        PROCESS = "CANCELVOUCHER";
                        createSession();
                        break;
                    }
                }
            }
        }


        public PaymentVouchersAdapter(List<Voucher> horizontalListPayment) {
            this.horizontalListPayment = horizontalListPayment;
        }

        private void setData(List<Voucher> horizontalListPayment) {
            this.horizontalListPayment = horizontalListPayment;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_airtime_payment1_item1, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Voucher voucher = paymentVouchersList.get(position);

            Logger.debug("antonhttp", "ADAPTER VOUCHER: " + new Gson().toJson(voucher));

            String bal = "0";
            String url = "";
            String remainingbal = "0";
            String isprepaid = "";
            if (voucher.getGroupVoucherCode().equals(".")) {
                if (voucher.getExtra3().equals(".")) {
                    holder.prepaidtag.setVisibility(View.GONE);
                } else {
                    holder.prepaidtag.setVisibility(View.VISIBLE);
                }

                Glide.with(mcontext)
                        .load(CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg")
                        .into(holder.txtView);

                holder.ball.setVisibility(View.VISIBLE);
                holder.groupBalance.setVisibility(View.GONE);
                holder.groupName.setVisibility(View.GONE);

                if (voucher.getRemainingBalance() % 1 == 0)
                    bal = String.valueOf((int) voucher.getRemainingBalance());
                else
                    bal = String.valueOf(voucher.getRemainingBalance());

//                bal = voucher.getVoucherBalance();

            } else {
                Glide.with(mcontext)
                        .load(CommonVariables.s3link + "group-vouchers.png")
                        .into(holder.txtView);

                double doubleBalance = Double.parseDouble(voucher.getGroupBalance());

                if (doubleBalance % 1 == 0)
                    bal = String.valueOf((int) doubleBalance);
                else
                    bal = String.valueOf(doubleBalance);

                holder.groupBalance.setVisibility(View.VISIBLE);
                holder.groupName.setVisibility(View.VISIBLE);
                holder.ball.setVisibility(View.GONE);
                holder.prepaidtag.setVisibility(View.GONE);
                holder.groupBalance.setText(CommonFunctions.currencyFormatter(voucher.getGroupBalance()));
                if (!voucher.getGroupName().equals("."))
                    holder.groupName.setText(voucher.getGroupName());
            }

            holder.ball.setText(bal);

//            holder.cancelpayment.setOnClickListener(v -> {
//                String serialno = paymentVouchersList.get(position).getVoucherSerialNo();
//                Cursor c = db.getSpecificVoucher(db, serialno);
//                if (c.getCount() > 0) {
//                    c.moveToFirst();
//                    do {
//                        if (c.getString(c.getColumnIndex("GroupVoucherCode")).equals(".")) {
//                            vouchercodeval = c.getString(c.getColumnIndex("vouchercode"));
//                            voucherpinval = c.getString(c.getColumnIndex("voucherpin"));
//                        } else {
//                            vouchercodeval = c.getString(c.getColumnIndex("GroupVoucherCode"));
//                            voucherpinval = c.getString(c.getColumnIndex("GroupVoucherPIN"));
//                        }
//                    } while (c.moveToNext());
//                }
//                currentpossition = position;
//                PROCESS = "CANCELVOUCHER";
//                createSession();
//
//            });

            holder.wrap.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    // TODO Auto-generated method stub
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
                    v.startDrag(data, shadow, null, 0);

                    droparea.setOnDragListener(new View.OnDragListener() {

                        @Override
                        public boolean onDrag(View v, DragEvent event) {

                            // TODO Auto-generated method stub
                            final int action = event.getAction();

                            switch (action) {

                                case DragEvent.ACTION_DRAG_STARTED:
                                    dropareawrap.setBackgroundResource(R.color.colorred);
                                    dropareatext.setText("Drop voucher here to cancel");
                                    dropareatext.setTextColor(Color.parseColor("#FF0000"));
                                    break;

                                case DragEvent.ACTION_DRAG_EXITED:
                                    dropareawrap.setBackgroundResource(R.color.superlightgray);
                                    dropareatext.setText("Drop voucher here to pay");
                                    dropareatext.setTextColor(Color.parseColor("#80000000"));
                                    break;

                                case DragEvent.ACTION_DRAG_ENTERED:
                                    break;

                                case DragEvent.ACTION_DROP: {

                                    String serialno = paymentVouchersList.get(position).getVoucherSerialNo();
                                    Cursor c = db.getSpecificVoucher(db, serialno);
                                    if (c.getCount() > 0) {
                                        c.moveToFirst();
                                        do {
                                            if (c.getString(c.getColumnIndex("GroupVoucherCode")).equals(".")) {
                                                vouchercodeval = c.getString(c.getColumnIndex("vouchercode"));
                                                voucherpinval = c.getString(c.getColumnIndex("voucherpin"));
                                            } else {
                                                vouchercodeval = c.getString(c.getColumnIndex("GroupVoucherCode"));
                                                voucherpinval = c.getString(c.getColumnIndex("GroupVoucherPIN"));
                                            }
                                        } while (c.moveToNext());
                                    }

                                    currentpossition = position;
                                    PROCESS = "CANCELVOUCHER";
                                    createSession();

                                    return (true);
                                }

                                case DragEvent.ACTION_DRAG_ENDED: {
                                    dropareawrap.setBackgroundResource(R.color.superlightgray);
                                    dropareatext.setText("Drop voucher here to pay");
                                    dropareatext.setTextColor(Color.parseColor("#80000000"));

                                    return (true);

                                }

                                default:
                                    break;
                            }
                            return true;
                        }
                    });


                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalListPayment.size();
        }
    }

    public void proceedConfirmation(View view) {

        String vouchercode = "";
        String pin = "";
        String serialused = "";

        if (purchasestatus.equals("PAID")) {
            for (int i = 0; i < paymentVouchersList.size(); i++) {
                serialused = serialused + "|" + paymentVouchersList.get(i);

                if (!paymentVouchersList.get(i).equals("") && paymentVouchersList.get(i) != null) {
                    try {
                        Cursor c = db.getSpecificVoucher(db, paymentVouchersList.get(i).getVoucherSerialNo());
                        if (c.getCount() > 0) {
                            c.moveToFirst();
                            do {
                                vouchercode = vouchercode + "|" + c.getString(c.getColumnIndex("vouchercode"));
                                pin = pin + "|" + c.getString(c.getColumnIndex("voucherpin"));
                            } while (c.moveToNext());
                            //   c.close();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (source.contentEquals("FROMMERCHANTEXPRESS")) {
                Intent intent = new Intent(this, PaymentConfirmation.class);
                intent.putExtra("AMOUNTTOPAY", amountopayvalue);
                intent.putExtra("MOBILE", mobileval);
                intent.putExtra("MERCHANT", merchant);
                intent.putExtra("VOUCHERSESSION", vouchersession);
                intent.putExtra("FROMMERCHANTEXPRESS", source);
                intent.putExtra("CHANGE", Double.toString(remaingbalance));
                intent.putExtra("AMOUNTENDERED", Double.toString(totalamountpaid + remaingbalance));
                intent.putExtra("PAYVIAQRRESELLERDISCOUNT", String.valueOf(resellerDiscount));
                intent.putExtra("GROSSAMOUNT", String.valueOf(resellerAmount));
                startActivityForResult(intent, 1);

            } else if (source.contentEquals("BILLSPAYMENT")) {
                Intent intent = new Intent(this, BillsPaymentConfirmationActivity.class);
                intent.putExtra("AMOUNT", amountopayvalue);
                intent.putExtra("SPBILLERCODE", spbillercode);
                intent.putExtra("BILLERPARAMDATA", billerparamdata);
                intent.putExtra("BILLERNAME", billername);
                intent.putExtra("VOUCHERSESSION", vouchersession);
                intent.putExtra("CHANGE", Double.toString(remaingbalance));
                intent.putExtra("AMOUNTENDERED", Double.toString(totalamountpaid + remaingbalance));
                intent.putExtra("SERVICECHARGE", servicecharge);
                intent.putExtra("OTHERCHARGES", othercharges);
                intent.putExtra("NEWAPPLICATION", isNewApplication);
                intent.putExtra("PPV", isPPV);
                intent.putExtra("DISCOUNT", discount);
                intent.putExtra("GROSSPRICE", grossprice);
                intent.putExtra("GKSERVICECODE", servicecode);
                intent.putExtra("GKHASDISCOUNT", hasdiscount);
                intent.putExtra("RECEIPTMOBILENO", receiptmobileno);
                intent.putExtra("RECEIPTEMAILADD", receiptemailadd);
                intent.putExtra("LATITUDE", latitude);
                intent.putExtra("LONGITUDE", longitude);

                startActivityForResult(intent, 1);

            } else if (source.contentEquals("RETAILERLOADING")) {
                Intent intent = new Intent(this, RetailerLoadConfirmationActivity.class);
                intent.putExtra("MOBILE", mobileval);
                intent.putExtra("NETWORK", networkval);
                intent.putExtra("AMOUNTTOPAY", amountopayvalue);
                intent.putExtra("PRODUCTTYPE", producttypeval);
                intent.putExtra("PRODUCTCODE", productcodeval);
                intent.putExtra("AMOUNTENDERED", Double.toString(totalamountpaid + remaingbalance));
                intent.putExtra("CHANGE", Double.toString(remaingbalance));
                intent.putExtra("VOUCHERSESSION", vouchersession);
                intent.putExtra("DISCOUNT", discount);
                intent.putExtra("GROSSPRICE", grossprice);
                intent.putExtra("GKSERVICECODE", servicecode);
                intent.putExtra("GKHASDISCOUNT", hasdiscount);
                intent.putExtra("LATITUDE", latitude);
                intent.putExtra("LONGITUDE", longitude);

                startActivityForResult(intent, 1);

            } else if (source.contentEquals("PARAMOUNTQUEUE")) {

                Intent intent = new Intent(getViewContext(), ParamountInsuranceConfirmationActivity.class);
                intent.putExtra("vouchersessionid", vouchersession);
                intent.putExtra("origin", "QUEUE");
                intent.putExtra("PARAMOUNTRESELLERDISCOUNT", String.valueOf(resellerDiscount));

                intent.putExtra("AMOUNTENDERED", Double.toString(totalamountpaid + remaingbalance));
                intent.putExtra("CHANGE", Double.toString(remaingbalance));
                intent.putExtra("AMOUNTTOPAY", amountopayvalue);

                startActivity(intent);

//                Bundle args = new Bundle();
//                args.putString("vouchersessionid",vouchersession);
//                args.putString("PARAMOUNTRESELLERDISCOUNT", String.valueOf(resellerDiscount));
//                ParamountInsuranceConfirmationActivity.start(getViewContext(), vouchersession);


            } else if (source.contentEquals("SKYCABLEPPVQUEUE")) {
                Bundle args = new Bundle();
                args.putString("vouchersession", vouchersession);
                args.putParcelable("PPVSUBSCRIPTION", skycablePPVSubscription);
                args.putString("SKYCABLEAMOUNT", skycableamount);
                args.putString("SKYCABLESERVICECHARGE", skycableservicecharge);
                args.putString("SKYCABLEAMOUNTPAID", amountopayvalue);
                args.putString("TYPE", "CONFIRM");
                SkyCableActivity.start(getViewContext(), 6, args);
            } else if (source.contentEquals("PROJECTCOOPP2S")) {
                Bundle args = new Bundle();
                args.putString("vouchersession", vouchersession);
                args.putParcelable("GAMEP2SOBJ", gameProduct);
                args.putString("AMOUNT", coopAmount);
                args.putString("SERVICECHARGE", coopServiceCharge);
                args.putString("AMOUNTPAID", amountopayvalue);
                args.putString("TYPE", "CONFIRM");
                args.putString("P2SCOMBINATION", winningCombination);
                args.putString("P2SNAME", winningName);
                args.putString("P2SACCOUNTNUMBER", winningAccountNumber);
                ProjectCoopActivity.start(getViewContext(), 3, args);
            } else if (source.contentEquals("SKYCABLEREGISTRATION")) {
                Bundle args = new Bundle();
                args.putString("vouchersession", vouchersession);
                args.putParcelable("skycableRegistration", skycableRegistration);
                args.putString("SKYCABLEAMOUNT", skycableamount);
                args.putString("SKYCABLESERVICECHARGE", skycableservicecharge);
                args.putString("SKYCABLEAMOUNTPAID", amountopayvalue);
                args.putString("TYPE", "CONFIRM");
                SkyCableActivity.start(getViewContext(), 11, args);
            } else if (source.contentEquals("MEDPADALA")) {
                MedPadalaConfirmationActivity.start(getViewContext(), mobileval, amount, servicecharge, amountopayvalue, String.valueOf(totalamountpaid + remaingbalance), String.valueOf(remaingbalance), vouchersession);

            } else if (source.contentEquals("SCHOOLMINI")) {
                Intent intent = new Intent(this, SchoolMiniConfirmationActivity.class);
                intent.putExtra("STUDENTID", studentid);
                intent.putExtra("COURSE", studentcourse);
                intent.putExtra("YEARLEVEL", studentyearlevel);
                intent.putExtra("FIRSTNAME", firstname);
                intent.putExtra("MIDDLENAME", studentmiddlename);
                intent.putExtra("LASTNAME", lastname);
                intent.putExtra("MOBILENUMBER", mobileno);
                intent.putExtra("EMAILADDRESS", emailadd);
                intent.putExtra("AMOUNT", amountopayvalue);
                intent.putExtra("MOBILENUMBER", mobileno);
                intent.putExtra("EMAILADDRESS", emailadd);
                intent.putExtra("VOUCHERSESSION", vouchersession);
                intent.putExtra("AMOUNT", amountopayvalue);
                intent.putExtra("DISCOUNT", discount);
                intent.putExtra("AMOUNTENDERED", Double.toString(totalamountpaid + remaingbalance));
                intent.putExtra("CHANGE", Double.toString(remaingbalance));
                intent.putExtra("SCHOOLYEAR", schoolyear);
                intent.putExtra("SEMESTER", semester);
                intent.putExtra("SEMESTRALFEE", semestralfee);
                intent.putExtra("SOAID", soaid);
                intent.putExtra("MERCHANTREFERENCENO", studentmerchantreferenceno);
                intent.putExtra("GROSSPRICE", grossprice);
                intent.putExtra("GKHASDISCOUNT", hasdiscount);
                intent.putExtra("GKMPAYMENTTYPE", mPaymentType);
                intent.putExtra("GKCUSTOMERSERVICECHARGE", strcustomerservicecharge);
                intent.putExtra("REMARKS", school_remarks);

                startActivityForResult(intent, 1);

            } else if (source.contentEquals("VOTES")) {
                Intent intent = new Intent(this, VotesConfirmationActivity.class);
                intent.putExtra("EVENTID", eventid);
                intent.putExtra("EVENTNAME", eventname);
                intent.putExtra("PARTICIPANTID", participantid);
                intent.putExtra("SERVICECODE", servicecode);
                intent.putExtra("PARTICIPANTNUMBER", participantnumber);
                intent.putExtra("PARTICIPANTNAME", participantname);
                intent.putExtra("VOUCHERSESSION", vouchersession);
                intent.putExtra("AMOUNT", amountopayvalue);
                intent.putExtra("DISCOUNT", discount);
                intent.putExtra("AMOUNTENDERED", Double.toString(totalamountpaid + remaingbalance));
                intent.putExtra("CHANGE", Double.toString(remaingbalance));
                intent.putExtra("GROSSPRICE", grossprice);
                intent.putExtra("GKHASDISCOUNT", hasdiscount);
                intent.putExtra("GKCUSTOMERSERVICECHARGE", strcustomerservicecharge);
                startActivityForResult(intent, 1);

            }  else if (source.contentEquals("COOPASSIST")) {
                Intent intent = new Intent(this, CoopAssistantConfirmationActivity.class);
                intent.putExtra("COOPMEMBERNAME", membername);
                intent.putExtra("COOPMEMBERMOBILENO", membermobileno);
                intent.putExtra("COOPMEMBEREMAILADD", memberemailadd);
                intent.putExtra("COOPACCOUNTNAME", memberaccountname);
                intent.putExtra("COOPREQUESTID", requestid);
                intent.putExtra("VOUCHERSESSION", vouchersession);
                intent.putExtra("AMOUNT", amountopayvalue);
                intent.putExtra("DISCOUNT", discount);
                intent.putExtra("AMOUNTENDERED", Double.toString(totalamountpaid + remaingbalance));
                intent.putExtra("CHANGE", Double.toString(remaingbalance));
                intent.putExtra("GROSSPRICE", grossprice);
                intent.putExtra("GKHASDISCOUNT", hasdiscount);
                intent.putExtra("GKCUSTOMERSERVICECHARGE", strcustomerservicecharge);
                intent.putExtra("FROM", strfrom);
                intent.putExtra(CoopAssistantBills.KEY_COOPBILLS, coopAssistantBills);
                startActivityForResult(intent, 1);

            } else if (source.contentEquals("GKEARN")) {
                Intent intent = new Intent(this, GKEarnConfirmationActivity.class);
                intent.putExtra("GKEARNBORROWERNAME", borrowername);
                intent.putExtra("GKEARNPACKAGEID", packageID);
                intent.putExtra("GKEARNPACKAGENAME", packageName);
                intent.putExtra("VOUCHERSESSION", vouchersession);
                intent.putExtra("AMOUNT", amountopayvalue);
                intent.putExtra("DISCOUNT", discount);
                intent.putExtra("AMOUNTENDERED", Double.toString(totalamountpaid + remaingbalance));
                intent.putExtra("CHANGE", Double.toString(remaingbalance));
                intent.putExtra("GROSSPRICE", grossprice);
                intent.putExtra("GKHASDISCOUNT", hasdiscount);
                intent.putExtra("GKCUSTOMERSERVICECHARGE", strcustomerservicecharge);
                intent.putExtra("FROM", strfrom);
                startActivityForResult(intent, 1);

            }
            else if (source.contentEquals("RFID")) {
                Intent intent = new Intent(this, RFIDConfirmationActivity.class);
                intent.putExtra("RFIDBORROWERNAME", borrowername);
                intent.putExtra("RFIDNUMBER", rfidNumber);
                intent.putExtra("RFIDCARDNUMBER", rfidCardNumber);
                intent.putExtra("RFIDVOUCHERCODE", rfidvouchercode);
                intent.putExtra("RFIDVOUCHERSERIAL", rfidvoucherserialno);
                intent.putExtra("VOUCHERSESSION", vouchersession);
                intent.putExtra("AMOUNT", amountopayvalue);
                intent.putExtra("DISCOUNT", discount);
                intent.putExtra("AMOUNTENDERED", Double.toString(totalamountpaid + remaingbalance));
                intent.putExtra("CHANGE", Double.toString(remaingbalance));
                intent.putExtra("GROSSPRICE", grossprice);
                intent.putExtra("GKHASDISCOUNT", hasdiscount);
                intent.putExtra("GKCUSTOMERSERVICECHARGE", strcustomerservicecharge);
                intent.putExtra("FROM", strfrom);
                startActivityForResult(intent, 1);

            }
            else if (source.contentEquals("GKSTOREDETAILS")) {
                Intent intent = new Intent(this, GKStoreConfirmationActivity.class);

                intent.putExtra("GKSTOREFIRSTNAME", firstname);
                intent.putExtra("GKSTORELASTNAME", lastname);
                intent.putExtra("GKSTOREMOBILENO", mobileno);
                intent.putExtra("GKSTOREEMAILADD", emailadd);
                intent.putExtra("GKSTORECUSTOMERADD", customeradd);
                intent.putExtra("VOUCHERSESSION", vouchersession);
                intent.putExtra("AMOUNT", amountopayvalue);
                intent.putExtra("DISCOUNT", discount);
                intent.putExtra("AMOUNTENDERED", Double.toString(totalamountpaid + remaingbalance));
                intent.putExtra("CHANGE", Double.toString(remaingbalance));
                intent.putExtra("CUSTOMEROTHERDETAILS", customerotherdetails);
                intent.putExtra("ORDERDETAILS", orderdetails);
                intent.putExtra("MERCHANTID", merchantid);
                intent.putExtra("GKSTOREID", storeid);
                intent.putExtra("LATITUDE", latitude);
                intent.putExtra("LONGITUDE", longitude);
                intent.putExtra("GKSTOREBORROWERNAME", borrowername);
                intent.putExtra("GKSTOREDELIVERYTYPE", strgkstoredeliverytype);
                intent.putExtra("GKSTOREMERCHANTLAT", strmerchantlat);
                intent.putExtra("GKSTOREMERCHANTLONG", strmerchantlong);
                intent.putExtra("GKSTOREMERCHANTADD", strmerchantaddress);
                intent.putExtra("GKSTOREMERCHANTSC", strservicecharge);
                intent.putExtra("GKSTOREREMARKS", remarks);
                intent.putExtra("GROSSPRICE", grossprice);
                intent.putExtra("GKSERVICECODE", servicecode);
                intent.putExtra("GKHASDISCOUNT", hasdiscount);
                startActivityForResult(intent, 1);

            } else if (source.contentEquals("GKNEGOSYO")) {

                GKNegosyoPaymentConfirmationActivity.start(
                        getViewContext(),
                        mDistributorID,
                        amount,
                        mGKNegosyoPackage,
                        mGKService,
                        amountopayvalue,
                        String.valueOf(totalamountpaid + remaingbalance),
                        String.valueOf(remaingbalance),
                        vouchersession,
                        mGKNegosyoApplicationStatus,
                        mDistributorName
                );
            } else if (source.contentEquals("PAYMENTREQUEST")) {

                Bundle args = new Bundle();
                args.putParcelable(PaymentRequest.KEY_PAYMENTREQUEST, mPaymentRequest);
                args.putString("amounttopay", amountopayvalue);
                args.putDouble("amounttendered", totalamountpaid + remaingbalance);
                args.putString("vouchersession", vouchersession);
                args.putDouble("resellerdiscount", resellerDiscount);

                Intent intent = new Intent(getViewContext(), PaymentRequestConfirmationActivity.class);
                intent.putExtras(args);
                startActivity(intent);

                Logger.debug("vanhttp", "vouchersession: " + vouchersession);

//                PaymentRequestConfirmationActivity.start(getViewContext(), args);

            } else if(source.contentEquals("EGAME")){
                Bundle args = new Bundle();
                args.putString("mobileno", mobileval);
                args.putString("productcode", productcodeval);
                args.putDouble("amounttopay", Double.valueOf(amountopayvalue));
                args.putDouble("amounttendered", totalamountpaid + remaingbalance);
                args.putDouble("resellerdiscount", resellerDiscount);
                args.putString("vouchersession", vouchersession);
                args.putInt("hasdiscount", hasdiscount);
                args.putString("longitude", longitude);
                args.putString("latitude", latitude);
                args.putDouble("originalamount", Double.valueOf(amount));

                Intent intent = new Intent(getViewContext(), EGameConfirmationActivity.class);
                intent.putExtras(args);
                startActivity(intent);
            } else if(source.contentEquals("GKEARNREGISTRATION")){
                Bundle args = new Bundle();
                args.putDouble("amounttopay", Double.valueOf(amountopayvalue));
                args.putDouble("amounttendered", totalamountpaid + remaingbalance);
                args.putString("vouchersession", vouchersession);
                args.putDouble("originalamount", Double.valueOf(amount));
                args.putString("gkearnreferralcode", gkearnreferralcode);

                Intent intent = new Intent(getViewContext(), GKEarnRegistrationConfirmationActivity.class);
                intent.putExtras(args);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, PaymentConfirmation.class);
                intent.putExtra("AMOUNTTOPAY", amountopayvalue);
                intent.putExtra("MOBILE", mobileval);
                intent.putExtra("NETWORK", networkval);
                intent.putExtra("PRODUCTTYPE", producttypeval);
                intent.putExtra("PRODUCTCODE", productcodeval);
                intent.putExtra("AMOUNTENDERED", Double.toString(totalamountpaid + remaingbalance));
                intent.putExtra("CHANGE", Double.toString(remaingbalance));
                intent.putExtra("SERIALUSED", serialused);
                intent.putExtra("USEDVOUCHERCODE", vouchercode);
                intent.putExtra("USEDPIN", pin);
                intent.putExtra("VOUCHERSESSION", vouchersession);
                intent.putExtra("PREPAIDLOADINGRESELLERDISCOUNT", String.valueOf(resellerDiscount));
                intent.putExtra("GROSSAMOUNT", String.valueOf(resellerAmount));
                startActivityForResult(intent, 1);
            }

        } else {
            if (Double.valueOf(totalamountpaid + remaingbalance) <= 0) {
                showToast("No voucher added yet.", GlobalToastEnum.NOTICE);

            } else {
                showToast("You tendered insufficient amount. Please add more voucher.", GlobalToastEnum.WARNING);
            }
        }
    }

    private void createSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            CommonFunctions.showDialog(mcontext, "", "Processing. Please wait ...", false);
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            if (PROCESS.contentEquals("VALIDATEVOUCHER")) {
                validateVoucherV2();
            } else if (PROCESS.contentEquals("CANCELTRANSACTION")) {
                //deletePaymentTransaction();
                deletePaymentTransactionV2();
            } else if (PROCESS.contentEquals("FETCHVOUCHER")) {
                new HttpAsyncTask3().execute(CommonVariables.GETVOUCHERS);
            } else {
                deleteIndividualPaymentV2();
            }
        } else {
            showNoInternetToast();
        }
    }

    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            try {

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("vouchercode", vouchercodeval);
                jsonObject.accumulate("voucherpin", voucherpinval);
                jsonObject.accumulate("vouchersession", vouchersession);
                jsonObject.accumulate("merchantid", ".");
                jsonObject.accumulate("borrowerid", borrowerid);


                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                e.printStackTrace();
                CommonFunctions.hideDialog();
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {
                CommonFunctions.hideDialog();
                if (CommonVariables.isDebugMode) {
                    Log.i("RESULT FROM SERVER", result);
                }

                if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    switch (result) {
                        case "null":
                            showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                            break;
                        case "error":
                            showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                            break;
                        case "001":
                            showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
                            break;
                        case "002":
                            showToast("Invalid Authentication.", GlobalToastEnum.NOTICE);
                            break;
                        case "003":
                            showToast("Invalid Session.", GlobalToastEnum.NOTICE);
                            break;
                        case "004":
                            showToast("Voucher invalid or transaction is expired.", GlobalToastEnum.NOTICE);
                            //showExpiredDialog();
                            showExpiredDialogNew();
                            break;
                        default:
                            //  processResult1(result);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("vouchersession", vouchersession);
                jsonObject.accumulate("borrowerid", borrowerid);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                e.printStackTrace();
                CommonFunctions.hideDialog();
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {
                CommonFunctions.hideDialog();
                if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    switch (result) {
                        case "null":
                            showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                            break;
                        case "error":
                            showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                            break;
                        case "001":
                            showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
                            break;
                        case "002":
                            showToast("Invalid Authentication.", GlobalToastEnum.NOTICE);
                            break;
                        case "003":
                            showToast("Invalid Session.", GlobalToastEnum.NOTICE);
                            break;
                        default:
                            processResult2(result);

                    }
                }
            } catch (Exception e) {

            }

        }
    }

    private class HttpAsyncTask3 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            try {

                String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("result", currentlimit);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                e.printStackTrace();
                CommonFunctions.hideDialog();
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {
                CommonFunctions.hideDialog();

                if (result == null) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("001")) {
                    showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    showToast("Invalid Session.", GlobalToastEnum.NOTICE);
                } else if (result.equals("003")) {
                    showToast("Invalid Guarantor ID.", GlobalToastEnum.NOTICE);
                } else if (result.equals("error")) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    //4.
                    processResultVoucher(result);
                }
            } catch (Exception e) {
            }

        }
    }

    private void setUIVoucherValid(ValidatedVoucher voucher) {
        try {

            Logger.debug("okhttp", "VALIDATED VOUCHER: " + new Gson().toJson(voucher));

            totalamountpaid = Double.parseDouble(voucher.getAmountPaid());
            amounttopay = Double.parseDouble(voucher.getPurchaseBalance());
            remaingbalance = Double.parseDouble(voucher.getChange());
            purchasestatus = voucher.getStatus();

            amounttenval.setText(formatter.format(totalamountpaid + remaingbalance));

            dropareawrap.setBackgroundResource(R.color.superlightgray);
            dropareatext.setText("Drop voucher here to pay");
            dropareatext.setTextColor(Color.parseColor("#80000000"));

            Voucher mVoucher = availableVouchersList.get(availableVouchersAdapter.getClickedPosition());
            mVoucher.setRemainingBalance(Double.parseDouble(voucher.getVoucherBalance()));

            paymentVouchersList.add(0, mVoucher);
            paymentVouchersAdapter.setData(paymentVouchersList);
            paymentVouchersAdapter.notifyDataSetChanged();
            horizontal_recycler_payment.smoothScrollToPosition(0);

            availableVouchersList.remove(availableVouchersAdapter.getClickedPosition());
            availableVouchersAdapter.setData(availableVouchersList);
            availableVouchersAdapter.notifyDataSetChanged();

            currentpossition = 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUIRemovedVoucher(ValidatedVoucher data) {

        try {
            totalamountpaid = Double.parseDouble(data.getAmountPaid());
            amounttopay = Double.parseDouble(data.getPurchaseBalance());
            remaingbalance = Double.parseDouble(data.getChange());
            purchasestatus = data.getStatus();

            amounttenval.setText(formatter.format(totalamountpaid + remaingbalance));

            dropareawrap.setBackgroundResource(R.color.superlightgray);
            dropareatext.setTextColor(Color.parseColor("#80000000"));


            availableVouchersList.add(paymentVouchersList.get(currentpossition));
            horizontal_recycler_view.setAdapter(availableVouchersAdapter);

            paymentVouchersList.remove(paymentVouchersList.get(currentpossition));
            horizontal_recycler_payment.setAdapter(paymentVouchersAdapter);

            currentpossition = 0;

            availableVouchersAdapter.setData(availableVouchersList);
            availableVouchersAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void cancelPaymetTransactionDialog() {
        try {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Cancellation");
            alertDialog.setMessage("Are you sure you want to cancel this payment transaction?");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    PROCESS = "CANCELTRANSACTION";
                    createSession();
                }
            });

            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processResult2(String result) {
        try {
            finish();
            purchasestatus = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cancelPaymetTransactionDialogNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("Cancellation", "Are you sure you want to cancel " +
                        "this payment transaction?",
                "Cancel", "OK", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                dialog.dismiss();
                PROCESS = "CANCELTRANSACTION";
                createSession();
            }
        });
    }

    private void showExpiredDialog() {

        try {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Information");
            alertDialog.setMessage("Transaction has expired. Please retry.");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                    purchasestatus = "";
                }
            });

            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showExpiredDialogNew() {

        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("Notice", "Transaction has expired. Please retry.",
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.NOTICE);

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                purchasestatus = "";
                finish();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                purchasestatus = "";
                finish();
            }
        });

    }

    private void processResultVoucher(String result) {
        try {
            GetVoucherResponse response = gson.fromJson(result, GetVoucherResponse.class);

            if (response.getVouchers().size() > 0) {
                db.deleteVoucher(db);

                for (Voucher voucher : response.getVouchers()) {
                    db.insertVouchers(db, voucher);
                }
            }
            preLoadVoucher("REFRESH");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void openRefreshVoucherNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("Notice", "It seems your vouchers are not updated. " +
                        "Please press refresh to update your voucher.",
                "Cancel", "Refresh", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                dialog.dismiss();
                PROCESS = "FETCHVOUCHER";
                createSession();
            }
        });
    }

    private void deletePaymentTransaction() {
        Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
                .deletePaymentTransaction(
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        sessionid,
                        vouchersession
                );
        call.enqueue(deletePaymentTransactionCallback);
    }

    private Callback<String> deletePaymentTransactionCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                CommonFunctions.hideDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    finish();
                    purchasestatus = "";
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            try {
                finish();
                purchasestatus = "";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void validateVoucherV2() {

        String mMerchantID = ".";

        if (source.contentEquals("FROMMERCHANTEXPRESS")) {
            mMerchantID = merchant.getMerchantID();
        } else if (source.contentEquals("SCHOOLMINI")) {
            mMerchantID = merchantid;
        } else if (source.contentEquals("GKSTOREDETAILS")) {
            mMerchantID = merchantid;
        } else if (source.contentEquals("PARAMOUNTQUEUE")) {

            if (vehicleClassificationCode.equals("VC_01")) {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "paramount_merchantid_01");
            } else if (vehicleClassificationCode.equals("VC_02")) {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "paramount_merchantid_02");
            } else if (vehicleClassificationCode.equals("VC_03")) {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "paramount_merchantid_03");
            } else if (vehicleClassificationCode.equals("VC_04")) {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "paramount_merchantid_04");
            } else {
                mMerchantID = null;
            }

        } else if (source.contentEquals("SKYCABLEPPVQUEUE")) {
            mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "skycablemerchantidppv");
        } else if (source.contentEquals("SKYCABLEREGISTRATION")) {
            mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "skycablemerchantidnewapplication");
        } else if (source.contentEquals("PROJECTCOOPP2S")) {
            mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "projectcoopmerchantid");
        } else if (source.contentEquals("MEDPADALA")) {
            mMerchantID = "MEDPADALA";
        } else if (source.contentEquals("GKNEGOSYO")) {
            mMerchantID = "GKNEGOSYO";
        } else if (source.contentEquals("BILLSPAYMENT")) {
            if (isNewApplication) {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "skycablemerchantidnewapplication");
            } else if (isPPV) {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "skycablemerchantidppv");
            } else {
                mMerchantID = "GKBILLSPAYMENT";
            }
        } else if (source.contentEquals("RETAILERLOADING")) {
            mMerchantID = "GKSMARTLOADWALLET";
        } else if (source.contentEquals("GKEARN")) {
            if(strfrom.equals(GKEarnStockistPackagesDetailsActivity.KEY_GKEARN_VALUE_PACKAGE)) {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_GKEARNSTOCKISTMERCHANTID);
            } else {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");
            }
        } else if (source.contentEquals("RFID")) {
            mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RFID_MERCHANTID);
        } else if (source.contentEquals("EGAME")){
            mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "EGameMerchantID");
        } else if(source.contentEquals("GKEARNREGISTRATION")){
            mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");
        } else {
            mMerchantID = "PREPAIDLOAD";
        }

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei",imei);
        parameters.put("userid",userid);
        parameters.put("borrowerid",borrowerid);
        parameters.put("vouchercode", vouchercodeval);
        parameters.put("voucherpin", voucherpinval);
        parameters.put("vouchersession", vouchersession);
        parameters.put("merchantid", mMerchantID);


        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        validateVoucherIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", validateVoucherIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        validateVoucherAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        validateVoucherKeyEncryption = CommonFunctions.getSha1Hex(validateVoucherAuthenticationid + sessionid + "validateVoucherV2");
        validateVoucherParam = CommonFunctions.encryptAES256CBC(validateVoucherKeyEncryption, String.valueOf(paramJson));

        Call<GenericResponse> call = RetrofitBuilder.getPaymentV2API(getViewContext())
                .validateVoucherV2(
                       validateVoucherAuthenticationid,sessionid,validateVoucherParam
                );

        call.enqueue(validateVoucherV2Callback);
    }

    private Callback<GenericResponse> validateVoucherV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            CommonFunctions.hideDialog();

                if (errBody == null) {
                    String decryptedMessage = CommonFunctions.decryptAES256CBC(validateVoucherKeyEncryption,response.body().getMessage());
                    String status = response.body().getStatus();
                    if (status.equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(validateVoucherKeyEncryption,response.body().getData());
                        Logger.debug("okhttp","DATTAAAA: "+decryptedData);
                        ValidatedVoucher validatedVoucher = new Gson().fromJson(decryptedData,ValidatedVoucher.class);
                        Logger.debug("okhttp","DATTAAASA: "+new Gson().toJson(validatedVoucher));
                        setUIVoucherValid(validatedVoucher);
                    }else if (status.equals("003")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else if (status.equals("004")) {
                        showToast("Voucher invalid. A pending transaction with this voucher is being processed.", GlobalToastEnum.NOTICE);
                    }  else if (status.equals("006")) {
                        showToast("Voucher is restricted.", GlobalToastEnum.WARNING);
                    } else if (status.equals("007")) {
                        //showExpiredDialog();
                        showExpiredDialogNew();
                    } else if (status.equals("008")) {
                        showToast("Invalid Session.", GlobalToastEnum.NOTICE);
                    } else if (status.equals("009")) {
                        showToast("Voucher is transferred. Partial consummation is not allowed. " +
                                "Please try another voucher.", GlobalToastEnum.WARNING);
                    } else {
                        showErrorToast(decryptedMessage);
                    }
                } else {
                    showErrorGlobalDialogs();
                }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            showErrorGlobalDialogs();
        }
    };

    private void deleteIndividualPaymentV2() {
        String mMerchantID = ".";

        if (source.contentEquals("FROMMERCHANTEXPRESS")) {
            mMerchantID = merchant.getMerchantID();
        } else if (source.contentEquals("SCHOOLMINI")) {
            mMerchantID = merchantid;
        } else if (source.contentEquals("GKSTOREDETAILS")) {
            mMerchantID = merchantid;
        } else if (source.contentEquals("PARAMOUNTQUEUE")) {

            if (vehicleClassificationCode.equals("VC_01")) {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "paramount_merchantid_01");
            } else if (vehicleClassificationCode.equals("VC_02")) {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "paramount_merchantid_02");
            } else if (vehicleClassificationCode.equals("VC_03")) {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "paramount_merchantid_03");
            } else if (vehicleClassificationCode.equals("VC_04")) {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "paramount_merchantid_04");
            } else {
                mMerchantID = null;
            }

        } else if (source.contentEquals("SKYCABLEPPVQUEUE")) {
            mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "skycablemerchantidppv");
        } else if (source.contentEquals("SKYCABLEREGISTRATION")) {
            mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "skycablemerchantidnewapplication");
        } else if (source.contentEquals("PROJECTCOOPP2S")) {
            mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "projectcoopmerchantid");
        } else if (source.contentEquals("MEDPADALA")) {
            mMerchantID = "MEDPADALA";
        } else if (source.contentEquals("GKEARN")) {
            if(strfrom.equals(GKEarnStockistPackagesDetailsActivity.KEY_GKEARN_VALUE_PACKAGE)) {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_GKEARNSTOCKISTMERCHANTID);
            } else {
                mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");
            }
        } else if (source.contentEquals("RFID")) {
            mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RFID_MERCHANTID);
        } else if (source.contentEquals("EGAME")){
            mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "EGameMerchantID");
        } else if(source.contentEquals("GKEARNREGISTRATION")){
            mMerchantID = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");
        } else {
            mMerchantID = "PREPAIDLOAD";
        }


        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei",imei);
        parameters.put("userid",userid);
        parameters.put("borrowerid",borrowerid);
        parameters.put("vouchercode", vouchercodeval);
        parameters.put("voucherpin", voucherpinval);
        parameters.put("vouchersession", vouchersession);
        parameters.put("merchantid", mMerchantID);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        deleteIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", deleteIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        deleteAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        deleteKeyEncryption = CommonFunctions.getSha1Hex(deleteAuthenticationid + sessionid + "deleteIndividualPaymentV2");
        deleteParam = CommonFunctions.encryptAES256CBC(deleteKeyEncryption, String.valueOf(paramJson));

        Call<GenericResponse> call = RetrofitBuilder.getPaymentV2API(getViewContext())
                .deleteIndividualPaymentV2(deleteAuthenticationid,sessionid,deleteParam);
        call.enqueue(deleteIndividualPaymentV2Callback);

    }

    private Callback<GenericResponse> deleteIndividualPaymentV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            CommonFunctions.hideDialog();
            try {
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(deleteKeyEncryption,response.body().getMessage());
                    String status = response.body().getStatus();
                    if (status.equals("000")) {
                        String data = CommonFunctions.decryptAES256CBC(deleteKeyEncryption,response.body().getData());
                        Logger.debug("okhttp","DDDD: "+data);
                        setUIRemovedVoucher(new Gson().fromJson(data,ValidatedVoucher.class));
                    } else if (status.equals("003")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else if (status.equals("004")) {
                        //showExpiredDialog();
                        showExpiredDialogNew();
                    } else {
                        showToast(message, GlobalToastEnum.NOTICE);
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            showErrorGlobalDialogs();
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * OCTOBER 2019    *
     * *****************/
    private void getVouchersV3withSecurity(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("limit", String.valueOf(currentlimit));
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
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decrypteddata = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    db.truncateTable(db, DatabaseHandler.VOUCHERS);


                    ArrayList<Voucher> voucherList = new Gson().fromJson(CommonFunctions.parseJSON(decrypteddata,"Voucher"),new TypeToken<ArrayList<Voucher>>(){}.getType());
                    Logger.debug("okhttp","VOUCHERS:::::::::::::::::: "+voucherList);
                    //inserting vouchers to local db
                    new LongInsertOperation().execute(voucherList);

                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }
                else {
                    hideProgressDialog();
                    showErrorGlobalDialogs(decryptedMessage);
                }
            } else {
                hideProgressDialog();
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            hideProgressDialog();
            showNoInternetToast();
        }
    };


    //delete payment transaction
    private void deletePaymentTransactionV2(){

        deleteIndex = "";
        deleteAuthenticationid = "";
        deleteParam = "";
        deleteKeyEncryption = "";

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei",imei);
        parameters.put("userid",userid);
        parameters.put("borrowerid",borrowerid);
        parameters.put("vouchersession", vouchersession);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        deleteIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", deleteIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        deleteAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        deleteKeyEncryption = CommonFunctions.getSha1Hex(deleteAuthenticationid + sessionid + "deletePaymentTransactionV2");
        deleteParam = CommonFunctions.encryptAES256CBC(deleteKeyEncryption, String.valueOf(paramJson));

        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .deletePaymentTransactionV2(deleteAuthenticationid,sessionid,deleteParam);
        call.enqueue(deletePaymentTransactionV2Callback);

    }
    private Callback<GenericResponse> deletePaymentTransactionV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                CommonFunctions.hideDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(deleteKeyEncryption,response.body().getMessage());
                   switch (response.body().getStatus()){
                       case "000":
                           finish();
                           purchasestatus = "";
                           break;
                       case "003":
                           showAutoLogoutDialog(getString(R.string.logoutmessage));
                           break;
                       default:
                           showToast(message,GlobalToastEnum.INFORMATION);
                           break;
                   }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            try {
                finish();
                purchasestatus = "";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
