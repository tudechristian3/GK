package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PublicSponsorV2API {

    @FormUrlEncoded
    @POST("wsb29")
    Call<GenericResponse> getPublicSponsors (@Header("authenticationid") String authenticationid,
                                          @Header("sessionid") String sessionid,
                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb30")
    Call<GenericResponse> getPublicSponsorPromos (@Header("authenticationid") String authenticationid,
                                             @Header("sessionid") String sessionid,
                                             @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb31")
    Call<GenericResponse> verifySponsorCodeV2 (@Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

}
