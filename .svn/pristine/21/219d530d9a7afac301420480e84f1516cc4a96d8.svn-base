package com.goodkredit.myapplication.model.prepaidload.networkcodes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreloadedSun implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("Type")
    @Expose
    private String Type;
    @SerializedName("Keyword")
    @Expose
    private String Keyword;
    @SerializedName("Amount")
    @Expose
    private String Amount;
    @SerializedName("Description")
    @Expose
    private String Description;

    public PreloadedSun(int ID, String type, String keyword, String amount, String description) {
        this.ID = ID;
        Type = type;
        Keyword = keyword;
        Amount = amount;
        Description = description;
    }

    protected PreloadedSun(Parcel in) {
        ID = in.readInt();
        Type = in.readString();
        Keyword = in.readString();
        Amount = in.readString();
        Description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Type);
        dest.writeString(Keyword);
        dest.writeString(Amount);
        dest.writeString(Description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PreloadedSun> CREATOR = new Creator<PreloadedSun>() {
        @Override
        public PreloadedSun createFromParcel(Parcel in) {
            return new PreloadedSun(in);
        }

        @Override
        public PreloadedSun[] newArray(int size) {
            return new PreloadedSun[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getType() {
        return Type;
    }

    public String getKeyword() {
        return Keyword;
    }

    public String getAmount() {
        return Amount;
    }

    public String getDescription() {
        return Description;
    }
}
