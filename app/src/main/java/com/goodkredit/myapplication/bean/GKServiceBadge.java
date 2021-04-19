package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKServiceBadge implements Parcelable {

    @SerializedName("ServiceCode")
    @Expose
    private String ServiceCode;
    @SerializedName("TotalBadge")
    @Expose
    private int TotalBadge;

    protected GKServiceBadge(Parcel in) {
        ServiceCode = in.readString();
        TotalBadge = in.readInt();
    }

    public GKServiceBadge(String serviceCode, int totalBadge) {
        ServiceCode = serviceCode;
        TotalBadge = totalBadge;
    }

    public static final Creator<GKServiceBadge> CREATOR = new Creator<GKServiceBadge>() {
        @Override
        public GKServiceBadge createFromParcel(Parcel in) {
            return new GKServiceBadge(in);
        }

        @Override
        public GKServiceBadge[] newArray(int size) {
            return new GKServiceBadge[size];
        }
    };

    public String getServiceCode() {
        return ServiceCode;
    }

    public int getTotalBadge() {
        return TotalBadge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ServiceCode);
        parcel.writeInt(TotalBadge);
    }
}
