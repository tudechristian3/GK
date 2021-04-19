package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.DiscountResponse;
import com.goodkredit.myapplication.responses.GetChargeResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DiscountAPI {
    @FormUrlEncoded
    @POST("calculateDiscountForReseller")
    Call<DiscountResponse> calculateDiscountForReseller(@Field("userid") String userid,
                                                        @Field("imei") String imei,
                                                        @Field("authcode") String authcode,
                                                         @Field("sessionid") String sessionid,
                                                         @Field("borrowerid") String borrowerid,
                                                         @Field("merchantid") String merchantid,
                                                         @Field("amountpaid") double amountpaid,
                                                         @Field("servicecode") String servicecode,
                                                         @Field("longitude") String longitude,
                                                         @Field("latitude") String latitude);
    @FormUrlEncoded
    @POST("calculateServiceCharge")
    Call<GetChargeResponse> calculateServiceCharge(@Field("sessionid") String sessionid,
                                                   @Field("imei") String imei,
                                                   @Field("userid") String userid,
                                                   @Field("authcode") String authcode,
                                                   @Field("borrowerid") String borrowerid,
                                                   @Field("amountpaid") double amountpaid,
                                                   @Field("servicecode") String servicecode,
                                                   @Field("merchantid") String merchantid
                                                   );
}
