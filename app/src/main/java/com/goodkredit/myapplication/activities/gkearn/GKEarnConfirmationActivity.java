package com.goodkredit.myapplication.activities.gkearn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKEarnConfirmationActivity extends BaseActivity implements View.OnClickListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String borrowername = "";
    private String borroweremail = "";
    private String borrowermobileno = "";

    private String servicecode = "";
    private String merchantid = "";
    private String merchantname = "";

    //LOCAL CALL
    private DatabaseHandler mdb;

    //MAIN CONTAINER
    private LinearLayout maincontainer;
    private NestedScrollView home_body;

    //NO INTERNET CONNECTION
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //GK NEGOSYO
    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;
    private boolean checkIfReseller = false;

    //FROM
    private String strFrom = "";

    //GK EARN PACKAGE
    private TextView txv_packagename;
    private TextView txv_borrowername;
    private TextView txv_package_price;

    private String packageID = "";
    private String packagename = "";
    private String grossprice = "";
    private String discount = "";
    private String vouchersession = "";
    private String amountopayvalue = "";
    private String amountendered = "";
    private String amountchange = "";
    private int hasdiscount = 0;
    private String strcustomerservicecharge = "";

    //PAYMENTS
    private TextView txv_amount;
    private TextView txv_servicecharge;
    private TextView txv_change;
    private TextView txv_amounttendered;

    //DELAY TIME
    private int currentdelaytime = 0;
    private int totaldelaytime = 10000;

    //PROCEED
    private LinearLayout confirmcontainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gkearn_confirmation);

            init();

            initdata();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------METHODS---------------------
    private void init() {
        mdb = new DatabaseHandler(getViewContext());

        maincontainer = (LinearLayout) findViewById(R.id.maincontainer);
        home_body = (NestedScrollView) findViewById(R.id.home_body);

        //NO INTERNET
        nointernetconnection = (RelativeLayout) findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        txv_packagename = findViewById(R.id.txv_packagename);
        txv_borrowername = findViewById(R.id.txv_borrowername);
        txv_package_price = findViewById(R.id.txv_package_price);

        //PAYMENTS
        txv_amount = (TextView) findViewById(R.id.txv_amount);
        txv_servicecharge = (TextView) findViewById(R.id.txv_servicecharge);
        txv_change = (TextView) findViewById(R.id.txv_change);
        txv_amounttendered = (TextView) findViewById(R.id.txv_amounttendered);

        confirmcontainer = (LinearLayout) findViewById(R.id.proceedcontainer);

        scrollonTop();
    }

    private void initdata() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_GKEARNSTOCKISTSERVICECODE);
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_GKEARNSTOCKISTMERCHANTID);

        //set toolbar
        setupToolbar();

        getSupportActionBar().setTitle("");

        //get the passed parameters
        Bundle b = getIntent().getExtras();
        strFrom = b.getString("FROM");

        borrowername = b.getString("GKEARNBORROWERNAME");
        packageID = b.getString("GKEARNPACKAGEID");
        packagename = b.getString("GKEARNPACKAGENAME");
        vouchersession = b.getString("VOUCHERSESSION");
        amountendered = b.getString("AMOUNTENDERED");
        amountchange = b.getString("CHANGE");
        amountopayvalue = b.getString("AMOUNT");
        discount = b.getString("DISCOUNT");
        grossprice = b.getString("GROSSPRICE");
        hasdiscount = b.getInt("GKHASDISCOUNT");
        strcustomerservicecharge = b.getString("GKCUSTOMERSERVICECHARGE");

        displayData();

        onClickListeners();
    }

    private void displayData() {
        if (!borrowername.equals("")) {
            String name = borrowername;
            if(name.contains(".")) {
                name = CommonFunctions.replaceEscapeData(CommonFunctions.capitalizeWord(name));
                name = name.replace(".", "");
            }
            txv_borrowername.setText(name);
        }

        txv_packagename.setText(packagename);

        txv_package_price.setText(CommonFunctions.currencyFormatter(grossprice));

        Double dblservicecharge = Double.parseDouble(strcustomerservicecharge);
        if (dblservicecharge > 0) {
            txv_servicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(strcustomerservicecharge)));
            txv_amount.setText(CommonFunctions.currencyFormatter(String.valueOf(Double.valueOf(amountopayvalue) - Double.valueOf(strcustomerservicecharge))));
        } else {
            txv_servicecharge.setText(CommonFunctions.currencyFormatter("0"));
            txv_amount.setText(CommonFunctions.currencyFormatter(amountopayvalue));
        }

        txv_change.setText(CommonFunctions.currencyFormatter(amountchange));

        txv_amounttendered.setText(CommonFunctions.currencyFormatter(amountendered));
    }

    private void scrollonTop() {
        home_body.post(new Runnable() {
            public void run() {
                home_body.smoothScrollTo(0, 0);
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
                proceedToGKEarnHome();
            }
        });

    }

    private void proceedToGKEarnHome(){
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(getViewContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        getViewContext().startActivity(intent);
        GKEarnHomeActivity.start(getViewContext(),
                GKEarnHomeActivity.GKEARN_FRAGMENT_REGISTERED_MEMBER,
                null);
    }

    private void showConfirmFailedDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("FAILED", message,
                "Retry", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.returntoBeforePayments();
            }
        });

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

    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, GKEarnConfirmationActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    //----------------------------API-----------------------------------
    private void callMainAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Processing reqyest", "Please wait...");
            registerGKEarnStockistRequest();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //REGISTER EARN STOCKIST REQUEST
    private void registerGKEarnStockistRequest() {
        Call<GenericResponse> registerGKEarnStockistRequest = RetrofitBuild.getgkEarnAPIService(getViewContext())
                .registerGKEarnStockist(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        borrowername,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        vouchersession,
                        merchantid,
                        servicecode,
                        packageID,
                        grossprice,
                        strcustomerservicecharge,
                        "PAY VIA GK",
                        "."
                );

        registerGKEarnStockistRequest.enqueue(registerGKEarnStockistRequestCallBack);
    }

    private final Callback<GenericResponse> registerGKEarnStockistRequestCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        showConfirmSuccessDialog(response.body().getMessage());
                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
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

    //----------------ON CLICK--------------------------
    private void onClickListeners() {
        confirmcontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalDialogs dialog = new GlobalDialogs(getViewContext());

                dialog.createDialog("", "You are about to pay your request.",
                        "Close", "Proceed", ButtonTypeEnum.DOUBLE,
                        false, false, DialogTypeEnum.NOTICE);

                dialog.hideCloseImageButton();

                View btndoubleone = dialog.btnDoubleOne();
                btndoubleone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });

                View btndoubletwo = dialog.btnDoubleTwo();
                btndoubletwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                        dialog.dismiss();
                        currentdelaytime = 0;
                        callMainAPI();
                    }
                });
            }
        });
    }

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
        if (v.getId() == R.id.refreshnointernet) {
            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                showNoInternetConnection(false);
                showContent(true);
            } else {
                showNoInternetConnection(true);
                showContent(false);
                showNoInternetToast();
            }
        }
    }
}
