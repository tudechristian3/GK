package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DropOffV2API {

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * OCTOBER 2019    *
     * *****************/

    @FormUrlEncoded
    @POST("wsb142")
    Call<GenericResponse> getDropOffPendingOrdersV2 (@Header("authenticationid") String authenticationid,
                                                              @Header("sessionid") String sessionid,
                                                              @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb143")
    Call<GenericResponse> getDropOffPaymentHistoryV2 (@Header("authenticationid") String authenticationid,
                                                     @Header("sessionid") String sessionid,
                                                     @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb144")
    Call<GenericResponse> searchDropOffMerchantV2 (@Header("authenticationid") String authenticationid,
                                                      @Header("sessionid") String sessionid,
                                                      @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb145")
    Call<GenericResponse> processDropOffPayViaPartnerV2 (@Header("authenticationid") String authenticationid,
                                                   @Header("sessionid") String sessionid,
                                                   @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb146")
    Call<GenericResponse> cancelDropOffOrderV2 (@Header("authenticationid") String authenticationid,
                                                         @Header("sessionid") String sessionid,
                                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );


}
