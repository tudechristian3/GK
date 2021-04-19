package com.goodkredit.myapplication.responses.coopassistant;

import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantTransactionHistory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantTransactionHistoryResponse {
    @SerializedName("data")
    @Expose
    private List<CoopAssistantTransactionHistory> data = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<CoopAssistantTransactionHistory> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}


