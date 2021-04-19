package com.goodkredit.myapplication.responses.schoolmini;

import com.goodkredit.myapplication.model.schoolmini.SchoolMiniConfig;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDtr;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniDtrResponse {
    @SerializedName("data")
    @Expose
    private List<SchoolMiniDtr> list = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SchoolMiniDtr> getList() {
        return list;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
