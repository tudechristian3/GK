package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WhatsNewV2API {

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * OCTOBER 2019    *
     * *****************/

    @FormUrlEncoded
    @POST("wsb113")
    Call<GenericResponse> getGKPromotionsV2 (@Header("authenticationid") String authenticationid,
                                                 @Header("sessionid") String sessionid,
                                                 @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb59")
    Call<GenericResponse> getGKNewUpdatesV2 (@Header("authenticationid") String authenticationid,
                                             @Header("sessionid") String sessionid,
                                             @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb60")
    Call<GenericResponse> updateAdViewsV2 (@Header("authenticationid") String authenticationid,
                                             @Header("sessionid") String sessionid,
                                             @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb184")
    Call<GenericResponse> updateAdClicksV2 (@Header("authenticationid") String authenticationid,
                                           @Header("sessionid") String sessionid,
                                           @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
}
