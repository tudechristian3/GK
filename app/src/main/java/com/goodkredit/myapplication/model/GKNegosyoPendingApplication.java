package com.goodkredit.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GKNegosyoPendingApplication {

    @SerializedName("RegistrationID")
    @Expose
    private String RegistrationID;
    @SerializedName("DistributorID")
    @Expose
    private String DistributorID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("EmailAddress")
    @Expose
    private String EmailAddress;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("Birthday")
    @Expose
    private String Birthday;
    @SerializedName("ServiceArea")
    @Expose
    private String ServiceArea;
    @SerializedName("StreetAddress")
    @Expose
    private String StreetAddress;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("Province")
    @Expose
    private String Province;
    @SerializedName("Country")
    @Expose
    private String Country;
    @SerializedName("Longitude")
    @Expose
    private String Longitude;
    @SerializedName("Latitude")
    @Expose
    private String Latitude;
    @SerializedName("ZipCode")
    @Expose
    private String ZipCode;
    @SerializedName("PackageID")
    @Expose
    private String PackageID;
    @SerializedName("PackageName")
    @Expose
    private String PackageName;
    @SerializedName("TotalAmount")
    @Expose
    private double TotalAmount;
    @SerializedName("PaymentType")
    @Expose
    private String PaymentType;
    @SerializedName("Status")
    @Expose
    private String Status;

    public String getRegistrationID() {
        return RegistrationID;
    }

    public String getDistributorID() {
        return DistributorID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getGender() {
        return Gender;
    }

    public String getBirthday() {
        return Birthday;
    }

    public String getServiceArea() {
        return ServiceArea;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public String getCity() {
        return City;
    }

    public String getProvince() {
        return Province;
    }

    public String getCountry() {
        return Country;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public String getPackageID() {
        return PackageID;
    }

    public String getPackageName() {
        return PackageName;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public String getStatus() {
        return Status;
    }
}
