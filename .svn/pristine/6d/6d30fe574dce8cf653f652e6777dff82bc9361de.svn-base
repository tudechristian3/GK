package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface GKEarnV2API {
    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * MARCH 2020    *
     * *****************/
    @FormUrlEncoded
    @POST("wsb200")
    Call<GenericResponse> getEarnSubscribersV2 (@Header("authenticationid") String authenticationid,
                                                    @Header("sessionid") String sessionid,
                                                    @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb201")
    Call<GenericResponse> getEarnConversionPointsV2 (@Header("authenticationid") String authenticationid,
                                                   @Header("sessionid") String sessionid,
                                                   @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb203")
    Call<GenericResponse> getGKEarnTopUpHistoryV2 (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb204")
    Call<GenericResponse> validateGKEarnReferralCodeV2 (@Header("authenticationid") String authenticationid,
                                                   @Header("sessionid") String sessionid,
                                                   @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb207")
    Call<GenericResponse> getGKEarnRegistrationFeeV2 (@Header("authenticationid") String authenticationid,
                                                        @Header("sessionid") String sessionid,
                                                        @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb210")
    Call<GenericResponse> registerGKEarnV2 (@Header("authenticationid") String authenticationid,
                                                        @Header("sessionid") String sessionid,
                                                        @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

}
