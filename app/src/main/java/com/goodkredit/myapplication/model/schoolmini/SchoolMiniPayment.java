package com.goodkredit.myapplication.model.schoolmini;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolMiniPayment implements Parcelable {
    @SerializedName("VoucherSessionID")
    @Expose
    private String VoucherSessionID;
    @SerializedName("MerchantReferenceNo")
    @Expose
    private String MerchantReferenceNo;
    @SerializedName("TotalAmount")
    @Expose
    private double TotalAmount;

    public SchoolMiniPayment(String voucherSessionID, String merchantReferenceNo, double totalAmount) {
        VoucherSessionID = voucherSessionID;
        MerchantReferenceNo = merchantReferenceNo;
        TotalAmount = totalAmount;
    }

    protected SchoolMiniPayment(Parcel in) {
        VoucherSessionID = in.readString();
        MerchantReferenceNo = in.readString();
        TotalAmount = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(VoucherSessionID);
        dest.writeString(MerchantReferenceNo);
        dest.writeDouble(TotalAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SchoolMiniPayment> CREATOR = new Creator<SchoolMiniPayment>() {
        @Override
        public SchoolMiniPayment createFromParcel(Parcel in) {
            return new SchoolMiniPayment(in);
        }

        @Override
        public SchoolMiniPayment[] newArray(int size) {
            return new SchoolMiniPayment[size];
        }
    };

    public String getVoucherSessionID() {
        return VoucherSessionID;
    }

    public String getMerchantReferenceNo() {
        return MerchantReferenceNo;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }
}
