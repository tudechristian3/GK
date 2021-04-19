package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 7/15/2017.
 */

public class SubscriberBillSummary implements Parcelable {

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("GuarantorName")
    @Expose
    private String GuarantorName;

    @SerializedName("SubGuarantorID")
    @Expose
    private String SubGuarantorID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("BorrowerName")
    @Expose
    private String BorrowerName;
    @SerializedName("BillingID")
    @Expose
    private String BillingID;
    @SerializedName("StatementDate")
    @Expose
    private String StatementDate;
    @SerializedName("DueDate")
    @Expose
    private String DueDate;
    @SerializedName("Amount")
    @Expose
    private double Amount;
    @SerializedName("PeriodFrom")
    @Expose
    private String PeriodFrom;
    @SerializedName("PeriodTo")
    @Expose
    private String PeriodTo;
    @SerializedName("isNotified")
    @Expose
    private String isNotified;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Extra3")
    @Expose
    private String Extra3;
    @SerializedName("Extra4")
    @Expose
    private String Extra4;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;


    public SubscriberBillSummary(int ID, String dateTimeIN, String guarantorID, String guarantorName, String subGuarantorID, String borrowerID,
                                 String borrowerName, String billingID, String statementDate, String dueDate, double amount,
                                 String periodFrom, String periodTo, String isNotified, String extra1, String extra2, String extra3,
                                 String extra4, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        GuarantorID = guarantorID;
        SubGuarantorID = subGuarantorID;
        GuarantorName = guarantorName;
        BorrowerID = borrowerID;
        BorrowerName = borrowerName;
        BillingID = billingID;
        StatementDate = statementDate;
        DueDate = dueDate;
        Amount = amount;
        PeriodFrom = periodFrom;
        PeriodTo = periodTo;
        this.isNotified = isNotified;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }


    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }

    public String getGuarantorName() {
        return GuarantorName;
    }

    public String getSubGuarantorID() {
        return SubGuarantorID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getBorrowerName() {
        return BorrowerName;
    }

    public String getBillingID() {
        return BillingID;
    }

    public String getStatementDate() {
        return StatementDate;
    }

    public String getDueDate() {
        return DueDate;
    }

    public double getAmount() {
        return Amount;
    }

    public String getPeriodFrom() {
        return PeriodFrom;
    }

    public String getPeriodTo() {
        return PeriodTo;
    }

    public String getIsNotified() {
        return isNotified;
    }

    public String getExtra1() {
        return Extra1;
    }

    public String getExtra2() {
        return Extra2;
    }

    public String getExtra3() {
        return Extra3;
    }

    public String getExtra4() {
        return Extra4;
    }

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }


    public static final Parcelable.Creator<SubscriberBillSummary> CREATOR
            = new Parcelable.Creator<SubscriberBillSummary>() {
        public SubscriberBillSummary createFromParcel(Parcel in) {
            return new SubscriberBillSummary(in);
        }

        public SubscriberBillSummary[] newArray(int size) {
            return new SubscriberBillSummary[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeIN);
        dest.writeString(GuarantorID);
        dest.writeString(GuarantorName);
        dest.writeString(SubGuarantorID);
        dest.writeString(BorrowerID);
        dest.writeString(BorrowerName);
        dest.writeString(BillingID);
        dest.writeString(StatementDate);
        dest.writeString(DueDate);
        dest.writeDouble(Amount);
        dest.writeString(PeriodFrom);
        dest.writeString(PeriodTo);
        dest.writeString(isNotified);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    protected SubscriberBillSummary(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        GuarantorID = in.readString();
        GuarantorName = in.readString();
        SubGuarantorID = in.readString();
        BorrowerID = in.readString();
        BorrowerName = in.readString();
        BillingID = in.readString();
        StatementDate = in.readString();
        DueDate = in.readString();
        Amount = in.readDouble();
        PeriodFrom = in.readString();
        PeriodTo = in.readString();
        isNotified = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }
}
