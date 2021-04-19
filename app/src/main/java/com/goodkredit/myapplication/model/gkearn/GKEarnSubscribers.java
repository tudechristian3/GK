package com.goodkredit.myapplication.model.gkearn;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKEarnSubscribers implements Parcelable {
    public static final String KEY_GKEARNSUBCRIBERS = "gkearnsubcribers";

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("BorrowerName")
    @Expose
    private String BorrowerName;
    @SerializedName("UplineBorrowerID")
    @Expose
    private String UplineBorrowerID;
    @SerializedName("UplineBorrowerName")
    @Expose
    private String UplineBorrowerName;
    @SerializedName("TotalDownLines")
    @Expose
    private int TotalDownLines;
    @SerializedName("TotalPoints")
    @Expose
    private double TotalPoints;
    @SerializedName("LastConversionDateTime")
    @Expose
    private String LastConversionDateTime;
    @SerializedName("LastEarnDateTime")
    @Expose
    private String LastEarnDateTime;
    @SerializedName("isStockist")
    @Expose
    private int isStockist;
    @SerializedName("StockistPackage")
    @Expose
    private String StockistPackage;
    @SerializedName("StockistRegisteredDateTime")
    @Expose
    private String StockistRegisteredDateTime;
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

    public GKEarnSubscribers(int ID, String dateTimeIN, String borrowerID, String borrowerName, String uplineBorrowerID, String uplineBorrowerName, int totalDownLines, double totalPoints, String lastConversionDateTime, String lastEarnDateTime, int isStockist, String stockistPackage, String stockistRegisteredDateTime, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        BorrowerID = borrowerID;
        BorrowerName = borrowerName;
        UplineBorrowerID = uplineBorrowerID;
        UplineBorrowerName = uplineBorrowerName;
        TotalDownLines = totalDownLines;
        TotalPoints = totalPoints;
        LastConversionDateTime = lastConversionDateTime;
        LastEarnDateTime = lastEarnDateTime;
        this.isStockist = isStockist;
        StockistPackage = stockistPackage;
        StockistRegisteredDateTime = stockistRegisteredDateTime;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected GKEarnSubscribers(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        BorrowerID = in.readString();
        BorrowerName = in.readString();
        UplineBorrowerID = in.readString();
        UplineBorrowerName = in.readString();
        TotalDownLines = in.readInt();
        TotalPoints = in.readDouble();
        LastConversionDateTime = in.readString();
        LastEarnDateTime = in.readString();
        isStockist = in.readInt();
        StockistPackage = in.readString();
        StockistRegisteredDateTime = in.readString();
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
        dest.writeString(BorrowerID);
        dest.writeString(BorrowerName);
        dest.writeString(UplineBorrowerID);
        dest.writeString(UplineBorrowerName);
        dest.writeInt(TotalDownLines);
        dest.writeDouble(TotalPoints);
        dest.writeString(LastConversionDateTime);
        dest.writeString(LastEarnDateTime);
        dest.writeInt(isStockist);
        dest.writeString(StockistPackage);
        dest.writeString(StockistRegisteredDateTime);
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

    public static final Creator<GKEarnSubscribers> CREATOR = new Creator<GKEarnSubscribers>() {
        @Override
        public GKEarnSubscribers createFromParcel(Parcel in) {
            return new GKEarnSubscribers(in);
        }

        @Override
        public GKEarnSubscribers[] newArray(int size) {
            return new GKEarnSubscribers[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getBorrowerName() {
        return BorrowerName;
    }

    public String getUplineBorrowerID() {
        return UplineBorrowerID;
    }

    public String getUplineBorrowerName() {
        return UplineBorrowerName;
    }

    public int getTotalDownLines() {
        return TotalDownLines;
    }

    public double getTotalPoints() {
        return TotalPoints;
    }

    public String getLastConversionDateTime() {
        return LastConversionDateTime;
    }

    public String getLastEarnDateTime() {
        return LastEarnDateTime;
    }

    public int getIsStockist() {
        return isStockist;
    }

    public String getStockistPackage() {
        return StockistPackage;
    }

    public String getStockistRegisteredDateTime() {
        return StockistRegisteredDateTime;
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




