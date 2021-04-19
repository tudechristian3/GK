package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.GetGenerateQr;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GenerateQrAPI {
    @FormUrlEncoded
    @POST("wsb232")
    Call<GetGenerateQr> getGenerateQrCall(@Field("sessionid") String sessionid,
                                          @Field("imei") String imei,
                                          @Field("userid") String userid,
                                          @Field("devicetype") String devicetype,
                                          @Field("index") String index);
}
