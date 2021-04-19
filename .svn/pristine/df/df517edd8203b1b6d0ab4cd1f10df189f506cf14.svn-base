package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GetMerchantPayExpress;
import com.goodkredit.myapplication.responses.GetValidMerchantPayExpressResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Ban_Lenovo on 11/16/2017.
 */

public interface PayByQRCodeApi {

    @FormUrlEncoded
    @POST("validateQrPartial")
    Call<GetMerchantPayExpress> validateQRCode(@Field("sessionid") String sessionid,
                                               @Field("imei") String imei,
                                               @Field("authcode") String authcode,
                                               @Field("userid") String userid,
                                               @Field("borrowerid") String borrowerid,
                                               @Field("merchantid") String merchantid,
                                               @Field("branchid") String branchid,
                                               @Field("securitykey") String securitykey);

    @FormUrlEncoded
    @POST("validateQrDataV2")
    Call<GetValidMerchantPayExpressResponse> validateQrData(@Field("sessionid") String sessionid,
                                                            @Field("imei") String imei,
                                                            @Field("authcode") String authcode,
                                                            @Field("userid") String userid,
                                                            @Field("borrowerid") String borrowerid,
                                                            @Field("merchantid") String merchantid,
                                                            @Field("branchid") String branchid,
                                                            @Field("securitykey") String securitykey);
}
