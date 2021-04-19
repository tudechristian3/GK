package com.goodkredit.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ban_Lenovo on 12/28/2016.
 */

public class Transaction implements Serializable {

    private String transactionNumber;
    private String dateCompleted;
    private String totalVoucherAmount;
    private String numOfVoucher;
    private String outlet;
    private String amount;
    private String amountTransferred;
    private String sender;
    private String receiver;
    private String refcode;
    private String serial;
    private String voucherCode;
    private String prodID;
    private String transferType;
    private String MerchantBranchName;
    private String Extra3;
    private String mobileNumber;
    private String emaillAddress;
    private String merchantLogo;
    private String merchantID;
    private String status;

    //borrowing transactions
    public Transaction(String transactionNumber, String dateCompleted, String totalVoucherAmount, String numOfVoucher) {
        this.transactionNumber = transactionNumber;
        this.totalVoucherAmount = totalVoucherAmount;
        this.dateCompleted = dateCompleted;
        this.numOfVoucher = numOfVoucher;
    }

    //consumed transaction v2
    public Transaction(String refcode, String dateCompleted, String amount, String outlet, String transactionNumber, String MerchantBranchName, String Extra3, String merchantLogo, String merchantID,String Status) {
        this.transactionNumber = transactionNumber;
        this.amount = amount;
        this.dateCompleted = dateCompleted;
        this.refcode = refcode;
        this.outlet = outlet;
        this.MerchantBranchName = MerchantBranchName;
        this.Extra3 = Extra3;
        this.merchantLogo = merchantLogo;
        this.merchantID = merchantID;
        this.status = Status;
    }

    //consumed transaction
    public Transaction(String refcode, String dateCompleted, String amount, String outlet, String transactionNumber, String MerchantBranchName, String Extra3, String Status) {
        this.transactionNumber = transactionNumber;
        this.amount = amount;
        this.dateCompleted = dateCompleted;
        this.refcode = refcode;
        this.outlet = outlet;
        this.MerchantBranchName = MerchantBranchName;
        this.Extra3 = Extra3;
        this.status = Status;
    }

    //transferred transaction
    public Transaction(String amountTransferred, String dateCompleted, String sender, String receiver, String transactionNumber, String transferType, String voucherCode, String outlet, String Extra3, String mobile, String email, String Status) {
        this.dateCompleted = dateCompleted;
        this.amountTransferred = amountTransferred;
        this.sender = sender;
        this.receiver = receiver;
        this.transactionNumber = transactionNumber;
        this.transferType = transferType;
        this.voucherCode = voucherCode;
        this.Extra3 = Extra3;
        this.mobileNumber = mobile;
        this.emaillAddress = email;
        this.status = Status;
    }

    //consumed details
    public Transaction(String transactionNumber, String serial, String voucherCode, String amount, String prodID, String outlet, String dummy, String Extra3,String Status) {
        this.transactionNumber = transactionNumber;
        this.serial = serial;
        this.voucherCode = voucherCode;
        this.amount = amount;
        this.prodID = prodID;
        this.Extra3 = Extra3;
        this.status = Status;
    }

    public String getMerchatInitials() {
        String[] temp = getOutlet().split("\\s");
        String result = "";
        for (int x = 0; x < temp.length; x++) {
            if (temp[x] != null && !temp[x].equals("") && !temp[x].equals(" ")) {
                result += (temp[x].charAt(0));
            }
        }
        if (result.length() >= 2) {
            result = result.substring(0, 2);
        }
        return result;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getMerchantLogo() {
        return merchantLogo;
    }

    public void setMerchantLogo(String merchantLogo) {
        this.merchantLogo = merchantLogo;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmaillAddress() {
        return emaillAddress;
    }

    public String getExtra3() {
        return Extra3;
    }

    public void setExtra3(String extra3) {
        Extra3 = extra3;
    }

    public String getMerchantBranchName() {
        return MerchantBranchName;
    }

    public void setMerchantBranchName(String merchantBranchName) {
        MerchantBranchName = merchantBranchName;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getRefcode() {
        return refcode;
    }

    public void setRefcode(String refcode) {
        this.refcode = refcode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAmountTransferred() {
        return amountTransferred;
    }

    public void setAmountTransferred(String amountTransferred) {
        this.amountTransferred = amountTransferred;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getTotalVoucherAmount() {
        return totalVoucherAmount;
    }

    public void setTotalVoucherAmount(String totalVoucherAmount) {
        this.totalVoucherAmount = totalVoucherAmount;
    }

    public String getNumOfVoucher() {
        return numOfVoucher;
    }

    public void setNumOfVoucher(String numOfVoucher) {
        this.numOfVoucher = numOfVoucher;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
