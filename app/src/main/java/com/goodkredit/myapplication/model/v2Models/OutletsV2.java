package com.goodkredit.myapplication.model.v2Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutletsV2 {

    @SerializedName("BranchName")
    @Expose
    private String outletBranchName;
    @SerializedName("StreetAddress")
    @Expose
    private String outletStreetAddress;
    @SerializedName("City")
    @Expose
    private String outletCity;
    @SerializedName("Province")
    @Expose
    private String outletProvince;
    @SerializedName("Country")
    @Expose
    private String outletCountry;
    @SerializedName("AuthorisedTelNo")
    @Expose
    private String outletTelephone;
    @SerializedName("AuthorisedMobileNo")
    @Expose
    private String outletMobile;
    @SerializedName("MerchantID")
    @Expose
    private String outletMerchantID;
    @SerializedName("BranchID")
    @Expose
    private String outletBranchID;
    @SerializedName("AuthorisedRepresentative")
    @Expose
    private String outletAuthorisedRepresentative;
    @SerializedName("Zip")
    @Expose
    private String outletZip;
    @SerializedName("Longitude")
    @Expose
    private String outletLongitude;
    @SerializedName("Latitude")
    @Expose
    private String outletLatitude;
    @SerializedName("AuthorisedEmailAddress")
    @Expose
    private String outletAuthorisedEmailAddress;
    @SerializedName("Fax")
    @Expose
    private String outletFax;
    @SerializedName("DateAdded")
    @Expose
    private String outletDateAdded;
    @SerializedName("Status")
    @Expose
    private String outletStatus;
    @SerializedName("MerchantGroup")
    @Expose
    private String outletMerchantGroup;


    public String getOutletBranchName() {
        return outletBranchName;
    }

    public String getOutletStreetAddress() {
        return outletStreetAddress;
    }

    public String getOutletCity() {
        return outletCity;
    }

    public String getOutletProvince() {
        return outletProvince;
    }

    public String getOutletCountry() {
        return outletCountry;
    }

    public String getOutletTelephone() {
        return outletTelephone;
    }

    public String getOutletMobile() {
        return outletMobile;
    }

    public String getOutletMerchantID() {
        return outletMerchantID;
    }

    public String getOutletBranchID() {
        return outletBranchID;
    }

    public String getOutletAuthorisedRepresentative() {
        return outletAuthorisedRepresentative;
    }

    public String getOutletZip() {
        return outletZip;
    }

    public String getOutletLongitude() {
        return outletLongitude;
    }

    public String getOutletLatitude() {
        return outletLatitude;
    }

    public String getOutletAuthorisedEmailAddress() {
        return outletAuthorisedEmailAddress;
    }

    public String getOutletFax() {
        return outletFax;
    }

    public String getOutletDateAdded() {
        return outletDateAdded;
    }

    public String getOutletStatus() {
        return outletStatus;
    }

    public String getOutletMerchantGroup() {
        return outletMerchantGroup;
    }
}
