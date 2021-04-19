package com.goodkredit.myapplication.activities;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.account.CodeVerificationActivity;
import com.goodkredit.myapplication.activities.account.GuarantorVerificationActivity;
import com.goodkredit.myapplication.activities.account.SetupPasswordActivity;
import com.goodkredit.myapplication.activities.account.SignInActivity;
import com.goodkredit.myapplication.activities.account.SignUpActivity;
import com.goodkredit.myapplication.activities.account.WelcomePageActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.gkapplication.CheckAppVersionResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.rohitss.uceh.UCEHandler;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*
* Algo:
* Check if account is login (via SQLITE)
* Check the statuses:
*       // If status is LOGIN - open Main Page
        // If empty - open Sign In Page
        // If FORVERIFICATION - Open Verification Code
* */


public class SplashScreenActivity extends BaseActivity {

    //Declaration here
    private DatabaseHandler db;

    private String STATUS = "";
    private String borroweridval = "";
    private String userid = "";
    private String sessionidval = "";
    private String imei = "";
    //private ImageView splash;
    private ImageView country;
    private TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Initialize Database
        db = new DatabaseHandler(getViewContext());
//        splash = (ImageView) findViewById(R.id.splash);
        country = findViewById(R.id.country);



        //for dev purposes
        if(CommonVariables.isDebugMode){
            new UCEHandler.Builder(this)
                    .setTrackActivitiesEnabled(true)
                    .build();
        }

        Glide.with(getViewContext())
                .load(R.drawable.flag)
                .into(country);

        KenBurnsView splash = findViewById(R.id.splash);
        Glide.with(getViewContext())
                .load(R.drawable.mapphil)
                .into(splash);

