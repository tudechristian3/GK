package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.mdp.GetMDPBulletinResponse;
import com.goodkredit.myapplication.responses.mdp.GetMDPSupportConversationResponse;
import com.goodkredit.myapplication.responses.mdp.GetMDPSupportFAQResponse;
import com.goodkredit.myapplication.responses.mdp.GetMDPSupportThreadResponse;
import com.goodkredit.myapplication.responses.mdp.SendMDPSupportMessageResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MDPAPI {

    @FormUrlEncoded
    @POST("getMiniAppBulletin")
    Call<GetMDPBulletinResponse> getMDPBulletinCall (@Field("sessionid") String sessionid,
                                                     @Field("imei") String imei,
                                                     @Field("userid") String userid,
                                                     @Field("borrowerid") String borrowerid,
                                                     @Field("authcode") String authcode,
                                                     @Field("schoolid") String schoolid,
                                                     @Field("servicecode") String servicecode);

    @FormUrlEncoded
    @POST("getUniversalSupportFAQ")
    Call<GetMDPSupportFAQResponse> getMDPSupportFAQCall(@Field("sessionid") String sessionid,
                                                        @Field("imei") String imei,
                                                        @Field("userid") String userid,
                                                        @Field("borrowerid") String borrowerid,
                                                        @Field("authcode") String authcode,
                                                        @Field("helptopic") String helptopic,
                                                        @Field("schoolid") String schoolid,
                                                        @Field("servicecode") String servicecode);

    @FormUrlEncoded
    @POST("getUniversalSupportThread")
    Call<GetMDPSupportThreadResponse> getMDPSupportThreadCall(@Field("sessionid") String sessionid,
                                                              @Field("imei") String imei,
                                                              @Field("userid") String userid,
                                                              @Field("borrowerid") String borrowerid,
                                                              @Field("authcode") String authcode,
                                                              @Field("year") String year,
                                                              @Field("schoolid") String schoolid,
                                                              @Field("servicecode") String servicecode);

    @FormUrlEncoded
    @POST("getUniversalSupportConversation")
    Call<GetMDPSupportConversationResponse> getMDPSupportConversationCall   (@Field("sessionid") String sessionid,
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
    Call<SendMDPSupportMessageResponse> sendMDPSupportMessageCall   (@Field("sessionid") String sessionid,
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
