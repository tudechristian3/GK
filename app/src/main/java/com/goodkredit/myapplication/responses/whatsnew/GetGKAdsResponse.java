package com.goodkredit.myapplication.responses.whatsnew;

import com.goodkredit.myapplication.model.gkads.GKAds;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetGKAdsResponse {

    @SerializedName("data")
    @Expose
    private List<GKAds> ads = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<GKAds> getAds() {
        return ads;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
