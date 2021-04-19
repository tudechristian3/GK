package com.goodkredit.myapplication.bean.loadmessenger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadTransaction {

    @SerializedName("DateTimeIN")
    @Expose
    String DateTimeIN;

    @SerializedName("DateTimeCompleted")
    @Expose
    String DateTimeCompleted;

    @SerializedName("GuarantorID")
    @Expose
    String GuarantorID;

    @SerializedName("BorrowerID")
    @Expose
    String BorrowerID;

    @SerializedName("UserID")
    @Expose
    String UserID;

    @SerializedName("TransactionNo")
    @Expose
    String TransactionNo;

    @SerializedName("ProviderTxnNo")
    @Expose
    String ProviderTxnNo;

    @SerializedName("TelcoTxnNo")
    @Expose
    String TelcoTxnNo;

    @SerializedName("Network")
    @Expose
    String Network;

    @SerializedName("MobileTarget")
    @Expose
    String MobileTarget;

    @SerializedName("ProductCode")
    @Expose
    String ProductCode;

    @SerializedName("DenomType")
    @Expose
    String DenomType;

    @SerializedName("Amount")
    @Expose
    String Amount;

    @SerializedName("TxnStatus")
    @Expose
    String TxnStatus;

    @SerializedName("TxnMedium")
    @Expose
    String TxnMedium;

    @SerializedName("ProcessID")
    @Expose
    String ProcessID;

    @SerializedName("IMEI")
    @Expose
    String IMEI;

    @SerializedName("Extra1")
    @Expose
    String Extra1;

    @SerializedName("Extra2")
    @Expose
    String Extra2;

    @SerializedName("Notes1")
    @Expose
    String Notes1;

    @SerializedName("Notes2")
    @Expose
    String Notes2;

    @SerializedName("FBName")
    @Expose
    String FBName;

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getUserID() {
        return UserID;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getProviderTxnNo() {
        return ProviderTxnNo;
    }

    public String getTelcoTxnNo() {
        return TelcoTxnNo;
    }

    public String getNetwork() {
        return Network;
    }

    public String getMobileTarget() {
        return MobileTarget;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public String getDenomType() {
        return DenomType;
    }

    public String getAmount() {
        return Amount;
    }

    public String getTxnStatus() {
        return TxnStatus;
    }

    public String getTxnMedium() {
        return TxnMedium;
    }

    public String getProcessID() {
        return ProcessID;
    }

    public String getIMEI() {
        return IMEI;
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

    public String getFBName() {
        return FBName;
    }
}
