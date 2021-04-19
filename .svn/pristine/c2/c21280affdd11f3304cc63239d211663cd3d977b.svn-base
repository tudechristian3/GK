package com.goodkredit.myapplication.model.gkearn;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKEarnReferralRandom implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int id;
    @SerializedName("BorrowerID")
    @Expose
    private String BorrowerID;
    @SerializedName("BorrowerName")
    @Expose
    private String BorrowerName;
    @SerializedName("Extra3")
    @Expose
    private String Extra3;

    public GKEarnReferralRandom(int id, String borrowerID, String borrowerName, String extra3) {
        this.id = id;
        BorrowerID = borrowerID;
        BorrowerName = borrowerName;
        Extra3 = extra3;
    }

    protected GKEarnReferralRandom(Parcel in) {
        id = in.readInt();
        BorrowerID = in.readString();
        BorrowerName = in.readString();
        Extra3 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(BorrowerID);
        dest.writeString(BorrowerName);
        dest.writeString(Extra3);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GKEarnReferralRandom> CREATOR = new Creator<GKEarnReferralRandom>() {
        @Override
        public GKEarnReferralRandom createFromParcel(Parcel in) {
            return new GKEarnReferralRandom(in);
        }

        @Override
        public GKEarnReferralRandom[] newArray(int size) {
            return new GKEarnReferralRandom[size];
        }
    };

    public int getID() {
        return id;
    }

    public String getBorrowerID() {
        return BorrowerID;
    }

    public String getBorrowerName() {
        return BorrowerName;
    }

    public String getExtra3() {
        return Extra3;
    }
}
