package com.goodkredit.myapplication.responses.paramount;

import com.goodkredit.myapplication.bean.ParamountVouchers;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetParamountPaymentVouchersResponse {
    @SerializedName("data")
    @Expose
    private List<ParamountVouchers> paramountVouchers = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ParamountVouchers> getParamountVouchers() {
        return paramountVouchers;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
