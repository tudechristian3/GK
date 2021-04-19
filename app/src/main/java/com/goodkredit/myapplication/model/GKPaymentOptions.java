package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKPaymentOptions implements Parcelable {
    @SerializedName("PaymentName")
    @Expose
    private String PaymentName;
    @SerializedName("PaymentDescription")
    @Expose
    private String PaymentDescription;
    @SerializedName("PaymentImageUrl")
    @Expose
    private String PaymentImageUrl;
    @SerializedName("PaymentStatus")
    @Expose
    private String PaymentStatus;
    @SerializedName("PaymentPrice")
    @Expose
    private double PaymentPrice;

    //GENERAL PAYMENT OPTION
    public GKPaymentOptions(String paymentName, String paymentDescription, String paymentImageUrl, String paymentStatus) {
        PaymentName = paymentName;
        PaymentDescription = paymentDescription;
        PaymentImageUrl = paymentImageUrl;
        PaymentStatus = paymentStatus;
    }

    //WITH PRICE
    public GKPaymentOptions(String paymentName, double paymentPrice,String paymentStatus) {
        PaymentName = paymentName;
        PaymentPrice = paymentPrice;
        PaymentStatus = paymentStatus;
    }

    protected GKPaymentOptions(Parcel in) {
        PaymentName = in.readString();
        PaymentDescription = in.readString();
        PaymentImageUrl = in.readString();
        PaymentStatus = in.readString();
        PaymentPrice = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PaymentName);
        dest.writeString(PaymentDescription);
        dest.writeString(PaymentImageUrl);
        dest.writeString(PaymentStatus);
        dest.writeDouble(PaymentPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GKPaymentOptions> CREATOR = new Creator<GKPaymentOptions>() {
        @Override
        public GKPaymentOptions createFromParcel(Parcel in) {
            return new GKPaymentOptions(in);
        }

        @Override
        public GKPaymentOptions[] newArray(int size) {
            return new GKPaymentOptions[size];
        }
    };

    public String getPaymentName() {
        return PaymentName;
    }

    public String getPaymentDescription() {
        return PaymentDescription;
    }

    public String getPaymentImageUrl() {
        return PaymentImageUrl;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public double getPaymentPrice() {
        return PaymentPrice;
    }
}
