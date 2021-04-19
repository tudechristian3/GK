package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 2/21/2017.
 */

public class Outlets implements Parcelable {

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

    public Outlets(String outletBranchName, String outletStreetAddress,
                   String outletCity, String outletProvince, String outletCountry,
                   String outletTelephone, String outletMobile, String outletMerchantID,
                   String outletBranchID, String outletAuthorisedRepresentative, String outletZip,
                   String outletLongitude, String outletLatitude, String outletAuthorisedEmailAddress,
                   String outletFax, String outletDateAdded, String outletStatus, String outletMerchantGroup) {
        this.outletBranchName = outletBranchName;
        this.outletStreetAddress = outletStreetAddress;
        this.outletCity = outletCity;
        this.outletProvince = outletProvince;
        this.outletCountry = outletCountry;
        this.outletTelephone = outletTelephone;
        this.outletMobile = outletMobile;
        this.outletMerchantID = outletMerchantID;
        this.outletBranchID = outletBranchID;
        this.outletAuthorisedRepresentative = outletAuthorisedRepresentative;
        this.outletZip = outletZip;
        this.outletLongitude = outletLongitude;
        this.outletLatitude = outletLatitude;
        this.outletAuthorisedEmailAddress = outletAuthorisedEmailAddress;
        this.outletFax = outletFax;
        this.outletDateAdded = outletDateAdded;
        this.outletStatus = outletStatus;
        this.outletMerchantGroup = outletMerchantGroup;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Outlets(Parcel in) {
        this.outletBranchName = in.readString();
        this.outletStreetAddress = in.readString();
        this.outletCity = in.readString();
        this.outletProvince = in.readString();
        this.outletCountry = in.readString();
        this.outletTelephone = in.readString();
        this.outletMobile = in.readString();
        this.outletMerchantID = in.readString();
        this.outletBranchID = in.readString();
        this.outletAuthorisedRepresentative = in.readString();
        this.outletZip = in.readString();
        this.outletLongitude = in.readString();
        this.outletLatitude = in.readString();
        this.outletAuthorisedEmailAddress = in.readString();
        this.outletFax = in.readString();
        this.outletDateAdded = in.readString();
        this.outletStatus = in.readString();
        this.outletMerchantGroup = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(outletBranchName);
        dest.writeString(outletStreetAddress);
        dest.writeString(outletCity);
        dest.writeString(outletProvince);
        dest.writeString(outletCountry);
        dest.writeString(outletTelephone);
        dest.writeString(outletMobile);
        dest.writeString(outletMerchantID);
        dest.writeString(outletBranchID);
        dest.writeString(outletAuthorisedRepresentative);
        dest.writeString(outletZip);
        dest.writeString(outletLongitude);
        dest.writeString(outletLatitude);
        dest.writeString(outletAuthorisedEmailAddress);
        dest.writeString(outletFax);
        dest.writeString(outletDateAdded);
        dest.writeString(outletStatus);
        dest.writeString(outletMerchantGroup);
    }

    public static final Creator<Outlets> CREATOR = new Creator<Outlets>() {
        @Override
        public Outlets createFromParcel(Parcel source) {
            return new Outlets(source);
        }

        @Override
        public Outlets[] newArray(int size) {
            return new Outlets[size];
        }
    };

    public String getOutletName() {
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

    public String getOutletBranchName() {
        return outletBranchName;
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
