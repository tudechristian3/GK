package com.goodkredit.myapplication.responses.prepaidrequest;

import com.goodkredit.myapplication.bean.prepaidrequest.EGHLPayment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestVoucherViaEghlPaymentResponse {
    @SerializedName("data")
    @Expose
    private EGHLPayment eghlPayment;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public EGHLPayment getEghlPayment() {
        return eghlPayment;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
