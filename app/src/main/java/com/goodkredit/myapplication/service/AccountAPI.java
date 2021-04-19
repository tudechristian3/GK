package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericObjectResponse;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetVoucherResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccountAPI {

    @FormUrlEncoded
    @POST("verifymobileV2")
    Call<GenericObjectResponse> verifyMobileLoginCall(@Field("code") String code,
                                                      @Field("mobile") String mobile,
                                                      @Field("imei") String imei,
                                                      @Field("auth") String auth,
                                                      @Field("authcode") String authcode,
                                                      @Field("from") String from,
                                                      @Field("isalwayssignin") int isalwayssignin );

    @FormUrlEncoded
    @POST("partialRegisterV2")
    Call<GenericResponse> partialRegisterV2Call  (@Field("mobile") String mobile,
                                                      @Field("countrycode") String countrycode,
                                                      @Field("imei") String imei,
                                                      @Field("authcode") String authcode,
                                                      @Field("from") String from);

    @FormUrlEncoded
    @POST("validateSession")
    Call<GenericResponse> validateSessionCall  (@Field("sessionid") String sessionid,
                                                  @Field("imei") String imei,
                                                  @Field("borrowerid") String borrowerid,
                                                  @Field("userid") String userid,
                                                  @Field("devicetype") String devicetype);
}
