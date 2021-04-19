package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User-PC on 10/27/2017.
 */

public class PaymentChannels implements Parcelable {
    @SerializedName("NetworkID")
    @Expose
    private String NetworkID;
    @SerializedName("NetworkName")
    @Expose
    private String NetworkName;
    @SerializedName("NetworkType")
    @Expose
    private String NetworkType;
    @SerializedName("NetworkGroup")
    @Expose
    private String NetworkGroup;
    @SerializedName("NetworkStatus")
    @Expose
    private String NetworkStatus;
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
    private String Zip;
    @SerializedName("Country")
    @Expose
    private String Country;
    @SerializedName("Longitude")
    @Expose
    private String Longitude;
    @SerializedName("Latitude")
    @Expose
    private String Latitude;
    @SerializedName("Representative")
    @Expose
    private String Representative;
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
    @SerializedName("NatureOfBusiness")
    @Expose
    private String NatureOfBusiness;
    @SerializedName("TIN")
    @Expose
    private String TIN;
    @SerializedName("OrganizationType")
    @Expose
    private String OrganizationType;
    @SerializedName("NoOfBranches")
    @Expose
    private String NoOfBranches;
    @SerializedName("DateTimeAdded")
    @Expose
    private String DateTimeAdded;
    @SerializedName("AddedBy")
    @Expose
    private String AddedBy;
    @SerializedName("Logo")
    @Expose
    private String Logo;

    protected PaymentChannels(Parcel in) {
        NetworkID = in.readString();
        NetworkName = in.readString();
        NetworkType = in.readString();
        NetworkGroup = in.readString();
        NetworkStatus = in.readString();
        StreetAddress = in.readString();
        City = in.readString();
        Province = in.readString();
        Zip = in.readString();
        Country = in.readString();
        Longitude = in.readString();
        Latitude = in.readString();
        Representative = in.readString();
        AuthorisedEmailAddress = in.readString();
        AuthorisedTelNo = in.readString();
        AuthorisedMobileNo = in.readString();
        Fax = in.readString();
        NatureOfBusiness = in.readString();
        TIN = in.readString();
        OrganizationType = in.readString();
        NoOfBranches = in.readString();
        DateTimeAdded = in.readString();
        AddedBy = in.readString();
        Logo = in.readString();
    }

    public static final Creator<PaymentChannels> CREATOR = new Creator<PaymentChannels>() {
        @Override
        public PaymentChannels createFromParcel(Parcel in) {
            return new PaymentChannels(in);
        }

        @Override
        public PaymentChannels[] newArray(int size) {
            return new PaymentChannels[size];
        }
    };

    public PaymentChannels(String networkID, String networkName, String networkType, String networkGroup, String networkStatus, String streetAddress, String city, String province, String zip, String country, String longitude, String latitude, String representative, String authorisedEmailAddress, String authorisedTelNo, String authorisedMobileNo, String fax, String natureOfBusiness, String TIN, String organizationType, String noOfBranches, String dateTimeAdded, String addedBy, String logo) {
        NetworkID = networkID;
        NetworkName = networkName;
        NetworkType = networkType;
        NetworkGroup = networkGroup;
        NetworkStatus = networkStatus;
        StreetAddress = streetAddress;
        City = city;
        Province = province;
        Zip = zip;
        Country = country;
        Longitude = longitude;
        Latitude = latitude;
        Representative = representative;
        AuthorisedEmailAddress = authorisedEmailAddress;
        AuthorisedTelNo = authorisedTelNo;
        AuthorisedMobileNo = authorisedMobileNo;
        Fax = fax;
        NatureOfBusiness = natureOfBusiness;
        this.TIN = TIN;
        OrganizationType = organizationType;
        NoOfBranches = noOfBranches;
        DateTimeAdded = dateTimeAdded;
        AddedBy = addedBy;
        Logo = logo;
    }

    public String getNetworkID() {
        return NetworkID;
    }

    public String getNetworkName() {
        return NetworkName;
    }

    public String getNetworkType() {
        return NetworkType;
    }

    public String getNetworkGroup() {
        return NetworkGroup;
    }

    public String getNetworkStatus() {
        return NetworkStatus;
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

    public String getZip() {
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

    public String getRepresentative() {
        return Representative;
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

    public String getNatureOfBusiness() {
        return NatureOfBusiness;
    }

    public String getTIN() {
        return TIN;
    }

    public String getOrganizationType() {
        return OrganizationType;
    }

    public String getNoOfBranches() {
        return NoOfBranches;
    }

    public String getDateTimeAdded() {
        return DateTimeAdded;
    }

    public String getAddedBy() {
        return AddedBy;
    }

    public String getLogo() {
        return Logo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NetworkID);
        dest.writeString(NetworkName);
        dest.writeString(NetworkType);
        dest.writeString(NetworkGroup);
        dest.writeString(NetworkStatus);
        dest.writeString(StreetAddress);
        dest.writeString(City);
        dest.writeString(Province);
        dest.writeString(Zip);
        dest.writeString(Country);
        dest.writeString(Longitude);
        dest.writeString(Latitude);
        dest.writeString(Representative);
        dest.writeString(AuthorisedEmailAddress);
        dest.writeString(AuthorisedTelNo);
        dest.writeString(AuthorisedMobileNo);
        dest.writeString(Fax);
        dest.writeString(NatureOfBusiness);
        dest.writeString(TIN);
        dest.writeString(OrganizationType);
        dest.writeString(NoOfBranches);
        dest.writeString(DateTimeAdded);
        dest.writeString(AddedBy);
        dest.writeString(Logo);
    }
}
