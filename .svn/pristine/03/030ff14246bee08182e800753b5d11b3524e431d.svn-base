package com.goodkredit.myapplication.responses.schoolmini;

import com.goodkredit.myapplication.model.schoolmini.SchoolMiniConfig;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniConfigResponse {
    @SerializedName("data")
    @Expose
    private List<SchoolMiniConfig> schoolminiconfiglist = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SchoolMiniConfig> getSchoolminiconfiglist() {
        return schoolminiconfiglist;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
