package com.goodkredit.myapplication.responses.rfid;

import com.goodkredit.myapplication.model.rfid.RFIDInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetRFIDInformationResponse {

    @SerializedName("data")
    @Expose
    private List<RFIDInfo> rfidInformationList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<RFIDInfo> getRfidInformationList() {
        return rfidInformationList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
