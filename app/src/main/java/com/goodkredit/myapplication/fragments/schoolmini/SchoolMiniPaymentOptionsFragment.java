package com.goodkredit.myapplication.fragments.schoolmini;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniBillingReferenceActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GetChargeResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniTuitionFee;
import com.goodkredit.myapplication.responses.DiscountResponse;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniPaymentResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolMiniPaymentOptionsFragment extends BaseFragment implements View.OnClickListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //MERCHANT
    private String merchantid = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //SCHOOL
    private String schoolid = "";
    private String studentid = "";
    private String course = "";
    private String yearlevel = "";
    private String firstname = "";
    private String middlename = "";
    private String lastname = "";
    private String mobilenumber = "";
    private String emailaddress = "";

    //TUITIONFEE
    private String tuitionfee = "";
    private List<SchoolMiniTuitionFee> schoolMiniTuitionFeeList = new ArrayList<>();
    private String schoolyear = "";
    private String semester = "";
    private String semestralfee = "";
    private String soaid = "";

    //PENDING REQUEST
    private TextView txv_content;
    private RelativeLayout layout_req_via_payment_channel;

    //AMOUNT
    private LinearLayout layout_amount_container;
    private EditText edt_amount;
    private double totalamount = 0.00;

    //PAYMENT OPTIONS
    private RadioGroup rg_payment_options;
    private RadioButton rb_channel;
    private RadioButton rb_voucher;
    private RadioButton rb_choosed_option;

    private String mPaymentType;

    //DISCOUNT RESELLER
    private double discount = 0.00;
    private String strdiscount = "";
    private String latitude = "";
    private String longitude = "";
    private String strtotalamount = "";
    private int hasdiscount = 0;
    private String discountmessage = "";

    private GPSTracker tracker;

    //GK NEGOSYO
    private String distributorid = "";
    private String resellerid = "";

    //PROCEED
    private TextView btn_proceed;
    private double totalamounttopay = 0.00;
    private String vouchersession = "";
    private String merchantreferenceno = "";

    private SchoolMiniPayment schoolminipayment;
    private double totalamountcheck = 0.00;

    //DISCOUNT DIALOG
    private MaterialDialog mMaterialDialog = null;
    private Dialog mDialog = null;

    //CUSTOMER SERVICE CHARGE
    private double customerservicecharge = 0.00;
    private String strcustomerservicecharge = "";

    //AMOUNT DETAILS
    private String amountdetails = "";

    //REMARKS
    private EditText edt_remarks;
    private String str_remarks = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmini_tuition_paymentoptions, container, false);
        try {
            init(view);
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        rg_payment_options = view.findViewById(R.id.rg_payment_options);
        rg_payment_options.setOnCheckedChangeListener(paymentOptionListener);

        rb_channel = view.findViewById(R.id.rb_channel);
        rb_voucher = view.findViewById(R.id.rb_voucher);

        edt_amount = view.findViewById(R.id.edt_amount);
        layout_amount_container = view.findViewById(R.id.layout_amount_container);

        edt_remarks = view.findViewById(R.id.edt_remarks);

        btn_proceed = view.findViewById(R.id.btn_proceed);
        btn_proceed.setOnClickListener(this);

        //PENDING REQUEST
        txv_content = view.findViewById(R.id.txv_content);
        layout_req_via_payment_channel = view.findViewById(R.id.layout_req_via_payment_channel);
        view.findViewById(R.id.btn_cancel_request).setOnClickListener(this);
        view.findViewById(R.id.btn_paynow_request).setOnClickListener(this);
        view.findViewById(R.id.btn_close).setOnClickListener(this);

    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mdb = new DatabaseHandler(getViewContext());

        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

        schoolid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCSERVICECODE);
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTID);

        if (getArguments() != null) {
            studentid = getArguments().getString("STUDENTID");
            course = getArguments().getString("COURSE");
            yearlevel = getArguments().getString("YEARLEVEL");
            firstname = getArguments().getString("FIRSTNAME");
            middlename = getArguments().getString("MIDDLENAME");
            lastname = getArguments().getString("LASTNAME");
            mobilenumber = getArguments().getString("MOBILENUMBER");
            emailaddress = getArguments().getString("EMAILADDRESS");
            schoolyear = getArguments().getString("SCHOOLYEAR");
            semester = getArguments().getString("SEMESTER");
            semestralfee = getArguments().getString("SEMESTRALFEE");
            soaid = getArguments().getString("SOAID");
        }
    }

    //IF BORROWER IS A RESELLER AND NOT IN SERVICE AREA.
    private void noDiscountResellerServiceArea() {
        mMaterialDialog = new MaterialDialog.Builder(getViewContext())
                .content(discountmessage)
                .cancelable(false)
                .positiveText("OK")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        //payStudentTuitionFee(payStudentTuitionFeeResponseCallBack);
                        hasdiscount = 0;
                        showChargeDialog();
                        mMaterialDialog.dismiss();
                        mMaterialDialog = null;
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        mMaterialDialog.dismiss();
                        mMaterialDialog = null;
                        CommonFunctions.hideDialog();

                    }
                })
                .show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, mMaterialDialog.getView());
    }

    //CHECKS THE LONG LAT FOR DISCOUNT
    private void checkGPSforDiscount() {
        Double currentlat = null;
        Double currentlon = null;

        tracker = new GPSTracker(getViewContext());

        if (!tracker.canGetLocation()) {
            currentlat = 0.0;
            currentlon = 0.0;
        } else {
            currentlat = tracker.getLatitude();
            currentlon = tracker.getLongitude();
        }
        //DISCOUNT LAT AND LONG
        latitude = String.valueOf(currentlat);
        longitude = String.valueOf(currentlon);
    }

    private void showChargeDialog() {

        TextView popok;
        TextView popcancel;
        TextView popamountpaid;
        TextView popservicecharge;
        TextView poptotalamount;

        //SERVICE CHARGE
        TableRow chargerow;
        TextView popcustomerchargelbl;
        TextView popcustomerchargeeval;


        if (mDialog == null) {
            mDialog = new Dialog(new ContextThemeWrapper(getViewContext(), android.R.style.Theme_Holo_Light));
            mDialog.setCancelable(false);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.pop_discountwithcharge_dialog);

            popamountpaid = mDialog.findViewById(R.id.popamounttopayval);
            popservicecharge = mDialog.findViewById(R.id.popservicechargeval);
            poptotalamount = mDialog.findViewById(R.id.poptotalval);
            popok = mDialog.findViewById(R.id.popok);
            popcancel = mDialog.findViewById(R.id.popcancel);

            //SERVICE CHARGE
            chargerow = mDialog.findViewById(R.id.chargerow);
            popcustomerchargelbl = mDialog.findViewById(R.id.popcustomerchargelbl);
            popcustomerchargeeval = mDialog.findViewById(R.id.popcustomerchargeeval);

            //set value
            popamountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(totalamounttopay)));
            popcustomerchargeeval.setText(CommonFunctions.currencyFormatter(String.valueOf(customerservicecharge)));
            popservicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(discount)));
            poptotalamount.setText(CommonFunctions.currencyFormatter(String.valueOf(strtotalamount)));

            //click close
            popcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mDialog = null;
                    hideProgressDialog();
                }

            });

            popok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonFunctions.hideDialog();
                    if (mPaymentType.contains("PARTNERS")) {
                        //SchoolMiniBillingReferenceActivity.start(getViewContext(), schoolminipayment, String.valueOf(totalamountcheck), "schoolMiniPaymentOptionsFragment");
                        //payStudentTuitionFee(payStudentTuitionFeeResponseCallBack);
                        proccedtoPayStudentTuitionFee();
                    } else {
                        //payStudentTuitionFee(payStudentTuitionFeeResponseCallBack);
                        //proceedtoPayments();
                        proccedtoPayStudentTuitionFee();
                    }
                    mDialog.dismiss();
                    mDialog = null;
                }

            });

            mDialog.show();
        }
    }

    private void showCancelRequestWarningDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("Are you sure you want to cancel your payment request?")
                .negativeText("Cancel")
                .positiveText("Proceed")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getCancelledSession();
                    }
                })
                .show();
    }

    private void showCancellationSuccessfulDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("You have cancelled your transaction.")
                .cancelable(false)
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        layout_req_via_payment_channel.setVisibility(View.GONE);
                    }
                })
                .show();
    }

    private void proccedtoPayStudentTuitionFee() {
        amountdetails = "<grossamount>" + totalamounttopay + "</grossamount>" + "\n" +
                        "<customersc>" + strcustomerservicecharge + "</customersc>" + "\n";
        //payStudentTuitionFee(payStudentTuitionFeeResponseCallBack);
        payStudentTuitionFeeV2();
    }

    //PROCEEDING TO PAYMENTS
    private void proceedtoPayments() {
        CommonFunctions.hideDialog();

        Intent intent = new Intent(getViewContext(), Payment.class);
        intent.putExtra("STUDENTID", studentid);
        intent.putExtra("COURSE", course);
        intent.putExtra("YEARLEVEL", yearlevel);
        intent.putExtra("FIRSTNAME", firstname);
        intent.putExtra("MIDDLENAME", middlename);
        intent.putExtra("LASTNAME", lastname);
        intent.putExtra("MOBILENUMBER", mobilenumber);
        intent.putExtra("EMAILADDRESS", emailaddress);
        intent.putExtra("SCHOOLYEAR", schoolyear);
        intent.putExtra("SEMESTER", semester);
        intent.putExtra("SEMESTRALFEE", semestralfee);
        intent.putExtra("SOAID", soaid);
        intent.putExtra("VOUCHERSESSION", vouchersession);
        intent.putExtra("AMOUNT", String.valueOf(totalamountcheck));
        intent.putExtra("DISCOUNT", strdiscount);
        intent.putExtra("MERCHANTREFERENCENO", merchantreferenceno);
        intent.putExtra("GROSSPRICE", String.valueOf(totalamounttopay));
        intent.putExtra("GKHASDISCOUNT", hasdiscount);
        intent.putExtra("GKMPAYMENTTYPE", mPaymentType);
        intent.putExtra("GKCUSTOMERSERVICECHARGE", strcustomerservicecharge);
        intent.putExtra("REMARKS", str_remarks);
        startActivity(intent);

    }

    //----------------API------------------
    //MAIN SESSION
