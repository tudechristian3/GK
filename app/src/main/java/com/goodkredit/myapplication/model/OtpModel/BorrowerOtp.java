package com.goodkredit.myapplication.model.OtpModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BorrowerOtp implements Parcelable {
    @SerializedName("UserID")
    @Expose
    private String UserID;
    @SerializedName("Otp")
    @Expose
    private String Otp;
    public BorrowerOtp(String userID, String otp) {
        UserID = userID;
        Otp = otp;
    }
    protected BorrowerOtp(Parcel in) {
        UserID = in.readString();
        Otp = in.readString();
    }
    public static final Creator<BorrowerOtp> CREATOR = new Creator<BorrowerOtp>() {
        @Override
        public BorrowerOtp createFromParcel(Parcel in) {
            return new BorrowerOtp(in);
        }
        @Override
        public BorrowerOtp[] newArray(int size) {
            return new BorrowerOtp[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UserID);
        dest.writeString(Otp);
    }
    public String getUserID() {
        return UserID;
    }
    public String getOtp() {
        return Otp;
    }
}
