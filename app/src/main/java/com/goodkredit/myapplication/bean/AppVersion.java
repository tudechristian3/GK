package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.goodkredit.myapplication.model.prepaidload.PreloadedSignature;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ban_Lenovo on 11/3/2017.
 */

public class AppVersion implements Parcelable {

    @SerializedName("appversion")
    @Expose
    private String appversion;
    @SerializedName("serviceversion")
    @Expose
    private String serviceversion;
    @SerializedName("preloaded")
    @Expose
    private List<PreloadedSignature> preloaded;

    public AppVersion(String appversion, String serviceversion) {
        this.appversion = appversion;
        this.serviceversion = serviceversion;
    }

    protected AppVersion(Parcel in) {
        appversion = in.readString();
        serviceversion = in.readString();
        preloaded = in.createTypedArrayList(PreloadedSignature.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appversion);
        dest.writeString(serviceversion);
        dest.writeTypedList(preloaded);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppVersion> CREATOR = new Creator<AppVersion>() {
        @Override
        public AppVersion createFromParcel(Parcel in) {
            return new AppVersion(in);
        }

        @Override
        public AppVersion[] newArray(int size) {
            return new AppVersion[size];
        }
    };

    public String getAppversion() {
        return appversion;
    }

    public String getServiceversion() {
        return serviceversion;
    }

    public List<PreloadedSignature> getPreloaded() {
        return preloaded;
    }
}

