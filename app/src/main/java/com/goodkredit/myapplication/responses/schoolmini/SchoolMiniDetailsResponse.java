package com.goodkredit.myapplication.responses.schoolmini;

import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniDetailsResponse {
    @SerializedName("data")
    @Expose
    private List<SchoolMiniDetails> schoolMiniDetails = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    
    public List<SchoolMiniDetails> getSchoolMiniDetails() {
        return schoolMiniDetails;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
