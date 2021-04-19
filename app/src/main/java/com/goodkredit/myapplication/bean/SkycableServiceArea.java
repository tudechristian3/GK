package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkycableServiceArea implements Parcelable {
    @SerializedName("ServiceAreaID")
    @Expose
    private String ServiceAreaID;
    @SerializedName("BaseLocation")
    @Expose
    private String BaseLocation;
    @SerializedName("Radius")
    @Expose
    private String Radius;
    @SerializedName("Longitude")
    @Expose
    private String Longitude;
    @SerializedName("Latitude")
    @Expose
    private String Latitude;

    public SkycableServiceArea(String serviceAreaID, String baseLocation, String radius, String longitude, String latitude) {
        ServiceAreaID = serviceAreaID;
        BaseLocation = baseLocation;
        Radius = radius;
        Longitude = longitude;
        Latitude = latitude;
    }

    protected SkycableServiceArea(Parcel in) {
        ServiceAreaID = in.readString();
        BaseLocation = in.readString();
        Radius = in.readString();
        Longitude = in.readString();
        Latitude = in.readString();
    }

    public static final Creator<SkycableServiceArea> CREATOR = new Creator<SkycableServiceArea>() {
        @Override
        public SkycableServiceArea createFromParcel(Parcel in) {
            return new SkycableServiceArea(in);
        }

        @Override
        public SkycableServiceArea[] newArray(int size) {
            return new SkycableServiceArea[size];
        }
    };

    public String getServiceAreaID() {
        return ServiceAreaID;
    }

    public String getBaseLocation() {
        return BaseLocation;
    }

    public String getRadius() {
        return Radius;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ServiceAreaID);
        dest.writeString(BaseLocation);
        dest.writeString(Radius);
        dest.writeString(Longitude);
        dest.writeString(Latitude);
    }
}
