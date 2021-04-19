package com.goodkredit.myapplication.responses.transfervoucher;

import com.goodkredit.myapplication.model.transfervoucher.TransferValidateVoucher;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransferValidateVoucherResponse {
    @SerializedName("data")
    @Expose
    private List<TransferValidateVoucher> data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<TransferValidateVoucher> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
