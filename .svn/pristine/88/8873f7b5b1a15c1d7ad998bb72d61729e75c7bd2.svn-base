package com.goodkredit.myapplication.service.coopassistant;


import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.coopassistant.CoopAssistantLoanTransactionsResponse;
import com.goodkredit.myapplication.responses.coopassistant.CoopAssistantMemberCreditsResponse;
import com.goodkredit.myapplication.responses.coopassistant.CoopAssistantPaymentsResponse;
import com.goodkredit.myapplication.responses.coopassistant.CoopAssistantSOABillsResponse;
import com.goodkredit.myapplication.responses.coopassistant.CoopAssistantTransactionHistoryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CoopAssistantAPI {
    @FormUrlEncoded
    @POST("getCoopandMembersInformation")
    Call<GenericResponse> getCoopandMembersInformation(@Field("sessionid") String sessionid,
                                                       @Field("imei") String imei,
                                                       @Field("userid") String userid,
                                                       @Field("borrowerid") String borrowerid,
                                                       @Field("authcode") String authcode,
                                                       @Field("servicecode") String servicecode);

    @FormUrlEncoded
    @POST("getCOOPSOA")
    Call<CoopAssistantSOABillsResponse> getCoopAssistantSOABillsCall(@Field("sessionid") String sessionid,
                                                                     @Field("imei") String imei,
                                                                     @Field("userid") String userid,
                                                                     @Field("borrowerid") String borrowerid,
                                                                     @Field("authcode") String authcode,
                                                                     @Field("servicecode") String servicecode,
                                                                     @Field("month") String month,
                                                                     @Field("year") String year,
                                                                     @Field("limit") String limit,
                                                                     @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("getCoopPayment")
    Call<CoopAssistantPaymentsResponse> getCoopAssistantPaymentsCall(@Field("sessionid") String sessionid,
                                                                     @Field("imei") String imei,
                                                                     @Field("userid") String userid,
                                                                     @Field("borrowerid") String borrowerid,
                                                                     @Field("authcode") String authcode,
                                                                     @Field("servicecode") String servicecode,
                                                                     @Field("month") String month,
                                                                     @Field("year") String year,
                                                                     @Field("limit") String limit,
                                                                     @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("referCoopToAFriend")
    Call<GenericResponse> referCoopToAFriendCall(@Field("sessionid") String sessionid,
                                                 @Field("imei") String imei,
                                                 @Field("userid") String userid,
                                                 @Field("borrowerid") String borrowerid,
                                                 @Field("authcode") String authcode,
                                                 @Field("servicecode") String servicecode,
                                                 @Field("referredname") String referredname,
                                                 @Field("referredmobileno") String referredmobileno,
                                                 @Field("referredemail") String referredemail,
                                                 @Field("referredaddress") String referredaddress,
                                                 @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("applyCoopLoan")
    Call<GenericResponse> applyCoopLoanCall(@Field("sessionid") String sessionid,
                                            @Field("imei") String imei,
                                            @Field("userid") String userid,
                                            @Field("borrowerid") String borrowerid,
                                            @Field("authcode") String authcode,
                                            @Field("servicecode") String servicecode,
                                            @Field("loanid") String loanid,
                                            @Field("amount") String amount,
                                            @Field("kycotherinfo") String kycotherinfo,
                                            @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("applyCoopAsMember")
    Call<GenericResponse> applyCoopAsMember(@Field("sessionid") String sessionid,
                                            @Field("imei") String imei,
                                            @Field("userid") String userid,
                                            @Field("borrowerid") String borrowerid,
                                            @Field("authcode") String authcode,
                                            @Field("servicecode") String servicecode,
                                            @Field("membershiptype") String membershiptype,
                                            @Field("paymenttype") String paymenttype,
                                            @Field("merchantid") String merchantid,
                                            @Field("firstname") String firstname,
                                            @Field("middlename") String middlename,
                                            @Field("lastname") String lastname,
                                            @Field("mobilenumber") String mobilenumber,
                                            @Field("emailaddress") String emailaddress,
                                            @Field("gender") String gender,
                                            @Field("dateofbirth") String dateofbirth,
                                            @Field("civilstatus") String civilstatus,
                                            @Field("nationality") String nationality,
                                            @Field("occupation") String occupation,
                                            @Field("presentaddress") String presentaddress,
                                            @Field("placeofbirth") String placeofbirth,
                                            @Field("referredbyid") String referredbyid,
                                            @Field("referredbyname") String referredbyname,
                                            @Field("kycotherinfo") String kycotherinfo,
                                            @Field("paymentoption") String paymentoption
    );

    @FormUrlEncoded
    @POST("cancelCoopMembershipRequest")
    Call<GenericResponse> cancelCoopMembershipRequest(@Field("sessionid") String sessionid,
                                                      @Field("imei") String imei,
                                                      @Field("userid") String userid,
                                                      @Field("borrowerid") String borrowerid,
                                                      @Field("authcode") String authcode,
                                                      @Field("servicecode") String servicecode,
                                                      @Field("requestid") String requestid,
                                                      @Field("remarks") String remarks
    );

    @FormUrlEncoded
    @POST("payMembershipRequest")
    Call<GenericResponse> payMembershipRequest(@Field("sessionid") String sessionid,
                                               @Field("imei") String imei,
                                               @Field("userid") String userid,
                                               @Field("borrowerid") String borrowerid,
                                               @Field("authcode") String authcode,
                                               @Field("servicecode") String servicecode,
                                               @Field("vouchersession") String vouchersession,
                                               @Field("amount") String amount,
                                               @Field("merchantid") String merchantid,
                                               @Field("requestid") String requestid,
                                               @Field("hasdiscount") int hasdiscount,
                                               @Field("grossamount") String grossamount,
                                               @Field("paymenttype") String paymenttype,
                                               @Field("paymentoption") String paymentoption);

    @FormUrlEncoded
    @POST("getCoopLoanTransactions")
    Call<CoopAssistantLoanTransactionsResponse> getCoopLoanTransactionsCall(@Field("sessionid") String sessionid,
                                                                            @Field("imei") String imei,
                                                                            @Field("userid") String userid,
                                                                            @Field("borrowerid") String borrowerid,
                                                                            @Field("authcode") String authcode,
                                                                            @Field("servicecode") String servicecode,
                                                                            @Field("year") String year,
                                                                            @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("addCoopAccountWallet")
    Call<GenericResponse> addCoopAccountWallet(@Field("sessionid") String sessionid,
                                               @Field("imei") String imei,
                                               @Field("userid") String userid,
                                               @Field("borrowerid") String borrowerid,
                                               @Field("authcode") String authcode,
                                               @Field("servicecode") String servicecode,
                                               @Field("vouchersession") String vouchersession,
                                               @Field("amount") String amount,
                                               @Field("merchantid") String merchantid,
                                               @Field("accountname") String accountname,
                                               @Field("hasdiscount") int hasdiscount,
                                               @Field("grossamount") String grossamount,
                                               @Field("paymenttype") String paymenttype,
                                               @Field("paymentoption") String paymentoption);

    @FormUrlEncoded
    @POST("payCoopSOA")
    Call<GenericResponse> payCoopSOACall(@Field("sessionid") String sessionid,
                                         @Field("imei") String imei,
                                         @Field("userid") String userid,
                                         @Field("borrowerid") String borrowerid,
                                         @Field("authcode") String authcode,
                                         @Field("servicecode") String servicecode,
                                         @Field("vouchersession") String vouchersession,
                                         @Field("merchantid") String merchantid,
                                         @Field("amount") String amount,
                                         @Field("soaid") String soaid,
                                         @Field("hasdiscount") String hasdiscount,
                                         @Field("grossamount") String grossamount,
                                         @Field("paymenttype") String paymenttype,
                                         @Field("month") String month,
                                         @Field("year") String year,
                                         @Field("paymentoption") String paymentoption,
                                         @Field("devicetype") String devicetype);


    @FormUrlEncoded
    @POST("validateIfHasPendingPaymentRequest")
    Call<CoopAssistantSOABillsResponse> validateIfHasPendingPaymentRequest(@Field("sessionid") String sessionid,
                                                                              @Field("imei") String imei,
                                                                              @Field("userid") String userid,
                                                                              @Field("borrowerid") String borrowerid,
                                                                              @Field("authcode") String authcode,
                                                                              @Field("servicecode") String servicecode,
                                                                              @Field("paymenttype") String paymenttype,
                                                                              @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("checkEGHLTransactions")
    Call<GenericResponse> checkEGHLTransactions(@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("servicecode") String servicecode,
                                                    @Field("ordertxnno") String ordertxnno,
                                                    @Field("paymentno") String paymentno,
                                                    @Field("paymenttype") String paymenttype,
                                                    @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("cancelRequestPayments")
    Call<GenericResponse> cancelCoopPaymentChannelRequestCall (@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("servicecode") String servicecode,
                                                    @Field("paymenttype") String paymenttype,
                                                    @Field("txnno") String txnno,
                                                    @Field("remarks") String remarks,
                                                    @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("validateIfHasPendingLoanRequest")
    Call<GenericResponse> validateIfHasPendingLoanRequestCall (@Field("imei") String imei,
                                                    @Field("sessionid") String sessionid,
                                                    @Field("userid") String userid,
                                                    @Field("authcode") String authcode,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("servicecode") String servicecode,
                                                    @Field("loanid") String loanid,
                                                    @Field("devicetype") String devicetype);
    @FormUrlEncoded
    @POST("cancelCoopLoanRequest")
    Call<GenericResponse> cancelCoopLoanRequestCall (@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("servicecode") String servicecode,
                                                    @Field("requestid") String requestid,
                                                    @Field("remarks") String remarks,
                                                    @Field("devicetype") String devicetype);


    @FormUrlEncoded
    @POST("cancelCoopEGHLRequest")
    Call<GenericResponse> cancelCoopEGHLRequest(@Field("sessionid") String sessionid,
                                                      @Field("imei") String imei,
                                                      @Field("userid") String userid,
                                                      @Field("borrowerid") String borrowerid,
                                                      @Field("authcode") String authcode,
                                                      @Field("servicecode") String servicecode,
                                                      @Field("txnno") String txnno,
                                                      @Field("remarks") String remarks
    );

    @FormUrlEncoded
    @POST("getCoopMemberCredits")
    Call<CoopAssistantMemberCreditsResponse> getCoopMemberCredits(@Field("sessionid") String sessionid,
                                                                  @Field("imei") String imei,
                                                                  @Field("userid") String userid,
                                                                  @Field("borrowerid") String borrowerid,
                                                                  @Field("authcode") String authcode,
                                                                  @Field("servicecode") String servicecode);

    @FormUrlEncoded
    @POST("requestCoopGetVoucherGeneration")
    Call<GenericResponse> requestCoopGetVoucherGeneration(@Field("sessionid") String sessionid,
                                                          @Field("imei") String imei,
                                                          @Field("userid") String userid,
                                                          @Field("borrowerid") String borrowerid,
                                                          @Field("authcode") String authcode,
                                                          @Field("vouchers") String vouchers,
                                                          @Field("servicecode") String servicecode,
                                                          @Field("servicetype") String servicetype);


    @FormUrlEncoded
    @POST("getCoopMemberTransactionHistory")
    Call<CoopAssistantTransactionHistoryResponse> getCoopMemberTransactionHistory(@Field("sessionid") String sessionid,
                                                                                  @Field("imei") String imei,
                                                                                  @Field("userid") String userid,
                                                                                  @Field("borrowerid") String borrowerid,
                                                                                  @Field("servicecode") String servicecode,
                                                                                  @Field("authcode") String authcode,
                                                                                  @Field("year") String year,
                                                                                  @Field("limit") int limit,
                                                                                  @Field("devicetype") String devicetype);

}

