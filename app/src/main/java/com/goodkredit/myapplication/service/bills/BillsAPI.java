package com.goodkredit.myapplication.service.bills;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetBillsPaymentLogsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BillsAPI {
    @FormUrlEncoded
    @POST("addBorrowerBiller")
    Call<GenericResponse> addBorrowerBiller(@Field("sessionid") String sessionid,
                                                  @Field("imei") String imei,
                                                  @Field("borrowerid") String borrowerid,
                                                  @Field("userid") String userid,
                                                  @Field("authcode") String authcode,
                                                  @Field("serviceproviderbillercode") String serviceproviderbillercode,
                                                  @Field("billerparamdata") String billerparamdata,
                                                  @Field("billernickname") String billernickname);

}
