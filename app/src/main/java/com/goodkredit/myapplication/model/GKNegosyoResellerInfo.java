package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKNegosyoResellerInfo {

    @SerializedName("ResellerID")
    @Expose
    private String ResellerID;
    @SerializedName("DistributorID")
    @Expose
    private String DistributorID;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("DistributorName")
    @Expose
    private String DistributorName;

    public String getResellerID() {
        return ResellerID;
    }

    public String getDistributorID() {
        return DistributorID;
    }

    public String getStatus() {
        return Status;
    }

    public String getDistributorName() {
        return DistributorName;
    }
}
