package com.goodkredit.myapplication.base;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.BuildConfig;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.account.SignInActivity;
import com.goodkredit.myapplication.activities.billspayment.BillsPaymentConfirmationActivity;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoRedirectionActivity;
import com.goodkredit.myapplication.activities.gkstore.GKStoreConfirmationActivity;
import com.goodkredit.myapplication.activities.gkstore.GKStoreDetailsActivity;
import com.goodkredit.myapplication.activities.gkstore.GkStoreHistoryActivity;
import com.goodkredit.myapplication.activities.reloadretailer.RetailerLoadConfirmationActivity;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.activities.transactions.ViewOthersTransactionsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.common.PaymentConfirmation;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.VouchersFragment;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.GlobalToast;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.OnSwipeTouchListener;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import ph.com.voyagerinnovation.freenet.applink.FreenetSdk;

/**
 * Created by Ban_Lenovo on 2/16/2017.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = "TAG-GKAPP";
    private ProgressDialog mProgressDialog;

    public CountDownTimer mLoaderTimer;

    public Uri fileUri = null;

    private ValueAnimator animator;

    public String imei = "";
    public String userid = "";
    public String session = "";
    public String authcode = "";
    public String borrowerid = "";
    public String guarantorid = "";

    private DatabaseHandler db;

    public MaterialDialog mDialog = null;

    //PROGRESSDIALOG
    public MaterialDialog mLoaderDialog;
    public TextView mLoaderDialogMessage;
    public TextView mLoaderDialogTitle;
    public ImageView mLoaderDialogImage;

    //GLOBAL DIALOG
    private Dialog mGlobalDialog = null;

    private TextView mGlobalTitle;
    private LinearLayout mGlobalContentViewContainer;
    private ImageView mGlobalContentImageView;
    private TextView mGlobalContentView;
    private FrameLayout mGlobalPaymentLogoContainer;
    private RelativeLayout mDialogTitleLogoContainer;
    private ImageView mGlobalTitleDialogLogo;
    private LinearLayout mGlobalCloseBtn;

    private String strmessagetitle = "";
    private String strmessagecontent = "";
    private String strbuttonmessage = "";
    private ButtonTypeEnum typebuttonTypeEnum;
    private boolean boolisReseller = false;
    private boolean boolishtmlcode = false;
    private DialogTypeEnum typedialogTypeEnum;
    private boolean boolredirects = false;

    //GLOBAL BUTTON
    private LinearLayout btn_global;
    private TextView txv_btn_global;
    private LinearLayout btn_global_double_container;
    private TextView txv_btn_global_double;
    private TextView txv_btn_global_double_two;

    //GLOBAL QRCODE
    private LinearLayout layout_qrcode_container;
    private TableRow approvalcodewrap;
    private ImageView leftarrow;
    private ImageView successBarcode;
    private ImageView successQRCode;
    private ImageView rightarrow;

    private LinearLayout layout_values_container;
    private TextView barcodeValue;
    private TextView merchantRefCodeLabel;
    private TextView dialogNote;

    //GK NEGOSYO
    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;

    //GLOBAL SKYCABLE
    private String strskybtnaction = "";

    private Fragment globalfragment = null;

    public void showProgressDialog(Context context, String title, String message) {
        try {
            if (!isFinishing() && mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setInverseBackgroundForced(true);
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setMessage(title + "\n" + message);
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing())
                if (mProgressDialog != null && !isFinishing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupToolbar() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupToolbarWithTitle(String title) {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDebugEnabled() {
        return CommonVariables.isDebugMode;
    }

    public Context getViewContext() {
        return this;
    }

    public void log(String message) {
        for (int i = 0, length = message.length(); i < length; i++) {
            int newline = message.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + 4000);
                Logger.debug("OkHttp", message.substring(i, end));
                i = end;
            } while (i < newline);
        }
    }

    public void activateFreeNet() {
        try {
            FreenetSdk.activateZeroRating(getApplicationContext(), CommonVariables.FREENETKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deactivateFreeNet() {
        try {
            FreenetSdk.deactivateZeroRating(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * if data is on or connectivity
     * @return
     */
    public boolean isDataEnabled() {

        boolean bool = false;
        try {
            log("network: " + String.valueOf(CommonFunctions.getConnectivityStatus(getViewContext())));
            if (CommonFunctions.getConnectivityStatus(getViewContext()) == 2) {
                bool = true;
            } else {
                bool = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bool;
    }

    public void setupOverlay() {
        try {
            LinearLayout overlayLayout = (LinearLayout) findViewById(R.id.overlayLayout);
            if (isDataEnabled() && isSim1OperatorSupported()) {
                overlayLayout.setVisibility(View.VISIBLE);
            } else {
                overlayLayout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setOverlayGUI(boolean isOnFreeMode) {
        try {
            TextView mTv = (TextView) findViewById(R.id.freeModeTextView);
            Button mBtn = (Button) findViewById(R.id.freeModeButton);

            if (isOnFreeMode) {
                mTv.setText("You're on Free Mode");
                mBtn.setText("Use Data");
            } else {
                mTv.setText("You're on Data Mode");
                mBtn.setText("Go to Free");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFreeModeEnabled() {
        return (PreferenceUtils.getIsFreeMode(getViewContext()) && isDataEnabled() && isSim1OperatorSupported());
    }

    public boolean isSim1OperatorSupported() {
        boolean isSupported = false;
        if (ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
        } else {
            //get IMEI
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei = tm.getImei();
            } else {
                imei = tm.getDeviceId();
            }
            if (tm.getNetworkOperatorName().contains("SMART") ||
                    tm.getNetworkOperatorName().contains("Smart") ||
                    tm.getNetworkOperatorName().contains("TNT") ||
                    tm.getNetworkOperatorName().contains("Talk") ||
                    tm.getNetworkOperatorName().contains("SUN") ||
                    tm.getNetworkOperatorName().contains("Sun")) {
                isSupported = true;
            }
        }
        return isSupported;
    }

    public void startLoaderAnimation() {
        final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(750);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();

    }

    public void showError(String content) {
        try {
            if (mDialog != null)
                mDialog.dismiss();

            mDialog = new MaterialDialog.Builder(getViewContext())
                    .positiveText("OK")
                    .content(content)
                    .cancelable(false)
                    .show();
            V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mDialog.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showError() {
        showError("Oops! We failed to connect to the service. Please check your internet connection and try again.");
    }

    //-----------------GLOBAL TOAST MESSAGES--------------------
    public void showToast(String message, GlobalToastEnum globalToastEnum) {
        try {
            GlobalToast.displayToast(getViewContext(), message, globalToastEnum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showErrorToast(String content) {
        showToast(content, GlobalToastEnum.ERROR);
    }

    public void showErrorToast() {
        showErrorToast("Oops! We failed to connect to the service. Please check your internet connection and try again.");
    }

    public void showWarningToast(String content) {
        showToast(content, GlobalToastEnum.WARNING);
    }

    public void showWarningToast() {
        showWarningToast("Oops! We failed to connect to the service. Please check your internet connection and try again.");
    }

    public void showNoInternetToast() {
        showToast("Oops! We failed to connect to the service. Please check your internet connection and try again.", GlobalToastEnum.WARNING);
    }

    public Uri getOutputMediaFileUri(int type) {
        if (Build.VERSION.SDK_INT > 23) {
            return FileProvider.getUriForFile(getViewContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    getOutputMediaFile(type));
        } else {
            return Uri.fromFile(getOutputMediaFile(type));
        }
    }

    private File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "GoodKredit");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Logger.debug("GoodKredit", "Oops! Failed create "
                        + "GoodKredit" + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    //=======================================================
    //CALCULATE EXPECTED LIMIT
    //length: size of array list
    //limitSize: limit value for each call
    //=======================================================
    public int getLimit(final double length, final double limitSize) {
        return (int) ((length == 0) ? 0 : limitSize * Math.ceil(length / limitSize));
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        try {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String capitalizeWord(String word) {
        String[] cap_word_arr = word.toLowerCase().split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : cap_word_arr) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap + " ");
        }
        return builder.toString();
    }

    public String getMobileNumber(String number) {
        String result = "";
        if (number.length() >= 9) {
            if (number.substring(0, 2).equals("63")) {
                if (number.length() == 12) {
                    result = number;
                } else {
                    result = "INVALID";
                }
            } else if (number.substring(0, 2).equals("09")) {
                if (number.length() == 11) {
                    result = "63" + number.substring(1, number.length());
                } else {
                    result = "INVALID";
                }
            } else if (number.substring(0, 1).equals("9")) {
                if (number.length() == 10) {
                    result = "63" + number;
                } else {
                    result = "INVALID";
                }
            } else {
                result = "INVALID";
            }
        } else {
            result = "INVALID";
        }
        return result;
    }

    public boolean isGPSEnabled(Context mContext) {
        boolean bool = false;
        try {
            LocationManager locationManager = (LocationManager)
                    mContext.getSystemService(Context.LOCATION_SERVICE);
            bool = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    public void logDeviceInformation() {
        log(System.getProperty("os.version"));
        log(String.valueOf(Build.VERSION.SDK_INT));
        log(android.os.Build.DEVICE);
        log(android.os.Build.MODEL);
        log(android.os.Build.PRODUCT);
        log(String.valueOf(CommonFunctions.getScreenWidthPixel(getViewContext())));
    }

    //CREATE AND SHOW CUSTOM LOADER
    public void setUpProgressLoader() {
        try {
            if (!isFinishing() && mLoaderDialog == null) {
                mLoaderDialog = new MaterialDialog.Builder(getViewContext())
                        .cancelable(false)
                        .customView(R.layout.dialog_progress_loader, false)
                        .build();

                View customView = mLoaderDialog.getCustomView();

                if (customView != null) {
                    mLoaderDialogMessage = (TextView) customView.findViewById(R.id.mLoaderDialogMessage);
                    mLoaderDialogTitle = (TextView) customView.findViewById(R.id.mLoaderDialogTitle);
                    mLoaderDialogImage = (ImageView) customView.findViewById(R.id.mLoaderDialogImage);
                    mLoaderDialogTitle.setText("Processing...");

                    Glide.with(getViewContext())
                            .load(R.drawable.progressloader)
                            .into(mLoaderDialogImage);

                    mLoaderDialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DISMISS CUSTOM LOADER
    public void setUpProgressLoaderDismissDialog() {
        if (mLoaderDialog != null && mLoaderDialog.isShowing())
            if (mLoaderDialog != null && !isFinishing()) {
                mLoaderDialog.dismiss();
                mLoaderDialog = null;
            }
    }

    //CHANGE THE MESSAGE OF THE LOADER
    public void setUpLoaderTitleDialog(String title) {
        mLoaderDialogTitle.setText(title);
    }

    public void setUpProgressLoaderMessageDialog(String message) {
        if (mLoaderDialog != null) {
            mLoaderDialogMessage.setText(message);
        }
    }

    //--------------------------GLOBAL CONFIRMATION DIALOGS---------------------

    //---------- THIS IS NO LONGER SUPPORTED. PLEASE USE THE NEW ONE (IN UTILITIES, GLOBAL DIALOGS) -------------
    //ALSO IF HAVE TIME, PLEASE REMOVED THIS ONE AND REPLACE IT WITH THE CURRENT GLOBAL DIALOG    // Updated on: (22/11/2018) by Kris-Kun

    //PARAMETERS:
    //messagetitle -> Content of the title.
    //messagecontent -> Content of the message.
    //buttonmessage -> Content of the message for the button.
    //buttonTypeEnum -> Refers to type of button that will display on the bottom that contains a text message. (E.G SINGLE, DOUBLE)
    //isReseller -> Displays the GK Negosyo Modal (I Want This).
    //ishtmlcode -  Refers whether the message content will read html code format or not.
    //dialogTypeEnum -> Refers to the type of dialog that will display. (E.G SUCCESS, FAILED, ONPROCESS).
    //redirects -> Refers to whether the dialogs should dismiss or redirects to another page.

    //NOTES:
    //1. THIS DOES NOT INCLUDE ALL TYPES OF DIALOGS AND THE NUMBER OF GLOBAL BUTTONS THAT WILL DISPLAY IS LIMITED TO TWO.

    //2. FOR CUSTOMIZING OF THE REDIRECTION TO ANOTHER PAGE FOR THE BUTTONS,
    //THESE ARE SET MANUALLY AND PER ACTIIVTY. FRAGMENTS ARE NOT SUPPORTED YET.

    //3. "RETRY" IS SET TO ONLY TRUE FOR FAILED DIALOGS AND A SINGLE BUTTON TYPE.
    //IT DOES NOT SUPPORT OTHER DIALOGS AND OTHER BUTTONTYPES.

    //4. GK NEGOSYO MODAL IS SET TO ONLY TRUE FOR SUCCESS DIALOGS. DOES NOT SUPPORT OTHER DIALOGS.

    //5. EDITING OF MESSAGE TITLE IS NOT SUPPORTED AS OF THE MOMENT. THESE ARE SET MANUALLY.

    public void showGlobalDialogs(String messagecontent, String buttonmessage, ButtonTypeEnum buttonTypeEnum,
                                  boolean isReseller, boolean ishtmlcode, DialogTypeEnum dialogTypeEnum) {

        strmessagecontent = messagecontent;
        strbuttonmessage = buttonmessage;
        typebuttonTypeEnum = buttonTypeEnum;
        boolisReseller = isReseller;
        boolishtmlcode = ishtmlcode;
        typedialogTypeEnum = dialogTypeEnum;

        //SINCE THESE PARAMS ARE NOT BEINGS PASSED (DEFAULTS ARE BEING SET)
        strmessagetitle = "";
        boolredirects = true;

        globalDialogsMain();
    }

    public void showGlobalDialogs(String messagecontent, String buttonmessage, ButtonTypeEnum buttonTypeEnum,
                                  boolean isReseller, boolean ishtmlcode, DialogTypeEnum dialogTypeEnum, boolean redirects) {

        strmessagecontent = messagecontent;
        strbuttonmessage = buttonmessage;
        typebuttonTypeEnum = buttonTypeEnum;
        boolisReseller = isReseller;
        boolishtmlcode = ishtmlcode;
        typedialogTypeEnum = dialogTypeEnum;
        boolredirects = redirects;

        //SINCE THESE PARAMS ARE NOT BEINGS PASSED (DEFAULTS ARE BEING SET)
        strmessagetitle = "";

        globalDialogsMain();
    }

    public void showGlobalDialogs(String messagecontent, String buttonmessage, ButtonTypeEnum buttonTypeEnum,
                                  boolean isReseller, boolean ishtmlcode, DialogTypeEnum dialogTypeEnum, boolean redirects,
                                  Fragment fragment)
    {

        strmessagecontent = messagecontent;
        strbuttonmessage = buttonmessage;
        typebuttonTypeEnum = buttonTypeEnum;
        boolisReseller = isReseller;
        boolishtmlcode = ishtmlcode;
        typedialogTypeEnum = dialogTypeEnum;
        boolredirects = redirects;
        globalfragment = fragment;

        //SINCE THESE PARAMS ARE NOT BEINGS PASSED (DEFAULTS ARE BEING SET)
        strmessagetitle = "";

        globalDialogsMain();
    }

    private void globalDialogsMain() {
        try {
            if (mGlobalDialog != null)

                mGlobalDialog.dismiss();
            mGlobalDialog = null;

            mGlobalDialog = new Dialog(getViewContext());
            mGlobalDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            mGlobalDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Objects.requireNonNull(mGlobalDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mGlobalDialog.setContentView(R.layout.dialog_global_messages);
            mGlobalDialog.setCancelable(false);

            //CUSTOM FIELDS
            //TITLE
            mGlobalTitle = (TextView) mGlobalDialog.findViewById(R.id.txv_title);
            mGlobalTitle.setVisibility(View.VISIBLE);
            //CONTENT
            mGlobalContentViewContainer = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_contentview_container);
            mGlobalContentViewContainer.setVisibility(View.VISIBLE);
            //IMAGE CONTENT
            mGlobalContentImageView = (ImageView) mGlobalDialog.findViewById(R.id.img_content_logo);
            mGlobalContentImageView.setVisibility(View.GONE);
            //TITLE LOGO
            mDialogTitleLogoContainer = (RelativeLayout) mGlobalDialog.findViewById(R.id.layout_titlelogo_container);
            mGlobalTitleDialogLogo = (ImageView) mGlobalDialog.findViewById(R.id.img_title_logo);
            //BUTTON TEXT
            txv_btn_global = (TextView) mGlobalDialog.findViewById(R.id.txv_btn_global);
            txv_btn_global_double = (TextView) mGlobalDialog.findViewById(R.id.txv_btn_global_double);
            txv_btn_global_double_two = (TextView) mGlobalDialog.findViewById(R.id.txv_btn_global_double_two);

            //PAYMENT LOGO
            mGlobalPaymentLogoContainer = (FrameLayout) mGlobalDialog.findViewById(R.id.layout_payment_logo_container);
            mGlobalPaymentLogoContainer.setVisibility(View.GONE);
            //CLOSE ICON BUTTON
            mGlobalCloseBtn = (LinearLayout) mGlobalDialog.findViewById(R.id.btn_close_container);
            mGlobalCloseBtn.setVisibility(View.VISIBLE);

            switch (typedialogTypeEnum) {
                case SUCCESS:
                    //TITLE AND CONTENT
                    mGlobalTitle.setAllCaps(true);
                    mGlobalTitle.setText("SUCCESS");
                    mGlobalTitle.setTextColor(getResources().getColor(R.color.color_success_green));
                    globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
                    //TITLE LOGO
                    mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
                    mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_success);
                    //CUSTOM BUTTONS
                    txv_btn_global.setTextColor(getResources().getColor(R.color.color_success_green));
                    txv_btn_global_double.setTextColor(getResources().getColor(R.color.color_success_green));
                    globalDialogButtonActions(strbuttonmessage, typebuttonTypeEnum, false, boolisReseller, boolredirects);
                    break;
                case FAILED:
                    //TITLE AND CONTENT
                    mGlobalTitle.setAllCaps(true);
                    mGlobalTitle.setText("FAILED");
                    mGlobalTitle.setTextColor(getResources().getColor(R.color.color_error_red));
                    globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
                    //TITLE LOGO
                    mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
                    mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_error);
                    //CUSTOM BUTTONS
                    txv_btn_global.setTextColor(getResources().getColor(R.color.color_error_red));
                    txv_btn_global_double.setTextColor(getResources().getColor(R.color.color_error_red));
                    globalDialogButtonActions(strbuttonmessage, typebuttonTypeEnum, true, true, boolredirects);
                    break;

                case ONPROCESS:
                    //TITLE AND CONTENT
                    mGlobalTitle.setAllCaps(true);
                    mGlobalTitle.setText("ON PROCESS");
                    mGlobalTitle.setTextColor(getResources().getColor(R.color.color_onprocess_yellow));
                    globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
                    //TITLE LOGO
                    mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
                    mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_onprocess);
                    //CUSTOM BUTTONS
                    txv_btn_global.setTextColor(getResources().getColor(R.color.color_onprocess_yellow));
                    txv_btn_global_double.setTextColor(getResources().getColor(R.color.color_onprocess_yellow));
                    globalDialogButtonActions(strbuttonmessage, typebuttonTypeEnum, false, true, boolredirects);
                    break;
                case TIMEOUT:
                    //TITLE AND CONTENT
                    mGlobalTitle.setAllCaps(true);
                    mGlobalTitle.setText("TIMEOUT");
                    mGlobalTitle.setTextColor(getResources().getColor(R.color.color_timeout_orange));
                    globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
                    //TITLE LOGO
                    mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
                    mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_timeout);
                    //CUSTOM BUTTONS
                    txv_btn_global.setTextColor(getResources().getColor(R.color.color_timeout_orange));
                    txv_btn_global_double.setTextColor(getResources().getColor(R.color.color_timeout_orange));
                    globalDialogButtonActions(strbuttonmessage, typebuttonTypeEnum, false, true, boolredirects);
                    break;

                case QRCODE:
                    //TITLE AND CONTENT
                    mGlobalTitle.setAllCaps(true);
                    mGlobalTitle.setText("TRANSACTION COMPLETE");
                    mGlobalTitle.setTextSize(20);
                    mGlobalTitle.setTextColor(getResources().getColor(R.color.color_qrcode_darkblue));
                    mGlobalContentViewContainer.setVisibility(View.GONE);
                    //TITLE LOGO
                    mDialogTitleLogoContainer.setVisibility(View.GONE);
                    //QRLAYOUT
                    globalQRCodeLayout(strmessagecontent);
                    //CUSTOM BUTTONS
                    mGlobalCloseBtn.setVisibility(View.GONE);
                    txv_btn_global.setTextColor(getResources().getColor(R.color.color_qrcode_darkblue));
                    txv_btn_global_double.setTextColor(getResources().getColor(R.color.color_qrcode_darkblue));
                    globalDialogButtonActions(strbuttonmessage, typebuttonTypeEnum, false, true, boolredirects);
                    break;

                case IMAGE:
                    //TITLE AND CONTENT
                    mGlobalTitle.setVisibility(View.GONE);
                    globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
                    //TITLE LOGO
                    mDialogTitleLogoContainer.setVisibility(View.GONE);
                    //CONTENT LOGO
                    mGlobalContentImageView.setVisibility(View.VISIBLE);
                    globalContentImageLogo();
                    //CUSTOM BUTTONS
                    globalDialogButtonActions(strbuttonmessage, typebuttonTypeEnum, false, true, boolredirects);
                    break;

                case TEXT:
                    //TITLE AND CONTENT
                    mGlobalTitle.setVisibility(View.GONE);
                    globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
                    //TITLE LOGO
                    mDialogTitleLogoContainer.setVisibility(View.GONE);
                    //CUSTOM BUTTONS
                    globalContentText();
                    globalDialogButtonActions(strbuttonmessage, typebuttonTypeEnum, false, true, boolredirects);
                    break;

                case NOTICE:
                    //TITLE AND CONTENT
                    mGlobalTitle.setAllCaps(true);
                    mGlobalTitle.setText("NOTICE");
                    mGlobalTitle.setTextColor(getResources().getColor(R.color.color_information_blue));
                    globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
                    //TITLE LOGO
                    mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
                    mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_notice);
                    //CUSTOM BUTTONS
                    globalContentNotice();
                    globalDialogButtonActions(strbuttonmessage, typebuttonTypeEnum, false, true, boolredirects);
                    break;

                case WARNING:
                    //TITLE AND CONTENT
                    mGlobalTitle.setAllCaps(true);
                    mGlobalTitle.setText("WARNING");
                    mGlobalTitle.setTextColor(getResources().getColor(R.color.color_onprocess_yellow));
                    globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
                    //TITLE LOGO
                    mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
                    mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_warning);
                    //CUSTOM BUTTONS
                    globalContentWarning();
                    globalDialogButtonActions(strbuttonmessage, typebuttonTypeEnum, false, true, boolredirects);
                    break;
            }

            mGlobalDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
            e.getMessage();
        }
    }

    //CHECKS IF THE CONTENT WILL BE DISPLAYED WITH HTML FORMAT OR NOT
    private void globalContentWithHTMLCode(boolean ishtmlcode, String message) {
        mGlobalContentView = (TextView) mGlobalDialog.findViewById(R.id.txv_contentview);
        if (ishtmlcode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mGlobalContentView.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
            } else {
                mGlobalContentView.setText(Html.fromHtml(message));
            }
        } else {
            mGlobalContentView.setText(message);
        }
    }

    //THE ACTION FOR THE BUTTONS BEING DISPLAYED. (CLOSE ICON, SINGLE, DOUBLE)
    private void globalDialogButtonActions(String buttonmessage, ButtonTypeEnum buttonTypeEnum,
                                           boolean isretry, boolean isReseller, boolean redirects) {

        try {
            //CLOSE ICON BUTTON
            if (!redirects) {
                mGlobalCloseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideProgressDialog();
                        mGlobalDialog.dismiss();
                        setUpProgressLoaderDismissDialog();
                    }
                });
            } else {
                //CUSTOMIZE REDIRECTION
                if (getViewContext().getClass().equals(MainActivity.class)) {
                    mGlobalCloseBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hideProgressDialog();
                            mGlobalDialog.dismiss();
                            setUpProgressLoaderDismissDialog();
                        }
                    });
                } else if (getViewContext().getClass().equals(GKStoreDetailsActivity.class)) {
                    mGlobalCloseBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hideProgressDialog();
                            mGlobalDialog.dismiss();
                            setUpProgressLoaderDismissDialog();
                            proceedtoHistoryActivity();
                        }
                    });
                } else {
                    mGlobalCloseBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hideProgressDialog();
                            mGlobalDialog.dismiss();
                            setUpProgressLoaderDismissDialog();
                            proceedtoMainActivity();
                        }
                    });
                }
            }

            //BUTTON GLOBAL CONTAINER (SINGLE AND DOUBLE)
            btn_global = (LinearLayout) mGlobalDialog.findViewById(R.id.btn_global);
            btn_global_double_container = (LinearLayout) mGlobalDialog.findViewById(R.id.btn_global_double_container);

            //CHECKS THE TYPE OF BUTTON THAT WILL BE DISPLAYED. DEFAULT WILL BE THE SINGLE BUTTON
            switch (buttonTypeEnum) {
                case DOUBLE:
                    btn_global.setVisibility(View.GONE);
                    btn_global_double_container.setVisibility(View.VISIBLE);
                    break;
                case SINGLE:
                default:
                    btn_global.setVisibility(View.VISIBLE);
                    btn_global_double_container.setVisibility(View.GONE);
                    break;
            }

            //CHECKS IF BUTTON GLOBAL HAS RETRY OR NOT.
            if (!isretry) {
                //SINGLE
                txv_btn_global.setAllCaps(true);
                txv_btn_global.setText(buttonmessage);
                //DOUBLE
                txv_btn_global_double.setAllCaps(true);
                txv_btn_global_double_two.setAllCaps(true);

                //SKYCABLE
                strskybtnaction = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SKY_BUTTON);

                if (!redirects) {
                    if (getViewContext().getClass().equals(MainActivity.class)) {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                mainActivityRefresh();
                            }
                        });

                        txv_btn_global_double.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                mainActivityRefresh();
                            }
                        });
                    } else {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                            }
                        });

                        txv_btn_global_double.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                            }
                        });
                    }
                } else {
                    //CUSTOMIZED REDIRECTION
                    //FIRST BUTTON (SINGLE AND DOUBLE)
                    if (getViewContext().getClass().equals(MainActivity.class)) {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                            }
                        });

                        txv_btn_global_double.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                            }
                        });
                    } else if (getViewContext().getClass().equals(SkyCableActivity.class)) {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                returntoSkyCableHomeFragment();
                            }
                        });

                        txv_btn_global_double.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                returntoSkyCableHomeFragment();
                            }
                        });

                    } else if (getViewContext().getClass().equals(GKStoreDetailsActivity.class)) {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                proceedtoHistoryActivity();
                            }
                        });

                        txv_btn_global_double.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                proceedtoHistoryActivity();
                            }
                        });
                    } else {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                proceedtoMainActivity();
                            }
                        });

                        txv_btn_global_double.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                proceedtoMainActivity();
                            }
                        });
                    }
                }

                //CUSTOMIZED REDIRECTION
                //SECOND BUTTON (DOUBLE)
                if (getViewContext().getClass().equals(BillsPaymentConfirmationActivity.class)) {
                    txv_btn_global_double_two.setText("ADD TO MY BILLERS");
                    txv_btn_global_double_two.setAllCaps(true);
                    txv_btn_global_double_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hideProgressDialog();
                            mGlobalDialog.dismiss();
                            setUpProgressLoaderDismissDialog();
                            ((BillsPaymentConfirmationActivity) getViewContext()).callBillsPaymentVerifySession();
                        }
                    });
                } else if (getViewContext().getClass().equals(SkyCableActivity.class)) {
                    if (strskybtnaction.equals("GOTOHISTORY")) {
                        txv_btn_global_double_two.setText("GO TO HISTORY");
                        txv_btn_global_double_two.setAllCaps(true);
                        txv_btn_global_double_two.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                returntoSkycablePayPerViewHistoryFragment();
                            }
                        });
                    }
                } else {
                    txv_btn_global_double_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            txv_btn_global_double_two.setText("RETURN TO HOME");
                            hideProgressDialog();
                            mGlobalDialog.dismiss();
                            setUpProgressLoaderDismissDialog();
                            proceedtoMainActivity();
                        }
                    });
                }

            } else {
                //IF RETRY IS TRUE...
                btn_global.setVisibility(View.VISIBLE);
                btn_global_double_container.setVisibility(View.GONE);
                //SINGLE
                txv_btn_global.setAllCaps(true);
                txv_btn_global.setText(buttonmessage);

                if (!redirects) {
                    if (getViewContext().getClass().equals(MainActivity.class)) {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                mainActivityRefresh();
                            }
                        });
                    } else {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();

                            }
                        });
                    }
                } else {
                    if (getViewContext().getClass().equals(MainActivity.class)) {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                            }
                        });
                    } else if (getViewContext().getClass().equals(GKStoreConfirmationActivity.class)) {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                returntoBeforePayments();
                            }
                        });
                    } else if (getViewContext().getClass().equals(BillsPaymentConfirmationActivity.class)) {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                returntoLogsBillsPaymentFragment();
                            }
                        });
                    } else if (getViewContext().getClass().equals(PaymentConfirmation.class)) {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                String source = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_PVSOURCE);
                                if (source.contentEquals("FROMMERCHANTEXPRESS")) {
                                    proceedtoMainActivity();
                                } else {
                                    returntoLogsPrepaidFragment();
                                }
                            }
                        });
                    } else if (getViewContext().getClass().equals(RetailerLoadConfirmationActivity.class)) {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                returntoLogsRetailerFragment();
                            }
                        });
                    } else {
                        btn_global.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideProgressDialog();
                                mGlobalDialog.dismiss();
                                setUpProgressLoaderDismissDialog();
                                proceedtoMainActivity();
                            }
                        });
                    }
                }
            }

            //GKNEGOSYO RESELLER
            linearGkNegosyoLayout = (LinearLayout) mGlobalDialog.findViewById(R.id.linearGkNegosyoLayout);
            txvGkNegosyoDescription = (TextView) mGlobalDialog.findViewById(R.id.txvGkNegosyoDescription);
            txvGkNegosyoDescription.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/RobotoCondensed-Regular.ttf", getResources().getString(R.string.str_gk_negosyo_prompt)));
            txvGkNegosyoRedirection = (TextView) mGlobalDialog.findViewById(R.id.txvGkNegosyoRedirection);
            txvGkNegosyoRedirection.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/RobotoCondensed-Regular.ttf", "I WANT THIS!"));

            final DatabaseHandler mdb = new DatabaseHandler(getViewContext());
            txvGkNegosyoRedirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GKNegosyoRedirectionActivity.start(getViewContext(), mdb.getGkServicesData(mdb, "GKNEGOSYO"));
                }
            });

            if (!isReseller) {
                linearGkNegosyoLayout.setVisibility(View.VISIBLE);
            } else {
                linearGkNegosyoLayout.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    //QR CODE
    private void globalQRCodeLayout(String messagecontent) {
        try {
            layout_qrcode_container = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_qrcode_container);
            approvalcodewrap = (TableRow) mGlobalDialog.findViewById(R.id.approvalcodewrap);
            leftarrow = (ImageView) mGlobalDialog.findViewById(R.id.leftarrow);
            successBarcode = (ImageView) mGlobalDialog.findViewById(R.id.successBarcode);
            successQRCode = (ImageView) mGlobalDialog.findViewById(R.id.successQRCode);
            rightarrow = (ImageView) mGlobalDialog.findViewById(R.id.rightarrow);

            layout_values_container = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_values_container);
            barcodeValue = (TextView) mGlobalDialog.findViewById(R.id.barcodeValue);
            merchantRefCodeLabel = (TextView) mGlobalDialog.findViewById(R.id.barcodeValue);
            dialogNote = (TextView) mGlobalDialog.findViewById(R.id.dialogNote);

            dialogNote.setText("Present this QR code/barcode to the merchant for your payment verification.");
            barcodeValue.setText(messagecontent);

            // barcode image
            Bitmap bitmap = null;
            try {
                bitmap = encodeAsBitmap(messagecontent, BarcodeFormat.CODE_128, 600, 300);
                successBarcode.setImageBitmap(bitmap);

                bitmap = encodeAsBitmap(messagecontent, BarcodeFormat.QR_CODE, 600, 300);
                successQRCode.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
                e.getLocalizedMessage();
                e.getMessage();
            }

            layout_qrcode_container.setVisibility(View.VISIBLE);
            approvalcodewrap.setVisibility(View.VISIBLE);
            successQRCode.setVisibility(View.VISIBLE);
            barcodeValue.setVisibility(View.VISIBLE);

            layout_values_container.setVisibility(View.VISIBLE);

            //slide left and right to reverse view of the barcode or QR code
            successQRCode.setOnTouchListener(new OnSwipeTouchListener() {
                public void onSwipeRight() {
                    successQRCode.setVisibility(View.GONE);
                    successBarcode.setVisibility(View.VISIBLE);
                }

                public void onSwipeLeft() {
                    successQRCode.setVisibility(View.GONE);
                    successBarcode.setVisibility(View.VISIBLE);
                }
            });

            successBarcode.setOnTouchListener(new OnSwipeTouchListener() {
                public void onSwipeRight() {
                    successQRCode.setVisibility(View.VISIBLE);
                    successBarcode.setVisibility(View.GONE);
                }

                public void onSwipeLeft() {
                    successQRCode.setVisibility(View.VISIBLE);
                    successBarcode.setVisibility(View.GONE);
                }

            });
            leftarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (successQRCode.getVisibility() == View.VISIBLE) {
                        successQRCode.setVisibility(View.GONE);
                        successBarcode.setVisibility(View.VISIBLE);
                    } else {
                        successQRCode.setVisibility(View.VISIBLE);
                        successBarcode.setVisibility(View.GONE);
                    }

                }

            });

            rightarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (successQRCode.getVisibility() == View.VISIBLE) {
                        successQRCode.setVisibility(View.GONE);
                        successBarcode.setVisibility(View.VISIBLE);
                    } else {
                        successQRCode.setVisibility(View.VISIBLE);
                        successBarcode.setVisibility(View.GONE);
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
            e.getMessage();
        }
    }

    //IMAGE
    private void globalContentImageLogo() {
        if (getViewContext().getClass().equals(SkyCableActivity.class)) {
            Glide.with(getViewContext())
                    .load(R.drawable.skycable_header)
                    .apply(new RequestOptions()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    )
                    .into(mGlobalContentImageView);

            txv_btn_global.setTextColor(getResources().getColor(R.color.color_image_darkblue));
            txv_btn_global_double.setTextColor(getResources().getColor(R.color.color_image_darkblue));
        } else {
            Glide.with(getViewContext())
                    .load(R.drawable.goodkredit_logo)
                    .apply(new RequestOptions()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    )
                    .into(mGlobalContentImageView);
            txv_btn_global.setTextColor(getResources().getColor(R.color.color_image_darkgray));
            txv_btn_global_double.setTextColor(getResources().getColor(R.color.color_image_darkgray));
        }
    }

    //TEXT
    private void globalContentText() {
        if (getViewContext().getClass().equals(SkyCableActivity.class)) {
            //PAYMENT LOGO
            mGlobalPaymentLogoContainer.setVisibility(View.VISIBLE);

            mGlobalTitle.setVisibility(View.VISIBLE);
            mGlobalTitle.setText("Request For Approval");
            mGlobalTitle.setAllCaps(false);
            mGlobalTitle.setTextSize(22);
            mGlobalTitle.setTextColor(getResources().getColor(R.color.color_text_darkblue));

            txv_btn_global.setTextColor(getResources().getColor(R.color.color_text_lightblue));
            txv_btn_global_double.setTextColor(getResources().getColor(R.color.color_text_lightblue));
            txv_btn_global_double_two.setTextColor(getResources().getColor(R.color.color_text_darkblue));

            txv_btn_global.setTextSize(16);
            txv_btn_global_double.setTextSize(16);
            txv_btn_global_double_two.setTextSize(16);
        } else {
            mGlobalTitle.setVisibility(View.VISIBLE);
            mGlobalTitle.setText("Notice!");
            mGlobalTitle.setAllCaps(false);
            mGlobalTitle.setTextSize(22);
            mGlobalTitle.setTextColor(getResources().getColor(R.color.color_text_darkgray));

            txv_btn_global.setTextColor(getResources().getColor(R.color.color_text_lightgray));
            txv_btn_global_double.setTextColor(getResources().getColor(R.color.color_text_lightgray));
            txv_btn_global_double_two.setTextColor(getResources().getColor(R.color.color_text_darkgray));

            txv_btn_global.setTextSize(16);
            txv_btn_global_double.setTextSize(16);
            txv_btn_global_double_two.setTextSize(16);
        }
    }

    //NOTICE
    private void globalContentNotice() {
        if (getViewContext().getClass().equals(SkyCableActivity.class)) {
            //PAYMENT LOGO
            mGlobalPaymentLogoContainer.setVisibility(View.VISIBLE);

            mGlobalTitle.setText("Request For Approval");
            mGlobalTitle.setAllCaps(false);
            mGlobalTitle.setTextSize(22);
            mGlobalTitle.setTextColor(getResources().getColor(R.color.color_information_blue));

            txv_btn_global.setTextColor(getResources().getColor(R.color.color_information_blue));
            txv_btn_global_double.setTextColor(getResources().getColor(R.color.color_information_blue));
            txv_btn_global_double_two.setTextColor(getResources().getColor(R.color.color_information_lightblue));
        } else {
            txv_btn_global.setTextColor(getResources().getColor(R.color.color_information_blue));
            txv_btn_global_double.setTextColor(getResources().getColor(R.color.color_information_blue));
            txv_btn_global_double_two.setTextColor(getResources().getColor(R.color.color_information_lightblue));
        }
    }

    //WARNING
    private void globalContentWarning() {
        txv_btn_global.setTextColor(getResources().getColor(R.color.color_onprocess_yellow));
        txv_btn_global_double.setTextColor(getResources().getColor(R.color.color_onprocess_yellow));
        txv_btn_global_double_two.setTextColor(getResources().getColor(R.color.color_uno_yellow));
    }

    //REFRESH
    private void mainActivityRefresh() {
        globalfragment = (VouchersFragment) getSupportFragmentManager().findFragmentById(R.id.container_body);
        if (globalfragment != null) {
            ((VouchersFragment) globalfragment).onRefresh();
        }
    }

    //CUSTOM REDIRECTION FOR DIALOG ACTION BUTTONS
    //RETURNS TO MAIN ACTIVITY
    public void proceedtoMainActivity() {
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(getViewContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // RETURNS TO BEFORE PAYMENTS (DRAG AND DROP VOUCHER)
    private void returntoBeforePayments() {
        final Handler handlerstatus = new Handler();
        handlerstatus.postDelayed(new Runnable() {
            @Override
            public void run() {
                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                Payment.paymentfinishActivity.finish();
                finish();
            }
        }, 1000);
    }

    //RETURNS TO TRANSACTION LOGS OF BILLSPAYMENT
    private void returntoLogsBillsPaymentFragment() {
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(getViewContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        ViewOthersTransactionsActivity.start(getViewContext(), 5);
    }

    //RETURNS TO TRANSACTION LOGS OF PREPAID FRAGMENT
    private void returntoLogsPrepaidFragment() {
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(getViewContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        ViewOthersTransactionsActivity.start(getViewContext(), 4);
    }

    //RETURNS TO TRANSACTION LOGS OF SMART WALLET FRAGMENT
    private void returntoLogsRetailerFragment() {
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(getViewContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        ViewOthersTransactionsActivity.start(getViewContext(), 6);
    }

    //RETURS TO SKYCABLE HOMEFRAGMENT OR MAINACTIVITY
    private void returntoSkyCableHomeFragment() {
        if (strskybtnaction.equals("GOTOHISTORY")) {
            proceedtoMainActivity();
        } else {
            SkyCableActivity.skycablefinishActivity.finish();
            finish();
            SkyCableActivity.start(getViewContext(), 1, null);
        }
    }

    //RETURNS TO SKYCABLE PAYPERVIEWHISTORY
    private void returntoSkycablePayPerViewHistoryFragment() {
        SkyCableActivity.skycablefinishActivity.finish();
        finish();
        Intent intent = new Intent(getViewContext(), SkyCableActivity.class);
        intent.putExtra("index", 8);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //REDIRECTS TO GKSTOREHISTORY (ACTIVITY)
    private void proceedtoHistoryActivity() {
        String merchantid = PreferenceUtils.getStringPreference(getViewContext(), "gkstmerchantid");
        String storeid = PreferenceUtils.getStringPreference(getViewContext(), "gkststoreid");
        String servicecode = PreferenceUtils.getStringPreference(getViewContext(), "gkstserviecode");

        Intent intent = new Intent(getBaseContext(), GkStoreHistoryActivity.class);
        intent.putExtra("GKSTOREMERCHANTID", merchantid);
        intent.putExtra("GKSTOREID", storeid);
        intent.putExtra("GKSTORESERVICECODE", servicecode);
        startActivity(intent);
    }

    //BARCODE
    private static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {

        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    //--------------------------------------END OF GLOBAL CONFIRMATION DIALOGS------------------------

    //-----------------------------------------COMMON GLOBAL DIALOGS----------------------------------
    //Created on: 06/28/2019 (mm/dd/yy).

    //IF YOUR PREFER A DEFAULT GLOBAL DIALOG WITH DEFAULT ACTIONS.

    //GLOBAL COMMON ERROR DIALOGS
    public void showErrorGlobalDialogs(String content) {
        GlobalDialogs globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("", content, "OK", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        globalDialogs.defaultDialogActions();
    }

    public void showErrorGlobalDialogs() {
        GlobalDialogs globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("", "Oops! Something went wrong. We are working on it as we speak.",
                "OK", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        globalDialogs.defaultDialogActions();
    }

    //GLOBAL COMMON WARNING DIALOGS
    public void showWarningGlobalDialogs(String content) {
        GlobalDialogs globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("", content, "OK", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.WARNING);

        globalDialogs.defaultDialogActions();
    }

    public void showWarningGlobalDialogs() {
        GlobalDialogs globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("", "Something went wrong. Please try again.",
                "OK", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.WARNING);

        globalDialogs.defaultDialogActions();
    }

    //----------------------------------END OF COMMON GLOBAL DIALOGS---------------------------

    /**
     * Gets the state of Airplane Mode.
     *
     * @param context
     * @return true if enabled.
     */
    public boolean isAirplaneModeOn(Context context) {

        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;

    }

    /**
     * Gets the state of GPS Mode.
     *
     * @return true if enabled.
     */
    public boolean isGPSModeOn() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Go to Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    /**
     * Check Address is within given radius
     *
     * @return true if valid. otherwise false
     */
    public boolean checkIfAddressIsWithinRadius(double firstlat, double firstlong, double secondlat, double secondlong, double radius) {

        boolean checkifitswithin = false;

        float[] distance = new float[2];

        Location.distanceBetween(firstlat, firstlong,
                secondlat, secondlong, distance);

        if (distance[0] > radius) {
            checkifitswithin = false;
        } else {
            checkifitswithin = true;
        }

        return checkifitswithin;
    }

    public void showAutoLogoutDialog(String message) {
        //DB
        GlobalDialogs globalDialogs = new GlobalDialogs(this);
        globalDialogs.createDialog("",
                message,
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        globalDialogs.hideContentTitle();
        globalDialogs.hideCloseImageButton();

        View closebtn = globalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                autoLogout();
            }
        });

        View singlebtn = globalDialogs.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                autoLogout();
            }
        });
    }

    private void autoLogout() {
        showProgressDialog(getViewContext(), "", "Logging you out. Please wait ...");
        try {
            if(getViewContext().getClass().equals(MainActivity.class)) {
                ((MainActivity) getViewContext()).inValidSessionWillNowLogout();
            } else {
                Context mainActivityContext = MainActivity.getMainActivityContext();
                MainActivity mainActivity = MainActivity.getMainActivity();

                if(mainActivity != null) {
                    ((MainActivity) mainActivityContext).unsubscribePush();
                     if (isFreeModeEnabled()) {
                        deactivateFreeNet();
                        FreenetSdk.unregisterConnectionListener(mainActivity);
                    }
                }

                DatabaseHandler mdb = new DatabaseHandler(getViewContext());
                //clear variable
                CommonVariables.clearVariables();
                //clear data
                mdb.Logout(mdb);
                PreferenceUtils.clearPreference(getViewContext());
                CacheManager.getInstance().clearCache();

                //open login page
                Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //PROCEED TO COOP HOME
    public void proceedtoCoopHome() {
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(getViewContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getViewContext().startActivity(intent);

        CoopAssistantHomeActivity.start(getViewContext(), 0, null);
    }
}
