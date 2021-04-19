package com.goodkredit.myapplication.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 7/15/2017.
 */

public class GetSessionResponse {

    @SerializedName("data")
    @Expose
    private String session;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public String getSession() {
        return session;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
