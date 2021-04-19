package com.goodkredit.myapplication.activities.coopassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eghl.sdk.EGHL;
import com.eghl.sdk.params.PaymentParams;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniBillingReferenceActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.prepaidrequest.EGHLPayment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantBills;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantConfirmationActivity extends BaseActivity implements View.OnClickListener {
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

    //LOCAL CALL
    private DatabaseHandler mdb;

    //MAIN CONTAINER
    private LinearLayout maincontainer;
    private NestedScrollView home_body;

    //NO INTERNET CONNECTION
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //GK NEGOSYO
    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;
    private String resellerid = "";
    private String distributorid = "";
    private boolean checkIfReseller = false;

    //FROM
    private String strFrom = "";

    //PAYMENT MEMBERSHIP
    private TextView txv_name;
    private TextView txv_mobileno;
    private TextView txv_emailaddress;
    private LinearLayout layout_acccountname;
    private TextView txv_accountname;

    private String membername = "";
    private String membermobileno = "";
    private String memberemailadd = "";
    private String requestid = "";
    private String memberaccountname = "";

    //PAYMENTS
    private TextView txv_amount;
    private TextView txv_servicecharge;
    private TextView txv_change;
    private TextView txv_amounttendered;

    //DELAY TIME
    private int currentdelaytime = 0;
    private int totaldelaytime = 10000;

    private String grossprice = "";
    private String discount = "";
    private String vouchersession = "";
    private String amountopayvalue = "";
    private String amountendered = "";
    private String amountchange = "";
    private int hasdiscount = 0;
    private String strcustomerservicecharge = "";

    //PROCEED
    private LinearLayout confirmcontainer;

    //LOAN PAYMENT
    private CoopAssistantBills coopAssistantBills = null;
    private String soaid = "";
    private String year;
    private String month;
    private Calendar c = Calendar.getInstance();


    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String payIndex;
    private String payKey;
    private String payAuth;
    private String payParam;

    //pay membership request
    private String payMemberAuth;
    private String payMemberIndex;
    private String payMemberKey;
    private String payMemberParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_coopassistant_confirmation);

            init();

            initdata();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------METHODS---------------------
    private void init() {
        mdb = new DatabaseHandler(getViewContext());
        //initialize here
        maincontainer = findViewById(R.id.maincontainer);
        home_body = findViewById(R.id.home_body);

        //No Internet
        nointernetconnection = findViewById(R.id.nointernetconnection);
        refreshnointernet = findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //PAYMENT MEMBERSHIP
        txv_name = findViewById(R.id.txv_name);
        txv_mobileno = findViewById(R.id.txv_mobileno);
        txv_emailaddress = findViewById(R.id.txv_emailaddress);
        layout_acccountname = findViewById(R.id.layout_acccountname);
        txv_accountname = findViewById(R.id.txv_accountname);


        //PAYMENTS
        txv_servicecharge = findViewById(R.id.txv_servicecharge);
        txv_amount = findViewById(R.id.txv_amount);
        txv_change = findViewById(R.id.txv_change);
        txv_amounttendered = findViewById(R.id.txv_amounttendered);

        confirmcontainer = findViewById(R.id.proceedcontainer);

        scrollonTop();
    }

    private void initdata() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");

        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

        //set toolbar
        setupToolbar();

        getSupportActionBar().setTitle("");

        //get the passed parameters
        Bundle b = getIntent().getExtras();
        strFrom = b.getString("FROM");

        membername = b.getString("COOPMEMBERNAME");
        membermobileno = b.getString("COOPMEMBERMOBILENO");
        memberemailadd = b.getString("COOPMEMBEREMAILADD");
        requestid = b.getString("COOPREQUESTID");
        memberaccountname = b.getString("COOPACCOUNTNAME");
        vouchersession = b.getString("VOUCHERSESSION");
        amountendered = b.getString("AMOUNTENDERED");
        amountchange = b.getString("CHANGE");
        amountopayvalue = b.getString("AMOUNT");
        discount = b.getString("DISCOUNT");
        grossprice = b.getString("GROSSPRICE");
        hasdiscount = b.getInt("GKHASDISCOUNT");
        strcustomerservicecharge = b.getString("GKCUSTOMERSERVICECHARGE");


        if (strFrom.equals("LoanBillsPayment")) {
            showProgressDialog(getViewContext(), "Processing registration request", "Please wait...");
            coopAssistantBills = b.getParcelable(CoopAssistantBills.KEY_COOPBILLS);
            soaid = CommonFunctions.replaceEscapeData(coopAssistantBills.getSOAID());

            year = String.valueOf(c.get(Calendar.YEAR));
            month = String.valueOf(c.get(Calendar.MONTH) + 1);

            if (Integer.valueOf(month) < 10) {
                month = "0" + month;
            }
            hideProgressDialog();
        }



        displayData();
        onClickListeners();
    }

    private void displayData() {
        if (!membername.equals("")) {
            String name = membername;
            if(name.contains(".")) {
                name = CommonFunctions.replaceEscapeData(CommonFunctions.capitalizeWord(name));
                name = name.replace(".", "");
            }
            txv_name.setText(name);
        }

        if (!membermobileno.equals("")) {
            txv_mobileno.setText(CommonFunctions.replaceEscapeData("+".concat(membermobileno)));
        }

        if (!memberemailadd.equals("")) {
            txv_emailaddress.setText(CommonFunctions.replaceEscapeData(memberemailadd));
        }

        if(strFrom.equals("MemberShipAccounts")) {
            if (!memberaccountname.equals("")) {
                layout_acccountname.setVisibility(View.VISIBLE);
                txv_accountname.setText(CommonFunctions.replaceEscapeData(memberaccountname));
            }
        }

        Double dblservicecharge = Double.parseDouble(strcustomerservicecharge);
        if (dblservicecharge > 0) {
            txv_servicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(strcustomerservicecharge)));
            txv_amount.setText(CommonFunctions.currencyFormatter(String.valueOf(Double.valueOf(amountopayvalue) - Double.valueOf(strcustomerservicecharge))));
        } else {
            txv_servicecharge.setText(CommonFunctions.currencyFormatter("0"));
            txv_amount.setText(CommonFunctions.currencyFormatter(amountopayvalue));
        }

        txv_change.setText(CommonFunctions.currencyFormatter(amountchange));

        txv_amounttendered.setText(CommonFunctions.currencyFormatter(amountendered));
    }

    private void scrollonTop() {
        home_body.post(new Runnable() {
            public void run() {
                home_body.smoothScrollTo(0, 0);
            }
        });
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    private void showContent(boolean isShow) {
        if (isShow) {
            home_body.setVisibility(View.VISIBLE);
        } else {
            home_body.setVisibility(View.GONE);
        }
    }

    private void showConfirmSuccessDialog(String message) {
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
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.proceedtoCoopHome();
            }
        });

    }

    private void showConfirmFailedDialog(String message) {
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
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.returntoBeforePayments();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.returntoBeforePayments();
            }
        });
    }

    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, CoopAssistantConfirmationActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    //----------------------------API-----------------------------------
    private void callMainAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            if (strFrom.equals("MemberShipPayment")) {
                showProgressDialog(getViewContext(), "Processing registration request", "Please wait...");
//                payMembershipRequest();
                payMembershipRequestV2();
            } else if (strFrom.equals("MemberShipAccounts")) {
                showProgressDialog(getViewContext(), "Processing payment request", "Please wait...");
                //addCoopAccountWallet();
                addCoopAccountWalletV2();
            } else if (strFrom.equals("LoanBillsPayment")) {
                showProgressDialog(getViewContext(), "Processing loan payment", "Please wait...");
                //payLoanBills();
                payCoopSOAV2();
            }
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //----------------------------------------PAYMENT MEMBERSHIP ------------------------------------
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
//                        amountopayvalue,
//                        merchantid,
//                        requestid,
//                        hasdiscount,
//                        grossprice,
//                        "PAY VIA GK",
//                        "."
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
//                        checkIfReseller = !distributorid.equals("") && !distributorid.equals(".")
//                                && !resellerid.equals("") && !resellerid.equals(".");
//
//                        showConfirmSuccessDialog(response.body().getMessage());
//
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

    //------------------------------------------PAYMENT ACCOUNTS---------------------------------------
