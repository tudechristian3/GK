package com.goodkredit.myapplication.model.coopassistant;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoopAssistantLoanType implements Parcelable {
    public static final String KEY_COOPLOANTYPE = "CoopLoanType";

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("CoopID")
    @Expose
    private String CoopID;
    @SerializedName("LoanID")
    @Expose
    private String LoanID;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("OtherDetails")
    @Expose
    private String OtherDetails;
    @SerializedName("KYCOtherInfo")
    @Expose
    private String KYCOtherInfo;
    @SerializedName("Amount")
    @Expose
    private double Amount;
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


    public CoopAssistantLoanType(int ID, String coopID, String loanID, String name, String description, String otherDetails,
                                 String KYCOtherInfo, double amount, String status, String extra1, String extra2, String notes1,
                                 String notes2) {
        this.ID = ID;
        CoopID = coopID;
        LoanID = loanID;
        Name = name;
        Description = description;
        OtherDetails = otherDetails;
        this.KYCOtherInfo = KYCOtherInfo;
        Amount = amount;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected CoopAssistantLoanType(Parcel in) {
        ID = in.readInt();
        CoopID = in.readString();
        LoanID = in.readString();
        Name = in.readString();
        Description = in.readString();
        OtherDetails = in.readString();
        KYCOtherInfo = in.readString();
        Amount = in.readDouble();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(CoopID);
        dest.writeString(LoanID);
        dest.writeString(Name);
        dest.writeString(Description);
        dest.writeString(OtherDetails);
        dest.writeString(KYCOtherInfo);
        dest.writeDouble(Amount);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CoopAssistantLoanType> CREATOR = new Creator<CoopAssistantLoanType>() {
        @Override
        public CoopAssistantLoanType createFromParcel(Parcel in) {
            return new CoopAssistantLoanType(in);
        }

        @Override
        public CoopAssistantLoanType[] newArray(int size) {
            return new CoopAssistantLoanType[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getCoopID() {
        return CoopID;
    }

    public String getLoanID() {
        return LoanID;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getOtherDetails() {
        return OtherDetails;
    }

    public String getKYCOtherInfo() {
        return KYCOtherInfo;
    }

    public double getAmount() {
        return Amount;
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
}
