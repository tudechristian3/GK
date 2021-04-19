package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.GetVoucherDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetVoucherDetailsAPI {
    @FormUrlEncoded
    @POST("getBorrowingsDetails")
    Call<String> getVoucherDetailsCall(@Field("sessionid") String sessionid,
                                                          @Field("imei") String imei,
                                                          @Field("userid") String userid,
                                                          @Field("borrowerid") String borrowerid,
                                                          @Field("authcode") String authcode,
                                                          @Field("year") String year,
                                                          @Field("month") String month,
                                                          @Field("transactionno") String transactionno);
}
