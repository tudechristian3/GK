package com.goodkredit.myapplication.activities.gknegosyo;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.model.GKNegosyPackage;
import com.goodkredit.myapplication.model.GKNegosyoApplicationStatus;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKNegosyoPaymentConfirmationActivity extends BaseActivity implements View.OnClickListener {

    private static final String DSTRBTR = "DSTRBTR";
    private static final String DSTRBTRNAME = "DSTRBTRNAME";
    private static final String AMNT = "AMNT";
    private static final String PCKG = "PCKGNME";
    private static final String AMNTPAY = "AMNTPAY";
    private static final String AMNTTNDRD = "AMNTTNDRD";
    private static final String CHNG = "CHNG";
    private static final String VCHERSESH = "VCHERSESH";
    private static final String SRVCE = "SRVCE";
    private static final String STATS = "STATS";

    private static final String PROCESS_TRANSACTION = "PROCESS_TRANSACTION";

    private TextView mTvDistributor;
    private TextView mTvAmount;
    private TextView mTvPackageName;
    private TextView mTvAmountToPay;
    private TextView mTvAmountTendered;
    private TextView mTvChange;

    private String strDistributor;
    private String strDistributorName;
    private String strAmount;
    private String strPackageName;
    private String strAmountToPay;
    private String strAmountTendered;
    private String strChange;
    private String strVoucherSession;

    private GKService mGKService;
    private GKNegosyPackage mGKNegosyoPackage;
    private String mDistributorID;
    private GKNegosyoApplicationStatus mGKNegosyoApplicationStatus;

    private Intent mIntent = null;

    private boolean isStatusChecked = false;
    private int totaldelaytime = 10000;
    private int currentdelaytime = 0;

    MaterialDialog mLoaderDialog;
    private TextView mLoaderDialogMessage;
    private TextView mLoaderDialogTitle;
    private ImageView mLoaderDialogImage;
    private TextView mLoaderDialogClose;
    private TextView mLoaderDialogRetry;
    private LinearLayout cancelbtncontainer;
    private RelativeLayout buttonLayout;

    private Address mAddress;

    private String sessionid;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_payment_confirmation);
        init();
    }

    private void init() {

        setupToolbar();

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mIntent = getIntent();

        strDistributor = mIntent.getStringExtra(DSTRBTR);
        strDistributorName = mIntent.getStringExtra(DSTRBTRNAME);
        strAmount = mIntent.getStringExtra(AMNT);
        mGKService = mIntent.getParcelableExtra(SRVCE);
        mGKNegosyoPackage = mIntent.getParcelableExtra(PCKG);
        strAmountToPay = mIntent.getStringExtra(AMNTPAY);
        strAmountTendered = mIntent.getStringExtra(AMNTTNDRD);
        strChange = mIntent.getStringExtra(CHNG);
        strVoucherSession = mIntent.getStringExtra(VCHERSESH);
        mGKNegosyoApplicationStatus = mIntent.getParcelableExtra(STATS);

        mTvDistributor = findViewById(R.id.tv_distributor);
        mTvAmount = findViewById(R.id.tv_amount);
        mTvPackageName = findViewById(R.id.tv_packagename);
        mTvAmountToPay = findViewById(R.id.tv_amount_to_pay);
        mTvAmountTendered = findViewById(R.id.tv_amount_tendered);
        mTvChange = findViewById(R.id.tv_amount_change);

        mTvDistributor.setText(strDistributorName);
        mTvAmount.setText(CommonFunctions.currencyFormatter(strAmount));
        mTvPackageName.setText(mGKNegosyoPackage.getPackageName());
        mTvAmountToPay.setText(CommonFunctions.currencyFormatter(strAmountToPay));
        mTvAmountTendered.setText(CommonFunctions.currencyFormatter(strAmountTendered));
        mTvChange.setText(CommonFunctions.currencyFormatter(strChange));

        findViewById(R.id.confirm).setOnClickListener(this);

        if (CacheManager.getInstance().getGKNegosyoResellerAddress() != null) {
            mAddress = CacheManager.getInstance().getGKNegosyoResellerAddress();
        }

        setupLoaderDialog();
    }

    public static void start(Context context, String distributor, String amount, GKNegosyPackage pckage, GKService service, String amountToPay,
                             String amountTendered, String change, String voucherSession, GKNegosyoApplicationStatus status, String distroName) {
        Intent intent = new Intent(context, GKNegosyoPaymentConfirmationActivity.class);
        intent.putExtra(DSTRBTR, distributor);
        intent.putExtra(DSTRBTRNAME, distroName);
        intent.putExtra(AMNT, amount);
        intent.putExtra(PCKG, pckage);
        intent.putExtra(SRVCE, service);
        intent.putExtra(AMNTPAY, amountToPay);
        intent.putExtra(AMNTTNDRD, amountTendered);
        intent.putExtra(CHNG, change);
        intent.putExtra(VCHERSESH, voucherSession);
        intent.putExtra(STATS, status);
        context.startActivity(intent);
    }

