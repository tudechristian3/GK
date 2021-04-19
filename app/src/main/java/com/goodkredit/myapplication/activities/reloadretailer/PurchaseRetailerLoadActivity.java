
package com.goodkredit.myapplication.activities.reloadretailer;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.bean.PrepaidLogs;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.DiscountResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseRetailerLoadActivity extends BaseActivity {
    CommonFunctions cf;
    CommonVariables cv;
    DatabaseHandler db;
    Button proceed;

    String borroweridval = "";
    String useridval = "";
    String imei = "";
    String amountval = "";
    String mobileval = "";
    String networkval = "";
    String producttypeval = "";
    String productcodeval = "";
    String sessionid = "";

    ImageView smartlogo;
    EditText country;
    EditText mobilenumber;
    TextView mobileNote;
    TextView networklbl;
    EditText network;
    TextView prodtypelbl;
    EditText loadtype;
    TextView amountlbl;
    EditText amount;
    TextView amounthint;
    TextView prodcodelbl;
    EditText productval;
    EditText productcode;
    String networkprefix = "";
    String discount = "";
    String totalamount = "";
    String vouchersession = "";

    ArrayAdapter<String> spinTypeAdapter;
    String[] selecttype;
    Dialog dialog;
    boolean isProceed = true;


    //SERVICES
    private GKService gkservice;
    private String servicecode;

    //DISCOUNT RESELLER
    //DISCOUNT
    private double resellerdiscount;
    private double resellertotalamount;
    private String latitude = "";
    private String longitude = "";
    private int hasdiscount = 0;
    private String discountmessage = "";

    private GPSTracker tracker;

    private FusedLocationProviderClient mFusedLocationClient;

    private String distributorid = "";
    private String resellerid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_retailer_load);

        try {
            //set toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            //Initialize database
            db = new DatabaseHandler(this);

            gkservice = getIntent().getParcelableExtra("GKSERVICE_OBJECT");

            if(gkservice != null) {
                servicecode = gkservice.getServiceCode();
            }
            else {
                servicecode = "SMARTWALLET";
            }

            distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
            resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

            //Initialize element here
            smartlogo = findViewById(R.id.smartlogo);
            proceed = findViewById(R.id.proceed);
            country = findViewById(R.id.country);
            mobilenumber = findViewById(R.id.mobilenumber);
            mobileNote = findViewById(R.id.mobileNote);
            networklbl = findViewById(R.id.networklbl);
            network = findViewById(R.id.network);
            prodtypelbl = findViewById(R.id.prodtypelbl);
            prodcodelbl = findViewById(R.id.prodcodelbl);
            productval = findViewById(R.id.productval);
            productcode = findViewById(R.id.productcode);
            amountlbl = findViewById(R.id.amountlbl);
            amount = findViewById(R.id.amount);
            amounthint = findViewById(R.id.amounthint);
            loadtype = findViewById(R.id.loadtype);
            Glide.with(this).load(R.drawable.smartretaillogo).into(smartlogo);

            //SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            //get account information
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

            //get passed value
            Intent intent = getIntent();
            PrepaidLogs item = intent.getParcelableExtra("item");

            if (item != null) {
                isProceed = true;
                mobileval = item.getMobileTarget();
                mobileval = mobileval.substring(1);
                networkval = item.getNetwork();
                productcodeval = "DR";
                amountval = item.getAmount();
                producttypeval = item.getDenomType();

                processRetry();
            }


        /*--------------
        TRIGGERS
        ----------------*/
            mobilenumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count > 0) {
                        Drawable img;
                        Resources res = getResources();
                        img = ContextCompat.getDrawable(getViewContext(), R.mipmap.xcircle);
                        img.setBounds(0, 0, 36, 36);
                        mobilenumber.setCompoundDrawables(null, null, img, null);
                    } else {
                        mobilenumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 10) {
                        clearVariable();
                        Character num = mobilenumber.getText().toString().charAt(0);
                        if (num.toString().equals("0") || num.toString().equals("6")) {
                            showToast("Invalid Mobile Number", GlobalToastEnum.WARNING);
                            networklbl.setVisibility(View.GONE);
                            network.setVisibility(View.GONE);
                            prodtypelbl.setVisibility(View.GONE);
                            loadtype.setVisibility(View.GONE);
                            productcode.setVisibility(View.GONE);
                            prodcodelbl.setVisibility(View.GONE);
                            amountlbl.setVisibility(View.GONE);
                            amount.setVisibility(View.GONE);
                            amounthint.setVisibility(View.GONE);

                            networkval = "";
                        } else {
                            networkprefix = mobilenumber.getText().toString().substring(0, 3);
                            networkval = db.getLoadwalletPrefix(db, networkprefix);

                            if (networkval.length() == 0) {

                                networkprefix = mobilenumber.getText().toString().substring(0, 5);
                                networkval = db.getLoadwalletPrefix(db, networkprefix);
                                if (networkval.length() == 0) {
                                    networkval = "";
                                    networklbl.setVisibility(View.GONE);
                                    network.setVisibility(View.GONE);
                                    prodtypelbl.setVisibility(View.GONE);
                                    loadtype.setVisibility(View.GONE);
                                    productcode.setVisibility(View.GONE);
                                    prodcodelbl.setVisibility(View.GONE);
                                    amountlbl.setVisibility(View.GONE);
                                    amount.setVisibility(View.GONE);
                                    amounthint.setVisibility(View.GONE);
                                    showToast("Invalid Mobile Number (Prefix not defined.)", GlobalToastEnum.WARNING);

                                } else {


                                    network.setText(networkval);
                                    networklbl.setVisibility(View.VISIBLE);
                                    network.setVisibility(View.VISIBLE);
                                    mobileNote.setVisibility(View.GONE);
                                    prodtypelbl.setVisibility(View.GONE);
                                    loadtype.setVisibility(View.GONE);
                                    amount.setVisibility(View.VISIBLE);
                                    amountlbl.setVisibility(View.VISIBLE);
                                    amounthint.setVisibility(View.VISIBLE);
                                    prodcodelbl.setVisibility(View.VISIBLE);
                                    productcode.setVisibility(View.VISIBLE);
                                    productcode.setText("DR");
                                    producttypeval = "REGULAR";
                                    loadtype.setText(producttypeval);
                                    hideSoftKeyboard();


                                }
                            } else {

                                network.setText(networkval);
                                networklbl.setVisibility(View.VISIBLE);
                                network.setVisibility(View.VISIBLE);
                                mobileNote.setVisibility(View.GONE);
                                prodtypelbl.setVisibility(View.GONE);
                                loadtype.setVisibility(View.GONE);
                                amount.setVisibility(View.VISIBLE);
                                amountlbl.setVisibility(View.VISIBLE);
                                amounthint.setVisibility(View.VISIBLE);
                                prodcodelbl.setVisibility(View.GONE);
                                productcode.setVisibility(View.GONE);
                                productcode.setText("DR");
                                producttypeval = "REGULAR";
                                loadtype.setText(producttypeval);
                                hideSoftKeyboard();
                            }

                        }


                    } else {
                        clearVariable();
                        networklbl.setVisibility(View.GONE);
                        network.setVisibility(View.GONE);
                        loadtype.setVisibility(View.GONE);
                        productcode.setVisibility(View.GONE);
                        prodcodelbl.setVisibility(View.GONE);
                        amount.setVisibility(View.GONE);
                        amountlbl.setVisibility(View.GONE);
                        amounthint.setVisibility(View.GONE);
                        prodtypelbl.setVisibility(View.GONE);
                        mobileNote.setVisibility(View.VISIBLE);
                    }

                }
            });

            mobilenumber.setOnTouchListener(new View.OnTouchListener() {
                final int DRAWABLE_RIGHT = 2;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (mobilenumber.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                            int leftEdgeOfRightDrawable = mobilenumber.getRight()
                                    - mobilenumber.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                            leftEdgeOfRightDrawable -= getResources().getDimension(R.dimen.EdtPadding);
                            if (event.getRawX() >= leftEdgeOfRightDrawable) {
                                mobilenumber.setText("");
                                network.setText("");
                                productcode.setText("");
                                productval.setText("");
                                amount.setText("");
                                networklbl.setVisibility(View.GONE);
                                network.setVisibility(View.GONE);
                                loadtype.setVisibility(View.GONE);
                                productcode.setVisibility(View.GONE);
                                prodcodelbl.setVisibility(View.GONE);
                                amount.setVisibility(View.GONE);
                                amountlbl.setVisibility(View.GONE);
                                amounthint.setVisibility(View.GONE);
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*******************
     * FUNCTIONS
     * ****/
    public static void start(Context context, PrepaidLogs item) {
        try {
            Intent intent = new Intent(context, PurchaseRetailerLoadActivity.class);
            intent.putExtra("item", item);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void hideSoftKeyboard() {
        try {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void processRetry() {

        try {
            //set value here

            mobilenumber.setText(mobileval);
            network.setText(networkval);
            productcode.setText(productcodeval);
            productval.setText(productcodeval);
            amount.setText(amountval);

            //set visibility
            mobilenumber.setVisibility(View.VISIBLE);
            network.setVisibility(View.VISIBLE);
            productcode.setVisibility(View.GONE);
            amount.setVisibility(View.VISIBLE);
            amounthint.setVisibility(View.VISIBLE);

            networklbl.setVisibility(View.VISIBLE);
            prodcodelbl.setVisibility(View.GONE);
            amountlbl.setVisibility(View.VISIBLE);
            mobileNote.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearVariable() {

        try {
            productcodeval = "";
            amountval = "";
            productcode.setText(productcodeval);
            productval.setText(productcodeval);
            amount.setText(amountval);
        } catch (Exception e) {
        }


    }

    private void showDiscountDialog() {

        try {
            TextView popok;
            TextView popcancel;
            TextView popamountpaid;
            TextView popservicecharge;
            TextView poptotalamount;

            if (dialog == null) {
                dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
                dialog.setCancelable(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.pop_discount_dialog);

                popamountpaid = dialog.findViewById(R.id.popamounttopayval);
                popservicecharge = dialog.findViewById(R.id.popservicechargeval);
                poptotalamount = dialog.findViewById(R.id.poptotalval);
                popok = dialog.findViewById(R.id.popok);
                popcancel = dialog.findViewById(R.id.popcancel);


                //set value
                popamountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(amountval)));

                double checkdiscount = Double.parseDouble(discount);
                if (checkdiscount > 0) {
                    popservicecharge.setVisibility(View.VISIBLE);
                    popservicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(discount)));
                } else {
                    popservicecharge.setVisibility(View.GONE);
                    popservicecharge.setText("0");
                }

                poptotalamount.setText(CommonFunctions.currencyFormatter(String.valueOf(totalamount)));

                dialog.show();
                isProceed = true;

                //click close
                popcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        CommonFunctions.hideDialog();
                        dialog = null;
                    }

                });

                popok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        CommonFunctions.hideDialog();
                        dialog = null;

                        Intent intent = new Intent(getViewContext(), Payment.class);
                        intent.putExtra("AMOUNT", totalamount);
                        intent.putExtra("MOBILE", mobileval);
                        intent.putExtra("NETWORK", networkval);
                        intent.putExtra("PRODUCTTYPE", producttypeval);
                        intent.putExtra("PRODUCTCODE", productcodeval);
                        intent.putExtra("VOUCHERSESSION", vouchersession);
                        intent.putExtra("DISCOUNT", discount);
                        intent.putExtra("FROMRETAILERLOADING", "");
                        intent.putExtra("GROSSPRICE", String.valueOf(amountval));
                        intent.putExtra("GKSERVICECODE", servicecode);
                        intent.putExtra("GKHASDISCOUNT", hasdiscount);
                        intent.putExtra("LATITUDE", latitude);
                        intent.putExtra("LONGITUDE", longitude);
                        startActivity(intent);
                    }

                });
            }
        } catch (Exception e) {
            isProceed = true;
        }

    }

    //IF BORROWER IS A RESELLER AND NOT IN SERVICE AREA.
    private void noDiscountResellerServiceArea() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .content(discountmessage)
                .cancelable(false)
                .positiveText("OK")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        new HttpAsyncTask1().execute(CommonVariables.GETSMARTRETAILERDISCOUNT);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        isProceed = true;
                        mDialog.dismiss();
                        CommonFunctions.hideDialog();
                    }
                })
                .show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mDialog.getView());
    }

    //CHECKS THE LONG LAT FOR DISCOUNT
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
        latitude = String.valueOf(currentlat);
        longitude = String.valueOf(currentlon);
    }

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
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        showError("GPS must be enabled to proceed.");
                    }
                })
                .show();
}

    private void getLastKnowLocation() {
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
                                latitude = String.valueOf(location.getLongitude());
                                longitude = String.valueOf(location.getLatitude());
                            }
                        }

                    });
            if(latitude.equals("0.0") || longitude.equals("0.0") || latitude.equals("null") || longitude.equals("null")) {
                showError("GPS service was not fully loaded yet. Please try again in a few seconds.");
            }
            else {
                calculateDiscountForReseller(calculateDiscountForResellerCallBack);
            }
        }
    }

    //PROCEED PAYMENT
    public void proceedPayment(View view) {

        try {

            if (isProceed) {
                isProceed = false;
                mobileval = mobilenumber.getText().toString();
                networkval = network.getText().toString();
                amountval = amount.getText().toString();
                productcodeval = productcode.getText().toString() + "" + amountval;

                if (!mobileval.contentEquals("") && mobileval.length() == 10 && !networkval.equals("")) {

                    if (!mobileval.equals("") && !productcodeval.equals("") && !amountval.equals("")) {

                        //Check is the amount if valid amount
                        String isvalidamount = "1";
                        if (Integer.parseInt(amountval) == 300
                                || Integer.parseInt(amountval) >= 500
                                && Integer.parseInt(amountval) <= 20000) {
                            isvalidamount = "1";
                        } else {
                            isvalidamount = "0";
                        }

                        if (!isvalidamount.equals("0")) {
                            int status = CommonFunctions.getConnectivityStatus(getBaseContext());
                            if (status == 0) { //no connection
                                isProceed = true;
                                CommonFunctions.showSnackbar(getViewContext(),findViewById(R.id.activity_purchase_retailer_load),"All fields are mandatory. ","WARNING");
                            } else {
                                  getSession();
                            }
                        } else {
                            isProceed = true;
                            amount.setError("Invalid amount. ");
                        }
                    } else {
                        isProceed = true;
                        CommonFunctions.showSnackbar(getViewContext(),findViewById(R.id.activity_purchase_retailer_load),"All fields are mandatory. ","WARNING");
                    }

                } else {
                    isProceed = true;
                    mobilenumber.setError("Invalid Mobile Number");
                }
            }
        } catch (Exception e) {
            isProceed = true;
        }


    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            CommonFunctions.showDialog(this, "", "Processing. Please wait ...", false);
            if (Double.parseDouble(amountval) > 0) {
                try {
                    if (distributorid.equals("") || distributorid.equals(".")
                            || resellerid.equals("") || resellerid.equals(".")) {
                        if(latitude.equals("") || longitude.equals("")
                                || latitude.equals("null") || longitude.equals("null")) {
                            latitude = "0.0";
                            longitude = "0.0";
                        }
                        calculateDiscountForReseller(calculateDiscountForResellerCallBack);
                    } else {
                        checkGPSforDiscount();
                        if(latitude.equals("") || longitude.equals("")
                                || latitude.equals("null") || longitude.equals("null")) {
                            latitude = "0.0";
                            longitude = "0.0";
                        }
                        calculateDiscountForReseller(calculateDiscountForResellerCallBack);
                    }
                } catch (Exception e) {
                    isProceed = true;
                    e.printStackTrace();
                }
            } else {
                isProceed = true;
                CommonFunctions.hideDialog();
                showToast("Invalid Amount.", GlobalToastEnum.WARNING);
            }
        } else {
            isProceed = true;
            CommonFunctions.hideDialog();
            showNoInternetToast();
        }
    }

    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + useridval + sessionid);
            try {

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("amountpaid", amountval);
                jsonObject.accumulate("userid", useridval);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("authcode", authcode);
                //convert JSONObject to JSON to String
                json = jsonObject.toString();


            } catch (Exception e) {
                isProceed = true;
                CommonFunctions.hideDialog();
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

                if (status.equals("000")) {
                    discount = parentObj.getString("discount");
                    //calculate
                    totalamount = String.valueOf((Double.parseDouble(amountval) - Double.parseDouble(discount)));

                    if (Double.parseDouble(amountval) > 0) {
                        //call AsynTask to perform network operation on separate thread
                        new HttpAsyncTask().execute(CommonVariables.PREPURCHASE);
                    } else {
                        isProceed = true;
                        CommonFunctions.hideDialog();
                        showToast("Invalid Amount.", GlobalToastEnum.WARNING);
                    }

                } else {
                    isProceed = true;
                    showToast("" + message, GlobalToastEnum.WARNING);
                }

                CommonFunctions.hideDialog();


            } catch (Exception e) {
                CommonFunctions.hideDialog();
                isProceed = true;
                e.printStackTrace();
            }
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + useridval + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("amountpurchase", totalamount);
                jsonObject.accumulate("userid", useridval);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("authcode", authcode);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                CommonFunctions.hideDialog();
                isProceed = true;
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {
                if (result == null) {
                    isProceed = true;
                    CommonFunctions.hideDialog();
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("001")) {
                    isProceed = true;
                    CommonFunctions.hideDialog();
                    showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    isProceed = true;
                    CommonFunctions.hideDialog();
                    showToast("Invalid Authentication.", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    isProceed = true;
                    CommonFunctions.hideDialog();
                    showToast("Session Expired.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    isProceed = true;
                    CommonFunctions.hideDialog();
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    vouchersession = result;
                    showDiscountDialog();
                }
            } catch (Exception e) {
                CommonFunctions.hideDialog();
                e.printStackTrace();
            }
        }
    }

    private void calculateDiscountForReseller(Callback<DiscountResponse> calculateDiscountForResellerCallBack) {
        authcode = CommonFunctions.getSha1Hex(imei + useridval + sessionid);

        Call<DiscountResponse> discountresponse = RetrofitBuild.getDiscountService(getViewContext())
                .calculateDiscountForReseller(
                        useridval,
                        imei,
                        authcode,
                        sessionid,
                        borroweridval,
                        ".",
                        Double.parseDouble(amountval),
                        servicecode,
                        longitude,
                        latitude
                );

        discountresponse.enqueue(calculateDiscountForResellerCallBack);
    }

    private final Callback<DiscountResponse> calculateDiscountForResellerCallBack =
            new Callback<DiscountResponse>() {

                @Override
                public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {

                    ResponseBody errorBody = response.errorBody();
                    if (errorBody == null) {
                        if (response.body().getStatus().equals("000")) {
                            resellerdiscount = response.body().getDiscount();

                            discount = String.valueOf(resellerdiscount);

                            if (resellerdiscount <= 0) {
                                discount = "";
                                hasdiscount = 0;
                                new HttpAsyncTask1().execute(CommonVariables.GETSMARTRETAILERDISCOUNT);
                            } else {
                                hasdiscount = 1;
                                totalamount = String.valueOf((Double.parseDouble(amountval) - Double.parseDouble(discount)));
                                if (Double.parseDouble(String.valueOf(totalamount)) > 0) {
                                    new HttpAsyncTask().execute(CommonVariables.PREPURCHASE);
                                }
                            }

                            CommonFunctions.hideDialog();

                        } else if (response.body().getStatus().equals("005")) {
                            resellerdiscount = response.body().getDiscount();
                            discount = String.valueOf(resellerdiscount);
                            discountmessage = response.body().getMessage();

                            discount = "";
                            hasdiscount = 0;
//                            noDiscountResellerServiceArea();
                            new HttpAsyncTask1().execute(CommonVariables.GETSMARTRETAILERDISCOUNT);

                            CommonFunctions.hideDialog();
                        } else {
                            showError(response.body().getMessage());
                            CommonFunctions.hideDialog();
                        }
                    } else {
                        showError();
                        CommonFunctions.hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<DiscountResponse> call, Throwable t) {
                    showError();
                    CommonFunctions.hideDialog();
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


}
