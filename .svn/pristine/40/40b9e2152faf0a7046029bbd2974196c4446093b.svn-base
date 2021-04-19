package com.goodkredit.myapplication.model.prepaidload;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreloadedSignature implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("Type")
    @Expose
    private String Type;
    @SerializedName("Signature")
    @Expose
    private String Signature;
    @SerializedName("Status")
    @Expose
    private String Status;

    public PreloadedSignature(int ID, String type, String signature, String status) {
        this.ID = ID;
        Type = type;
        Signature = signature;
        Status = status;
    }

    protected PreloadedSignature(Parcel in) {
        ID = in.readInt();
        Type = in.readString();
        Signature = in.readString();
        Status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Type);
        dest.writeString(Signature);
        dest.writeString(Status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PreloadedSignature> CREATOR = new Creator<PreloadedSignature>() {
        @Override
        public PreloadedSignature createFromParcel(Parcel in) {
            return new PreloadedSignature(in);
        }

        @Override
        public PreloadedSignature[] newArray(int size) {
            return new PreloadedSignature[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getType() {
        return Type;
    }

    public String getSignature() {
        return Signature;
    }

    public String getStatus() {
        return Status;
    }
}
