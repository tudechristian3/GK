package com.goodkredit.myapplication.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.account.SignInActivity;
import com.goodkredit.myapplication.activities.account.WelcomePageActivity;
import com.goodkredit.myapplication.activities.csbrewards.CSBSettingsChangeMobileConfimationActivity;
import com.goodkredit.myapplication.activities.notification.NotificationGKAdsActivity;
import com.goodkredit.myapplication.activities.notification.NotificationProcessSuccessActivity;
import com.goodkredit.myapplication.activities.notification.NotificationReferralSuccessActivity;
import com.goodkredit.myapplication.activities.notification.NotificationResellerReferralSuccessActivity;
import com.goodkredit.myapplication.activities.notification.NotificationsActivity;
import com.goodkredit.myapplication.activities.profile.V2EditProfileActivity;
import com.goodkredit.myapplication.activities.profile.V2ViewProfileActivity;
import com.goodkredit.myapplication.activities.projectcoop.ProjectCoopActivity;
import com.goodkredit.myapplication.activities.settings.SupportSendMessageActivity;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.activities.transactions.V2ProcessQueueActivity;
import com.goodkredit.myapplication.activities.vouchers.grouping.GroupVoucherActivity;
import com.goodkredit.myapplication.activities.whatsnew.GKAdsActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.SkycableConversation;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.EGamePreloadedData;
import com.goodkredit.myapplication.common.PreloadedData;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.MainDrawerFragment;
import com.goodkredit.myapplication.fragments.MerchantsCategoryFragment;
import com.goodkredit.myapplication.fragments.ReferAFriendFragment;
import com.goodkredit.myapplication.fragments.StatementOfAccountFragment;
import com.goodkredit.myapplication.fragments.TransactionsFragment;
import com.goodkredit.myapplication.fragments.V2PublicSponsorsFragment;
import com.goodkredit.myapplication.fragments.V2SettingsFragment;
import com.goodkredit.myapplication.fragments.V2SupportFragment;
import com.goodkredit.myapplication.fragments.VouchersFragment;
import com.goodkredit.myapplication.fragments.WhatsNewFragment;
import com.goodkredit.myapplication.fragments.coopassistant.support.CoopAssistantSendMessageFragment;
import com.goodkredit.myapplication.fragments.mdp.MDPSupportSendMessageFragment;
import com.goodkredit.myapplication.fragments.rfid.RFIDFragment;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniSendMessageFragment;
import com.goodkredit.myapplication.fragments.services.ServicesFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableSupportSendMessageFragment;
import com.goodkredit.myapplication.fragments.vouchers.payoutone.VoucherPayoutOneBankDepositFragment;
import com.goodkredit.myapplication.gcmpush.RegistrationIntentService;
import com.goodkredit.myapplication.model.GKProcessRequest;
import com.goodkredit.myapplication.model.SupportConversation;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.model.V2SubscriberDetails;
import com.goodkredit.myapplication.model.egame.EGameProducts;
import com.goodkredit.myapplication.model.gkads.GKAds;
import com.goodkredit.myapplication.model.prepaidload.PreloadedDataNew;
import com.goodkredit.myapplication.model.prepaidload.PreloadedPrefix;
import com.goodkredit.myapplication.model.prepaidload.PreloadedSignature;
import com.goodkredit.myapplication.model.prepaidload.networkcodes.PreloadedGlobe;
import com.goodkredit.myapplication.model.prepaidload.networkcodes.PreloadedSmart;
import com.goodkredit.myapplication.model.prepaidload.networkcodes.PreloadedSun;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetPayload;
import com.goodkredit.myapplication.responses.PayloadDetails;
import com.goodkredit.myapplication.responses.prepaidload.GetProductCodesResponse;
import com.goodkredit.myapplication.responses.support.GetSupportConversationResponseData;
import com.goodkredit.myapplication.responses.support.GetSupportMessagesResponseData;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.ResponseBody;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdk;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionEvent;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.goodkredit.myapplication.fragments.services.ServicesFragment.hasNewVoucher;

public class MainActivity extends BaseActivity implements MainDrawerFragment.FragmentDrawerListener, FreenetSdkConnectionListener, View.OnClickListener {

    public static String KEY_VIEW = "view";

    private Toolbar mToolbar;
    private MainDrawerFragment drawerFragment;
    private DatabaseHandler db;
    private CommonVariables cv;
    private CommonFunctions cf;
    private Context mcontex;

    private RelativeLayout nav_header_container;
    private TextView profilename;
    private TextView profilenum;
    private ImageView profileimage;
    private String borrowerid = "";
    private String imei = "";
    private String guarantorStatus;

    private Gson gson;
    private boolean isClicked = false;
    private boolean isShownOnce = false;

    private AlertDialog dialog;

    private AQuery aq;
    private Bitmap icon;

    private int view = 0;

    private GPSTracker mGPSTracker;
    private double mLongitude;
    private double mLatitude;

    //DEFAULT LONG LATS
    private String defaultmLatitude = "10.315699";
    private String defaultmLongitude = "123.885437";

    //UNIFIED SESSIONID
    private String sessionid = "";

    private static MainActivity mainActivity;
    private static Context mainActivityContext;

    //PRELOADED
    private List<PreloadedPrefix> loadWalletPrefixList;
    private List<PreloadedPrefix> networkPrefixList;
    private List<PreloadedGlobe> preloadedGlobeList;
    private List<PreloadedSmart> preloadedSmartList;
    private List<PreloadedSun> preloadedSunList;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String getProductIndex;
    private String getProductAuthenticationid;
    private String getProductKeyEncryption;
    private String getProductParam;

    private String checkCSBRequestIndex;
    private String checkCSBRequestAuthcode;
    private String checkCSBRequestKeyEncryption;
    private String checkCSBRequestParam;

    private String checkAppVersionIndex;
    private String checkAppVersionAuthID;
    private String checkAppVersionKey;
    private String checkAppVersionParam;

    private String getGKAdsIndex;
    private String getGKAdsAuthID;
    private String getGKAdsKey;
    private String getGKAdsParam;

