package com.goodkredit.myapplication.activities.transfer;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.transfervoucher.TransferValidateVoucher;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.SpacesItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferVouchersActivity extends BaseActivity implements View.OnClickListener {

    DatabaseHandler db;

    private RelativeLayout overlay;

    //COMMON
    private String sessionid = "";
    private String authcode = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";

    //guarantor id
    private String guarantorid;

    //Views
    TextView droparea;
    RelativeLayout dropareawrap;
    TextView dropareatext;
    TextView totalnoofvouchers;
    TextView amounttenval;
    TextView mTvEmpty;

    Button proceed;

    //horizontal recyclerview
    RecyclerView horizontal_recycler_payment, horizontal_recycler_view;

    //UTILITIES
    private DecimalFormat formatter;
    double remaingbalance = 0.0;
    double amounttopay = 0.0;
    double totalamountpaid = 0.0;
    int numberofvouchers = 0;
    private String mobileval = "";
    private String networkval = "";
    private String producttypeval = "";
    private String productcodeval = "";
    private String amountopayvalue = "0";
    private String vouchercodeval = "";
    private String voucherpinval = "";
    private String vouchersession = "";
    private String purchasestatus = "";
    private String mMerchantID = ".";
    int currentpossition = 0;
    static String PROCESS = "";

    //First load list
    private AvailableVouchersAdapter availableVouchersAdapter;
    private PaymentVouchersAdapter paymentVouchersAdapter;

    private ArrayList<Voucher> availableVouchersList;
    private ArrayList<Voucher> paymentVouchersList;

    //for list of selected vouchers
    private ArrayList<String> selectedVouchers = new ArrayList<>();


    //FOR SECURITY
    private String index;
    private String authenticationid;
    private String param;
    private String keyEncryption;

    private String transferAuth;
    private String transferIndex;
    private String transferKey;
    private String transferParam;



    //validate voucher
    private String validateVoucherAuthenticationid;
    private String validateVoucherIndex;
    private String validateVoucherKeyEncryption;
    private String validateVoucherParam;


    //
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
    TextView poppopvouchercodelbl;
    TextView canceltxn;
    TextView popconfirm;
    EditText code;
    EditText countrycode;

    Dialog dialog;
    private MaterialDialog otpdialog;

    private String otpmessage;

    String receivernameval = "";
    String receiveraddressval = "";
    String receivermobileval = "";
    String receiverborroweridval = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_vouchers);

        //intialize db
        db = new DatabaseHandler(this);

        //VERSION 2
        imei = PreferenceUtils.getImeiId(getApplicationContext());
        userid = PreferenceUtils.getUserId(getApplicationContext());
        borrowerid = PreferenceUtils.getBorrowerId(getApplicationContext());
        //SESSION
        sessionid = PreferenceUtils.getSessionID(getApplicationContext());


        //for overlay in startup
        overlay = findViewById(R.id.overlay);
        overlay.requestFocus();
        final Handler overlayFadeOut = new Handler();
        overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overlay.setVisibility(View.GONE);
            }
        });

        overlayFadeOut.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (overlay.getVisibility() == View.VISIBLE) {
                    Animation fadeOut = new AlphaAnimation(1, 0);
                    fadeOut.setInterpolator(new AccelerateInterpolator());
                    fadeOut.setDuration(1000);

                    fadeOut.setAnimationListener(new Animation.AnimationListener() {
                        public void onAnimationEnd(Animation animation) {
                            overlay.setVisibility(View.GONE);
                        }

                        public void onAnimationRepeat(Animation animation) {
                        }

                        public void onAnimationStart(Animation animation) {
                        }
                    });
                    overlay.startAnimation(fadeOut);
                }
            }
        }, 5000);

        //for cancel toolbar
        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_actionbar_icon);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancelPaymetTransactionDialog();
                cancelPaymetTransactionDialogNew();
            }
        });


        //initialize recycler view
        horizontal_recycler_view = findViewById(R.id.horizontal_recycler_view);
        horizontal_recycler_payment = findViewById(R.id.horizontal_recycler_view2);

        //initialize elements
        droparea = findViewById(R.id.droparea);
        dropareawrap = findViewById(R.id.dropareawrap);
        dropareatext = findViewById(R.id.dropareatext);
        totalnoofvouchers = findViewById(R.id.totalnoofvouchers);
        amounttenval = findViewById(R.id.amounttenval);
        mTvEmpty = findViewById(R.id.emptyvoucherPayment);
        proceed = findViewById(R.id.proceed);

        proceed.setOnClickListener(this);

        //Initialize first load of data
        formatter = new DecimalFormat("#,###,##0.00");

        availableVouchersList = new ArrayList<>();
        availableVouchersList = db.getIndividualVouchers(db);

        //get vouchers
        getSession();

        paymentVouchersList = new ArrayList<>();

        //initialize adapter
        availableVouchersAdapter = new AvailableVouchersAdapter(availableVouchersList);
        paymentVouchersAdapter = new PaymentVouchersAdapter(paymentVouchersList);

        //Initialize Recycler view
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);

        LinearLayoutManager horizontalLayoutManagaer1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        horizontal_recycler_view.addItemDecoration(new SpacesItemDecoration(10));
        horizontal_recycler_payment.setLayoutManager(horizontalLayoutManagaer1);


        //for dialog
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
        popconfirm = dialog.findViewById(R.id.popconfirm);
        popconfirmation = dialog.findViewById(R.id.confirmationlbl);
        popconfirmation.setText("Transfer to a Subscriber");

        popconfirmationwrap = dialog.findViewById(R.id.confirmationwrap);
        popsuccesswrap = dialog.findViewById(R.id.successwrap);
        popsuccessclose = dialog.findViewById(R.id.popok);
        popsuccessconfirmation = dialog.findViewById(R.id.succesconfirmationlbl);
        poppopvouchercodelbl = dialog.findViewById(R.id.popvouchercodelbl);
        canceltxn = dialog.findViewById(R.id.canceltxn);
        canceltxn.setOnClickListener(this);
        popconfirm.setOnClickListener(this);

        popsuccessconfirmation.setText("Transfer to a Subscriber");
        popemail.setVisibility(View.GONE);
        poppopvouchercodelbl.setText("Vouchers:");


        //click close
        popsuccessclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CommonVariables.VOUCHERISFIRSTLOAD = true; //to realod the vouchers
                Intent intent = new Intent(getViewContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        });
    }

    //methods
    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            getVouchersV3withSecurity();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private void preLoadVoucher(String from) {
        try {
            if (db.getIndividualVouchers(db).size() > 0) {
                if (from.equals("REFRESH")) {
                    availableVouchersList.clear();
                }
                availableVouchersList = db.getIndividualVouchers(db);
                availableVouchersAdapter.setData(availableVouchersList);
                mTvEmpty.setVisibility(View.GONE);
                horizontal_recycler_view.setVisibility(View.VISIBLE);
            } else {
                mTvEmpty.setVisibility(View.VISIBLE);
                horizontal_recycler_view.setVisibility(View.GONE);
            }

            if (from.equals("REFRESH")) {
                for (int i = 0; i < paymentVouchersList.size(); i++) {
                    String serialno = paymentVouchersList.get(i).getVoucherSerialNo();

                    if (availableVouchersList.contains(serialno)) {
                        availableVouchersList.remove(paymentVouchersList.get(paymentVouchersList.indexOf(serialno)));
                    }
                }
            }

            //set adapter first load
            horizontal_recycler_view.setAdapter(availableVouchersAdapter);
            horizontal_recycler_payment.setAdapter(paymentVouchersAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Processing. Please wait ...");
            if (PROCESS.contentEquals("VALIDATEVOUCHER")) {
                validateSubscriberVoucher();
            } else if (PROCESS.contentEquals("FETCHVOUCHER")) {
                getSession();
            }
        } else {
            showNoInternetToast();
        }
    }

    private void cancelPaymetTransactionDialogNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());
        dialog.createDialog("Cancellation", "Are you sure you want to cancel " +
                        "this process?",
                "Cancel", "OK", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

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
                dialog.dismiss();
                PROCESS = "CANCELTRANSACTION";
                finish();
            }
        });
    }

    private void showInputOTP() {

        Log.d("okhttp","VOUCHERCODES : "+new Gson().toJson(selectedVouchers));

        try {
            otpdialog = new MaterialDialog.Builder(getViewContext())
                    .customView(R.layout.dialog_otp_name, false)
                    .cancelable(false)
                    .positiveText("OK")
                    .negativeText("Cancel")
                    .show();

            View view = otpdialog.getCustomView();
            final EditText edtOtpName = view.findViewById(R.id.dialogOtpName);

            final TextView txvOtpLabel = view.findViewById(R.id.dialogOtpLabelName);

            txvOtpLabel.setText("Enter One Time Password");

            View positive = otpdialog.getActionButton(DialogAction.POSITIVE);
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    otpmessage = edtOtpName.getText().toString();
                    if (otpmessage.trim().equals("")) {
                        showToast("Please input the receive otp message", GlobalToastEnum.WARNING);
                    } else {
                        showProgressDialog(getViewContext(), "", "Processing. Please wait ...");
                        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
                            transferVoucherToSubscriberV2();
                        }else{
                            hideProgressDialog();
                            showNoInternetToast();
                        }
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
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * */

    //get vouchers
    private void getVouchersV3withSecurity(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("limit", String.valueOf(0));
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getVouchersV3");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getVouchersV3withSecurityObject(getVouchersV3withSecurityCallback);

        }else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }
    private void getVouchersV3withSecurityObject(Callback<GenericResponse> getVoucherV3Callback) {
        Call<GenericResponse> getvouchers = RetrofitBuilder.getVoucherV2API(getViewContext())
                .getVouchersV3(authenticationid,sessionid,param);
        getvouchers.enqueue(getVoucherV3Callback);
    }
    private final Callback<GenericResponse>  getVouchersV3withSecurityCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    ArrayList<Voucher> voucherList = new Gson().fromJson(CommonFunctions.parseJSON(data,"Voucher"),new TypeToken<ArrayList<Voucher>>(){}.getType());

                    db.truncateTable(db, DatabaseHandler.VOUCHERS);

                    //inserting vouchers to local db
                    new LongInsertOperation().execute(voucherList);

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

    //validate Vouchers
    private void validateSubscriberVoucher(){

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei",imei);
        parameters.put("userid",userid);
        parameters.put("borrowerid",borrowerid);
        parameters.put("vouchercode", vouchercodeval);
        parameters.put("devicetype",CommonVariables.devicetype);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        validateVoucherIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", validateVoucherIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        validateVoucherAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        validateVoucherKeyEncryption = CommonFunctions.getSha1Hex(validateVoucherAuthenticationid + sessionid + "validateSubscriberVoucher");
        validateVoucherParam = CommonFunctions.encryptAES256CBC(validateVoucherKeyEncryption, String.valueOf(paramJson));

        Call<GenericResponse> call = RetrofitBuilder.getTransferVoucherV2API(getViewContext())
                .validateSubscriberVoucher(
                        validateVoucherAuthenticationid,sessionid,validateVoucherParam
                );

        call.enqueue(validateVoucherV2Callback);


    }
    private final Callback<GenericResponse>  validateVoucherV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(validateVoucherKeyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String decryptedData = CommonFunctions.decryptAES256CBC(validateVoucherKeyEncryption,response.body().getData());
                    Log.d("okhttp","DATTAAAA: "+decryptedData);
                    Voucher validatedVoucher = new Gson().fromJson(decryptedData,Voucher.class);
                    Log.d("okhttp","DATTAAASA: "+new Gson().toJson(validatedVoucher));

                    setUIVoucherValid(validatedVoucher);
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
        }
    };

    //validate subscriber
    private void validateRecipientSubscriber(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("recipientmobilenumber",receivermobileval);
        parameters.put("type","TRANSFEROTP");
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
            dialog.dismiss();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                   showInputOTP();
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
            dialog.dismiss();
            t.printStackTrace();
            showErrorGlobalDialogs();
        }
    };


    //transfer vouchers
    private void transferVoucherToSubscriberV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
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




    //LISTENER
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.proceed:{

                String data = getIntent().getStringExtra("recipient");

                List<TransferValidateVoucher> voteparticipantlist = new Gson().fromJson(data,new
                        TypeToken<List<TransferValidateVoucher>>(){}.getType());

                for(TransferValidateVoucher transferValidateVoucher : voteparticipantlist) {
                    receiverborroweridval = transferValidateVoucher.getBorrowerID();
                    receivernameval = transferValidateVoucher.getBorrowerName();
                    receivermobileval = transferValidateVoucher.getMobileNo();
                    receiveraddressval = transferValidateVoucher.getStreetAddress() + transferValidateVoucher.getCity();
                }

                DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
                popname.setText(receivernameval);
                popvoucher.setText(String.valueOf(numberofvouchers));
                popamount.setText(formatter.format(Double.parseDouble(String.valueOf(totalamountpaid))));
                popmobile.setText("+" + receivermobileval);
                dialog.show();

                break;
            }
            case R.id.canceltxn:{
                if(dialog != null){
                    dialog.dismiss();
                }
                break;
            }
            case R.id.popconfirm:{
                validateRecipientSubscriber();
                break;
            }

        }
    }


    //Asynctask
    private class LongInsertOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {

        @Override
        protected ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
            if (db != null) {
                for (Voucher voucher : lists[0]) {
                    db.insertVouchers(db, voucher);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> vouchers) {
            super.onPostExecute(vouchers);
            //populating available voucher list
            if (db.getIndividualVouchers(db).size() > 0) {
                new LongOperation().execute();
            } else {
                hideProgressDialog();
            }
        }
    }
    private class LongOperation extends AsyncTask<ArrayList<Voucher>, Void, ArrayList<Voucher>> {

        @Override
        protected void onPreExecute() {
            availableVouchersList.clear();
        }

        @Override
        protected ArrayList<Voucher> doInBackground(ArrayList<Voucher>... lists) {
            availableVouchersList = db.getIndividualVouchers(db);
            return availableVouchersList;
        }

        @Override
        protected void onPostExecute(ArrayList<Voucher> voucherData) {
            hideProgressDialog();
            preLoadVoucher("REFRESH");
        }
    }


    //Private Class
    class AvailableVouchersAdapter extends RecyclerView.Adapter<AvailableVouchersAdapter.MyViewHolder> {

        private ArrayList<Voucher> horizontalList;
        private int mPosition = 0;
        private Voucher mVoucher;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView txtView;
            public RelativeLayout wrap;
            public TextView ball;
            public ImageView prepaidtag;
            private AppCompatTextView groupBalance;
            private TextView groupName;

            public MyViewHolder(View view) {
                super(view);
                txtView = view.findViewById(R.id.txtView);
                ball = view.findViewById(R.id.bal);
                wrap = view.findViewById(R.id.wrapper);
                prepaidtag = view.findViewById(R.id.prepaid_tag);
                groupBalance = view.findViewById(R.id.group_voucher_balance);
                groupName = view.findViewById(R.id.group_voucher_title);

                groupBalance.setVisibility(View.GONE);
                groupName.setVisibility(View.GONE);
            }
        }


        public AvailableVouchersAdapter(ArrayList<Voucher> horizontalList) {
            this.horizontalList = horizontalList;
        }

        private void setData(ArrayList<Voucher> horizontalList) {
            this.horizontalList.clear();
            this.horizontalList.addAll(horizontalList);
            notifyDataSetChanged();
        }

        private void clear() {
            this.horizontalList.clear();
            notifyDataSetChanged();
        }

        public ArrayList<Voucher> getCurrentData(int currentPos) {
            horizontalList.remove(currentPos);
            notifyDataSetChanged();
            return horizontalList;
        }

        public int getClickedPosition() {
            return mPosition;
        }

        private void setClickedPosition(int pos) {
            mPosition = pos;
        }

        private void setClickedVoucher(Voucher voucher) {
            mVoucher = voucher;
        }

        public Voucher getClickedVoucher() {
            return mVoucher;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_airtime_payement1_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            Voucher voucher = horizontalList.get(position);
            String bal;
            String url = "";
            String remainingbal = "0";
            String isprepaid = "";

            if (voucher != null) {
                try {
                    if (voucher.getGroupVoucherCode().equals(".") && voucher.getIsPartiallyUsed() == 0) {
                        if (voucher.getExtra3().equals(".")) {
                            holder.prepaidtag.setVisibility(View.GONE);
                        } else {
                            holder.prepaidtag.setVisibility(View.VISIBLE);
                        }

                        Glide.with(getViewContext())
                                .load(CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg")
                                .into(holder.txtView);

                        holder.ball.setVisibility(View.VISIBLE);
                        if (voucher.getRemainingBalance() % 1 == 0)
                            bal = String.valueOf((int) voucher.getRemainingBalance());
                        else
                            bal = String.valueOf(voucher.getRemainingBalance());

                        holder.ball.setText(bal);
                        holder.wrap.setTag(voucher);
                    }else{
                        holder.wrap.setVisibility(View.GONE);
                    }

                    holder.wrap.setOnLongClickListener(new View.OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {
                            // TODO Auto-generated method stub
                            setClickedPosition(holder.getPosition());
                            setClickedVoucher(horizontalList.get(getClickedPosition()));
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
                            v.startDrag(data, shadow, null, 0);

                            droparea.setOnDragListener(new View.OnDragListener() {

                                @Override
                                public boolean onDrag(View v, DragEvent event) {

                                    // TODO Auto-generated method stub
                                    final int action = event.getAction();

                                    switch (action) {

                                        case DragEvent.ACTION_DRAG_STARTED:
                                            dropareawrap.setBackgroundResource(R.color.colorPrimaryDark);
                                            dropareatext.setText("Drop voucher here");
                                            dropareatext.setTextColor(Color.parseColor("#25bed9"));
                                            break;

                                        case DragEvent.ACTION_DRAG_EXITED:
                                            dropareawrap.setBackgroundResource(R.color.superlightgray);
                                            dropareatext.setText("Drop voucher here");
                                            dropareatext.setTextColor(Color.parseColor("#80000000"));
                                            break;
                                        case DragEvent.ACTION_DRAG_ENTERED:
                                            break;

                                        case DragEvent.ACTION_DROP: {
                                            if (purchasestatus.equals("PAID")) {
                                                showToast("Amount tendered is already enough for this transaction", GlobalToastEnum.NOTICE);
                                            } else {
                                                if(availableVouchersList.size() > 0) {
                                                    String serialno = availableVouchersList.get(position).getVoucherSerialNo();
                                                    Cursor c = db.getSpecificVoucher(db, serialno);
                                                    if (c.getCount() > 0) {
                                                        c.moveToFirst();
                                                        do {
                                                            if (c.getString(c.getColumnIndex("GroupVoucherCode")).equals(".")) {
                                                                vouchercodeval = c.getString(c.getColumnIndex("vouchercode"));
                                                                voucherpinval = c.getString(c.getColumnIndex("voucherpin"));
                                                            } else {
                                                                vouchercodeval = c.getString(c.getColumnIndex("GroupVoucherCode"));
                                                                voucherpinval = c.getString(c.getColumnIndex("GroupVoucherPIN"));
                                                            }
                                                        } while (c.moveToNext());
                                                    }

                                                    currentpossition = position;
                                                    PROCESS = "VALIDATEVOUCHER";
                                                    createSession();
                                                }
                                            }
                                            return (true);
                                        }

                                        case DragEvent.ACTION_DRAG_ENDED: {
                                            dropareawrap.setBackgroundResource(R.color.superlightgray);
                                            dropareatext.setText("Drop voucher here");
                                            dropareatext.setTextColor(Color.parseColor("#80000000"));
                                            return (true);

                                        }

                                        default:
                                            break;
                                    }
                                    return true;
                                }
                            });


                            return false;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }
    class PaymentVouchersAdapter extends RecyclerView.Adapter<PaymentVouchersAdapter.MyViewHolder> {

        private ArrayList<Voucher> horizontalListPayment;

         class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView txtView;
            public RelativeLayout wrap;
            public TextView ball;
            public ImageView cancelpayment;
            public ImageView prepaidtag;
            private AppCompatTextView groupBalance;
            private TextView groupName;


            public MyViewHolder(View view) {
                super(view);
                txtView = view.findViewById(R.id.txtView);
                ball = view.findViewById(R.id.bal);
                wrap = view.findViewById(R.id.wrapper);
                cancelpayment = view.findViewById(R.id.cancelpayment);
                prepaidtag = view.findViewById(R.id.prepaid_tag);
                groupBalance = view.findViewById(R.id.group_voucher_balance);
                groupName = view.findViewById(R.id.group_voucher_title);
            }

        }


        public PaymentVouchersAdapter(ArrayList<Voucher> horizontalListPayment) {
            this.horizontalListPayment = horizontalListPayment;
        }

        private void setData(ArrayList<Voucher> horizontalListPayment) {
            this.horizontalListPayment = horizontalListPayment;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_airtime_payment1_item1, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Voucher voucher = paymentVouchersList.get(position);

            Log.d("antonhttp", "ADAPTER VOUCHER: " + new Gson().toJson(voucher));

            String bal = "0";
            String url = "";
            String remainingbal = "0";
            String isprepaid = "";
            if (voucher.getGroupVoucherCode().equals(".")) {
                if (voucher.getExtra3().equals(".")) {
                    holder.prepaidtag.setVisibility(View.GONE);
                } else {
                    holder.prepaidtag.setVisibility(View.VISIBLE);
                }

                Glide.with(getViewContext())
                        .load(CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg")
                        .into(holder.txtView);

                holder.ball.setVisibility(View.VISIBLE);
                holder.groupBalance.setVisibility(View.GONE);
                holder.groupName.setVisibility(View.GONE);

                if (voucher.getRemainingBalance() % 1 == 0)
                    bal = String.valueOf((int) voucher.getRemainingBalance());
                else
                    bal = String.valueOf(voucher.getRemainingBalance());

//                bal = voucher.getVoucherBalance();

            } else {
                Glide.with(getViewContext())
                        .load(CommonVariables.s3link + "group-vouchers.png")
                        .into(holder.txtView);

                double doubleBalance = Double.parseDouble(voucher.getGroupBalance());

                if (doubleBalance % 1 == 0)
                    bal = String.valueOf((int) doubleBalance);
                else
                    bal = String.valueOf(doubleBalance);

                holder.groupBalance.setVisibility(View.VISIBLE);
                holder.groupName.setVisibility(View.VISIBLE);
                holder.ball.setVisibility(View.GONE);
                holder.prepaidtag.setVisibility(View.GONE);
                holder.groupBalance.setText(CommonFunctions.currencyFormatter(voucher.getGroupBalance()));
                if (!voucher.getGroupName().equals("."))
                    holder.groupName.setText(voucher.getGroupName());
            }

            holder.ball.setText(bal);

            holder.wrap.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    // TODO Auto-generated method stub
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
                    v.startDrag(data, shadow, null, 0);

                    droparea.setOnDragListener(new View.OnDragListener() {

                        @Override
                        public boolean onDrag(View v, DragEvent event) {

                            // TODO Auto-generated method stub
                            final int action = event.getAction();

                            switch (action) {

                                case DragEvent.ACTION_DRAG_STARTED:
                                    dropareawrap.setBackgroundResource(R.color.colorred);
                                    dropareatext.setText("Drop voucher here to cancel");
                                    dropareatext.setTextColor(Color.parseColor("#FF0000"));
                                    break;

                                case DragEvent.ACTION_DRAG_EXITED:
                                    dropareawrap.setBackgroundResource(R.color.superlightgray);
                                    dropareatext.setText("Drop voucher here");
                                    dropareatext.setTextColor(Color.parseColor("#80000000"));
                                    break;

                                case DragEvent.ACTION_DRAG_ENTERED:
                                    break;

                                case DragEvent.ACTION_DROP: {

                                    String serialno = paymentVouchersList.get(position).getVoucherSerialNo();
                                    Cursor c = db.getSpecificVoucher(db, serialno);
                                    if (c.getCount() > 0) {
                                        c.moveToFirst();
                                        do {
                                            if (c.getString(c.getColumnIndex("GroupVoucherCode")).equals(".")) {
                                                vouchercodeval = c.getString(c.getColumnIndex("vouchercode"));
                                                voucherpinval = c.getString(c.getColumnIndex("voucherpin"));
                                            } else {
                                                vouchercodeval = c.getString(c.getColumnIndex("GroupVoucherCode"));
                                                voucherpinval = c.getString(c.getColumnIndex("GroupVoucherPIN"));
                                            }
                                        } while (c.moveToNext());
                                    }

                                    currentpossition = position;

                                    return (true);
                                }

                                case DragEvent.ACTION_DRAG_ENDED: {
                                    dropareawrap.setBackgroundResource(R.color.superlightgray);
                                    dropareatext.setText("Drop voucher here");
                                    dropareatext.setTextColor(Color.parseColor("#80000000"));

                                    return (true);

                                }

                                default:
                                    break;
                            }
                            return true;
                        }
                    });


                    return false;
                }
            });

            holder.cancelpayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String serialno = paymentVouchersList.get(position).getVoucherSerialNo();
                    Cursor c = db.getSpecificVoucher(db, serialno);
                    if (c.getCount() > 0) {
                        c.moveToFirst();
                        do {
                            if (c.getString(c.getColumnIndex("GroupVoucherCode")).equals(".")) {
                                vouchercodeval = c.getString(c.getColumnIndex("vouchercode"));
                                voucherpinval = c.getString(c.getColumnIndex("voucherpin"));
                            } else {
                                vouchercodeval = c.getString(c.getColumnIndex("GroupVoucherCode"));
                                voucherpinval = c.getString(c.getColumnIndex("GroupVoucherPIN"));
                            }
                        } while (c.moveToNext());
                    }
                    currentpossition = position;
                    setUIRemovedVoucher(voucher);
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalListPayment.size();
        }
    }



    //VOUCHER VALIDATION method
    private void  setUIVoucherValid(Voucher voucher) {
        try {

            numberofvouchers+=1;

            totalnoofvouchers.setText(String.valueOf(numberofvouchers));

            remaingbalance = Double.parseDouble(String.valueOf(voucher.getRemainingBalance()));
            totalamountpaid += remaingbalance;

            Log.d("okhttp", "VALIDATED VOUCHER: " + new Gson().toJson(voucher));

            amounttenval.setText(formatter.format(totalamountpaid));

            dropareawrap.setBackgroundResource(R.color.superlightgray);
            dropareatext.setText("Drop voucher here");
            dropareatext.setTextColor(Color.parseColor("#80000000"));

            Voucher mVoucher = availableVouchersList.get(availableVouchersAdapter.getClickedPosition());
            mVoucher.setRemainingBalance(Double.parseDouble(String.valueOf(voucher.getRemainingBalance())));

            paymentVouchersList.add(0, mVoucher);
            paymentVouchersAdapter.setData(paymentVouchersList);
            paymentVouchersAdapter.notifyDataSetChanged();
            horizontal_recycler_payment.smoothScrollToPosition(0);

            availableVouchersList.remove(availableVouchersAdapter.getClickedPosition());
            availableVouchersAdapter.setData(availableVouchersList);
            availableVouchersAdapter.notifyDataSetChanged();

            selectedVouchers.add(voucher.getVoucherCode());

            currentpossition = 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setUIRemovedVoucher(Voucher voucher) {
        try {

            numberofvouchers-=1;

            totalnoofvouchers.setText(String.valueOf(numberofvouchers));

            remaingbalance = Double.parseDouble(String.valueOf(voucher.getRemainingBalance()));
            totalamountpaid -= remaingbalance;

            Log.d("okhttp", "VALIDATED VOUCHER: " + new Gson().toJson(voucher));

            amounttenval.setText(formatter.format(totalamountpaid));

            dropareawrap.setBackgroundResource(R.color.superlightgray);
            dropareatext.setText("Drop voucher here");
            dropareatext.setTextColor(Color.parseColor("#80000000"));

            //
            availableVouchersList.add(paymentVouchersList.get(currentpossition));
            horizontal_recycler_view.setAdapter(availableVouchersAdapter);

            paymentVouchersList.remove(paymentVouchersList.get(currentpossition));
            horizontal_recycler_payment.setAdapter(paymentVouchersAdapter);

            currentpossition = 0;

            availableVouchersAdapter.setData(availableVouchersList);
            availableVouchersAdapter.notifyDataSetChanged();

            selectedVouchers.remove(voucher.getVoucherCode());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}