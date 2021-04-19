package com.goodkredit.myapplication.fragments.transactions;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.transactions.ConsumedDetailsRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.BillsPaymentLogs;
import com.goodkredit.myapplication.bean.Transaction;
import com.goodkredit.myapplication.bean.TransactionDetails;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.GenericObject;
import com.goodkredit.myapplication.responses.GenericObjectResponse;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.ConsummationTransactionDetailsResponse;
import com.goodkredit.myapplication.responses.SendEmailForBillsPaymentResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsummatedDetails extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private DatabaseHandler db;
    private CommonVariables cv;
    private CommonFunctions cf;
    private Context mcontext;

    private Intent gIntent;
    private Transaction transaction;

    private TextView merchantRefCode;
    private TextView dateTime;
    private TextView outletName;
    private TextView merchantName;

    private RecyclerView recycler_view_consumed;
    private ConsumedDetailsRecyclerAdapter adapter;
//    private ConsumedDetailsAdapter adapter;

    private ImageView branch_photo;
    private TextView merchantInitial;

    private String borrowerid = "";
    private String imei = "";
    //    private String sessionid = "";
    private String userid = "";
    private String merchantid = "";
    private String merchantreferenceno;

    private Calendar c;
    private int year;
    private int month;

    private int currentyear = 0;//make this not changeable for (filter condition)
    private int currentmonth = 0; //make this not changeable for (filter condition)

    private LinearLayout regularLayout;
    private LinearLayout prepaidLayout;
    private LinearLayout billsLayout;
    private LinearLayout discountLayout;
    private LinearLayout netamountLayout;

    private TextView transactionNo;
    private TextView mobileTarget;
    private TextView productCode;
    private TextView amount;
    private TextView prepaidDate;
    private TextView amountReg;
    private TextView discount;
    private TextView netamount;

    private TextView gkTransactionNumber;
    private TextView billerTransactionNo;
    private TextView biller;
    private TextView accountNo;
    private TextView accountName;
    private TextView amountPaid;
    private TextView billsDate;
    private TextView status;

    private List<TransactionDetails> arrayList;

    private Button btn_send_email;

    private String emailaddress = "";
    private String currency = "";
    private String transactionrefno = "";
    private String transactiondate = "";
    private String billername = "";
    private String amountpaid = "";
    private String accountno = "";
    private String accountname = "";
    private String billertransactionno = "";

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private MaterialDialog emailDialog;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private LinearLayout transactiondetailslabel;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumed_transaction);

        try {
            //set toolbar
            setupToolbar();

            mcontext = this;

            transactiondetailslabel = findViewById(R.id.transactiondetailslabel);

            db = new DatabaseHandler(this);

            //get account information
            imei = PreferenceUtils.getImeiId(getViewContext());
            userid = PreferenceUtils.getUserId(getViewContext());
            borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

            //UNIFIED SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            gIntent = getIntent();
            transaction = (Transaction) gIntent.getSerializableExtra("TRANSACTION_OBJECT");

            Logger.debug("okhttp","TRANSACTION_OBJECT: "+new Gson().toJson(transaction));

            merchantid = transaction.getMerchantID();
            merchantreferenceno = transaction.getRefcode();

            //initialize date
            //year = Calendar.getInstance().get(Calendar.YEAR);
            //month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            year = Integer.parseInt(CommonFunctions.getYearFromDate(transaction.getDateCompleted()));
            month = Integer.parseInt(CommonFunctions.getMonthFromDate(transaction.getDateCompleted()));
            currentyear = Calendar.getInstance().get(Calendar.YEAR);
            currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

            mSwipeRefreshLayout = findViewById(R.id.swipe_container);
            mSwipeRefreshLayout.setOnRefreshListener(this);

            mLlLoader = findViewById(R.id.loaderLayout);
            mTvFetching = findViewById(R.id.fetching);
            mTvWait = findViewById(R.id.wait);

            mLoaderTimer = new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    mLlLoader.setVisibility(View.GONE);
                }
            };

            btn_send_email = findViewById(R.id.btn_send_email);
            btn_send_email.setOnClickListener(this);
            btn_send_email.setVisibility(View.GONE);

            regularLayout = findViewById(R.id.transactionDetailsLinearLayout);
            prepaidLayout = findViewById(R.id.prepaidLoadLayout);
            billsLayout = findViewById(R.id.billsPayLayout);
            discountLayout = findViewById(R.id.discountLayout);
            netamountLayout = findViewById(R.id.netamountLayout);

            branch_photo = findViewById(R.id.branch_photo);

            //REGULAR
            merchantRefCode = findViewById(R.id.consumedMerchantRefCode);
            dateTime = findViewById(R.id.consumedDateTime);
            outletName = findViewById(R.id.consumedMerchantOutletName);
            merchantName = findViewById(R.id.consumedMerchantName);
            merchantInitial = findViewById(R.id.merchantDefaultLogo);
            amountReg = findViewById(R.id.amountReg);
            status = findViewById(R.id.status);

            //PREPAID
            transactionNo = findViewById(R.id.transactionNo);
            mobileTarget = findViewById(R.id.mobileTarget);
            productCode = findViewById(R.id.productCode);
            amount = findViewById(R.id.amount);
            prepaidDate = findViewById(R.id.prepaidDate);
            discount = findViewById(R.id.discount);
            netamount = findViewById(R.id.netamount);

            //BILLS
            gkTransactionNumber = findViewById(R.id.gkTransactionNumber);
            billerTransactionNo = findViewById(R.id.billerTransactionNo);
            biller = findViewById(R.id.biller);
            accountNo = findViewById(R.id.accountNo);
            accountName = findViewById(R.id.accountName);
            amountPaid = findViewById(R.id.amountPaid);
            billsDate = findViewById(R.id.billsDate);

            arrayList = new ArrayList<>();

            if (transaction.getMerchantLogo().contains("https")) {
                Glide.with(getViewContext())
                        .load(transaction.getMerchantLogo())
                        .apply(RequestOptions
                                .fitCenterTransform())
                        .into(branch_photo);
                merchantInitial.setVisibility(View.GONE);
            } else {
                branch_photo.setVisibility(View.GONE);
                merchantInitial.setVisibility(View.VISIBLE);
                String initials = transaction.getMerchatInitials();
                merchantInitial.setText(initials);
            }

            String isexist = db.isTxnDetailsExist(db, merchantreferenceno);

            Logger.debug("okhttp","ISEXIST: "+isexist);
            if (isexist != null) {
                transactiondetailslabel.setVisibility(View.VISIBLE);
                switch (isexist) {
                    case "BILLSPAYMENT":
                        billsLayout.setVisibility(View.VISIBLE);
                        btn_send_email.setVisibility(View.GONE);
                        setBillsLayout(db.getTransactionDetails(db, merchantreferenceno));
                        break;
                    case "AIRTIME":
                        discountLayout.setVisibility(View.GONE);
                        netamountLayout.setVisibility(View.GONE);
                        prepaidLayout.setVisibility(View.VISIBLE);
                        setPrepaidLayout(db.getTransactionDetails(db, merchantreferenceno));
                        break;
//                    case "SMARTLOADWALLET":
//                        discountLayout.setVisibility(View.VISIBLE);
//                        netamountLayout.setVisibility(View.VISIBLE);
//                        prepaidLayout.setVisibility(View.VISIBLE);
//                        setPrepaidLayout(db.getTransactionDetails(db, merchantreferenceno));
//                        break;
                    default:
                        regularLayout.setVisibility(View.VISIBLE);
                        setRegularLayout();
                        break;
                }

            } else {
                getSession();
            }

            recycler_view_consumed = findViewById(R.id.recycler_view_consumed_vouchers);
            recycler_view_consumed.setLayoutManager(new LinearLayoutManager(getViewContext()));
            recycler_view_consumed.setNestedScrollingEnabled(false);
            adapter = new ConsumedDetailsRecyclerAdapter(getViewContext(), getAllVouchersUsed(transaction.getRefcode(), merchantid));
            recycler_view_consumed.setAdapter(adapter);
            recycler_view_consumed.setLayoutFrozen(true);

            setTitle("Usage Details");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Fetching usage details.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getTransactionDetails(getTransactionDetailsSession);
            getTransactionDetailsV2();

        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showError(getString(R.string.generic_internet_error_message));
        }

    }

    public void setRegularLayout() {
        transactiondetailslabel.setVisibility(View.VISIBLE);

        regularLayout.setVisibility(View.VISIBLE);
        prepaidLayout.setVisibility(View.GONE);
        billsLayout.setVisibility(View.GONE);

        merchantRefCode.setText(transaction.getRefcode());
        outletName.setText(CommonFunctions.replaceEscapeData(transaction.getMerchantBranchName()));
        dateTime.setText(CommonFunctions.convertDate(transaction.getDateCompleted()));
        merchantName.setText(CommonFunctions.replaceEscapeData(transaction.getOutlet()));
        amountReg.setText(transaction.getAmount());
        status.setText(transaction.getStatus());
        if (transaction.getStatus().equals("VOID")) {
            status.setTextColor(getResources().getColor(R.color.colorred));
        } else {
            status.setTextColor(getResources().getColor(R.color.colortoolbar));
        }
    }

    public void setBillsLayout(List<TransactionDetails> details) {
        if (details.size() > 0) {
            transactiondetailslabel.setVisibility(View.VISIBLE);
            TransactionDetails item = details.get(0);

            regularLayout.setVisibility(View.GONE);
            prepaidLayout.setVisibility(View.GONE);
            billsLayout.setVisibility(View.VISIBLE);

            gkTransactionNumber.setText(item.getGKTransactionNo());
            billerTransactionNo.setText(item.getBillerTransactionNo());
            biller.setText(item.getBillerName());
            accountNo.setText(item.getAccountNo());
            accountName.setText(item.getAccountName());
            amountPaid.setText(CommonFunctions.currencyFormatter(String.valueOf(item.getTotalAmountPaid())));
            billsDate.setText(CommonFunctions.convertDate(item.getDateTimeCompleted()));
        } else {
            transactiondetailslabel.setVisibility(View.GONE);
        }
    }

    public void setPrepaidLayout(List<TransactionDetails> details) {
        if (details.size() > 0) {
            transactiondetailslabel.setVisibility(View.VISIBLE);
            TransactionDetails item = details.get(0);
            regularLayout.setVisibility(View.GONE);
            prepaidLayout.setVisibility(View.VISIBLE);
            billsLayout.setVisibility(View.GONE);

            discount.setText(CommonFunctions.currencyFormatter(String.valueOf(item.getDiscount())));
            netamount.setText(CommonFunctions.currencyFormatter(String.valueOf(item.getNetAmount())));
            transactionNo.setText(item.getTransactionNo());
            mobileTarget.setText(item.getMobileTarget());
            productCode.setText(item.getProductCode());
            amount.setText(CommonFunctions.currencyFormatter(String.valueOf(item.getAmount())));
            prepaidDate.setText(CommonFunctions.convertDate(item.getDateTimeCompleted()));
        } else {
            transactiondetailslabel.setVisibility(View.GONE);
            prepaidLayout.setVisibility(View.GONE);
            regularLayout.setVisibility(View.GONE);
        }
    }

