package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CoopAssistantV2API {

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    @FormUrlEncoded
    @POST("wsb81")
    Call<GenericResponse> getCoopandMembersInformationV2(@Header("authenticationid") String authenticationid,
                                                         @Header("sessionid") String sessionid,
                                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb82")
    Call<GenericResponse> getCOOPSOAV2(@Header("authenticationid") String authenticationid,
                                                         @Header("sessionid") String sessionid,
                                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb83")
    Call<GenericResponse> referCoopToAFriendV2(@Header("authenticationid") String authenticationid,
                                          @Header("sessionid") String sessionid,
                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb84")
    Call<GenericResponse> getCoopPaymentV2(@Header("authenticationid") String authenticationid,
                                               @Header("sessionid") String sessionid,
                                               @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb86")
    Call<GenericResponse> cancelCoopMembershipRequestV2(@Header("authenticationid") String authenticationid,
                                           @Header("sessionid") String sessionid,
                                           @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb168")
    Call<GenericResponse> applyCoopasmemberV2(@Header("authenticationid") String authenticationid,
                                       @Header("sessionid") String sessionid,
                                       @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb169")
    Call<GenericResponse> applyCoopLoanV2(@Header("authenticationid") String authenticationid,
                                              @Header("sessionid") String sessionid,
                                              @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb170")
    Call<GenericResponse> addCoopAccountWalletV2(@Header("authenticationid") String authenticationid,
                                          @Header("sessionid") String sessionid,
                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb172")
    Call<GenericResponse> getCoopLoanTransactionV2(@Header("authenticationid") String authenticationid,
                                                            @Header("sessionid") String sessionid,
                                                            @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb173")
    Call<GenericResponse> validateIfHasPendingLoanRequestV2(@Header("authenticationid") String authenticationid,
                                          @Header("sessionid") String sessionid,
                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb174")
    Call<GenericResponse> cancelCoopLoanRequestV2(@Header("authenticationid") String authenticationid,
                                                            @Header("sessionid") String sessionid,
                                                            @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb194")
    Call<GenericResponse> cancelRequestPaymentsV2(@Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb198")
    Call<GenericResponse> validateIfHasPendingPaymentRequestV2(@Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb199")
    Call<GenericResponse> payCoopSOAV2(@Header("authenticationid") String authenticationid,
                                                               @Header("sessionid") String sessionid,
                                                               @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );


    @FormUrlEncoded
    @POST("wsb205")
    Call<GenericResponse> convertGkEarnPointsV2 (@Header("authenticationid") String authenticationid,
                                                 @Header("sessionid") String sessionid,
                                                 @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb220")
    Call<GenericResponse> payMembershipRequestV2(@Header("authenticationid") String authenticationid,
                                       @Header("sessionid") String sessionid,
                                       @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

}
