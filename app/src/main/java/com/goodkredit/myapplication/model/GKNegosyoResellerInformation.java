package com.goodkredit.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GKNegosyoResellerInformation {

    @SerializedName("PendingApplication")
    @Expose
    private GKNegosyoPendingApplication PendingApplication = null;
    @SerializedName("ResellerInfo")
    @Expose
    private GKNegosyoResellerInfo ResellerInfo = null;
    @SerializedName("ResellerPackage")
    @Expose
    private GKNegosyPackage ResellerPackage = null;


    public GKNegosyoPendingApplication getPendingApplication() {
        return PendingApplication;
    }

    public GKNegosyoResellerInfo getResellerInfo() {
        return ResellerInfo;
    }

    public GKNegosyPackage getResellerPackage() {
        return ResellerPackage;
    }
}
