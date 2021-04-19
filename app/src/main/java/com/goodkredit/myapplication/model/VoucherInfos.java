package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 1/10/2018.
 */

public class VoucherInfos implements Parcelable {

    @SerializedName("ProductID")
    @Expose
    private String ProductID;
    @SerializedName("Denom")
    @Expose
    private String Denom;
    @SerializedName("Quantity")
    @Expose
    private String Quantity;


    protected VoucherInfos(Parcel in) {
        ProductID = in.readString();
        Denom = in.readString();
        Quantity = in.readString();
    }

    public static final Creator<VoucherInfos> CREATOR = new Creator<VoucherInfos>() {
        @Override
        public VoucherInfos createFromParcel(Parcel in) {
            return new VoucherInfos(in);
        }

        @Override
        public VoucherInfos[] newArray(int size) {
            return new VoucherInfos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ProductID);
        dest.writeString(Denom);
        dest.writeString(Quantity);
    }

    public String getProductID() {
        return ProductID;
    }

    public String getDenom() {
        return Denom;
    }

    public String getQuantity() {
        return Quantity;
    }
}
