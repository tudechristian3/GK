package com.goodkredit.myapplication.activities.settings;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.settings.SupportSendMessageAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetGenericResponse;
import com.goodkredit.myapplication.responses.support.GetSupportConversationResponse;
import com.goodkredit.myapplication.responses.support.GetSupportConversationResponseData;
import com.goodkredit.myapplication.responses.support.GetSupportMessagesResponseData;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 3/9/2017.
 */

public class SupportSendMessageActivity extends BaseActivity implements View.OnClickListener {

    private ScrollView scrollViewMessages;
    private RecyclerView list;
    private SupportSendMessageAdapter mAdapter;

    public static RecyclerView staticList;
    public static SupportSendMessageAdapter staticAdapter;
    public static ScrollView staticLinearManager;

    private LinearLayoutManager linearLayoutManager;

    private DatabaseHandler db;

    private String mSource;
    private String mThreadID;
    private String mHelpTopic;

    private TextView sendMessageHelpTopic;
    private TextView sendMessageTicket;
    private TextView sendMessageSubject;
    private TextView sendMessageStatus;
    private ImageView sendMessageHelpTopicLogo;
    private ImageView imageView;
    private EditText editText;

    private ImageView sendButton;
    private ImageView openCameraButton;

    private CommonVariables cv;

    private ProgressDialog mProgressDialog;

    private AQuery aq;

    private Gson gson;

    private MaterialDialog mDialog;

    private String imageURL = "";
    private String tID;

    public static final String KEY_SOURCE = "source";
    public static final String KEY_THREAD_ID = "thread_id";
    public static final String KEY_HELP_TOPIC = "helptopic";
    public static final String KEY_DATA = "data";

    private File mCurrentPhoto;
    private static final int REQUEST_IMAGE_SELECTOR = 111;

    private GetSupportMessagesResponseData mGetSupportMesssagesResponseData;

    public static boolean isActive = false;
    public static String thisThreadid = ".";

    private RelativeLayout rootView;
    private RelativeLayout preSendImageLayout;

    private String imageurl = "";

    //GLOBAL DIALOGS
    private EditText dialogEdt;
    private String dialogString = "";

    private String sessionid = "";

    @Override
    protected void onPause() {
        isActive = false;
        thisThreadid = ".";
        super.onPause();
    }

    @Override
    protected void onResume() {
        isActive = true;
        mGetSupportMesssagesResponseData = getIntent().getParcelableExtra(KEY_DATA);
        thisThreadid = mGetSupportMesssagesResponseData.getThreadID();
        scrollViewMessages.fullScroll(View.FOCUS_DOWN);
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        initialize();
        setupToolbarWithTitle(mGetSupportMesssagesResponseData.getHelpTopic());
        setUI(mGetSupportMesssagesResponseData.getSubject());
    }

    private void initialize() {

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());


        db = new DatabaseHandler(this);
        aq = new AQuery(this);
        gson = new Gson();

        mSource = getIntent().getStringExtra(KEY_SOURCE);
        mThreadID = getIntent().getStringExtra(KEY_THREAD_ID);
        mGetSupportMesssagesResponseData = getIntent().getParcelableExtra(KEY_DATA);
        mHelpTopic = getIntent().getStringExtra(KEY_HELP_TOPIC);

