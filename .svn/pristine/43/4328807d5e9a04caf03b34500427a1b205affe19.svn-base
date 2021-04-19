package com.goodkredit.myapplication.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.payment.AvailableVouchersPaymentAdapter;
import com.goodkredit.myapplication.adapter.payment.PaymentVouchersPaymentAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Merchant;
import com.goodkredit.myapplication.bean.SkycablePPVSubscription;
import com.goodkredit.myapplication.bean.SkycableRegistration;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.SpacesItemDecoration;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends BaseActivity implements View.OnClickListener {

    private final int ID_VALIDATE_VOUCHER = 111;
    private final int ID_CANCEL_SESSION = 222;
    private final int ID_GET_VOUCHERS = 333;
    private final int ID_REMOVE_VOUCHER = 444;

    private int mID = 0;

    private DatabaseHandler db;

    private RecyclerView mRvAvailableVouchers;
    private AvailableVouchersPaymentAdapter mAdapterAvailableVouchers;

    private RecyclerView mRvPaymentVouchers;
    private PaymentVouchersPaymentAdapter mAdapterPaymentVouchers;
    private ArrayList<Voucher> mPaymentList = new ArrayList<>();

    private TextView droparea;
    private RelativeLayout dropareawrap;
    private TextView dropareatext;
    private TextView amountopayval;
    private TextView amounttenval;
    private double remaingbalance = 0.0;
    private double amounttopay = 0.0;
    private double totalamountpaid = 0.0;
    private String sessionidval = "";
    private String useridval = "";
    private String imei = "";
    private String borroweridval = "";
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

    //SKYCABLE
    private SkycableRegistration skycableRegistration = null;
    private SkycablePPVSubscription skycablePPVSubscription = null;
    private String skycableamount = "";
    private String skycableservicecharge = "";

    //SECURITY
    private String deleteIndex;
    private String deleteAuthenticationid;
    private String deleteKeyEncryption;
    private String deleteParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
    }

    private void init() {
        db = new DatabaseHandler(getViewContext());
        //toolbar
        findViewById(R.id.btn_cancel_transaction).setOnClickListener(this);

        //initialize adapter
        mAdapterAvailableVouchers = new AvailableVouchersPaymentAdapter(getViewContext(), db.getAllVouchers(db));
        mAdapterPaymentVouchers = new PaymentVouchersPaymentAdapter(getViewContext(), mPaymentList);

        mRvAvailableVouchers = findViewById(R.id.rv_available_vouchers);
        mRvAvailableVouchers.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvAvailableVouchers.addItemDecoration(new SpacesItemDecoration(10));
        mRvAvailableVouchers.setAdapter(mAdapterAvailableVouchers);

        mRvPaymentVouchers = findViewById(R.id.rv_payments);
        mRvPaymentVouchers.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvPaymentVouchers.addItemDecoration(new SpacesItemDecoration(10));
        mRvPaymentVouchers.setAdapter(mAdapterPaymentVouchers);

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        session = PreferenceUtils.getBorrowerId(getViewContext());


        //Get the passed Value
        Bundle b = getIntent().getExtras();

        if (getIntent().hasExtra("FROMMERCHANTEXPRESS")) {
            Intent intent = getIntent();
            mobileval = intent.getStringExtra("MOBILE").toString();
            vouchersession = intent.getStringExtra("VOUCHERSESSION").toString();
            amountopayvalue = intent.getStringExtra("AMOUNT").toString();
            source = intent.getStringExtra("FROMMERCHANTEXPRESS").toString();
            merchant = (Merchant) intent.getSerializableExtra("MERCHANT");

        } else if (getIntent().hasExtra("BILLERPARAMDATA")) {
            Intent intent = getIntent();
            source = "BILLSPAYMENT";
            amountopayvalue = intent.getStringExtra("AMOUNT").toString();
            spbillercode = intent.getStringExtra("SPBILLERCODE").toString();
            billerparamdata = intent.getStringExtra("BILLERPARAMDATA").toString();
            vouchersession = intent.getStringExtra("VOUCHERSESSION").toString();
            billername = intent.getStringExtra("BILLERNAME").toString();
            servicecharge = intent.getStringExtra("SERVICECHARGE").toString();
            othercharges = intent.getStringExtra("OTHERCHARGES").toString();

        } else if (getIntent().hasExtra("FROMRETAILERLOADING")) {
            source = "RETAILERLOADING";
            mobileval = b.getString("MOBILE").toString();
            networkval = b.getString("NETWORK").toString();
            producttypeval = b.getString("PRODUCTTYPE").toString();
            productcodeval = b.getString("PRODUCTCODE").toString();
            vouchersession = b.getString("VOUCHERSESSION").toString();
            amountopayvalue = b.getString("AMOUNT").toString();
            discount = b.getString("DISCOUNT").toString();

        } else if (getIntent().hasExtra("GKSTOREFIRSTNAME")) {
            source = "GKSTOREDETAILS";
            firstname = b.getString("GKSTOREFIRSTNAME");
            lastname = b.getString("GKSTORELASTNAME");
            mobileno = b.getString("GKSTOREMOBILENO");
            emailadd = b.getString("GKSTOREEMAILADD");
            customeradd = b.getString("GKSTORECUSTOMERADD");
            vouchersession = b.getString("VOUCHERSESSION");
            amountopayvalue = b.getString("AMOUNT");
            customerotherdetails = b.getString("CUSTOMEROTHERDETAILS");
            orderdetails = b.getString("ORDERDETAILS");
            merchantid = b.getString("MERCHANTID");
            storeid = b.getString("GKSTOREID");
            latitude = b.getString("latitude");
            longitude = b.getString("longitude");
            borrowername = b.getString("GKSTOREBORROWERNAME");
            strgkstoredeliverytype = b.getString("GKSTOREDELIVERYTYPE");
            strmerchantlat = b.getString("GKSTOREMERCHANTLAT");
            strmerchantlong = b.getString("GKSTOREMERCHANTLONG");
            strmerchantaddress = b.getString("GKSTOREMERCHANTADD");
            strservicecharge = b.getString("GKSTOREMERCHANTSC");
            remarks = b.getString("GKSTOREREMARKS");


        } else if (getIntent().hasExtra("PARAMOUNTQUEUE")) {
            source = "PARAMOUNTQUEUE";
            Intent intent = getIntent();
            vouchersession = intent.getStringExtra("VOUCHERSESSION").toString();
            amountopayvalue = intent.getStringExtra("AMOUNT").toString();

        } else if (getIntent().hasExtra("SKYCABLEPPVQUEUE")) {
            source = "SKYCABLEPPVQUEUE";
            Intent intent = getIntent();
            vouchersession = intent.getStringExtra("VOUCHERSESSION").toString();
            amountopayvalue = intent.getStringExtra("AMOUNTPAID").toString();
            skycableamount = intent.getStringExtra("AMOUNT");
            skycableservicecharge = intent.getStringExtra("SERVICECHARGE");
            skycablePPVSubscription = intent.getParcelableExtra("PPVSUBSCRIPTION");

        } else if (getIntent().hasExtra("SKYCABLEREGISTRATION")) {
            source = "SKYCABLEREGISTRATION";
            Intent intent = getIntent();
            vouchersession = intent.getStringExtra("VOUCHERSESSION").toString();
            amountopayvalue = intent.getStringExtra("AMOUNTPAID").toString();
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
        } else {
            source = "PREPAID";
            mobileval = b.getString("MOBILE").toString();
            networkval = b.getString("NETWORK").toString();
            producttypeval = b.getString("PRODUCTTYPE").toString();
            productcodeval = b.getString("PRODUCTCODE").toString();
            vouchersession = b.getString("VOUCHERSESSION").toString();
            //set amount to pay
            amountopayvalue = b.getString("AMOUNT").toString();
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, PaymentActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void startTransaction(int ID) {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) == 0) {
            showToast("Seems that you are not connected to the internet. Please check your connection and try again. Thank you.", GlobalToastEnum.NOTICE);
        } else {
            switch (ID) {
                case ID_GET_VOUCHERS: {
                    mID = ID_GET_VOUCHERS;
                    break;
                }
                case ID_CANCEL_SESSION: {
                    mID = ID_CANCEL_SESSION;
                    //deletePaymentTransaction();
                    deletePaymentTransactionV2();
                    break;
                }
                case ID_REMOVE_VOUCHER: {
                    mID = ID_REMOVE_VOUCHER;
                    break;
                }
                case ID_VALIDATE_VOUCHER: {
                    mID = ID_VALIDATE_VOUCHER;
                    break;
                }
            }
        }
    }

    private void showCancelTransactionDialog() {
        try {
            new MaterialDialog.Builder(getViewContext())
                    .title("Cancellation")
                    .content("Are you sure you want to cancel this payment transaction?")
                    .positiveText("OK")
                    .negativeText("Cancel")
                    .cancelable(false)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            startTransaction(ID_CANCEL_SESSION);
                        }
                    })
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_transaction: {
                showCancelTransactionDialog();
            }
        }
    }

    public TextView getDroparea() {
        return droparea;
    }

    private void deletePaymentTransaction() {
        Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
                .deletePaymentTransaction(
                        imei,
                        CommonFunctions.getSha1Hex(imei + userid + session),
                        userid,
                        borrowerid,
                        session,
                        vouchersession
                );

        call.enqueue(deletePaymentTransactionCallback);
    }

    private Callback<String> deletePaymentTransactionCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {

        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {

        }
    };

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     */

    //delete payment transaction
    private void deletePaymentTransactionV2() {

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("vouchersession", vouchersession);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, session);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        deleteIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", deleteIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        deleteAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        deleteKeyEncryption = CommonFunctions.getSha1Hex(deleteAuthenticationid + session + "deletePaymentTransactionV2");
        deleteParam = CommonFunctions.encryptAES256CBC(deleteKeyEncryption, String.valueOf(paramJson));

        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .deletePaymentTransactionV2(deleteAuthenticationid, session, deleteParam);
        call.enqueue(deletePaymentTransactionV2Callback);

    }
    private Callback<GenericResponse> deletePaymentTransactionV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            if (errBody == null) {
                String message = CommonFunctions.decryptAES256CBC(deleteKeyEncryption, response.body().getMessage());
                switch (response.body().getStatus()) {
                    case "000":
                        Logger.debug("okhttp", "CANCELLED");
                        break;
                    case "003":
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                        break;
                    default:
                        showToast(message, GlobalToastEnum.INFORMATION);
                        break;
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
        }
    };

}
