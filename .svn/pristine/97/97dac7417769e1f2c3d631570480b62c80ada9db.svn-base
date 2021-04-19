package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.V2GetProcessQueueResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Ban_Lenovo on 7/29/2017.
 */

public interface V2TransactionsApi {

    @FormUrlEncoded
    @POST("getProcessQueue")
    Call<V2GetProcessQueueResponse> getProcessQueue(@Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerId,
                                                    @Field("authcode") String authcode,
                                                    @Field("sessionid") String sessionId);


}

