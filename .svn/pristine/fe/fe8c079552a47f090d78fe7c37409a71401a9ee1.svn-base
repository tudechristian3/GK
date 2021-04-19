package com.goodkredit.myapplication.responses.coopassistant;

import com.goodkredit.myapplication.model.coopassistant.CoopRequestInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantRequestInfoResponse {
    @SerializedName("data")
    @Expose
    private List<CoopRequestInfo> coopRequestInfoList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<CoopRequestInfo> getCoopRequestInfoList() {
        return coopRequestInfoList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
