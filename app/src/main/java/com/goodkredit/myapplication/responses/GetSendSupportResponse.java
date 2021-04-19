package com.goodkredit.myapplication.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 3/2/2017.
 */

public class GetSendSupportResponse {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("message")
    @Expose
    private String message;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
