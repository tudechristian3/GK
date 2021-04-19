package com.goodkredit.myapplication.responses.gkearn;

import com.goodkredit.myapplication.model.gkearn.GKEarnSubscribers;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GKEarnSubscribersResponse {
    @SerializedName("data")
    @Expose
    private List<GKEarnSubscribers> data = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GKEarnSubscribers> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
