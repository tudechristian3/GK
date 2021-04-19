package com.goodkredit.myapplication.model.gkearn;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKEarnConversionPoints implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("CampaignID")
    @Expose
    private String CampaignID;
    @SerializedName("ConversionTxnNo")
    @Expose
    private String ConversionTxnNo;
    @SerializedName("VoucherProcessID")
    @Expose
    private String VoucherProcessID;
    @SerializedName("ReleasingTxnNo")
    @Expose
    private String ReleasingTxnNo;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("SponsorID")
    @Expose
    private String SponsorID;
    @SerializedName("TotalNoOfVouchers")
    @Expose
    private double TotalNoOfVouchers;
    @SerializedName("TotalVoucherAmount")
    @Expose
    private double TotalVoucherAmount;
    @SerializedName("TotalSC")
    @Expose
    private double TotalSC;
    @SerializedName("BorrowerPointsDeducted")
    @Expose
    private double BorrowerPointsDeducted;
    @SerializedName("BorrowerPrePoints")
    @Expose
    private double BorrowerPrePoints;
    @SerializedName("BorrowerPostPoints")
    @Expose
    private double BorrowerPostPoints;
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

    public GKEarnConversionPoints(int ID, String dateTimeIN, String dateTimeCompleted, String campaignID, String conversionTxnNo, String voucherProcessID, String releasingTxnNo, String borrowerID, String sponsorID, double totalNoOfVouchers, double totalVoucherAmount, double totalSC, double borrowerPointsDeducted, double borrowerPrePoints, double borrowerPostPoints, String transactionMedium, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        DateTimeCompleted = dateTimeCompleted;
        CampaignID = campaignID;
        ConversionTxnNo = conversionTxnNo;
        VoucherProcessID = voucherProcessID;
        ReleasingTxnNo = releasingTxnNo;
        BorrowerID = borrowerID;
        SponsorID = sponsorID;
        TotalNoOfVouchers = totalNoOfVouchers;
        TotalVoucherAmount = totalVoucherAmount;
        TotalSC = totalSC;
        BorrowerPointsDeducted = borrowerPointsDeducted;
        BorrowerPrePoints = borrowerPrePoints;
        BorrowerPostPoints = borrowerPostPoints;
        TransactionMedium = transactionMedium;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected GKEarnConversionPoints(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        DateTimeCompleted = in.readString();
        CampaignID = in.readString();
        ConversionTxnNo = in.readString();
        VoucherProcessID = in.readString();
        ReleasingTxnNo = in.readString();
        BorrowerID = in.readString();
        SponsorID = in.readString();
        TotalNoOfVouchers = in.readDouble();
        TotalVoucherAmount = in.readDouble();
        TotalSC = in.readDouble();
        BorrowerPointsDeducted = in.readDouble();
        BorrowerPrePoints = in.readDouble();
        BorrowerPostPoints = in.readDouble();
        TransactionMedium = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeIN);
        dest.writeString(DateTimeCompleted);
        dest.writeString(CampaignID);
        dest.writeString(ConversionTxnNo);
        dest.writeString(VoucherProcessID);
        dest.writeString(ReleasingTxnNo);
        dest.writeString(BorrowerID);
        dest.writeString(SponsorID);
        dest.writeDouble(TotalNoOfVouchers);
        dest.writeDouble(TotalVoucherAmount);
        dest.writeDouble(TotalSC);
        dest.writeDouble(BorrowerPointsDeducted);
        dest.writeDouble(BorrowerPrePoints);
        dest.writeDouble(BorrowerPostPoints);
        dest.writeString(TransactionMedium);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GKEarnConversionPoints> CREATOR = new Creator<GKEarnConversionPoints>() {
        @Override
        public GKEarnConversionPoints createFromParcel(Parcel in) {
            return new GKEarnConversionPoints(in);
        }

        @Override
        public GKEarnConversionPoints[] newArray(int size) {
            return new GKEarnConversionPoints[size];
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

    public String getCampaignID() {
        return CampaignID;
    }

    public String getConversionTxnNo() {
        return ConversionTxnNo;
    }

    public String getVoucherProcessID() {
        return VoucherProcessID;
    }

    public String getReleasingTxnNo() {
        return ReleasingTxnNo;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getSponsorID() {
        return SponsorID;
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

    public double getBorrowerPointsDeducted() {
        return BorrowerPointsDeducted;
    }

    public double getBorrowerPrePoints() {
        return BorrowerPrePoints;
    }

    public double getBorrowerPostPoints() {
        return BorrowerPostPoints;
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
}
