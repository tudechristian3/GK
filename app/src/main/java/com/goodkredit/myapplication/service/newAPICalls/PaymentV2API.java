package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PaymentV2API {
    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * OCTOBER 2019    *
     * *****************/

    @FormUrlEncoded
    @POST("wsb93")
    Call<GenericResponse> validateVoucherV2 (@Header("authenticationid") String authenticationid,
                                             @Header("sessionid") String sessionid,
                                             @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb75")
    Call<GenericResponse> getPaymentPartnersV2 (@Header("authenticationid") String authenticationid,
                                             @Header("sessionid") String sessionid,
                                             @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb127")
    Call<GenericResponse> getPaymentPartnerBranchesV2 (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb115")
    Call<GenericResponse> deleteIndividualPaymentV2 (@Header("authenticationid") String authenticationid,
                                                       @Header("sessionid") String sessionid,
                                                       @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
}
