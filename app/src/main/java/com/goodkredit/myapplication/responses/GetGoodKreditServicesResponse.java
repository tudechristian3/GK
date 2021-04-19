package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.bean.GKServiceBadge;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetGoodKreditServicesResponse {

    @SerializedName("data")
    @Expose
    private List<GKService> gkServiceList = new ArrayList<>();
    @SerializedName("badge")
    @Expose
    private List<GKServiceBadge> gkServiceBadges = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GKService> getGkServiceList() {
        return gkServiceList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<GKServiceBadge> getGkServiceBadges() {
        return gkServiceBadges;
    }
}
