package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.GetVirtualVoucherProductResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetVirtualVoucherProductAPI {
    @FormUrlEncoded
    @POST("getVirtualVoucherProductsV3")
    Call<GetVirtualVoucherProductResponse> getVirtualVoucherProductCall(@Field("sessionid") String sessionid,
                                                                        @Field("imei") String imei,
                                                                        @Field("authcode") String authcode,
                                                                        @Field("userid") String userid,
                                                                        @Field("borrowerid") String borrowerid,
                                                                        @Field("servicecode") String servicecode,
                                                                        @Field("servicetype") String servicetype,
                                                                        @Field("servicefrom") String servicefrom);
}
