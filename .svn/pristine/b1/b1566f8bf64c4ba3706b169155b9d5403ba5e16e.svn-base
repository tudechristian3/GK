package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkycableSOA implements Parcelable {
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("BillingID")
    @Expose
    private String BillingID;
    @SerializedName("SkyCableAccountNo")
    @Expose
    private String SkycableAccountNo;
    @SerializedName("SkyCableAccountName")
    @Expose
    private String SkycableAccountName;
    @SerializedName("MobileNo")
    @Expose
    private String MobileNo;
    @SerializedName("DueFromLastBill")
    @Expose
    private double DueFromLastBill;
    @SerializedName("CurrentCharges")
    @Expose
    private double CurrentCharges;
    @SerializedName("TotalCharges")
    @Expose
    private double TotalCharges;
    @SerializedName("DueDate")
    @Expose
    private String DueDate;
    @SerializedName("SOAURL")
    @Expose
    private String SOAURL;
    @SerializedName("UploadedBy")
    @Expose
    private String UploadedBy;
    @SerializedName("Status")
    @Expose
    private String Status;

    public SkycableSOA(String dateTimeIN, String billingID, String skycableAccountNo, String skycableAccountName, String mobileNo, double dueFromLastBill, double currentCharges, double totalCharges, String dueDate, String SOAURL, String uploadedBy, String status) {
        DateTimeIN = dateTimeIN;
        BillingID = billingID;
        SkycableAccountNo = skycableAccountNo;
        SkycableAccountName = skycableAccountName;
        MobileNo = mobileNo;
        DueFromLastBill = dueFromLastBill;
        CurrentCharges = currentCharges;
        TotalCharges = totalCharges;
        DueDate = dueDate;
        this.SOAURL = SOAURL;
        UploadedBy = uploadedBy;
        Status = status;
    }

    protected SkycableSOA(Parcel in) {
        DateTimeIN = in.readString();
        BillingID = in.readString();
        SkycableAccountNo = in.readString();
        SkycableAccountName = in.readString();
        MobileNo = in.readString();
        DueFromLastBill = in.readDouble();
        CurrentCharges = in.readDouble();
        TotalCharges = in.readDouble();
        DueDate = in.readString();
        SOAURL = in.readString();
        UploadedBy = in.readString();
        Status = in.readString();
    }

    public static final Creator<SkycableSOA> CREATOR = new Creator<SkycableSOA>() {
        @Override
        public SkycableSOA createFromParcel(Parcel in) {
            return new SkycableSOA(in);
        }

        @Override
        public SkycableSOA[] newArray(int size) {
            return new SkycableSOA[size];
        }
    };

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getBillingID() {
        return BillingID;
    }

    public String getSkycableAccountNo() {
        return SkycableAccountNo;
    }

    public String getSkycableAccountName() {
        return SkycableAccountName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public double getDueFromLastBill() {
        return DueFromLastBill;
    }

    public double getCurrentCharges() {
        return CurrentCharges;
    }

    public double getTotalCharges() {
        return TotalCharges;
    }

    public String getDueDate() {
        return DueDate;
    }

    public String getSOAURL() {
        return SOAURL;
    }

    public String getUploadedBy() {
        return UploadedBy;
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
        dest.writeString(DateTimeIN);
        dest.writeString(BillingID);
        dest.writeString(SkycableAccountNo);
        dest.writeString(SkycableAccountName);
        dest.writeString(MobileNo);
        dest.writeDouble(DueFromLastBill);
        dest.writeDouble(CurrentCharges);
        dest.writeDouble(TotalCharges);
        dest.writeString(DueDate);
        dest.writeString(SOAURL);
        dest.writeString(UploadedBy);
        dest.writeString(Status);
    }
}
