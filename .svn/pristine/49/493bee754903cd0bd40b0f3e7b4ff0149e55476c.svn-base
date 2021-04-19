package com.goodkredit.myapplication.model.prepaidload.networkcodes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreloadedSmart implements Parcelable {
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
    @SerializedName("Network")
    @Expose
    private String Network;

    public PreloadedSmart(int ID, String type, String keyword, String amount, String description, String network) {
        this.ID = ID;
        Type = type;
        Keyword = keyword;
        Amount = amount;
        Description = description;
        Network = network;
    }

    protected PreloadedSmart(Parcel in) {
        ID = in.readInt();
        Type = in.readString();
        Keyword = in.readString();
        Amount = in.readString();
        Description = in.readString();
        Network = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Type);
        dest.writeString(Keyword);
        dest.writeString(Amount);
        dest.writeString(Description);
        dest.writeString(Network);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PreloadedSmart> CREATOR = new Creator<PreloadedSmart>() {
        @Override
        public PreloadedSmart createFromParcel(Parcel in) {
            return new PreloadedSmart(in);
        }

        @Override
        public PreloadedSmart[] newArray(int size) {
            return new PreloadedSmart[size];
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

    public String getNetwork() {
        return Network;
    }
}
