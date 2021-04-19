package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UNORedemptionVoucherDetails implements Parcelable {

    @SerializedName("productid")
    @Expose
    private String ProductID;
    @SerializedName("voucherdenom")
    @Expose
    private String Denom;
    @SerializedName("quantity")
    @Expose
    private String Quantity;


    protected UNORedemptionVoucherDetails(Parcel in) {
        ProductID = in.readString();
        Denom = in.readString();
        Quantity = in.readString();
    }

    public static final Creator<UNORedemptionVoucherDetails> CREATOR = new Creator<UNORedemptionVoucherDetails>() {
        @Override
        public UNORedemptionVoucherDetails createFromParcel(Parcel in) {
            return new UNORedemptionVoucherDetails(in);
        }

        @Override
        public UNORedemptionVoucherDetails[] newArray(int size) {
            return new UNORedemptionVoucherDetails[size];
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