//    private void addCoopAccountWallet() {
//        Call<GenericResponse> addCoopAccountWallet = RetrofitBuild.getCoopAssistantAPI(getViewContext())
//                .addCoopAccountWallet(
//                        sessionid,
//                        imei,
//                        userid,
//                        borrowerid,
//                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
//                        servicecode,
//                        vouchersession,
//                        amountopayvalue,
//                        merchantid,
//                        memberaccountname,
//                        hasdiscount,
//                        grossprice,
//                        "PAY VIA GK",
//                        "."
//                );
//
//        addCoopAccountWallet.enqueue(addCoopAccountWalletRequestCallBack);
//    }
//
//    private final Callback<GenericResponse> addCoopAccountWalletRequestCallBack = new Callback<GenericResponse>() {
//        @Override
//        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
//            try {
//                hideProgressDialog();
//                ResponseBody errorBody = response.errorBody();
//                if (errorBody == null) {
//                    if (response.body().getStatus().equals("000")) {
//                        if (distributorid.equals("") || distributorid.equals(".")
//                                || resellerid.equals("") || resellerid.equals(".")) {
//                            checkIfReseller = false;
//                        } else {
//                            checkIfReseller = true;
//                        }
//
//                        showConfirmSuccessDialog(response.body().getMessage());
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

    //----------------------------------------LOAN PAYMENT--------------------------------------------
