package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscountPerTransactionType implements Parcelable {

    public static String OBJ = "object";

    @SerializedName("TotalDiscount")
    @Expose
    private double TotalDiscount;
    @SerializedName("TransactionType")
    @Expose
    private String TransactionType;
    @SerializedName("ServiceName")
    @Expose
    private String ServiceName;


    protected DiscountPerTransactionType(Parcel in) {
        TotalDiscount = in.readDouble();
        TransactionType = in.readString();
        ServiceName = in.readString();
    }

    public static final Creator<DiscountPerTransactionType> CREATOR = new Creator<DiscountPerTransactionType>() {
        @Override
        public DiscountPerTransactionType createFromParcel(Parcel in) {
            return new DiscountPerTransactionType(in);
        }

        @Override
        public DiscountPerTransactionType[] newArray(int size) {
            return new DiscountPerTransactionType[size];
        }
    };

    public double getTotalDiscount() {
        return TotalDiscount;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public String getServiceName() {
        return ServiceName.toUpperCase();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(TotalDiscount);
        dest.writeString(TransactionType);
        dest.writeString(ServiceName);
    }
}
