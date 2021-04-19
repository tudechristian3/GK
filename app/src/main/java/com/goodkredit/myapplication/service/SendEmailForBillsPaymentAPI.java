package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.SendEmailForBillsPaymentResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SendEmailForBillsPaymentAPI {
    @FormUrlEncoded
    @POST("sendEmailForBillsPayment")
    Call<SendEmailForBillsPaymentResponse> sendEmailForBillsPaymentCall(@Field("sessionid") String sessionid,
                                                                        @Field("imei") String imei,
                                                                        @Field("authcode") String authcode,
                                                                        @Field("userid") String userid,
                                                                        @Field("borrowerid") String borrowerid,
                                                                        @Field("transactionrefno") String transactionrefno,
                                                                        @Field("transactiondate") String transactiondate,
                                                                        @Field("biller") String biller,
                                                                        @Field("amount") String amount,
                                                                        @Field("accountno") String accountno,
                                                                        @Field("accountname") String accountname,
                                                                        @Field("emailaddress") String emailaddress,
                                                                        @Field("currency") String currency,
                                                                        @Field("billertransactionno") String billertransactionno);

    @FormUrlEncoded
    @POST("sendEmailForBillsPayment")
    Call<SendEmailForBillsPaymentResponse> sendEmailForBillsPaymentCallv2(@Field("sessionid") String sessionid,
                                                                        @Field("imei") String imei,
                                                                        @Field("authcode") String authcode,
                                                                        @Field("userid") String userid,
                                                                        @Field("borrowerid") String borrowerid,
                                                                        @Field("transactionrefno") String transactionrefno,
                                                                        @Field("transactiondate") String transactiondate,
                                                                        @Field("biller") String biller,
                                                                        @Field("amount") String amount,
                                                                        @Field("accountno") String accountno,
                                                                        @Field("accountname") String accountname,
                                                                        @Field("emailaddress") String emailaddress,
                                                                        @Field("currency") String currency,
                                                                        @Field("billertransactionno") String billertransactionno,
                                                                        @Field("totalservicecharge") String totalservicecharge,
                                                                        @Field("discount") String discount,
                                                                        @Field("totalpaid") String totalpaid);
}
