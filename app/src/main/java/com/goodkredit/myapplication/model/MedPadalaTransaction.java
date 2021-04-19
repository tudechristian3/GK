package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 2/27/2018.
 */

public class MedPadalaTransaction implements Parcelable {

    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("TransactionNo")
    @Expose
    private String TransactionNo;
    @SerializedName("MedpadalaTxnNo")
    @Expose
    private String MedpadalaTxnNo;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("UserID")
    @Expose
    private String UserID;
    @SerializedName("RecipientMobileNo")
    @Expose
    private String RecipientMobileNo;
    @SerializedName("Amount")
    @Expose
    private double Amount;
    @SerializedName("ServiceCharge")
    @Expose
    private double ServiceCharge;
    @SerializedName("OtherCharges")
    @Expose
    private double OtherCharges;
    @SerializedName("TotalAmount")
    @Expose
    private double TotalAmount;
    @SerializedName("TxnMedium")
    @Expose
    private String TxnMedium;
    @SerializedName("PreConsummationSessionID")
    @Expose
    private String PreConsummationSessionID;
    @SerializedName("GCCode")
    @Expose
    private String GCCode;
    @SerializedName("TxnStatus")
    @Expose
    private String TxnStatus;

    protected MedPadalaTransaction(Parcel in) {
        DateTimeCompleted = in.readString();
        TransactionNo = in.readString();
        MedpadalaTxnNo = in.readString();
        BorrowerID = in.readString();
        UserID = in.readString();
        RecipientMobileNo = in.readString();
        Amount = in.readDouble();
        ServiceCharge = in.readDouble();
        OtherCharges = in.readDouble();
        TotalAmount = in.readDouble();
        TxnMedium = in.readString();
        PreConsummationSessionID = in.readString();
        GCCode = in.readString();
        TxnStatus = in.readString();
    }

    public static final Creator<MedPadalaTransaction> CREATOR = new Creator<MedPadalaTransaction>() {
        @Override
        public MedPadalaTransaction createFromParcel(Parcel in) {
            return new MedPadalaTransaction(in);
        }

        @Override
        public MedPadalaTransaction[] newArray(int size) {
            return new MedPadalaTransaction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DateTimeCompleted);
        dest.writeString(TransactionNo);
        dest.writeString(MedpadalaTxnNo);
        dest.writeString(BorrowerID);
        dest.writeString(UserID);
        dest.writeString(RecipientMobileNo);
        dest.writeDouble(Amount);
        dest.writeDouble(ServiceCharge);
        dest.writeDouble(OtherCharges);
        dest.writeDouble(TotalAmount);
        dest.writeString(TxnMedium);
        dest.writeString(PreConsummationSessionID);
        dest.writeString(GCCode);
        dest.writeString(TxnStatus);
    }

    public MedPadalaTransaction(String dateTimeCompleted, String transactionNo, String medpadalaTxnNo,
                                String borrowerID, String userID, String recipientMobileNo,
                                double amount, double serviceCharge, double otherCharges, double totalAmount,
                                String txnMedium, String preConsummationSessionID, String GCCode, String TxnStatus) {
        DateTimeCompleted = dateTimeCompleted;
        TransactionNo = transactionNo;
        MedpadalaTxnNo = medpadalaTxnNo;
        BorrowerID = borrowerID;
        UserID = userID;
        RecipientMobileNo = recipientMobileNo;
        Amount = amount;
        ServiceCharge = serviceCharge;
        OtherCharges = otherCharges;
        TotalAmount = totalAmount;
        TxnMedium = txnMedium;
        PreConsummationSessionID = preConsummationSessionID;
        this.GCCode = GCCode;
        this.TxnStatus = TxnStatus;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getMedpadalaTxnNo() {
        return MedpadalaTxnNo;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getUserID() {
        return UserID;
    }

    public String getRecipientMobileNo() {
        return RecipientMobileNo;
    }

    public double getAmount() {
        return Amount;
    }

    public double getServiceCharge() {
        return ServiceCharge;
    }

    public double getOtherCharges() {
        return OtherCharges;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public String getTxnMedium() {
        return TxnMedium;
    }

    public String getPreConsummationSessionID() {
        return PreConsummationSessionID;
    }

    public String getGCCode() {
        return GCCode;
    }

    public String getTxnStatus() {
        return TxnStatus;
    }
}
