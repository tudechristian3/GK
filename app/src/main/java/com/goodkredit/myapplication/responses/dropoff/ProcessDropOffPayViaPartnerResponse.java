package com.goodkredit.myapplication.responses.dropoff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProcessDropOffPayViaPartnerResponse {

    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public ProcessDropOffPayViaPartnerResponse(String data, String status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
