package com.goodkredit.myapplication.model.synccontacts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyncContacts implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;

    public SyncContacts(int ID, String name, String mobileNumber) {
        this.ID = ID;
        Name = name;
        MobileNumber = mobileNumber;
    }

    protected SyncContacts(Parcel in) {
        ID = in.readInt();
        Name = in.readString();
        MobileNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Name);
        dest.writeString(MobileNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SyncContacts> CREATOR = new Creator<SyncContacts>() {
        @Override
        public SyncContacts createFromParcel(Parcel in) {
            return new SyncContacts(in);
        }

        @Override
        public SyncContacts[] newArray(int size) {
            return new SyncContacts[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }
}