//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                    getTransactionDetails(getTransactionDetailsSession);
//                }
//            } else {
//                mLlLoader.setVisibility(View.GONE);
//                mSwipeRefreshLayout.setRefreshing(false);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            mLlLoader.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setRefreshing(false);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getTransactionDetails(Callback<ConsummationTransactionDetailsResponse> getTransactionCallback) {
        Call<ConsummationTransactionDetailsResponse> getTransaction = RetrofitBuild.getTransactionDetailsService(getViewContext())
                .getTransactionDetailsCall(imei,
                        sessionid,
                        borrowerid,
                        userid,
                        authcode,
                        String.valueOf(year),
                        String.valueOf(month),
                        merchantid,
                        merchantreferenceno);
        getTransaction.enqueue(getTransactionCallback);
    }

    private final Callback<ConsummationTransactionDetailsResponse> getTransactionDetailsSession = new Callback<ConsummationTransactionDetailsResponse>() {

        @Override
        public void onResponse(Call<ConsummationTransactionDetailsResponse> call, Response<ConsummationTransactionDetailsResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    //db.truncateTable(db, DatabaseHandler.CONSUMMATIONTRANSACTIONDETAILS);

                    if (response.body().getType().equals("BILLSPAYMENT") ||
                            response.body().getType().equals("SMARTLOADWALLET") ||
                            response.body().getType().equals("AIRTIME")
                    ) {

                        List<TransactionDetails> txndetails = response.body().getTransaction();
                        for (TransactionDetails details : txndetails) {
                            db.insertConsummationTransactionDetails(db, details, response.body().getType());
                        }

                    } else {
                        db.insertConsummationTransactionDetails(db, new TransactionDetails(merchantreferenceno, null, null, 0, transaction.getDateCompleted(), null, null, null, null, null, 0, 0), "REGULAR");
                    }

                    switch (response.body().getType()) {
                        case "BILLSPAYMENT":
                            btn_send_email.setVisibility(View.GONE);
                            setBillsLayout(db.getTransactionDetails(db, merchantreferenceno));
                            break;
                        case "AIRTIME":
                            discountLayout.setVisibility(View.GONE);
                            netamountLayout.setVisibility(View.GONE);
                            setPrepaidLayout(db.getTransactionDetails(db, merchantreferenceno));
                            break;
                        case "SMARTLOADWALLET":
                            discountLayout.setVisibility(View.VISIBLE);
                            netamountLayout.setVisibility(View.VISIBLE);
                            setPrepaidLayout(db.getTransactionDetails(db, merchantreferenceno));
                            break;
                        default:
                            setRegularLayout();
                            break;
                    }

                }
            }
        }

        @Override
        public void onFailure(Call<ConsummationTransactionDetailsResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Transaction> getAllVouchersUsed(String merchantReferenceCode, String merchantid) {
        ArrayList<Transaction> arrayList = new ArrayList<>();
        Cursor mCur = db.getVouchersUsedTransaction(db, merchantReferenceCode, merchantid);
        while (mCur.moveToNext()) {
            String productID = mCur.getString(mCur.getColumnIndex("productid"));
            String voucherSerial = mCur.getString(mCur.getColumnIndex("voucherserial"));
            String voucherCode = mCur.getString(mCur.getColumnIndex("vouchercode"));
            String amountConsumed = mCur.getString(mCur.getColumnIndex("amountconsumed"));
            String voucherTxn = mCur.getString(mCur.getColumnIndex("transactionno"));
            String Extra3 = mCur.getString(mCur.getColumnIndex("Extra3"));
            String Status = mCur.getString(mCur.getColumnIndex("Status"));

            arrayList.add(new Transaction(voucherTxn, voucherSerial, voucherCode, amountConsumed, productID, "", "", Extra3, Status));
        }
        mCur.close();
        return arrayList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_email: {
                sendEmailForBillsPayment(db.getTransactionDetails(db, merchantreferenceno));
                break;
            }
        }
    }

    public void sendEmailForBillsPayment(List<TransactionDetails> transactions) {
        TransactionDetails item = transactions.get(0);
        emailaddress = db.getEmail(db);
        currency = db.getCurrencyID(db).equals("") ? "." : db.getCurrencyID(db);

        transactionrefno = item.getGKTransactionNo();
        transactiondate = item.getDateTimeCompleted();
        billername = item.getBillerName();
        amountpaid = String.valueOf(item.getAmountPaid());
        accountno = item.getAccountNo();
        accountname = item.getAccountName();
        billertransactionno = item.getBillerTransactionNo();

        emailDialog = new MaterialDialog.Builder(getViewContext())
                .autoDismiss(false)
                .cancelable(false)
                .title("Email")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Add email address", emailaddress, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        emailaddress = input.toString();
                    }
                })
                .positiveText("Send")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (!emailaddress.isEmpty()) {
                            getCreateSession();
                        } else {
                            showToast("Please input an email address", GlobalToastEnum.WARNING);
                        }
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, emailDialog.getView());
    }

    public void getCreateSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Sending email.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            CommonFunctions.showDialog(getViewContext(), "Sending email", "Please wait...", false);

