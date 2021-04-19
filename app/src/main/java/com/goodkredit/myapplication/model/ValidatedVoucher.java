package com.goodkredit.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidatedVoucher {
    @SerializedName("AmountPaid")
    @Expose
    private String AmountPaid;
    @SerializedName("PurchaseBalance")
    @Expose
    private String PurchaseBalance;
    @SerializedName("Change")
    @Expose
    private String Change;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("ProductID")
    @Expose
    private String ProductID;
    @SerializedName("VoucherDenom")
    @Expose
    private String VoucherDenom;
    @SerializedName("VoucherBalance")
    @Expose
    private String VoucherBalance;
    @SerializedName("VoucherSerial")
    @Expose
    private String VoucherSerial;
    @SerializedName("VoucherCode")
    @Expose
    private String VoucherCode;

    public String getAmountPaid() {
        return AmountPaid;
    }

    public String getPurchaseBalance() {
        return PurchaseBalance;
    }

    public String getChange() {
        return Change;
    }

    public String getStatus() {
        return Status;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getVoucherDenom() {
        return VoucherDenom;
    }

    public String getVoucherBalance() {
        return VoucherBalance;
    }

    public String getVoucherSerial() {
        return VoucherSerial;
    }

    public String getVoucherCode() {
        return VoucherCode;
    }
}
