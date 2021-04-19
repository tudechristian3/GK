package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.PaymentSummary;
import com.goodkredit.myapplication.bean.SubscriberBillSummary;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 7/15/2017.
 */

public class GetBillingSummaryResponse {

    @SerializedName("billings")
    @Expose
    private List<SubscriberBillSummary> subscriberBillSummaryList = new ArrayList<>();
    @SerializedName("paymentlogs")
    @Expose
    private List<PaymentSummary> paymentSummaryList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SubscriberBillSummary> getSubscriberBillSummaryList() {
        return subscriberBillSummaryList;
    }

    public List<PaymentSummary> getPaymentSummaryList() {
        return paymentSummaryList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
