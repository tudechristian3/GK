package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.bean.V2VirtualVoucherRequestQueue;
import com.goodkredit.myapplication.model.V2BillPaymentQueue;
import com.goodkredit.myapplication.model.V2PrepaidLoadQueue;
import com.goodkredit.myapplication.model.V2SmartRetailWalletQueue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 7/31/2017.
 */

public class V2GetProcessQueueResponse {

    @SerializedName("loaddata")
    @Expose
    private List<V2PrepaidLoadQueue> prepaidLoadQueueList = new ArrayList<>();
    @SerializedName("billspaydata")
    @Expose
    private List<V2BillPaymentQueue> billsPaymentQueueList = new ArrayList<>();
    @SerializedName("smartreloadqueue")
    @Expose
    private List<V2SmartRetailWalletQueue> smartRetailWalletQueueList = new ArrayList<>();
    @SerializedName("buyvoucherqueue")
    @Expose
    private List<V2VirtualVoucherRequestQueue> virtualVoucherRequest = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<V2PrepaidLoadQueue> getPrepaidLoadQueueList() {
        return prepaidLoadQueueList;
    }

    public List<V2BillPaymentQueue> getBillsPaymentQueueList() {
        return billsPaymentQueueList;
    }

    public List<V2SmartRetailWalletQueue> getSmartRetailWalletQueueList() {
        return smartRetailWalletQueueList;
    }

    public List<V2VirtualVoucherRequestQueue> getVirtualVoucherRequest() {
        return virtualVoucherRequest;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
//public class V2GetProcessQueueResponse {
//
//    @SerializedName("loaddata")
//    @Expose
//    private String loaddata;
//    @SerializedName("billspaydata")
//    @Expose
//    private String billspaydata;
//    @SerializedName("smartreloadqueue")
//    @Expose
//    private String smartreloadqueue;
//    @SerializedName("buyvoucherqueue")
//    @Expose
//    private String buyvoucherqueue;
//    @SerializedName("status")
//    @Expose
//    private String status;
//    @SerializedName("message")
//    @Expose
//    private String message;
//
//
//    public String getStatus() {
//        return status;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public String getLoaddata() {
//        return loaddata;
//    }
//
//    public String getBillspaydata() {
//        return billspaydata;
//    }
//
//    public String getSmartreloadqueue() {
//        return smartreloadqueue;
//    }
//
//    public String getBuyvoucherqueue() {
//        return buyvoucherqueue;
//    }
//}