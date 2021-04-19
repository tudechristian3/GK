package com.goodkredit.myapplication.service.freesms;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.freesms.GetFreeSMSHistoryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FreeSMSAPI {

    @FormUrlEncoded
    @POST("sendFreeSMS")
    Call<GenericResponse> sendFreeSMSCall(@Field("sessionid") String sessionid,
                                          @Field("imei") String imei,
                                          @Field("userid") String userid,
                                          @Field("borrowerid") String borrowerid,
                                          @Field("authcode") String authcode,
                                          @Field("recipient") String recipient,
                                          @Field("message") String message,
                                          @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("getFreeSMSHistory")
    Call<GetFreeSMSHistoryResponse> getFreeSMSHistoryCall (@Field("sessionid") String sessionid,
                                                           @Field("imei") String imei,
                                                           @Field("userid") String userid,
                                                           @Field("borrowerid") String borrowerid,
                                                           @Field("authcode") String authcode,
                                                           @Field("limit") String limit,
                                                           @Field("devicetype") String devicetype);

}
