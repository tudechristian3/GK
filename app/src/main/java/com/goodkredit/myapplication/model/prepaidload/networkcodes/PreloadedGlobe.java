package com.goodkredit.myapplication.model.prepaidload.networkcodes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreloadedGlobe implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("DenomMode")
    @Expose
    private String DenomMode;
    @SerializedName("DenomType")
    @Expose
    private String DenomType;
    @SerializedName("MinAmnt")
    @Expose
    private String MinAmnt;
    @SerializedName("MaxAmnt")
    @Expose
    private String MaxAmnt;
    @SerializedName("ProductCode")
    @Expose
    private String ProductCode;
    @SerializedName("DenomStatus")
    @Expose
    private String DenomStatus;
    @SerializedName("Network")
    @Expose
    private String Network;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;

    public PreloadedGlobe(int ID, String denomMode, String denomType, String minAmnt, String maxAmnt, String productCode, String denomStatus, String network, String extra1, String extra2, String notes1, String notes2) {
        this.ID = ID;
        DenomMode = denomMode;
        DenomType = denomType;
        MinAmnt = minAmnt;
        MaxAmnt = maxAmnt;
        ProductCode = productCode;
        DenomStatus = denomStatus;
        Network = network;
        Extra1 = extra1;
        Extra2 = extra2;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected PreloadedGlobe(Parcel in) {
        ID = in.readInt();
        DenomMode = in.readString();
        DenomType = in.readString();
        MinAmnt = in.readString();
        MaxAmnt = in.readString();
        ProductCode = in.readString();
        DenomStatus = in.readString();
        Network = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(DenomMode);
        dest.writeString(DenomType);
        dest.writeString(MinAmnt);
        dest.writeString(MaxAmnt);
        dest.writeString(ProductCode);
        dest.writeString(DenomStatus);
        dest.writeString(Network);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PreloadedGlobe> CREATOR = new Creator<PreloadedGlobe>() {
        @Override
        public PreloadedGlobe createFromParcel(Parcel in) {
            return new PreloadedGlobe(in);
        }

        @Override
        public PreloadedGlobe[] newArray(int size) {
            return new PreloadedGlobe[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getDenomMode() {
        return DenomMode;
    }

    public String getDenomType() {
        return DenomType;
    }

    public String getMinAmnt() {
        return MinAmnt;
    }

    public String getMaxAmnt() {
        return MaxAmnt;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public String getDenomStatus() {
        return DenomStatus;
    }

    public String getNetwork() {
        return Network;
    }

    public String getExtra1() {
        return Extra1;
    }

    public String getExtra2() {
        return Extra2;
    }

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }
}
