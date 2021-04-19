package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 4/25/2017.
 */

public class VoucherSummary implements Parcelable {

    @SerializedName("VoucherDenom")
    @Expose
    private double VoucherDenom;
    @SerializedName("ProductID")
    @Expose
    private String ProductID;
    @SerializedName("TotalNumber")
    @Expose
    private int TotalNumber;
    @SerializedName("TotalBalance")
    @Expose
    private double TotalBalance;
    @SerializedName("TotalDenom")
    @Expose
    private double TotalDenom;
    @SerializedName("RFIDNumber")
    @Expose
    private String RFIDNumber;
    @SerializedName("RFIDCardNumber")
    @Expose
    private String RFIDCardNumber;
    @SerializedName("RFIDPIN")
    @Expose
    private String RFIDPIN;

    public String getRFIDNumber() {
        return RFIDNumber;
    }

    public String getRFIDCardNumber() {
        return RFIDCardNumber;
    }

    public String getRFIDPIN() {
        return RFIDPIN;
    }

    public double getVoucherDenom() {
        return VoucherDenom;
    }

    public String getProductID() {
        return ProductID;
    }

    public int getTotalNumber() {
        return TotalNumber;
    }

    public double getTotalBalance() {
        return TotalBalance;
    }

    public double getTotalDenom() {
        return TotalDenom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(VoucherDenom);
        dest.writeString(ProductID);
        dest.writeInt(TotalNumber);
        dest.writeDouble(TotalBalance);
        dest.writeDouble(TotalDenom);
        dest.writeString(RFIDNumber);
        dest.writeString(RFIDCardNumber);
        dest.writeString(RFIDPIN);
    }

    private VoucherSummary(Parcel in) {
        this.VoucherDenom = in.readDouble();
        this.ProductID = in.readString();
        this.TotalNumber = in.readInt();
        this.TotalBalance = in.readDouble();
        this.TotalDenom = in.readDouble();
        this.RFIDNumber = in.readString();
        this.RFIDCardNumber = in.readString();
        this.RFIDPIN = in.readString();
    }

    public VoucherSummary(double voucherDenom, String productID, int totalNumber, double totalBalance, double totalDenom) {
        VoucherDenom = voucherDenom;
        ProductID = productID;
        TotalNumber = totalNumber;
        TotalBalance = totalBalance;
        TotalDenom = totalDenom;
    }

    public static final Creator<VoucherSummary> CREATOR = new Creator<VoucherSummary>() {
        @Override
        public VoucherSummary createFromParcel(Parcel source) {
            return new VoucherSummary(source);
        }

        @Override
        public VoucherSummary[] newArray(int size) {
            return new VoucherSummary[size];
        }
    };
}
