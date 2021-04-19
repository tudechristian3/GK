package com.goodkredit.myapplication.fragments.coopassistant.nonmember;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantPaymentOptionsActivity;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniBillingReferenceActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.coopassistant.CoopAssistantHomeFragment;
import com.goodkredit.myapplication.model.coopassistant.CoopRequestInfo;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

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

public class CoopAssistantApplicationDetailsFragment extends BaseFragment implements View.OnClickListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //GK SERVICE
    private String servicecode = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    private NestedScrollView home_body;
    private LinearLayout personalinfo_container;
    private List<CoopRequestInfo> requestInfoList = new ArrayList<>();

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
    private String str_requestxmldetails = "";
    private String str_staticdetails = "";
    private String str_otherdetails = "";

    private String str_coopname = "";
    private String str_merchantname = "";
    private String str_billingid = "";
    private double totalamount = 0.0;

    //REQUEST ID
    private String str_requestid = "";
    //REMARKS
    private String str_remarks = "";
    //PAYMENT TYPE
    private String str_paymenttype = "";

    //CANCEL DIALOG
    private EditText dialogEdt;
    private String dialogString = "";

    private LinearLayout btn_action_container;
    private TextView btn_cancel;
    private TextView btn_action;
    private LinearLayout layout_coop_action;
    private LinearLayout layout_coop_cancel;

    //API ACTION
    private boolean isCancelCoopMembershipRequest = false;
    private boolean isPayMembershipRequest = false;

    //REQUEST STATUS
    private String mRequestStatus = "";

    //NEW VARIABLES FOR SECURITY
    private String authenticationid;
    private String index;
    private String param;
    private String keyEncryption;

    private MenuItem menus;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_application_details, container, false);



        try {
            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        personalinfo_details_container = view.findViewById(R.id.personalinfo_details_container);

        personalinfo_container = view.findViewById(R.id.personalinfo_container);
        txv_basicinfo = view.findViewById(R.id.txv_basicinfo);

        layout_memberid_container = view.findViewById(R.id.layout_memberid_container);
        txv_memberid = view.findViewById(R.id.txv_memberid);
        layout_surname_container = view.findViewById(R.id.layout_surname_container);
        txv_surname = view.findViewById(R.id.txv_surname);
        layout_givenname_container = view.findViewById(R.id.layout_givenname_container);
        txv_givenname = view.findViewById(R.id.txv_givenname);
        layout_middlename_container = view.findViewById(R.id.layout_middlename_container);
        txv_middlename = view.findViewById(R.id.txv_middlename);
        layout_mobileno_container = view.findViewById(R.id.layout_mobileno_container);
        txv_mobileno = view.findViewById(R.id.txv_mobileno);
        txv_emailaddress = view.findViewById(R.id.txv_emailaddress);
        txv_gender = view.findViewById(R.id.txv_gender);
        txv_birthdate = view.findViewById(R.id.txv_birthdate);
        txv_civilstatus = view.findViewById(R.id.txv_civilstatus);
        txv_national = view.findViewById(R.id.txv_national);
        txv_hometown = view.findViewById(R.id.txv_hometown);
        txv_currentadd = view.findViewById(R.id.txv_currentadd);
        txv_occupation = view.findViewById(R.id.txv_occupation);
        layout_membershiptype_container = view.findViewById(R.id.layout_membershiptype_container);
        txv_membershiptype = view.findViewById(R.id.txv_membershiptype);
        layout_otherdetails = view.findViewById(R.id.layout_otherdetails);
        txv_otherinfo = view.findViewById(R.id.txv_otherinfo);

        btn_action_container = view.findViewById(R.id.btn_action_container);
        btn_action = view.findViewById(R.id.btn_action);
        btn_action.setOnClickListener(this);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        layout_coop_action = view.findViewById(R.id.layout_coop_action);
        layout_coop_cancel = view.findViewById(R.id.layout_coop_cancel);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

        mdb = new DatabaseHandler(getViewContext());

        getMemberProfile();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_done, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    private void getMemberProfile() {
        requestInfoList = PreferenceUtils.getCoopRequestInfoListPreference(getViewContext(), CoopRequestInfo.KEY_COOPREQUESTINFO);

        showProfile();
    }

    private void showProfile() {
        txv_basicinfo.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, "Basic Information"));

        for (CoopRequestInfo coopRequestInfo : requestInfoList) {
            str_memberid = coopRequestInfo.getMemberID();
            str_coopname = coopRequestInfo.getCoopName();
            str_requestxmldetails = coopRequestInfo.getRequestXMLDetails();
            str_requestid = coopRequestInfo.getRequestID();
            str_paymenttype = coopRequestInfo.getPaymentType();
            str_merchantname = coopRequestInfo.getMerchantName();
            str_billingid = coopRequestInfo.getBillingID();
            totalamount = coopRequestInfo.getTotalAmount();
            str_membershiptype = coopRequestInfo.getMembershipType();
        }

        str_surname = CommonFunctions.parseXML(str_requestxmldetails, "lastname");
        str_givenname = CommonFunctions.parseXML(str_requestxmldetails, "firstname");
        str_middlename = CommonFunctions.parseXML(str_requestxmldetails, "middlename");
        str_mobileno = CommonFunctions.parseXML(str_requestxmldetails, "mobilenumber");
        str_emailaddress = CommonFunctions.parseXML(str_requestxmldetails, "emailaddress");
        str_gender = CommonFunctions.parseXML(str_requestxmldetails, "gender");
        str_birthdate = CommonFunctions.parseXML(str_requestxmldetails, "dateofbirth");
        str_civilstatus = CommonFunctions.parseXML(str_requestxmldetails, "civilstatus");
        str_national = CommonFunctions.parseXML(str_requestxmldetails, "nationality");
        str_currentadd = CommonFunctions.parseXML(str_requestxmldetails, "presentaddress");
        str_hometown = CommonFunctions.parseXML(str_requestxmldetails, "placeofbirth");
        str_occupation = CommonFunctions.parseXML(str_requestxmldetails, "occupation");
        str_otherdetails = CommonFunctions.parseXML(str_requestxmldetails, "kycotherinfo");

        if(str_memberid != null) {
            if (str_memberid.equals("") || str_memberid.trim().equals(".")) {
                layout_memberid_container.setVisibility(View.GONE);
            } else {
                txv_memberid.setText(CommonFunctions.replaceEscapeData(str_memberid));
            }
        }

        txv_surname.setText(CommonFunctions.replaceEscapeData(str_surname.toUpperCase()));
        txv_givenname.setText(CommonFunctions.replaceEscapeData(str_givenname.toUpperCase()));
        if(str_middlename != null) {
            if (str_middlename.equals("") || str_middlename.trim().equals(".")) {
                layout_middlename_container.setVisibility(View.GONE);
            } else {
                txv_middlename.setText(CommonFunctions.replaceEscapeData(str_middlename.toUpperCase()));
            }
        }

        txv_mobileno.setText("+" + str_mobileno);
        txv_emailaddress.setText(CommonFunctions.replaceEscapeData(str_emailaddress));
        txv_gender.setText(CommonFunctions.replaceEscapeData(str_gender));
        txv_birthdate.setText(str_birthdate);
        txv_civilstatus.setText(CommonFunctions.replaceEscapeData(str_civilstatus));
        txv_national.setText(CommonFunctions.replaceEscapeData(str_national));
        txv_hometown.setText(CommonFunctions.replaceEscapeData(str_hometown));
        txv_currentadd.setText(CommonFunctions.replaceEscapeData(str_currentadd));
        txv_occupation.setText(CommonFunctions.replaceEscapeData(str_occupation));
        txv_membershiptype.setText(CommonFunctions.replaceEscapeData(str_membershiptype));

        mRequestStatus = getArguments().getString(CoopAssistantHomeFragment.KEY_COOP_MEMBER_REQUEST_STATUS);

        if(mRequestStatus.contains("PENDING")){
            layout_coop_action.setVisibility(View.GONE);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
            );

            layout_coop_cancel.setLayoutParams(param);

        } else if(mRequestStatus.contains("FOR PAYMENT")){
            layout_coop_action.setVisibility(View.VISIBLE);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f
            );

            layout_coop_cancel.setLayoutParams(param);
        } 

        if(str_paymenttype.contains("PARTNER")) {
            btn_action.setText("VIEW BILLINGS");
        } else {
            btn_action.setText("PAY NOW");
        }

        txv_otherinfo.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, "Other Information"));
        addXMLDetails();
    }

    private void addXMLDetails() {
        try {
            if(str_otherdetails != null) {
                if (!str_otherdetails.trim().equals("") && !str_otherdetails.trim().equals("NONE") && !str_otherdetails.equals(".")) {

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
                imv_value.setPadding(10, 10, 10, 10);
                imv_value.setLayoutParams(containerParams);

                Glide.with(mContext)
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


    //CANCELLATION
    @SuppressLint("ClickableViewAccessibility")
    private void cancelDialog() {

        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "",
                "Cancel", "OK", ButtonTypeEnum.DOUBLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //TEXTVIEW
        LinearLayout textViewMainContainer = dialog.getTextViewMessageContainer();
        textViewMainContainer.setPadding(15, 15, 15, 0);

        List<String> declineList = new ArrayList<>();
        declineList.add("Reason for Cancellation:");

        LinearLayout textViewContainer = dialog.setContentTextView(declineList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.CENTER));

        //EDITTEXT
        LinearLayout editTextMainContainer = dialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(15, 0, 15, 15);

        List<String> editTextDataType = new ArrayList<>();
        editTextDataType.add(String.valueOf(GlobalDialogsEditText.CUSTOMVARCHAR));

        LinearLayout editTextContainer = dialog.setContentEditText(editTextDataType,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.CENTER,
                        R.drawable.border, 300, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {

                EditText editItem = (EditText) editView;

                editItem.setScroller(new Scroller(getViewContext()));
                editItem.setSingleLine(false);
                editItem.setLines(5);
                editItem.setMinLines(3);
                editItem.setMaxLines(5);
                editItem.setVerticalScrollBarEnabled(true);
                editItem.setMovementMethod(new ScrollingMovementMethod());

                editItem.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                        }

                        return false;
                    }
                });

                editItem.setBackgroundResource(R.drawable.border);
                String taggroup = editItem.getTag().toString();

                if (taggroup.equals("TAG 0")) {
                    dialogEdt = editItem;
                    dialogEdt.addTextChangedListener(dialogTextWatcher);
                }
            }
        }

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View dblbtnone = dialog.btnDoubleOne();
        dblbtnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View dblbtntwo = dialog.btnDoubleTwo();
        dblbtntwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!dialogString.trim().equals("")) {
                    dialog.dismiss();
                    isCancelCoopMembershipRequest = true;
                    isPayMembershipRequest = false;
                    str_remarks = dialogString;
                    callMainAPI();
                } else {
                    showToast("Reason for cancellation is required.", GlobalToastEnum.WARNING);
                }
            }
        });
    }

    void cancelSuccessDialog() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "Your request has been successfully cancelled.",
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.SUCCESS);

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getActivity().finish();
            }
        });
    }

    //API
    private void callMainAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Processing request...", "Please wait...");

            if (isCancelCoopMembershipRequest) {
                //cancelCoopMembershipRequest();
                cancelCoopMembershipRequestV2();
            }

        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void cancelCoopMembershipRequest() {
        Call<GenericResponse> coopMemberShipRequest = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .cancelCoopMembershipRequest(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        servicecode,
                        str_requestid,
                        str_remarks
                );

        coopMemberShipRequest.enqueue(cancelCoopMembershipRequestCallBack);
    }

    private final Callback<GenericResponse> cancelCoopMembershipRequestCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        PreferenceUtils.saveBooleanPreference(getViewContext(), "cancelcooprequest", true);
                        cancelSuccessDialog();
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
            showErrorGlobalDialogs();
            hideProgressDialog();

        }
    };

    //OTHERS
    private TextWatcher dialogTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            dialogString = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action: {
                if (!SClick.check(SClick.BUTTON_CLICK)) return;

                if(btn_action.getText().equals("PAY NOW")) {
                    Bundle args = new Bundle();
                    args.putString("FROM", "MemberShipPayment");
                    CoopAssistantPaymentOptionsActivity.start(getViewContext(),  args);
                } else {
                    PreferenceUtils.saveStringPreference(mContext, PreferenceUtils.KEY_SCMERCHANTNAME, str_coopname);
                    SchoolMiniBillingReferenceActivity.start(mContext, new SchoolMiniPayment(".", str_billingid, totalamount), String.valueOf(totalamount), "CoopAssistantApplicationDetailsFragment");
                }

                break;
            }

            case R.id.btn_cancel: {
                if (!SClick.check(SClick.BUTTON_CLICK)) return;

                cancelDialog();

                break;
            }
        }
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * **/

    private void cancelCoopMembershipRequestV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode",servicecode);
        parameters.put("requestid",str_requestid);
        parameters.put("remarks",str_remarks);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "cancelCoopMembershipRequestV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        cancelCoopMembershipRequestV2Object(cancelCoopMembershipRequestV2Callback);

    }

    private void cancelCoopMembershipRequestV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .cancelCoopMembershipRequestV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  cancelCoopMembershipRequestV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    PreferenceUtils.saveBooleanPreference(getViewContext(), "cancelcooprequest", true);
                    cancelSuccessDialog();

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            } else{
                showErrorGlobalDialogs();
            }

        }
        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            t.printStackTrace();
            hideProgressDialog();
        }
    };

}
