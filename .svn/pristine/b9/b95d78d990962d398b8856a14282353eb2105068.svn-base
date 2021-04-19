package com.goodkredit.myapplication.responses.skycable;

import com.goodkredit.myapplication.bean.SkycablePPVHistory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetSkycablePpvHistoryResponse {
    @SerializedName("data")
    @Expose
    private List<SkycablePPVHistory> skycablePPVHistory = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SkycablePPVHistory> getSkycablePPVHistory() {
        return skycablePPVHistory;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
