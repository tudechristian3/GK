package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.profile.GetSubscriberDetailsResponse;
import com.goodkredit.myapplication.responses.profile.GetSubscribersSponsorRequestStatusResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Ban on 09/03/2018.
 */

public interface SubscriberAPI {

    @FormUrlEncoded
    @POST("getSubscribersProfile")
    Call<GetSubscriberDetailsResponse> getSubscribersProfile(@Field("imei") String imei,
                                                             @Field("authcode") String authcode,
                                                             @Field("userid") String userid,
                                                             @Field("borrowerid") String borrowerId);

    @FormUrlEncoded
    @POST("getSubscriberSponsorRequestStatus")
    Call<GetSubscribersSponsorRequestStatusResponse> getSubscriberSponsorRequestStatus(@Field("imei") String imei,
                                                                                       @Field("authcode") String authcode,
                                                                                       @Field("sessionid") String sessionid,
                                                                                       @Field("userid") String userid,
                                                                                       @Field("borrowerid") String borrowerId);

    @Multipart
    @POST("uploadFile")
    Call<GenericResponse> uploadImage(@Part("uploadcommand") RequestBody command,
                                      @Part MultipartBody.Part imageFile,
                                      @Part("borrowerid") RequestBody borrowerid,
                                      @Part("imei") RequestBody imei,
                                      @Part("bucketname") RequestBody bucketname,
                                      @Part("userid") RequestBody userid,
                                      @Part("sessionid") RequestBody sessionid,
                                      @Part("authcode") RequestBody authcode
    );


    @Multipart
    @POST("uploadAlbum")
    Call<GenericResponse> uploadAlbum(@Part("uploadcommand") RequestBody command,
                                      @Part List<MultipartBody.Part> imageFiles,
                                      @Part("borrowerid") RequestBody borrowerid,
                                      @Part("imei") RequestBody imei,
                                      @Part("bucketname") RequestBody bucketname,
                                      @Part("userid") RequestBody userid,
                                      @Part("sessionid") RequestBody sessionid,
                                      @Part("authcode") RequestBody authcode
    );


    @FormUrlEncoded
    @POST("registerToSponsorV2")
    Call<GenericResponse> registerToSponsorV2Call   (@Field("imei") String imei,
                                                     @Field("userid") String userid,
                                                     @Field("borrowerid") String borrowerId,
                                                     @Field("authcode") String authcode,
                                                     @Field("sessionid") String sessionid,
                                                     @Field("lastname") String lastname,
                                                     @Field("firstname") String firstname,
                                                     @Field("middlename") String middlename,
                                                     @Field("gender") String gender,
                                                     @Field("emailaddress") String emailaddress,
                                                     @Field("streetaddress") String streetaddress,
                                                     @Field("city") String city,
                                                     @Field("sponsorcode") String sponsorcode,
                                                     @Field("message") String message,
                                                     @Field("picurl") String picurl,
                                                     @Field("devicetype") String devicetype);

    @FormUrlEncoded
    @POST("updateSpecificProfile")
    Call<GenericResponse> updateProfile(@Field("imei") String imei,
                                        @Field("userid") String userid,
                                        @Field("borrowerid") String borrowerId,
                                        @Field("authcode") String authcode,
                                        @Field("sessionid") String sessionId,
                                        @Field("lastname") String lastName,
                                        @Field("firstname") String firstName,
                                        @Field("middlename") String middleName,
                                        @Field("gender") String gender,
                                        @Field("emailaddress") String emailAddress,
                                        @Field("streetaddress") String streetAddress,
                                        @Field("city") String city);

}
