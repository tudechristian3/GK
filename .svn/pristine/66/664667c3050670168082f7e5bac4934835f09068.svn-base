package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKNegosyoPackageVoucher implements Parcelable {
    @SerializedName("Product")
    @Expose
    private String Product;
    @SerializedName("Denom")
    @Expose
    private int Denom;
    @SerializedName("ImageURL")
    @Expose
    private String ImageURL;
    @SerializedName("Quantity")
    @Expose
    private int Quantity;

    protected GKNegosyoPackageVoucher(Parcel in) {
        Product = in.readString();
        Denom = in.readInt();
        ImageURL = in.readString();
        Quantity = in.readInt();
    }

    public static final Creator<GKNegosyoPackageVoucher> CREATOR = new Creator<GKNegosyoPackageVoucher>() {
        @Override
        public GKNegosyoPackageVoucher createFromParcel(Parcel in) {
            return new GKNegosyoPackageVoucher(in);
        }

        @Override
        public GKNegosyoPackageVoucher[] newArray(int size) {
            return new GKNegosyoPackageVoucher[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Product);
        dest.writeInt(Denom);
        dest.writeString(ImageURL);
        dest.writeInt(Quantity);
    }

    public String getProduct() {
        return Product;
    }

    public int getDenom() {
        return Denom;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public int getQuantity() {
        return Quantity;
    }
}
