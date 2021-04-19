package com.goodkredit.myapplication.adapter;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by user on 2/2/2017.
 */

public class VoucherSummaryItem implements Serializable {
    private String voucherimage;
    private String totalnumbervoucher;
    private String totalbalance;
    private String totaldenoms;


    public VoucherSummaryItem() {
        super();
    }

    public VoucherSummaryItem(String voucherimage, String totalnumbervoucher, String totalbalance, String totaldenoms) {
        this.voucherimage = voucherimage;
        this.totalnumbervoucher = totalnumbervoucher;
        this.totalbalance = totalbalance;
        this.totaldenoms = totaldenoms;

    }

    public String getVoucherImage() {
        return voucherimage;
    }

    public void setVoucherImage(String voucherimage) {
        this.voucherimage = voucherimage;
    }

    public String getTotalVoucherNumber() {
        return totalnumbervoucher;
    }

    public void setTotalVoucherNumber(String totalnumbervoucher) {
        this.totalnumbervoucher = totalnumbervoucher;
    }

    public String getTotalBalance() {
        return totalbalance;
    }

    public void setTotalBalance(String totalbalance) {
        this.totalbalance = totalbalance;
    }

    public String getTotalDenoms() {
        return totaldenoms;
    }

    public void setTotalDenoms(String totaldenom) {
        this.totaldenoms = totaldenom;
    }

}
