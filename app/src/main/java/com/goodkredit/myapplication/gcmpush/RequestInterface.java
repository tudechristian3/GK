package com.goodkredit.myapplication.gcmpush;

import com.goodkredit.myapplication.model.RequestBody;
import com.goodkredit.myapplication.model.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("devices")
    Call<ResponseBody> registerDevice(@Body RequestBody body);
}
