package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.projectcoop.GetGameP2SCutoverDetailsResponse;
import com.goodkredit.myapplication.responses.projectcoop.GetGameP2SResultsResponse;
import com.goodkredit.myapplication.responses.projectcoop.GetGamePreviousEntriesResponse;
import com.goodkredit.myapplication.responses.projectcoop.GetGameProductsResponse;
import com.goodkredit.myapplication.responses.projectcoop.GetGameServiceChargeResponse;
import com.goodkredit.myapplication.responses.projectcoop.InsertEntryNumberP2SResponse;
import com.goodkredit.myapplication.responses.projectcoop.ValidateEntryP2SCutoverResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycableSupportConversationResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycableSupportFAQResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycableSupportThreadResponse;
import com.goodkredit.myapplication.responses.skycable.SendSkycableSupportMessageResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProjectcoopAPI {
    @FormUrlEncoded
    @POST("getGameProducts")
    Call<GetGameProductsResponse> getGameProductsCall(@Field("sessionid") String sessionid,
                                                      @Field("imei") String imei,
                                                      @Field("userid") String userid,
                                                      @Field("borrowerid") String borrowerid,
                                                      @Field("authcode") String authcode);

    @FormUrlEncoded
    @POST("getGameP2SResults")
    Call<GetGameP2SResultsResponse> getGameP2SResultsCall(@Field("sessionid") String sessionid,
                                                          @Field("imei") String imei,
                                                          @Field("userid") String userid,
                                                          @Field("borrowerid") String borrowerid,
                                                          @Field("authcode") String authcode,
                                                          @Field("currentday") String currentday);

    @FormUrlEncoded
    @POST("insertEntryNumberP2S")
    Call<InsertEntryNumberP2SResponse> insertEntryNumberP2SCall(@Field("sessionid") String sessionid,
                                                                @Field("imei") String imei,
                                                                @Field("userid") String userid,
                                                                @Field("borrowerid") String borrowerid,
                                                                @Field("authcode") String authcode,
                                                                @Field("entrynumber") String entrynumber,
                                                                @Field("productid") String productid,
                                                                @Field("amountpaid") String amountpaid,
                                                                @Field("amount") String amount,
                                                                @Field("servicecharge") String servicecharge,
                                                                @Field("accountid") String accountid,
                                                                @Field("name") String name,
                                                                @Field("preconsummationsession") String preconsummationsession,
                                                                @Field("merchantid") String merchantid);

    @FormUrlEncoded
    @POST("getGameServiceCharge")
    Call<GetGameServiceChargeResponse> getGameServiceChargeCall(@Field("sessionid") String sessionid,
                                                                @Field("imei") String imei,
                                                                @Field("userid") String userid,
                                                                @Field("borrowerid") String borrowerid,
                                                                @Field("authcode") String authcode,
                                                                @Field("chargeclass") String chargeclass,
                                                                @Field("amount") String amount);

    @FormUrlEncoded
    @POST("getGamePreviousEntries")
    Call<GetGamePreviousEntriesResponse> getGamePreviousEntriesCall(@Field("sessionid") String sessionid,
                                                                    @Field("imei") String imei,
                                                                    @Field("userid") String userid,
                                                                    @Field("borrowerid") String borrowerid,
                                                                    @Field("authcode") String authcode,
                                                                    @Field("limit") String limit,
                                                                    @Field("year") String year,
                                                                    @Field("month") String month);

    @FormUrlEncoded
    @POST("getProjectCoopSupportFAQ")
    Call<GetSkycableSupportFAQResponse> getProjectCoopSupportFAQCall(@Field("sessionid") String sessionid,
                                                                     @Field("imei") String imei,
                                                                     @Field("userid") String userid,
                                                                     @Field("borrowerid") String borrowerid,
                                                                     @Field("authcode") String authcode,
                                                                     @Field("helptopic") String helptopic);

    @FormUrlEncoded
    @POST("getProjectCoopSupportThread")
    Call<GetSkycableSupportThreadResponse> getProjectCoopSupportThreadCall(@Field("sessionid") String sessionid,
                                                                           @Field("imei") String imei,
                                                                           @Field("userid") String userid,
                                                                           @Field("borrowerid") String borrowerid,
                                                                           @Field("authcode") String authcode,
                                                                           @Field("year") String year);

    @FormUrlEncoded
    @POST("getProjectCoopSupportConversation")
    Call<GetSkycableSupportConversationResponse> getProjectCoopSupportConversationCall(@Field("sessionid") String sessionid,
                                                                                       @Field("imei") String imei,
                                                                                       @Field("userid") String userid,
                                                                                       @Field("borrowerid") String borrowerid,
                                                                                       @Field("authcode") String authcode,
                                                                                       @Field("threadid") String threadid,
                                                                                       @Field("year") String year);

    @FormUrlEncoded
    @POST("sendProjectCoopSupportMessage")
    Call<SendSkycableSupportMessageResponse> sendProjectCoopSupportMessageCall(@Field("sessionid") String sessionid,
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
                                                                               @Field("message") String message);

    @FormUrlEncoded
    @POST("getGameP2SCutoverDetails")
    Call<GetGameP2SCutoverDetailsResponse> getGameP2SCutoverDetailsCall(@Field("sessionid") String sessionid,
                                                                        @Field("imei") String imei,
                                                                        @Field("userid") String userid,
                                                                        @Field("borrowerid") String borrowerid,
                                                                        @Field("authcode") String authcode,
                                                                        @Field("cutoverid") String cutoverid);

    @FormUrlEncoded
    @POST("validateEntryP2SCutover")
    Call<ValidateEntryP2SCutoverResponse> validateEntryP2SCutoverCall(@Field("sessionid") String sessionid,
                                                                      @Field("imei") String imei,
                                                                      @Field("userid") String userid,
                                                                      @Field("borrowerid") String borrowerid,
                                                                      @Field("authcode") String authcode);

}
