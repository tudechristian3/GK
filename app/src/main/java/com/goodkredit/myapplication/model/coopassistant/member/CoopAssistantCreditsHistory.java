package com.goodkredit.myapplication.model.coopassistant.member;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoopAssistantCreditsHistory implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("Type")
    @Expose
    private String Type;
    @SerializedName("CoopID")
    @Expose
    private String CoopID;
    @SerializedName("CoopName")
    @Expose
    private String CoopName;
    @SerializedName("MemberID")
    @Expose
    private String MemberID;
    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("Amount")
    @Expose
    private double Amount;
    @SerializedName("PreAvailableCredit")
    @Expose
    private double PreAvailableCredit;
    @SerializedName("PostAvailableCredits")
    @Expose
    private double PostAvailableCredits;
    @SerializedName("Medium")
    @Expose
    private String Medium;
    @SerializedName("Remarks")
    @Expose
    private String Remarks;
    @SerializedName("UpdatedBy")
    @Expose
    private String UpdatedBy;
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

    public CoopAssistantCreditsHistory(int ID, String dateTimeIN, String type, String coopID, String coopName, String memberID, String mobileNumber, double amount, double preAvailableCredit, double postAvailableCredits, String medium, String remarks, String updatedBy, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        Type = type;
        CoopID = coopID;
        CoopName = coopName;
        MemberID = memberID;
        MobileNumber = mobileNumber;
        Amount = amount;
        PreAvailableCredit = preAvailableCredit;
        PostAvailableCredits = postAvailableCredits;
        Medium = medium;
        Remarks = remarks;
        UpdatedBy = updatedBy;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected CoopAssistantCreditsHistory(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        Type = in.readString();
        CoopID = in.readString();
        CoopName = in.readString();
        MemberID = in.readString();
        MobileNumber = in.readString();
        Amount = in.readDouble();
        PreAvailableCredit = in.readDouble();
        PostAvailableCredits = in.readDouble();
        Medium = in.readString();
        Remarks = in.readString();
        UpdatedBy = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeIN);
        dest.writeString(Type);
        dest.writeString(CoopID);
        dest.writeString(CoopName);
        dest.writeString(MemberID);
        dest.writeString(MobileNumber);
        dest.writeDouble(Amount);
        dest.writeDouble(PreAvailableCredit);
        dest.writeDouble(PostAvailableCredits);
        dest.writeString(Medium);
        dest.writeString(Remarks);
        dest.writeString(UpdatedBy);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CoopAssistantCreditsHistory> CREATOR = new Creator<CoopAssistantCreditsHistory>() {
        @Override
        public CoopAssistantCreditsHistory createFromParcel(Parcel in) {
            return new CoopAssistantCreditsHistory(in);
        }

        @Override
        public CoopAssistantCreditsHistory[] newArray(int size) {
            return new CoopAssistantCreditsHistory[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getType() {
        return Type;
    }

    public String getCoopID() {
        return CoopID;
    }

    public String getCoopName() {
        return CoopName;
    }

    public String getMemberID() {
        return MemberID;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public double getAmount() {
        return Amount;
    }

    public double getPreAvailableCredit() {
        return PreAvailableCredit;
    }

    public double getPostAvailableCredits() {
        return PostAvailableCredits;
    }

    public String getMedium() {
        return Medium;
    }

    public String getRemarks() {
        return Remarks;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
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
