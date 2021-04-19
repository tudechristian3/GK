package com.goodkredit.myapplication.activities.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.model.V2SubscriberDetails;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.profile.GetSubscriberDetailsResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
 * Created by Ban_Lenovo on 7/31/2017.
 */

public class V2ViewProfileActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvFullName;
    private TextView tvMobileNumber;
    private TextView tvEmailAddress;
    private TextView tvAddress;
    private TextView tvSponsor;
    private TextView tvCreditLimit;
    private TextView tvAvailableCredits;
    private TextView tvUsedCredits;
    private TextView tvOutstandingBalance;

    private DatabaseHandler db;
    private V2Subscriber mSubscriber = null;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    public final int REQ_GALLERY = 111;
    public final int REQ_CAMERA = 222;

    private File imageFile;
    public Uri fileUri = null;
    String imageurl = "";

    private ImageView mImgViewDisplayPicture;
    public ProfileLogo target;

    private String guarantoridstatus = "";
    Uri imageUri;

    //GLOBAL  DIALOGS
    private GlobalDialogs globaldialogs;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String param;
    private String keyEncryption;
    private String authenticationid;
    private String index;

    //update profile pic
    private String updateParam;
    private String updateKeyEncryption;
    private String updateAuthenticationid;
    private String updateIndex;

    ProgressBar progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        init();
    }

    private void init() {

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        checkPermissions();
        setupToolbar();
        target = new ProfileLogo();
        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        mLlLoader = findViewById(R.id.loaderLayout);
        mTvFetching = findViewById(R.id.fetching);
        mTvWait = findViewById(R.id.wait);

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        db = new DatabaseHandler(getViewContext());

        findViewById(R.id.profile_edit_profpic).setOnClickListener(this);
        findViewById(R.id.profile_edit_profile).setOnClickListener(this);

        tvMobileNumber = findViewById(R.id.profile_mobile_number);
        tvFullName = findViewById(R.id.profile_fullname);
        tvEmailAddress = findViewById(R.id.profile_email_address);
        tvAddress = findViewById(R.id.profile_address);
        tvSponsor = findViewById(R.id.profile_sponsor);
        tvCreditLimit = findViewById(R.id.tv_credit_limit);
        tvAvailableCredits = findViewById(R.id.tv_available_credits);
        tvUsedCredits = findViewById(R.id.tv_used_credits);
        tvOutstandingBalance = findViewById(R.id.tv_outstanding_balance);
        mImgViewDisplayPicture = findViewById(R.id.circleImageView);
        progress = findViewById(R.id.progress);

        db = new DatabaseHandler(getViewContext());
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                guarantoridstatus = cursor.getString(cursor.getColumnIndex("guarantorregistrationstatus"));
            } while (cursor.moveToNext());
        }
        cursor.close();

        mSubscriber = db.getSubscriber(db);
        populateData(mSubscriber);

        Picasso.with(getViewContext()).load(mSubscriber.getProfilePic())
                .placeholder(R.drawable.emptyprofilepic)
                .error(R.drawable.emptyprofilepic)
                .into(target);

        getSession();
        getSubscribersProfile();

    }

    private void populateData(V2Subscriber subs) {

        if (subs.getBorrowerName() != null) {
            tvFullName.setText(subs.getBorrowerName());
        }
        tvMobileNumber.setText("+" + subs.getMobileNumber());

        if (subs.getEmailAddress().equals("."))
            tvEmailAddress.setText("");
        else
            tvEmailAddress.setText(subs.getEmailAddress());

        String street = subs.getStreetAddress();
        String city = subs.getCity();
        String country = subs.getCountry();

        if (street.equals("."))
            street = "";

        if (city.equals("."))
            city = "";

        if (country.equals("."))
            country = "";

        if (city.equals("") && country.equals("")) {
            tvAddress.setText((street + "" + city + "" + country).trim());
        } else if (country.equals("")) {
            tvAddress.setText((street + "," + city + "" + country).trim());
        } else {
            tvAddress.setText((street + "," + city + "," + country).trim());
        }


        if (guarantoridstatus.equals("PENDING")) {
            tvSponsor.setText("Sponsor request for approval");
            tvSponsor.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
        } else if (guarantoridstatus.equals("DECLINED") || guarantoridstatus.equals(".")) {
            tvSponsor.setText(getString(R.string.string_no_sponsor_yet));
            tvSponsor.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
        } else if (guarantoridstatus.equals("APPROVED")) {
            //tvSponsor.setText(subs.getGuarantorID().toUpperCase());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSubscriber = db.getSubscriber(db);
        populateData(mSubscriber);
    }

    public Bitmap getRoundedShape(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(output);

            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                    bitmap.getWidth() / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    private class ProfileLogo implements Target {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            progress.setVisibility(View.GONE);
            mImgViewDisplayPicture.setVisibility(View.VISIBLE);
            mImgViewDisplayPicture.setImageBitmap(getRoundedShape(bitmap));
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            progress.setVisibility(View.GONE);
            mImgViewDisplayPicture.setVisibility(View.VISIBLE);
            Drawable myDrawable = getResources().getDrawable(R.drawable.emptyprofilepic);
            Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
            mImgViewDisplayPicture.setImageBitmap(getRoundedShape(myLogo));
        }


        @Override
        public void onPrepareLoad(Drawable drawable) {
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, V2ViewProfileActivity.class);
        context.startActivity(intent);
    }

    private void getSession() {
        getAccountInfoV2();
    }

    private void getAccountInfo() {
        Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
                .getAccountWalletInfo(
                        imei,
                        userid,
                        borrowerid,
                        sessionid,
                        V2Utils.getSha1Hex(imei + userid + sessionid));

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0)
            call.enqueue(processQueueCallback);
        else
            //showError(getString(R.string.generic_internet_error_message));
            showWarningToast("Seems you are not connected to the internet. " +
                    "Please check your connection and try again. Thank you.");
    }

    private Callback<String> processQueueCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            ResponseBody errBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);

            if (errBody == null) {
                String code = response.body();
                if (!code.equals("001") && !code.equals("002") && !code.equals("003") && !code.equals("004") && !code.contains("<!DOCTYPE html>")) {
                    //success
                    processResult(response.body());

                } else {

                }
            } else {
                showWarningToast("Seems you are not connected to the internet. " +
                        "Please check your connection and try again. Thank you.");
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            //showError(getString(R.string.generic_error_message));
            showErrorGlobalDialogs("An error has occurred. Please try again.");
        }
    };

    String creditlimitval;
    String availablecreditval;
    String usedcreditval;
    String outstandingbalval;

    public void processResult(String result) {

        try {
            JSONArray jsonArr = new JSONArray(result);

            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    creditlimitval = jsonObj.getString("CreditLimit");
                    availablecreditval = jsonObj.getString("AvailableCredits");
                    usedcreditval = jsonObj.getString("UsedCredits");
                    outstandingbalval = jsonObj.getString("OutstandingBalance");
                    String currentbillval = jsonObj.getString("CurrentBillingBalance");
                    String overduebalval = jsonObj.getString("OverdueBillingBalance");
                    String advancesval = jsonObj.getString("Advances");
                    String currencyval = jsonObj.getString("CurrencyID");

                    db.insertAccountWallet(db, creditlimitval, availablecreditval, usedcreditval, outstandingbalval, currentbillval, overduebalval, advancesval, currencyval);
                }
            }
            populateAccountInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateAccountInfo() {
        findViewById(R.id.lLayoutAccountInfo).setVisibility(View.VISIBLE);
        tvCreditLimit.setText(CommonFunctions.currencyFormatter(creditlimitval));
        tvAvailableCredits.setText(CommonFunctions.currencyFormatter(availablecreditval));
        tvUsedCredits.setText(CommonFunctions.currencyFormatter(usedcreditval));
        tvOutstandingBalance.setText(CommonFunctions.currencyFormatter(outstandingbalval));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_GALLERY) {
            if (resultCode == RESULT_OK) {
                cropImage(data.getData());
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                setDisplayPicture(imageUri);
                imageFile = new File(imageUri.getPath());
                getUploadSession();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == REQ_CAMERA) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    cropImage(fileUri);
                    fileUri = null;
                } else {
                    cropImage(data.getData());
                }
            }
        }
    }

    private void cropImage(Uri uri) {
        if (uri != null) {
            CropImage.activity(uri)
                    .setActivityTitle("Crop Image")
                    .setAutoZoomEnabled(true)
                    .setBorderCornerColor(R.color.zxing_transparent)
                    .setBorderCornerThickness(0)
                    .setActivityMenuIconColor(R.color.colorwhite)
                    .setAllowRotation(true)
                    .setAllowFlipping(true)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setFixAspectRatio(true)
                    .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                    .start(this);
        }
    }

    private void setDisplayPicture(Uri uri) {
        mImgViewDisplayPicture.setImageBitmap(getRoundedShape(getImageBitmap(uri)));
    }

    public Bitmap getImageBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void getUploadSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Uploading image", "Please wait...");
            uploadFile(imageFile);
        } else {
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }

    @SuppressLint("InlinedApi")
    private void checkPermissions() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1234);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profile_edit_profpic) {
            //showUpdateProfPicConfirmation();
            showUpdateProfPicConfirmationNew();
        } else if (v.getId() == R.id.profile_edit_profile) {
            V2EditProfileActivity.start(getViewContext(), mSubscriber);
        }
    }

    private void showUpdateProfPicConfirmation() {
        new MaterialDialog.Builder(getViewContext())
                .title("Select Mode")
                .items(R.array.str_action_profile_picture)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == 0) {
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            fileUri = getOutputMediaFileUri(1);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, REQ_CAMERA);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            V2ViewProfileActivity.this.startActivityForResult(intent, REQ_GALLERY);
                        }
                        return true;
                    }
                })
                .show();
    }

    private void showUpdateProfPicConfirmationNew() {
        globaldialogs = new GlobalDialogs(getViewContext());

        globaldialogs.createDialog("Select Mode", "",
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.RADIO);

        globaldialogs.hideCloseImageButton();

        View closebtn = globaldialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globaldialogs.dismiss();
            }
        });

        List<String> radiocontent = new ArrayList<>();
        radiocontent.add("Camera");
        radiocontent.add("Gallery");

        LinearLayout radGroupContainer = globaldialogs.setContentRadio(radiocontent, RadioGroup.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, 0));

        RadioGroup radgroup = new RadioGroup(getViewContext());

        int radCount = radGroupContainer.getChildCount();
        for (int i = 0; i < radCount; i++) {
            View view = radGroupContainer.getChildAt(i);
            if (view instanceof RadioGroup) {
                radgroup = (RadioGroup) view;
            }
        }

        int count = radgroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View radBtnView = radgroup.getChildAt(i);
            radBtnView.setOnClickListener(profilepicListener);
        }

        View singlebtn = globaldialogs.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globaldialogs.dismiss();
            }
        });
    }

    private View.OnClickListener profilepicListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = String.valueOf(v.getTag());
            if (tag.equals("Camera")) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        globaldialogs.dismiss();
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        fileUri = getOutputMediaFileUri(1);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, REQ_CAMERA);
                    }
                }, 100);
            } else if (tag.equals("Gallery")) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        globaldialogs.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        V2ViewProfileActivity.this.startActivityForResult(intent, REQ_GALLERY);
                    }
                }, 100);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (mLlLoader != null)
            mLoaderTimer.cancel();
    }

    private void getSubscribersProfile() {
        Call<GetSubscriberDetailsResponse> call = RetrofitBuild.getSubscriberAPIService(getViewContext())
                .getSubscribersProfile(
                        imei,
                        CommonFunctions.getSha1Hex(imei + userid),
                        userid,
                        borrowerid
                );

        call.enqueue(getSubscribersProfileCallback);
    }

    private Callback<GetSubscriberDetailsResponse> getSubscribersProfileCallback = new Callback<GetSubscriberDetailsResponse>() {
        @Override
        public void onResponse(Call<GetSubscriberDetailsResponse> call, Response<GetSubscriberDetailsResponse> response) {
            try {

                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        V2SubscriberDetails subDetails = response.body().getSubscriberDetails();
                        String guarantorApprovalStatus = subDetails.getGuarantorApprovalStatus();
                        db.updateGuarantorStatus(db, guarantorApprovalStatus, userid);
                        db.setGuarantorID(db, subDetails.getGuarantorID(), userid);
                        if (guarantorApprovalStatus.equals("PENDING")) {
                            tvSponsor.setText("Sponsor request for approval");
                            tvSponsor.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
                        } else if (guarantorApprovalStatus.equals("DECLINE") || guarantorApprovalStatus.equals(".")) {
                            tvSponsor.setText(getString(R.string.string_no_sponsor_yet));
                            tvSponsor.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
                        } else if (guarantorApprovalStatus.equals("APPROVED")) {
                            tvSponsor.setText(subDetails.getGuarantorID().toUpperCase());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetSubscriberDetailsResponse> call, Throwable throwable) {
        }
    };

    private void uploadFile(File file) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part bodyFile = MultipartBody.Part.createFormData("upload_file", file.getName(), requestFile);

        RequestBody bodyBorrowerID = RequestBody.create(MediaType.parse("text/plain"), borrowerid);
        RequestBody bodyIMEI = RequestBody.create(MediaType.parse("text/plain"), imei);
        RequestBody bodyBucketName = RequestBody.create(MediaType.parse("text/plain"), CommonVariables.BUCKETNAME);
        RequestBody bodyUserID = RequestBody.create(MediaType.parse("text/plain"), userid);
        RequestBody bodySession = RequestBody.create(MediaType.parse("text/plain"), sessionid);
        RequestBody bodyAuthCode = RequestBody.create(MediaType.parse("text/plain"), CommonFunctions.getSha1Hex(imei + userid + sessionid));
        RequestBody bodyCommand = RequestBody.create(MediaType.parse("text/plain"), "UPLOAD PROFILE IMAGE");

        Logger.debug("okhttp","FILES:::"+bodyFile);

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
                        getSessionUpdateProfPicLink();
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    if (errBody.string().contains("!DOCTYPE html")) {
                        //showReUploadDialog();
                        showReUploadDialogNew();
                    } else {
                        //showError();
                        showErrorGlobalDialogs();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
        }
    };

    private void getSessionUpdateProfPicLink() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            //updateProfPicLink();
            updateProfilePicV2();
        } else {
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
            showNoInternetToast();
        }
    }


    private void updateProfPicLink() {
        Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
                .updateProfilePic(
                        imei,
                        userid,
                        borrowerid,
                        V2Utils.getSha1Hex(imei + userid + sessionid),
                        sessionid,
                        imageurl);

        call.enqueue(updateProfPicLinkCallback);
    }

    private Callback<String> updateProfPicLinkCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            hideProgressDialog();
            if (response.body().equals("000")) {
                db.updateProfilePic(db, imageurl, mSubscriber.getMobileNumber());
            } else {
                //showError();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            hideProgressDialog();
            //showError();
            showErrorGlobalDialogs();
        }
    };

    private void showReUploadDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                .content("Failed in uploading your image. Please try again.")
                .positiveText("Try Again")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getUploadSession();
                    }
                }).show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());
    }

    private void showReUploadDialogNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("Upload", "Failed in uploading your image. Please try again.",
                "Cancel", "Try Again", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.FAILED);

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

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
                getUploadSession();
            }
        });
    }


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     */

    private void getAccountInfoV2() {

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(jsonString, "index");
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            parameters.put("index", index);

            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            //AUTHENTICATIONID
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getAccountWalletInfo");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getAccountInfoObjectV2(getAccountInfoObjectV2Session);

        } else {
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }

    }
    private void getAccountInfoObjectV2(Callback<GenericResponse> getAccountWallet) {
        Call<GenericResponse> call = RetrofitBuilder.getSubscriberV2APIService(getViewContext())
                .getAccountWalletInfo(
                        authenticationid, sessionid, param);
        call.enqueue(getAccountWallet);
    }
    private Callback<GenericResponse> getAccountInfoObjectV2Session = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);

            if (errBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                switch (response.body().getStatus()) {

                    case "000":
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                        if (decryptedMessage.equals("error") || decryptedData.equals("error")) {
                            showErrorToast();
                        } else {
                            processResult(decryptedData);
                        }
                        break;
                    default:
                        if (response.body().getStatus().equals("error")) {
                            showError();
                        }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        }
                        break;
                }

            } else {
                showWarningToast("Seems you are not connected to the internet. " +
                        "Please check your connection and try again. Thank you.");
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            showErrorGlobalDialogs("An error has occurred. Please try again.");
        }
    };

    //update profile picture
    private void updateProfilePicV2(){

        progress.setVisibility(View.VISIBLE);
        mImgViewDisplayPicture.setVisibility(View.GONE);

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("profilepicurl", imageurl);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        updateIndex = CommonFunctions.parseJSON(jsonString, "index");
        updateAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        parameters.put("index", updateIndex);

        String paramJson = new Gson().toJson(parameters, Map.class);
        updateKeyEncryption = CommonFunctions.getSha1Hex(updateAuthenticationid + sessionid + "updateProfilePicV2");
        updateParam = CommonFunctions.encryptAES256CBC(updateKeyEncryption, String.valueOf(paramJson));

        updateProfilePicV2Object(updateProfilePicV2Callback);

    }
    private void updateProfilePicV2Object(Callback<GenericResponse> updateProfilePicCallback) {
        Call<GenericResponse> call = RetrofitBuilder.getAccountV2API(getViewContext())
                .updateProfilePicV2(
                        updateAuthenticationid, sessionid, updateParam);
        call.enqueue(updateProfilePicCallback);
    }
    private Callback<GenericResponse> updateProfilePicV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            if(response.errorBody() == null){
                String message = CommonFunctions.decryptAES256CBC(updateKeyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    db.updateProfilePic(db, imageurl, mSubscriber.getMobileNumber());
                    recreate();
                } else if (response.body().getStatus().equals("003")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else {
                    showErrorGlobalDialogs(message);
                }
            }else {
                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

    private void uploadFileV2(){

    }
}
