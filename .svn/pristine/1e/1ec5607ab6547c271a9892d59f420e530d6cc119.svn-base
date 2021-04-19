package com.goodkredit.myapplication.activities.transfer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.appcompat.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.transfervoucher.TransferValidateVoucher;
import com.goodkredit.myapplication.responses.transfervoucher.TransferValidateVoucherResponse;
import com.goodkredit.myapplication.responses.transfervoucher.TransferVoucherV2Response;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Algo:
 *
 * */
public class TransferThruBorrower extends BaseActivity implements View.OnClickListener {

    private final int RESULT_PICK_CONTACT = 963;
    DatabaseHandler db;
    Context mcontext;
    EditText country;
    EditText receivermobile;
    Button proceed;


    String borroweridval = "";
    String imei = "";
    String receivernameval = "";
    String receiveraddressval = "";
    String receivermobileval = "";
    String textbutton = "";
    String voucherval = "";
    String amountval = "";
    String currentrequest = "";
    String receiverborroweridval = "";
    String countryprefixval = "";
    String sendermobileval = "";
    String countryval = "";

    TextView popmobile;
    TextView popname;
    TextView popvoucher;
    TextView popamount;
    TableRow popemail;
    TextView popconfirmation;
    TextView popsuccessclose;
    TextView popsuccessconfirmation;
    ScrollView popconfirmationwrap;
    LinearLayout popsuccesswrap;
    EditText code;
    EditText countrycode;

    Dialog dialog;

    TableRow namerow;
    TableRow addressrow;
    TableRow borroweridrow;

    private MaterialDialog otpdialog;
    private String otpmessage = "";
    private String receivermobilevalfinal = "";

    //SESSIONID
    private String sessionid = "";

    //NEW OTP
    private EditText edt_otp;
    private String str_otp = "";
    private GlobalDialogs globalTransfertoSubDialog;
    private GlobalDialogs globalOTPDialog;

    //WITH SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    //for transfer v2
    private String transferAuth;
    private String transferIndex;
    private String transferKey;
    private String transferParam;

    private String FROM = "";
    private ArrayList<String> selectedVouchers = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_thru_borrower);
        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FROM = getIntent().getStringExtra("from");

        mcontext = this;

        //initialize database
        db = new DatabaseHandler(this);

        //get country code and name
        String currentcountry = GetCountryZipCode();

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        //get the passed value
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            voucherval = extras.getString("VOUCHERCODE");
            amountval = extras.getString("AMOUNT");
        }

        //get account information
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                sendermobileval = cursor.getString(cursor.getColumnIndex("mobile"));
                borroweridval = cursor.getString(cursor.getColumnIndex("borrowerid"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));
            } while (cursor.moveToNext());
        }
        cursor.close();

        //Initialize Elements
        proceed = findViewById(R.id.proceed);
        country = findViewById(R.id.country);
        receivermobile = findViewById(R.id.receivermobile);
        countrycode = findViewById(R.id.countrycode);
        code = findViewById(R.id.code);

        //first load auto detect of the country based on the sim
//        if (currentcountry != null && !currentcountry.equals("")) {
//            country.setText(currentcountry);
//            countrycode.setText(countryprefixval);
//            code.setText("+" + countryprefixval);
//        } else {
        country.setText("Philippines");
        countrycode.setText("63");
        code.setText("+63");
