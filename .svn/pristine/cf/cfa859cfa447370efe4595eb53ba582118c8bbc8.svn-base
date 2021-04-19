package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkycablePPV implements Parcelable {
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("ShowingDateFrom")
    @Expose
    private String ShowingDateFrom;
    @SerializedName("ShowingDateTo")
    @Expose
    private String ShowingDateTo;
    @SerializedName("PPVID")
    @Expose
    private String PPVID;
    @SerializedName("PPVName")
    @Expose
    private String PPVName;
    @SerializedName("PPVDescription")
    @Expose
    private String PPVDescription;
    @SerializedName("ImageURL")
    @Expose
    private String ImageURL;
    @SerializedName("Currency")
    @Expose
    private String Currency;
    @SerializedName("DiscountPercentage")
    @Expose
    private double DiscountPercentage;
    @SerializedName("GrossRate")
    @Expose
    private double GrossRate;
    @SerializedName("DiscountedRate")
    @Expose
    private double DiscountedRate;
    @SerializedName("AddedBy")
    @Expose
    private String AddedBy;
    @SerializedName("SubscriptionCount")
    @Expose
    private double SubscriptionCount;
    @SerializedName("Status")
    @Expose
    private String Status;

    public SkycablePPV(String dateTimeIN, String showingDateFrom, String showingDateTo, String PPVID, String PPVName, String PPVDescription, String imageURL, String currency, double discountPercentage, double grossRate, double discountedRate, String addedBy, double subscriptionCount, String status) {
        DateTimeIN = dateTimeIN;
        ShowingDateFrom = showingDateFrom;
        ShowingDateTo = showingDateTo;
        this.PPVID = PPVID;
        this.PPVName = PPVName;
        this.PPVDescription = PPVDescription;
        ImageURL = imageURL;
        Currency = currency;
        DiscountPercentage = discountPercentage;
        GrossRate = grossRate;
        DiscountedRate = discountedRate;
        AddedBy = addedBy;
        SubscriptionCount = subscriptionCount;
        Status = status;
    }

    protected SkycablePPV(Parcel in) {
        DateTimeIN = in.readString();
        ShowingDateFrom = in.readString();
        ShowingDateTo = in.readString();
        PPVID = in.readString();
        PPVName = in.readString();
        PPVDescription = in.readString();
        ImageURL = in.readString();
        Currency = in.readString();
        DiscountPercentage = in.readDouble();
        GrossRate = in.readDouble();
        DiscountedRate = in.readDouble();
        AddedBy = in.readString();
        SubscriptionCount = in.readDouble();
        Status = in.readString();
    }

    public static final Creator<SkycablePPV> CREATOR = new Creator<SkycablePPV>() {
        @Override
        public SkycablePPV createFromParcel(Parcel in) {
            return new SkycablePPV(in);
        }

        @Override
        public SkycablePPV[] newArray(int size) {
            return new SkycablePPV[size];
        }
    };

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getShowingDateFrom() {
        return ShowingDateFrom;
    }

    public String getShowingDateTo() {
        return ShowingDateTo;
    }

    public String getPPVName() {
        return PPVName;
    }

    public String getPPVDescription() {
        return PPVDescription;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public String getCurrency() {
        return Currency;
    }

    public double getDiscountPercentage() {
        return DiscountPercentage;
    }

    public double getGrossRate() {
        return GrossRate;
    }

    public double getDiscountedRate() {
        return DiscountedRate;
    }

    public String getAddedBy() {
        return AddedBy;
    }

    public double getSubscriptionCount() {
        return SubscriptionCount;
    }

    public String getStatus() {
        return Status;
    }

    public String getPPVID() {
        return PPVID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DateTimeIN);
        dest.writeString(ShowingDateFrom);
        dest.writeString(ShowingDateTo);
        dest.writeString(PPVID);
        dest.writeString(PPVName);
        dest.writeString(PPVDescription);
        dest.writeString(ImageURL);
        dest.writeString(Currency);
        dest.writeDouble(DiscountPercentage);
        dest.writeDouble(GrossRate);
        dest.writeDouble(DiscountedRate);
        dest.writeString(AddedBy);
        dest.writeDouble(SubscriptionCount);
        dest.writeString(Status);
    }
}
