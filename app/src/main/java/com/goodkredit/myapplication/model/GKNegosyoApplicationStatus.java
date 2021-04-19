package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKNegosyoApplicationStatus implements Parcelable {

    @SerializedName("DataResID")
    @Expose
    private String DataResID;
    @SerializedName("RegistrationID")
    @Expose
    private String RegistrationID;

    public GKNegosyoApplicationStatus(String dataResID, String registrationID) {
        DataResID = dataResID;
        RegistrationID = registrationID;
    }

    protected GKNegosyoApplicationStatus(Parcel in) {
        DataResID = in.readString();
        RegistrationID = in.readString();
    }

    public static final Creator<GKNegosyoApplicationStatus> CREATOR = new Creator<GKNegosyoApplicationStatus>() {
        @Override
        public GKNegosyoApplicationStatus createFromParcel(Parcel in) {
            return new GKNegosyoApplicationStatus(in);
        }

        @Override
        public GKNegosyoApplicationStatus[] newArray(int size) {
            return new GKNegosyoApplicationStatus[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DataResID);
        dest.writeString(RegistrationID);
    }

    public String getDataResID() {
        return DataResID;
    }

    public String getRegistrationID() {
        return RegistrationID;
    }
}
