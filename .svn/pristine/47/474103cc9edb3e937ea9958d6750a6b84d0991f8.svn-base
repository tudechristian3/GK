package com.goodkredit.myapplication.model.gkearn;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKEarnStockistPackage implements Parcelable {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("PackageID")
    @Expose
    private String PackageID;
    @SerializedName("PackageName")
    @Expose
    private String PackageName;
    @SerializedName("PackageDescription")
    @Expose
    private String PackageDescription;
    @SerializedName("Price")
    @Expose
    private double Price;
    @SerializedName("ServiceCharge")
    @Expose
    private double ServiceCharge;
    @SerializedName("Reward")
    @Expose
    private double Reward;
    @SerializedName("NumberMonthExpiry")
    @Expose
    private int NumberMonthExpiry;
    @SerializedName("PackageImageURL")
    @Expose
    private String PackageImageURL;
    @SerializedName("XMLDetails")
    @Expose
    private String XMLDetails;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("Extra1")
    @Expose
    private String Extra1;
    @SerializedName("Extra2")
    @Expose
    private String Extra2;
    @SerializedName("Extra3")
    @Expose
    private String Extra3;
    @SerializedName("Extra4")
    @Expose
    private String Extra4;
    @SerializedName("Notes1")
    @Expose
    private String Notes1;
    @SerializedName("Notes2")
    @Expose
    private String Notes2;

    public GKEarnStockistPackage(int ID, String packageID, String packageName, String packageDescription, double price, double serviceCharge, double reward, int numberMonthExpiry, String packageImageURL, String XMLDetails, String status, String extra1, String extra2, String extra3, String extra4, String notes1, String notes2) {
        this.ID = ID;
        PackageID = packageID;
        PackageName = packageName;
        PackageDescription = packageDescription;
        Price = price;
        ServiceCharge = serviceCharge;
        Reward = reward;
        NumberMonthExpiry = numberMonthExpiry;
        PackageImageURL = packageImageURL;
        this.XMLDetails = XMLDetails;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    protected GKEarnStockistPackage(Parcel in) {
        ID = in.readInt();
        PackageID = in.readString();
        PackageName = in.readString();
        PackageDescription = in.readString();
        Price = in.readDouble();
        ServiceCharge = in.readDouble();
        Reward = in.readDouble();
        NumberMonthExpiry = in.readInt();
        PackageImageURL = in.readString();
        XMLDetails = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(PackageID);
        dest.writeString(PackageName);
        dest.writeString(PackageDescription);
        dest.writeDouble(Price);
        dest.writeDouble(ServiceCharge);
        dest.writeDouble(Reward);
        dest.writeInt(NumberMonthExpiry);
        dest.writeString(PackageImageURL);
        dest.writeString(XMLDetails);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GKEarnStockistPackage> CREATOR = new Creator<GKEarnStockistPackage>() {
        @Override
        public GKEarnStockistPackage createFromParcel(Parcel in) {
            return new GKEarnStockistPackage(in);
        }

        @Override
        public GKEarnStockistPackage[] newArray(int size) {
            return new GKEarnStockistPackage[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getPackageID() {
        return PackageID;
    }

    public String getPackageName() {
        return PackageName;
    }

    public String getPackageDescription() {
        return PackageDescription;
    }

    public double getPrice() {
        return Price;
    }

    public double getServiceCharge() {
        return ServiceCharge;
    }

    public double getReward() {
        return Reward;
    }

    public int getNumberMonthExpiry() {
        return NumberMonthExpiry;
    }

    public String getPackageImageURL() {
        return PackageImageURL;
    }

    public String getXMLDetails() {
        return XMLDetails;
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

    public String getExtra3() {
        return Extra3;
    }

    public String getExtra4() {
        return Extra4;
    }

    public String getNotes1() {
        return Notes1;
    }

    public String getNotes2() {
        return Notes2;
    }
}
