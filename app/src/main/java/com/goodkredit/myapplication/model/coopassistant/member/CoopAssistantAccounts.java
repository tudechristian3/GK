package com.goodkredit.myapplication.model.coopassistant.member;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoopAssistantAccounts implements Parcelable {
    public static final String KEY_COOPACCOUNTS = "CoopAccounts";

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("CoopID")
    @Expose
    private String CoopID;
    @SerializedName("MemberID")
    @Expose
    private String MemberID;
    @SerializedName("CurrencyID")
    @Expose
    private String CurrencyID;
    @SerializedName("TotalAmount")
    @Expose
    private double TotalAmount;
    @SerializedName("AccountID")
    @Expose
    private String AccountID;
    @SerializedName("AccountName")
    @Expose
    private String AccountName;
    @SerializedName("LastPayDateTime")
    @Expose
    private String LastPayDateTime;
    @SerializedName("LastPayAmount")
    @Expose
    private double LastPayAmount;
    @SerializedName("LastPayPreWallet")
    @Expose
    private double LastPayPreWallet;
    @SerializedName("LastPayPostWallet")
    @Expose
    private double LastPayPostWallet;
    @SerializedName("LastDrawDateTime")
    @Expose
    private String LastDrawDateTime;
    @SerializedName("LastDrawAmount")
    @Expose
    private double LastDrawAmount;
    @SerializedName("LastDrawPreWallet")
    @Expose
    private double LastDrawPreWallet;
    @SerializedName("LastDrawPostWallet")
    @Expose
    private double LastDrawPostWallet;
    @SerializedName("LastUpdatedDateTime")
    @Expose
    private String LastUpdatedDateTime;
    @SerializedName("OtherDetails")
    @Expose
    private String OtherDetails;
    @SerializedName("MinAmount")
    @Expose
    private double MinAmount;
    @SerializedName("MaxAmount")
    @Expose
    private double MaxAmount;
    @SerializedName("IncrementalAmount")
    @Expose
    private double IncrementalAmount;
    @SerializedName("isAllowedTopup")
    @Expose
    private int isAllowedTopup;
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
    
    public CoopAssistantAccounts(int ID, String coopID, String memberID, String currencyID, double totalAmount, String accountID, String accountName, String lastPayDateTime, double lastPayAmount, double lastPayPreWallet, double lastPayPostWallet, String lastDrawDateTime, double lastDrawAmount, double lastDrawPreWallet, double lastDrawPostWallet, String lastUpdatedDateTime, String otherDetails, double minAmount, double maxAmount, double incrementalAmount, int isAllowedTopup, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        CoopID = coopID;
        MemberID = memberID;
        CurrencyID = currencyID;
        TotalAmount = totalAmount;
        AccountID = accountID;
        AccountName = accountName;
        LastPayDateTime = lastPayDateTime;
        LastPayAmount = lastPayAmount;
        LastPayPreWallet = lastPayPreWallet;
        LastPayPostWallet = lastPayPostWallet;
        LastDrawDateTime = lastDrawDateTime;
        LastDrawAmount = lastDrawAmount;
        LastDrawPreWallet = lastDrawPreWallet;
        LastDrawPostWallet = lastDrawPostWallet;
        LastUpdatedDateTime = lastUpdatedDateTime;
        OtherDetails = otherDetails;
        MinAmount = minAmount;
        MaxAmount = maxAmount;
        IncrementalAmount = incrementalAmount;
        this.isAllowedTopup = isAllowedTopup;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected CoopAssistantAccounts(Parcel in) {
        ID = in.readInt();
        CoopID = in.readString();
        MemberID = in.readString();
        CurrencyID = in.readString();
        TotalAmount = in.readDouble();
        AccountID = in.readString();
        AccountName = in.readString();
        LastPayDateTime = in.readString();
        LastPayAmount = in.readDouble();
        LastPayPreWallet = in.readDouble();
        LastPayPostWallet = in.readDouble();
        LastDrawDateTime = in.readString();
        LastDrawAmount = in.readDouble();
        LastDrawPreWallet = in.readDouble();
        LastDrawPostWallet = in.readDouble();
        LastUpdatedDateTime = in.readString();
        OtherDetails = in.readString();
        MinAmount = in.readDouble();
        MaxAmount = in.readDouble();
        IncrementalAmount = in.readDouble();
        isAllowedTopup = in.readInt();
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
        dest.writeString(CoopID);
        dest.writeString(MemberID);
        dest.writeString(CurrencyID);
        dest.writeDouble(TotalAmount);
        dest.writeString(AccountID);
        dest.writeString(AccountName);
        dest.writeString(LastPayDateTime);
        dest.writeDouble(LastPayAmount);
        dest.writeDouble(LastPayPreWallet);
        dest.writeDouble(LastPayPostWallet);
        dest.writeString(LastDrawDateTime);
        dest.writeDouble(LastDrawAmount);
        dest.writeDouble(LastDrawPreWallet);
        dest.writeDouble(LastDrawPostWallet);
        dest.writeString(LastUpdatedDateTime);
        dest.writeString(OtherDetails);
        dest.writeDouble(MinAmount);
        dest.writeDouble(MaxAmount);
        dest.writeDouble(IncrementalAmount);
        dest.writeInt(isAllowedTopup);
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

    public static final Creator<CoopAssistantAccounts> CREATOR = new Creator<CoopAssistantAccounts>() {
        @Override
        public CoopAssistantAccounts createFromParcel(Parcel in) {
            return new CoopAssistantAccounts(in);
        }

        @Override
        public CoopAssistantAccounts[] newArray(int size) {
            return new CoopAssistantAccounts[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getCoopID() {
        return CoopID;
    }

    public String getMemberID() {
        return MemberID;
    }

    public String getCurrencyID() {
        return CurrencyID;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public String getAccountID() {
        return AccountID;
    }

    public String getAccountName() {
        return AccountName;
    }

    public String getLastPayDateTime() {
        return LastPayDateTime;
    }

    public double getLastPayAmount() {
        return LastPayAmount;
    }

    public double getLastPayPreWallet() {
        return LastPayPreWallet;
    }

    public double getLastPayPostWallet() {
        return LastPayPostWallet;
    }

    public String getLastDrawDateTime() {
        return LastDrawDateTime;
    }

    public double getLastDrawAmount() {
        return LastDrawAmount;
    }

    public double getLastDrawPreWallet() {
        return LastDrawPreWallet;
    }

    public double getLastDrawPostWallet() {
        return LastDrawPostWallet;
    }

    public String getLastUpdatedDateTime() {
        return LastUpdatedDateTime;
    }

    public String getOtherDetails() {
        return OtherDetails;
    }

    public double getMinAmount() {
        return MinAmount;
    }

    public double getMaxAmount() {
        return MaxAmount;
    }

    public double getIncrementalAmount() {
        return IncrementalAmount;
    }

    public int getIsAllowedTopup() {
        return isAllowedTopup;
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

