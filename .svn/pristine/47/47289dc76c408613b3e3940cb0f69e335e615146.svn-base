package com.goodkredit.myapplication.activities.schoolmini;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniTuitionFee;
import com.goodkredit.myapplication.responses.GenericResponse;
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


public class SchoolMiniConfirmationActivity extends BaseActivity implements View.OnClickListener {
    //LOCAL CALL
    private DatabaseHandler mdb;

    //MAIN CONTAINER
    private LinearLayout maincontainer;
    private NestedScrollView home_body;

    //GPS TRACKER
    private GPSTracker tracker;

    //GK NEGOSYO
    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;
    private String resellerid = "";
    private String distributorid = "";
    private boolean checkIfReseller = false;

    private TextView txv_id;
    private TextView txv_name;
    private TextView txv_mobilenumber;
    private TextView txv_emailaddress;
    private TextView txv_totalsemfee;
    private TextView txv_amount;
    private TextView txv_servicecharge;
    private TextView txv_discount;
    private TextView txv_change;
    private TextView txv_schoolyear;
    private TextView txv_semester;

    private String studentid = "";
    private String course = "";
    private String yearlevel = "";
    private String firstname = "";
    private String middlename = "";
    private String lastname = "";
    private String mobilenumber = "";
    private String tuitionlist = "";
    private String grossprice = "";
    private String emailaddress = "";
    private String discount = "";
    private String vouchersession = "";
    private String amountopayvalue = "";
    private String amountendered = "";
    private String amountchange = "";
    private String merchantreferenceno = "";
    private String schoolyear = "";
    private String semester = "";
    private String semestralfee = "";
    private int hasdiscount = 0;
    private String schoolid = "";
    private String merchantid = "";
    private String soaid = "";
    private String mPaymentType = "";
    private String strcustomerservicecharge = "";
    private String sessionid = "";

    //CHECK STATUS
    private String ordertxnno = "";
    private boolean isStatusChecked = false;
    private int totaldelaytime = 10000;
    private int currentdelaytime = 0;

    private List<SchoolMiniTuitionFee> schoolMiniTuitionFeeList = new ArrayList<>();

    //NO INTERNET CONNECTION
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;


    private LinearLayout confirmcontainer;
    private String morderdetailsstr;

