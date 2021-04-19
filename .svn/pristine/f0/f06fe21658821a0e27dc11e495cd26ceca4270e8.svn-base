package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 7/15/2017.
 */

public class PaymentSummary implements Parcelable {

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("PaymentTxnNo")
    @Expose
    private String PaymentTxnNo;
    @SerializedName("AccountType")
    @Expose
    private String AccountType;
    @SerializedName("AccountID")
    @Expose
    private String AccountID;
    @SerializedName("AccountName")
    @Expose
    private String AccountName;
    @SerializedName("Amount")
    @Expose
    private double Amount;
    @SerializedName("isAdvancePayment")
    @Expose
    private String isAdvancePayment;
    @SerializedName("CollectedBy")
    @Expose
    private String CollectedBy;
    @SerializedName("CollectedByUser")
    @Expose
    private String CollectedByUser;
    @SerializedName("CollectionMedium")
    @Expose
    private String CollectionMedium;
    @SerializedName("PaymentOption")
    @Expose
    private String PaymentOption;
    @SerializedName("Status")
    @Expose
    private String Status;
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

    public PaymentSummary(int ID, String dateTimeIN, String paymentTxnNo, String accountType, String accountID, String accountName,
                          double amount, String isAdvancePayment, String collectedBy, String collectedByUser, String collectionMedium,
                          String paymentOption, String status, String extra1, String extra2, String extra3, String extra4, String notes1,
                          String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        PaymentTxnNo = paymentTxnNo;
        AccountType = accountType;
        AccountID = accountID;
        AccountName = accountName;
        Amount = amount;
        this.isAdvancePayment = isAdvancePayment;
        CollectedBy = collectedBy;
        CollectedByUser = collectedByUser;
        CollectionMedium = collectionMedium;
        PaymentOption = paymentOption;
        Status = status;
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

    public String getPaymentTxnNo() {
        return PaymentTxnNo;
    }

    public String getAccountType() {
        return AccountType;
    }

    public String getAccountID() {
        return AccountID;
    }

    public String getAccountName() {
        return AccountName;
    }

    public double getAmount() {
        return Amount;
    }

    public String getIsAdvancePayment() {
        return isAdvancePayment;
    }

    public String getCollectedBy() {
        return CollectedBy;
    }

    public String getCollectedByUser() {
        return CollectedByUser;
    }

    public String getCollectionMedium() {
        return CollectionMedium;
    }

    public String getPaymentOption() {
        return PaymentOption;
    }

    public String getStatus() {
        return Status;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeIN);
        dest.writeString(PaymentTxnNo);
        dest.writeString(AccountType);
        dest.writeString(AccountID);
        dest.writeString(AccountName);
        dest.writeDouble(Amount);
        dest.writeString(isAdvancePayment);
        dest.writeString(CollectedBy);
        dest.writeString(CollectedByUser);
        dest.writeString(CollectionMedium);
        dest.writeString(PaymentOption);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    protected PaymentSummary(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        PaymentTxnNo = in.readString();
        AccountType = in.readString();
        AccountID = in.readString();
        AccountName = in.readString();
        Amount = in.readDouble();
        isAdvancePayment = in.readString();
        CollectedBy = in.readString();
        CollectedByUser = in.readString();
        CollectionMedium = in.readString();
        PaymentOption = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    public static final Parcelable.Creator<PaymentSummary> CREATOR
            = new Parcelable.Creator<PaymentSummary>() {
        public PaymentSummary createFromParcel(Parcel in) {
            return new PaymentSummary(in);
        }

        public PaymentSummary[] newArray(int size) {
            return new PaymentSummary[size];
        }
    };

}
