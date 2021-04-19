package com.goodkredit.myapplication.activities.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.account.SignInActivity;
import com.goodkredit.myapplication.activities.account.WelcomePageActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.cropimage.GOTOConstants;
import com.goodkredit.myapplication.cropimage.PicModeSelectDialogFragment;
import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.profile.GetSubscribersSponsorRequestStatusResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.view.ParallaxScollListView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdk;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionEvent;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.goodkredit.myapplication.database.DatabaseHandler.ACCOUNTS;

public class SubscriberProfileActivity extends BaseActivity implements PicModeSelectDialogFragment.IPicModeSelectListener, FreenetSdkConnectionListener {

    //Declaration
    private DatabaseHandler db;
    private CommonFunctions cf;
    private CommonVariables cv;
    private Context mcontext;
    private EditText firstname;
    private EditText middlename;
    private EditText lastname;
    private EditText email;
    private EditText streetaddress;
    private EditText city;
    private EditText message;
    private TextView messagelbl;
    private ImageView mImageView;
    private ParallaxScollListView mListView;
    private EditText guarantorcode;
    private TextView guarantorcodelbl;
    private Spinner spinType;
    private Button register;
    private TextView mobile;
    private View view;

    private ProfileLogo target;

    private String borroweridval = ".";
    private String mobileval = ".";
    private String firstnameval = ".";
    private String middlenameval = ".";
    private String lastnameval = ".";
    private String emailval = ".";
    private String streetaddressval = ".";
    private String cityval = ".";
    private String messageval = ".";
    private String guarantoridval = ".";
    private String subguarantoridval = ".";
    private String sessionid = ".";
    private String imei = ".";
    private String countryval = ".";
    private String gender = ".";
    private String guarantorname = ".";
    private String guarantoremail = ".";
    private String profilepic = ".";
    private String from = ".";
    private String previousactivity = "";
    private String newmemberupdate = "true";
    private String issubguarantor = "0";
    private boolean isguarantorregistered = false;
    private String guarantorregistrationstatus = "";

    //spinner
    private ArrayAdapter<String> spinTypeAdapter;
    private String[] selecttype;

    /*IMAGE HERE*/
    private ImageView btnSelect;
    private Uri picUri;

    private String imageurl = "";
    private ProgressBar imageprogressbar;
    private TextView uploadlbl;
    private static String REQUESTINDICATOR = "";
    public static final int REQUEST_CODE_UPDATE_PIC = 0x1;
    private static final int PERMISSION_CAMERA = 2;
    private static final int PERMISSION_WRITE_EXTERNALSTORAGE = 3;
    String awskey = "";
    String secretkey = "";

    private AQuery aq;
    private boolean isMenuHidden = false;

