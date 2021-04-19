package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GoodApps on 22/01/2018.
 */

public class GKStoreProducts implements Parcelable {

    @SerializedName("MerchantID")
    @Expose
    private String MerchantID;
    @SerializedName("GKStoreID")
    @Expose
    private String GKStoreID;
    @SerializedName("ProductID")
    @Expose
    private String ProductID;
    @SerializedName("ProductName")
    @Expose
    private String ProductName;
    @SerializedName("ProductDesc")
    @Expose
    private String ProductDesc;
    @SerializedName("GrossPrice")
    @Expose
    private double GrossPrice;
    @SerializedName("DiscountPercentage")
    @Expose
    private double DiscountPercentage;
    @SerializedName("ActualPrice")
    @Expose
    private double ActualPrice;
    @SerializedName("ProductImageURL")
    @Expose
    private String ProductImageURL;
    @SerializedName("isPromo")
    @Expose
    private int isPromo;
    @SerializedName("PromoDetails")
    @Expose
    private String PromoDetails;

    @SerializedName("OrderQuantity")
    @Expose
    private int OrderQuantity;

    private int Quantity = 0;
    private double Total = 0.00;
    private int IsSelected = 0;

    //FROM HISTORY
    private String ProductType = "";
    @SerializedName("ProductImage")
    @Expose
    private String ProductImage;



    protected GKStoreProducts(Parcel in) {
        //COMMON
        MerchantID = in.readString();
        GKStoreID = in.readString();
        ProductID = in.readString();
        ProductName = in.readString();
        ProductDesc = in.readString();
        GrossPrice = in.readDouble();
        DiscountPercentage = in.readDouble();
        ActualPrice = in.readDouble();
        ProductImageURL = in.readString();
        isPromo = in.readInt();
        PromoDetails = in.readString();
        Quantity = in.readInt();
        Total = in.readDouble();
        IsSelected = in.readInt();
        //FROM HISTORY
        OrderQuantity = in.readInt();
        ProductType = in.readString();
        ProductImage = in.readString();
    }

    public static final Creator<GKStoreProducts> CREATOR = new Creator<GKStoreProducts>() {
        @Override
        public GKStoreProducts createFromParcel(Parcel in) {
            return new GKStoreProducts(in);
        }

        @Override
        public GKStoreProducts[] newArray(int size) {
            return new GKStoreProducts[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(MerchantID);
        dest.writeString(GKStoreID);
        dest.writeString(ProductID);
        dest.writeString(ProductName);
        dest.writeString(ProductDesc);
        dest.writeDouble(GrossPrice);
        dest.writeDouble(DiscountPercentage);
        dest.writeDouble(ActualPrice);
        dest.writeString(ProductImageURL);
        dest.writeInt(isPromo);
        dest.writeString(PromoDetails);
        dest.writeInt(Quantity);
        dest.writeDouble(Total);
        dest.writeInt(IsSelected);
        //FROM HISTORY
        dest.writeInt(OrderQuantity);
        dest.writeString(ProductType);
        dest.writeString(ProductImage);
    }

    public GKStoreProducts(String productID, String productName , int quantity, double actualPrice) {
        ProductID = productID;
        ProductName = productName;
        Quantity = quantity;
        ActualPrice = actualPrice;

    }

    public GKStoreProducts(String productID, String productName , int quantity, double actualPrice,
                           String productImageURL) {
        ProductID = productID;
        ProductName = productName;
        Quantity = quantity;
        ActualPrice = actualPrice;
        ProductImageURL = productImageURL;
    }

    public GKStoreProducts(String productID, String productName , int quantity, double actualPrice,
                           String productImageURL, int isSelected) {
        ProductID = productID;
        ProductName = productName;
        Quantity = quantity;
        ActualPrice = actualPrice;
        ProductImageURL = productImageURL;
        IsSelected = isSelected;
    }

    public GKStoreProducts(String merchantID, String GKstoreID, String productID, String productName,
                           String productDesc, double grossPrice, double discountPercentage, double actualPrice,
                           String productImageURL, int ispromo, String promoDetails ) {
        MerchantID = merchantID;
        GKStoreID = GKstoreID;
        ProductID = productID;
        ProductName = productName;
        ProductDesc = productDesc;
        GrossPrice = grossPrice;
        DiscountPercentage = discountPercentage;
        ActualPrice = actualPrice;
        ProductImageURL = productImageURL;
        isPromo = ispromo;
        PromoDetails = promoDetails;
    }

    public GKStoreProducts(String merchantID, String GKstoreID, String productID, String productName,
                           String productDesc, double grossPrice, double discountPercentage, double actualPrice,
                           String productImageURL, int ispromo, String promoDetails, int isSelected) {

        MerchantID = merchantID;
        GKStoreID = GKstoreID;
        ProductID = productID;
        ProductName = productName;
        ProductDesc = productDesc;
        GrossPrice = grossPrice;
        DiscountPercentage = discountPercentage;
        ActualPrice = actualPrice;
        ProductImageURL = productImageURL;
        isPromo = ispromo;
        PromoDetails = promoDetails;
        IsSelected = isSelected;
    }

    public GKStoreProducts(String productID, String productName, String productDesc,
                           String productType, String productImage,
                           double grossPrice, double discountPercentage,
                           double actualPrice, int orderQuantity) {

        ProductID = productID;
        ProductName = productName;
        ProductDesc = productDesc;
        ProductType = productType;
        GrossPrice = grossPrice;
        DiscountPercentage = discountPercentage;
        ActualPrice = actualPrice;
        ProductImage = productImage;
        OrderQuantity = orderQuantity;
    }

    public String getMerchantID() {
        return MerchantID;
    }

    public String getStoreID() {
        return GKStoreID;
    }

    public String getProductID() { return ProductID; }

    public String getProductName() {
        return ProductName;
    }

    public String getProductDesc() {
        return ProductDesc;
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

    public String getProductImageURL() {
        return ProductImageURL;
    }

    public int getisPromo() {
        return isPromo;
    }

    public String getPromoDetails() {
        return PromoDetails;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setMerchantID(String merchantID) {
        MerchantID = merchantID;
    }

    public void setGKStoreID(String GKStoreID) {
        this.GKStoreID = GKStoreID;
    }


    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public void setActualPrice(double actualPrice) {
        ActualPrice = actualPrice;
    }

    public String getGKStoreID() {
        return GKStoreID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public int getIsSelected() {
        return IsSelected;
    }

    public void setIsSelected(int isSelected) {
        IsSelected = isSelected;
    }

    public int getIsPromo() {
        return isPromo;
    }

    public int getOrderQuantity() {
        return OrderQuantity;
    }

    public String getProductType() {
        return ProductType;
    }

    public String getProductImage() {
        return ProductImage;
    }
}
