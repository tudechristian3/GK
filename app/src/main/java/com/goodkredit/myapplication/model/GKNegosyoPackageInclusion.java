package com.goodkredit.myapplication.model;

import android.media.session.MediaSession;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GKNegosyoPackageInclusion implements Parcelable {

    @SerializedName("VoucherCredits")
    @Expose
    private double VoucherCredits;
    @SerializedName("Products")
    @Expose
    private List<GKNegosyoPackageVoucher> Products;


    protected GKNegosyoPackageInclusion(Parcel in) {
        VoucherCredits = in.readDouble();
        Products = in.createTypedArrayList(GKNegosyoPackageVoucher.CREATOR);
    }

    public static final Creator<GKNegosyoPackageInclusion> CREATOR = new Creator<GKNegosyoPackageInclusion>() {
        @Override
        public GKNegosyoPackageInclusion createFromParcel(Parcel in) {
            return new GKNegosyoPackageInclusion(in);
        }

        @Override
        public GKNegosyoPackageInclusion[] newArray(int size) {
            return new GKNegosyoPackageInclusion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(VoucherCredits);
        dest.writeTypedList(Products);
    }

    public double getVoucherCredits() {
        return VoucherCredits;
    }

    public List<GKNegosyoPackageVoucher> getProducts() {
        return Products;
    }
}
