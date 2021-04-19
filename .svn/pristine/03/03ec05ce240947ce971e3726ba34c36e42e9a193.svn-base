package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.GetPaymentPartnersResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetPaymentPartnersAPI {
    @FormUrlEncoded
    @POST("getPaymentPartners")
    Call<GetPaymentPartnersResponse> getPaymentPartnersCall(@Field("sessionid") String sessionid,
                                                            @Field("imei") String imei,
                                                            @Field("authcode") String authcode,
                                                            @Field("userid") String userid,
                                                            @Field("borrowerid") String borrowerid,
                                                            @Field("limit") String limit);
}
