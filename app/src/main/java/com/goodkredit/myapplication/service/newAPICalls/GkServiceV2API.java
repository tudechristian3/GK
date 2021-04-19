package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GkServiceV2API {

    @FormUrlEncoded
    @POST("wsb33v2")
    Call<GenericResponse> getBillers(@Header("authenticationid") String authenticationid,
                                     @Header("sessionid") String sessionid,
                                     @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb34V2")
    Call<GenericResponse> getGoodKreditServicesV2(@Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb116")
    Call<GenericResponse> getBorrowerBillerList(@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb121")
    Call<GenericResponse> addToBorrowerBillerList(@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb122")
    Call<GenericResponse> processBillsPaymentV2(@Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb188V2")
    Call<GenericResponse> getGKServicesMenuV2(@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb189")
    Call<GenericResponse> gkServicesMenuV2(@Header("authenticationid") String authenticationid,
                                              @Header("sessionid") String sessionid,
                                              @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
}
