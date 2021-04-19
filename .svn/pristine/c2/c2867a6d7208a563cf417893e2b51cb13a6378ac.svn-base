package com.goodkredit.myapplication.responses.mdp;


import com.goodkredit.myapplication.model.mdp.MDPSupportFAQ;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetMDPSupportFAQResponse {

    @SerializedName("data")
    @Expose
    private List<MDPSupportFAQ> MDPSupportFAQList = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<MDPSupportFAQ> getMDPSupportFAQList() {
        return MDPSupportFAQList;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
