package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.coopassistant.member.CoopAssisantMemberProfileAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantMemberProfileFragment extends BaseFragment implements View.OnClickListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";
    private String servicecode = "";

    private DatabaseHandler db;

    private String strLastName;
    private String strFirtName;
    private String strMiddleName;
    private String strGender;
    private String strEmailAddress;
    private String strStreetAddress;
    private String strCity;

    private V2Subscriber mSubscriber;

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    private NestedScrollView home_body;

    private LinearLayout personalinfo_container;
    private RecyclerView rv_personalinfo;
    private CoopAssisantMemberProfileAdapter rv_personalinfoadapter;
    private List<CoopAssistantMembers> memberInfoList = new ArrayList<>();

    private LinearLayout personalinfo_details_container;
    private TextView txv_basicinfo;
    private LinearLayout layout_memberid_container;
    private TextView txv_memberid;
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
    private LinearLayout layout_membershiptype_container;
    private TextView txv_membershiptype;

    private LinearLayout layout_otherdetails;
    private TextView txv_otherinfo;

    private String str_memberid = "";
    private String str_surname = "";
    private String str_givenname = "";
    private String str_middlename = "";
    private String str_mobileno = "";
    private String str_emailaddress = "";
    private String str_gender = "";
    private String str_birthdate = "";
    private String str_civilstatus = "";
    private String str_national = "";
    private String str_hometown = "";
    private String str_currentadd = "";
    private String str_occupation = "";
    private String str_membershiptype = "";
    private String str_otherdetails = "";

    private LinearLayout btn_action_container;
    private TextView btn_action;

    private String param;
    private String keyEncryption;
    private String authenticationid;
    private String index;


    private MenuItem menus;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_member_profile, container, false);




        try {
            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private void init(View view) {
        db = new DatabaseHandler(getViewContext());

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        mSubscriber = db.getSubscriber(db);

        //UUNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        personalinfo_details_container = (LinearLayout) view.findViewById(R.id.personalinfo_details_container);

        personalinfo_container = (LinearLayout) view.findViewById(R.id.personalinfo_container);
        rv_personalinfo = (RecyclerView) view.findViewById(R.id.rv_personalinfo);
        txv_basicinfo = (TextView) view.findViewById(R.id.txv_basicinfo);

        layout_memberid_container = (LinearLayout) view.findViewById(R.id.layout_memberid_container);
        txv_memberid = (TextView) view.findViewById(R.id.txv_memberid);
        layout_surname_container = (LinearLayout) view.findViewById(R.id.layout_surname_container);
        txv_surname = (TextView) view.findViewById(R.id.txv_surname);
        layout_givenname_container = (LinearLayout) view.findViewById(R.id.layout_givenname_container);
        txv_givenname = (EditText) view.findViewById(R.id.txv_givenname);
        layout_middlename_container = (LinearLayout) view.findViewById(R.id.layout_middlename_container);
        txv_middlename = (TextView) view.findViewById(R.id.txv_middlename);
        layout_mobileno_container = (LinearLayout) view.findViewById(R.id.layout_mobileno_container);
        txv_mobileno = (TextView) view.findViewById(R.id.txv_mobileno);
        txv_emailaddress = (TextView) view.findViewById(R.id.txv_emailaddress);
        txv_gender = (TextView) view.findViewById(R.id.txv_gender);
        txv_birthdate = (EditText) view.findViewById(R.id.txv_birthdate);
        txv_civilstatus = (TextView) view.findViewById(R.id.txv_civilstatus);
        txv_national = (TextView) view.findViewById(R.id.txv_national);
        txv_hometown = (TextView) view.findViewById(R.id.txv_hometown);
        txv_currentadd = (TextView) view.findViewById(R.id.txv_currentadd);
        txv_occupation = (TextView) view.findViewById(R.id.txv_occupation);
        layout_membershiptype_container = (LinearLayout) view.findViewById(R.id.layout_membershiptype_container);
        txv_membershiptype = (TextView) view.findViewById(R.id.txv_membershiptype);

        layout_otherdetails = (LinearLayout) view.findViewById(R.id.layout_otherdetails);
        txv_otherinfo = (TextView) view.findViewById(R.id.txv_otherinfo);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mdb = new DatabaseHandler(getViewContext());

        rv_personalinfo.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_personalinfo.setNestedScrollingEnabled(false);
        rv_personalinfoadapter = new CoopAssisantMemberProfileAdapter(getViewContext());
        rv_personalinfo.setAdapter(rv_personalinfoadapter);

        getMemberProfile();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_done, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void getMemberProfile() {
        memberInfoList = PreferenceUtils.getCoopMembersListPreference(getViewContext(), CoopAssistantMembers.KEY_COOPMEMBERINFORMATION);

        rv_personalinfoadapter.updateData(memberInfoList);

        showProfile();
    }

    private void showProfile() {

        txv_basicinfo.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, "Basic Information"));

        for (CoopAssistantMembers coopAssistantMembers : memberInfoList) {
            str_memberid = coopAssistantMembers.getMemberID();
            str_surname = coopAssistantMembers.getLastName();
            str_givenname = coopAssistantMembers.getFirstName();
            str_middlename = coopAssistantMembers.getMiddleName();
            str_mobileno = coopAssistantMembers.getMobileNumber();
            str_emailaddress = coopAssistantMembers.getEmailAddress();
            str_gender = coopAssistantMembers.getGender();
            str_birthdate = coopAssistantMembers.getBirthdate();
            str_civilstatus = coopAssistantMembers.getCivilStatus();
            str_national = coopAssistantMembers.getNationality();
            str_hometown = coopAssistantMembers.getHomeTownAddress();
            str_currentadd = coopAssistantMembers.getCurrentAddress();
            str_occupation = coopAssistantMembers.getOccupation();
            str_membershiptype = coopAssistantMembers.getMembershipType();
            str_otherdetails = coopAssistantMembers.getOtherDetails();
        }

        txv_memberid.setText(CommonFunctions.replaceEscapeData(str_memberid));
        txv_surname.setText(CommonFunctions.replaceEscapeData(str_surname.toUpperCase()));
        txv_givenname.setText(CommonFunctions.replaceEscapeData(str_givenname.toUpperCase()));
        if(str_middlename != null) {
            if (str_middlename.equals("") || str_middlename.trim().equals(".")) {
                layout_middlename_container.setVisibility(View.GONE);
            } else {
                txv_middlename.setText(CommonFunctions.replaceEscapeData(str_middlename.toUpperCase()));
            }
        }
        layout_mobileno_container.setVisibility(View.GONE);
        txv_mobileno.setText("+" + str_mobileno);
        txv_emailaddress.setText(CommonFunctions.replaceEscapeData(str_emailaddress));
        txv_gender.setText(CommonFunctions.replaceEscapeData(str_gender));
        txv_birthdate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(str_birthdate)));
        txv_civilstatus.setText(CommonFunctions.replaceEscapeData(str_civilstatus));
        txv_national.setText(CommonFunctions.replaceEscapeData(str_national));
        txv_hometown.setText(CommonFunctions.replaceEscapeData(str_hometown));
        txv_currentadd.setText(CommonFunctions.replaceEscapeData(str_currentadd));
        txv_occupation.setText(CommonFunctions.replaceEscapeData(str_occupation));
        txv_membershiptype.setText(CommonFunctions.replaceEscapeData(str_membershiptype));

        txv_otherinfo.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, "Other Information"));

        addXMLDetails();
    }


    private void addXMLDetails() {
        try {
            if(str_otherdetails != null) {
                if (!str_otherdetails.trim().equals("") && !str_otherdetails.trim().equals("NONE")) {

                    txv_otherinfo.setVisibility(View.VISIBLE);
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
                txv_name.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
                txv_name.setTextColor(ContextCompat.getColor(mContext, R.color.coopassist_green));
                txv_name.setTextSize(14);
                txv_name.setText(keyname);
                txv_name.setLayoutParams(containerParams);
                containerLayout.addView(txv_name, containerParams);

                TextView txv_value = new TextView(getViewContext());
                txv_value.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
                txv_value.setTextColor(ContextCompat.getColor(mContext, R.color.coopassist_gray1));
                txv_value.setTextSize(14);
                if(keyvalue.trim().equals(".")){
                    txv_value.setText("N/A");
                } else{
                    txv_value.setText(keyvalue);
                }

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
                containerParams.setMargins(0,0,0,10);


                LinearLayout containerLayout = new LinearLayout(getViewContext());
                containerLayout.setOrientation(LinearLayout.VERTICAL);

                //TEXTVIEW
                TextView txv_name = new TextView(getViewContext());
                txv_name.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
                txv_name.setTextColor(ContextCompat.getColor(mContext, R.color.coopassist_green));
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
                imv_value.setPadding(10,10,10,10);
                imv_value.setLayoutParams(containerParams);

                Glide.with(mContext)
                        .load(keyvalue)
                        .apply(new RequestOptions()
                                .fitCenter()
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action: {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;

                break;
            }
        }
    }




        private void updateProfile() {
        Call<GenericResponse> call = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .getCoopandMembersInformation(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        servicecode);
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0)
            call.enqueue(getCoopandMembersInformationCallBack);
        else {
            //showError(getString(R.string.generic_internet_error_message));
            showErrorGlobalDialogs("Seems you are not connected to the internet. " +
                    "Please check your connection and try again. Thank you.");
            hideProgressDialog();
        }
    }
    private void getCoopandMembersInformation() {
        Call<GenericResponse> coopinformation = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .getCoopandMembersInformation(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        servicecode
                );

        coopinformation.enqueue(getCoopandMembersInformationCallBack);
    }

    private final Callback<GenericResponse> getCoopandMembersInformationCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();

                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        String strresponse = response.body().getData();
                        //checkCoopServiceStatus(strresponse);
                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else if (response.body().getStatus().equals("201")) {
                        String strresponse = response.body().getData();
                        //checkCoopServiceStatus(strresponse);
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
                hideProgressDialog();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

//    Callback<GenericResponse> updateProfileCallback = new Callback<GenericResponse>() {
//        @Override
//        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
//            hideProgressDialog();
//            ResponseBody errBody = response.errorBody();
//            if (errBody == null) {
//                if (response.body().getStatus().equals("000")) {
//                    //success
//                    db.v2UpdateProfile(db,
//                            str_surname,
//                            str_givenname,
//                            str_middlename,
//                            str_gender,
//                            str_emailaddress,
//                            str_currentadd,
//                            str_currentadd,
//                            userid);
//                    //updateProfile();
//
//                    //showSuccessDialog();
//                    showSuccessDialogNew();
//                } else {
//                    showToast("" + response.body().getMessage(), GlobalToastEnum.NOTICE);
//                }
//            } else {
//                showToast("Internal server error. Please try again.", GlobalToastEnum.ERROR);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            hideProgressDialog();
//        }
//    };
//
//    private void showSuccessDialogNew() {
//        GlobalDialogs dialog = new GlobalDialogs(getViewContext());
//
//        dialog.createDialog("Updating Successful", "Your profile has been updated.",
//                "OK", "", ButtonTypeEnum.SINGLE,
//                false, false, DialogTypeEnum.NOTICE);
//
//        dialog.hideCloseImageButton();
//
//        View closebtn = dialog.btnCloseImage();
//        closebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        View singlebtn = dialog.btnSingle();
//        singlebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_PROFILENOTCOMPLETE);
//                dialog.dismiss();
//                //finish();
//            }
//        });
//    }
//
//    private void updateProfileObject() {
//        try {
//            sessionid = PreferenceUtils.getSessionID(getViewContext());
//            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
//                parameters.put("imei", imei);
//                parameters.put("userid", userid);
//                parameters.put("borrowerid", borrowerid);
//                parameters.put("lastname", str_surname);
//                parameters.put("firstname", str_givenname);
//                parameters.put("middlename", str_middlename);
//                parameters.put("gender", str_gender);
//                parameters.put("emailaddress", str_emailaddress);
//                parameters.put("streetaddress", str_currentadd);
//                parameters.put("city", str_civilstatus);
//                //parameters.put("birthdate", str_birthdate);
//
////                LinkedHashMap indexAuthMapObject;
////                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
////                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
////                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");
////                parameters.put("index", index);
////                String paramJson = new Gson().toJson(parameters, Map.class);
////
////                //ENCRYPTION
////                authenticationid = CommonFunctions.parseJSON(String.valueOf(jsonString), "authenticationid");
////                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "updateSpecificProfile");
////                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));
//                LinkedHashMap indexAuthMapObject;
//                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getActivity(), parameters, sessionid);
//                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
//                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");
//
//                parameters.put("index", index);
//                String paramJson = new Gson().toJson(parameters, Map.class);
//
//                //ENCRYPTION
//                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
//                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getCoopandMembersInformation");
//                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));
//
//                String checker = new Gson().toJson(parameters);
//                updateProfileObjectV2(updateProfileObjectV2Session);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            showError("Something went wrong. Please try again. ");
//        }
//
//    }
//
//    private void updateProfileObjectV2(Callback<GenericResponse> updateProfileObjectV2Callback) {
//        Call<GenericResponse> updateProfileObject = RetrofitBuilder.getSubscriberV2APIService(getViewContext())
//                .updateSpecificProfile(authenticationid,
//                        sessionid,
//                        param);
//
//        updateProfileObject.enqueue(updateProfileObjectV2Callback);
//    }
//
//    Callback<GenericResponse> updateProfileObjectV2Session = new Callback<GenericResponse>() {
//        @Override
//        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
//            ResponseBody errBody = response.errorBody();
//            if (errBody == null) {
//
//                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
//
//                if (response.body().getStatus().equals("000")) {
//
//                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
//
//                    if (decryptedData.equals("error") || decryptedMessage.equals("error")) {
//                        showErrorToast();
//                    } else {
//                        //success
//                        db.v2UpdateProfile(db,
//                                str_surname,
//                                str_givenname,
//                                str_middlename,
//                                str_gender,
//                                str_emailaddress,
//                                str_currentadd,
//                                str_currentadd,
//                                userid);
//
//                        showSuccessDialogNew();
//                    }
//
//                } else {
//                    if (response.body().getStatus().equals("error")) {
//                        showErrorGlobalDialogs();
//                    } else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
//                        showAutoLogoutDialog(getString(R.string.logoutmessage));
//                    } else {
//                        showToast(decryptedMessage, GlobalToastEnum.NOTICE);
//                    }
//                }
//            } else {
//                showToast("Internal server error. Please try again.", GlobalToastEnum.ERROR);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };
    private void getCoopandMembersInformationV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("servicecode",servicecode);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(jsonString, "index");
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            parameters.put("index", index);

            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            //AUTHENTICATIONID
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getCoopandMembersInformationV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getCoopandMembersInformationV2Object(getCoopandMembersInformationV2Callback);

        }else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private void getCoopandMembersInformationV2Object(Callback<GenericResponse> genericResponseCallback){
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .getCoopandMembersInformationV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse> getCoopandMembersInformationV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    String data;
                    switch (response.body().getStatus()) {
                        case "000":
                        case "201":
                            data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                            //checkCoopServiceStatus(data);
                            break;
                        case "003":
                        case "002":
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                            break;
                        default:
                            showErrorGlobalDialogs(message);
                            break;
                    }
                } else {
                    showErrorGlobalDialogs();
                }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        this.menus = item;
        int id = menus.getItemId();
        if(id == R.id.done){
            updateProfile();
        }

        return super.onOptionsItemSelected(item);
    }
}
