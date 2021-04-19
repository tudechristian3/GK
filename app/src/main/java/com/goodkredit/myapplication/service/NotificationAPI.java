package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetNotifications;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Ban on 08/03/2018.
 */

public interface NotificationAPI {

    @FormUrlEncoded
    @POST("getNotifications")
    Call<String> getNotifications(@Field("sessionid") String sessionid,
                                  @Field("imei") String imei,
                                  @Field("userid") String userid,
                                  @Field("borrowerid") String borrowerid,
                                  @Field("authcode") String authcode,
                                  @Field("year") String year,
                                  @Field("month") String month,
                                  @Field("result") String limit);

}
