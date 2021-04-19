package com.goodkredit.myapplication.model.v2Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MerchantObject {

    @SerializedName("MerchantID")
    @Expose
    private String merchantid;

    @SerializedName("MerchantName")
    @Expose
    private String merchantname;

    @SerializedName("StreetAddress")
    @Expose
    private String streetaddress;

    @SerializedName("City")
    @Expose
    private String city;

    @SerializedName("Province")
    @Expose
    private String province;

    @SerializedName("DateAdded")
    @Expose
    private String dateadded;

    @SerializedName("Longitude")
    @Expose
    private String longitude;

    @SerializedName("Latitude")
    @Expose
    private String latitude;

    @SerializedName("MerchantType")
    @Expose
    private String merchanttype;

    @SerializedName("MerchantGroup")
    @Expose
    private String merchantgroup;

    @SerializedName("MerchantStatus")
    @Expose
    private String merchantstatus;

    @SerializedName("Zip")
    @Expose
    private String zip;

    @SerializedName("Country")
    @Expose
    private String country;

    @SerializedName("Representative")
    @Expose
    private String representative;

    @SerializedName("AuthorisedEmailAddress")
    @Expose
    private String authorizedemailaddress;

    @SerializedName("AuthorisedTelNo")
    @Expose
    private String authorisedtelno;

    @SerializedName("AuthorisedMobileNo")
    @Expose
    private String authorisedmobileno;

    @SerializedName("Fax")
    @Expose
    private String fax;

    @SerializedName("NatureOfBusiness")
    @Expose
    private String natureofbusiness;

    @SerializedName("TIN")
    @Expose
    private String tin;

    @SerializedName("OrganizationType")
    @Expose
    private String organizationtype;

    @SerializedName("NoOfBranches")
    @Expose
    private String noofbranches;

    @SerializedName("isFeatured")
    @Expose
    private String isfeatured;

    @SerializedName("FeatureAddsPath")
    @Expose
    private String featuredaddspath;

    @SerializedName("MerchantLogo")
    @Expose
    private String merchantlogo;

    @SerializedName("OrganizationLogo")
    @Expose
    private String organizationlogo;

    public String getMerchantid() {
        return merchantid;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public String getStreetaddress() {
        return streetaddress;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getDateadded() {
        return dateadded;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getMerchanttype() {
        return merchanttype;
    }

    public String getMerchantgroup() {
        return merchantgroup;
    }

    public String getMerchantstatus() {
        return merchantstatus;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public String getRepresentative() {
        return representative;
    }

    public String getAuthorizedemailaddress() {
        return authorizedemailaddress;
    }

    public String getAuthorisedtelno() {
        return authorisedtelno;
    }

    public String getAuthorisedmobileno() {
        return authorisedmobileno;
    }

    public String getFax() {
        return fax;
    }

    public String getNatureofbusiness() {
        return natureofbusiness;
    }

    public String getTin() {
        return tin;
    }

    public String getOrganizationtype() {
        return organizationtype;
    }

    public String getNoofbranches() {
        return noofbranches;
    }

    public String getIsfeatured() {
        return isfeatured;
    }

    public String getFeaturedaddspath() {
        return featuredaddspath;
    }

    public String getMerchantlogo() {
        return merchantlogo;
    }

    public String getOrganizationlogo() {
        return organizationlogo;
    }
}
