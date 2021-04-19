package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 11/16/2017.
 */

public class MerchantPayExpress implements Parcelable {

    @SerializedName("MerchantID")
    @Expose
    private String MerchantID;
    @SerializedName("MerchantName")
    @Expose
    private String MerchantName;
    @SerializedName("BranchID")
    @Expose
    private String BranchID;
    @SerializedName("MerchantLogo")
    @Expose
    private String MerchantLogo;
    @SerializedName("BranchName")
    @Expose
    private String BranchName;
    @SerializedName("SecurityKey")
    @Expose
    private String SecurityKey;

    protected MerchantPayExpress(Parcel in) {
        MerchantID = in.readString();
        MerchantName = in.readString();
        BranchID = in.readString();
        MerchantLogo = in.readString();
        BranchName = in.readString();
        SecurityKey = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(MerchantID);
        dest.writeString(MerchantName);
        dest.writeString(BranchID);
        dest.writeString(MerchantLogo);
        dest.writeString(BranchName);
        dest.writeString(SecurityKey);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MerchantPayExpress> CREATOR = new Creator<MerchantPayExpress>() {
        @Override
        public MerchantPayExpress createFromParcel(Parcel in) {
            return new MerchantPayExpress(in);
        }

        @Override
        public MerchantPayExpress[] newArray(int size) {
            return new MerchantPayExpress[size];
        }
    };

    public String getMerchantName() {
        return MerchantName;
    }

    public String getBranchID() {
        return BranchID;
    }

    public String getMerchantLogo() {
        return MerchantLogo;
    }

    public String getBranchName() {
        return BranchName;
    }

    public String getSecurityKey() {
        return SecurityKey;
    }

    public String getMerchantID() {
        return MerchantID;
    }
}
