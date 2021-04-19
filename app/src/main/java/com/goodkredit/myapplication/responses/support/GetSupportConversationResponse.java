package com.goodkredit.myapplication.responses.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 3/15/2017.
 */

public class GetSupportConversationResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<GetSupportConversationResponseData> data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<GetSupportConversationResponseData> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
