package com.goodkredit.myapplication.bean.projectcoop;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameProduct implements Parcelable {
    @SerializedName("ProductID")
    @Expose
    private String ProductID;
    @SerializedName("ProductName")
    @Expose
    private String ProductName;
    @SerializedName("ProductDesc")
    @Expose
    private String ProductDesc;
    @SerializedName("Digit")
    @Expose
    private int Digit;
    @SerializedName("Price")
    @Expose
    private double Price;
    @SerializedName("MinNumber")
    @Expose
    private int MinNumber;
    @SerializedName("MaxNumber")
    @Expose
    private int MaxNumber;
    @SerializedName("IsUnique")
    @Expose
    private int IsUnique;
    @SerializedName("ServiceChargeClass")
    @Expose
    private String ServiceChargeClass;

    public GameProduct(String productID, String productName, String productDesc, int digit, double price, int minNumber, int maxNumber, int isUnique, String serviceChargeClass) {
        ProductID = productID;
        ProductName = productName;
        ProductDesc = productDesc;
        Digit = digit;
        Price = price;
        MinNumber = minNumber;
        MaxNumber = maxNumber;
        IsUnique = isUnique;
        ServiceChargeClass = serviceChargeClass;
    }

    protected GameProduct(Parcel in) {
        ProductID = in.readString();
        ProductName = in.readString();
        ProductDesc = in.readString();
        Digit = in.readInt();
        Price = in.readDouble();
        MinNumber = in.readInt();
        MaxNumber = in.readInt();
        IsUnique = in.readInt();
        ServiceChargeClass = in.readString();
    }

    public static final Creator<GameProduct> CREATOR = new Creator<GameProduct>() {
        @Override
        public GameProduct createFromParcel(Parcel in) {
            return new GameProduct(in);
        }

        @Override
        public GameProduct[] newArray(int size) {
            return new GameProduct[size];
        }
    };

    public String getProductID() {
        return ProductID;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public int getDigit() {
        return Digit;
    }

    public double getPrice() {
        return Price;
    }

    public int getMinNumber() {
        return MinNumber;
    }

    public int getMaxNumber() {
        return MaxNumber;
    }

    public int getIsUnique() {
        return IsUnique;
    }

    public String getServiceChargeClass() {
        return ServiceChargeClass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ProductID);
        dest.writeString(ProductName);
        dest.writeString(ProductDesc);
        dest.writeInt(Digit);
        dest.writeDouble(Price);
        dest.writeInt(MinNumber);
        dest.writeInt(MaxNumber);
        dest.writeInt(IsUnique);
        dest.writeString(ServiceChargeClass);
    }
}