        thisThreadid = mGetSupportMesssagesResponseData.getThreadID();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setInverseBackgroundForced(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Sending message\nPlease wait...");

        imageView = (ImageView) findViewById(R.id.preSendImage);
        editText = (EditText) findViewById(R.id.preSendMessage);
        sendMessageHelpTopic = (TextView) findViewById(R.id.sendMessageHelpTopic);
        sendMessageTicket = (TextView) findViewById(R.id.sendMessageTicket);
        sendMessageSubject = (TextView) findViewById(R.id.sendMessageSubject);
        sendMessageStatus = (TextView) findViewById(R.id.sendMessageStatus);
        sendMessageHelpTopicLogo = (ImageView) findViewById(R.id.sendMessageHelpTopicLogo);

        editText.addTextChangedListener(textWatcher);

        Glide
                .with(this)
                .load(mGetSupportMesssagesResponseData.getLogo())
                .apply(RequestOptions.fitCenterTransform())
                .into(sendMessageHelpTopicLogo);

        sendMessageHelpTopic.setText(mGetSupportMesssagesResponseData.getHelpTopic());
        sendMessageStatus.setText(mGetSupportMesssagesResponseData.getStatus());

        if (mGetSupportMesssagesResponseData.getThreadID().equals("."))
            sendMessageTicket.setText("-");
        else
            sendMessageTicket.setText(mGetSupportMesssagesResponseData.getThreadID());

        if (mGetSupportMesssagesResponseData.getSubject().equals("."))
            sendMessageSubject.setText("-");
        else
            sendMessageSubject.setText(mGetSupportMesssagesResponseData.getSubject());


        if (mGetSupportMesssagesResponseData.getStatus().equals("OPEN"))
            sendMessageStatus.setTextColor(getResources().getColor(R.color.support_open));
        else {
            sendMessageStatus.setTextColor(getResources().getColor(R.color.colorsilver));
            findViewById(R.id.reopenThread).setVisibility(View.VISIBLE);
            findViewById(R.id.reopenThread).setOnClickListener(this);
            findViewById(R.id.chatpane).setVisibility(View.GONE);
        }

        linearLayoutManager = new LinearLayoutManager(getViewContext());
        list = (RecyclerView) findViewById(R.id.sendMessageThread);
        list.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setStackFromEnd(true);

        mAdapter = new SupportSendMessageAdapter(getViewContext(), db.getConversation(db, mGetSupportMesssagesResponseData.getThreadID()));
        list.setAdapter(mAdapter);

        staticList = list;
        staticAdapter = mAdapter;
        staticLinearManager = scrollViewMessages;

        sendButton = (ImageView) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        openCameraButton = (ImageView) findViewById(R.id.openCameraButton);
        openCameraButton.setOnClickListener(this);

        tID = mGetSupportMesssagesResponseData.getThreadID();

        scrollViewMessages = (ScrollView) findViewById(R.id.scrollViewMessages);

        preSendImageLayout = (RelativeLayout) findViewById(R.id.preSendImageLayout);
        findViewById(R.id.removeImage).setOnClickListener(this);

        rootView = (RelativeLayout) findViewById(R.id.rootview);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootView.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    scrollViewMessages.fullScroll(View.FOCUS_DOWN);
                    editText.requestFocus();
                } else {
                    scrollViewMessages.fullScroll(View.FOCUS_DOWN);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    /********
     * FUNCTIONx
     * **********/


    private void setUI(String subject) {
        if (!subject.equals(".")) {

        } else {
            //gkan ug faq
            //askForSubject();
            askForSubjectNew();
        }
    }

    private void askForSubject() {
        mDialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_ask_for_subject, false)
                .cancelable(true)
                .positiveText("OK")
                .negativeText("CANCEL")
                .show();

        View view = mDialog.getCustomView();
        final EditText dialogEdt = (EditText) view.findViewById(R.id.dialogSubject);

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mDialog.getView());

