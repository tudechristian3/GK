package com.goodkredit.myapplication.model.mdp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MDPBulletin implements Parcelable {

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

    public MDPBulletin(int ID, String dateTimeIN, String title, String description, String imageURL,
                       String periodStart, String periodEnd, String addedby, String status, String extra1,
                       String extra2, String extra3, String extra4, String notes1, String notes2) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.DateTimeIN);
        dest.writeString(this.Title);
        dest.writeString(this.Description);
        dest.writeString(this.ImageURL);
        dest.writeString(this.PeriodStart);
        dest.writeString(this.PeriodEnd);
        dest.writeString(this.Addedby);
        dest.writeString(this.Status);
        dest.writeString(this.Extra1);
        dest.writeString(this.Extra2);
        dest.writeString(this.Extra3);
        dest.writeString(this.Extra4);
        dest.writeString(this.Notes1);
        dest.writeString(this.Notes2);
    }

    protected MDPBulletin(Parcel in) {
        this.ID = in.readInt();
        this.DateTimeIN = in.readString();
        this.Title = in.readString();
        this.Description = in.readString();
        this.ImageURL = in.readString();
        this.PeriodStart = in.readString();
        this.PeriodEnd = in.readString();
        this.Addedby = in.readString();
        this.Status = in.readString();
        this.Extra1 = in.readString();
        this.Extra2 = in.readString();
        this.Extra3 = in.readString();
        this.Extra4 = in.readString();
        this.Notes1 = in.readString();
        this.Notes2 = in.readString();
    }

    public static final Parcelable.Creator<MDPBulletin> CREATOR = new Parcelable.Creator<MDPBulletin>() {
        @Override
        public MDPBulletin createFromParcel(Parcel source) {
            return new MDPBulletin(source);
        }

        @Override
        public MDPBulletin[] newArray(int size) {
            return new MDPBulletin[size];
        }
    };
}
