package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.paramount.AddParamountQueueResponse;
import com.goodkredit.myapplication.responses.CheckTransactionStatusResponse;
import com.goodkredit.myapplication.responses.GetCitiesResponse;
import com.goodkredit.myapplication.responses.paramount.GetParamountChargesResponse;
import com.goodkredit.myapplication.responses.paramount.GetParamountHistoryResponse;
import com.goodkredit.myapplication.responses.paramount.GetParamountPaymentVouchersResponse;
import com.goodkredit.myapplication.responses.paramount.GetParamountPolicyTypeResponse;
import com.goodkredit.myapplication.responses.paramount.GetParamountVehicleTypeResponse;
import com.goodkredit.myapplication.responses.GetProvincesResponse;
import com.goodkredit.myapplication.responses.paramount.GetVehicleMakerResponse;
import com.goodkredit.myapplication.responses.paramount.GetVehicleSeriesResponse;
import com.goodkredit.myapplication.responses.SendEmailForBillsPaymentResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ParamountAPI {
    @FormUrlEncoded
    @POST("getParamountPolicyType")
    Call<GetParamountPolicyTypeResponse> getParamountPolicyTypeCall(@Field("sessionid") String sessionid,
                                                                    @Field("imei") String imei,
                                                                    @Field("userid") String userid,
                                                                    @Field("borrowerid") String borrowerid,
                                                                    @Field("authcode") String authcode);

    @FormUrlEncoded
    @POST("getParamountLtoMVTypes")
    Call<GetParamountVehicleTypeResponse> getParamountVehicleTypeCall(@Field("sessionid") String sessionid,
                                                                      @Field("imei") String imei,
                                                                      @Field("userid") String userid,
                                                                      @Field("borrowerid") String borrowerid,
                                                                      @Field("authcode") String authcode,
                                                                      @Field("limit") String limit);

    @FormUrlEncoded
    @POST("getParamountCharges")
    Call<GetParamountChargesResponse> getParamountChargesCall(@Field("sessionid") String sessionid,
                                                              @Field("imei") String imei,
                                                              @Field("userid") String userid,
                                                              @Field("borrowerid") String borrowerid,
                                                              @Field("authcode") String authcode,
                                                              @Field("vehicletypeid") String vehicletypeid,
                                                              @Field("applicationtype") String applicationtype);

    @FormUrlEncoded
    @POST("getVehicleMaker")
    Call<GetVehicleMakerResponse> getVehicleMakerCall(@Field("sessionid") String sessionid,
                                                      @Field("imei") String imei,
                                                      @Field("userid") String userid,
                                                      @Field("borrowerid") String borrowerid,
                                                      @Field("authcode") String authcode);

    @FormUrlEncoded
    @POST("getVehicleMakerV2")
    Call<GetVehicleMakerResponse> getVehicleMakerV2Call(@Field("sessionid") String sessionid,
                                                        @Field("imei") String imei,
                                                        @Field("userid") String userid,
                                                        @Field("borrowerid") String borrowerid,
                                                        @Field("authcode") String authcode,
                                                        @Field("limit") String limit);

    @FormUrlEncoded
    @POST("getSearchedVehicleMakerV2")
    Call<GetVehicleMakerResponse> getSearchedVehicleMakerV2Call(@Field("sessionid") String sessionid,
                                                                @Field("imei") String imei,
                                                                @Field("userid") String userid,
                                                                @Field("borrowerid") String borrowerid,
                                                                @Field("authcode") String authcode,
                                                                @Field("keyword") String keyword,
                                                                @Field("limit") String limit);

    @FormUrlEncoded
    @POST("getVehicleSeries")
    Call<GetVehicleSeriesResponse> getVehicleSeriesCall(@Field("sessionid") String sessionid,
                                                        @Field("imei") String imei,
                                                        @Field("userid") String userid,
                                                        @Field("borrowerid") String borrowerid,
                                                        @Field("authcode") String authcode,
                                                        @Field("maker") String maker);

    @FormUrlEncoded
    @POST("getVehicleSeriesV2")
    Call<GetVehicleSeriesResponse> getVehicleSeriesV2Call(@Field("sessionid") String sessionid,
                                                          @Field("imei") String imei,
                                                          @Field("userid") String userid,
                                                          @Field("borrowerid") String borrowerid,
                                                          @Field("authcode") String authcode,
                                                          @Field("maker") String maker,
                                                          @Field("limit") String limit);

    @FormUrlEncoded
    @POST("getSearchedVehicleSeriesV2")
    Call<GetVehicleSeriesResponse> getSearchedVehicleSeriesV2Call(@Field("sessionid") String sessionid,
                                                                  @Field("imei") String imei,
                                                                  @Field("userid") String userid,
                                                                  @Field("borrowerid") String borrowerid,
                                                                  @Field("authcode") String authcode,
                                                                  @Field("maker") String maker,
                                                                  @Field("keyword") String keyword,
                                                                  @Field("limit") String limit);

    @FormUrlEncoded
    @POST("getProvinces")
    Call<GetProvincesResponse> getProvincesCall(@Field("sessionid") String sessionid,
                                                @Field("imei") String imei,
                                                @Field("userid") String userid,
                                                @Field("borrowerid") String borrowerid,
                                                @Field("authcode") String authcode);

    @FormUrlEncoded
    @POST("getCities")
    Call<GetCitiesResponse> getCitiesCall(@Field("sessionid") String sessionid,
                                          @Field("imei") String imei,
                                          @Field("userid") String userid,
                                          @Field("borrowerid") String borrowerid,
                                          @Field("authcode") String authcode,
                                          @Field("province") String province);

    @FormUrlEncoded
    @POST("getParamountHistory")
    Call<GetParamountHistoryResponse> getParamountHistoryCall(@Field("sessionid") String sessionid,
                                                              @Field("imei") String imei,
                                                              @Field("userid") String userid,
                                                              @Field("borrowerid") String borrowerid,
                                                              @Field("authcode") String authcode,
                                                              @Field("limit") String limit,
                                                              @Field("year") String year);

    @FormUrlEncoded
    @POST("getParamountPaymentVouchers")
    Call<GetParamountPaymentVouchersResponse> getParamountPaymentVouchersCall(@Field("sessionid") String sessionid,
                                                                              @Field("imei") String imei,
                                                                              @Field("userid") String userid,
                                                                              @Field("borrowerid") String borrowerid,
                                                                              @Field("authcode") String authcode,
                                                                              @Field("year") String year,
                                                                              @Field("month") String month,
                                                                              @Field("merchanttxnno") String merchanttxnno);

    @FormUrlEncoded
    @POST("addParamountQueueV2")
    Call<AddParamountQueueResponse> addParamountQueueCall(@Field("sessionid") String sessionid,
                                                          @Field("imei") String imei,
                                                          @Field("userid") String userid,
                                                          @Field("borrowerid") String borrowerid,
                                                          @Field("guarantorid") String guarantorid,
                                                          @Field("authcode") String authcode,
                                                          @Field("preconsummationsessionid") String preconsummationsessionid,
                                                          @Field("paramqueue") String paramqueue,
                                                          @Field("hasdiscount") String hasdiscount,
                                                          @Field("servicecode") String servicecode,
                                                          @Field("grossamount") String grossamount,
                                                          @Field("longitude") String longitude,
                                                          @Field("latitude") String latitude);

    @FormUrlEncoded
    @POST("checkParamountQueueTransactionStatus")
    Call<CheckTransactionStatusResponse> checkTransactionStatusCall(@Field("sessionid") String sessionid,
                                                                    @Field("imei") String imei,
                                                                    @Field("userid") String userid,
                                                                    @Field("borrowerid") String borrowerid,
                                                                    @Field("authcode") String authcode,
                                                                    @Field("transactionno") String transactionno);

    @FormUrlEncoded
    @POST("sendParamountEmail")
    Call<SendEmailForBillsPaymentResponse> sendEmailForParamountCall(@Field("imei") String imei,
                                                                     @Field("authcode") String authcode,
                                                                     @Field("sessionid") String sessionid,
                                                                     @Field("userid") String userid,
                                                                     @Field("registrationid") String registrationid,
                                                                     @Field("transactionno") String transactionno,
                                                                     @Field("emailaddress") String emailaddress,
                                                                     @Field("mobileno") String mobileno,
                                                                     @Field("borrowerid") String borrowerid);

}
