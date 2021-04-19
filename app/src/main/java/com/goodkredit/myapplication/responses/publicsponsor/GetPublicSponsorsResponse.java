package com.goodkredit.myapplication.responses.publicsponsor;

import com.goodkredit.myapplication.model.publicsponsor.PublicSponsor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetPublicSponsorsResponse {

    @SerializedName("data")
    @Expose
    private List<PublicSponsor> PublicSponsorList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PublicSponsor> getPublicSponsorList() {
        return PublicSponsorList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
