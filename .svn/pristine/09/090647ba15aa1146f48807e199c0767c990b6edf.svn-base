package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User-PC on 10/24/2017.
 */

public class PrepaidRequest implements Parcelable {
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("ProductID")
    @Expose
    private String ProductID;
    @SerializedName("VoucherStatus")
    @Expose
    private String VoucherStatus;
    @SerializedName("TotalNumberVoucher")
    @Expose
    private int TotalNumberVoucher;
    @SerializedName("VoucherDenom")
    @Expose
    private int VoucherDenom;

    @SerializedName("OrderQuantity")
    @Expose
    private int OrderQuantity;

    @SerializedName("Fees")
    @Expose
    private String Fees;

    public PrepaidRequest(String guarantorID, String productID, String voucherStatus, int totalNumberVoucher, int voucherDenom, int orderQuantity, String fees) {
        GuarantorID = guarantorID;
        ProductID = productID;
        VoucherStatus = voucherStatus;
        TotalNumberVoucher = totalNumberVoucher;
        VoucherDenom = voucherDenom;
        OrderQuantity = orderQuantity;
        Fees = fees;
    }

    protected PrepaidRequest(Parcel in) {
        GuarantorID = in.readString();
        ProductID = in.readString();
        VoucherStatus = in.readString();
        TotalNumberVoucher = in.readInt();
        VoucherDenom = in.readInt();
        OrderQuantity = in.readInt();
        Fees = in.readString();
    }

    public static final Creator<PrepaidRequest> CREATOR = new Creator<PrepaidRequest>() {
        @Override
        public PrepaidRequest createFromParcel(Parcel in) {
            return new PrepaidRequest(in);
        }

        @Override
        public PrepaidRequest[] newArray(int size) {
            return new PrepaidRequest[size];
        }
    };

    public String getGuarantorID() {
        return GuarantorID;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getVoucherStatus() {
        return VoucherStatus;
    }

    public int getTotalNumberVoucher() {
        return TotalNumberVoucher;
    }

    public int getVoucherDenom() {
        return VoucherDenom;
    }

    public int getOrderQuantity() {
        return OrderQuantity;
    }

    public void setGuarantorID(String guarantorID) {
        GuarantorID = guarantorID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public void setVoucherStatus(String voucherStatus) {
        VoucherStatus = voucherStatus;
    }

    public void setTotalNumberVoucher(int totalNumberVoucher) {
        TotalNumberVoucher = totalNumberVoucher;
    }

    public void setVoucherDenom(int voucherDenom) {
        VoucherDenom = voucherDenom;
    }

    public void setOrderQuantity(int orderQuantity) {
        OrderQuantity = orderQuantity;
    }

    public String getFees() {
        return Fees;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(GuarantorID);
        dest.writeString(ProductID);
        dest.writeString(VoucherStatus);
        dest.writeInt(TotalNumberVoucher);
        dest.writeInt(VoucherDenom);
        dest.writeInt(OrderQuantity);
        dest.writeString(Fees);
    }
}
