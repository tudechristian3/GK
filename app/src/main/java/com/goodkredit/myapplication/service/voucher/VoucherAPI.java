package com.goodkredit.myapplication.service.voucher;


import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetVoucherResponse;
import com.goodkredit.myapplication.responses.vouchers.AccreditedBanksResponse;
import com.goodkredit.myapplication.responses.vouchers.payoutone.BankDepositHistoryQueueResponse;
import com.goodkredit.myapplication.responses.vouchers.payoutone.SubscriberBankAccountsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VoucherAPI {
    @FormUrlEncoded
    @POST("getVoucherV3")
    Call<GetVoucherResponse> getVoucherV3Call(@Field("borrowerid") String borrowerid,
                                              @Field("guarantorid") String guarantorid,
                                              @Field("sessionid") String sessionid,
                                              @Field("imei") String imei,
                                              @Field("userid") String userid,
                                              @Field("authcode") String authcode,
                                              @Field("result") String result);

    @FormUrlEncoded
    @POST("getAllowedBanks")
    Call<AccreditedBanksResponse> getAccreditedBanks(@Field("sessionid") String sessionid,
                                                     @Field("imei") String imei,
                                                     @Field("userid") String userid,
                                                     @Field("borrowerid") String borrowerid,
                                                     @Field("authcode") String authcode,
                                                     @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("addSubscriberBankAccount")
    Call<GenericResponse> addSubscriberBankAccount (@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("bank") String bank,
                                                    @Field("accountno") String accountno,
                                                    @Field("accountname") String accountname,
                                                    @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("getSubscriberBankAccounts")
    Call<SubscriberBankAccountsResponse> getSubscriberBankAccounts(@Field("sessionid") String sessionid,
                                                                   @Field("imei") String imei,
                                                                   @Field("userid") String userid,
                                                                   @Field("borrowerid") String borrowerid,
                                                                   @Field("authcode") String authcode,
                                                                   @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("getPayoutOneSubscriberServiceCharge")
    Call<GenericResponse> getPayoutOneSubscriberServiceChargeCall (@Field("sessionid") String sessionid,
                                                                   @Field("imei") String imei,
                                                                   @Field("userid") String userid,
                                                                   @Field("borrowerid") String borrowerid,
                                                                   @Field("authcode") String authcode,
                                                                   @Field("grossamount") String grossamount,
                                                                   @Field("devicetype") String devicetype);
    @FormUrlEncoded
    @POST("depositChequeToSubscriberBankAccount")
    Call<GenericResponse> depositChequeToSubscriberBankAccountCall (@Field("sessionid") String sessionid,
                                                                   @Field("imei") String imei,
                                                                   @Field("userid") String userid,
                                                                   @Field("borrowerid") String borrowerid,
                                                                   @Field("authcode") String authcode,
                                                                   @Field("bank") String bank,
                                                                   @Field("accountname") String accountname,
                                                                   @Field("accountno") String accountno,
                                                                   @Field("grossamount") String grossamount,
                                                                   @Field("totalamount") String totalamount,
                                                                   @Field("voucherserialno") String voucherserialno,
                                                                   @Field("vouchercode") String vouchercode,
                                                                   @Field("subscriberremarks") String subscriberremarks,
                                                                   @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("getBankDepositDetails")
    Call<BankDepositHistoryQueueResponse> getBankDepositDetailsCall (@Field("sessionid") String sessionid,
                                                                     @Field("imei") String imei,
                                                                     @Field("userid") String userid,
                                                                     @Field("borrowerid") String borrowerid,
                                                                     @Field("authcode") String authcode,
                                                                     @Field("statusType") String statusType,
                                                                     @Field("year") String year,
                                                                     @Field("devicetype") String devicetype);

}