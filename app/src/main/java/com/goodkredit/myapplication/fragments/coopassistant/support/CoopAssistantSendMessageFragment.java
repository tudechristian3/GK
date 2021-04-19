package com.goodkredit.myapplication.fragments.coopassistant.support;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.adapter.coopassistant.support.CoopAssistantSupportMessagesAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.AmazonCredentials;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.coopassistant.CoopMiniSupportConversationDB;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.SupportConversation;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.responses.GetS3keyResponse;
import com.goodkredit.myapplication.responses.GetSupportSchoolConversationResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniSupportMessageResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantSendMessageFragment extends BaseFragment implements View.OnClickListener {
    public static boolean isActive = false;

    private ImageView sendMessageHelpTopicLogo;
    private TextView sendMessageHelpTopic;
    private TextView sendMessageStatus;
    private TextView sendMessageTicket;
    private TextView sendMessageSubject;

    private MaterialDialog mDialog;

    private ImageView sendButton;
    private EditText preSendMessage;

    private String servicecode = "";

    private DatabaseHandler mdb;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;

    private String threadid;
    private String helptopic;
    private String subject;
    private String mobileno;
    private String borrowername;
    private String emailaddress;
    private String message;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    public static RecyclerView recyclerViewMessageThread;
    public static CoopAssistantSupportMessagesAdapter staticmAdapter;
    public static ScrollView scrollViewMessages;

    private CoopAssistantSupportMessagesAdapter mAdapter;

    private boolean isConversation = false;

    private int year = 2018;


    private LinearLayout sendLayout;

    private ImageView openCameraButton;

    private boolean isError = false;
    private Uri fileUri = null;
    public Uri imageSupportUri = null;
    private String bucketName;
    private AmazonCredentials amazonCredentials = null;
    private boolean isS3Key = false;
    private String imagesupportfilename;
    private String imagesupporturl;

    private MaterialDialog mLoaderDialog;
    private TextView mLoaderDialogMessage;
    private TextView mLoaderDialogTitle;
    private ImageView mLoaderDialogImage;
    private TextView mLoaderDialogClose;
    private TextView mLoaderDialogRetry;
    private RelativeLayout buttonLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_support_send_message, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    @Override
    public void onPause() {
        isActive = false;
        super.onPause();
    }

    @Override
    public void onResume() {
        isActive = true;
        super.onResume();
    }

    private void init(View view) {
        sendLayout = view.findViewById(R.id.sendLayout);
        scrollViewMessages = view.findViewById(R.id.scrollViewMessages);

        sendMessageHelpTopic = view.findViewById(R.id.sendMessageHelpTopic);
        sendMessageStatus = view.findViewById(R.id.sendMessageStatus);
        sendMessageTicket = view.findViewById(R.id.sendMessageTicket);
        sendMessageHelpTopicLogo = view.findViewById(R.id.sendMessageHelpTopicLogo);
        sendMessageSubject = view.findViewById(R.id.sendMessageSubject);

        sendButton = view.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        preSendMessage = view.findViewById(R.id.preSendMessage);

        //loader
        mLlLoader = view.findViewById(R.id.loaderLayout);
        mTvFetching = view.findViewById(R.id.fetching);
        mTvWait = view.findViewById(R.id.wait);

        //no internet connection
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet = view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        recyclerViewMessageThread = view.findViewById(R.id.sendMessageThread);
        recyclerViewMessageThread.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerViewMessageThread.setNestedScrollingEnabled(false);
        mAdapter = new CoopAssistantSupportMessagesAdapter(getViewContext());
        staticmAdapter = mAdapter;
        recyclerViewMessageThread.setAdapter(mAdapter);

        openCameraButton = view.findViewById(R.id.openCameraButton);
        openCameraButton.setOnClickListener(this);

        setupLoaderDialog();
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        year = Calendar.getInstance().get(Calendar.YEAR);

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

        V2Subscriber v2Subscriber = mdb.getSubscriber(mdb);

//        skycableSupportHelpTopics = getArguments().getParcelable("skycableSupportHelpTopics");
        String status = getArguments().getString("STATUS");
        String threadidargs = getArguments().getString("THREADID");
        String subjectargs = getArguments().getString("SUBJECT");

        helptopic = getArguments().getString("HELPTOPIC");
        threadid = getArguments().getString("THREADID");
        subject = getArguments().getString("SUBJECT");

        mobileno = v2Subscriber.getMobileNumber();
        borrowername = v2Subscriber.getBorrowerName();
        emailaddress = v2Subscriber.getEmailAddress();
        message = "";

        sendMessageStatus.setText(status);
        if (status.equals("OPEN")) {
            sendMessageStatus.setTextColor(getResources().getColor(R.color.support_open));
        } else {
            sendMessageStatus.setTextColor(getResources().getColor(R.color.colorsilver));
        }

        if (threadidargs.equals(".")) {
            sendMessageTicket.setText("-");
        } else {
            isConversation = true;
            sendMessageTicket.setText(threadidargs);
            getSession();
        }

        sendMessageSubject.setText(subjectargs);
        sendMessageHelpTopic.setText(helptopic);
//        Glide
//                .with(this)
//                .load(skycableSupportHelpTopics.getLogo())
//                .apply(RequestOptions.fitCenterTransform())
//                .into(sendMessageHelpTopicLogo);

        if (subject.equals("-")) {
            askForSubject();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {

                if (threadid.equals(".")) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Bundle args = new Bundle();
                    args.putString("HELPTOPIC", helptopic);
                    //((SchoolMiniActivity) getViewContext()).displayView(8, args);
                    ((CoopAssistantHomeActivity) getViewContext()).displayView(7, args);

                } else {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    //((SchoolMiniActivity) getViewContext()).displayView(7, null);
                    ((CoopAssistantHomeActivity) getViewContext()).displayView(6, null);

                }

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void askForSubject() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .customView(R.layout.dialog_ask_for_subject, false)
                .cancelable(true)
                .positiveText("OK")
                .negativeText("CANCEL")
                .show();

        View view = mDialog.getCustomView();
        final EditText dialogEdt = view.findViewById(R.id.dialogSubject);

        View positive = mDialog.getActionButton(DialogAction.POSITIVE);
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject = dialogEdt.getText().toString().trim();
                sendMessageSubject.setText(dialogEdt.getText().toString().trim());
                mDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendButton: {
                if (sendMessageSubject.getText().toString().equals("-")) {
                    askForSubject();
                } else {
                    sendMessage();
                }
                break;
            }
            case R.id.openCameraButton: {
                showCameraOptionDialog();
                break;
            }
        }
    }

    private void sendMessage() {
        if (preSendMessage.getText().toString().trim().length() > 0) {
            message = preSendMessage.getText().toString().trim();
            isConversation = false;
            getSession();
        } else {
            showToast("Message is required", GlobalToastEnum.WARNING);
        }
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {

            if (isConversation) {
                mTvFetching.setText("Fetching messages..");
            } else if (isS3Key) {
                mLoaderDialog.show();
                mLoaderDialogMessage.setText("Fetching Credentials, Please wait...");
                mTvFetching.setText("Sending request..");
            } else {
                mTvFetching.setText("Sending message..");
            }

            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isConversation) {
                getUniversalSupportConversation(getUniversalSupportConversationCallBack);
            } else if (isS3Key) {
                getS3key(getS3keySession);
            } else {
                sendUniversalSupportMessage(sendUniversalSupportMessageCallBack);
            }

        } else {
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getUniversalSupportConversation(Callback<GetSupportSchoolConversationResponse> getUniversalSupportConversationCallBack) {
        Call<GetSupportSchoolConversationResponse> getconversation = RetrofitBuild.getSupportAPIService(getViewContext())
                .getUniversalSupportConversation(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        threadid,
                        String.valueOf(year),
                        "coop",
                        servicecode
                );
        getconversation.enqueue(getUniversalSupportConversationCallBack);
    }

    private final Callback<GetSupportSchoolConversationResponse> getUniversalSupportConversationCallBack = new Callback<GetSupportSchoolConversationResponse>() {

        @Override
        public void onResponse(Call<GetSupportSchoolConversationResponse> call, Response<GetSupportSchoolConversationResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            ResponseBody errorBody = response.errorBody();

            isConversation = false;

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    if (mAdapter != null) {

                        if (mdb != null) {

                            List<SupportConversation> supportConversationList = response.body().getSchoolConversationList();

                            if (supportConversationList.size() > 0) {
//
                                mdb.truncateTable(mdb, CoopMiniSupportConversationDB.TABLE_COOP_SUPPORT_CONVERSATIONS);

                                for (SupportConversation supportConversation : supportConversationList) {
                                    mdb.insertCoopAssistantConversation(mdb, supportConversation);
                                }

                                mAdapter.update(mdb.getCoopAssistantConversation(mdb, threadid));

                            } else {
                                mAdapter.clear();
                            }

                            scrollDown();
                        }
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetSupportSchoolConversationResponse> call, Throwable t) {
            isConversation = false;
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void sendUniversalSupportMessage(Callback<SchoolMiniSupportMessageResponse> sendUniversalSupportMessageCallBack) {
        Call<SchoolMiniSupportMessageResponse> sendmessage = RetrofitBuild.getSupportAPIService(getViewContext())
                .sendUniversalSupportMessage(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        threadid,
                        helptopic,
                        subject,
                        mobileno,
                        borrowername,
                        emailaddress,
                        message,
                        "coop",
                        servicecode
                );
        sendmessage.enqueue(sendUniversalSupportMessageCallBack);
    }

    private final Callback<SchoolMiniSupportMessageResponse> sendUniversalSupportMessageCallBack = new Callback<SchoolMiniSupportMessageResponse>() {

        @Override
        public void onResponse(Call<SchoolMiniSupportMessageResponse> call, Response<SchoolMiniSupportMessageResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    threadid = response.body().getThreadID();
                    sendMessageTicket.setText(threadid.equals(".") ? "-" : threadid);
                    preSendMessage.setText("");

                    getUniversalSupportConversation(getUniversalSupportConversationCallBack);

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<SchoolMiniSupportMessageResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    public void scrollDown() {
        scrollViewMessages.post(new Runnable() {
            @Override
            public void run() {
                scrollViewMessages.fullScroll(View.FOCUS_DOWN);
            }
        });

        preSendMessage.requestFocus();
    }

    private void showCameraOptionDialog() {
        new MaterialDialog.Builder(getViewContext())
                .title("Select Option")
                .items(R.array.str_action_profile_picture)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/

                        switch (which) {
                            case 0: {
                                //CAMERA
                                cameraIntent();
                                break;
                            }
                            case 1: {
                                //GALLERY
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, SELECT_FILE);
                                break;
                            }
                        }

                        return true;
                    }
                })
                .show();
    }

    private void cameraIntent() {
        fileUri = getOutputMediaFileUri(1);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        List<ResolveInfo> resolvedIntentActivities = getViewContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
            String packageName = resolvedIntentInfo.activityInfo.packageName;

            getViewContext().grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        if (intent.resolveActivity(getViewContext().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onImageResult(data.getData());
            } else if (requestCode == REQUEST_CAMERA) {
                onImageResult(fileUri);
                fileUri = null;
            }
        }
    }

    private void onImageResult(Uri uri) {
        imageSupportUri = uri;

        isS3Key = true;
        isConversation = false;
        getSession();

    }

    private void getS3key(Callback<GetS3keyResponse> getS3keyCallback) {
        Call<GetS3keyResponse> getS3key = RetrofitBuild.getS3keyService(getViewContext())
                .getAmzCredCall(imei,
                        CommonFunctions.getSha1Hex(imei + CommonVariables.version),
                        CommonVariables.version,
                        "GKSERVICES");
        getS3key.enqueue(getS3keyCallback);
    }

    private final Callback<GetS3keyResponse> getS3keySession = new Callback<GetS3keyResponse>() {

        @Override
        public void onResponse(Call<GetS3keyResponse> call, Response<GetS3keyResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                List<AmazonCredentials> amzlist = response.body().getAmazonCredentials();

                bucketName = response.body().getBucketName();

                if (bucketName.trim().length() > 0 && amzlist.size() > 0) {

                    amazonCredentials = amzlist.get(0);

                    imagesupportfilename = userid + "-" + Calendar.getInstance().getTimeInMillis() + "-skycable-support.jpg";

                    imagesupporturl = "https://s3-us-west-1.amazonaws.com/" + bucketName + "/" + imagesupportfilename;

                    isS3Key = false;

                    uploadImagetoAWS(imageSupportUri, imagesupportfilename, false, imagesupporturl);

                } else {
                    showError("Something went wrong during upload.");
                }

            }
        }

        @Override
        public void onFailure(Call<GetS3keyResponse> call, Throwable t) {

            if (mLoaderDialog != null) {
                mLoaderDialog.dismiss();
            }

            mLlLoader.setVisibility(View.GONE);
            isS3Key = false;
            showError("Error in connection. Please contact support.");
        }
    };

    private void createFile(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File imageFile;
    private String dialogName;
    private String filePath = null;

    private void uploadImagetoAWS(final Uri imageUri, final String filename, final boolean isFinish, final String imageurl) {

        isError = false;

        if (imageUri != null) {
            filePath = null;

            dialogName = "image";

            imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "/" + filename);

            createFile(getViewContext(), imageUri, imageFile);

            @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> uploadTask = new AsyncTask<String, String, String>() {

                @Override
                protected void onPreExecute() {

                    mLoaderDialogMessage.setText("Uploading " + dialogName);
                    mTvFetching.setText("Please wait...");

                }

                @Override
                protected String doInBackground(String... strings) {

                    try {

                        AmazonS3Client s3Client1 = new AmazonS3Client(new BasicAWSCredentials(amazonCredentials.getAPIKey(), amazonCredentials.getAPISecretKey()));
                        PutObjectRequest por = new PutObjectRequest(
                                bucketName,
                                filename,
                                imageFile);

                        //making the object Public
                        por.setCannedAcl(CannedAccessControlList.PublicRead);
                        s3Client1.putObject(por);

                    } catch (Exception e) {
                        isError = true;
                        e.printStackTrace();
                    }

                    return imageurl;
                }

                @Override
                protected void onPostExecute(String s) {
                    if (mLoaderDialog != null) {
                        mLoaderDialog.dismiss();
                    }

                    if (isError) {
                        showError("Something went wrong during upload.");
                    } else {

                        message = imagesupporturl;
                        isS3Key = false;
                        isConversation = false;
                        getSession();

                    }

                }
            };
            uploadTask.execute((String[]) null);

        } else {

            if (mLoaderDialog != null) {
                mLoaderDialog.dismiss();
            }

            showError("Something went wrong during upload.");
        }

    }

    private void setupLoaderDialog() {
        mLoaderDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_custom_animated, false)
                .build();

        View view = mLoaderDialog.getCustomView();
        if (view != null) {
            mLoaderDialogMessage = view.findViewById(R.id.mLoaderDialogMessage);
            mLoaderDialogTitle = view.findViewById(R.id.mLoaderDialogTitle);
            mLoaderDialogImage = view.findViewById(R.id.mLoaderDialogImage);
            mLoaderDialogClose = view.findViewById(R.id.mLoaderDialogClose);
            mLoaderDialogClose.setOnClickListener(this);
            mLoaderDialogRetry = view.findViewById(R.id.mLoaderDialogRetry);
            mLoaderDialogRetry.setVisibility(View.GONE);
            mLoaderDialogRetry.setOnClickListener(this);
            buttonLayout = view.findViewById(R.id.buttonLayout);
            buttonLayout.setVisibility(View.GONE);

            mLoaderDialogTitle.setText("Processing...");

            Glide.with(getViewContext())
                    .load(R.drawable.progressloader)
                    .into(mLoaderDialogImage);
        }
    }
}
