package com.goodkredit.myapplication.bean.loadmessenger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReplenishLogs {

    @SerializedName("DateTimeCompleted")
    @Expose
    String DateTimeCompleted;


    @SerializedName("DateTimeIN")
    @Expose
    String DateTimeIN;


    @SerializedName("TransactionNo")
    @Expose
    String TransactionNo;


    @SerializedName("Amount")
    @Expose
    String Amount;


    @SerializedName("PreCredits")
    @Expose
    String PreCredits;


    @SerializedName("PostCredits")
    @Expose
    String PostCredits;


    @SerializedName("PreAvailableCredits")
    @Expose
    String PreAvailableCredits;


    @SerializedName("PostAvailableCredits")
    @Expose
    String PostAvailableCredits;

    @SerializedName("PreUsedCredits")
    @Expose
    String PreUsedCredits;


    @SerializedName("PostUsedCredits")
    @Expose
    String PostUsedCredits;


    @SerializedName("BorrowerID")
    @Expose
    String BorrowerID;


    @SerializedName("SenderID")
    @Expose
    String SenderID;


    @SerializedName("LogType")
    @Expose
    String LogType;


    @SerializedName("XMLDetails")
    @Expose
    String XMLDetails;

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

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getAmount() {
        return Amount;
    }

    public String getPreCredits() {
        return PreCredits;
    }

    public String getPostCredits() {
        return PostCredits;
    }

    public String getPreAvailableCredits() {
        return PreAvailableCredits;
    }

    public String getPostAvailableCredits() {
        return PostAvailableCredits;
    }

    public String getPreUsedCredits() {
        return PreUsedCredits;
    }

    public String getPostUsedCredits() {
        return PostUsedCredits;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getSenderID() {
        return SenderID;
    }

    public String getLogType() {
        return LogType;
    }

    public String getXMLDetails() {
        return XMLDetails;
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
