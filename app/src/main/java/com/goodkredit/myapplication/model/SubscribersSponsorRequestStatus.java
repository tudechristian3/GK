package com.goodkredit.myapplication.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban on 13/03/2018.
 */

public class SubscribersSponsorRequestStatus implements Parcelable {

    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("SubGuarantorID")
    @Expose
    private String SubGuarantorID;
    @SerializedName("Status")
    @Expose
    private String Status;

    protected SubscribersSponsorRequestStatus(Parcel in) {
        GuarantorID = in.readString();
        SubGuarantorID = in.readString();
        Status = in.readString();
    }

    public static final Creator<SubscribersSponsorRequestStatus> CREATOR = new Creator<SubscribersSponsorRequestStatus>() {
        @Override
        public SubscribersSponsorRequestStatus createFromParcel(Parcel in) {
            return new SubscribersSponsorRequestStatus(in);
        }

        @Override
        public SubscribersSponsorRequestStatus[] newArray(int size) {
            return new SubscribersSponsorRequestStatus[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(GuarantorID);
        dest.writeString(SubGuarantorID);
        dest.writeString(Status);
    }

    public SubscribersSponsorRequestStatus(String guarantorID, String subGuarantorID, String status) {
        GuarantorID = guarantorID;
        SubGuarantorID = subGuarantorID;
        Status = status;
    }

    public String getGuarantorID() {
        if (GuarantorID.equals(".") && !SubGuarantorID.equals("."))
            return SubGuarantorID;
        else
            return GuarantorID;
    }


    public String getStatus() {
        return Status;
    }
}
