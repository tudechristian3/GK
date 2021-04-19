package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantPayments;

public class CoopAssistantPaymentDetailsFragment extends BaseFragment {

    private CoopAssistantPayments cooppayments = null;

    private TextView txv_paymenttype;
    private TextView txv_paymenttxnid;
    private TextView txv_datetimepaid;
    private TextView txv_merchantname;
    private TextView txv_amount;
    private TextView txv_custsercharge;
    private TextView txv_merchsercharge;
    private TextView txv_totalamountpaid;
    private TextView txv_transactionmedium;
    private TextView txv_paymentoption;
    private TextView txv_status;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_payment_details, container, false);

        init(view);
        initData();

        return view;
    }

    private void init(View view) {

        txv_paymenttype = view.findViewById(R.id.txv_coop_paymenttype);
        txv_paymenttxnid = view.findViewById(R.id.txv_coop_paymenttxnid);
        txv_datetimepaid = view.findViewById(R.id.txv_coop_datetimepaid);
        txv_merchantname = view.findViewById(R.id.txv_coop_merchantname);
        txv_amount = view.findViewById(R.id.txv_coop_amount);
        txv_custsercharge = view.findViewById(R.id.txv_coop_custsercharge);
        txv_merchsercharge = view.findViewById(R.id.txv_coop_merchsercharge);
        txv_totalamountpaid = view.findViewById(R.id.txv_coop_totalamountpaid);
        txv_transactionmedium = view.findViewById(R.id.txv_coop_transactionmedium);
        txv_paymentoption = view.findViewById(R.id.txv_coop_paymentoption);
        txv_status = view.findViewById(R.id.txv_coop_status);

    }

    private void initData() {

        try{
            cooppayments = getArguments().getParcelable(CoopAssistantPayments.KEY_COOPPAYMENTS);

            txv_paymenttype.setText(CommonFunctions.replaceEscapeData(cooppayments.getPaymentName()));
            txv_paymenttxnid.setText(CommonFunctions.replaceEscapeData(cooppayments.getPaymentTxnID()));
            txv_datetimepaid.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(cooppayments.getDateTimePaid())));
            txv_merchantname.setText(CommonFunctions.replaceEscapeData(cooppayments.getMerchantName()));
            txv_amount.setText(CommonFunctions.currencyFormatter(String.valueOf(cooppayments.getAmount())));
            txv_custsercharge.setText(CommonFunctions.currencyFormatter(String.valueOf(cooppayments.getCustomerServiceCharge())));
            txv_merchsercharge.setText(CommonFunctions.currencyFormatter(String.valueOf(cooppayments.getMerchantServiceCharge())));
            txv_totalamountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(cooppayments.getTotalAmount())));
            txv_transactionmedium.setText(CommonFunctions.replaceEscapeData(cooppayments.getTransactionMedium()));
            txv_paymentoption.setText(CommonFunctions.replaceEscapeData(cooppayments.getPaymentOption()));
            txv_status.setText(CommonFunctions.replaceEscapeData(cooppayments.getStatus()));

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
