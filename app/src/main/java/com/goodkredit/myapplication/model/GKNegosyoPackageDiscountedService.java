package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKNegosyoPackageDiscountedService implements Parcelable {

    @SerializedName("PackageID")
    @Expose
    private String PackageID;
    @SerializedName("ServiceName")
    @Expose
    private String GKService;
    @SerializedName("ResellerVariableFee")
    @Expose
    private double ResellerVariableFee;
    @SerializedName("ResellerBaseFee")
    @Expose
    private double ResellerBaseFee;
    @SerializedName("AmountFrom")
    @Expose
    private double AmountFrom;
    @SerializedName("AmountTo")
    @Expose
    private double AmountTo;
    @SerializedName("GKServices")
    @Expose
    private String ServiceName;

    protected GKNegosyoPackageDiscountedService(Parcel in) {
        PackageID = in.readString();
        GKService = in.readString();
        ServiceName = in.readString();
        ResellerVariableFee = in.readDouble();
        ResellerBaseFee = in.readDouble();
        AmountFrom = in.readDouble();
        AmountTo = in.readDouble();
    }

    public static final Creator<GKNegosyoPackageDiscountedService> CREATOR = new Creator<GKNegosyoPackageDiscountedService>() {
        @Override
        public GKNegosyoPackageDiscountedService createFromParcel(Parcel in) {
            return new GKNegosyoPackageDiscountedService(in);
        }

        @Override
        public GKNegosyoPackageDiscountedService[] newArray(int size) {
            return new GKNegosyoPackageDiscountedService[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PackageID);
        dest.writeString(GKService);
        dest.writeString(ServiceName);
        dest.writeDouble(ResellerVariableFee);
        dest.writeDouble(ResellerBaseFee);
        dest.writeDouble(AmountFrom);
        dest.writeDouble(AmountTo);
    }

    public String getPackageID() {
        return PackageID;
    }

    public String getGKService() {
        return GKService;
    }
    public String getServiceName() {
        return ServiceName;
    }

    public double getResellerVariableFee() {
        return ResellerVariableFee;
    }

    public double getResellerBaseFee() {
        return ResellerBaseFee;
    }

    public double getAmountFrom() {
        return AmountFrom;
    }

    public double getAmountTo() {
        return AmountTo;
    }
}
