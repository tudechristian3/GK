package com.goodkredit.myapplication.service;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PrePurchaseAPI {
    @FormUrlEncoded
    @POST("prePurchase")
    Call<String> prePurchaseCall(@Field("borrowerid") String borrowerid,
                                 @Field("amountpurchase") String amount,
                                 @Field("userid") String userid,
                                 @Field("imei") String imei,
                                 @Field("sessionid") String sessionid,
                                 @Field("authcode") String authcode);
}
