package com.goodkredit.myapplication.model.transfervoucher;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferValidateVoucher implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("BorrowerName")
    @Expose
    private String BorrowerName;
    @SerializedName("MobileNo")
    @Expose
    private String MobileNo;
    @SerializedName("StreetAddress")
    @Expose
    private String StreetAddress;
    @SerializedName("City")
    @Expose
    private String City;

    public TransferValidateVoucher(int ID, String borrowerID, String borrowerName, String mobileNo, String streetAddress, String city) {
        this.ID = ID;
        BorrowerID = borrowerID;
        BorrowerName = borrowerName;
        MobileNo = mobileNo;
        StreetAddress = streetAddress;
        City = city;
    }

    protected TransferValidateVoucher(Parcel in) {
        ID = in.readInt();
        BorrowerID = in.readString();
        BorrowerName = in.readString();
        MobileNo = in.readString();
        StreetAddress = in.readString();
        City = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(BorrowerID);
        dest.writeString(BorrowerName);
        dest.writeString(MobileNo);
        dest.writeString(StreetAddress);
        dest.writeString(City);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TransferValidateVoucher> CREATOR = new Creator<TransferValidateVoucher>() {
        @Override
        public TransferValidateVoucher createFromParcel(Parcel in) {
            return new TransferValidateVoucher(in);
        }

        @Override
        public TransferValidateVoucher[] newArray(int size) {
            return new TransferValidateVoucher[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getBorrowerName() {
        return BorrowerName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public String getCity() {
        return City;
    }
}
