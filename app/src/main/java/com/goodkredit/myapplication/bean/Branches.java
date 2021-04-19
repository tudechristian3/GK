package com.goodkredit.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User-PC on 11/6/2017.
 */

public class Branches {

    @SerializedName("NetworkID")
    @Expose
    private String NetworkID;
    @SerializedName("OutletID")
    @Expose
    private String OutletID;
    @SerializedName("OutletName")
    @Expose
    private String OutletName;
    @SerializedName("AuthorisedRepresentative")
    @Expose
    private String AuthorisedRepresentative;
    @SerializedName("StreetAddress")
    @Expose
    private String StreetAddress;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("Province")
    @Expose
    private String Province;
    @SerializedName("Zip")
    @Expose
    private int Zip;
    @SerializedName("Country")
    @Expose
    private String Country;
    @SerializedName("Longitude")
    @Expose
    private String Longitude;
    @SerializedName("Latitude")
    @Expose
    private String Latitude;
    @SerializedName("AuthorisedEmailAddress")
    @Expose
    private String AuthorisedEmailAddress;
    @SerializedName("AuthorisedTelNo")
    @Expose
    private String AuthorisedTelNo;
    @SerializedName("AuthorisedMobileNo")
    @Expose
    private String AuthorisedMobileNo;
    @SerializedName("Fax")
    @Expose
    private String Fax;
    @SerializedName("DateTimeAdded")
    @Expose
    private String DateTimeAdded;
    @SerializedName("DateTimeUpdated")
    @Expose
    private String DateTimeUpdated;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("CalculatedDistance")
    @Expose
    private double CalculatedDistance;

    public Branches(String networkID, String outletID, String outletName, String authorisedRepresentative, String streetAddress, String city, String province, int zip, String country, String longitude, String latitude, String authorisedEmailAddress, String authorisedTelNo, String authorisedMobileNo, String fax, String dateTimeAdded, String dateTimeUpdated, String status, double calculatedDistance) {
        NetworkID = networkID;
        OutletID = outletID;
        OutletName = outletName;
        AuthorisedRepresentative = authorisedRepresentative;
        StreetAddress = streetAddress;
        City = city;
        Province = province;
        Zip = zip;
        Country = country;
        Longitude = longitude;
        Latitude = latitude;
        AuthorisedEmailAddress = authorisedEmailAddress;
        AuthorisedTelNo = authorisedTelNo;
        AuthorisedMobileNo = authorisedMobileNo;
        Fax = fax;
        DateTimeAdded = dateTimeAdded;
        DateTimeUpdated = dateTimeUpdated;
        Status = status;
        CalculatedDistance = calculatedDistance;
    }

    public String getNetworkID() {
        return NetworkID;
    }

    public String getOutletID() {
        return OutletID;
    }

    public String getOutletName() {
        return OutletName;
    }

    public String getAuthorisedRepresentative() {
        return AuthorisedRepresentative;
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

    public int getZip() {
        return Zip;
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

    public String getAuthorisedEmailAddress() {
        return AuthorisedEmailAddress;
    }

    public String getAuthorisedTelNo() {
        return AuthorisedTelNo;
    }

    public String getAuthorisedMobileNo() {
        return AuthorisedMobileNo;
    }

    public String getFax() {
        return Fax;
    }

    public String getDateTimeAdded() {
        return DateTimeAdded;
    }

    public String getDateTimeUpdated() {
        return DateTimeUpdated;
    }

    public String getStatus() {
        return Status;
    }

    public double getCalculatedDistance() {
        return CalculatedDistance;
    }
}
