package com.goodkredit.myapplication.activities.prepaidload;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.PrepaidLogs;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.DiscountResponse;
import com.goodkredit.myapplication.responses.prepaidload.CheckNetworkConnectionResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyPrepaidLoadActivity extends BaseActivity {
    Context mcontext;
    DatabaseHandler db;
    static EditText country;
    static EditText countrycode;
    static EditText mobile;
    static EditText network;
    static EditText productcode;
    static EditText amount;
    static Spinner spinType;
    ArrayAdapter<String> spinTypeAdapter;
    static EditText productval;

    static TextView networklbl;
    static TextView prodtypelbl;
    static TextView prodcodelbl;
    static TextView amountlbl;
    private TextView mobileNote;

    static String mobileval = "";
    static String networkval = "";
    static String productcodeval = "";
    static String amountval = "";
    static String useridval = "";
    static String borroweridval = "";
    static String imei = "";

    int imgClear;

    String countrycodeval = "";
    String countryval = "";
    String brand = "";
    String networkprefix = "";
    String[] selecttype;
    String producttypeval = "";
    String productcodevalue = "";
    boolean isProceed = true;

    private double resellerDiscount = 0;
    private double resellerAmount = 0;
    private double resellerTotalAmount = 0;
    private String authcode;

    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String param;
    private String keyEncryption;
    private String authenticationid;
    private String index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set context
        mcontext = this;

        //SESSIONID
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        //inflate view
        setContentView(R.layout.activity_airtime_purchase);
        try {
            //set toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            //Initialize database
            db = new DatabaseHandler(this);

            //get country
            String currentcountry = GetCountryZipCode();

            //intialize here
            selecttype = getResources().getStringArray(R.array.loadtype);
            imgClear = R.mipmap.xcircle;
            country = findViewById(R.id.country);
            mobile = findViewById(R.id.mobilenumber);
            network = findViewById(R.id.network);
            productcode = findViewById(R.id.productcode);
            amount = findViewById(R.id.amount);
            productval = findViewById(R.id.productval);

            networklbl = findViewById(R.id.networklbl);
            prodtypelbl = findViewById(R.id.prodtypelbl);
            prodcodelbl = findViewById(R.id.prodcodelbl);
            amountlbl = findViewById(R.id.amountlbl);
            mobileNote = findViewById(R.id.mobileNote);


            countrycode = findViewById(R.id.countrycode);
            // country.setText("Philippines (+63)");
            country.setText("+63");
            countrycode.setText("63");

            spinType = findViewById(R.id.loadtype);
            // spinType.setOnItemSelectedListener(mcontext);
            spinTypeAdapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_spinner_dropdown_item, selecttype);
            spinTypeAdapter.setDropDownViewResource(R.layout.spinner_arrow);
            spinType.setAdapter(spinTypeAdapter);

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
                countrycodeval = "63";
                countryval = "+63";
                mobileval = item.getMobileTarget();

                if(mobileval.length() >= 10) {
                    mobileval = mobileval.substring(mobileval.length() - 10);
                }

                brand = item.getNetwork();
                productcodevalue = item.getProductCode();
                amountval = item.getAmount();
                producttypeval = item.getDenomType();

                processRetry();
            }


        /*--------------
        TRIGGERS
        ----------------*/
            mobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count > 0) {
                        //mobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, imgClear, 0);
                        Drawable img;
                        Resources res = getResources();
                        img = ContextCompat.getDrawable(getViewContext(), R.mipmap.xcircle);
                        img.setBounds(0, 0, 36, 36);
                        mobile.setCompoundDrawables(null, null, img, null);
                    } else {
                        mobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 10) {
                        clearVariable();
                        Character num = mobile.getText().toString().charAt(0);
                        if (num.toString().equals("0") || num.toString().equals("6")) {
                            showToast("Invalid Mobile Number", GlobalToastEnum.WARNING);
                            networklbl.setVisibility(View.GONE);
                            network.setVisibility(View.GONE);
                            prodtypelbl.setVisibility(View.GONE);
                            spinType.setVisibility(View.GONE);
                            productcode.setVisibility(View.GONE);
                            prodcodelbl.setVisibility(View.GONE);
                            amountlbl.setVisibility(View.GONE);
                            amount.setVisibility(View.GONE);

                            brand = "";
                        } else {
                            networkprefix = mobile.getText().toString().substring(0, 3);
                            brand = db.getNetworkPrefix(db, networkprefix);

                            if (brand.length() == 0) {

                                networkprefix = mobile.getText().toString().substring(0, 5);
                                brand = db.getNetworkPrefix(db, networkprefix);
                                if (brand.length() == 0) {
                                    brand = "";
                                    networklbl.setVisibility(View.GONE);
                                    network.setVisibility(View.GONE);
                                    prodtypelbl.setVisibility(View.GONE);
                                    spinType.setVisibility(View.GONE);
                                    productcode.setVisibility(View.GONE);
                                    prodcodelbl.setVisibility(View.GONE);
                                    amountlbl.setVisibility(View.GONE);
                                    amount.setVisibility(View.GONE);
                                    showToast("Invalid Mobile Number (Prefix not defined.)", GlobalToastEnum.WARNING);

                                } else {


                                    network.setText(brand);
                                    networklbl.setVisibility(View.VISIBLE);
                                    network.setVisibility(View.VISIBLE);
                                    mobileNote.setVisibility(View.GONE);
                                    hideSoftKeyboard();

                                    if (brand.equalsIgnoreCase("GLOBE") || brand.equalsIgnoreCase("TM") || brand.contains("SMART") || brand.contains("TNT")) {

                                        prodtypelbl.setVisibility(View.VISIBLE);
                                        spinType.setVisibility(View.VISIBLE);
                                        spinType.setSelection(0);
                                        productcode.setVisibility(View.GONE);
                                        prodcodelbl.setVisibility(View.GONE);
                                    } else if (brand.equalsIgnoreCase("ABS") || brand.equalsIgnoreCase("CHERRY")) {
                                        amount.setVisibility(View.VISIBLE);
                                        amountlbl.setVisibility(View.VISIBLE);
                                        amount.setFocusable(true);
                                        amount.requestFocus();
                                        amount.setFocusableInTouchMode(true);
                                        prodcodelbl.setVisibility(View.GONE);
                                        productcode.setVisibility(View.GONE);
                                        productcode.setText("Regular");
                                        producttypeval = "Regular";

                                        if (brand.contains("SMART") || brand.contains("TNT")) {
                                            productval.setText("MYLOAD");
                                        } else {
                                            productval.setText("LD");
                                        }

                                    } else {
                                        productcodeval = "";
                                        productcode.setVisibility(View.VISIBLE);
                                        prodcodelbl.setVisibility(View.VISIBLE);
                                        amount.setVisibility(View.GONE);
                                        amountlbl.setVisibility(View.GONE);
                                        spinType.setVisibility(View.GONE);
                                        prodtypelbl.setVisibility(View.GONE);
                                        producttypeval = "Special";
                                    }
                                }
                            } else {

                                network.setText(brand);
                                networklbl.setVisibility(View.VISIBLE);
                                network.setVisibility(View.VISIBLE);
                                mobileNote.setVisibility(View.GONE);
                                hideSoftKeyboard();

                                if (brand.equalsIgnoreCase("GLOBE") || brand.equalsIgnoreCase("TM") || brand.contains("SMART") || brand.contains("TNT")) {

                                    prodtypelbl.setVisibility(View.VISIBLE);
                                    spinType.setVisibility(View.VISIBLE);
                                    spinType.setSelection(0);
                                    productcode.setVisibility(View.GONE);
                                    prodcodelbl.setVisibility(View.GONE);
                                } else if (brand.equalsIgnoreCase("ABS") || brand.equalsIgnoreCase("CHERRY")) {
                                    amount.setVisibility(View.VISIBLE);
                                    amountlbl.setVisibility(View.VISIBLE);
                                    amount.setFocusable(true);
                                    amount.requestFocus();
                                    amount.setFocusableInTouchMode(true);
                                    prodcodelbl.setVisibility(View.GONE);
                                    productcode.setVisibility(View.GONE);
                                    productcode.setText("Regular");
                                    producttypeval = "Regular";

                                    if (brand.contains("SMART") || brand.contains("TNT")) {
                                        productval.setText("MYLOAD");
                                    } else {
                                        productval.setText("LD");
                                    }

                                } else {
                                    productcode.setVisibility(View.VISIBLE);
                                    prodcodelbl.setVisibility(View.VISIBLE);
                                    amount.setVisibility(View.GONE);
                                    amountlbl.setVisibility(View.GONE);
                                    spinType.setVisibility(View.GONE);
                                    prodtypelbl.setVisibility(View.GONE);
                                    producttypeval = "Special";
                                }
                            }

                        }


                    } else {
                        clearVariable();
                        networklbl.setVisibility(View.GONE);
                        network.setVisibility(View.GONE);
                        spinType.setVisibility(View.GONE);
                        spinType.setSelection(0);
                        productcode.setVisibility(View.GONE);
                        prodcodelbl.setVisibility(View.GONE);
                        amount.setVisibility(View.GONE);
                        amountlbl.setVisibility(View.GONE);
                        prodtypelbl.setVisibility(View.GONE);
                        mobileNote.setVisibility(View.VISIBLE);
                    }

                }
            });


            spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                protected Adapter initializedAdapter = null;

                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    producttypeval = parentView.getItemAtPosition(position).toString();

                    if (producttypeval.equals("Regular")) {
                        amount.setVisibility(View.VISIBLE);
                        amountlbl.setVisibility(View.VISIBLE);
                        amount.setFocusable(true);
                        amount.requestFocus();
                        amount.setFocusableInTouchMode(true);
                        prodcodelbl.setVisibility(View.GONE);
                        productcode.setVisibility(View.GONE);
                        productcode.setText("Regular");

                        if (brand.contains("SMART") || brand.contains("TNT")) {
                            productval.setText("MYLOAD");
                        } else {
                            productval.setText("LD");
                        }


                    } else if (producttypeval.equals("Special")) {
                        prodcodelbl.setVisibility(View.VISIBLE);
                        productcode.setVisibility(View.VISIBLE);
                        amount.setVisibility(View.GONE);
                        amountlbl.setVisibility(View.GONE);
                        amount.setFocusable(false);
                        productcode.setText("");
                        productval.setText("");
                    } else {
                        amount.setVisibility(View.GONE);
                        amountlbl.setVisibility(View.GONE);
                        amount.setFocusable(false);
                        amount.setText("");
                        prodcodelbl.setVisibility(View.GONE);
                        productcode.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });


            mobile.setOnTouchListener(new View.OnTouchListener() {
                final int DRAWABLE_RIGHT = 2;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (mobile.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                            int leftEdgeOfRightDrawable = mobile.getRight()
                                    - mobile.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                            leftEdgeOfRightDrawable -= getResources().getDimension(R.dimen.EdtPadding);
                            if (event.getRawX() >= leftEdgeOfRightDrawable) {
                                mobile.setText("");
                                network.setText("");
                                productcode.setText("");
                                productval.setText("");
                                amount.setText("");
                                networklbl.setVisibility(View.GONE);
                                network.setVisibility(View.GONE);
                                spinType.setVisibility(View.GONE);
                                spinType.setSelection(0);
                                productcode.setVisibility(View.GONE);
                                prodcodelbl.setVisibility(View.GONE);
                                amount.setVisibility(View.GONE);
                                amountlbl.setVisibility(View.GONE);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String proddesc = data.getStringExtra("ProductDescription");
                    String prodcode = data.getStringExtra("ProductCode");
                    String prodamount = data.getStringExtra("ProductAmount");

                    if (!proddesc.equals("") && !prodcode.equals("") && !prodamount.equals("")) {
                        productcode.setText(proddesc);
                        productval.setText(prodcode);
                        amount.setText(prodamount);
                        amount.setVisibility(View.VISIBLE);
                        amountlbl.setVisibility(View.VISIBLE);
                        amount.setFocusable(false);
                    }

                    hideSoftKeyboard();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }


    public static void start(Context context, PrepaidLogs item) {
        Intent intent = new Intent(context, BuyPrepaidLoadActivity.class);
        intent.putExtra("item", item);
        context.startActivity(intent);
    }

    /*
     * FUNCTIONS
     * */

    public void getProductCode(View view) {
        Intent intent = new Intent(this, PrepaidLoadProductCodesActivity.class);
        intent.putExtra("Brand", brand);
        startActivityForResult(intent, 1);
    }

    public String GetCountryZipCode() {
        String CountryID = "";
        String CountryZipCode = "";
        try {
            TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            //getNetworkCountryIso
            CountryID = manager.getSimCountryIso().toUpperCase();
            String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
            for (int i = 0; i < rl.length; i++) {
                String[] g = rl[i].split(",");
                if (g[1].trim().equals(CountryID.trim())) {
                    countrycodeval = g[0];
                    countryval = g[2];
                    CountryZipCode = countryval + " (+" + countrycodeval + ")";
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CountryZipCode;
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void proceedPayment(View view) {

        try {
            if (isProceed == true) {
                isProceed = false;

                mobileval = mobile.getText().toString();
                networkval = network.getText().toString();
                amountval = amount.getText().toString();

                if (productval.getText().toString().equals("MYLOAD")) {
                    productcodeval = productval.getText().toString() + "" + amountval;
                } else {
                    productcodeval = productval.getText().toString();
                }

                if (!mobileval.contentEquals("") && mobileval.length() == 10) {
                    if (!mobileval.equals("") && !networkval.equals("") && !productcodeval.equals("") && !amountval.equals("")) {

                        //Check is the amount if valid amount
                        String isvalidamount = "1";
                        if (productval.getText().toString().equals("LD")) {
                            isvalidamount = db.isValidAmount(db, productval.getText().toString(), amountval, networkval);

                        } else if (productval.getText().toString().equals("MYLOAD")) {
                            if (Integer.parseInt(amountval) >= 5 && Integer.parseInt(amountval) <= 1000) {
                                isvalidamount = "1";
                            } else {
                                isvalidamount = "0";
                            }
                        }

                        if (!isvalidamount.equals("0")) {
                            int status = CommonFunctions.getConnectivityStatus(getBaseContext());
                            if (status == 0) {
                                isProceed = true;//no connection
                                showToast("No internet connection.", GlobalToastEnum.NOTICE);
                            } else {

                                getSession();
//                                CommonFunctions.showDialog(mcontext, "", "Validating Voucher. Please wait ...", false);
//
//                                CreateSession newsession = new CreateSession(this);
//                                newsession.setQueryListener(new CreateSession.QueryListener() {
//                                    @SuppressWarnings("unchecked")
//                                    public void QuerySuccessFul(String data) {
//                                        if (data == null) {
//                                            isProceed = true;
//                                            cf.hideDialog();
//                                            showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
//                                        } else if (data.equals("001")) {
//                                            isProceed = true;
//                                            cf.hideDialog();
//                                            showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
//                                        } else if (data.equals("002")) {
//                                            isProceed = true;
//                                            cf.hideDialog();
//                                            showToast("Something went wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
//                                        } else if (data.equals("error")) {
//                                            isProceed = true;
//                                            cf.hideDialog();
//                                            showToast("Something went wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
//                                        } else if (data.contains("<!DOCTYPE html>")) {
//                                            isProceed = true;
//                                            cf.hideDialog();
//                                            showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
//                                        } else {
//                                            sessionid = data;
//                                            authcode = CommonFunctions.getSha1Hex(imei + useridval + sessionid);
//                                            //call AsynTask to perform network operation on separate thread
//                                            calculateDiscountForReseller(calculateDiscountForResellerSession);
//                                        }
//                                    }
//
//                                });
//                                newsession.execute(cv.CREATESESSION, imei, useridval);
                            }
                        } else {
                            isProceed = true;
                            showToast("Invalid amount.", GlobalToastEnum.WARNING);
                        }
                    } else {
                        isProceed = true;
                        showToast("All fields are mandatory. Please check.", GlobalToastEnum.WARNING);

                    }
                } else {
                    isProceed = true;
                    showToast("Invalid Mobile Number", GlobalToastEnum.WARNING);
                }
            }


        } catch (Exception e) {
            isProceed = true;
        }
    }


//    private void callPrePurchase() {
//        CommonFunctions.showDialog(mcontext, "", "Processing. Please wait ...", false);
//
//        CreateSession newsession = new CreateSession(getViewContext());
//        newsession.setQueryListener(new CreateSession.QueryListener() {
//            @SuppressWarnings("unchecked")
//            public void QuerySuccessFul(String data) {
//                if (data == null) {
//                    isProceed = true;
//                    CommonFunctions.hideDialog();
//                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (data.equals("001")) {
//                    isProceed = true;
//                    CommonFunctions.hideDialog();
//                    showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
//                } else if (data.equals("002")) {
//                    isProceed = true;
//                    CommonFunctions.hideDialog();
//                    showToast("Something went wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
//                } else if (data.equals("error")) {
//                    isProceed = true;
//                    CommonFunctions.hideDialog();
//                    showToast("Something went wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
//                } else if (data.contains("<!DOCTYPE html>")) {
//                    isProceed = true;
//                    CommonFunctions.hideDialog();
//                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = data;
//                    authcode = CommonFunctions.getSha1Hex(imei + useridval + sessionid);
//
//                    //call AsynTask to perform network operation on separate thread
//                    new HttpAsyncTask().execute(cv.PREPURCHASE);
//                }
//            }
//
//        });
//        newsession.execute(CommonVariables.CREATESESSION, imei, useridval);
//    }

    private void callPrePurchase() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            CommonFunctions.showDialog(mcontext, "", "Processing. Please wait ...", false);
            authcode = CommonFunctions.getSha1Hex(imei + useridval + sessionid);
            new HttpAsyncTask().execute(CommonVariables.PREPURCHASE);
        } else {
            CommonFunctions.hideDialog();
            isProceed = true;
            showNoInternetToast();
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
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("amountpurchase", String.valueOf(resellerTotalAmount));
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
            CommonFunctions.hideDialog();
            isProceed = true;
            try {
                if (result == null) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("001")) {
                    showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    showToast("Invalid Authentication.", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    showToast("Session Expired.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {

                    if (Double.parseDouble(amountval) > 0) {
                        Intent intent = new Intent(getBaseContext(), Payment.class);
                        intent.putExtra("AMOUNT", String.valueOf(resellerTotalAmount));
                        intent.putExtra("MOBILE", mobileval);
                        intent.putExtra("NETWORK", brand);
                        intent.putExtra("PRODUCTTYPE", producttypeval);
                        intent.putExtra("PRODUCTCODE", productcodeval);
                        intent.putExtra("VOUCHERSESSION", result);
                        intent.putExtra("GROSSAMOUNT", amountval);
                        intent.putExtra("PREPAIDLOADINGRESELLERDISCOUNT", String.valueOf(resellerDiscount));
                        startActivity(intent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void processRetry() {

        productcodeval = db.getProductCodeDesc(db, brand, productcodevalue);

        //set value here
        country.setText(countryval);
        countrycode.setText(countrycodeval);
        mobile.setText(mobileval);
        network.setText(brand);
        productcode.setText(productcodeval);
        productval.setText(productcodevalue);
        amount.setText(amountval);

        //set visibility
        country.setVisibility(View.VISIBLE);
        mobile.setVisibility(View.VISIBLE);
        network.setVisibility(View.VISIBLE);
        productcode.setVisibility(View.VISIBLE);
        amount.setVisibility(View.VISIBLE);

        networklbl.setVisibility(View.VISIBLE);
        prodcodelbl.setVisibility(View.VISIBLE);
        amountlbl.setVisibility(View.VISIBLE);
        mobileNote.setVisibility(View.GONE);


        if (brand.equalsIgnoreCase("GLOBE") || brand.equalsIgnoreCase("TM") || brand.contains("SMART") || brand.contains("TNT")) {

            spinType.setVisibility(View.VISIBLE);
            prodtypelbl.setVisibility(View.VISIBLE);

            if (producttypeval.equals("Regular")) {
                spinType.setSelection(1);
            } else {
                spinType.setSelection(2);
            }

        } else {
            spinType.setVisibility(View.GONE);
            prodtypelbl.setVisibility(View.GONE);
        }

    }

    private void clearVariable() {

        productcodeval = "";
        productcodevalue = "";
        amountval = "";
        productcode.setText(productcodeval);
        productval.setText(productcodevalue);
        amount.setText(amountval);
    }

    private void calculateDiscountForReseller(Callback<DiscountResponse> calculateDiscountForResellerCallback) {
        PreferenceUtils.saveStringPreference(getViewContext(), "prepaidloading_service_code", "BUYPREPAIDLOAD");

        GPSTracker gpsTracker = new GPSTracker(getViewContext());

        Call<DiscountResponse> resellerdiscount = RetrofitBuild.getDiscountService(getViewContext())
                .calculateDiscountForReseller(useridval,
                        imei,
                        authcode,
                        sessionid,
                        borroweridval,
                        ".",
                        Double.valueOf(amountval),
                        "BUYPREPAIDLOAD",
                        String.valueOf(gpsTracker.getLongitude()),
                        String.valueOf(gpsTracker.getLatitude()));
        resellerdiscount.enqueue(calculateDiscountForResellerCallback);
    }

    private final Callback<DiscountResponse> calculateDiscountForResellerSession = new Callback<DiscountResponse>() {

        @Override
        public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {

            CommonFunctions.hideDialog();

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    resellerAmount = Double.valueOf(amountval);
                    resellerDiscount = response.body().getDiscount();
                    resellerTotalAmount = Double.valueOf(amountval) - resellerDiscount;

                    if (resellerDiscount > 0) {

                        showDiscountDialog();

                    } else {

                        callPrePurchase();
                    }

                } else if (response.body().getStatus().equals("005")) {

                    new MaterialDialog.Builder(getViewContext())
                            .content(response.body().getMessage())
                            .cancelable(false)
                            .negativeText("Cancel")
                            .positiveText("OK")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    resellerAmount = Double.valueOf(amountval);
                                    resellerDiscount = 0;
                                    resellerTotalAmount = Double.valueOf(amountval) - resellerDiscount;

                                    callPrePurchase();

                                }
                            })
                            .dismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {

                                    isProceed = true;

                                }
                            })
                            .show();

                } else {
                    isProceed = true;
                    showError(response.body().getMessage());
                }
            } else {
                isProceed = true;
                showError();
            }

        }

        @Override
        public void onFailure(Call<DiscountResponse> call, Throwable t) {
            isProceed = true;
            CommonFunctions.hideDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showDiscountDialog() {

        try {
            TextView popok;
            TextView popcancel;
            TextView popamountpaid;
            TextView popservicecharge;
            TextView poptotalamount;

            final Dialog dialog = new Dialog(new ContextThemeWrapper(getViewContext(), android.R.style.Theme_Holo_Light));
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_discount_dialog);

            popamountpaid = dialog.findViewById(R.id.popamounttopayval);
            popservicecharge = dialog.findViewById(R.id.popservicechargeval);
            poptotalamount = dialog.findViewById(R.id.poptotalval);
            popok = dialog.findViewById(R.id.popok);
            popcancel = dialog.findViewById(R.id.popcancel);

            //set value
            popamountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerAmount)));
            popservicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerDiscount)));
            poptotalamount.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerTotalAmount)));


            dialog.show();

            //click close
            popcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isProceed = true;
                    dialog.dismiss();
                }

            });

            popok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callPrePurchase();

                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//