    AlertDialog levelDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {

            if(PreferenceUtils.getStringPreference(getViewContext(),"EGAMEPRELOADEDDATA").length() <= 0){
                PreferenceUtils.removePreference(getViewContext(), "EGAMEPRELOADEDDATA");
                PreferenceUtils.saveStringPreference(getViewContext(), "EGAMEPRELOADEDDATA", EGamePreloadedData.egame);
            }


            mainActivity = this;
            mainActivityContext = this;

            if (isFreeModeEnabled()) {
                FreenetSdk.registerConnectionListener(this);
                activateFreeNet();
            }

            imei = CommonFunctions.getImei(getViewContext());

            mGPSTracker = new GPSTracker(this);

            mcontex = getViewContext();
            mToolbar = findViewById(R.id.toolbar);
            db = new DatabaseHandler(getViewContext());
            aq = new AQuery(getViewContext());

            //UNIFIED SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            gson = new Gson();

            icon = BitmapFactory.decodeResource(getViewContext().getResources(),
                    R.drawable.emptyprofilepic);

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            profilename = findViewById(R.id.profilename);
            profilenum = findViewById(R.id.profilenumber);
            profileimage = findViewById(R.id.profileimage);
            nav_header_container = findViewById(R.id.nav_header_container);

            drawerFragment = (MainDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
            drawerFragment.setDrawerListener(this);

            if (getIntent().hasExtra(KEY_VIEW)) {
                view = getIntent().getIntExtra(KEY_VIEW, 0);
                displayView(view);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerFragment.selectItem(view);
                    }
                }, 100);


            } else {
                // display the first navigation drawer view on app launch
                displayView(view);
            }

            //get account information
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    guarantorStatus = cursor.getString(cursor.getColumnIndex("guarantorregistrationstatus"));

                } while (cursor.moveToNext());
            }
            cursor.close();

            //PUSH NOTIFICATION
            registerReceiver();
            if (checkPermission()) {
                Logger.debug("okhttp", "checkpermission true");
                startRegisterService();
            } else {
                Logger.debug("okhttp", "checkpermission false");
                requestPermission();
            }

            findViewById(R.id.freeModeButton).setOnClickListener(this);

            dialog = new AlertDialog.Builder(mcontex)
                    .setMessage("Unable to connect. Free mode works only on SMART, SUN and TNT subscribers. It only works in primary sim (SIM1).")
                    .setPositiveButton("Got It", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();

            logDeviceInformation();

            AtomicBoolean processing = new AtomicBoolean(true);
            new Executor.Builder()
                    .add(() -> {
                       Logger.debug("okhttp","checkCSBChangeMobileRequestV2  Start");
                        try {
                            Thread.sleep(2000);
                            checkCSBChangeMobileRequestV2();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Logger.debug("okhttp","checkCSBChangeMobileRequestV2 Complete");
                    })
                    .add(() -> {
                        Logger.debug("okhttp","getSubscribersProfile Start");
                        try {
                            Thread.sleep(500);
                            getSubscribersProfile();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Logger.debug("okhttp","getSubscribersProfile Complete");
                    })
                    .add(() -> {
                        Logger.debug("okhttp","checkAppVersionV2 Start");
                        try {
                            Thread.sleep(1000);
                            checkAppVersionV2();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Logger.debug("okhttp","checkAppVersionV2() Complete");
                    }).add(() -> {
                            Logger.debug("okhttp"," preLoadData() Start");
                            try {
                                Thread.sleep(1000);
                                preLoadData();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Logger.debug("okhttp"," preLoadData() Complete");
                        })
                    .add(() -> {
                        Logger.debug("okhttp","preLoadEGameData Start");
                        try {
                            Thread.sleep(1000);
                            preLoadEGameData();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Logger.debug("okhttp","preLoadEGameData Complete");
                    })
                    .callback(() -> {
                        Logger.debug("okhttp","All TASK COMPLETED");
                        processing.set(false);
                    })
                    .build()
                    .execute();

            while (processing.get()) {

            }
            // preLoadData();
            //CHECK for CSB REQ
            //checkCSBChangeMobileRequest();
            //checkCSBChangeMobileRequestV2();
            //getSubscribersProfile();
            //preLoadData();
            //checkAppVersionV3();
            //checkAppVersionV2();

            if (!PreferenceUtils.getBooleanPreference(getViewContext(), PreferenceUtils.KEY_GK_ADS_DISALLOW_POP_UP))
                //getGKAds();
                getGKAdsV2();
            

            if (mGPSTracker != null) {
                PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_LAST_KNOWN_LONGITUDE);
                PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_LAST_KNOWN_LATITUDE);
                if (mGPSTracker.getLongitude() == 0.0 || mGPSTracker.getLatitude() == 0.0) {
                    PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_LAST_KNOWN_LONGITUDE, defaultmLongitude);
                    PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_LAST_KNOWN_LATITUDE, defaultmLatitude);
                } else {
                    PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_LAST_KNOWN_LONGITUDE, String.valueOf(mGPSTracker.getLongitude()));
                    PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_LAST_KNOWN_LATITUDE, String.valueOf(mGPSTracker.getLatitude()));
                }
            } else {
                PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_LAST_KNOWN_LONGITUDE, defaultmLongitude);
                PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_LAST_KNOWN_LATITUDE, defaultmLatitude);
            }
				checkSubscriberProfileFirstandLastName();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preLoadEGameData(){

        if(db.getEGameProducts(db,0).size() <= 0){
            List<EGameProducts> eGameProductsList = new Gson().fromJson(EGamePreloadedData.egame, new TypeToken<List<EGameProducts>>() {
            }.getType());

            Logger.debug("vanhttp", "preload eGameProductsList: " + new Gson().toJson(eGameProductsList));
            for(EGameProducts eGameProducts : eGameProductsList){
                db.insertEGameProducts(db, eGameProducts);
            }
        }
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static Context getMainActivityContext() {
        return mainActivityContext;
    }

    public static void start(Context context, int menu) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_VIEW, menu);
        context.startActivity(intent);
    }

    @Override
    public void onResume() {
        try {
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String firstname = cursor.getString(cursor.getColumnIndex("firstname"));
                    String lastname = cursor.getString(cursor.getColumnIndex("lastname"));
                    String name = firstname + " " + lastname;
                    String mobile = cursor.getString(cursor.getColumnIndex("mobile"));
                    String profilepic = cursor.getString(cursor.getColumnIndex("profilepic"));

                    try {
                        profilename.setText(name.toUpperCase());
                        profilenum.setText(String.format("+%s", mobile));

                        Glide.with(getViewContext())
                                .load(profilepic)
                                .apply(RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.emptyprofilepic))
                                .into(profileimage);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            //show overlay if mobile data on and valid operator
            setupOverlay();
            //set overlay messages
            setOverlayGUI(PreferenceUtils.getIsFreeMode(getViewContext()));
            //GK_Services
//            getGoodKreditServices();
            //Preloaded
            preLoadData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    @Override
    public void onBackPressed() {
        //closeAppDialog();
        closeAppDialogNew();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    public void openProfile(View view) {
        try {
            V2ViewProfileActivity.start(getViewContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int position;

    public void displayView(int position) {
        if (position != 9)
            this.position = position;
        try {
            Fragment fragment = null;
            String title = "Voucher";
            switch (position) {
                case 0:
//                    fragment = new VouchersFragment();
//                    title = "Vouchers";
                    fragment = new ServicesFragment();
                    title = "Services";
                    break;
                case 1:
                    fragment = new VouchersFragment();
                    title = "Vouchers";
                    break;
                case 2:
                    fragment = new RFIDFragment();
                    title = "RFID Information";
                    break;
                case 3:
                    fragment = new WhatsNewFragment();
                    title = "What's New";
                    break;
                case 4:
                    fragment = new MerchantsCategoryFragment();
                    title = "Merchants";
                    break;
                case 5:
                    fragment = new VoucherPayoutOneBankDepositFragment();
                    title = "Bank Deposit";
                    break;
                case 6:
                    fragment = new TransactionsFragment();
                    title = "Transactions";
                    break;
                case 7:
                    fragment = new StatementOfAccountFragment();
                    title = "Statement of Account";
                    break;
                case 8:
//                    fragment = new PublicSponsorsFragment();
                    fragment = new V2PublicSponsorsFragment();
                    title = "Public Sponsors";
                    break;
                case 9:
                    fragment = new ReferAFriendFragment();
                    title = "Refer A Friend";
                    break;
                case 10:
                    fragment = new V2SupportFragment();
                    title = "Support";
                    break;
                case 11:
                    fragment = new V2SettingsFragment();
                    title = "Settings";
                    break;
                case 12:
                    //logout();
                    logoutNew();
                    break;

                default:
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();

                // set the toolbar title
                Objects.requireNonNull(getSupportActionBar()).setTitle(title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void logoutNew() {
        try {
            GlobalDialogs dialog = new GlobalDialogs(getViewContext());
            dialog.createDialog("Logout", "Are you sure you want to logout?",
                    "Cancel", "OK", ButtonTypeEnum.DOUBLE,
                    false, false, DialogTypeEnum.NOTICE);

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
                    showProgressDialog(getViewContext(), "", "Logging you out. Please wait ...");
                    new LogoutTaskRunner().execute("");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class LogoutTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            unsubscribePush();
            //clear variable
            CommonVariables.clearVariables();
            //clear data
            db.Logout(db);

            PreferenceUtils.clearPreference(getViewContext());
            CacheManager.getInstance().clearCache();
            if (isFreeModeEnabled()) {
                deactivateFreeNet();
                FreenetSdk.unregisterConnectionListener(MainActivity.this);
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            //open login page
            hideProgressDialog();
            Intent intent = new Intent(getBaseContext(), SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        }

    }

    //IF SESSION ISINVALID AUTOLOGOUT FOR USER
    public void inValidSessionWillNowLogout() {
        try {
            unsubscribePush();
            //clear variable
            CommonVariables.clearVariables();
            //clear data
            db.Logout(db);
            PreferenceUtils.clearPreference(getViewContext());
            CacheManager.getInstance().clearCache();
            if (isFreeModeEnabled()) {
                deactivateFreeNet();
                FreenetSdk.unregisterConnectionListener(MainActivity.this);
            }
            //open login page
            Intent intent = new Intent(getBaseContext(), SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
            showNoInternetToast();
        }
    }

    public void closeAppDialog() {
        try {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Close");
            alertDialog.setMessage("Are you sure you want to close this app?");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (isFreeModeEnabled()) {
                        deactivateFreeNet();
                        FreenetSdk.unregisterConnectionListener(MainActivity.this);
                    }
                    dialog.dismiss();
                    finish();
                }
            });

            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeAppDialogNew() {
        try {
            GlobalDialogs dialog = new GlobalDialogs(getViewContext());

            dialog.createDialog("Close", "Are you sure you want to close this app?",
                    "Cancel", "OK", ButtonTypeEnum.DOUBLE,
                    false, false, DialogTypeEnum.NOTICE);

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
                    if (isFreeModeEnabled()) {
                        deactivateFreeNet();
                        FreenetSdk.unregisterConnectionListener(MainActivity.this);
                    }
                    finish();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Push Notification HERE
    private void startRegisterService() {
        Logger.debug("okhttp", "startRegisterService()");
        try {
            Intent intent = new Intent(MainActivity.this, RegistrationIntentService.class);
            intent.putExtra("BORROWERID", borrowerid);
            intent.putExtra("IMEI", imei);
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private LocalBroadcastManager bManager;

    private void registerReceiver() {

        Logger.debug("okhttp", "registerReceiver()");

        try {
            bManager = LocalBroadcastManager.getInstance(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(CommonVariables.REGISTRATION_PROCESS);
            intentFilter.addAction(CommonVariables.MESSAGE_RECEIVED);
            bManager.registerReceiver(broadcastReceiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlertDialog(String message) {

        try {
            CommonVariables.notifCount += 1;
            log(String.valueOf(CommonVariables.notifCount));
            invalidateOptionsMenu();

            JSONObject mainObject = new JSONObject(message);
            JSONObject payload = mainObject.getJSONObject("payload");
            String txnno = payload.getString("txnno");
            String subject = payload.getString("subject");
            String from = payload.getString("sender");
            String fromicon = payload.getString("senderlogo");
            String curmessage = payload.getString("message");
            String date = payload.getString("date");
            db.insertNotification(db, txnno, subject, "0", from, fromicon, curmessage, date);

            curmessage = CommonFunctions.replaceEscapeData(curmessage);

            if (subject.equals("APPROVEREQUEST")) {
                String credit = curmessage.substring(curmessage.indexOf("[") + 1, curmessage.indexOf("]"));
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(this);
                }
                Intent intent = new Intent(this, WelcomePageActivity.class);
                intent.putExtra("OTHERS", credit);
                intent.putExtra("SUBJECT", subject);
                startActivity(intent);


                //update registration status of borrower
                db.updateRegistrationStatus(db, "APPROVED", borrowerid);
            } else if (subject.equals("DECLINEREQUEST")) {
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(this);
                }
                Intent intent = new Intent(this, WelcomePageActivity.class);
                intent.putExtra("OTHERS", "");
                intent.putExtra("SUBJECT", subject);
                startActivity(intent);


                //update registration status of borrower
                db.updateRegistrationStatus(db, "DECLINED", borrowerid);
                //delete guarantor code since it was declined
                db.deleteGuarantorCode(db, borrowerid);
            } else if (subject.equals("SUPPORTMESSAGE")) {
                GetPayload getPayload = gson.fromJson(String.valueOf(mainObject), GetPayload.class);
                PayloadDetails pldeatials = getPayload.getPayloadDetails();
                if (!db.alreadyExist(db, pldeatials.getMessage(), pldeatials.getThreadid()))
                    db.insertMessagesConversation(db, new GetSupportConversationResponseData(".", pldeatials.getDate(), pldeatials.getThreadid()
                            , pldeatials.getSupportid(), pldeatials.getSupportname(), "1", pldeatials.getMessage()));

                if (SupportSendMessageActivity.isActive) {

                    log("ACTIVITY " + String.valueOf(SupportSendMessageActivity.isActive));
                    SupportSendMessageActivity.staticAdapter.update(db.getConversation(db, pldeatials.getThreadid()));
                    SupportSendMessageActivity.staticList.scrollToPosition(SupportSendMessageActivity.staticAdapter.getItemCount() - 1);
                    SupportSendMessageActivity.staticLinearManager.fullScroll(View.FOCUS_DOWN);
                } else if (V2SupportFragment.isActive) {
                    //to be continued
                    ArrayList<String> title = new ArrayList<>();
                    HashMap<String, ArrayList<GetSupportMessagesResponseData>> details = new HashMap<>();
                    db.updateSupportMessagesThreadsReadStatus(db, "0", pldeatials.getThreadid());
                    if (!db.getAllMessagesThreads(db, "OPEN").isEmpty()) {
                        title.add("OPEN");
                        details.put("OPEN", db.getAllMessagesThreads(db, "OPEN"));
                    }
                    if (!db.getAllMessagesThreads(db, "CLOSED").isEmpty()) {
                        title.add("CLOSED");
                        details.put("CLOSED", db.getAllMessagesThreads(db, "CLOSED"));
                    }
                    V2SupportFragment.staticAdapter.update(title, details);
                } else if (SkycableSupportSendMessageFragment.isActive) {
                    if (db != null) {
                        if (!pldeatials.getThreadid().substring(0, 4).equals("SPTN")) {
                            db.insertSkycableConversations(db, new SkycableConversation(pldeatials.getDate(), pldeatials.getThreadid(), pldeatials.getSupportid(), pldeatials.getSupportname(), "1", pldeatials.getMessage()));
                            SkycableSupportSendMessageFragment.staticmAdapter.update(db.getSkycableConversations(db, pldeatials.getThreadid()));
                            SkycableSupportSendMessageFragment skycableSupportSendMessageFragment = new SkycableSupportSendMessageFragment();
                            skycableSupportSendMessageFragment.scrollDown();
                        }
                    }
                } else if (SchoolMiniSendMessageFragment.isActive) {
                    if (db != null) {
                        if (!pldeatials.getThreadid().substring(0, 4).equals("SPTN")) {
                            db.insertSchoolMiniConversation(db, new SupportConversation(pldeatials.getDate(), pldeatials.getThreadid(), pldeatials.getSupportid(), pldeatials.getSupportname(), "1", pldeatials.getMessage()));
                            SchoolMiniSendMessageFragment.staticmAdapter.update(db.getSchoolConversation(db, pldeatials.getThreadid()));
                            SchoolMiniSendMessageFragment schoolminisendmessagefragment = new SchoolMiniSendMessageFragment();
                            schoolminisendmessagefragment.scrollDown();
                            SchoolMiniSendMessageFragment.recyclerViewMessageThread.scrollToPosition(SupportSendMessageActivity.staticAdapter.getItemCount() - 1);
                            SchoolMiniSendMessageFragment.scrollViewMessages.fullScroll(View.FOCUS_DOWN);
                        }
                    }
                } else if (MDPSupportSendMessageFragment.isActive) {
                    if (db != null) {
                        if (!pldeatials.getThreadid().substring(0, 4).equals("SPTN")) {
                            db.insertMDPSupportConversation(db, new SupportConversation(pldeatials.getDate(), pldeatials.getThreadid(), pldeatials.getSupportid(), pldeatials.getSupportname(), "1", pldeatials.getMessage()));
                            MDPSupportSendMessageFragment.staticmAdapter.update(db.getMDPSupportConversation(db, pldeatials.getThreadid()));
                            MDPSupportSendMessageFragment mdpsupportsendmessagefragment = new MDPSupportSendMessageFragment();
                            mdpsupportsendmessagefragment.scrollDown();
                        }
                    }
                } else if (CoopAssistantSendMessageFragment.isActive) {
                    if (db != null) {
                        if (!pldeatials.getThreadid().substring(0, 4).equals("SPTN")) {
                            db.insertCoopAssistantConversation(db, new SupportConversation(pldeatials.getDate(), pldeatials.getThreadid(), pldeatials.getSupportid(), pldeatials.getSupportname(), "1", pldeatials.getMessage()));

                            CoopAssistantSendMessageFragment.staticmAdapter.update(db.getCoopAssistantConversation(db, pldeatials.getThreadid()));
                            CoopAssistantSendMessageFragment coopAssistantSendMessageFragment = new CoopAssistantSendMessageFragment();
                            coopAssistantSendMessageFragment.scrollDown();
                            SchoolMiniSendMessageFragment.recyclerViewMessageThread.scrollToPosition(SupportSendMessageActivity.staticAdapter.getItemCount() - 1);
                            SchoolMiniSendMessageFragment.scrollViewMessages.fullScroll(View.FOCUS_DOWN);
                        }
                    }
                }

            } else if (subject.contains("BILLS PAYMENT") && CommonVariables.PROCESSNOTIFICATIONINDICATOR == "") {
                NotificationProcessSuccessActivity.start(getViewContext(), message);
            } else if (subject.contains("PREPAID LOAD") && CommonVariables.PROCESSNOTIFICATIONINDICATOR == "") {
                NotificationProcessSuccessActivity.start(getViewContext(), message);
            } else if (subject.contains("CHANGE APPLICATION STATUS REQUEST") ||
                    subject.contains("LINK ACCOUNT REQUEST") ||
                    subject.contains("PPV SUBSCRIPTION REQUEST") ||
                    (from.contains("SKYCABLE") && subject.contains("BROADCAST"))) {
                Bundle args = new Bundle();
                args.putString("MESSAGE", curmessage);
                args.putString("SUBJECT", subject);
                SkyCableActivity.start(getViewContext(), 13, args);
            } else if (subject.contains("CPMPC")) {
                Bundle args = new Bundle();
                args.putString("MESSAGE", curmessage);
                args.putString("SUBJECT", subject);
                ProjectCoopActivity.start(getViewContext(), 8, args);
            } else if (subject.equals("REFERRALPROMO")) {
                NotificationReferralSuccessActivity.start(getViewContext(), curmessage);
            } else if (subject.equals("RESELLERREFERRALPROMO")) {
                NotificationResellerReferralSuccessActivity.start(getViewContext(), curmessage, subject);
            } else if (subject.equals("UNILEVELREFERRALPROMO")) {
                NotificationResellerReferralSuccessActivity.start(getViewContext(), curmessage, subject);
            } else if (from.equals("GOODKREDIT ADS")) {
                NotificationGKAdsActivity.start(getViewContext(), curmessage, subject, fromicon);
                PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_GK_ADS_DISALLOW_POP_UP, false);
            } else if (subject.equals("RELEASEVOUCHER") || subject.equals("TRANSFERVOUCHER")) {
                if (CommonFunctions.isAppRunning(getViewContext(), "com.goodkredit.myapplication")) {
                    hasNewVoucher();
                }
            } else if (subject.equals("GOODKREDIT")) {
                if (!curmessage.contains("You have successfully transferred")) {
                    if (CommonFunctions.isAppRunning(getViewContext(), "com.goodkredit.myapplication")) {
                        hasNewVoucher();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Logger.debug("okhttp", "broadcastReceiver");

            try {
                if (intent.getAction().equals(CommonVariables.REGISTRATION_PROCESS)) {
                    String result = intent.getStringExtra("result");
                    String message = intent.getStringExtra("message");

                } else if (intent.getAction().equals(CommonVariables.MESSAGE_RECEIVED)) {
                    String message = intent.getStringExtra("message");
                    Logger.debug("okhttp", "message: " + message);
                    showAlertDialog(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private boolean checkPermission() {
        boolean isTrue = false;
        try {
            int result = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE);
            isTrue = result == PackageManager.PERMISSION_GRANTED;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, CommonVariables.PERMISSION_REQUEST_CODE);
    }

    public void unsubscribePush() {
        try {
            new HttpAsyncTask().execute(CommonVariables.UNSUBSCRIBEPUSH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";

            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("registrationId", CommonVariables.SOCKETID);
                jsonObject.accumulate("borrowerid", borrowerid);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
            }
            return CommonFunctions.POST(urls[0], json);
        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        }
    }

    private class LogoutTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";

            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("registrationId", CommonVariables.SOCKETID);
                jsonObject.accumulate("borrowerid", borrowerid);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
            }
            return CommonFunctions.POST(urls[0], json);
        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        }
    }

    //CHECK FIRST THE FIRSTNAME AND LASTNAME OF SUBSCRIBER IS EMPTY
    private void checkSubscriberProfileFirstandLastName() {
        V2Subscriber mSubscriber = db.getSubscriber(db);
        String firstname = mSubscriber.getFirstName();
        String lastname = mSubscriber.getLastName();

        if (firstname.trim().equals("") || firstname.trim().equals(".")
                || lastname.trim().equals("") || lastname.trim().equals(".")) {
            PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_PROFILENOTCOMPLETE, true);
            V2EditProfileActivity.start(getViewContext(), mSubscriber);
        }
    }

    @Override
    public void onConnectionChange(FreenetSdkConnectionEvent freenetSdkConnectionEvent) {
        Logger.debug("getStatus", "STATUS: " + String.valueOf(freenetSdkConnectionEvent.getStatus()));
        Logger.debug("getErrorDescription", "Error Desc: " + String.valueOf(freenetSdkConnectionEvent.getErrorDescription()));
        Logger.debug("getErrorCode", "Error Code: " + String.valueOf(freenetSdkConnectionEvent.getErrorCode()));
        Logger.debug("toString", "toString: " + String.valueOf(freenetSdkConnectionEvent.toString()));

        if (freenetSdkConnectionEvent.getStatus() == 1) {
            hideProgressDialog();
            setOverlayGUI(true);
            if (isClicked) {
                PreferenceUtils.saveIsFreeMode(getViewContext(), true);
                isClicked = false;
            }
        } else if (freenetSdkConnectionEvent.getStatus() == 0) {
            hideProgressDialog();
            setOverlayGUI(false);
            if (isClicked) {
                PreferenceUtils.saveIsFreeMode(getViewContext(), false);
                isClicked = false;
            }
        } else {
            hideProgressDialog();
            switch (freenetSdkConnectionEvent.getErrorCode()) {
                case 4022:
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(MainActivity.this);

                    showToast("Please check network configuration on your device.", GlobalToastEnum.NOTICE);
                    break;
                case 4020: {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(MainActivity.this);
                    showToast("Unsupported network settings on the device.", GlobalToastEnum.WARNING);
                    showFreeModeAllowedDialogNew();
//                    if (!dialog.isShowing()) {
//                        showToast("Unsupported network settings on the device.", GlobalToastEnum.WARNING);
//                        showFreeModeAllowedDialog();
//                    }
                    break;
                }
                case 4023:
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(MainActivity.this);

                    showToast("Android version of the device is not supported", GlobalToastEnum.NOTICE);
                    break;
                case 4001:
                    showToast("Internal error occurred. (inc Params)", GlobalToastEnum.ERROR);
                    break;
                case 4002:
                    showToast("Internal error occurred. (auth failed Params)", GlobalToastEnum.ERROR);
                    break;
                case 4011:
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(MainActivity.this);

                    showToast("Internal error occurred. (invalid Params)", GlobalToastEnum.ERROR);
                    break;
                case 4012:
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(MainActivity.this);

                    showToast("Internal error occurred. (Expired) ", GlobalToastEnum.ERROR);
                    break;
                case 4021:
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(MainActivity.this);

                    showToast("Internal error occurred. (SDK_Exception) ", GlobalToastEnum.ERROR);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.freeModeButton) {
            showProgressDialog(getViewContext(), "Switching mode", "Please wait...");
            isClicked = true;
            if (PreferenceUtils.getIsFreeMode(getViewContext())) {
                log("deactivate");
                deactivateFreeNet();
                FreenetSdk.unregisterConnectionListener(this);
            } else {
                log("activate");
                FreenetSdk.registerConnectionListener(this);
                activateFreeNet();
            }
        }
    }

    private void showFreeModeAllowedDialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    private void showFreeModeAllowedDialogNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("Notice", " Unable to connect. Free mode works only on SMART, SUN " +
                        "and TNT subscribers. It only works in primary sim (SIM1).", "Got It",
                "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.NOTICE);

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bManager != null) {
            bManager.unregisterReceiver(broadcastReceiver);
        }

        if (isFreeModeEnabled()) {
            deactivateFreeNet();
            FreenetSdk.unregisterConnectionListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sortoption, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (position == 0) {
            switch (CommonVariables.notifCount) {
                case 0:
                    menu.findItem(R.id.action_notification_0).setVisible(true);
                    menu.findItem(R.id.action_notification_1).setVisible(false);
                    menu.findItem(R.id.action_notification_2).setVisible(false);
                    menu.findItem(R.id.action_notification_3).setVisible(false);
                    menu.findItem(R.id.action_notification_4).setVisible(false);
                    menu.findItem(R.id.action_notification_5).setVisible(false);
                    menu.findItem(R.id.action_notification_6).setVisible(false);
                    menu.findItem(R.id.action_notification_7).setVisible(false);
                    menu.findItem(R.id.action_notification_8).setVisible(false);
                    menu.findItem(R.id.action_notification_9).setVisible(false);
                    menu.findItem(R.id.action_notification_9plus).setVisible(false);
                    break;
                case 1:
                    menu.findItem(R.id.action_notification_0).setVisible(false);
                    menu.findItem(R.id.action_notification_1).setVisible(true);
                    menu.findItem(R.id.action_notification_2).setVisible(false);
                    menu.findItem(R.id.action_notification_3).setVisible(false);
                    menu.findItem(R.id.action_notification_4).setVisible(false);
                    menu.findItem(R.id.action_notification_5).setVisible(false);
                    menu.findItem(R.id.action_notification_6).setVisible(false);
                    menu.findItem(R.id.action_notification_7).setVisible(false);
                    menu.findItem(R.id.action_notification_8).setVisible(false);
                    menu.findItem(R.id.action_notification_9).setVisible(false);
                    menu.findItem(R.id.action_notification_9plus).setVisible(false);
                    break;
                case 2:
                    menu.findItem(R.id.action_notification_0).setVisible(false);
                    menu.findItem(R.id.action_notification_1).setVisible(false);
                    menu.findItem(R.id.action_notification_2).setVisible(true);
                    menu.findItem(R.id.action_notification_3).setVisible(false);
                    menu.findItem(R.id.action_notification_4).setVisible(false);
                    menu.findItem(R.id.action_notification_5).setVisible(false);
                    menu.findItem(R.id.action_notification_6).setVisible(false);
                    menu.findItem(R.id.action_notification_7).setVisible(false);
                    menu.findItem(R.id.action_notification_8).setVisible(false);
                    menu.findItem(R.id.action_notification_9).setVisible(false);
                    menu.findItem(R.id.action_notification_9plus).setVisible(false);
                    break;
                case 3:
                    menu.findItem(R.id.action_notification_0).setVisible(false);
                    menu.findItem(R.id.action_notification_1).setVisible(false);
                    menu.findItem(R.id.action_notification_2).setVisible(false);
                    menu.findItem(R.id.action_notification_3).setVisible(true);
                    menu.findItem(R.id.action_notification_4).setVisible(false);
                    menu.findItem(R.id.action_notification_5).setVisible(false);
                    menu.findItem(R.id.action_notification_6).setVisible(false);
                    menu.findItem(R.id.action_notification_7).setVisible(false);
                    menu.findItem(R.id.action_notification_8).setVisible(false);
                    menu.findItem(R.id.action_notification_9).setVisible(false);
                    menu.findItem(R.id.action_notification_9plus).setVisible(false);
                    break;
                case 4:
                    menu.findItem(R.id.action_notification_0).setVisible(false);
                    menu.findItem(R.id.action_notification_1).setVisible(false);
                    menu.findItem(R.id.action_notification_2).setVisible(false);
                    menu.findItem(R.id.action_notification_3).setVisible(false);
                    menu.findItem(R.id.action_notification_4).setVisible(true);
                    menu.findItem(R.id.action_notification_5).setVisible(false);
                    menu.findItem(R.id.action_notification_6).setVisible(false);
                    menu.findItem(R.id.action_notification_7).setVisible(false);
                    menu.findItem(R.id.action_notification_8).setVisible(false);
                    menu.findItem(R.id.action_notification_9).setVisible(false);
                    menu.findItem(R.id.action_notification_9plus).setVisible(false);
                    break;
                case 5:
                    menu.findItem(R.id.action_notification_0).setVisible(false);
                    menu.findItem(R.id.action_notification_1).setVisible(false);
                    menu.findItem(R.id.action_notification_2).setVisible(false);
                    menu.findItem(R.id.action_notification_3).setVisible(false);
                    menu.findItem(R.id.action_notification_4).setVisible(false);
                    menu.findItem(R.id.action_notification_5).setVisible(true);
                    menu.findItem(R.id.action_notification_6).setVisible(false);
                    menu.findItem(R.id.action_notification_7).setVisible(false);
                    menu.findItem(R.id.action_notification_8).setVisible(false);
                    menu.findItem(R.id.action_notification_9).setVisible(false);
                    menu.findItem(R.id.action_notification_9plus).setVisible(false);
                    break;
                case 6:
                    menu.findItem(R.id.action_notification_0).setVisible(false);
                    menu.findItem(R.id.action_notification_1).setVisible(false);
                    menu.findItem(R.id.action_notification_2).setVisible(false);
                    menu.findItem(R.id.action_notification_3).setVisible(false);
                    menu.findItem(R.id.action_notification_4).setVisible(false);
                    menu.findItem(R.id.action_notification_5).setVisible(false);
                    menu.findItem(R.id.action_notification_6).setVisible(true);
                    menu.findItem(R.id.action_notification_7).setVisible(false);
                    menu.findItem(R.id.action_notification_8).setVisible(false);
                    menu.findItem(R.id.action_notification_9).setVisible(false);
                    menu.findItem(R.id.action_notification_9plus).setVisible(false);
                    break;
                case 7:
                    menu.findItem(R.id.action_notification_0).setVisible(false);
                    menu.findItem(R.id.action_notification_1).setVisible(false);
                    menu.findItem(R.id.action_notification_2).setVisible(false);
                    menu.findItem(R.id.action_notification_3).setVisible(false);
                    menu.findItem(R.id.action_notification_4).setVisible(false);
                    menu.findItem(R.id.action_notification_5).setVisible(false);
                    menu.findItem(R.id.action_notification_6).setVisible(false);
                    menu.findItem(R.id.action_notification_7).setVisible(true);
                    menu.findItem(R.id.action_notification_8).setVisible(false);
                    menu.findItem(R.id.action_notification_9).setVisible(false);
                    menu.findItem(R.id.action_notification_9plus).setVisible(false);
                    break;
                case 8:
                    menu.findItem(R.id.action_notification_0).setVisible(false);
                    menu.findItem(R.id.action_notification_1).setVisible(false);
                    menu.findItem(R.id.action_notification_2).setVisible(false);
                    menu.findItem(R.id.action_notification_3).setVisible(false);
                    menu.findItem(R.id.action_notification_4).setVisible(false);
                    menu.findItem(R.id.action_notification_5).setVisible(false);
                    menu.findItem(R.id.action_notification_6).setVisible(false);
                    menu.findItem(R.id.action_notification_7).setVisible(false);
                    menu.findItem(R.id.action_notification_8).setVisible(true);
                    menu.findItem(R.id.action_notification_9).setVisible(false);
                    menu.findItem(R.id.action_notification_9plus).setVisible(false);
                    break;
                case 9:
                    menu.findItem(R.id.action_notification_0).setVisible(false);
                    menu.findItem(R.id.action_notification_1).setVisible(false);
                    menu.findItem(R.id.action_notification_2).setVisible(false);
                    menu.findItem(R.id.action_notification_3).setVisible(false);
                    menu.findItem(R.id.action_notification_4).setVisible(false);
                    menu.findItem(R.id.action_notification_5).setVisible(false);
                    menu.findItem(R.id.action_notification_6).setVisible(false);
                    menu.findItem(R.id.action_notification_7).setVisible(false);
                    menu.findItem(R.id.action_notification_8).setVisible(false);
                    menu.findItem(R.id.action_notification_9).setVisible(true);
                    menu.findItem(R.id.action_notification_9plus).setVisible(false);
                    break;
                default:
                    menu.findItem(R.id.action_notification_0).setVisible(false);
                    menu.findItem(R.id.action_notification_1).setVisible(false);
                    menu.findItem(R.id.action_notification_2).setVisible(false);
                    menu.findItem(R.id.action_notification_3).setVisible(false);
                    menu.findItem(R.id.action_notification_4).setVisible(false);
                    menu.findItem(R.id.action_notification_5).setVisible(false);
                    menu.findItem(R.id.action_notification_6).setVisible(false);
                    menu.findItem(R.id.action_notification_7).setVisible(false);
                    menu.findItem(R.id.action_notification_8).setVisible(false);
                    menu.findItem(R.id.action_notification_9).setVisible(false);
                    menu.findItem(R.id.action_notification_9plus).setVisible(true);
                    break;
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.group_voucher: {
                Cursor cs = db.getVouchers(db);
                if (cs.getCount() > 0) {
                    GroupVoucherActivity.start(getViewContext());
                } else {
                    showToast("No voucher available", GlobalToastEnum.NOTICE);
                }
                break;
            }
            case R.id.action_notification_0:
            case R.id.action_notification_1:
            case R.id.action_notification_2:
            case R.id.action_notification_3:
            case R.id.action_notification_4:
            case R.id.action_notification_5:
            case R.id.action_notification_6:
            case R.id.action_notification_7:
            case R.id.action_notification_8:
            case R.id.action_notification_9:
            case R.id.action_notification_9plus: {
                CommonVariables.notifCount = 0;
                invalidateOptionsMenu();
                NotificationsActivity.start(getViewContext());
                break;
            }
            case R.id.action_process_queue: {
                V2ProcessQueueActivity.start(getViewContext());
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }


    private void getProductCodes() {
        Call<GetProductCodesResponse> call = RetrofitBuild.getPrepaidLoadService(getViewContext())
                .getProductCodes(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        CommonVariables.devicetype
                );
        call.enqueue(getProductCodesCallBack);
    }

    private Callback<GetProductCodesResponse> getProductCodesCallBack = new Callback<GetProductCodesResponse>() {
        @Override
        public void onResponse(Call<GetProductCodesResponse> call, Response<GetProductCodesResponse> response) {
            ResponseBody errBody = response.errorBody();
            try {
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        PreloadedDataNew preloadedDataNew = response.body().getPreloadedDataNew();

                        loadWalletPrefixList = preloadedDataNew.getLoadWalletPrefix();
                        networkPrefixList = preloadedDataNew.getNetworkPrefix();
                        preloadedGlobeList = preloadedDataNew.getPreloadedGlobe();
                        preloadedSmartList = preloadedDataNew.getPreloadedSmart();
                        preloadedSunList = preloadedDataNew.getPreloadedSun();

                        preLoadDataNew();
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.getLocalizedMessage();
                e.printStackTrace();
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GetProductCodesResponse> call, Throwable t) {
            t.printStackTrace();
            t.getLocalizedMessage();
            showNoInternetToast();
        }
    };

    private void savePreLoadedSignature(List<PreloadedSignature> list) {
        PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_PRELOADED_SIGNATURE);
        PreferenceUtils.savePreloadedSignatureListPreference(getViewContext(), PreferenceUtils.KEY_PRELOADED_SIGNATURE, list);
    }

    public void preLoadData() {
        try {
            Cursor sunc = db.getProduct(db, "SUN");
            Cursor globec = db.getProduct(db, "GLOBE");
            Cursor smartc = db.getProduct(db, "SMART");
            Cursor retailerprefix = db.getLoadwalletPrefixCount(db);

            if (retailerprefix.getCount() == 0) {
                JSONArray loadwalletprefixarry = new JSONArray(PreloadedData.loadwalletprefix);
                db.DeleteLoadWalletPrefix(db);
                db.saveLoadWalletPrefix(db, loadwalletprefixarry);
            }

            if (sunc.getCount() == 0 || globec.getCount() == 0 || smartc.getCount() == 0) {

                JSONArray globearry = new JSONArray(PreloadedData.globestr);
                JSONArray sunarry = new JSONArray(PreloadedData.sunstr);
                JSONArray smartarry = new JSONArray(PreloadedData.smartstr);
                JSONArray prefixarry = new JSONArray(PreloadedData.prefix);

                db.deleteProdCode(db);
                db.DeletePrefix(db);

                db.saveSunProductCode(db, sunarry, "");
                db.saveGlobeProductCode(db, globearry, "");
                db.saveSmartProductCode(db, smartarry, "");
                db.saveNetPrefix(db, prefixarry);

            }

            sunc.close();
            globec.close();
            smartc.close();
            retailerprefix.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preLoadDataNew() {
        try {
            String strloadwalletprefix = new Gson().toJson(loadWalletPrefixList);
            String strnetworkPrefix = new Gson().toJson(networkPrefixList);
            String strpreloadedGlobe = new Gson().toJson(preloadedGlobeList);
            String strpreloadedSmart = new Gson().toJson(preloadedSmartList);
            String strpreloadedSun = new Gson().toJson(preloadedSunList);

            JSONArray loadwalletprefixarry = new JSONArray(strloadwalletprefix);
            JSONArray prefixarry = new JSONArray(strnetworkPrefix);
            JSONArray globearry = new JSONArray(strpreloadedGlobe);
            JSONArray smartarry = new JSONArray(strpreloadedSmart);
            JSONArray sunarry = new JSONArray(strpreloadedSun);

            Cursor sunc = db.getProduct(db, "SUN");
            Cursor globec = db.getProduct(db, "GLOBE");
            Cursor smartc = db.getProduct(db, "SMART");
            Cursor retailerprefix = db.getLoadwalletPrefixCount(db);

            db.DeleteLoadWalletPrefix(db);
            db.saveLoadWalletPrefix(db, loadwalletprefixarry);

            db.DeletePrefix(db);
            db.deleteProdCode(db);

            db.saveNetPrefix(db, prefixarry);
            db.saveGlobeProductCode(db, globearry, "");
            db.saveSmartProductCode(db, smartarry, "");
            db.saveSunProductCode(db, sunarry, "");

            sunc.close();
            globec.close();
            smartc.close();
            retailerprefix.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }


    private void showOutDatedDialog() {
        try {
            GlobalDialogs dialog = new GlobalDialogs(getViewContext());

            dialog.createDialog("Information", "We detected that your app is outdated. " +
                            "We recommend that you update your app to enjoy our new features and " +
                            "to make sure that everything will work smoothly.",
                    "DOWNLOAD", "", ButtonTypeEnum.SINGLE,
                    false, false, DialogTypeEnum.NOTICE);

            dialog.hideCloseImageButton();

            View closebtn = dialog.btnCloseImage();
            closebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            View singlebtn = dialog.btnSingle();
            singlebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showCSBMobileChangeWarningDialogNew(final String new_mobile) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("City Savings Bank",
                "We detected that you recently change your mobile number via City Savings Bank. " +
                        "Proceed to verify the change.",
                "PROCEED", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.NOTICE);

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                CSBSettingsChangeMobileConfimationActivity.start(getViewContext(),
                        true, new_mobile, "MOBILE VERIFICATION");
            }
        });
    }


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     */

    private void getSubscribersProfile() {

        //PARAMETERS
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
//        parameters.put("devicetype",CommonVariables.devicetype);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthenticationID(parameters);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);

        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");
        parameters.put("index", index);

        String paramJson = new Gson().toJson(parameters, Map.class);
        authenticationid = CommonFunctions.getSha1Hex(CommonFunctions.parseJSON(String.valueOf(jsonString), "authenticationid"));
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + "getSubscribersProfile");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        getSubscriberProfileObject(getSubscribersProfileCallback);

    }

    private void getSubscriberProfileObject(Callback<GenericResponse> getSubscriberDetailsResponseCall) {
        Call<GenericResponse> getSubscriberDetails = RetrofitBuilder.getSubscriberV2APIService(getApplicationContext())
                .getSubscribersProfile(authenticationid, param);

        getSubscriberDetails.enqueue(getSubscriberDetailsResponseCall);
    }

    private Callback<GenericResponse> getSubscribersProfileCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {

                ResponseBody errBody = response.errorBody();

                if (errBody == null) {

                    String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                    if (response.body().getStatus().equals("000")) {

                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                        String profile = CommonFunctions.parseJSON(decryptedData,"profile");

                        V2SubscriberDetails subDetails = new Gson().fromJson(profile, V2SubscriberDetails.class);

                        if (!guarantorStatus.equals("APPROVED")) {
                            String guarantorApprovalStatus = subDetails.getGuarantorApprovalStatus();
                            db.updateGuarantorStatus(db, guarantorApprovalStatus, userid);
                        }

                        db.setGuarantorID(db, subDetails.getGuarantorID(), userid);

                        PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID, subDetails.getDistributorID());
                        PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER,subDetails.getResellerID());

                        //checkSyncContact(subDetails.getSyncContact());

                    } else if (response.body().getStatus().equals("111")) {
                        showToast(decryptedMessage, GlobalToastEnum.NOTICE);
                        unsubscribePush();
                        //clear variable
                        CommonVariables.clearVariables();
                        //clear data
                        db.Logout(db);

                        if (isFreeModeEnabled()) {
                            deactivateFreeNet();
                            FreenetSdk.unregisterConnectionListener(MainActivity.this);
                        }
                        //open login page
                        Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else{
                        showErrorToast(decryptedMessage);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable throwable) {
            throwable.printStackTrace();
        }
    };


    //Get Product Codes
    private void getProuductCodesV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("devicetype",CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            getProductIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", getProductIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            getProductAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            getProductKeyEncryption = CommonFunctions.getSha1Hex(getProductAuthenticationid + sessionid + "getProductCodesV2");
            getProductParam = CommonFunctions.encryptAES256CBC(getProductKeyEncryption, String.valueOf(paramJson));

            getProductCodesV2Object(getProductCodesV2CallBack);

        }else{
            showNoInternetToast();
        }
    }
    private void getProductCodesV2Object(Callback<GenericResponse> getProductCodeV2) {
        Call<GenericResponse> call = RetrofitBuilder.getPrepaidLoadV2API(getViewContext())
                .getProductCodesV2(
                        getProductAuthenticationid,sessionid,getProductParam
                );
        call.enqueue(getProductCodeV2);
    }

    private Callback<GenericResponse> getProductCodesV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            try {
                if (errBody == null) {
                    String decryptedMessage = CommonFunctions.decryptAES256CBC(getProductKeyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {

                        String data = CommonFunctions.decryptAES256CBC(getProductKeyEncryption,response.body().getData());
                        if (decryptedMessage.equals("error") ||data.equals("error")) {
                            showErrorGlobalDialogs();
                        }else{
                            //Logger.debug("okhttp","PRODUCTSS ====================== : "+data);

                            String loadwallet = CommonFunctions.parseJSON(data,"LoadWallet");
                            String network = CommonFunctions.parseJSON(data,"Network");
                            String globeproducts = CommonFunctions.parseJSON(data,"GlobeProducts");
                            String smartproducts = CommonFunctions.parseJSON(data,"SmartProducts");
                            String sunproducts = CommonFunctions.parseJSON(data,"SunProducts");

                            loadWalletPrefixList = new Gson().fromJson(loadwallet, new TypeToken<List<PreloadedPrefix>>(){}.getType());
                            networkPrefixList = new Gson().fromJson(network, new TypeToken<List<PreloadedPrefix>>(){}.getType());
                            preloadedGlobeList = new Gson().fromJson(globeproducts, new TypeToken<List<PreloadedGlobe>>(){}.getType());
                            preloadedSmartList = new Gson().fromJson(smartproducts, new TypeToken<List<PreloadedSmart>>(){}.getType());
                            preloadedSunList = new Gson().fromJson(sunproducts, new TypeToken<List<PreloadedSun>>(){}.getType());

                            preLoadDataNew();
                        }
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.getLocalizedMessage();
                e.printStackTrace();
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            t.getLocalizedMessage();
            showNoInternetToast();
        }
    };

    private void checkCSBChangeMobileRequestV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("requesttype","MOBILE VERIFICATION");
            parameters.put("devicetype",CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            checkCSBRequestIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", checkCSBRequestIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            checkCSBRequestAuthcode = CommonFunctions.parseJSON(jsonString, "authenticationid");
            checkCSBRequestKeyEncryption = CommonFunctions.getSha1Hex(checkCSBRequestAuthcode + sessionid + "checkCSBChangeMobileRequest");
            checkCSBRequestParam = CommonFunctions.encryptAES256CBC(checkCSBRequestKeyEncryption, String.valueOf(paramJson));

            checkCSBChangeMobileRequestV2Object(checkCSBChangeMobileRequestV2Callback);

        }else{
            showNoInternetToast();
        }
    }

    private void checkCSBChangeMobileRequestV2Object(Callback<GenericResponse> checkCSBrequest){
        Call<GenericResponse> call = RetrofitBuilder.getAccountV2API(getViewContext())
                .checkCSBChangeMobileRequest(
                        checkCSBRequestAuthcode,sessionid,checkCSBRequestParam
                );
        call.enqueue(checkCSBrequest);
    }

    private Callback<GenericResponse> checkCSBChangeMobileRequestV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String decryptedMessage = CommonFunctions.decryptAES256CBC(checkCSBRequestKeyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String data = CommonFunctions.decryptAES256CBC(checkCSBRequestKeyEncryption,response.body().getData());
                        if (decryptedMessage.equals("error") ||data.equals("error")) {
                            showErrorGlobalDialogs();
                        }else{
                            Logger.debug("okhttp","REQUEST : "+data);
                            GKProcessRequest request = new Gson().fromJson(data,GKProcessRequest.class);
                            //showCSBMobileChangeWarningDialog(CommonFunctions.parseXML(request.getXMLDetails(), "newmobilenumber"));
                            showCSBMobileChangeWarningDialogNew(CommonFunctions.parseXML(request.getXMLDetails(), "newmobilenumber"));

                        }
                    }
                    else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else if(response.body().getStatus().equals("005") && !decryptedMessage.contains("Unable to Access")){
                        if(!decryptedMessage.contains("request")){
                            showErrorGlobalDialogs(decryptedMessage);
                        }
                    }
                } else {
                    showErrorGlobalDialogs("Something went wrong. Please try again.");
                }
        }

        @Override
        public void onFailure(@NotNull Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            t.getLocalizedMessage();
            showErrorToast("Something went wrong. Please try again.");
        }
    };


    //Check app version V2 for security Update
    private void checkAppVersionV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("version",CommonVariables.version);
            parameters.put("devicetype",CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            checkAppVersionIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", checkAppVersionIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            checkAppVersionAuthID = CommonFunctions.parseJSON(jsonString, "authenticationid");
            checkAppVersionKey = CommonFunctions.getSha1Hex(checkAppVersionAuthID + sessionid + "checkAppVersionV2");
            checkAppVersionParam = CommonFunctions.encryptAES256CBC(checkAppVersionKey, String.valueOf(paramJson));

            checkAppVersionV2Object(checkAppVersionV2Callback);

        }else{
            showNoInternetToast();
        }
    }
    private void checkAppVersionV2Object(Callback<GenericResponse> checkAppVersion){
        Call<GenericResponse> call = RetrofitBuilder.getAccountV2API(getViewContext())
                .checkAppVersionV2(
                        checkAppVersionAuthID,sessionid,checkAppVersionParam
                );
        call.enqueue(checkAppVersion);
    }
    private Callback<GenericResponse> checkAppVersionV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            try {
                if (errBody == null) {
                    String message  = CommonFunctions.decryptAES256CBC(checkAppVersionKey,response.body().getData());
                    if (response.body().getStatus().equals("000")) {
                        String decrypteddata = CommonFunctions.decryptAES256CBC(checkAppVersionKey,response.body().getData());

                        String decryptedPreloaded = CommonFunctions.parseJSON(decrypteddata,"preloaded");

                        Type type = new TypeToken<List<PreloadedSignature>>(){}.getType();
                        List<PreloadedSignature> list = new Gson().fromJson(decryptedPreloaded, type);

                        List<PreloadedSignature> preflist = PreferenceUtils.getPreloadedSignaturePreference
                                (getViewContext(), PreferenceUtils.KEY_PRELOADED_SIGNATURE);

                        if (preflist != null) {
                            if (preflist.size() > 0) {

                                Collections.sort(list, new Comparator<PreloadedSignature>() {
                                    @Override
                                    public int compare(PreloadedSignature lhs, PreloadedSignature rhs) {
                                        return lhs.getType().compareTo(rhs.getType());
                                    }
                                });

                                Collections.sort(preflist, new Comparator<PreloadedSignature>() {
                                    @Override
                                    public int compare(PreloadedSignature lhs, PreloadedSignature rhs) {
                                        return lhs.getType().compareTo(rhs.getType());
                                    }
                                });

                                JsonParser parser = new JsonParser();
                                JsonElement t1 = parser.parse(new Gson().toJson(list));
                                JsonElement t2 = parser.parse(new Gson().toJson(preflist));
                                boolean match = t2.equals(t1);

                                if (!match) {
                                    savePreLoadedSignature(list);
                                    // getProductCodes();
                                    getProuductCodesV2();
                                }
                            } else {
                                savePreLoadedSignature(list);
                                // getProductCodes();
                                getProuductCodesV2();
                            }
                        } else {
                            savePreLoadedSignature(list);
                            //getProductCodes();
                            getProuductCodesV2();
                        }
                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }
                    else if (response.body().getStatus().equals("105")) {
                        showOutDatedDialog();
                    } else {
                        showErrorGlobalDialogs(message);
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.getLocalizedMessage();
                e.printStackTrace();
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(@NotNull Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            t.getLocalizedMessage();
            showNoInternetToast();
        }
    };

    //GET GKPROMOTIONS V2
    private void getGKAdsV2(){

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            mLongitude = mGPSTracker.getLongitude();
            mLatitude = mGPSTracker.getLatitude();

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("type", "PROMOTIONS");
            parameters.put("longitude", String.valueOf(mLongitude));
            parameters.put("latitude", String.valueOf(mLatitude));
            parameters.put("devicetype", CommonVariables.devicetype);


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            getGKAdsIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", getGKAdsIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            getGKAdsAuthID = CommonFunctions.parseJSON(jsonString, "authenticationid");
            getGKAdsKey = CommonFunctions.getSha1Hex(getGKAdsAuthID + sessionid + "getGKPromotionsV2");
            getGKAdsParam = CommonFunctions.encryptAES256CBC(getGKAdsKey, String.valueOf(paramJson));


            Call<GenericResponse> call = RetrofitBuilder.getWhatsNewV2API(getViewContext())
                    .getGKPromotionsV2(
                        getGKAdsAuthID,sessionid,getGKAdsParam
                    );

            call.enqueue(getGKAdsV2Callback);

        }else{
            showNoInternetToast();
        }
    }
    private Callback<GenericResponse> getGKAdsV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String decryptedMessage = CommonFunctions.decryptAES256CBC(getGKAdsKey,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(getGKAdsKey,response.body().getData());

                        Logger.debug("okhttp","GETGKADS ::::::"+decryptedData);

                        if(decryptedData.equals("error") || decryptedMessage.equals("error")){
                            showErrorGlobalDialogs("Something went wrong. Please try again.");
                        }else{
                            List<GKAds> gkAds = new Gson().fromJson(decryptedData, new TypeToken<List<GKAds>>(){}.getType());
                            CacheManager.getInstance().savePromotions(gkAds);
                            if (gkAds.size() > 0)
                                GKAdsActivity.start(getViewContext(), true);
                        }
                    }else if(response.body().getStatus().equals("003")){
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else if(response.body().getStatus().equals("005")){
                        showErrorToast(decryptedMessage);
                    }else if(response.body().getStatus().equals("500")){
                        showErrorGlobalDialogs();
                    }
                }
        }

        @Override
        public void onFailure(@NotNull Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            showErrorToast();
        }
    };
}
class Executor extends Thread {
    private ConcurrentLinkedQueue<Worker> workers;
    private Callback callback;
    private CountDownLatch latch;

    private Executor(List<Runnable> tasks, Callback callback) {
        super();
        this.callback = callback;
        workers = new ConcurrentLinkedQueue<>();
        latch = new CountDownLatch(tasks.size());

        for (Runnable task : tasks) {
            workers.add(new Worker(task, latch));
        }
    }

    public void execute() {
        start();
    }

    @Override
    public void run() {
        while (true) {
            Worker worker = workers.poll();
            if (worker == null) {
                break;
            }
            worker.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (callback != null) {
            callback.onComplete();
        }
    }

    public static class Builder {
        private List<Runnable> tasks = new ArrayList<>();
        private Callback callback;

        public Builder add(Runnable task) {
            tasks.add(task);
            return this;
        }

        public Builder callback(Callback callback) {
            this.callback = callback;
            return this;
        }

        public Executor build() {
            return new Executor(tasks, callback);
        }
    }

    public interface Callback {
        void onComplete();
    }
}

class Worker implements Runnable {

    private AtomicBoolean started;
    private Runnable task;
    private Thread thread;
    private CountDownLatch latch;

    public Worker(Runnable task, CountDownLatch latch) {
        this.latch = latch;
        this.task = task;
        started = new AtomicBoolean(false);
        thread = new Thread(this);
    }

    public void start() {
        if (!started.getAndSet(true)) {
            thread.start();
        }
    }

    @Override
    public void run() {
        task.run();
        latch.countDown();
    }
}


