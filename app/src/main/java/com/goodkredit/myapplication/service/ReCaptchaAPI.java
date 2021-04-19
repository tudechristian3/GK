package com.goodkredit.myapplication.service;

import com.goodkredit.myapplication.responses.gkapplication.ReCaptchaResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ReCaptchaAPI {

    @FormUrlEncoded
    @POST("siteverify")
    Call<ReCaptchaResponse> verifyReCaptchaToken(@Field("secret") String secret,
                                                 @Field("response") String response);
}
