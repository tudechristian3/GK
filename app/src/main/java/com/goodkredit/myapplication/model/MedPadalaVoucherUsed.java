package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban on 16/03/2018.
 */

public class MedPadalaVoucherUsed implements Parcelable {
    @SerializedName("TransactionNo")
    @Expose
    private String TransactionNo;
    @SerializedName("MerchantTransactionNo")
    @Expose
    private String MerchantTransactionNo;
    @SerializedName("ProductID")
    @Expose
    private String ProductID;
    @SerializedName("VoucherSeriesNo")
    @Expose
    private String VoucherSeriesNo;
    @SerializedName("VoucherCode")
    @Expose
    private String VoucherCode;
    @SerializedName("VoucherPIN")
    @Expose
    private String VoucherPIN;
    @SerializedName("VoucherDenom")
    @Expose
    private String VoucherDenom;
    @SerializedName("AmountConsumed")
    @Expose
    private double AmountConsumed;
    @SerializedName("VoucherType")
    @Expose
    private String VoucherType;

    protected MedPadalaVoucherUsed(Parcel in) {
        TransactionNo = in.readString();
        MerchantTransactionNo = in.readString();
        ProductID = in.readString();
        VoucherSeriesNo = in.readString();
        VoucherCode = in.readString();
        VoucherPIN = in.readString();
        VoucherDenom = in.readString();
        AmountConsumed = in.readDouble();
        VoucherType = in.readString();
    }

    public static final Creator<MedPadalaVoucherUsed> CREATOR = new Creator<MedPadalaVoucherUsed>() {
        @Override
        public MedPadalaVoucherUsed createFromParcel(Parcel in) {
            return new MedPadalaVoucherUsed(in);
        }

        @Override
        public MedPadalaVoucherUsed[] newArray(int size) {
            return new MedPadalaVoucherUsed[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TransactionNo);
        dest.writeString(MerchantTransactionNo);
        dest.writeString(ProductID);
        dest.writeString(VoucherSeriesNo);
        dest.writeString(VoucherCode);
        dest.writeString(VoucherPIN);
        dest.writeString(VoucherDenom);
        dest.writeDouble(AmountConsumed);
        dest.writeString(VoucherType);
    }

    public MedPadalaVoucherUsed(String transactionNo, String merchantTransactionNo, String productID, String voucherSeriesNo, String voucherCode, String voucherPIN, String voucherDenom, double amountConsumed, String voucherType) {
        TransactionNo = transactionNo;
        MerchantTransactionNo = merchantTransactionNo;
        ProductID = productID;
        VoucherSeriesNo = voucherSeriesNo;
        VoucherCode = voucherCode;
        VoucherPIN = voucherPIN;
        VoucherDenom = voucherDenom;
        AmountConsumed = amountConsumed;
        VoucherType = voucherType;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getMerchantTransactionNo() {
        return MerchantTransactionNo;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getVoucherSeriesNo() {
        return VoucherSeriesNo;
    }

    public String getVoucherCode() {
        return VoucherCode;
    }

    public String getVoucherPIN() {
        return VoucherPIN;
    }

    public String getVoucherDenom() {
        return VoucherDenom;
    }

    public double getAmountConsumed() {
        return AmountConsumed;
    }

    public String getVoucherType() {
        return VoucherType;
    }
}
