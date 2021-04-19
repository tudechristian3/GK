package com.goodkredit.myapplication.activities.freesms;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreeSMSActivity extends BaseActivity implements View.OnClickListener {

    private TextView txv_sender;
    private EditText edt_recipient;
    private EditText edt_message;
    private Button btn_send;
    private LinearLayout btn_view_sent_messages;
    private ImageView btn_open_contacts;
    private TextView txv_char_left;

    private String recipient;
    private String message;

    private String sessionid;
    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;
    private String devicetype;

    private static final int PICK_CONTACT = 214;
    private boolean isSend = false;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_sms);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
        initData();
    }

    private void init() {
        setupToolbar();

        txv_sender = (TextView) findViewById(R.id.txv_sendfrom);
        edt_recipient = (EditText) findViewById(R.id.edt_sendto);
        edt_message = (EditText) findViewById(R.id.edt_message);
        btn_send = (Button) findViewById(R.id.btn_send_message);
        btn_view_sent_messages = (LinearLayout) findViewById(R.id.btn_view_sent_messages);
        btn_open_contacts = (ImageView) findViewById(R.id.btn_open_contacts);
        txv_char_left = (TextView) findViewById(R.id.txv_char_left);

        btn_send.setOnClickListener(this);
        btn_view_sent_messages.setOnClickListener(this);
        btn_open_contacts.setOnClickListener(this);
    }

    private void initData() {
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        devicetype = "ANDROID";

        txv_sender.setText("+".concat(userid));
        txv_char_left.setText("100 characters left.");
        txv_char_left.setTypeface(txv_char_left.getTypeface(), Typeface.ITALIC);

        //TRIGGERS:
        edt_recipient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0){
                    Drawable img;
                    Resources res = getResources();
                    img = ContextCompat.getDrawable(getViewContext(), R.mipmap.xcircle);
                    img.setBounds(0,0,36,36);
                    edt_recipient.setCompoundDrawables(null, null, img, null);
                } else{
                    edt_recipient.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() == 10){
                    String num = edt_recipient.getText().toString().substring(0,1);
                    if(num.equals("9")){
                        //do nothing
                        Logger.debug("vanhttp", "I'M HERE.");
                    } else{
                        showToast("Invalid Mobile Number", GlobalToastEnum.WARNING);
                    }
                }
            }
        });

        edt_recipient.setOnTouchListener(new View.OnTouchListener() {

            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(edt_recipient.getCompoundDrawables()[DRAWABLE_RIGHT] != null){
                        int leftEdgeOfRightDrawable = edt_recipient.getRight()
                                - edt_recipient.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                        leftEdgeOfRightDrawable -= getResources().getDimension(R.dimen.EdtPadding);
                        if (event.getRawX() >= leftEdgeOfRightDrawable) {

                            edt_recipient.setText("");

                            return true;
                        }
                    }
                }

                return false;
            }
        });

        edt_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(100-s.length() == 1){
                    txv_char_left.setText(String.valueOf(100 - s.length()).concat(" character left."));
                } else{
                    txv_char_left.setText(String.valueOf(100 - s.length()).concat(" characters left."));
                }

                if(s.length() > 100){
                    txv_char_left.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_error_red));
                    edt_message.setBackgroundResource(R.drawable.border_freesms_error);
                } else{
                    txv_char_left.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_757575));
                    edt_message.setBackgroundResource(R.drawable.border_freesms_message);
                }
            }
        });

        edt_message.setFilters(new InputFilter[]{new EmojiExcludeFilter()});

    }

    private class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btn_send_message: {

               recipient = edt_recipient.getText().toString().trim();

               if(!recipient.equals("")){
                   if(recipient.length() == 10 && recipient.substring(0,1).equals("9")){
                       sendMessage();
                   } else{
                       showToast("Invalid Mobile Number.", GlobalToastEnum.WARNING);
                   }
               } else{
                   showToast("Invalid Mobile Number.", GlobalToastEnum.WARNING);
               }

               break;
           }

           case R.id.btn_view_sent_messages: {
               Intent intent = new Intent(getViewContext(), FreeSMSHistoryActivity.class);
               startActivity(intent);
               break;
           }

           case R.id.btn_open_contacts: {

               Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                       ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
               startActivityForResult(contactPickerIntent, PICK_CONTACT);
               break;
           }
       }
    }

    private void sendMessage(){

        message = edt_message.getText().toString();

        Logger.debug("vanhttp", "message: " + message.length());
        if(message.equals("") || message.trim().equals("")){

            showToast("Please enter a valid message.", GlobalToastEnum.WARNING);
        } else{

            if(message.length() <= 100){
                getSession();
//                showToast("na send ni ban.", GlobalToastEnum.INFORMATION);
            } else{
                showToast("You are only allowed to send free sms with 100 characters.", GlobalToastEnum.WARNING);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case (PICK_CONTACT): {
                if (resultCode == Activity.RESULT_OK) {

                    contactpicked(data);
                }
                break;
            }
        }
    }

    private void contactpicked(Intent data) {
        Cursor cursor = null;
        try{
            String phoneNo = null;
            String name = null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo = cursor.getString(phoneIndex);
            edt_recipient.setText(CommonFunctions.userMobileNumber(phoneNo, false));
            edt_recipient.setSelection(edt_recipient.getText().length());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            setUpProgressLoader();
            setUpProgressLoaderMessageDialog("Processing request... Please wait.");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //sendFreeSMS(sendFreeSMSSession);
                    sendFreeSMSV2();
                }
            }, 2500);
        } else {
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }
    }

    private void sendFreeSMS (Callback<GenericResponse> sendFreeSMSCallback) {
        Call<GenericResponse> sendfreesms = RetrofitBuild.getFreeSMSAPIService(getViewContext())
                .sendFreeSMSCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        "63" + recipient,
                        message,
                        devicetype);

        sendfreesms.enqueue(sendFreeSMSCallback);
    }

    private final Callback<GenericResponse> sendFreeSMSSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            okhttp3.ResponseBody errorBody = response.errorBody();

            setUpProgressLoaderDismissDialog();

            if(errorBody == null) {
                if(response.body().getStatus().equals("000")){

                    //SHOW SUCCESS DIALOG
                    GlobalDialogs dialog = new GlobalDialogs(getViewContext());

                    dialog.createDialog("SUCCESS", response.body().getMessage(), "New Message", "",
                            ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.SUCCESS);

                    View closebtn = dialog.btnCloseImage();
                    closebtn.setVisibility(View.GONE);

                    View singlebtn = dialog.btnSingle();
                    singlebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            clearDetails();
                        }
                    });

                } else{
                    showError(response.body().getMessage());
                }
            } else{
                showError(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void clearDetails(){
        edt_recipient.setText("");
        edt_recipient.requestFocus();

        edt_message.setText("");
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void sendFreeSMSV2(){

        LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
        parameters.put("imei",imei);
        parameters.put("userid",userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("recipient", "63" + recipient);
        parameters.put("message", message);
        parameters.put("devicetype",  CommonVariables.devicetype);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "sendFreeSMSV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        sendFreeSMSV2Object(sendFreeSMSV2Session);

    }

    private void sendFreeSMSV2Object (Callback<GenericResponse> sendFreeSMSCallback) {
        Call<GenericResponse> sendfreesms = RetrofitBuilder.getFreeSMSV2API(getViewContext())
                .sendFreeSMSV2(authenticationid,sessionid,param);
        sendfreesms.enqueue(sendFreeSMSCallback);
    }

    private final Callback<GenericResponse> sendFreeSMSV2Session = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            setUpProgressLoaderDismissDialog();

            if(errorBody == null) {
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    //SHOW SUCCESS DIALOG
                    GlobalDialogs dialog = new GlobalDialogs(getViewContext());
                    dialog.createDialog("SUCCESS", message, "New Message", "",
                            ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.SUCCESS);

                    View closebtn = dialog.btnCloseImage();
                    closebtn.setVisibility(View.GONE);

                    View singlebtn = dialog.btnSingle();
                    singlebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            clearDetails();
                        }
                    });

                }else if (response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(message);
                }
                else{
                    showError(message);
                }
            } else{
                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };



}
