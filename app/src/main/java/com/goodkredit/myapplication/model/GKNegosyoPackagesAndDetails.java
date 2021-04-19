package com.goodkredit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GKNegosyoPackagesAndDetails implements Parcelable {

    @SerializedName("DistributorPackages")
    @Expose
    private List<GKNegosyPackage> DistributorPackages = new ArrayList<>();
    @SerializedName("DiscountedServices")
    @Expose
    private List<GKNegosyoPackageDiscountedService> DiscountedServices = new ArrayList<>();

    protected GKNegosyoPackagesAndDetails(Parcel in) {
        DistributorPackages = in.createTypedArrayList(GKNegosyPackage.CREATOR);
        DiscountedServices = in.createTypedArrayList(GKNegosyoPackageDiscountedService.CREATOR);
    }

    public static final Creator<GKNegosyoPackagesAndDetails> CREATOR = new Creator<GKNegosyoPackagesAndDetails>() {
        @Override
        public GKNegosyoPackagesAndDetails createFromParcel(Parcel in) {
            return new GKNegosyoPackagesAndDetails(in);
        }

        @Override
        public GKNegosyoPackagesAndDetails[] newArray(int size) {
            return new GKNegosyoPackagesAndDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(DistributorPackages);
        dest.writeTypedList(DiscountedServices);
    }

    public List<GKNegosyPackage> getDistributorPackages() {
        return DistributorPackages;
    }

    public List<GKNegosyoPackageDiscountedService> getDiscountedServices(String packageID) {
        List<GKNegosyoPackageDiscountedService> data = new ArrayList<>();
        for (GKNegosyoPackageDiscountedService gknpDiscountedService : DiscountedServices) {
            if (gknpDiscountedService.getPackageID().contentEquals(packageID))
                data.add(gknpDiscountedService);
        }
        return data;
    }
}
