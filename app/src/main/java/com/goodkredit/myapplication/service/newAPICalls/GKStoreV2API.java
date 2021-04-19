package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GKStoreV2API {
    @FormUrlEncoded
    @POST("wsb149")
    Call<GenericResponse> getGkStoreBadgeV2(@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb150")
    Call<GenericResponse> getStoreProductsV2(@Header("authenticationid") String authenticationid,
                                            @Header("sessionid") String sessionid,
                                            @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb191")
    Call<GenericResponse> getGkStoreCustomerServiceChargeV2(@Header("authenticationid") String authenticationid,
                                             @Header("sessionid") String sessionid,
                                             @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb151")
    Call<GenericResponse> processGKStoreOrderV4(@Header("authenticationid") String authenticationid,
                                                            @Header("sessionid") String sessionid,
                                                            @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb192")
    Call<GenericResponse> checkGKStoreStatusQueV2(@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb152")
    Call<GenericResponse> insertOrderLogsQueueV2(@Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb152V2")
    Call<GenericResponse> insertOrderLogsQueueV3(@Header("authenticationid") String authenticationid,
                                                 @Header("sessionid") String sessionid,
                                                 @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb153")
    Call<GenericResponse> fetchStoreOrdersListV3(@Header("authenticationid") String authenticationid,
                                                 @Header("sessionid") String sessionid,
                                                 @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb154")
    Call<GenericResponse> gkstoreCancelOrderV2(@Header("authenticationid") String authenticationid,
                                                 @Header("sessionid") String sessionid,
                                                 @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );



}
