package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 1/25/2018.
 */

public class PromoQRDetails implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("PromoID")
    @Expose
    private String PromoID;
    @SerializedName("PromoName")
    @Expose
    private String PromoName;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("VoucherProductID")
    @Expose
    private String VoucherProductID;
    @SerializedName("VoucherDenom")
    @Expose
    private String VoucherDenom;
    @SerializedName("VoucherQuantity")
    @Expose
    private int VoucherQuantity;
    @SerializedName("PromoStartDate")
    @Expose
    private String PromoStartDate;
    @SerializedName("PromoEndDate")
    @Expose
    private String PromoEndDate;
    @SerializedName("PromoImageURL")
    @Expose
    private String PromoImageURL;
    @SerializedName("TotalRedemption")
    @Expose
    private int TotalRedemption;
    @SerializedName("PromoCreatedDateTime")
    @Expose
    private String PromoCreatedDateTime;
    @SerializedName("PromoCreatedBy")
    @Expose
    private String PromoCreatedBy;
    @SerializedName("PromoApprovedDateTime")
    @Expose
    private String PromoApprovedDateTime;
    @SerializedName("PromoApprovedBy")
    @Expose
    private String PromoApprovedBy;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Extra3")
    @Expose
    private String Extra3;
    @SerializedName("Extra4")
    @Expose
    private String Extra4;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;


    protected PromoQRDetails(Parcel in) {
        ID = in.readInt();
        GuarantorID = in.readString();
        PromoID = in.readString();
        PromoName = in.readString();
        Description = in.readString();
        VoucherProductID = in.readString();
        VoucherDenom = in.readString();
        VoucherQuantity = in.readInt();
        PromoStartDate = in.readString();
        PromoEndDate = in.readString();
        PromoImageURL = in.readString();
        TotalRedemption = in.readInt();
        PromoCreatedDateTime = in.readString();
        PromoCreatedBy = in.readString();
        PromoApprovedDateTime = in.readString();
        PromoApprovedBy = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    public static final Creator<PromoQRDetails> CREATOR = new Creator<PromoQRDetails>() {
        @Override
        public PromoQRDetails createFromParcel(Parcel in) {
            return new PromoQRDetails(in);
        }

        @Override
        public PromoQRDetails[] newArray(int size) {
            return new PromoQRDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(GuarantorID);
        dest.writeString(PromoID);
        dest.writeString(PromoName);
        dest.writeString(Description);
        dest.writeString(VoucherProductID);
        dest.writeString(VoucherDenom);
        dest.writeInt(VoucherQuantity);
        dest.writeString(PromoStartDate);
        dest.writeString(PromoEndDate);
        dest.writeString(PromoImageURL);
        dest.writeInt(TotalRedemption);
        dest.writeString(PromoCreatedDateTime);
        dest.writeString(PromoCreatedBy);
        dest.writeString(PromoApprovedDateTime);
        dest.writeString(PromoApprovedBy);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    public int getID() {
        return ID;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }

    public String getPromoID() {
        return PromoID;
    }

    public String getPromoName() {
        return PromoName;
    }

    public String getDescription() {
        return Description;
    }

    public String getVoucherProductID() {
        return VoucherProductID;
    }

    public String getVoucherDenom() {
        return VoucherDenom;
    }

    public int getVoucherQuantity() {
        return VoucherQuantity;
    }

    public String getPromoStartDate() {
        return PromoStartDate;
    }

    public String getPromoEndDate() {
        return PromoEndDate;
    }

    public String getPromoImageURL() {
        return PromoImageURL;
    }

    public int getTotalRedemption() {
        return TotalRedemption;
    }

    public String getPromoCreatedDateTime() {
        return PromoCreatedDateTime;
    }

    public String getPromoCreatedBy() {
        return PromoCreatedBy;
    }

    public String getPromoApprovedDateTime() {
        return PromoApprovedDateTime;
    }

    public String getPromoApprovedBy() {
        return PromoApprovedBy;
    }

    public String getStatus() {
        return Status;
    }

    public String getExtra1() {
        return Extra1;
    }

    public String getExtra2() {
        return Extra2;
    }

    public String getExtra3() {
        return Extra3;
    }

    public String getExtra4() {
        return Extra4;
    }

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }
}
