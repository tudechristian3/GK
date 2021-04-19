package com.goodkredit.myapplication.activities.gkearn;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKEarnRegistrationConfirmationActivity extends BaseActivity {

    private TextView txv_subscribername;
    private TextView txv_amounttopay;
    private TextView txv_amounttendered;
    private TextView txv_change;

    private double amounttopay;
    private double amounttendered;
    private String subscribername;

    private DatabaseHandler mdb;

    private String sessionid;
    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;
    private String referralcode;
    private String servicetype;
    private String vouchersession;
    private String servicecode;
    private String merchantid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gkearn_registration_confirmation);

            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId() == R.id.confirm){
                getSession();

            } else if (item.getItemId() == android.R.id.home) {
                finish();
            }
            return super.onOptionsItemSelected(item);
        }

    private void init() {
        setupToolbarWithTitle("GK Earn");

        txv_subscribername = findViewById(R.id.txv_subscribername);
        txv_amounttopay = findViewById(R.id.txv_amounttopay);
        txv_amounttendered = findViewById(R.id.txv_amounttendered);
        txv_change = findViewById(R.id.txv_change);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        V2Subscriber v2Subscriber = mdb.getSubscriber(mdb);
        subscribername = v2Subscriber.getBorrowerName();

        sessionid = PreferenceUtils.getSessionID(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
        servicetype = "GKEARN";
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");

        //get passed value from payments
        Bundle b = getIntent().getExtras();

        try{

            if(b!=null){
                amounttendered = b.getDouble("amounttendered");
                amounttopay = b.getDouble("amounttopay");
                double change = amounttendered - amounttopay;

                vouchersession = b.getString("vouchersession");
                referralcode = b.getString("gkearnreferralcode");

                txv_amounttendered.setText(CommonFunctions.currencyFormatter(String.valueOf(amounttendered)));
                txv_amounttopay.setText(CommonFunctions.currencyFormatter(String.valueOf(amounttopay)));
                txv_change.setText(CommonFunctions.currencyFormatter(String.valueOf(change)));
                txv_subscribername.setText(CommonFunctions.replaceEscapeData(subscribername));

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getSession(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            showProgressDialog(getViewContext(), "Processing request.", "Please wait...");
            registerGKEarn(registerGKEarnSession);
        } else{
            showNoInternetToast();
            hideProgressDialog();
        }
    }
    private void registerGKEarn (retrofit2.Callback<GenericResponse> registerGKEarnCallback) {
        Call<GenericResponse> registergkearn = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .registerGKEarnCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        subscribername,
                        authcode,
                        referralcode,
                        servicetype,
                        String.valueOf(amounttopay),
                        vouchersession,
                        servicecode,
                        merchantid,
                        CommonVariables.devicetype);

        registergkearn.enqueue(registerGKEarnCallback);
    }

    private final retrofit2.Callback<GenericResponse> registerGKEarnSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            okhttp3.ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            try{
                if(errorBody == null) {
                    if(response.body().getStatus().equals("000")){
                        showConfirmSuccessDialog("You have successfully registered as GK Earn Subscriber!");
                    } else{
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else{
                    showErrorGlobalDialogs();
                }
            }catch (Exception e){
                e.printStackTrace();
                hideProgressDialog();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

    private void showConfirmSuccessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        boolean isGKNegosyoModal = false;

        dialog.createDialog("SUCCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                isGKNegosyoModal, false, DialogTypeEnum.SUCCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                Bundle args = new Bundle();
                args.putString(GKEarnHomeActivity.GKEARN_KEY_FROM, GKEarnHomeActivity.GKEARN_VALUE_FROMREGISTRATION);
                GKEarnHomeActivity.start(getViewContext(), GKEarnHomeActivity.GKEARN_FRAGMENT_INVITE_A_FRIEND, args);
            }
        });

    }
}
