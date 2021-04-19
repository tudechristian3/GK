package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.support.GetSupportFAQResponse;
import com.goodkredit.myapplication.responses.GetSupportSchoolConversationResponse;
import com.goodkredit.myapplication.responses.support.GetSupportThreadResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniSupportMessageResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SupportAPI {

    @FormUrlEncoded
    @POST("getUniversalSupportThread")
    Call<GetSupportThreadResponse> getUniversalSupportThread(@Field("sessionid") String sessionid,
                                                             @Field("imei") String imei,
                                                             @Field("userid") String userid,
                                                             @Field("borrowerid") String borrowerid,
                                                             @Field("authcode") String authcode,
                                                             @Field("year") String year,
                                                             @Field("schoolid") String schoolid,
                                                             @Field("servicecode") String servicecode);

    @FormUrlEncoded
    @POST("getUniversalSupportFAQ")
    Call<GetSupportFAQResponse> getUniversalSupportFAQ(@Field("sessionid") String sessionid,
                                                       @Field("imei") String imei,
                                                       @Field("userid") String userid,
                                                       @Field("borrowerid") String borrowerid,
                                                       @Field("authcode") String authcode,
                                                       @Field("helptopic") String helptopic,
                                                       @Field("schoolid") String schoolid,
                                                       @Field("servicecode") String servicecode);

    @FormUrlEncoded
    @POST("getUniversalSupportConversation")
    Call<GetSupportSchoolConversationResponse> getUniversalSupportConversation(@Field("sessionid") String sessionid,
                                                                                  @Field("imei") String imei,
                                                                                  @Field("userid") String userid,
                                                                                  @Field("borrowerid") String borrowerid,
                                                                                  @Field("authcode") String authcode,
                                                                                  @Field("threadid") String threadid,
                                                                                  @Field("year") String year,
                                                                                  @Field("schoolid") String schoolid,
                                                                                  @Field("servicecode") String servicecode);


    @FormUrlEncoded
    @POST("sendUniversalSupportMessage")
    Call<SchoolMiniSupportMessageResponse> sendUniversalSupportMessage(@Field("sessionid") String sessionid,
                                                                       @Field("imei") String imei,
                                                                       @Field("userid") String userid,
                                                                       @Field("borrowerid") String borrowerid,
                                                                       @Field("authcode") String authcode,
                                                                       @Field("threadid") String threadid,
                                                                       @Field("helptopic") String helptopic,
                                                                       @Field("subject") String subject,
                                                                       @Field("mobileno") String mobileno,
                                                                       @Field("borrowername") String borrowername,
                                                                       @Field("emailaddress") String emailaddress,
                                                                       @Field("message") String message,
                                                                       @Field("schoolid") String schoolid,
                                                                       @Field("servicecode") String servicecode);


}
