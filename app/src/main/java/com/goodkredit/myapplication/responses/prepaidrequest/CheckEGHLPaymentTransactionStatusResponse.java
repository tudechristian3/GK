package com.goodkredit.myapplication.responses.prepaidrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CheckEGHLPaymentTransactionStatusResponse {
    @SerializedName("data")
    @Expose
    private String TxnStatus;
    @SerializedName("remarks")
    @Expose
    private String Remarks;
    @SerializedName("status")
    @Expose
    private String Status;
    @SerializedName("message")
    @Expose
    private String Message;

    public String getTxnStatus() {
        return TxnStatus;
    }

    public String getRemarks() {
        return Remarks;
    }

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }
}
