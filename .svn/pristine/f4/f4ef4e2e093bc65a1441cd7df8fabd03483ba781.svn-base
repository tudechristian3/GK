package com.goodkredit.myapplication.activities.syncontacts;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.synccontacts.SyncContacts;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncContactsActivity extends BaseActivity implements View.OnClickListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String borrowername = "";
    private String borroweremail = "";
    private String borrowermobileno = "";

    private String servicecode = "";
    private String merchantid = "";
    private String merchantname = "";

    //LOCAL CALL
    private DatabaseHandler mdb;

    //MAIN CONTAINER
    private LinearLayout maincontainer;
    private NestedScrollView home_body;

    private LinearLayout linear_synccontacts_container;
    private ImageView btn_close;
    private TextView btn_sync_contacts;
    private List<SyncContacts> syncContactsList = new ArrayList<>();

    private LinearLayout sync_loader_container;
    private ImageView imv_sync_loader;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_synccontacts);

            init();

            initdata();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------METHODS---------------------
    private void init() {
        mdb = new DatabaseHandler(getViewContext());
        //initialize here
        maincontainer = (LinearLayout) findViewById(R.id.maincontainer);
        home_body = (NestedScrollView) findViewById(R.id.home_body);

        linear_synccontacts_container = findViewById(R.id.linear_synccontacts_container);
        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        btn_sync_contacts = findViewById(R.id.btn_sync_contacts);
        btn_sync_contacts.setOnClickListener(this);

        sync_loader_container = findViewById(R.id.sync_loader_container);
        imv_sync_loader = findViewById(R.id.imv_sync_loader);
    }

    private void initdata() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");

        //set toolbar
        setupToolbar();

        getSupportActionBar().setTitle("");
    }

    private void syncContacts() {
//        if (ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.READ_CONTACTS)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS,}, 000);
//        } else {
//            if (syncContactsList != null) {
//                syncContactsList.clear();
//            }
//
//            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                    null,null,null,
//                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
//
//
//            if (phones.getCount() > 0) {
//                phones.moveToFirst();
//                do {
//                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                    String mobileNumber = CommonFunctions.userMobileNumber(phoneNumber, false);
//
//                    mobileNumber = getMobileNumber(mobileNumber);
//
//                    if (!mobileNumber.equals("INVALID")) {
//                        syncContactsList.add(new SyncContacts(phones.getPosition(), name, mobileNumber));
//                    }
//
//
//                } while (phones.moveToNext());
//            }
//            phones.close();
//
//            callMainAPI();
//        }
    }

    private void callMainAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showLoader();
            syncSubscribersContactsAPI();
        } else {
            hideSyncLoader();
            showNoInternetToast();
        }
    }

    private void syncSubscribersContactsAPI() {

        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("contacts", new Gson().toJson(syncContactsList));
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "syncSubscribersContacts");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                syncSubscribersContactsObject();

            } else {
                showNoInternetToast();
            }


        } catch (Exception e) {
            showError();
            e.printStackTrace();
        }

    }

    private void syncSubscribersContactsObject() {
        Call<GenericResponse> syncSubscribersContacts = RetrofitBuilder.getSubscriberV2APIService(getViewContext())
                .syncSubscribersContacts(
                        authenticationid, sessionid, param
                );

        syncSubscribersContacts.enqueue(syncSubscribersContactsCallBack);
    }

    private Callback<GenericResponse> syncSubscribersContactsCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();

                if (errBody == null) {

                    String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                    if (response.body().getStatus().equals("000")) {
                        home_body.setVisibility(View.GONE);
                        showConfirmSuccessDialog(decryptedMessage);
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                        hideSyncLoader();
                    } else {
                        showErrorGlobalDialogs(decryptedMessage);
                        hideSyncLoader();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                showNoInternetToast();
                hideSyncLoader();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            showNoInternetToast();
            hideSyncLoader();
        }
    };


    private void showLoader() {
        Glide.with(getViewContext())
                .load(R.drawable.sync_contacts_loader)
                .into(imv_sync_loader);

        linear_synccontacts_container.setVisibility(View.GONE);
        sync_loader_container.setVisibility(View.VISIBLE);
    }

    private void hideSyncLoader() {
        linear_synccontacts_container.setVisibility(View.VISIBLE);
        sync_loader_container.setVisibility(View.GONE);
    }

    private void showConfirmSuccessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("SUCCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.SUCCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

    }

    //OTHERS
    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, SyncContactsActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (sync_loader_container.getVisibility() == View.GONE) {
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_in_200, R.anim.fade_out_200);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close: {
                finish();
                break;
            }

            case R.id.btn_sync_contacts: {
                syncContacts();
                break;
            }

        }
    }


}
