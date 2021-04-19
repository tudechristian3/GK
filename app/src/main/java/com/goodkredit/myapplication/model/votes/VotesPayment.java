package com.goodkredit.myapplication.model.votes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VotesPayment implements Parcelable {
    @SerializedName("VoucherSessionID")
    @Expose
    private String VoucherSessionID;
    @SerializedName("MerchantReferenceNo")
    @Expose
    private String MerchantReferenceNo;
    @SerializedName("PaymentTxnID")
    @Expose
    private String PaymentTxnID;
    @SerializedName("Amount")
    @Expose
    private double Amount;

    public VotesPayment(String voucherSessionID, String merchantReferenceNo, String paymentTxnID, double amount) {
        VoucherSessionID = voucherSessionID;
        MerchantReferenceNo = merchantReferenceNo;
        PaymentTxnID = paymentTxnID;
        Amount = amount;
    }


    protected VotesPayment(Parcel in) {
        VoucherSessionID = in.readString();
        MerchantReferenceNo = in.readString();
        PaymentTxnID = in.readString();
        Amount = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(VoucherSessionID);
        dest.writeString(MerchantReferenceNo);
        dest.writeString(PaymentTxnID);
        dest.writeDouble(Amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VotesPayment> CREATOR = new Creator<VotesPayment>() {
        @Override
        public VotesPayment createFromParcel(Parcel in) {
            return new VotesPayment(in);
        }

        @Override
        public VotesPayment[] newArray(int size) {
            return new VotesPayment[size];
        }
    };

    public String getVoucherSessionID() {
        return VoucherSessionID;
    }

    public String getMerchantReferenceNo() {
        return MerchantReferenceNo;
    }

    public String getPaymentTxnID() {
        return PaymentTxnID;
    }

    public double getAmount() {
        return Amount;
    }
}
