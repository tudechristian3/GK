package com.goodkredit.myapplication.model.gknegosyo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Distributor implements Parcelable {

    public static final String KEY_GK_NEG_DISTRO = "GKNegosyoDistributor";

    @SerializedName("DistributorID")
    @Expose
    private String DistributorID;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("Longitude")
    @Expose
    private String Longitude;
    @SerializedName("Latitude")
    @Expose
    private String Latitude;
    @SerializedName("AuthorizedMobile")
    @Expose
    private String AuthorizedMobile;
    @SerializedName("AuthorizedEmail")
    @Expose
    private String AuthorizedEmail;
    @SerializedName("AuthorizedTelNo")
    @Expose
    private String AuthorizedTelNo;
    @SerializedName("StreetAddress")
    @Expose
    private String StreetAddress;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("Province")
    @Expose
    private String Province;
    @SerializedName("ServiceArea")
    @Expose
    private double ServiceArea;

    protected Distributor(Parcel in) {
        DistributorID = in.readString();
        LastName = in.readString();
        FirstName = in.readString();
        Longitude = in.readString();
        Latitude = in.readString();
        AuthorizedMobile = in.readString();
        AuthorizedEmail = in.readString();
        AuthorizedTelNo = in.readString();
        StreetAddress = in.readString();
        City = in.readString();
        Province = in.readString();
        ServiceArea = in.readDouble();
    }

    public static final Creator<Distributor> CREATOR = new Creator<Distributor>() {
        @Override
        public Distributor createFromParcel(Parcel in) {
            return new Distributor(in);
        }

        @Override
        public Distributor[] newArray(int size) {
            return new Distributor[size];
        }
    };

    public String getDistributorID() {
        return DistributorID;
    }

    public String getLastName() {
        return LastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getAuthorizedMobile() {
        return AuthorizedMobile;
    }

    public String getAuthorizedEmail() {
        return AuthorizedEmail;
    }

    public String getAuthorizedTelNo() {
        return AuthorizedTelNo;
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

    public double getServiceArea() {
        return ServiceArea;
    }

    public String getFullName() {
        return FirstName + " " + LastName;
    }

    public String getFullAddress() {
        return StreetAddress + ", " + City + ", " + Province;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DistributorID);
        dest.writeString(LastName);
        dest.writeString(FirstName);
        dest.writeString(Longitude);
        dest.writeString(Latitude);
        dest.writeString(AuthorizedMobile);
        dest.writeString(AuthorizedEmail);
        dest.writeString(AuthorizedTelNo);
        dest.writeString(StreetAddress);
        dest.writeString(City);
        dest.writeString(Province);
        dest.writeDouble(ServiceArea);
    }

    public Distributor(String distributorID, String lastName, String firstName, String longitude, String latitude,
                       String authorizedMobile, String authorizedEmail, String authorizedTelNo, String streetAddress,
                       String city, String province, double serviceArea) {
        DistributorID = distributorID;
        LastName = lastName;
        FirstName = firstName;
        Longitude = longitude;
        Latitude = latitude;
        AuthorizedMobile = authorizedMobile;
        AuthorizedEmail = authorizedEmail;
        AuthorizedTelNo = authorizedTelNo;
        StreetAddress = streetAddress;
        City = city;
        Province = province;
        ServiceArea = serviceArea;
    }
}