    //NEW VARIABLES FOR SECURITY
    private String param;
    private String keyEncryption;
    private String authenticationid;
    private String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiling);

        try {
            //initializing local db
            db = new DatabaseHandler(this);
            aq = new AQuery(this);

            //set toolbar
            setupToolbar();

            //setting context
            mcontext = this;

            //for the profile pic
            target = new ProfileLogo();

            sessionid = PreferenceUtils.getSessionID(getViewContext());
            //get local
            getLocalInformation();

            //initialize element here
            mListView = findViewById(R.id.layout_listview);

            //get the header view
            View header = LayoutInflater.from(this).inflate(R.layout.activity_profile_registrationheader, null);
            //get the header elements
            mImageView = header.findViewById(R.id.layout_header_image);
            spinType = header.findViewById(R.id.loadtype);
            selecttype = getResources().getStringArray(R.array.gender);
            email = header.findViewById(R.id.email);
            firstname = header.findViewById(R.id.firstname);
            middlename = header.findViewById(R.id.middlename);
            lastname = header.findViewById(R.id.lastname);
            email = header.findViewById(R.id.email);
            streetaddress = header.findViewById(R.id.streetaddress);
            city = header.findViewById(R.id.city);
            message = header.findViewById(R.id.message);
            messagelbl = header.findViewById(R.id.messagelbl);
            guarantorcode = header.findViewById(R.id.guarantoridval);
            guarantorcodelbl = header.findViewById(R.id.guarantoridlbl);
            mobile = header.findViewById(R.id.mobile);
            btnSelect = header.findViewById(R.id.btnSelectPhoto);
            register = header.findViewById(R.id.register);

            uploadlbl = header.findViewById(R.id.uploadlbl);
            imageprogressbar = header.findViewById(R.id.imageprogressbar);

            // spinType.setOnItemSelectedListener(mcontext);
            spinTypeAdapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_spinner_dropdown_item, selecttype);
            spinTypeAdapter.setDropDownViewResource(R.layout.spinner_arrow);
            spinType.setAdapter(spinTypeAdapter);

            //set image the pull scroll image
            mListView.setZoomRatio(ParallaxScollListView.ZOOM_X2);
            mListView.setParallaxImageView(mImageView);
            mListView.addHeaderView(header);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_expandable_list_item_1,
                    new String[]{
                            "",
                    }
            );
            mListView.setAdapter(adapter);

            //get passed parameter from other activity
            Bundle b = getIntent().getExtras();
            guarantorname = b.getString("GUARANTORNAME");
            guarantoremail = b.getString("GUARANTOREMAIL");
            from = b.getString("FROM");
            issubguarantor = b.getString("ISSUBGUARANTOR");
            guarantoridval = b.getString("GUARANTORID");
            previousactivity = b.getString("PREVACTIVITY");


            if(guarantorname == null || guarantorname.isEmpty()){
                guarantorname = ".";
            }

            if(guarantoremail == null || guarantoremail.isEmpty()){
                guarantoremail = ".";
            }

            if(guarantoridval == null || guarantoridval.isEmpty()){
                guarantoridval = ".";
            }

            //check where this came from (SKIP GUARANTOR, REGISTER GUARANTOR OR MAIN ACTIVITY)
            if (from.equals("GUARANTORVERIFICATION")) {
                prepareForGuarantorRegistration();
                isguarantorregistered = true;

            } else if (from.equals("SKIPVERIFICATION")) {
                validateOrdinaryProfiling();
                isguarantorregistered = false;

            } else if (from.equals("MAINACTIVITY")) {
                newmemberupdate = "false";
                validateOrdinaryProfiling();
                isguarantorregistered = false;
            } else if (from.equals("SPONSORDETAILS")) {
                prepareForGuarantorRegistration();
                isguarantorregistered = true;
            }

            checkPermissions();


            /*TRIGGERS HERE*/

            uploadlbl.setVisibility(View.INVISIBLE);
            imageprogressbar.setVisibility(View.INVISIBLE);

            btnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showAddProfilePicDialog();
                }
            });

            spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                protected Adapter initializedAdapter = null;

                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    gender = parentView.getItemAtPosition(position).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }
            });

            if (isFreeModeEnabled()) {
                FreenetSdk.registerConnectionListener(this);
                activateFreeNet();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            getLocalInformation();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            //check where this came from (SKIP GUARANTOR, REGISTER GUARANTOR OR MAIN ACTIVITY)
            if (from.equals("SKIPVERIFICATION")) {

                SQLiteDatabase database = db.getWritableDatabase();
                String query = DBUtils.DELETE + ACCOUNTS + "";
                database.execSQL(query);

                Intent intent = new Intent(getViewContext(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else  {
               finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*--------------
     * FUNCTIONS
     * --------------*/

    private void getLocalInformation() {
        try {
            //get all local data
            Cursor cursor = db.getAccountInformation(db);

            Logger.debug("okhttp", "ACCOUNT INFO: " + cursor.getCount());
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borroweridval = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    mobileval = cursor.getString(cursor.getColumnIndex("mobile"));
                    emailval = cursor.getString(cursor.getColumnIndex("email"));
                    firstnameval = cursor.getString(cursor.getColumnIndex("firstname"));
                    lastnameval = cursor.getString(cursor.getColumnIndex("lastname"));
                    middlenameval = cursor.getString(cursor.getColumnIndex("middlename"));
                    streetaddressval = cursor.getString(cursor.getColumnIndex("streetaddress"));
                    cityval = cursor.getString(cursor.getColumnIndex("city"));
                    profilepic = cursor.getString(cursor.getColumnIndex("profilepic"));
                    countryval = cursor.getString(cursor.getColumnIndex("country"));
                    gender = cursor.getString(cursor.getColumnIndex("gender"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                    guarantorregistrationstatus = cursor.getString(cursor.getColumnIndex("guarantorregistrationstatus"));
                    imageurl = profilepic;
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareForGuarantorRegistration() {

        try {

            //show message fields
            message.setVisibility(View.VISIBLE);
            messagelbl.setVisibility(View.VISIBLE);
            guarantorcode.setVisibility(View.VISIBLE);
            guarantorcodelbl.setVisibility(View.VISIBLE);

            //show register button
            register.setVisibility(View.VISIBLE);
            register.setText("REGISTER");
            mobile.setText("+" + mobileval);

            guarantorcode.setText(guarantoridval);

            //set the information
            if (previousactivity.equals("SETTINGS") || previousactivity.equals("SPONSORDETAILS")) {
                lastname.setText(lastnameval);
                firstname.setText(firstnameval);
                middlename.setText(middlenameval);
                email.setText(emailval);
                streetaddress.setText(streetaddressval);
                city.setText(cityval);

                String[] gendr = getResources().getStringArray(R.array.gender);
                spinType.setSelection(Arrays.asList(gendr).indexOf(gender));
            }

            if (profilepic != null && !profilepic.isEmpty() && !profilepic.equals("null") && profilepic.contains("http")) {
                Picasso.with(getViewContext()).load(profilepic).into(target);

            } else {
                Drawable myDrawable = getResources().getDrawable(R.drawable.emptyprofilepic);
                Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                mImageView.setImageBitmap(myLogo);

            }

        } catch (Exception e) {
        }
    }

    public void validateOrdinaryProfiling() {
        try {

            Bitmap bm = getBitmapFromURL(profilepic);

            if (from.equals("SKIPVERIFICATION")) {
                //set the button to update
                register.setVisibility(View.VISIBLE);
                register.setText("REGISTER");
                mobile.setText("+" + mobileval);
                guarantorcode.setVisibility(View.GONE);
                guarantorcodelbl.setVisibility(View.GONE);
            } else {
                //set the button to update
                register.setVisibility(View.VISIBLE);
                register.setText("UPDATE");
                mobile.setText("+" + mobileval);
                guarantorcode.setVisibility(View.VISIBLE);
                guarantorcodelbl.setVisibility(View.VISIBLE);
            }


            //hide message to guarantor
            message.setVisibility(View.GONE);
            messagelbl.setVisibility(View.GONE);


            //set value
            if (emailval.equals(".")) {
                emailval = "";
            }
            if (streetaddressval.equals(".")) {
                streetaddressval = "";
            }
            if (cityval.equals(".")) {
                cityval = "";
            }
            if (lastnameval.equals(".")) {
                lastnameval = "";
            }
            if (firstnameval.equals(".")) {
                firstnameval = "";
            }
            if (middlenameval.equals(".")) {
                middlenameval = "";
            }

            lastname.setText(lastnameval);
            firstname.setText(firstnameval);
            middlename.setText(middlenameval);
            email.setText(emailval);
            streetaddress.setText(streetaddressval);
            city.setText(cityval);

            String[] gendr = getResources().getStringArray(R.array.gender);
            spinType.setSelection(Arrays.asList(gendr).indexOf(gender));


            if (guarantoridval != null && !guarantoridval.equals(".") && !guarantoridval.equals("")) {
                guarantorcode.setText(guarantoridval);
                guarantorcodelbl.setVisibility(View.VISIBLE);
                guarantorcode.setVisibility(View.VISIBLE);
                disableUpdating();
            } else {
                guarantorcode.setText("");
                guarantorcodelbl.setVisibility(View.GONE);
                guarantorcode.setVisibility(View.GONE);
            }

            if (profilepic != null && !profilepic.isEmpty() && !profilepic.equals("null") && profilepic.contains("http")) {
                Picasso.with(getViewContext()).load(profilepic).into(target);
            } else {
                Drawable myDrawable = getResources().getDrawable(R.drawable.emptyprofilepic);
                Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                mImageView.setImageBitmap(myLogo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void disableUpdating() {

        try {

            //hide not needed element
            register.setVisibility(View.GONE);
            message.setVisibility(View.GONE);
            messagelbl.setVisibility(View.GONE);

            //show needed elements
            guarantorcode.setVisibility(View.VISIBLE);
            guarantorcodelbl.setVisibility(View.VISIBLE);

            email.setFocusable(false);
            firstname.setFocusable(false);
            middlename.setFocusable(false);
            lastname.setFocusable(false);
            streetaddress.setFocusable(false);
            city.setFocusable(false);
            message.setFocusable(false);
            guarantorcode.setFocusable(false);
            spinType.setFocusable(false);

            email.setFocusableInTouchMode(false);
            firstname.setFocusableInTouchMode(false);
            middlename.setFocusableInTouchMode(false);
            lastname.setFocusableInTouchMode(false);
            streetaddress.setFocusableInTouchMode(false);
            city.setFocusableInTouchMode(false);
            message.setFocusableInTouchMode(false);
            guarantorcode.setFocusableInTouchMode(false);
            spinType.setFocusableInTouchMode(false);

            email.setLongClickable(false);
            firstname.setLongClickable(false);
            middlename.setLongClickable(false);
            lastname.setLongClickable(false);
            streetaddress.setLongClickable(false);
            city.setLongClickable(false);
            message.setLongClickable(false);
            guarantorcode.setLongClickable(false);
            spinType.setLongClickable(false);

            email.setClickable(false);
            firstname.setClickable(false);
            middlename.setClickable(false);
            lastname.setClickable(false);
            streetaddress.setClickable(false);
            city.setClickable(false);
            message.setClickable(false);
            guarantorcode.setClickable(false);
            spinType.setClickable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void SaveProfile(View view) {
        try {
            //1.
            int status = CommonFunctions.getConnectivityStatus(this);
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else { //has connection proceed

                //2.
                firstnameval = firstname.getText().toString();
                middlenameval = middlename.getText().toString();
                lastnameval = lastname.getText().toString();
                emailval = email.getText().toString();
                streetaddressval = streetaddress.getText().toString();
                cityval = city.getText().toString();
                messageval = message.getText().toString();

                if (!firstnameval.equals("")) {
                    firstnameval = firstnameval.toUpperCase();
                }
                if (!middlenameval.equals("")) {
                    middlenameval = middlenameval.toUpperCase();
                }
                if (!lastnameval.equals("")) {
                    lastnameval = lastnameval.toUpperCase();
                }
                if (!streetaddressval.equals("")) {
                    streetaddressval = streetaddressval.toUpperCase();
                }
                if (!cityval.equals("")) {
                    cityval = cityval.toUpperCase();
                }
                if (!messageval.equals("")) {
                    messageval = messageval.toUpperCase();
                }
                if (!gender.equalsIgnoreCase("Select Gender")) {
                    gender = gender.toUpperCase();
                } else {
                    gender = ".";
                }
                if (!guarantorname.equals("")) {
                    guarantorname = guarantorname.toUpperCase();
                }
                if (!countryval.equals("")) {
                    countryval = countryval.toUpperCase();
                }

                if (from.equals("MAINACTIVITY") || from.equals("SKIPVERIFICATION")) {

                    if(firstnameval.isEmpty() || firstnameval.equals(".") ||
                      lastnameval.isEmpty() || lastnameval.equals(".") ||
                       gender.isEmpty() || gender.equalsIgnoreCase("Select Gender") ||
                       emailval.isEmpty() || emailval.equals(".")||
                       streetaddressval.isEmpty() || streetaddressval.equals(".") ||
                       cityval.isEmpty() || cityval.equals(".")
                      ){
                        showToast("All fields are mandatory.",GlobalToastEnum.INFORMATION);
                    }else{
                        if (!CommonFunctions.isValidEmail(emailval.trim())) {
                            showToast("Invalid Email Address.", GlobalToastEnum.WARNING);
                        } else {
                            REQUESTINDICATOR = "";
                            verifySession();
                        }
                    }

                } else if (from.equals("GUARANTORVERIFICATION") || from.equals("SPONSORDETAILS")) {


                    if (firstnameval.equals(".") || firstnameval.equals("")  || lastnameval.equals(".") || lastnameval.equals("") || emailval.equals(".") || streetaddressval.equals(".") || cityval.equals(".") || messageval.equals(".") || messageval.equals("") || gender.equals(".")) {
                        showToast("All fields are mandatory", GlobalToastEnum.WARNING);
                    } else if (!CommonFunctions.isValidEmail(emailval.trim())) {
                        showToast("Invalid Email Address.", GlobalToastEnum.WARNING);
                    } else {
                        //3.

                        //check if guarantorid is a subguarantorid
                        if (issubguarantor.equals("1")) {
                            subguarantoridval = guarantoridval.toUpperCase();
                            guarantoridval = ".";
                        } else {
                            subguarantoridval = ".";
                            guarantoridval = guarantoridval.toUpperCase();
                        }
                        REQUESTINDICATOR = "";
                        verifySession();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void verifySession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            if (!REQUESTINDICATOR.equals("UPLOADPROFILEPIC")) {
                CommonFunctions.showDialog(mcontext, "", "Updating Profile. Please wait ...", false);
            }
            if (REQUESTINDICATOR.equals("UPLOADPROFILEPIC")) {
                //call AsynTask to perform network operation on separate thread
                new HttpAsyncTask1().execute(CommonVariables.SAVEPROFILEPICURL);
            } else {
                //call AsynTask to perform network operation on separate thread
                //new HttpAsyncTask().execute(CommonVariables.UPDATEPROFILEURL);
                updateProfile();
            }
        } else {
            CommonFunctions.hideDialog();
            showNoInternetToast();
        }
    }

    @Override
    public void onConnectionChange(FreenetSdkConnectionEvent freenetSdkConnectionEvent) {
        Logger.debug("getStatus", "STATUS: " + String.valueOf(freenetSdkConnectionEvent.getStatus()));
        Logger.debug("getErrorDescription", "Error Desc: " + String.valueOf(freenetSdkConnectionEvent.getErrorDescription()));
        Logger.debug("getErrorCode", "Error Code: " + String.valueOf(freenetSdkConnectionEvent.getErrorCode()));
        Logger.debug("toString", "toString: " + String.valueOf(freenetSdkConnectionEvent.toString()));

        if (freenetSdkConnectionEvent.getStatus() == 1) {
        } else if (freenetSdkConnectionEvent.getStatus() == 0) {
        } else {
            switch (freenetSdkConnectionEvent.getErrorCode()) {
                case 4022:
                    showToast("Please check network configuration on your device.", GlobalToastEnum.NOTICE);
                    break;
                case 4020:
                    showToast("Unsupported network settings on the device.", GlobalToastEnum.NOTICE);
                    break;
                case 4023:
                    showToast("Android version of the device is not supported", GlobalToastEnum.NOTICE);
                    break;
                case 4001:
                case 4002:
                case 4011:
                    showToast("Internal error occurred. (Params)", GlobalToastEnum.ERROR);
                    break;
                case 4012:
                    showToast("Internal error occurred. (Expired) ", GlobalToastEnum.ERROR);
                    break;
                case 4021:
                    showToast("Internal error occurred. (SDK_Exception) ", GlobalToastEnum.ERROR);
                    break;
            }
        }
    }

    //Process updating of profile
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            try {

                String authcode = CommonFunctions.getSha1Hex(imei + mobileval + sessionid);
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("mobile", mobileval);
                jsonObject.accumulate("email", emailval);
                jsonObject.accumulate("firstname", firstnameval);
                jsonObject.accumulate("middlename", middlenameval);
                jsonObject.accumulate("lastname", lastnameval);
                jsonObject.accumulate("streetaddress", streetaddressval);
                jsonObject.accumulate("city", cityval);
                jsonObject.accumulate("country", countryval);
                jsonObject.accumulate("message", messageval);
                jsonObject.accumulate("guarantorid", guarantoridval);
                jsonObject.accumulate("subguarantorid", subguarantoridval);
                jsonObject.accumulate("isGuarantorRegistered", isguarantorregistered);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", mobileval);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("gender", gender);
                jsonObject.accumulate("guarantorname", guarantorname);
                jsonObject.accumulate("guarantoremail", guarantoremail);
                jsonObject.accumulate("newmemberupdate", newmemberupdate);
                jsonObject.accumulate("profilepic", imageurl);
                jsonObject.accumulate("issubguarantor", issubguarantor);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                CommonFunctions.hideDialog();
            }

            return CommonFunctions.POST(urls[0], json);

        }

        //  onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            CommonFunctions.hideDialog();
            try {
                if (result == null) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("000")) {

                    String accountstatus = "";
                    if (previousactivity.equals("SETTINGS")) {
                        accountstatus = "LOGIN";
                        db.updateRegistrationStatus(db, "PENDING", borroweridval);
                        openConfirmation("MAINACTIVITY");
                    } else {
                        if (from.equals("MAINACTIVITY")) {
                            accountstatus = "LOGIN";
                            showToast("Profile was successfully updated", GlobalToastEnum.INFORMATION);
                        } else if (from.equals("SKIPVERIFICATION")) {
                            accountstatus = "LOGIN";
                            proceedQuickInfo();
                        } else if (from.equals("SPONSORDETAILS")) {
                            accountstatus = "LOGIN";
                            db.updateRegistrationStatus(db, "PENDING", borroweridval);
                            openConfirmation("SPONSORDETAILS");
                        } else {
                            accountstatus = "WELCOME";
                            db.updateRegistrationStatus(db, "PENDING", borroweridval);
                            openConfirmation("WELCOME");
                        }
                    }

                    //this is to check if its a subguarantor (we will insert in local as a guarantor)
                    if (subguarantoridval != null && !subguarantoridval.equals(".") && !subguarantoridval.equals("")) {
                        guarantoridval = subguarantoridval;
                    }

                    Logger.debug("GUARANTORID", "updete" + guarantoridval);

                    //update local info
                    db.updateProfile(db, mobileval, firstnameval, middlenameval, lastnameval, streetaddressval, cityval, emailval, accountstatus, imageurl, gender, guarantoridval);


                } else if (result.equals("001")) {
                    showToast("Invalid Entry", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    showToast("Invalid Session", GlobalToastEnum.NOTICE);
                } else if (result.equals("003")) {
                    showToast("Invalid Authentication", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                }
            } catch (Exception e) {
                //Toast.makeText(getBaseContext(), "Error.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void proceedQuickInfo() {
        try {
            if (isFreeModeEnabled()) {
                deactivateFreeNet();
                FreenetSdk.unregisterConnectionListener(this);
            }
            Intent intent = new Intent(this, WelcomePageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("OTHERS", "");
            intent.putExtra("SUBJECT", "");
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } catch (Exception e) {
        }

    }


    @Override
    public void onBackPressed() {
        if (isFreeModeEnabled()) {
            deactivateFreeNet();
            FreenetSdk.unregisterConnectionListener(this);
        }
        super.onBackPressed();
    }

    //Process updating of profile picture
    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + mobileval + sessionid);
            try {

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", mobileval);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("profilepicurl", imageurl);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                CommonFunctions.hideDialog();
            }

            return CommonFunctions.POST(urls[0], json);

        }

        //  onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {
                imageprogressbar.setVisibility(View.GONE);
                uploadlbl.setVisibility(View.GONE);

                if (result == null) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("000")) {
                    //update ProfilePic Path in localdb
                    db.updateProfilePic(db, imageurl, mobileval);

                } else if (result.equals("001")) {
                    showToast("Invalid Entry", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    showToast("Invalid Session", GlobalToastEnum.NOTICE);
                } else if (result.equals("003")) {
                    showToast("Invalid Authentication", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                }
            } catch (Exception e) {
                showToast("Error.", GlobalToastEnum.ERROR);
            }
        }
    }

    public void openConfirmation(final String activitytogo) {
        try {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Registration Successful");
            alertDialog.setMessage("You have successfully sent your registration request to your sponsor. You will be notified once your sponsor confirmed your request. \n\nThank you!");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (isFreeModeEnabled()) {
                        deactivateFreeNet();
                        FreenetSdk.unregisterConnectionListener(SubscriberProfileActivity.this);
                    }
                    if (activitytogo.equals("MAINACTIVITY")) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    } else if (activitytogo.equals("SPONSORDETAILS")) {
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    } else {
                        proceedQuickInfo();
                    }


                }
            });
            alertDialog.show();
        } catch (Exception e) {
        }


    }


    /*PROFILE PICTURE HERE*/

    @SuppressLint("InlinedApi")
    private void checkPermissions() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1234);
            }

            if (ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mcontext, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
            }
        } catch (Exception e) {
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        try {
            if (requestCode == REQUEST_CODE_UPDATE_PIC) {
                if (resultCode == RESULT_OK) {
                    String imagePath = result.getStringExtra(GOTOConstants.IntentExtras.IMAGE_PATH);
                    showCroppedImage(imagePath);
                } else if (resultCode == RESULT_CANCELED) {
                    //TODO : Handle case
                } else {
                    String errorMsg = result.getStringExtra(SubscriberProfileImageCroppingActivity.ERROR_MSG);
                    showToast("" + errorMsg, GlobalToastEnum.ERROR);
                }
            }
        } catch (Exception e) {
        }

    }

    private void showCroppedImage(String mImagePath) {
        try {
            if (mImagePath != null) {
                Bitmap myBitmap = BitmapFactory.decodeFile(mImagePath);
                picUri = getUri(myBitmap);
                mImageView.setImageBitmap(myBitmap);

                if (awskey.equals("") && secretkey.equals("")) {
                    new getCred().execute(CommonVariables.GETAMZCRED);
                } else {
                    uploadAWS();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showAddProfilePicDialog() {
        PicModeSelectDialogFragment dialogFragment = new PicModeSelectDialogFragment();
        dialogFragment.setiPicModeSelectListener(this);
        dialogFragment.show(getFragmentManager(), "picModeSelector");
    }

    private void actionProfilePic(String action) {

        Intent intent = new Intent(this, SubscriberProfileImageCroppingActivity.class);
        intent.putExtra("ACTION", action);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_PIC);
    }

    @Override
    public void onPicModeSelected(String mode) {
        try {
            String action = mode.equalsIgnoreCase(GOTOConstants.PicModes.CAMERA) ? GOTOConstants.IntentExtras.ACTION_CAMERA : GOTOConstants.IntentExtras.ACTION_GALLERY;
            actionProfilePic(action);
        } catch (Exception e) {
        }

    }


    private class getCred extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageprogressbar.setVisibility(View.VISIBLE);
            // btnSelect.setVisibility(View.INVISIBLE);
            uploadlbl.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";

            try {
                String authcode = CommonFunctions.getSha1Hex(imei + CommonVariables.version);
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("version", CommonVariables.version);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 3. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject parentObj = new JSONObject(result);
                String data = parentObj.getString("data");
                String message = parentObj.getString("message");
                String status = parentObj.getString("status");

                if (status.equals("000")) {
                    JSONArray jsonArr = new JSONArray(data);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject obj = jsonArr.getJSONObject(i);
                        awskey = obj.getString("APIKey");
                        secretkey = obj.getString("APISecretKey");

                        uploadAWS();
                    }
                } else {
                    imageprogressbar.setVisibility(View.GONE);
                    uploadlbl.setVisibility(View.GONE);
                    showToast("" + message, GlobalToastEnum.ERROR);
                }

            } catch (Exception e) {
                imageprogressbar.setVisibility(View.GONE);
                uploadlbl.setVisibility(View.GONE);
            }
        }
    }


    //upload to aws
    private void uploadAWS() {
        AsyncTask<String, String, String> _Task = new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(String... arg0) {
                try {
                    java.util.Date expiration = new java.util.Date();
                    long msec = expiration.getTime();
                    msec += 1000 * 60 * 60; // 1 hour.
                    expiration.setTime(msec);
                    publishProgress(arg0);

                    String keyName = borroweridval;
                    String filePath = getRealPathFromURI(picUri);

                    AmazonS3Client s3Client1 = new AmazonS3Client(new BasicAWSCredentials(awskey, secretkey));
                    PutObjectRequest por = new PutObjectRequest(CommonVariables.BUCKETNAME,
                            keyName + ".jpg", new File(filePath));

                    //making the object Public
                    por.setCannedAcl(CannedAccessControlList.PublicRead);
                    s3Client1.putObject(por);

                    imageurl = "https://s3-us-west-1.amazonaws.com/" + CommonVariables.BUCKETNAME + "/" + keyName + ".jpg";
                    log(imageurl);
                } catch (Exception e) {
                    Logger.debug("S3AWS", "error" + e);
                }


                return null;

            }

            @Override
            protected void onProgressUpdate(String... values) {
                // TODO Auto-generated method stub
                super.onProgressUpdate(values);
                System.out.println("Progress : " + values);
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    REQUESTINDICATOR = "UPLOADPROFILEPIC";
                    verifySession();
                } catch (Exception e) {
                    imageprogressbar.setVisibility(View.GONE);
                    uploadlbl.setVisibility(View.GONE);
                }
            }
        };
        _Task.execute((String[]) null);
    }

    //i used this get the path for the AWS
    private String getRealPathFromURI(Uri contentURI) {
        try {
            String result;
            Cursor cursor = this.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
            return result;
        } catch (Exception e) {
        }

        return null;
    }

    //will be using this if not save
    private Uri getUri(Bitmap inImage) {

        try {
            File tempDir = Environment.getExternalStorageDirectory();
            tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
            tempDir.mkdir();
            File tempFile = File.createTempFile(borroweridval, ".jpg", tempDir);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] bitmapData = bytes.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(bitmapData);
            fos.flush();
            fos.close();

            return Uri.fromFile(tempFile);

        } catch (Exception e) {
        }
        return null;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            // Log exception
            return null;
        }
    }

    private class ProfileLogo implements Target {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            mImageView.setImageBitmap(bitmap);
        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }
        @Override
        public void onPrepareLoad(Drawable drawable) {
        }
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     **/

    private void updateProfile() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", mobileval);
                parameters.put("borrowerid", borroweridval);
                parameters.put("mobile", mobileval);
                parameters.put("email", emailval);
                parameters.put("firstname", firstnameval);
                parameters.put("middlename", middlenameval);
                parameters.put("lastname", lastnameval);
                parameters.put("streetaddress", streetaddressval);
                parameters.put("city", cityval);
                parameters.put("country", countryval);
                if(messageval.isEmpty()){
                    parameters.put("message", ".");
                }else{
                    parameters.put("message", messageval);
                }
                parameters.put("guarantorid", guarantoridval);
                parameters.put("subguarantorid", subguarantoridval);
                parameters.put("isGuarantorRegistered", String.valueOf(isguarantorregistered));
                parameters.put("gender", gender);
                parameters.put("guarantorname", guarantorname = ((guarantorname == null) || guarantorname.isEmpty() ? "." : guarantorname));
                parameters.put("guarantoremail", guarantoremail = ((guarantoremail == null) || guarantoremail.isEmpty() ? "." : guarantoremail));
                parameters.put("newmemberupdate", newmemberupdate);

                if (imageurl.equals(null) || imageurl.equals("null")) {
                    parameters.put("profilepic", ".");
                } else {
                    parameters.put("profilepic", imageurl);
                }
                parameters.put("issubguarantor", issubguarantor);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(jsonString, "index");
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                parameters.put("index", index);

                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                //AUTHENTICATIONID
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "updateProfile");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                updateProfileObject(updateProfileObjectCallback);

            } else {
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }

        } catch (Exception e) {
            CommonFunctions.hideDialog();
            e.printStackTrace();
        }
    }

    private void updateProfileObject(Callback<GenericResponse> updateProfileCallback) {
        Call<GenericResponse> call = RetrofitBuilder.getSubscriberV2APIService(getViewContext())
                .updateProfile(
                        authenticationid, sessionid, param
                );

        call.enqueue(updateProfileCallback);
    }

    private Callback<GenericResponse> updateProfileObjectCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            CommonFunctions.hideDialog();
            try {
                ResponseBody responseBody = response.errorBody();
                if (responseBody == null) {
                    String decryptMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                        if (decryptMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            String accountstatus = "";
                            if (previousactivity.equals("SETTINGS")) {
                                accountstatus = "LOGIN";
                                db.updateRegistrationStatus(db, "PENDING", borroweridval);
                                openConfirmation("MAINACTIVITY");
                            } else {
                                if (from.equals("MAINACTIVITY")) {
                                    accountstatus = "LOGIN";
                                    showToast("Profile was successfully updated", GlobalToastEnum.INFORMATION);
                                } else if (from.equals("SKIPVERIFICATION")) {
                                    accountstatus = "LOGIN";
                                    proceedQuickInfo();
                                } else if (from.equals("SPONSORDETAILS")) {
                                    accountstatus = "LOGIN";
                                    db.updateRegistrationStatus(db, "PENDING", borroweridval);
                                    openConfirmation("SPONSORDETAILS");
                                } else {
                                    accountstatus = "WELCOME";
                                    db.updateRegistrationStatus(db, "PENDING", borroweridval);
                                    openConfirmation("WELCOME");
                                }
                            }

                            //this is to check if its a subguarantor (we will insert in local as a guarantor)
                            if (subguarantoridval != null && !subguarantoridval.equals(".") && !subguarantoridval.equals("")) {
                                guarantoridval = subguarantoridval;
                            }

                            Logger.debug("okhttp", "update:" + guarantoridval);

                            //update local info
                            db.updateProfile(db, mobileval, firstnameval, middlenameval, lastnameval, streetaddressval, cityval, emailval, accountstatus, imageurl, gender, guarantoridval);
                        }
                    } else {
                        if (response.body().getStatus().equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else if (response.body().getStatus().equals("003")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        } else {
                            showErrorToast(decryptMessage);
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable throwable) {
            CommonFunctions.hideDialog();
            throwable.printStackTrace();
        }
    };
}
