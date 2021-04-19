package com.goodkredit.myapplication.model.prepaidload;

import android.os.Parcel;
import android.os.Parcelable;

import com.goodkredit.myapplication.model.prepaidload.networkcodes.PreloadedGlobe;
import com.goodkredit.myapplication.model.prepaidload.networkcodes.PreloadedSmart;
import com.goodkredit.myapplication.model.prepaidload.networkcodes.PreloadedSun;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreloadedDataNew implements Parcelable {
    @SerializedName("LoadWallet")
    @Expose
    private List<PreloadedPrefix> loadWalletPrefix;
    @SerializedName("Network")
    @Expose
    private List<PreloadedPrefix> networkPrefix;
    @SerializedName("GlobeProducts")
    @Expose
    private List<PreloadedGlobe> preloadedGlobe;
    @SerializedName("SmartProducts")
    @Expose
    private List<PreloadedSmart> preloadedSmart;
    @SerializedName("SunProducts")
    @Expose
    private List<PreloadedSun> preloadedSun;

    public PreloadedDataNew(List<PreloadedPrefix> loadWalletPrefix, List<PreloadedPrefix> networkPrefix, List<PreloadedGlobe> preloadedGlobe, List<PreloadedSmart> preloadedSmart, List<PreloadedSun> preloadedSun) {
        this.loadWalletPrefix = loadWalletPrefix;
        this.networkPrefix = networkPrefix;
        this.preloadedGlobe = preloadedGlobe;
        this.preloadedSmart = preloadedSmart;
        this.preloadedSun = preloadedSun;
    }

    protected PreloadedDataNew(Parcel in) {
        loadWalletPrefix = in.createTypedArrayList(PreloadedPrefix.CREATOR);
        networkPrefix = in.createTypedArrayList(PreloadedPrefix.CREATOR);
        preloadedGlobe = in.createTypedArrayList(PreloadedGlobe.CREATOR);
        preloadedSmart = in.createTypedArrayList(PreloadedSmart.CREATOR);
        preloadedSun = in.createTypedArrayList(PreloadedSun.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(loadWalletPrefix);
        dest.writeTypedList(networkPrefix);
        dest.writeTypedList(preloadedGlobe);
        dest.writeTypedList(preloadedSmart);
        dest.writeTypedList(preloadedSun);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PreloadedDataNew> CREATOR = new Creator<PreloadedDataNew>() {
        @Override
        public PreloadedDataNew createFromParcel(Parcel in) {
            return new PreloadedDataNew(in);
        }

        @Override
        public PreloadedDataNew[] newArray(int size) {
            return new PreloadedDataNew[size];
        }
    };

    public List<PreloadedPrefix> getLoadWalletPrefix() {
        return loadWalletPrefix;
    }

    public List<PreloadedPrefix> getNetworkPrefix() {
        return networkPrefix;
    }

    public List<PreloadedGlobe> getPreloadedGlobe() {
        return preloadedGlobe;
    }

    public List<PreloadedSmart> getPreloadedSmart() {
        return preloadedSmart;
    }

    public List<PreloadedSun> getPreloadedSun() {
        return preloadedSun;
    }
}