    //REMARKS
    private String str_remarks = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_school_mini_confirmation);

            init();

            initdata();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mdb = new DatabaseHandler(getViewContext());

        schoolid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCSERVICECODE);
        merchantid = PreferenceUtils.getStringPreference(getViewContext(),PreferenceUtils.KEY_SCMERCHANTID);
        //initialize here
        maincontainer = (LinearLayout) findViewById(R.id.maincontainer);

        home_body = (NestedScrollView) findViewById(R.id.home_body);

        confirmcontainer = (LinearLayout) findViewById(R.id.proceedcontainer);

        nointernetconnection = (RelativeLayout) findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        txv_id = (TextView) findViewById(R.id.txv_id);
        txv_name = (TextView) findViewById(R.id.txv_name);
        txv_mobilenumber = (TextView) findViewById(R.id.txv_mobilenumber);
        txv_emailaddress = (TextView) findViewById(R.id.txv_emailaddress);
        txv_totalsemfee = (TextView) findViewById(R.id.txv_totalsemfee);
        txv_amount = (TextView) findViewById(R.id.txv_amount);
        txv_servicecharge = (TextView) findViewById(R.id.txv_servicecharge);
        txv_discount = (TextView) findViewById(R.id.txv_discount);
        txv_change = (TextView) findViewById(R.id.txv_change);
        txv_schoolyear = (TextView) findViewById(R.id.txv_schoolyear);
        txv_semester = (TextView) findViewById(R.id.txv_semester);

        scrollonTop();
    }

    private void initdata() {
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

        schoolid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCSERVICECODE);
        merchantid = PreferenceUtils.getStringPreference(getViewContext(),PreferenceUtils.KEY_SCMERCHANTID);

        //set toolbar
        setupToolbar();

        getSupportActionBar().setTitle("");
        //get the passed parameters
        Bundle b = getIntent().getExtras();
        studentid = b.getString("STUDENTID");
        course = b.getString("COURSE");
        yearlevel = b.getString("YEARLEVEL");
        firstname = b.getString("FIRSTNAME");
        middlename = b.getString("MIDDLENAME");
        lastname = b.getString("LASTNAME");
        mobilenumber = b.getString("MOBILENUMBER");
        emailaddress = b.getString("EMAILADDRESS");
        schoolyear = b.getString("SCHOOLYEAR");
        semester = b.getString("SEMESTER");
        semestralfee = b.getString("SEMESTRALFEE");
        soaid = b.getString("SOAID");
        vouchersession = b.getString("VOUCHERSESSION");
        amountendered = b.getString("AMOUNTENDERED");
        amountchange = b.getString("CHANGE");
        amountopayvalue = b.getString("AMOUNT");
        discount = b.getString("DISCOUNT");
        merchantreferenceno = b.getString("MERCHANTREFERENCENO");
        grossprice = b.getString("GROSSPRICE");
        hasdiscount = b.getInt("GKHASDISCOUNT");
        mPaymentType = b.getString("GKMPAYMENTTYPE");
        strcustomerservicecharge = b.getString("GKCUSTOMERSERVICECHARGE");
        str_remarks = b.getString("REMARKS");

        displayData();
        onClickListeners();
    }

    private void displayData() {
        txv_id.setText(studentid);
        txv_name.setText(firstname + " " + middlename + " " + lastname);
        txv_mobilenumber.setText("+" + mobilenumber);
        txv_emailaddress.setText(emailaddress);

        txv_schoolyear.setText(schoolyear);
        txv_semester.setText(semester);

        txv_totalsemfee.setText(CommonFunctions.currencyFormatter(semestralfee));
        txv_amount.setText(CommonFunctions.currencyFormatter(amountopayvalue));

        Double dblservicecharge = Double.parseDouble(strcustomerservicecharge);
        if(dblservicecharge > 0) {
            txv_servicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(strcustomerservicecharge)));
        } else {
            txv_servicecharge.setText(CommonFunctions.currencyFormatter("0"));
        }

        Double dbldiscount = Double.parseDouble(discount);
        if(dbldiscount > 0) {
            txv_discount.setText(CommonFunctions.currencyFormatter(String.valueOf(dbldiscount)));
        } else {
            txv_discount.setText(CommonFunctions.currencyFormatter("0"));
        }

        txv_change.setText(CommonFunctions.currencyFormatter(amountchange));
    }

    private void scrollonTop() {
        home_body.post(new Runnable() {
            public void run() {
                home_body.smoothScrollTo(0, 0);
            }
        });
    }

    private void onClickListeners() {
        confirmcontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog materialDialog = new MaterialDialog.Builder(getViewContext())
                        .content("You are about to pay your request.")
                        .cancelable(false)
                        .negativeText("Close")
                        .positiveText("Proceed")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                currentdelaytime = 0;
                                getSession();
                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        })
                        .show();

                V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, materialDialog.getView());
            }
        });
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    private void showContent(boolean isShow) {
        if (isShow) {
            home_body.setVisibility(View.VISIBLE);
        } else {
            home_body.setVisibility(View.GONE);
        }
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showNoInternetConnection(false);
            showContent(true);
            setUpProgressLoader();
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            final Handler handlerstatus = new Handler();
            handlerstatus.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setUpProgressLoaderMessageDialog("Checking status, Please wait...");
                }
            }, 1000);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentdelaytime = currentdelaytime + 1000;
                    setUpProgressLoaderMessageDialog("Processing request, Please wait...");
                    //processStudentPaymentConsummation(processStudentPaymentConsummationResponseCallBack);
                    processStudentPaymentConsummationV2();
                }
            }, 1000);
        } else {
            setUpProgressLoaderDismissDialog();
            showNoInternetConnection(true);
            showContent(false);
            showNoInternetToast();
        }
    }

    //PAYSTUDENTTUITIONFEE
    private void processStudentPaymentConsummation(Callback<GenericResponse> processStudentPaymentConsummationResponseCallBack) {
        Call<GenericResponse> paystudenttuitionfee = RetrofitBuild.getSchoolAPIService(getViewContext())
                .processStudentPaymentConsummation(sessionid,
                        imei,
                        userid,
                        authcode,
                        borrowerid,
                        vouchersession,
                        merchantid,
                        soaid,
                        studentid,
                        schoolid,
                        merchantreferenceno,
                        amountopayvalue,
                        mPaymentType,
                        hasdiscount,
                        schoolid,
                        grossprice,
                        str_remarks
                );

        paystudenttuitionfee.enqueue(processStudentPaymentConsummationResponseCallBack);
    }

    private final Callback<GenericResponse> processStudentPaymentConsummationResponseCallBack = new
            Callback<GenericResponse>() {

                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    try {
                        hideProgressDialog();
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                if (distributorid.equals("") || distributorid.equals(".")
                                        || resellerid.equals("") || resellerid.equals(".")) {
                                    checkIfReseller = false;
                                } else {
                                    checkIfReseller = true;
                                }
                                setUpProgressLoaderDismissDialog();
                                showGlobalDialogs("You have successfully paid " + "\n" + CommonFunctions.currencyFormatter(amountopayvalue) + " for Account/Ref "
                                        + merchantreferenceno + ".\n" +" You can check your transaction under Usage Menu.", "close", ButtonTypeEnum.SINGLE,
                                        checkIfReseller, false, DialogTypeEnum.SUCCESS);
                            } else {
                                currentdelaytime = 0;
                                setUpProgressLoaderDismissDialog();
                                showGlobalDialogs(response.body().getMessage(), "retry", ButtonTypeEnum.SINGLE,
                                        false, false, DialogTypeEnum.FAILED);
                            }
                        } else {
                            setUpProgressLoaderDismissDialog();
                            hideProgressDialog();
                            showError(response.body().getMessage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    setUpProgressLoaderDismissDialog();
                    hideProgressDialog();
                    showErrorToast();
                    hideProgressDialog();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshnointernet: {
                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                    showNoInternetConnection(false);
                    showContent(true);
                } else {
                    showNoInternetConnection(true);
                    showContent(false);
                    showError(getString(R.string.generic_internet_error_message));
                }
                break;
            }
        }
    }

    /*
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * */

    private void processStudentPaymentConsummationV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("vouchersessionid", vouchersession);
                parameters.put("merchantid", merchantid);
                parameters.put("soaid", soaid);
                parameters.put("studentid", studentid);
                parameters.put("schoolid", schoolid);
                parameters.put("merchantreferenceno", merchantreferenceno);
                parameters.put("amount", amountopayvalue);
                parameters.put("paymenttype", mPaymentType);
                parameters.put("hasdiscount", String.valueOf(hasdiscount));
                parameters.put("servicecode", schoolid);
                parameters.put("grossamount", grossprice);
                parameters.put("remarks", str_remarks);
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "processStudentPaymentConsummationV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                processStudentPaymentConsummationV2Object();

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

    private void processStudentPaymentConsummationV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getSchoolV2API(getViewContext())
                .processStudentPaymentConsummationV2(authenticationid, sessionid, param);

        call.enqueue(processStudentPaymentConsummationV2CallBack);
    }

    private Callback<GenericResponse> processStudentPaymentConsummationV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();

            setUpProgressLoaderDismissDialog();

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                if (response.body().getStatus().equals("000")) {
                    setUpProgressLoaderDismissDialog();

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    if (distributorid.equals("") || distributorid.equals(".")
                            || resellerid.equals("") || resellerid.equals(".")) {
                        checkIfReseller = false;
                    } else {
                        checkIfReseller = true;
                    }

                    String successmessage = "You have successfully paid " + "\n" + CommonFunctions.currencyFormatter(amountopayvalue) + " for Account/Ref "
                            + merchantreferenceno + ".\n" +" You can check your transaction under Usage Menu.";

                    showSuccessDialog(successmessage);

                } else {
                    setUpProgressLoaderDismissDialog();
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        currentdelaytime = 0;
                        showFailedDialog(decryptedMessage);
                    }
                }
            } else {
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }
    };

    private void showSuccessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        boolean isGKNegosyoModal;
        if (checkIfReseller) {
            isGKNegosyoModal = false;
        } else {
            isGKNegosyoModal = true;
        }

        dialog.createDialog("SUCCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                isGKNegosyoModal, true, DialogTypeEnum.SUCCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });
    }

    private void showFailedDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("FAILED", message,
                "Retry", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.returntoBeforePayments();
            }
        });
    }
}
