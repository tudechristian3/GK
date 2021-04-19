package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.ValidateVoucherResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PaymentAPI {

    @FormUrlEncoded
    @POST("validateVoucherV2")
    Call<ValidateVoucherResponse> validateVoucherV2(@Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("authcode") String authcode,
                                                    @Field("vouchercode") String vouchercode,
                                                    @Field("voucherpin") String voucherpin,
                                                    @Field("merchantid") String merchantid,
                                                    @Field("sessionid") String sessionid,
                                                    @Field("vouchersession") String vouchersession,
                                                    @Field("borrowerid") String borrowerid
    );

    @FormUrlEncoded
    @POST("deleteIndividualPaymentV2")
    Call<ValidateVoucherResponse> deleteIndividualPaymentV2(@Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("authcode") String authcode,
                                                    @Field("vouchercode") String vouchercode,
                                                    @Field("voucherpin") String voucherpin,
                                                    @Field("merchantid") String merchantid,
                                                    @Field("sessionid") String sessionid,
                                                    @Field("vouchersession") String vouchersession,
                                                    @Field("borrowerid") String borrowerid
    );
}
