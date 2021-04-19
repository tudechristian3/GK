package com.goodkredit.myapplication.model.publicsponsor;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifySponsorData implements Parcelable {

    public static final String KEY_VERIFYSPONSORDATA = "key_verifysponsordata";

    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("AuthorisedEmailAddress")
    @Expose
    private String AuthorisedEmailAddress;
    @SerializedName("GuarantorName")
    @Expose
    private String GuarantorName;
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;

    public VerifySponsorData(String ID, String authorisedEmailAddress, String guarantorName, String guarantorID) {
        this.ID = ID;
        AuthorisedEmailAddress = authorisedEmailAddress;
        GuarantorName = guarantorName;
        GuarantorID = guarantorID;
    }

    public String getID() {
        return ID;
    }

    public String getAuthorisedEmailAddress() {
        return AuthorisedEmailAddress;
    }

    public String getGuarantorName() {
        return GuarantorName;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.AuthorisedEmailAddress);
        dest.writeString(this.GuarantorName);
        dest.writeString(this.GuarantorID);
    }

    protected VerifySponsorData(Parcel in) {
        this.ID = in.readString();
        this.AuthorisedEmailAddress = in.readString();
        this.GuarantorName = in.readString();
        this.GuarantorID = in.readString();
    }

    public static final Parcelable.Creator<VerifySponsorData> CREATOR = new Parcelable.Creator<VerifySponsorData>() {
        @Override
        public VerifySponsorData createFromParcel(Parcel source) {
            return new VerifySponsorData(source);
        }

        @Override
        public VerifySponsorData[] newArray(int size) {
            return new VerifySponsorData[size];
        }
    };
}
