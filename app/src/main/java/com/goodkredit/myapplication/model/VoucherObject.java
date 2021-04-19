package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoucherObject implements Parcelable {

    @SerializedName("VoucherProductID")
    @Expose
    private String VoucherProductID;
    @SerializedName("VoucherDenom")
    @Expose
    private String VoucherDenom;
    @SerializedName("Quantity")
    @Expose
    private double Quantity;
    @SerializedName("Message")
    @Expose
    private String Message;

    protected VoucherObject(Parcel in) {
        VoucherProductID = in.readString();
        VoucherDenom = in.readString();
        Message = in.readString();
        Quantity = in.readDouble();
    }

    public static final Creator<VoucherObject> CREATOR = new Creator<VoucherObject>() {
        @Override
        public VoucherObject createFromParcel(Parcel in) {
            return new VoucherObject(in);
        }

        @Override
        public VoucherObject[] newArray(int size) {
            return new VoucherObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(VoucherProductID);
        dest.writeString(VoucherDenom);
        dest.writeString(Message);
        dest.writeDouble(Quantity);
    }

    public String getVoucherProductID() {
        return VoucherProductID;
    }

    public String getVoucherDenom() {
        return VoucherDenom;
    }

    public String getMessage() {
        return Message;
    }

    public double getQuantity() {
        return Quantity;
    }
}