//    private void createSession() {
//        if (!mLoaderDialog.isShowing())
//            mLoaderDialog.show();
//        createSession(createSessionCallback);
//    }
//
//    private Callback<String> createSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            try {
//                ResponseBody errBody = response.errorBody();
//                if (errBody == null) {
//                    if (!response.body().isEmpty()
//                            && !response.body().contains("<!DOCTYPE html>")
//                            && !response.body().equals("001")
//                            && !response.body().equals("002")
//                            && !response.body().equals("003")
//                            && !response.body().equals("004")
//                            && !response.body().equals("error")) {
//                        sessionid = response.body();
//                        processGKNegosyoConsummation();
//                    } else {
//                        mLoaderDialog.dismiss();
//                        showError();
//                    }
//                } else {
//                    mLoaderDialog.dismiss();
//                    showError();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            mLoaderDialog.dismiss();
//            showError();
//        }
//    };

    private void createSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            processGKNegosyoConsummation();
            //processGKNegosyoConsummationv2();
        } else {
            mLoaderDialog.dismiss();
            showNoInternetToast();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm) {
            if (!mLoaderDialog.isShowing() || mLoaderDialog == null) {
                mLoaderDialog.show();
            }
            createSession();

        } else if (v.getId() == R.id.mLoaderDialogClose || v.getId() == R.id.mLoaderDialogRetry || v.getId() == R.id.cancelbtncontainer) {
            mLoaderDialog.dismiss();
            CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
            Intent intent = new Intent(getViewContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            CommonVariables.VOUCHERISFIRSTLOAD = true;
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setupLoaderDialog() {
        mLoaderDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_custom_animated, true)
                .build();

        View view = mLoaderDialog.getCustomView();
        if (view != null) {
            mLoaderDialogMessage = view.findViewById(R.id.mLoaderDialogMessage);
            mLoaderDialogTitle = view.findViewById(R.id.mLoaderDialogTitle);
            mLoaderDialogImage = view.findViewById(R.id.mLoaderDialogImage);
            mLoaderDialogClose = view.findViewById(R.id.mLoaderDialogClose);
            mLoaderDialogClose.setOnClickListener(this);
            cancelbtncontainer = view.findViewById(R.id.cancelbtncontainer);
            cancelbtncontainer.setOnClickListener(this);
            cancelbtncontainer.setVisibility(View.GONE);
            mLoaderDialogRetry = view.findViewById(R.id.mLoaderDialogRetry);
            mLoaderDialogRetry.setVisibility(View.GONE);
            mLoaderDialogRetry.setOnClickListener(this);
            buttonLayout = view.findViewById(R.id.buttonLayout);
            buttonLayout.setVisibility(View.GONE);

            mLoaderDialogTitle.setText("Processing...");

            Glide.with(getViewContext())
                    .load(R.drawable.progressloader)
                    .into(mLoaderDialogImage);
        }
    }

    private String checkAddress(String data) {
        try {
            String str = ".";
            if (data.isEmpty())
                return str;
            else
                return data;
        } catch (Exception e) {
            e.printStackTrace();
            return ".";
        }
    }

    private void processGKNegosyoConsummation() {
        Call<GenericResponse> call = RetrofitBuild.getGKNegosyoAPIService(getViewContext())
                .processGKNegosyoConsummation(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        sessionid,
                        mGKService.getGuarantorID(),
                        mGKNegosyoPackage.getPackageID(),
                        strVoucherSession,
                        mGKService.getMerchantID(),
                        mGKService.getServiceCode(),
                        mGKNegosyoApplicationStatus.getRegistrationID(),
                        checkAddress(mAddress.getLocality() == null ? "." : mAddress.getLocality()),
                        checkAddress(mAddress.getThoroughfare() == null ? "." : mAddress.getThoroughfare()),
                        checkAddress(mAddress.getAdminArea() == null ? mAddress.getSubAdminArea() : mAddress.getAdminArea()),
                        mAddress.getLatitude(),
                        mAddress.getLongitude(),
                        strDistributor
                );

        call.enqueue(processGKNegosyoConsummationCallback);
    }

    private Callback<GenericResponse> processGKNegosyoConsummationCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        buttonLayout.setVisibility(View.VISIBLE);
                        mLoaderDialogRetry.setVisibility(View.GONE);
                        mLoaderDialogTitle.setText("SUCCESSFUL TRANSACTION");
                        mLoaderDialogMessage.setText(response.body().getMessage());
                        mLoaderDialogImage.setVisibility(View.GONE);
                        mLoaderDialogClose.setVisibility(View.VISIBLE);
                        cancelbtncontainer.setVisibility(View.VISIBLE);
                        PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_IS_SUBS_RESELLER, true);
                    }else {
                        mLoaderDialog.dismiss();
                        showError(response.body().getMessage());
                    }
                } else {
                    mLoaderDialog.dismiss();
                    showError();
                }
            } catch (Exception e) {
                mLoaderDialog.dismiss();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLoaderDialog.dismiss();
            showError();
        }
    };

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * **/

    private void processGKNegosyoConsummationv2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("vouchersessionid", strVoucherSession);
            parameters.put("merchantid",  mGKService.getMerchantID());
            parameters.put("registrationid", mGKNegosyoApplicationStatus.getRegistrationID());
            parameters.put("guarantorid",  mGKService.getGuarantorID());
            parameters.put("servicecode", mGKService.getServiceCode());


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "processGKNegosyoConsummationv2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            processGKNegosyoConsummationv2Object(processGKNegosyoConsummationv2Callback);

        }else{
            mLoaderDialog.dismiss();
            showNoInternetToast();
        }
    }

    private void processGKNegosyoConsummationv2Object(Callback<GenericResponse> callback) {
        Call<GenericResponse> call = RetrofitBuilder.getGkNegosyoV2API(getViewContext())
                .processGKNegosyoConsummationv2(
                       authenticationid,sessionid,param
                );

        call.enqueue(callback);
    }

    private Callback<GenericResponse> processGKNegosyoConsummationv2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        buttonLayout.setVisibility(View.VISIBLE);
                        mLoaderDialogRetry.setVisibility(View.GONE);
                        mLoaderDialogTitle.setText("SUCCESSFUL TRANSACTION");
                        mLoaderDialogMessage.setText(message);
                        mLoaderDialogImage.setVisibility(View.GONE);
                        mLoaderDialogClose.setVisibility(View.VISIBLE);
                        cancelbtncontainer.setVisibility(View.VISIBLE);
                        PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_IS_SUBS_RESELLER, true);
                    } else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                        mLoaderDialog.dismiss();
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
                        mLoaderDialog.dismiss();
                        showErrorGlobalDialogs(message);
                    }
                } else {
                    mLoaderDialog.dismiss();
                    showErrorGlobalDialogs();
                }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLoaderDialog.dismiss();
            showErrorGlobalDialogs();
        }
    };

}
