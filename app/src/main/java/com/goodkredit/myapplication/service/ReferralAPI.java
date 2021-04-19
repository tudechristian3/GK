package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.referafriend.GenerateReferralCodeResponse;
import com.goodkredit.myapplication.responses.referafriend.ProcessReferralResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ReferralAPI {

    @FormUrlEncoded
    @POST("generateReferralCode")
    Call<GenerateReferralCodeResponse> generateReferralCode(
            @Field("imei") String imei,
            @Field("userid") String userid,
            @Field("sessionid") String sessionid,
            @Field("authcode") String authcode,
            @Field("borrowerid") String borrowerid,
            @Field("generationtype") String generationtype,
            @Field("devicetype") String devicetype
    );

    @FormUrlEncoded
    @POST("validateReferralIfAvailable")
    Call<GenericResponse> validateReferralIfAvailable(
            @Field("imei") String imei,
            @Field("userid") String userid,
            @Field("sessionid") String sessionid,
            @Field("authcode") String authcode,
            @Field("borrowerid") String borrowerid,
            @Field("devicetype") String devicetype
    );

    @FormUrlEncoded
    @POST("processReferral")
    Call<ProcessReferralResponse> processReferral(
            @Field("imei") String imei,
            @Field("userid") String userid,
            @Field("sessionid") String sessionid,
            @Field("authcode") String authcode,
            @Field("borrowerid") String borrowerid,
            @Field("borrowername") String borrowername,
            @Field("referralcode") String referralcode,
            @Field("devicetype") String devicetype
    );

    @FormUrlEncoded
    @POST("customizeReferralCode")
    Call<GenericResponse> customizeReferralCode(
            @Field("imei") String imei,
            @Field("userid") String userid,
            @Field("sessionid") String sessionid,
            @Field("authcode") String authcode,
            @Field("borrowerid") String borrowerid,
            @Field("referralcode") String referralcode,
            @Field("devicetype") String devicetype
    );

    @FormUrlEncoded
    @POST("generateResellerReferralCode")
    Call<GenerateReferralCodeResponse> generateResellerReferralCode(
            @Field("imei") String imei,
            @Field("userid") String userid,
            @Field("sessionid") String sessionid,
            @Field("authcode") String authcode,
            @Field("borrowerid") String borrowerid,
            @Field("devicetype") String devicetype
    );

    @FormUrlEncoded
    @POST("validateResellerReferralCode")
    Call<GenericResponse> validateResellerReferralCode(
            @Field("imei") String imei,
            @Field("userid") String userid,
            @Field("sessionid") String sessionid,
            @Field("authcode") String authcode,
            @Field("borrowerid") String borrowerid,
            @Field("packageid") String packageid,
            @Field("referralcode") String referralcode,
            @Field("devicetype") String devicetype
    );

    @FormUrlEncoded
    @POST("validateResellerReferralIfAvailable")
    Call<GenericResponse> checkIfPromoActive(
            @Field("imei") String imei,
            @Field("userid") String userid,
            @Field("authcode") String authcode,
            @Field("borrowerid") String borrowerid
    );
}
