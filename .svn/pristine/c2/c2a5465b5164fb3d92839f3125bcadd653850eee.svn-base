package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetPublicSponsorPromoResponse;
import com.goodkredit.myapplication.responses.GetS3keyResponse;
import com.goodkredit.myapplication.responses.publicsponsor.GetPublicSponsorsResponse;
import com.goodkredit.myapplication.responses.publicsponsor.VerifySponsorCodeV2Response;
import com.goodkredit.myapplication.responses.skycable.GetSkycablePPVCatalogsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Ban_Lenovo on 7/15/2017.
 */

public interface PublicSponsorAPI {

    @FormUrlEncoded
    @POST("getPublicSponsorPromos")
    Call<GetPublicSponsorPromoResponse> getPublicSponsorPromoCall(@Field("sessionid") String sessionid,
                                                                  @Field("imei") String imei,
                                                                  @Field("userid") String userid,
                                                                  @Field("borrowerid") String borrowerid,
                                                                  @Field("authcode") String authcode,
                                                                  @Field("limit") Integer limit,
                                                                  @Field("guarantorid") String guarantorid);

    @FormUrlEncoded
    @POST("getPublicSponsorsV2")
    Call<GetPublicSponsorsResponse> getPublicSponsorsCall    (@Field("imei") String imei,
                                                              @Field("authcode") String authcode,
                                                              @Field("userid") String userid,
                                                              @Field("borrowerid") String borrowerid,
                                                              @Field("limit") String limit,
                                                              @Field("devicetype") String devicetype);


    @FormUrlEncoded
    @POST("verifySponsorCodeV2")
    Call<VerifySponsorCodeV2Response> verifySponsorCodeV2Call    (@Field("imei") String imei,
                                                                  @Field("userid") String userid,
                                                                  @Field("borrowerid") String borrowerid,
                                                                  @Field("authcode") String authcode,
                                                                  @Field("sessionid") String sessionid,
                                                                  @Field("sponsorcode") String sponsorcode,
                                                                  @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("getPublicSponsorPromosV2")
    Call<GetPublicSponsorPromoResponse> getPublicSponsorPromosV2Call   (@Field("imei") String imei,
                                                                        @Field("userid") String userid,
                                                                        @Field("borrowerid") String borrowerid,
                                                                        @Field("authcode") String authcode,
                                                                        @Field("limit") String limit,
                                                                        @Field("sponsorid") String sponsorid,
                                                                        @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("updateBorrowerCustomDetails ")
    Call<GenericResponse> updateBorrowerCustomDetailsCall    (@Field("imei") String imei,
                                                              @Field("userid") String userid,
                                                              @Field("borrowerid") String borrowerid,
                                                              @Field("authcode") String authcode,
                                                              @Field("sessionid") String sessionid,
                                                              @Field("limit") String limit,
                                                              @Field("sponsorid") String sponsorid,
                                                              @Field("customdetails") String customdetails,
                                                              @Field("devicetype") String devicetype);

}
