package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkycableDictionary implements Parcelable {
    @SerializedName("PlanID")
    @Expose
    private String PlanID;
    @SerializedName("PlanName")
    @Expose
    private String PlanName;
    @SerializedName("PlanType")
    @Expose
    private String PlanType;
    @SerializedName("PlanImgUrl")
    @Expose
    private String PlanImgUrl;
    @SerializedName("PlanStyle")
    @Expose
    private String PlanStyle;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("DiscountBase")
    @Expose
    private double DiscountBase;
    @SerializedName("DiscountPercentage")
    @Expose
    private double DiscountPercentage;
    @SerializedName("InstallationFee")
    @Expose
    private double InstallationFee;
    @SerializedName("MonthlyFee")
    @Expose
    private double MonthlyFee;
    @SerializedName("InitialCashout")
    @Expose
    private double InitialCashout;
    @SerializedName("Status")
    @Expose
    private String Status;

    public SkycableDictionary(String planID, String planName, String planType, String planImgUrl, String planStyle, String description, double discountBase, double discountPercentage, double installationFee, double monthlyFee, double initialCashout, String status) {
        PlanID = planID;
        PlanName = planName;
        PlanType = planType;
        PlanImgUrl = planImgUrl;
        PlanStyle = planStyle;
        Description = description;
        DiscountBase = discountBase;
        DiscountPercentage = discountPercentage;
        InstallationFee = installationFee;
        MonthlyFee = monthlyFee;
        InitialCashout = initialCashout;
        Status = status;
    }

    protected SkycableDictionary(Parcel in) {
        PlanID = in.readString();
        PlanName = in.readString();
        PlanType = in.readString();
        PlanImgUrl = in.readString();
        PlanStyle = in.readString();
        Description = in.readString();
        DiscountBase = in.readDouble();
        DiscountPercentage = in.readDouble();
        InstallationFee = in.readDouble();
        MonthlyFee = in.readDouble();
        InitialCashout = in.readDouble();
        Status = in.readString();
    }

    public static final Creator<SkycableDictionary> CREATOR = new Creator<SkycableDictionary>() {
        @Override
        public SkycableDictionary createFromParcel(Parcel in) {
            return new SkycableDictionary(in);
        }

        @Override
        public SkycableDictionary[] newArray(int size) {
            return new SkycableDictionary[size];
        }
    };

    public String getPlanID() {
        return PlanID;
    }

    public String getPlanName() {
        return PlanName;
    }

    public String getPlanType() {
        return PlanType;
    }

    public String getPlanImgUrl() {
        return PlanImgUrl;
    }

    public String getPlanStyle() {
        return PlanStyle;
    }

    public String getDescription() {
        return Description;
    }

    public double getDiscountBase() {
        return DiscountBase;
    }

    public double getDiscountPercentage() {
        return DiscountPercentage;
    }

    public double getInstallationFee() {
        return InstallationFee;
    }

    public double getMonthlyFee() {
        return MonthlyFee;
    }

    public double getInitialCashout() {
        return InitialCashout;
    }

    public String getStatus() {
        return Status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PlanID);
        dest.writeString(PlanName);
        dest.writeString(PlanType);
        dest.writeString(PlanImgUrl);
        dest.writeString(PlanStyle);
        dest.writeString(Description);
        dest.writeDouble(DiscountBase);
        dest.writeDouble(DiscountPercentage);
        dest.writeDouble(InstallationFee);
        dest.writeDouble(MonthlyFee);
        dest.writeDouble(InitialCashout);
        dest.writeString(Status);
    }
}
