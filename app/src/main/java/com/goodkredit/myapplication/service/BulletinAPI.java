package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GKBulletinResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BulletinAPI {
    @FormUrlEncoded
    @POST("getMiniAppBulletin")
    Call<GKBulletinResponse> getMiniAppBulletin(@Field("sessionid") String sessionid,
                                                @Field("imei") String imei,
                                                @Field("userid") String userid,
                                                @Field("authcode") String authcode,
                                                @Field("borrowerid") String borrowerid,
                                                @Field("schoolid") String schoolid,
                                                @Field("servicecode") String servicecode);
}
