package com.goodkredit.myapplication.activities.vouchers.payoutone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniBillingReferenceActivity;
import com.goodkredit.myapplication.activities.vouchers.AvailableVoucherDetailsTransaction;
import com.goodkredit.myapplication.activities.vouchers.rfid.RFIDPaymentOptionsActvity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androidquery.util.AQUtility.getContext;

public class VoucherPayoutOneDetailsActivity extends BaseActivity implements View.OnClickListener {
    private DatabaseHandler db;

    private String sessionid;
    private String imei;
    private String authcode;
    private String borrowerid;
    private String userid;

    //VOUCHERS
    private Voucher voucher = null;
    private String vouchercode;
    private String voucherserialno;
    private String voucherpin;
    private String version;
    private String productid;
    private String voucherdenom;
    private String remainingbalance;
    private double voucherused;

    private TextView txv_regeneratepin;
    private TextView txv_viewtransactions;

    private ImageView img_cheque;
    private TextView txv_cheque_amount;

    private TextView txv_voucher_content_label;
    private TextView txv_voucher_content;

    //PAYOUTONE
    private LinearLayout linear_payoutone_container;
    private TextView txv_issuedby;
    private TextView txv_dateissued;
    private TextView txv_chequeno;
    private TextView txv_deposittobank;

    //RFID
    private LinearLayout linear_rfid_container;
    private TextView txv_rfid_number;
    private TextView txv_remainingbalance;

    private TextView txv_addbalance;

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

