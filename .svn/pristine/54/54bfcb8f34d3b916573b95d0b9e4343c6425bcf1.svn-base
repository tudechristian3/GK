package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SubscriberV2API {

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * OCTOBER 2019    *
     * *****************/

    @FormUrlEncoded
    @POST("wsb08")
    Call<GenericResponse> getSubscribersProfile (@Header("authenticationid") String authenticationid,
                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb26")
    Call<GenericResponse> getSubscriberSponsorRequestStatus (@Header("authenticationid") String authenticationid,
                                                 @Header("sessionid") String sessionid,
                                                 @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb27")
    Call<GenericResponse> registerGuarantor (@Header("authenticationid")String authenticationid,
                                                             @Header("sessionid") String sessionid,
                                                             @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb28")
    Call<GenericResponse> updateProfile (@Header("authenticationid")String authenticationid,
                                             @Header("sessionid") String sessionid,
                                             @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb35")
    Call<GenericResponse> getAccountWalletInfo ( @Header("authenticationid") String authenticationid,
                                           @Header("sessionid") String sessionid,
                                           @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb38")
    Call<GenericResponse> updateSpecificProfile ( @Header("authenticationid") String authenticationid,
                                                 @Header("sessionid") String sessionid,
                                                 @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    
    @FormUrlEncoded
    @POST("wsb41")
    Call<GenericResponse> registerToSponsorV2 ( @Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb120")
    Call<GenericResponse> syncSubscribersContacts(@Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

}
