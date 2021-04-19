package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.AmazonCredentials;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetS3keyResponse {
    @SerializedName("data")
    @Expose
    private List<AmazonCredentials> amazonCredentials = new ArrayList<>();
    @SerializedName("bucketname")
    @Expose
    private String BucketName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<AmazonCredentials> getAmazonCredentials() {
        return amazonCredentials;
    }

    public String getBucketName() {
        return BucketName;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