        View positive = mDialog.getActionButton(DialogAction.POSITIVE);
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageSubject.setText(dialogEdt.getText().toString());
                mDialog.dismiss();
            }
        });
    }

    private void askForSubjectNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("Enter Subject", "",
                "CANCEL", "OK", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.EDITTEXT);

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        List<String> editTextDataType  = new ArrayList<>();
        editTextDataType.add(String.valueOf(GlobalDialogsEditText.VARCHAR));

        LinearLayout editTextContainer = dialog.setContentEditText(editTextDataType,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.CENTER,
                        R.drawable.underline, 12, "Enter subject here ..."));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;
                editItem.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_ITALIC));
                String taggroup = editItem.getTag().toString();
                if(taggroup.equals("TAG 0")) {
                    dialogEdt = editItem;
                    dialogEdt.addTextChangedListener(dialogTextWatcher);
                }
            }
        }

        View btndoubleone = dialog.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        View btndoubletwo = dialog.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sendMessageSubject.setText(dialogString);
            }
        });
    }

    private void dispatchPhotoSelectionIntent() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            this.startActivityForResult(galleryIntent, REQUEST_IMAGE_SELECTOR);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE_SELECTOR: {
                if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getViewContext().getContentResolver().query(data.getData(), filePathColumn, null, null, null);
                    if (cursor == null || cursor.getCount() < 1) {
                        mCurrentPhoto = null;
                        break;
                    }
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    if (columnIndex < 0) { // no column index
                        mCurrentPhoto = null;
                        break;
                    }
                    mCurrentPhoto = new File(cursor.getString(columnIndex));
                    cursor.close();
                } else {
                    mCurrentPhoto = null;
                }

                if (mCurrentPhoto != null) {
                    preSendImageLayout.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                    Glide.with(getViewContext())
                            .load(mCurrentPhoto)
                            .apply(RequestOptions.placeholderOf(R.drawable.ic_image)
                                    .fitCenter())
                            .into(imageView);

                    sendButton.setImageResource(R.drawable.ic_sendblue);
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //onButtonClick
    private void sendMessage() {
        if (mCurrentPhoto != null) {
            getUploadSession();
        } else if (!editText.getText().toString().isEmpty()) {
            sendText(editText.getText().toString());
        } else {
            showToast("Message is required", GlobalToastEnum.WARNING);
        }
    }

//    private void getUploadSession() {
//
//        Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                .getRegisteredSession(imei, userid);
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            call.enqueue(sessionUploadCallback);
//            showProgressDialog(getViewContext(), "Sending message", "Please wait...");
//        } else {
//            hideProgressDialog();
//            showError(getString(R.string.generic_internet_error_message));
//        }
//    }
//
//    private Callback<String> sessionUploadCallback = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                    hideProgressDialog();
//                } else if (responseData.equals("error")) {
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    hideProgressDialog();
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    hideProgressDialog();
//                } else {
//                    sessionid = response.body();
//                    uploadFile(mCurrentPhoto);
//                }
//            } else {
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                hideProgressDialog();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            hideProgressDialog();
//        }
//    };

    private void getUploadSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Sending message", "Please wait...");
            uploadFile(mCurrentPhoto);
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void uploadFile(File file) {

        RequestBody requestFile = RequestBody.create(
                MediaType.parse("image/*"), file);
        MultipartBody.Part bodyFile = MultipartBody.Part.createFormData(
                "upload_file", file.getName(), requestFile);

        RequestBody bodyBorrowerID = RequestBody.create(MediaType.parse("text/plain"), borrowerid);
        RequestBody bodyIMEI = RequestBody.create(MediaType.parse("text/plain"), imei);
        RequestBody bodyBucketName = RequestBody.create(MediaType.parse("text/plain"), CommonVariables.BUCKETNAME);
        RequestBody bodyUserID = RequestBody.create(MediaType.parse("text/plain"), userid);
        RequestBody bodySession = RequestBody.create(MediaType.parse("text/plain"), sessionid);
        RequestBody bodyAuthCode = RequestBody.create(MediaType.parse("text/plain"), CommonFunctions.getSha1Hex(imei + userid + sessionid));
        RequestBody bodyCommand = RequestBody.create(MediaType.parse("text/plain"), "UPLOAD PROFILE IMAGE");

        Call<GenericResponse> call = RetrofitBuild.getSubscriberAPIService(getViewContext())
                .uploadImage(
                        bodyCommand,
                        bodyFile,
                        bodyBorrowerID,
                        bodyIMEI,
                        bodyBucketName,
                        bodyUserID,
                        bodySession,
                        bodyAuthCode
                );
        call.enqueue(uploadFileCallback);
    }

    private Callback<GenericResponse> uploadFileCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        imageurl = response.body().getData();
                        sendText(imageurl);
                    } else {
                        showError(response.body().getMessage());
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            hideProgressDialog();
        }
    };

    //send message
    private void sendText(String message) {

        Map<String, Object> params = new HashMap<>();
        params.put("helptopic", mGetSupportMesssagesResponseData.getHelpTopic());
        params.put("userid", mGetSupportMesssagesResponseData.getMobileNo());
        params.put("usermobile", mGetSupportMesssagesResponseData.getMobileNo());
        params.put("userimei", mGetSupportMesssagesResponseData.getGKIMEI());
        params.put("messagesubject", sendMessageSubject.getText().toString());
        params.put("useremail", mGetSupportMesssagesResponseData.getGKEmailAddress());
        params.put("message", message.trim());
        params.put("senderlogo", ".");
        params.put("senderborrowerid", mGetSupportMesssagesResponseData.getGKBorrowerID());
        params.put("sendername", mGetSupportMesssagesResponseData.getGKBorrowerName());
        params.put("threadid", tID);
        params.put("supportdepartment", mGetSupportMesssagesResponseData.getSupportUserID());
        params.put("priority", mGetSupportMesssagesResponseData.getPriority());

        log("SupportSendMessageActivity Requests=== " + String.valueOf(params));

        aq.progress(mProgressDialog).ajax(CommonVariables.SEND_SUPPORT_MESSAGE, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                String json = String.valueOf(object);
                log(url + " --+-- " + json);
                if (object != null) {
                    GetSupportConversationResponse response = gson.fromJson(json, GetSupportConversationResponse.class);
                    if (response.getStatus().equals("000")) {
                        //insert all data to db
                        if (!response.getData().isEmpty()) {
                            for (GetSupportConversationResponseData data : response.getData()) {
                                db.insertMessagesConversation(db, data);
                                tID = data.getThreadID();
                            }
                            postSend();
                        } else {
                            showToast("No data yet.", GlobalToastEnum.NOTICE);
                        }
                    } else {
                        showToast(response.getMessage(), GlobalToastEnum.NOTICE);
                    }
                } else {
                    if (CommonFunctions.getConnectivityStatus(getViewContext()) != 0)
                        showToast("Connection maybe slow. Please try again.", GlobalToastEnum.NOTICE);
                    else
                        showToast("No internet connection. Please check.", GlobalToastEnum.NOTICE);
                }
            }
        });

    }

    public static void start(Context context, String source, String helptopic, String threadid, GetSupportMessagesResponseData data) {
        Intent intent = new Intent(context, SupportSendMessageActivity.class);
        intent.putExtra(KEY_SOURCE, source);
        intent.putExtra(KEY_THREAD_ID, threadid);
        intent.putExtra(KEY_HELP_TOPIC, helptopic);
        intent.putExtra(KEY_DATA, data);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openCameraButton:
                if (editText.getText().toString().trim().isEmpty())
                    dispatchPhotoSelectionIntent();
                else {
                    showToast("You can only send either photo or text message at a time.", GlobalToastEnum.WARNING);
                }
                break;
            case R.id.sendButton:
                if (sendMessageSubject.getText().toString().isEmpty() || sendMessageSubject.getText().toString().equals("-")) {
                    //askForSubject();
                    askForSubjectNew();
                } else {
                    sendMessage();
                }
                break;
            case R.id.removeImage: {
                imageView.setImageResource(0);
                mCurrentPhoto = null;
                imageURL = "";
                preSendImageLayout.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
                sendButton.setImageResource(R.drawable.ic_send);
                break;
            }
            case R.id.reopenThread: {
                reopenthread(tID);
                break;
            }
        }
    }

    private void reopenthread(String threadid) {

        Map<String, Object> params = new HashMap<>();
        params.put("userid", mGetSupportMesssagesResponseData.getMobileNo());
        params.put("usermobile", mGetSupportMesssagesResponseData.getMobileNo());
        params.put("imei", mGetSupportMesssagesResponseData.getGKIMEI());
        params.put("borrowerid", mGetSupportMesssagesResponseData.getGKBorrowerID());
        params.put("year", CommonFunctions.getYearFromDate(mGetSupportMesssagesResponseData.getDateTimeIN()));
        params.put("threadid", threadid);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setInverseBackgroundForced(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Reopening thread\nPlease wait...");

        log(params.toString());

        aq.progress(mProgressDialog).ajax(CommonVariables.REOPEN_THREAD, params, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                log(url + " --+-- " + String.valueOf(object));
                if (object != null) {
                    GetGenericResponse response = gson.fromJson(String.valueOf(object), GetGenericResponse.class);
                    if (response.getStatus().equals("000")) {
                        db.updateSupportMessageThreadStatus(db, tID);
                        postSend();
                        sendMessageStatus.setText("OPEN");
                        sendMessageStatus.setTextColor(getResources().getColor(R.color.support_open));
                        findViewById(R.id.reopenThread).setVisibility(View.GONE);
                        findViewById(R.id.chatpane).setVisibility(View.VISIBLE);
                    } else {
                        showToast(response.getMessage(), GlobalToastEnum.NOTICE);
                    }
                } else {
                    if (CommonFunctions.getConnectivityStatus(getViewContext()) != 0)
                        showToast("Connection maybe slow. Please try again.", GlobalToastEnum.NOTICE);
                    else
                        showToast("No internet connection. Please check.", GlobalToastEnum.NOTICE);
                }
            }
        });

    }

    private void postSend() {
        sendMessageTicket.setText(tID);
        sendMessageStatus.setText(mGetSupportMesssagesResponseData.getStatus());
        mAdapter.update(db.getConversation(db, tID));
        mCurrentPhoto = null;
        imageURL = "";
        editText.setText("");
        editText.setVisibility(View.VISIBLE);
        imageView.setImageResource(0);
        preSendImageLayout.setVisibility(View.GONE);
        scrollViewMessages.fullScroll(View.FOCUS_DOWN);
        editText.requestFocus();

        sendButton.setImageResource(R.drawable.ic_send);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().length() > 0) {
                sendButton.setImageResource(R.drawable.ic_sendblue);
            } else {
                sendButton.setImageResource(R.drawable.ic_send);
                openCameraButton.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher dialogTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            dialogString = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
