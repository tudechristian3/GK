package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.PublicSponsorPromos;
import com.goodkredit.myapplication.bean.SkycablePPV;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetPublicSponsorPromoResponse {
    @SerializedName("data")
    @Expose
    private List<PublicSponsorPromos> publicSponsorPromos = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PublicSponsorPromos> getPublicSponsorPromos() {
        return publicSponsorPromos;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
