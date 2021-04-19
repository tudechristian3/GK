package com.goodkredit.myapplication.responses.dropoff;

import com.goodkredit.myapplication.model.dropoff.PaymentRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetPaymentRequestPendingResponse {
    @SerializedName("data")
    @Expose
    private List<PaymentRequest> PaymentRequestPendingList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PaymentRequest> getPaymentRequestPendingList() {
        return PaymentRequestPendingList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
