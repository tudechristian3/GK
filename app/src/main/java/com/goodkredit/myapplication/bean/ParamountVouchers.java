package com.goodkredit.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User-PC on 3/23/2018.
 */

public class ParamountVouchers {

    @SerializedName("MerchantTransactionNo")
    @Expose
    private String MerchantTransactionNo;
    @SerializedName("TransactionNo")
    @Expose
    private String TransactionNo;
    @SerializedName("VoucherSeriesNo")
    @Expose
    private String VoucherSeriesNo;
    @SerializedName("VoucherCode")
    @Expose
    private String VoucherCode;
    @SerializedName("AmountConsumed")
    @Expose
    private double AmountConsumed;
    @SerializedName("ProductID")
    @Expose
    private String ProductID;
    @SerializedName("VoucherDenom")
    @Expose
    private double VoucherDenom;
    @SerializedName("Extra3")
    @Expose
    private String Extra3;

    public ParamountVouchers(String merchantTransactionNo, String transactionNo, String voucherSeriesNo, String voucherCode, double amountConsumed, String productID, double voucherDenom, String extra3) {
        MerchantTransactionNo = merchantTransactionNo;
        TransactionNo = transactionNo;
        VoucherSeriesNo = voucherSeriesNo;
        VoucherCode = voucherCode;
        AmountConsumed = amountConsumed;
        ProductID = productID;
        VoucherDenom = voucherDenom;
        Extra3 = extra3;
    }

    public String getMerchantTransactionNo() {
        return MerchantTransactionNo;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getVoucherSeriesNo() {
        return VoucherSeriesNo;
    }

    public String getVoucherCode() {
        return VoucherCode;
    }

    public double getAmountConsumed() {
        return AmountConsumed;
    }

    public String getProductID() {
        return ProductID;
    }

    public double getVoucherDenom() {
        return VoucherDenom;
    }

    public String getExtra3() {
        return Extra3;
    }
}
