package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKConfigurations implements Parcelable {

    public static final String KEY_MENUSERVICES = "Menuservices";

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("ServiceCode")
    @Expose
    private String ServiceCode;
    @SerializedName("ServiceConfigCode")
    @Expose
    private String ServiceConfigCode;
    @SerializedName("ServiceConfigName")
    @Expose
    private String ServiceConfigName;
    @SerializedName("Status")
    @Expose
    private String Status;
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

    public GKConfigurations(int ID, String serviceCode, String serviceConfigCode, String serviceConfigName, String status, String extra1, String extra2, String notes1, String notes2) {
        this.ID = ID;
        ServiceCode = serviceCode;
        ServiceConfigCode = serviceConfigCode;
        ServiceConfigName = serviceConfigName;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected GKConfigurations(Parcel in) {
        ID = in.readInt();
        ServiceCode = in.readString();
        ServiceConfigCode = in.readString();
        ServiceConfigName = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(ServiceCode);
        dest.writeString(ServiceConfigCode);
        dest.writeString(ServiceConfigName);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GKConfigurations> CREATOR = new Creator<GKConfigurations>() {
        @Override
        public GKConfigurations createFromParcel(Parcel in) {
            return new GKConfigurations(in);
        }

        @Override
        public GKConfigurations[] newArray(int size) {
            return new GKConfigurations[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public String getServiceConfigCode() {
        return ServiceConfigCode;
    }

    public String getServiceConfigName() {
        return ServiceConfigName;
    }

    public String getStatus() {
        return Status;
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
