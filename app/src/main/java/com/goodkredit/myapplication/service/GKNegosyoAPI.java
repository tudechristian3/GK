package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.gknegosyo.ApplyGKNegosyoPackagesResponse;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetDistributorsResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetGKNegosyoPackagesResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetGKNegosyoTransactionResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetResellerInformationResponse;
import com.goodkredit.myapplication.responses.gknegosyo.ValidateDistributorResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetDiscountTransactionsResponse;
import com.goodkredit.myapplication.responses.gknegosyo.GetDiscounterMerchantsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GKNegosyoAPI {

    @FormUrlEncoded
    @POST("getGKNegosyoPackages")
    Call<GetGKNegosyoPackagesResponse> getGKNegosyoPackages(@Field("imei") String imei,
                                                            @Field("userid") String userid,
                                                            @Field("authcode") String authcode,
                                                            @Field("borrowerid") String borrowerid,
                                                            @Field("sessionid") String sessionid,
                                                            @Field("distributorid") String distributorid
    );

    @FormUrlEncoded
    @POST("applyGKNegosyoPackages")
    Call<ApplyGKNegosyoPackagesResponse> applyGKNegosyoPackages(@Field("imei") String imei,
                                                                @Field("userid") String userid,
                                                                @Field("authcode") String authcode,
                                                                @Field("borrowerid") String borrowerid,
                                                                @Field("sessionid") String sessionid,
                                                                @Field("distributorid") String distributorid,
                                                                @Field("packageid") String packageid,
                                                                @Field("paymenttype") String paymenttype,
                                                                @Field("servicecode") String servicecode,
                                                                @Field("city") String city,
                                                                @Field("streetaddress") String streetaddress,
                                                                @Field("province") String province,
                                                                @Field("latitude") double latitude,
                                                                @Field("longitude") double longitude,
                                                                @Field("referralcode") String referralcode
    );


    @FormUrlEncoded
    @POST("processGKNegosyoConsummation")
    Call<GenericResponse> processGKNegosyoConsummation(@Field("imei") String imei,
                                                       @Field("userid") String userid,
                                                       @Field("authcode") String authcode,
                                                       @Field("borrowerid") String borrowerid,
                                                       @Field("sessionid") String sessionid,
                                                       @Field("guarantorid") String guarantorid,
                                                       @Field("packageid") String packageid,
                                                       @Field("vouchersessionid") String vouchersessionid,
                                                       @Field("merchantid") String merchantid,
                                                       @Field("servicecode") String servicecode,
                                                       @Field("registrationid") String registrationid,
                                                       @Field("city") String city,
                                                       @Field("streetaddress") String streetaddress,
                                                       @Field("province") String province,
                                                       @Field("latitude") double latitude,
                                                       @Field("longitude") double longitude,
                                                       @Field("distributorid") String distributorid
    );

    @FormUrlEncoded
    @POST("getGKNegosyoTransaction")
    Call<GetGKNegosyoTransactionResponse> getGKNegosyoTransaction(@Field("imei") String imei,
                                                                  @Field("userid") String userid,
                                                                  @Field("authcode") String authcode,
                                                                  @Field("borrowerid") String borrowerid,
                                                                  @Field("sessionid") String sessionid,
                                                                  @Field("limit") String limit,
                                                                  @Field("datetype") String datetype,
                                                                  @Field("year") String year,
                                                                  @Field("month") String month
    );

    @FormUrlEncoded
    @POST("getResellerInformation")
    Call<GetResellerInformationResponse> getResellerInformation(@Field("imei") String imei,
                                                                @Field("userid") String userid,
                                                                @Field("authcode") String authcode,
                                                                @Field("borrowerid") String borrowerid,
                                                                @Field("sessionid") String sessionid);

    @FormUrlEncoded
    @POST("cancelPackageApplication")
    Call<GenericResponse> cancelApplicationRequest(@Field("imei") String imei,
                                                   @Field("userid") String userid,
                                                   @Field("authcode") String authcode,
                                                   @Field("borrowerid") String borrowerid,
                                                   @Field("sessionid") String sessionid,
                                                   @Field("registrationid") String registrationid,
                                                   @Field("servicecode") String servicecode
    );


    @FormUrlEncoded
    @POST("getGKNegosyoDistributorDetails")
    Call<ValidateDistributorResponse> getGKNegosyoDistributorDetails(@Field("imei") String imei,
                                                                     @Field("userid") String userid,
                                                                     @Field("authcode") String authcode,
                                                                     @Field("borrowerid") String borrowerid,
                                                                     @Field("sessionid") String sessionid,
                                                                     @Field("distributorid") String distributorid
    );

    @FormUrlEncoded
    @POST("getDiscountedMerchants")
    Call<GetDiscounterMerchantsResponse> getDiscountedMerchants(@Field("imei") String imei,
                                                                @Field("userid") String userid,
                                                                @Field("authcode") String authcode,
                                                                @Field("borrowerid") String borrowerid,
                                                                @Field("sessionid") String sessionid,
                                                                @Field("servicecode") String servicecode
    );

    @FormUrlEncoded
    @POST("getDiscountTransactions")
    Call<GetDiscountTransactionsResponse> getDiscountTransactions(@Field("imei") String imei,
                                                                  @Field("userid") String userid,
                                                                  @Field("authcode") String authcode,
                                                                  @Field("borrowerid") String borrowerid,
                                                                  @Field("sessionid") String sessionid,
                                                                  @Field("servicecode") String servicecode,
                                                                  @Field("year") String year,
                                                                  @Field("month") String month,
                                                                  @Field("limit") String limit
    );


    @FormUrlEncoded
    @POST("sendTransactionEmail")
    Call<GenericResponse> sendToEmail(@Field("imei") String imei,
                                      @Field("userid") String userid,
                                      @Field("authcode") String authcode,
                                      @Field("borrowerid") String borrowerid,
                                      @Field("sessionid") String sessionid,
                                      @Field("receiveremail") String receiveremail,
                                      @Field("year") int year,
                                      @Field("month") int month,
                                      @Field("remarks") String remarks
    );

    @FormUrlEncoded
    @POST("getDistributors")
    Call<GetDistributorsResponse> getDistributors(@Field("imei") String imei,
                                                  @Field("userid") String userid,
                                                  @Field("authcode") String authcode,
                                                  @Field("borrowerid") String borrowerid,
                                                  @Field("sessionid") String sessionid,
                                                  @Field("devicetype") String devicetype
    );

}
