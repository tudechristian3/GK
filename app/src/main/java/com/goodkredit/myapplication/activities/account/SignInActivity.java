package com.goodkredit.myapplication.activities.account;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.gcmpush.MainGCM;
import com.goodkredit.myapplication.gcmpush.RegistrationIntentService;
import com.goodkredit.myapplication.model.OtpModel.BorrowerOtp;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericObjectResponse;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdk;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionEvent;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInActivity extends BaseActivity implements View.OnClickListener, FreenetSdkConnectionListener {

    //Declaration
    private DatabaseHandler db;
    private Context mcontext;

    private TextView mobile;
    private TextView password;
    private EditText countrycode;
    private EditText code;
    private TextView forgetpassword;

    private String mobileval;
    private String passwordval;
    private String imei;
    private String sessionid = "";
    private String currentcountryprefix;
    private static final int PERMISSION_READ_STATE = 1;

    private boolean isClicked = false;

    private AlertDialog dialog;

    private ImageView imgNewLogo;

    //VERIFY MOBILE
    private EditText edt_otp;
    private String str_otp = "";
    private String otpmobile;
    private String auth;
    private String from;

    private GlobalDialogs globaldialog = null;

    //ALWAYS SIGN IN
    private CheckBox cbx_alwayssignin;
    private int isalwayssignin = 0;

    //NEW VARIABLES FOR SECURITY UPDATE
    private String index;
    private String keyEncryption;
    private String param;
    private String authenticationid;

    private String requestType = "0";

    private String loginIndex;
    private String loginKeyEncryption;
    private String loginParam;
    private String loginAuthenticationid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        try {
            init();
            initData();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        //initialize object
        countrycode = findViewById(R.id.countrycode);
        code = findViewById(R.id.code);
        forgetpassword = findViewById(R.id.forgetpassword);
        mobile = findViewById(R.id.mobileno);
        password = findViewById(R.id.password);

        imgNewLogo = findViewById(R.id.imageView1);

        cbx_alwayssignin = findViewById(R.id.cbx_alwayssignin);
    }

    private void initData() {
        //set context
        mcontext = this;
        //initialize local db
        db = new DatabaseHandler(this);

        imei = CommonFunctions.getImei(getViewContext());


        Glide.with(getViewContext())
                .load(R.drawable.gk_new_logo)
                .into(imgNewLogo);

        KenBurnsView splash = findViewById(R.id.splash);
        Glide.with(getViewContext())
                .load(R.drawable.mapphil)
                .into(splash);

        code.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_REGULAR, "PH +63"));
        countrycode.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_REGULAR, "63"));

        //TRIGGERS
        //open forget password
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pscode = countrycode.getText().toString();
                String psmobile = mobile.getText().toString();
                if (pscode == null) {
                    pscode = "";
                }
                if (psmobile == null) {
                    psmobile = "";
                }
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(SignInActivity.this);
                }
                Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                PreferenceUtils.saveFromValue(getViewContext(),"FORGETPASSWORD");
                intent.putExtra("PREFIX", pscode);
                intent.putExtra("MOBILE", psmobile);
                startActivity(intent);
            }
        });

        if (isFreeModeEnabled()) {
            FreenetSdk.registerConnectionListener(this);
            activateFreeNet();
            Logger.debug("checkhttp", "Free net available");
        }

        dialog = new AlertDialog.Builder(getViewContext())
                .setMessage("Unable to connect. Free mode works only on SMART, SUN and TNT subscribers. It only works in primary sim (SIM1).")
                .setPositiveButton("Got It", (dialog, which) -> dialog.dismiss()).create();

        cbx_alwayssignin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isalwayssignin = 1;
            } else {
                isalwayssignin = 0;
            }
        });


    }


    @Override
    protected void onResume() {
        //show overlay if mobile data on and valid operator
        setupOverlay();
        findViewById(R.id.freeModeButton).setOnClickListener(this);
        //set overlay messages
        setOverlayGUI(PreferenceUtils.getIsFreeMode(getViewContext()));
        super.onResume();
    }

    @SuppressLint("HardwareIds")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String[] permissions, @NotNull int[] grantResults) {

        try {
            if (requestCode == PERMISSION_READ_STATE) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        imei = tm.getImei();
                    } else {
                        imei = tm.getDeviceId();
                    }
                } else {
                    imei = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // Check which request we're responding to
            if (requestCode == 1) {
                if (resultCode == Activity.RESULT_OK) {
                    String countrycodeval = data.getStringExtra("CountryCode");
                    String countryprefix = data.getStringExtra("CountryPrefix");
                    countrycode.setText(countryprefix);
                    if (!countrycodeval.equals("")) {
                        code.setText(countrycodeval + " +" + countryprefix);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void openCountryList(View view) {

    }

    public void openMainPage(View view) {
        try {

            int status = CommonFunctions.getConnectivityStatus(this);
            if (status == 0) {
                CommonFunctions.showSnackbar(getViewContext(), findViewById(R.id.parent), "No internet connection.", "ERROR");
            } else {

                mobileval = mobile.getText().toString();
                passwordval = password.getText().toString();
                currentcountryprefix = countrycode.getText().toString();

                //Validate input
                if (currentcountryprefix.equals("") || currentcountryprefix == null) {
                    showToast("Please select your country code.", GlobalToastEnum.WARNING);
                } else if (mobileval.equals("") || passwordval.equals("")) {
                    //showToast("All fields are mandatory", GlobalToastEnum.WARNING);
                    CommonFunctions.showSnackbar(getViewContext(), findViewById(R.id.parent), "All fields are mandatory.", "WARNING");
                } else if (mobileval.length() < 10) {
                    mobile.setError("Invalid mobile number.");
                    mobile.requestFocus();

                } else if(mobileval.length() == 10) {

                    if (currentcountryprefix.trim().equals("63")) {
                        String first = mobileval.trim().substring(0, 1);
                        if (first.equals("0")) {
                            mobileval = mobileval.substring(1);
                        }
                    }
                    imei = CommonFunctions.checkImeiIfNull(imei, mobileval);
                    verifySession();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openSignUpPage(View view) {
        try {
            if (isFreeModeEnabled()) {
                deactivateFreeNet();
                FreenetSdk.unregisterConnectionListener(this);
            }

            PreferenceUtils.saveFromValue(getViewContext(),".");
            Intent intent = new Intent(this, SignUpActivity.class);
            intent.putExtra("PREFIX", "");
            intent.putExtra("MOBILE", "");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public boolean matchUserID(String username) {
        Logger.debug("okhttp", "MATCH USER ID : " + username);
        boolean ismatch = false;
        Cursor cursor = db.getData(db, username);
        Logger.debug("okhttp", "CURSOR COUNT: " + cursor.getCount());
        if (cursor.getCount() > 0) {
            ismatch = true;
        }
        return ismatch;
    }

    private void verifySession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            if (isDataEnabled()) {
                setupOverlay();
                setOverlayGUI(PreferenceUtils.getIsFreeMode(getViewContext()));
            }
            CommonFunctions.showDialog(mcontext, "", "Checking request. Please wait ...", false);
            /*|***With Security*****|*/
            try {
                if (db.getBorrowersOTP(db) == null || db.getBorrowersOTP(db).size() <= 0) {
                    Logger.debug("okhttp", "BORROWERS OTP IS NULL");
                    signInUser("login");
                }else {
                    if (matchUserID(userid)) {
                        Logger.debug("okhttp", "MATCH USERID ");
                        signInUser("verifyLogin");
                    } else {
                        Logger.debug("okhttp", "USERID NOT MATCH  ");
                        signInUser("login");
                    }
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } else {
            CommonFunctions.hideDialog();
            showNoInternetToast();
        }
    }


    @Override
    public void onBackPressed() {
        if (isFreeModeEnabled()) {
            deactivateFreeNet();
            FreenetSdk.unregisterConnectionListener(this);
            PreferenceUtils.saveIsFreeMode(getViewContext(), false);
        }
        super.onBackPressed();
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

    private void showFreeModeAllowedDialogNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("Notice", " Unable to connect. Free mode works only on SMART, SUN " +
                        "and TNT subscribers. It only works in primary sim (SIM1).", "Got It",
                "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.NOTICE);

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(view -> dialog.dismiss());

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(view -> dialog.dismiss());
    }

    @Override
    public void onConnectionChange(FreenetSdkConnectionEvent freenetSdkConnectionEvent) {
        Logger.debug("getStatus", "STATUS: " + freenetSdkConnectionEvent.getStatus());
        Logger.debug("getErrorDescription", "Error Desc: " + freenetSdkConnectionEvent.getErrorDescription());
        Logger.debug("getErrorCode", "Error Code: " + freenetSdkConnectionEvent.getErrorCode());
        Logger.debug("toString", "toString: " + freenetSdkConnectionEvent.toString());

        if (freenetSdkConnectionEvent.getStatus() == 1) {
            if (isClicked) {
                hideProgressDialog();
                setOverlayGUI(true);
                PreferenceUtils.saveIsFreeMode(getViewContext(), true);
                isClicked = false;
            }
        } else if (freenetSdkConnectionEvent.getStatus() == 0) {
            if (isClicked) {
                hideProgressDialog();
                setOverlayGUI(false);
                PreferenceUtils.saveIsFreeMode(getViewContext(), false);
                isClicked = false;
            }
        } else {
            hideProgressDialog();
            switch (freenetSdkConnectionEvent.getErrorCode()) {
                case 4022:
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(SignInActivity.this);

                    showToast("Please check network configuration on your device.", GlobalToastEnum.WARNING);
                    break;
                case 4020:
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(SignInActivity.this);
                    showToast("Unsupported network settings on the device.", GlobalToastEnum.WARNING);
                    showFreeModeAllowedDialogNew();

                    break;
                case 4023:
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(SignInActivity.this);

                    showToast("Android version of the device is not supported", GlobalToastEnum.WARNING);
                    break;
                case 4001:
                case 4002:
                case 4011:
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(SignInActivity.this);

                    showToast("Internal error occurred. (Params)", GlobalToastEnum.ERROR);
                    break;
                case 4012:
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(SignInActivity.this);

                    showToast("Internal error occurred. (Expired) ", GlobalToastEnum.ERROR);
                    break;
                case 4021:
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(SignInActivity.this);

                    showToast("Internal error occurred. (SDK_Exception) ", GlobalToastEnum.ERROR);
                    break;
            }
        }
    }

    private void proceedMainPageV2(String profilejson) {

        hideSoftKeyboard();
        try {
            Logger.debug("okhttp", "PJSON : " + profilejson);

            String borrowerid = CommonFunctions.parseJSON(profilejson, "BorrowerID");
            String firstname = CommonFunctions.parseJSON(profilejson, "FirstName");
            String middlename = CommonFunctions.parseJSON(profilejson, "MiddleName");
            String lastname = CommonFunctions.parseJSON(profilejson, "LastName");
            String birthdate = CommonFunctions.parseJSON(profilejson, "BirthDate");
            String gender = CommonFunctions.parseJSON(profilejson, "Gender");
            String occupation = CommonFunctions.parseJSON(profilejson, "Occupation");
            String email = CommonFunctions.parseJSON(profilejson, "EmailAddress");
            String mobilenumber = CommonFunctions.parseJSON(profilejson, "MobileNo");
            String streetaddress = CommonFunctions.parseJSON(profilejson, "StreetAddress");
            String city = CommonFunctions.parseJSON(profilejson, "City");
            String dateregistered = CommonFunctions.parseJSON(profilejson, "DateRegistration");
            String country = CommonFunctions.parseJSON(profilejson, "Country");
            String guarantorid = CommonFunctions.parseJSON(profilejson, "GuarantorID");
            String profilepic = CommonFunctions.parseJSON(profilejson, "ProfilePicURL");
            String borrowertype = CommonFunctions.parseJSON(profilejson, "BorrowerType");
            String guarantorregistrationstatus = CommonFunctions.parseJSON(profilejson, "Extra1");

            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_BORROWER_ID);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_USER_ID);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_IMEI);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_BORROWER_NAME);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_SESSIONID);
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_VERSION_CODE);

            PreferenceUtils.saveBorrowerID(getViewContext(), borrowerid);
            PreferenceUtils.saveUserID(getViewContext(), mobilenumber);
            PreferenceUtils.saveImeiID(getViewContext(), imei);
            PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_BORROWER_NAME, firstname + " " + lastname);
            PreferenceUtils.saveSessionID(getViewContext(), sessionid);
            PreferenceUtils.saveGKVersionCodePreference(getViewContext(),
                    getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);

            //save to local database
            db.insertAccountDetails(db, borrowerid, mobilenumber, firstname, middlename, lastname, email, streetaddress,
                    city, dateregistered, country, guarantorid, gender, profilepic, guarantorregistrationstatus, imei);

            if (firstname.equals(".") && lastname.equals(".")) {
                db.updateAccount(db, borrowerid, "GUARANTORSETUP", mobilenumber);
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(this);
                }
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(this);
                }
                Intent intent = new Intent(this, GuarantorVerificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else {
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(this);
                }
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                Intent intent = new Intent(getViewContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     **/

    private void signInUser(String key) {

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            String mobile = currentcountryprefix + mobileval;

            String paramJson = "";
            String jsonString = "";

            LinkedHashMap indexAuthMapObject = null;

            //PARAMETERS
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

            switch (key) {
                case "login":

                    parameters.put("imei", imei);
                    parameters.put("userid", mobile);
                    parameters.put("password", passwordval);
                    parameters.put("devicetype", CommonVariables.devicetype);
                    parameters.put("type", "LOGIN");
                    parameters.put("isalwayssignin", String.valueOf(isalwayssignin));

                    //ENCRYPTION
                    indexAuthMapObject = CommonFunctions.getIndexAndAuthenticationID(parameters);
                    jsonString = new Gson().toJson(indexAuthMapObject, Map.class);

                    loginIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");
                    parameters.put("index", loginIndex);

                    paramJson = new Gson().toJson(parameters, Map.class);
                    loginAuthenticationid = CommonFunctions.getSha1Hex(CommonFunctions.parseJSON(String.valueOf(jsonString), "authenticationid"));
                    loginKeyEncryption = CommonFunctions.getSha1Hex(loginAuthenticationid + "loginV2");
                    loginParam = CommonFunctions.encryptAES256CBC(loginKeyEncryption, String.valueOf(paramJson));

                    getSignInUserObject(getVerifySignInUser);

                    break;


                case "verifyLogin":

                    parameters.put("imei", imei);
                    parameters.put("userid", mobile);
                    parameters.put("usertype", CommonVariables.usertype);
                    parameters.put("devicetype", CommonVariables.devicetype);
                    parameters.put("otp", str_otp);
                    parameters.put("requestType", requestType);
                    parameters.put("isalwayssignin", String.valueOf(isalwayssignin));

                    //ENCRYPTION
                    indexAuthMapObject = CommonFunctions.getIndexAndAuthenticationID(parameters);
                    jsonString = new Gson().toJson(indexAuthMapObject, Map.class);

                    index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");
                    parameters.put("index", index);

                    paramJson = new Gson().toJson(parameters, Map.class);
                    authenticationid = CommonFunctions.getSha1Hex(CommonFunctions.parseJSON(String.valueOf(jsonString), "authenticationid"));
                    keyEncryption = CommonFunctions.getSha1Hex(authenticationid + "verifyLoginV2");
                    param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                    getVerifyLoginV2(getVerifyLoginCallback);

                    break;

            }

        } else {
            showNoInternetToast();
        }

    }

    private void getSignInUserObject(Callback<GenericResponse> getSignInUserObjectCallback) {

        Call<GenericResponse> getSignInUserObject = RetrofitBuilder.getAccountV2API(getViewContext())
                .loginV2(loginAuthenticationid,
                        loginParam);

        getSignInUserObject.enqueue(getSignInUserObjectCallback);
    }

    private final Callback<GenericResponse> getVerifySignInUser = new Callback<GenericResponse>() {
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            CommonFunctions.hideDialog();
            if (errorBody == null) {
                String decryptedmessage = CommonFunctions.decryptAES256CBC(loginKeyEncryption, response.body().getMessage());
                switch (response.body().getStatus()) {
                    case "000":
                        String decrypteddata = CommonFunctions.decryptAES256CBC(loginKeyEncryption, response.body().getData());
                        if (decrypteddata.equals("error") || decryptedmessage.equals("error")) {
                            showErrorGlobalDialogs();
                        } else {

                            hideSoftKeyboard();

                            String profile = CommonFunctions.parseJSON(decrypteddata, "profile");
                            sessionid = CommonFunctions.parseJSON(decrypteddata, "sessionid");

                            Logger.debug("okhttp", "PROFILE: " + profile);
                            Logger.debug("okhttp", "Session ID: " + sessionid);

                            String privatekey = CommonFunctions.getSha1Hex(CommonFunctions.parseJSON(profile, "RecentOTP"));
                            List<String> strSplitPrivateKey;

                            Logger.debug("okhttp", "OTP: " + CommonFunctions.parseJSON(profile, "RecentOTP"));

                            strSplitPrivateKey = CommonFunctions.splitOtpList(privatekey);
                            db.updateOTP(db, userid, CommonFunctions.parseJSON(profile, "RecentOTP"));

                            PreferenceUtils.removePreference(getViewContext(), "sha1otp");
                            PreferenceUtils.saveListPreference(getViewContext(), "sha1otp", strSplitPrivateKey);

                            proceedMainPageV2(profile);
                        }
                        break;
                    case "201":
                        if (globaldialog != null) {
                            globaldialog.dismiss();
                            return;
                        }
                        requestType = ".";
                        //enterOtpDialog();
                        dialog(decryptedmessage);
                        break;
                    default:
                        if (response.body().getStatus().equalsIgnoreCase("error")) {
                            showError();
                        } else {
                            Toast.makeText(getViewContext(), decryptedmessage, Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            } else {
                showErrorGlobalDialogs();
            }
        }


        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            Toast.makeText(getViewContext(), "Oops! We failed to connect to the service. Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
        }
    };

    private void getVerifyLoginV2(Callback<GenericResponse> getVerifyLoginCallback) {

        Call<GenericResponse> getVerifyLoginObject = RetrofitBuilder.getAccountV2API(getViewContext())
                .verifyLoginV2(authenticationid,
                        param);
        getVerifyLoginObject.enqueue(getVerifyLoginCallback);
    }

    private final Callback<GenericResponse> getVerifyLoginCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    String decryptedmessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                    if (response.body().getStatus().equals("000")) {
                        String decrypteddata = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decrypteddata.equals("error") || decryptedmessage.equals("error")) {
                            showErrorGlobalDialogs();
                        } else {

                            String otpprofile = CommonFunctions.parseJSON(decrypteddata, "profile");
                            sessionid = CommonFunctions.parseJSON(decrypteddata, "sessionid");

                            Logger.debug("okhttp", "sessionid " + sessionid);
                            Logger.debug("okhttp", "decrypteddata " + decrypteddata);

                            String userID = CommonFunctions.parseJSON(otpprofile, "UserID");

                            try {
                                try {
                                    if (matchUserID(userID)) {
                                        BorrowerOtp sponsorOTP = new BorrowerOtp(userID, str_otp);
                                        Logger.debug("okhttp", "==============================: USER ID MATCH - UPDATE BORROWER DATA " + new Gson().toJson(sponsorOTP));
                                        db.updateOTP(db, userID, str_otp);

                                        //ENCRYPTION
                                        String privatekey = CommonFunctions.getSha1Hex(str_otp);
                                        List<String> strSplitPrivateKey;

                                        assert privatekey != null;

                                        strSplitPrivateKey = CommonFunctions.splitOtpList(privatekey);
                                        PreferenceUtils.saveListPreference(getViewContext(), "sha1otp", strSplitPrivateKey);

                                    } else {
                                        Logger.debug("okhttp", "==============================: USER ID NOT MATCH - INSERT TO DB");
                                        db.insertBorrowersOTP(db, new BorrowerOtp(userID, str_otp));

                                        //ENCRYPTION
                                        String privatekey = CommonFunctions.getSha1Hex(str_otp);
                                        List<String> strSplitPrivateKey;

                                        assert privatekey != null;

                                        strSplitPrivateKey = CommonFunctions.splitOtpList(privatekey);
                                        PreferenceUtils.saveListPreference(getViewContext(), "sha1otp", strSplitPrivateKey);

                                    }
                                    mDialog.dismiss();
                                    proceedMainPageV2(otpprofile);

                                } catch (SQLiteException e) {
                                    Logger.debug("okhttp", "ERROR: " + e.getMessage());
                                }
                                proceedMainPageV2(otpprofile);

                            } catch (SQLiteException e) {
                                Logger.debug("okhttp", "ERROR: " + e.getMessage());
                            }


                        }
                    } else if (response.body().getStatus().equals("066")) {
                        str_otp = "";
                        signInUser("login");
                    } else {
                        if (response.body().getStatus().equalsIgnoreCase("error")) {
                            showError();
                        } else {
                            Toast.makeText(getViewContext(), decryptedmessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            Toast.makeText(getViewContext(), "Oops! We failed to connect to the service. Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
        }
    };

    private void dialog(String message){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(SignInActivity.this);
        builder.title("OTP Verification");
        builder.customView(R.layout.custom_view_edittext, true);
        builder.positiveText("OK");
        builder.autoDismiss(false);
        builder.negativeText("Cancel");
        builder.canceledOnTouchOutside(false);
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                assert mDialog.getInputEditText() != null;
                TextInputEditText et = (TextInputEditText) dialog.findViewById(R.id.dialog_otp_edittext);
                et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                if (et.getText().toString().isEmpty()) {
                    et.setError("Please enter OTP code.");
                } else {
                    if(et.getText().toString().length() == 6){
                        str_otp = et.getText().toString();
                        showProgressDialog(getViewContext(), "", "Authenticating. Please wait...");
                        signInUser("verifyLogin");
                         mDialog = dialog;
                    }else{
                        et.setError("Invalid OTP Code.");
                    }
                }
            }
        });
        builder.onNegative((dialog, which) -> dialog.dismiss());
        mDialog = builder.build();
        TextInputLayout etLayout = (TextInputLayout) mDialog.findViewById(R.id.dialog_otp_textinputlayout);
        etLayout.setHelperText(message);
        mDialog.show();
    }
}
