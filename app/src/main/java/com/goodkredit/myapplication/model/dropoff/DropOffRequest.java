package com.goodkredit.myapplication.model.dropoff;

public class DropOffRequest {

    private String merchant;
    private String totalamount;
    private String purpose;
    private String txnno;

    public DropOffRequest(String merchant, String totalamount, String purpose, String txnno) {
        this.merchant = merchant;
        this.totalamount = totalamount;
        this.purpose = purpose;
        this.txnno = txnno;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getTxnno() {
        return txnno;
    }
}