//        }

        //create a dialog confirmation
        dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pop_confirmation);

        popmobile = dialog.findViewById(R.id.popsms);
        popname = dialog.findViewById(R.id.popname);
        popvoucher = dialog.findViewById(R.id.popvouchercode);
        popamount = dialog.findViewById(R.id.popamount);
        popemail = dialog.findViewById(R.id.emailrow);
        popconfirmation = dialog.findViewById(R.id.confirmationlbl);
        popconfirmation.setText("Transfer to a Subscriber");
        popconfirmationwrap = dialog.findViewById(R.id.confirmationwrap);
        popsuccesswrap = dialog.findViewById(R.id.successwrap);
        popsuccessclose = dialog.findViewById(R.id.popok);
        popsuccessconfirmation = dialog.findViewById(R.id.succesconfirmationlbl);
        popsuccessconfirmation.setText("Transfer to a Subscriber");
        popemail.setVisibility(View.GONE);


        //TRIGGERS
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentrequest = "VALIDATEBORROWER";
                //validateBorrower();
                validateBorrowerNew();
            }
        });

        //click confirm transaction
        TextView confirm = dialog.findViewById(R.id.popconfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentrequest = "TRANSFEROTP";
                validateRecipientSubscriber();
            }
        });

        //click cancel
        TextView canceltxn = dialog.findViewById(R.id.canceltxn);
        canceltxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //click close
        popsuccessclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CommonVariables.VOUCHERISFIRSTLOAD = true; //to realod the vouchers
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        });

        findViewById(R.id.btn_open_contacts).setOnClickListener(this);
    }

    //---------------------FUNCTIONS--------------------------
    public void openCountryList(View view) {
//        Intent intent = new Intent(this, CountryCodeListActivity.class);
//        startActivityForResult(intent, 1);
//        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    //VALIDATE BORROWER ID
    public void validateBorrower() {

        //1. Check inputs
        receivermobileval = receivermobile.getText().toString();
        countryprefixval = countrycode.getText().toString();

        if (receivermobileval.equals("") || countryprefixval.equals("")) {
            showToast("All fields are mandatory", GlobalToastEnum.WARNING);
        } else {
            //2.
            int status = CommonFunctions.getConnectivityStatus(this);
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else { //has connection proceed
                //3.
                String first = receivermobileval.trim().substring(0, 1);
                if (countryprefixval.equals("63")) {
                    if (first.equals("0")) {
                        receivermobileval = receivermobileval.substring(1);
                    }
                }
                String receiverfinalmobile = countryprefixval + receivermobileval;

                if (sendermobileval.equals(receiverfinalmobile)) {
                    showToast("You cannot transfer voucher to yourself.", GlobalToastEnum.WARNING);
                } else {
                    CommonFunctions.showDialog(mcontext, "", "Validating Subscriber ID. Please wait ...", false);
                    //verifySession();
                    getSession();
                }
            }
        }
    }

    public void validateBorrowerNew() {

        //1. Check inputs
        receivermobileval = receivermobile.getText().toString();
        countryprefixval = countrycode.getText().toString();

        if (receivermobileval.equals("") || countryprefixval.equals("")) {
            showToast("All fields are mandatory", GlobalToastEnum.WARNING);
        } else {
            //2.
            int status = CommonFunctions.getConnectivityStatus(this);
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else { //has connection proceed
                //3.
                String first = receivermobileval.trim().substring(0, 1);
                if (countryprefixval.equals("63")) {
                    if (first.equals("0")) {
                        receivermobileval = receivermobileval.substring(1);
                    }
                }
                receivermobilevalfinal = countryprefixval + receivermobileval;
                if (sendermobileval.equals(receivermobilevalfinal)) {
                    showToast("You cannot transfer voucher to yourself.", GlobalToastEnum.WARNING);
                } else {
                   showProgressDialog(getViewContext(), "", "Validating Subscriber ID. Please wait ...");
                    getSession();
                }
            }
        }
    }

    public String GetCountryZipCode() {
        String CountryID = "";
        String CountryZipCode = "";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID = manager.getSimCountryIso().toUpperCase();
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(CountryID.trim())) {
                countryprefixval = g[0];
                countryval = g[2];
                CountryZipCode = countryval;
                break;
            }
        }
        return CountryZipCode;
    }

    private void openContacts() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null;
            String name = null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo = cursor.getString(phoneIndex);
            receivermobile.setText(CommonFunctions.userMobileNumber(phoneNo, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showInputOTP() {
        try {
            otpdialog = new MaterialDialog.Builder(getViewContext())
                    .customView(R.layout.dialog_otp_name, false)
                    .cancelable(false)
                    .positiveText("OK")
                    .negativeText("Cancel")
                    .show();

            View view = otpdialog.getCustomView();
            final EditText edtOtpName = (EditText) view.findViewById(R.id.dialogOtpName);

            final TextView txvOtpLabel = (TextView) view.findViewById(R.id.dialogOtpLabelName);

            txvOtpLabel.setText("Enter One Time Password");

            View positive = otpdialog.getActionButton(DialogAction.POSITIVE);
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    otpmessage = edtOtpName.getText().toString();
                    if (otpmessage.trim().equals("")) {
                        showToast("Please input the receive otp message", GlobalToastEnum.WARNING);
                    } else {
                        showProgressDialog(getViewContext(),"","Processing your request... Please wait.");
                        //transferVoucherToSubscriber(transferVoucherToSubscriberCallBack);
                        selectedVouchers.add(voucherval);
                        transferVoucherToSubscriberV2();
                    }

                }
            });

            View negative = otpdialog.getActionButton(DialogAction.NEGATIVE);
            negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonFunctions.hideDialog();
                    otpdialog.dismiss();
                    dialog.show();
                    otpdialog = null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showInputOTPNew() {
       globalOTPDialog = new GlobalDialogs(getViewContext());

        globalOTPDialog.createDialog("", "",
                "OK", "Cancel", ButtonTypeEnum.SINGLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //OTP LABEL
        LinearLayout otpContainer = globalOTPDialog.getTextViewMessageContainer();
        otpContainer.setPadding(15, 15, 15, 0);

        List<String> otplblList = new ArrayList<>();
        String otplbl = "Enter One Time Password";
        otplblList.add(otplbl);

        globalOTPDialog.setContentCustomMultiTextView(otplblList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_045C84, 16, Gravity.TOP | Gravity.CENTER));

        //OTP EDITTEXT
        LinearLayout editTextMainContainer = globalOTPDialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(35, 0, 35, 20);

        List<String> editTextDataType = new ArrayList<>();
        editTextDataType.add(String.valueOf(GlobalDialogsEditText.CUSTOMNUMBER));

        LinearLayout editTextContainer = globalOTPDialog.setContentCustomMultiEditText(editTextDataType,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.START,
                        R.drawable.border, 6, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;
                editItem.setPadding(20, 20, 20, 20);
                String taggroup = editItem.getTag().toString();
                if (taggroup.equals("TAG 0")) {
                    edt_otp = editItem;
                    edt_otp.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                    edt_otp.addTextChangedListener(otpTextWatcher);
                }
            }
        }

        View closebtn = globalOTPDialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalOTPDialog.dismiss();
            }
        });

        View cancelbtn = globalOTPDialog.btnDoubleOne();
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalOTPDialog.dismiss();
            }
        });

        View okbtn = globalOTPDialog.btnDoubleTwo();
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SClick.check(SClick.BUTTON_CLICK)) return;
                if (str_otp.trim().equals("")) {
                    showToast("Please input the receive otp message", GlobalToastEnum.WARNING);
                } else {
                    globalOTPDialog.dismiss();
                    transferVoucherToSubscriber(transferVoucherToSubscriberCallBack);
                }
            }
        });
    }

    private TextWatcher otpTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            str_otp = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    //--------------------API------------------------------------
    public void verifySession() {

//        CreateSession newsession = new CreateSession(this);
//        newsession.setQueryListener(new CreateSession.QueryListener() {
//            @SuppressWarnings("unchecked")
//            public void QuerySuccessFul(String data) {
//                if (data == null) {
//                    CommonFunctions.hideDialog();
//                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (data.equals("001")) {
//                    CommonFunctions.hideDialog();
//                    showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
//                } else if (data.equals("002")) {
//                    CommonFunctions.hideDialog();
//                    showToast("Invalid User", GlobalToastEnum.NOTICE);
//                } else if (data.equals("error")) {
//                    CommonFunctions.hideDialog();
//                    showToast("Something went wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
//                } else if (data.contains("<!DOCTYPE html>")) {
//                    CommonFunctions.hideDialog();
//                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = data;
//                    //call AsynTask to perform network operation on separate thread
//                    if (currentrequest.equals("VALIDATEBORROWER")) {
//                        //new ValidateBorrowerHttpAsyncTask().execute(CommonVariables.VALIDATEBORROWERID);
//                        validateRecipientSubscriber(validateRecipientSubscriberCallBack);
//                    } else {
//                        //new ProcessTransferHttpAsyncTask().execute(cv.TRANSFERVOUCHER);
//                        //transferVoucherV2(transferVoucherV2Session);
//                        dialog.dismiss();
//                        showInputOTP();
//                    }
//                }
//            }
//
//        });
//        newsession.execute(CommonVariables.CREATESESSION, imei, sendermobileval);


        //call AsynTask to perform network operation on separate thread
        if (currentrequest.equals("VALIDATEBORROWER")) {
            //new ValidateBorrowerHttpAsyncTask().execute(CommonVariables.VALIDATEBORROWERID);
            //validateRecipientSubscriber(validateRecipientSubscriberCallBack);
            validateRecipientSubscriber();
        } else {
            //new ProcessTransferHttpAsyncTask().execute(cv.TRANSFERVOUCHER);
            //transferVoucherV2(transferVoucherV2Session);
            dialog.dismiss();
            if(currentrequest.equals("") || currentrequest == null){
                currentrequest = "TRANSFEROTP";
            }
            validateRecipientSubscriber();
            if(otpdialog == null){
                showInputOTP();
            }
            //showInputOTPNew();
        }

    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, sendermobileval);
