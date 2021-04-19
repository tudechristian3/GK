package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetProcessQueueResponseV2;
import com.goodkredit.myapplication.responses.V2GetProcessQueueResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TransactionsV2API {

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * OCTOBER 2019    *
     * *****************/

    @FormUrlEncoded
    @POST("wsb11V2")
    Call<GenericResponse> getConsummation (@Header("authenticationid") String authenticationid,
                                           @Header("sessionid") String sessionid,
                                           @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb12V2")
    Call<GenericResponse> getBorrowings ( @Header("authenticationid") String authenticationid,
                                          @Header("sessionid") String sessionid,
                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb13V2")
    Call<GenericResponse> getTransferedVoucher (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb14V2")
    Call<GenericResponse> getPrepaidLogs (@Header("authenticationid") String authenticationid,
                                          @Header("sessionid") String sessionid,
                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );


    @FormUrlEncoded
    @POST("wsb15V2")
    Call<GenericResponse> sendEmailForBillsPayment (@Header("authenticationid") String authenticationid,
                                                    @Header("sessionid") String sessionid,
                                                    @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb16V2")
    Call<GenericResponse> getBillsPaymentLogs (@Header("authenticationid") String authenticationid,
                                               @Header("sessionid") String sessionid,
                                               @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb17V2")
    Call<GenericResponse> getSmartRetailerLoadingLogs (@Header("authenticationid") String authenticationid,
                                                       @Header("sessionid") String sessionid,
                                                       @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb18V2")
    Call<GenericResponse> getConsummationTransactionDetails (@Header("authenticationid") String authenticationid,
                                                             @Header("sessionid") String sessionid,
                                                             @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb21V2")
    Call<GenericResponse> getProcessQueue (@Header("authenticationid") String authenticationid,
                                                     @Header("sessionid") String sessionid,
                                                     @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param);

    @FormUrlEncoded
    @POST("wsb23V2")
    Call<GenericResponse> getBorrowingsDetails (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb39")
    Call<GenericResponse> sendEmailForBillsPaymentV2 (@Header("authenticationid") String authenticationid,
                                                      @Header("sessionid") String sessionid,
                                                      @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb87V2")
    Call<GenericResponse> getPrepaidTransactionV2 (@Header("authenticationid") String authenticationid,
                                                    @Header("sessionid") String sessionid,
                                                    @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb190")
    Call<GenericResponse> getProcessQueueV2 (@Header("authenticationid") String authenticationid,
                                           @Header("sessionid") String sessionid,
                                           @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );



}
