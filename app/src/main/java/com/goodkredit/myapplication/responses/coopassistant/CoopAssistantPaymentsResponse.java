package com.goodkredit.myapplication.responses.coopassistant;

import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantPayments;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantPaymentsResponse {
    @SerializedName("data")
    @Expose
    private List<CoopAssistantPayments> copAssistantPaymentList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<CoopAssistantPayments> getCoopAssistantPaymentList() {
        return copAssistantPaymentList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
