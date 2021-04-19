package com.goodkredit.myapplication.model.coopassistant.member;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoopAssistantMemberCredits implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("CoopID")
    @Expose
    private String CoopID;
    @SerializedName("CoopName")
    @Expose
    private String CoopName;
    @SerializedName("MemberID")
    @Expose
    private String MemberID;
    @SerializedName("MemberMobileNo")
    @Expose
    private String MemberMobileNo;
    @SerializedName("CreditLimit")
    @Expose
    private double CreditLimit;
    @SerializedName("AvailableCredits")
    @Expose
    private double AvailableCredits;
    @SerializedName("UsedCredits")
    @Expose
    private double UsedCredits;
    @SerializedName("LastDateTimeUpdated")
    @Expose
    private String LastDateTimeUpdated;
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
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;

    public CoopAssistantMemberCredits(int ID, String dateTimeIN, String coopID, String coopName, String memberID, String memberMobileNo, double creditLimit, double availableCredits, double usedCredits, String lastDateTimeUpdated, String updatedBy, String status, String extra1, String extra2, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        CoopID = coopID;
        CoopName = coopName;
        MemberID = memberID;
        MemberMobileNo = memberMobileNo;
        CreditLimit = creditLimit;
        AvailableCredits = availableCredits;
        UsedCredits = usedCredits;
        LastDateTimeUpdated = lastDateTimeUpdated;
        UpdatedBy = updatedBy;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected CoopAssistantMemberCredits(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        CoopID = in.readString();
        CoopName = in.readString();
        MemberID = in.readString();
        MemberMobileNo = in.readString();
        CreditLimit = in.readDouble();
        AvailableCredits = in.readDouble();
        UsedCredits = in.readDouble();
        LastDateTimeUpdated = in.readString();
        UpdatedBy = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DateTimeIN);
        dest.writeString(CoopID);
        dest.writeString(CoopName);
        dest.writeString(MemberID);
        dest.writeString(MemberMobileNo);
        dest.writeDouble(CreditLimit);
        dest.writeDouble(AvailableCredits);
        dest.writeDouble(UsedCredits);
        dest.writeString(LastDateTimeUpdated);
        dest.writeString(UpdatedBy);
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

    public static final Creator<CoopAssistantMemberCredits> CREATOR = new Creator<CoopAssistantMemberCredits>() {
        @Override
        public CoopAssistantMemberCredits createFromParcel(Parcel in) {
            return new CoopAssistantMemberCredits(in);
        }

        @Override
        public CoopAssistantMemberCredits[] newArray(int size) {
            return new CoopAssistantMemberCredits[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
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

    public String getMemberMobileNo() {
        return MemberMobileNo;
    }

    public double getCreditLimit() {
        return CreditLimit;
    }

    public double getAvailableCredits() {
        return AvailableCredits;
    }

    public double getUsedCredits() {
        return UsedCredits;
    }

    public String getLastDateTimeUpdated() {
        return LastDateTimeUpdated;
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

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }
}
