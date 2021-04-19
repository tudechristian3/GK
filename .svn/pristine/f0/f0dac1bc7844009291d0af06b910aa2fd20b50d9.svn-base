package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CommonV2API {

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * OCTOBER 2019    *
     * *****************/

    @FormUrlEncoded
    @POST("wsb00V2")
    Call<GenericResponse> validateSessionV2 (@Header("authenticationid") String authenticationid,
                                                          @Header("sessionid") String sessionid,
                                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb25V2")
    Call<GenericResponse> calculateDiscountForResellerV3 (@Header("authenticationid") String authenticationid,
                                                          @Header("sessionid") String sessionid,
                                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb24")
    Call<GenericResponse> prePurchaseV2 (@Header("authenticationid") String authenticationid,
                                         @Header("sessionid") String sessionid,
                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb109")
    Call<GenericResponse> processQRConsummationV3 (@Header("authenticationid") String authenticationid,
                                         @Header("sessionid") String sessionid,
                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb110")
    Call<GenericResponse> processConsummationV3 (@Header("authenticationid") String authenticationid,
                                                   @Header("sessionid") String sessionid,
                                                   @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb57")
    Call<GenericResponse> getCustomerBillerServiceChargeV2 (@Header("authenticationid") String authenticationid,
                                                 @Header("sessionid") String sessionid,
                                                 @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb58")
    Call<GenericResponse> checkBillsPaymentTransactionStatus (@Header("authenticationid") String authenticationid,
                                                            @Header("sessionid") String sessionid,
                                                            @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb114")
    Call<GenericResponse> deletePaymentTransactionV2 (@Header("authenticationid") String authenticationid,
                                                              @Header("sessionid") String sessionid,
                                                              @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb78")
    Call<GenericResponse> generateReferralCodeV2 (@Header("authenticationid") String authenticationid,
                                                      @Header("sessionid") String sessionid,
                                                      @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb40")
    Call<GenericResponse> customizeReferralCode ( @Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb141")
    Call<GenericResponse> generateResellerReferralCodeV2 ( @Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb24V2")
    Call<GenericResponse> prePurchaseV3 (@Header("authenticationid") String authenticationid,
                                         @Header("sessionid") String sessionid,
                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    
    @FormUrlEncoded
    @POST("wsb161")
    Call<GenericResponse> getUniversalSupportThreadV2 (@Header("authenticationid") String authenticationid,
                                                       @Header("sessionid") String sessionid,
                                                       @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb162")
    Call<GenericResponse> getUniversalSupportFAQV2 (@Header("authenticationid") String authenticationid,
                                                    @Header("sessionid") String sessionid,
                                                    @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb193")
    Call<GenericResponse> getUniversalSupportConversationV2 (@Header("authenticationid") String authenticationid,
                                                             @Header("sessionid") String sessionid,
                                                             @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb163")
    Call<GenericResponse> sendUniversalSupportMessageV2 (@Header("authenticationid") String authenticationid,
                                                         @Header("sessionid") String sessionid,
                                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb72")
    Call<GenericResponse> calculateServiceChargeV2 (@Header("authenticationid") String authenticationid,
                                                    @Header("sessionid") String sessionid,
                                                    @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

}

