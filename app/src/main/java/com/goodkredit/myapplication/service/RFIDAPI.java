package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.rfid.GetRFIDInformationResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RFIDAPI {

    @FormUrlEncoded
    @POST("getRFIDInformationV2")
    Call<GetRFIDInformationResponse> getRFIDInformationCall (@Field("sessionid") String sessionid,
                                                             @Field("imei") String imei,
                                                             @Field("borrowerid") String borrowerid,
                                                             @Field("userid") String userid,
                                                             @Field("authcode") String authcode,
                                                             @Field("limit") String limit,
                                                             @Field("devicetype") String devicetype);
    @FormUrlEncoded
    @POST("updateRFIDStatusV2")
    Call<GenericResponse> updateRFIDStatusCall (@Field("sessionid") String sessionid,
                                                @Field("imei") String imei,
                                                @Field("borrowerid") String borrowerid,
                                                @Field("userid") String userid,
                                                @Field("authcode") String authcode,
                                                @Field("rfidcardno") String rfidcardno,
                                                @Field("rfidstatus") String rfidstatus,
                                                @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("updateRFIDPIN")
    Call<GenericResponse> updateRFIDPINCall (@Field("sessionid") String sessionid,
                                                  @Field("imei") String imei,
                                                  @Field("borrowerid") String borrowerid,
                                                  @Field("userid") String userid,
                                                  @Field("authcode") String authcode,
                                                  @Field("rfidcardno") String rfidcardno,
                                                  @Field("rfidpin") String rfidpin,
                                                  @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("onOffRFIDBalanceLimitV2")
    Call<GenericResponse> onOffRFIDBalanceLimitCall (@Field("sessionid") String sessionid,
                                                     @Field("imei") String imei,
                                                     @Field("borrowerid") String borrowerid,
                                                     @Field("userid") String userid,
                                                     @Field("authcode") String authcode,
                                                     @Field("rfidcardno") String rfidcardno,
                                                     @Field("updatetype") String updatetype,
                                                     @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("updateRFIDBalanceV2")
    Call<GenericResponse> updateRFIDBalanceCall (@Field("sessionid") String sessionid,
                                                  @Field("imei") String imei,
                                                  @Field("borrowerid") String borrowerid,
                                                  @Field("userid") String userid,
                                                  @Field("authcode") String authcode,
                                                  @Field("rfidcardno") String rfidcardno,
                                                  @Field("amount") String amount,
                                                  @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("onOffRFIDWithPIN")
    Call<GenericResponse> onOffRFIDWithPIN (@Field("sessionid") String sessionid,
                                                     @Field("imei") String imei,
                                                     @Field("borrowerid") String borrowerid,
                                                     @Field("userid") String userid,
                                                     @Field("authcode") String authcode,
                                                     @Field("rfidcardno") String rfidcardno,
                                                     @Field("withpin") String withpin,
                                                     @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("updateRFIDWithPIN")
    Call<GenericResponse> updateRFIDWithPIN (@Field("sessionid") String sessionid,
                                                 @Field("imei") String imei,
                                                 @Field("borrowerid") String borrowerid,
                                                 @Field("userid") String userid,
                                                 @Field("authcode") String authcode,
                                                 @Field("rfidcardno") String rfidcardno,
                                                 @Field("rfidpin") String rfidpin,
                                                 @Field("amount") String amount,
                                                 @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("addRFIDBalance")
    Call<GenericResponse> addRFIDBalance(@Field("sessionid") String sessionid,
                                         @Field("imei") String imei,
                                         @Field("userid") String userid,
                                         @Field("borrowerid") String borrowerid,
                                         @Field("borrowername") String borrowername,
                                         @Field("authcode") String authcode,
                                         @Field("merchantid") String merchantid,
                                         @Field("servicecode") String servicecode,
                                         @Field("grossamount") String grossamount,
                                         @Field("vouchercode") String vouchercode,
                                         @Field("voucherserialno") String voucherserialno,
                                         @Field("vouchersession") String vouchersession,
                                         @Field("customersc") String customersc,
                                         @Field("paymentoption") String paymentoption,
                                         @Field("paymenttype") String paymenttype,
                                         @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("validateIfRFIDPendingPaymentRequest")
    Call<GenericResponse> validateIfRFIDPendingPaymentRequest(@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("voucherserialno") String voucherserialno,
                                                    @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("cancelRFIDRequest")
    Call<GenericResponse> cancelRFIDRequest(@Field("sessionid") String sessionid,
                                                              @Field("imei") String imei,
                                                              @Field("userid") String userid,
                                                              @Field("borrowerid") String borrowerid,
                                                              @Field("authcode") String authcode,
                                                              @Field("voucherserialno") String voucherserialno,
                                                              @Field("txnno") String txnno,
                                                              @Field("remarks") String remarks,
                                                              @Field("devicetype") String devicetype);

    //EGHL
    @FormUrlEncoded
    @POST("checkEGHLTransactions")
    Call<GenericResponse> checkEGHLTransactions(@Field("sessionid") String sessionid,
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
    @POST("getMerchantVirtualAssigned")
    Call<GenericResponse> getMerchantVirtualAssigned(@Field("sessionid") String sessionid,
                                                     @Field("imei") String imei,
                                                     @Field("userid") String userid,
                                                     @Field("borrowerid") String borrowerid,
                                                     @Field("authcode") String authcode,
                                                     @Field("type") String type,
                                                     @Field("devicetype") String devicetype);


}
