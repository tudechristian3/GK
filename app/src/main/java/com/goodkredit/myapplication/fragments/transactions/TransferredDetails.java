package com.goodkredit.myapplication.fragments.transactions;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Transaction;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Ban_Lenovo on 1/18/2017.
 */

public class TransferredDetails extends BaseActivity {

    private Toolbar toolbar;

    private MaterialEditText type;
    private MaterialEditText from;
    private MaterialEditText to;
    private MaterialEditText voucherCode;
    private MaterialEditText amount;
    private MaterialEditText date;
    private MaterialEditText mobileNum;
    private MaterialEditText emailAdd;

    private Transaction transaction;

    private CommonFunctions cf;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferred_details);

        try {

            setupToolbar();

            type = (MaterialEditText) findViewById(R.id.transferredDetailsType);
            from = (MaterialEditText) findViewById(R.id.transferredDetailsFrom);
            to = (MaterialEditText) findViewById(R.id.transferredDetailsTo);
            voucherCode = (MaterialEditText) findViewById(R.id.transferredDetailsVoucherCode);
            amount = (MaterialEditText) findViewById(R.id.transferredDetailsAmount);
            date = (MaterialEditText) findViewById(R.id.transferredDetailsDate);
            mobileNum = (MaterialEditText) findViewById(R.id.transferredDetailsMobileNumber);
            emailAdd = (MaterialEditText) findViewById(R.id.transferredDetailsEmailAddress);

            transaction = (Transaction) getIntent().getSerializableExtra("TRANSFERRED_OBJECT");
            String voucherCodes = transaction.getVoucherCode();
            String vcode = voucherCodes.substring(0, 2) + "-" + voucherCodes.substring(2, 6) + "-" + voucherCodes.substring(6, 11) + "-" + voucherCodes.substring(11, 12);

            if (transaction.getTransferType().toUpperCase().equals("BORROWER"))
                type.setText("SUBSCRIBER");
            else
                type.setText(transaction.getTransferType());

            if (type.getText().toString().equals("EMAIL")) {
                emailAdd.setVisibility(View.VISIBLE);
                emailAdd.setText(transaction.getEmaillAddress());
            } else if (type.getText().toString().equals("SMS")) {
                mobileNum.setVisibility(View.VISIBLE);
                mobileNum.setText("+" + transaction.getMobileNumber());
            }

            from.setText(transaction.getSender());
            to.setText(transaction.getReceiver());
            voucherCode.setText(vcode);
            amount.setText(transaction.getAmountTransferred());
            date.setText(cf.convertDate(transaction.getDateCompleted()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
