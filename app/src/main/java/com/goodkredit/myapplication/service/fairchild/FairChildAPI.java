package com.goodkredit.myapplication.service.fairchild;

import com.goodkredit.myapplication.model.fairchild.FairChildMembers;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.fairchild.FairChildMembersResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FairChildAPI {
    @FormUrlEncoded
    @POST("validateFairChildMembers")
    Call<FairChildMembersResponse> validateFairChildMembers(@Field("sessionid") String sessionid,
                                                            @Field("imei") String imei,
                                                            @Field("userid") String userid,
                                                            @Field("borrowerid") String borrowerid,
                                                            @Field("authcode") String authcode);

    @FormUrlEncoded
    @POST("voteFairChildCandidates")
    Call<GenericResponse> voteFairChildCandidatesCall (@Field("sessionid") String sessionid,
                                                       @Field("imei") String imei,
                                                       @Field("userid") String userid,
                                                       @Field("borrowerid") String borrowerid,
                                                       @Field("authcode") String authcode,
                                                       @Field("devicetype") String devicetype,
                                                       @Field("participantid") List<String> participantidlist);
}


