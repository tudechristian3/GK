package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoadMessengerV2API {

    @FormUrlEncoded
    @POST("wsb213")
    Call<GenericResponse> getConnectedAccounts (@Header("authenticationid") String authenticationid,
                                          @Header("sessionid") String sessionid,
                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb214")
    Call<GenericResponse> accountConfig (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb215")
    Call<GenericResponse> loadTransactions (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb216")
    Call<GenericResponse> searchAccount (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb217")
    Call<GenericResponse> transactionSummary (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb218")
    Call<GenericResponse> replenishAmount (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb219")
    Call<GenericResponse> replenishLogs (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

}
