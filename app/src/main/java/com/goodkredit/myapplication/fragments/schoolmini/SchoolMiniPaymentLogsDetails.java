package com.goodkredit.myapplication.fragments.schoolmini;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;

public class SchoolMiniPaymentLogsDetails extends BaseFragment {

    private String session = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //SCHOOL
    private String schoolid = "";
    private String studentid = "";
    private String course = "";
    private String yearlevel = "";
    private String firstname = "";
    private String middlename = "";
    private String lastname = "";
    private String mobilenumber = "";
    private String emailaddress = "";
    private String address = "";

    //STUDENT INFORMATION
    private TextView txn_paymentid;
    private LinearLayout txn_billingid_container;
    private TextView txn_billingid;
    private TextView txn_amount;
    private TextView txn_merchantservice;
    private TextView txn_customerservice;
    private TextView txn_totalamount;
    private TextView txn_discount;
    private TextView txn_transamedium;

    private String paymenttxn = "";
    private String billingid = "";
    private String amount = "";
    private String merchantservicecharge = "";
    private String customerservicecharge = "";
    private String resellerdiscount = "";
    private String totalamount = "";
    private String transactionmedium = "";

    //REMARKS
    private LinearLayout layout_school_remarks;
    private TextView txv_remarks;
    private String str_remarks = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmini_paymentdetails, container, false);
        try {
            init(view);

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        txn_paymentid = (TextView) view.findViewById(R.id.txn_paymentid);
        txn_billingid_container = (LinearLayout) view.findViewById(R.id.txn_billingid_container);
        txn_billingid = (TextView) view.findViewById(R.id.txn_billingid);
        txn_amount = (TextView) view.findViewById(R.id.txn_amount);
        txn_merchantservice = (TextView) view.findViewById(R.id.txn_merchantservice);
        txn_customerservice = (TextView) view.findViewById(R.id.txn_customerservice);
        txn_discount = (TextView) view.findViewById(R.id.txn_discount);
        txn_transamedium = (TextView) view.findViewById(R.id.txn_transamedium);
        txn_totalamount = (TextView) view.findViewById(R.id.txn_totalamount);

        //REMARKS
        layout_school_remarks = (LinearLayout) view.findViewById(R.id.layout_school_remarks);
        txv_remarks = (TextView) view.findViewById(R.id.txv_school_remarks);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        mdb = new DatabaseHandler(getViewContext());

        schoolid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCSERVICECODE);

        if (getArguments() != null) {
            paymenttxn = getArguments().getString("PAYMENTTXN");
            billingid = getArguments().getString("BILLINGID");
            amount = getArguments().getString("AMOUNT");
            merchantservicecharge = getArguments().getString("MERCHANTSERVICECHARGE");
            customerservicecharge = getArguments().getString("CUSTOMERSERVICECHARGE");
            resellerdiscount = getArguments().getString("RESELLERDISCOUNT");
            totalamount = getArguments().getString("TOTALAMOUNT");
            transactionmedium = getArguments().getString("TRANSACTIONMEDIUM");
            str_remarks = getArguments().getString("REMARKS");
        }

        setPaymentInformation();
    }

    private void setPaymentInformation() {
        txn_paymentid.setText(paymenttxn);
        if(billingid.trim().equals(".") || billingid.trim().equals("")) {
            txn_billingid_container.setVisibility(View.GONE);
        }
        else {
            txn_billingid_container.setVisibility(View.VISIBLE);
            txn_billingid.setText(billingid);
        }

        if(str_remarks.equals(".") || str_remarks.equals("")){
            layout_school_remarks.setVisibility(View.GONE);
        } else{
            layout_school_remarks.setVisibility(View.VISIBLE);
        }

        txn_amount.setText(CommonFunctions.currencyFormatter(amount));
        txn_merchantservice.setText(CommonFunctions.currencyFormatter(merchantservicecharge));
        txn_customerservice.setText(CommonFunctions.currencyFormatter(customerservicecharge));
        txn_discount.setText(CommonFunctions.currencyFormatter(resellerdiscount));
        txn_totalamount.setText(CommonFunctions.currencyFormatter(totalamount));
        txn_transamedium.setText(transactionmedium);
        txv_remarks.setText(str_remarks);

        Logger.debug("vanhttp", "remarks2: ======= " + str_remarks);
    }

}