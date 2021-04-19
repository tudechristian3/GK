package com.goodkredit.myapplication.service.prepaidload;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.prepaidload.CheckNetworkConnectionResponse;
import com.goodkredit.myapplication.responses.prepaidload.GetProductCodesResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PrepaidLoadAPI {
    @FormUrlEncoded
    @POST("checkNetworkConnection")
    Call<CheckNetworkConnectionResponse> checkNetworkConnectionCall(@Field("sessionid") String sessionid,
                                                                    @Field("imei") String imei,
                                                                    @Field("userid") String userid,
                                                                    @Field("borrowerid") String borrowerid,
                                                                    @Field("authcode") String authcode,
                                                                    @Field("merchantid") String merchantid,
                                                                    @Field("network") String network);

    @FormUrlEncoded
    @POST("getProductCodes")
    Call<GetProductCodesResponse> getProductCodes(@Field("sessionid") String sessionid,
                                                  @Field("imei") String imei,
                                                  @Field("userid") String userid,
                                                  @Field("borrowerid") String borrowerid,
                                                  @Field("authcode") String authcode,
                                                  @Field("devicetype") String devicetype);


}
