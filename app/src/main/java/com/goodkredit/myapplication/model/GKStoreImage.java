package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKStoreImage implements Parcelable {
    @SerializedName("imageurl")
    @Expose
    private String ImageUrl;

    protected GKStoreImage(Parcel in) {
        ImageUrl = in.readString();
    }

    public static final Creator<GKStoreImage> CREATOR = new Creator<GKStoreImage>() {
        @Override
        public GKStoreImage createFromParcel(Parcel in) {
            return new GKStoreImage(in);
        }

        @Override
        public GKStoreImage[] newArray(int size) {
            return new GKStoreImage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public GKStoreImage(String imageurl) {
        ImageUrl = imageurl;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ImageUrl);
    }

    public String getImageUrl() {
        return ImageUrl;
    }
}
