package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenericBulletin implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("Title")
    @Expose
    private String Title;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("ImageURL")
    @Expose
    private String ImageURL;
    @SerializedName("PeriodStart")
    @Expose
    private String PeriodStart;
    @SerializedName("PeriodEnd")
    @Expose
    private String PeriodEnd;
    @SerializedName("isPrivate")
    @Expose
    private int isPrivate;
    @SerializedName("Addedby")
    @Expose
    private String Addedby;
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

    public GenericBulletin(int ID, String dateTimeIN, String title, String description, String imageURL, String periodStart, String periodEnd,
                           int isPrivate, String addedby, String status, String extra1, String extra2, String extra3, String extra4,
                           String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        Title = title;
        Description = description;
        ImageURL = imageURL;
        PeriodStart = periodStart;
        PeriodEnd = periodEnd;
        this.isPrivate = isPrivate;
        Addedby = addedby;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    public GenericBulletin(int ID, String dateTimeIN, String title, String description, String imageURL, String periodStart, String periodEnd, String addedby, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        DateTimeIN = dateTimeIN;
        Title = title;
        Description = description;
        ImageURL = imageURL;
        PeriodStart = periodStart;
        PeriodEnd = periodEnd;
        Addedby = addedby;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected GenericBulletin(Parcel in) {
        ID = in.readInt();
        DateTimeIN = in.readString();
        Title = in.readString();
        Description = in.readString();
        ImageURL = in.readString();
        PeriodStart = in.readString();
        PeriodEnd = in.readString();
        isPrivate = in.readInt();
        Addedby = in.readString();
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
        dest.writeString(Title);
        dest.writeString(Description);
        dest.writeString(ImageURL);
        dest.writeString(PeriodStart);
        dest.writeString(PeriodEnd);
        dest.writeInt(isPrivate);
        dest.writeString(Addedby);
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

    public static final Creator<GenericBulletin> CREATOR = new Creator<GenericBulletin>() {
        @Override
        public GenericBulletin createFromParcel(Parcel in) {
            return new GenericBulletin(in);
        }

        @Override
        public GenericBulletin[] newArray(int size) {
            return new GenericBulletin[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public String getPeriodStart() {
        return PeriodStart;
    }

    public String getPeriodEnd() {
        return PeriodEnd;
    }

    public int getIsPrivate() {
        return isPrivate;
    }

    public String getAddedby() {
        return Addedby;
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
