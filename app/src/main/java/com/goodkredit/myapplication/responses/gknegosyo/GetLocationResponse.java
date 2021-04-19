package com.goodkredit.myapplication.responses.gknegosyo;

import com.goodkredit.myapplication.model.GeoCodingResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetLocationResponse {
    @SerializedName("results")
    @Expose
    List<GeoCodingResult> results = new ArrayList<>();

    public List<GeoCodingResult> getResults() {
        return results;
    }
}
