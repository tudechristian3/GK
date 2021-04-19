package com.goodkredit.myapplication.activities.vouchers.payoutone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.model.vouchers.SubscriberBankAccounts;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherPayoutOneDepositConfirmationActivity extends BaseActivity implements View.OnClickListener {

    private EditText edt_bankname;
    private EditText edt_bankaccountno;
    private EditText edt_bankaccountname;
    private EditText edt_amount;
    private EditText edt_charges;
    private EditText edt_totalnetamount;
    private EditText edt_remarks;

    private LinearLayout layout_remarks;

    private String bank;
    private String accountno;
    private String accountname;
    private String servicecharge;

    private String sessionid;
    private String imei;
    private String authcode;
    private String borrowerid;
    private String userid;
    private String grossamount;
    private String totalamount;
    private String voucherserialno;
    private String vouchercode;
    private String subscriberremarks;
    private double totalnetamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_payoutone_deposit_confirmation);

        setupToolbar();

        init();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_payoutone_proceed, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void init(){
        edt_bankname = findViewById(R.id.edt_bankname);
        edt_bankaccountno = findViewById(R.id.edt_bankaccountno);
        edt_bankaccountname = findViewById(R.id.edt_bankaccountname);
        edt_amount = findViewById(R.id.edt_amount);
        edt_charges = findViewById(R.id.edt_charges);
        edt_totalnetamount = findViewById(R.id.edt_totalnetamount);
        edt_remarks = findViewById(R.id.edt_remarks);
        layout_remarks = findViewById(R.id.layout_remarks);
    }

    private void initData() {

        sessionid = PreferenceUtils.getSessionID(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        Intent intent = getIntent();

        bank = intent.getStringExtra(SubscriberBankAccounts.KEY_SUBSCRIBERBANK_BANK);
        accountno = intent.getStringExtra(SubscriberBankAccounts.KEY_SUBSCRIBERBANK_ACCOUNTNO);
        accountname = intent.getStringExtra(SubscriberBankAccounts.KEY_SUBSCRIBERBANK_ACCOUNTNAME);
        grossamount = intent.getStringExtra(SubscriberBankAccounts.KEY_SUBSCRIBERBANK_REMAININGBALANCE);
        voucherserialno = intent.getStringExtra(Voucher.KEY_VOUCHERSERIALNO);
        vouchercode = intent.getStringExtra(Voucher.KEY_VOUCHERCODE);
        subscriberremarks = intent.getStringExtra("SUBSCRIBERREMARKS");

        if(subscriberremarks.equals("")){
            subscriberremarks = ".";
            layout_remarks.setVisibility(View.GONE);
        } else{
            layout_remarks.setVisibility(View.VISIBLE);
        }

        edt_bankname.setText(CommonFunctions.replaceEscapeData(bank));
        edt_bankaccountno.setText(CommonFunctions.replaceEscapeData(accountno));
        edt_bankaccountname.setText(CommonFunctions.replaceEscapeData(accountname));
        edt_amount.setText(CommonFunctions.currencyFormatter(grossamount));
        edt_remarks.setText(CommonFunctions.replaceEscapeData(subscriberremarks));

        getSession("getPayoutOneSubscriberServiceCharge");
    }

    private void getSession(String from){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            if (from.equals("getPayoutOneSubscriberServiceCharge")) {
                getPayoutOneSubscriberServiceCharge(getPayoutOneSubscriberServiceChargeSession);
            } else if(from.equals("depositChequeToSubscriberBankAccount")){
                setUpProgressLoader();
                setUpProgressLoaderMessageDialog("Processing request... Please wait.");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        depositChequeToSubscriberBankAccount(depositChequeToSubscriberBankAccountSession);
                    }
                },2000);
            }

        } else{
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }
    }

    private void getPayoutOneSubscriberServiceCharge (Callback<GenericResponse> getPayoutOneSubscriberServiceChargeCallback ){
        Call<GenericResponse> getservicecharge = RetrofitBuild.getVoucherV3Service(getViewContext())
                .getPayoutOneSubscriberServiceChargeCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        grossamount,
                        CommonVariables.devicetype);
        getservicecharge.enqueue(getPayoutOneSubscriberServiceChargeCallback);
    }

    private final Callback<GenericResponse> getPayoutOneSubscriberServiceChargeSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){

                    servicecharge = response.body().getData();

                    if(servicecharge.equals("0")){
                        edt_charges.setText("0.00");
                    } else{
                        edt_charges.setText(servicecharge);
                    }

                    totalnetamount = Double.valueOf(grossamount) - Double.valueOf(servicecharge);
                    edt_totalnetamount.setText(CommonFunctions.currencyFormatter(String.valueOf(totalnetamount)));

                } else if(response.body().getStatus().equals("104")) {
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
            showErrorGlobalDialogs();
        }
    };

    private void depositChequeToSubscriberBankAccount (Callback<GenericResponse> getPayoutOneSubscriberServiceChargeCallback ){
        Call<GenericResponse> getservicecharge = RetrofitBuild.getVoucherV3Service(getViewContext())
                .depositChequeToSubscriberBankAccountCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        bank,
                        accountname,
                        accountno,
                        grossamount,
                        CommonFunctions.decimalFormatter(String.valueOf(totalnetamount)),
                        voucherserialno,
                        vouchercode,
                        subscriberremarks,
                        CommonVariables.devicetype);
        getservicecharge.enqueue(getPayoutOneSubscriberServiceChargeCallback);
    }

    private final Callback<GenericResponse> depositChequeToSubscriberBankAccountSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();

            setUpProgressLoaderDismissDialog();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){

                    //SHOW SUCCESS DIALOG
                    GlobalDialogs dialog = new GlobalDialogs(getViewContext());

                    dialog.createDialog("SUCCESS", response.body().getMessage(), "Close", "",
                            ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.SUCCESS);

                    View closebtn = dialog.btnCloseImage();
                    closebtn.setVisibility(View.GONE);

                    View singlebtn = dialog.btnSingle();
                    singlebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            proceedtoMainActivity();
                        }
                    });

                } else if(response.body().getStatus().equals("104")) {
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
            setUpProgressLoaderDismissDialog();
            showErrorGlobalDialogs();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.payoutone_proceed) {
            getSession("depositChequeToSubscriberBankAccount");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
