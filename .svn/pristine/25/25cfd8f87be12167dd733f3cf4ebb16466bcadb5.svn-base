package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.PaymentChannels;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetPaymentPartnersResponse {
    @SerializedName("data")
    @Expose
    private List<PaymentChannels> paymentChannels = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PaymentChannels> getPaymentChannels() {
        return paymentChannels;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
