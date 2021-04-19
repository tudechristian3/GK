package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.GetMerchantPromosResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetMerchantPromosAPI {
    @FormUrlEncoded
    @POST("getMerchantPromos")
    Call<GetMerchantPromosResponse> getMerchantPromosCall(@Field("sessionid") String sessionid,
                                                          @Field("imei") String imei,
                                                          @Field("authcode") String authcode,
                                                          @Field("userid") String userid,
                                                          @Field("borrowerid") String borrowerid,
                                                          @Field("merchantid") String merchantid);

}
