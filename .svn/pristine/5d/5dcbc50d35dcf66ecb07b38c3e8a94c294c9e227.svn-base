package com.goodkredit.myapplication.activities.voting;

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
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VotesConfirmationActivity extends BaseActivity implements View.OnClickListener {
    //LOCAL CALL
    private DatabaseHandler mdb;

    //MAIN CONTAINER
    private LinearLayout maincontainer;
    private NestedScrollView home_body;

    //VOTES
    private TextView txv_event_name;
    private TextView txv_candidate_no;
    private TextView txv_candidate_name;
    private TextView txv_amount;
    private TextView txv_servicecharge;
    private TextView txv_discount;
    private TextView txv_change;
    private TextView txv_amounttendered;

    private String borrowerid = "";
    private String borrowername = "";
    private String eventid = "";
    private String participantid = "";
    private String servicecode = "";
    private String eventname = "";
    private int participantnumber = 0;
    private String participantname = "";
    private String grossprice = "";
    private String discount = "";
    private String vouchersession = "";
    private String amountopayvalue = "";
    private String amountendered = "";
    private String amountchange = "";
    private int hasdiscount = 0;
    private String strcustomerservicecharge = "";

    //GK NEGOSYO
    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;
    private String resellerid = "";
    private String distributorid = "";
    private boolean checkIfReseller = false;

    //NO INTERNET CONNECTION
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private LinearLayout confirmcontainer;

    //CHECK STATUS
    private String ordertxnno = "";
    private boolean isStatusChecked = false;
    private int totaldelaytime = 10000;
    private int currentdelaytime = 0;

    private TextView txv_votesdetailslbl;
    private LinearLayout layout_votesdetails;

    private String sessionid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_votes_confirmation);

            init();

            initdata();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------FUNCTION---------------------
    private void init() {
        mdb = new DatabaseHandler(getViewContext());
        //initialize here
        maincontainer = (LinearLayout) findViewById(R.id.maincontainer);
        home_body = (NestedScrollView) findViewById(R.id.home_body);

        //VOTES
        txv_event_name = (TextView) findViewById(R.id.txv_event_name);
        txv_candidate_no = (TextView) findViewById(R.id.txv_candidate_no);
        txv_candidate_name = (TextView) findViewById(R.id.txv_candidate_name);
        txv_amount = (TextView) findViewById(R.id.txv_amount);
        txv_servicecharge = (TextView) findViewById(R.id.txv_servicecharge);
        txv_discount = (TextView) findViewById(R.id.txv_discount);
        txv_change = (TextView) findViewById(R.id.txv_change);

        confirmcontainer = (LinearLayout) findViewById(R.id.proceedcontainer);

        nointernetconnection = (RelativeLayout) findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        txv_votesdetailslbl = (TextView) findViewById(R.id.txv_votesdetailslbl);
        layout_votesdetails = (LinearLayout) findViewById(R.id.layout_votesdetails);

        txv_amounttendered = (TextView) findViewById(R.id.txv_amounttendered);

        scrollonTop();
    }

    private void initdata() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        borrowername = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_BORROWER_NAME);
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);
        //set toolbar
        setupToolbar();

        getSupportActionBar().setTitle("");
        //get the passed parameters
        Bundle b = getIntent().getExtras();
        eventid = b.getString("EVENTID");
        eventname = b.getString("EVENTNAME");
        participantid = b.getString("PARTICIPANTID");
        servicecode = b.getString("SERVICECODE");
        participantnumber = b.getInt("PARTICIPANTNUMBER");
        participantname = b.getString("PARTICIPANTNAME");
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

        if(participantid.trim().equals("DONATE")) {
            txv_votesdetailslbl.setVisibility(View.GONE);
            layout_votesdetails.setVisibility(View.GONE);
        } else {
            txv_votesdetailslbl.setVisibility(View.VISIBLE);
            layout_votesdetails.setVisibility(View.VISIBLE);
        }

        txv_event_name.setText(CommonFunctions.replaceEscapeData(eventname));

        String candidateno = (participantnumber < 10 ? "0" : "") + participantnumber;
        txv_candidate_no.setText(CommonFunctions.replaceEscapeData(candidateno));

        txv_candidate_name.setText(CommonFunctions.replaceEscapeData(participantname));

        txv_amount.setText(CommonFunctions.currencyFormatter(amountopayvalue));

        Double dblservicecharge = Double.parseDouble(strcustomerservicecharge);
        if (dblservicecharge > 0) {
            txv_servicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(strcustomerservicecharge)));
        } else {
            txv_servicecharge.setText(CommonFunctions.currencyFormatter("0"));
        }

        Double dbldiscount = Double.parseDouble(discount);
        if (dbldiscount > 0) {
            txv_discount.setText(CommonFunctions.currencyFormatter(String.valueOf(dbldiscount)));
        } else {
            txv_discount.setText(CommonFunctions.currencyFormatter("0"));
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

        boolean isGKNegosyoModal;
        if (checkIfReseller) {
            isGKNegosyoModal = false;
        } else {
            isGKNegosyoModal = true;
        }

        dialog.createDialog("SUCCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                isGKNegosyoModal, false, DialogTypeEnum.SUCCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                redirectiontoVotingList();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                redirectiontoVotingList();
            }
        });

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

    private void redirectiontoVotingList() {
        PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_VOTES_FROM, true);
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Payment.paymentfinishActivity.finish();
        VotesPaymentOptionActivity votesPaymentOptionActivity = VotesPaymentOptionActivity.votesPaymentOptionActivity;
        if(votesPaymentOptionActivity != null) {
            votesPaymentOptionActivity.finish();
        }
        VotesParticipantDetailsActivity votesParticipantDetailsActivity = VotesParticipantDetailsActivity.votesParticipantDetailsActivity;
        if(votesParticipantDetailsActivity != null) {
            votesParticipantDetailsActivity.finish();
        }
        finish();
    }

    //----------------------------API-----------------------------------
