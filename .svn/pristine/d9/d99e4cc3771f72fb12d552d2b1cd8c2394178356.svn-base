package com.goodkredit.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User-PC on 1/17/2018.
 */

public class ParamountCharges {
    @SerializedName("ChargeID")
    @Expose
    private String ChargeID;
    @SerializedName("PolicyTypeID")
    @Expose
    private String PolicyTypeID;
    @SerializedName("PolicyTypeName")
    @Expose
    private String PolicyTypeName;
    @SerializedName("VehicleTypeID")
    @Expose
    private String VehicleTypeID;
    @SerializedName("ApplicationType")
    @Expose
    private String ApplicationType;
    @SerializedName("VehicleDescription")
    @Expose
    private String VehicleDescription;
    @SerializedName("Amount")
    @Expose
    private double Amount;
    @SerializedName("OtherCharges")
    @Expose
    private double OtherCharges;
    @SerializedName("Status")
    @Expose
    private String Status;

    public ParamountCharges(String chargeID, String vehicleDescription) {
        ChargeID = chargeID;
        VehicleDescription = vehicleDescription;
    }

    public ParamountCharges(String chargeID, String policyTypeID, String policyTypeName, String vehicleTypeID, String applicationType, String vehicleDescription, double amount, double otherCharges, String status) {
        ChargeID = chargeID;
        PolicyTypeID = policyTypeID;
        PolicyTypeName = policyTypeName;
        VehicleTypeID = vehicleTypeID;
        ApplicationType = applicationType;
        VehicleDescription = vehicleDescription;
        Amount = amount;
        OtherCharges = otherCharges;
        Status = status;
    }

    public String getChargeID() {
        return ChargeID;
    }

    public String getPolicyTypeID() {
        return PolicyTypeID;
    }

    public String getPolicyTypeName() {
        return PolicyTypeName;
    }

    public String getVehicleTypeID() {
        return VehicleTypeID;
    }

    public String getApplicationType() {
        return ApplicationType;
    }

    public String getVehicleDescription() {
        return VehicleDescription;
    }

    public double getAmount() {
        return Amount;
    }

    public double getOtherCharges() {
        return OtherCharges;
    }

    public String getStatus() {
        return Status;
    }
}
