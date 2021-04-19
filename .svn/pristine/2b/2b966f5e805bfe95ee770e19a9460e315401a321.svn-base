package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.promo.RedeemPromoQRResponse;
import com.goodkredit.myapplication.responses.promo.RedeemedPromoHistoryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Ban_Lenovo on 1/18/2018.
 */

public interface PromoQRCodeAPI {
    @FormUrlEncoded
    @POST("preValidatePromoQRCode")
    Call<GenericResponse> preValidatePromoQRCode(@Field("sessionid") String sessionid,
                                                 @Field("imei") String imei,
                                                 @Field("authcode") String authcode,
                                                 @Field("userid") String userid,
                                                 @Field("borrowerid") String borrowerid,
                                                 @Field("guarantorid") String guarantorid,
                                                 @Field("promoid") String promoid,
                                                 @Field("securitykey") String securitykey);

    @FormUrlEncoded
    @POST("releasePromoReward")
    Call<RedeemPromoQRResponse> releasePromoReward(@Field("sessionid") String sessionid,
                                                   @Field("imei") String imei,
                                                   @Field("authcode") String authcode,
                                                   @Field("userid") String userid,
                                                   @Field("borrowerid") String borrowerid,
                                                   @Field("guarantorid") String guarantorid,
                                                   @Field("promoid") String promoid,
                                                   @Field("gender") String gender,
                                                   @Field("latitude") String latitude,
                                                   @Field("longitude") String longitude,
                                                   @Field("securitykey") String securitykey,
                                                   @Field("userguarantorid") String userguarantorid);

    @FormUrlEncoded
    @POST("getPromoQRHistory")
    Call<RedeemedPromoHistoryResponse> getRedeemedPromoHistory(@Field("sessionid") String sessionid,
                                                               @Field("imei") String imei,
                                                               @Field("authcode") String authcode,
                                                               @Field("userid") String userid,
                                                               @Field("borrowerid") String borrowerid,
                                                               @Field("year") int year,
                                                               @Field("limit") int limit);
}