//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, useridval);
//
//            call.enqueue(callsession);
//
//        } else {
//            isProceed = true;
//            showError(getString(R.string.generic_internet_error_message));
//        }
//
//    }
//
//    private final Callback<String> callsession = new Callback<String>()     {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    isProceed = true;
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    isProceed = true;
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    isProceed = true;
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + useridval + sessionid);
//
//                    checkNetworkConnection(checkNetworkConnectionSession);
//                }
//            } else {
//                isProceed = true;
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            isProceed = true;
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + useridval + sessionid);
            //calculateDiscountForReseller(calculateDiscountForResellerSession);
            calculateDiscountForResellerV2();
        } else {
            isProceed = true;
            showNoInternetToast();
        }
    }


    /*
     *SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
    * */

    private void calculateDiscountForResellerV2(){
        try{

            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

                PreferenceUtils.saveStringPreference(getViewContext(), "prepaidloading_service_code", "BUYPREPAIDLOAD");
                GPSTracker gpsTracker = new GPSTracker(getViewContext());


                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",useridval);
                parameters.put("borrowerid",borroweridval);
                parameters.put("merchantid",".");
                parameters.put("amountpaid",String.valueOf(Double.valueOf(amountval)));
                parameters.put("servicecode","BUYPREPAIDLOAD");
                parameters.put("longitude",String.valueOf(gpsTracker.getLongitude()));
                parameters.put("latitude",String.valueOf(gpsTracker.getLatitude()));
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

               calculateDiscountForResellerObjectV2(calculateDiscountForResellerSessionV2);

            }else{
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }

        }catch (Exception e){
            CommonFunctions.hideDialog();
            showErrorToast();
            e.printStackTrace();
        }
    }

    private void calculateDiscountForResellerObjectV2(Callback<GenericResponse> calculateDiscountForResellerCallback) {
        Call<GenericResponse> resellerdiscount = RetrofitBuilder.getCommonV2API(getViewContext())
                .calculateDiscountForResellerV3(authenticationid,sessionid,param);
        resellerdiscount.enqueue(calculateDiscountForResellerCallback);
    }

    private final Callback<GenericResponse> calculateDiscountForResellerSessionV2 = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            CommonFunctions.hideDialog();

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());

                if (response.body().getStatus().equals("000")) {

                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                   if(decryptedData.equalsIgnoreCase("error") || decryptedMessage.equalsIgnoreCase("error")){
                       showErrorToast();
                   }else{
                       resellerAmount = Double.valueOf(amountval);
                       resellerDiscount = Double.parseDouble(decryptedData);
                       resellerTotalAmount = Double.valueOf(amountval) - resellerDiscount;

                       if (resellerDiscount > 0) {
                           showDiscountDialog();
                       } else {
                           callPrePurchase();
                       }
                   }

                } else if (response.body().getStatus().equals("005") && !decryptedMessage.contains("Unable to Access")) {

                    new MaterialDialog.Builder(getViewContext())
                            .content(response.body().getMessage())
                            .cancelable(false)
                            .negativeText("Cancel")
                            .positiveText("OK")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    resellerAmount = Double.valueOf(amountval);
                                    resellerDiscount = 0;
                                    resellerTotalAmount = Double.valueOf(amountval) - resellerDiscount;

                                    callPrePurchase();

                                }
                            })
                            .dismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {

                                    isProceed = true;

                                }
                            })
                            .show();

                }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else {
                    isProceed = true;
                    showError(decryptedMessage);
                }
            } else {
                isProceed = true;
                showError();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isProceed = true;
            CommonFunctions.hideDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

//    private void checkNetworkConnection(Callback<CheckNetworkConnectionResponse> checkNetworkConnectionCallback) {
//        Call<CheckNetworkConnectionResponse> checkconnection = RetrofitBuild.getPrepaidLoadService(getViewContext())
//                .checkNetworkConnectionCall(sessionid,
//                        imei,
//                        useridval,
//                        borroweridval,
//                        authcode,
//                        "AIRTIME",
//                        networkval);
//        checkconnection.enqueue(checkNetworkConnectionCallback);
//    }
//
//    private final Callback<CheckNetworkConnectionResponse> checkNetworkConnectionSession = new Callback<CheckNetworkConnectionResponse>() {
//
//        @Override
//        public void onResponse(Call<CheckNetworkConnectionResponse> call, Response<CheckNetworkConnectionResponse> response) {
//            ResponseBody errorBody = response.errorBody();
//
//            if (errorBody == null) {
//                if (response.body().getStatus().equals("000")) {
//
//                    if (response.body().isIntermittent()) {
//
//                        isProceed = true;
//                        showToast(response.body().getMessage(), GlobalToastEnum.WARNING);
//
//                    } else {
//
//                        CommonFunctions.showDialog(mcontext, "", "Validating Voucher. Please wait ...", false);
//
////                        CreateSession newsession = new CreateSession(getViewContext());
////                        newsession.setQueryListener(new CreateSession.QueryListener() {
////                            @SuppressWarnings("unchecked")
////                            public void QuerySuccessFul(String data) {
////                                if (data == null) {
////                                    isProceed = true;
////                                    cf.hideDialog();
////                                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
////                                } else if (data.equals("001")) {
////                                    isProceed = true;
////                                    cf.hideDialog();
////                                    showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
////                                } else if (data.equals("002")) {
////                                    isProceed = true;
////                                    cf.hideDialog();
////                                    showToast("Something went wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
////                                } else if (data.equals("error")) {
////                                    isProceed = true;
////                                    cf.hideDialog();
////                                    showToast("Something went wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
////                                } else if (data.contains("<!DOCTYPE html>")) {
////                                    isProceed = true;
////                                    cf.hideDialog();
////                                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
////                                } else {
////                                    sessionid = data;
////                                    authcode = CommonFunctions.getSha1Hex(imei + useridval + sessionid);
////                                    //call AsynTask to perform network operation on separate thread
////                                    calculateDiscountForReseller(calculateDiscountForResellerSession);
////                                }
////                            }
////
////                        });
////                        newsession.execute(cv.CREATESESSION, imei, useridval);
//
//                        calculateDiscountForReseller(calculateDiscountForResellerSession);
//                    }
//
//                } else {
//                    isProceed = true;
//                    showError(response.body().getMessage());
//                }
//            } else {
//                isProceed = true;
//                showError();
//            }
//
//        }
//
//        @Override
//        public void onFailure(Call<CheckNetworkConnectionResponse> call, Throwable t) {
//            isProceed = true;
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

}

