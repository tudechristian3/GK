package com.goodkredit.myapplication.responses.promo;

import com.goodkredit.myapplication.bean.PromoQRDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 1/25/2018.
 */

public class RedeemPromoQRResponse {
    @SerializedName("data")
    @Expose
    private PromoQRDetails promoQRDetails;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public PromoQRDetails getPromoQRDetails() {
        return promoQRDetails;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
