package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.medpadala.GetMedPadalaTransactionVoucherDetailsResponse;
import com.goodkredit.myapplication.responses.medpadala.GetMedPadalaTransactionsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Ban_Lenovo on 2/19/2018.
 */

public interface MedPadalaAPI {

    @FormUrlEncoded
    @POST("getMedPadalaServiceCharge")
    Call<GenericResponse> getMedPadalaServiceCharge(@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("amount") String amount);

    @FormUrlEncoded
    @POST("processMedPadalaTransaction")
    Call<GenericResponse> processMedPadalaTransaction(@Field("sessionid") String sessionid,
                                                      @Field("imei") String imei,
                                                      @Field("userid") String userid,
                                                      @Field("borrowerid") String borrowerid,
                                                      @Field("authcode") String authcode,
                                                      @Field("amount") String amount,
                                                      @Field("recipientmobileno") String recipientmobileno,
                                                      @Field("vouchersession") String vouchersession);

    @FormUrlEncoded
    @POST("checkMedPadalaTransactionStatus")
    Call<GenericResponse> checkMedPadalaTransactionStatus(@Field("imei") String imei,
                                                          @Field("userid") String userid,
                                                          @Field("borrowerid") String borrowerid,
                                                          @Field("authcode") String authcode,
                                                          @Field("year") String year,
                                                          @Field("vouchersession") String vouchersession);

    @FormUrlEncoded
    @POST("getMedPadalaTransactionHistory")
    Call<GetMedPadalaTransactionsResponse> getHistory(@Field("sessionid") String sessionid,
                                                      @Field("imei") String imei,
                                                      @Field("userid") String userid,
                                                      @Field("borrowerid") String borrowerid,
                                                      @Field("authcode") String authcode,
                                                      @Field("year") int year,
                                                      @Field("limit") int limit);

    @FormUrlEncoded
    @POST("getMedPadalaTransactionVoucherDetails")
    Call<GetMedPadalaTransactionVoucherDetailsResponse> getMedPadalaTransactionVoucherDetails(@Field("sessionid") String sessionid,
                                                                                              @Field("imei") String imei,
                                                                                              @Field("userid") String userid,
                                                                                              @Field("borrowerid") String borrowerid,
                                                                                              @Field("authcode") String authcode,
                                                                                              @Field("transactionnumber") String transactionnumber,
                                                                                              @Field("year") String year,
                                                                                              @Field("month") String month);
}