//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            showProgressDialog("", "Please wait...");
//            createSession(getSessionCallback);
//        } else {
//            hideProgressDialog();
//            showToast("Seems you are not connected to the internet. Please check your connection and try again. Thank you.", GlobalToastEnum.WARNING);
//        }
//    }
//
//    private Callback<String> getSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            ResponseBody errBody = response.errorBody();
//
//            if (errBody == null) {
//                sessionid = response.body().toString();
//                authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                if (!sessionid.isEmpty()) {
//                    if (distributorid.equals("") || distributorid.equals(".")
//                            || resellerid.equals("") || resellerid.equals(".")) {
//                        if (latitude.equals("") || longitude.equals("")
//                                || latitude.equals("null") || longitude.equals("null")) {
//                            latitude = "0.0";
//                            longitude = "0.0";
//                        }
//                        calculateServiceCharge(calculateServiceChargeCallBack);
//                    } else {
//                        checkGPSforDiscount();
//                        if (latitude.equals("") || longitude.equals("")
//                                || latitude.equals("null") || longitude.equals("null")) {
//                            latitude = "0.0";
//                            longitude = "0.0";
//                        }
//                        calculateServiceCharge(calculateServiceChargeCallBack);
//                    }
//
////                    if (mPaymentType.contains("PARTNERS")) {
////                        strtotalamount = semestralfee;
////                        payStudentTuitionFee(payStudentTuitionFeeResponseCallBack);
////                    } else {
////                        if (distributorid.equals("") || distributorid.equals(".")
////                                || resellerid.equals("") || resellerid.equals(".")) {
////                            if (latitude.equals("") || longitude.equals("")
////                                    || latitude.equals("null") || longitude.equals("null")) {
////                                latitude = "0.0";
////                                longitude = "0.0";
////                            }
////                            calculateServiceCharge(calculateServiceChargeCallBack);
////                        } else {
////                            checkGPSforDiscount();
////                            if (latitude.equals("") || longitude.equals("")
////                                    || latitude.equals("null") || longitude.equals("null")) {
////                                latitude = "0.0";
////                                longitude = "0.0";
////                            }
////                            calculateServiceCharge(calculateServiceChargeCallBack);
////                        }
////                    }
//                } else {
//                    showErrorToast();
//                    hideProgressDialog();
//                }
//            } else {
//                showErrorToast();
//                hideProgressDialog();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//            hideProgressDialog();
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("", "Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            if (distributorid.equals("") || distributorid.equals(".")
                    || resellerid.equals("") || resellerid.equals(".")) {
                if (latitude.equals("") || longitude.equals("")
                        || latitude.equals("null") || longitude.equals("null")) {
                    latitude = "0.0";
                    longitude = "0.0";
                }
                //calculateServiceCharge(calculateServiceChargeCallBack);
                calculateServiceChargeV2();
            } else {
                checkGPSforDiscount();
                if (latitude.equals("") || longitude.equals("")
                        || latitude.equals("null") || longitude.equals("null")) {
                    latitude = "0.0";
                    longitude = "0.0";
                }
                //calculateServiceCharge(calculateServiceChargeCallBack);
                calculateServiceChargeV2();
            }
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //CALCULATE CHARGE
    private void calculateServiceCharge(Callback<GetChargeResponse> calculateServiceChargeCallBack) {
        //Limits the two decimal places
        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(totalamount));
        totalamounttopay = Double.parseDouble(valuecheck);

        Call<GetChargeResponse> calculateresponse = RetrofitBuild.getDiscountService(getViewContext())
                .calculateServiceCharge(
                        sessionid,
                        imei,
                        userid,
                        authcode,
                        borrowerid,
                        totalamounttopay,
                        schoolid,
                        merchantid
                );

        calculateresponse.enqueue(calculateServiceChargeCallBack);
    }

    private final Callback<GetChargeResponse> calculateServiceChargeCallBack =
            new Callback<GetChargeResponse>() {

                @Override
                public void onResponse(Call<GetChargeResponse> call, Response<GetChargeResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                customerservicecharge = response.body().getCharge();
                                calculateDiscountForReseller(calculateDiscountForResellerCallBack);
                            } else {
                                showError(response.body().getMessage());
                                hideProgressDialog();
                            }
                        } else {
                            showError();
                            hideProgressDialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideProgressDialog();
                    }
                }

                @Override
                public void onFailure(Call<GetChargeResponse> call, Throwable t) {
                    showError();
                    hideProgressDialog();
                    CommonFunctions.hideDialog();
                }
            };

    //CALCULATEDISCOUNTFORRESELLER
    private void calculateDiscountForReseller(Callback<DiscountResponse> calculateDiscountForResellerCallBack) {
        //Limits the two decimal places
        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(totalamount));
        totalamounttopay = Double.parseDouble(valuecheck);

        Call<DiscountResponse> discountresponse = RetrofitBuild.getDiscountService(getViewContext())
                .calculateDiscountForReseller(
                        userid,
                        imei,
                        authcode,
                        sessionid,
                        borrowerid,
                        merchantid,
                        totalamounttopay,
                        schoolid,
                        latitude,
                        longitude
                );

        discountresponse.enqueue(calculateDiscountForResellerCallBack);
    }

    private final Callback<DiscountResponse> calculateDiscountForResellerCallBack =
            new Callback<DiscountResponse>() {

                @Override
                public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                discount = response.body().getDiscount();

                                strdiscount = String.valueOf(discount);
                                strcustomerservicecharge = String.valueOf(customerservicecharge);

                                //SERVICE CHARGE AND DISCOUNT
                                if (discount <= 0) {
                                    strtotalamount = String.valueOf(totalamounttopay + customerservicecharge);
                                    hasdiscount = 0;
                                    showChargeDialog();
                                    //payStudentTuitionFee(payStudentTuitionFeeResponseCallBack);
                                } else {
                                    strtotalamount = String.valueOf((totalamounttopay + customerservicecharge - discount));
                                    if (Double.parseDouble(String.valueOf(totalamounttopay)) > 0) {
                                        hasdiscount = 1;
                                        showChargeDialog();
                                        //payStudentTuitionFee(payStudentTuitionFeeResponseCallBack);
                                    }
                                }
                            } else if (response.body().getStatus().equals("005")) {
                                discountmessage = response.body().getMessage();
                                discount = response.body().getDiscount();
                                strdiscount = String.valueOf(discount);
                                strtotalamount = String.valueOf(totalamounttopay);
                                noDiscountResellerServiceArea();
                            } else {
                                showError(response.body().getMessage());
                                hideProgressDialog();
                            }
                        } else {
                            showError();
                            hideProgressDialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<DiscountResponse> call, Throwable t) {
                    showError();
                    hideProgressDialog();
                    CommonFunctions.hideDialog();
                }
            };

    //PAYSTUDENTTUITIONFEE
    private void payStudentTuitionFee(Callback<SchoolMiniPaymentResponse> payStudentTuitionFeeResponseCallBack) {
        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(strtotalamount));
        strtotalamount = valuecheck;

        Call<SchoolMiniPaymentResponse> paystudenttuitionfee = RetrofitBuild.getSchoolAPIService(getViewContext())
                .payStudentTuitionFee(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        schoolid,
                        studentid,
                        soaid,
                        strtotalamount,
                        mPaymentType,
                        amountdetails,
                        str_remarks
                );

        paystudenttuitionfee.enqueue(payStudentTuitionFeeResponseCallBack);
    }

    private final Callback<SchoolMiniPaymentResponse> payStudentTuitionFeeResponseCallBack = new Callback<SchoolMiniPaymentResponse>() {
        @Override
        public void onResponse(Call<SchoolMiniPaymentResponse> call, Response<SchoolMiniPaymentResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {

                    String discountcheckifequal = String.valueOf(discount);
                    String discounttotalamounttopay = String.valueOf(totalamounttopay);

                    if (response.body().getStatus().equals("000")) {
                        layout_req_via_payment_channel.setVisibility(View.GONE);
                        schoolminipayment = response.body().getSchoolMiniPayment();
                        vouchersession = schoolminipayment.getVoucherSessionID();
                        merchantreferenceno = schoolminipayment.getMerchantReferenceNo();

                        if (mPaymentType.contains("PARTNERS")) {
                            totalamountcheck = Double.parseDouble(strtotalamount);
                            PreferenceUtils.saveBooleanPreference(getViewContext(), "schoolpaymentrequest", true);
                            SchoolMiniBillingReferenceActivity.start(getViewContext(), schoolminipayment, String.valueOf(totalamountcheck), "schoolMiniPaymentOptionsFragment");
                        } else {
                            if (!vouchersession.isEmpty()) {
                                if (vouchersession.equals("001")) {
                                    CommonFunctions.hideDialog();
                                    showToast("Session: Invalid sessionid.", GlobalToastEnum.NOTICE);
                                } else if (vouchersession.equals("error")) {
                                    CommonFunctions.hideDialog();
                                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                                } else if (vouchersession.contains("<!DOCTYPE html>")) {
                                    CommonFunctions.hideDialog();
                                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                                } else {
                                    if (vouchersession.length() > 0) {
                                        if (discounttotalamounttopay.equals(discountcheckifequal)) {
                                            showError("Invalid amount. Cannot Proceed.");
                                            hideProgressDialog();
                                        } else {
                                            totalamountcheck = Double.parseDouble(strtotalamount);
                                            proceedtoPayments();
//                                            if (totalamountcheck > 0) {
//                                                if (discount > 0) {
//                                                    hasdiscount = 1;
//                                                    showChargeDialog();
//                                                } else {
//                                                    hasdiscount = 0;
//                                                    showChargeDialog();
//                                                    //proceedtoPayments();
//                                                }
//                                            }
                                        }
                                    } else {
                                        showError("Invalid Voucher Session.");
                                        hideProgressDialog();
                                    }
                                }
                            } else {
                                CommonFunctions.hideDialog();
                                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                            }
                        }
                    } else if (response.body().getStatus().equals("006")) {
                        schoolminipayment = response.body().getSchoolMiniPayment();
                        totalamountcheck = schoolminipayment.getTotalAmount();
                        vouchersession = schoolminipayment.getVoucherSessionID();
                        merchantreferenceno = schoolminipayment.getMerchantReferenceNo();
                        txv_content.setText(response.body().getMessage());
                        layout_req_via_payment_channel.setVisibility(View.VISIBLE);
                    } else {
                        showErrorToast(response.body().getMessage());
                    }
                } else {
                    showErrorToast();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<SchoolMiniPaymentResponse> call, Throwable t) {
            showErrorToast();
            t.printStackTrace();
            t.getLocalizedMessage();
            hideProgressDialog();
        }
    };

    private void getCancelledSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("", "Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            cancelSchoolPaymentPartnerRequest(cancelSchoolPaymentPartnerRequestCallBack);
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //CANCELPAYMENTREQUEST
    private void cancelSchoolPaymentPartnerRequest(Callback<GenericResponse> payStudentTuitionFeeResponseCallBack) {
        Call<GenericResponse> paystudenttuitionfee = RetrofitBuild.getSchoolAPIService(getViewContext())
                .cancelSchoolPaymentPartnerRequest(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        schoolid,
                        soaid,
                        merchantreferenceno,
                        studentid
                );

        paystudenttuitionfee.enqueue(payStudentTuitionFeeResponseCallBack);
    }

    private final Callback<GenericResponse> cancelSchoolPaymentPartnerRequestCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        showCancellationSuccessfulDialog();
                    } else {
                        showErrorToast(response.body().getMessage());
                    }
                } else {
                    showErrorToast();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorToast();
            t.printStackTrace();
            t.getLocalizedMessage();
            hideProgressDialog();
        }
    };

    //---------------ALL LISTENERS, OVERRIDE AND START----------------------
    private RadioGroup.OnCheckedChangeListener paymentOptionListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

            switch (checkedId) {
                case R.id.rb_channel:
                    layout_amount_container.setVisibility(View.VISIBLE);
                    break;
                case R.id.rb_voucher:
                    layout_amount_container.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_proceed:
                try {
                    int selectedId = rg_payment_options.getCheckedRadioButtonId();
                    rb_choosed_option = getActivity().findViewById(selectedId);
                    String strChoosedOption = rb_choosed_option.getText().toString().trim().toUpperCase();

                    str_remarks = edt_remarks.getText().toString().trim();
                    if(str_remarks.equals("")){
                        str_remarks = ".";
                    }

                    if (!strChoosedOption.isEmpty()) {
                        if (strChoosedOption.contains("VOUCHER")) {
                            mPaymentType = "PAY VIA GK";
                            String checkamount = edt_amount.getText().toString();
                            if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                                showToast("Please input an amount to proceed", GlobalToastEnum.WARNING);
                            } else {
                                totalamount = Double.parseDouble(checkamount);
                                if(totalamount > 0) {
                                    getSession();
                                 } else {
                                    showToast("Invalid amount. Please try again.", GlobalToastEnum.WARNING);
                                }
                            }
                        } else {
                            mPaymentType = "PAY VIA PARTNERS";

                            String checkamount = edt_amount.getText().toString();
                            if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                                showToast("Please input an amount to proceed", GlobalToastEnum.WARNING);
                            } else {
                                totalamount = Double.parseDouble(checkamount);
                                if(totalamount > 0) {
                                    getSession();
                                } else {
                                    showToast("Invalid amount. Please try again.", GlobalToastEnum.WARNING);
                                }
                            }
                        }
                    } else {
                        showToast("Please choose a payment option", GlobalToastEnum.WARNING);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getLocalizedMessage();
                    e.getMessage();
                    String checkamount = edt_amount.getText().toString();
                    if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                        showToast("Please input an amount to proceed", GlobalToastEnum.WARNING);
                    } else {
                        showToast("Please choose a payment option", GlobalToastEnum.WARNING);
                    }
                }
                break;

            case R.id.btn_close: {
                layout_req_via_payment_channel.setVisibility(View.GONE);
                break;
            }

            case R.id.btn_cancel_request: {
                showCancelRequestWarningDialog();
                break;
            }
            case R.id.btn_paynow_request: {
                SchoolMiniBillingReferenceActivity.start(getViewContext(), schoolminipayment, String.valueOf(totalamountcheck), "schoolMiniPaymentOptionsFragment");
                break;
            }
        }
    }

    /*
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * */

    private void calculateServiceChargeV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(totalamount));
                totalamounttopay = Double.parseDouble(valuecheck);

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("amountpaid", String.valueOf(totalamounttopay));
                parameters.put("servicecode", schoolid);
                parameters.put("merchantid", merchantid);
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "calculateServiceChargeV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                calculateServiceChargeV2Object();

            } else {
                hideProgressDialog();
                showNoInternetToast();
            }

        } catch (Exception e) {
            e.printStackTrace();
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void calculateServiceChargeV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .calculateServiceChargeV2(authenticationid, sessionid, param);

        call.enqueue(calculateServiceChargeV2CallBack);
    }

    private Callback<GenericResponse> calculateServiceChargeV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();

            hideProgressDialog();

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    customerservicecharge = Double.parseDouble(data);

                    calculateDiscountForResellerV2();
                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            } else {
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showNoInternetToast();
        }
    };

    private void calculateDiscountForResellerV2() {
        try{
            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
                String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(totalamount));
                totalamounttopay = Double.parseDouble(valuecheck);

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("merchantid" ,merchantid);
                parameters.put("amountpaid", String.valueOf(totalamounttopay));
                parameters.put("servicecode", schoolid);
                parameters.put("longitude",longitude);
                parameters.put("latitude",latitude);
                parameters.put("devicetype",CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "calculateDiscountForResellerV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                calculateDiscountForResellerV2Object();
            } else{
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }
        }catch (Exception e){
            e.printStackTrace();
            CommonFunctions.hideDialog();
            showErrorToast();
        }
    }

    private void calculateDiscountForResellerV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .calculateDiscountForResellerV3(authenticationid,sessionid,param);
        call.enqueue(calculateDiscountForResellerV2CallBack);
    }

    private final Callback<GenericResponse> calculateDiscountForResellerV2CallBack = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());

                if (response.body().getStatus().equals("000")) {

                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    discount = Double.parseDouble(decryptedData);

                    strdiscount = String.valueOf(discount);
                    strcustomerservicecharge = String.valueOf(customerservicecharge);

                    //SERVICE CHARGE AND DISCOUNT
                    if (discount <= 0) {
                        strtotalamount = String.valueOf(totalamounttopay + customerservicecharge);
                        hasdiscount = 0;
                        showChargeDialog();
                    } else {
                        strtotalamount = String.valueOf((totalamounttopay + customerservicecharge - discount));
                        if (Double.parseDouble(String.valueOf(totalamounttopay)) > 0) {
                            hasdiscount = 1;
                            showChargeDialog();
                        }
                    }

                } else {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else if (response.body().getStatus().equals("005")) {
                        discount = Double.parseDouble(decryptedData);
                        discountmessage = decryptedMessage;
                        strdiscount = String.valueOf(discount);
                        strtotalamount = String.valueOf(totalamounttopay);
                        noDiscountResellerServiceArea();
                    } else {
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            } else {
                showNoInternetToast();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showNoInternetToast();
        }
    };

    private void payStudentTuitionFeeV2() {
        try{
            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(strtotalamount));
                strtotalamount = valuecheck;

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("schoolid",schoolid);
                parameters.put("studentid",studentid);
                parameters.put("soaid",soaid);
                parameters.put("amountpurchase",strtotalamount);
                parameters.put("paymenttype",mPaymentType);
                parameters.put("amountdetails",amountdetails);
                parameters.put("remarks",str_remarks);
                parameters.put("devicetype",CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "payStudentTuitionFeeV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                payStudentTuitionFeeV2Object();
            } else {
                CommonFunctions.hideDialog();
                showNoInternetToast();
            }
        }catch (Exception e){
            e.printStackTrace();
            CommonFunctions.hideDialog();
            showErrorToast();
        }
    }

    private void payStudentTuitionFeeV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getSchoolV2API(getViewContext())
                .payStudentTuitionFeeV2(authenticationid,sessionid,param);
        call.enqueue(payStudentTuitionFeeV2CallBack);
    }

    private final Callback<GenericResponse> payStudentTuitionFeeV2CallBack = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());

                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    String discountcheckifequal = String.valueOf(discount);
                    String discounttotalamounttopay = String.valueOf(totalamounttopay);

                    layout_req_via_payment_channel.setVisibility(View.GONE);
                    schoolminipayment = new Gson().fromJson(decryptedData, new TypeToken<SchoolMiniPayment>(){}.getType());
                    vouchersession = schoolminipayment.getVoucherSessionID();
                    merchantreferenceno = schoolminipayment.getMerchantReferenceNo();

                    if (mPaymentType.contains("PARTNERS")) {
                        totalamountcheck = Double.parseDouble(strtotalamount);
                        PreferenceUtils.saveBooleanPreference(getViewContext(), "schoolpaymentrequest", true);
                        SchoolMiniBillingReferenceActivity.start(getViewContext(), schoolminipayment, String.valueOf(totalamountcheck), "schoolMiniPaymentOptionsFragment");
                    } else {
                        if (discounttotalamounttopay.equals(discountcheckifequal)) {
                            showErrorGlobalDialogs("Invalid amount. Cannot Proceed.");
                            hideProgressDialog();
                        } else {
                            totalamountcheck = Double.parseDouble(strtotalamount);
                            proceedtoPayments();
                        }
                    }

                } else {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else if (response.body().getStatus().equals("006")) {
                        schoolminipayment = new Gson().fromJson(decryptedData, new TypeToken<SchoolMiniPayment>(){}.getType());
                        totalamountcheck = schoolminipayment.getTotalAmount();
                        vouchersession = schoolminipayment.getVoucherSessionID();
                        merchantreferenceno = schoolminipayment.getMerchantReferenceNo();
                        txv_content.setText(decryptedData);
                        layout_req_via_payment_channel.setVisibility(View.VISIBLE);
                    } else {
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            } else {
                showNoInternetToast();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showNoInternetToast();
        }
    };

}