        ImageView imageView1 = findViewById(R.id.imageView1);
        Glide.with(getViewContext())
                .load(R.drawable.gk_new_logo)
                .into(imageView1);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 000);
        } else {
            imei = CommonFunctions.getImei(getViewContext());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openPage();
                }
            }, 4000);
        }

        //1.check status of the account
        Cursor cursor = db.isLogin(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                STATUS = cursor.getString(cursor.getColumnIndex("status"));
                borroweridval = cursor.getString(cursor.getColumnIndex("borrowerid"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        checkPreferenceUpdates();
    } //end on create


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            imei = CommonFunctions.getImei(getViewContext());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openPage();
                }
            }, 2000);
        } else {
            showToast("Please allow permission to use GoodKredit Application.", GlobalToastEnum.NOTICE);
            finish();
        }
    }

    public void openPage() {
//        if (DeviceRoot.isDeviceRooted(getViewContext())) {
//            deviceIsRootedDialog("The system detected that your device is being rooted. " +
//                    "You can't use this application. ");
//            clearAllDataIfDeviceIsRooted();
//        } else {
//
//        }

        if(CommonVariables.link.contains("staging") && CommonVariables.securedLink.contains("staging")){
            createNotification("Staging Build");
        }else if(CommonVariables.link.contains("172.16.16") && CommonVariables.securedLink.contains("172.16.16")){
            createNotification("Development Build");
        }

        switch (STATUS) {
            case "LOGIN":
                openMainPage();
                break;
            case "FORVERIFICATION":
                openVerificationCode();
                break;
            case "PASSWORDSETUP":
                openPasswordSetUp();
                break;
            case "GUARANTORSETUP":
                openGuarantorSetUp();
                break;
            case "WELCOME":
                openWelcomePage();
                break;
            case "FORVERIFICATIONFORGETPASSWORD":
                openVerificationForgetPassword();
                break;
            case "PASSWORDSETUPFORGETPASSWORD":
                openPasswordSetupForgetPassword();
                break;
            default:
                openSignInPage();
                break;
        }
    }

    private void checkAppVersionV2() {
        Call<CheckAppVersionResponse> call = RetrofitBuild.getSoaApiService(getViewContext())
                .checkAppVersionV3(
                        imei,
                        CommonVariables.version,
                        CommonFunctions.getSha1Hex(imei + CommonVariables.version)
                );
        call.enqueue(checkAppVersionV3Callback);
    }

    private Callback<CheckAppVersionResponse> checkAppVersionV3Callback = new Callback<CheckAppVersionResponse>() {
        @Override
        public void onResponse(Call<CheckAppVersionResponse> call, Response<CheckAppVersionResponse> response) {
            ResponseBody errBody = response.errorBody();
            if (errBody == null) {
                if (response.body().getStatus().equals("000")) {
                    openPage();
                } else if (response.body().getStatus().equals("003")) {
                    showAlertDialog();
                }
            }
        }

        @Override
        public void onFailure(Call<CheckAppVersionResponse> call, Throwable t) {
            if (isFreeModeEnabled())
                openPage();
        }
    };

    private void showAlertDialog() {
        try {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Information");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("We detected that your app is outdated. We recommend that you update your app to enjoy our new features and to make sure that everything will work smoothly.");
            alertDialog.setPositiveButton("DOWNLOAD", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    final String appPackageName = getPackageName();
                    try {
                        Intent appStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
                        appStoreIntent.setPackage("com.goodkredit.myapplication");
                        startActivity(appStoreIntent);
                    } catch (android.content.ActivityNotFoundException exception) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }

                    finish();
                }
            });

            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    public void openSignInPage() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openVerificationCode() {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openVerificationForgetPassword() {
        Intent intent = new Intent(this, CodeVerificationActivity.class);
        intent.putExtra("FROM", "FORGETPASSWORD");
        intent.putExtra("PREFIX", "");
        intent.putExtra("MOBILE", "");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openPasswordSetUp() {
        Intent intent = new Intent(this, SetupPasswordActivity.class);
        intent.putExtra("FROM", "");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openPasswordSetupForgetPassword() {
        Intent intent = new Intent(this, SetupPasswordActivity.class);
        intent.putExtra("FROM", "FORGETPASSWORD");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openGuarantorSetUp() {
        Intent intent = new Intent(this, GuarantorVerificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openWelcomePage() {
        Intent intent = new Intent(this, WelcomePageActivity.class);
        intent.putExtra("OTHERS", "");
        intent.putExtra("SUBJECT", "");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void deviceIsRootedDialog(String message) {
        try {
            GlobalDialogs dialog = new GlobalDialogs(getViewContext());

            dialog.createDialog("Close", message,
                    "OK", "", ButtonTypeEnum.SINGLE,
                    false, false, DialogTypeEnum.NOTICE);

            dialog.hideCloseImageButton();

            View singlebtn = dialog.btnSingle();
            singlebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    private void clearAllDataIfDeviceIsRooted() {
        //THIS IS FOR EXISTING USERS WITH ROOT DEVICES. CLEAR ALL DATA.
        if(db != null) {
            db.Logout(db);
        }
        PreferenceUtils.clearPreference(getViewContext());
        CommonVariables.clearVariables();
        CacheManager.getInstance().clearCache();
    }

    private void checkPreferenceUpdates() {
        try {
            int versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            int gkVersionCode = PreferenceUtils.getGKVersionCode(getViewContext());

            //FOR OLD VERSIONS OF APP SINCE WE  ARE SAVING A PREF FOR THE VERSION CODE
            if(gkVersionCode <= 0) {
                if(versionCode == 96) {
                    PreferenceUtils.clearPreference(getViewContext());
                }
            } else {
                //ADD THE CHANGES TO YOUR PREFERENCE HERE. (REMOVE AND SAVE)
                //ONLY IS USED IF YOU ARE CHANGING THE DATA TYPE OF A EXISTING PREFERENCE.
                if(gkVersionCode < 96) {

                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createNotification(String message)
    {

        String CHANNEL_ID = "gkph_channel_test";
        CharSequence name = "gkph";
        String Description = "testing channel";

        int NOTIFICATION_ID = 234;

        NotificationManagerCompat  notificationManager = NotificationManagerCompat.from(this);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(mChannel);

        }

        Intent resultIntent = new Intent(this, SplashScreenActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SplashScreenActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Goodkredit PH")
                .setOngoing(true)
                .setContentText(message)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSmallIcon(R.drawable.img_floating_gk_logo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .setColor(getResources().getColor(android.R.color.holo_blue_light));


        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

}
