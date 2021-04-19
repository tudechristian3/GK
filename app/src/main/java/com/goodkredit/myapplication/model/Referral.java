package com.goodkredit.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Referral {

    @SerializedName("ReferralCode")
    @Expose
    private String ReferralCode;
    @SerializedName("ReferralDescription")
    @Expose
    private String ReferralDescription;
    @SerializedName("InvitationMessage")
    @Expose
    private String InvitationMessage;
    @SerializedName("ReferralPromo")
    @Expose
    private String ReferralPromo;

    public String getReferralCode() {
        return ReferralCode;
    }

    public String getReferralDescription() {
        return ReferralDescription;
    }

    public String getInvitationMessage() {
        return InvitationMessage;
    }

    public String getReferralPromo() {
        return ReferralPromo;
    }
}
