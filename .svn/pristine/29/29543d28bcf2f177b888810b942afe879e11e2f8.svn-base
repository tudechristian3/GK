package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.prepaidrequest.CalculateEGHLServiceChargeResponse;
import com.goodkredit.myapplication.responses.prepaidrequest.CheckEGHLPaymentTransactionStatusResponse;
import com.goodkredit.myapplication.responses.prepaidrequest.RequestVoucherGenerationResponse;
import com.goodkredit.myapplication.responses.prepaidrequest.RequestVoucherViaEghlPaymentResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RequestVoucherGenerationAPI {
    @FormUrlEncoded
    @POST("requestVoucherGenerationV2")
    Call<RequestVoucherGenerationResponse> requestVoucherGenerationCall(@Field("sessionid") String sessionid,
                                                                        @Field("imei") String imei,
                                                                        @Field("authcode") String authcode,
                                                                        @Field("userid") String userid,
                                                                        @Field("borrowerid") String borrowerid,
                                                                        @Field("borrowername") String borrowername,
                                                                        @Field("borrowermobileno") String borrowermobileno,
                                                                        @Field("vouchers") String vouchers,
                                                                        @Field("servicetype") String servicetype);

    @FormUrlEncoded
    @POST("cancelRequestVoucherViaEghlPayment")
    Call<RequestVoucherViaEghlPaymentResponse> cancelRequestVoucherViaEghlPaymentCall(@Field("sessionid") String sessionid,
                                                                                @Field("imei") String imei,
                                                                                @Field("authcode") String authcode,
                                                                                @Field("userid") String userid,
                                                                                @Field("borrowerid") String borrowerid,
                                                                                @Field("devicetype") String devicetype,
                                                                                @Field("paymenttxnno") String paymenttxnno,
                                                                                @Field("ordertxnno") String ordertxnno);

    @FormUrlEncoded
    @POST("requestVoucherViaEghlPayment")
    Call<RequestVoucherViaEghlPaymentResponse> requestVoucherViaEghlPaymentCall(@Field("sessionid") String sessionid,
                                                                                @Field("imei") String imei,
                                                                                @Field("authcode") String authcode,
                                                                                @Field("userid") String userid,
                                                                                @Field("borrowerid") String borrowerid,
                                                                                @Field("borrowername") String borrowername,
                                                                                @Field("borrowermobileno") String borrowermobileno,
                                                                                @Field("vouchers") String vouchers,
                                                                                @Field("servicetype") String servicetype,
                                                                                @Field("paymentoption") String paymentoption);

    @FormUrlEncoded
    @POST("checkEGHLPaymentTransactionStatus")
    Call<CheckEGHLPaymentTransactionStatusResponse> checkEGHLPaymentTransactionStatusCall(@Field("sessionid") String sessionid,
                                                                                          @Field("imei") String imei,
                                                                                          @Field("authcode") String authcode,
                                                                                          @Field("userid") String userid,
                                                                                          @Field("borrowerid") String borrowerid,
                                                                                          @Field("vouchergenerationid") String vouchergenerationid,
                                                                                          @Field("paymenttxnno") String paymenttxnno);

    @FormUrlEncoded
    @POST("calculateEGHLServiceCharge")
    Call<CalculateEGHLServiceChargeResponse> calculateEGHLServiceChargeCall(@Field("sessionid") String sessionid,
                                                                            @Field("imei") String imei,
                                                                            @Field("borrowerid") String borrowerid,
                                                                            @Field("userid") String userid,
                                                                            @Field("authcode") String authcode,
                                                                            @Field("devicetype") String devicetype,
                                                                            @Field("paymentoption") String paymentoption,
                                                                            @Field("amount") String amount);
}
