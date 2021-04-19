package com.goodkredit.myapplication.fragments.coopassistant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;

import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniBillingReferenceActivity;
import com.goodkredit.myapplication.adapter.coopassistant.member.CoopAssistantAccountsAdapter;
import com.goodkredit.myapplication.adapter.coopassistant.member.CoopAssistantPersonalInformationAdapter;
import com.goodkredit.myapplication.adapter.coopassistant.nonmember.CoopAssistantMenuServicesAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.decoration.DividerItemDecoration;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.model.coopassistant.CoopRequestInfo;
import com.goodkredit.myapplication.model.GKConfigurations;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantInformation;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantLoanType;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantAccounts;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantBills;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantMembershipType;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantHomeFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String KEY_COOP_MEMBER_REQUEST_STATUS = "key_coop_member_request_status";

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    //HOME
    private NestedScrollView home_body;
    private LinearLayout home_body_container;

    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //SERVICE
    private String servicecode = "";
    private String merchantid = "";
    private String servicestatus = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //NON MEMBER
    private LinearLayout nonmember_container;
    private LinearLayout imv_logo_container;
    private ImageView imv_logo;
    private LinearLayout menu_container;
    private TextView txv_header;
    private TextView txv_description;
    private LinearLayout layout_coop_aboutus;

    //MEMBERS
    private LinearLayout member_container;

    //COOP INFORMATION
    private List<CoopAssistantInformation> coopInformationList = new ArrayList<>();

    //PERSONAL INFO
    private LinearLayout personalinfo_container;
    private TextView txv_personalinfo;
    private RecyclerView rv_personalinfo;
    private CoopAssistantPersonalInformationAdapter rv_personalinfoadapter;
    private List<CoopAssistantMembers> personalInfoList = new ArrayList<>();

    //MENU
    private TextView txv_services;
    private RecyclerView rv_menu;
    private CoopAssistantMenuServicesAdapter rv_menuadapter;
    private List<GKConfigurations> menuServicesList = new ArrayList<>();

    //MEMBERSHIP
    private List<CoopAssistantMembershipType> coopMemberShipTypeList = new ArrayList<>();

    //LOAN TYPE
    private List<CoopAssistantLoanType> coopLoanTypeList = new ArrayList<>();

    //ACCOUNTS
    private LinearLayout accountsinfo_container;
    private TextView txv_accounts;
    private RecyclerView rv_accountsinfo;
    private CoopAssistantAccountsAdapter rv_accountsadapter;
    private List<CoopAssistantAccounts> accountsList = new ArrayList<>();

    //REQUEST PENDING
    private LinearLayout request_container;
    private View view_header_status;
    private TextView txv_header_request;
    private TextView txv_description_request;
    private List<CoopRequestInfo> requestInfoList = new ArrayList<>();

    private String mRequestStatus = "";
    private String mPaymentType = "";

    //ACTION
    private LinearLayout btn_action_container;
    private TextView btn_action;

    //PENDING REQUEST
    private static TextView txv_pending_content;
    private static RelativeLayout layout_pending_req_via_payment_channel;
    private static LinearLayout layout_pending_paynow;
    private static LinearLayout layout_pending_cancelrequest;
    private static TextView btn_pending_paynow;
    private static TextView btn_pending_cancelrequest;
    private static ImageView btn_pending_close;

    //PENDING CANCELLATION REMARKS
    private MaterialDialog mDialog;
    private EditText edt_remarks;
    private String remarks = "";
    private static String billingid = "";
    private static String coopname = "";
    private static String billingamount = "";

    //CANCEL DIALOG
    private EditText dialogEdt;
    private String dialogString = "";

    private static Context staticContext;

    //ERROR
    private FrameLayout error_main_container;
    private RelativeLayout error_sub_container;
    private LinearLayout errormsg_container;
    private TextView txv_errormsg;

    //REFRESH
    private SwipeRefreshLayout swipe_container;

    //FOR SECURITY UPDATE
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String cancelIndex;
    private String cancelAuth;
    private String cancelKey;
    private String cancelParam;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_home, container, false);
        try {


            init(view);
            initData();

        } catch (Exception e) {
          Logger.debug("okhttp","ERROR : "+e.getMessage());
        }

        return view;
    }

    private void init(View view) {
        //HOME
        home_body = view.findViewById(R.id.home_body);
        home_body_container = view.findViewById(R.id.home_body_container);

        //MENU
        txv_services = view.findViewById(R.id.txv_services);
        rv_menu = view.findViewById(R.id.rv_menu);

        //NON MEMBER
        nonmember_container = view.findViewById(R.id.nonmember_container);
        imv_logo_container = view.findViewById(R.id.imv_logo_container);
        imv_logo = view.findViewById(R.id.imv_logo);
        menu_container = view.findViewById(R.id.menu_container);
        txv_header = view.findViewById(R.id.txv_header);
        txv_description = view.findViewById(R.id.txv_description);
        layout_coop_aboutus = view.findViewById(R.id.layout_coop_aboutus);

        //MEMBERS
        member_container = view.findViewById(R.id.member_container);
        personalinfo_container = view.findViewById(R.id.personalinfo_container);
        txv_personalinfo = view.findViewById(R.id.txv_personalinfo);
        rv_personalinfo = view.findViewById(R.id.rv_personalinfo);
        accountsinfo_container = view.findViewById(R.id.accountsinfo_container);
        txv_accounts = view.findViewById(R.id.txv_accounts);
        rv_accountsinfo = view.findViewById(R.id.rv_accountsinfo);

        //REQUEST PENDING
        request_container = view.findViewById(R.id.request_container);
        view_header_status = view.findViewById(R.id.view_header_status);
        txv_header_request = view.findViewById(R.id.txv_header_request);
        txv_description_request = view.findViewById(R.id.txv_description_request);

        //ACTION
        btn_action_container = view.findViewById(R.id.btn_action_container);
        btn_action = view.findViewById(R.id.btn_action);
        btn_action.setText("APPLY");
        btn_action.setOnClickListener(this);

        //PENDING REQUEST
        txv_pending_content = view.findViewById(R.id.txv_content);
        layout_pending_req_via_payment_channel = view.findViewById(R.id.layout_req_via_payment_channel);
        layout_pending_paynow = view.findViewById(R.id.layout_payment_channel_paynow);
        layout_pending_cancelrequest = view.findViewById(R.id.layout_payment_channel_cancel);
        btn_pending_cancelrequest = view.findViewById(R.id.btn_cancel_request);
        btn_pending_paynow = view.findViewById(R.id.btn_paynow_request);
        btn_pending_close = view.findViewById(R.id.btn_close);
        btn_pending_paynow.setOnClickListener(this);
        btn_pending_close.setOnClickListener(this);
        btn_pending_cancelrequest.setOnClickListener(this);

        //ERROR
        error_main_container = view.findViewById(R.id.error_main_container);
        error_sub_container = view.findViewById(R.id.error_sub_container);
        error_sub_container.setGravity(Gravity.CENTER);
        errormsg_container = view.findViewById(R.id.errormsg_container);
        txv_errormsg = view.findViewById(R.id.txv_errormsg);

        //REFRESH
        swipe_container = view.findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(this);
        swipe_container.setEnabled(true);

    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");
        servicestatus = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantStatus");

        mdb = new DatabaseHandler(getViewContext());

        staticContext = getViewContext();

        //MENU
        rv_menu.setLayoutManager(new GridLayoutManager(getViewContext(), 2));
        rv_menu.setNestedScrollingEnabled(false);
        rv_menuadapter = new CoopAssistantMenuServicesAdapter(getViewContext(), CoopAssistantHomeFragment.this);
        rv_menu.setAdapter(rv_menuadapter);

        //PERSONAL INFO
        rv_personalinfo.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_personalinfo.setNestedScrollingEnabled(false);
        rv_personalinfoadapter = new CoopAssistantPersonalInformationAdapter(getViewContext());
        rv_personalinfo.setAdapter(rv_personalinfoadapter);

        //ACCOUNTS
        rv_accountsinfo.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_accountsinfo.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider)));
        rv_accountsinfo.setNestedScrollingEnabled(false);
        rv_accountsadapter = new CoopAssistantAccountsAdapter(getViewContext(), CoopAssistantHomeFragment.this);
        rv_accountsinfo.setAdapter(rv_accountsadapter);


        //CALL API
        callMainAPI();
    }

    //CHECK IF COOP HAS MERCHANT
    private void checkIfCoophasMerchant() {
        if (merchantid.trim().equals(".")) {
            btn_action_container.setVisibility(View.GONE);
        }
    }

    //API
    private void callMainAPI() {

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            swipe_container.setRefreshing(false);
            showProgressDialog("Fetching Coop Information", "Please wait...");
            //getCoopandMembersInformation();
            getCoopandMembersInformationV2();
        } else {
            swipe_container.setRefreshing(false);
            hideProgressDialog();
            showNoInternetToast();
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
                        checkCoopServiceStatus(strresponse);
                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else if (response.body().getStatus().equals("201")) {
                        String strresponse = response.body().getData();
                        checkCoopServiceStatus(strresponse);
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

    //CHECK COOP STATUS IF ONLINE/OFFLINE
    private void checkCoopServiceStatus(String strresponse) {

        if (servicestatus.equalsIgnoreCase("OFFLINE")) {
            home_body_container.setVisibility(View.GONE);
            error_main_container.setVisibility(View.VISIBLE);
            txv_errormsg.setText("It seems that the coop is offline. Please try again later.");
        } else {
            home_body_container.setVisibility(View.VISIBLE);
            error_main_container.setVisibility(View.GONE);

            getCoopInformation(strresponse);

            getCoopMemberInformation(strresponse);

            getCoopMenuServices(strresponse);

            getCoopMemberShipType(strresponse);

            getCoopLoanType(strresponse);

            getCoopAccounts(strresponse);

            checkIfCoophasMerchant();

            getCoopRequestInfo(strresponse);
        }
    }

    //COOP INFORMATION
    private void getCoopInformation(String strresponse) {
        String coopinformation = CommonFunctions.parseJSON(strresponse, "CoopInformation");

        coopInformationList = new Gson().fromJson(coopinformation, new TypeToken<List<CoopAssistantInformation>>() {
        }.getType());

        CoopAssistantInformation coopAssistantInformation = null;

        for (CoopAssistantInformation information : coopInformationList) {
            coopAssistantInformation = information;
        }

        getActivity().setTitle(CommonFunctions.replaceEscapeData(coopAssistantInformation.getCoopName()));

        if (coopAssistantInformation != null) {
            txv_header.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, "About the Coop"));
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if(coopAssistantInformation.getAboutUs() != null && !coopAssistantInformation.getAboutUs().equals(".")){
                    layout_coop_aboutus.setVisibility(View.VISIBLE);
                    txv_description.setText(Html.fromHtml(CommonFunctions.replaceEscapeData
                                    (coopAssistantInformation.getAboutUs()),
                            Html.FROM_HTML_MODE_COMPACT));
                } else{
                    layout_coop_aboutus.setVisibility(View.GONE);
                }

            } else {
                if(coopAssistantInformation.getAboutUs() != null && !coopAssistantInformation.getAboutUs().equals(".")){
                    layout_coop_aboutus.setVisibility(View.VISIBLE);
                    txv_description.setText(Html.fromHtml(
                            CommonFunctions.replaceEscapeData(coopAssistantInformation.getAboutUs())));
                } else{
                    layout_coop_aboutus.setVisibility(View.GONE);
                }

            }

            Glide.with(mContext)
                    .load(coopAssistantInformation.getLogo())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .fitCenter())
                    .into(imv_logo);
        }

        PreferenceUtils.removePreference(getViewContext(), CoopAssistantInformation.KEY_COOPINFORMATION);
        PreferenceUtils.saveCoopInformationListPreference(getViewContext(), CoopAssistantInformation.KEY_COOPINFORMATION, coopInformationList);
    }

    //COOP MEMBERS
    private void getCoopMemberInformation(String strresponse) {
        String coopmemberinformation = CommonFunctions.parseJSON(strresponse, "CoopMemberInformation");

        personalInfoList = new Gson().fromJson(coopmemberinformation, new TypeToken<List<CoopAssistantMembers>>() {
        }.getType());

        txv_personalinfo.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, "Personal Information"));

        if (personalInfoList.size() > 0) {
            nonmember_container.setVisibility(View.GONE);
            member_container.setVisibility(View.VISIBLE);
            nonmember_container.setVisibility(View.GONE);
            btn_action_container.setVisibility(View.GONE);
            rv_personalinfoadapter.updateData(personalInfoList);
        } else {
            member_container.setVisibility(View.GONE);
            nonmember_container.setVisibility(View.VISIBLE);
            member_container.setVisibility(View.GONE);
            btn_action_container.setVisibility(View.VISIBLE);
        }

        PreferenceUtils.removePreference(getViewContext(), CoopAssistantMembers.KEY_COOPMEMBERINFORMATION);
        PreferenceUtils.saveCoopMembersListPreference(getViewContext(), CoopAssistantMembers.KEY_COOPMEMBERINFORMATION, personalInfoList);
    }

    //COOP SERVICES
    private void getCoopMenuServices(String strresponse) {
        String menuservices = CommonFunctions.parseJSON(strresponse, "Menuservices");

        menuServicesList = new Gson().fromJson(menuservices, new TypeToken<List<GKConfigurations>>() {
        }.getType());

        rv_menuadapter.updateData(menuServicesList);

        if (menuServicesList.size() > 0) {
            menu_container.setVisibility(View.VISIBLE);
            txv_services.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, "Services"));
        } else {
            menu_container.setVisibility(View.GONE);
        }
        PreferenceUtils.removePreference(getViewContext(), GKConfigurations.KEY_MENUSERVICES);
        PreferenceUtils.saveCoopMenusListPreference(getViewContext(), GKConfigurations.KEY_MENUSERVICES, menuServicesList);
    }

    //MEMBERSHIP TYPE
    private void getCoopMemberShipType(String strresponse) {
        String coopmembershiptype = CommonFunctions.parseJSON(strresponse, "CoopMembershipType");

        coopMemberShipTypeList = new Gson().fromJson(coopmembershiptype, new TypeToken<List<CoopAssistantMembershipType>>() {
        }.getType());

        PreferenceUtils.removePreference(getViewContext(), CoopAssistantMembershipType.KEY_COOPMEMBERSHIPTYPE);
        PreferenceUtils.saveCoopMemberShipTypeListPreference(getViewContext(), CoopAssistantMembershipType.KEY_COOPMEMBERSHIPTYPE, coopMemberShipTypeList);
    }

    //LOAN TYPE
    private void getCoopLoanType(String strresponse) {
        String cooploantype = CommonFunctions.parseJSON(strresponse, "CoopLoanType");

        coopLoanTypeList = new Gson().fromJson(cooploantype, new TypeToken<List<CoopAssistantLoanType>>() {
        }.getType());

        PreferenceUtils.removePreference(getViewContext(), CoopAssistantLoanType.KEY_COOPLOANTYPE);
        PreferenceUtils.saveCoopAssistantLoanTypeListPreference(getViewContext(),  CoopAssistantLoanType.KEY_COOPLOANTYPE, coopLoanTypeList);
    }

    //ACCOUNTS
    private void getCoopAccounts(String strresponse) {

        String coopaccounts = CommonFunctions.parseJSON(strresponse, "CoopAccounts");
        accountsList = new Gson().fromJson(coopaccounts, new TypeToken<List<CoopAssistantAccounts>>() {
        }.getType());

        if(accountsList != null) {
            if(accountsList.size() > 0) {
                accountsinfo_container.setVisibility(View.VISIBLE);
                txv_accounts.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, "Accounts"));

                rv_accountsadapter.updateData(accountsList);
                PreferenceUtils.removePreference(getViewContext(), CoopAssistantAccounts.KEY_COOPACCOUNTS);
                PreferenceUtils.saveCoopAccountsListPreference(getViewContext(), CoopAssistantAccounts.KEY_COOPACCOUNTS, accountsList);
            } else {
                accountsinfo_container.setVisibility(View.GONE);
            }
        } else {
            accountsinfo_container.setVisibility(View.GONE);
        }
    }

    //COOP REQUEST INFO
    private void getCoopRequestInfo(String strresponse) {

        String cooprequestInfo = CommonFunctions.parseJSON(strresponse, "CoopRequestInfo");

        if(!cooprequestInfo.trim().equals("")) {
            requestInfoList = new Gson().fromJson(cooprequestInfo, new TypeToken<List<CoopRequestInfo>>() {
            }.getType());

            if (requestInfoList != null) {
                if (requestInfoList.size() > 0) {
                    btn_action_container.setVisibility(View.GONE);
                    request_container.setVisibility(View.VISIBLE);

                    CoopRequestInfo coopRequestInfo = null;

                    for (CoopRequestInfo requestInfo : requestInfoList) {
                        coopRequestInfo = requestInfo;
                    }

                    if (coopRequestInfo != null) {
                        String coopName = coopRequestInfo.getCoopName();
                        String mobilenumber = coopRequestInfo.getMemberMobileNumber();

                        mRequestStatus = coopRequestInfo.getRequestStatus();
                        mPaymentType = coopRequestInfo.getPaymentType();

                        String title = "";
                        String description = "";

                        if (mRequestStatus.contains("FOR PAYMENT")) {
                            title = "Your application has been approved.";
                            description = "Your application for membership to " + coopName + " has been approved. " +
                                    " If you want to accept, please click this for your membership payment. ";

                            view_header_status.setBackground(ContextCompat.getDrawable(getViewContext(), R.color.coopassist_request_blue));

                        } else if (mRequestStatus.contains("PENDING")) {
                            title = "Your application is still pending for approval.";

                            description = "You will be notified through your mobile number +" + mobilenumber +
                                    " or an officer from " + coopName + " may contact you anytime to assist you" +
                                    " with your application. Please click this to view your membership request details.";

//                            if (mPaymentType.contains("PARTNER")) {
//                                description = "You will be notified through your mobile number +" + mobilenumber +
//                                        " or an officer from " + coopName + " may contact you anytime to assist you" +
//                                        " with your application. Please click this to view your membership request details.";
//                            } else {
//                                description = "You will be notified through your mobile number +" + mobilenumber +
//                                        " or an officer from " + coopName + " may contact you anytime to assist you" +
//                                        " with your application.";
//                            }

                            view_header_status.setBackground(ContextCompat.getDrawable(getViewContext(), R.color.coopassist_request_yellow));
                        }

                        txv_header_request.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, title));

                        txv_description_request.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_REGULAR, description));

                        request_container.setOnClickListener(requestListener);

                        PreferenceUtils.removePreference(getViewContext(), CoopRequestInfo.KEY_COOPREQUESTINFO);
                        PreferenceUtils.saveCoopRequestInfoListPreference(getViewContext(), CoopRequestInfo.KEY_COOPREQUESTINFO, requestInfoList);
                    }
                } else {
                    request_container.setVisibility(View.GONE);
                }
            } else {
                request_container.setVisibility(View.GONE);
            }
        } else {
            request_container.setVisibility(View.GONE);
        }
    }

    //RADIO LISTENER
    private View.OnClickListener requestListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!SClick.check(SClick.BUTTON_CLICK)) return;
