package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User-PC on 8/1/2017.
 */

public class PrepaidLogs implements Parcelable {
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("TransactionNo")
    @Expose
    private String TransactionNo;
    @SerializedName("MobileTarget")
    @Expose
    private String MobileTarget;
    @SerializedName("ProductCode")
    @Expose
    private String ProductCode;
    @SerializedName("Amount")
    @Expose
    private String Amount;
    @SerializedName("TxnStatus")
    @Expose
    private String TxnStatus;
    @SerializedName("DenomType")
    @Expose
    private String DenomType;
    @SerializedName("Network")
    @Expose
    private String Network;
    @SerializedName("Discount")
    @Expose
    private double Discount;
    @SerializedName("NetAmount")
    @Expose
    private double NetAmount;

    public PrepaidLogs(String dateTimeCompleted, String transactionNo, String mobileTarget, String productCode, String amount, String txnStatus, String denomType, String network) {
        DateTimeCompleted = dateTimeCompleted;
        TransactionNo = transactionNo;
        MobileTarget = mobileTarget;
        ProductCode = productCode;
        Amount = amount;
        TxnStatus = txnStatus;
        DenomType = denomType;
        Network = network;
    }

    public PrepaidLogs(String dateTimeCompleted, String transactionNo, String mobileTarget, String productCode, String amount, String txnStatus, String denomType, String network, double discount, double netAmount) {
        DateTimeCompleted = dateTimeCompleted;
        TransactionNo = transactionNo;
        MobileTarget = mobileTarget;
        ProductCode = productCode;
        Amount = amount;
        TxnStatus = txnStatus;
        DenomType = denomType;
        Network = network;
        Discount = discount;
        NetAmount = netAmount;
    }

    protected PrepaidLogs(Parcel in) {
        DateTimeCompleted = in.readString();
        TransactionNo = in.readString();
        MobileTarget = in.readString();
        ProductCode = in.readString();
        Amount = in.readString();
        TxnStatus = in.readString();
        DenomType = in.readString();
        Network = in.readString();
        Discount = in.readDouble();
        NetAmount = in.readDouble();
    }

    public static final Creator<PrepaidLogs> CREATOR = new Creator<PrepaidLogs>() {
        @Override
        public PrepaidLogs createFromParcel(Parcel in) {
            return new PrepaidLogs(in);
        }

        @Override
        public PrepaidLogs[] newArray(int size) {
            return new PrepaidLogs[size];
        }
    };

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public void setDateTimeCompleted(String dateTimeCompleted) {
        DateTimeCompleted = dateTimeCompleted;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        TransactionNo = transactionNo;
    }

    public String getMobileTarget() {
        return MobileTarget;
    }

    public void setMobileTarget(String mobileTarget) {
        MobileTarget = mobileTarget;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTxnStatus() {
        return TxnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        TxnStatus = txnStatus;
    }

    public String getDenomType() {
        return DenomType;
    }

    public void setDenomType(String denomType) {
        DenomType = denomType;
    }

    public String getNetwork() {
        return Network;
    }

    public void setNetwork(String network) {
        Network = network;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(double netAmount) {
        NetAmount = netAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DateTimeCompleted);
        dest.writeString(TransactionNo);
        dest.writeString(MobileTarget);
        dest.writeString(ProductCode);
        dest.writeString(Amount);
        dest.writeString(TxnStatus);
        dest.writeString(DenomType);
        dest.writeString(Network);
        dest.writeDouble(Discount);
        dest.writeDouble(NetAmount);
    }
}
