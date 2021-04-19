package com.goodkredit.myapplication.activities.coopassistant.nonmember;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.net.Uri;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;

import com.amazonaws.util.IOUtils;
import com.goodkredit.myapplication.base.BaseActivity;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantMembershipType;
import com.goodkredit.myapplication.model.coopassistant.CoopRequestInfo;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantApplyMemberActivity extends BaseActivity implements View.OnClickListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //GK SERVICE
    private GKService gkService;
    private String servicecode = "";
    private String merchantid = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    private NestedScrollView home_body;
    private LinearLayout personalinfo_container;
    private List<CoopRequestInfo> requestInfoList = new ArrayList<>();

    private LinearLayout personalinfo_details_container;

    private TextView txv_paymentinfo;
    private LinearLayout layout_membershiptype_container;
    private TextView txv_membershiptype;
    private LinearLayout layout_amounttopay_container;
    private TextView txv_amounttopay;
    private LinearLayout layout_paymentoption_container;
    private TextView txv_paymentoption;

    private TextView txv_basicinfo;
    private LinearLayout layout_surname_container;
    private TextView txv_surname;
    private LinearLayout layout_givenname_container;
    private TextView txv_givenname;
    private LinearLayout layout_middlename_container;
    private TextView txv_middlename;
    private LinearLayout layout_mobileno_container;
    private TextView txv_mobileno;
    private TextView txv_emailaddress;
    private TextView txv_gender;
    private TextView txv_birthdate;
    private TextView txv_civilstatus;
    private TextView txv_national;
    private TextView txv_hometown;
    private TextView txv_currentadd;
    private TextView txv_occupation;

    private LinearLayout layout_otherdetails;
    private TextView txv_otherinfo;

    //APPLICATION FOR MEMBERSHIP
    private ArrayList<CoopAssistantMembershipType> coopAssistantMembershipList;
    private String str_lastname = "";
    private String str_firstname = "";
    private String str_middlename = "";
    private String str_mobilenumber = "";
    private String str_emailaddress = "";
    private String str_gender = "";
    private String str_birthdate = "";
    private String str_civilstatus = "";
    private String str_nationality = "";
    private String str_hometown = "";
    private String str_currentaddresss = "";
    private String str_occupation = "";
    private String str_referredbyname = "";
    private String str_membershiptype = "";
    private String str_otherdetails = "";
    private String str_kycWithoutUploading = "";
    private String str_kycUploading = "";
    private String str_kycOtherInfoToBeSubmited = "";

    //PAYMENT TYPE
    private String str_PaymentType = "";
    //PAYMENT OPTION
    private String str_PaymentOption = "";
    //PAYMENT AMOUNT
    private String str_amounttopay = "";

    //UPLOAD
    private File imageFile;
    public Uri fileUri = null;
    private String imageidfilename = "";
    private List<MultipartBody.Part> bodyParts = new ArrayList<>();

    private LinearLayout btn_action_container;
    private TextView btn_action;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_coopassistant_applymember);

            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        personalinfo_details_container = findViewById(R.id.personalinfo_details_container);

        personalinfo_container = findViewById(R.id.personalinfo_container);

        txv_paymentinfo = findViewById(R.id.txv_paymentinfo);
        layout_membershiptype_container = findViewById(R.id.layout_membershiptype_container);
        txv_membershiptype = findViewById(R.id.txv_membershiptype);
        layout_amounttopay_container = findViewById(R.id.layout_amounttopay_container);
        txv_amounttopay = findViewById(R.id.txv_amounttopay);
        layout_paymentoption_container = findViewById(R.id.layout_paymentoption_container);
        txv_paymentoption = findViewById(R.id.txv_paymentoption);

        txv_basicinfo = findViewById(R.id.txv_basicinfo);
        layout_surname_container = findViewById(R.id.layout_surname_container);
        txv_surname = findViewById(R.id.txv_surname);
        layout_givenname_container = findViewById(R.id.layout_givenname_container);
        txv_givenname = findViewById(R.id.txv_givenname);
        layout_middlename_container = findViewById(R.id.layout_middlename_container);
        txv_middlename = findViewById(R.id.txv_middlename);
        layout_mobileno_container = findViewById(R.id.layout_mobileno_container);
        txv_mobileno = findViewById(R.id.txv_mobileno);
        txv_emailaddress = findViewById(R.id.txv_emailaddress);
        txv_gender = findViewById(R.id.txv_gender);
        txv_birthdate = findViewById(R.id.txv_birthdate);
        txv_civilstatus = findViewById(R.id.txv_civilstatus);
        txv_national = findViewById(R.id.txv_national);
        txv_hometown = findViewById(R.id.txv_hometown);
        txv_currentadd = findViewById(R.id.txv_currentadd);
        txv_occupation = findViewById(R.id.txv_occupation);

        layout_otherdetails = findViewById(R.id.layout_otherdetails);
        txv_otherinfo = findViewById(R.id.txv_otherinfo);

        btn_action_container = findViewById(R.id.btn_action_container);
        btn_action_container.setVisibility(View.VISIBLE);
        btn_action = findViewById(R.id.btn_action);
        btn_action.setText("Proceed");
        btn_action.setOnClickListener(this);
    }

    private void initData() {
        //set toolbar
        setupToolbarWithTitle("Membership Request");

        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

        gkService = PreferenceUtils.getGKServicesPreference(getViewContext(), "GKSERVICE_OBJECT");

        merchantid = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");

        mdb = new DatabaseHandler(getViewContext());

        getApplicationDetails();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getApplicationDetails() {
        Bundle args = new Bundle();
        args = getIntent().getBundleExtra("args");

        txv_paymentinfo.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_BOLD, "Please check before proceeding"));
        txv_basicinfo.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_BOLD, "Basic Information"));

        str_lastname = args.getString("LastName");
        str_firstname = args.getString("FirstName");
        str_middlename = args.getString("MiddleName");
        str_mobilenumber = args.getString("MobileNumber");
        str_emailaddress = args.getString("EmailAddress");
        str_gender = args.getString("Gender");
        str_birthdate = args.getString("Birthdate");
        str_civilstatus = args.getString("CivilStatus");
        str_nationality = args.getString("Nationality");
        str_hometown = args.getString("HomeTown");
        str_currentaddresss = args.getString("CurrentAddress");
        str_occupation = args.getString("Occupation");
        str_referredbyname = args.getString("ReferredbyName");
        coopAssistantMembershipList = args.getParcelableArrayList("MemberShipSelected");
        str_kycWithoutUploading = args.getString("kycWithoutUploading");
        str_kycUploading = args.getString("kycUploading");
