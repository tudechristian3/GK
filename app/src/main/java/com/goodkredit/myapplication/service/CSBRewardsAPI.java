package com.goodkredit.myapplication.service;

/**
 * Created by Ban_Lenovo on 12/7/2017.
 */

import com.goodkredit.myapplication.responses.GKProcessRequestResponse;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.csb.GetCSBLogsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CSBRewardsAPI {

    @FormUrlEncoded
    @POST("getCSBRewards")
    Call<GenericResponse> getCSBRewards(@Field("imei") String imei,
                                        @Field("userid") String userid,
                                        @Field("authcode") String authcode,
                                        @Field("sessionid") String sessionid,
                                        @Field("borrowerid") String borrowerid,
                                        @Field("mobileno") String mobileno
    );

    @FormUrlEncoded
    @POST("redeemCSBRewards")
    Call<GenericResponse> redeemCSBRewards(@Field("imei") String imei,
                                           @Field("userid") String userid,
                                           @Field("authcode") String authcode,
                                           @Field("sessionid") String sessionid,
                                           @Field("borrowerid") String borrowerid,
                                           @Field("mobileno") String mobileno,
                                           @Field("vouchers") String vouchers,
                                           @Field("servicetype") String servicetype
    );

    @FormUrlEncoded
    @POST("getCSBLogs")
    Call<GetCSBLogsResponse> getCSBLogs(@Field("imei") String imei,
                                        @Field("userid") String userid,
                                        @Field("authcode") String authcode,
                                        @Field("sessionid") String sessionid,
                                        @Field("borrowerid") String borrowerid,
                                        @Field("year") int year,
                                        @Field("month") int month,
                                        @Field("limit") int limit
    );

    @FormUrlEncoded
    @POST("requestCSBChangeMobile")
    Call<GenericResponse> requestCSBChangeMobile(@Field("imei") String imei,
                                                 @Field("userid") String userid,
                                                 @Field("authcode") String authcode,
                                                 @Field("sessionid") String sessionid,
                                                 @Field("borrowerid") String borrowerid,
                                                 @Field("newmobileno") String newmobileno,
                                                 @Field("borrowername") String borrowername,
                                                 @Field("borroweremail") String borroweremail
    );

    @FormUrlEncoded
    @POST("validateCSBChangeMobile")
    Call<GenericResponse> validateCSBChangeMobile(@Field("imei") String imei,
                                                  @Field("userid") String userid,
                                                  @Field("authcode") String authcode,
                                                  @Field("sessionid") String sessionid,
                                                  @Field("borrowerid") String borrowerid,
                                                  @Field("newmobileno") String newmobileno,
                                                  @Field("verificationcode") String verificationcode,
                                                  @Field("borrowername") String borrowername,
                                                  @Field("borroweremail") String borroweremail
    );

    @FormUrlEncoded
    @POST("resendCSBChangeNoVerificationCode")
    Call<GenericResponse> resendCSBChangeNoVerificationCode(@Field("imei") String imei,
                                                            @Field("userid") String userid,
                                                            @Field("authcode") String authcode,
                                                            @Field("sessionid") String sessionid,
                                                            @Field("borrowerid") String borrowerid,
                                                            @Field("newmobileno") String newmobileno
    );

    @FormUrlEncoded
    @POST("checkCSBChangeMobileRequest")
    Call<GKProcessRequestResponse> checkCSBChangeMobileRequest(@Field("imei") String imei,
                                                               @Field("userid") String userid,
                                                               @Field("requesttype") String requesttype,
                                                               @Field("borrowerid") String borrowerid
    );

    @FormUrlEncoded
    @POST("preSimCsbActivation")
    Call<GenericResponse> preSimCSBActivationCall (@Field("sessionid") String sessionid,
                                                   @Field("imei") String imei,
                                                   @Field("userid") String userid,
                                                   @Field("borrowerid") String borrowerid,
                                                   @Field("authcode") String authcode,
                                                   @Field("mobileno") String mobileno,
                                                   @Field("devicetype") String devicetype
    );

    @FormUrlEncoded
    @POST("activateCsbSimCard")
    Call<GenericResponse> activateCSBSimCall(@Field("sessionid") String sessionid,
                                             @Field("imei") String imei,
                                             @Field("userid") String userid,
                                             @Field("borrowerid") String borrowerid,
                                             @Field("authcode") String authcode,
                                             @Field("mobileno") String mobileno,
                                             @Field("authid") String authid,
                                             @Field("otp") String otp,
                                             @Field("devicetype") String devicetype
    );

}
