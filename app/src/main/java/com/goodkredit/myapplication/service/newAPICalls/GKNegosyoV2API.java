package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GKNegosyoV2API {

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * OCTOBER 2019    *
     * *****************/
    @FormUrlEncoded
    @POST("wsb134")
    Call<GenericResponse> getResellerInformationV2 (@Header("authenticationid") String authenticationid,
                                                          @Header("sessionid") String sessionid,
                                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb135")
    Call<GenericResponse> getDistributorsV2 (@Header("authenticationid") String authenticationid,
                                                     @Header("sessionid") String sessionid,
                                                     @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb136")
    Call<GenericResponse> applyGKNegosyoPackagesV2 (@Header("authenticationid") String authenticationid,
                                                     @Header("sessionid") String sessionid,
                                                     @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb137")
    Call<GenericResponse> processGKNegosyoConsummationv2 (@Header("authenticationid") String authenticationid,
                                                    @Header("sessionid") String sessionid,
                                                    @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb138")
    Call<GenericResponse> cancelPackageApplicationV2 (@Header("authenticationid") String authenticationid,
                                                          @Header("sessionid") String sessionid,
                                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );



    @FormUrlEncoded
    @POST("wsb185")
    Call<GenericResponse> getGKNegosyoPackagesV2 (@Header("authenticationid") String authenticationid,
                                                    @Header("sessionid") String sessionid,
                                                    @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb186")
    Call<GenericResponse> getGKNegosyoTransactionV2 (@Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
}
