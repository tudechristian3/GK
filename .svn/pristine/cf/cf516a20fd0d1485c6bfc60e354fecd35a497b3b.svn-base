package com.goodkredit.myapplication.gcmpush;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.RequestBody;
import com.goodkredit.myapplication.model.ResponseBody;
import com.goodkredit.myapplication.utilities.Logger;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import me.leolin.shortcutbadger.ShortcutBadger;

public class RegistrationIntentService extends IntentService {

    CommonVariables cv;



    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String borrowerid = intent.getStringExtra("BORROWERID");
        String imei = intent.getStringExtra("IMEI");

        Logger.debug("okhttp","RegistrationIntentService() on handle intent");

        try {

            InstanceID instanceID = InstanceID.getInstance(this);
            String registrationId = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            cv.SOCKETID = registrationId;

            Logger.debug("roneldayanan","REG ID: "+registrationId);

            registerDeviceProcess(borrowerid, registrationId, imei);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //register device for push notification
    private void registerDeviceProcess(String deviceId, String registrationId, String imei) {

        Logger.debug("okhttp","RegistrationIntentService() -> registerDeviceProcess on handle intent");
        try {
            RequestBody requestBody = new RequestBody();
            requestBody.setDeviceId(deviceId);
            requestBody.setRegistrationId(registrationId);
            requestBody.setIMEI(imei);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            if (CommonVariables.isHttpLogs) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(interceptor);
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(cv.REGISTERPUSH)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RequestInterface request = retrofit.create(RequestInterface.class);
            Call<ResponseBody> call = request.registerDevice(requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        ResponseBody responseBody = response.body();
                        Intent intent = new Intent(cv.REGISTRATION_PROCESS);
                        intent.putExtra("result", responseBody.getResult());
                        intent.putExtra("message", responseBody.getMessage());
                        LocalBroadcastManager.getInstance(RegistrationIntentService.this).sendBroadcast(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getLocalizedMessage();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                   Logger.debug("okhttp","ERROR: "+t.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
