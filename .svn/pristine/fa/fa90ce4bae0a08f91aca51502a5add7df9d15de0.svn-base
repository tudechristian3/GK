package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.gkapplication.CheckAppVersionResponse;
import com.goodkredit.myapplication.responses.GetBillingSummaryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Ban_Lenovo on 7/15/2017.
 */

public interface SoaApi {

    @FormUrlEncoded
    @POST("getBillingSummary")
    Call<GetBillingSummaryResponse> getBillingSummary(@Field("imei") String imei,
                                                      @Field("sessionid") String sessionid,
                                                      @Field("authcode") String authcode,
                                                      @Field("userid") String userid,
                                                      @Field("borrowerid") String borrowerid,
                                                      @Field("year") int year,
                                                      @Field("month") int month);

    @FormUrlEncoded
    @POST("checkAppVersionV3")
    Call<CheckAppVersionResponse> checkAppVersionV3(@Field("imei") String imei,
                                                    @Field("version") String version,
                                                    @Field("authcode") String authcode);

}