//        str_PaymentType = args.getString("PaymentType");
//        str_PaymentOption = args.getString("PaymentOption");

        setUpOtherDetails();

        txv_membershiptype.setText(CommonFunctions.replaceEscapeData(str_membershiptype));
        txv_amounttopay.setText(CommonFunctions.currencyFormatter(str_amounttopay));
        //txv_paymentoption.setText(CommonFunctions.replaceEscapeData(str_PaymentType));

        txv_surname.setText(CommonFunctions.replaceEscapeData(str_lastname));
        txv_givenname.setText(CommonFunctions.replaceEscapeData(str_firstname));
        if (str_middlename != null) {
            if (str_middlename.equals("") || str_middlename.trim().equals(".")) {
                layout_middlename_container.setVisibility(View.GONE);
            } else {
                txv_middlename.setText(CommonFunctions.replaceEscapeData(str_middlename));
            }
        }
        if(str_referredbyname.equals("")) {
            str_referredbyname = ".";
        }
        txv_mobileno.setText("+".concat(str_mobilenumber));
        txv_emailaddress.setText(CommonFunctions.replaceEscapeData(str_emailaddress));
        txv_gender.setText(CommonFunctions.replaceEscapeData(str_gender));
        txv_birthdate.setText(str_birthdate);
        txv_civilstatus.setText(CommonFunctions.replaceEscapeData(str_civilstatus));
        txv_national.setText(CommonFunctions.replaceEscapeData(str_nationality));
        txv_hometown.setText(CommonFunctions.replaceEscapeData(str_hometown));
        txv_currentadd.setText(CommonFunctions.replaceEscapeData(str_currentaddresss));
        txv_occupation.setText(CommonFunctions.replaceEscapeData(str_occupation));

        txv_otherinfo.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_BOLD, "Other Information"));
    }

    private void setUpOtherDetails() {
        getMemberShipType();

        str_otherdetails = setUpUploadingInfo(str_kycWithoutUploading, str_kycUploading);

        addXMLDetails();
    }

    //GET MEMBERSHIP TYPE
    private void getMemberShipType() {
        for (CoopAssistantMembershipType coopAssistantMembershipType : coopAssistantMembershipList) {
            str_membershiptype = coopAssistantMembershipType.getName();
            str_amounttopay = String.valueOf(coopAssistantMembershipType.getAmount());

        }
    }

    private String setUpUploadingInfo(String str_kycWithoutUploading, String str_kycUploading) {
        String str_upload = "";

        try {
            if (str_kycUploading != null) {
                if (!str_kycUploading.trim().equals("") &&
                        !str_kycUploading.trim().equals("[]")) {

                    JSONArray jsonKycInfo = new JSONArray();

                    JSONArray jsonKycWithoutUpload = new JSONArray(str_kycWithoutUploading);

                    if (jsonKycWithoutUpload.length() > 0) {
                        for (int i = 0; i < jsonKycWithoutUpload.length(); i++) {
                            JSONObject jsonObject = jsonKycWithoutUpload.getJSONObject(i);
                            jsonKycInfo.put(jsonObject);
                        }
                    }

                    JSONArray jsonKycUpload = new JSONArray(str_kycUploading);

                    if (jsonKycUpload.length() > 0) {
                        for (int i = 0; i < jsonKycUpload.length(); i++) {
                            JSONObject jsonObject = jsonKycUpload.getJSONObject(i);
                            jsonKycInfo.put(jsonObject);
                        }
                    }

                    if (jsonKycInfo.length() > 0) {
                        str_upload = jsonKycInfo.toString();
                    }

                } else {
                    str_upload = str_kycWithoutUploading;
                }
            } else {
                str_upload = str_kycWithoutUploading;
            }

            if (str_upload.equals("[]")) {
                str_upload = ".";
            }

            str_upload = convertKYCInfoJSONTOXML(str_upload);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return str_upload;
    }

    //CONVERT THE KYC INFO
    private String convertKYCInfoJSONTOXML(String kycinfo) {
        try {

            if (kycinfo != null) {
                if (!kycinfo.trim().equals("") &&
                        !kycinfo.trim().equals("NONE") &&
                        !kycinfo.trim().equals(".")) {

                    JSONArray otherDetailsJSONArray = new JSONArray(kycinfo);
                    if (otherDetailsJSONArray.length() > 0) {
                        String countTag = "";
                        StringBuilder numberCountTag = new StringBuilder();

                        for (int i = 0; i < otherDetailsJSONArray.length(); i++) {

                            JSONObject jsonObject = otherDetailsJSONArray.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            String value = jsonObject.getString("value");
                            int count = i + 1;

                            numberCountTag.append("<").append(count).append(">");

                            numberCountTag.append(name).append(":::").append(value);

                            numberCountTag.append("</").append(count).append(">").append("\n");

                            countTag = "<count>" + count + "</count>" + "\n";
                        }

                        kycinfo = countTag + numberCountTag.toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kycinfo;
    }

    private void addXMLDetails() {
        try {
            if (str_otherdetails != null) {
                if (!str_otherdetails.trim().equals("") &&
                        !str_otherdetails.trim().equals("NONE") &&
                        !str_otherdetails.trim().equals(".")) {

                    layout_otherdetails.setVisibility(View.VISIBLE);
                    //String kycinfo = CommonFunctions.parseXML(str_otherdetails, "kycotherinfo");
                    String count = CommonFunctions.parseXML(str_otherdetails, "count");

                    if (!count.equals("") && !count.equals(".") && !count.equals("NONE")) {

                        for (int i = 1; i <= Integer.parseInt(count); i++) {
                            String field = CommonFunctions.parseXML(str_otherdetails, String.valueOf(i));
                            String[] fieldarr = field.split(":::");

                            try {
                                if (!field.equals("NONE") && !field.equals("")) {
                                    if (fieldarr.length > 0) {
                                        String keyname = fieldarr[0];
                                        String keyvalue = fieldarr[1];

                                        JSONObject obj = new JSONObject();
                                        obj.put("keyname", keyname);
                                        obj.put("keyvalue", keyvalue);

                                        if (keyvalue.contains("http")) {
                                            createPhotoLabel(keyname, keyvalue);
                                        } else if (keyvalue.contains("file") || keyvalue.contains("content")) {
                                            Uri photoUri = Uri.parse(keyvalue);
                                            createPhotoLabel(keyname, photoUri.toString());
                                        } else {
                                            createDoubleTextView(keyname, keyvalue);
                                        }
                                    }

                                }
                            } catch (Error e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    txv_otherinfo.setVisibility(View.GONE);
                    layout_otherdetails.setVisibility(View.GONE);
                }
            } else {
                txv_otherinfo.setVisibility(View.GONE);
                layout_otherdetails.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createDoubleTextView(String keyname, String keyvalue) {

        try {
            if (!keyname.trim().equals("") && !keyvalue.trim().equals("")) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1.0f
                        );

                layoutParams.setMargins(0, 20, 0, 20
                );

                LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1.0f
                        );


                LinearLayout containerLayout = new LinearLayout(getViewContext());
                containerLayout.setOrientation(LinearLayout.VERTICAL);

                TextView txv_name = new TextView(getViewContext());
                txv_name.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                txv_name.setTextColor(ContextCompat.getColor(getViewContext(), R.color.coopassist_green));
                txv_name.setTextSize(14);
                txv_name.setText(keyname);
                txv_name.setLayoutParams(containerParams);
                containerLayout.addView(txv_name, containerParams);

                TextView txv_value = new TextView(getViewContext());
                txv_value.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                txv_value.setTextColor(ContextCompat.getColor(getViewContext(), R.color.coopassist_gray1));
                txv_value.setTextSize(14);
                txv_value.setText(keyvalue);
                txv_value.setLayoutParams(containerParams);
                containerLayout.addView(txv_value, containerParams);

                layout_otherdetails.addView(containerLayout, layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPhotoLabel(String keyname, String keyvalue) {

        try {
            if (!keyname.trim().equals("") && !keyvalue.trim().equals("")) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1.0f
                        );

                layoutParams.setMargins(0, 20, 0, 20
                );

                LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1.0f
                        );
                containerParams.setMargins(0, 0, 0, 10);


                LinearLayout containerLayout = new LinearLayout(getViewContext());
                containerLayout.setOrientation(LinearLayout.VERTICAL);

                //TEXTVIEW
                TextView txv_name = new TextView(getViewContext());
                txv_name.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                txv_name.setTextColor(ContextCompat.getColor(getViewContext(), R.color.coopassist_green));
                txv_name.setTextSize(14);
                txv_name.setText(keyname);
                txv_name.setLayoutParams(containerParams);
                containerLayout.addView(txv_name, containerParams);

                //IMAGEVIEW
                LinearLayout imageLayoutContainer = new LinearLayout(getViewContext());
                imageLayoutContainer.setBackgroundResource(R.drawable.border);
                imageLayoutContainer.setOrientation(LinearLayout.VERTICAL);
                imageLayoutContainer.setLayoutParams(containerParams);

                ImageView imv_value = new ImageView(getViewContext());
                imv_value.setPadding(10, 10, 10, 10);
                imv_value.setLayoutParams(containerParams);

                Glide.with(getViewContext())
                        .load(keyvalue)
                        .apply(new RequestOptions()
                                .fitCenter()
                                .placeholder(R.drawable.generic_placeholder_gk_background)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        )
                        .into(imv_value);

                imageLayoutContainer.addView(imv_value, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        CommonFunctions.converttoDP(getViewContext(), 150)));

                containerLayout.addView(imageLayoutContainer, containerParams);

                layout_otherdetails.addView(containerLayout, layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyForMemberDialog() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "You're applying as a member for this coop. Would you like to proceed?",
                "Close", "Proceed", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        dialog.hideCloseImageButton();

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
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                dialog.dismiss();
                validateInfoToBeSubmitted();

            }
        });
    }

    private void validateInfoToBeSubmitted() {
        if (str_kycUploading != null) {
            if (!str_kycUploading.trim().equals("") &&
                    !str_kycUploading.trim().equals("[]")) {
                setuploadPhotostoAWS(str_kycUploading);
            } else {
                str_kycOtherInfoToBeSubmited = str_kycWithoutUploading;
                if (str_kycOtherInfoToBeSubmited.equals("[]")) {
                    str_kycOtherInfoToBeSubmited = ".";
                } else {
                    str_kycOtherInfoToBeSubmited = convertKYCInfoJSONTOXML(str_kycOtherInfoToBeSubmited);
                }

                callMainAPI();
            }
        } else {
            str_kycOtherInfoToBeSubmited = str_kycWithoutUploading;
            if (str_kycOtherInfoToBeSubmited.equals("[]")) {
                str_kycOtherInfoToBeSubmited = ".";
            } else {
                str_kycOtherInfoToBeSubmited = convertKYCInfoJSONTOXML(str_kycOtherInfoToBeSubmited);
            }
            callMainAPI();
        }
    }

    //API
    private void callMainAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Processing registration request", "Please wait...");
            //applyCoopAsMember();
            applyCoopasmemberV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void applyCoopAsMember() {
        Call<GenericResponse> applyCoopAsMember = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .applyCoopAsMember(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        servicecode,
                        str_membershiptype,
                        ".",
                        merchantid,
                        str_firstname,
                        str_middlename,
                        str_lastname,
                        str_mobilenumber,
                        str_emailaddress,
                        str_gender,
                        str_birthdate,
                        str_civilstatus,
                        str_nationality,
                        str_occupation,
                        str_currentaddresss,
                        str_hometown,
                        ".",
                        str_referredbyname,
                        str_kycOtherInfoToBeSubmited,
                        "."
                );

        applyCoopAsMember.enqueue(applyCoopAsMemberCallBack);
    }

    private final Callback<GenericResponse> applyCoopAsMemberCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        returntoHomeSuccessDialog(response.body().getMessage());
                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                hideProgressDialog();
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            hideProgressDialog();
        }
    };

    private void returntoHomeSuccessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.SUCCESS);

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SClick.check(SClick.BUTTON_CLICK)) return;
                dialog.dismiss();
                returntoHomeFragment();
            }
        });
    }

    //-----------------------------UPLOAD-----------------------------------
    private void setuploadPhotostoAWS(String str_upload) {
        try {
            JSONArray uploadDetailsJSONArray = new JSONArray(str_upload);

            if (bodyParts != null) {
                if (bodyParts.size() > 0) {
                    bodyParts.clear();
                }
            }

            if (uploadDetailsJSONArray.length() > 0) {
                for (int i = 0; i < uploadDetailsJSONArray.length(); i++) {
                    JSONObject jsonObj = uploadDetailsJSONArray.getJSONObject(i);
                    String name = jsonObj.getString("name");
                    String value = jsonObj.getString("value");

                    if (value.contains("file") || value.contains("content")) {
                        Uri photoUri = Uri.parse(value);
                        bodyParts.add(prepareFilePart("upload_album", CommonFunctions.removeSpecialCharacters(name), photoUri));
                    }
                }

                callUploadAPI();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, String uriName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri

        //File file = new File(fileUri.getPath());

        //coopid-cooplogo-timestamp.file extention

        String filename = userid + "-" + uriName + "-" + Calendar.getInstance().getTimeInMillis() + "-coop-imageid.jpg";

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "/" + filename);

        createFile(getViewContext(), fileUri, file);

        file = CommonFunctions.compressImage(getViewContext(),file);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private void callUploadAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Uploading image", "Please wait...");
            uploadAlbum();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void uploadAlbum() {
        RequestBody bodyBorrowerID = RequestBody.create(MediaType.parse("text/plain"), borrowerid);
        RequestBody bodyIMEI = RequestBody.create(MediaType.parse("text/plain"), imei);
        RequestBody bodyBucketName = RequestBody.create(MediaType.parse("text/plain"), CommonVariables.BUCKETNAME);
        RequestBody bodyUserID = RequestBody.create(MediaType.parse("text/plain"), userid);
        RequestBody bodySession = RequestBody.create(MediaType.parse("text/plain"), sessionid);
        RequestBody bodyAuthCode = RequestBody.create(MediaType.parse("text/plain"), CommonFunctions.getSha1Hex(imei + userid + sessionid));
        RequestBody bodyCommand = RequestBody.create(MediaType.parse("text/plain"), "UPLOAD COOP IMAGE");

        Call<GenericResponse> call = RetrofitBuild.getSubscriberAPIService(getViewContext())
                .uploadAlbum(
                        bodyCommand,
                        bodyParts,
                        bodyBorrowerID,
                        bodyIMEI,
                        bodyBucketName,
                        bodyUserID,
                        bodySession,
                        bodyAuthCode
                );
        call.enqueue(uploadAlbumCallBack);

    }

    private Callback<GenericResponse> uploadAlbumCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        String imageurl = response.body().getData();

                        replaceFileToUploadedAWS(imageurl);
                    } else if(response.body().getStatus().equals("004")) {
                        showReUploadDialogNew(str_kycUploading);
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }

                } else {
                    if (errBody.string().contains("!DOCTYPE html")) {
                        showReUploadDialogNew(str_kycUploading);
                    } else {
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
            hideProgressDialog();
        }
    };

    private void replaceFileToUploadedAWS(String imageurl) {
        try {
            JSONArray uploadArray = new JSONArray(imageurl);

            JSONArray kycArray = new JSONArray(str_kycUploading);

            JSONArray finalArray = new JSONArray();

            if (uploadArray.length() > 0) {
                for (int i = 0; i < uploadArray.length(); i++) {
                    JSONObject uploadObject = uploadArray.getJSONObject(i);
                    String uploadname = uploadObject.getString("name");
                    String uploadvalue = uploadObject.getString("value");

                    for(int y = 0; y < kycArray.length(); y++) {
                        JSONObject kycObject = kycArray.getJSONObject(y);
                        String kycname = kycObject.getString("name");
                        String kycvalue = kycObject.getString("value");

                        if(uploadname.contains(CommonFunctions.removeSpecialCharacters(kycname))) {
                            JSONObject finalObject = new JSONObject();
                            finalObject.put("name", kycname);
                            finalObject.put("value", uploadvalue);
                            finalArray.put(finalObject);
                            break;
                        }
                    }
                }

                String str_kycAfterAWSUpload = finalArray.toString();

                str_kycOtherInfoToBeSubmited = setUpUploadingInfo(str_kycWithoutUploading, str_kycAfterAWSUpload);

                callMainAPI();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showReUploadDialogNew(String str_upload) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "Failed in uploading your images. Please try again.",
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
                setuploadPhotostoAWS(str_upload);
            }
        });
    }

    public void returntoHomeFragment() {
        PreferenceUtils.removePreference(getViewContext(), "GKServiceCode");
        PreferenceUtils.removePreference(getViewContext(), "GKMerchantID");
        PreferenceUtils.removePreference(getViewContext(), "GKMerchantStatus");

        PreferenceUtils.saveStringPreference(getViewContext(), "GKServiceCode", gkService.getServiceCode());
        PreferenceUtils.saveStringPreference(getViewContext(), "GKMerchantID", gkService.getMerchantID());
        PreferenceUtils.saveStringPreference(getViewContext(), "GKMerchantStatus", gkService.getGKStoreStatus());

        PreferenceUtils.removePreference(getViewContext(), "GKSERVICE_OBJECT");
        PreferenceUtils.saveGKServicesPreference(getViewContext(), "GKSERVICE_OBJECT", gkService);

        CoopAssistantTermsandConditionsActivity coopTermsActivity = CoopAssistantTermsandConditionsActivity.coopAssistantTermsandConditionsActivity;

        if (coopTermsActivity != null) {
            coopTermsActivity.finish();
        }

        finish();

        proceedtoCoopHome();
    }

    //-----------------------------OTHERS-------------------------------------
    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, CoopAssistantApplyMemberActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action: {
                if (!SClick.check(SClick.BUTTON_CLICK)) return;

                applyForMemberDialog();
                break;
            }
        }
    }


    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    private void applyCoopasmemberV2(){

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("membershiptype", str_membershiptype);
        parameters.put(("firstname") , str_firstname);
        parameters.put("middlename", str_middlename);
        parameters.put("lastname", str_lastname);
        parameters.put("emailaddress", str_emailaddress);
        parameters.put("gender", str_gender);
        parameters.put("dateofbirth", str_birthdate);
        parameters.put("civilstatus", str_civilstatus);
        parameters.put("nationality", str_nationality);
        parameters.put("placeofbirth",str_hometown );
        parameters.put("presentaddress", str_currentaddresss);
        parameters.put("occupation", str_occupation);
        parameters.put("referredbyid",  ".");
        parameters.put("referredbyname", str_referredbyname);
        parameters.put("kycotherinfo", str_kycOtherInfoToBeSubmited);
        parameters.put("paymenttype", ".");
        parameters.put("merchantid", merchantid);
        parameters.put("paymentoption", servicecode);
        parameters.put("devicetype", CommonVariables.devicetype);


        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "applyCoopasmemberV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        applyCoopasmemberV2Object(applyCoopasmemberV2Callback);

    }

    private void applyCoopasmemberV2Object(Callback<GenericResponse> genericResponseCallback){
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .applyCoopasmemberV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }

    private final Callback<GenericResponse> applyCoopasmemberV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        returntoHomeSuccessDialog(message);
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showErrorGlobalDialogs(message);
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                hideProgressDialog();
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            hideProgressDialog();
        }
    };


}
