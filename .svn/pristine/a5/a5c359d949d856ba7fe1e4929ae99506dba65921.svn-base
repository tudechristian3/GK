package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GoodApps on 22/02/2018.
 */

public class GenericObject implements Parcelable {
    @SerializedName("productid")
    @Expose
    private String ProductID;
    @SerializedName("quantity")
    @Expose
    private int Quantity;
    @SerializedName("imageurl")
    @Expose
    private String ImageUrl;


    protected GenericObject(Parcel in) {
        ProductID = in.readString();
        Quantity = in.readInt();
        ImageUrl = in.readString();
    }

    public static final Creator<GenericObject> CREATOR = new Creator<GenericObject>() {
        @Override
        public GenericObject createFromParcel(Parcel in) {
            return new GenericObject(in);
        }

        @Override
        public GenericObject[] newArray(int size) {
            return new GenericObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public GenericObject(String productID, int quantity, String imageurl) {
        ProductID = productID;
        Quantity = quantity;
        ImageUrl = imageurl;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ProductID);
        dest.writeInt(Quantity);
        dest.writeString(ImageUrl);
    }

    public String getProductID() {
        return ProductID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
}
