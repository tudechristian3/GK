package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 2/8/2018.
 */

public class RedeemedPromo implements Parcelable {

    @SerializedName("DateTimeRedeem")
    @Expose
    private String DateTimeRedeem;
    @SerializedName("PromoID")
    @Expose
    private String PromoID;
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("IMEI")
    @Expose
    private String IMEI;
    @SerializedName("XMLDetails")
    @Expose
    private String XMLDetails;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;
    @SerializedName("PromoName")
    @Expose
    private String PromoName;
    @SerializedName("PromoLogo")
    @Expose
    private String PromoLogo;

    protected RedeemedPromo(Parcel in) {
        DateTimeRedeem = in.readString();
        PromoID = in.readString();
        GuarantorID = in.readString();
        BorrowerID = in.readString();
        MobileNumber = in.readString();
        IMEI = in.readString();
        XMLDetails = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
        PromoName = in.readString();
        PromoLogo = in.readString();
    }

    public static final Creator<RedeemedPromo> CREATOR = new Creator<RedeemedPromo>() {
        @Override
        public RedeemedPromo createFromParcel(Parcel in) {
            return new RedeemedPromo(in);
        }

        @Override
        public RedeemedPromo[] newArray(int size) {
            return new RedeemedPromo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DateTimeRedeem);
        dest.writeString(PromoID);
        dest.writeString(GuarantorID);
        dest.writeString(BorrowerID);
        dest.writeString(MobileNumber);
        dest.writeString(IMEI);
        dest.writeString(XMLDetails);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
        dest.writeString(PromoName);
        dest.writeString(PromoLogo);
    }

    public RedeemedPromo(String dateTimeRedeem, String promoID, String guarantorID, String borrowerID, String mobileNumber, String IMEI, String XMLDetails, String status, String extra1, String extra2, String notes1, String notes2, String promoName, String promoLogo) {
        DateTimeRedeem = dateTimeRedeem;
        PromoID = promoID;
        GuarantorID = guarantorID;
        BorrowerID = borrowerID;
        MobileNumber = mobileNumber;
        this.IMEI = IMEI;
        this.XMLDetails = XMLDetails;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Notes1 = notes1;
        Notes2 = notes2;
        PromoName = promoName;
        PromoLogo = promoLogo;
    }

    public String getDateTimeRedeem() {
        return DateTimeRedeem;
    }

    public String getPromoID() {
        return PromoID;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getIMEI() {
        return IMEI;
    }

    public String getXMLDetails() {
        return XMLDetails;
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

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }

    public String getPromoName() {
        return PromoName;
    }

    public String getPromoLogo() {
        return PromoLogo;
    }
}
