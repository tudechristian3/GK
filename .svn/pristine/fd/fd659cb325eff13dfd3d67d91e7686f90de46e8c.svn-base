package com.goodkredit.myapplication.model.prepaidload;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreloadedPrefix implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("Prefix")
    @Expose
    private String Prefix;
    @SerializedName("Brand")
    @Expose
    private String Brand;

    public PreloadedPrefix(int ID, String prefix, String brand) {
        this.ID = ID;
        Prefix = prefix;
        Brand = brand;
    }

    protected PreloadedPrefix(Parcel in) {
        ID = in.readInt();
        Prefix = in.readString();
        Brand = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Prefix);
        dest.writeString(Brand);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PreloadedPrefix> CREATOR = new Creator<PreloadedPrefix>() {
        @Override
        public PreloadedPrefix createFromParcel(Parcel in) {
            return new PreloadedPrefix(in);
        }

        @Override
        public PreloadedPrefix[] newArray(int size) {
            return new PreloadedPrefix[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getPrefix() {
        return Prefix;
    }

    public String getBrand() {
        return Brand;
    }
}
