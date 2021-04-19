package com.goodkredit.myapplication.model.v2Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionModel {

    @SerializedName("DateTimeIN")
    @Expose
    String DateTimeIN;

    @SerializedName("DateTimeCompleted")
    @Expose
    String DateTimeCompleted;

    @SerializedName("TransferTxnNo")
    @Expose
    String TransferTxnNo;

    @SerializedName("TransferType")
    @Expose
    String TransferType;

    @SerializedName("SourceBorrowerID")
    @Expose
    String SourceBorrowerID;

    @SerializedName("SourceBorrowerName")
    @Expose
    String SourceBorrowerName;

    @SerializedName("RecipientBorrowerID")
    @Expose
    String RecipientBorrowerID;

    @SerializedName("RecipientBorrowerName")
    @Expose
    String RecipientBorrowerName;

    @SerializedName("RecipientMobileNo")
    @Expose
    String RecipientMobileNo;

    @SerializedName("RecipientEmailAddress")
    @Expose
    String RecipientEmailAddress;

    @SerializedName("VoucherSerialNo")
    @Expose
    String VoucherSerialNo;

    @SerializedName("VoucherCode")
    @Expose
    String VoucherCode;

    @SerializedName("VoucherDenoms")
    @Expose
    String VoucherDenoms;

    @SerializedName("ProcessID")
    @Expose
    String ProcessID;

    @SerializedName("Status")
    @Expose
    String Status;

    @SerializedName("Extra1")
    @Expose
    String Extra1;

    @SerializedName("Extra2")
    @Expose
    String Extra2;
    @SerializedName("Extra3")
    @Expose
    String Extra3;
    @SerializedName("Extra4")
    @Expose
    String Extra4;
    @SerializedName("Notes1")
    @Expose
    String Notes1;
    @SerializedName("Notes2")
    @Expose
    String Notes2;

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getTransferTxnNo() {
        return TransferTxnNo;
    }

    public String getTransferType() {
        return TransferType;
    }

    public String getSourceBorrowerID() {
        return SourceBorrowerID;
    }

    public String getSourceBorrowerName() {
        return SourceBorrowerName;
    }

    public String getRecipientBorrowerID() {
        return RecipientBorrowerID;
    }

    public String getRecipientBorrowerName() {
        return RecipientBorrowerName;
    }

    public String getRecipientMobileNo() {
        return RecipientMobileNo;
    }

    public String getRecipientEmailAddress() {
        return RecipientEmailAddress;
    }

    public String getVoucherSerialNo() {
        return VoucherSerialNo;
    }

    public String getVoucherCode() {
        return VoucherCode;
    }

    public String getVoucherDenoms() {
        return VoucherDenoms;
    }

    public String getProcessID() {
        return ProcessID;
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

    public TransactionModel(String dateTimeCompleted, String transferTxnNo, String transferType, String sourceBorrowerID, String sourceBorrowerName, String recipientBorrowerID, String recipientBorrowerName, String recipientMobileNo, String recipientEmailAddress, String voucherSerialNo, String voucherCode, String voucherDenoms, String processID, String status) {
        DateTimeCompleted = dateTimeCompleted;
        TransferTxnNo = transferTxnNo;
        TransferType = transferType;
        SourceBorrowerID = sourceBorrowerID;
        SourceBorrowerName = sourceBorrowerName;
        RecipientBorrowerID = recipientBorrowerID;
        RecipientBorrowerName = recipientBorrowerName;
        RecipientMobileNo = recipientMobileNo;
        RecipientEmailAddress = recipientEmailAddress;
        VoucherSerialNo = voucherSerialNo;
        VoucherCode = voucherCode;
        VoucherDenoms = voucherDenoms;
        ProcessID = processID;
        Status = status;
    }
}
