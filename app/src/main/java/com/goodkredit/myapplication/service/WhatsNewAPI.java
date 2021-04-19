package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.whatsnew.GetGKAdsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WhatsNewAPI {

    @FormUrlEncoded
    @POST("getGKPromotions")
    Call<GetGKAdsResponse> getGKPromotions(@Field("imei") String imei,
                                           @Field("userid") String userid,
                                           @Field("authcode") String authcode,
                                           @Field("borrowerid") String borrowerid,
                                           @Field("type") String type,
                                           @Field("devicetype") String devicetype,
                                           @Field("longitude") double longitude,
                                           @Field("latitude") double latitude
    );

    @FormUrlEncoded
    @POST("getGKNewUpdates")
    Call<GetGKAdsResponse> getGKNewUpdates(@Field("imei") String imei,
                                           @Field("userid") String userid,
                                           @Field("authcode") String authcode,
                                           @Field("borrowerid") String borrowerid,
                                           @Field("type") String type,
                                           @Field("devicetype") String devicetype,
                                           @Field("longitude") double longitude,
                                           @Field("latitude") double latitude
    );

    @FormUrlEncoded
    @POST("updateAdViews")
    Call<GenericResponse> updateAdViews(@Field("imei") String imei,
                                        @Field("userid") String userid,
                                        @Field("authcode") String authcode,
                                        @Field("borrowerid") String borrowerid,
                                        @Field("adID") String adID,
                                        @Field("devicetype") String devicetype
    );

    @FormUrlEncoded
    @POST("updateAdClicks")
    Call<GenericResponse> updateAdClicks(@Field("imei") String imei,
                                         @Field("userid") String userid,
                                         @Field("authcode") String authcode,
                                         @Field("borrowerid") String borrowerid,
                                         @Field("adID") String adID,
                                         @Field("devicetype") String devicetype
    );
}
