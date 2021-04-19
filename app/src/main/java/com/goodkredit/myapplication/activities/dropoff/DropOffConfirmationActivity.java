package com.goodkredit.myapplication.activities.dropoff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoPackageBillinReferenceActivity;
import com.goodkredit.myapplication.adapter.prepaidrequest.PaymentChannelsRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.PaymentChannels;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.model.dropoff.DropOffMerchants;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetPaymentPartnersResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DropOffConfirmationActivity extends BaseActivity implements View.OnClickListener {

    private static final String KEY_MERCHANT = "merchant";
    private static final String KEY_NOTES = "notes";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_PURPOSE = "purpose";
    private static final String KEY_REFERENCE_NO = "referenceno";

    private TextView mTvReferenceNo;
    private TextView mTvDropOffAmount;
    private TextView mTvTotalAmount;
    private TextView mTvMerchantName;
    private TextView mTvMerchantAddress;
    private TextView mTvNotes;
    private TextView text_dropoff_refno;
    private LinearLayout layout_dropoff_notes;

    private DropOffMerchants mMerchant;

//    private RadioButton mRbPaymentChannel;

    private String mNotes;
    private String mAmount;
    private String mPurpose;
    private String mReferenceNo;

    private RecyclerView rv_payment_channels;
    private PaymentChannelsRecyclerAdapter paymentChannelsAdapter;

    private String sessionid;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropoff_confirmation);
        init();
        populateData();
    }

    public static void start(Context context, DropOffMerchants merchant, String notes, String amount, String purpose, String referenceno) {
        Intent intent = new Intent(context, DropOffConfirmationActivity.class);
        intent.putExtra(KEY_MERCHANT, merchant);
        intent.putExtra(KEY_NOTES, notes);
        intent.putExtra(KEY_AMOUNT, amount);
        intent.putExtra(KEY_PURPOSE, purpose);
        intent.putExtra(KEY_REFERENCE_NO, referenceno);
        context.startActivity(intent);
    }

    private void init() {
        setupToolbar();

        findViewById(R.id.btn_continue).setOnClickListener(this);

        mTvReferenceNo = findViewById(R.id.txv_dropoff_refno);
        mTvDropOffAmount = findViewById(R.id.tv_dropoff_amount);
        mTvTotalAmount = findViewById(R.id.tv_total_amount);
        mTvMerchantName = findViewById(R.id.tv_merchant_name);
        mTvMerchantAddress = findViewById(R.id.tv_merchant_address);
        mTvNotes = findViewById(R.id.tv_notes);
        text_dropoff_refno = findViewById(R.id.text_dropoff_refno);
        layout_dropoff_notes = findViewById(R.id.layout_dropoff_notes);

        mMerchant = getIntent().getParcelableExtra(KEY_MERCHANT);
        mAmount = getIntent().getStringExtra(KEY_AMOUNT);
        mPurpose = getIntent().getStringExtra(KEY_PURPOSE);
        mNotes = getIntent().getStringExtra(KEY_NOTES);
        mReferenceNo = getIntent().getStringExtra(KEY_REFERENCE_NO);

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

//        mRbPaymentChannel = findViewById(R.id.rb_pr_channel);

        rv_payment_channels = findViewById(R.id.rv_payment_channels);
        rv_payment_channels.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_payment_channels.setNestedScrollingEnabled(false);
        paymentChannelsAdapter = new PaymentChannelsRecyclerAdapter(getViewContext());
        rv_payment_channels.setAdapter(paymentChannelsAdapter);

        //createSession(paymentParnersSession);
        getpaymentPartnersSession();
    }

    private void populateData() {
        populateMerchantDetails(mMerchant, mNotes);
        populateTransactionDetails(mReferenceNo, mAmount);

//        mRbPaymentChannel.setSelected(true);
    }

    private void populateMerchantDetails(DropOffMerchants merchant, String notes) {
        mTvMerchantName.setText(CommonFunctions.replaceEscapeData(merchant.getMerchantName()));
        String address = merchant.getStreetAddress() + ", " + merchant.getCity() + ", " + merchant.getProvince();
        mTvMerchantAddress.setText(CommonFunctions.replaceEscapeData(address));

        if (notes.isEmpty() || notes.equals(" ")) {
            layout_dropoff_notes.setVisibility(View.GONE);
        } else {
            layout_dropoff_notes.setVisibility(View.VISIBLE);
            mTvNotes.setText(notes);
        }
    }

    private void populateTransactionDetails(String refno, String amount) {

        if(refno.equals(".")){
            text_dropoff_refno.setVisibility(View.GONE);
            mTvReferenceNo.setVisibility(View.GONE);
        } else{
            text_dropoff_refno.setVisibility(View.VISIBLE);
            mTvReferenceNo.setVisibility(View.VISIBLE);
            mTvReferenceNo.setText(refno);
        }

        mTvDropOffAmount.setText("PHP " + CommonFunctions.currencyFormatter(amount));
        mTvTotalAmount.setText("PHP " + CommonFunctions.currencyFormatter(amount));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continue: {
                //continue button here

//                if (mRbPaymentChannel.isSelected())
//                    getSession();
//                else
//                    showError();

                getSession();

            }
        }
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "", "Please wait...");
            //processDropOffPayViaPartner();
            processDropOffPayViaPartnerV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void processDropOffPayViaPartner() {

        if (getIntent().getStringExtra(KEY_NOTES).isEmpty()
                || getIntent().getStringExtra(KEY_NOTES).equals(" ")) {
            mNotes = ".";
        } else {
            mNotes = getIntent().getStringExtra(KEY_NOTES);
        }

        Call<GenericResponse> call = RetrofitBuild.getDropOffAPIService(getViewContext())
                .processDropOffPayViaPartner(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        mMerchant.getMerchantID(),
                        mAmount,
                        mPurpose,
                        mNotes,
                        mReferenceNo,
                        "ANDROID"
                );

        call.enqueue(processDropOffPayViaPartnerCallback);
    }

    private Callback<GenericResponse> processDropOffPayViaPartnerCallback = new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                try {
                    if (response.body().getStatus().equals("000")) {
                        GKNegosyoPackageBillinReferenceActivity.start(
                                getViewContext(),
                                response.body().getData(),
                                mAmount);
                        finish();
                    } else {
                        hideProgressDialog();
                        showError(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                hideProgressDialog();
                showError();
            }
    };

    private final Callback<String> paymentParnersSession = new Callback<String>() {

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            String responseData = response.body().toString();
            if (!responseData.isEmpty()) {
                if (responseData.equals("001")) {
                } else if (responseData.equals("error")) {
                } else if (responseData.contains("<!DOCTYPE html>")) {
                } else {
                    sessionid = response.body().toString();
                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
                    getPaymentPartners(getPaymentPartnersSession);
                }
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
        }
    };

    private void getpaymentPartnersSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//            getPaymentPartners(getPaymentPartnersSession);
            getPaymentPartnersV2();
        } else {
            showNoInternetToast();
        }
    }

    private void getPaymentPartners(Callback<GetPaymentPartnersResponse> getPaymentPartnersCallback) {
        Call<GetPaymentPartnersResponse> getPaymentPartners = RetrofitBuild.getPaymentPartnersService(getViewContext())
                .getPaymentPartnersCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        "0");
        getPaymentPartners.enqueue(getPaymentPartnersCallback);
    }

    private final Callback<GetPaymentPartnersResponse> getPaymentPartnersSession = new Callback<GetPaymentPartnersResponse>() {

        @Override
        public void onResponse(Call<GetPaymentPartnersResponse> call, Response<GetPaymentPartnersResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    List<PaymentChannels> paymentChannels = response.body().getPaymentChannels();
                    if (paymentChannels.size() > 0) {
                        rv_payment_channels.setVisibility(View.VISIBLE);
                        paymentChannelsAdapter.setPaymentsChannelsData(paymentChannels);
                    } else {
                        rv_payment_channels.setVisibility(View.GONE);
                    }
                } else {
                    showError(response.body().getMessage());
                }
            }
        }

        @Override
        public void onFailure(Call<GetPaymentPartnersResponse> call, Throwable t) {
            rv_payment_channels.setVisibility(View.GONE);
        }
    };

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void processDropOffPayViaPartnerV2(){

        if (getIntent().getStringExtra(KEY_NOTES).isEmpty()
                || getIntent().getStringExtra(KEY_NOTES).equals(" ")) {
            mNotes = ".";
        } else {
            mNotes = getIntent().getStringExtra(KEY_NOTES);
        }


        LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
        parameters.put("imei",imei);
        parameters.put("userid",userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("merchantid",  mMerchant.getMerchantID());
        parameters.put("amount", mAmount);
        parameters.put("purpose", mPurpose);
        parameters.put("notes", mNotes);
        parameters.put("referenceno", mReferenceNo);
        parameters.put("devicetype", CommonVariables.devicetype);


        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "processDropOffPayViaPartnerV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        processDropOffPayViaPartnersV2Object(processDropOffPayViaPartnerV2Session);

    }

    private void processDropOffPayViaPartnersV2Object(Callback<GenericResponse> process){
        Call<GenericResponse> call = RetrofitBuilder.getDropOffV2API(getViewContext())
                .processDropOffPayViaPartnerV2(
                    authenticationid,sessionid,param
                );

        call.enqueue(process);
    }

    private Callback<GenericResponse> processDropOffPayViaPartnerV2Session = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorbody = response.errorBody();

           if(errorbody == null){
               String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
               if (response.body().getStatus().equals("000")) {

                   String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                   GKNegosyoPackageBillinReferenceActivity.start(
                           getViewContext(),
                           data,
                           mAmount);
                   finish();

               }else if(response.body().getStatus().equals("003")){
                   showAutoLogoutDialog(message);
               }else {
                   hideProgressDialog();
                   showError(message);
               }
           }else{
               showError();
           }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            Logger.debug("okhttp","ERROR: "+t.getMessage());
            showError();
        }
    };


    //GET PAYMENT PARTNERS
    private void getPaymentPartnersV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("limit", "0");

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getPaymentPartnersV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getPaymentPartnersV2Object(getPaymentPartnersV2Callback);

        }else{
            showNoInternetToast();
        }
    }
    private void getPaymentPartnersV2Object(Callback<GenericResponse> getPaymentPartners){
        Call<GenericResponse> call = RetrofitBuilder.getPaymentV2API(getViewContext())
                .getPaymentPartnersV2(authenticationid,sessionid,param);
        call.enqueue(getPaymentPartners);
    }
    private final Callback<GenericResponse> getPaymentPartnersV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData  =  CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<PaymentChannels> paymentChannels = new Gson().fromJson(decryptedData, new TypeToken<List<PaymentChannels>>(){}.getType());
                    if (paymentChannels.size() > 0) {
                        rv_payment_channels.setVisibility(View.VISIBLE);
                        paymentChannelsAdapter.setPaymentsChannelsData(paymentChannels);
                    } else {
                        rv_payment_channels.setVisibility(View.GONE);
                    }
                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }
                else {
                    showErrorGlobalDialogs(decryptedMessage);
                }
            }else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorToast("Something went wrong.Please try again. ");
        }
    };

}
