package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User-PC on 11/7/2017.
 */

public class Merchants implements Parcelable {
    @SerializedName("MerchantID")
    @Expose
    private String MerchantID;
    @SerializedName("MerchantName")
    @Expose
    private String MerchantName;
    @SerializedName("MerchantType")
    @Expose
    private String MerchantType;
    @SerializedName("MerchantGroup")
    @Expose
    private String MerchantGroup;
    @SerializedName("MerchantStatus")
    @Expose
    private String MerchantStatus;
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
    @SerializedName("MerchantLogo")
    @Expose
    private String MerchantLogo;
    @SerializedName("isFeatured")
    @Expose
    private String isFeatured;
    @SerializedName("FeatureAddsPath")
    @Expose
    private String FeatureAddsPath;
    @SerializedName("OrganizationLogo")
    @Expose
    private String OrganizationLogo;

    public Merchants(String organizationType) {
        OrganizationType = organizationType;
    }

    public Merchants(String merchantID, String merchantName, String merchantType, String merchantGroup, String merchantStatus, String streetAddress, String city, String province, String zip, String country, String longitude, String latitude, String representative, String authorisedEmailAddress, String authorisedTelNo, String authorisedMobileNo, String fax, String natureOfBusiness, String TIN, String organizationType, String noOfBranches, String merchantLogo, String isFeatured, String featureAddsPath, String organizationLogo) {
        MerchantID = merchantID;
        MerchantName = merchantName;
        MerchantType = merchantType;
        MerchantGroup = merchantGroup;
        MerchantStatus = merchantStatus;
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
        MerchantLogo = merchantLogo;
        this.isFeatured = isFeatured;
        FeatureAddsPath = featureAddsPath;
        OrganizationLogo = organizationLogo;
    }

    protected Merchants(Parcel in) {
        MerchantID = in.readString();
        MerchantName = in.readString();
        MerchantType = in.readString();
        MerchantGroup = in.readString();
        MerchantStatus = in.readString();
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
        MerchantLogo = in.readString();
        isFeatured = in.readString();
        FeatureAddsPath = in.readString();
        OrganizationLogo = in.readString();
    }

    public static final Creator<Merchants> CREATOR = new Creator<Merchants>() {
        @Override
        public Merchants createFromParcel(Parcel in) {
            return new Merchants(in);
        }

        @Override
        public Merchants[] newArray(int size) {
            return new Merchants[size];
        }
    };

    public String getMerchantID() {
        return MerchantID;
    }

    public String getMerchantName() {
        return MerchantName;
    }

    public String getMerchantType() {
        return MerchantType;
    }

    public String getMerchantGroup() {
        return MerchantGroup;
    }

    public String getMerchantStatus() {
        return MerchantStatus;
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

    public String getMerchantLogo() {
        return MerchantLogo;
    }

    public String getIsFeatured() {
        return isFeatured;
    }

    public String getFeatureAddsPath() {
        return FeatureAddsPath;
    }

    public String getOrganizationLogo() {
        return OrganizationLogo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(MerchantID);
        dest.writeString(MerchantName);
        dest.writeString(MerchantType);
        dest.writeString(MerchantGroup);
        dest.writeString(MerchantStatus);
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
        dest.writeString(MerchantLogo);
        dest.writeString(isFeatured);
        dest.writeString(FeatureAddsPath);
        dest.writeString(OrganizationLogo);
    }
}
