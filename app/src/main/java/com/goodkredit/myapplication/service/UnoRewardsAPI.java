package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.uno.GetUNOPointsConversionResponse;
import com.goodkredit.myapplication.responses.uno.GetUNORedemptionHistoryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UnoRewardsAPI {

    @FormUrlEncoded
    @POST("getUnoMemberPoints")
    Call<GenericResponse> getUnoMemberPoints(@Field("imei") String imei,
                                             @Field("authcode") String authcode,
                                             @Field("userid") String userid,
                                             @Field("borrowerid") String borrowerId,
                                             @Field("sessionid") String sessionid);

    @FormUrlEncoded
    @POST("getUNOPointsConversion")
    Call<GetUNOPointsConversionResponse> getUNOPointsConversion(@Field("imei") String imei,
                                                                @Field("authcode") String authcode,
                                                                @Field("userid") String userid,
                                                                @Field("borrowerid") String borrowerId
    );

    @FormUrlEncoded
    @POST("redeemUNORewards")
    Call<GenericResponse> redeemUNORewards(@Field("imei") String imei,
                                           @Field("userid") String userid,
                                           @Field("authcode") String authcode,
                                           @Field("sessionid") String sessionid,
                                           @Field("borrowerid") String borrowerid,
                                           @Field("mobileno") String mobileno,
                                           @Field("borrowername") String borrowername,
                                           @Field("vouchers") String vouchers,
                                           @Field("servicetype ") String servicetype
    );

    @FormUrlEncoded
    @POST("getUNORedemptionHistory")
    Call<GetUNORedemptionHistoryResponse> getUNORedemptionHistory(@Field("imei") String imei,
                                                                  @Field("userid") String userid,
                                                                  @Field("authcode") String authcode,
                                                                  @Field("borrowerid") String borrowerid
    );


}
