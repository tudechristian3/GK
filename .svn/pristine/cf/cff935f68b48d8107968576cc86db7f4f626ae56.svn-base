package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKProcessRequest implements Parcelable {

    @SerializedName("XMLDetails")
    @Expose
    private String XMLDetails;

    protected GKProcessRequest(Parcel in) {
        XMLDetails = in.readString();
    }

    public static final Creator<GKProcessRequest> CREATOR = new Creator<GKProcessRequest>() {
        @Override
        public GKProcessRequest createFromParcel(Parcel in) {
            return new GKProcessRequest(in);
        }

        @Override
        public GKProcessRequest[] newArray(int size) {
            return new GKProcessRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(XMLDetails);
    }

    public GKProcessRequest(String XMLDetails) {
        this.XMLDetails = XMLDetails;
    }

    public String getXMLDetails() {
        return XMLDetails;
    }
}
