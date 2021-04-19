package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.ConsummationTransactionDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ConsummationTransactionDetailsAPI {
    @FormUrlEncoded
    @POST("getConsummationTransactionDetails")
    Call<ConsummationTransactionDetailsResponse> getTransactionDetailsCall(@Field("imei") String imei,
                                                                           @Field("sessionid") String sessionid,
                                                                           @Field("borrowerid") String borrowerid,
                                                                           @Field("userid") String userid,
                                                                           @Field("authcode") String authcode,
                                                                           @Field("year") String year,
                                                                           @Field("month") String month,
                                                                           @Field("merchantid") String merchantid,
                                                                           @Field("merchantreferenceno") String merchantreferenceno);
}
