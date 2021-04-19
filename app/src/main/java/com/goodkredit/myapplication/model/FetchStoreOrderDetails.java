package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.goodkredit.myapplication.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by GoodApps on 15/03/2018.
 */

public class FetchStoreOrderDetails implements Parcelable {
    @SerializedName("ProductID")
    @Expose
    private String ProductID;
    @SerializedName("ProductName")
    @Expose
    private String ProductName;
    @SerializedName("ProductDesc")
    @Expose
    private String ProductDesc;
    @SerializedName("ProductImage")
    @Expose
    private String ProductImage;
    @SerializedName("GrossPrice")
    @Expose
    private double GrossPrice;
    @SerializedName("DiscountPercentage")
    @Expose
    private double DiscountPercentage;
    @SerializedName("ActualPrice")
    @Expose
    private double ActualPrice;
    @SerializedName("OrderQuantity")
    @Expose
    private int OrderQuantity;

    protected FetchStoreOrderDetails(Parcel in) {
        ProductID = in.readString();
        ProductName = in.readString();
        ProductDesc = in.readString();
        GrossPrice = in.readDouble();
        DiscountPercentage = in.readDouble();
        ActualPrice = in.readDouble();
        OrderQuantity = in.readInt();
    }

    public static final Creator<FetchStoreOrderDetails> CREATOR = new Creator<FetchStoreOrderDetails>() {
        @Override
        public FetchStoreOrderDetails createFromParcel(Parcel in) {
            return new FetchStoreOrderDetails(in);
        }

        @Override
        public FetchStoreOrderDetails[] newArray(int size) {
            return new FetchStoreOrderDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ProductID);
        dest.writeString(ProductName);
        dest.writeString(ProductDesc);
        dest.writeString(ProductImage);
        dest.writeDouble(GrossPrice);
        dest.writeDouble(DiscountPercentage);
        dest.writeDouble(ActualPrice);
        dest.writeInt(OrderQuantity);
    }

    public FetchStoreOrderDetails(String productID, String productName, String productDesc, String productImage,
                                  double grossprice, double discountPercentage, double actualPrice, int orderQuantity) {
        ProductID = productID;
        ProductName = productName;
        ProductDesc = productDesc;
        ProductImage = productImage;
        GrossPrice = grossprice;
        DiscountPercentage = discountPercentage;
        ActualPrice = actualPrice;
        OrderQuantity = orderQuantity;
    }
    public String getProductID() {
        return ProductID;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public double getGrossPrice() {
        return GrossPrice;
    }

    public double getDiscountPercentage() {
        return DiscountPercentage;
    }

    public double getActualPrice() {
        return ActualPrice;
    }

    public int getOrderQuantity() {
        return OrderQuantity;
    }
}
