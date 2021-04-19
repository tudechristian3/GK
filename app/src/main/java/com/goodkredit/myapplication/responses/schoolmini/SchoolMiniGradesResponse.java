package com.goodkredit.myapplication.responses.schoolmini;

import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDetails;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniGrades;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniGradesResponse {
    @SerializedName("data")
    @Expose
    private List<SchoolMiniGrades> schoolminigradeslist = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SchoolMiniGrades> getSchoolMiniGrades() {
        return schoolminigradeslist;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
