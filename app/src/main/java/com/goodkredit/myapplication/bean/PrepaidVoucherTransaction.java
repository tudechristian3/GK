package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 5/25/2017.
 */

public class PrepaidVoucherTransaction implements Parcelable {

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("TransactionNo")
    @Expose
    private String TransactionNo;
    @SerializedName("BorrowingType")
    @Expose
    private String BorrowingType;
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("GuarantorName")
    @Expose
    private String GuarantorName;
    @SerializedName("SubGuarantorID")
    @Expose
    private String SubGuarantorID;
    @SerializedName("SubGuarantorName")
    @Expose
    private String SubGuarantorName;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("BorrowerName")
    @Expose
    private String BorrowerName;
    @SerializedName("TotalNoOfVouchers")
    @Expose
    private int TotalNoOfVouchers;
    @SerializedName("TotalVoucherAmount")
    @Expose
    private double TotalVoucherAmount;
    @SerializedName("isThruSubGuarantor")
    @Expose
    private int isThruSubGuarantor;
    @SerializedName("GuarantorPreNoOfVouchers")
    @Expose
    private int GuarantorPreNoOfVouchers;
    @SerializedName("GuarantorPostNoOfVouchers")
    @Expose
    private int GuarantorPostNoOfVouchers;
    @SerializedName("SubGuarantorPreNoOfVouchers")
    @Expose
    private int SubGuarantorPreNoOfVouchers;
    @SerializedName("SubGuarantorPostNoOfVouchers")
    @Expose
    private int SubGuarantorPostNoOfVouchers;
    @SerializedName("BorrowerPreNoOfVouchers")
    @Expose
    private int BorrowerPreNoOfVouchers;
    @SerializedName("BorrowerPostNoOfVouchers")
    @Expose
    private int BorrowerPostNoOfVouchers;
    @SerializedName("TransactionMedium")
    @Expose
    private String TransactionMedium;
    @SerializedName("IMEI")
    @Expose
    private String IMEI;
    @SerializedName("UserID")
    @Expose
    private String UserID;
    @SerializedName("ScheduleProcessID")
    @Expose
    private String ScheduleProcessID;
    @SerializedName("BorrowScheduleID")
    @Expose
    private String BorrowScheduleID;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("VoucherGenerationID")
    @Expose
    private String VoucherGenerationID;
    @SerializedName("PaymentTxnNo")
    @Expose
    private String PaymentTxnNo;

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getBorrowingType() {
        return BorrowingType;
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

    public String getSubGuarantorName() {
        return SubGuarantorName;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getBorrowerName() {
        return BorrowerName;
    }

    public int getTotalNoOfVouchers() {
        return TotalNoOfVouchers;
    }

    public double getTotalVoucherAmount() {
        return TotalVoucherAmount;
    }

    public int getIsThruSubGuarantor() {
        return isThruSubGuarantor;
    }

    public int getGuarantorPreNoOfVouchers() {
        return GuarantorPreNoOfVouchers;
    }

    public int getGuarantorPostNoOfVouchers() {
        return GuarantorPostNoOfVouchers;
    }

    public int getSubGuarantorPreNoOfVouchers() {
        return SubGuarantorPreNoOfVouchers;
    }

    public int getSubGuarantorPostNoOfVouchers() {
        return SubGuarantorPostNoOfVouchers;
    }

    public int getBorrowerPreNoOfVouchers() {
        return BorrowerPreNoOfVouchers;
    }

    public int getBorrowerPostNoOfVouchers() {
        return BorrowerPostNoOfVouchers;
    }

    public String getTransactionMedium() {
        return TransactionMedium;
    }

    public String getIMEI() {
        return IMEI;
    }

    public String getUserID() {
        return UserID;
    }

    public String getScheduleProcessID() {
        return ScheduleProcessID;
    }

    public String getBorrowScheduleID() {
        return BorrowScheduleID;
    }

    public String getStatus() {
        return Status;
    }

    public String getVoucherGenerationID() {
        return VoucherGenerationID;
    }

    public String getPaymentTxnNo() {
        return PaymentTxnNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeIN);
        dest.writeString(DateTimeCompleted);
        dest.writeString(TransactionNo);
        dest.writeString(BorrowingType);
        dest.writeString(GuarantorID);
        dest.writeString(GuarantorName);
        dest.writeString(SubGuarantorID);
        dest.writeString(SubGuarantorName);
        dest.writeString(BorrowerID);
        dest.writeString(BorrowerName);
        dest.writeInt(TotalNoOfVouchers);
        dest.writeDouble(TotalVoucherAmount);
        dest.writeInt(isThruSubGuarantor);
        dest.writeInt(GuarantorPreNoOfVouchers);
        dest.writeInt(GuarantorPostNoOfVouchers);
        dest.writeInt(SubGuarantorPreNoOfVouchers);
        dest.writeInt(SubGuarantorPostNoOfVouchers);
        dest.writeInt(BorrowerPreNoOfVouchers);
        dest.writeInt(BorrowerPostNoOfVouchers);
        dest.writeString(TransactionMedium);
        dest.writeString(IMEI);
        dest.writeString(UserID);
        dest.writeString(ScheduleProcessID);
        dest.writeString(BorrowScheduleID);
        dest.writeString(Status);
        dest.writeString(VoucherGenerationID);
        dest.writeString(PaymentTxnNo);
    }

    private PrepaidVoucherTransaction(Parcel in) {
        this.ID = in.readInt();
        this.DateTimeIN = in.readString();
        this.DateTimeCompleted = in.readString();
        this.TransactionNo = in.readString();
        this.BorrowingType = in.readString();
        this.GuarantorID = in.readString();
        this.GuarantorName = in.readString();
        this.SubGuarantorID = in.readString();
        this.SubGuarantorName = in.readString();
        this.BorrowerID = in.readString();
        this.BorrowerName = in.readString();
        this.TotalNoOfVouchers = in.readInt();
        this.TotalVoucherAmount = in.readDouble();
        this.isThruSubGuarantor = in.readInt();
        this.GuarantorPreNoOfVouchers = in.readInt();
        this.GuarantorPostNoOfVouchers = in.readInt();
        this.SubGuarantorPreNoOfVouchers = in.readInt();
        this.SubGuarantorPostNoOfVouchers = in.readInt();
        this.BorrowerPreNoOfVouchers = in.readInt();
        this.BorrowerPostNoOfVouchers = in.readInt();
        this.TransactionMedium = in.readString();
        this.IMEI = in.readString();
        this.UserID = in.readString();
        this.ScheduleProcessID = in.readString();
        this.BorrowScheduleID = in.readString();
        this.Status = in.readString();
        this.VoucherGenerationID = in.readString();
        this.PaymentTxnNo = in.readString();
    }

    public static final Creator<PrepaidVoucherTransaction> CREATOR = new Creator<PrepaidVoucherTransaction>() {
        @Override
        public PrepaidVoucherTransaction createFromParcel(Parcel source) {
            return new PrepaidVoucherTransaction(source);
        }

        @Override
        public PrepaidVoucherTransaction[] newArray(int size) {
            return new PrepaidVoucherTransaction[size];
        }
    };

    public PrepaidVoucherTransaction(int ID, String dateTimeIN, String dateTimeCompleted, String transactionNo, String borrowingType, String guarantorID, String guarantorName, String subGuarantorID, String subGuarantorName, String borrowerID, String borrowerName, int totalNoOfVouchers, double totalVoucherAmount, int isThruSubGuarantor, int guarantorPreNoOfVouchers, int guarantorPostNoOfVouchers, int subGuarantorPreNoOfVouchers, int subGuarantorPostNoOfVouchers, int borrowerPreNoOfVouchers, int borrowerPostNoOfVouchers, String transactionMedium, String IMEI, String userID, String scheduleProcessID, String borrowScheduleID, String status, String voucherGenerationID, String paymentTxnNo) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        DateTimeCompleted = dateTimeCompleted;
        TransactionNo = transactionNo;
        BorrowingType = borrowingType;
        GuarantorID = guarantorID;
        GuarantorName = guarantorName;
        SubGuarantorID = subGuarantorID;
        SubGuarantorName = subGuarantorName;
        BorrowerID = borrowerID;
        BorrowerName = borrowerName;
        TotalNoOfVouchers = totalNoOfVouchers;
        TotalVoucherAmount = totalVoucherAmount;
        this.isThruSubGuarantor = isThruSubGuarantor;
        GuarantorPreNoOfVouchers = guarantorPreNoOfVouchers;
        GuarantorPostNoOfVouchers = guarantorPostNoOfVouchers;
        SubGuarantorPreNoOfVouchers = subGuarantorPreNoOfVouchers;
        SubGuarantorPostNoOfVouchers = subGuarantorPostNoOfVouchers;
        BorrowerPreNoOfVouchers = borrowerPreNoOfVouchers;
        BorrowerPostNoOfVouchers = borrowerPostNoOfVouchers;
        TransactionMedium = transactionMedium;
        this.IMEI = IMEI;
        UserID = userID;
        ScheduleProcessID = scheduleProcessID;
        BorrowScheduleID = borrowScheduleID;
        Status = status;
        VoucherGenerationID = voucherGenerationID;
        PaymentTxnNo = paymentTxnNo;
    }
}
