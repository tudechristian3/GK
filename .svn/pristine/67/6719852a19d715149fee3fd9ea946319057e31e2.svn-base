package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.model.gkearn.GKEarnTopUpHistory;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.gkearn.GKEarnConversionPointsResponse;
import com.goodkredit.myapplication.responses.gkearn.GKEarnPackagesResponse;
import com.goodkredit.myapplication.responses.gkearn.GKEarnSubscribersResponse;
import com.goodkredit.myapplication.responses.gkearn.GKEarnTopUpHistoryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GKEarnAPI {
    @FormUrlEncoded
    @POST("getEarnSubscribers")
    Call<GKEarnSubscribersResponse> getEarnSubscribers(@Field("sessionid") String sessionid,
                                                       @Field("imei") String imei,
                                                       @Field("userid") String userid,
                                                       @Field("borrowerid") String borrowerid,
                                                       @Field("authcode") String authcode);


    @FormUrlEncoded
    @POST("getEarnConversionPoints")
    Call<GKEarnConversionPointsResponse> getEarnConversionPoints(@Field("sessionid") String sessionid,
                                                                 @Field("imei") String imei,
                                                                 @Field("userid") String userid,
                                                                 @Field("borrowerid") String borrowerid,
                                                                 @Field("authcode") String authcode,
                                                                 @Field("year") String year,
                                                                 @Field("limit") int limit);

    @FormUrlEncoded
    @POST("convertGkEarnPoints")
    Call<GenericResponse> convertGkEarnPoints(@Field("sessionid") String sessionid,
                                              @Field("imei") String imei,
                                              @Field("userid") String userid,
                                              @Field("authcode") String authcode,
                                              @Field("borrowerid") String borrowerid,
                                              @Field("borrowername") String borrowername,
                                              @Field("mobileno") String mobileno,
                                              @Field("vouchers") String vouchers,
                                              @Field("servicetype") String servicetype);

    @FormUrlEncoded
    @POST("registerGKEarn")
    Call<GenericResponse> registerGKEarnCall(@Field("sessionid") String sessionid,
                                             @Field("imei") String imei,
                                             @Field("userid") String userid,
                                             @Field("borrowerid") String borrowerid,
                                             @Field("borrowername") String borrowername,
                                             @Field("authcode") String authcode,
                                             @Field("referralcode") String referralcode,
                                             @Field("servicetype") String servicetype,
                                             @Field("grossamount") String grossamount,
                                             @Field("vouchersession") String vouchersession,
                                             @Field("servicecode") String servicecode,
                                             @Field("merchantid") String merchantid,
                                             @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("getGKEarnPackages")
    Call<GKEarnPackagesResponse> getGKEarnPackages(@Field("sessionid") String sessionid,
                                                   @Field("imei") String imei,
                                                   @Field("userid") String userid,
                                                   @Field("borrowerid") String borrowerid,
                                                   @Field("authcode") String authcode);

    @FormUrlEncoded
    @POST("registerGKEarnStockist")
    Call<GenericResponse> registerGKEarnStockist(@Field("sessionid") String sessionid,
                                                 @Field("imei") String imei,
                                                 @Field("userid") String userid,
                                                 @Field("borrowerid") String borrowerid,
                                                 @Field("borrowername") String borrowername,
                                                 @Field("authcode") String authcode,
                                                 @Field("vouchersession") String vouchersession,
                                                 @Field("merchantid") String merchantid,
                                                 @Field("servicecode") String servicecode,
                                                 @Field("packageid") String packageid,
                                                 @Field("grossamount") String grossamount,
                                                 @Field("customersc") String customersc,
                                                 @Field("paymenttype") String paymenttype,
                                                 @Field("paymentoption") String paymentoption);

    @FormUrlEncoded
    @POST("validateIfEarnHasPendingPaymentRequest")
    Call<GenericResponse> validateIfEarnHasPendingPaymentRequest(@Field("sessionid") String sessionid,
                                                                 @Field("imei") String imei,
                                                                 @Field("userid") String userid,
                                                                 @Field("borrowerid") String borrowerid,
                                                                 @Field("authcode") String authcode,
                                                                 @Field("earntype") String earntype,
                                                                 @Field("devicetype") String devicetype);


    @FormUrlEncoded
    @POST("checkEarnEGHLTransactions")
    Call<GenericResponse> checkEarnEGHLTransactions(@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("servicecode") String servicecode,
                                                    @Field("ordertxnno") String ordertxnno,
                                                    @Field("paymentno") String paymentno,
                                                    @Field("paymenttype") String paymenttype,
                                                    @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("getGKEarnTopUpHistory")
    Call<GKEarnTopUpHistoryResponse> getGKEarnTopUpHistoryCall(@Field("sessionid") String sessionid,
                                                               @Field("imei") String imei,
                                                               @Field("userid") String userid,
                                                               @Field("borrowerid") String borrowerid,
                                                               @Field("authcode") String authcode,
                                                               @Field("year") String year,
                                                               @Field("month") String month,
                                                               @Field("limit") int limit,
                                                               @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("cancelEarnRequest")
    Call<GenericResponse> cancelEarnRequest(@Field("sessionid") String sessionid,
                                            @Field("imei") String imei,
                                            @Field("userid") String userid,
                                            @Field("borrowerid") String borrowerid,
                                            @Field("authcode") String authcode,
                                            @Field("txnno") String txnno,
                                            @Field("remarks") String remarks,
                                            @Field("earntype") String earntype,
                                            @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("stockistTopUP")
    Call<GenericResponse> stockistTopUPCall(@Field("sessionid") String sessionid,
                                            @Field("imei") String imei,
                                            @Field("userid") String userid,
                                            @Field("borrowerid") String borrowerid,
                                            @Field("authcode") String authcode,
                                            @Field("borrowername") String borrowername,
                                            @Field("merchantid") String merchantid,
                                            @Field("servicecode") String servicecode,
                                            @Field("grossamount") String grossamount,
                                            @Field("customersc") String customersc,
                                            @Field("paymenttype") String paymenttype,
                                            @Field("paymentoption") String paymentoption,
                                            @Field("devicetype") String devicetype);


    @FormUrlEncoded
    @POST("getGKEarnRegistrationFee")
    Call<GenericResponse> getGKEarnRegistrationFeeCall(@Field("sessionid") String sessionid,
                                            @Field("imei") String imei,
                                            @Field("userid") String userid,
                                            @Field("borrowerid") String borrowerid,
                                            @Field("authcode") String authcode,
                                            @Field("servicetype") String servicetype,
                                            @Field("devicetype") String devicetype);


    @FormUrlEncoded
    @POST("validateGKEarnReferralCode")
    Call<GenericResponse> validateGKEarnReferralCode(@Field("sessionid") String sessionid,
                                                       @Field("imei") String imei,
                                                       @Field("userid") String userid,
                                                       @Field("borrowerid") String borrowerid,
                                                       @Field("authcode") String authcode,
                                                       @Field("referralcode") String referralcode,
                                                       @Field("devicetype") String devicetype);


}