//            if(mRequestStatus.contains("FOR PAYMENT")) {
//                CoopAssistantHomeActivity.start(mContext, 16, null);
//            } else if (mRequestStatus.contains("PENDING")) {
//                if(mPaymentType.contains("PARTNER")) {
//                    CoopAssistantHomeActivity.start(mContext, 16, null);
//                }
//            }

            Bundle args = new Bundle();
            args.putString(KEY_COOP_MEMBER_REQUEST_STATUS, mRequestStatus);

            CoopAssistantHomeActivity.start(mContext, 16, args);
        }
    };

    //ONCLICK AND STARTS
    @Override
    public void onResume() {
        //BADGE
        boolean cancelcooprequest = PreferenceUtils.getBooleanPreference(getViewContext(), "cancelcooprequest");
        if (cancelcooprequest) {
            PreferenceUtils.removePreference(getViewContext(), "cancelcooprequest");
            swipe_container.setRefreshing(true);
            callMainAPI();
        }

        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_action) {
            if (!SClick.check(SClick.BUTTON_CLICK)) return;
            CoopAssistantHomeActivity.start(getViewContext(), 1, null);
        }

        switch (view.getId()){
            case R.id.btn_close: {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;

                layout_pending_req_via_payment_channel.setVisibility(View.GONE);
                break;
            }

            case R.id.btn_cancel_request: {

                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                cancelDialog();

                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        swipe_container.setRefreshing(true);
        callMainAPI();
    }

    public static void showPendingLayout(List<CoopAssistantBills> list, String txnno, String amount, String coopname){

        billingid = txnno;

        if(list != null){
            if(list.isEmpty()){
                layout_pending_req_via_payment_channel.setVisibility(View.GONE);
            }  else{
                if(txnno.substring(0,3).equals("771") || txnno.substring(0,3).equals("991")
                        || txnno.substring(0,3).equals("551")){
                    layout_pending_paynow.setVisibility(View.GONE);
                    txv_pending_content.setText("You have a pending payment transaction. If you think you completed " +
                            "this transaction, please contact support,if not just cancel the request.");

                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                    );
                    layout_pending_cancelrequest.setLayoutParams(param);

                } else{

                    layout_pending_paynow.setVisibility(View.VISIBLE);
                    txv_pending_content.setText("You have a pending payment request, kindly settle it first or cancel " +
                            "your previous request.");

                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f
                    );
                    layout_pending_cancelrequest.setLayoutParams(param);
                }

                layout_pending_req_via_payment_channel.setVisibility(View.VISIBLE);
            }
        }

        btn_pending_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PreferenceUtils.saveStringPreference(staticContext, PreferenceUtils.KEY_SCMERCHANTNAME, coopname);
                SchoolMiniBillingReferenceActivity.start(staticContext, new SchoolMiniPayment(".", txnno, Double.valueOf(amount)),
                        amount, "CoopAssistantHomeMemberAccount");
            }

            //CoopAssistantPaymentOptionsActivity
        });
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
                dialog.dismiss();

                if (!dialogString.trim().equals("")) {
                    remarks = dialogString;
                } else {
                    remarks = ".";
                }

                cancelRequest();

            }
        });
    }

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

    private void cancelRequest(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            showProgressDialog("", "Please wait...");
           // cancelCoopPaymentChannelRequest();
            cancelRequestPaymentsV2();
        } else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }

//    private void cancelCoopPaymentChannelRequest() {
//        Call<GenericResponse> cancelpayment = RetrofitBuild.getCoopAssistantAPI(getViewContext())
//                .cancelCoopPaymentChannelRequestCall(
//                        sessionid,
//                        imei,
//                        userid,
//                        borrowerid,
//                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
//                        servicecode,
//                        "MEMBER ACCOUNT",
//                        billingid,
//                        remarks,
//                        CommonVariables.devicetype
//                );
//
//        cancelpayment.enqueue(cancelCoopPaymentChannelRequestSession);
//    }
//
//    private final Callback<GenericResponse> cancelCoopPaymentChannelRequestSession = new Callback<GenericResponse>() {
//        @Override
//        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
//            try {
//                hideProgressDialog();
//                ResponseBody errorBody = response.errorBody();
//                if (errorBody == null) {
//                    if (response.body().getStatus().equals("000")) {
//                        layout_pending_req_via_payment_channel.setVisibility(View.GONE);
//                        showCancellatiowsb168 nSuccessfulDialog();
//
//                    } else if (response.body().getStatus().equals("104")) {
//                        showAutoLogoutDialog(response.body().getMessage());
//                    } else {
//                        showErrorGlobalDialogs(response.body().getMessage());
//                    }
//                } else {
//                    showErrorGlobalDialogs();
//                }
//            } catch (Exception e) {
//                hideProgressDialog();
//                showErrorGlobalDialogs();
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            showErrorGlobalDialogs();
//            hideProgressDialog();
//        }
//    };

    private void showCancellationSuccessfulDialog() {
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
            }
        });
    }

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    private void getCoopandMembersInformationV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("servicecode",servicecode);
            parameters.put("devicetype",CommonVariables.devicetype);

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
                            checkCoopServiceStatus(data);
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


    private void cancelRequestPaymentsV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("paymenttype", "MEMBER ACCOUNT");
        parameters.put("txnno", billingid);
        parameters.put("remarks", remarks);
        parameters.put("devicetype", CommonVariables.devicetype);


        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        cancelIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", cancelIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        cancelAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        cancelKey = CommonFunctions.getSha1Hex(cancelAuth + sessionid + "cancelRequestPaymentsV2");
        cancelParam = CommonFunctions.encryptAES256CBC(cancelKey, String.valueOf(paramJson));

        cancelRequestPaymentsV2Object(cancelRequestPaymentsV2Callback);


    }
    private void cancelRequestPaymentsV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .cancelRequestPaymentsV2(cancelAuth,sessionid,cancelParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  cancelRequestPaymentsV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(cancelKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    layout_pending_req_via_payment_channel.setVisibility(View.GONE);
                    showCancellationSuccessfulDialog();
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
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };





}