//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            showNoInternetConnection(false);
//            showContent(true);
//            setUpProgressLoader();
//            createSession(getSessionCallback);
//
//        } else {
//            setUpProgressLoaderDismissDialog();
//            showNoInternetConnection(true);
//            showContent(false);
//            showWarningToast("Seems you are not connected to the internet. " +
//                    "Please check your connection and try again. Thank you.");
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
//                    final Handler handlerstatus = new Handler();
//                    handlerstatus.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            setUpProgressLoaderMessageDialog("Checking status, Please wait...");
//                        }
//                    }, 1000);
//
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            currentdelaytime = currentdelaytime + 1000;
//                            setUpProgressLoaderMessageDialog("Processing request, Please wait...");
//                            processVotingPaymentViaGK(processVotingPaymentViaGKCallBack);
//                        }
//                    }, 1000);
//                } else {
//                    setUpProgressLoaderDismissDialog();
//                    hideProgressDialog();
//                    showErrorGlobalDialogs();
//                    showNoInternetConnection(true);
//                    showContent(false);
//                }
//            } else {
//                setUpProgressLoaderDismissDialog();
//                hideProgressDialog();
//                showErrorGlobalDialogs();
//                showNoInternetConnection(true);
//                showContent(false);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            setUpProgressLoaderDismissDialog();
//            hideProgressDialog();
//            showNoInternetConnection(true);
//            showContent(false);
//            showErrorGlobalDialogs();
//        }
//    };

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
                    processVotingPaymentViaGK(processVotingPaymentViaGKCallBack);
                }
            }, 1000);
        } else {
            setUpProgressLoaderDismissDialog();
            showNoInternetConnection(true);
            showContent(false);
            showNoInternetToast();
        }
    }

    //PROCESS VOTING GK
    private void processVotingPaymentViaGK(Callback<GenericResponse> processVotingPaymentViaGKCallBack) {
        Call<GenericResponse> processVotingPaymentViaGK = RetrofitBuild.getVotesAPIService(getViewContext())
                .processVotingPaymentViaGK(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        borrowername,
                        authcode,
                        eventid,
                        participantid,
                        hasdiscount,
                        servicecode,
                        grossprice,
                        vouchersession,
                        strcustomerservicecharge
                );

        processVotingPaymentViaGK.enqueue(processVotingPaymentViaGKCallBack);
    }

    private final Callback<GenericResponse> processVotingPaymentViaGKCallBack =
            new Callback<GenericResponse>() {

                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                if (distributorid.equals("") || distributorid.equals(".")
                                        || resellerid.equals("") || resellerid.equals(".")) {
                                    checkIfReseller = false;
                                } else {
                                    checkIfReseller = true;
                                }
                                String message = "";
                                if(participantid.trim().equals("DONATE")) {
                                    message = "You have donated succesfully with the reference no." + "\n" + response.body().getData();
                                } else {
                                    message = response.body().getMessage() + "\n" + response.body().getData();
                                }

                                showConfirmSuccessDialog(message);
                            } else {
                                setUpProgressLoaderDismissDialog();
                                showConfirmFailedDialog(response.body().getMessage());
                                hideProgressDialog();
                            }
                        } else {
                            showErrorGlobalDialogs();
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    showErrorGlobalDialogs();
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                    CommonFunctions.hideDialog();
                }
            };

    //----------------ON CLICK--------------------------
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
                    showWarningToast("Seems you are not connected to the internet. " +
                            "Please check your connection and try again. Thank you.");
                }
                break;
            }
        }
    }
}
