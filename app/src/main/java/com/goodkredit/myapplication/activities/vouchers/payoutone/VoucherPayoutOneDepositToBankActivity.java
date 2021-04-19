package com.goodkredit.myapplication.activities.vouchers.payoutone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.vouchers.payoutone.VoucherPayoutOneSubscriberBankAccountsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.vouchers.SubscriberBankAccounts;
import com.goodkredit.myapplication.responses.vouchers.payoutone.SubscriberBankAccountsResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherPayoutOneDepositToBankActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView rv_listofbanks;
    private List<SubscriberBankAccounts> mGridData;
    private VoucherPayoutOneSubscriberBankAccountsAdapter mAdapter;
    private ArrayList<SubscriberBankAccounts> passedSubscriberBankAccount;

    private TextView txv_voucheramount;
    private TextView txv_amounttodeposit;
    private TextView txv_addbankaccount;
    private EditText edt_remarks;

    private Voucher voucher = null;
    private Bundle bundle;

    private String imei;
    private String sessionid;
    private String userid;
    private String borrowerid;
    private String authcode;

    private String bank;
    private String accountno;
    private String accountname;
    private double grossamount;
    private String voucherserialno;
    private String vouchercode;
    private String subscriberremarks;

    //NO DATA FETCHED
    private TextView txv_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_payoutone_deposit_to_bank);

        setupToolbar();

        init();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_payoutone_proceed, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {

        rv_listofbanks = findViewById(R.id.rv_listofbanks);
        txv_voucheramount = findViewById(R.id.txv_voucheramount);
        txv_amounttodeposit = findViewById(R.id.txv_amounttodeposit);
        txv_addbankaccount = findViewById(R.id.txv_addbankaccount);
        edt_remarks = findViewById(R.id.edt_remarks);
        txv_nodata = findViewById(R.id.txv_nodata);

        txv_addbankaccount.setOnClickListener(this);

    }

    private void initData() {
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        mAdapter = new VoucherPayoutOneSubscriberBankAccountsAdapter(getViewContext(), VoucherPayoutOneDepositToBankActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rv_listofbanks.setNestedScrollingEnabled(false);
        rv_listofbanks.setLayoutManager(layoutManager);
        rv_listofbanks.setAdapter(mAdapter);

        txv_nodata.setVisibility(View.GONE);
        rv_listofbanks.setVisibility(View.VISIBLE);

        bundle = getIntent().getExtras();

        if(bundle != null){
            voucher = bundle.getParcelable(Voucher.KEY_VOUCHER_OBJECT);
        }

        try{
            grossamount = voucher.getRemainingBalance();
            txv_voucheramount.setText(CommonFunctions.currencyFormatter(String.valueOf(grossamount)));
            txv_amounttodeposit.setText(CommonFunctions.currencyFormatter(String.valueOf(grossamount)));

            voucherserialno = voucher.getVoucherSerialNo();
            vouchercode = voucher.getVoucherCode();

        }catch (Exception e){
            e.printStackTrace();
        }


        getSession();
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            getSubscriberBankAccounts(getSubscriberBankAccountsSession);
        } else{
            showNoInternetToast();
        }

    }

    private void getSubscriberBankAccounts (Callback<SubscriberBankAccountsResponse> getSubscriberBankAccountsCallback ){
        Call<SubscriberBankAccountsResponse> getsubscriberbankaccounts = RetrofitBuild.getVoucherV3Service(getViewContext())
                .getSubscriberBankAccounts(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        CommonVariables.devicetype);
        getsubscriberbankaccounts.enqueue(getSubscriberBankAccountsCallback);
    }

    private final Callback<SubscriberBankAccountsResponse> getSubscriberBankAccountsSession = new Callback<SubscriberBankAccountsResponse>() {
        @Override
        public void onResponse(Call<SubscriberBankAccountsResponse> call, Response<SubscriberBankAccountsResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){

                    try{
                        CacheManager.getInstance().saveSubscriberBankAccounts(response.body().getSubscriberBankAccountsList());
                        mGridData = CacheManager.getInstance().getSubscriberBankAccounts();

                        if(mGridData.size() > 0){
                            mAdapter.updateList(mGridData);
                        } else{
                            txv_nodata.setVisibility(View.VISIBLE);
                            rv_listofbanks.setVisibility(View.GONE);
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
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
        public void onFailure(Call<SubscriberBankAccountsResponse> call, Throwable t) {
            showErrorGlobalDialogs();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.payoutone_proceed) {
            proceedToConfirmation();
        }
        return super.onOptionsItemSelected(item);
    }

    private void proceedToConfirmation(){
        if (!SClick.check(SClick.BUTTON_CLICK)) return;

        if(mGridData != null){
            if(mGridData.size() > 0){
                if(passedSubscriberBankAccount != null){
                    if(passedSubscriberBankAccount.size() > 0){

                        try{
                            for(SubscriberBankAccounts subscriberBankAccounts : passedSubscriberBankAccount){
                                bank = subscriberBankAccounts.getBank();

                                accountno = subscriberBankAccounts.getAccountNo();
                                accountname = subscriberBankAccounts.getAccountName();
                            }

                            subscriberremarks = edt_remarks.getText().toString().trim();

                            Intent intent = new Intent(getViewContext(), VoucherPayoutOneDepositConfirmationActivity.class);
                            intent.putExtra(SubscriberBankAccounts.KEY_SUBSCRIBERBANK_BANK, bank);
                            intent.putExtra(SubscriberBankAccounts.KEY_SUBSCRIBERBANK_ACCOUNTNO, accountno);
                            intent.putExtra(SubscriberBankAccounts.KEY_SUBSCRIBERBANK_ACCOUNTNAME, accountname);
                            intent.putExtra(SubscriberBankAccounts.KEY_SUBSCRIBERBANK_REMAININGBALANCE, String.valueOf(grossamount));
                            intent.putExtra(Voucher.KEY_VOUCHERSERIALNO, voucherserialno);
                            intent.putExtra(Voucher.KEY_VOUCHERCODE, vouchercode);
                            intent.putExtra("SUBSCRIBERREMARKS", subscriberremarks);

                            Logger.debug("vanhttp", "grossamount ni van: ======= " + grossamount);

                            startActivity(intent);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    } else{
                        showToast("Please select a bank account.", GlobalToastEnum.WARNING);
                    }
                } else{
                    showToast("Please select a bank account.", GlobalToastEnum.WARNING);
                }
            } else{
                showToast("Please add a bank account to proceed.", GlobalToastEnum.WARNING);
            }
        } else{
            showToast("Please add a bank account to proceed.", GlobalToastEnum.WARNING);
        }

    }

    //CALLS DURING THE SELECTION
    public void selectAccreditedBanks (ArrayList<SubscriberBankAccounts> subscriberBankAccounts) {
        Collections.reverse(subscriberBankAccounts);
        passedSubscriberBankAccount = subscriberBankAccounts;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txv_addbankaccount: {
                //enroll bank account
                Intent intent = new Intent(getViewContext(), VoucherPayoutOneAccreditedBankActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSession();
    }
}