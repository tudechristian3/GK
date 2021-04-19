 package com.goodkredit.myapplication.activities.billspayment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.BillsPaymentLogs;
import com.goodkredit.myapplication.bean.SkycableSOA;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.DiscountResponse;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillsPaymentBillerDetailsActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private DatabaseHandler db;
    private CommonFunctions cf;
    private CommonVariables cv;
    private AQuery aq;

    private String billercode = "";
    private String spbillercode = "";
    private String spbilleraccountno = "";
    private String billerinfo = "";
    private String billername = "";
    private String billerlogo = "";
    private String billerservicecode = "";
    private String servicechargeclass = "";
    private String optionchoice = "";
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private double totalamount = 0.00;
    private double totalamounttopay = 0.00;
    private String vouchersession = "";
    private String servicecharge = "0";
    private String othercharges = "0";
    private String from = "";
    private String accountno = "";
    private String otherinfo = "";
    private String billernote = "";
    boolean isProceed = true;


    TextView callendar;
    LinearLayout linearLayout;

    List<Button> buttonslist = new ArrayList<Button>();
    List<EditText> inputslist = new ArrayList<EditText>();
    List<Spinner> spinnerlist = new ArrayList<Spinner>();
    List<TextView> textviewlist = new ArrayList<TextView>();
    final HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();
    JSONArray params = new JSONArray();
    JSONArray billspayresarr;
    private Dialog dialog = null;

    private SkycableSOA skycableSOA = null;
    private boolean isNewApplication = false;
    private boolean isPPV = false;

    //DISCOUNT
    private double discount;
    private String strdiscount = "";
    private String servicecode = "";
    private String latitude = "";
    private String longitude = "";
    private String strtotalamount = "";
    private int hasdiscount = 0;

    private GPSTracker tracker;
    private double conveniencefee = 0.00;

    //ADVANCE OPTIONS
    private JSONArray advjsonarr = new JSONArray();
    private List<TextView> txvadvlist = new ArrayList<>();
    private List<EditText> edtadvlist = new ArrayList<>();
    //private String advXMLremarks = "";
    private String receiptmobileno = "";
    private String receiptemailadd = "";
    private boolean isadv = false;
    private String discountmessage = "";

    private FusedLocationProviderClient mFusedLocationClient;

    private String distributorid = "";
    private String resellerid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String keyEncryption;
    private String authenticationid;
    private String param;

    private String prePurchaseIndex;
    private String prePurchaseAuthenticationid;
    private String prePurchaseKeyEncryption;
    private String prePurchaseParam;

    private String getServiceChargeIndex;
    private String getServiceChargeAuthID;
    private String getServiceChargeKey;
    private String getServiceChargeParam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_payment_biller_details);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        try {

            //initialize
            db = new DatabaseHandler(this);
            aq = new AQuery(this);

            distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
            resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            //get account information
            getAccountInfo(db);

            //get passed value from bills payment logs retry
            Intent intent = getIntent();
            BillsPaymentLogs item = intent.getParcelableExtra("item");

            if (item != null) {
                billername = item.getBillerName();
                billerlogo = item.getBillerLogoURL();
                billerinfo = item.getBillerInfo();
                billerservicecode = item.getServiceCode();
                servicechargeclass = item.getCustomerSCClass();
                accountno = item.getAccountNo();
                otherinfo = item.getBillDetails();
                billercode = item.getBillerCode();
                spbillercode = item.getServiceProviderBillerCode();
                spbilleraccountno = item.getAccountNo();
                from = "BILLSPAYMENTLOGS";
                servicecode = billerservicecode;

                getBillerInformation(from, billercode, spbilleraccountno);

            } else {
                //get the passed value
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    billercode = extras.getString("BILLCODE");
                    spbillercode = extras.getString("SPBILLCODE");
                    spbilleraccountno = extras.getString("SPBILLERACCOUNTNO");
                    billername = extras.getString("BILLNAME");
                    from = extras.getString("FROM");
                    //BILLCODE
                    skycableSOA = extras.getParcelable("skycableSOA");
                    isNewApplication = extras.getBoolean("NEWAPPLICATION", false);
                    isPPV = extras.getBoolean("PPV", false);

                    if (isNewApplication || isPPV || skycableSOA != null || extras.getBoolean("SKYCABLEBILLSPAYMENT", false)) {
                        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "skycableservicecode");
                    } else {
                        servicecode = extras.getString("ServiceCode");
                    }
                }
                //GET BILLER INFORMATION
                getBillerInformation(from, billercode, spbilleraccountno);

            }
            //set toolbar
            setupToolbarWithTitle(billername);

            //set layout
            linearLayout = findViewById(R.id.billerdetails);
            callendar = findViewById(R.id.calendar);
            ImageView logo = findViewById(R.id.billerlogo);
            TextView bnote = findViewById(R.id.billernote);

            //set image of the biller and biller note
            aq.id(logo).image(billerlogo, false, true);
            if (billernote.trim().equals(".") || billernote.trim() == null || billernote.trim().equals("null")) {
                bnote.setVisibility(View.VISIBLE);
            } else {
                bnote.setText(billernote.trim());
            }


            //parse parameters per biller
            String count = parseXML(billerinfo, "count");
            for (int i = 0; i < Integer.parseInt(count); i++) {
                String value = parseXML(billerinfo, String.valueOf(i));
                String field = parseXML(value, String.valueOf("mobile"));

                if (!field.equals("NONE")) {
                    String[] fieldarr = field.split(",");
                    if (fieldarr.length > 0) {
                        String description = fieldarr[0];
                        String name = fieldarr[1];
                        String datatype = fieldarr[2];
                        String mandatory = fieldarr[3];
                        String inputtype = fieldarr[4];

                        //set this for the submit checking of values
                        JSONObject obj = new JSONObject();
                        obj.put("name", name);
                        obj.put("mandatory", mandatory);
                        obj.put("description", description);
                        obj.put("datatype", datatype);
                        params.put(obj);

                        //1. Create title (description)
                        if (!mandatory.equals("NO")) {
                            description = description + " * ";
                        }

                        if (inputtype.contains("SELECT")) {

                            //1.
                            createTextView(name, description, mandatory);
                            //2.
                            createSpinner(name, inputtype, mandatory);

                        } else if (inputtype.contains("CALENDAR")) {

                            //1.
                            createTextView(name, description, mandatory);

                            //2.Add button
                            createButton(name, mandatory);


                        } else {//TEXT VIEW

                            //1.
                            createTextView(name, description, mandatory);

                            //2.Add inputs
                            createEditText(name, datatype, mandatory);

                        }

                    }
                }

            }//end loop

            createAdvanceOptions();

            //3. Add Button
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 40, 0, 10);
            Button submit = new Button(this);
            submit.setTextAppearance(getViewContext(), R.style.roboto_bold);
            submit.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_BOLD, "SUBMIT"));
            submit.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
            submit.setTag("SUBMIT");
            submit.setTextColor(Color.parseColor("white"));
            submit.setBackgroundResource(R.drawable.button);
            submit.setOnClickListener(this);
            linearLayout.addView(submit, layoutParams);


            //populate information for the borrower biller information
            if (from.equals("BORROWERBILLERS")) {
                populateBorrowerBillerInformation();
            } else if (from.equals("BILLSPAYMENTLOGS")) {
                otherinfo = CommonFunctions.parseJSON(otherinfo, "data");
                populateBorrowerBillerInformation();
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            hideSoftKeyboard();
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        return super.onOptionsItemSelected(item);
    }

    /***************
     * FUNCTIONS
     * *************/
    public static void start(Context context, BillsPaymentLogs item) {
        Intent intent = new Intent(context, BillsPaymentBillerDetailsActivity.class);
        intent.putExtra("item", item);
        context.startActivity(intent);
    }

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
    }

    private void getBillerInformation(String from, String spbillercode, String spbilleraccountno) {

        try {

            if (from.equals("BILLERS")) {
                //get account information

                Cursor cursor = db.getBillerInfo(db, spbillercode, "FALSE", "");
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    do {
                        billername = cursor.getString(cursor.getColumnIndex("BillerName"));
                        billerlogo = cursor.getString(cursor.getColumnIndex("LogoURL"));
                        billerinfo = cursor.getString(cursor.getColumnIndex("BillerInfo"));
                        billerservicecode = cursor.getString(cursor.getColumnIndex("ServiceCode"));
                        servicechargeclass = cursor.getString(cursor.getColumnIndex("CustomerSCClass"));
                        billernote = cursor.getString(cursor.getColumnIndex("Notes"));

                    } while (cursor.moveToNext());
                }
                //populate fields
                //String count = parseXML(billerinfo,"count");
            } else if (from.equals("BILLSPAYMENTLOGS")) {
                //get account information
                Cursor cursor = db.getBillerInfo(db, spbillercode, "FALSE", spbilleraccountno);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    do {
                        billername = cursor.getString(cursor.getColumnIndex("BillerName"));
                        billerlogo = cursor.getString(cursor.getColumnIndex("LogoURL"));
                        billerinfo = cursor.getString(cursor.getColumnIndex("BillerInfo"));
                        billerservicecode = cursor.getString(cursor.getColumnIndex("ServiceCode"));
                        servicechargeclass = cursor.getString(cursor.getColumnIndex("CustomerSCClass"));
                        billernote = cursor.getString(cursor.getColumnIndex("Notes"));
                    } while (cursor.moveToNext());
                }
            } else { //meaning from borrower biller information
                //get account information
                Cursor cursor = db.getBillerInfo(db, spbillercode, "TRUE", spbilleraccountno);

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    do {
                        billername = cursor.getString(cursor.getColumnIndex("BillerName"));
                        billerlogo = cursor.getString(cursor.getColumnIndex("LogoURL"));
                        billerinfo = cursor.getString(cursor.getColumnIndex("BillerInfo"));
                        billerservicecode = cursor.getString(cursor.getColumnIndex("ServiceCode"));
                        servicechargeclass = cursor.getString(cursor.getColumnIndex("CustomerSCClass"));
                        accountno = cursor.getString(cursor.getColumnIndex("AccountNo"));
                        otherinfo = cursor.getString(cursor.getColumnIndex("OtherInfo"));
                        billernote = cursor.getString(cursor.getColumnIndex("Notes"));
                    } while (cursor.moveToNext());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }

    }

    private void populateBorrowerBillerInformation() {

        try {

            JSONArray jsonArr = new JSONArray(otherinfo);


            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject obj = jsonArr.getJSONObject(i);
                String value = obj.getString("value");
                String id = obj.getString("name");

                //set the data in the input text
                for (EditText inputdata : inputslist) {
                    String f = inputdata.getTag().toString();
                    JSONObject jresponse = new JSONObject(f);
                    String name = jresponse.getString("name");

                    if (name.equals(id)) {
                        inputdata.setText(value.toString());
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }

    }

    private String parseXML(String data, String nametag) {
        String result = "";
        int startpoint;
        int endpoint;

        //getting the starting point
        startpoint = data.indexOf("<" + nametag + ">");
        //getting the endpoint
        endpoint = data.indexOf("</" + nametag + ">");
        if (startpoint == -1 || endpoint == -1) {
            //return empty
            result = "NONE";
        } else {
            int starttaglen = nametag.length() + 2;
            //returning the extracted data
            result = data.substring(startpoint + starttaglen, endpoint);
        }

        return result;
    }

    private void createTextView(String name, String description, String mandatory) {

        try {
            JSONObject txtviewtag = new JSONObject();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 40, 0, 10);
            TextView txtv = new TextView(this);
            txtv.setText(description);
            txtv.setTextSize(16);
            txtv.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_676767));
            txtv.setTextAppearance(getViewContext(), R.style.roboto_regular);
            txtv.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            txtviewtag.put("name", name);
            txtviewtag.put("mandatory", mandatory);
            txtv.setTag(txtviewtag);
            textviewlist.add(txtv);
            linearLayout.addView(txtv, layoutParams);

            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                txtv.setVisibility(View.VISIBLE);
            } else {
                txtv.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createEditText(String name, String datatype, String mandatory) {
        try {
            JSONObject edittexttag = new JSONObject();
            EditText editText = new EditText(this);
            editText.setPadding(20, 20, 20, 20);
            editText.setTextSize(16);
            edittexttag.put("name", name);
            edittexttag.put("mandatory", mandatory);
            editText.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
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

            linearLayout.addView(editText);
            inputslist.add(editText);


            if (skycableSOA != null) {
                switch (name) {
                    case "AccountNo": {
                        editText.setBackgroundColor(ContextCompat.getColor(getViewContext(), R.color.lightgray));
                        editText.setEnabled(false);
                        editText.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorblack));
                        editText.setText(skycableSOA.getSkycableAccountNo());
                        break;
                    }
                    case "AccountName": {
                        editText.setBackgroundColor(ContextCompat.getColor(getViewContext(), R.color.lightgray));
                        editText.setEnabled(false);
                        editText.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorblack));
                        editText.setText(CommonFunctions.replaceEscapeData(skycableSOA.getSkycableAccountName()));
                        break;
                    }
                    case "AmountPaid": {
                        editText.setBackgroundColor(ContextCompat.getColor(getViewContext(), R.color.lightgray));
                        editText.setEnabled(false);
                        editText.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorblack));
                        editText.setText(String.valueOf(skycableSOA.getTotalCharges()));
                        break;
                    }
                }
            }

            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                editText.setVisibility(View.VISIBLE);
            } else {
                editText.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createButton(String name, String mandatory) {
        try {
            JSONObject btntag = new JSONObject();
            Button btn = new Button(this);
            btn.setPadding(10, 10, 10, 10);
            btn.setFocusable(false);
            // btn.setTextAppearance(getViewContext(), R.style.roboto_bold);
            btn.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
            btntag.put("name", name);
            btntag.put("mandatory", mandatory);
            btn.setTag(btntag);
            btn.setGravity(Gravity.LEFT);
            btn.setBackgroundResource(R.drawable.border);
            linearLayout.addView(btn);
            btn.setOnClickListener(this);


            //add to the list of button for me to find it, once i need to set value on it.
            buttonslist.add(btn);

            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                btn.setVisibility(View.VISIBLE);
            } else {
                btn.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void createSpinner(String name, String inputtype, String mandatory) {
        //2. Create spinner
        try {
            ArrayList<String> spinnerArray = new ArrayList<String>();

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
            JSONObject spinnertag = new JSONObject();
            final Spinner spinner = new Spinner(this);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            spinner.setAdapter(spinnerArrayAdapter);
            spinnertag.put("name", name);
            spinnertag.put("mandatory", mandatory);
            spinner.setTag(spinnertag);
            linearLayout.addView(spinner);


            spinnerlist.add(spinner);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {

                    try {
                        String selectedoptn = spinner.getSelectedItem().toString();
                        String id = spinnerMap.get(spinner.getSelectedItemPosition() + 1);

                        //  String[] selectedarr = selectedoptn.split("-");
                        //  optionchoice = selectedarr[0];
                        optionchoice = id;

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


                        for (Button b : buttonslist) {
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

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //ADVANCE OPTIONS (RECEIPT MOBILE NO (PROGRAMATICALLY ADDED)
    private void createAdvanceOptions() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("name", "advanceoptions");
        obj.put("description", "description");
        advjsonarr.put(obj);

        //Adding Receipt Mobile No
        toggleAdvanceOptions();
    }

    private void toggleAdvanceOptions() {
        try {
            //OUTER LINEARLAYOUT
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 40, 0, 10);

            //ADVANCE LINEARLAYOUT
            LinearLayout advanceOption = new LinearLayout(this);
            advanceOption.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams advanceParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 120);
            advanceParams.setMargins(0, 5, 5, 5);

            //ADVANCE OPTION
            TextView txtv = new TextView(this);
            txtv.setTextSize(16);
            txtv.setPadding(0, 10, 0, 10);
            txtv.setGravity(Gravity.CENTER);
            txtv.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_676767));
            txtv.setTextAppearance(getViewContext(), R.style.roboto_regular);
            txtv.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            txtv.setText("Notify Recipient");

            advanceOption.addView(txtv, advanceParams);

            final ImageView imageview = new ImageView(this);
            Glide.with(getViewContext())
                    .asDrawable()
                    .override(36,36)
                    .load(R.drawable.ic_baseline_arrow_forward_ios_24)
                    .into(imageview);
            advanceOption.addView(imageview, advanceParams);

//            imageview.getLayoutParams().width = 350;
//            imageview.getLayoutParams().height = 120;
//            imageview.requestLayout();

            //RECEIPT LINEARLAYOUT
            final LinearLayout textViewOption = new LinearLayout(this);
            textViewOption.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams receiptparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            receiptparams.setMargins(0, 10, 0, 10);

            JSONObject receipttextviewtag = new JSONObject();
            TextView receipttextview = new TextView(this);
            receipttextview.setTextSize(16);
            receipttextview.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_676767));
            receipttextview.setTextAppearance(getViewContext(), R.style.roboto_regular);
            receipttextview.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            receipttextview.setText("Recipient Mobile No");
            receipttextviewtag.put("name", "advanceoptions");
            receipttextview.setTag(receipttextviewtag);
            txvadvlist.add(receipttextview);
            textViewOption.addView(receipttextview, receiptparams);


            //RECEIPTMOBILE EDIT TEXT (WITH CODE)
            final LinearLayout linearlayout_receiptedtxt = new LinearLayout(this);
            linearlayout_receiptedtxt.setOrientation(LinearLayout.HORIZONTAL);

            //PARAMS TEXTIVEW
            LinearLayout.LayoutParams receipttxvParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            receipttxvParams.weight = 0.3f;
            receipttxvParams.setMargins(0, 5, 0, 5);

            //PARAMS EDTXT
            LinearLayout.LayoutParams receiptedtxtParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            receiptedtxtParams.weight = 1.0f;
            receiptedtxtParams.setMargins(0, 5, 0, 5);


            TextView receiptcodetextview = new TextView(this);
            // receiptcodetextview.setPadding(20, 20, 20, 20);
            receiptcodetextview.setTextSize(16);
            receiptcodetextview.setGravity(Gravity.CENTER);
            receiptcodetextview.setBackgroundResource(R.drawable.inputgray);
            receiptcodetextview.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_676767));
            receiptcodetextview.setTextAppearance(getViewContext(), R.style.roboto_regular);
            receiptcodetextview.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            receiptcodetextview.setText("+63");
            linearlayout_receiptedtxt.addView(receiptcodetextview, receipttxvParams);


            JSONObject receiptedittexttag = new JSONObject();
            EditText receipteditext = new EditText(this);
            receipteditext.setPadding(20, 20, 20, 20);
            receipteditext.setTextSize(16);
            receiptedittexttag.put("name", "advanceoptions");
            receiptedittexttag.put("namefield", "receiptmobileno");
            receipteditext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
            receipteditext.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            receipteditext.setTag(receiptedittexttag);
//            receipteditext.setBackgroundResource(R.drawable.whiteinput1);
            receipteditext.setBackgroundResource(R.drawable.border);
            receipteditext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
            receipteditext.setFilters(new InputFilter[]{
                    new InputFilter.LengthFilter(12),
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

            edtadvlist.add(receipteditext);
            linearlayout_receiptedtxt.addView(receipteditext, receiptedtxtParams);

            textViewOption.addView(linearlayout_receiptedtxt, receiptparams);
//            textViewOption.addView(receipteditext, receiptparams);


            //EMAIL LINEARLAYOUT
            JSONObject emailtextviewtag = new JSONObject();
            TextView emailtextview = new TextView(this);
            emailtextview.setTextSize(16);
            emailtextview.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_676767));
            emailtextview.setTextAppearance(getViewContext(), R.style.roboto_regular);
            emailtextview.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            emailtextview.setText("Recipient Email Address");
            emailtextviewtag.put("name", "advanceoptions");
            emailtextview.setTag(emailtextviewtag);
            txvadvlist.add(emailtextview);
            textViewOption.addView(emailtextview, receiptparams);

            JSONObject emailedittexttag = new JSONObject();
            EditText emaileditext = new EditText(this);
            emaileditext.setPadding(20, 20, 20, 20);
            emaileditext.setTextSize(16);
            emailedittexttag.put("name", "advanceoptions");
            emailedittexttag.put("namefield", "receiptemailadd");
            emaileditext.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            emaileditext.setTag(emailedittexttag);
            emaileditext.setBackgroundResource(R.drawable.border);
            emaileditext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            edtadvlist.add(emaileditext);
            textViewOption.addView(emaileditext, receiptparams);


            advanceOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textViewOption.getVisibility() == View.VISIBLE) {
                        textViewOption.setVisibility(View.GONE);
                        imageview.setRotation(360);
                    } else {
                        textViewOption.setVisibility(View.VISIBLE);
                        imageview.setRotation(90);
                    }
                }
            });

            textViewOption.setVisibility(View.GONE);

            linearLayout.addView(advanceOption, layoutParams);
            linearLayout.addView(textViewOption);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void proceedPayment() {
        try {

            if (isProceed) {
                isProceed = false;

                double amountpaid = 0.00;
                double othercharge = 0.00;

                for (int i = 0; i < billspayresarr.length(); i++) {
                    JSONObject c = billspayresarr.getJSONObject(i);
                    String name = c.getString("name");

                    if (name.toUpperCase().equals("AMOUNTPAID")) {
                        amountpaid = Double.parseDouble(c.getString("value"));
                    }

                    if (name.toUpperCase().equals("OTHERCHARGES")) {
                        othercharge = Double.parseDouble(c.getString(" "));
                    }

                    totalamount = amountpaid;
                }

                if (totalamount > 0) {
                    verifySession("");
                } else {
                    isProceed = true;
                    showToast("Invalid Amount", GlobalToastEnum.WARNING);
                }
            }
        } catch (Exception e) {
            isProceed = true;
        }


    }

    private void showServiceChargeDialog() {

        TextView popok;
        TextView popcancel;
        TextView popamountpaid;
        TextView popservicecharge;
        TextView popotherchargeval;
        TextView poptotalamount;

        //DISCOUNT
        TableRow discountrow;
        TextView popdiscount;

        if (dialog == null) {
            dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_billspayment_preconfirmation);

            popamountpaid = dialog.findViewById(R.id.popamounttopayval);
            popservicecharge = dialog.findViewById(R.id.popservicechargeval);
            poptotalamount = dialog.findViewById(R.id.poptotalval);
            popotherchargeval = dialog.findViewById(R.id.popotherchargeval);
            popok = dialog.findViewById(R.id.popok);
            popcancel = dialog.findViewById(R.id.popcancel);
            discountrow = dialog.findViewById(R.id.discountrow);
            popdiscount = dialog.findViewById(R.id.popdiscount);

            //set value
            popamountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(totalamount)));
            popservicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(conveniencefee)));
            popotherchargeval.setText(CommonFunctions.currencyFormatter(String.valueOf(othercharges)));
