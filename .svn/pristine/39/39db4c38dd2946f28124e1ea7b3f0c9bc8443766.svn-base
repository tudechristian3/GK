package com.goodkredit.myapplication.responses.schoolmini;

import com.goodkredit.myapplication.model.schoolmini.SchoolMiniStudents;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniStudentsResponse {
    @SerializedName("data")
    @Expose
    private ArrayList<SchoolMiniStudents> schoolMiniStudents = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<SchoolMiniStudents> getSchoolMiniStudents() {
        return schoolMiniStudents;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
