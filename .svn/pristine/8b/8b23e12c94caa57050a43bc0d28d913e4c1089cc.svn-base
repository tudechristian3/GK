package com.goodkredit.myapplication.responses.coopassistant;

import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantCreditsHistory;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMemberCredits;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantCreditsHistoryResponse {
    @SerializedName("data")
    @Expose
    private List<CoopAssistantCreditsHistory> data = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<CoopAssistantCreditsHistory> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
