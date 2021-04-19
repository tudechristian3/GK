package com.goodkredit.myapplication.activities.gkservices.medpadala;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Ban_Lenovo on 2/14/2018.
 */

public class MedPadalaActivity extends BaseActivity implements View.OnClickListener {

    public static final String GK_SERVICE = "gkservice_medpadal";

    private static final int RESULT_PICK_CONTACT = 850;

    private static final int MED_PADALA_SERVICE_CHARGE_SESSION = 123;
    private static final int VOUCHER_SESSION = 234;
    private int mID;

    private EditText edtMobileNumber;
    private EditText edtAmount;
    private String amount = "0";
    private String voucherSession = "";

    private Dialog dialog;

    private String mAmountToPay = "0";
    private String mServiceCharge = "0";

    private GKService mService;

    private SliderLayout mSlider;

    private ScrollView mScrollView;

    private String sessionid = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medpadala);
        init();
    }

    private void init() {
        setupToolbar();
        findViewById(R.id.btn_open_contacts).setOnClickListener(this);
        findViewById(R.id.btn_proceed).setOnClickListener(this);
        edtMobileNumber = (EditText) findViewById(R.id.edt_recipient_mobile);
        edtAmount = (EditText) findViewById(R.id.edt_amount);
        edtAmount.setOnClickListener(this);

        mService = getIntent().getParcelableExtra(GK_SERVICE);
        mSlider = (SliderLayout) findViewById(R.id.sliderLayout);
        setUpSlider();

        findViewById(R.id.btn_view_history).setOnClickListener(this);
        findViewById(R.id.btn_view_history).requestFocus();

        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mScrollView.fullScroll(View.FOCUS_UP);

        //COMMON
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
    }

    public static void start(Context context, GKService service) {
        Intent intent = new Intent(context, MedPadalaActivity.class);
        intent.putExtra(GK_SERVICE, service);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_medpadala, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.action_view_info) {
            showMoreInfoDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_contacts: {
                openContacts();
                break;
            }
            case R.id.btn_proceed: {
                if (isMobileNumberValid(edtMobileNumber.getText().toString())) {
                    if (isAmountValid()) {
                        try {
                            amount = edtAmount.getText().toString();
                            createSession(MED_PADALA_SERVICE_CHARGE_SESSION);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        showError("Amount is zero. Please check.");
                    }
                } else {
                    showMobileNumberInvalidDialog();
                }
                break;
            }
            case R.id.edt_amount: {
                hideSoftKeyboard();
                showAmountListDialog();
                break;
            }
            case R.id.btn_view_history: {
                MedPadalaTransactionHistoryActivity.start(getViewContext());
                break;
            }
        }
    }

    private boolean isMobileNumberValid(String str) {
        boolean bool = false;
        if (!str.isEmpty()) {
            if (str.length() == 10) {
                if (String.valueOf(str.charAt(0)).equals("9")) {
                    setNumber(str);
                    bool = true;
                }
            } else if (str.length() == 11) {
                if (str.substring(0, 2).equals("09")) {
                    setNumber(str);
                    bool = true;
                }
            } else if (str.length() == 12) {
                if (str.substring(0, 3).equals("639")) {
                    setNumber(str);
                    bool = true;
                }
            } else if (str.length() < 10) {
                showMobileNumberInvalidDialog();
                bool = false;
            }
        } else if (str.isEmpty()) {
            bool = false;
        }

        return bool;
    }

    private boolean isAmountValid() {
        if (edtAmount.getText().toString().isEmpty() || edtAmount.getText().toString().equals("0")) {
            return false;
        } else {
            return true;
        }
    }

    private void openPayment() {
        Intent intent = new Intent(getViewContext(), Payment.class);
        intent.putExtra("MEDPADALA", mService.getMerchantID());
        intent.putExtra("MOBILE", edtMobileNumber.getText().toString());
        intent.putExtra("AMOUNT", amount);
        intent.putExtra("AMOUNTTOPAY", mAmountToPay);
        intent.putExtra("SERVICECHARGE", mServiceCharge);
        intent.putExtra("VOUCHERSESSION", voucherSession);
        startActivity(intent);
    }

    private void openContacts() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        }
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
            if (isMobileNumberValid(CommonFunctions.userMobileNumber(phoneNo,true))) {
                setNumber(phoneNo);
            } else {
                showMobileNumberInvalidDialog();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNumber(String number) {
        edtMobileNumber.setText(CommonFunctions.userMobileNumber(number,true));
    }

//    private void createSession(int ID) {
//
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) == 0) {
//
//            showError("Seems that you are not connected to the internet. Please check your connection and try again. Thank you.");
//
//        } else {
//            mID = ID;
//            switch (ID) {
//                case MED_PADALA_SERVICE_CHARGE_SESSION: {
//                    showProgressDialog(getViewContext(), "Calculating service charge.", "Please wait.");
//                    createSession(createSessionCallback);
//                    break;
//                }
//                case VOUCHER_SESSION: {
//                    showProgressDialog(getViewContext(), "Getting voucher session.", "Please wait.");
//                    createSession(createSessionCallback);
//                    break;
//                }
//            }
//        }
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
//                        switch (mID) {
//                            case MED_PADALA_SERVICE_CHARGE_SESSION: {
//                                getMedPadalaServiceCharge();
//                                break;
//                            }
//                            case VOUCHER_SESSION: {
//                                getVoucherSession();
//                                break;
//                            }
//                        }
//                    } else {
//                        hideProgressDialog();
//                        showError();
//                    }
//                } else {
//                    hideProgressDialog();
//                    showError();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//            showError();
//        }
//    };

    private void createSession(int ID) {

        if (CommonFunctions.getConnectivityStatus(getViewContext()) == 0) {

            showNoInternetToast();

        } else {
            mID = ID;
            switch (ID) {
                case MED_PADALA_SERVICE_CHARGE_SESSION: {
                    showProgressDialog(getViewContext(), "Calculating service charge.", "Please wait.");
                    getMedPadalaServiceCharge();
                    break;
                }
                case VOUCHER_SESSION: {
                    showProgressDialog(getViewContext(), "Getting voucher session.", "Please wait.");
                    getVoucherSession();
                    break;
                }
            }
        }
    }

    private void getVoucherSession() {
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
        Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
                .getVoucherSession(borrowerid,
                        mAmountToPay,
                        userid,
                        imei,
                        sessionid,
                        authcode);
        call.enqueue(getVoucherSessionCallback);
    }

    private Callback<String> getVoucherSessionCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (!response.body().isEmpty()
                            && !response.body().contains("<!DOCTYPE html>")
                            && !response.body().equals("001")
                            && !response.body().equals("002")
                            && !response.body().equals("003")
                            && !response.body().equals("004")
                            && !response.body().equals("error")) {
                        voucherSession = response.body();
                        openPayment();
                    } else {
                        showError();
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

    private void getMedPadalaServiceCharge() {
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
        Call<GenericResponse> call = RetrofitBuild.getMedPadalaApiService(getViewContext())
                .getMedPadalaServiceCharge(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        amount);
        call.enqueue(getMedPadalaServiceChargeCallback);
    }

    private Callback<GenericResponse> getMedPadalaServiceChargeCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();

            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        mServiceCharge = response.body().getData();
                        showServiceChargeDialog(Double.valueOf(amount), Double.valueOf(response.body().getData()));
                    } else {
                        showError(response.body().getMessage());
                    }
                } else {
                    showError();
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

    private void showServiceChargeDialog(double amount, double servicecharge) {

        double totalamounttopay = 0;

        TextView popok;
        TextView popcancel;
        TextView popamountpaid;
        TextView popservicecharge;
        TextView popotherchargeval;
        TextView poptotalamount;
        TextView popmobileval;

        dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pop_billspayment_preconfirmation);

        dialog.findViewById(R.id.tblrow5).setVisibility(View.VISIBLE);

        dialog.findViewById(R.id.tblrow6).setVisibility(View.GONE);

        popamountpaid = (TextView) dialog.findViewById(R.id.popamounttopayval);
        popservicecharge = (TextView) dialog.findViewById(R.id.popservicechargeval);
        poptotalamount = (TextView) dialog.findViewById(R.id.poptotalval);
        popotherchargeval = (TextView) dialog.findViewById(R.id.popotherchargeval);
        popotherchargeval.setVisibility(View.GONE);
        popok = (TextView) dialog.findViewById(R.id.popok);
        popcancel = (TextView) dialog.findViewById(R.id.popcancel);
        popmobileval = (TextView) dialog.findViewById(R.id.popmobileval);

        //calculate
        totalamounttopay = amount + servicecharge;
        mAmountToPay = String.valueOf(totalamounttopay);

        //set value
        popamountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(amount)));
        popservicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(servicecharge)));
        poptotalamount.setText(CommonFunctions.currencyFormatter(String.valueOf(totalamounttopay)));
        popmobileval.setText(String.valueOf("+" + edtMobileNumber.getText()));

        dialog.show();

        //click close
        popcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });

        popok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                createSession(VOUCHER_SESSION);
            }

        });
    }

    private List<String> amountList() {
        List<String> amounts = new ArrayList<>();
        int min = 100;
        int max = 5000;
        int temp = 0;
        while (temp < max) {
            temp += min;
            amounts.add(String.valueOf(temp));
        }

        return amounts;
    }

    private void showAmountListDialog() {
        new MaterialDialog.Builder(getViewContext())
                .title("MEDPadala Amount")
                .items(amountList())
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        edtAmount.setText(text);
                        return true;
                    }
                })
                .positiveText("OK")
                .show();
    }


    private void setUpSlider() {
        try {
            int[] resIds = new int[]{
                    R.drawable.medpadala_banner1,
                    R.drawable.medpadala_banner2,
                    R.drawable.medpadala_banner3,
                    R.drawable.medpadala_banner4};

            mSlider.removeAllSliders();

            for (int resId : resIds) {
                DefaultSliderView defaultSliderView = new DefaultSliderView(getViewContext());
                defaultSliderView
                        .image(resId)
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                mSlider.addSlider(defaultSliderView);
            }

            final Handler scanBCHandler = new Handler();
            scanBCHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSlider.startAutoCycle();
                }
            }, 1500);

            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mSlider.setCustomAnimation(new DescriptionAnimation());
            mSlider.setDuration(4000);

            PagerIndicator indicator = mSlider.getPagerIndicator();
            indicator.setDefaultIndicatorColor(Color.parseColor("#191919"), Color.parseColor("#807F7F7F"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSlider != null) {
            mSlider.stopAutoCycle();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSlider != null) {
            setUpSlider();
        }
        hideSoftKeyboard();
    }

    private void showMoreInfoDialog() {
        new MaterialDialog.Builder(getViewContext())
                .title("What is MEDPadala?")
                .customView(R.layout.dialog_medpadala_more_info, true)
                .positiveText("Know More")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.medpadala.com.ph/"));
                        startActivity(intent);
                    }
                })
                .show();
    }

    private void showMobileNumberInvalidDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("Mobile number is invalid. Please check.")
                .positiveText("OK")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        edtMobileNumber.setText("639");
                    }
                })
                .show();
    }
}
