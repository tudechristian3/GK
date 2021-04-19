package com.goodkredit.myapplication.activities.gkstore;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gkstore.GKStoreConfirmationDetailsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKStoreProducts;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.GenericObject;
import com.goodkredit.myapplication.responses.gkstore.CheckGKStoreStatusQueResponse;
import com.goodkredit.myapplication.responses.gkstore.ProcessGkStoreOrderResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKStoreConfirmationActivity extends BaseActivity implements View.OnClickListener {
    private AQuery aq;
    private DatabaseHandler mdb;
    private CommonFunctions cf;
    private CommonVariables cv;

    //MAIN CONTAINER
    private LinearLayout maincontainer;
    private NestedScrollView home_body;

    //INFORMATION PASSED
    private LinearLayout personalinformationcontainer;
    private LinearLayout orderdetailscontainer;
    private LinearLayout paymentdetailscontainer;
    private RecyclerView GKStoreAdapterRV;
    private GKStoreConfirmationDetailsAdapter gkadapter;
    private ArrayList<GKStoreProducts> orderdetailslist = new ArrayList<>();

    private String firstname = "";
    private String lastname = "";
    private String mobileno = "";
    private String emailaddress = "";
    private String customeradd = "";
    private String vouchersession = "";
    private String amountopayvalue = "";
    private String otherdetails = "";
    private String gkorderdetails = "";
    private String amountendered = "";
    private String amountchange = "";
    private String borrowername = "";
    private String storeid = "";
    private String merchantid = "";
    private String latitude = "";
    private String longitude = "";
    private String strgkstoredeliverytype = "";
    private String strmerchantlat = "";
    private String strmerchantlong = "";
    private String strmerchantaddress = "";
    private String strservicecharge = "";
    private String remarks = "";
    private String grossprice = "";
    private String servicecode = "";
    private int hasdiscount = 0;
    private String getstorefrom = "";
    private String passedordertxnno = "";

    private LinearLayout confirmcontainer;
    private String morderdetailsstr;

    //NO INTERNET CONNECTION
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private final boolean ifsucess = false;

    //CHECK STATUS
    private String ordertxnno = "";
    private boolean isStatusChecked = false;
    private final int totaldelaytime = 10000;
    private int currentdelaytime = 0;

    //DISCOUNT
    private String discount = "";

    //GK NEGOSYO
    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;
    private String resellerid = "";
    private String distributorid = "";
    private boolean checkIfReseller = false;

    //GPS TRACKER
    private GPSTracker tracker;

    private String sessionid;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gk_store_confirmation);

            init();

            initdata();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {

        aq = new AQuery(this);

        mdb = new DatabaseHandler(getViewContext());
        //initialize here
        maincontainer = findViewById(R.id.maincontainer);

        personalinformationcontainer = findViewById(R.id.personalinformationcontainer);
        orderdetailscontainer = findViewById(R.id.orderdetailscontainer);
        paymentdetailscontainer = findViewById(R.id.paymentdetailscontainer);

        confirmcontainer = findViewById(R.id.proceedcontainer);

        GKStoreAdapterRV = findViewById(R.id.gkstoredetailslist);
        GKStoreAdapterRV.setLayoutManager(new LinearLayoutManager(getViewContext()));
        GKStoreAdapterRV.setNestedScrollingEnabled(false);
        GKStoreAdapterRV.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null));
        gkadapter = new GKStoreConfirmationDetailsAdapter(getViewContext(), orderdetailslist);
        GKStoreAdapterRV.setAdapter(gkadapter);

        nointernetconnection = findViewById(R.id.nointernetconnection);
        refreshnointernet = findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        home_body = findViewById(R.id.home_body);

        scrollonTop();
    }

    private void initdata() {
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

        //set toolbar
        setupToolbar();

        getSupportActionBar().setTitle("");
        //get the passed parameters
        Bundle b = getIntent().getExtras();
        firstname = b.getString("GKSTOREFIRSTNAME");
        lastname = b.getString("GKSTORELASTNAME");
        mobileno = b.getString("GKSTOREMOBILENO");
        emailaddress = b.getString("GKSTOREEMAILADD");
        customeradd = b.getString("GKSTORECUSTOMERADD");
        vouchersession = b.getString("VOUCHERSESSION");
        amountopayvalue = b.getString("AMOUNT");
        discount = b.getString("DISCOUNT");
        otherdetails = b.getString("CUSTOMEROTHERDETAILS");
        gkorderdetails = b.getString("ORDERDETAILS");
        amountendered = b.getString("AMOUNTENDERED");
        amountchange = b.getString("CHANGE");
        merchantid = b.getString("MERCHANTID");
        storeid = b.getString("GKSTOREID");
        latitude = b.getString("LATITUDE");
        longitude = b.getString("LONGITUDE");
        borrowername = b.getString("GKSTOREBORROWERNAME");
        strgkstoredeliverytype = b.getString("GKSTOREDELIVERYTYPE");
        strmerchantlat = b.getString("GKSTOREMERCHANTLAT");
        strmerchantlong = b.getString("GKSTOREMERCHANTLONG");
        strmerchantaddress = b.getString("GKSTOREMERCHANTADD");
        strservicecharge = b.getString("GKSTOREMERCHANTSC");
        remarks = b.getString("GKSTOREREMARKS");
        grossprice = b.getString("GROSSPRICE");
        servicecode = b.getString("GKSERVICECODE");
        hasdiscount = b.getInt("GKHASDISCOUNT");

        getstorefrom = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_STORE_FROM);
        passedordertxnno = PreferenceUtils.getStringPreference(getViewContext(), "gkstoretxnno");

        Log.d("okhttp","ORDER DETAILS "+ gkorderdetails);

        onClickListeners();
        populateFields();
    }

    private void scrollonTop() {
        home_body.post(new Runnable() {
            public void run() {
                home_body.smoothScrollTo(0, 0);
            }
        });
    }

    private void populateFields() {

        try {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 15, 5, 0);

            LinearLayout.LayoutParams layoutParamsval = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsval.setMargins(5, 0, 5, 15);

            TextView gkfirstname = new TextView(this);
            gkfirstname.setText("Name");
            gkfirstname.setTextSize(14);
            gkfirstname.setAllCaps(true);
            gkfirstname.setPadding(0, 0, 0, 0);
            gkfirstname.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            gkfirstname.setLayoutParams(layoutParams);
            personalinformationcontainer.addView(gkfirstname, layoutParams);

            TextView gkfirstnameval = new TextView(this);
            gkfirstnameval.setText(firstname + " " + lastname);
            gkfirstnameval.setTextSize(16);
            gkfirstnameval.setAllCaps(true);
            gkfirstnameval.setPadding(0, 0, 0, 0);
            gkfirstnameval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
            gkfirstnameval.setTextColor(getResources().getColor(R.color.colorTextGrey));
            gkfirstnameval.setLayoutParams(layoutParamsval);
            personalinformationcontainer.addView(gkfirstnameval, layoutParamsval);


            TextView gkmobileno = new TextView(this);
            gkmobileno.setText("Mobile Number");
            gkmobileno.setTextSize(14);
            gkmobileno.setAllCaps(true);
            gkmobileno.setPadding(0, 0, 0, 0);
            gkmobileno.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            gkmobileno.setLayoutParams(layoutParams);
            personalinformationcontainer.addView(gkmobileno, layoutParams);

            TextView gkmobilenoval = new TextView(this);
            gkmobilenoval.setText("+" + mobileno);
            gkmobilenoval.setTextSize(16);
            gkmobilenoval.setPadding(0, 0, 0, 0);
            gkmobilenoval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
            gkmobilenoval.setTextColor(getResources().getColor(R.color.colorTextGrey));
            gkmobilenoval.setLayoutParams(layoutParamsval);
            personalinformationcontainer.addView(gkmobilenoval, layoutParamsval);

            TextView gkemailadd = new TextView(this);
            gkemailadd.setText("Email Address");
            gkemailadd.setTextSize(14);
            gkemailadd.setAllCaps(true);
            gkemailadd.setPadding(0, 0, 0, 0);
            gkemailadd.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            gkemailadd.setLayoutParams(layoutParams);
            personalinformationcontainer.addView(gkemailadd, layoutParams);

            TextView gkemailaddval = new TextView(this);
            gkemailaddval.setText(emailaddress);
            gkemailaddval.setTextSize(16);
            gkemailaddval.setPadding(0, 0, 0, 0);
            gkemailaddval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
            gkemailaddval.setTextColor(getResources().getColor(R.color.colorTextGrey));
            gkemailaddval.setLayoutParams(layoutParamsval);
            personalinformationcontainer.addView(gkemailaddval, layoutParamsval);

            switch (strgkstoredeliverytype) {
                case "PHILCARE": {
                    //Sets The address, latitude and longitude to the merchants/store.
                    customeradd = strmerchantaddress;
                    latitude = strmerchantlat;
                    longitude = strmerchantlong;
                    break;
                }
                case "PICKUP": {
                    TextView gkdeliverytype = new TextView(this);
                    gkdeliverytype.setText("Order Type");
                    gkdeliverytype.setTextSize(14);
                    gkdeliverytype.setAllCaps(true);
                    gkdeliverytype.setPadding(0, 0, 0, 0);
                    gkdeliverytype.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                    gkdeliverytype.setLayoutParams(layoutParams);
                    personalinformationcontainer.addView(gkdeliverytype, layoutParams);

                    TextView gkdeliverytypeval = new TextView(this);
                    gkdeliverytypeval.setText(strgkstoredeliverytype);
                    gkdeliverytypeval.setTextSize(16);
                    gkdeliverytypeval.setPadding(0, 0, 0, 0);
                    gkdeliverytypeval.setAllCaps(true);
                    gkdeliverytypeval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                    gkdeliverytypeval.setTextColor(getResources().getColor(R.color.colorTextGrey));
                    gkdeliverytypeval.setLayoutParams(layoutParamsval);
                    personalinformationcontainer.addView(gkdeliverytypeval, layoutParamsval);

                    //Sets The address, latitude and longitude to the merchants/store.
                    customeradd = strmerchantaddress;
                    latitude = strmerchantlat;
                    longitude = strmerchantlong;

                    TextView gkcustomeradd = new TextView(this);
                    gkcustomeradd.setText("Store Address");
                    gkcustomeradd.setTextSize(14);
                    gkcustomeradd.setAllCaps(true);
                    gkcustomeradd.setPadding(0, 0, 0, 0);
                    gkcustomeradd.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                    gkcustomeradd.setLayoutParams(layoutParams);
                    personalinformationcontainer.addView(gkcustomeradd, layoutParams);

                    TextView customeraddval = new TextView(this);
                    customeraddval.setText(CommonFunctions.replaceEscapeData(customeradd));
                    customeraddval.setTextSize(16);
                    customeraddval.setPadding(0, 0, 0, 0);
                    customeraddval.setAllCaps(true);
                    customeraddval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                    customeraddval.setTextColor(getResources().getColor(R.color.colorTextGrey));
                    customeraddval.setLayoutParams(layoutParamsval);
                    personalinformationcontainer.addView(customeraddval, layoutParamsval);

                    break;
                }
                default: {
                    TextView gkdeliverytype = new TextView(this);
                    gkdeliverytype.setText("Order Type");
                    gkdeliverytype.setTextSize(14);
                    gkdeliverytype.setAllCaps(true);
                    gkdeliverytype.setPadding(0, 0, 0, 0);
                    gkdeliverytype.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                    gkdeliverytype.setLayoutParams(layoutParams);
                    personalinformationcontainer.addView(gkdeliverytype, layoutParams);

                    TextView gkdeliverytypeval = new TextView(this);
                    gkdeliverytypeval.setText(strgkstoredeliverytype);
                    gkdeliverytypeval.setTextSize(16);
                    gkdeliverytypeval.setPadding(0, 0, 0, 0);
                    gkdeliverytypeval.setAllCaps(true);
                    gkdeliverytypeval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                    gkdeliverytypeval.setTextColor(getResources().getColor(R.color.colorTextGrey));
                    gkdeliverytypeval.setLayoutParams(layoutParamsval);
                    personalinformationcontainer.addView(gkdeliverytypeval, layoutParamsval);

                    TextView gkcustomeradd = new TextView(this);
                    gkcustomeradd.setText("Delivery Address");
                    gkcustomeradd.setTextSize(14);
                    gkcustomeradd.setAllCaps(true);
                    gkcustomeradd.setPadding(0, 0, 0, 0);
                    gkcustomeradd.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                    gkcustomeradd.setLayoutParams(layoutParams);
                    personalinformationcontainer.addView(gkcustomeradd, layoutParams);

                    TextView customeraddval = new TextView(this);
                    customeraddval.setText(CommonFunctions.replaceEscapeData(customeradd));
                    customeraddval.setTextSize(16);
                    customeraddval.setPadding(0, 0, 0, 0);
                    customeraddval.setAllCaps(true);
                    customeraddval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                    customeraddval.setTextColor(getResources().getColor(R.color.colorTextGrey));
                    customeraddval.setLayoutParams(layoutParamsval);
                    personalinformationcontainer.addView(customeraddval, layoutParamsval);
                    break;
                }
            }

            JSONArray otherdetailsarr = new JSONArray(otherdetails);
            if (otherdetailsarr.length() > 0) {

                for (int i = 0; i < otherdetailsarr.length(); i++) {
                    JSONObject jsonObj = otherdetailsarr.getJSONObject(i);
                    String name = jsonObj.getString("name");
                    String value = jsonObj.getString("value");
                    String description = jsonObj.getString("description");

                    if (!value.equals("")) {
                        if (storeid.equals("PHILCARE")) {
                            if (name.equals("MiddleName")) {
                                String textpassed = firstname + " " + value + " " + lastname;
                                gkfirstnameval.setText(textpassed);
                            } else {
                                TextView gkotherdetails = new TextView(this);
                                gkotherdetails.setText(name);
                                gkotherdetails.setTextSize(14);
                                gkotherdetails.setAllCaps(true);
                                gkotherdetails.setPadding(0, 0, 0, 0);
                                gkotherdetails.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                                gkotherdetails.setLayoutParams(layoutParams);
                                personalinformationcontainer.addView(gkotherdetails, layoutParams);

                                TextView gkotherdetailsval = new TextView(this);
                                gkotherdetailsval.setText(value);
                                gkotherdetailsval.setTextSize(16);
                                gkotherdetailsval.setPadding(0, 0, 0, 0);
                                gkotherdetailsval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                                gkotherdetailsval.setTextColor(getResources().getColor(R.color.colorTextGrey));
                                gkotherdetailsval.setLayoutParams(layoutParamsval);
                                personalinformationcontainer.addView(gkotherdetailsval, layoutParamsval);
                            }
                        } else {
                            TextView gkotherdetails = new TextView(this);
                            gkotherdetails.setText(name);
                            gkotherdetails.setTextSize(14);
                            gkotherdetails.setAllCaps(true);
                            gkotherdetails.setPadding(0, 0, 0, 0);
                            gkotherdetails.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                            gkotherdetails.setLayoutParams(layoutParams);
                            personalinformationcontainer.addView(gkotherdetails, layoutParams);
                            TextView gkotherdetailsval = new TextView(this);
                            gkotherdetailsval.setText(value);
                            gkotherdetailsval.setTextSize(16);
                            gkotherdetailsval.setPadding(0, 0, 0, 0);
                            gkotherdetailsval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                            gkotherdetailsval.setTextColor(getResources().getColor(R.color.colorTextGrey));
                            gkotherdetailsval.setLayoutParams(layoutParamsval);
                            personalinformationcontainer.addView(gkotherdetailsval, layoutParamsval);
                        }
                    }
                }
            }

            orderdetailslist = new Gson().fromJson(gkorderdetails, new TypeToken<ArrayList<GKStoreProducts>>() {
            }.getType());

            TextView gkcurrency = new TextView(this);
            gkcurrency.setText("Currency");
            gkcurrency.setTextSize(14);
            gkcurrency.setAllCaps(true);
            gkcurrency.setPadding(0, 0, 0, 0);
            gkcurrency.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            gkcurrency.setLayoutParams(layoutParams);
            paymentdetailscontainer.addView(gkcurrency, layoutParams);

            TextView gkcurrencyval = new TextView(this);
            gkcurrencyval.setText("Php");
            gkcurrencyval.setTextSize(16);
            gkcurrencyval.setPadding(0, 0, 0, 0);
            gkcurrencyval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
            gkcurrencyval.setTextColor(getResources().getColor(R.color.colorTextGrey));
            gkcurrencyval.setLayoutParams(layoutParamsval);
            paymentdetailscontainer.addView(gkcurrencyval, layoutParamsval);


            TextView gkamount = new TextView(this);
            gkamount.setText("Amount");
            gkamount.setTextSize(14);
            gkamount.setAllCaps(true);
            gkamount.setPadding(0, 0, 0, 0);
            gkamount.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            gkamount.setLayoutParams(layoutParams);
            paymentdetailscontainer.addView(gkamount, layoutParams);

            Log.d("okhttp","SERVICE CHARGE: "+strservicecharge);

            TextView gkamountval = new TextView(this);
            if (!strservicecharge.equals("") && !strservicecharge.equals(".") || Double.parseDouble(strservicecharge) > 0) {
                double dblgrossprice = Double.parseDouble(amountopayvalue);
                double dbldeliverycharge = Double.parseDouble(strservicecharge);
                String originalprice = String.valueOf(dblgrossprice - dbldeliverycharge);
                gkamountval.setText(CommonFunctions.currencyFormatter(originalprice));
            }else {
                gkamountval.setText(CommonFunctions.currencyFormatter(grossprice));
            }
            gkamountval.setTextSize(16);
            gkamountval.setPadding(0, 0, 0, 0);
            gkamountval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
            gkamountval.setTextColor(getResources().getColor(R.color.colorTextGrey));
            gkamountval.setLayoutParams(layoutParamsval);
            paymentdetailscontainer.addView(gkamountval, layoutParamsval);


            if (strgkstoredeliverytype.equals("DELIVERY")) {
                TextView gkservicecharge = new TextView(this);
                gkservicecharge.setText("Delivery Charge");
                gkservicecharge.setTextSize(14);
                gkservicecharge.setAllCaps(true);
                gkservicecharge.setPadding(0, 0, 0, 0);
                gkservicecharge.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                gkservicecharge.setLayoutParams(layoutParams);
                paymentdetailscontainer.addView(gkservicecharge, layoutParams);

                TextView gkservicechargeval = new TextView(this);
                gkservicechargeval.setText(strservicecharge);
                gkservicechargeval.setTextSize(16);
                gkservicechargeval.setPadding(0, 0, 0, 0);
                gkservicechargeval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                gkservicechargeval.setTextColor(getResources().getColor(R.color.colorTextGrey));
                gkservicechargeval.setLayoutParams(layoutParamsval);
                paymentdetailscontainer.addView(gkservicechargeval, layoutParamsval);
            }


            TextView gkamountdiscount = new TextView(this);
            gkamountdiscount.setText("Reseller Discount");
            gkamountdiscount.setTextSize(14);
            gkamountdiscount.setAllCaps(true);
            gkamountdiscount.setPadding(0, 0, 0, 0);
            gkamountdiscount.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            gkamountdiscount.setLayoutParams(layoutParams);
            paymentdetailscontainer.addView(gkamountdiscount, layoutParams);

            TextView gkamountdiscountval = new TextView(this);
            gkamountdiscountval.setText(CommonFunctions.currencyFormatter(discount));
            gkamountdiscountval.setTextSize(16);
            gkamountdiscountval.setPadding(0, 0, 0, 0);
            gkamountdiscountval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
            gkamountdiscountval.setTextColor(getResources().getColor(R.color.colorTextGrey));
            gkamountdiscountval.setLayoutParams(layoutParamsval);
            paymentdetailscontainer.addView(gkamountdiscountval, layoutParamsval);


            TextView gkamounttopay = new TextView(this);
            gkamounttopay.setText("Amount to Pay");
            gkamounttopay.setTextSize(14);
            gkamounttopay.setAllCaps(true);
            gkamounttopay.setPadding(0, 0, 0, 0);
            gkamounttopay.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            gkamounttopay.setLayoutParams(layoutParams);
            paymentdetailscontainer.addView(gkamounttopay, layoutParams);

            TextView gkamounttopayval = new TextView(this);
            gkamounttopayval.setText(CommonFunctions.currencyFormatter(amountopayvalue));
            gkamounttopayval.setTextSize(16);
            gkamounttopayval.setPadding(0, 0, 0, 0);
            gkamounttopayval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
            gkamounttopayval.setTextColor(getResources().getColor(R.color.colorTextGrey));
            gkamounttopayval.setLayoutParams(layoutParamsval);
            paymentdetailscontainer.addView(gkamounttopayval, layoutParamsval);


            TextView gkamountendered = new TextView(this);
            gkamountendered.setText("Amount Tendered");
            gkamountendered.setTextSize(14);
            gkamountendered.setAllCaps(true);
            gkamountendered.setPadding(0, 0, 0, 0);
            gkamountendered.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            gkamountendered.setLayoutParams(layoutParams);
            paymentdetailscontainer.addView(gkamountendered, layoutParams);

            TextView gkamounttenderedval = new TextView(this);
            gkamounttenderedval.setText(CommonFunctions.currencyFormatter(amountendered));
            gkamounttenderedval.setTextSize(16);
            gkamounttenderedval.setPadding(0, 0, 0, 0);
            gkamounttenderedval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
            gkamounttenderedval.setTextColor(getResources().getColor(R.color.colorTextGrey));
            gkamounttenderedval.setLayoutParams(layoutParamsval);
            paymentdetailscontainer.addView(gkamounttenderedval, layoutParamsval);

            TextView gkamountchange = new TextView(this);
            gkamountchange.setText("Change");
            gkamountchange.setTextSize(14);
            gkamountchange.setAllCaps(true);
            gkamountchange.setPadding(0, 0, 0, 0);
            gkamountchange.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            gkamountchange.setLayoutParams(layoutParams);
            paymentdetailscontainer.addView(gkamountchange, layoutParams);

            TextView gkamountchangeval = new TextView(this);
            gkamountchangeval.setText(CommonFunctions.currencyFormatter(String.valueOf(amountchange)));
            gkamountchangeval.setTextSize(16);
            gkamountchangeval.setPadding(0, 0, 0, 0);
            gkamountchangeval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
            gkamountchangeval.setTextColor(getResources().getColor(R.color.colorTextGrey));
            gkamountchangeval.setLayoutParams(layoutParamsval);
            paymentdetailscontainer.addView(gkamountchangeval, layoutParamsval);

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }

    }

    private <T> Iterable<T> iterate(final Iterator<T> i) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return i;
            }
        };
    }

    private void onClickListeners() {
        confirmcontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                //payRequestDialog();
                payRequestDialogNew();
            }
        });
    }

    private void payRequestDialog() {
        MaterialDialog materialDialog = new MaterialDialog.Builder(getViewContext())
                .content("You are about to pay your request.")
                .cancelable(false)
                .negativeText("Close")
                .positiveText("Proceed")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        currentdelaytime = 0;
                        isStatusChecked = false;
                        getSession();
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                })
                .show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, materialDialog.getView());
    }

    private void payRequestDialogNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("Notice", "You are about to pay your request.",
                "CLOSE", "PROCEED", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        dialog.hideCloseImageButton();

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
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                dialog.dismiss();
                currentdelaytime = 0;
                isStatusChecked = false;
                getSession();
            }
        });
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    private void showContent(boolean isShow) {
        if (isShow) {
            home_body.setVisibility(View.VISIBLE);
        } else {
            home_body.setVisibility(View.GONE);
        }
    }

    private void converttoList() throws JSONException {

        morderdetailsstr = gkorderdetails;
        ArrayList<GKStoreProducts> gkstorelist = new ArrayList<>();
        List<GenericObject> genericobject = new ArrayList<>();

        gkstorelist = new Gson().fromJson(morderdetailsstr, new TypeToken<ArrayList<GKStoreProducts>>() {
        }.getType());

        if (getstorefrom.trim().equals("HISTORY")) {
            for (GKStoreProducts gkStoreProducts : gkstorelist) {
                String productid = gkStoreProducts.getProductID();
                int quantity = gkStoreProducts.getOrderQuantity();
                String productimage = gkStoreProducts.getProductImage();
                genericobject.add(new GenericObject(productid, quantity, productimage));
            }
        } else {
            for (GKStoreProducts gkStoreProducts : gkstorelist) {
                String productid = gkStoreProducts.getProductID();
                int quantity = gkStoreProducts.getQuantity();
                String productimage = gkStoreProducts.getProductImage();
                genericobject.add(new GenericObject(productid, quantity, productimage));
            }
        }

        morderdetailsstr = new Gson().toJson(genericobject);

    }

    private void showGKStoreSuccessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        boolean isGKNegosyoModal;
        isGKNegosyoModal = !checkIfReseller;

        dialog.createDialog("SUCCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                isGKNegosyoModal, true, DialogTypeEnum.SUCCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });

    }

    private void showGKStoreFailedDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("FAILED", message,
                "Retry", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.returntoBeforePayments();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.returntoBeforePayments();
            }
        });
    }

    private void showGKStoreTimeOutDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("TIMEOUT", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.TIMEOUT);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });
    }

    private void showGKStoreOnProcessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("ON PROCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.ONPROCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });
    }

    //-----------------------------------API---------------------------------
    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showNoInternetConnection(false);
            showContent(true);
            setUpProgressLoader();

            if (storeid.equals("PHILCARE")) {
                if (isStatusChecked) {
                    setUpProgressLoaderMessageDialog("Checking status, Please wait...");
                    //checkGkStoreStatusQue(checkGKStoreStatusQueResponseCallback);
                    checkGKStoreStatusQueV2();
                } else {
                    setUpProgressLoaderMessageDialog("Processing request, Please wait...");
                    //processGKStoreOrder(processGKStoreSession);
                    processGKStoreOrderV4();
                }
            } else {
                final Handler handlerstatus = new Handler();
                handlerstatus.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setUpProgressLoaderMessageDialog("Checking status, Please wait...");
                    }
                }, 1000);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentdelaytime = currentdelaytime + 1000;
                        setUpProgressLoaderMessageDialog("Processing request, Please wait...");
                        //processGKStoreOrder(processGKStoreSession);
                        processGKStoreOrderV4();
                    }
                }, 1000);
            }
        } else {
            setUpProgressLoaderDismissDialog();
            showNoInternetConnection(true);
            showContent(false);
            showNoInternetToast();
        }
    }

    private void processGKStoreOrder(Callback<ProcessGkStoreOrderResponse> processGKStoreOrderResponseCallback) {
        try {
            converttoList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        Call<ProcessGkStoreOrderResponse> processgkstoreorder = RetrofitBuild.getGKStoreService(getViewContext())
                .processGKStoreOrder(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        borrowername,
                        merchantid,
                        storeid,
                        firstname,
                        lastname,
                        mobileno,
                        emailaddress,
                        customeradd,
                        otherdetails,
                        morderdetailsstr,
                        vouchersession,
                        latitude,
                        longitude,
                        remarks,
                        strgkstoredeliverytype,
                        servicecode,
                        grossprice,
                        hasdiscount,
                        passedordertxnno
                );
        processgkstoreorder.enqueue(processGKStoreOrderResponseCallback);
    }

    private final Callback<ProcessGkStoreOrderResponse> processGKStoreSession = new Callback<ProcessGkStoreOrderResponse>() {

        @Override
        public void onResponse(Call<ProcessGkStoreOrderResponse> call, Response<ProcessGkStoreOrderResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    ordertxnno = response.body().getOrdertxnno();
                    if (storeid.equals("PHILCARE")) {
                        //check transaction status here
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentdelaytime = currentdelaytime + 1000;
                                isStatusChecked = true;
                                getSession();
                            }
                        }, 1000);
                    } else {
                        checkIfReseller = !distributorid.equals("") && !distributorid.equals(".")
                                && !resellerid.equals("") && !resellerid.equals(".");
                        setUpProgressLoaderDismissDialog();
                        //showGlobalDialogs(response.body().getMessage(), "close", ButtonTypeEnum.SINGLE, checkIfReseller, false, DialogTypeEnum.SUCCESS);
                        showGKStoreSuccessDialog(response.body().getMessage());
                    }

                } else {
                    currentdelaytime = 0;
                    setUpProgressLoaderDismissDialog();
                    //showGlobalDialogs(response.body().getMessage(), "retry", ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);
                    showGKStoreFailedDialog(response.body().getMessage());

                }
            } else {
                setUpProgressLoaderDismissDialog();
                showErrorGlobalDialogs(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<ProcessGkStoreOrderResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            showNoInternetConnection(true);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void checkGkStoreStatusQue(Callback<CheckGKStoreStatusQueResponse> processGKStoreOrderResponseCallback) {
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        Call<CheckGKStoreStatusQueResponse> checkgkstorestatus = RetrofitBuild.getGKStoreService(getViewContext())
                .checkGkStoreStatusQue(sessionid,
                        imei,
                        userid,
                        authcode,
                        borrowerid,
                        ordertxnno,
                        storeid
                );
        checkgkstorestatus.enqueue(processGKStoreOrderResponseCallback);
    }

    private final Callback<CheckGKStoreStatusQueResponse> checkGKStoreStatusQueResponseCallback = new Callback<CheckGKStoreStatusQueResponse>() {

        @Override
        public void onResponse(Call<CheckGKStoreStatusQueResponse> call, Response<CheckGKStoreStatusQueResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    checkIfReseller = !distributorid.equals("") && !distributorid.equals(".")
                            && !resellerid.equals("") && !resellerid.equals(".");
                    String status = response.body().getTxnStatus();

                    if (status != null) {
                        if (response.body().getTxnStatus().equals("COMPLETED")) {
                            setUpProgressLoaderDismissDialog();
                            //showGlobalDialogs(response.body().getRemarks(), "close", ButtonTypeEnum.SINGLE, checkIfReseller, true, DialogTypeEnum.SUCCESS);
                            showGKStoreSuccessDialog(response.body().getRemarks());

                        } else if (response.body().getTxnStatus().equals("TIMEOUT")) {
                            setUpProgressLoaderDismissDialog();
                            //showGlobalDialogs(response.body().getRemarks(), "close", ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.TIMEOUT);
                            showGKStoreTimeOutDialog(response.body().getRemarks());
                        } else {
                            setUpProgressLoaderDismissDialog();
                            //showGlobalDialogs(response.body().getRemarks(), "retry", ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);
                            showGKStoreFailedDialog(response.body().getRemarks());
                        }
                    } else {
                        showErrorGlobalDialogs("Something went wrong. Please try again.");
                    }
                } else if (response.body().getStatus().equals("005")) {
                    if (currentdelaytime < totaldelaytime) {
                        //check transaction status here
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentdelaytime = currentdelaytime + 1000;
                                //checkGkStoreStatusQue(checkGKStoreStatusQueResponseCallback);
                            }
                        }, 1000);
                    } else {
                        setUpProgressLoaderDismissDialog();
                        //showGlobalDialogs(response.body().getRemarks(), "close", ButtonTypeEnum.SINGLE,false, false, DialogTypeEnum.ONPROCESS);
                        showGKStoreOnProcessDialog(response.body().getRemarks());
                    }
                } else if (response.body().getStatus().equals("420")) {
                    if (currentdelaytime < totaldelaytime) {
                        //check transaction status here
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentdelaytime = currentdelaytime + 1000;
                                //checkGkStoreStatusQue(checkGKStoreStatusQueResponseCallback);
                            }
                        }, 1000);
                    } else {
                        setUpProgressLoaderDismissDialog();
                        //showGlobalDialogs(response.body().getRemarks(), "close", ButtonTypeEnum.SINGLE,false, false, DialogTypeEnum.ONPROCESS);
                        showGKStoreOnProcessDialog(response.body().getRemarks());
                    }
                } else {
                    setUpProgressLoaderDismissDialog();
                    currentdelaytime = 0;
                    //showGlobalDialogs("FAILED TRANSACTION", "retry", ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);
                    showGKStoreFailedDialog("FAILED TRANSACTION");
                }
            } else {
                setUpProgressLoaderDismissDialog();
                showErrorGlobalDialogs(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<CheckGKStoreStatusQueResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            showNoInternetConnection(true);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        if (gkadapter != null) {
            orderdetailslist = new Gson().fromJson(gkorderdetails, new TypeToken<List<GKStoreProducts>>() {
            }.getType());
            gkadapter.updateData(orderdetailslist);
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshnointernet: {
                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                    showNoInternetConnection(false);
                    showContent(true);
                } else {
                    showNoInternetConnection(true);
                    showContent(false);
                    showWarningToast();
                }
                break;
            }
        }
    }

    /*
     * SECURITY UPDATE
     * AS OF
     * JANUARY 2020
     * */
    private void processGKStoreOrderV4() {
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
                parameters.put("firstname", firstname);
                parameters.put("lastname", lastname);
                parameters.put("mobileno", mobileno);
                parameters.put("emailaddress", emailaddress);
                parameters.put("address", customeradd);
                parameters.put("otherdetails", otherdetails);
                parameters.put("orderdetails", morderdetailsstr);
                parameters.put("vouchersession", vouchersession);
                parameters.put("remarks", remarks);
                parameters.put("ordertype", strgkstoredeliverytype);
                parameters.put("hasdiscount", String.valueOf(hasdiscount));
                parameters.put("servicecode", servicecode);
                parameters.put("grossamount", grossprice);
                parameters.put("longitude", longitude);
                parameters.put("latitude", latitude);
                parameters.put("ordertxnno", passedordertxnno);
                parameters.put("devicetype", CommonVariables.devicetype);

                Log.d("checkhttp","VALUE OF LIFE" + parameters);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "processGKStoreOrderV4");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                processGKStoreOrderV4Object();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            showNoInternetToast();
        }

    }

    private void processGKStoreOrderV4Object() {
        Call<GenericResponse> call = RetrofitBuilder.getGKStoreV2API(getViewContext())
                .processGKStoreOrderV4(
                        authenticationid, sessionid, param
                );
        call.enqueue(processGKStoreOrderV4CallBack);
    }

    private final Callback<GenericResponse> processGKStoreOrderV4CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();

                if (errorBody == null) {
                    String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            ordertxnno = decryptedData;
                            if (storeid.equals("PHILCARE")) {
                                //check transaction status here
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        currentdelaytime = currentdelaytime + 1000;
                                        isStatusChecked = true;
                                        getSession();
                                    }
                                }, 1000);
                            } else {
                                checkIfReseller = !distributorid.equals("") && !distributorid.equals(".")
                                        && !resellerid.equals("") && !resellerid.equals(".");
                                setUpProgressLoaderDismissDialog();
                                showGKStoreSuccessDialog(decryptedMessage);
                            }
                        }

                    } else {
                        setUpProgressLoaderDismissDialog();
                        if (response.body().getStatus().equals("error")) {
                            showErrorToast();
                        } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        } else {
                            currentdelaytime = 0;
                            showGKStoreFailedDialog(decryptedMessage);
                        }
                    }
                } else {
                    setUpProgressLoaderDismissDialog();
                    showErrorToast();
                }
            } catch (Exception e) {
                e.printStackTrace();
                setUpProgressLoaderDismissDialog();
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            showErrorToast();
        }
    };

    private void checkGKStoreStatusQueV2() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("ordertxnno", ordertxnno);
            parameters.put("gkstoreid", storeid);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "checkGKStoreStatusQueV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            checkGKStoreStatusQueV2Object();
        } else {
            showNoInternetToast();
        }

    }

    private void checkGKStoreStatusQueV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getGKStoreV2API(getViewContext())
                .checkGKStoreStatusQueV2(
                        authenticationid, sessionid, param
                );
        call.enqueue(checkGKStoreStatusQueV2CallBack);
    }

    private final Callback<GenericResponse> checkGKStoreStatusQueV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();

                if (errorBody == null) {
                    String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            setUpProgressLoaderDismissDialog();

                            checkIfReseller = !distributorid.equals("") && !distributorid.equals(".")
                                    && !resellerid.equals("") && !resellerid.equals(".");

                            if (decryptedData.equals("COMPLETED")) {
                                showGKStoreSuccessDialog(decryptedMessage);
                            } else if (decryptedData.equals("TIMEOUT")) {
                                showGKStoreTimeOutDialog(decryptedMessage);
                            } else {
                                showGKStoreFailedDialog(decryptedMessage);
                            }
                        }

                    } else {
                        if (response.body().getStatus().equals("error")) {
                            setUpProgressLoaderDismissDialog();
                            showErrorToast();
                        } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                            setUpProgressLoaderDismissDialog();
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        } else if (response.body().getStatus().equals("420")) {
                            if (currentdelaytime < totaldelaytime) {
                                //check transaction status here
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        currentdelaytime = currentdelaytime + 1000;
                                        checkGKStoreStatusQueV2();
                                    }
                                }, 1000);
                            } else {
                                setUpProgressLoaderDismissDialog();
                                showGKStoreOnProcessDialog(decryptedMessage);
                            }
                        } else {
                            setUpProgressLoaderDismissDialog();
                            currentdelaytime = 0;
                            showGKStoreFailedDialog(decryptedMessage);
                        }
                    }
                } else {
                    setUpProgressLoaderDismissDialog();
                    showErrorToast();
                }
            } catch (Exception e) {
                e.printStackTrace();
                setUpProgressLoaderDismissDialog();
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            showErrorToast();
        }
    };


}
