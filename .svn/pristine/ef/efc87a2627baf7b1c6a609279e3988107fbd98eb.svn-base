package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnoPointsConversion implements Parcelable {

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("Points")
    @Expose
    private String Points;
    @SerializedName("ConversionAmount")
    @Expose
    private double ConversionAmount;


    protected UnoPointsConversion(Parcel in) {
        ID = in.readInt();
        Points = in.readString();
        ConversionAmount = in.readDouble();
    }

    public static final Creator<UnoPointsConversion> CREATOR = new Creator<UnoPointsConversion>() {
        @Override
        public UnoPointsConversion createFromParcel(Parcel in) {
            return new UnoPointsConversion(in);
        }

        @Override
        public UnoPointsConversion[] newArray(int size) {
            return new UnoPointsConversion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Points);
        dest.writeDouble(ConversionAmount);
    }

    public int getID() {
        return ID;
    }

    public String getPoints() {
        return Points;
    }

    public double getConversionAmount() {
        return ConversionAmount;
    }
}