//            call.enqueue(getSessionCallBack);
//        } else {
//            CommonFunctions.hideDialog();
//            showToast("Seems you are not connected to the internet. Please check your connection and try again. Thank you.", GlobalToastEnum.WARNING);
//        }
//
//    }
//
//    private Callback<String> getSessionCallBack = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            ResponseBody errBody = response.errorBody();
//
//            if (errBody == null) {
//                sessionid = response.body().toString();
//                if (!sessionid.isEmpty()) {
//                    if (currentrequest.equals("VALIDATEBORROWER")) {
//                        validateRecipientSubscriber(validateRecipientSubscriberCallBack);
//                    } else {
//                        dialog.dismiss();
//                        showInputOTP();
//                    }
//                } else {
//                    CommonFunctions.hideDialog();
//                    showErrorToast();
//                }
//            } else {
//                CommonFunctions.hideDialog();
//                showErrorToast();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            CommonFunctions.hideDialog();
//            showErrorToast();
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            if (!currentrequest.equals("VALIDATEBORROWER")) {
                //validateRecipientSubscriber(validateRecipientSubscriberCallBack);
                dialog.dismiss();
                if(currentrequest.equals("") || currentrequest == null){
                    currentrequest = "TRANSFEROTP";
                }
            }else{
                validateRecipientSubscriber();
            }

        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //VALIDATE SUBSCRIBER (OLD)
    private class ValidateBorrowerHttpAsyncTask extends AsyncTask<String, Void, String> {
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
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("mobile", sendermobileval);
                jsonObject.accumulate("receivermobile", receivermobileval);
                jsonObject.accumulate("countrycode", countryprefixval);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                CommonFunctions.hideDialog();
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            CommonFunctions.hideDialog();
            if (result == null) {
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else if (result.equals("001")) {
                showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
            } else if (result.equals("002")) {
                showToast("Invalid Session.", GlobalToastEnum.NOTICE);
            } else if (result.equals("003")) {
                showToast("Subscriber not found.", GlobalToastEnum.NOTICE);
            } else if (result.equals("error")) {
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else if (result.contains("<!DOCTYPE html>")) {
                showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                //4.
                processResult(result);
            }
        }
    }

    //VALIDATE SUBSCRIBER (NEW)
    private void validateRecipientSubscriber(Callback<TransferValidateVoucherResponse> validateRecipientSubscriberCallBack) {
        String authcode = CommonFunctions.getSha1Hex(imei + sendermobileval + sessionid);

        Call<TransferValidateVoucherResponse> validateRecipientSubscriber = RetrofitBuild.transferVoucherV2Service(getViewContext())
                .validateRecipientSubscriber(
                        sessionid,
                        imei,
                        borroweridval,
                        sendermobileval,
                        authcode,
                        receivermobilevalfinal,
                        voucherval
                );
        validateRecipientSubscriber.enqueue(validateRecipientSubscriberCallBack);
    }

    private final Callback<TransferValidateVoucherResponse> validateRecipientSubscriberCallBack = new Callback<TransferValidateVoucherResponse>() {

        @Override
        public void onResponse(Call<TransferValidateVoucherResponse> call, Response<TransferValidateVoucherResponse> response) {
            ResponseBody errorBody = response.errorBody();
            CommonFunctions.hideDialog();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    CommonFunctions.hideDialog();

                    List<TransferValidateVoucher> voteparticipantlist = response.body().getData();

                    for(TransferValidateVoucher transferValidateVoucher : voteparticipantlist) {
                        receiverborroweridval = transferValidateVoucher.getBorrowerID();
                        receivernameval = transferValidateVoucher.getBorrowerName();
                        receivermobileval = transferValidateVoucher.getMobileNo();
                        receiveraddressval = transferValidateVoucher.getStreetAddress() + transferValidateVoucher.getCity();
                    }

                    DecimalFormat formatter = new DecimalFormat("#,###,##0.00");

                    currentrequest = "PROCESSTRANSFER";
                    popname.setText(receivernameval);
                    String vcode = voucherval.substring(0, 2) + "-" + voucherval.substring(2, 6) + "-" + voucherval.substring(6, 11) + "-" + voucherval.substring(11, 12);
                    popvoucher.setText(vcode);
                    popamount.setText(formatter.format(Double.parseDouble(amountval)));
                    popmobile.setText("+" + receivermobileval);
                    dialog.show();

                } else {
                    CommonFunctions.hideDialog();
                    showToast(response.body().getMessage(), GlobalToastEnum.WARNING);
                }
            } else {
                CommonFunctions.hideDialog();
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<TransferValidateVoucherResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            showErrorToast();
        }
    };

    public void processResult(String result) {
        try {
            JSONArray jsonArr = new JSONArray(result);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                receivernameval = jsonObj.getString("BorrowerName");
                receivermobileval = jsonObj.getString("MobileNo");
                receiveraddressval = jsonObj.getString("StreetAddress") + " " + jsonObj.getString("City");
                receiverborroweridval = jsonObj.getString("BorrowerID");
            }

            DecimalFormat formatter = new DecimalFormat("#,###,##0.00");

            currentrequest = "PROCESSTRANSFER";
            popname.setText(receivernameval);
            String vcode = voucherval.substring(0, 2) + "-" + voucherval.substring(2, 6) + "-" + voucherval.substring(6, 11) + "-" + voucherval.substring(11, 12);
            popvoucher.setText(vcode);
            popamount.setText(formatter.format(Double.parseDouble(amountval)));
            popmobile.setText("+" + receivermobileval);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }


    }

    //TRANSFER VOUCHER TO SUBSCRIBER (VERY OLD)
    private class ProcessTransferHttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + sendermobileval + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("recipientborrowerid", receiverborroweridval);
                jsonObject.accumulate("vouchercode", voucherval);
                jsonObject.accumulate("receivername", receivernameval);
                jsonObject.accumulate("receivermobile", receivermobileval);
                jsonObject.accumulate("transfertype", "BORROWER");
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("userid", sendermobileval);


                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                CommonFunctions.hideDialog();
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            CommonFunctions.hideDialog();
            if (result == null) {
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else if (result.equals("000")) {
                popsuccesswrap.setVisibility(View.VISIBLE);
                popconfirmationwrap.setVisibility(View.GONE);

            } else if (result.equals("001")) {
                showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
            } else if (result.equals("002")) {
                showToast("Invalid Session.", GlobalToastEnum.NOTICE);
            } else if (result.equals("003")) {
                showToast("Invalid Voucher Code.", GlobalToastEnum.NOTICE);
            } else if (result.equals("004")) {
                showToast("Invalid Authentication.", GlobalToastEnum.NOTICE);
            } else if (result.equals("005")) {
                showToast("Voucher invalid. A pending transaction with this voucher is being processed.", GlobalToastEnum.NOTICE);
            } else if (result.contains("<!DOCTYPE html>")) {
                showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            }
        }
    }

    //TRANSFER VOUCHER TO SUBSCRIBER (OLD)
    private void transferVoucherV2(Callback<TransferVoucherV2Response> transferVoucherV2Callback) {
        String authcode = CommonFunctions.getSha1Hex(imei + sendermobileval + sessionid);
        Call<TransferVoucherV2Response> transfervoucher = RetrofitBuild.transferVoucherV2Service(getViewContext())
                .transferVoucherV2Call(borroweridval,
                        receiverborroweridval,
                        voucherval,
                        receivernameval,
                        "BORROWER",
                        ".",
                        ".",
                        sessionid,
                        imei,
                        sendermobileval,
                        authcode,
                        receivermobileval,
                        ".");
        transfervoucher.enqueue(transferVoucherV2Callback);
    }

    private final Callback<TransferVoucherV2Response> transferVoucherV2Session = new Callback<TransferVoucherV2Response>() {

        @Override
        public void onResponse(Call<TransferVoucherV2Response> call, Response<TransferVoucherV2Response> response) {
            ResponseBody errorBody = response.errorBody();
            CommonFunctions.hideDialog();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    popsuccesswrap.setVisibility(View.VISIBLE);
                    popconfirmationwrap.setVisibility(View.GONE);
                } else {
                    showToast(response.body().getMessage(), GlobalToastEnum.NOTICE);
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<TransferVoucherV2Response> call, Throwable t) {
            CommonFunctions.hideDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    //TRANSFER VOUCHER TO SUBSCRIBER (NEW)
    private void transferVoucherToSubscriber(Callback<TransferVoucherV2Response> transferVoucherToSubscriberCallBack) {
        String authcode = CommonFunctions.getSha1Hex(imei + sendermobileval + sessionid);
        Call<TransferVoucherV2Response> transferVoucherToSubscriber = RetrofitBuild.transferVoucherV2Service(getViewContext())
                .transferVoucherToSubscriber(
                        sessionid,
                        imei,
                        borroweridval,
                        sendermobileval,
                        authcode,
                        receivermobilevalfinal,
                        voucherval,
                        otpmessage
                );
        transferVoucherToSubscriber.enqueue(transferVoucherToSubscriberCallBack);
    }

    private final Callback<TransferVoucherV2Response> transferVoucherToSubscriberCallBack = new Callback<TransferVoucherV2Response>() {

        @Override
        public void onResponse(Call<TransferVoucherV2Response> call, Response<TransferVoucherV2Response> response) {
            ResponseBody errorBody = response.errorBody();
           hideProgressDialog();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    CommonFunctions.hideDialog();
                    otpdialog.dismiss();
                    dialog.show();
                    popconfirmationwrap.setVisibility(View.GONE);
                    popsuccesswrap.setVisibility(View.VISIBLE);
                } else if (response.body().getStatus().equals("104")) {
                    CommonFunctions.hideDialog();
                    showToast(response.body().getMessage(), GlobalToastEnum.WARNING);
                    otpdialog.show();
                    dialog.dismiss();
                } else {
                    CommonFunctions.hideDialog();
                    showToast(response.body().getMessage(), GlobalToastEnum.ERROR);
                    otpdialog.show();
                    dialog.dismiss();
                }
            } else {
                CommonFunctions.hideDialog();
                showError();
                dialog.dismiss();
                otpdialog.show();
            }
        }

        @Override
        public void onFailure(Call<TransferVoucherV2Response> call, Throwable t) {
            hideProgressDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            dialog.dismiss();
            otpdialog.show();
        }
    };

    //TRANSFER PROCESS HERE
    public void proceedTransfer() {
        int status = CommonFunctions.getConnectivityStatus(this);
        if (status == 0) { //no connection
            showToast("No internet connection.", GlobalToastEnum.NOTICE);
        } else {
            //3.
            showProgressDialog(getViewContext(), "", "Processing Transfer. Please wait ...");
            //verifySession();
            getSession();
        }
    }

    //---------------------------OVERRIDE AND ONCLICK-------------
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_contacts: {
                openContacts();
                break;
            }
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
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String countryval = data.getStringExtra("Country");
                countryprefixval = data.getStringExtra("CountryPrefix");
                country.setText(countryval);
                countrycode.setText(countryprefixval);
                code.setText("+" + countryprefixval);

            }
        } else if (requestCode == RESULT_PICK_CONTACT) {
            if (resultCode == Activity.RESULT_OK)
                contactPicked(data);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                sendermobileval = cursor.getString(cursor.getColumnIndex("mobile"));
                borroweridval = cursor.getString(cursor.getColumnIndex("borrowerid"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));
            } while (cursor.moveToNext());
        }
        cursor.close();

    }
    /**
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * **/

    private void validateRecipientSubscriber(){

        showProgressDialog(getViewContext(),"","Validating subscriber... Please wait...");

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", sendermobileval);
        parameters.put("borrowerid", borroweridval);
        parameters.put("recipientmobilenumber",receivermobilevalfinal);
        parameters.put("type",currentrequest);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "validateRecipientSubscriberV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        validateRecipientSubscriberV2Object(validateRecipientSubscriberV2Callback);

    }
    private void validateRecipientSubscriberV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getTransferVoucherV2API(getViewContext())
                .validateRecipientSubscriberV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse> validateRecipientSubscriberV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    if(FROM.equals("MULTIPLE")){
                        startActivity(new Intent(getViewContext(),TransferVouchersActivity.class).putExtra("recipient",data));
                    }else{
                       if(currentrequest.equals("VALIDATEBORROWER")){
                           processResult(data);
                       }else{
                           if(otpdialog == null){
                               showInputOTP();
                           }
                       }
                    }

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            t.printStackTrace();
            showErrorGlobalDialogs();
        }
    };

    //transfer vouchers
    private void transferVoucherToSubscriberV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", sendermobileval);
        parameters.put("borrowerid", borroweridval);
        parameters.put("recipientmobilenumber",receivermobileval);
        parameters.put("vouchercodes", new Gson().toJson(selectedVouchers));
        parameters.put("otp",otpmessage);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        transferIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", transferIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        transferAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        transferKey = CommonFunctions.getSha1Hex(transferAuth + sessionid + "transferVoucherToSubscriberV3");
        transferParam = CommonFunctions.encryptAES256CBC(transferKey, String.valueOf(paramJson));

        transferVoucherToSubscriberV2Object(transferVoucherToSubscriberV2Callback);


    }
    private void transferVoucherToSubscriberV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getTransferVoucherV2API(getViewContext())
                .transferVoucherToSubscriberV3(transferAuth,sessionid,transferParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  transferVoucherToSubscriberV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(transferKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    popconfirmationwrap.setVisibility(View.GONE);
                    popsuccesswrap.setVisibility(View.VISIBLE);
                    otpdialog.dismiss();
                    dialog.show();
                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }
            }else{
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
            t.printStackTrace();
        }
    };



}
