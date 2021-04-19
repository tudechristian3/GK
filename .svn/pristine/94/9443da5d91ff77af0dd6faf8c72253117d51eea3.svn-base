package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class GKStoreServiceCharge implements Parcelable {
    private String serviceCharge;

    protected GKStoreServiceCharge(Parcel in) {
        serviceCharge = in.readString();
    }

    public static final Creator<GKStoreServiceCharge> CREATOR = new Creator<GKStoreServiceCharge>() {
        @Override
        public GKStoreServiceCharge createFromParcel(Parcel in) {
            return new GKStoreServiceCharge(in);
        }

        @Override
        public GKStoreServiceCharge[] newArray(int size) {
            return new GKStoreServiceCharge[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serviceCharge);
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }
}
