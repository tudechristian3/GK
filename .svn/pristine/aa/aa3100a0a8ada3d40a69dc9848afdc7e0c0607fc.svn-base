package com.goodkredit.myapplication.activities.gkearn.stockist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gkearn.GKEarnHomeActivity;
import com.goodkredit.myapplication.activities.gkearn.GKEarnPaymentOptionsActivity;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniBillingReferenceActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.gkearn.GKEarnStockistPackage;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKEarnStockistPackagesDetailsActivity extends BaseActivity implements View.OnClickListener {

    public static final String GKEARN_STOCKISTPACKAGEDETAILS = "GKEarnStockistPackagesDetailsActivity";

    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String borrowername = "";
    private String borroweremail = "";
    private String borrowermobileno = "";

    private String servicecode = "";
    private String merchantname = "";

    private GKService gkService;

    private NestedScrollView home_body;

    //PACKAGE
    public static String KEY_GKEARN_STOCKISTPACKAGE;

    private ImageView imv_btn_close_image;
    private TextView txv_packages_lbl;
    private TextView txv_packages_name;
    private TextView txv_packages_description;
    private LinearLayout btn_package_container;
    private TextView txv_package;

    private GKEarnStockistPackage gkEarnStockistPackage;

    public static final String KEY_GKEARN_VALUE_PACKAGE = "FROMPACKAGE";

    //PENDING REQUEST
    private TextView txv_pending_content;
    private RelativeLayout layout_pending_req_via_payment_channel;
    private LinearLayout layout_pending_paynow;
    private LinearLayout layout_pending_cancelrequest;
    private TextView btn_pending_paynow;
    private TextView btn_pending_cancelrequest;
    private ImageView btn_pending_close;
    private String billingid = "";

    //PAYMENT TYPE
    private String paymenttype = "";


    //CANCEL DIALOG
    private EditText dialogEdt;
    private String dialogString = "";
    private String remarks = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gkearn_stockist_packages_details);

            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() {
        home_body = (NestedScrollView) findViewById(R.id.home_body);

        imv_btn_close_image = findViewById(R.id.imv_btn_close_image);
        imv_btn_close_image.setOnClickListener(this);
        txv_packages_lbl = findViewById(R.id.txv_packages_lbl);
        txv_packages_lbl.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
        txv_packages_name = findViewById(R.id.txv_packages_name);
        txv_packages_name.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
        txv_packages_description = findViewById(R.id.txv_packages_description);
        btn_package_container = findViewById(R.id.btn_package_container);
        btn_package_container.setOnClickListener(this);
        txv_package = findViewById(R.id.txv_package);

        //PENDING REQUEST
        txv_pending_content = (TextView) findViewById(R.id.txv_content);
        layout_pending_req_via_payment_channel = (RelativeLayout) findViewById(R.id.layout_req_via_payment_channel);
        layout_pending_paynow = (LinearLayout) findViewById(R.id.layout_payment_channel_paynow);
        layout_pending_cancelrequest = (LinearLayout) findViewById(R.id.layout_payment_channel_cancel);
        btn_pending_cancelrequest = (TextView) findViewById(R.id.btn_cancel_request);
        btn_pending_paynow = (TextView) findViewById(R.id.btn_paynow_request);
        btn_pending_close = (ImageView) findViewById(R.id.btn_close);
        btn_pending_paynow.setOnClickListener(this);
        btn_pending_close.setOnClickListener(this);
        btn_pending_cancelrequest.setOnClickListener(this);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

        gkService = PreferenceUtils.getGKServicesPreference(getViewContext(), "GKSERVICE_OBJECT");

        Bundle args = new Bundle();
        args = getIntent().getBundleExtra("args");


        gkEarnStockistPackage = args.getParcelable(KEY_GKEARN_STOCKISTPACKAGE);
        if (gkEarnStockistPackage != null) {
            Double totalprice = gkEarnStockistPackage.getPrice() + gkEarnStockistPackage.getServiceCharge();
            txv_packages_name.setText(CommonFunctions.pointsFormatter(String.valueOf(totalprice)));
            txv_packages_description.setText(gkEarnStockistPackage.getPackageDescription());
        }
    }

    //---------------------------------------API----------------------------------
    private void callMainAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Processing payment request", "Please wait...");
            validateIfEarnHasPendingPaymentRequest();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //VALIDATE IF EARN HAS PENDING PAYMENT REQUEST
    private void validateIfEarnHasPendingPaymentRequest() {
        Call<GenericResponse> validateIfEarnHasPendingPaymentRequest = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .validateIfEarnHasPendingPaymentRequest(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        "APPLY AS STOCKIST",
                        CommonVariables.devicetype
                );

        validateIfEarnHasPendingPaymentRequest.enqueue(validateIfEarnHasPendingPaymentRequestCallBack);
    }

    private final Callback<GenericResponse> validateIfEarnHasPendingPaymentRequestCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        proceedtoPaymentOption();
                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else if (response.body().getStatus().equals("105")) {
                        String data = response.body().getData();
                        if (data != null) {
                            if (!data.equals("")) {
                                String registrationID = CommonFunctions.parseJSON(data, "MerchantReferenceNo");
                                String receiveamount = CommonFunctions.parseJSON(data, "Amount");
                                paymenttype =  CommonFunctions.parseJSON(data, "PaymentType");

                                showPendingLayout(data, registrationID, receiveamount);

                            } else {
                                proceedtoPaymentOption();
                            }
                        } else {
                            proceedtoPaymentOption();
                        }
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                hideProgressDialog();
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            hideProgressDialog();
        }
    };

    private void showPendingLayout(String data, String txnno, String amount) {

        billingid = txnno;

        if (data != null) {
            if (!data.equals("")) {
                if (txnno.substring(0, 3).equals("771") || txnno.substring(0, 3).equals("991")
                        || txnno.substring(0, 3).equals("551")) {
                    layout_pending_paynow.setVisibility(View.GONE);
                    txv_pending_content.setText("You have a pending payment transaction. If you think you completed this transaction, please contact support,if not just cancel the request.");

                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                    );
                    layout_pending_cancelrequest.setLayoutParams(param);

                } else {
                    if (paymenttype.contains("PARTNER")) {
                        layout_pending_paynow.setVisibility(View.VISIBLE);
                        txv_pending_content.setText("You have a pending payment request, kindly settle it first or cancel your previous request.");
                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f
                        );
                        layout_pending_cancelrequest.setLayoutParams(param);
                    } else {
                        layout_pending_paynow.setVisibility(View.GONE);
                        txv_pending_content.setText("You have a pending payment transaction. If you think you completed his transaction, please contact support,if not just cancel the request.");

                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                        );
                        layout_pending_cancelrequest.setLayoutParams(param);
                    }
                }

                layout_pending_req_via_payment_channel.setVisibility(View.VISIBLE);
            } else {
                layout_pending_req_via_payment_channel.setVisibility(View.GONE);
            }
        }

        btn_pending_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymenttype.contains("PARTNER")) {
                    layout_pending_req_via_payment_channel.setVisibility(View.GONE);
                    PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, gkService.getServiceName());
                    SchoolMiniBillingReferenceActivity.start(getViewContext(), new SchoolMiniPayment(".",
                            txnno, Double.valueOf(amount)), String.valueOf(amount), GKEARN_STOCKISTPACKAGEDETAILS);
                }

            }

        });
    }

    private void proceedtoPaymentOption() {
        PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_GKEARNSELECTEDPACKAGE);
        PreferenceUtils.saveGKEarnSelectedPackagePreference(getViewContext(),
                PreferenceUtils.KEY_GKEARNSELECTEDPACKAGE, gkEarnStockistPackage);
        Bundle args = new Bundle();
        args.putString(GKEarnHomeActivity.GKEARN_KEY_FROM, KEY_GKEARN_VALUE_PACKAGE);
        GKEarnPaymentOptionsActivity.start(getViewContext(), args);
    }
    //CANCELLATION
    private void cancelDialog() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "",
                "Cancel", "OK", ButtonTypeEnum.DOUBLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //TEXTVIEW
        LinearLayout textViewMainContainer = dialog.getTextViewMessageContainer();
        textViewMainContainer.setPadding(15, 15, 15, 0);

        List<String> declineList = new ArrayList<>();
        declineList.add("Reason for Cancellation:");

        LinearLayout textViewContainer = dialog.setContentTextView(declineList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.CENTER));

        //EDITTEXT
        LinearLayout editTextMainContainer = dialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(15, 0, 15, 15);

        List<String> editTextDataType = new ArrayList<>();
        editTextDataType.add(String.valueOf(GlobalDialogsEditText.CUSTOMVARCHAR));

        LinearLayout editTextContainer = dialog.setContentEditText(editTextDataType,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.CENTER,
                        R.drawable.border, 300, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;
                editItem.setScroller(new Scroller(getViewContext()));
                editItem.setSingleLine(false);

                editItem.setLines(5);
                editItem.setMinLines(3);
                editItem.setMaxLines(5);
                editItem.setVerticalScrollBarEnabled(true);
                editItem.setMovementMethod(new ScrollingMovementMethod());

                editItem.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                        }

                        return false;
                    }
                });
                editItem.setBackgroundResource(R.drawable.border);
                String taggroup = editItem.getTag().toString();
                if (taggroup.equals("TAG 0")) {
                    dialogEdt = editItem;
                    dialogEdt.addTextChangedListener(dialogTextWatcher);
                }
            }
        }

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View dblbtnone = dialog.btnDoubleOne();
        dblbtnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View dblbtntwo = dialog.btnDoubleTwo();
        dblbtntwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialogEdt.getText().toString().equals("")) {
                    dialog.dismiss();
                    remarks = dialogString;
                    cancelRequest();
                } else {
                    showToast("Reason for cancellation is required.", GlobalToastEnum.WARNING);
                }
            }
        });
    }

    //OTHERS
    private TextWatcher dialogTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            dialogString = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void cancelRequest(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            showProgressDialog(getViewContext(), "Processing Request", "Please wait...");
            cancelEarnRequest();
        } else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //VALIDATE IF EARN HAS PENDING PAYMENT REQUEST
    private void cancelEarnRequest() {
        Call<GenericResponse> cancelEarnRequest = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .cancelEarnRequest(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        billingid,
                        remarks,
                        GKEarnHomeActivity.GKEARN_VALUE_TYPE_APPLY_AS_STOCKIST,
                        CommonVariables.devicetype
                );

        cancelEarnRequest.enqueue(cancelEarnRequestCallBack);
    }

    private final Callback<GenericResponse> cancelEarnRequestCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        showCancellationSuccessfulDialog();
                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else {
                        showCancellationErrorDialog(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                hideProgressDialog();
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            hideProgressDialog();
        }
    };

    private void showCancellationSuccessfulDialog() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "Your request has been successfully cancelled.",
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.SUCCESS);

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                layout_pending_req_via_payment_channel.setVisibility(View.GONE);
            }
        });
    }

    private void showCancellationErrorDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", message,
                "OK", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    //-----------------------------OTHERS-------------------------------------
    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, GKEarnStockistPackagesDetailsActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_package_container: {
                callMainAPI();
                break;
            }

            case R.id.btn_close: {
                layout_pending_req_via_payment_channel.setVisibility(View.GONE);
                break;
            }

            case R.id.btn_cancel_request: {
                cancelDialog();
                break;
            }

            case R.id.imv_btn_close_image: {
                finish();
                break;
            }
        }
    }
}