//    private void payLoanBills() {
//
//
//        Call<GenericResponse> payCoopSOA = RetrofitBuild.getCoopAssistantAPI(getViewContext())
//                .payCoopSOACall(
//                        sessionid,
//                        imei,
//                        userid,
//                        borrowerid,
//                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
//                        servicecode,
//                        vouchersession,
//                        merchantid,
//                        amountopayvalue,
//                        soaid,
//                        String.valueOf(hasdiscount),
//                        grossprice,
//                        "PAY VIA GK",
//                        month,
//                        year,
//                        ".",
//                        CommonVariables.devicetype
//                );
//
//        payCoopSOA.enqueue(payCoopSOASession);
//    }
//
//    private final Callback<GenericResponse> payCoopSOASession = new Callback<GenericResponse>() {
//        @Override
//        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
//            try {
//                hideProgressDialog();
//                ResponseBody errorBody = response.errorBody();
//                if (errorBody == null) {
//                    if (response.body().getStatus().equals("000")) {
//                        checkIfReseller = !distributorid.equals("") && !distributorid.equals(".")
//                                && !resellerid.equals("") && !resellerid.equals(".");
//
//                        showConfirmSuccessDialog(response.body().getMessage());
//
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

    //----------------ON CLICK--------------------------
    private void onClickListeners() {
        confirmcontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalDialogs dialog = new GlobalDialogs(getViewContext());

                dialog.createDialog("", "You are about to pay your request.",
                        "Close", "Proceed", ButtonTypeEnum.DOUBLE,
                        false, false, DialogTypeEnum.NOTICE);

                dialog.hideCloseImageButton();

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
                        if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                        dialog.dismiss();
                        currentdelaytime = 0;
                        callMainAPI();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshnointernet: {
                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                    showNoInternetConnection(false);
                    showContent(true);
                } else {
                    showNoInternetConnection(true);
                    showContent(false);
                    showWarningToast("Seems you are not connected to the internet. " +
                            "Please check your connection and try again. Thank you.");
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

    //------------------------------------------PAYMENT ACCOUNTS--------------------------------------------//
    private void addCoopAccountWalletV2() {

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("vouchersession", vouchersession);
        parameters.put("merchantid", merchantid);
        parameters.put("amount", amountopayvalue);
        parameters.put("accountname", memberaccountname);
        parameters.put("hasdiscount", String.valueOf(hasdiscount));
        parameters.put("grossamount",  String.valueOf(grossprice));
        parameters.put("paymenttype",  "PAY VIA GK");
        parameters.put("paymentoption",  ".");
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

                    checkIfReseller = !distributorid.equals("") && !distributorid.equals(".")
                            && !resellerid.equals("") && !resellerid.equals(".");

                    showConfirmSuccessDialog(message);

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
    //-----------------------------------------END HERE-------------------------------------------------------//

    //PAY COOP SOA
    private void  payCoopSOAV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("vouchersession", vouchersession);
        parameters.put("merchantid", merchantid);
        parameters.put("amount", amountopayvalue);
        parameters.put("soaid", soaid);
        parameters.put("hasdiscount", String.valueOf(hasdiscount));
        parameters.put("servicecode", servicecode);
        parameters.put("grossamount", grossprice);
        parameters.put("paymenttype", "PAY VIA GK");
        parameters.put("paymentoption", ".");
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
    private final Callback<GenericResponse>  payCoopSOAV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(payKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    if (distributorid.equals("") || distributorid.equals(".")
                            || resellerid.equals("") || resellerid.equals(".")) {
                        checkIfReseller = false;
                    } else {
                        checkIfReseller = true;
                    }
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
        parameters.put("amount", amountopayvalue);
        parameters.put("merchantid", merchantid);
        parameters.put("requestid", requestid);
        parameters.put("hasdiscount", String.valueOf(hasdiscount));
        parameters.put("grossamount", String.valueOf(grossprice));
        parameters.put("paymenttype", "PAY VIA GK");
        parameters.put("paymentoption", ".");
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
                    checkIfReseller = !distributorid.equals("") && !distributorid.equals(".")
                            && !resellerid.equals("") && !resellerid.equals(".");

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
            showErrorGlobalDialogs();
            hideProgressDialog();
            t.printStackTrace();
        }
    };

}
