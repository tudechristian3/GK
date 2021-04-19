package com.goodkredit.myapplication.bean.prepaidrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EGHLPayment {
    @SerializedName("ordertxnno")
    @Expose
    private String ordertxnno;
    @SerializedName("paymenttxnno")
    @Expose
    private String paymenttxnno;
    @SerializedName("merchantreturnurl")
    @Expose
    private String merchantreturnurl;
    @SerializedName("serviceid")
    @Expose
    private String serviceid;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("merchantname")
    @Expose
    private String merchantname;
    @SerializedName("paymentgateway")
    @Expose
    private String paymentgateway;

    public EGHLPayment(String ordertxnno, String paymenttxnno, String merchantreturnurl, String serviceid, String password, String merchantname, String paymentgateway) {
        this.ordertxnno = ordertxnno;
        this.paymenttxnno = paymenttxnno;
        this.merchantreturnurl = merchantreturnurl;
        this.serviceid = serviceid;
        this.password = password;
        this.merchantname = merchantname;
        this.paymentgateway = paymentgateway;
    }

    public String getOrdertxnno() {
        return ordertxnno;
    }

    public String getPaymenttxnno() {
        return paymenttxnno;
    }

    public String getMerchantReturnurl() {
        return merchantreturnurl;
    }

    public String getServiceid() {
        return serviceid;
    }

    public String getPassword() {
        return password;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public String getPaymentgateway() {
        return paymentgateway;
    }
}
