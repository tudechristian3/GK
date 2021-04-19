package com.goodkredit.myapplication.responses.promo;

import com.goodkredit.myapplication.model.RedeemedPromo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 2/8/2018.
 */

public class RedeemedPromoHistoryResponse {

    @SerializedName("data")
    @Expose
    private List<RedeemedPromo> redeemedPromos = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<RedeemedPromo> getRedeemedPromos() {
        return redeemedPromos;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
