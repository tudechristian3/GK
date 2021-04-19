package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.security.acl.Group;

/**
 * Created by GoodApps on 08/02/2018.
 */

public class BillsPaymentCategories implements Serializable {
    private String billerlogo;
    private String billercode;
    private String category;
    private String billername;
    private String accountno;
    private String serviceprovidercode;
    private String categorygroup;
    private String categorylogo;


    public BillsPaymentCategories() {
        super();
    }

    public BillsPaymentCategories(String billerlogo, String billercode, String billername , String category, String accountno, String serviceprovidercode, String categorygroup, String categorylogo) {
        this.billerlogo = billerlogo;
        this.billercode = billercode;
        this.billername = billername;
        this.category = category;
        this.accountno = accountno;
        this.serviceprovidercode = serviceprovidercode;
        this.categorygroup = categorygroup;
        this.categorylogo = categorylogo;
    }

    public String getBillerLogo() {
        return billerlogo;
    }

    public void setBillerlogo(String billerlogo) {
        this.billerlogo = billerlogo;
    }

    public String getBiller() {
        return billercode;
    }

    public void setBiller(String biller) {
        this.billercode = biller;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBillerName() {
        return billername;
    }

    public void setBillerName(String billername) {
        this.billername = billername;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getCategorygroup() {
        return categorygroup;
    }

    public void setCategorygroup(String categorygroup) {
        this.categorygroup = categorygroup;
    }

    public String getCategorylogo() {
        return categorylogo;
    }

    public void setCategorylogo(String categorylogo) {
        this.categorylogo = categorylogo;
    }

    public String getBillercode() {
        return billercode;
    }

    public void setServiceProviderCode(String serviceprovidercode) {
        this.serviceprovidercode = serviceprovidercode;
    }

    public String getServiceProviderCode() {
        return serviceprovidercode;
    }
}
