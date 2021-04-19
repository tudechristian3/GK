package com.goodkredit.myapplication.activities.reloadretailer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoRedirectionActivity;
import com.goodkredit.myapplication.activities.transactions.ViewOthersTransactionsActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;

import org.json.JSONObject;

import java.text.DecimalFormat;

public class RetailerLoadConfirmationActivity extends BaseActivity {

    DatabaseHandler db;
    DecimalFormat formatter;

    private String borroweridval = "";
    private String useridval = "";
    private String imei = "";
    private String mobileval = "";
    private String networkval = "";
    private String producttypeval = "";
    private String productcodeval = "";
    private String amountopayval = "";
    private String amountenderedval = "";
    private String changeval = "";
    private String sessionid = "";
    private String vouchersession = "";

    private TextView txvmobile;
    private TextView txvnetwork;
    private TextView txvproducttype;
    private TextView txvproductcode;
    private TextView txvamounttopay;
    private TextView txvamounttendered;
    private TextView txvamountchange;
    private TextView txvamount;
    private TextView txvdiscount;
    private TextView txvdiscountlbl;

    Dialog dialog;
    Dialog progressdialog;
    int currentdelaytime = 0;
    int totaldelaytime = 10000;
    String transactionno = "";
    String discount = "0";

    //DISCOUNT
    private String grossprice = "";
    private String servicecode = "";
    private int hasdiscount = 0;

    private TableRow discountrow;
    private TextView discountval;

    //GK NEGOSYO
    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;
    private String distributorid = "";
    private String resellerid = "";

    //LONG AND LAT
    private String latitude;
    private String longitude;

    //GPS TRACKER
    private GPSTracker tracker;

