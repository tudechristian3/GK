package com.goodkredit.myapplication.activities.gkstore;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.GKNotificationsCustomValues;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.whatsnew.BadgeResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.view.CustomNestedScrollView;
import com.goodkredit.myapplication.activities.delivery.SetupDeliveryAddressActivity;
import com.goodkredit.myapplication.adapter.gkstore.GKStoreAdapter;
import com.goodkredit.myapplication.adapter.gkstore.GKStoreSummaryAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.bean.GKStoreMerchants;
import com.goodkredit.myapplication.bean.GKStoreProducts;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.gkstore.GKStoreAgentFragment;
import com.goodkredit.myapplication.model.GenericObject;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.DiscountResponse;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetServiceChargeResponse;
import com.goodkredit.myapplication.responses.gkstore.GetStoreProductsResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import fr.arnaudguyon.xmltojsonlib.JsonToXml;
import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKStoreDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Toolbar toolbar;
    private DatabaseHandler mdb;
    //COMMON
    private String sessionid;
    private Dialog alertDialog;

    //LOADER
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;
    private SwipeRefreshLayout swipeRefreshLayout;

    //FOR DISCOUNT DIALOG (OLD)
    private Dialog discountdialog = null;

    //SERVICES
    private GKService gkservice = null;

    private String merchantid = "";
    private String storeid = "";
    private String servicecode = "";

    //GKSTORE PRODUCTS
    private List<GKStoreProducts> productslist = new ArrayList<>();
    private List<GKStoreProducts> orderdetailslist = new ArrayList<>();
    private String gkstorefirstnamestr = "";
    private String gkstorelastnamestr = "";
    private String gkstoremobilenumstr = "";
    private String gkstoreemailaddressstr = "";
    private String gkstoredeliveryaddressstr = "";
    private String latitude = "";
    private String longitude = "";
    private String optionchoice = "";
    private String kycinfo = "";
    private double totalorderamount = 0.00;
    private double grossamount = 0.00;
    private String borrowername = "";
    private String vouchersession = "";
    private int limit = 0;

    private RecyclerView GKStoreAdapterRV;
    private GKStoreMerchants gkstoremerchantspassed;
    private GKStoreAdapter gkadapter;

    private RelativeLayout gkstoreheaderlayout;
    private RelativeLayout picturecontainer;
    private LinearLayout gkstorepersonalinfo;
    private LinearLayout gkstoreadaptercontainer;
    private FrameLayout adapterbackgroundcontainer;
    private LinearLayout proceedcontainer;
    private RelativeLayout gkstoreinactivecontainer;

    //CUSTOMER INFORMATION (STATIC)
    private EditText gkstorefirstname;
    private EditText gkstorelastname;
    private EditText gkstoreemailaddress;
    private EditText gkstoremobilenum;
    private EditText gkstoredeliveryaddress;

    //ERROR MESSAGE (STATIC)
    private TextView firstnameErrorMessage;
    private TextView lastnameErrorMessage;
    private TextView mobileErrorMessage;
    private TextView emailErrorMessage;
    private TextView deliveryaddErrorMessage;

    private ImageView closeiconview;
    private ImageView mLogoPic;
    private TextView emptyproducts;

    private boolean proceedtopayments = false;
    private boolean xmlexist = false;

    private boolean adapterexist = true;
    private boolean otherinfoxml = true;
    private boolean ifcanproceed = false;

    //OTHER XML DETAILS
    private List<EditText> inputslist = new ArrayList<>();
    private List<TextView> textviewlist = new ArrayList<TextView>();
    private List<TextView> txtbuttonlist = new ArrayList<TextView>();
    private List<Spinner> spinnerlist = new ArrayList<Spinner>();
    private List<LinearLayout> spinnerlistcontainer = new ArrayList<LinearLayout>();
    final HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();
    private JSONArray params = new JSONArray();
    private JSONArray gkstoreresarr = new JSONArray();

    //ERROR TEXTVIEW
    private List<TextView> errortextviewlist = new ArrayList<TextView>();

    //REMARKS
    private JSONArray remarksjsonarr = new JSONArray();
    private JSONArray passedjsonarr = new JSONArray();
    private List<EditText> remarkslist = new ArrayList<EditText>();
    private String passedXMLremarks = "";
    private boolean isRemarks = false;

    //LAYOUT FOR NO INTERNET CONNECTION
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //VIEW HISTORY
    private LinearLayout btn_view_history_container;

    private MaterialDialog mDialog = null;
    private GPSTracker tracker;

    //CUSTOM NESTED SCROLLVIEW
    public CustomNestedScrollView scrollmaincontainer;

    //STORE MORE INFO
    private LinearLayout gkstoremoreinfo;
    private Dialog gkstoremoreinfodialog = null;

    //SUMMARY
    private RecyclerView gkstoreordersummaryRV;
    private GKStoreSummaryAdapter gkstoresummaryadapter;
    private TextView txttotalamount;

    //SEARCH
    private EditText edt_search_box;
    private TextView noresultsfound;
    private LinearLayout edt_search_icon_image_container;
    private ImageView edt_search_icon_image;
    private int searchiconselected = 0;

    //MAPS
    private ImageView gkstoremap;
    private int checklocationiffound = 0;
    private Address mapsAddress;

    //OFFLINE STORE
    private LinearLayout gkstoreoffline;
    private RelativeLayout gkstoreofflinecovers;

    //PICKUP
    private LinearLayout gkstoredeliverytypecontainer;
    private TextView gkstoredeliverytype;
    private String strgkstoredeliverytype;

    //DELIVERY
    private LinearLayout gkstoredeliveryaddresscont;
    private MaterialDialog materialDialog;

    //MERCHANT ADDRESS
    private String strmerchantlat = "";
    private String strmerchantlong = "";
    private String strradius = "";
    private String strmerchantaddress = "";
    private String strmercordertype = "";
    private String strpaymenttype = "";
    private String strmerchantpaymenttype = "";

    //SERVICE CHARGE
    private TextView txtDeliveryCharge;
    private double servicecharge = 0.00;
    private String strdeliverycharge = "";
    private boolean isServiceCharge = false;
    private LinearLayout deliverychargecontainer;

    //STORE ADDRESS
    private LinearLayout gkstorestoreaddcontainer;
    private TextView gkstorestoreadd;
    private boolean checkgkstorestatus = false;

    //SCROLLLIMIT
    private boolean isloadmore = true;
    private boolean isbottomscroll = false;
    private boolean isfirstload = true;
    private boolean isnowScrollonBottom = false;

    //PHILCARE
    private LinearLayout gkstoremiddlenamecontainer;
    private TextView gkstoremiddlenamelbl;
    private EditText gkstoremiddlename;
    private TextView middlenameErrorMessage;

    //DISCOUNT RESELLER
    private String discountlatitude;
    private String discountlongitude;
    private String strtotalamount;
    private double discount;
    private String strdiscount;
    private int hasdiscount = 0;
    private String discountmessage = "";
    private String distributorid = "";
    private String resellerid = "";

    private FusedLocationProviderClient mFusedLocationClient;

    //AGENT
    private FrameLayout fragment_metrogas;
    private boolean isAgent = false;
    private String currentdate = "";

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    //CHECK IF DELIVERY ADDRESS IF DISABLED OR NOT
    private boolean checkDeliveryEditStatus = false;

    //DELIVERY ADDRESS HINT
    private TextView deliveryhint;

    //STEP ORDER
    private int isapproved = 1;
    private String forapproval = "";
    private String morderdetailsstr;

    //NEW GLOBAL DIALOGS
    private GlobalDialogs deliverOptionsDialogs;

    //BADGE
    private boolean badgecounter = false;
    private int badge = 0;

    //OTHER DETAILS
    private LinearLayout layout_otherinfo;

    //MAIN CONTAINER FOR ORDER LAYOUT
    private LinearLayout gkstoreordercontainer;

    //for receiving the object from product details activity
    private GKStoreProducts getGkStoreProducts;
    private GKNotificationsCustomValues customValues;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String badgeIndex;
    private String badgeAuth;
    private String badgeKey;
    private String badgeParam;

    private String productIndex;
    private String productAuth;
    private String productKey;
    private String productParam;

    private String insertIndex;
    private String insertAuthenticationid;
    private String insertKey;
    private String insertParam;

     String deliverytype = "";
     boolean isDeliverTypeSelected =false;
     boolean isPaymentTypeSelected =false;
    String orderstatus ="";

    GKService service;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gk_store_details);
            //All Initializations
            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init() {
        toolbar = findViewById(R.id.toolbar);

        mLlLoader = findViewById(R.id.loaderLayout);
        mTvFetching = findViewById(R.id.fetching);
        mTvWait = findViewById(R.id.wait);

        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        gkstorepersonalinfo = findViewById(R.id.gkstorepersonalinfo);
        gkstoreadaptercontainer = findViewById(R.id.gkstoreadaptercontainer);
        adapterbackgroundcontainer = findViewById(R.id.adapterbackgroundcontainer);
        proceedcontainer = findViewById(R.id.proceedcontainer);
        gkstoreinactivecontainer = findViewById(R.id.gkstoreinactivecontainer);
        closeiconview = findViewById(R.id.closeiconview);
        picturecontainer = findViewById(R.id.picturecontainer);

        emptyproducts = findViewById(R.id.emptyproducts);
        mLogoPic = findViewById(R.id.mLogoPic);

        nointernetconnection = findViewById(R.id.nointernetconnection);
        refreshnointernet = findViewById(R.id.refreshnointernet);

        btn_view_history_container = findViewById(R.id.btn_view_history_container);

        gkstoreheaderlayout = findViewById(R.id.gkstoreheaderlayout);

        scrollmaincontainer = findViewById(R.id.scrollmaincontainer);
        scrollmaincontainer.setOnScrollChangeListener(scrollOnChangedListener);


        gkstoremoreinfo = findViewById(R.id.gkstoremoreinfo);

        //SEARCH
        edt_search_box = findViewById(R.id.edt_search_box);
        noresultsfound = findViewById(R.id.noresultsfound);
        edt_search_icon_image = findViewById(R.id.edt_search_icon_image);
        edt_search_icon_image_container = findViewById(R.id.edt_search_icon_image_container);

        //MAP
        gkstoremap = findViewById(R.id.gkstoremap);

        //OFFLINE STORE
        gkstoreoffline = findViewById(R.id.gkstoreoffline);

        //PICKUP
        gkstoredeliverytypecontainer = findViewById(R.id.gkstoredeliverytypecontainer);
        gkstoredeliverytype = findViewById(R.id.gkstoredeliverytype);

        //STORE ADD
        gkstorestoreaddcontainer = findViewById(R.id.gkstorestoreaddcontainer);
        gkstorestoreadd = findViewById(R.id.gkstorestoreadd);

        //DELIVERY
        gkstoredeliveryaddresscont = findViewById(R.id.gkstoredeliveryaddresscont);

        //Service Charge
        txtDeliveryCharge = findViewById(R.id.txtServiceCharge);
        deliverychargecontainer = findViewById(R.id.deliverychargecontainer);

        //TOTAL AMOUNT
        txttotalamount = findViewById(R.id.txttotalamount);

        //FOR TEXT WATCHER
        gkstorefirstname = findViewById(R.id.gkstorefirstname);
        firstnameErrorMessage = findViewById(R.id.firstnameErrorMessage);
        firstnameErrorMessage.setText(R.string.string_firstname_error);
        gkstorefirstname.addTextChangedListener(new TextWatcher(gkstorefirstname, firstnameErrorMessage));

        gkstorelastname = findViewById(R.id.gkstorelastname);
        lastnameErrorMessage = findViewById(R.id.lastnameErrorMessage);
        lastnameErrorMessage.setText(R.string.string_lastname_error);
        gkstorelastname.addTextChangedListener(new TextWatcher(gkstorelastname, lastnameErrorMessage));

        gkstoremobilenum = findViewById(R.id.gkstoremobilenum);
        mobileErrorMessage = findViewById(R.id.mobileErrorMessage);
        mobileErrorMessage.setText(R.string.string_mobilenum_error);
        gkstoremobilenum.addTextChangedListener(new TextWatcher(gkstoremobilenum, mobileErrorMessage));

        gkstoreemailaddress = findViewById(R.id.gkstoreemailaddress);
        emailErrorMessage = findViewById(R.id.emailErrorMessage);
        emailErrorMessage.setText(R.string.string_emailadd_error);
        gkstoreemailaddress.addTextChangedListener(new TextWatcher(gkstoreemailaddress, emailErrorMessage));

        gkstoredeliveryaddress = findViewById(R.id.gkstoredeliveryaddress);
        deliveryaddErrorMessage = findViewById(R.id.deliveryaddErrorMessage);
        deliveryaddErrorMessage.setText(R.string.string_deliveryadd_error);
        gkstoredeliveryaddress.addTextChangedListener(new TextWatcher(gkstoredeliveryaddress, deliveryaddErrorMessage));

        //MIDDLE NAME
        gkstoremiddlenamecontainer = findViewById(R.id.gkstoremiddlenamecontainer);
        gkstoremiddlenamelbl = findViewById(R.id.gkstoremiddlenamelbl);
        gkstoremiddlename = findViewById(R.id.gkstoremiddlename);
        middlenameErrorMessage = findViewById(R.id.middlenameErrorMessage);
        middlenameErrorMessage.setText(R.string.string_middlename_error);
        if (storeid.equals("PHILCARE")) {
            gkstoremiddlename.addTextChangedListener(new TextWatcher(gkstoremiddlename, middlenameErrorMessage));
        }

        //AGENT
        fragment_metrogas = findViewById(R.id.fragment_metrogas);

        deliveryhint = findViewById(R.id.deliveryhint);

        //OTHER DETAILS
        layout_otherinfo = findViewById(R.id.layout_otherinfo);

        gkstoreordercontainer = findViewById(R.id.gkstoreordercontainer);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());

        service = getIntent().getParcelableExtra("GKSERVICE_OBJECT");
        if (service != null) {
            gkservice = service;
            merchantid = gkservice.getMerchantID();
            storeid = gkservice.getGKStoreID();
            servicecode = gkservice.getServiceCode();

        } else {
            customValues = new Gson().fromJson(getIntent().getStringExtra("NOTIF_VALUES"), GKNotificationsCustomValues.class);
            merchantid = customValues.getMerchantid();
            storeid = customValues.getStoreid();
            servicecode = customValues.getServicecode();
        }

        //DISABLE REFRESH
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setRefreshing(false);

        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        //GK NEGOSYO
        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

        //STORE PRODUCTS
        GKStoreAdapterRV = findViewById(R.id.gkstoredetailslist);
        GKStoreAdapterRV.setLayoutManager(new LinearLayoutManager(getViewContext()));
        GKStoreAdapterRV.setNestedScrollingEnabled(false);
        gkadapter = new GKStoreAdapter(getViewContext(), productslist);
        GKStoreAdapterRV.setAdapter(gkadapter);
        gkadapter.updateData(productslist);
        if (customValues != null) {
            gkadapter.filter(customValues.getProductname());
        }

        //CREATES THE TOOLBAR
        setUpToolbar();

        //CHECKS IF THE GPS IS OFFLINE OR ONLINE
        checkGPSATStart();

        //LOADER
        loaderTimer();

        //SCROLLS ON TOP
        scrollonTop();

        //DISABLES KEYBOARD WHEN USER SCROLLS ON THE LIST
        hideKeyboardwhileScrolling();

        //GETS PERSONAL INFORMATION
        getPersonalInformation();

        //CHECKS ALL ONCLICK LISTENER
        globalonClickListeners();

        //SEARCH
        gkStoreSearch();

        //IF STORE IS PHILCARE, DISPLAY THE MIDDLE NAME
        checkMiddleName();

        //API CALL
        if (productslist.isEmpty()) {
            getSession();
        }
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        toolBarName();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //TOOLBAR NAME FOR PROCEED
    private void toolBarName() {
        TextView proceedtxt = toolbar.findViewById(R.id.proceedtxt);
        proceedtxt.setText("PROCEED");
        getSupportActionBar().setTitle("");
    }

    //TIMER FOR THE LOADER
    private void loaderTimer() {
        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };
    }

    //GET SUBSCRIBER INFORMATION OR FROM PROFILE
    private void getPersonalInformation() {
        String strfirstname = "";
        String strlastname = "";
        String strmobilenum = "";
        String stremailaddress = "";
        String strmiddlename = "";

        //get account information
        Cursor cursor = mdb.getAccountInformation(mdb);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                strfirstname = cursor.getString(cursor.getColumnIndex("firstname"));
                strmiddlename = cursor.getString(cursor.getColumnIndex("middlename"));
                strlastname = cursor.getString(cursor.getColumnIndex("lastname"));
                strmobilenum = cursor.getString(cursor.getColumnIndex("mobile"));
                stremailaddress = cursor.getString(cursor.getColumnIndex("email"));

            } while (cursor.moveToNext());
        }
        cursor.close();

        //Setting the borrower name to the retrieve firstname and lastname not the inputted names.
        borrowername = strfirstname + " " + strlastname;

        if (!servicecode.equals("GKPHONESTORE")) {
            gkstorefirstname.setText(strfirstname);
            gkstorelastname.setText(strlastname);
            gkstoremobilenum.setText(strmobilenum);
            if (storeid.equals("PHILCARE")) {
                gkstoremiddlename.setText(strmiddlename);
            }
            if (stremailaddress.equals(".")) {
                stremailaddress = "";
            }
            gkstoreemailaddress.setText(stremailaddress);
        }
    }

    //GETS THE LOGO OF THE MERCHANT
    private void gettingDetailsLogo() {
        try {
            String gkstorebannerlogo = gkstoremerchantspassed.getGKStoreBannerURL();
            Glide.with(getViewContext())
                    .load(gkstorebannerlogo)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.gkstore_banner_default)
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    )
                    .into(mLogoPic);
        } catch (Exception e) {
            e.printStackTrace();
            Glide.with(getViewContext())
                    .load(R.drawable.gkstore_banner_default)
                    .apply(new RequestOptions()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    )
                    .into(mLogoPic);
        }
    }

    //DISPLAYING THE PASSED XML DETAILS
    private void addXMLDetails() {
        try {
            params = new JSONArray();
            String count = CommonFunctions.parseXML(kycinfo, "count");
            if (!count.equals("") && !count.equals(".") && !count.equals("NONE")) {
                for (int i = 0; i < Integer.parseInt(count); i++) {
                    String value = CommonFunctions.parseXML(kycinfo, String.valueOf(i));
                    String field = CommonFunctions.parseXML(value, "mobile");
                    String[] fieldarr = field.split(":::");
                    try {
                        if (!field.equals("NONE") && !field.equals("")) {
                            if (fieldarr.length > 0) {
                                String name = fieldarr[0];
                                String description = fieldarr[1];
                                String datatype = fieldarr[2];
                                String mandatory = fieldarr[3];
                                String inputtype = fieldarr[4];


                                mandatory = mandatory.toUpperCase();
                                //set this for the submit checking of values
                                JSONObject obj = new JSONObject();
                                obj.put("name", name);
                                obj.put("description", description);
                                obj.put("mandatory", mandatory);
                                obj.put("datatype", datatype);
                                params.put(obj);

                                //1. Create title (description)
                                if (!mandatory.equals("NO")) {
                                    name = name + "*";
                                }

                                if (inputtype.contains("SELECT")) {

                                    createTextView(name, description, mandatory);

                                    createSpinner(name, inputtype, mandatory);

                                    createErrorTextView(name, inputtype, mandatory);

                                } else if (inputtype.contains("CALENDAR")) {
                                    //1.
                                    createTextView(name, description, mandatory);

                                    createTxtButton(name, mandatory);

                                    createErrorTextView(name, inputtype, mandatory);

                                } else {//TEXT VIEW
                                    //1.
                                    createTextView(name, description, mandatory);

                                    //2.Add inputs
                                    createEditText(name, datatype, mandatory);

                                    createErrorTextView(name, inputtype, mandatory);
                                }
                            }
                        }
                        otherDetailsAddTextWatcher();
                    } catch (Error e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //CREATES THE TEXTVIEW (XML DETAILS)
    private void createTextView(String name, String description, String mandatory) {

        try {
            JSONObject txtviewtag = new JSONObject();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            int marginleft = 0;
            int margintop = 15;
            int marginright = 0;
            int marginbottom = 0;

            marginleft = converttoDP(marginleft);
            margintop = converttoDP(margintop);
            marginright = converttoDP(marginright);
            marginbottom = converttoDP(marginbottom);

            layoutParams.setMargins(marginleft, margintop, marginright, marginbottom);

            if (storeid.equals("PHILCARE")) {
                if (name.equals("MiddleName*") || name.equals("MiddleName")) {
                    txtviewtag.put("name", name);
                    txtviewtag.put("mandatory", mandatory);
                    gkstoremiddlenamelbl.setTag(txtviewtag);
                    textviewlist.add(gkstoremiddlenamelbl);
                    if (mandatory.equals("YES") || mandatory.equals("NO")) {
                        gkstoremiddlenamelbl.setVisibility(View.VISIBLE);
                    } else {
                        gkstoremiddlenamelbl.setVisibility(View.GONE);
                    }

                } else {
                    displayTextView(name, description, mandatory, txtviewtag, layoutParams);
                }
            } else {
                displayTextView(name, description, mandatory, txtviewtag, layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DISPLAY THE TEXTVIEW (XML DETAILS)
    private void displayTextView(String name, String description, String mandatory,
                                 JSONObject txtviewtag, LinearLayout.LayoutParams layoutParams) {
        try {
            TextView txtv = new TextView(this);
            String silver = "#C0C0C0";
            txtv.setTextColor(Color.parseColor(silver));
            txtv.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            txtv.setText(name);
            txtv.setTextSize(14);

            txtv.setPadding(10, 10, 10, 10);
            txtviewtag.put("name", name);
            txtviewtag.put("mandatory", mandatory);
            txtv.setTag(txtviewtag);
            textviewlist.add(txtv);
            layout_otherinfo.addView(txtv, layoutParams);
            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                txtv.setVisibility(View.VISIBLE);
            } else {
                txtv.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CREATES THE EDITEXT (XML DETAILS)
    private void createEditText(String name, String datatype, String mandatory) {
        try {
            JSONObject edittexttag = new JSONObject();
            if (storeid.equals("PHILCARE")) {
                if (name.equals("MiddleName*") || name.equals("MiddleName")) {
                    edittexttag.put("name", name);
                    edittexttag.put("mandatory", mandatory);
                    gkstoremiddlename.setTag(edittexttag);
                    inputslist.add(gkstoremiddlename);
                    if (mandatory.equals("YES") || mandatory.equals("NO")) {
                        gkstoremiddlename.setVisibility(View.VISIBLE);
                    } else {
                        gkstoremiddlename.setVisibility(View.GONE);
                    }

                } else {
                    displayEditText(name, datatype, mandatory);
                }
            } else {
                displayEditText(name, datatype, mandatory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DISPLAY THE EDITEXT (XML DETAILS)
    private void displayEditText(String name, String datatype, String mandatory) {
        try {
            JSONObject edittexttag = new JSONObject();
            EditText editText = new EditText(this);
            editText.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));

            int paddingall = 10;
            paddingall = converttoDP(paddingall);

            editText.setPadding(paddingall, paddingall, paddingall, paddingall);
            String gray = "#49494A";
            editText.setTextColor(Color.parseColor(gray));
            editText.setTextSize(14);
            editText.setSingleLine();
            edittexttag.put("name", name);
            edittexttag.put("mandatory", mandatory);
            editText.setTag(edittexttag);
            editText.setBackgroundResource(R.drawable.border);
            if (datatype.toUpperCase().equals("MONEY") || datatype.toUpperCase().equals("NUMBER")) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                editText.setFilters(new InputFilter[]{
                        new InputFilter() {
                            @Override
                            public CharSequence filter(CharSequence cs, int start,
                                                       int end, Spanned spanned, int dStart, int dEnd) {
                                // TODO Auto-generated method stub
                                if (cs.equals("")) { // for backspace
                                    return cs;
                                }
                                if (cs.toString().matches("[.0-9]+")) {
                                    return cs;
                                }
                                return "";
                            }
                        }
                });
            } else if (datatype.toUpperCase().equals("VARCHAR")) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setFilters(new InputFilter[]{
                        new InputFilter() {
                            @Override
                            public CharSequence filter(CharSequence cs, int start,
                                                       int end, Spanned spanned, int dStart, int dEnd) {
                                // TODO Auto-generated method stub
                                if (cs.equals("")) { // for backspace
                                    return cs;
                                }
                                if (cs.toString().matches("[a-zA-Z 0-9]+")) {
                                    return cs;
                                }
                                return "";
                            }
                        }
                });
            }

            layout_otherinfo.addView(editText);
            inputslist.add(editText);

            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                editText.setVisibility(View.VISIBLE);
            } else {
                editText.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CREATES THE SPINNER (XML DETAILS)
    private void createSpinner(String name, String inputtype, String mandatory) {
        //2. Create spinner
        try {

            ArrayList<String> spinnerArray = new ArrayList<String>();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                    .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            JSONObject spinnertag = new JSONObject();
            if (name.contains("*")) {
                name = name.replace("*", "");
            }
            spinnerArray.add("Select " + name);

            String[] selectoption = inputtype.split(":");
            if (selectoption.length > 0) {
                for (int x = 1; x < selectoption.length; x++) {

                    String[] value = selectoption[x].split("-");
                    String desc = value[1];
                    String id = value[0];

                    spinnerArray.add(desc);
                    spinnerMap.put(x, id);
                }
            }

            final LinearLayout spinnercontainer = new LinearLayout(this);
            spinnercontainer.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
            final Spinner spinner = new Spinner(this);


            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                    R.layout.activity_custom_spinner, spinnerArray) {

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {

                    View view = super.getDropDownView(position, convertView, parent);
                    if (position == 0) {
                        ((TextView) view.findViewById(R.id.customSpinner)).setTextColor(getResources().getColor(R.color.colorsilver));
                        ((TextView) view.findViewById(R.id.customSpinner)).setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                    } else {
                        ((TextView) view.findViewById(R.id.customSpinner)).setTextColor(getResources().getColor(R.color.colorTextGrey));
                        ((TextView) view.findViewById(R.id.customSpinner)).setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                    }
                    return view;
                }
            };

            spinnerArrayAdapter.setDropDownViewResource(R.layout.activity_custom_spinner);

            spinner.setAdapter(spinnerArrayAdapter);
            spinner.setSelection(0);

            spinnertag.put("name", name);
            spinnertag.put("mandatory", mandatory);
            spinner.setTag(spinnertag);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {
                        if (position > 0) {
                            String selectedoptn = spinner.getSelectedItem().toString();
                            String checkid = spinnerMap.get(spinner.getSelectedItemPosition() + 1);

                            optionchoice = checkid;

                            for (EditText inputdata : inputslist) {
                                String f = inputdata.getTag().toString();
                                JSONObject jresponse = new JSONObject(f);
                                String md = jresponse.getString("mandatory");

                                if (md.equals("YES") || md.equals("NO") || md.equals(optionchoice)) {
                                    inputdata.setVisibility(View.VISIBLE);

                                } else {
                                    inputdata.setVisibility(View.GONE);
                                    inputdata.setText("");
                                }
                            }

                            for (TextView tv : textviewlist) {
                                String tg = tv.getTag().toString();
                                JSONObject jresponse = new JSONObject(tg);
                                String md = jresponse.getString("mandatory");

                                if (md.equals("YES") || md.equals("NO") || md.equals(optionchoice)) {
                                    tv.setVisibility(View.VISIBLE);
                                } else {
                                    tv.setVisibility(View.GONE);
                                }

                            }

                            for (TextView b : txtbuttonlist) {
                                String tg = b.getTag().toString();
                                JSONObject jresponse = new JSONObject(tg);
                                String md = jresponse.getString("mandatory");

                                if (md.equals("YES") || md.equals("NO") || md.equals(optionchoice)) {
                                    b.setVisibility(View.VISIBLE);
                                } else {
                                    b.setVisibility(View.GONE);
                                    b.setText("");
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }

            });

            spinnerlist.add(spinner);

            spinnercontainer.addView(spinner, layoutParams);
            spinnerlistcontainer.add(spinnercontainer);

            layout_otherinfo.addView(spinnercontainer, layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CREATES THE BUTTON (XML DETAILS)
    private void createTxtButton(String name, String mandatory) {
        try {
            JSONObject txtbtntag = new JSONObject();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                    .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            final LinearLayout txtbuttoncontainer = new LinearLayout(this);
            txtbuttoncontainer.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
            TextView txtbtn = new TextView(this);

            int paddingleft = 10;
            int paddingtop = 10;
            int paddingright = 10;
            int paddingbottom = 10;

            paddingleft = converttoDP(paddingleft);
            paddingtop = converttoDP(paddingtop);
            paddingright = converttoDP(paddingright);
            paddingbottom = converttoDP(paddingbottom);

            txtbtn.setPadding(paddingleft, paddingtop, paddingright, paddingbottom);
            txtbtn.setFocusable(false);
            txtbtntag.put("name", name);
            txtbtntag.put("mandatory", mandatory);
            txtbtn.setTag(txtbtntag);
            txtbtn.setGravity(Gravity.CENTER_VERTICAL);

            txtbtn.setTextSize(14);
            txtbtn.setTextColor(getResources().getColor(R.color.colorTextGrey));
            txtbtn.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            txtbuttoncontainer.addView(txtbtn, layoutParams);

            layout_otherinfo.addView(txtbuttoncontainer);

            txtbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag = v.getTag().toString();
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(GKStoreDetailsActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.show(getFragmentManager(), tag);
                }
            });

            //add to the list of button for me to find it, once i need to set value on it.
            txtbuttonlist.add(txtbtn);

            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                txtbtn.setVisibility(View.VISIBLE);
            } else {
                txtbtn.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //CREATES THE ERROR TEXTVIEW (XML DETAILS)
    private void createErrorTextView(String name, String description, String mandatory) {

        try {
            JSONObject txtviewtag = new JSONObject();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            int marginleft = 0;
            int margintop = 10;
            int marginright = 0;
            int marginbottom = 0;

            marginleft = converttoDP(marginleft);
            margintop = converttoDP(margintop);
            marginright = converttoDP(marginright);
            marginbottom = converttoDP(marginbottom);

            layoutParams.setMargins(marginleft, margintop, marginright, marginbottom);

            if (storeid.equals("PHILCARE")) {
                if (name.equals("MiddleName*")) {
                    gkstoremiddlename.addTextChangedListener(new TextWatcher(gkstoremiddlename, middlenameErrorMessage));
                } else {
                    displayErrorTextView(name, description, mandatory, txtviewtag, layoutParams);
                }
            } else {
                displayErrorTextView(name, description, mandatory, txtviewtag, layoutParams);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DISPLAY THE ERRORTEXTVIEW (XML DETAILS)
    private void displayErrorTextView(String name, String description, String mandatory,
                                      JSONObject txtviewtag, LinearLayout.LayoutParams layoutParams) {
        try {
            TextView txtv = new TextView(this);
            String color = "#FF0000";
            txtv.setTextColor(Color.parseColor(color));
            if (name.contains("*")) {
                name = name.replace("*", "");
            }
            txtv.setText("Invalid " + name);
            txtv.setTextSize(14);
            txtv.setPadding(10, 10, 10, 10);
            txtv.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            txtviewtag.put("name", name);
            txtviewtag.put("mandatory", mandatory);
            txtv.setTag(txtviewtag);
            errortextviewlist.add(txtv);
            txtv.setVisibility(View.GONE);
            layout_otherinfo.addView(txtv, layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //REMARKS FOR STORE (PROGRAMATICALLY ADDED)
    private void createRemarks() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("name", "remarks");
        obj.put("description", "description");
        remarksjsonarr.put(obj);

        //Adding Remarks
        createRemarksTextView();
        createRemarksEditText();
    }

    //CREATES THE TEXTVIEW (REMARKS)
    private void createRemarksTextView() {

        try {
            JSONObject txtviewtag = new JSONObject();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            int marginleft = 0;
            int margintop = 10;
            int marginright = 0;
            int marginbottom = 0;

            marginleft = converttoDP(marginleft);
            margintop = converttoDP(margintop);
            marginright = converttoDP(marginright);
            marginbottom = converttoDP(marginbottom);

            layoutParams.setMargins(marginleft, margintop, marginright, marginbottom);
            TextView txtv = new TextView(this);
            String silver = "#C0C0C0";
            txtv.setTextColor(Color.parseColor(silver));
            txtv.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            txtv.setText("Special Instructions");
            txtv.setTextSize(14);

            txtv.setPadding(10, 10, 10, 10);
            txtviewtag.put("name", "remarks");
            txtv.setTag(txtviewtag);
            textviewlist.add(txtv);
            layout_otherinfo.addView(txtv, layoutParams);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CREATES THE EDITTEXT (REMARKS)
    private void createRemarksEditText() {
        try {
            JSONObject edittexttag = new JSONObject();
            EditText editText = new EditText(this);
            editText.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));

            int paddingall = 10;
            paddingall = converttoDP(paddingall);

            editText.setPadding(paddingall, paddingall, paddingall, paddingall);
            String gray = "#49494A";
            editText.setTextColor(Color.parseColor(gray));

            editText.setTextSize(14);
            editText.setLines(8);
            editText.setMaxLines(10);
            editText.setMinLines(6);
            editText.setVerticalScrollBarEnabled(true);
            editText.setGravity(Gravity.TOP | Gravity.LEFT);
            edittexttag.put("name", "remarks");
            editText.setTag(edittexttag);
            editText.setBackgroundResource(R.drawable.border);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            editText.setFilters(new InputFilter[]{
                    new InputFilter() {
                        @Override
                        public CharSequence filter(CharSequence cs, int start,
                                                   int end, Spanned spanned, int dStart, int dEnd) {
                            // TODO Auto-generated method stub
                            if (cs.equals("")) { // for backspace
                                return cs;
                            }
                            if (cs.toString().matches("[a-zA-Z 0-9 , .]+")) {
                                return cs;
                            }
                            return "";
                        }
                    }
            });

            layout_otherinfo.addView(editText);
            remarkslist.add(editText);

            // Add TextView for Spacing on the bottom
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 5);
            TextView textview = new TextView(this);
            layout_otherinfo.addView(textview, layoutParams);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CONVERT THE JSON INTO XML (REMARKS)
    private void convertRemarkJSONtoXML() {
        //Converts the Json Array to JSONOBJECT
        JSONObject jsonObject = null;
        for (int i = 0, count = passedjsonarr.length(); i < count; i++) {
            try {
                jsonObject = passedjsonarr.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Converts the JSONOBject to XML FORMAT
        JsonToXml jsonToXml = new JsonToXml.Builder(jsonObject).build();

        // Converts to a simple XML String
        String formattedXml = jsonToXml.toString();

//        // Converts to a formatted XML String
//        String formattedXml = "";
//        int indentationSize = 1;
//        formattedXml = jsonToXml.toFormattedString(indentationSize);

        passedXMLremarks = formattedXml.substring(formattedXml.indexOf("?>") + 2);
    }

    //CONVERTING VALUES INTO DP (XML DETAILS)
    private int converttoDP(int converttodp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (converttodp * scale + 0.5f);
    }

    //ADD LISTENER TO OTHER DETAILS/PASSED XML
    private void otherDetailsAddTextWatcher() throws JSONException {
        //OTHER DETAILS
        for (int i = 0; i < params.length(); i++) {
            JSONObject c = params.getJSONObject(i);
            String inputname = c.getString("name");
            String ismandatory = c.getString("mandatory");
            String description = c.getString("description");
            String datatype = c.getString("datatype");

            //Check Stuff
            ismandatory = ismandatory.toUpperCase();

            //get the data in the input text
            for (EditText inputdata : inputslist) {
                String f = inputdata.getTag().toString();
                JSONObject jresponse = new JSONObject(f);
                String name = jresponse.getString("name");

                if (name.contains("*")) {
                    name = name.replace("*", "");
                }

                if (name.equals(inputname)) {
                    String inputres = inputdata.getText().toString();

                    //if mandatory, need not empty
                    if (!ismandatory.equals("NO") && inputdata.getVisibility() == View.VISIBLE) {
                        if (inputres.trim().length() == 0) {
                            for (TextView errordata : errortextviewlist) {
                                String errorgetTag = errordata.getTag().toString();
                                JSONObject errorjresponse = new JSONObject(errorgetTag);
                                String errorname = errorjresponse.getString("name");
                                if (errorname.equals(name)) {
                                    if (storeid.equals("PHILCARE")) {
                                        if (name.equals("MiddleName*")) {
                                            gkstoremiddlename.addTextChangedListener(new TextWatcher(gkstoremiddlename, middlenameErrorMessage));
                                        } else {
                                            inputdata.addTextChangedListener(new TextWatcher(inputdata, errordata));
                                        }
                                    } else {
                                        inputdata.addTextChangedListener(new TextWatcher(inputdata, errordata));
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //RESETS THE OTHER DETAILS STATE (THE BORDER RED)
    private void resetOtherDetailsInfo() {
        //For EditText
        for (EditText inputdata : inputslist) {
            for (TextView errordata : errortextviewlist) {
                errordata.setVisibility(View.GONE);
                inputdata.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
            }
        }
        //For Butons
        for (TextView b : txtbuttonlist) {
            for (TextView errordata : errortextviewlist) {
                errordata.setVisibility(View.GONE);
                b.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
            }
        }
    }

    //CALLS DURING UPDATE OF ORDERS DURING ADDING AND REMOVING OF ITEMS.
    public void orderDetails(List<GKStoreProducts> orderdata) {

        List<GKStoreProducts> gkstoreproductscompare = new ArrayList<>();

        gkstoreproductscompare = mdb.getGKStoreProductKeyword(mdb, merchantid, "");
        totalorderamount = 0.00;


        for (GKStoreProducts gkStorePassed : orderdata) {
            String productidpassed = gkStorePassed.getProductID();
            for (GKStoreProducts gkStoreProducts : gkstoreproductscompare) {
                String productid = gkStoreProducts.getProductID();
                if (productidpassed.equals(productid)) {
                    totalorderamount += (gkStorePassed.getQuantity() * gkStoreProducts.getActualPrice());
                }
            }
        }

        //Limits to two decimal places
        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(totalorderamount));
        totalorderamount = Double.parseDouble(valuecheck);

        Collections.reverse(orderdata);
        orderdetailslist = orderdata;
    }

    //CHECKS THE SCROLLVIEW POSItiON AND CREATES A DELAY BEFORE THE USER CAN SCROLL AGAIN.
    public void scrolltoPositionAdapter(final int position, final LinearLayout itemgkstore, final boolean checkstatus) {
        final CustomNestedScrollView main = findViewById(R.id.scrollmaincontainer);
        if (GKStoreAdapterRV != null) {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!checkstatus) {
                        new CountDownTimer(500, 20) {
                            public void onTick(long millisUntilFinished) {
                                main.smoothScrollTo((int) (500 - millisUntilFinished), itemgkstore.getTop());
                            }

                            public void onFinish() {
                                main.setEnableScrolling(true);
                            }
                        }.start();
                    } else {
                        new CountDownTimer(500, 20) {
                            public void onTick(long millisUntilFinished) {
                                main.smoothScrollTo((int) (500 - millisUntilFinished), itemgkstore.getTop());
                            }

                            public void onFinish() {
                                main.setEnableScrolling(true);
                            }
                        }.start();
                    }
                }
            }, 0);
        }
    }

    //HIDES THE KEYBOARD WHEN USER SCROLLS ON THE LIST
    private void hideKeyboardwhileScrolling() {

        GKStoreAdapterRV.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }

        });
    }

    //DISABLE/HIDE THE KEYBOARD ON START (IF ACTIVITY IS FIRST OPENED)
    private void hideKeyboardonStart() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    //HIDES THE KEYBOARD WHEN DISPLAYED
    private void hideKeyboard(GKStoreDetailsActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(GKStoreDetailsActivity.INPUT_METHOD_SERVICE);
        View focusView = activity.getCurrentFocus();
        if (focusView != null) {
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    //SCROLLS ON TOP
    public void scrollonTop() {
//        final NestedScrollView main = (NestedScrollView) findViewById(R.id.scrollmaincontainer);

        final CustomNestedScrollView main = findViewById(R.id.scrollmaincontainer);
        main.post(new Runnable() {
                      public void run() {
                          main.smoothScrollTo(0, 0);
                      }
                  }
        );
    }

    //IF INTERNET HAS NO CONNECTION
    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    //GETS THE LONG LAT OF BOTH USER AND DISCOUNT
    private void checkGPSATStart() {
        Double currentlat = null;
        Double currentlon = null;

        tracker = new GPSTracker(getViewContext());

        if (!tracker.canGetLocation()) {
            //gpsNotEnabledDialog();
            gpsNotEnabledDialogNew();
        } else {
            currentlat = tracker.getLatitude();
            currentlon = tracker.getLongitude();
            setDeliveryAddresstoEditableORNot(false);
        }
        //USER LAT AND LONG
        latitude = String.valueOf(currentlat);
        longitude = String.valueOf(currentlon);

        //DISCOUNT LAT AND LONG
        discountlatitude = String.valueOf(currentlat);
        discountlongitude = String.valueOf(currentlon);
    }

    //GETS THE LONG LAT OF USER IF EMPTY.
    private void checkGPSforLatLngIfEmpty() {
        Double currentlat = null;
        Double currentlon = null;

        tracker = new GPSTracker(getViewContext());

        if (!tracker.canGetLocation()) {
//            gpsNotEnabledDialog();
            currentlat = 0.0;
            currentlon = 0.0;
        } else {
            currentlat = tracker.getLatitude();
            currentlon = tracker.getLongitude();
        }

        if (latitude.equals("0.0") || longitude.equals("0.0") ||
                latitude.equals("null") || longitude.equals("null")) {
            //USER LAT AND LONG
            latitude = String.valueOf(currentlat);
            longitude = String.valueOf(currentlon);
        }
    }

    //GETS THE LONG LAT FOR DISCOUNT.
    private void checkGPSforDiscount() {
        Double currentlat = null;
        Double currentlon = null;

        tracker = new GPSTracker(getViewContext());

        if (!tracker.canGetLocation()) {
//            gpsNotEnabledDialog();
            currentlat = 0.0;
            currentlon = 0.0;
        } else {
            currentlat = tracker.getLatitude();
            currentlon = tracker.getLongitude();
        }
        //DISCOUNT LAT AND LONG
        discountlatitude = String.valueOf(currentlat);
        discountlongitude = String.valueOf(currentlon);
    }

    //GETS THE LAST KNOW LOCATION OF THE USER WHEN ITS 0 OR EMPTY
    private void getLastKnownLocationIfEmpty() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getViewContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            if (latitude.equals("0.0") || longitude.equals("0.0") ||
                    latitude.equals("null") || longitude.equals("null")) {
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    latitude = String.valueOf(location.getLongitude());
                                    longitude = String.valueOf(location.getLatitude());
                                }
                            }

                        });
            }
        }
    }

    //GETS THE LAST KNOW LOCATION OF THE DISCOUNT
    private void getLastKnownDiscountLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getViewContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                discountlatitude = String.valueOf(location.getLongitude());
                                discountlongitude = String.valueOf(location.getLatitude());
                            }
                        }

                    });
        }
    }

//    //CHECK IF STATUS OF BOTH LATLNGS IF THERE 0 or NULL.
//    private void checkStatusofLatLngsBeforeProceeding() {
//        if (latitude.equals("0.0") || longitude.equals("0.0") ||
//                latitude.equals("null") || longitude.equals("null") ||
//                discountlatitude.equals("0.0") || discountlongitude.equals("0.0") ||
//                discountlatitude.equals("null") || discountlongitude.equals("null")
//        ) {
//            mLlLoader.setVisibility(View.GONE);
//            swipeRefreshLayout.setRefreshing(false);
//            showWarningGlobalDialogs("GPS service was not fully loaded yet. Please try again in a few seconds.");
//            getLastKnownLocationIfEmpty();
//            getLastKnownDiscountLocation();
//        } else {
//            checkGPSforDiscount();
//            //calculateDiscountForReseller(calculateDiscountForResellerCallBack);
//            calculateDiscountForResellerV2();
//        }
//    }

    //CHECKS IF GPS IS ENABLED OR NOT
    private void gpsNotEnabledDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .content("GPS is disabled in your device.\nWould you like to enable it?")
                .cancelable(false)
                .positiveText("Go to Settings")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        setDeliveryAddresstoEditableORNot(false);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        setDeliveryAddresstoEditableORNot(true);
                    }
                })
                .show();
    }

    //CHECKS IF GPS IS ENABLED OR NOT (NEW)
    private void gpsNotEnabledDialogNew() {
        GlobalDialogs dialogs = new GlobalDialogs(getViewContext());

        dialogs.createDialog("", "GPS is disabled in your device." +
                        "\nWould you like to enable it?",
                "Cancel", "Go to Settings", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        View closebtn = dialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                setDeliveryAddresstoEditableORNot(true);
            }
        });

        View btndoubleone = dialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                setDeliveryAddresstoEditableORNot(true);
            }
        });

        View btndoubletwo = dialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                setDeliveryAddresstoEditableORNot(false);
            }
        });
    }

    private void gpsNotEnabledMapsDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .content("GPS is disabled in your device.\nWould you like to enable it?")
                .cancelable(false)
                .positiveText("Go to Settings")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        setDeliveryAddresstoEditableORNot(false);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        showToast("GPS must be enabled to avail the service.", GlobalToastEnum.WARNING);
                        setDeliveryAddresstoEditableORNot(true);
                    }
                })
                .show();
    }

    //CLOSES THE ACTIVITY
    private void closetheActivityDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .content("You are about to leave the store.\nAre you sure you want to exit?")
                .cancelable(false)
                .positiveText("OK")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        mDialog.dismiss();
                        mDialog = null;
                    }
                })
                .show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mDialog.getView());
    }

    //CLOSES THE ACTIVITY (NEW)
    private void closetheActivityDialogNew() {
        GlobalDialogs dialogs = new GlobalDialogs(getViewContext());

        dialogs.createDialog("Notice", "You are about to leave the store.\nAre you sure you want to exit?",
                "CANCEL", "OK", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        View closebtn = dialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
            }
        });

        View btndoubleone = dialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
            }
        });

        View btndoubletwo = dialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                finish();
            }
        });
    }

    //CHECKS WHETHER THE LOCATION ON THE EDITEXT EXIST OR NOT.
    private void checkGKStoreLocationFound() {
        Geocoder geocoder = new Geocoder(getViewContext(), Locale.getDefault());

        EditText gkstoredeliveryaddress = findViewById(R.id.gkstoredeliveryaddress);
        String checkadd = gkstoredeliveryaddress.getText().toString();

        if (!checkadd.trim().equals("")) {
            List<Address> addresses = new ArrayList<>();
            try {
                //Calculates the LatLng from the passed String.
                addresses = geocoder.getFromLocationName(checkadd, 1);

                if (addresses.size() > 0) {
                    double getlattext = addresses.get(0).getLatitude();
                    double getlongtext = addresses.get(0).getLongitude();

                    latitude = String.valueOf(getlattext);
                    longitude = String.valueOf(getlongtext);

                    latitude = longlatLimiter(latitude);
                    longitude = longlatLimiter(longitude);

                    checklocationiffound = 1;
                } else {
                    checklocationiffound = 0;
                }
                //If the edited text is equal to the mapsaddress, Passed the mapsAddress LatLng
                //This is for when user does not change the Delivery Address.
                String checkmapsadd = getMarkerAddress(mapsAddress);
                if (!checkmapsadd.equals("")) {
                    if (checkadd.equals(checkmapsadd)) {
                        double checklat = mapsAddress.getLatitude();
                        double checklong = mapsAddress.getLongitude();

                        latitude = String.valueOf(checklat);
                        longitude = String.valueOf(checklong);

                        latitude = longlatLimiter(latitude);
                        longitude = longlatLimiter(longitude);

                        checklocationiffound = 1;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            tracker = new GPSTracker(getViewContext());
            //Gets the current user location if the text is empty at start;
            Double currentlat = tracker.getLatitude();
            Double currentlon = tracker.getLongitude();

            latitude = String.valueOf(currentlat);
            longitude = String.valueOf(currentlon);

            checklocationiffound = 1;
        }
    }

    //CHECKS IF THE STORE IS OFFLINE OR ONLINE
    public boolean checkGKStoreStatus() {
        String strcheckgkstorestatus = "";
        try {
            if (gkservice.getGKStoreStatus() == null) {
                strcheckgkstorestatus = "ONLINE";
            } else {
                strcheckgkstorestatus = gkservice.getGKStoreStatus();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }

        if (strcheckgkstorestatus.equals("OFFLINE")) {
            checkgkstorestatus = true;
            proceedcontainer.setVisibility(View.GONE);
            gkstoreoffline.setVisibility(View.VISIBLE);
        } else {
            checkgkstorestatus = false;
            proceedcontainer.setVisibility(View.VISIBLE);
            gkstoreoffline.setVisibility(View.GONE);
        }
        return checkgkstorestatus;
    }

    //OPENS A DIALOG WHEN A PRODUCT HASN'T BEEN SELECTED.
    private void selectAProductDialog() {
        GlobalDialogs dialogs = new GlobalDialogs(getViewContext());

        dialogs.createDialog("Notice", "Please select a product.",
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.NOTICE);
        dialogs.hideCloseImageButton();
        dialogs.defaultDialogActions();
    }

    private void gkPhoneStoreSingleSelectionDialog() {
        GlobalDialogs dialogs = new GlobalDialogs(getViewContext());

        dialogs.createDialog("Notice", "Sorry only 1 item can be ordered at a time. " +
                        "Please update your selection.",
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.WARNING);
        dialogs.hideCloseImageButton();
        dialogs.defaultDialogActions();
    }

    //OPENS A DIALOG IF PICKUP OR DELIVERY
    private void openPickDeliveryOptionDialog() {

        if (storeid.equals("PHILCARE")) {
            storePhilCare();
        } else {
            if (strmercordertype.equals("DELIVERY:::PICKUP")) {
                createBothPickDelivorNoneDialog();
            } else if (strmercordertype.equals("PICKUP")) {
                createPickUpDialog();
            } else if (strmercordertype.equals("DELIVERY")) {
                createDeliveryDialog();
            } else {
                createBothPickDelivorNoneDialog();
            }
        }
    }

    //OPENS A DIALOG IF PICKUP OR DELIVERY (NEW)
    private void openPickDeliveryOptionDialogNew() {

        if (storeid.equals("PHILCARE")) {
            storePhilCare();
        } else {
            createLocalDialog();
        }
    }

    //CREATES A DIALOG FOR PICKUP AND DELIVERY
    private void createBothPickDelivorNoneDialog() {
        materialDialog = new MaterialDialog.Builder(getViewContext())
                .title("Choose your option")
                .customView(R.layout.dialog_pickupordelivery, false)
                .show();
        View view = materialDialog.getCustomView();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, view);

        if (view != null) {
            RadioButton pickup = view.findViewById(R.id.rd_pickup);
            pickup.setTag(String.valueOf(pickup.getText()));
            RadioButton delivery = view.findViewById(R.id.rd_delivery);
            delivery.setTag(String.valueOf(delivery.getText()));
            pickup.setOnClickListener(pickupordeliverlistener);
            delivery.setOnClickListener(pickupordeliverlistener);
        }
    }

    //CREATES A DIALOG FOR PICKUP AND DELIVERY
//    private void createBothPickDelivorNoneDialogNew() {
//        deliverOptionsDialogs = new GlobalDialogs(getViewContext());
//
//
//        deliverOptionsDialogs.createDialog("Notice", "", "Close",
//                "", ButtonTypeEnum.SINGLE,
//                false, false, DialogTypeEnum.CUSTOMCONTENT);
//
//        deliverOptionsDialogs.hideCloseImageButton();
//
//        List<String> textViewcontent = new ArrayList<>();
//        textViewcontent.add("Please select how you want to receive your order: ");
//
//        LinearLayout linearLayout = deliverOptionsDialogs.setContentCustomMultiTextView(textViewcontent, LinearLayout.VERTICAL,
//                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.START));
//
//
//        linearLayout.setPadding(0, 15, 0, 15);
//
//        List<String> radiocontent = new ArrayList<>();
//        radiocontent.add("Pick Up");
//        radiocontent.add("For Delivery");
//
//        LinearLayout radGroupContainer = deliverOptionsDialogs.setContentCustomMultiRadio(radiocontent, RadioGroup.VERTICAL,
//                new GlobalDialogsObject(R.color.colorTextGrey, 16, 0));
//
//        RadioGroup radGroup = new RadioGroup(getViewContext());
//        int radGroupCount = radGroupContainer.getChildCount();
//        for (int i = 0; i < radGroupCount; i++) {
//            View view = radGroupContainer.getChildAt(i);
//            if (view instanceof RadioGroup) {
//                radGroup = (RadioGroup) view;
//            }
//        }
//
//
//        int count = radGroup.getChildCount();
//        for (int i = 0; i < count; i++) {
//            View radBtnView = radGroup.getChildAt(i);
//            radBtnView.setOnClickListener(pickupORdeliveryRGListener);
//        }
//
//
//
//        View closebtn = deliverOptionsDialogs.btnCloseImage();
//        closebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deliverOptionsDialogs.dismiss();
//            }
//        });
//
//        View singlebtn = deliverOptionsDialogs.btnSingle();
//        singlebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deliverOptionsDialogs.dismiss();
//            }
//        });
//
//    }


    //CREATES A DIALOG FOR PICKUP
    private void createPickUpDialog() {
        materialDialog = new MaterialDialog.Builder(getViewContext())
                .title("Choose your option")
                .customView(R.layout.dialog_pickupordelivery, false)
                .show();
        View view = materialDialog.getCustomView();
        materialDialog.getTitleView().setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));

        if (view != null) {
            RadioButton pickup = view.findViewById(R.id.rd_pickup);
            pickup.setTag(String.valueOf(pickup.getText()));
            RadioButton delivery = view.findViewById(R.id.rd_delivery);
            delivery.setTag(String.valueOf(delivery.getText()));
            delivery.setVisibility(View.GONE);
            pickup.setOnClickListener(pickupordeliverlistener);
        }
    }

    //CREATES A DIALOG FOR PICKUP (NEW)
//    private void createPickUpDialogNew() {
//        deliverOptionsDialogs = new GlobalDialogs(getViewContext());
//
//        deliverOptionsDialogs.createDialog("Notice", "", "Close",
//                "", ButtonTypeEnum.SINGLE,
//                false, false, DialogTypeEnum.CUSTOMCONTENT);
//
//        deliverOptionsDialogs.hideCloseImageButton();
//
//        List<String> textViewcontent = new ArrayList<>();
//        textViewcontent.add("Please select how you want to receive your order: ");
//
//        LinearLayout linearLayout = deliverOptionsDialogs.setContentCustomMultiTextView(textViewcontent, LinearLayout.VERTICAL,
//                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.START));
//
//        linearLayout.setPadding(0, 15, 0, 15);
//
//        List<String> radiocontent = new ArrayList<>();
//        radiocontent.add("Pick Up");
//
//
//        LinearLayout radGroupContainer = deliverOptionsDialogs.setContentCustomMultiRadio(radiocontent, RadioGroup.VERTICAL,
//                new GlobalDialogsObject(R.color.colorTextGrey, 16, 0));
//
//        RadioGroup radGroup = new RadioGroup(getViewContext());
//
//        int radGroupCount = radGroupContainer.getChildCount();
//        for (int i = 0; i < radGroupCount; i++) {
//            View view = radGroupContainer.getChildAt(i);
//            if (view instanceof RadioGroup) {
//                radGroup = (RadioGroup) view;
//            }
//        }
//
//        int count = radGroup.getChildCount();
//        for (int i = 0; i < count; i++) {
//            View radBtnView = radGroup.getChildAt(i);
//            radBtnView.setOnClickListener(pickupORdeliveryRGListener);
//        }
//
//        View closebtn = deliverOptionsDialogs.btnCloseImage();
//        closebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deliverOptionsDialogs.dismiss();
//            }
//        });
//
//        View singlebtn = deliverOptionsDialogs.btnSingle();
//        singlebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deliverOptionsDialogs.dismiss();
//            }
//        });
//    }

    //CREATES A DIALOG FOR DELIVERY
    private void createDeliveryDialog() {
        materialDialog = new MaterialDialog.Builder(getViewContext())
                .title("Choose your option")
                .customView(R.layout.dialog_pickupordelivery, false)
                .show();
        View view = materialDialog.getCustomView();

        materialDialog.getTitleView().setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
        if (view != null) {
            RadioButton pickup = view.findViewById(R.id.rd_pickup);
            pickup.setTag(String.valueOf(pickup.getText()));
            pickup.setVisibility(View.GONE);
            RadioButton delivery = view.findViewById(R.id.rd_delivery);
            delivery.setTag(String.valueOf(delivery.getText()));
            delivery.setOnClickListener(pickupordeliverlistener);
        }
    }

    //CREATES A DIALOG FOR DELIVERY (NEW)
//    private void createDeliveryDialogNew() {
//        deliverOptionsDialogs = new GlobalDialogs(getViewContext());
//
//        deliverOptionsDialogs.createDialog("Notice", "", "Close",
//                "", ButtonTypeEnum.SINGLE,
//                false, false, DialogTypeEnum.CUSTOMCONTENT);
//
//        deliverOptionsDialogs.hideCloseImageButton();
//
//        List<String> textViewcontent = new ArrayList<>();
//        textViewcontent.add("Please select how you want to receive your order: ");
//
//        LinearLayout linearLayout = deliverOptionsDialogs.setContentCustomMultiTextView(textViewcontent, LinearLayout.VERTICAL,
//                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.START));
//
//        linearLayout.setPadding(0, 15, 0, 15);
//
//        List<String> radiocontent = new ArrayList<>();
//        radiocontent.add("For Delivery");
//
//        LinearLayout radGroupContainer = deliverOptionsDialogs.setContentCustomMultiRadio(radiocontent, RadioGroup.VERTICAL,
//                new GlobalDialogsObject(R.color.colorTextGrey, 16, 0));
//
//        RadioGroup radGroup = new RadioGroup(getViewContext());
//
//        int radGroupCount = radGroupContainer.getChildCount();
//        for (int i = 0; i < radGroupCount; i++) {
//            View view = radGroupContainer.getChildAt(i);
//            if (view instanceof RadioGroup) {
//                radGroup = (RadioGroup) view;
//            }
//        }
//
//        int count = radGroup.getChildCount();
//        for (int i = 0; i < count; i++) {
//            View radBtnView = radGroup.getChildAt(i);
//            radBtnView.setOnClickListener(pickupORdeliveryRGListener);
//        }
//
//        View closebtn = deliverOptionsDialogs.btnCloseImage();
//        closebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deliverOptionsDialogs.dismiss();
//            }
//        });
//
//        View singlebtn = deliverOptionsDialogs.btnSingle();
//        singlebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deliverOptionsDialogs.dismiss();
//            }
//        });
//    }

    //HIDE DELIVERY OPTIONS
    private void hideDeliverOptionsDialog() {
        if (deliverOptionsDialogs != null) {
            if (!isFinishing()) {
                deliverOptionsDialogs.dismiss();
            }
        }
    }

    //WHEN OPTION PICKUP IS SELECTED IN THE RADIO DIALOG
    private void orderIsPickUp() {
        gkstoreadaptercontainer.setVisibility(View.GONE);
        gkstoreheaderlayout.setVisibility(View.GONE);
        adapterbackgroundcontainer.setVisibility(View.GONE);
        gkstorepersonalinfo.setVisibility(View.VISIBLE);

        gkstoreordersummaryRV = findViewById(R.id.gkstoreordersummary);
        gkstoreordersummaryRV.setLayoutManager(new LinearLayoutManager(getViewContext()));
        gkstoreordersummaryRV.setNestedScrollingEnabled(false);
        gkstoresummaryadapter = new GKStoreSummaryAdapter(getViewContext(), orderdetailslist);
        gkstoreordersummaryRV.setAdapter(gkstoresummaryadapter);
        gkstoresummaryadapter.updateSummary(orderdetailslist);

        gkstorefirstname.requestFocus();
        hideKeyboard(GKStoreDetailsActivity.this);
        scrollonTop();
        gkstorefirstname.clearFocus();

        //VIEWS VISIBILITY
        gkstoredeliverytypecontainer.setVisibility(View.VISIBLE);
        gkstoredeliveryaddresscont.setVisibility(View.GONE);

        //DELIVERYTYPE AND SERVICE CHARGE
        gkstoredeliverytype.setText("PICKUP");
        strgkstoredeliverytype = gkstoredeliverytype.getText().toString();
        txtDeliveryCharge.setText("0.00");
        deliverychargecontainer.setVisibility(View.GONE);
        isServiceCharge = false;

        //TOTAL AMOUNT
        grossamount = 0.00;
        grossamount = totalorderamount;
        txttotalamount.setText("₱".concat(CommonFunctions.currencyFormatter(String.valueOf(grossamount))));

        //STORE ADDRESS
        gkstorestoreaddcontainer.setVisibility(View.VISIBLE);
        gkstorestoreadd.setText(CommonFunctions.replaceEscapeData(strmerchantaddress));

        hideDeliverOptionsDialog();
    }

    //WHEN OPTION DELIVERY IS SELECTED IN THE RADIO DIALOG
    private void orderIsDelivery() {
        gkstoreadaptercontainer.setVisibility(View.GONE);
        gkstoreheaderlayout.setVisibility(View.GONE);
        adapterbackgroundcontainer.setVisibility(View.GONE);
        gkstorepersonalinfo.setVisibility(View.VISIBLE);

        gkstoreordersummaryRV = findViewById(R.id.gkstoreordersummary);
        gkstoreordersummaryRV.setLayoutManager(new LinearLayoutManager(getViewContext()));
        gkstoreordersummaryRV.setNestedScrollingEnabled(false);
        gkstoresummaryadapter = new GKStoreSummaryAdapter(getViewContext(), orderdetailslist);
        gkstoreordersummaryRV.setAdapter(gkstoresummaryadapter);
        gkstoresummaryadapter.updateSummary(orderdetailslist);

        gkstorefirstname.requestFocus();
        hideKeyboard(GKStoreDetailsActivity.this);
        scrollonTop();
        gkstorefirstname.clearFocus();

        //DELIVERY
        gkstoredeliverytypecontainer.setVisibility(View.VISIBLE);
        gkstoredeliverytype.setText("FOR DELIVERY");
        gkstoredeliveryaddresscont.setVisibility(View.VISIBLE);

        //STORE ADDRESS
        gkstorestoreaddcontainer.setVisibility(View.GONE);

        strgkstoredeliverytype = gkstoredeliverytype.getText().toString()
                .substring(gkstoredeliverytype.getText().toString().indexOf("DELIVERY"));
        isServiceCharge = true;
        grossamount = 0.00;
        getSession();

        hideDeliverOptionsDialog();

        //CHECKS THE GPS IF ITS NOT ON, USER WILL BE ABLE TO INPUT THEIR ADDRESS MANUALLY
        tracker = new GPSTracker(getViewContext());
        if (!tracker.canGetLocation()) {
            setDeliveryAddresstoEditableORNot(true);
        } else {
            setDeliveryAddresstoEditableORNot(false);
        }
    }

    //DIALOG FOR DISCOUNT
//    private void showDiscountDialog(final double totalamountcheck) {
//
//        TextView popok;
//        TextView popcancel;
//        TextView popamountpaid;
//        TextView popservicecharge;
//        TextView poptotalamount;
//
//        if (discountdialog == null) {
//            discountdialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
//            discountdialog.setCancelable(false);
//            discountdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            discountdialog.setContentView(R.layout.pop_discount_dialog);
//
//            popamountpaid = discountdialog.findViewById(R.id.popamounttopayval);
//            popservicecharge = discountdialog.findViewById(R.id.popservicechargeval);
//            poptotalamount = discountdialog.findViewById(R.id.poptotalval);
//            popok = discountdialog.findViewById(R.id.popok);
//            popcancel = discountdialog.findViewById(R.id.popcancel);
//
//            //set value
//            popamountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(grossamount)));
//            popservicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(discount)));
//            poptotalamount.setText(CommonFunctions.currencyFormatter(String.valueOf(strtotalamount)));
//
//            //click close
//            popcancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    discountdialog.dismiss();
//                    discountdialog = null;
//                }
//
//            });
//
//            popok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (storeid.equals("PHILCARE") ||
//                            forapproval.trim().equals("") ||
//                            forapproval.trim().equals(".") ||
//                            forapproval.trim().equals("") ||
//                            forapproval.trim().equals("DIRECT ORDER")
//                    ) {
//                        proceedtoPayments(totalamountcheck);
//                    } else if (forapproval.equals("APPROVAL ORDER")) {
//                        stepOrderDialog();
//                    }
//                    discountdialog.dismiss();
//                    discountdialog = null;
//                }
//
//            });
//
//            discountdialog.show();
//        }
//    }

    //DIALOG FOR DISCOUNT (NEW)
    private void showDiscountDialogNew(final double totalamountcheck) {

        GlobalDialogs globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("DISCOUNT", "",
                "CANCEL", "CONFIRM", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.DOUBLETEXTVIEW);

        View closebtn = globalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
            }
        });

        List<String> titleList = new ArrayList<>();
        titleList.add("Amount");
        titleList.add("Discount");
        titleList.add("Total");

        List<String> contentList = new ArrayList<>();
        contentList.add(String.valueOf(grossamount));
        contentList.add(String.valueOf(discount));
        contentList.add(strtotalamount);

        LinearLayout linearLayout = globalDialogs.setContentDoubleTextView(
                titleList,
                new GlobalDialogsObject(R.color.color_908F90, 18, Gravity.START),
                contentList,
                new GlobalDialogsObject(R.color.color_23A8F6, 18, Gravity.END)
        );

        View btndoubleone = globalDialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
            }
        });

        View btndoubletwo = globalDialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;

                if (storeid.equals("PHILCARE") || forapproval.trim().equals("") ||
                        forapproval.trim().equals(".") || forapproval.trim().equals("") ||
                        forapproval.trim().equals("DIRECT ORDER") && strpaymenttype.equals("VOUCHER")) {
                    globalDialogs.dismiss();
                    proceedtoPayments(totalamountcheck);
                } else if (forapproval.equals("APPROVAL ORDER")) {
                    globalDialogs.dismiss();
                    stepOrderDialog();
                }else if(forapproval.trim().equals("DIRECT ORDER") && strpaymenttype.equals("CASH")){
                    globalDialogs.dismiss();
                    getSessionOrderLogsQue();
                }else {
                    globalDialogs.dismiss();
                }
            }
        });
    }

    //IF DELIVERY, CHECKS IF THE DELIVERY ADDRESS IS WITHIN THE RADIUS.
    private boolean checkifAddressisWithinRadius() {

        boolean checkifitswithin = false;

        double merchantlat = Double.parseDouble(strmerchantlat);
        double merchantlong = Double.parseDouble(strmerchantlong);
        double radius = Double.parseDouble(strradius);

        latitude = longlatLimiter(latitude);
        longitude = longlatLimiter(longitude);

        double checkpassedlat = Double.parseDouble(latitude);
        double checkpassedlong = Double.parseDouble(longitude);

        double distance = checkdistancebetweentwolocs(checkpassedlat, checkpassedlong, merchantlat, merchantlong);

        checkifitswithin = distance <= radius;

        return checkifitswithin;
    }

    //BUILT IN METHOD (LOCATION)
    private double checkdistancebetweentwolocs(double subslat, double subslong, double merclat, double merclong) {
        double distanceoftwopoints = 0.00;
        try {
            Location locationA = new Location("Point A");
            locationA.setLatitude(subslat);
            locationA.setLongitude(subslong);

            Location locationB = new Location("Point B");
            locationB.setLatitude(merclat);
            locationB.setLongitude(merclong);

            //Passed Km.
            distanceoftwopoints = locationA.distanceTo(locationB) / 1000;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return distanceoftwopoints;
    }

    //GETS THE LATLONG OF THE GKSTORE MERCHANT
    private void getLatLongofMerchantStore() {
        String notes1 = gkstoremerchantspassed.getNotes1();
        Log.d("okhttp","NOTES: "+notes1);
        strmerchantlat = CommonFunctions.parseXML(notes1, "Latitude");
        strmerchantlong = CommonFunctions.parseXML(notes1, "Longitude");
        strradius = CommonFunctions.parseXML(notes1, "Radius");
        strmerchantaddress = CommonFunctions.parseXML(notes1, "gkstoreaddress");
        strmercordertype = CommonFunctions.parseXML(notes1, "ordertype");
        strmerchantpaymenttype = CommonFunctions.parseXML(notes1,"paymenttype");

        //Limits to Seven Digits
        if (!strmerchantlat.equals("") && !strmerchantlat.equals(".") && !strmerchantlat.equals("NONE") &&
                !strmerchantlong.equals("") && !strmerchantlong.equals(".") && !strmerchantlong.equals("NONE")) {
            strmerchantlat = longlatLimiter(strmerchantlat);
            strmerchantlong = longlatLimiter(strmerchantlong);
        }

    }

    //CONVERT THE LATLONG OF MERCHANT/STORE TO ADDRESS;
    private void convertLatLongofMerchant() {
        Geocoder geocoder = new Geocoder(getViewContext(), Locale.getDefault());
        List<Address> address = new ArrayList<>();

        if (strmerchantaddress.equals("") || strmerchantaddress.equals(".") || strmerchantaddress.equals("NONE")) {
            if (!strmerchantlat.equals("") && !strmerchantlat.equals(".") && !strmerchantlat.equals("NONE") &&
                    !strmerchantlong.equals("") && !strmerchantlong.equals(".") && !strmerchantlong.equals("NONE")) {

                double merchantlat = Double.parseDouble(strmerchantlat);
                double merchantlong = Double.parseDouble(strmerchantlong);

                try {
                    String display_address = "";
                    address = geocoder.getFromLocation(merchantlat, merchantlong, 1);
                    display_address = address.get(0).getAddressLine(0);

                    strmerchantaddress = display_address;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //LIMIT ANG LATITUDE AND LONGITUDE
    private static String longlatLimiter(String number) {
        String pattern = "#.#######";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        return decimalFormat.format(Double.parseDouble(number));
    }

    //GETS THE ADDRESS OF THE MARKER
    private String getMarkerAddress(Address address) {
        String display_address = "";
        if (address != null) {

            display_address += address.getAddressLine(0) + ", ";

            for (int i = 1; i < address.getMaxAddressLineIndex(); i++) {
                display_address += address.getAddressLine(i) + ", ";
            }

            display_address = display_address.substring(0, display_address.length() - 2);
        }

        return display_address;
    }

    //IF STORE IS PHILCARE, PROCEED DIRECTLY TO THE NEXT PAGE.
    private void storePhilCare() {
        gkstoreadaptercontainer.setVisibility(View.GONE);
        gkstoreheaderlayout.setVisibility(View.GONE);
        adapterbackgroundcontainer.setVisibility(View.GONE);
        gkstorepersonalinfo.setVisibility(View.VISIBLE);

        gkstoreordersummaryRV = findViewById(R.id.gkstoreordersummary);
        gkstoreordersummaryRV.setLayoutManager(new LinearLayoutManager(getViewContext()));
        gkstoreordersummaryRV.setNestedScrollingEnabled(false);
        gkstoresummaryadapter = new GKStoreSummaryAdapter(getViewContext(), orderdetailslist);
        gkstoreordersummaryRV.setAdapter(gkstoresummaryadapter);
        gkstoresummaryadapter.updateSummary(orderdetailslist);

        gkstorefirstname.requestFocus();
        hideKeyboard(GKStoreDetailsActivity.this);
        scrollonTop();
        gkstorefirstname.clearFocus();

        //VIEWS VISIBILITY
        gkstoredeliverytypecontainer.setVisibility(View.GONE);
        gkstoredeliveryaddresscont.setVisibility(View.GONE);

        //DELIVERYTYPE AND SERVICE CHARGE
//        gkstoredeliverytype.setText("PICKUP");
        strgkstoredeliverytype = "PHILCARE";
//        gkstoredeliverytype.setText("PICKUP");
//        strgkstoredeliverytype = gkstoredeliverytype.getText().toString();
        txtDeliveryCharge.setText("0.00");
        deliverychargecontainer.setVisibility(View.GONE);
        isServiceCharge = false;

        //TOTAL AMOUNT
        grossamount = 0.00;
        grossamount = totalorderamount;
        txttotalamount.setText("₱".concat(CommonFunctions.currencyFormatter(String.valueOf(grossamount))));

        //STORE ADDRESS
        gkstorestoreaddcontainer.setVisibility(View.VISIBLE);
        gkstorestoreadd.setText(CommonFunctions.replaceEscapeData(strmerchantaddress));

        materialDialog.dismiss();
        materialDialog = null;
    }

    //IF STORE IS PHILCARE, DISPLAYS THE MIDDLENAME
    private void checkMiddleName() {
        if (storeid.equals("PHILCARE")) {
            gkstoremiddlenamecontainer.setVisibility(View.VISIBLE);
        } else {
            gkstoremiddlenamecontainer.setVisibility(View.GONE);
        }
    }

    //SEARCH LISTENER FOR GKSTORE.
    private void gkStoreSearch() {
        //CLEARS THE FOCUS ON SEARCH ON START
        edt_search_box.clearFocus();

        edt_search_box.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    gkadapter.clear();
                    productslist.clear();


                    gkadapter.updateData(mdb.getGKStoreProductKeyword(mdb, merchantid, s.toString().trim()));
                    productslist = mdb.getGKStoreProductKeyword(mdb, merchantid, s.toString().trim());
                    edt_search_icon_image.setImageResource(R.drawable.ic_close_grey600_24dp);
                    searchiconselected = 1;

                    if (productslist.isEmpty()) {
                        noresultsfound.setVisibility(View.VISIBLE);
                        adapterbackgroundcontainer.setVisibility(View.GONE);

                    } else {
                        noresultsfound.setVisibility(View.GONE);
                        adapterbackgroundcontainer.setVisibility(View.VISIBLE);

                    }

                    picturecontainer.setVisibility(View.GONE);
                    if (storeid.equals("METROGAS")) {
                        if (isAgent) {
                            fragment_metrogas.setVisibility(View.GONE);
                        } else {
                            btn_view_history_container.setVisibility(View.GONE);
                        }
                    } else {
                        btn_view_history_container.setVisibility(View.GONE);
                    }
                } else {
                    gkadapter.clear();
                    productslist.clear();

                    gkadapter.updateData(mdb.getGKStoreProductKeyword(mdb, merchantid, ""));
                    searchiconselected = 0;
                    noresultsfound.setVisibility(View.GONE);
                    adapterbackgroundcontainer.setVisibility(View.VISIBLE);

                    edt_search_icon_image.setImageResource(R.drawable.ic_search);

                    picturecontainer.setVisibility(View.VISIBLE);

                    if (storeid.equals("METROGAS")) {
                        if (isAgent) {
                            fragment_metrogas.setVisibility(View.VISIBLE);
                        } else {
                            btn_view_history_container.setVisibility(View.VISIBLE);
                        }
                    } else {
                        btn_view_history_container.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //IF BORROWER IS A RESELLER AND NOT IN SERVICE AREA.
//    private void noDiscountResellerServiceArea() {
//        mDialog = new MaterialDialog.Builder(getViewContext())
//                .content(discountmessage)
//                .cancelable(false)
//                .positiveText("OK")
//                .negativeText("Cancel")
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
//                        if (forapproval.trim().equals("") || forapproval.trim().equals(".")) {
//                            //prePurchase(prePurchaseSession);
//                            prePurchaseV3();
//                        } else if (forapproval.equals("APPROVAL ORDER")) {
//                            proceedtoinsertOrderLogsQue();
//                        }else if(forapproval.equals("DIRECT ORDER") && strpaymenttype.equals("CASH")){
//                            proceedtoinsertOrderLogsQue();
//                        }else if(forapproval.equals("DIRECT ORDER") && strpaymenttype.equals("VOUCHER")){
//                            prePurchaseV3();
//                        }
//                        mDialog.dismiss();
//                        mDialog = null;
//                    }
//                })
//                .onNegative(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
//                        mDialog.dismiss();
//                        mDialog = null;
//                    }
//                })
//                .show();
//
//        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mDialog.getView());
//    }

    //DISPLAY IF SUBSCRIBER IS AN AGENT
    private void displayFragmentMetroGas() {
        Fragment fragment = null;
        fragment = new GKStoreAgentFragment();
        Bundle args = new Bundle();
        args.putString("GKSTOREMERCHANTID", merchantid);
        args.putString("GKSTOREID", storeid);
        args.putString("GKSTORESERVICECODE", servicecode);
        args.putString("CURRENTDATE", currentdate);
        args.putString("GKSTOREBORROWERNAME", borrowername);
        args.putString("GKSTOREMERCHANTLAT", strmerchantlat);
        args.putString("GKSTOREMERCHANTLONG", strmerchantlong);
        args.putString("GKSTOREMERCHANTADD", strmerchantaddress);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_metrogas, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //CHECKS IF THE SUBSCRIBER IS AN AGENT (FOR NOW METROGAS)
    private void displayIfAgent() {
        if (storeid.equals("METROGAS")) {
            if (isAgent) {
                btn_view_history_container.setVisibility(View.GONE);
                fragment_metrogas.setVisibility(View.VISIBLE);
                displayFragmentMetroGas();
            } else {
                btn_view_history_container.setVisibility(View.VISIBLE);
                fragment_metrogas.setVisibility(View.GONE);
            }
        }
    }

    //SET DELIVERY ADDRESS TO EDITABLE OR NOT
    private void setDeliveryAddresstoEditableORNot(boolean editable) {
        if (servicecode.equals("GKPHONESTORE")) {
            if (forapproval.equals("APPROVAL ORDER")) {
                deliveryAddressEnabled();
                checkDeliveryEditStatus = true;
            } else {
                tracker = new GPSTracker(getViewContext());
                if (!tracker.canGetLocation()) {
                    deliveryAddressEnabled();
                    checkDeliveryEditStatus = true;
                } else {
                    deliveryAddressDisabled();
                    checkDeliveryEditStatus = false;
                }
            }
        } else {
            if (editable) {
                deliveryAddressEnabled();
            } else {
                deliveryAddressDisabled();
            }
            checkDeliveryEditStatus = editable;
        }

    }

    private void deliveryAddressEnabled() {
        gkstoredeliveryaddress.setClickable(true);
        gkstoredeliveryaddress.setEnabled(true);
        gkstoredeliveryaddress.setFocusable(true);
        gkstoredeliveryaddress.setFocusableInTouchMode(true);
        gkstoredeliveryaddress.setBackgroundResource(R.drawable.border);
        deliveryhint.setVisibility(View.VISIBLE);
    }

    private void deliveryAddressDisabled() {
        gkstoredeliveryaddress.setClickable(false);
        gkstoredeliveryaddress.setEnabled(false);
        gkstoredeliveryaddress.setFocusable(false);
        gkstoredeliveryaddress.setFocusableInTouchMode(false);
        gkstoredeliveryaddress.setBackgroundResource(R.drawable.border_disabled);
        deliveryhint.setVisibility(View.GONE);
    }

    //SET DELIVERY ADDRESS BG TO DISABLED LOOK OR NOT
    private void setDeliveryAddBG() {
        if (checkDeliveryEditStatus) {
            gkstoredeliveryaddress.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
        } else {
            gkstoredeliveryaddress.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_disabled));
        }
    }

    //SET DELIVERY ADDRESS ERROR BG TO DISABLED LOOK OR NOT
    private void setDeliveryAddErrorBG() {
        if (checkDeliveryEditStatus) {
            gkstoredeliveryaddress.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
        } else {
            gkstoredeliveryaddress.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.gkstore_border_disabled_red));
        }
    }

    //VERIFY IF ALL VALUES ARE CORRECT BEFORE PROCEEDING TO PAYMENTS
    private void checkBeforePayments() {
        if (servicecode.equals("GKPHONESTORE")) {
            if (orderdetailslist.size() > 1) {
                gkPhoneStoreSingleSelectionDialog();
            } else {
                boolean morethanquant = false;
                for (GKStoreProducts checkStoreProducts : orderdetailslist) {
                    int quantity = checkStoreProducts.getQuantity();
                    if (quantity > 1) {
                        morethanquant = true;
                        break;
                    }
                }

                if (morethanquant) {
                    gkPhoneStoreSingleSelectionDialog();
                } else {
                    if (gkstorepersonalinfo.getVisibility() == View.VISIBLE) {
                        validateInformationBeforeProceedingtoPayments();
                    } else {
                        openCustomerInformation();
                    }
                }
            }
        } else {
            if (gkstorepersonalinfo.getVisibility() == View.VISIBLE) {
                validateInformationBeforeProceedingtoPayments();
            } else {
                openCustomerInformation();
            }
        }
    }

    private void openCustomerInformation() {
        //FIRST PROCEED
        try {
            int isvalid = 0;
            if (orderdetailslist.isEmpty()) {
                isvalid = isvalid + 1;
            }
            if (isvalid > 0) {
                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                    showNoInternetConnection(false);
                    selectAProductDialog();
                } else {
                    showNoInternetConnection(true);
                    showToast("An error has occurred. Please try again.", GlobalToastEnum.WARNING);
                }
            } else {
                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                    //openPickDeliveryOptionDialog();
                    openPickDeliveryOptionDialogNew();
                } else {
                    showNoInternetConnection(true);
                    showToast("An error has occurred. Please try again.", GlobalToastEnum.WARNING);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateInformationBeforeProceedingtoPayments() {
        //SECOND PROCEEED
        try {
            int isvalid = 0;
            int gpscheckstats = 0;
            int notes1invalid = 0;
            int otherdetailsvalid = 0;
            int withinradius = 0;
            int somethingwrongwithfields = 0;
            gkstoreresarr = new JSONArray();

            //STRING MESSAGES
            gkstorefirstnamestr = gkstorefirstname.getText().toString();
            gkstorelastnamestr = gkstorelastname.getText().toString();
            gkstoremobilenumstr = getMobileNumber(gkstoremobilenum.getText().toString());
            gkstoreemailaddressstr = gkstoreemailaddress.getText().toString();
            gkstoredeliveryaddressstr = gkstoredeliveryaddress.getText().toString();
            strdeliverycharge = txtDeliveryCharge.getText().toString();
            String gkstoremiddlenamestr = gkstoremiddlename.getText().toString();

            //FIRSTNAME
            if (gkstorefirstnamestr.trim().equals("")) {
                firstnameErrorMessage.setVisibility(View.VISIBLE);
                gkstorefirstname.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                isvalid = isvalid + 1;
            } else {
                firstnameErrorMessage.setVisibility(View.GONE);
                gkstorefirstname.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
            }

            //LASTNAME
            if (gkstorelastnamestr.trim().equals("")) {
                lastnameErrorMessage.setVisibility(View.VISIBLE);
                gkstorelastname.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                isvalid = isvalid + 1;

            } else {
                lastnameErrorMessage.setVisibility(View.GONE);
                gkstorelastname.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
            }

            //MOBILE NUM
            if (gkstoremobilenumstr.trim().equals("")) {
                mobileErrorMessage.setVisibility(View.VISIBLE);
                gkstoremobilenum.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                isvalid = isvalid + 1;
            } else if (gkstoremobilenumstr.equals("INVALID")) {
                mobileErrorMessage.setVisibility(View.VISIBLE);
                gkstoremobilenum.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                isvalid = isvalid + 1;
                somethingwrongwithfields = somethingwrongwithfields + 1;
            } else {
                mobileErrorMessage.setVisibility(View.GONE);
                gkstoremobilenum.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
            }

            //EMAIL
            if (gkstoreemailaddressstr.trim().equals("")) {
                emailErrorMessage.setVisibility(View.VISIBLE);
                gkstoreemailaddress.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                isvalid = isvalid + 1;
            } else if (!CommonFunctions.isValidEmail(gkstoreemailaddressstr)) {
                emailErrorMessage.setVisibility(View.VISIBLE);
                gkstoreemailaddress.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                isvalid = isvalid + 1;
                somethingwrongwithfields = somethingwrongwithfields + 1;
            } else {
                emailErrorMessage.setVisibility(View.GONE);
                gkstoreemailaddress.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
            }

            //DELIVERY ADDRESS. CHECKS IF THE DELIVERY ADDRESS IS EMPTY
            if (gkstoredeliveryaddresscont.getVisibility() == View.VISIBLE) {
                if (gkstoredeliveryaddressstr.trim().equals("")) {
                    deliveryaddErrorMessage.setVisibility(View.VISIBLE);
                    deliveryaddErrorMessage.setText(R.string.string_deliveryadd_error);
                    setDeliveryAddErrorBG();

                    isvalid = isvalid + 1;
                } else {
                    deliveryaddErrorMessage.setVisibility(View.GONE);
                    setDeliveryAddBG();
                }
            }

            //MIDDLENAME
            if (storeid.equals("PHILCARE")) {
                if (gkstoremiddlenamestr.trim().equals("")) {
                    middlenameErrorMessage.setVisibility(View.VISIBLE);
                    gkstoremiddlename.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                    isvalid = isvalid + 1;
                } else {
                    middlenameErrorMessage.setVisibility(View.GONE);
                    gkstoremiddlename.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                }

            }

            tracker = new GPSTracker(getViewContext());

            if (!tracker.canGetLocation()) {
//                    isvalid = isvalid + 1;
//                    gpscheckstats = gpscheckstats + 1;
                if (strmerchantlat.equals("") && strmerchantlat.equals(".") && strmerchantlat.equals("NONE") &&
                        strmerchantlong.equals("") && strmerchantlong.equals(".") && strmerchantlong.equals("NONE")) {
                    if (gkstoredeliveryaddresscont.getVisibility() == View.VISIBLE) {
                        isvalid = isvalid + 1;
                        notes1invalid = notes1invalid + 1;
                        deliveryaddErrorMessage.setVisibility(View.GONE);
                        setDeliveryAddBG();
                    }
                }
            } else {
                checkGKStoreLocationFound();
                if (checklocationiffound == 0) {
                    deliveryaddErrorMessage.setVisibility(View.VISIBLE);
                    deliveryaddErrorMessage.setText("Location not found. Please choose another address.");
                    setDeliveryAddErrorBG();
                    isvalid = isvalid + 1;
                    somethingwrongwithfields = somethingwrongwithfields + 1;
                    setDeliveryAddresstoEditableORNot(true);
                } else {
                    //Checks if notes1/merchantstoredetails is empty or NONE
                    if (!strmerchantlat.equals("") && !strmerchantlat.equals(".") && !strmerchantlat.equals("NONE") &&
                            !strmerchantlong.equals("") && !strmerchantlong.equals(".") && !strmerchantlong.equals("NONE")) {
                        if (gkstoredeliveryaddresscont.getVisibility() == View.VISIBLE) {
                            if (!gkstoredeliveryaddressstr.trim().equals("")) {

                                if (servicecode.equals("GKPHONESTORE")) {
                                    if (!forapproval.equals("APPROVAL ORDER")) {
                                        //Checks if the Address Input is Within the Radius of the Merchant Address.
                                        if (!checkifAddressisWithinRadius()) {
                                            deliveryaddErrorMessage.setVisibility(View.VISIBLE);
                                            deliveryaddErrorMessage.setText("Sorry, entered address is not within our area of delivery. Please try another address.");
                                            setDeliveryAddErrorBG();
                                            isvalid = isvalid + 1;
                                            somethingwrongwithfields = somethingwrongwithfields + 1;
                                        } else {
                                            deliveryaddErrorMessage.setVisibility(View.GONE);
                                            setDeliveryAddBG();
                                        }
                                    }
                                } else {
                                    //Checks if the Address Input is Within the Radius of the Merchant Address.
                                    if (!checkifAddressisWithinRadius()) {
                                        deliveryaddErrorMessage.setVisibility(View.VISIBLE);
                                        deliveryaddErrorMessage.setText("Sorry, entered address is not within our area of delivery. Please try another address.");
                                        setDeliveryAddErrorBG();
                                        isvalid = isvalid + 1;
                                        somethingwrongwithfields = somethingwrongwithfields + 1;
                                    } else {
                                        deliveryaddErrorMessage.setVisibility(View.GONE);
                                        setDeliveryAddBG();
                                    }
                                }
                            }
                        }
                    } else {
                        isvalid = isvalid + 1;
                        notes1invalid = notes1invalid + 1;
                        deliveryaddErrorMessage.setVisibility(View.GONE);
                        setDeliveryAddBG();
                    }
                }
            }


            //OTHER DETAILS
            for (int i = 0; i < params.length(); i++) {

                JSONObject gkstoreres = new JSONObject();

                JSONObject c = params.getJSONObject(i);
                String inputname = c.getString("name");
                String ismandatory = c.getString("mandatory");
                String description = c.getString("description");
                String datatype = c.getString("datatype");

                //Check Stuff
                ismandatory = ismandatory.toUpperCase();

                //get the data in the input text
                for (EditText inputdata : inputslist) {
                    String f = inputdata.getTag().toString();
                    JSONObject jresponse = new JSONObject(f);
                    String name = jresponse.getString("name");

                    if (name.contains("*")) {
                        name = name.replace("*", "");
                    }

                    if (name.equals(inputname)) {
                        String inputres = inputdata.getText().toString();

                        //place data in the object
                        gkstoreres.put("name", inputname);
                        gkstoreres.put("description", description.toUpperCase());
                        gkstoreres.put("value", inputres);

                        gkstoreresarr.put(gkstoreres);

                        //if mandatory, need not empty
                        if (!ismandatory.equals("NO") && inputdata.getVisibility() == View.VISIBLE) {
                            if (inputres.trim().length() == 0) {
                                isvalid = isvalid + 1;
                                otherdetailsvalid = otherdetailsvalid + 1;
                                for (TextView errordata : errortextviewlist) {
                                    String errorgetTag = errordata.getTag().toString();
                                    JSONObject errorjresponse = new JSONObject(errorgetTag);
                                    String errorname = errorjresponse.getString("name");
                                    if (errorname.equals(name)) {
                                        errordata.setVisibility(View.VISIBLE);
                                        inputdata.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                                        break;
                                    }
                                }
                            } else {
                                for (TextView errordata : errortextviewlist) {
                                    String errorgetTag = errordata.getTag().toString();
                                    JSONObject errorjresponse = new JSONObject(errorgetTag);
                                    String errorname = errorjresponse.getString("name");
                                    if (errorname.equals(name)) {
                                        errordata.setVisibility(View.GONE);
                                        inputdata.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                for (TextView b : txtbuttonlist) {
                    String f = b.getTag().toString();
                    JSONObject jresponse = new JSONObject(f);
                    String name = jresponse.getString("name");

                    if (name.contains("*")) {
                        name = name.replace("*", "");
                    }

                    if (name.equals(inputname)) {
                        String buttonres = b.getText().toString();

                        //place data in the object
                        gkstoreres.put("name", inputname);
                        gkstoreres.put("description", description.toUpperCase());
                        gkstoreres.put("value", buttonres);
                        gkstoreresarr.put(gkstoreres);

                        //if mandatory, need not empty
                        if (!ismandatory.equals("NO") && b.getVisibility() == View.VISIBLE) {
                            if (buttonres.equals("")) {
                                isvalid = isvalid + 1;
                                otherdetailsvalid = otherdetailsvalid + 1;
                                for (TextView errordata : errortextviewlist) {
                                    String errorgetTag = errordata.getTag().toString();
                                    JSONObject errorjresponse = new JSONObject(errorgetTag);
                                    String errorname = errorjresponse.getString("name");
                                    if (errorname.equals(name)) {
                                        errordata.setVisibility(View.VISIBLE);
                                        b.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                                        break;
                                    }
                                }
                            } else {
                                for (TextView errordata : errortextviewlist) {
                                    String errorgetTag = errordata.getTag().toString();
                                    JSONObject errorjresponse = new JSONObject(errorgetTag);
                                    String errorname = errorjresponse.getString("name");
                                    if (errorname.equals(name)) {
                                        errordata.setVisibility(View.GONE);
                                        b.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                for (LinearLayout spinnercontainer : spinnerlistcontainer) {
                    for (Spinner selectedval : spinnerlist) {
                        String f = selectedval.getTag().toString();
                        JSONObject jresponse = new JSONObject(f);
                        String name = jresponse.getString("name");

                        if (name.contains("*")) {
                            name = name.replace("*", "");
                        }

                        if (name.equals(inputname)) {
                            String selectedoptn = selectedval.getSelectedItem().toString();
                            String id = spinnerMap.get(selectedval.getSelectedItemPosition() + 1);

                            if (selectedoptn.contains("*")) {
                                selectedoptn = selectedoptn.replace("*", "");
                            }

                            if (!selectedoptn.contains("Select")) {
                                gkstoreres.put("name", inputname);
                                gkstoreres.put("description", description.toUpperCase());
                                //gkstoreres.put("value", id + "-" + selectedoptn);
                                gkstoreres.put("value", selectedoptn);
                                gkstoreresarr.put(gkstoreres);

                                //if mandatory, need not empty
                                if (!ismandatory.equals("NO") && selectedval.getVisibility() == View.VISIBLE) {
                                    if (selectedoptn.equals("")) {
                                        isvalid = isvalid + 1;
                                        otherdetailsvalid = otherdetailsvalid + 1;
                                        for (TextView errordata : errortextviewlist) {
                                            String errorgetTag = errordata.getTag().toString();
                                            JSONObject errorjresponse = new JSONObject(errorgetTag);
                                            String errorname = errorjresponse.getString("name");
                                            if (errorname.equals(name)) {
                                                errordata.setVisibility(View.VISIBLE);
                                                spinnercontainer.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                                                break;
                                            }
                                        }
                                    } else {
                                        for (TextView errordata : errortextviewlist) {
                                            String errorgetTag = errordata.getTag().toString();
                                            JSONObject errorjresponse = new JSONObject(errorgetTag);
                                            String errorname = errorjresponse.getString("name");
                                            if (errorname.equals(name)) {
                                                errordata.setVisibility(View.GONE);
                                                spinnercontainer.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (!ismandatory.equals("NO") && selectedval.getVisibility() == View.VISIBLE) {
                                    isvalid = isvalid + 1;
                                    otherdetailsvalid = otherdetailsvalid + 1;
                                    for (TextView errordata : errortextviewlist) {
                                        String errorgetTag = errordata.getTag().toString();
                                        JSONObject errorjresponse = new JSONObject(errorgetTag);
                                        String errorname = errorjresponse.getString("name");
                                        if (errorname.equals(name)) {
                                            errordata.setVisibility(View.VISIBLE);
                                            spinnercontainer.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                                            break;
                                        }
                                    }
                                } else {
                                    for (TextView errordata : errortextviewlist) {
                                        String errorgetTag = errordata.getTag().toString();
                                        JSONObject errorjresponse = new JSONObject(errorgetTag);
                                        String errorname = errorjresponse.getString("name");
                                        if (errorname.equals(name)) {
                                            errordata.setVisibility(View.GONE);
                                            spinnercontainer.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

            //REMARKS
            for (int i = 0; i < remarksjsonarr.length(); i++) {

                JSONObject gkstoreres = new JSONObject();

                JSONObject c = remarksjsonarr.getJSONObject(i);
                String inputname = c.getString("name");
                //get the data in the input text
                for (EditText remarksdata : remarkslist) {
                    String f = remarksdata.getTag().toString();
                    JSONObject jresponse = new JSONObject(f);
                    String name = jresponse.getString("name");
                    String description = c.getString("description");

                    if (name.contains("*")) {
                        name = name.replace("*", "");
                    }

                    if (name.equals(inputname)) {
                        String inputres = remarksdata.getText().toString();

                        //place data in the object
                        gkstoreres.put("remarks", inputres);
                        passedjsonarr.put(gkstoreres);
                    }
                }
            }

            if (isvalid > 0) {
                if (gpscheckstats > 0) {
                    //gpsNotEnabledDialog();
                    gpsNotEnabledDialogNew();
                } else if (somethingwrongwithfields > 0) {
                    showWarningGlobalDialogs("There's something wrong with the entered fields. Please try again.");
                }
                //Checks if merc lat and long exist (It has to exist but if incase it does not. Checking is done.)
                else if (notes1invalid > 0) {
                    showWarningGlobalDialogs("There's something wrong with the store setup, please contact support");
                } else {
                    if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                        showWarningGlobalDialogs("Please input all required fields.");
                        hideKeyboard(GKStoreDetailsActivity.this);
                    }
                }
            } else {
                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                    proceedtopayments = true;
                    //Converts the 0/9 to 63 mobile number before calling proceeding
                    gkstoremobilenumstr = CommonFunctions.convertoPHCountryCodeNumber(gkstoremobilenumstr);
                    isServiceCharge = false;
                    checkGPSforLatLngIfEmpty();
                    checkGPSforDiscount();
                    //Converts the remarks json to xml.
                    convertRemarkJSONtoXML();
                    getSession();

                } else {
                    showNoInternetConnection(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //IF STORE IS SET FOR APPROVAL, PROCEED HERE
    private void proceedtoinsertOrderLogsQue() {
        mLlLoader.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);

        //SETS  ADDRESS, LATITUDE AND LONGITUDE TO THE MERCHANTS/STORE.
        if (strgkstoredeliverytype.trim().equals("PHILCARE") || strgkstoredeliverytype.equals("PICKUP")) {
            gkstoredeliveryaddressstr = strmerchantaddress;
            latitude = strmerchantlat;
            longitude = strmerchantlong;
        }

        String discountcheckifequal = String.valueOf(discount);
        String discountgrossamount = String.valueOf(grossamount);
        if (discountgrossamount.equals(discountcheckifequal)) {
            showWarningGlobalDialogs("Invalid amount. Cannot Proceed.");
        } else {
            double totalamountcheck = Double.parseDouble(strtotalamount);
            if (totalamountcheck > 0) {
                if (discount > 0) {
                    hasdiscount = 1;
                    //showDiscountDialog(totalamountcheck);
                    showDiscountDialogNew(totalamountcheck);
                } else {
                    hasdiscount = 0;
                    if(forapproval.equals("DIRECT ORDER") && strpaymenttype.equals("CASH")){
                        getSessionOrderLogsQue();
                    }else{
                        stepOrderDialog();
                    }

                }
            }
        }
    }

    //DIALOG FOR STEP ORDER
    private void stepOrderDialog() {
        GlobalDialogs dialogs = new GlobalDialogs(getViewContext());

        dialogs.createDialog("For Approval", " This store requires orders to be verified" +
                        " before paying. \nWould you like to proceed?",
                "CANCEL", "PROCEED", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        View closebtn = dialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
            }
        });

        View btndoubleone = dialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
            }
        });

        View btndoubletwo = dialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                dialogs.dismiss();
                getSessionOrderLogsQue();
            }
        });
    }

    //DIALOG WHEN SENT FOR APPROVAL
    private void setForApprovalDialog() {
        GlobalDialogs globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("Notice", "Your order request has been sent and " +
                        "is waiting for approval. Thank you for using Goodkredit.",
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.NOTICE);

        globalDialogs.hideCloseImageButton();

        View closebtn = globalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
            }
        });

        View btnsingle = globalDialogs.btnSingle();
        btnsingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                PreferenceUtils.saveBooleanPreference(getViewContext(), "forapprovalhistory", true);
                Intent intent = new Intent(getBaseContext(), GkStoreHistoryActivity.class);
                intent.putExtra("GKSTOREMERCHANTID", merchantid);
                intent.putExtra("GKSTOREID", storeid);
                intent.putExtra("GKSTORESERVICECODE", servicecode);
                intent.putExtra("GKSTOREBORROWERNAME", borrowername);
                intent.putExtra("GKSTOREMERCHANTLAT", strmerchantlat);
                intent.putExtra("GKSTOREMERCHANTLONG", strmerchantlong);
                intent.putExtra("GKSTOREMERCHANTADD", strmerchantaddress);
                startActivity(intent);
                globalDialogs.dismiss();
            }
        });
    }

    //DIALOG WHEN SENT FOR APPROVAL
    private void setDirectCashSuccessDialog() {
        GlobalDialogs globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("Notice", "Your order request has been sent. Thank you for using Goodkredit.",
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.NOTICE);

        globalDialogs.hideCloseImageButton();

        View closebtn = globalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
            }
        });

        View btnsingle = globalDialogs.btnSingle();
        btnsingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                Intent intent = new Intent(getViewContext(), GKStoreDetailsActivity.class);
                intent.putExtra("GKSERVICE_OBJECT", service);
                startActivity(intent);
                finish();
                globalDialogs.dismiss();
            }
        });
    }


    //BADGE ORDER DIALOG
    private void badgeOrderDialog() {
        GlobalDialogs globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("", "It seems that you currently have orders to be settled." +
                        "Would you like to complete them?",
                "NO", "YES", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        View closebtn = globalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
            }
        });

        View btndoubleone = globalDialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
            }
        });

        View btndoubletwo = globalDialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                PreferenceUtils.saveBooleanPreference(getViewContext(), "gkstorebadgeorder", true);
                Intent intent = new Intent(getBaseContext(), GkStoreHistoryActivity.class);
                intent.putExtra("GKSTOREMERCHANTID", merchantid);
                intent.putExtra("GKSTOREID", storeid);
                intent.putExtra("GKSTORESERVICECODE", servicecode);
                intent.putExtra("GKSTOREBORROWERNAME", borrowername);
                intent.putExtra("GKSTOREMERCHANTLAT", strmerchantlat);
                intent.putExtra("GKSTOREMERCHANTLONG", strmerchantlong);
                intent.putExtra("GKSTOREMERCHANTADD", strmerchantaddress);
                startActivity(intent);
                globalDialogs.dismiss();
            }
        });
    }

    private void converttoList() throws JSONException {
        morderdetailsstr = new Gson().toJson(orderdetailslist);

        ArrayList<GKStoreProducts> gkstorelist = new ArrayList<>();
        List<GenericObject> genericobject = new ArrayList<>();

        gkstorelist = new Gson().fromJson(morderdetailsstr, new TypeToken<List<GKStoreProducts>>() {
        }.getType());

        for (GKStoreProducts gkStoreProducts : gkstorelist) {
            String productid = gkStoreProducts.getProductID();
            int quantity = gkStoreProducts.getQuantity();
            String productimage = gkStoreProducts.getProductImageURL();
            genericobject.add(new GenericObject(productid, quantity, productimage));
        }

        morderdetailsstr = new Gson().toJson(genericobject);
    }

    //CLEAR CUSTOMER INFORMATION
    private void clearCustomerInformation() {
        gkstorefirstname.setText("");
        gkstorelastname.setText("");
        gkstoremobilenum.setText("");
        if (storeid.equals("PHILCARE")) {
            gkstoremiddlename.setText("");
        }
        gkstoreemailaddress.setText("");

        gkstoredeliveryaddress.setText("");

        layout_otherinfo.removeAllViews();

        addXMLDetails();

        try {
            createRemarks();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        clearErrorMessages();
    }

    //RESETS ERROR MESSAGES (THE BORDER RED)
    private void clearErrorMessages() {
        gkstorefirstname.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
        firstnameErrorMessage.setVisibility(View.GONE);

        gkstorelastname.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
        lastnameErrorMessage.setVisibility(View.GONE);

        gkstoremobilenum.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
        mobileErrorMessage.setVisibility(View.GONE);

        gkstoreemailaddress.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
        emailErrorMessage.setVisibility(View.GONE);

        gkstoredeliveryaddress.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
        deliveryaddErrorMessage.setVisibility(View.GONE);

        gkstoremiddlename.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
        middlenameErrorMessage.setVisibility(View.GONE);
    }

    //SHOW ERROR FOR WHITELISTING
    private void showErrorWhiteListing(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", message, "OK", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
    }

//  ---------------------------------------- ALL LISTENERS AND WATCHERS ----------------------------------------

    //GLOBAL ONCLICK LISTENERS
    private void globalonClickListeners() {
        proceedcontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2500)) return;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkBeforePayments();
                    }
                }, 100);
            }
        });

        closeiconview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                gkstoreinactivecontainer.setVisibility(View.GONE);
                finish();
            }
        });

        refreshnointernet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                    showNoInternetConnection(false);
                    mLlLoader.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(true);
                    getSession();
                } else {
                    showNoInternetConnection(true);
                    showWarningGlobalDialogs("Seems you are not connected to the internet. " +
                            "Please check your connection and try again. Thank you.");
                }
            }
        });

        btn_view_history_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                Intent intent = new Intent(getBaseContext(), GkStoreHistoryActivity.class);
                intent.putExtra("GKSTOREMERCHANTID", merchantid);
                intent.putExtra("GKSTOREID", storeid);
                intent.putExtra("GKSTORESERVICECODE", servicecode);
                intent.putExtra("GKSTOREBORROWERNAME", borrowername);
                intent.putExtra("GKSTOREMERCHANTLAT", strmerchantlat);
                intent.putExtra("GKSTOREMERCHANTLONG", strmerchantlong);
                intent.putExtra("GKSTOREMERCHANTADD", strmerchantaddress);

                startActivity(intent);
            }
        });

        edt_search_icon_image_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                if (searchiconselected == 1) {
                    edt_search_icon_image.setImageResource(R.drawable.ic_search);
                    edt_search_box.setText("");
                    searchiconselected = 0;
                    hideKeyboard(GKStoreDetailsActivity.this);
                }
            }
        });

        gkstoremap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                tracker = new GPSTracker(getViewContext());
                if (!tracker.canGetLocation()) {
                    gpsNotEnabledMapsDialog();
                } else {
                    checkGKStoreLocationFound();
                    if (checklocationiffound == 1) {
                        if (!strmerchantlat.equals("") && !strmerchantlat.equals(".") && !strmerchantlat.equals("NONE") &&
                                !strmerchantlong.equals("") && !strmerchantlong.equals(".") && !strmerchantlong.equals("NONE")) {

                            Bundle args = new Bundle();
                            args.putString("latitude", String.valueOf(latitude));
                            args.putString("longitude", String.valueOf(longitude));
                            args.putString("merclatitude", String.valueOf(strmerchantlat));
                            args.putString("merclongitude", String.valueOf(strmerchantlong));
                            args.putString("mercradius", String.valueOf(strradius));

                            Intent intent = new Intent(getViewContext(), SetupDeliveryAddressActivity.class);
                            intent.putExtra("index", 0);
                            intent.putExtra("args", args);
                            startActivityForResult(intent, 1);
                        } else {
                            showWarningGlobalDialogs("There's something wrong with the store setup, please contact support");
                        }
                    } else {
                        deliveryaddErrorMessage.setVisibility(View.VISIBLE);
                        deliveryaddErrorMessage.setText("Location not found. Please choose another address.");
                        setDeliveryAddErrorBG();
                        setDeliveryAddresstoEditableORNot(true);
                    }
                }
            }
        });
    }

    //GK STORE INFORMATION (LISTENER)
    private void gkstoreInfoOnClickListener() {
        gkstoremoreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                String gkstorename = gkstoremerchantspassed.getGKStoreName();
                String gkstoredesc = gkstoremerchantspassed.getGKStoreDesc();
                String gkstorecat = gkstoremerchantspassed.getGKStoreCategory();
                String gkstorerep = gkstoremerchantspassed.getGKStoreRepresentative();
                String gkstoremobileno = gkstoremerchantspassed.getGKStoreMobileNo();
                String gkstoretelno = gkstoremerchantspassed.getGKStoreTelNo();
                String gkstoreemailadd = gkstoremerchantspassed.getGKStoreEmailAddress();

                Intent intent = new Intent(getBaseContext(), GKStoreInformationActivity.class);
                intent.putExtra("GKSTORENAME", gkstorename);
                intent.putExtra("GKSTOREDESC", gkstoredesc);
                intent.putExtra("GKSTORECAT", gkstorecat);
                intent.putExtra("GKSTOREREP", gkstorerep);
                intent.putExtra("GKSTOREMOBILENO", gkstoremobileno);
                intent.putExtra("GKSTORETELNO", gkstoretelno);
                intent.putExtra("GKSTOREEMAILADD", gkstoreemailadd);
                intent.putExtra("GKSTOREMERCADD", strmerchantaddress);
                intent.putExtra("GKSTOREMERCHANTLAT", strmerchantlat);
                intent.putExtra("GKSTOREMERCHANTLONG", strmerchantlong);
                intent.putExtra("GKSTOREMERCHANTRAD", strradius);
                startActivity(intent);
            }
        });
    }

    //LISTENER FOR PICKUP AND DELIVERY LISTENER
    private View.OnClickListener pickupordeliverlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = String.valueOf(v.getTag());
            if (tag.equals("Pick Up")) {
                gkstoreadaptercontainer.setVisibility(View.GONE);
                gkstoreheaderlayout.setVisibility(View.GONE);
                adapterbackgroundcontainer.setVisibility(View.GONE);
                gkstorepersonalinfo.setVisibility(View.VISIBLE);

                gkstoreordersummaryRV = findViewById(R.id.gkstoreordersummary);
                gkstoreordersummaryRV.setLayoutManager(new LinearLayoutManager(getViewContext()));
                gkstoreordersummaryRV.setNestedScrollingEnabled(false);
                gkstoresummaryadapter = new GKStoreSummaryAdapter(getViewContext(), orderdetailslist);
                gkstoreordersummaryRV.setAdapter(gkstoresummaryadapter);
                gkstoresummaryadapter.updateSummary(orderdetailslist);

                gkstorefirstname.requestFocus();
                hideKeyboard(GKStoreDetailsActivity.this);
                scrollonTop();
                gkstorefirstname.clearFocus();

                //VIEWS VISIBILITY
                gkstoredeliverytypecontainer.setVisibility(View.VISIBLE);
                gkstoredeliveryaddresscont.setVisibility(View.GONE);

                //DELIVERYTYPE AND SERVICE CHARGE
                gkstoredeliverytype.setText("PICKUP");
                strgkstoredeliverytype = gkstoredeliverytype.getText().toString();
                txtDeliveryCharge.setText("0.00");
                deliverychargecontainer.setVisibility(View.GONE);
                isServiceCharge = false;

                //TOTAL AMOUNT
                grossamount = 0.00;
                grossamount = totalorderamount;
                txttotalamount.setText("₱".concat(CommonFunctions.currencyFormatter(String.valueOf(grossamount))));

                //STORE ADDRESS
                gkstorestoreaddcontainer.setVisibility(View.VISIBLE);
                gkstorestoreadd.setText(CommonFunctions.replaceEscapeData(strmerchantaddress));

                materialDialog.dismiss();
                materialDialog = null;
            } else if (tag.equals("For Delivery")) {
                gkstoreadaptercontainer.setVisibility(View.GONE);
                gkstoreheaderlayout.setVisibility(View.GONE);
                adapterbackgroundcontainer.setVisibility(View.GONE);
                gkstorepersonalinfo.setVisibility(View.VISIBLE);

                gkstoreordersummaryRV = findViewById(R.id.gkstoreordersummary);
                gkstoreordersummaryRV.setLayoutManager(new LinearLayoutManager(getViewContext()));
                gkstoreordersummaryRV.setNestedScrollingEnabled(false);
                gkstoresummaryadapter = new GKStoreSummaryAdapter(getViewContext(), orderdetailslist);
                gkstoreordersummaryRV.setAdapter(gkstoresummaryadapter);
                gkstoresummaryadapter.updateSummary(orderdetailslist);

                gkstorefirstname.requestFocus();
                hideKeyboard(GKStoreDetailsActivity.this);
                scrollonTop();
                gkstorefirstname.clearFocus();

                //DELIVERY
                gkstoredeliverytypecontainer.setVisibility(View.VISIBLE);
                gkstoredeliverytype.setText("FOR DELIVERY");
                gkstoredeliveryaddresscont.setVisibility(View.VISIBLE);

                //STORE ADDRESS
                gkstorestoreaddcontainer.setVisibility(View.GONE);

                strgkstoredeliverytype = gkstoredeliverytype.getText().toString()
                        .substring(gkstoredeliverytype.getText().toString().indexOf("DELIVERY"));
                isServiceCharge = true;
                grossamount = 0.00;
                getSession();

                materialDialog.dismiss();
                materialDialog = null;

                //CHECKS THE GPS IF ITS NOT ON, USER WILL BE ABLE TO INPUT THEIR ADDRESS MANUALLY
                tracker = new GPSTracker(getViewContext());
                if (!tracker.canGetLocation()) {
                    setDeliveryAddresstoEditableORNot(true);
                } else {
                    setDeliveryAddresstoEditableORNot(false);
                }
            }
        }
    };

    //LISTENER FOR PICKUP AND DELIVERY LISTENER (NEW)
    private View.OnClickListener pickupORdeliveryRGListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = String.valueOf(v.getTag());
            if (tag.equals("Pick Up")) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideDeliverOptionsDialog();
                        //createPaymentOptionDialog();
                        //orderIsPickUp();
                    }
                }, 100);
            } else if (tag.equals("For Delivery")) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideDeliverOptionsDialog();
                        //createPaymentOptionDialog();
                       // orderIsDelivery();
                    }
                }, 100);
            }
        }
    };

    //TEXT WATCHER FOR STATIC AND DYNAMIC VIEWS
    private class TextWatcher implements android.text.TextWatcher {
        private EditText editText;
        private TextView errorTextView;

        public TextWatcher(EditText editText, TextView errorTextView) {
            this.editText = editText;
            this.errorTextView = errorTextView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s.length() > 0) {
                if (editText != null) {

                }
            } else {
                if (errorTextView != null) {
                    errorTextView.setVisibility(View.GONE);
                    //Resets the delivery (Message)
                    deliveryaddErrorMessage.setText(R.string.string_deliveryadd_error);
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            int checktype = editText.getInputType();

            if (s.length() > 0) {
                if (editText != null) {
                    switch (checktype) {
                        //CHECK IF EMAIL (ADDED ONE FOR EMAIL TO MATCH THE GETINPUT TYPE)
                        case InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS + 1:
                            if (!CommonFunctions.isValidEmail(editText.getText().toString())) {
                                errorTextView.setVisibility(View.VISIBLE);
                                editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            } else {
                                errorTextView.setVisibility(View.GONE);
                                editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                            }
                            break;
                        //CHECK IF INPUT TYPE IS NUMBER
                        case InputType.TYPE_CLASS_NUMBER:
                            String strcheckmobilenumber = getMobileNumber(editText.getText().toString().trim());

                            if (strcheckmobilenumber.equals("INVALID")) {
                                errorTextView.setVisibility(View.VISIBLE);
                                editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            } else {
                                errorTextView.setVisibility(View.GONE);
                                editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                            }
                            break;
                        //DEFAULT VALUES
                        default:
                            errorTextView.setVisibility(View.GONE);
                            editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                            break;
                    }
                }
            } else {
                if (errorTextView != null) {
                    errorTextView.setVisibility(View.VISIBLE);
                    editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                }
            }
        }
    }

    //PROCEEDING TO PAYMENTS
    private void proceedtoPayments(final double totalamountcheck) {
        PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_STORE_FROM, "");
        PreferenceUtils.saveStringPreference(getViewContext(), "gkstoretxnno", ".");

        Intent intent = new Intent(getBaseContext(), Payment.class);
        intent.putExtra("GKSTOREFIRSTNAME", gkstorefirstnamestr);
        intent.putExtra("GKSTORELASTNAME", gkstorelastnamestr);
        intent.putExtra("GKSTOREMOBILENO", gkstoremobilenumstr);
        intent.putExtra("GKSTOREEMAILADD", gkstoreemailaddressstr);
        intent.putExtra("GKSTORECUSTOMERADD", gkstoredeliveryaddressstr);
        intent.putExtra("VOUCHERSESSION", vouchersession);
        intent.putExtra("AMOUNT", String.valueOf(totalamountcheck));
        intent.putExtra("DISCOUNT", strdiscount);
        intent.putExtra("CUSTOMEROTHERDETAILS", gkstoreresarr.toString());
        intent.putExtra("ORDERDETAILS", new Gson().toJson(orderdetailslist));
        intent.putExtra("MERCHANTID", merchantid);
        intent.putExtra("GKSTOREID", storeid);
        intent.putExtra("LATITUDE", latitude);
        intent.putExtra("LONGITUDE", longitude);
        intent.putExtra("GKSTOREBORROWERNAME", borrowername);
        intent.putExtra("GKSTOREDELIVERYTYPE", deliverytype);
        intent.putExtra("GKSTOREMERCHANTLAT", strmerchantlat);
        intent.putExtra("GKSTOREMERCHANTLONG", strmerchantlong);
        intent.putExtra("GKSTOREMERCHANTADD", strmerchantaddress);
        intent.putExtra("GKSTOREMERCHANTSC", strdeliverycharge);
        intent.putExtra("GKSTOREREMARKS", passedXMLremarks);
        intent.putExtra("GROSSPRICE", String.valueOf(grossamount));
        intent.putExtra("GKSERVICECODE", servicecode);
        intent.putExtra("GKHASDISCOUNT", hasdiscount);

        startActivity(intent);
    }

    public CustomNestedScrollView.OnScrollChangeListener scrollOnChangedListener = new CustomNestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (gkstoreadaptercontainer.getVisibility() == View.VISIBLE) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    isbottomscroll = true;

                    if (isloadmore) {
                        if (!isfirstload) {
                            limit = limit + 30;
                        }

                        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                            showNoInternetConnection(false);
                            mTvFetching.setText("Fetching merchant details...");
                            mTvWait.setText(" Please wait...");
                            mLlLoader.setVisibility(View.VISIBLE);
                            //getStoreProducts(getStoreProductSession);
                            getStoreProductsV2();
                        } else {
                            showNoInternetConnection();
                        }
                    }
                } else {
                    if (nestedScrollViewAtTop(scrollmaincontainer)) {
                        swipeRefreshLayout.setEnabled(true);
                    } else {
                        swipeRefreshLayout.setEnabled(false);
                    }
                }
            }
        }
    };

    private boolean nestedScrollViewAtTop(CustomNestedScrollView customNestedScrollView) {
        return customNestedScrollView.getChildCount() == 0 || customNestedScrollView.getChildAt(0).getTop() == 0;
    }

    //------------------------------------------API-------------------------------------------------------
    private void showNoInternetConnection() {
        swipeRefreshLayout.setRefreshing(false);
        mLlLoader.setVisibility(View.GONE);
        showNoInternetConnection(true);
        showNoInternetToast();
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showNoInternetConnection(false);
            mTvFetching.setText("Fetching merchant details...");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (proceedtopayments) {
                if (latitude.equals("null") && longitude.equals("null") &&
                        discountlatitude.equals("null") && discountlongitude.equals("null")) {
                    latitude = "0.0";
                    longitude = "0.0";
                    discountlatitude = "0.0";
                    discountlongitude = "0.0";
                    //calculateDiscountForReseller(calculateDiscountForResellerCallBack);
                    calculateDiscountForResellerV2();
                } else {
                    //calculateDiscountForReseller(calculateDiscountForResellerCallBack);
                    calculateDiscountForResellerV2();
                }
            } else if (isServiceCharge) {
                //getServiceCharge(getServiceChargeSession);
                getGkStoreCustomerServiceChargeV2();
            } else if (!badgecounter) {
                //getGkStoreBadge(getGkStoreBadgeCallBack);
                getGkStoreBadgeV2();
            } else {
                if (gkstoreadaptercontainer.getVisibility() == View.VISIBLE) {
                    //getStoreProducts(getStoreProductSession);
                    getStoreProductsV2();
                }
            }
        } else {
            showNoInternetConnection();
        }
    }

    private void prePurchase(Callback<String> prePurchaseCallback) {
        //Limits the two decimal places
        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(strtotalamount));
        strtotalamount = valuecheck;

        Call<String> prepurchase = RetrofitBuild.prePurchaseService(getViewContext())
                .prePurchaseCall(borrowerid,
                        strtotalamount,
                        userid,
                        imei,
                        sessionid,
                        authcode);
        prepurchase.enqueue(prePurchaseCallback);
    }

    private final Callback<String> prePurchaseSession = new Callback<String>() {

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {

                    vouchersession = response.body();
                    mLlLoader.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                    if (!vouchersession.isEmpty()) {
                        if (vouchersession.equals("001")) {
                            showErrorGlobalDialogs("Session: Invalid session.");
                        } else if (vouchersession.equals("error")) {
                            showErrorGlobalDialogs();
                        } else if (vouchersession.contains("<!DOCTYPE html>")) {
                            showErrorGlobalDialogs();
                        } else {
                            if (vouchersession.length() > 0) {
                                String discountcheckifequal = String.valueOf(discount);
                                String discountgrossamount = String.valueOf(grossamount);
                                if (discountgrossamount.equals(discountcheckifequal)) {
                                    showErrorGlobalDialogs("Invalid amount. Cannot Proceed.");
                                } else {
                                    double totalamountcheck = Double.parseDouble(strtotalamount);
                                    if (totalamountcheck > 0) {
                                        if (discount > 0) {
                                            hasdiscount = 1;
                                            showDiscountDialogNew(totalamountcheck);
                                        } else {
                                            hasdiscount = 0;
                                            proceedtoPayments(totalamountcheck);
                                        }
                                    }
                                }
                            } else {
                                showErrorGlobalDialogs("Invalid Voucher Session.");
                            }
                        }
                    } else {
                        showErrorGlobalDialogs();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            t.printStackTrace();
            showNoInternetToast();
        }
    };

    private void getStoreProducts(Callback<GetStoreProductsResponse> getStoreProductsResponseCallback) {

        Call<GetStoreProductsResponse> getstoreproducts = RetrofitBuild.getGKStoreService(getViewContext())
                .getStoreProductsCall(sessionid,
                        imei,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        userid,
                        borrowerid,
                        merchantid,
                        storeid,
                        limit
                );

        getstoreproducts.enqueue(getStoreProductsResponseCallback);
    }

    private final Callback<GetStoreProductsResponse> getStoreProductSession = new Callback<GetStoreProductsResponse>() {

        @Override
        public void onResponse(Call<GetStoreProductsResponse> call, Response<GetStoreProductsResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {

                    mLlLoader.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                    if (response.body().getStatus().equals("000")) {
                        try {
                            //MERCHANTS
                            mdb.deleteGKStoreMerchants(mdb, merchantid);
                            GKStoreMerchants gkStoreMerchants = response.body().getGkStoreMerchants();

                            mdb.insertGKStoreMerchants(mdb, gkStoreMerchants);

                            gkstoremerchantspassed = mdb.getGKStoreMerchants(mdb, merchantid);

                            //PRODUCTS LOGO
                            gettingDetailsLogo();
                            //GK STORE (INFO)
                            gkstoreInfoOnClickListener();
                            //GK STORE MERCHANT (LAT AND LONG)
                            getLatLongofMerchantStore();
                            convertLatLongofMerchant();

                            //KYC INFO
                            kycinfo = gkstoremerchantspassed.getKYCOtherInfo();
                            if (!kycinfo.trim().equals(".") && !kycinfo.trim().equals("")) {
                                if (!xmlexist) {
                                    addXMLDetails();
                                    xmlexist = true;
                                }
                            }

                            //REMARKS
                            if (!isRemarks) {
                                createRemarks();
                                isRemarks = true;
                            }

                            //CHECK IF SUBSCRIBER IS AN AGENT (METROGAS)
                            isAgent = response.body().getisIsagent();
                            displayIfAgent();

                            //CURRENT DATE
                            currentdate = response.body().getCurrentdate();

                            //STEP ORDER
                            forapproval = gkstoremerchantspassed.getExtra1();

                            //PRODUCTS
                            isloadmore = response.body().getGKStore().size() > 0;

                            isfirstload = false;

                            if (!isbottomscroll) {
                                mdb.deleteGKStoreProducts(mdb, merchantid);
                            }

                            List<GKStoreProducts> productslist = response.body().getGKStore();

                            if (productslist.size() > 0) {
                                for (GKStoreProducts gkstore : productslist) {
                                    mdb.insertGKStoreProducts(mdb, gkstore);
                                }
                            }

                            checkProductsList(mdb.getGKStoreProductKeyword(mdb, merchantid, ""));

                            //STORE STATUS
                            checkGKStoreStatus();

                            //SET DELIVERY ADDRESS TO BE EDITABLE OR NOT
                            setDeliveryAddresstoEditableORNot(false);

                            toolbar.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (response.body().getStatus().equals("004")) {
                        showWarningGlobalDialogs(response.body().getMessage());
                        gkstoreadaptercontainer.setVisibility(View.GONE);
                        adapterbackgroundcontainer.setVisibility(View.GONE);
                        gkstoreheaderlayout.setVisibility(View.GONE);
                        gkstoreinactivecontainer.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.GONE);
                        proceedcontainer.setVisibility(View.GONE);

                        if (storeid.equals("METROGAS")) {
                            if (isAgent) {
                                fragment_metrogas.setVisibility(View.GONE);
                            } else {
                                btn_view_history_container.setVisibility(View.GONE);
                            }
                        } else {
                            btn_view_history_container.setVisibility(View.GONE);
                        }
                    } else if (response.body().getStatus().equals("428")) {
                        showErrorWhiteListing(response.body().getMessage());
                        gkstoreordercontainer.setVisibility(View.GONE);
                        proceedcontainer.setVisibility(View.GONE);
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                        adapterbackgroundcontainer.setVisibility(View.GONE);
                        proceedcontainer.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetStoreProductsResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
        }
    };


    private void checkProductsList(List<GKStoreProducts> data) {
        if (data.size() > 0) {
            //SEARCH
            String searchmessage = edt_search_box.getText().toString().trim();
            if (!searchmessage.trim().equals("")) {
                checkAgentVisibility(View.GONE);
                gkadapter.updateData(mdb.getGKStoreProductKeyword(mdb, merchantid, searchmessage));
                List<GKStoreProducts> list = mdb.getGKStoreProductKeyword(mdb, merchantid, searchmessage);
                if (list.isEmpty()) {
                    noresultsfound.setVisibility(View.VISIBLE);
                    adapterbackgroundcontainer.setVisibility(View.GONE);
                } else {
                    noresultsfound.setVisibility(View.GONE);
                    adapterbackgroundcontainer.setVisibility(View.VISIBLE);
                }
            } else {
                checkAgentVisibility(View.VISIBLE);
                gkadapter.updateData(data);
                emptyproducts.setVisibility(View.GONE);
            }
            proceedcontainer.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setEnabled(true);
        } else {
            adapterbackgroundcontainer.setVisibility(View.GONE);
            emptyproducts.setVisibility(View.VISIBLE);
            noresultsfound.setVisibility(View.GONE);
            proceedcontainer.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setEnabled(false);
        }
    }

    private void checkAgentVisibility(int visibility) {
        if (storeid.equals("METROGAS")) {
            if (isAgent) {
                fragment_metrogas.setVisibility(visibility);
            } else {
                btn_view_history_container.setVisibility(visibility);
            }
        } else {
            btn_view_history_container.setVisibility(visibility);
        }
    }

    private void getServiceCharge(Callback<GetServiceChargeResponse> getServiceChargeResponseCallback) {
        //Limits to two Decimal Places
        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(totalorderamount));
        totalorderamount = Double.parseDouble(valuecheck);

        Call<GetServiceChargeResponse> getservicecharge = RetrofitBuild.getGKStoreService(getViewContext())
                .getServiceCharge(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        totalorderamount,
                        storeid
                );

        getservicecharge.enqueue(getServiceChargeResponseCallback);
    }

    private final Callback<GetServiceChargeResponse> getServiceChargeSession = new Callback<GetServiceChargeResponse>() {
        @Override
        public void onResponse(Call<GetServiceChargeResponse> call, Response<GetServiceChargeResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    mLlLoader.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                    if (response.body().getStatus().equals("000")) {
                        //Setting Service Charge
                        gkstoreadaptercontainer.setVisibility(View.GONE);
                        gkstoreheaderlayout.setVisibility(View.GONE);
                        adapterbackgroundcontainer.setVisibility(View.GONE);
                        gkstorepersonalinfo.setVisibility(View.VISIBLE);

                        gkstoreordersummaryRV = findViewById(R.id.gkstoreordersummary);
                        gkstoreordersummaryRV.setLayoutManager(new LinearLayoutManager(getViewContext()));
                        gkstoreordersummaryRV.setNestedScrollingEnabled(false);
                        gkstoresummaryadapter = new GKStoreSummaryAdapter(getViewContext(), orderdetailslist);
                        gkstoreordersummaryRV.setAdapter(gkstoresummaryadapter);
                        gkstoresummaryadapter.updateSummary(orderdetailslist);

                        gkstorefirstname.requestFocus();
                        hideKeyboard(GKStoreDetailsActivity.this);
                        scrollonTop();
                        gkstorefirstname.clearFocus();

                        servicecharge = response.body().getServicecharge();
                        deliverychargecontainer.setVisibility(View.VISIBLE);

                        txtDeliveryCharge.setText(CommonFunctions.totalamountlimiter(String.valueOf(servicecharge)));

                        //Limits to two decimal places.
                        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(servicecharge));
                        grossamount = totalorderamount + Double.parseDouble(valuecheck);
                        txttotalamount.setText("₱".concat(CommonFunctions.currencyFormatter(String.valueOf(grossamount))));
                        isServiceCharge = false;
                    } else if (response.body().getStatus().equals("005")) {
                        showWarningGlobalDialogs("There's something wrong with the store setup, please contact support");
                    } else if (response.body().getStatus().equals("error")) {
                        showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.ERROR);
                    } else if (response.body().getStatus().contains("<!DOCTYPE html>")) {
                        showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.ERROR);
                    } else {
                        showWarningGlobalDialogs(response.body().getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetServiceChargeResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    //CHECKS IF A DISCOUNT EXIST
//    private void calculateDiscountForReseller(Callback<DiscountResponse> calculateDiscountForResellerCallBack) {
//        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//        //Limits the two decimal places
//        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(grossamount));
//        grossamount = Double.parseDouble(valuecheck);
//
//        Call<DiscountResponse> discountresponse = RetrofitBuild.getDiscountService(getViewContext())
//                .calculateDiscountForReseller(
//                        userid,
//                        imei,
//                        authcode,
//                        sessionid,
//                        borrowerid,
//                        merchantid,
//                        grossamount,
//                        servicecode,
//                        discountlongitude,
//                        discountlatitude
//                );
//
//        discountresponse.enqueue(calculateDiscountForResellerCallBack);
//    }
//
//    private final Callback<DiscountResponse> calculateDiscountForResellerCallBack =
//            new Callback<DiscountResponse>() {
//
//                @Override
//                public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {
//                    try {
//                        ResponseBody errorBody = response.errorBody();
//                        if (errorBody == null) {
//                            if (response.body().getStatus().equals("000")) {
//                                discount = response.body().getDiscount();
//
//                                strdiscount = String.valueOf(discount);
//
//                                if (discount <= 0) {
//                                    strtotalamount = String.valueOf(grossamount);
//                                    if (storeid.equals("PHILCARE") ||
//                                            forapproval.trim().equals("") ||
//                                            forapproval.trim().equals(".") ||
//                                            forapproval.trim().equals("") ||
//                                            forapproval.trim().equals("DIRECT ORDER")||
//                                            (forapproval.trim().equals("DIRECT ORDER") && strpaymenttype.equals("VOUCHER"))
//                                    ) {
//                                        //prePurchase(prePurchaseSession);
//                                        prePurchaseV3();
//                                    } else if (forapproval.equals("APPROVAL ORDER")) {
//                                        proceedtoinsertOrderLogsQue();
//                                    }else if(forapproval.equals("DIRECT ORDER") && strpaymenttype.equals("CASH")){
//                                        proceedtoinsertOrderLogsQue();
//                                    }
//                                } else {
//                                    strtotalamount = String.valueOf((grossamount - discount));
//                                    if (Double.parseDouble(String.valueOf(grossamount)) > 0) {
//                                        if (storeid.equals("PHILCARE") ||
//                                                forapproval.trim().equals("") ||
//                                                forapproval.trim().equals(".") ||
//                                                forapproval.trim().equals("") ||
//                                                forapproval.trim().equals("DIRECT ORDER")
//
//                                        ) {
//                                            //prePurchase(prePurchaseSession);
//                                            prePurchaseV3();
//                                        } else if (forapproval.equals("APPROVAL ORDER")) {
//                                            proceedtoinsertOrderLogsQue();
//                                        }
//                                    }
//                                }
//                            } else if (response.body().getStatus().equals("005")) {
//                                discountmessage = response.body().getMessage();
//                                discount = response.body().getDiscount();
//                                strdiscount = String.valueOf(discount);
//                                strtotalamount = String.valueOf(grossamount);
//                                mLlLoader.setVisibility(View.GONE);
//                                swipeRefreshLayout.setRefreshing(false);
//                                noDiscountResellerServiceArea();
//                            } else {
//                                mLlLoader.setVisibility(View.GONE);
//                                swipeRefreshLayout.setRefreshing(false);
//                                showErrorGlobalDialogs(response.body().getMessage());
//                            }
//                        } else {
//                            mLlLoader.setVisibility(View.GONE);
//                            swipeRefreshLayout.setRefreshing(false);
//                            showErrorGlobalDialogs();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<DiscountResponse> call, Throwable t) {
//                    showErrorGlobalDialogs();
//                }
//            };

    private void getSessionOrderLogsQue() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showNoInternetConnection(false);
            mTvFetching.setText("Fetching merchant details...");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //insertOrderLogsQue(insertOrderLogsQueCallBack);
            insertOrderLogsQueueV2();
        } else {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showWarningToast("Seems you are not connected to the internet. " +
                    "Please check your connection and try again. Thank you.");
        }
    }

    private void insertOrderLogsQue(Callback<GenericResponse> insertOrderLogsQueCallBack) {
        try {
            converttoList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<GenericResponse> insertorderlogsque = RetrofitBuild.getGKStoreService(getViewContext())
                .insertOrderLogsQue(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        borrowername,
                        merchantid,
                        storeid,
                        gkstorefirstnamestr,
                        gkstorelastnamestr,
                        gkstoremobilenumstr,
                        gkstoreemailaddressstr,
                        gkstoredeliveryaddressstr,
                        gkstoreresarr.toString(),
                        morderdetailsstr,
                        ".",
                        latitude,
                        longitude,
                        passedXMLremarks,
                        strgkstoredeliverytype,
                        servicecode,
                        String.valueOf(grossamount),
                        hasdiscount,
                        isapproved
                );

        insertorderlogsque.enqueue(insertOrderLogsQueCallBack);
    }

    private final Callback<GenericResponse> insertOrderLogsQueCallBack =
            new Callback<GenericResponse>() {

                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                mLlLoader.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);
                                setForApprovalDialog();
                            } else {
                                mLlLoader.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);
                                showErrorGlobalDialogs(response.body().getMessage());
                            }
                        } else {
                            mLlLoader.setVisibility(View.GONE);
                            swipeRefreshLayout.setRefreshing(false);
                            showErrorGlobalDialogs();
                        }
                    } catch (Exception e) {
                        mLlLoader.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        showErrorGlobalDialogs();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    showErrorGlobalDialogs();
                    mLlLoader.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            };

    //GET BADGE
    private void getGkStoreBadge(Callback<BadgeResponse> getGkStoreBadgeCallBack) {
        Call<BadgeResponse> getBadge = RetrofitBuild.getGKStoreService(getViewContext())
                .getGkStoreBadge(sessionid,
                        imei,
                        userid,
                        authcode,
                        borrowerid,
                        servicecode
                );

        getBadge.enqueue(getGkStoreBadgeCallBack);
    }

    private final Callback<BadgeResponse> getGkStoreBadgeCallBack =
            new Callback<BadgeResponse>() {

                @Override
                public void onResponse(Call<BadgeResponse> call, Response<BadgeResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                mLlLoader.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);
                                CommonFunctions.hideDialog();
                                badgecounter = true;

                                int badge = response.body().getCount();
                                if (badge > 0) {
                                    badgeOrderDialog();
                                } else {
                                    //getStoreProducts(getStoreProductSession);
                                }
                            } else {
                                mLlLoader.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);
                                showErrorGlobalDialogs(response.body().getMessage());
                            }
                        } else {
                            mLlLoader.setVisibility(View.GONE);
                            swipeRefreshLayout.setRefreshing(false);
                            showErrorGlobalDialogs();
                        }
                    } catch (Exception e) {
                        mLlLoader.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        showErrorGlobalDialogs();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BadgeResponse> call, Throwable t) {
                    showErrorGlobalDialogs();
                    mLlLoader.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            };


    // ---------------------------------------- START AND OVERRIDE ----------------------------------------
    public static void start(Context context, GKService gks) {
        Intent intent = new Intent(context, GKStoreDetailsActivity.class);
        intent.putExtra("GKSERVICE_OBJECT", gks);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                setDeliveryAddresstoEditableORNot(false);
                Bundle args = data.getExtras();
//                Address checkaddress = null;
                if (args != null) {
                    mapsAddress = args.getParcelable("MarkerAddress");
                }

                if (mapsAddress != null) {
                    double checklat = mapsAddress.getLatitude();
                    double checklong = mapsAddress.getLongitude();

                    latitude = String.valueOf(checklat);
                    longitude = String.valueOf(checklong);

                    latitude = longlatLimiter(latitude);
                    longitude = longlatLimiter(longitude);
                }

                EditText gkstoredeliveryaddress = findViewById(R.id.gkstoredeliveryaddress);
                gkstoredeliveryaddress.setText(getMarkerAddress(mapsAddress));

            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data != null) {
                    Bundle args = data.getExtras();
                    if (args != null) {
                        checkDeliveryEditStatus = args.getBoolean("MapsError");

                        if (checkDeliveryEditStatus) {
                            setDeliveryAddresstoEditableORNot(true);
                        } else {
                            setDeliveryAddresstoEditableORNot(false);
                        }
                    }
                } else {
                    setDeliveryAddresstoEditableORNot(false);
                }
            }
        }
    }//onActivityResult

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        try {
            String date = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

            for (TextView b : txtbuttonlist) {
                String f = b.getTag().toString();
                JSONObject jresponse = new JSONObject(f);
                String name = jresponse.getString("name");

                String d = view.getTag();
                JSONObject jres = new JSONObject(d);
                String viewtag = jres.getString("name");

                if (name.equals(viewtag)) {

                    b.setText(date);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (gkstorepersonalinfo.getVisibility() == View.VISIBLE) {
                adapterbackgroundcontainer.setVisibility(View.VISIBLE);
                gkstorepersonalinfo.setVisibility(View.GONE);
                gkstoreheaderlayout.setVisibility(View.VISIBLE);
                gkstoreadaptercontainer.setVisibility(View.VISIBLE);
                hideKeyboard(GKStoreDetailsActivity.this);
                scrollonTop();
                proceedtopayments = false;
                isServiceCharge = false;
            } else {
                //closetheActivityDialog();
                closetheActivityDialogNew();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (gkstorepersonalinfo.getVisibility() == View.VISIBLE) {
            adapterbackgroundcontainer.setVisibility(View.VISIBLE);
            gkstorepersonalinfo.setVisibility(View.GONE);
            gkstoreheaderlayout.setVisibility(View.VISIBLE);
            gkstoreadaptercontainer.setVisibility(View.VISIBLE);
            hideKeyboard(GKStoreDetailsActivity.this);
            scrollonTop();
            proceedtopayments = false;
            isServiceCharge = false;
        } else {
            //closetheActivityDialog();
            closetheActivityDialogNew();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }
    }

    @Override
    public void onRefresh() {
        try {
            if (gkstoreadaptercontainer.getVisibility() == View.VISIBLE) {
                if (orderdetailslist.size() > 0) {
                    //IN CASE A DIALOG IS NEEDED
                    //onRefreshWarningDialog();
                    onRefreshValues();
                } else {
                    onRefreshValues();
                }
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onRefreshWarningDialog() {
        GlobalDialogs dialogs = new GlobalDialogs(getViewContext());

        dialogs.createDialog("", "You currently have unfinished orders and refreshing this page will remove them. Would you like to proceed?",
                "Proceed", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.WARNING);

        View closebtn = dialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        View singlebtn = dialogs.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
                onRefreshValues();
            }
        });
    }

    private void onRefreshValues() {
        adapterexist = true;

        productslist.clear();
        orderdetailslist.clear();
        gkadapter.fullclear();

        gkadapter = new GKStoreAdapter(getViewContext(), productslist);
        GKStoreAdapterRV.setAdapter(gkadapter);
        gkadapter.updateData(productslist);

        limit = 0;
        isbottomscroll = false;
        isloadmore = false;
        isfirstload = false;

        getSession();
    }

    @Override
    protected void onResume() {
        //Checks if personalinfo/ordedetails page is visible
        if (gkstorepersonalinfo.getVisibility() == View.VISIBLE) {
            proceedtopayments = false;
        }

        //FOR APPROVAL
        Boolean forapprovalhisstatus = PreferenceUtils.getBooleanPreference(getViewContext(), "forapprovalhistory");

        if (forapprovalhisstatus) {
            PreferenceUtils.removePreference(getViewContext(), "forapprovalhistory");

            adapterbackgroundcontainer.setVisibility(View.VISIBLE);
            gkstorepersonalinfo.setVisibility(View.GONE);
            gkstoreheaderlayout.setVisibility(View.VISIBLE);
            gkstoreadaptercontainer.setVisibility(View.VISIBLE);
            hideKeyboard(GKStoreDetailsActivity.this);
            scrollonTop();
            proceedtopayments = false;
            isServiceCharge = false;

            orderdetailslist.clear();
            productslist.clear();
            gkadapter.clear();

            inputslist.clear();
            textviewlist.clear();
            txtbuttonlist.clear();
            spinnerlist.clear();

            gkstoreresarr = new JSONArray();
            params = new JSONArray();

            searchiconselected = 0;
            noresultsfound.setVisibility(View.GONE);
            edt_search_icon_image.setImageResource(R.drawable.ic_search);
            picturecontainer.setVisibility(View.VISIBLE);
            edt_search_box.setText("");

            gkadapter = new GKStoreAdapter(getViewContext(), productslist);
            GKStoreAdapterRV.setAdapter(gkadapter);
            gkadapter.updateData(mdb.getGKStoreProductKeyword(mdb, merchantid, ""));

            clearCustomerInformation();

            getPersonalInformation();
        }

        //BADGE
        Boolean gkstorebadgeorderstatus = PreferenceUtils.getBooleanPreference(getViewContext(), "gkstorebadgeorder");
        if (gkstorebadgeorderstatus) {
            PreferenceUtils.removePreference(getViewContext(), "gkstorebadgeorder");
            onRefreshValues();
        }

        super.onResume();
    }

    @Override
    protected void onStop() {
        hideKeyboard(this);
        super.onStop();
    }


    /*
     * SECURITY UPDATE
     * AS OF
     * JANUARY 2020
     * */

    private void getGkStoreBadgeV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("servicecode", servicecode);
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                badgeIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", badgeIndex);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                badgeAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
                badgeKey = CommonFunctions.getSha1Hex(badgeAuth + sessionid + "getGkStoreBadgeV2");
                badgeParam = CommonFunctions.encryptAES256CBC(badgeKey, String.valueOf(paramJson));

                getGkStoreBadgeV2Object();

            } else {
                hideProgressDialog();
                showNoInternetToast();
            }

        } catch (Exception e) {
            e.printStackTrace();
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getGkStoreBadgeV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getGKStoreV2API(getViewContext())
                .getGkStoreBadgeV2(badgeAuth, sessionid, badgeParam);

        call.enqueue(getGkStoreBadgeV2CallBack);
    }

    private Callback<GenericResponse> getGkStoreBadgeV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();

            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(badgeKey, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(badgeKey, response.body().getData());

                   if(data.equals("error")){
                       showErrorToast();
                   }else{
                       badgecounter = true;
                       if(data.equals("error")){
                          data = "0";
                       }
                       badge = Integer.parseInt(data);
                   }

                    getStoreProductsV2();

                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            } else {
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showNoInternetToast();
        }
    };

    private void getStoreProductsV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("merchantid", merchantid);
                parameters.put("storeid", storeid);
                parameters.put("limit", String.valueOf(limit));
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                productIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", productIndex);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                productAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
                productKey = CommonFunctions.getSha1Hex(productAuth + sessionid + "getStoreProductsV2");
                productParam = CommonFunctions.encryptAES256CBC(productKey, String.valueOf(paramJson));

                getStoreProductsV2Object();

            } else {
                hideProgressDialog();
                showNoInternetToast();
            }

        } catch (Exception e) {
            e.printStackTrace();
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getStoreProductsV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getGKStoreV2API(getViewContext())
                .getStoreProductsV2(productAuth, sessionid, productParam);

        call.enqueue(getStoreProductsV2CallBack);
    }

    private Callback<GenericResponse> getStoreProductsV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(productKey, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(productKey, response.body().getData());

                    String products = CommonFunctions.parseJSON(data, "products");
                    String storeinfo = CommonFunctions.parseJSON(data, "storeinfo");
                    String isagent = CommonFunctions.parseJSON(data, "isagent");
                    String date = CommonFunctions.parseJSON(data, "currentdate");

                    if(products != null && storeinfo != null) {
                        //MERCHANTS
                        mdb.deleteGKStoreMerchants(mdb, merchantid);

                        GKStoreMerchants gkStoreMerchants = new Gson().fromJson(storeinfo, new TypeToken<GKStoreMerchants>() {}.getType());

                        mdb.insertGKStoreMerchants(mdb, gkStoreMerchants);

                        gkstoremerchantspassed = mdb.getGKStoreMerchants(mdb, merchantid);

                        //PRODUCTS LOGO
                        gettingDetailsLogo();
                        //GK STORE (INFO)
                        gkstoreInfoOnClickListener();
                        //GK STORE MERCHANT (LAT AND LONG)
                        getLatLongofMerchantStore();
                        convertLatLongofMerchant();

                        //KYC INFO
                        kycinfo = gkstoremerchantspassed.getKYCOtherInfo();
                        if (!kycinfo.trim().equals(".") && !kycinfo.trim().equals("")) {
                            if (!xmlexist) {
                                addXMLDetails();
                                xmlexist = true;
                            }
                        }

                        //REMARKS
                        if (!isRemarks) {
                            try {
                                createRemarks();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            isRemarks = true;
                        }

                        //CHECK IF SUBSCRIBER IS AN AGENT (METROGAS)
                        isAgent = Boolean.parseBoolean(isagent);
                        displayIfAgent();

                        //CURRENT DATE
                        currentdate = date;

                        //STEP ORDER
                        forapproval = gkstoremerchantspassed.getExtra1();

                        //PRODUCTS
                        List<GKStoreProducts> productslist = new Gson().fromJson(products, new TypeToken<List<GKStoreProducts>>() {}.getType());

                        isfirstload = false;

                        if (!isbottomscroll) {
                            mdb.deleteGKStoreProducts(mdb, merchantid);
                        }

                        if (productslist.size() > 0) {
                            isloadmore = true;
                            for (GKStoreProducts gkstore : productslist) {
                                mdb.insertGKStoreProducts(mdb, gkstore);
                            }
                        } else {
                            isloadmore = false;
                        }

                        checkProductsList(mdb.getGKStoreProductKeyword(mdb, merchantid, ""));

                        //STORE STATUS
                        checkGKStoreStatus();

                        //SET DELIVERY ADDRESS TO BE EDITABLE OR NOT
                        setDeliveryAddresstoEditableORNot(false);

                        toolbar.setVisibility(View.VISIBLE);

                        //BADGE
                        if (badge > 0) {
                            badge = 0;
                            badgeOrderDialog();
                        }
                    } else {
                        getStoreProductsV2();
                    }
                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                        adapterbackgroundcontainer.setVisibility(View.GONE);
                        proceedcontainer.setVisibility(View.GONE);
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                        adapterbackgroundcontainer.setVisibility(View.GONE);
                        proceedcontainer.setVisibility(View.GONE);
                    } else if (response.body().getStatus().equals("004")) {
                        showWarningGlobalDialogs(decryptedMessage);
                        gkstoreadaptercontainer.setVisibility(View.GONE);
                        adapterbackgroundcontainer.setVisibility(View.GONE);
                        gkstoreheaderlayout.setVisibility(View.GONE);
                        gkstoreinactivecontainer.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.GONE);
                        proceedcontainer.setVisibility(View.GONE);

                        if (storeid.equals("METROGAS")) {
                            if (isAgent) {
                                fragment_metrogas.setVisibility(View.GONE);
                            } else {
                                btn_view_history_container.setVisibility(View.GONE);
                            }
                        } else {
                            btn_view_history_container.setVisibility(View.GONE);
                        }
                    } else if (response.body().getStatus().equals("428")) {
                        showErrorWhiteListing(decryptedMessage);
                        gkstoreordercontainer.setVisibility(View.GONE);
                        proceedcontainer.setVisibility(View.GONE);
                    } else {
                        showErrorGlobalDialogs(decryptedMessage);
                        adapterbackgroundcontainer.setVisibility(View.GONE);
                        proceedcontainer.setVisibility(View.GONE);
                    }
                }
            } else {
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showNoInternetToast();
        }
    };

    private void calculateDiscountForResellerV2() {
        try {
            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(grossamount));
                grossamount = Double.parseDouble(valuecheck);

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("merchantid", merchantid);
                parameters.put("amountpaid", String.valueOf(grossamount));
                parameters.put("servicecode", servicecode);
                parameters.put("longitude", String.valueOf(discountlongitude));
                parameters.put("latitude", String.valueOf(discountlatitude));
                parameters.put("devicetype",CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "calculateDiscountForResellerV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                calculateDiscountForResellerV2Object();
            } else {
                showNoInternetToast();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorToast();
        }
    }

    private void calculateDiscountForResellerV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .calculateDiscountForResellerV3(authenticationid, sessionid, param);
        call.enqueue(calculateDiscountForResellerV2Callback);
    }

    private final Callback<GenericResponse> calculateDiscountForResellerV2Callback = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                Log.d("okhttp","DATA: "+decryptedData);

                if (response.body().getStatus().equals("000")) {
                    if (decryptedData.equalsIgnoreCase("error") || decryptedMessage.equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else {
                        discount = Double.parseDouble(decryptedData);
                        strdiscount = String.valueOf(discount);

                        Log.d("okhttp","DATA: "+forapproval);

                        if (discount <= 0) {
                            strtotalamount = String.valueOf(grossamount);
                            if (storeid.equals("PHILCARE") ||
                                    forapproval.trim().equals("") ||
                                    forapproval.trim().equals(".") ||
                                    forapproval.trim().equals("") ||
                                    forapproval.trim().equals("DIRECT ORDER") && strpaymenttype.equals("VOUCHER")
                            ) {
                                //prePurchase(prePurchaseSession);
                                prePurchaseV3();
                            } else if(forapproval.trim().equals("DIRECT ORDER") && strpaymenttype.equals("CASH")){
                                orderstatus = "PENDING";
                                proceedtoinsertOrderLogsQue();
                            }else{
                                orderstatus = "FOR APPROVAL";
                               proceedtoinsertOrderLogsQue();
                            }
                        } else {
                            strtotalamount = String.valueOf((grossamount - discount));
                            if (Double.parseDouble(String.valueOf(grossamount)) > 0) {
                                if (storeid.equals("PHILCARE") ||
                                        forapproval.trim().equals("") ||
                                        forapproval.trim().equals(".") ||
                                        forapproval.trim().equals("") ||
                                        forapproval.trim().equals("DIRECT ORDER") && strpaymenttype.equals("VOUCHER")
                                ) {
                                    //prePurchase(prePurchaseSession);
                                    prePurchaseV3();
                                } else if(forapproval.trim().equals("DIRECT ORDER") && strpaymenttype.equals("CASH")){
                                    orderstatus = "PENDING";
                                    proceedtoinsertOrderLogsQue();
                                }else{
                                    orderstatus = "FOR APPROVAL";
                                    proceedtoinsertOrderLogsQue();
                                }
                            }
                        }
                    }

                } else {
                    if (decryptedData.equalsIgnoreCase("error") || decryptedMessage.equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else {
                        if (response.body().getStatus().equals("error")) {
                            showErrorToast();
                        } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        } else {
                            showErrorGlobalDialogs(decryptedMessage);
                        }
                    }
                }
            } else {
                showErrorToast();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorToast();
        }
    };

    private void getGkStoreCustomerServiceChargeV2() {
        try {
            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(totalorderamount));
                totalorderamount = Double.parseDouble(valuecheck);

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("gkstoreid", storeid);
                parameters.put("amount", String.valueOf(totalorderamount));
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getGkStoreCustomerServiceChargeV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getGkStoreCustomerServiceChargeV2Object();
            } else {
                showNoInternetToast();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorToast();
        }
    }

    private void getGkStoreCustomerServiceChargeV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getGKStoreV2API(getViewContext())
                .getGkStoreCustomerServiceChargeV2(authenticationid, sessionid, param);
        call.enqueue(getGkStoreCustomerServiceChargeV2CallBack);
    }

    private final Callback<GenericResponse> getGkStoreCustomerServiceChargeV2CallBack = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    if (decryptedData.equalsIgnoreCase("error") || decryptedMessage.equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else {
                        //Setting Service Charge
                        gkstoreadaptercontainer.setVisibility(View.GONE);
                        gkstoreheaderlayout.setVisibility(View.GONE);
                        adapterbackgroundcontainer.setVisibility(View.GONE);
                        gkstorepersonalinfo.setVisibility(View.VISIBLE);

                        gkstoreordersummaryRV = findViewById(R.id.gkstoreordersummary);
                        gkstoreordersummaryRV.setLayoutManager(new LinearLayoutManager(getViewContext()));
                        gkstoreordersummaryRV.setNestedScrollingEnabled(false);
                        gkstoresummaryadapter = new GKStoreSummaryAdapter(getViewContext(), orderdetailslist);
                        gkstoreordersummaryRV.setAdapter(gkstoresummaryadapter);
                        gkstoresummaryadapter.updateSummary(orderdetailslist);

                        gkstorefirstname.requestFocus();
                        hideKeyboard(GKStoreDetailsActivity.this);
                        scrollonTop();
                        gkstorefirstname.clearFocus();

                        servicecharge = Double.parseDouble(decryptedData);
                        deliverychargecontainer.setVisibility(View.VISIBLE);

                        txtDeliveryCharge.setText(CommonFunctions.totalamountlimiter(String.valueOf(servicecharge)));

                        //Limits to two decimal places.
                        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(servicecharge));
                        grossamount = totalorderamount + Double.parseDouble(valuecheck);
                        txttotalamount.setText("₱".concat(CommonFunctions.currencyFormatter(String.valueOf(grossamount))));
                        isServiceCharge = false;
                    }
                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            } else {
                showErrorToast();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorToast();
        }
    };

    private void prePurchaseV3() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(strtotalamount));
            strtotalamount = valuecheck;

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("amountpurchase", strtotalamount);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index =CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "prePurchaseV3");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            prePurchaseV3Object();

        } else {
            showNoInternetToast();
        }

    }

    private void prePurchaseV3Object(){
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .prePurchaseV3(
                        authenticationid, sessionid, param
                );
        call.enqueue(prePurchaseV3Callback);
    }

    private final Callback<GenericResponse> prePurchaseV3Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                if (errorBody == null) {
                    String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            vouchersession = CommonFunctions.parseJSON(decryptedData, "value");
                            if (vouchersession.length() > 0) {
                                String discountcheckifequal = String.valueOf(discount);
                                String discountgrossamount = String.valueOf(grossamount);
                                if (discountgrossamount.equals(discountcheckifequal)) {
                                    showErrorGlobalDialogs("Invalid amount. Cannot Proceed.");
                                } else {
                                    double totalamountcheck = Double.parseDouble(strtotalamount);
                                    if (totalamountcheck > 0) {
                                        if (discount > 0) {
                                            hasdiscount = 1;
                                            showDiscountDialogNew(totalamountcheck);
                                        } else {
                                            hasdiscount = 0;
                                            proceedtoPayments(totalamountcheck);
                                        }
                                    }
                                }
                            } else {
                                showErrorGlobalDialogs("Invalid Voucher Session.");
                            }
                        }

                    }  else {
                        if (response.body().getStatus().equals("error")) {
                            showErrorToast();
                        } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        } else {
                            showErrorGlobalDialogs(decryptedMessage);
                        }
                    }
                } else {
                    showErrorToast();
                }
            } catch (Exception e){
                e.printStackTrace();
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorToast();
        }
    };

    private void insertOrderLogsQueueV2() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            try {
                converttoList();
                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("borrowername", borrowername);
                parameters.put("merchantid", merchantid);
                parameters.put("storeid", storeid);
                parameters.put("firstname", gkstorefirstnamestr);
                parameters.put("lastname", gkstorelastnamestr);
                parameters.put("mobileno", gkstoremobilenumstr);
                parameters.put("emailaddress", gkstoreemailaddressstr);
                parameters.put("address", gkstoredeliveryaddressstr);
                parameters.put("otherdetails", gkstoreresarr.toString());
                parameters.put("orderdetails", morderdetailsstr);
                parameters.put("vouchersession", ".");
                parameters.put("remarks", passedXMLremarks);
                parameters.put("ordertype", ((strpaymenttype.equals("CASH") ? deliverytype+"-"+strpaymenttype : deliverytype)));
                parameters.put("hasdiscount", String.valueOf(hasdiscount));
                parameters.put("servicecode", servicecode);
                parameters.put("grossamount",  String.valueOf(grossamount));
                parameters.put("longitude",  longitude);
                parameters.put("latitude",  latitude);
                parameters.put("isapproved", String.valueOf(isapproved));
                parameters.put("orderstatus",orderstatus);
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                insertIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", insertIndex);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                insertAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                insertKey = CommonFunctions.getSha1Hex(insertAuthenticationid + sessionid + "insertOrderLogsQueueV3");
                insertParam = CommonFunctions.encryptAES256CBC(insertKey, String.valueOf(paramJson));

                insertOrderLogsQueueV2Object();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            showNoInternetToast();
        }

    }

    private void insertOrderLogsQueueV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getGKStoreV2API(getViewContext())
                .insertOrderLogsQueueV3(
                        insertAuthenticationid, sessionid, insertParam
                );
        call.enqueue(insertOrderLogsQueueV2CallBack);
    }

    private final Callback<GenericResponse> insertOrderLogsQueueV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                if (errorBody == null) {
                    String decryptedMessage = CommonFunctions.decryptAES256CBC(insertKey, response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(insertKey, response.body().getData());

                        if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            if(forapproval.equals("DIRECT ORDER")){
                                setDirectCashSuccessDialog();
                            }else {
                                setForApprovalDialog();
                            }

                        }
                    }  else {
                        if (response.body().getStatus().equals("error")) {
                            showErrorToast();
                        } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        } else {
                            showErrorGlobalDialogs(decryptedMessage);
                        }
                    }
                } else {
                    showErrorToast();
                }
            } catch (Exception e){
                e.printStackTrace();
                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorToast();
        }
    };

    private void createLocalDialog(){
        alertDialog = new Dialog(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        } else {
            Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.gkstore_custom_layout, null);

        alertDialog.setContentView(view);
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();


        RadioGroup deliverTypeGroup = view.findViewById(R.id.deliveryTypeRadioGroup);
        RadioGroup paymentTypeGroup = view.findViewById(R.id.paymentTypeRadioGroup);

        //delivery type
        RadioButton deliveryPickup = view.findViewById(R.id.radioButtonPickup);
        RadioButton deliveryForDelivery = view.findViewById(R.id.radioButtonForDelivery);

        //payment type
        RadioButton paymentcash = view.findViewById(R.id.radioButtonPaymentCash);
        RadioButton paymentvoucher = view.findViewById(R.id.radioButtonPaymentVoucher);

        TextView proceedBtn = view.findViewById(R.id.gkstore_custom_btn);
        proceedBtn.setText("Proceed");
        proceedBtn.setTextColor(getResources().getColor(R.color.color_information_blue));

        if (strmercordertype.equals("DELIVERY:::PICKUP")) {
            deliveryForDelivery.setEnabled(true);
            deliveryPickup.setEnabled(true);
        } else if (strmercordertype.equals("PICKUP")) {
            deliveryForDelivery.setEnabled(false);
            deliveryPickup.setEnabled(true);
        } else if (strmercordertype.equals("DELIVERY")) {
            deliveryForDelivery.setEnabled(true);
            deliveryPickup.setEnabled(false);
        } else {
            deliveryForDelivery.setEnabled(true);
            deliveryPickup.setEnabled(true);
        }


        if (strmerchantpaymenttype.equals("VOUCHER:::CASH")) {
            paymentcash.setEnabled(true);
            paymentvoucher.setEnabled(true);
        } else if (strmerchantpaymenttype.equals("CASH")) {
            paymentvoucher.setEnabled(false);
            paymentcash.setEnabled(true);
        } else if (strmerchantpaymenttype.equals("VOUCHER")) {
            paymentvoucher.setEnabled(true);
            paymentcash.setEnabled(false);
        } else {
            paymentcash.setEnabled(false);
            paymentvoucher.setEnabled(true);
        }


        deliverTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isDeliverTypeSelected = true;
                switch (checkedId){
                    case R.id.radioButtonPickup:
                        deliverytype = "PICKUP";
                         //showToast("Delivery Type is Pick-up", GlobalToastEnum.NOTICE);
                        break;
                    case R.id.radioButtonForDelivery:
                        deliverytype = "DELIVERY";
                        //showToast("Delivery Type is Delivery", GlobalToastEnum.NOTICE);
                        break;
                }
            }
        });

        paymentTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isPaymentTypeSelected = true;
                switch (checkedId){
                    case R.id.radioButtonPaymentCash:
                        strpaymenttype = "CASH";
                        //showToast("Payment Type is cash", GlobalToastEnum.NOTICE);
                    break;
                    case R.id.radioButtonPaymentVoucher:
                        strpaymenttype = "VOUCHER";
                        //showToast("Payment Type is voucher", GlobalToastEnum.NOTICE);
                     break;
                }
            }
        });

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDeliverTypeSelected && isPaymentTypeSelected){
                     if(deliverytype.equals("PICKUP")){
                         orderIsPickUp();
                     }else if(deliverytype.equals("DELIVERY")){
                         orderIsDelivery();
                     }
                     alertDialog.dismiss();
                     alertDialog = null;

                     isDeliverTypeSelected =false;
                     isPaymentTypeSelected =false;

                }else{
                    if(!isPaymentTypeSelected){
                        showToast("Please select payment type",GlobalToastEnum.NOTICE);
                    }
                    if(!isDeliverTypeSelected){
                        showToast("Please select order type",GlobalToastEnum.NOTICE);
                    }
                }
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isDeliverTypeSelected =false;
                isPaymentTypeSelected =false;
            }
        });

    }

}
