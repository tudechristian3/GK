package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 10/25/2017.
 */

public class GKService implements Parcelable {

    public static final String KEY_SERVICE_OBJ = "service_object";

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("ServiceCode")
    @Expose
    private String ServiceCode;
    @SerializedName("ServiceName")
    @Expose
    private String ServiceName;
    @SerializedName("Type")
    @Expose
    private String Type;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("NewLogo")
    @Expose
    private String Logo;
    @SerializedName("MerchantID")
    @Expose
    private String MerchantID;
    @SerializedName("GKStoreID")
    @Expose
    private String GKStoreID;
    @SerializedName("ServiceType")
    @Expose
    private String ServiceType;
    @SerializedName("GKStoreStatus")
    @Expose
    private String GKStoreStatus;
    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;

    @SerializedName("Category")
    @Expose
    private String Category;

    @SerializedName("Color")
    @Expose
    private String Color;

    @SerializedName("Rank")
    @Expose
    private int Rank;

    protected GKService(Parcel in) {
        ID = in.readInt();
        ServiceCode = in.readString();
        ServiceName = in.readString();
        Type = in.readString();
        Status = in.readString();
        Logo = in.readString();
        MerchantID = in.readString();
        GKStoreID = in.readString();
        ServiceType = in.readString();
        GKStoreStatus = in.readString();
        GuarantorID = in.readString();
        Category = in.readString();
        Color = in.readString();
        Rank = in.readInt();

    }

    public static final Creator<GKService> CREATOR = new Creator<GKService>() {
        @Override
        public GKService createFromParcel(Parcel in) {
            return new GKService(in);
        }

        @Override
        public GKService[] newArray(int size) {
            return new GKService[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(ServiceCode);
        dest.writeString(ServiceName);
        dest.writeString(Type);
        dest.writeString(Status);
        dest.writeString(Logo);
        dest.writeString(MerchantID);
        dest.writeString(GKStoreID);
        dest.writeString(ServiceType);
        dest.writeString(GKStoreStatus);
        dest.writeString(GuarantorID);
        dest.writeString(Category);
        dest.writeString(Color);
        dest.writeInt(Rank);
    }

    public GKService(int ID, String serviceCode, String serviceName, String type, String status, String logo,
                     String merchantID, String GKStoreID, String serviceType, String gkStoreStatus, String guarantorID,String category,String color,int rank) {
        this.ID = ID;
        ServiceCode = serviceCode;
        ServiceName = serviceName;
        Type = type;
        Status = status;
        Logo = logo;
        MerchantID = merchantID;
        this.GKStoreID = GKStoreID;
        ServiceType = serviceType;
        GKStoreStatus = gkStoreStatus;
        GuarantorID = guarantorID;
        Category = category;
        Color = color;
        Rank = rank;
    }

    public int getID() {
        return ID;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public String getType() {
        return Type;
    }

    public String getStatus() {
        return Status;
    }

    public String getLogo() {
        return Logo;
    }

    public String getMerchantID() {
        return MerchantID;
    }

    public String getGKStoreID() {
        return GKStoreID;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public String getGKStoreStatus() {
        return GKStoreStatus;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }
    public String getColor() {
        return Color;
    }

    public String getCategory() {
        return Category;
    }

    public int getRank() {
        return Rank;
    }
}
