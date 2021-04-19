package com.goodkredit.myapplication.activities.profile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.GlobalDialogs;

import com.goodkredit.myapplication.utilities.PreferenceUtils;

import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Ban_Lenovo on 8/2/2017.
 */

public class V2EditProfileActivity extends BaseActivity implements View.OnClickListener {

    private V2Subscriber mSubscriber;

    private EditText edtLastName;
    private EditText edtFirstName;
    private EditText edtMiddleName;
    private EditText edtGender;
    private EditText edtEmailAddress;
    private EditText edtStreetAddress;
    private EditText edtCity;

    private String strLastName;
    private String strFirtName;
    private String strMiddleName;
    private String strGender;
    private String strEmailAddress;
    private String strStreetAddress;
    private String strCity;

    private DatabaseHandler db;

    private String guarantoridstatus = "";

    private GlobalDialogs globalDialogs;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String param;
    private String keyEncryption;
    private String authenticationid;
    private String index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
    }

    private void init() {
        setupToolbar();

        if (PreferenceUtils.getBooleanPreference(getViewContext(), PreferenceUtils.KEY_PROFILENOTCOMPLETE)) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        db = new DatabaseHandler(getViewContext());

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        //UUNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        edtLastName = (EditText) findViewById(R.id.edt_last_name);
        edtFirstName = (EditText) findViewById(R.id.edt_first_name);
        edtMiddleName = (EditText) findViewById(R.id.edt_middle_name);
        edtGender = (EditText) findViewById(R.id.edt_gender);
        edtEmailAddress = (EditText) findViewById(R.id.edt_email_address);
        edtStreetAddress = (EditText) findViewById(R.id.edt_street_address);
        edtCity = (EditText) findViewById(R.id.edt_city);

        findViewById(R.id.edit_profile_save).setOnClickListener(this);
        edtGender.setOnClickListener(this);

        mSubscriber = db.getSubscriber(db);

        log(new Gson().toJson(mSubscriber));

        edtLastName.setText(mSubscriber.getLastName());
        edtFirstName.setText(mSubscriber.getFirstName());

        if (mSubscriber.getMiddleName().equals("."))
            edtMiddleName.setText("");
        else
            edtMiddleName.setText(mSubscriber.getMiddleName());

        if (mSubscriber.getGender().equals("."))
            edtGender.setText("");
        else
            edtGender.setText(mSubscriber.getGender());

        if (mSubscriber.getEmailAddress().equals("."))
            edtEmailAddress.setText("");
        else
            edtEmailAddress.setText(mSubscriber.getEmailAddress());

        if (mSubscriber.getStreetAddress().equals("."))
            edtStreetAddress.setText("");
        else
            edtStreetAddress.setText(mSubscriber.getStreetAddress());

        if (mSubscriber.getCity().equals("."))
            edtCity.setText("");
        else
            edtCity.setText(mSubscriber.getCity());


        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                guarantoridstatus = cursor.getString(cursor.getColumnIndex("guarantorregistrationstatus"));
            } while (cursor.moveToNext());
        }
        cursor.close();

        log(guarantoridstatus);

        if (guarantoridstatus.equals("APPROVED")) {
            edtLastName.setEnabled(false);
            edtFirstName.setEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, V2Subscriber subscriber) {
        Intent intent = new Intent(context, V2EditProfileActivity.class);
        intent.putExtra("SUBSCRIBER", subscriber);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_profile_save) {
            validateEntries();
        } else if (v.getId() == R.id.edt_gender) {
            showGenderOptionsDialog();
            //showGenderOptionsDialogNew();
        }
    }

    public void validateEntries() {
        strLastName = edtLastName.getText().toString().trim();
        strFirtName = edtFirstName.getText().toString().trim();
        strMiddleName = edtMiddleName.getText().toString().trim();
        strGender = edtGender.getText().toString().trim();
        strEmailAddress = edtEmailAddress.getText().toString().trim();
        strStreetAddress = edtStreetAddress.getText().toString().trim();
        strCity = edtCity.getText().toString().trim();

        if (strLastName.isEmpty() ||
                strFirtName.isEmpty() ||
                strMiddleName.isEmpty() ||
                strGender.isEmpty() ||
                strEmailAddress.isEmpty() ||
                strStreetAddress.isEmpty() ||
                strCity.isEmpty()) {
            //showError("Please fill all fields.");
            showWarningGlobalDialogs("Please fill all fields.");
        } else {
            if (V2Utils.isValidEmail(strEmailAddress)) {
                getSession();
            } else {
                //showError("Invalid email address. Please check.");
                showWarningGlobalDialogs("Invalid email address. Please check.");
            }
        }
    }

    private void showGenderOptionsDialog() {
        new MaterialDialog.Builder(this)
                .title("Select Gender")
                .items(R.array.arr_gender)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        edtGender.setText(text.toString().toUpperCase());
                        return true;
                    }
                })
                .show();
    }

    private void showGenderOptionsDialogNew() {
        globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("Select Gender", "",
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.RADIO);

        globalDialogs.hideCloseImageButton();

        View closebtn = globalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
            }
        });

        List<String> radiocontent = new ArrayList<>();
        radiocontent.add("Male");
        radiocontent.add("Female");

        LinearLayout radGroupContainer = globalDialogs.setContentRadio(radiocontent, RadioGroup.VERTICAL,
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
            radBtnView.setOnClickListener(genderListener);
        }

        View singlebtn = globalDialogs.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
            }

        });
    }

    //LISTENER FOR PICKUP AND DELIVERY LISTENER (NEW)
    private View.OnClickListener genderListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RadioButton radioButton = (RadioButton) view;
                    String strRadBtn = radioButton.getText().toString();
                    edtGender.setText(strRadBtn.toUpperCase());
                    globalDialogs.dismiss();
                }
            }, 100);

        }
    };

    private void getSession() {
        //updateProfile();
        updateProfileObject();
    }

