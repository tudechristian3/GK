package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 4/25/2017.
 */

public class VoucherRestriction implements Parcelable {

    @SerializedName("ProductID")
    @Expose
    private String ProductID;
    @SerializedName("RestrictionType")
    @Expose
    private String RestrictionType;
    @SerializedName("RestrictedTo")
    @Expose
    private String RestrictedTo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ProductID);
        dest.writeString(RestrictionType);
        dest.writeString(RestrictedTo);
    }

    private VoucherRestriction(Parcel in) {
        ProductID = in.readString();
        RestrictionType = in.readString();
        RestrictedTo = in.readString();
    }

    public static final Parcelable.Creator<VoucherRestriction> CREATOR = new Parcelable.Creator<VoucherRestriction>() {
        @Override
        public VoucherRestriction createFromParcel(Parcel source) {
            return new VoucherRestriction(source);
        }

        @Override
        public VoucherRestriction[] newArray(int size) {
            return new VoucherRestriction[size];
        }
    };

    public String getProductID() {
        return ProductID;
    }

    public String getRestrictionType() {
        return RestrictionType;
    }

    public String getRestrictedTo() {
        return RestrictedTo;
    }
}
