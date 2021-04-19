package com.goodkredit.myapplication.responses.gkearn;

import com.goodkredit.myapplication.model.gkearn.GKEarnTopUpHistory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GKEarnTopUpHistoryResponse {

    @SerializedName("data")
    @Expose
    private List<GKEarnTopUpHistory> gkEarnTopUpHistoryList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GKEarnTopUpHistory> getGkEarnTopUpHistoryList() {
        return gkEarnTopUpHistoryList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
