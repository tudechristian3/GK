package com.goodkredit.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkycableConfig {
    @SerializedName("ServiceCode")
    @Expose
    private String ServiceCode;
    @SerializedName("ServiceConfigCode")
    @Expose
    private String ServiceConfigCode;
    @SerializedName("ServiceConfigName")
    @Expose
    private String ServiceConfigName;
    @SerializedName("Status")
    @Expose
    private String Status;

    public SkycableConfig(String serviceCode, String serviceConfigCode, String serviceConfigName, String status) {
        ServiceCode = serviceCode;
        ServiceConfigCode = serviceConfigCode;
        ServiceConfigName = serviceConfigName;
        Status = status;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public String getServiceConfigCode() {
        return ServiceConfigCode;
    }

    public String getServiceConfigName() {
        return ServiceConfigName;
    }

    public String getStatus() {
        return Status;
    }
}
