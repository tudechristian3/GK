package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.skycable.CallLinkSkycableAccountResponse;
import com.goodkredit.myapplication.responses.skycable.CheckIfConfigIsAvailableResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycableConfigResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycableDictionariesResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycableNewApplicationRegistrationsResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycablePPVCatalogsResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycablePPVCustomerServiceChargeResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycablePpvHistoryResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycableSOAResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycableSupportConversationResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycableSupportFAQResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycableSupportThreadResponse;
import com.goodkredit.myapplication.responses.skycable.RegisterNewSkycableAccountResponse;
import com.goodkredit.myapplication.responses.skycable.SendSkycableSupportMessageResponse;
import com.goodkredit.myapplication.responses.skycable.SkycableProcessVoucherConsummationResponse;
import com.goodkredit.myapplication.responses.skycable.SubscribeSkycablePpvResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SkycableAPI {
    @FormUrlEncoded
    @POST("getSkycablePpvCatalogs")
    Call<GetSkycablePPVCatalogsResponse> getSkycablePpvCatalogsCall(@Field("sessionid") String sessionid,
                                                                    @Field("imei") String imei,
                                                                    @Field("userid") String userid,
                                                                    @Field("borrowerid") String borrowerid,
                                                                    @Field("authcode") String authcode,
                                                                    @Field("limit") String limit);

    @FormUrlEncoded
    @POST("getSkycablePPVCustomerServiceCharge")
    Call<GetSkycablePPVCustomerServiceChargeResponse> getSkycablePPVCustomerServiceChargeCall(@Field("sessionid") String sessionid,
                                                                                              @Field("imei") String imei,
                                                                                              @Field("userid") String userid,
                                                                                              @Field("borrowerid") String borrowerid,
                                                                                              @Field("authcode") String authcode,
                                                                                              @Field("amount") String amount,
                                                                                              @Field("type") String type);

    @FormUrlEncoded
    @POST("subscribeSkycablePpv")
    Call<SubscribeSkycablePpvResponse> subscribeSkycablePpvCall(@Field("sessionid") String sessionid,
                                                                @Field("imei") String imei,
                                                                @Field("userid") String userid,
                                                                @Field("borrowerid") String borrowerid,
                                                                @Field("authcode") String authcode,
                                                                @Field("ppvid") String ppvid,
                                                                @Field("skycableaccountno") String skycableaccountno,
                                                                @Field("borrowername") String borrowername,
                                                                @Field("customerfirstname") String customerfirstname,
                                                                @Field("customerlastname") String customerlastname,
                                                                @Field("customermobilenumber") String customermobilenumber,
                                                                @Field("customeremailaddress") String customeremailaddress,
                                                                @Field("customeraddress") String customeraddress,
                                                                @Field("city") String city,
                                                                @Field("amount") String amount,
                                                                @Field("merchantid") String merchantid,
                                                                @Field("longitude") String longitude,
                                                                @Field("latitude") String latitude,
                                                                @Field("preconsummationsessionid") String preconsummationsessionid,
                                                                @Field("servicecode") String servicecode);

    @FormUrlEncoded
    @POST("registerNewSkycableAccount")
    Call<RegisterNewSkycableAccountResponse> registerNewSkycableAccountCall(@Field("sessionid") String sessionid,
                                                                            @Field("imei") String imei,
                                                                            @Field("userid") String userid,
                                                                            @Field("borrowerid") String borrowerid,
                                                                            @Field("authcode") String authcode,
                                                                            @Field("firstname") String firstname,
                                                                            @Field("lastname") String lastname,
                                                                            @Field("gender") String gender,
                                                                            @Field("birthdate") String birthdate,
                                                                            @Field("address") String address,
                                                                            @Field("city") String city,
                                                                            @Field("longitude") String longitude,
                                                                            @Field("latitude") String latitude,
                                                                            @Field("zipcode") String zipcode,
                                                                            @Field("billingaddress") String billingaddress,
                                                                            @Field("installationaddress") String installationaddress,
                                                                            @Field("emailaddress") String emailaddress,
                                                                            @Field("mobileno") String mobileno,
                                                                            @Field("telephoneno") String telephoneno,
                                                                            @Field("planid") String planid,
                                                                            @Field("merchantid") String merchantid,
                                                                            @Field("imageidurl") String imageidurl,
                                                                            @Field("imagepoburl") String imagepoburl,
                                                                            @Field("serviceareaid") String serviceareaid);

    @FormUrlEncoded
    @POST("getSkycablePpvHistory")
    Call<GetSkycablePpvHistoryResponse> getSkycablePpvHistoryCall(@Field("sessionid") String sessionid,
                                                                  @Field("imei") String imei,
                                                                  @Field("userid") String userid,
                                                                  @Field("borrowerid") String borrowerid,
                                                                  @Field("authcode") String authcode,
                                                                  @Field("limit") String limit,
                                                                  @Field("year") String year);

    @FormUrlEncoded
    @POST("getSkycableSOA")
    Call<GetSkycableSOAResponse> getSkycableSOACall(@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("mobileno") String mobileno,
                                                    @Field("limit") String limit);

    @FormUrlEncoded
    @POST("linkSkycableAccount")
    Call<CallLinkSkycableAccountResponse> callLinkSkycableAccountCall(@Field("sessionid") String sessionid,
                                                                      @Field("imei") String imei,
                                                                      @Field("userid") String userid,
                                                                      @Field("borrowerid") String borrowerid,
                                                                      @Field("authcode") String authcode,
                                                                      @Field("skycableaccountno") String skycableaccountno,
                                                                      @Field("skycableaccountname") String skycableaccountname,
                                                                      @Field("mobileno") String mobileno);

    @FormUrlEncoded
    @POST("getSkycableNewApplicationRegistrations")
    Call<GetSkycableNewApplicationRegistrationsResponse> getSkycableNewApplicationRegistrationsCall(@Field("sessionid") String sessionid,
                                                                                                    @Field("imei") String imei,
                                                                                                    @Field("userid") String userid,
                                                                                                    @Field("borrowerid") String borrowerid,
                                                                                                    @Field("authcode") String authcode,
                                                                                                    @Field("limit") String limit,
                                                                                                    @Field("year") String year);

    @FormUrlEncoded
    @POST("getSkycableDictionaries")
    Call<GetSkycableDictionariesResponse> getSkycableDictionariesCall(@Field("sessionid") String sessionid,
                                                                      @Field("imei") String imei,
                                                                      @Field("userid") String userid,
                                                                      @Field("borrowerid") String borrowerid,
                                                                      @Field("authcode") String authcode);

    @FormUrlEncoded
    @POST("skycableProcessVoucherConsummation")
    Call<SkycableProcessVoucherConsummationResponse> skycableProcessVoucherConsummationCall(@Field("sessionid") String sessionid,
                                                                                            @Field("imei") String imei,
                                                                                            @Field("userid") String userid,
                                                                                            @Field("borrowerid") String borrowerid,
                                                                                            @Field("authcode") String authcode,
                                                                                            @Field("vouchersessionid") String vouchersessionid,
                                                                                            @Field("merchantid") String merchantid,
                                                                                            @Field("registrationid") String registrationid,
                                                                                            @Field("servicecode") String servicecode);


    @FormUrlEncoded
    @POST("getSkycableSupportThread")
    Call<GetSkycableSupportThreadResponse> getSkycableSupportThreadCall(@Field("sessionid") String sessionid,
                                                                        @Field("imei") String imei,
                                                                        @Field("userid") String userid,
                                                                        @Field("borrowerid") String borrowerid,
                                                                        @Field("authcode") String authcode,
                                                                        @Field("year") String year);

    @FormUrlEncoded
    @POST("getSkycableSupportFAQ")
    Call<GetSkycableSupportFAQResponse> getSkycableSupportFAQCall(@Field("sessionid") String sessionid,
                                                                  @Field("imei") String imei,
                                                                  @Field("userid") String userid,
                                                                  @Field("borrowerid") String borrowerid,
                                                                  @Field("authcode") String authcode,
                                                                  @Field("helptopic") String helptopic);

    @FormUrlEncoded
    @POST("sendSkycableSupportMessage")
    Call<SendSkycableSupportMessageResponse> sendSkycableSupportMessageCall(@Field("sessionid") String sessionid,
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
    @POST("getSkycableSupportConversation")
    Call<GetSkycableSupportConversationResponse> getSkycableSupportConversationCall(@Field("sessionid") String sessionid,
                                                                                    @Field("imei") String imei,
                                                                                    @Field("userid") String userid,
                                                                                    @Field("borrowerid") String borrowerid,
                                                                                    @Field("authcode") String authcode,
                                                                                    @Field("threadid") String threadid,
                                                                                    @Field("year") String year);

    @FormUrlEncoded
    @POST("getSkycableConfig")
    Call<GetSkycableConfigResponse> getSkycableConfigCall(@Field("sessionid") String sessionid,
                                                          @Field("imei") String imei,
                                                          @Field("userid") String userid,
                                                          @Field("borrowerid") String borrowerid,
                                                          @Field("authcode") String authcode,
                                                          @Field("servicecode") String servicecode);

    @FormUrlEncoded
    @POST("checkIfConfigIsAvailable")
    Call<CheckIfConfigIsAvailableResponse> checkIfConfigIsAvailableCall(@Field("sessionid") String sessionid,
                                                                        @Field("imei") String imei,
                                                                        @Field("userid") String userid,
                                                                        @Field("borrowerid") String borrowerid,
                                                                        @Field("authcode") String authcode,
                                                                        @Field("servicecode") String servicecode,
                                                                        @Field("serviceconfigname") String serviceconfigname);
}
