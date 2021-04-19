package com.goodkredit.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User-PC on 7/31/2017.
 */

public class TransactionDetails {
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
    private double Amount;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("BillerTransactionNo")
    @Expose
    private String BillerTransactionNo;
    @SerializedName("GKTransactionNo")
    @Expose
    private String GKTransactionNo;
    @SerializedName("BillerName")
    @Expose
    private String BillerName;
    @SerializedName("AccountNo")
    @Expose
    private String AccountNo;
    @SerializedName("AccountName")
    @Expose
    private String AccountName;
    @SerializedName("AmountPaid")
    @Expose
    private double AmountPaid;
    @SerializedName("TotalAmountPaid")
    @Expose
    private double TotalAmountPaid;
    @SerializedName("Discount")
    @Expose
    private double Discount;
    @SerializedName("NetAmount")
    @Expose
    private double NetAmount;

    public TransactionDetails(String transactionNo, String mobileTarget, String productCode, double amount, String dateTimeCompleted, String billerTransactionNo, String GKTransactionNo, String billerName, String accountNo, String accountName, double amountPaid, double totalAmountPaid) {
        TransactionNo = transactionNo;
        MobileTarget = mobileTarget;
        ProductCode = productCode;
        Amount = amount;
        DateTimeCompleted = dateTimeCompleted;
        BillerTransactionNo = billerTransactionNo;
        this.GKTransactionNo = GKTransactionNo;
        BillerName = billerName;
        AccountNo = accountNo;
        AccountName = accountName;
        AmountPaid = amountPaid;
        TotalAmountPaid = totalAmountPaid;
    }

    public TransactionDetails(String transactionNo, String mobileTarget, String productCode, double amount, String dateTimeCompleted, String billerTransactionNo, String GKTransactionNo, String billerName, String accountNo, String accountName, double amountPaid, double totalAmountPaid, double discount, double netAmount) {
        TransactionNo = transactionNo;
        MobileTarget = mobileTarget;
        ProductCode = productCode;
        Amount = amount;
        DateTimeCompleted = dateTimeCompleted;
        BillerTransactionNo = billerTransactionNo;
        this.GKTransactionNo = GKTransactionNo;
        BillerName = billerName;
        AccountNo = accountNo;
        AccountName = accountName;
        AmountPaid = amountPaid;
        TotalAmountPaid = totalAmountPaid;
        Discount = discount;
        NetAmount = netAmount;
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

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public void setDateTimeCompleted(String dateTimeCompleted) {
        DateTimeCompleted = dateTimeCompleted;
    }

    public String getBillerTransactionNo() {
        return BillerTransactionNo;
    }

    public void setBillerTransactionNo(String billerTransactionNo) {
        BillerTransactionNo = billerTransactionNo;
    }

    public String getGKTransactionNo() {
        return GKTransactionNo;
    }

    public void setGKTransactionNo(String GKTransactionNo) {
        this.GKTransactionNo = GKTransactionNo;
    }

    public String getBillerName() {
        return BillerName;
    }

    public void setBillerName(String billerName) {
        BillerName = billerName;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public double getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        AmountPaid = amountPaid;
    }

    public double getTotalAmountPaid() {
        return TotalAmountPaid;
    }

    public void setTotalAmountPaid(double totalAmountPaid) {
        TotalAmountPaid = totalAmountPaid;
    }
}
