package com.goodkredit.myapplication.service.votes;

import com.goodkredit.myapplication.model.votes.VotesParticipants;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.prepaidrequest.RequestVoucherViaEghlPaymentResponse;
import com.goodkredit.myapplication.responses.votes.VotesGetPostEventResponse;
import com.goodkredit.myapplication.responses.votes.VotesHistoryResponse;
import com.goodkredit.myapplication.responses.votes.VotesParticipantsResponse;
import com.goodkredit.myapplication.responses.votes.VotesPaymentResponse;
import com.goodkredit.myapplication.responses.votes.VotesPendingResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VotesAPI {
    @FormUrlEncoded
    @POST("getPostEventVotes")
    Call<VotesGetPostEventResponse> getPostEventVotes(@Field("sessionid") String sessionid,
                                                       @Field("imei") String imei,
                                                       @Field("userid") String userid,
                                                       @Field("borrowerid") String borrowerid,
                                                       @Field("authcode") String authcode,
                                                       @Field("merchantid") String merchantid);

    @FormUrlEncoded
    @POST("getPostEventParticipants")
    Call<VotesParticipantsResponse> getPostEventParticipants(@Field("sessionid") String sessionid,
                                                             @Field("imei") String imei,
                                                             @Field("userid") String userid,
                                                             @Field("borrowerid") String borrowerid,
                                                             @Field("authcode") String authcode,
                                                             @Field("eventid") String eventid);

    @FormUrlEncoded
    @POST("processVotingPaymentViaGK")
    Call<GenericResponse> processVotingPaymentViaGK(@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("borrowername") String borrowername,
                                                    @Field("authcode") String authcode,
                                                    @Field("eventid") String eventid,
                                                    @Field("participantid") String participantid,
                                                    @Field("hasdiscount") int hasdiscount,
                                                    @Field("servicecode") String servicecode,
                                                    @Field("grossamount") String grossamount,
                                                    @Field("vouchersession") String vouchersession,
                                                    @Field("customersc") String customersc);

    @FormUrlEncoded
    @POST("processVotingPaymentViaPartner")
    Call<VotesPaymentResponse> processVotingPaymentViaPartner(@Field("sessionid") String sessionid,
                                                              @Field("imei") String imei,
                                                              @Field("userid") String userid,
                                                              @Field("borrowerid") String borrowerid,
                                                              @Field("borrowername") String borrowername,
                                                              @Field("authcode") String authcode,
                                                              @Field("eventid") String eventid,
                                                              @Field("participantid") String participantid,
                                                              @Field("hasdiscount") int hasdiscount,
                                                              @Field("servicecode") String servicecode,
                                                              @Field("grossamount") String grossamount,
                                                              @Field("vouchersession") String vouchersession,
                                                              @Field("customersc") String customersc);

    @FormUrlEncoded
    @POST("processVotingPaymentViaCard")
    Call<RequestVoucherViaEghlPaymentResponse> processVotingPaymentViaCard(@Field("sessionid") String sessionid,
                                                                           @Field("imei") String imei,
                                                                           @Field("userid") String userid,
                                                                           @Field("borrowerid") String borrowerid,
                                                                           @Field("borrowername") String borrowername,
                                                                           @Field("authcode") String authcode,
                                                                           @Field("eventid") String eventid,
                                                                           @Field("participantid") String participantid,
                                                                           @Field("hasdiscount") int hasdiscount,
                                                                           @Field("servicecode") String servicecode,
                                                                           @Field("grossamount") String grossamount,
                                                                           @Field("vouchersession") String vouchersession,
                                                                           @Field("customersc") String customersc,
                                                                           @Field("paymentoption") String paymentoption);

    @FormUrlEncoded
    @POST("cancelVotesRequest")
    Call<GenericResponse> cancelVotesRequest(@Field("sessionid") String sessionid,
                                                              @Field("imei") String imei,
                                                              @Field("userid") String userid,
                                                              @Field("borrowerid") String borrowerid,
                                                              @Field("authcode") String authcode,
                                                              @Field("billingid") String billingid,
                                                              @Field("paymenttxnno") String paymenttxnno,
                                                              @Field("paymenttype") String paymenttype);

    @FormUrlEncoded
    @POST("validateVotingTransaction")
    Call<GenericResponse> validateVotingTransaction(@Field("sessionid") String sessionid,
                                             @Field("imei") String imei,
                                             @Field("userid") String userid,
                                             @Field("borrowerid") String borrowerid,
                                             @Field("authcode") String authcode,
                                             @Field("billingid") String billingid,
                                             @Field("paymenttxnno") String paymenttxnno,
                                             @Field("paymenttype") String paymenttype);


    @FormUrlEncoded
    @POST("checkIfHasPendingVotingRequest")
    Call<VotesPaymentResponse> checkIfHasPendingVotingRequest(@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("paymenttype") String paymenttype);

    @FormUrlEncoded
    @POST("getVotingTransactions")
    Call<VotesHistoryResponse> getVotesHistoryCall (@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("devicetype") String devicetype,
                                                    @Field("year") String year,
                                                    @Field("month") String month,
                                                    @Field("limit") String limit);

    @FormUrlEncoded
    @POST("getVotingTransactionPendingRequest")
    Call<VotesPendingResponse> getVotesPendingCall (@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("devicetype") String devicetype,
                                                    @Field("limit") String limit);

}
