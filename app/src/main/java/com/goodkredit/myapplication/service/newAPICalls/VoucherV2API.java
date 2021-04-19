package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface VoucherV2API {

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * OCTOBER 2019    *
     * *****************/

    @FormUrlEncoded
    @POST("wsb85")
    Call<GenericResponse> getVouchersV3 (@Header("authenticationid") String authenticationid,
                                         @Header("sessionid") String sessionid,
                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb124")
    Call<GenericResponse> getVirtualVoucherProductV4 (@Header("authenticationid") String authenticationid,
                                         @Header("sessionid") String sessionid,
                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb126")
    Call<GenericResponse> requestVoucherGenerationV2 (@Header("authenticationid") String authenticationid,
                                                      @Header("sessionid") String sessionid,
                                                      @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb73")
    Call<GenericResponse> calculateEGHLServiceChargeV2 (@Header("authenticationid") String authenticationid,
                                                      @Header("sessionid") String sessionid,
                                                      @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb130")
    Call<GenericResponse> requestVoucherViaEghlPaymentV2 (@Header("authenticationid") String authenticationid,
                                                        @Header("sessionid") String sessionid,
                                                        @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb131")
    Call<GenericResponse> checkEGHLPaymentTransactionStatusV2 (@Header("authenticationid") String authenticationid,
                                                          @Header("sessionid") String sessionid,
                                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb61")
    Call<GenericResponse> groupVoucherV2 (@Header("authenticationid") String authenticationid,
                                                               @Header("sessionid") String sessionid,
                                                               @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb62")
    Call<GenericResponse> unGroupIndividualVoucherV2 (@Header("authenticationid") String authenticationid,
                                          @Header("sessionid") String sessionid,
                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb63")
    Call<GenericResponse> renameGroupV2 (@Header("authenticationid") String authenticationid,
                                                      @Header("sessionid") String sessionid,
                                                      @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb64")
    Call<GenericResponse> regenerateGroupVoucherPINV2 (@Header("authenticationid") String authenticationid,
                                         @Header("sessionid") String sessionid,
                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb65")
    Call<GenericResponse> generateNewPinV2 (@Header("authenticationid") String authenticationid,
                                                       @Header("sessionid") String sessionid,
                                                       @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb66")
    Call<GenericResponse> getVoucherDetailsLogsV2 (@Header("authenticationid") String authenticationid,
                                            @Header("sessionid") String sessionid,
                                            @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb92")
    Call<GenericResponse> validateRecipientSubscriber (@Header("authenticationid") String authenticationid,
                                                   @Header("sessionid") String sessionid,
                                                   @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb129")
    Call<GenericResponse> transferVoucherToSubscriberV2 (@Header("authenticationid") String authenticationid,
                                                       @Header("sessionid") String sessionid,
                                                       @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb182")
    Call<GenericResponse> cancelRequestVoucherViaEghlPaymentV2 (@Header("authenticationid") String authenticationid,
                                                         @Header("sessionid") String sessionid,
                                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
}
