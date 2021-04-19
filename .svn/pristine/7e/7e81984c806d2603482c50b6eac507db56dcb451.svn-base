package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GoodApps on 29/01/2018.
 */

public class GKStoreMerchants implements Parcelable {

    @SerializedName("MerchantID")
    @Expose
    private String MerchantID;
    @SerializedName("GKStoreID")
    @Expose
    private String GKStoreID;
    @SerializedName("GKStoreName")
    @Expose
    private String GKStoreName;
    @SerializedName("GKStoreDesc")
    @Expose
    private String GKStoreDesc;
    @SerializedName("GKStoreCategory")
    @Expose
    private String GKStoreCategory;
    @SerializedName("GKStoreRepresentative")
    @Expose
    private String GKStoreRepresentative;
    @SerializedName("GKStoreMobileNo")
    @Expose
    private String GKStoreMobileNo;
    @SerializedName("GKStoreTelNo")
    @Expose
    private String GKStoreTelNo;
    @SerializedName("GKStoreEmailAddress")
    @Expose
    private String GKStoreEmailAddress;
    @SerializedName("GKStoreMerchantChargeClass")
    @Expose
    private String GKStoreMerchantChargeClass;
    @SerializedName("GKStoreCustomerChargeClass")
    @Expose
    private String GKStoreCustomerChargeClass;
    @SerializedName("GKStoreLogoURL")
    @Expose
    private String GKStoreLogoURL;
    @SerializedName("GKStoreBannerURL")
    @Expose
    private String GKStoreBannerURL;
    @SerializedName("KYCOtherInfo")
    @Expose
    private String KYCOtherInfo;
    @SerializedName("NoOfProducts")
    @Expose
    private int NoOfProducts;
    @SerializedName("NoOfOrders")
    @Expose
    private int NoOfOrders;
    @SerializedName("GKStoreNotes")
    @Expose
    private String GKStoreNotes;
    @SerializedName("DateTimeAdded")
    @Expose
    private String DateTimeAdded;
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


    protected GKStoreMerchants(Parcel in) {
        MerchantID = in.readString();
        GKStoreID = in.readString();
        GKStoreName = in.readString();
        GKStoreDesc = in.readString();
        GKStoreCategory = in.readString();
        GKStoreRepresentative = in.readString();
        GKStoreMobileNo = in.readString();
        GKStoreTelNo = in.readString();
        GKStoreEmailAddress = in.readString();
        GKStoreMerchantChargeClass = in.readString();
        GKStoreCustomerChargeClass = in.readString();
        GKStoreLogoURL = in.readString();
        GKStoreBannerURL = in.readString();
        KYCOtherInfo = in.readString();
        NoOfProducts = in.readInt();
        NoOfOrders = in.readInt();
        GKStoreNotes = in.readString();
        DateTimeAdded = in.readString();
        Status = in.readString();
        Extra1 = in.readString();
        Extra2 = in.readString();
        Extra3 = in.readString();
        Extra4 = in.readString();
        Notes1 = in.readString();
        Notes2 = in.readString();
    }

    public static final Creator<GKStoreMerchants> CREATOR = new Creator<GKStoreMerchants>() {
        @Override
        public GKStoreMerchants createFromParcel(Parcel in) {
            return new GKStoreMerchants(in);
        }

        @Override
        public GKStoreMerchants[] newArray(int size) {
            return new GKStoreMerchants[size];
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
        dest.writeString(GKStoreName);
        dest.writeString(GKStoreDesc);
        dest.writeString(GKStoreCategory);
        dest.writeString(GKStoreRepresentative);
        dest.writeString(GKStoreMobileNo);
        dest.writeString(GKStoreTelNo);
        dest.writeString(GKStoreEmailAddress);
        dest.writeString(GKStoreMerchantChargeClass);
        dest.writeString(GKStoreCustomerChargeClass);
        dest.writeString(GKStoreLogoURL);
        dest.writeString(GKStoreBannerURL);
        dest.writeString(KYCOtherInfo);
        dest.writeInt(NoOfProducts);
        dest.writeInt(NoOfOrders);
        dest.writeString(GKStoreNotes);
        dest.writeString(DateTimeAdded);
        dest.writeString(Status);
        dest.writeString(Extra1);
        dest.writeString(Extra2);
        dest.writeString(Extra3);
        dest.writeString(Extra4);
        dest.writeString(Notes1);
        dest.writeString(Notes2);
    }

    public GKStoreMerchants(String merchantID, String GKstoreID, String gkStoreName, String gkStoreDesc,
                            String gkStoreCategory, String gkStoreRepresentative, String gkStoreMobileNo,
                            String gkStoreTelNo, String gkStoreEmailAddress, String gkStoreMerchantChargeClass,
                            String gkStoreCustomerChargeClass, String gkStoreLogoURL, String gKStoreBannerURL,
                            String kYCOtherInfo, int noOfProducts, int noOfOrders, String gkStoreNotes,
                            String dateTimeAdded, String status, String extra1, String extra2, String extra3,
                            String extra4, String notes1, String notes2
                            ) {
        MerchantID = merchantID;
        GKStoreID = GKstoreID;
        GKStoreName = gkStoreName;
        GKStoreDesc = gkStoreDesc;
        GKStoreCategory = gkStoreCategory;
        GKStoreRepresentative = gkStoreRepresentative;
        GKStoreMobileNo = gkStoreMobileNo;
        GKStoreTelNo = gkStoreTelNo;
        GKStoreEmailAddress = gkStoreEmailAddress;
        GKStoreMerchantChargeClass = gkStoreMerchantChargeClass;
        GKStoreCustomerChargeClass = gkStoreCustomerChargeClass;
        GKStoreLogoURL = gkStoreLogoURL;
        GKStoreBannerURL = gKStoreBannerURL;
        KYCOtherInfo = kYCOtherInfo;
        NoOfProducts = noOfProducts;
        NoOfOrders = noOfOrders;
        GKStoreNotes = gkStoreNotes;
        DateTimeAdded = dateTimeAdded;
        Status = status;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
        Extra4 = extra4;
        Notes1 = notes1;
        Notes2 = notes2;
    }

    public String getMerchantID() {
        return MerchantID;
    }

    public String getStoreID() {
        return GKStoreID;
    }

    public String getGKStoreName() { return GKStoreName; }

    public String getGKStoreDesc() { return GKStoreDesc; }

    public String getGKStoreCategory() { return GKStoreCategory; }

    public String getGKStoreRepresentative() { return GKStoreRepresentative; }

    public String getGKStoreMobileNo() { return GKStoreMobileNo; }

    public String getGKStoreTelNo() { return GKStoreTelNo; }

    public String getGKStoreEmailAddress() { return GKStoreEmailAddress; }

    public String getGKStoreMerchantChargeClass() { return GKStoreMerchantChargeClass; }

    public String getGKStoreCustomerChargeClass() { return GKStoreCustomerChargeClass; }

    public String getGKStoreLogoURL() { return GKStoreLogoURL; }

    public String getGKStoreBannerURL() { return GKStoreBannerURL; }

    public String getKYCOtherInfo() { return KYCOtherInfo; }

    public int getNoOfProducts() { return NoOfProducts; }

    public int getNoOfOrders() { return NoOfOrders; }

    public String getGKStoreNotes() { return GKStoreNotes; }

    public String getDateTimeAdded() { return DateTimeAdded; }

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
