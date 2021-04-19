package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.bean.VoucherRestriction;
import com.goodkredit.myapplication.bean.VoucherSummary;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 4/25/2017.
 */

public class GetVoucherResponse {

    @SerializedName("Voucher")
    @Expose
    private ArrayList<Voucher> vouchers = new ArrayList<>();
    @SerializedName("VoucherSummary")
    @Expose
    private ArrayList<VoucherSummary> vouchersSummary = new ArrayList<>();
    @SerializedName("VoucherRestriction")
    @Expose
    private ArrayList<VoucherRestriction> vouchersRestrictions = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("RegistrationStatus")
    @Expose
    private String registrationstatus;
    @SerializedName("TotalNotification")
    @Expose
    private int TotalNotification;

    public int getTotalNotification() {
        return TotalNotification;
    }

    public ArrayList<Voucher> getVouchers() {
        return vouchers;
    }

    public ArrayList<VoucherSummary> getVouchersSummary() {
        return vouchersSummary;
    }

    public ArrayList<VoucherRestriction> getVouchersRestrictions() {
        return vouchersRestrictions;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getRegistrationstatus() {
        return registrationstatus;
    }

}
