package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.GetPrepaidLogsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetPrepaidLogsAPI {

    @FormUrlEncoded
    @POST("getPrepaidLogs")
    Call<GetPrepaidLogsResponse> getPrepaidLogsCall(@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("authcode") String authcode,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("limit") String limit,
                                                    @Field("year") String year,
                                                    @Field("month") String month);

    @FormUrlEncoded
    @POST("getSmartRetailerLoadingLogs")
    Call<GetPrepaidLogsResponse> getSmartRetailerLoadingLogsCall(@Field("sessionid") String sessionid,
                                                                 @Field("imei") String imei,
                                                                 @Field("authcode") String authcode,
                                                                 @Field("userid") String userid,
                                                                 @Field("borrowerid") String borrowerid,
                                                                 @Field("limit") String limit,
                                                                 @Field("year") String year,
                                                                 @Field("month") String month);
}
