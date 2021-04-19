package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetS3keyResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetLocationResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Ban_Lenovo on 7/15/2017.
 */

public interface CommonApi {

//    @FormUrlEncoded
//    @POST("createRegisteredSession")
//    Call<String> getRegisteredSession(@Field("imei") String imei,
//                                      @Field("userid") String userid);

    @FormUrlEncoded
    @POST("getAccountWalletInfo")
    Call<String> getAccountWalletInfo(@Field("imei") String imei,
                                      @Field("userid") String userid,
                                      @Field("borrowerid") String borrowId,
                                      @Field("sessionid") String sessionId,
                                      @Field("authcode") String authcode);

    @FormUrlEncoded
    @POST("updateProfilePic")
    Call<String> updateProfilePic(@Field("imei") String imei,
                                  @Field("userid") String userid,
                                  @Field("borrowerid") String borrowerId,
                                  @Field("authcode") String authcode,
                                  @Field("sessionid") String sessionId,
                                  @Field("profilepicurl") String profilepicurl);

    @FormUrlEncoded
    @POST("prePurchase")
    Call<String> getVoucherSession(@Field("borrowerid") String borrowerid,
                                   @Field("amountpurchase") String amount,
                                   @Field("userid") String userid,
                                   @Field("imei") String imei,
                                   @Field("sessionid") String sessionid,
                                   @Field("authcode") String authcode);

    @FormUrlEncoded
    @POST("getAmazonCredentials")
    Call<GetS3keyResponse> getAmzCredCall(@Field("imei") String imei,
                                          @Field("authcode") String authcode,
                                          @Field("version") String version,
                                          @Field("type") String type);

    @FormUrlEncoded
    @POST("deletePaymentTransaction")
    Call<String> deletePaymentTransaction(@Field("imei") String imei,
                                          @Field("authcode") String authcode,
                                          @Field("userid") String userid,
                                          @Field("borrowerid") String borrowerId,
                                          @Field("sessionid") String sessionid,
                                          @Field("vouchersession") String vouchersession
    );

    @GET("https://maps.googleapis.com/maps/api/geocode/json")
    Call<GetLocationResponse> getLocationName(@Query("latlng") String latLong,
                                              @Query("key") String apiKey);

}
