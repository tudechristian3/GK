package com.goodkredit.myapplication.activities.vouchers.payoutone;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.vouchers.BankDepositHistoryQueue;

public class VoucherPayoutOneBankDepositDetailsActivity extends BaseActivity {

    private BankDepositHistoryQueue bankDepositHistoryQueue = null;

    private TextView txv_datetime;
    private TextView txv_deposittxnno;
    private TextView txv_vouchertype;
    private TextView txv_grossamount;
    private TextView txv_servicecharge;
    private TextView txv_totalamount;
    private TextView txv_bankname;
    private TextView txv_accountname;
    private TextView txv_accountno;
    private TextView txv_subscriberremarks;
    private TextView txv_processingremarks;
    private TextView txv_status;

    private LinearLayout layout_subscriberremarks;
    private LinearLayout layout_processingremarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_deposit_history_queue_details);

        if(getIntent().getStringExtra("FROM").equals("HISTORY")){
            setupToolbarWithTitle("Deposit History Details");
        } else{
            setupToolbarWithTitle("Deposit Queue Details");
        }

        init();
        initData();
    }

    private void init() {
        txv_datetime = findViewById(R.id.txv_datetime);
        txv_deposittxnno = findViewById(R.id.txv_deposittxnno);
        txv_vouchertype = findViewById(R.id.txv_vouchertype);
        txv_grossamount = findViewById(R.id.txv_grossamount);
        txv_servicecharge = findViewById(R.id.txv_servicecharge);
        txv_totalamount = findViewById(R.id.txv_totalamount);
        txv_bankname = findViewById(R.id.txv_bankname);
        txv_accountname = findViewById(R.id.txv_accountname);
        txv_accountno = findViewById(R.id.txv_accountno);
        txv_subscriberremarks = findViewById(R.id.txv_subscriberremarks);
        txv_processingremarks = findViewById(R.id.txv_processingremarks);
        txv_status = findViewById(R.id.txv_status);

        layout_subscriberremarks = findViewById(R.id.layout_subscriberremarks);
        layout_processingremarks = findViewById(R.id.layout_processingremarks);
    }

    private void initData() {

        if(getIntent() != null){
            bankDepositHistoryQueue = getIntent().getParcelableExtra(BankDepositHistoryQueue.KEY_BANKDEPOSITHISTORYQUEUE_OBJECT);
        }

        txv_datetime.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(bankDepositHistoryQueue.getDateTimeIN())));
        txv_deposittxnno.setText(CommonFunctions.replaceEscapeData(bankDepositHistoryQueue.getDepositTxnNo()));
        txv_vouchertype.setText(CommonFunctions.replaceEscapeData(bankDepositHistoryQueue.getVoucherType()));
        txv_grossamount.setText(CommonFunctions.currencyFormatter(String.valueOf(bankDepositHistoryQueue.getAmount())));
        txv_servicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(bankDepositHistoryQueue.getServiceCharge())));
        txv_totalamount.setText(CommonFunctions.currencyFormatter(String.valueOf(bankDepositHistoryQueue.getTotalAmount())));
        txv_bankname.setText(CommonFunctions.replaceEscapeData(bankDepositHistoryQueue.getBank()));
        txv_accountname.setText(CommonFunctions.replaceEscapeData(bankDepositHistoryQueue.getAccountName()));
        txv_accountno.setText(CommonFunctions.replaceEscapeData(bankDepositHistoryQueue.getAccountNo()));

        String subscriberremarks = bankDepositHistoryQueue.getSubscriberRemarks();
        if(subscriberremarks != null){
            if(subscriberremarks.equals("") || subscriberremarks.equals(".")){
                layout_subscriberremarks.setVisibility(View.GONE);
            } else{
                txv_subscriberremarks.setText(CommonFunctions.replaceEscapeData(subscriberremarks));
                layout_subscriberremarks.setVisibility(View.VISIBLE);
            }
        } else{
            layout_subscriberremarks.setVisibility(View.GONE);
        }

        String processingremarks = bankDepositHistoryQueue.getRemarks();
        if(processingremarks != null){
            if(processingremarks.equals("") || processingremarks.equals(".")){
                layout_processingremarks.setVisibility(View.GONE);
            } else{
                txv_processingremarks.setText(CommonFunctions.replaceEscapeData(processingremarks));
                layout_processingremarks.setVisibility(View.VISIBLE);
            }
        } else{
            layout_processingremarks.setVisibility(View.GONE);
        }

        String status = bankDepositHistoryQueue.getStatus();
        txv_status.setText(status);
        if(status.equals("COMPLETED")){
            txv_status.setTextColor(Color.parseColor("#09B403"));
        } else if(status.equals("CANCELLED")){
            txv_status.setTextColor(Color.parseColor("#FF0000"));
        } else if(status.equals("PENDING")){
            txv_status.setTextColor(Color.parseColor("#6B6B6B"));
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
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
