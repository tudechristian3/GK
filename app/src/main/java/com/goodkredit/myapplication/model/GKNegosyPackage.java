package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GKNegosyPackage implements Parcelable {
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
    @SerializedName("XMLDetails")
    @Expose
    private String strVoucherInclusions;
    @SerializedName("NumberMonthExpiry")
    @Expose
    private int NumberMonthExpiry;
    @SerializedName("URL")
    @Expose
    private String URL;
    @SerializedName("ExpiryDate")
    @Expose
    private String ExpiryDate;
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
    @SerializedName("DistributorName")
    @Expose
    private String DistributorName;


    public GKNegosyPackage(int ID, String packageID, String packageName, String packageDescription,
                           double price, List<GKNegosyoPackageVoucher> XMLDetails,
                           int numberMonthExpiry, String URL, String status, String extra1,
                           String extra2, String extra3, String extra4, String notes1, String notes2,
                           String expiryDate) {
        this.ID = ID;
        PackageID = packageID;
        PackageName = packageName;
        PackageDescription = packageDescription;
        Price = price;
        NumberMonthExpiry = numberMonthExpiry;
        this.URL = URL;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
        ExpiryDate = expiryDate;
    }


    protected GKNegosyPackage(Parcel in) {
        ID = in.readInt();
        PackageID = in.readString();
        PackageName = in.readString();
        PackageDescription = in.readString();
        Price = in.readDouble();
        strVoucherInclusions = in.readString();
        NumberMonthExpiry = in.readInt();
        URL = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
        ExpiryDate = in.readString();
        DistributorName = in.readString();
    }

    public static final Creator<GKNegosyPackage> CREATOR = new Creator<GKNegosyPackage>() {
        @Override
        public GKNegosyPackage createFromParcel(Parcel in) {
            return new GKNegosyPackage(in);
        }

        @Override
        public GKNegosyPackage[] newArray(int size) {
            return new GKNegosyPackage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(PackageID);
        dest.writeString(PackageName);
        dest.writeString(PackageDescription);
        dest.writeDouble(Price);
        dest.writeString(strVoucherInclusions);
        dest.writeInt(NumberMonthExpiry);
        dest.writeString(URL);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
        dest.writeString(ExpiryDate);
        dest.writeString(DistributorName);
    }

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

    public GKNegosyoPackageInclusion getVoucherInclusions() {
        return new Gson().fromJson(strVoucherInclusions, GKNegosyoPackageInclusion.class);
    }

    public int getNumberMonthExpiry() {
        return NumberMonthExpiry;
    }

    public String getURL() {
        return URL;
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

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public String getDistributorName() {
        return DistributorName;
    }
}
