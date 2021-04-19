package com.goodkredit.myapplication.responses.medpadala;

import com.goodkredit.myapplication.model.MedPadalaVoucherUsed;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ban on 16/03/2018.
 */

public class GetMedPadalaTransactionVoucherDetailsResponse {

    @SerializedName("data")
    @Expose
    private List<MedPadalaVoucherUsed> data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<MedPadalaVoucherUsed> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
