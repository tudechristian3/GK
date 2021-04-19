package com.goodkredit.myapplication.activities.petron;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Petron;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetronValueCardActivity extends BaseActivity implements View.OnClickListener {
    private EditText edtPetronValueCardNumber;
    private EditText edtAmount;

    private Button btnProceed;

    private DatabaseHandler mdb;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String guarantorid;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private Petron petron;

    //@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petron_value_card);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        setupToolbar();
        setTitle("PETRON Value Card");

        init();

        initData();
    }

    public void init() {
        edtPetronValueCardNumber = (EditText) findViewById(R.id.edtPetronValueCardNumber);
        edtAmount = (EditText) findViewById(R.id.edtAmount);
        btnProceed = (Button) findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(this);

        nointernetconnection = (RelativeLayout) findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //loader
        mLlLoader = (RelativeLayout) findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) findViewById(R.id.fetching);
        mTvWait = (TextView) findViewById(R.id.wait);

    }

    private void initData(){
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        guarantorid = mdb.getGuarantorID(mdb);

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };
    }

    public static void start(Context context, int index, Bundle args) {
        Intent intent = new Intent(context, PetronValueCardActivity.class);
        intent.putExtra("args", args);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Processing request.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                    prePurchase(prePurchaseSession);

        } else {
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }

    }

//    private void prePurchase(Callback<String> prePurchaseCallback) {
//
//        Call<String> prepurchase = RetrofitBuild.prePurchaseService(getViewContext())
//                .prePurchaseCall(borrowerid,
//                        String.valueOf(paramountQueue.getAmountPaid()),
//                        userid,
//                        imei,
//                        sessionid,
//                        authcode);
//        prepurchase.enqueue(prePurchaseCallback);
//    }
//
//    private final Callback<String> prePurchaseSession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            ResponseBody errorBody = response.errorBody();
//            if (errorBody == null) {
//
//                String responseData = response.body().toString();
//
//                if (!responseData.isEmpty()) {
//                    if (responseData.equals("001")) {
//                        CommonFunctions.hideDialog();
//                        Toast.makeText(getViewContext(), "Session: Invalid session.", Toast.LENGTH_LONG).show();
//                    } else if (responseData.equals("error")) {
//                        CommonFunctions.hideDialog();
//                        Toast.makeText(getViewContext(), "Session: Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
//                    } else if (responseData.contains("<!DOCTYPE html>")) {
//                        CommonFunctions.hideDialog();
//                        Toast.makeText(getViewContext(), "Session: Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
//                    } else {
//                        if (responseData.length() > 0) {
//
//                            Logger.debug("antonhttp", "AMOUNT PAID: " + String.valueOf(petron.getTotalAmount()));
//
//                            Intent intent = new Intent(getViewContext(), Payment.class);
//                            intent.putExtra("AMOUNT", String.valueOf(petron.getTotalAmount()));
//                            intent.putExtra("PETRONQUEUE", "true");
//                            intent.putExtra("VOUCHERSESSION", responseData);
//                            startActivity(intent);
//
//                        } else {
//                            showError("Invalid Voucher Session.");
//                        }
//                    }
//                } else {
//                    CommonFunctions.hideDialog();
//                    showNoInternetConnection(true);
//                    Toast.makeText(getViewContext(), "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            CommonFunctions.hideDialog();
//            Toast.makeText(getViewContext(), "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
//        }
//    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProceed: {

                if (edtPetronValueCardNumber.getText().toString().length() > 0 &&
                        edtAmount.getText().toString().length() > 0 ) {

                    if(Double.parseDouble(edtAmount.getText().toString()) > 0){

                        getSession();

                    } else {

                        showError("Invalid Amount");

                    }

                } else {

                    showError("Please input all required fields.");

                }


                break;
            }
            case R.id.refreshnointernet: {

                break;
            }
        }
    }


}
