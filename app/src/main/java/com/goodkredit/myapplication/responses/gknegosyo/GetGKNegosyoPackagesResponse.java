package com.goodkredit.myapplication.responses.gknegosyo;

import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.model.GKNegosyPackage;
import com.goodkredit.myapplication.model.GKNegosyoPackagesAndDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetGKNegosyoPackagesResponse {

    @SerializedName("data")
    @Expose
    private GKNegosyoPackagesAndDetails data;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public GetGKNegosyoPackagesResponse() {
    }

    public GKNegosyoPackagesAndDetails getGetGKNegosyoPackagesAndDetails() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
