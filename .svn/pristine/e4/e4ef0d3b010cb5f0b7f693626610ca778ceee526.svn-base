package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetGoodKreditServicesResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GKServicesAPI {

    @FormUrlEncoded
    @POST("getGoodKreditServices")
    Call<GetGoodKreditServicesResponse> getGoodKreditServices(@Field("imei") String imei,
                                                              @Field("userid") String userid,
                                                              @Field("authcode") String authcode,
                                                              @Field("borrowerid") String borrowerid);


    @FormUrlEncoded
    @POST("getGoodKreditServicesV2")
    Call<GetGoodKreditServicesResponse> getGoodKreditServices(@Field("imei") String imei,
                                                              @Field("userid") String userid,
                                                              @Field("authcode") String authcode,
                                                              @Field("sessionid") String sessionid,
                                                              @Field("borrowerid") String borrowerid);

    @FormUrlEncoded
    @POST("gkServicesMenu")
    Call<GenericResponse> gkServicesMenu(@Field("imei") String imei,
                                         @Field("userid") String userid,
                                         @Field("authcode") String authcode,
                                         @Field("sessionid") String sessionid,
                                         @Field("borrowerid") String borrowerid,
                                         @Field("serviceCode") String serviceCode,
                                         @Field("action") String action);
    @FormUrlEncoded
    @POST("getGKServicesMenu")
    Call<GetGoodKreditServicesResponse> getGKServicesMenu(
                                        @Field("imei") String imei,
                                         @Field("userid") String userid,
                                         @Field("authcode") String authcode,
                                         @Field("sessionid") String sessionid,
                                         @Field("borrowerid") String borrowerid);
}
