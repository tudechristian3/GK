package com.goodkredit.myapplication.service.transfervoucher;


import com.goodkredit.myapplication.responses.transfervoucher.TransferValidateVoucherResponse;
import com.goodkredit.myapplication.responses.transfervoucher.TransferVoucherV2Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TransferVoucherAPI {
    @FormUrlEncoded
    @POST("transferVoucherV2")
    Call<TransferVoucherV2Response> transferVoucherV2Call(@Field("borrowerid") String borrowerid,
                                                          @Field("recipientborrowerid") String recipientborrowerid,
                                                          @Field("vouchercode") String vouchercode,
                                                          @Field("receivername") String receivername,
                                                          @Field("transfertype") String transfertype,
                                                          @Field("authenticationcode") String authenticationcode,
                                                          @Field("verificationcode") String verificationcode,
                                                          @Field("sessionid") String sessionid,
                                                          @Field("imei") String imei,
                                                          @Field("userid") String userid,
                                                          @Field("authcode") String authcode,
                                                          @Field("receivermobile") String receivermobile,
                                                          @Field("receiveremail") String receiveremail);

    @FormUrlEncoded
    @POST("validateRecipientSubscriber")
    Call<TransferValidateVoucherResponse> validateRecipientSubscriber(@Field("sessionid") String sessionid,
                                                                      @Field("imei") String imei,
                                                                      @Field("borrowerid") String borrowerid,
                                                                      @Field("userid") String userid,
                                                                      @Field("authcode") String authcode,
                                                                      @Field("recipientmobilenumber") String recipientmobilenumber,
                                                                      @Field("vouchercode") String vouchercode);

    @FormUrlEncoded
    @POST("transferVoucherToSubscriber")
    Call<TransferVoucherV2Response> transferVoucherToSubscriber(@Field("sessionid") String sessionid,
                                                                @Field("imei") String imei,
                                                                @Field("borrowerid") String borrowerid,
                                                                @Field("userid") String userid,
                                                                @Field("authcode") String authcode,
                                                                @Field("recipientmobilenumber") String recipientmobilenumber,
                                                                @Field("vouchercode") String vouchercode,
                                                                @Field("otp") String otp);


}
