package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PublicSponsorPromos implements Parcelable {

    @SerializedName("GuarantorID")
    @Expose
    private String GuarantorID;
    @SerializedName("PromoLink")
    @Expose
    private String PromoLink;
    @SerializedName("Title")
    @Expose
    private String Title;
    @SerializedName("Description")
    @Expose
    private String Description;




    public PublicSponsorPromos(String ppsGuarantorID,String pspromoImage, String pspromoTitle, String psPromoDescription) {
        this.PromoLink = pspromoImage;
        this.Title = pspromoTitle;
        this.Description = psPromoDescription;
        this.GuarantorID = ppsGuarantorID;
    }

    protected PublicSponsorPromos(Parcel in) {
        GuarantorID = in.readString();
        PromoLink = in.readString();
        Title = in.readString();
        Description = in.readString();
    }

    public static final Creator<PublicSponsorPromos> CREATOR = new Creator<PublicSponsorPromos>() {
        @Override
        public PublicSponsorPromos createFromParcel(Parcel in) {
            return new PublicSponsorPromos(in);
        }

        @Override
        public PublicSponsorPromos[] newArray(int size) {
            return new PublicSponsorPromos[size];
        }
    };




    public String getPSPromoImage() {
        return PromoLink;
    }

    public void setPSPromoImage(String pspromoImage) {
        this.PromoLink = pspromoImage;
    }

    public String getPSPromoTitle() {
        return Title;
    }

    public void setPSPromoTitle(String pspromoTitle) {
        this.Title = pspromoTitle;
    }

    public String getPSPromoDescription() {
        return Description;
    }

    public void setPsPromoDescription(String psPromoDescription) {
        this.Description = psPromoDescription;
    }

    public String getGuarantorID() {
        return GuarantorID;
    }

    public void setPSGuarantorID(String psGuarantorID) {
        this.GuarantorID = psGuarantorID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(GuarantorID);
        dest.writeString(PromoLink);
        dest.writeString(Title);
        dest.writeString(Description);
    }
}



