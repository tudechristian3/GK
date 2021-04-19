package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class UNORedemption implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("TransactionNo")
    @Expose
    private String TransactionNo;
    @SerializedName("MemberAccountNo")
    @Expose
    private String MemberAccountNo;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("MobileNo")
    @Expose
    private String MobileNo;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("MiddleName")
    @Expose
    private String MiddleName;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("PointsRedeemed")
    @Expose
    private String PointsRedeemed;
    @SerializedName("ConversionPerPoint")
    @Expose
    private String ConversionPerPoint;
    @SerializedName("ConversionAmount")
    @Expose
    private double ConversionAmount;
    @SerializedName("TotalNoOfVoucher")
    @Expose
    private String TotalNoOfVoucher;
    @SerializedName("XMLDetails")
    @Expose
    private String voucherDetails;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("VoucherProcessID")
    @Expose
    private String VoucherProcessID;

    protected UNORedemption(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        DateTimeCompleted = in.readString();
        TransactionNo = in.readString();
        MemberAccountNo = in.readString();
        BorrowerID = in.readString();
        MobileNo = in.readString();
        LastName = in.readString();
        FirstName = in.readString();
        MiddleName = in.readString();
        Gender = in.readString();
        PointsRedeemed = in.readString();
        ConversionPerPoint = in.readString();
        ConversionAmount = in.readDouble();
        TotalNoOfVoucher = in.readString();
        voucherDetails = in.readString();
        Status = in.readString();
        VoucherProcessID = in.readString();
    }

    public static final Creator<UNORedemption> CREATOR = new Creator<UNORedemption>() {
        @Override
        public UNORedemption createFromParcel(Parcel in) {
            return new UNORedemption(in);
        }

        @Override
        public UNORedemption[] newArray(int size) {
            return new UNORedemption[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getMemberAccountNo() {
        return MemberAccountNo;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getLastName() {
        return LastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public String getGender() {
        return Gender;
    }

    public String getPointsRedeemed() {
        return PointsRedeemed;
    }

    public String getConversionPerPoint() {
        return ConversionPerPoint;
    }

    public double getConversionAmount() {
        return ConversionAmount;
    }

    public String getTotalNoOfVoucher() {
        return TotalNoOfVoucher;
    }

    public List<UNORedemptionVoucherDetails> getUNORedemptionVoucherDetails() {
        List<UNORedemptionVoucherDetails> list = new ArrayList<>();
        list = new Gson().fromJson(voucherDetails, new TypeToken<ArrayList<UNORedemptionVoucherDetails>>() {
        }.getType());
        return list;
    }

    public String getStatus() {
        return Status;
    }

    public String getVoucherProcessID() {
        return VoucherProcessID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeIN);
        dest.writeString(DateTimeCompleted);
        dest.writeString(TransactionNo);
        dest.writeString(MemberAccountNo);
        dest.writeString(BorrowerID);
        dest.writeString(MobileNo);
        dest.writeString(LastName);
        dest.writeString(FirstName);
        dest.writeString(MiddleName);
        dest.writeString(Gender);
        dest.writeString(PointsRedeemed);
        dest.writeString(ConversionPerPoint);
        dest.writeDouble(ConversionAmount);
        dest.writeString(TotalNoOfVoucher);
        dest.writeString(voucherDetails);
        dest.writeString(Status);
        dest.writeString(VoucherProcessID);
    }
}
