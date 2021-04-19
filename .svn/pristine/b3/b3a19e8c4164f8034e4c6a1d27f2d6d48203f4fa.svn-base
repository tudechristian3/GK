package com.goodkredit.myapplication.service.newAPICalls;

import com.goodkredit.myapplication.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface VotesV2API {

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    @FormUrlEncoded
    @POST("wsb67")
    Call<GenericResponse> getVotingTransactionPendingRequestV2 (@Header("authenticationid") String authenticationid,
                                         @Header("sessionid") String sessionid,
                                         @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb68")
    Call<GenericResponse> getVotingTransactionsV2 (@Header("authenticationid") String authenticationid,
                                               @Header("sessionid") String sessionid,
                                               @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb69")
    Call<GenericResponse> getPostEventVotesV2 (@Header("authenticationid") String authenticationid,
                                                                @Header("sessionid") String sessionid,
                                                                @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );

    @FormUrlEncoded
    @POST("wsb70")
    Call<GenericResponse> getPostEventParticipantsV2 (@Header("authenticationid") String authenticationid,
                                               @Header("sessionid") String sessionid,
                                               @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
    @FormUrlEncoded
    @POST("wsb71")
    Call<GenericResponse> checkIfHasPendingVotingRequestV2 (@Header("authenticationid") String authenticationid,
                                                      @Header("sessionid") String sessionid,
                                                      @Field("df06651788c884556a0b4b290fb40475ec9a45ba") String param
    );
}
