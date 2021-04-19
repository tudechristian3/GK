package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SchoolV2API {
    @FormUrlEncoded
    @POST("wsb155")
    Call<GenericResponse> getSchoolDetailsV2 (@Header("authenticationid") String authenticationid,
                                             @Header("sessionid") String sessionid,
                                             @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb156")
    Call<GenericResponse> getStudentAccountsV2 (@Header("authenticationid") String authenticationid,
                                              @Header("sessionid") String sessionid,
                                              @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb157")
    Call<GenericResponse> getMiniAppConfigV2 (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb158")
    Call<GenericResponse> getStudentGradesV2 (@Header("authenticationid") String authenticationid,
                                              @Header("sessionid") String sessionid,
                                              @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb159")
    Call<GenericResponse> getStudentTuitionFeeV2 (@Header("authenticationid") String authenticationid,
                                              @Header("sessionid") String sessionid,
                                              @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb160")
    Call<GenericResponse> getMiniAppBulletinV2 (@Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb164")
    Call<GenericResponse> checkTuitionFeePaymentStatusV2 (@Header("authenticationid") String authenticationid,
                                                @Header("sessionid") String sessionid,
                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb165")
    Call<GenericResponse> payStudentTuitionFeeV2 (@Header("authenticationid") String authenticationid,
                                                          @Header("sessionid") String sessionid,
                                                          @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb166")
    Call<GenericResponse> processStudentPaymentConsummationV2 (@Header("authenticationid") String authenticationid,
                                                  @Header("sessionid") String sessionid,
                                                  @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb167")
    Call<GenericResponse> getSchoolStudentPaymentLogsV2 (@Header("authenticationid") String authenticationid,
                                                               @Header("sessionid") String sessionid,
                                                               @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb195")
    Call<GenericResponse> getStudentDtrRecordV2 (@Header("authenticationid") String authenticationid,
                                                         @Header("sessionid") String sessionid,
                                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );


}
