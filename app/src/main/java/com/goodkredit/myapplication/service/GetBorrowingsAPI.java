package com.goodkredit.myapplication.service;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetBorrowingsAPI {
    @FormUrlEncoded
    @POST("getBorrowings")
    Call<String> getBorrowingsCall(@Field("sessionid") String sessionid,
                                         @Field("imei") String imei,
                                         @Field("authcode") String authcode,
                                         @Field("userid") String userid,
                                         @Field("borrowerid") String borrowerid,
                                         @Field("year") String year,
                                         @Field("month") String month);
}
