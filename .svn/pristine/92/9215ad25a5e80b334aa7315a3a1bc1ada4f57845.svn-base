package com.goodkredit.myapplication.responses.uno;

import com.goodkredit.myapplication.model.UNORedemption;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetUNORedemptionHistoryResponse {
    @SerializedName("data")
    @Expose
    private List<UNORedemption> data = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<UNORedemption> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