    public String index = "";
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private ImageView qrcodegenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_payoutone_details);

        setupToolbar();
        Toast.makeText(getApplicationContext(), "DETAILS", Toast.LENGTH_SHORT).show();
        init();

        initData();

        validateQRPartialV2();
    }

    private void init() {
        qrcodegenerate = findViewById(R.id.qrcodegenerate);
        img_cheque = findViewById(R.id.img_cheque);
        txv_cheque_amount = findViewById(R.id.txv_cheque_amount);
        txv_voucher_content_label = findViewById(R.id.txv_voucher_content_label);
        txv_voucher_content = findViewById(R.id.txv_voucher_content);

        txv_regeneratepin = findViewById(R.id.txv_regeneratepin);
        txv_viewtransactions = findViewById(R.id.txv_viewtransactions);

        txv_viewtransactions.setOnClickListener(this);

        //PAYOUTONE
        linear_payoutone_container = findViewById(R.id.linear_payoutone_container);
        txv_issuedby = findViewById(R.id.txv_issuedby);
        txv_dateissued = findViewById(R.id.txv_dateissued);
        txv_chequeno = findViewById(R.id.txv_chequeno);
        txv_deposittobank = findViewById(R.id.txv_deposittobank);
        txv_deposittobank.setOnClickListener(this);

        //RFID
        linear_rfid_container = findViewById(R.id.linear_rfid_container);
        txv_rfid_number = findViewById(R.id.txv_rfid_number);
        txv_addbalance = findViewById(R.id.txv_addtobalance);
        txv_addbalance.setOnClickListener(this);

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
        //COMMON
        db = new DatabaseHandler(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            voucher = bundle.getParcelable(Voucher.KEY_VOUCHER_OBJECT);
        }

        try {
            vouchercode = voucher.getVoucherCode();
            voucherserialno = voucher.getVoucherSerialNo();
            voucherpin = voucher.getVoucherPIN();
            version = CommonVariables.version;
            productid = voucher.getProductID();

//            voucherdenom = String.valueOf(voucher.getRemainingBalance());
//
//            if (voucher.getRemainingBalance() % 1 == 0) {
//                remainingbalance = String.valueOf((int) voucher.getRemainingBalance());
//            } else {
//                remainingbalance = String.valueOf((double) voucher.getRemainingBalance());
//            }
//
//            voucherused = Double.parseDouble(voucherdenom) - Double.parseDouble(remainingbalance);

            Glide.with(getViewContext())
                    .load(CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg")
                    .placeholder(R.drawable.generic_placeholder_gk_background)
                    .into(img_cheque);

            if (productid.contains("CHEQUE")) {
                setupToolbarWithTitle("Cheque Details");
                linear_payoutone_container.setVisibility(View.VISIBLE);
                txv_deposittobank.setVisibility(View.VISIBLE);

                txv_voucher_content_label.setText("PAY TO THE ORDER OF:");
                txv_voucher_content.setText(CommonFunctions.replaceEscapeData(voucher.getPayToTheOrderOf()));

                txv_dateissued.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(voucher.getDateTimeIssued())));
                txv_issuedby.setText(CommonFunctions.replaceEscapeData(voucher.getIssuedBy()));
                txv_chequeno.setText("CHEQUE-".concat(voucher.getVoucherSerialNo()));

            } else if (productid.contains("RFID")) {
                setupToolbarWithTitle("RFID Details");
                linear_rfid_container.setVisibility(View.VISIBLE);
                txv_addbalance.setVisibility(View.VISIBLE);
                txv_viewtransactions.setVisibility(View.VISIBLE);

                txv_voucher_content_label.setText("CARD#");
                txv_voucher_content_label.setTextColor(ContextCompat.getColor(getViewContext(), R.color.whitePrimary));
                txv_voucher_content_label.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));

                txv_voucher_content.setText(CommonFunctions.replaceEscapeData(CommonFunctions.addDashIntervals(voucher.getRFIDCardNumber(), 4)));
                txv_voucher_content.setTextColor(ContextCompat.getColor(getViewContext(), R.color.whitePrimary));
                txv_voucher_content.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));
                txv_rfid_number.setText(voucher.getRFIDNumber());
            } else {
                setupToolbarWithTitle("Voucher Details");
            }

            txv_cheque_amount.setText(CommonFunctions.currencyFormatter(String.valueOf(voucher.getRemainingBalance())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callAPI(String apirequest) {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Processing payment request", "Please wait...");
            if(apirequest != null) {
                if(apirequest.equalsIgnoreCase("RFID")) {
                    validateIfRFIDPendingPaymentRequest();
                }
            }
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //VALIDATE IF PENDING PAYMENT
    private void validateIfRFIDPendingPaymentRequest() {
        Call<GenericResponse> validateIfRFIDPendingPaymentRequest = RetrofitBuild.getRFIDAPIService(getViewContext())
                .validateIfRFIDPendingPaymentRequest(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        voucherserialno,
                        CommonVariables.devicetype
                );

        validateIfRFIDPendingPaymentRequest.enqueue(validateIfRFIDPendingPaymentRequestCallBack);
    }

    private final Callback<GenericResponse> validateIfRFIDPendingPaymentRequestCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                setUpProgressLoaderDismissDialog();
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
                                String txnno = CommonFunctions.parseJSON(data, "MerchantReferenceNo");
                                String amount = CommonFunctions.parseJSON(data, "Amount");
                                paymenttype = CommonFunctions.parseJSON(data, "PaymentType");

                                showPendingLayout(data, txnno, amount);

                            } else {
                                proceedtoPaymentOption();
                            }
                        } else {
                            showErrorGlobalDialogs();
                        }
                    } else {
                        showErrorGlobalDialogs();
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                setUpProgressLoaderDismissDialog();
                hideProgressDialog();
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showNoInternetToast();
            setUpProgressLoaderDismissDialog();
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
                    txv_pending_content.setText("You have a pending payment transaction. " +
                            "If you think you completed this transaction, " +
                            "please contact support,if not just cancel the request.");

                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                    );
                    layout_pending_cancelrequest.setLayoutParams(param);

                } else {
                    if (paymenttype.contains("PARTNER")) {
                        layout_pending_paynow.setVisibility(View.VISIBLE);
                        txv_pending_content.setText("You have a pending payment request, " +
                                "kindly settle it first or cancel your previous request.");
                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f
                        );
                        layout_pending_cancelrequest.setLayoutParams(param);
                    } else {
                        layout_pending_paynow.setVisibility(View.GONE);
                        txv_pending_content.setText("You have a pending payment transaction. " +
                                "If you think you completed this transaction," +
                                " please contact support,if not just cancel the request.");

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
                    PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, "RFID");
                    SchoolMiniBillingReferenceActivity.start(getViewContext(), new SchoolMiniPayment(".",
                            txnno, Double.valueOf(amount)), String.valueOf(amount), "");
                }

            }

        });
    }

    private void proceedtoPaymentOption() {
        Bundle args = new Bundle();
        args.putParcelable(Voucher.KEY_VOUCHER_OBJECT, voucher);
        RFIDPaymentOptionsActvity.start(getViewContext(), args);
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
            cancelRFIDRequest();
        } else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //VALIDATE IF PENDING PAYMENT
    private void cancelRFIDRequest() {
        Call<GenericResponse> cancelRFIDRequest = RetrofitBuild.getRFIDAPIService(getViewContext())
                .cancelRFIDRequest(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        voucherserialno,
                        billingid,
                        remarks,
                        CommonVariables.devicetype
                );

        cancelRFIDRequest.enqueue(cancelRFIDRequestCallBack);
    }

    private final Callback<GenericResponse> cancelRFIDRequestCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                setUpProgressLoaderDismissDialog();
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
                setUpProgressLoaderDismissDialog();
                hideProgressDialog();
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            setUpProgressLoaderDismissDialog();
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

    //OTHERS
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txv_viewtransactions: {                
                //view transaction of cheque voucher
                Intent intent = new Intent(getViewContext(), AvailableVoucherDetailsTransaction.class);
                intent.putExtra("SERIALNO", voucher.getVoucherSerialNo());
                intent.putExtra("RFIDCARDNUMBER", voucher.getRFIDCardNumber());
                startActivity(intent);
                break;
            }

            case R.id.txv_deposittobank: {
                //deposit cheque voucher to bank
                Intent intent = new Intent(getViewContext(), VoucherPayoutOneDepositToBankActivity.class);
                intent.putExtra(Voucher.KEY_VOUCHER_OBJECT, voucher);
                startActivity(intent);
                break;
            }

            case R.id.txv_addtobalance: {
                //ADD BALANCE TO RFID
                callAPI("RFID");
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

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private void getData() {
        sessionid = PreferenceUtils.getSessionID(getContext());
        imei = PreferenceUtils.getImeiId(getContext());
        userid = PreferenceUtils.getUserId(getContext());
        borrowerid = PreferenceUtils.getBorrowerId(getContext());
        session = PreferenceUtils.getSessionID(getContext());
    }

    private void validateQRPartialV2() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("devicetype", CommonVariables.devicetype);


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "generateBorrowersQRCode");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            String checker = new Gson().toJson(parameters);

            validateQRCode(validateQRCodeCallback);

        } else {
//            hideProgressDialog();
//            showNoInternetToast();
//            final Handler scanBCHandler = new Handler();
//            scanBCHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    bc.resume();
//                    scanQRCode();
//                }
//            }, 1000);
            Toast.makeText(getContext(), "qwe", Toast.LENGTH_SHORT).show();
        }
    }

    private void validateQRCode(Callback<GenericResponse> validateQRPartial) {
        Call<GenericResponse> call = RetrofitBuilder.getPayByQRCodeV2API(getContext())
                .generateBorrowersQRCode(
                        authenticationid,sessionid,param
                );
        call.enqueue(validateQRPartial);
    }

    private Callback<GenericResponse> validateQRCodeCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errBody = response.errorBody();
            if (errBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());

                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    QRCodeWriter writer = new QRCodeWriter();
                    try {
                        BitMatrix bitMatrix = writer.encode(decryptedData, BarcodeFormat.QR_CODE, 300, 300);
                        int width = bitMatrix.getWidth();
                        int height = bitMatrix.getHeight();

                        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                        for (int x = 0; x < width; x++) {
                            for (int y = 0; y < height; y++) {
                                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                            }
                        }
                        //progressCirular.setVisibility(View.GONE);
                        qrcodegenerate.setVisibility(View.VISIBLE);
                        qrcodegenerate.setImageBitmap(bmp);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                } else {
                    final Handler scanBCHandler = new Handler();
//                    scanBCHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            bc.resume();
//                            scanQRCode();
//                        }
//                    }, 1000);
//                    hideProgressDialog();
//                    showError(decryptedMessage);
                }
            } else {
                final Handler scanBCHandler = new Handler();
//                scanBCHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        bc.resume();
//                        scanQRCode();
//                    }
//                }, 1000);
//                hideProgressDialog();
//                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            final Handler scanBCHandler = new Handler();
//            scanBCHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    bc.resume();
//                    scanQRCode();
//                }
//            }, 1000);
//            hideProgressDialog();
//            showError();
            Toast.makeText(getContext(), "qwe", Toast.LENGTH_SHORT).show();
        }
    };
}