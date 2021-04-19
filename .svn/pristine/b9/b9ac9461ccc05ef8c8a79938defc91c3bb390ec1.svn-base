package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.GetPaymentPartnerBranchesResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetPaymentPartnerBranchesAPI {
    @FormUrlEncoded
    @POST("getPaymentPartnerBranches")
    Call<GetPaymentPartnerBranchesResponse> getPaymentPartnerBranchesCall(@Field("sessionid") String sessionid,
                                                                         @Field("imei") String imei,
                                                                         @Field("authcode") String authcode,
                                                                         @Field("userid") String userid,
                                                                          @Field("limit") String limit,
                                                                          @Field("borrowerid") String borrowerid,
                                                                         @Field("partnerid") String partnerid);
}