//          poptotalamount.setText(cf.currencyFormatter(String.valueOf(totalamounttopay)));
            poptotalamount.setText(CommonFunctions.currencyFormatter(strtotalamount));

            if (discount > 0) {
                discountrow.setVisibility(View.VISIBLE);
                popdiscount.setText(CommonFunctions.currencyFormatter(String.valueOf(discount)));
            } else {
                discountrow.setVisibility(View.GONE);
                popdiscount.setText("0");
            }

            dialog.show();
            isProceed = true;

            //click close
            popcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialog = null;
                }

            });

            popok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }

                    Intent intent = new Intent(getBaseContext(), Payment.class);
//                  intent.putExtra("AMOUNT", String.valueOf(totalamounttopay));
                    intent.putExtra("AMOUNT", strtotalamount);
                    intent.putExtra("SPBILLERCODE", spbillercode);
                    intent.putExtra("BILLERNAME", billername);
                    intent.putExtra("BILLERPARAMDATA", billspayresarr.toString());
                    intent.putExtra("VOUCHERSESSION", vouchersession);
                    intent.putExtra("SERVICECHARGE", servicecharge);
                    intent.putExtra("OTHERCHARGES", othercharges);
                    intent.putExtra("NEWAPPLICATION", isNewApplication);
                    intent.putExtra("PPV", isPPV);
                    intent.putExtra("DISCOUNT", strdiscount);
                    intent.putExtra("GROSSPRICE", String.valueOf(totalamounttopay));
                    intent.putExtra("GKSERVICECODE", servicecode);
                    intent.putExtra("GKHASDISCOUNT", hasdiscount);
                    intent.putExtra("RECEIPTMOBILENO", receiptmobileno);
                    intent.putExtra("RECEIPTEMAILADD", receiptemailadd);
                    intent.putExtra("LATITUDE", latitude);
                    intent.putExtra("LONGITUDE", longitude);
                    startActivity(intent);
                }

            });
        }

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
            if (latitude.equals("0.0") || longitude.equals("0.0") || latitude.equals("null") || longitude.equals("null")) {
                showError("GPS service was not fully loaded yet. Please try again in a few seconds.");
            } else {
                //calculateDiscountForReseller(calculateDiscountForResellerCallBack);
            }
        }
    }

    //CHECKS THE LONG LAT FOR DISCOUNT
    private void checkGPSforDiscount() {
        Double currentlat = null;
        Double currentlon = null;

        tracker = new GPSTracker(getViewContext());

        if (!tracker.canGetLocation()) {
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
//                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                        isProceed = true;
                        mDialog.dismiss();
                        CommonFunctions.hideDialog();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        showError("GPS must be enabled to proceed.");
                        isProceed = true;
                        mDialog.dismiss();
                        CommonFunctions.hideDialog();
                    }
                })
                .show();
    }

    //CHECKS IF THE MOBILENUMBER IS VALID
    private String getReceiptMobileNumber(String number) {
        String result = "";
        if (number.length() >= 9) {
            if (number.substring(0, 2).equals("63")) {
                result = "INVALID";
            } else if (number.substring(0, 2).equals("09")) {
                if (number.length() == 11) {
                    result = "63" + number.substring(1, number.length());
                } else {
                    result = "INVALID";
                }
            } else if (number.substring(0, 1).equals("9")) {
                if (number.length() == 10) {
                    result = "63" + number;
                } else {
                    result = "INVALID";
                }
            } else {
                result = "INVALID";
            }
        } else {
            result = "INVALID";
        }
        return result;
    }

    //Turns the 0/9 to 63
    private String convertoPHCountryCodeNumber(String number) {
        String result = "";
        if (number.length() > 0) {
            if (number.substring(0, 2).equals("63")) {
                if (number.length() == 12) {
                    result = number;
                }
            } else if (number.substring(0, 2).equals("09")) {
                if (number.length() == 11) {
                    result = "63" + number.substring(1, number.length());
                }
            } else if (number.substring(0, 1).equals("9")) {
                if (number.length() == 10) {
                    result = "63" + number;
                }
            }
        }
        return result;
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
                        //new HttpAsyncTask().execute(CommonVariables.PREPURCHASE);
                        prePurchaseV2();
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

    private void verifySession(final String flag) {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            CommonFunctions.showDialog(this, "", "Processing. Please wait ...", false);
            //call AsynTask to perform network operation on separate thread
            //new HttpAsyncTask1().execute(CommonVariables.GETSERVICECHARGE);
            getCustomerBillerServiceChargeV2();
        } else {
            isProceed = true;
            CommonFunctions.hideDialog();
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
            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            try {

                double amounttopay = Double.parseDouble(strtotalamount);
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("amountpurchase", amounttopay);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("authcode", authcode);
                //convert JSONObject to JSON to String
                json = jsonObject.toString();


            } catch (Exception e) {
                e.printStackTrace();
                json = null;
                isProceed = true;
                CommonFunctions.hideDialog();
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
                    if (totalamounttopay > 0) {
                        CommonFunctions.hideDialog();
                        vouchersession = result;
                        if (discount > 0) {
                            hasdiscount = 1;
                            showServiceChargeDialog();
                        } else {
                            hasdiscount = 0;
                            showServiceChargeDialog();
                        }
                    }
                }
            } catch (Exception e) {
                isProceed = true;
                CommonFunctions.hideDialog();
                e.printStackTrace();
            }
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
            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("amountpaid", totalamount);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("servicechargeclass", servicechargeclass);
                jsonObject.accumulate("serviceproviderbillercode", spbillercode);
                //convert JSONObject to JSON to String
                json = jsonObject.toString();


            } catch (Exception e) {
                e.printStackTrace();
                json = null;
                CommonFunctions.hideDialog();
                isProceed = true;
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
                    servicecharge = parentObj.getString("servicecharge");
                    othercharges = parentObj.getString("othercharges");
                    totalamounttopay = totalamount + Double.parseDouble(servicecharge) + Double.parseDouble(othercharges);
                    conveniencefee = Double.parseDouble(servicecharge) + Double.parseDouble(othercharges);

                    if (distributorid.equals("") || distributorid.equals(".")
                            || resellerid.equals("") || resellerid.equals(".")) {
                        if (latitude.equals("") || longitude.equals("")
                                || latitude.equals("null") || longitude.equals("null")) {
                            latitude = "0.0";
                            longitude = "0.0";
                        }
                        calculateDiscountForResellerV2();
                    } else {
                        checkGPSforDiscount();
                        if (latitude.equals("") || longitude.equals("")
                                || latitude.equals("null") || longitude.equals("null")) {
                            latitude = "0.0";
                            longitude = "0.0";
                        }
                        calculateDiscountForResellerV2();
                    }

                } else {
                    isProceed = true;
                    CommonFunctions.hideDialog();
                    showToast("" + message, GlobalToastEnum.ERROR);
                }


            } catch (Exception e) {
                isProceed = true;
                CommonFunctions.hideDialog();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        try {

            if (v.getTag().equals("SUBMIT")) {

                int isvalid = 0;
                int mobileinvalid = 0;
                int emailisinvalid = 0;
                billspayresarr = new JSONArray();


                for (int i = 0; i < params.length(); i++) {

                    JSONObject billspayres = new JSONObject();

                    JSONObject c = params.getJSONObject(i);
                    String inputname = c.getString("name");
                    String ismandatory = c.getString("mandatory");
                    String description = c.getString("description");
                    String datatype = c.getString("datatype");

                    //get the data in the input text
                    for (EditText inputdata : inputslist) {
                        String f = inputdata.getTag().toString();
                        JSONObject jresponse = new JSONObject(f);
                        String name = jresponse.getString("name");

                        if (name.equals(inputname)) {
                            String inputres = inputdata.getText().toString();

                            //place data in the object
                            billspayres.put("name", inputname);
                            billspayres.put("description", description.toUpperCase());
                            billspayres.put("value", inputres);
                            billspayres.put("datatype", datatype);
                            billspayresarr.put(billspayres);

                            //if mandatory, need not empty
                            if (!ismandatory.equals("NO") && inputdata.getVisibility() == View.VISIBLE) {
                                if (inputres.trim().length() == 0) {
                                    isvalid = isvalid + 1;
                                }
                            }

                        }
                    }

                    for (Button b : buttonslist) {
                        String f = b.getTag().toString();
                        JSONObject jresponse = new JSONObject(f);
                        String name = jresponse.getString("name");
                        if (name.equals(inputname)) {
                            String buttonres = b.getText().toString();


                            //place data in the object
                            billspayres.put("name", inputname);
                            billspayres.put("description", description.toUpperCase());
                            billspayres.put("value", buttonres);
                            billspayres.put("datatype", datatype);
                            billspayresarr.put(billspayres);

                            //if mandatory, need not empty
                            if (!ismandatory.equals("NO") && b.getVisibility() == View.VISIBLE) {
                                if (buttonres.equals("")) {
                                    isvalid = isvalid + 1;
                                }
                            }

                        }
                    }

                    for (Spinner selectedval : spinnerlist) {
                        String f = selectedval.getTag().toString();
                        JSONObject jresponse = new JSONObject(f);
                        String name = jresponse.getString("name");


                        if (name.equals(inputname)) {
                            String selectedoptn = selectedval.getSelectedItem().toString();
                            String id = spinnerMap.get(selectedval.getSelectedItemPosition() + 1);


                            //place data in the object
                            billspayres.put("name", inputname);
                            billspayres.put("description", description.toUpperCase());
                            billspayres.put("value", id);
                            billspayres.put("datatype", datatype);
                            billspayresarr.put(billspayres);

                            //if mandatory, need not empty
                            if (!ismandatory.equals("NO") && selectedval.getVisibility() == View.VISIBLE) {
                                if (selectedoptn.equals("")) {
                                    isvalid = isvalid + 1;
                                }
                            }
                        }
                    }

                }

                //ADVANCE OPTIONS
                for (int i = 0; i < advjsonarr.length(); i++) {

//                    JSONObject gkstoreres = new JSONObject();
                    JSONObject c = advjsonarr.getJSONObject(i);
                    String inputname = c.getString("name");
                    //get the data in the input text
                    for (EditText advancedata : edtadvlist) {
                        String f = advancedata.getTag().toString();
                        JSONObject jresponse = new JSONObject(f);
                        String name = jresponse.getString("name");
                        String namefield = jresponse.getString("namefield");
                        String description = c.getString("description");

                        if (name.contains("*")) {
                            name = name.replace("*", "");
                        }

                        if (name.equals(inputname)) {
                            String checkdata = advancedata.getText().toString();

                            if (namefield.equals("receiptmobileno")) {
                                //place data in the object
                                if (checkdata.trim().equals("")) {
                                    receiptmobileno = ".";
                                } else {
                                    checkdata = getReceiptMobileNumber(checkdata);
                                    if (checkdata.equals("INVALID")) {
                                        mobileinvalid = mobileinvalid + 1;
                                    } else {
                                        receiptmobileno = checkdata;
                                    }
                                }
                            } else if (namefield.equals("receiptemailadd")) {
                                //place data in the object
                                if (checkdata.trim().equals("")) {
                                    receiptemailadd = ".";
                                } else {
                                    if (!CommonFunctions.isValidEmail(checkdata)) {
                                        emailisinvalid = emailisinvalid + 1;
                                    } else {
                                        receiptemailadd = checkdata;
                                    }
                                }
                            }

                        }
                    }
                }

                if (isvalid > 0) {
                    showToast("Fields with * are mandatory", GlobalToastEnum.WARNING);
                } else if (mobileinvalid > 0) {
                    showToast("Invalid Recipient Number.", GlobalToastEnum.WARNING);
                } else if (emailisinvalid > 0) {
                    showToast("Invalid Email Address.", GlobalToastEnum.WARNING);
                } else {
                    if (!receiptmobileno.equals(".")) {
                        receiptmobileno = convertoPHCountryCodeNumber(receiptmobileno);
                    }
                    proceedPayment();
                }


            } else {
                String tag = v.getTag().toString();
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(BillsPaymentBillerDetailsActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), tag);
            }

        } catch (Exception e) {
            Logger.debug("ANN", "ERROR" + e.toString());
            e.printStackTrace();

        }


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        try {
            String date = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;


            for (Button b : buttonslist) {
                String f = b.getTag().toString();
                JSONObject jresponse = new JSONObject(f);
                String name = jresponse.getString("name");

                String d = view.getTag().toString();
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

    /***
     *SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    //CALCULATE DISCOUNT FOR RESELLER
    private void calculateDiscountForResellerV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("merchantid", ".");
                parameters.put("amountpaid", String.valueOf(totalamounttopay));
                parameters.put("servicecode", servicecode);
                parameters.put("longitude", longitude);
                parameters.put("latitude", latitude);
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

            } else {
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }

        } catch (Exception e) {
            CommonFunctions.hideDialog();
            showErrorToast();
            e.printStackTrace();
        }
    }

    private void calculateDiscountForResellerObjectV2(Callback<GenericResponse> calculateDiscountForResellerCallback) {
        Call<GenericResponse> resellerdiscount = RetrofitBuilder.getCommonV2API(getViewContext())
                .calculateDiscountForResellerV3(authenticationid, sessionid, param);
        resellerdiscount.enqueue(calculateDiscountForResellerCallback);
    }

    private final Callback<GenericResponse> calculateDiscountForResellerSessionV2 = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            CommonFunctions.hideDialog();
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    if (decryptedData.equalsIgnoreCase("error") || decryptedMessage.equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else {
                        Logger.debug("okhttp","DISCOUNT : "+decryptedData);
                        discount = Double.parseDouble(decryptedData);
                        strdiscount = String.valueOf(discount);
                        if (discount <= 0) {
                            strtotalamount = String.valueOf(totalamounttopay);
                            //new HttpAsyncTask().execute(CommonVariables.PREPURCHASE);
                            prePurchaseV2();
                        } else {
                            strtotalamount = String.valueOf((totalamounttopay - discount));
                            if (Double.parseDouble(String.valueOf(totalamounttopay)) > 0) {
                                //new HttpAsyncTask().execute(CommonVariables.PREPURCHASE);
                                prePurchaseV2();
                            }
                        }
                    }
                } else if (response.body().getStatus().equals("005") && !decryptedMessage.contains("Session")) {
                    discountmessage = decryptedMessage;
                    discount = Double.parseDouble(CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData()));
                    strdiscount = String.valueOf(discount);
                    strtotalamount = String.valueOf(totalamounttopay);
                    noDiscountResellerServiceArea();
                } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else {
                    showError(decryptedMessage);
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showError();
            CommonFunctions.hideDialog();
        }
    };


    // PREPURCHASE
    private void prePurchaseV2(){
        try{

            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("amountpurchase", String.valueOf(Double.parseDouble(strtotalamount)));
                parameters.put("devicetype",CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                prePurchaseIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", prePurchaseIndex);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                prePurchaseAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                prePurchaseKeyEncryption = CommonFunctions.getSha1Hex(prePurchaseAuthenticationid + sessionid + "prePurchaseV3");
                prePurchaseParam = CommonFunctions.encryptAES256CBC(prePurchaseKeyEncryption, String.valueOf(paramJson));

                prePurchaseV2Object(prePurchaseV2Callback);

            }else{
                isProceed = true;
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }

        }catch (Exception e){
            isProceed = true;
            CommonFunctions.hideDialog();
            showErrorToast();
            e.printStackTrace();
        }
    }

    private void prePurchaseV2Object(Callback<GenericResponse> prepurchase) {
        Call<GenericResponse> prePurchaseV2 = RetrofitBuilder.getCommonV2API(getViewContext())
                .prePurchaseV3(prePurchaseAuthenticationid,sessionid,prePurchaseParam);
        prePurchaseV2.enqueue(prepurchase);
    }

    private final Callback<GenericResponse> prePurchaseV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            CommonFunctions.hideDialog();
            isProceed = true;
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(prePurchaseKeyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(prePurchaseKeyEncryption,response.body().getData());
                    Logger.debug("okhttp","TXNOOOOOO ::::::::::::: "+decryptedData);
                    if (totalamounttopay > 0) {
                        vouchersession = CommonFunctions.parseJSON(decryptedData,"value");
                        if (discount > 0) {
                            hasdiscount = 1;
                            showServiceChargeDialog();
                        } else {
                            hasdiscount = 0;
                            showServiceChargeDialog();
                        }
                    }
                } else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else {
                    showError(decryptedMessage);
                }
            } else {
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isProceed = true;
            CommonFunctions.hideDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    // GET BILLER SERVICE CHARGE
    private void getCustomerBillerServiceChargeV2(){

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("servicechargeclass", servicechargeclass);
            parameters.put("amountpaid", String.valueOf(totalamount));
            parameters.put("serviceproviderbillercode", spbillercode);


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            getServiceChargeIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", getServiceChargeIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            getServiceChargeAuthID = CommonFunctions.parseJSON(jsonString, "authenticationid");
            getServiceChargeKey = CommonFunctions.getSha1Hex(getServiceChargeAuthID + sessionid + "getCustomerBillerServiceChargeV2");
            getServiceChargeParam = CommonFunctions.encryptAES256CBC(getServiceChargeKey, String.valueOf(paramJson));

            getBillerCharge(getBillerChargeCallback);

        }else{
            CommonFunctions.hideDialog();
            showNoInternetToast();
        }
    }
    private void getBillerCharge(Callback<GenericResponse> getBillerCharge) {
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .getCustomerBillerServiceChargeV2(getServiceChargeAuthID,sessionid,getServiceChargeParam);
        call.enqueue(getBillerCharge);
    }

    private final Callback<GenericResponse> getBillerChargeCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            CommonFunctions.hideDialog();
            isProceed = true;
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(getServiceChargeKey,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(getServiceChargeKey,response.body().getData());
                    Logger.debug("okhttp","CHARGE  ::::::::::::: "+decryptedData);

                    servicecharge = CommonFunctions.parseJSON(decryptedData,"servicecharge");
                    othercharges = CommonFunctions.parseJSON(decryptedData,"othercharges");

                    Logger.debug("okhttp","SERVICE CHARGE: " +CommonFunctions.parseJSON(decryptedData,"servicecharge"));
                    Logger.debug("okhttp","OTHER CHARGE: " +CommonFunctions.parseJSON(decryptedData,"othercharges"));

                    totalamounttopay = totalamount + Double.parseDouble(servicecharge) + Double.parseDouble(othercharges);
                    conveniencefee = Double.parseDouble(servicecharge) + Double.parseDouble(othercharges);

                    if (distributorid.equals("") || distributorid.equals(".") || resellerid.equals("") || resellerid.equals(".")) {
                        if (latitude.equals("") || longitude.equals("") || latitude.equals("null") || longitude.equals("null")) {
                            latitude = "0.0";
                            longitude = "0.0";
                        }
                        calculateDiscountForResellerV2();
                    } else {
                        checkGPSforDiscount();
                        if (latitude.equals("") || longitude.equals("")
                                || latitude.equals("null") || longitude.equals("null")) {
                            latitude = "0.0";
                            longitude = "0.0";
                        }
                        calculateDiscountForResellerV2();
                    }


                } else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else {
                    showError(decryptedMessage);
                }
            } else {
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            isProceed = true;
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };



}
