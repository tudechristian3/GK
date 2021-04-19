package com.goodkredit.myapplication.model.coopassistant.member;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoopAssistantTransactionHistory implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("CoopID")
    @Expose
    private String CoopID;
    @SerializedName("CoopName")
    @Expose
    private String CoopName;
    @SerializedName("GetVoucherTxnNo")
    @Expose
    private String GetVoucherTxnNo;
    @SerializedName("VoucherProcessID")
    @Expose
    private String VoucherProcessID;
    @SerializedName("BorrowingsTxnNo")
    @Expose
    private String BorrowingsTxnNo;
    @SerializedName("SponsorID")
    @Expose
    private String SponsorID;
    @SerializedName("SponsorName")
    @Expose
    private String SponsorName;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("MemberID")
    @Expose
    private String MemberID;
    @SerializedName("MemberName")
    @Expose
    private String MemberName;
    @SerializedName("MemberMobileNo")
    @Expose
    private String MemberMobileNo;
    @SerializedName("TotalNoOfVouchers")
    @Expose
    private double TotalNoOfVouchers;
    @SerializedName("TotalVoucherAmount")
    @Expose
    private double TotalVoucherAmount;
    @SerializedName("TotalSC")
    @Expose
    private double TotalSC;
    @SerializedName("MemberPreAvailableCredit")
    @Expose
    private double MemberPreAvailableCredit;
    @SerializedName("MemberPostAvailableCredits")
    @Expose
    private double MemberPostAvailableCredits;
    @SerializedName("TransactionMedium")
    @Expose
    private String TransactionMedium;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;

    public CoopAssistantTransactionHistory(int ID, String dateTimeIN, String dateTimeCompleted, String coopID, String coopName, String getVoucherTxnNo, String voucherProcessID, String borrowingsTxnNo, String sponsorID, String sponsorName, String borrowerID, String memberID, String memberName, String memberMobileNo, double totalNoOfVouchers, double totalVoucherAmount, double totalSC, double memberPreAvailableCredit, double memberPostAvailableCredits, String transactionMedium, String status, String extra1, String extra2, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        DateTimeCompleted = dateTimeCompleted;
        CoopID = coopID;
        CoopName = coopName;
        GetVoucherTxnNo = getVoucherTxnNo;
        VoucherProcessID = voucherProcessID;
        BorrowingsTxnNo = borrowingsTxnNo;
        SponsorID = sponsorID;
        SponsorName = sponsorName;
        BorrowerID = borrowerID;
        MemberID = memberID;
        MemberName = memberName;
        MemberMobileNo = memberMobileNo;
        TotalNoOfVouchers = totalNoOfVouchers;
        TotalVoucherAmount = totalVoucherAmount;
        TotalSC = totalSC;
        MemberPreAvailableCredit = memberPreAvailableCredit;
        MemberPostAvailableCredits = memberPostAvailableCredits;
        TransactionMedium = transactionMedium;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected CoopAssistantTransactionHistory(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        DateTimeCompleted = in.readString();
        CoopID = in.readString();
        CoopName = in.readString();
        GetVoucherTxnNo = in.readString();
        VoucherProcessID = in.readString();
        BorrowingsTxnNo = in.readString();
        SponsorID = in.readString();
        SponsorName = in.readString();
        BorrowerID = in.readString();
        MemberID = in.readString();
        MemberName = in.readString();
        MemberMobileNo = in.readString();
        TotalNoOfVouchers = in.readDouble();
        TotalVoucherAmount = in.readDouble();
        TotalSC = in.readDouble();
        MemberPreAvailableCredit = in.readDouble();
        MemberPostAvailableCredits = in.readDouble();
        TransactionMedium = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeIN);
        dest.writeString(DateTimeCompleted);
        dest.writeString(CoopID);
        dest.writeString(CoopName);
        dest.writeString(GetVoucherTxnNo);
        dest.writeString(VoucherProcessID);
        dest.writeString(BorrowingsTxnNo);
        dest.writeString(SponsorID);
        dest.writeString(SponsorName);
        dest.writeString(BorrowerID);
        dest.writeString(MemberID);
        dest.writeString(MemberName);
        dest.writeString(MemberMobileNo);
        dest.writeDouble(TotalNoOfVouchers);
        dest.writeDouble(TotalVoucherAmount);
        dest.writeDouble(TotalSC);
        dest.writeDouble(MemberPreAvailableCredit);
        dest.writeDouble(MemberPostAvailableCredits);
        dest.writeString(TransactionMedium);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CoopAssistantTransactionHistory> CREATOR = new Creator<CoopAssistantTransactionHistory>() {
        @Override
        public CoopAssistantTransactionHistory createFromParcel(Parcel in) {
            return new CoopAssistantTransactionHistory(in);
        }

        @Override
        public CoopAssistantTransactionHistory[] newArray(int size) {
            return new CoopAssistantTransactionHistory[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getCoopID() {
        return CoopID;
    }

    public String getCoopName() {
        return CoopName;
    }

    public String getGetVoucherTxnNo() {
        return GetVoucherTxnNo;
    }

    public String getVoucherProcessID() {
        return VoucherProcessID;
    }

    public String getBorrowingsTxnNo() {
        return BorrowingsTxnNo;
    }

    public String getSponsorID() {
        return SponsorID;
    }

    public String getSponsorName() {
        return SponsorName;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getMemberID() {
        return MemberID;
    }

    public String getMemberName() {
        return MemberName;
    }

    public String getMemberMobileNo() {
        return MemberMobileNo;
    }

    public double getTotalNoOfVouchers() {
        return TotalNoOfVouchers;
    }

    public double getTotalVoucherAmount() {
        return TotalVoucherAmount;
    }

    public double getTotalSC() {
        return TotalSC;
    }

    public double getMemberPreAvailableCredit() {
        return MemberPreAvailableCredit;
    }

    public double getMemberPostAvailableCredits() {
        return MemberPostAvailableCredits;
    }

    public String getTransactionMedium() {
        return TransactionMedium;
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

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }
}
