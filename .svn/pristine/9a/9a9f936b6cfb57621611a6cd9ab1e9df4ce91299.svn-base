package com.goodkredit.myapplication.responses.schoolmini;

import com.goodkredit.myapplication.model.schoolmini.SchoolMiniGrades;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniTuitionFee;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniTuitionFeeResponse {
    @SerializedName("data")
    @Expose
    private List<SchoolMiniTuitionFee> schoolminituitionfeelist = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SchoolMiniTuitionFee> getSchoolMiniTuition() {
        return schoolminituitionfeelist;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
