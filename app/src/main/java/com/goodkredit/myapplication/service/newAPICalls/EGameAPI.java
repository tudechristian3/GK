package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.egame.GetEGameProductsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EGameAPI {

    @FormUrlEncoded
    @POST("wsb117")
    Call<GenericResponse> getEGameProductsCall (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb118")
    Call<GenericResponse> getEGameTransactionsCall (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb118V2")
    Call<GenericResponse> getEGameTransactionsV2Call (@Header("authenticationid") String authenticationid,
                                                    @Header("sessionid") String sessionid,
                                                    @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb119")
    Call<GenericResponse> processEGameConsummationCall (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb125")
    Call<GenericResponse> checkEGameTransactionStatusCall (@Header("authenticationid") String authenticationid,
                                                        @Header("sessionid") String sessionid,
                                                        @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
}
