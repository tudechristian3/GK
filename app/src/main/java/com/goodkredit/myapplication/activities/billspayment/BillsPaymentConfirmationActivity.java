package com.goodkredit.myapplication.activities.billspayment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.androidquery.util.Common;
import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoRedirectionActivity;
import com.goodkredit.myapplication.activities.transactions.ViewOthersTransactionsActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillsPaymentConfirmationActivity extends BaseActivity implements View.OnClickListener {

    private DatabaseHandler db;
    private String amountopayvalue = "";
    private String spbillercode = "";
    private String billername = "";
    private String billerparamdata = "";
    private String vouchersession = "";
    private String sessionid = "";
    private String imei = "";
    private String borrowerid = "";
    private String userid = "";
    private String servicecharge = "0";
    private String othercharges = "0";
    private String amountendered = "0";
    private String amountchange = "0";

    private LinearLayout liniarlyout;
    private Dialog dialog;
    private Dialog progressdialog;
    private TextView popsuccessclose;
    private TextView popaddtomybiller;

    private String resbillercode = "";
    private String restransactionno = "";
    private String resspbillercode = "";
    private String resaccountno = "";
    private boolean istimelimitreach = false;
    private boolean isresultget = false;
    private int totaldelaytime = 10000;
    private int currentdelaytime = 0;

    private boolean isPPV = false;
    private boolean isNewApplication = false;
    private MaterialDialog mDialog;
    private TextView txvMessage;

    //DISCOUNT
    private String discount = "";
    private String grossprice = "";
    private String servicecode = "";
    private int hasdiscount = 0;

    //ADVANCE OPTIONS
    private String receiptmobileno = "";
    private String receiptemailadd = "";

    //GK NEGOSYO
    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;
    private String distributorid = "";
    private String resellerid = "";

    //LONGLATS
    private String latitude;
    private String longitude;

    //GPS TRACKER
    private GPSTracker tracker;

    private boolean checkIfReseller = false;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String billsIndex;
    private String billsAuthenticationid;
    private String billsKeyEncryption;
    private String billsParam;

    private String billStatusIndex;
    private String billStatusAuthID;
    private String billStatusKeyEncryption;
    private String billStatusParam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_payment_confirmation);


        //initialize here
        db = new DatabaseHandler(this);
        liniarlyout = findViewById(R.id.maincontainer);

        //set toolbar
        setupToolbar();

        //get the passed parameters
        Bundle b = getIntent().getExtras();
        amountopayvalue = b.getString(("AMOUNT"));
        spbillercode = b.getString("SPBILLERCODE");
        billername = b.getString("BILLERNAME");
        billerparamdata = b.getString("BILLERPARAMDATA");
        vouchersession = b.getString("VOUCHERSESSION");
        amountendered = b.getString("AMOUNTENDERED");
        amountchange = b.getString("CHANGE");
        servicecharge = b.getString("SERVICECHARGE");
        othercharges = b.getString("OTHERCHARGES");
        isNewApplication = b.getBoolean("NEWAPPLICATION", false);
        discount = b.getString("DISCOUNT");
        grossprice = b.getString("GROSSPRICE");
        servicecode = b.getString("GKSERVICECODE");
        hasdiscount = b.getInt("GKHASDISCOUNT");
        receiptmobileno = b.getString("RECEIPTMOBILENO");
        receiptemailadd = b.getString("RECEIPTEMAILADD");
        latitude = b.getString("LATITUDE");
        longitude = b.getString("LONGITUDE");

        isPPV = b.getBoolean("PPV", false);

        if (isNewApplication || isPPV) {
            setUpSkycableDialog();
        }

        sessionid = PreferenceUtils.getSessionID(getViewContext());

        //get account information
        getAccountInfo(db);

        populateFields();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /****************
     * FUNCTIONS
     * ******************/

    private void getAccountInfo(DatabaseHandler db) {
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));

            } while (cursor.moveToNext());
        }
        cursor.close();

        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);
    }

    private void populateFields() {

        try {

            JSONArray jsonArr = new JSONArray(billerparamdata);


            if (jsonArr.length() > 0) {
                TableLayout tl = new TableLayout(this);
                tl.setBackgroundResource(R.color.colorwhite);

                LinearLayout.LayoutParams rowlayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                TableRow.LayoutParams textrow = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.6f);
                textrow.weight = 0.5f;

                TableRow billerrow = new TableRow(this);
                billerrow.setBackgroundResource(R.color.colorwhite);
                billerrow.setPadding(10, 50, 10, 10);
                billerrow.setLayoutParams(rowlayout);


                TextView blname = new TextView(this);
                blname.setText("BILLER");
                blname.setTextSize(15);
                blname.setPadding(10, 0, 0, 50);
                blname.setGravity(Gravity.RIGHT);
                blname.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                blname.setLayoutParams(textrow);
                billerrow.addView(blname);


                TextView blval = new TextView(this);
                blval.setText(billername);
                blval.setTextSize(16);
                blval.setPadding(40, 0, 0, 0);
                blval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                blval.setTextColor(Color.BLACK);
                blval.setLayoutParams(textrow);
                billerrow.addView(blval);

                tl.addView(billerrow);

                if (!receiptmobileno.trim().equals(".") && !receiptmobileno.trim().equals("")) {

                    TableRow twreceiptmobileno = new TableRow(this);
                    twreceiptmobileno.setBackgroundResource(R.color.colorwhite);
                    twreceiptmobileno.setPadding(10, 10, 10, 10);
                    twreceiptmobileno.setLayoutParams(rowlayout);

                    TextView tvreceiptmobileno = new TextView(this);
                    tvreceiptmobileno.setText("Recipient Mobile No");
                    tvreceiptmobileno.setTextSize(15);
                    tvreceiptmobileno.setAllCaps(true);
                    tvreceiptmobileno.setPadding(10, 0, 0, 50);
                    tvreceiptmobileno.setGravity(Gravity.RIGHT);
                    tvreceiptmobileno.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                    tvreceiptmobileno.setLayoutParams(textrow);
                    twreceiptmobileno.addView(tvreceiptmobileno);

                    TextView tvreceiptmobilenoval = new TextView(this);
                    tvreceiptmobilenoval.setText("+" + receiptmobileno);
                    tvreceiptmobilenoval.setTextSize(16);
                    tvreceiptmobilenoval.setPadding(40, 0, 0, 0);
                    tvreceiptmobilenoval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                    tvreceiptmobilenoval.setTextColor(Color.BLACK);
                    tvreceiptmobilenoval.setLayoutParams(textrow);
                    twreceiptmobileno.addView(tvreceiptmobilenoval);

                    tl.addView(twreceiptmobileno);
                }


                if (!receiptemailadd.trim().equals(".") && !receiptemailadd.trim().equals("")) {
                    TableRow twreceiptemailadd = new TableRow(this);
                    twreceiptemailadd.setBackgroundResource(R.color.colorwhite);
                    twreceiptemailadd.setPadding(10, 10, 10, 10);
                    twreceiptemailadd.setLayoutParams(rowlayout);

                    TextView tvreceiptemailadd = new TextView(this);
                    tvreceiptemailadd.setText("Recipient Email Add");
                    tvreceiptemailadd.setTextSize(15);
                    tvreceiptemailadd.setAllCaps(true);
                    tvreceiptemailadd.setPadding(10, 0, 0, 50);
                    tvreceiptemailadd.setGravity(Gravity.RIGHT);
                    tvreceiptemailadd.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                    tvreceiptemailadd.setLayoutParams(textrow);
                    twreceiptemailadd.addView(tvreceiptemailadd);

                    TextView tvreceiptemailaddval = new TextView(this);
                    tvreceiptemailaddval.setText(receiptemailadd);
                    tvreceiptemailaddval.setTextSize(16);
                    tvreceiptemailaddval.setPadding(40, 0, 0, 0);
                    tvreceiptemailaddval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                    tvreceiptemailaddval.setTextColor(Color.BLACK);
                    tvreceiptemailaddval.setLayoutParams(textrow);
                    twreceiptemailadd.addView(tvreceiptemailaddval);

                    tl.addView(twreceiptemailadd);
                }

                double conveniencefee = Double.parseDouble(servicecharge) + Double.parseDouble(othercharges);

                TableRow twamount = new TableRow(this);
                twamount.setBackgroundResource(R.color.colorwhite);
                twamount.setPadding(10, 10, 10, 10);
                twamount.setLayoutParams(rowlayout);

                TextView tvamount = new TextView(this);
                tvamount.setText("AMOUNT");
                tvamount.setTextSize(15);
                tvamount.setPadding(10, 0, 0, 50);
                tvamount.setGravity(Gravity.RIGHT);
                tvamount.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                tvamount.setLayoutParams(textrow);
                twamount.addView(tvamount);

                TextView tvamountval = new TextView(this);
                if (!String.valueOf(conveniencefee).equals("") && !String.valueOf(conveniencefee).equals(".")) {
                    double dblgrossprice = Double.parseDouble(grossprice);
                    double dblservicecharge = Double.parseDouble(String.valueOf(conveniencefee));
                    String originalprice = String.valueOf(dblgrossprice - dblservicecharge);
                    tvamountval.setText(CommonFunctions.currencyFormatter(originalprice));
                } else {
                    tvamountval.setText(CommonFunctions.currencyFormatter(grossprice));
                }

                tvamountval.setTextSize(16);
                tvamountval.setPadding(40, 0, 0, 0);
                tvamountval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                tvamountval.setTextColor(Color.BLACK);
                tvamountval.setLayoutParams(textrow);
                twamount.addView(tvamountval);
                tl.addView(twamount);


                //Convenience Charge
                TableRow twcharge = new TableRow(this);
                twcharge.setBackgroundResource(R.color.colorwhite);
                twcharge.setPadding(10, 10, 10, 10);
                twcharge.setLayoutParams(rowlayout);

                TextView tvcharge = new TextView(this);
                tvcharge.setText("SERVICE CHARGE");
                tvcharge.setTextSize(15);
                tvcharge.setPadding(10, 0, 0, 50);
                tvcharge.setGravity(Gravity.RIGHT);
                tvcharge.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                tvcharge.setLayoutParams(textrow);
                twcharge.addView(tvcharge);

                TextView tvchargeval = new TextView(this);
                tvchargeval.setText(CommonFunctions.currencyFormatter(Double.toString(conveniencefee)));
                tvchargeval.setTextSize(16);
                tvchargeval.setPadding(40, 0, 0, 0);
                tvchargeval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                tvchargeval.setTextColor(Color.BLACK);
                tvchargeval.setLayoutParams(textrow);
                twcharge.addView(tvchargeval);

                tl.addView(twcharge);

                //OThers Charge
//                TableRow twothercharge = new TableRow(this);
//                twothercharge.setBackgroundResource(R.color.colorwhite);
//                twothercharge.setPadding(10,10,10,10);
//                twothercharge.setLayoutParams(rowlayout);
//
//                TextView tvothercharge = new TextView(this);
//                tvothercharge.setText("OTHERS CHARGES");
//                tvothercharge.setTextSize(16);
//                tvothercharge.setPadding(10,0,0,50);
//                tvothercharge.setGravity(Gravity.RIGHT);
//                tvothercharge.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
//                twothercharge.addView(tvothercharge);
//
//                TextView tvotherchargeval = new TextView(this);
//                tvotherchargeval.setText(cf.currencyFormatter(othercharges));
//                tvotherchargeval.setTextSize(16);
//                tvotherchargeval.setPadding(10,0,10,0);
//                tvotherchargeval.setGravity(Gravity.LEFT);
//                tvotherchargeval.setTypeface(Typeface.DEFAULT_BOLD);
//                tvotherchargeval.setTextColor(Color.BLACK);
//                twothercharge.addView(tvotherchargeval);
//
//                tl.addView(twothercharge);

                TableRow twdiscount = new TableRow(this);
                twdiscount.setBackgroundResource(R.color.colorwhite);
                twdiscount.setPadding(10, 10, 10, 10);
                twdiscount.setLayoutParams(rowlayout);

                TextView tvdiscount = new TextView(this);
                tvdiscount.setText("RESELLER DISCOUNT");
                tvdiscount.setTextSize(15);
                tvdiscount.setPadding(10, 0, 0, 50);
                tvdiscount.setGravity(Gravity.RIGHT);
                tvdiscount.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                tvdiscount.setLayoutParams(textrow);
                twdiscount.addView(tvdiscount);

                TextView tvdiscountval = new TextView(this);
                tvdiscountval.setText(CommonFunctions.currencyFormatter(discount));
                tvdiscountval.setTextSize(16);
                tvdiscountval.setPadding(40, 0, 0, 0);
                tvdiscountval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                tvdiscountval.setTextColor(Color.BLACK);
                tvdiscountval.setLayoutParams(textrow);
                twdiscount.addView(tvdiscountval);

                tl.addView(twdiscount);


                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    String name = jsonObj.getString("name");
                    String value = jsonObj.getString("value");
                    String description = jsonObj.getString("description");
                    String datatype = jsonObj.getString("datatype");

                    TableRow tb = new TableRow(this);
                    tb.setBackgroundResource(R.color.colorwhite);
                    tb.setPadding(10, 10, 10, 10);
                    tb.setLayoutParams(rowlayout);

                    TextView tv = new TextView(this);
                    tv.setText(description);
                    tv.setTextSize(15);
                    tv.setPadding(10, 0, 0, 50);
                    tv.setGravity(Gravity.RIGHT);
                    tv.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                    tv.setLayoutParams(textrow);
                    tb.addView(tv);

                    if (datatype.toUpperCase().equals("MONEY")) {
                        value = CommonFunctions.currencyFormatter(value);
                    }

                    TextView tv1 = new TextView(this);
                    if (name.equals("AmountPaid")) {
                        if (!String.valueOf(conveniencefee).equals("") && !String.valueOf(conveniencefee).equals(".")) {
                            double dblgrossprice = Double.parseDouble(grossprice);
                            double dbldiscount = Double.parseDouble(discount);
                            String combinedprice = String.valueOf(dblgrossprice - dbldiscount);
                            tv1.setText(CommonFunctions.currencyFormatter(combinedprice));
                        } else {
                            tv1.setText(CommonFunctions.currencyFormatter(value));
                        }
                    } else {
                        tv1.setText(value);
                    }
                    tv1.setTextSize(16);
                    tv1.setPadding(40, 0, 0, 0);
                    tv1.setGravity(Gravity.LEFT);
                    tv1.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                    tv1.setTextColor(Color.BLACK);
                    tv1.setLayoutParams(textrow);
                    tb.addView(tv1);

                    tl.addView(tb);

                }

                //Amount tendered
                TableRow twamountendered = new TableRow(this);
                twamountendered.setBackgroundResource(R.color.colorwhite);
                twamountendered.setPadding(10, 10, 10, 10);
                twamountendered.setLayoutParams(rowlayout);

                TextView tvamounttenderedlbl = new TextView(this);
                tvamounttenderedlbl.setText("AMOUNT TENDERED");
                tvamounttenderedlbl.setTextSize(15);
                tvamounttenderedlbl.setPadding(10, 0, 0, 50);
                tvamounttenderedlbl.setGravity(Gravity.RIGHT);
                tvamounttenderedlbl.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                tvamounttenderedlbl.setLayoutParams(textrow);
                twamountendered.addView(tvamounttenderedlbl);

                TextView tvamounttenedered = new TextView(this);
                tvamounttenedered.setText(CommonFunctions.currencyFormatter(amountendered));
                tvamounttenedered.setTextSize(16);
                tvamounttenedered.setPadding(40, 0, 0, 0);
                tvamounttenedered.setGravity(Gravity.LEFT);
                tvamounttenedered.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                tvamounttenedered.setTextColor(Color.BLACK);
                tvamounttenedered.setLayoutParams(textrow);
                twamountendered.addView(tvamounttenedered);

                tl.addView(twamountendered);

                //Amount Change
                TableRow twchange = new TableRow(this);
                twchange.setBackgroundResource(R.color.colorwhite);
                twchange.setPadding(10, 10, 10, 10);
                twchange.setLayoutParams(rowlayout);

                TextView tvchangelbl = new TextView(this);
                tvchangelbl.setText("CHANGE");
                tvchangelbl.setTextSize(15);
                tvchangelbl.setPadding(10, 0, 0, 50);
                tvchangelbl.setGravity(Gravity.RIGHT);
                tvchangelbl.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                tvchangelbl.setLayoutParams(textrow);
                twchange.addView(tvchangelbl);

                TextView tvchangeval = new TextView(this);
                tvchangeval.setText(CommonFunctions.currencyFormatter(amountchange));
                tvchangeval.setTextSize(16);
                tvchangeval.setPadding(40, 0, 0, 0);
                tvchangeval.setGravity(Gravity.LEFT);
                tvchangeval.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                tvchangeval.setTextColor(Color.BLACK);
                tvchangeval.setLayoutParams(textrow);
                twchange.addView(tvchangeval);

                tl.addView(twchange);

                liniarlyout.addView(tl);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void displayDialogMessage(String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txvMessage.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
        } else {
            txvMessage.setText(Html.fromHtml(message));
        }
    }

    private void setUpSkycableDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_skycable_link_account_status, false)
                .build();
        View view = mDialog.getCustomView();

        TextView txvCloseDialog = view.findViewById(R.id.txvCloseDialog);
        txvCloseDialog.setOnClickListener(this);
        txvMessage = view.findViewById(R.id.txvMessage);
    }

    private void showDialog() {

        try {
            dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_billspaymentconfimation_success);
            popsuccessclose = dialog.findViewById(R.id.cancel);
            popaddtomybiller = dialog.findViewById(R.id.addbtn);

            //click close
            popsuccessclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                    dialog.dismiss();
                    Intent intent = new Intent(getViewContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    CommonVariables.VOUCHERISFIRSTLOAD = true;
                    startActivity(intent);
                }

            });

            popaddtomybiller.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                    String optionval = popaddtomybiller.getText().toString();
                    if (optionval.equals("RETRY")) {
                        Intent intent = new Intent(getViewContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        startActivity(intent);
                    } else {
                        verifySession("ADDTOMYLIST");
                    }
                }

            });

            dialog.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showDialogDone(String stat, String remarks) {
        try {
            dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_billspaymentconfimation_success);
            popsuccessclose = dialog.findViewById(R.id.cancel);
            popaddtomybiller = dialog.findViewById(R.id.addbtn);

            linearGkNegosyoLayout = dialog.findViewById(R.id.linearGkNegosyoLayout);
            txvGkNegosyoDescription = dialog.findViewById(R.id.txvGkNegosyoDescription);
            txvGkNegosyoDescription.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/RobotoCondensed-Regular.ttf", getResources().getString(R.string.str_gk_negosyo_prompt)));
            txvGkNegosyoRedirection = dialog.findViewById(R.id.txvGkNegosyoRedirection);
            txvGkNegosyoRedirection.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/RobotoCondensed-Regular.ttf", "I WANT THIS!"));


            TextView message = dialog.findViewById(R.id.successMessage);
            TextView note = dialog.findViewById(R.id.dialogNote);
            TextView subject = dialog.findViewById(R.id.subject);
            TextView addbtn = dialog.findViewById(R.id.addbtn);

            if (stat.equals("SUCCESS")) {
                if (distributorid.equals("") || distributorid.equals(".")
                        || resellerid.equals("") || resellerid.equals(".")) {
                    linearGkNegosyoLayout.setVisibility(View.VISIBLE);
                } else {
                    linearGkNegosyoLayout.setVisibility(View.GONE);
                }
                subject.setText("SUCCESSFUL BILLS PAYMENT");
                subject.setTextColor(getResources().getColor(R.color.colortoolbar));
                message.setText(remarks);
                note.setText("Thank you for using GoodKredit. ");
            } else {
                subject.setText("FAILED BILLS PAYMENT");
                subject.setTextColor(getResources().getColor(R.color.colorred));
                message.setText(remarks);
                note.setText("Please try again.");
                addbtn.setText("RETRY");
            }

            dialog.show();

            //click close
            popsuccessclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(getViewContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    CommonVariables.VOUCHERISFIRSTLOAD = true;
                    startActivity(intent);
                    CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                }

            });

            popaddtomybiller.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                    String optionval = popaddtomybiller.getText().toString();
                    if (optionval.equals("RETRY")) {
                        Intent intent = new Intent(getViewContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        startActivity(intent);
                        ViewOthersTransactionsActivity.start(getViewContext(), 5);
                    } else {
                        verifySession("ADDTOMYLIST");
                    }

                }

            });

            //GK Negosyo
            txvGkNegosyoRedirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GKNegosyoRedirectionActivity.start(getViewContext(), db.getGkServicesData(db, "GKNEGOSYO"));
                }

            });


        } catch (Exception e) {
            Logger.debug("ERROR", "" + e);
        }

    }

    private void showDialogProcessedFinished(String stat, String remarks) {
        if (stat.equals("SUCCESS")) {
            if (distributorid.equals("") || distributorid.equals(".")
                    || resellerid.equals("") || resellerid.equals(".")) {
                checkIfReseller = false;
            } else {
                checkIfReseller = true;
            }
//            showGlobalSuccessDialog(remarks, "close", "double", checkIfReseller, false);

//            showGlobalDialogs(remarks, "close", ButtonTypeEnum.DOUBLE,
//                    checkIfReseller, false, DialogTypeEnum.SUCCESS);

            showSuccessFinishedDialog(remarks);

        } else {

//            showGlobalDialogs(remarks, "retry", ButtonTypeEnum.SINGLE,
//                    false, false, DialogTypeEnum.FAILED);
            showFailedFinishDialog(remarks);
        }
    }

    private void showSuccessFinishedDialog(String message) {

        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", message, "Close", "Add to My Billers", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        dialog.hideCloseImageButton();

        View btndoubleone = dialog.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });

        View btndoubletwo = dialog.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                verifySession("ADDTOMYLIST");
            }
        });
    }

    private void showFailedFinishDialog(String message) {

        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("FAILED", message, "Retry", "",
                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);

        View closebtn = dialog.btnCloseImage();
        closebtn.setVisibility(View.GONE);

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showProgressDialog(getViewContext(),"Processing Request.", "Please wait...");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        hideProgressDialog();
                        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        Intent intent = new Intent(getViewContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        ViewOthersTransactionsActivity.start(getViewContext(), 5);
                    }
                }, 2000);
            }
        });
    }

    public void callBillsPaymentVerifySession() {
        verifySession("ADDTOMYLIST");
    }

    private void showAlertDialog(final String message, final String status) {
        try {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            String msg = "";
            String title = "";
            if (status.equals("000")) {
                title = "Success";
                msg = "Biller was successfully added to your list";
            } else {
                title = "Failed";
                msg = message;
            }
            alertDialog.setTitle(title);
            alertDialog.setMessage(msg);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    CommonVariables.VOUCHERISFIRSTLOAD = true;

                    if (status.equals("000")) {
                        Intent intent = new Intent(getViewContext(), BillsPaymentActivity.class);
                        intent.putExtra("PROCESS", "VIEWBORROWERBILLER");
                        intent.putExtra("ServiceCode", servicecode);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getViewContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });


            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialog(final String message) {
        try {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            String msg = message;
            String title = "Failed";

            alertDialog.setTitle(title);
            alertDialog.setMessage(msg);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    CommonVariables.VOUCHERISFIRSTLOAD = true;

                    Intent intent = new Intent(getViewContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });


            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showProgressDialog(Context context, String message) {
        try {

            if (progressdialog == null) {
                progressdialog = new Dialog(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light));
                progressdialog.setCancelable(false);
                progressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressdialog.setContentView(R.layout.pop_processing);


                TextView processingmessage = progressdialog.findViewById(R.id.processingmessage);
                processingmessage.setText(message);
                ImageView iView = progressdialog.findViewById(R.id.imgloader);

                if (iView != null) {
                    Glide.with(context)
                            .load(R.drawable.progressloader)
                            .into(iView);
                }
                if (!progressdialog.isShowing()) {
                    progressdialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void hideProgressCustomDialog(Context context) {
        try {
            progressdialog.hide();
        } catch (Exception e) {

        }
    }

    public void proceedConfirmation(View view) {
        currentdelaytime = 0;
        verifySession("");
    }

    private void verifySession(final String flag) {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            setUpProgressLoader();
            setUpProgressLoaderMessageDialog("Sending request. Please wait...");
            if (flag.equals("ADDTOMYLIST")) {
                //new HttpAsyncTask1().execute(CommonVariables.ADDTOMYBILLERLIST);
                addToBorrowerBillerList();
            } else {
                //new HttpAsyncTask().execute(CommonVariables.PROCESSBILLSPAYMENT);
                processBillsPayment();
            }
        } else {
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            showProgressDialog(getViewContext(), "Processing request, please wait...");
            setUpProgressLoaderMessageDialog("Sending request. Please wait...");
        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("vouchersession", vouchersession);
                jsonObject.accumulate("spbillercode", spbillercode);
                jsonObject.accumulate("billerparamdata", billerparamdata);
                jsonObject.accumulate("servicecharge", servicecharge);
                jsonObject.accumulate("othercharges", othercharges);
                jsonObject.accumulate("isskycablenewapplication", isNewApplication);
                jsonObject.accumulate("isskycableppv", isPPV);
                jsonObject.accumulate("servicecode", servicecode);
                jsonObject.accumulate("grossamount", grossprice);
                jsonObject.accumulate("hasdiscount", hasdiscount);
                jsonObject.accumulate("recipientmobileno", receiptmobileno);
                jsonObject.accumulate("recipientemailadd", receiptemailadd);
                jsonObject.accumulate("latitude", latitude);
                jsonObject.accumulate("longitude", longitude);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();


            } catch (Exception e) {
                e.printStackTrace();
                setUpProgressLoaderDismissDialog();
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {

                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "PROCESSINGBILLSPAY";

                JSONObject parentObj = new JSONObject(result);
                String data = parentObj.getString("data");
                String message = parentObj.getString("message");
                String status = parentObj.getString("status");
                if (status.equals("000")) {

                    JSONArray jsonArr = new JSONArray(data);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject obj = jsonArr.getJSONObject(i);
                        restransactionno = obj.getString("TransactionNo");
                        resspbillercode = obj.getString("ServiceProviderBillerCode");
                        resbillercode = obj.getString("BillerCode");
                        resaccountno = obj.getString("AccountNumber");
                    }


                    //check transaction status here
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentdelaytime = currentdelaytime + 1000;
                            //Do something after 100ms
                            //new HttpAsyncTask2().execute(CommonVariables.CHECKBILLSPAYMENTTXNSTAT);
                        }
                    }, 1000);

                } else {
//                    hideProgressCustomDialog(getViewContext());
                    setUpProgressLoaderDismissDialog();
                    showToast("" + message, GlobalToastEnum.NOTICE);
                }


            } catch (Exception e) {
//                hideProgressCustomDialog(getViewContext());
                setUpProgressLoaderDismissDialog();
                e.printStackTrace();
            }
        }
    }

    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            showProgressDialog(getViewContext(), "Processing, please wait...");
            setUpProgressLoaderMessageDialog("Sending request. Please wait...");

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("vouchersession", vouchersession);
                jsonObject.accumulate("spbillercode", spbillercode);
                jsonObject.accumulate("billerparamdata", billerparamdata);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();


            } catch (Exception e) {
                e.printStackTrace();
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
//            hideProgressCustomDialog(getViewContext());
            setUpProgressLoaderDismissDialog();
            try {
                JSONObject parentObj = new JSONObject(result);
                String message = parentObj.getString("message");
                String status = parentObj.getString("status");

                showAlertDialog(message, status);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            showProgressDialog(getViewContext(), "Checking request status, please wait...");
            setUpProgressLoaderMessageDialog("Sending request. Please wait...");
        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("billercode", resbillercode);
                jsonObject.accumulate("accountno", resaccountno);
                jsonObject.accumulate("transactionno", restransactionno);
                jsonObject.accumulate("spbillercode", resspbillercode);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();


            } catch (Exception e) {
                e.printStackTrace();
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {


                JSONObject parentObj = new JSONObject(result);
                String message = parentObj.getString("message");
                String status = parentObj.getString("status");
                String data = parentObj.getString("data");

                if (status.equals("003")) {
                    if (currentdelaytime < totaldelaytime) {
                        //check transaction status here
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentdelaytime = currentdelaytime + 1000;
                                //Do something after 100ms
                                //new HttpAsyncTask2().execute(CommonVariables.CHECKBILLSPAYMENTTXNSTAT);

                            }
                        }, 1000);
                    } else {
//                        hideProgressCustomDialog(getViewContext());
                        setUpProgressLoaderDismissDialog();

                        if (isNewApplication) {
                            mDialog.show();
                            displayDialogMessage("Your payment for your New Application is still in process. You will receive notification once it's done. You can monitor your transaction under Usage Menu. <br><br> Thank you for using GoodKredit.");
                        } else if (isPPV) {
                            mDialog.show();
                            displayDialogMessage("Your payment for your PPV Subscription is still in process. You will receive notification once it's done. You can monitor your transaction under Usage Menu. <br><br> Thank you for using GoodKredit.");
                        } else {
//                            showDialog();
//                            showGlobalDialogs("Your request for bills payment is still in process. " +
//                                            "You will receive notification once it's done. " +
//                                            "You can monitor your transaction under Usage Menu. Thank you for using GoodKredit.",
//                                    "close",
//                                    ButtonTypeEnum.SINGLE,
//                                    true,
//                                    false,
//                                    DialogTypeEnum.ONPROCESS
//                            );
                            showBillsOnProgressDialog("Your request for bills payment is still in process. You will receive notification once it's done. You can monitor your transaction under Usage Menu. Thank you for using GoodKredit.");
                        }

                    }
                } else if (status.equals("000")) {
                    String remarks = parentObj.getString("remarks");
//                    hideProgressCustomDialog(getViewContext());
                    setUpProgressLoaderDismissDialog();
                    if (isNewApplication || isPPV) {
                        mDialog.show();
                        if (data.equals("SUCCESS")) {
                            displayDialogMessage(remarks);
                        } else {
                            displayDialogMessage(remarks);
                        }

                    } else {
//                        showDialogDone(data, remarks);
                        showDialogProcessedFinished(data, remarks);
                    }


                } else {
                    showToast("" + message, GlobalToastEnum.NOTICE);
//                    hideProgressCustomDialog(getViewContext());
                    setUpProgressLoaderDismissDialog();
                }


            } catch (Exception e) {
//                hideProgressCustomDialog(getViewContext());
                setUpProgressLoaderDismissDialog();
                e.printStackTrace();
            }
        }
    }

    private void showBillsOnProgressDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("ON PROGRESS", message, "Close", "",
                ButtonTypeEnum.SINGLE, true, false, DialogTypeEnum.ONPROCESS);

        View closebtn = dialog.btnCloseImage();
        closebtn.setVisibility(View.GONE);

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                hideProgressDialog();
                dialog.proceedtoMainActivity();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txvCloseDialog: {
                mDialog.dismiss();
                Intent intent = new Intent(getViewContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        if (progressdialog != null) {
            progressdialog.dismiss();
            progressdialog = null;
        }
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    //Add to Biller List
    private void addToBorrowerBillerList(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String,String> parameters  = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("borrowerid",borrowerid);
            parameters.put("userid",userid);
            parameters.put("vouchersession",vouchersession);
            parameters.put("serviceproviderbillercode",spbillercode);
            parameters.put("billerparams",billerparamdata);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "addToBorrowerBillerList");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            addToBillersList(addToBorrowerBillerListSession);

        }else{
            showNoInternetToast();
        }
    }
    private void addToBillersList(Callback<GenericResponse> addtoBorrowersList) {
        Call<GenericResponse> addtolist = RetrofitBuilder.getGkServiceV2API(getViewContext())
                .addToBorrowerBillerList(
                        authenticationid, sessionid, param
                );
        addtolist.enqueue(addtoBorrowersList);
    }
    private final Callback<GenericResponse> addToBorrowerBillerListSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            setUpProgressLoaderDismissDialog();
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                        showDialog("Something went wrong on your connection. Please check.");
                    } else {
                        showAlertDialog(decryptedMessage, response.body().getStatus());
                    }

                } else {
                    if (response.body().getStatus().equalsIgnoreCase("error")) {
                        showDialog("Something went wrong on your connection. Please check.");
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }
                    else {
                        showDialog(decryptedMessage);
                    }
                }
            } else {
                showDialog("Something went wrong on your connection. Please check.");
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            showDialog("Something went wrong on your connection. Please check.");
        }
    };

    //Process Bills Payment
    private void processBillsPayment(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String,String> parameters  = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("vouchersession", vouchersession);
            parameters.put("spbillercode", spbillercode);
            parameters.put("billerparam", billerparamdata);
            parameters.put("servicecharge", servicecharge);
            parameters.put("othercharges", othercharges);
            parameters.put("isskycablenewapplication", String.valueOf(isNewApplication));
            parameters.put("isskycableppv", String.valueOf(isPPV));
            parameters.put("hasdiscount", String.valueOf(hasdiscount));
            parameters.put("grossamount", grossprice);
            parameters.put("recipientmobileno", receiptmobileno);
            parameters.put("recipientemailadd", receiptemailadd);
            parameters.put("longitude", longitude);
            parameters.put("latitude", latitude);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            billsIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", billsIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            billsAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            billsKeyEncryption = CommonFunctions.getSha1Hex(billsAuthenticationid + sessionid + "processBillsPaymentV2");
            billsParam = CommonFunctions.encryptAES256CBC(billsKeyEncryption, String.valueOf(paramJson));

            processBillsPaymentObject(processBillsPaymentObjectSession);

        }else{
            showNoInternetToast();
        }
    }
    private void processBillsPaymentObject(Callback<GenericResponse> processPayment) {
        Call<GenericResponse> processBills = RetrofitBuilder.getGkServiceV2API(getViewContext())
                .processBillsPaymentV2(
                        billsAuthenticationid, sessionid, billsParam
                );
        processBills.enqueue(processPayment);
    }
    private final Callback<GenericResponse> processBillsPaymentObjectSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            CommonVariables.PROCESSNOTIFICATIONINDICATOR = "PROCESSINGBILLSPAY";
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(billsKeyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(billsKeyEncryption, response.body().getData());
                    if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else {
                            restransactionno = CommonFunctions.parseJSON(decryptedData,"TransactionNo");
                            resspbillercode = CommonFunctions.parseJSON(decryptedData,"ServiceProviderBillerCode");
                            resbillercode =CommonFunctions.parseJSON(decryptedData,"BillerCode");
                            resaccountno = CommonFunctions.parseJSON(decryptedData,"AccountNumber");

                            //check transaction status here
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    currentdelaytime = currentdelaytime + 1000;
                                    //Do something after 1000ms
                                    //new HttpAsyncTask2().execute(CommonVariables.CHECKBILLSPAYMENTTXNSTAT);
                                    checkBillsPaymentStatusV2();
                                }
                            }, 1000);

                    }
                } else {
                    setUpProgressLoaderDismissDialog();
                    if (response.body().getStatus().equalsIgnoreCase("error")) {
                        showErrorToast();
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }
                    else {
                        showToast(decryptedMessage, GlobalToastEnum.WARNING);
                    }
                }
            } else {
                setUpProgressLoaderDismissDialog();
                showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
        }
    };

    //Check Bills Payment Transaction Status
    private void checkBillsPaymentStatusV2() {

        setUpProgressLoaderMessageDialog("Sending request. Please wait...");

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("billercode", resbillercode);
            parameters.put("accountno", resaccountno);
            parameters.put("transactionno", restransactionno);
            parameters.put("spbillercode", resspbillercode);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            billStatusIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", billStatusIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            billStatusAuthID = CommonFunctions.parseJSON(jsonString, "authenticationid");
            billStatusKeyEncryption = CommonFunctions.getSha1Hex(billStatusAuthID + sessionid + "CheckBillsPaymentTransactionStatus");
            billStatusParam = CommonFunctions.encryptAES256CBC(billStatusKeyEncryption, String.valueOf(paramJson));

            checkBillsPaymentStatusV2Object(checkBillsPaymentStatusV2Callback);

        } else {
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }
    }
    private void checkBillsPaymentStatusV2Object(Callback<GenericResponse> checkstatus) {
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .checkBillsPaymentTransactionStatus(
                        billStatusAuthID, sessionid, billStatusParam
                );
        call.enqueue(checkstatus);
    }
    private Callback<GenericResponse> checkBillsPaymentStatusV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(billStatusKeyEncryption, response.body().getMessage());
                String status = response.body().getStatus();
                switch (status) {
                    case "000":
                        String decryptedData = CommonFunctions.decryptAES256CBC(billStatusKeyEncryption,response.body().getData());
                        String remarks = CommonFunctions.parseJSON(decryptedData,"remarks");
                        String message = CommonFunctions.parseJSON(decryptedData,"status");
                        setUpProgressLoaderDismissDialog();
                        if (isNewApplication || isPPV) {
                            mDialog.show();
                            if (message.equals("SUCCESS")) {
                                displayDialogMessage(remarks);
                            } else {
                                displayDialogMessage(remarks);
                            }

                        } else {
                            showDialogProcessedFinished(message, remarks);
                        }

                        break;
                    case "202":
                        if (currentdelaytime < totaldelaytime) {
                            //check transaction status here
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    currentdelaytime = currentdelaytime + 1000;
                                    //new HttpAsyncTask2().execute(CommonVariables.CHECKBILLSPAYMENTTXNSTAT);
                                    checkBillsPaymentStatusV2();
                                }
                            }, 1000);
                        } else {
                            setUpProgressLoaderDismissDialog();
                            if (isNewApplication) {
                                mDialog.show();
                                displayDialogMessage("Your payment for your New Application is still in process. You will receive notification once it's done. You can monitor your transaction under Usage Menu. <br><br> Thank you for using GoodKredit.");
                            } else if (isPPV) {
                                mDialog.show();
                                displayDialogMessage("Your payment for your PPV Subscription is still in process. You will receive notification once it's done. You can monitor your transaction under Usage Menu. <br><br> Thank you for using GoodKredit.");
                            } else {
                                showGlobalDialogs("Your request for bills payment is still in process. " +
                                                "You will receive notification once it's done. " +
                                                "You can monitor your transaction under Usage Menu. Thank you for using GoodKredit.",
                                        "close",
                                        ButtonTypeEnum.SINGLE,
                                        true,
                                        false,
                                        DialogTypeEnum.ONPROCESS
                                );
                            }

                        }
                        break;
                    case "003":
                        setUpProgressLoaderDismissDialog();
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                        break;
                    default:
                        setUpProgressLoaderDismissDialog();
                        showErrorToast(decryptedMessage);
                        break;
                }
            } else {
                setUpProgressLoaderDismissDialog();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            setUpProgressLoaderDismissDialog();
            showErrorGlobalDialogs();
        }
    };

}
