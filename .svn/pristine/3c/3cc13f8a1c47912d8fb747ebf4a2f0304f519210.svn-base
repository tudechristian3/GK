package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TransferVoucherV2API {

    /*************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    @FormUrlEncoded
    @POST("wsb92V2")
    Call<GenericResponse> validateRecipientSubscriberV2(@Header("authenticationid") String authenticationid,
                                                        @Header("sessionid") String sessionid,
                                                        @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb197")
    Call<GenericResponse> validateSubscriberVoucher (@Header("authenticationid") String authenticationid,
                                                       @Header("sessionid") String sessionid,
                                                       @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb129V2")
    Call<GenericResponse> transferVoucherToSubscriberV3(@Header("authenticationid") String authenticationid,
                                                        @Header("sessionid") String sessionid,
                                                        @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

}