//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callcreatesession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            sendEmailForPayment(sendEmailForBillsPaymentSession);
            //sendEmailForBillsPaymentV2();

        } else {
            mLlLoader.setVisibility(View.GONE);
            showError(getString(R.string.generic_internet_error_message));
        }
    }

//    private final Callback<String> callcreatesession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    CommonFunctions.hideDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    CommonFunctions.hideDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    mLlLoader.setVisibility(View.GONE);
//                    CommonFunctions.hideDialog();
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                    sendEmailForPayment(sendEmailForBillsPaymentSession);
//                }
//            } else {
//                CommonFunctions.hideDialog();
//                mLlLoader.setVisibility(View.GONE);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            mLlLoader.setVisibility(View.GONE);
//            CommonFunctions.hideDialog();
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void sendEmailForPayment(Callback<SendEmailForBillsPaymentResponse> sendEmailForBillsPaymentCallback) {
        Call<SendEmailForBillsPaymentResponse> sendemail = RetrofitBuild.sendEmailForBillsPaymentService(getViewContext())
                .sendEmailForBillsPaymentCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        transactionrefno,
                        transactiondate,
                        billername,
                        amountpaid,
                        accountno,
                        accountname,
                        emailaddress,
                        currency,
                        billertransactionno);
        sendemail.enqueue(sendEmailForBillsPaymentCallback);
    }

    private final Callback<SendEmailForBillsPaymentResponse> sendEmailForBillsPaymentSession = new Callback<SendEmailForBillsPaymentResponse>() {

        @Override
        public void onResponse(Call<SendEmailForBillsPaymentResponse> call, Response<SendEmailForBillsPaymentResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            CommonFunctions.hideDialog();
            if (errorBody == null) {

                evaluateResponse(response);

            } else {
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<SendEmailForBillsPaymentResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void evaluateResponse(Response<SendEmailForBillsPaymentResponse> response) {
        switch (response.body().getStatus()) {
            case "000": {
                emailDialog.dismiss();
                MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                        .title("Email")
                        .content("We successfully sent the transaction copy to " + emailaddress + ". Thank you.")
                        .positiveText("Close")
                        .show();
                break;
            }
            default: {
                MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                        .title("Email")
                        .content(response.body().getMessage())
                        .positiveText("Close")
                        .show();
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        if (db != null) {
            db.truncateTable(db, DatabaseHandler.CONSUMMATIONTRANSACTIONDETAILS);
        }
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        mSwipeRefreshLayout.setRefreshing(true);
        getSession();
    }


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     */

    //1.
    private void getTransactionDetailsV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("year", String.valueOf(year));
                parameters.put("month", String.valueOf(month));
                parameters.put("merchantid", merchantid);
                parameters.put("merchantreferenceno", merchantreferenceno);
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getConsummationTransactionDetailsV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getTransactionDetailsObjectV2(getTransactionDetailsV2Session);

            } else {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showError(getString(R.string.generic_internet_error_message));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTransactionDetailsObjectV2(Callback<GenericResponse> getTransactionCallback) {
        Call<GenericResponse> getTransaction = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getConsummationTransactionDetails(
                        authenticationid, sessionid, param
                );
        getTransaction.enqueue(getTransactionCallback);
    }

    private final Callback<GenericResponse> getTransactionDetailsV2Session = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                if (response.body().getStatus().equals("000")) {

                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    String decryptedtype = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getType());

                    Logger.debug("okhttp", "decryptedtype : " + decryptedtype);
                    Logger.debug("okhttp", "decrypteddata : " + decryptedData);

                    if (decryptedData.equals("error") || decryptedMessage.equals("error")) {
                        showErrorToast();
                    } else {

                        if (decryptedtype != null) {
                            if (decryptedtype.equals("BILLSPAYMENT") ||
                                    decryptedtype.equals("SMARTLOADWALLET") ||
                                    decryptedtype.equals("AIRTIME")) {

                                List<TransactionDetails> txndetails = new Gson().fromJson(decryptedData, new TypeToken<List<TransactionDetails>>() {
                                }.getType());
                                if(txndetails.size() > 0){
                                    for (TransactionDetails details : txndetails) {
                                        db.insertConsummationTransactionDetails(db, details, decryptedtype);
                                    }
                                }

                            } else {
                                db.insertConsummationTransactionDetails(db, new TransactionDetails(merchantreferenceno, null, null, 0, transaction.getDateCompleted(), null, null, null, null, null, 0, 0), "REGULAR");
                            }
                        }

                    }
                    switch (decryptedtype) {
                        case "BILLSPAYMENT":
                            btn_send_email.setVisibility(View.GONE);
                            setBillsLayout(db.getTransactionDetails(db, merchantreferenceno));
                            break;
                        case "AIRTIME":
                            discountLayout.setVisibility(View.GONE);
                            netamountLayout.setVisibility(View.GONE);
                            setPrepaidLayout(db.getTransactionDetails(db, merchantreferenceno));
                            break;
//                        case "SMARTLOADWALLET":
//                            discountLayout.setVisibility(View.VISIBLE);
//                            netamountLayout.setVisibility(View.VISIBLE);
//                            setPrepaidLayout(db.getTransactionDetails(db, merchantreferenceno));
//                            break;
                        default:
                            setRegularLayout();
                            break;
                    }

                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showErrorToast(decryptedMessage);
                    }
                }
            } else {
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    //2.
    private void sendEmailForBillsPaymentV2() {

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("transactionrefno", transactionrefno);
            parameters.put("transactiondate", transactiondate);
            parameters.put("billername", billername);
            parameters.put("amountpaid", amountpaid);
            parameters.put("accountno", accountno);
            parameters.put("accountname", accountname);
            parameters.put("emailaddress", emailaddress);
            parameters.put("currency", currency);
            parameters.put("billertransactionno", billertransactionno);


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "sendEmailForBillsPaymentV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

             sendEmailForBillsPaymentV2Object(sendEmailForBillsPaymentSessionV2);

        } else {
            showNoInternetToast();
            mLlLoader.setVisibility(View.GONE);
            CommonFunctions.hideDialog();

        }
    }

    private void sendEmailForBillsPaymentV2Object(Callback<GenericResponse> sendEmailForBillsPaymentCallback) {
        Call<GenericResponse> sendemail = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .sendEmailForBillsPaymentV2(
                        authenticationid,sessionid,param
                );
        sendemail.enqueue(sendEmailForBillsPaymentCallback);
    }

    private final Callback<GenericResponse> sendEmailForBillsPaymentSessionV2 = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            CommonFunctions.hideDialog();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                switch (response.body().getStatus()) {
                    case "000": {
                        emailDialog.dismiss();
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                        if(decryptedData.equals("error") ||  decryptedMessage.equals("error")){
                            showErrorGlobalDialogs();
                        }else{
                            new MaterialDialog.Builder(getViewContext())
                                    .title("Email")
                                    .content("We successfully sent the transaction copy to " + emailaddress + ". Thank you.")
                                    .positiveText("Close")
                                    .show();
                        }

                        break;
                    }
                    default: {
                         if(response.body().getStatus().equals("error")){
                             showErrorGlobalDialogs();
                         }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                             showAutoLogoutDialog(getString(R.string.logoutmessage));
                         }else{
                             new MaterialDialog.Builder(getViewContext())
                                     .title("Email")
                                     .content(decryptedMessage)
                                     .positiveText("Close")
                                     .show();
                         }
                        break;
                    }
                }

            } else {
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };


}
