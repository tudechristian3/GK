package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.dropoff.CancelDropOffOrderResponse;
import com.goodkredit.myapplication.responses.dropoff.CancelPaymentViaPartnerResponse;
import com.goodkredit.myapplication.responses.dropoff.GetDropOffPendingOrdersResponse;
import com.goodkredit.myapplication.responses.dropoff.SearchDropOffMerchantsResponse;
import com.goodkredit.myapplication.responses.dropoff.GetPaymentRequestCompletedResponse;
import com.goodkredit.myapplication.responses.dropoff.GetPaymentRequestPendingResponse;
import com.goodkredit.myapplication.responses.dropoff.ProcessDropOffPayViaPartnerResponse;
import com.goodkredit.myapplication.responses.dropoff.ProcessGKDropOffConsummationResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DropOffAPI {

    @FormUrlEncoded
    @POST("getMDPPendingOrders")
    Call<GetPaymentRequestPendingResponse> getPaymentRequestPendingCall(@Field("sessionid") String sessionid,
                                                                        @Field("imei") String imei,
                                                                        @Field("userid") String userid,
                                                                        @Field("borrowerid") String borrowerid,
                                                                        @Field("authcode") String authcode,
                                                                        @Field("merchantid") String merchantid,
                                                                        @Field("limit") String limit,
                                                                        @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("getMDPPaymentHistory")
    Call<GetPaymentRequestCompletedResponse> getPaymentRequestCompletedCall(@Field("sessionid") String sessionid,
                                                                            @Field("imei") String imei,
                                                                            @Field("userid") String userid,
                                                                            @Field("borrowerid") String borrowerid,
                                                                            @Field("authcode") String authcode,
                                                                            @Field("merchantid") String merchantid,
                                                                            @Field("limit") String limit,
                                                                            @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("processMDPPayViaPartner")
    Call<ProcessDropOffPayViaPartnerResponse> processDropOffPayViaPartnerCall(@Field("sessionid") String sessionid,
                                                                              @Field("imei") String imei,
                                                                              @Field("userid") String userid,
                                                                              @Field("borrowerid") String borrowerid,
                                                                              @Field("authcode") String authcode,
                                                                              @Field("merchantid") String merchantid,
                                                                              @Field("branchid") String branchid,
                                                                              @Field("ordertxnid") String ordertxnid,
                                                                              @Field("gktxnno") String gktxnno,
                                                                              @Field("hasdiscount") String hasdiscount,
                                                                              @Field("servicecode") String servicecode,
                                                                              @Field("grossamount") String grossamount,
                                                                              @Field("longitude") String longitude,
                                                                              @Field("latitude") String latitude);

    @FormUrlEncoded
    @POST("processGKMDPConsummation")
    Call<ProcessGKDropOffConsummationResponse> processGKDropOffConsummationCall(@Field("sessionid") String sessionid,
                                                                                @Field("imei") String imei,
                                                                                @Field("userid") String userid,
                                                                                @Field("borrowerid") String borrowerid,
                                                                                @Field("authcode") String authcode,
                                                                                @Field("merchantid") String merchantid,
                                                                                @Field("branchid") String branchid,
                                                                                @Field("ordertxnid") String ordertxnid,
                                                                                @Field("gktxnno") String gktxnno,
                                                                                @Field("hasdiscount") String hasdiscount,
                                                                                @Field("servicecode") String servicecode,
                                                                                @Field("grossamount") String grossamount,
                                                                                @Field("vouchersession") String vouchersession,
                                                                                @Field("longitude") String longitude,
                                                                                @Field("latitude") String latitude);

    @FormUrlEncoded
    @POST("cancelMDPPaymentViaPartner")
    Call<CancelPaymentViaPartnerResponse> cancelPaymentViaPartnerCall(@Field("sessionid") String sessionid,
                                                                      @Field("imei") String imei,
                                                                      @Field("userid") String userid,
                                                                      @Field("borrowerid") String borrowerid,
                                                                      @Field("authcode") String authcode,
                                                                      @Field("gktxnno") String gktxnno,
                                                                      @Field("devicetype") String devicetype);


    //NOTE: IBALHIN RA NI NAKO ANG MDP AFTER SA DROPOFF THANKS -NESSA

    @FormUrlEncoded
    @POST("searchDropOffMerchant")
    Call<SearchDropOffMerchantsResponse> searchDropOffMerchantsCall (@Field("sessionid") String sessionid,
                                                                      @Field("imei") String imei,
                                                                      @Field("userid") String userid,
                                                                      @Field("borrowerid") String borrowerid,
                                                                      @Field("authcode") String authcode,
                                                                      @Field("merchantid") String merchantid,
                                                                      @Field("merchantname") String merchantname,
                                                                      @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("getDropOffPendingOrders")
    Call<GetDropOffPendingOrdersResponse> getDropOffPendingOrdersCall(@Field("sessionid") String sessionid,
                                                                      @Field("imei") String imei,
                                                                      @Field("userid") String userid,
                                                                      @Field("borrowerid") String borrowerid,
                                                                      @Field("authcode") String authcode,
                                                                      @Field("merchantid") String merchantid,
                                                                      @Field("limit") String limit,
                                                                      @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("getDropOffPaymentHistory")
    Call<GetDropOffPendingOrdersResponse> getDropOffPaymentHistoryCall(@Field("sessionid") String sessionid,
                                                                       @Field("imei") String imei,
                                                                       @Field("userid") String userid,
                                                                       @Field("borrowerid") String borrowerid,
                                                                       @Field("authcode") String authcode,
                                                                       @Field("merchantid") String merchantid,
                                                                       @Field("limit") String limit,
                                                                       @Field("devicetype") String devicetype,
                                                                       @Field("month") String month,
                                                                       @Field("year") String year);

    @FormUrlEncoded
    @POST("processDropOffPayViaPartner")
    Call<GenericResponse> processDropOffPayViaPartner(@Field("sessionid") String sessionid,
                                                      @Field("imei") String imei,
                                                      @Field("userid") String userid,
                                                      @Field("borrowerid") String borrowerid,
                                                      @Field("authcode") String authcode,
                                                      @Field("merchantid") String merchantid,
                                                      @Field("amount") String amount,
                                                      @Field("purpose") String purpose,
                                                      @Field("notes") String notes,
                                                      @Field("referenceno") String referenceno,
                                                      @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("cancelDropOffOrder")
    Call<CancelDropOffOrderResponse> cancelDropOffOrderCall(@Field("sessionid") String sessionid,
                                                            @Field("imei") String imei,
                                                            @Field("userid") String userid,
                                                            @Field("borrowerid") String borrowerid,
                                                            @Field("authcode") String authcode,
                                                            @Field("gktxnno") String gktxnno,
                                                            @Field("devicetype") String devicetype);

}