//    private Callback<String> sessionCallback = new Callback<String>() {
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
//                    session = response.body().toString();
//                    updateProfile();
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

    private void updateProfile() {
        Call<GenericResponse> call = RetrofitBuild.getSubscriberAPIService(getViewContext())
                .updateProfile(
                        imei,
                        userid,
                        borrowerid,
                        V2Utils.getSha1Hex(imei + userid + sessionid),
                        sessionid,
                        strLastName.toUpperCase(),
                        strFirtName.toUpperCase(),
                        strMiddleName.toUpperCase(),
                        strGender.toUpperCase(),
                        strEmailAddress,
                        strStreetAddress.toUpperCase(),
                        strCity.toUpperCase());
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0)
            call.enqueue(updateProfileCallback);
        else {
            //showError(getString(R.string.generic_internet_error_message));
            showErrorGlobalDialogs("Seems you are not connected to the internet. " +
                    "Please check your connection and try again. Thank you.");
            hideProgressDialog();
        }
    }

    Callback<GenericResponse> updateProfileCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            ResponseBody errBody = response.errorBody();
            if (errBody == null) {
                if (response.body().getStatus().equals("000")) {
                    //success
                    db.v2UpdateProfile(db,
                            strLastName.toUpperCase(),
                            strFirtName.toUpperCase(),
                            strMiddleName.toUpperCase(),
                            strGender.toUpperCase(),
                            strEmailAddress,
                            strStreetAddress.toUpperCase(),
                            strCity.toUpperCase(),
                            userid);

                    //showSuccessDialog();
                    showSuccessDialogNew();
                } else {
                    showToast("" + response.body().getMessage(), GlobalToastEnum.NOTICE);
                }
            } else {
                showToast("Internal server error. Please try again.", GlobalToastEnum.ERROR);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            hideProgressDialog();
        }
    };

    private void showSuccessDialog() {
        new MaterialDialog.Builder(getViewContext())
                .title(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_REGULAR, "Updating Successful"))
                .content(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_REGULAR, "Your profile has been updated."))
                .positiveText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_REGULAR, "OK"))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .show();
    }

    private void showSuccessDialogNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("Updating Successful", "Your profile has been updated.",
                "OK", "", ButtonTypeEnum.SINGLE,
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
                PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_PROFILENOTCOMPLETE);
                dialog.dismiss();
                finish();
            }
        });
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     */

    private void updateProfileObject() {
        try {
            sessionid = PreferenceUtils.getSessionID(getViewContext());
            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("lastname", strLastName.toUpperCase());
                parameters.put("firstname", strFirtName.toUpperCase());
                parameters.put("middlename", strMiddleName.toUpperCase());
                parameters.put("gender", strGender.toUpperCase());
                parameters.put("emailaddress", strEmailAddress);
                parameters.put("streetaddress", strStreetAddress.toUpperCase());
                parameters.put("city", strCity.toUpperCase());

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");
                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(String.valueOf(jsonString), "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "updateSpecificProfile");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));
                updateProfileObjectV2(updateProfileObjectV2Session);

            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Something went wrong. Please try again. ");
        }

    }

    private void updateProfileObjectV2(Callback<GenericResponse> updateProfileObjectV2Callback) {
        Call<GenericResponse> updateProfileObject = RetrofitBuilder.getSubscriberV2APIService(getViewContext())
                .updateSpecificProfile(authenticationid,
                        sessionid,
                        param);

        updateProfileObject.enqueue(updateProfileObjectV2Callback);
    }

    Callback<GenericResponse> updateProfileObjectV2Session = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            if (errBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                if (response.body().getStatus().equals("000")) {

                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    if (decryptedData.equals("error") || decryptedMessage.equals("error")) {
                        showErrorToast();
                    } else {
                        //success
                        db.v2UpdateProfile(db,
                                strLastName.toUpperCase(),
                                strFirtName.toUpperCase(),
                                strMiddleName.toUpperCase(),
                                strGender.toUpperCase(),
                                strEmailAddress,
                                strStreetAddress.toUpperCase(),
                                strCity.toUpperCase(),
                                userid);

                        showSuccessDialogNew();
                    }

                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorGlobalDialogs();
                    } else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showToast(decryptedMessage, GlobalToastEnum.NOTICE);
                    }
                }
            } else {
                showToast("Internal server error. Please try again.", GlobalToastEnum.ERROR);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };
}
