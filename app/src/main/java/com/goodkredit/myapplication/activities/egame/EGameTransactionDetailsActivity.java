package com.goodkredit.myapplication.activities.egame;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.egame.EGameTransactions;

public class EGameTransactionDetailsActivity extends BaseActivity {

    private EGameTransactions eGameTransactions = null;

    private TextView txv_datetime;
    private TextView txv_transactionno;
    private TextView txv_mobileno;
    private TextView txv_productcode;
    private TextView txv_amount;
    private TextView txv_PIN;
    private TextView txv_status;
    private LinearLayout layout_pin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egame_transaction_details);

        init();
        initData();
    }

    private void init() {
        setupToolbarWithTitle("Transaction Details");

        txv_datetime = findViewById(R.id.txv_datetime);
        txv_transactionno = findViewById(R.id.txv_transactionno);
        txv_mobileno = findViewById(R.id.txv_mobileno);
        txv_productcode = findViewById(R.id.txv_productcode);
        txv_amount = findViewById(R.id.txv_amount);
        txv_PIN = findViewById(R.id.txv_PIN);
        txv_status = findViewById(R.id.txv_status);
        layout_pin = findViewById(R.id.layout_pin);
    }

    private void initData() {
        if(getIntent() != null){
            eGameTransactions = getIntent().getParcelableExtra(EGameTransactions.KEY_EGAMETRANSACTIONS);
        }

        try{

            String status = CommonFunctions.replaceEscapeData(eGameTransactions.getTxnStatus());
            txv_datetime.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(eGameTransactions.getDateTimeIN())));
            txv_transactionno.setText(CommonFunctions.replaceEscapeData(eGameTransactions.getTransactionNo()));
            txv_mobileno.setText(CommonFunctions.replaceEscapeData("+".concat(eGameTransactions.getMobileTarget())));
            txv_productcode.setText(CommonFunctions.replaceEscapeData(eGameTransactions.getProductCode()));
            txv_amount.setText(CommonFunctions.currencyFormatter(String.valueOf(eGameTransactions.getAmount())));
            txv_PIN.setText(CommonFunctions.replaceEscapeData(eGameTransactions.getPIN()));
            txv_status.setText(status);

            if(status.equals("SUCCESS")){
                layout_pin.setVisibility(View.VISIBLE);
            } else{
                layout_pin.setVisibility(View.GONE);
            }

        }catch (Exception e){
            e.printStackTrace();
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
