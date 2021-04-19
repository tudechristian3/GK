package com.goodkredit.myapplication.responses.gkearn;

import com.goodkredit.myapplication.model.gkearn.GKEarnStockistPackage;
import com.goodkredit.myapplication.model.gkearn.GKEarnSubscribers;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GKEarnPackagesResponse {
    @SerializedName("data")
    @Expose
    private List<GKEarnStockistPackage> data = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GKEarnStockistPackage> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