    private boolean checkIfReseller = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_load_confirmation);

        try {

            //initialize db
            db = new DatabaseHandler(this);
            //decimal
            formatter = new DecimalFormat("#,###,##0.00");

            //set toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            Bundle b = getIntent().getExtras();
            mobileval = b.getString("MOBILE");
            networkval = b.getString("NETWORK");
            producttypeval = b.getString("PRODUCTTYPE");
            productcodeval = b.getString("PRODUCTCODE");
            amountopayval = b.getString("AMOUNTTOPAY");
            amountenderedval = b.getString("AMOUNTENDERED");
            changeval = b.getString("CHANGE");
            vouchersession = b.getString("VOUCHERSESSION");
            discount = b.getString("DISCOUNT");
            grossprice = b.getString("GROSSPRICE");
            servicecode = b.getString("GKSERVICECODE");
            hasdiscount = b.getInt("GKHASDISCOUNT");
            latitude = b.getString("LATITUDE");
            longitude = b.getString("LONGITUDE");


            //initialize elements
            txvmobile = findViewById(R.id.mobileval);
            txvnetwork = findViewById(R.id.networkval);
            txvproducttype = findViewById(R.id.productval);
            txvproductcode = findViewById(R.id.productcodeval);
            txvamount = findViewById(R.id.amountval);
            txvdiscountlbl = findViewById(R.id.discountlbl);
            txvdiscount = findViewById(R.id.discountval);
            txvamounttopay = findViewById(R.id.amounttopayval);
            txvamounttendered = findViewById(R.id.amounttenderedval);
            txvamountchange = findViewById(R.id.amountchangeval);


            //set value
            txvmobile.setText("+63" + mobileval);
            txvnetwork.setText(networkval);
            txvproducttype.setText(producttypeval);
            txvproductcode.setText(productcodeval);
            txvamount.setText(CommonFunctions.currencyFormatter(grossprice));
            txvdiscount.setText(CommonFunctions.currencyFormatter(discount));
            txvamounttopay.setText(CommonFunctions.currencyFormatter(amountopayval));
            txvamounttendered.setText(CommonFunctions.currencyFormatter(amountenderedval));
            txvamountchange.setText(CommonFunctions.currencyFormatter(changeval));


            //get account information
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borroweridval = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    useridval = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                } while (cursor.moveToNext());
            }
            cursor.close();


            distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
            resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    /*---------------
     * FUNCTIONS
     * --------------*/

    public void proceedConfirmation(View view) {
        try {
            if (mobileval.equals("") || networkval.equals("") || producttypeval.equals("") || productcodeval.equals("") || amountopayval.equals("") || amountenderedval.equals("") || changeval.equals("")) {
                showToast("Some data maybe incorrect.", GlobalToastEnum.WARNING);
            } else {
                currentdelaytime = 0;
                verifySession();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    public void verifySession() {
//        try {
//            int status = CommonFunctions.getConnectivityStatus(getBaseContext());
//            if (status == 0) { //no connection
//                showToast("No internet connection.", GlobalToastEnum.NOTICE);
//            } else {
////                showProgressDialog(getViewContext(), "Sending request, please wait...");
//                setUpProgressLoader();
//                setUpProgressLoaderMessageDialog("Sending request, please wait...");
//
//                CreateSession newsession = new CreateSession(this);
//                newsession.setQueryListener(new CreateSession.QueryListener() {
//                    @SuppressWarnings("unchecked")
//                    public void QuerySuccessFul(String data) {
//                        if (data == null) {
////                            hideProgressCustomDialog(getViewContext());
//                            setUpProgressLoaderDismissDialog();
//                            showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
//                        } else if (data.equals("001")) {
////                            hideProgressCustomDialog(getViewContext());
//                            setUpProgressLoaderDismissDialog();
//                            showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
//                        } else if (data.equals("002")) {
////                            hideProgressCustomDialog(getViewContext());
//                            setUpProgressLoaderDismissDialog();
//                            showToast("Something went wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
//                        } else if (data.equals("error")) {
////                            hideProgressCustomDialog(getViewContext());
//                            setUpProgressLoaderDismissDialog();
//                            showToast("Something went wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
//                        } else if (data.contains("<!DOCTYPE html>")) {
////                            hideProgressCustomDialog(getViewContext());
//                            setUpProgressLoaderDismissDialog();
//                            showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
//                        } else {
//                            sessionid = data;
//
//                            if (!distributorid.equals(".") && !resellerid.equals(".")) {
//                                discount = "0";
//                            }
//                            new HttpAsyncTask().execute(CommonVariables.PROCCESSRETAILERLOADING);
//
//                        }
//                    }
//
//                });
//                newsession.execute(CommonVariables.CREATESESSION, imei, useridval);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void verifySession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            setUpProgressLoader();
            setUpProgressLoaderMessageDialog("Sending request, please wait...");

            if (!distributorid.equals(".") && !resellerid.equals(".")) {
                discount = "0";
            }
            new HttpAsyncTask().execute(CommonVariables.PROCCESSRETAILERLOADING);
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
            setUpProgressLoaderMessageDialog("Processing request, please wait...");
        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + useridval + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("mobile", "0" + mobileval);
                jsonObject.accumulate("network", networkval);
                jsonObject.accumulate("producttype", producttypeval);
                jsonObject.accumulate("productcode", productcodeval);
                jsonObject.accumulate("amount", amountopayval);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("userid", useridval);
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("vouchersession", vouchersession);
                jsonObject.accumulate("discount", discount);
                jsonObject.accumulate("servicecode", servicecode);
                jsonObject.accumulate("grossamount", grossprice);
                jsonObject.accumulate("hasdiscount", hasdiscount);
                jsonObject.accumulate("latitude", latitude);
                jsonObject.accumulate("longitude", longitude);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                e.printStackTrace();
//                hideProgressCustomDialog(getViewContext());
                setUpProgressLoaderDismissDialog();
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {

                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "PREPAIDISPROCESSING";

                JSONObject parentObj = new JSONObject(result);
                String message = parentObj.getString("message");
                String status = parentObj.getString("status");
                transactionno = parentObj.getString("data");

                if (status.equals("000")) {
                    //check transaction status here
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentdelaytime = currentdelaytime + 1000;
                            //Do something after 100ms
                            new HttpAsyncTask1().execute(CommonVariables.CHECKRETAILERLOADINGTXNSTAT);
                        }
                    }, 1000);

                } else if (result.contains("<!DOCTYPE html>")) {
//                    hideProgressCustomDialog(getViewContext());
                    setUpProgressLoaderDismissDialog();
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
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
//            showProgressDialog(getViewContext(), "Checking status, please wait...");
            setUpProgressLoaderMessageDialog("Checking status, please wait...");
        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + useridval + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userid", useridval);
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("transactionno", transactionno);

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
                String restransactionno = parentObj.getString("data");
                if (status.equals("003")) {

                    if (currentdelaytime < totaldelaytime) {
                        //check transaction status here
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentdelaytime = currentdelaytime + 1000;
                                //Do something after 100ms
                                new HttpAsyncTask1().execute(CommonVariables.CHECKRETAILERLOADINGTXNSTAT);
                            }
                        }, 1000);
                    } else {
//                        hideProgressCustomDialog(getViewContext());
                        setUpProgressLoaderDismissDialog();
//                        showDialog(restransactionno);
                        String passedmessage = " Your smart retailer wallet reloading transaction is still on process. " +
                                "You will receive a notification once it's done." + "\n" + "Thank you for using GoodKredit.";

                        showGlobalDialogs(passedmessage, "close",
                                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.ONPROCESS);
                    }


                } else if (status.equals("000")) {
//                    hideProgressCustomDialog(getViewContext());
                    setUpProgressLoaderDismissDialog();
                    String txnstat = parentObj.getString("data");
                    String remarks = parentObj.getString("remarks");
//                    showDialogDone(restransactionno, txnstat, remarks);
                    showDialogNewDone(txnstat, remarks);

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

    private void showDialog(String bcData) {
        //create dialog
        try {

            dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_airtimeconsummation_success);
            TextView popsuccessclose = dialog.findViewById(R.id.popok);
            TextView successMessage = dialog.findViewById(R.id.successMessage);
            successMessage.setText("Your smart retailer wallet reloading transaction is still on process. You will receive a notification once done. Please monitor your transaction under Usage Menu. ");

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialogDone(String transactiono, String status, String remarks) {

        try {
            dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_airtimeconsummation_success);
            TextView message = dialog.findViewById(R.id.successMessage);
            TextView note = dialog.findViewById(R.id.dialogNote);
            TextView subject = dialog.findViewById(R.id.subject);
            TextView popsuccessclose = dialog.findViewById(R.id.popok);
            TextView retrybtn = dialog.findViewById(R.id.retrybtn);

            linearGkNegosyoLayout = dialog.findViewById(R.id.linearGkNegosyoLayout);
            txvGkNegosyoDescription = dialog.findViewById(R.id.txvGkNegosyoDescription);
            txvGkNegosyoDescription.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/RobotoCondensed-Regular.ttf", getResources().getString(R.string.str_gk_negosyo_prompt)));
            txvGkNegosyoRedirection = dialog.findViewById(R.id.txvGkNegosyoRedirection);
            txvGkNegosyoRedirection.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/RobotoCondensed-Regular.ttf", "I WANT THIS!"));


            if (status.equals("SUCCESS")) {
                if (distributorid.equals("") || distributorid.equals(".")
                        || resellerid.equals("") || resellerid.equals(".")) {
                    linearGkNegosyoLayout.setVisibility(View.VISIBLE);
                } else {
                    linearGkNegosyoLayout.setVisibility(View.GONE);
                }
                subject.setText("SUCCESSFUL LOAD");
                subject.setTextColor(getResources().getColor(R.color.colortoolbar));
                message.setText(remarks);
                note.setText("Thank you for using GoodKredit");
                retrybtn.setVisibility(View.GONE);
            } else {
                subject.setText("FAILED LOAD");
                subject.setTextColor(getResources().getColor(R.color.colorred));
                message.setText(remarks);
                note.setText("Please try again.");
                retrybtn.setVisibility(View.VISIBLE);
            }

            dialog.show();

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

            //click retry
            retrybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                    dialog.dismiss();
                    Intent intent = new Intent(getViewContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    CommonVariables.VOUCHERISFIRSTLOAD = true;
                    startActivity(intent);
                    ViewOthersTransactionsActivity.start(getViewContext(), 6);
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
            e.printStackTrace();
        }

    }

    private void showDialogNewDone(String status, String remarks) {
        if (status.equals("SUCCESS")) {
            if (distributorid.equals("") || distributorid.equals(".")
                    || resellerid.equals("") || resellerid.equals(".")) {
                checkIfReseller = false;
            } else {
                checkIfReseller = true;
            }

            String passedmessage = remarks + "\n\n" + "Thank you for using Goodkredit";

            showGlobalDialogs(passedmessage, "close", ButtonTypeEnum.SINGLE,
                    checkIfReseller, false, DialogTypeEnum.SUCCESS);
        } else {
            String passedmessage = remarks + "\n" + "Please try again.";

            showGlobalDialogs(passedmessage, "retry",
                    ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);
        }
    }


    private void showProgressDialog(Context context, String message) {
        try {

            if (progressdialog == null) {
                progressdialog = new Dialog(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light));
                progressdialog.setCancelable(false);
                progressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressdialog.setContentView(R.layout.pop_processing);
            }
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void hideProgressCustomDialog(Context context) {
        try {

            try {
                progressdialog.hide();
            } catch (Exception e) {
                e.printStackTrace();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
