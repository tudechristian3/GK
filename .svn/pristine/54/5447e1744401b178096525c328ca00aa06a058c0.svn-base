package com.goodkredit.myapplication.activities.voting;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.eghl.sdk.EGHL;
import com.eghl.sdk.params.PaymentParams;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.prepaidrequest.EGHLPayment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.votes.VotesParticipants;
import com.goodkredit.myapplication.model.votes.VotesPayment;
import com.goodkredit.myapplication.model.votes.VotesPostEvent;
import com.goodkredit.myapplication.responses.DiscountResponse;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetChargeResponse;
import com.goodkredit.myapplication.responses.prepaidrequest.CalculateEGHLServiceChargeResponse;
import com.goodkredit.myapplication.responses.prepaidrequest.RequestVoucherViaEghlPaymentResponse;
import com.goodkredit.myapplication.responses.votes.VotesPaymentResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VotesPaymentOptionActivity extends BaseActivity implements View.OnClickListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String borrowername = "";
    private String borroweremail = "";
    private String borrowermobileno = "";
    private String authcode = "";

    //MERCHANT
    private String merchantid = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //VOTES
    private String eventid = "";
    private String eventname = "";
    private String participantid = "";
    private int participantnumber = 0;
    private String participantname = "";
    private String pricepervote = "";
    private String votemaxtype = "";
    private String maxvote = "";

    private VotesParticipants participants = null;
    private VotesPostEvent votesPostObject = null;

    private String merchantreferenceno = "";
    private String paymentreferenceno = "";

    //PENDING REQUEST
    private TextView txv_content;
    private RelativeLayout layout_req_via_payment_channel;
    private TextView btn_paynow;
    private TextView btn_cancelrequest;
    private ImageView btn_close;

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
    private String servicecode = "";
    private String latitude = "";
    private String longitude = "";
    private String strtotalamount = "";
    private int hasdiscount = 0;
    private String discountmessage = "";

    private GPSTracker tracker;

    //GK NEGOSYO
    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;
    private String resellerid = "";
    private String distributorid = "";
    private boolean checkIfReseller = false;

    //PROCEED
    private TextView btn_continue;
    private double totalamounttopay = 0.00;
    private String vouchersession = "";

    private VotesPayment votespayment;
    private double totalamountcheck = 0.00;

    //DISCOUNT DIALOG
    private MaterialDialog mMaterialDialog = null;
    private Dialog mDialog = null;

    //CUSTOMER SERVICE CHARGE
    private double customerservicecharge = 0.00;
    private String strcustomerservicecharge = "";

    //AMOUNT DETAILS
    private String amountdetails = "";

    //EGHL
    private EGHLPayment eghlPayment;
    private EGHL eghl;
    private PaymentParams.Builder params;
    private double eghlservicecharge = 0.00;
    private boolean isEGHLCheckStatus = false;
    private boolean isEGHLCancelPayment = false;
    private int totaldelaytime = 10000;
    private int currentdelaytime = 0;

    //VOTES (PAYMENT OPTION)
    private EditText edtNumberofVotes;
    private String edtNumberofVotesString = "";

    private TextView txvAmounttoPay;
    private boolean checkIfPaymenthasPending = false;

    private String passedamount = "";

    @SuppressLint("StaticFieldLeak")
    public static VotesPaymentOptionActivity votesPaymentOptionActivity;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String param;
    private String keyEncryption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_votes_payment_option);

            votesPaymentOptionActivity = this;
            //All Initializations
            init();

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setupToolbarWithTitle("Payment Options");
        rg_payment_options = (RadioGroup) findViewById(R.id.rg_payment_options);

        rb_channel = (RadioButton) findViewById(R.id.rb_channel);
        rb_voucher = (RadioButton) findViewById(R.id.rb_voucher);

        edt_amount = (EditText) findViewById(R.id.edt_amount);
        layout_amount_container = (LinearLayout) findViewById(R.id.layout_amount_container);

        btn_continue = (TextView) findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(this);

        //PENDING REQUEST
        txv_content = (TextView) findViewById(R.id.txv_content);
        layout_req_via_payment_channel = (RelativeLayout) findViewById(R.id.layout_req_via_payment_channel);
        btn_cancelrequest = (TextView) findViewById(R.id.btn_cancel_request);
        btn_cancelrequest.setOnClickListener(this);
        btn_paynow = (TextView) findViewById(R.id.btn_paynow_request);
        btn_paynow.setOnClickListener(this);
        btn_close = (ImageView) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());

        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        borrowername = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_BORROWER_NAME);
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        V2Subscriber mSubscriber = mdb.getSubscriber(mdb);
        borroweremail = mSubscriber.getEmailAddress();
        borrowermobileno = mSubscriber.getMobileNumber();

        //SERVICE CODE
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), "GKMerchantID");

        //GK NEGOSYO
        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

        //VOTES
        participants = getIntent().getParcelableExtra(VotesParticipants.KEY_VOTESPARTICIPANTS);
        votesPostObject = getIntent().getParcelableExtra("VotesPostObject");
        passedamount = getIntent().getStringExtra("VoteAmount");

        getVotesObject();

        getParticipantsObject();

        //PENDING REQUEST

    }

    //VOTES PROJECT
    private void getVotesObject() {
        eventid = votesPostObject.getEventID();
        eventname = votesPostObject.getEventName();

        String xmldetails = votesPostObject.getXMLDetails();
        pricepervote = CommonFunctions.parseXML(xmldetails, "pricepervote");
        votemaxtype = CommonFunctions.parseXML(xmldetails, "votemaxtype");
        maxvote = CommonFunctions.parseXML(xmldetails, "maxvote");
    }

    private void getParticipantsObject() {
        participantid = participants.getParticipantID();
        participantnumber = participants.getParticipantNumber();
        participantname = participants.getFirstName() + " " + participants.getLastName();
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
                        if (mPaymentType.contains("CARD")) {
                            calculateEGHLServiceCharge(calculateEGHLServiceChargeSession);
                        } else {
                            showChargeDialogNew();
                        }
                        mMaterialDialog.dismiss();
                        mMaterialDialog = null;
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        mMaterialDialog.dismiss();
                        mMaterialDialog = null;
                        hideProgressDialog();
                        setUpProgressLoaderDismissDialog();
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

            popamountpaid = (TextView) mDialog.findViewById(R.id.popamounttopayval);
            popservicecharge = (TextView) mDialog.findViewById(R.id.popservicechargeval);
            poptotalamount = (TextView) mDialog.findViewById(R.id.poptotalval);
            popok = (TextView) mDialog.findViewById(R.id.popok);
            popcancel = (TextView) mDialog.findViewById(R.id.popcancel);

            //SERVICE CHARGE
            chargerow = (TableRow) mDialog.findViewById(R.id.chargerow);
            popcustomerchargelbl = (TextView) mDialog.findViewById(R.id.popcustomerchargelbl);
            popcustomerchargeeval = (TextView) mDialog.findViewById(R.id.popcustomerchargeeval);

            //set value
            popamountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(totalamounttopay)));
            popcustomerchargeeval.setText(CommonFunctions.currencyFormatter(String.valueOf(strcustomerservicecharge)));
            popservicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(discount)));
            poptotalamount.setText(CommonFunctions.currencyFormatter(String.valueOf(strtotalamount)));

            //click close
            popcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mDialog = null;
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                }

            });

            popok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mPaymentType.contains("GK")) {
                        prePurchase(prePurchaseSession);
                    } else if (mPaymentType.contains("PARTNER")) {
                        processVotingPaymentViaPartner(processVotingPaymentViaPartnerCallBack);
                    } else {
                        processVotingPaymentViaCard(processVotingPaymentViaCardCallBack);
                    }
                    mDialog.dismiss();
                    mDialog = null;
                }

            });

            mDialog.show();
        }
    }

    //DIALOG FOR DISCOUNT (NEW)
    private void showChargeDialogNew() {
        GlobalDialogs globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("DISCOUNT", "",
                "CANCEL", "CONFIRM", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.DOUBLETEXTVIEW);

        View closebtn = globalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
            }
        });

        List<String> titleList = new ArrayList<>();
        titleList.add("Amount");
        titleList.add("Processing Fee");
        titleList.add("Discount");
        titleList.add("Amount to Pay");


        List<String> contentList = new ArrayList<>();
        contentList.add(CommonFunctions.currencyFormatter(String.valueOf(totalamounttopay)));
        contentList.add(CommonFunctions.currencyFormatter(String.valueOf(strcustomerservicecharge)));
        contentList.add(CommonFunctions.currencyFormatter(String.valueOf(discount)));
        contentList.add(CommonFunctions.currencyFormatter(String.valueOf(strtotalamount)));

        LinearLayout linearLayout = globalDialogs.setContentDoubleTextView(
                titleList,
                new GlobalDialogsObject(R.color.color_908F90, 18, Gravity.START),
                contentList,
                new GlobalDialogsObject(R.color.color_23A8F6, 18, Gravity.END)
        );

//        final int count = linearLayout.getChildCount();
//        for (int i = 0; i < count; i++) {
//            View childlinearlayout = linearLayout.getChildAt(i);
//            if(childlinearlayout instanceof LinearLayout) {
//                LinearLayout linearIItem = (TextView) textView;
//                View textView = childlinearlayout.getChildAt(i);
//                Logger.debug("checkhttp","YES IT A LINEARLAYOUT");
//            } else {
//                Logger.debug("checkhttp","NOPE");
//            }
//            View textView = linearLayout.getChildAt(i);
//            if (textView instanceof TextView) {
//                TextView txvItem = (TextView) textView;
//                String taggroup = txvItem.getTag().toString();
//                Logger.debug("checkhttp","check" + taggroup);
//                if (taggroup.equals("TITLE Amount to Pay")) {
//
//                }
//            } else {
//                Logger.debug("checkhttp","Not A textView");
//            }
//        }

        View btndoubleone = globalDialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();

            }
        });

        View btndoubletwo = globalDialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                checkIfHasPendingVotingRequest(checkIfHasPendingVotingRequestCallBack);
                if (mPaymentType.contains("GK")) {
                    prePurchase(prePurchaseSession);
                } else if (mPaymentType.contains("PARTNER")) {
                    hideProgressDialog();
                    setUpProgressLoader();
                    setUpProgressLoaderMessageDialog("Processing request, Please wait...");
                    processVotingPaymentViaPartner(processVotingPaymentViaPartnerCallBack);
                } else {
                    hideProgressDialog();
                    showProgressDialog(getViewContext(), "Processing request", " Please wait...");
                    processVotingPaymentViaCard(processVotingPaymentViaCardCallBack);
                }
                globalDialogs.dismiss();
            }
        });
    }

    private void showCancelRequestWarningDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("Are you sure you want to cancel this transaction?")
                .negativeText("Cancel")
                .positiveText("Proceed")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //getCancelledSession();
                        isEGHLCheckStatus = false;
                        isEGHLCancelPayment = true;

                        getTransactionSession();
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

    private void showVotesEghlSuccessDialog(String message) {
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
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                redirectiontoVotingList();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                redirectiontoVotingList();
            }
        });

    }

    private void showVotesEghlFailedDialog(String message) {
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
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
            }
        });
    }

    private void showVotesEghlTimeOutDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("TIMEOUT", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.TIMEOUT);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
            }
        });
    }

    private void showVotesEghlOnProcessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("ON PROCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.ONPROCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                redirectiontoVotingList();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                redirectiontoVotingList();
            }
        });
    }

    private void showVotesToPayValue() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "",
                "Close", "Proceed", ButtonTypeEnum.DOUBLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //VOTES LABEL
        LinearLayout txtVotesMainLblContainer = dialog.getTextViewMessageContainer();
        txtVotesMainLblContainer.setPadding(15, 15, 15, 0);

        List<String> votesLblList = new ArrayList<>();
        String voteslbl = "1 vote = " + pricepervote + " Php";
        votesLblList.add(voteslbl);

        dialog.setContentCustomMultiTextView(votesLblList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_045C84, 16, Gravity.TOP | Gravity.CENTER));

        //NUMBER OF VOTES
        //TEXTVIEW
        LinearLayout txtNumberOfVotesContainer = dialog.getTextViewMessageContainer();
        txtNumberOfVotesContainer.setPadding(35, 20, 35, 0);

        List<String> numberOfVotesList = new ArrayList<>();
        numberOfVotesList.add("Number of Votes: ");

        dialog.setContentCustomMultiTextView(numberOfVotesList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_878787, 16, Gravity.TOP | Gravity.START));

        //EDITTEXT
        LinearLayout editTextMainContainer = dialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(35, 0, 35, 20);

        List<String> editTextDataType = new ArrayList<>();
        editTextDataType.add(String.valueOf(GlobalDialogsEditText.CUSTOMNUMBER));

        LinearLayout editTextContainer = dialog.setContentCustomMultiEditText(editTextDataType,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.START,
                        R.drawable.border, 0, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;
                editItem.setPadding(20, 20, 20, 20);
                String taggroup = editItem.getTag().toString();
                if (taggroup.equals("TAG 0")) {
                    edtNumberofVotes = editItem;
                }
            }
        }

        //AMOUNT TO PAY
        //TEXTVIEW
        LinearLayout txtAmountToPayLblContainer = dialog.getTextViewMessageContainer();
        txtAmountToPayLblContainer.setPadding(35, 20, 35, 0);

        List<String> amountToPayLblList = new ArrayList<>();
        amountToPayLblList.add("Amount to Pay: ");

        LinearLayout amountToPayLblViewContainer = dialog.setContentCustomMultiTextView(amountToPayLblList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_878787, 16, Gravity.TOP | Gravity.START));

        //TEXTVIEW
        LinearLayout txtAmountToPayContainer = dialog.getTextViewMessageContainer();
        txtAmountToPayContainer.setPadding(35, 20, 35, 0);

        List<String> amountToPayList = new ArrayList<>();
        amountToPayList.add("");

        LinearLayout amounttoPayContainer = dialog.setContentCustomMultiTextView(amountToPayList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_878787, 16, Gravity.TOP | Gravity.START));


        final int amountopaycount = amounttoPayContainer.getChildCount();
        for (int i = 0; i < amountopaycount; i++) {
            View textView = amounttoPayContainer.getChildAt(i);
            if (textView instanceof TextView) {
                TextView textViewItem = (TextView) textView;
                textViewItem.setPadding(20, 20, 20, 20);
                textViewItem.setBackgroundResource(R.drawable.border_disabled);
                String taggroup = textViewItem.getTag().toString();
                if (taggroup.equals("")) {
                    txvAmounttoPay = textViewItem;
                    edtNumberofVotes.addTextChangedListener(numberofVotesTextWatcher);
                }
            }
        }

        View closebtn = dialog.btnDoubleOne();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                hideProgressDialog();

            }
        });

        View singlebtn = dialog.btnDoubleTwo();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                String checkamount = String.valueOf(edtNumberofVotes.getText());
                if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                    showToast("Please input a value.", GlobalToastEnum.WARNING);
                } else {
                    dialog.dismiss();
                    getSession();
                }
            }
        });
    }

    private void showDonateToPayValue() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "",
                "Proceed", "", ButtonTypeEnum.SINGLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //NUMBER OF VOTES
        //TEXTVIEW
        LinearLayout txtNumberOfVotesContainer = dialog.getTextViewMessageContainer();
        txtNumberOfVotesContainer.setPadding(35, 20, 35, 0);

        List<String> numberOfVotesList = new ArrayList<>();
        numberOfVotesList.add("Amount to Pay: ");

        dialog.setContentCustomMultiTextView(numberOfVotesList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_878787, 16, Gravity.TOP | Gravity.START));

        //EDITTEXT
        LinearLayout editTextMainContainer = dialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(35, 0, 35, 20);

        List<String> editTextDataType = new ArrayList<>();
        editTextDataType.add(String.valueOf(GlobalDialogsEditText.CUSTOMNUMBER));

        LinearLayout editTextContainer = dialog.setContentCustomMultiEditText(editTextDataType,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.START,
                        R.drawable.border, 19, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;
                editItem.setPadding(20, 20, 20, 20);
                String taggroup = editItem.getTag().toString();
                if (taggroup.equals("TAG 0")) {
                    edtNumberofVotes = editItem;
                    edtNumberofVotes.addTextChangedListener(donateTextWatcher);
                }
            }
        }

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                hideProgressDialog();

            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                String checkamount = String.valueOf(edtNumberofVotes.getText());
                if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                    showToast("Please input a value.", GlobalToastEnum.WARNING);
                } else {
                    dialog.dismiss();
                    getSession();
                }
            }
        });
    }

    private void redirectiontoVotingList() {
        PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_VOTES_FROM, true);
        VotesParticipantDetailsActivity votesParticipantDetailsActivity = VotesParticipantDetailsActivity.votesParticipantDetailsActivity;
        if(votesParticipantDetailsActivity != null) {
            votesParticipantDetailsActivity.finish();
        }
        finish();
    }

    //PROCEEDING TO PAYMENTS
    private void proceedtoPayments() {
        hideProgressDialog();
        setUpProgressLoaderDismissDialog();

        Intent intent = new Intent(getViewContext(), Payment.class);
        intent.putExtra("EVENTID", eventid);
        intent.putExtra("EVENTNAME", eventname);
        intent.putExtra("PARTICIPANTID", participantid);
        intent.putExtra("SERVICECODE", servicecode);
        intent.putExtra("EVENTNAME", eventname);
        intent.putExtra("PARTICIPANTNUMBER", participantnumber);
        intent.putExtra("PARTICIPANTNAME", participantname);
        intent.putExtra("GROSSPRICE", String.valueOf(totalamounttopay));
        intent.putExtra("DISCOUNT", strdiscount);
        intent.putExtra("VOUCHERSESSION", vouchersession);
        intent.putExtra("AMOUNT", String.valueOf(totalamountcheck));
        intent.putExtra("GKHASDISCOUNT", hasdiscount);
        intent.putExtra("GKCUSTOMERSERVICECHARGE", strcustomerservicecharge);
        startActivity(intent);

    }

    //---------------API---------------
//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            showProgressDialog(getViewContext(), "Processing request", " Please wait...");
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
//                } else {
//                    showErrorGlobalDialogs();
//                    hideProgressDialog();
//                    setUpProgressLoaderDismissDialog();
//                }
//            } else {
//                showErrorGlobalDialogs();
//                hideProgressDialog();
//                setUpProgressLoaderDismissDialog();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//            hideProgressDialog();
//            setUpProgressLoaderDismissDialog();
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Processing request", " Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            if (distributorid.equals("") || distributorid.equals(".")
                    || resellerid.equals("") || resellerid.equals(".")) {
                if (latitude.equals("") || longitude.equals("")
                        || latitude.equals("null") || longitude.equals("null")) {
                    latitude = "0.0";
                    longitude = "0.0";
                }
                calculateServiceCharge(calculateServiceChargeCallBack);
            } else {
                checkGPSforDiscount();
                if (latitude.equals("") || longitude.equals("")
                        || latitude.equals("null") || longitude.equals("null")) {
                    latitude = "0.0";
                    longitude = "0.0";
                }
                calculateServiceCharge(calculateServiceChargeCallBack);
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
                        servicecode,
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
                                showErrorGlobalDialogs(response.body().getMessage());
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                            }
                        } else {
                            showErrorGlobalDialogs();
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideProgressDialog();
                        setUpProgressLoaderDismissDialog();
                    }
                }

                @Override
                public void onFailure(Call<GetChargeResponse> call, Throwable t) {
                    showErrorGlobalDialogs();
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
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
                        servicecode,
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
                                    if (mPaymentType.contains("CARD")) {
                                        calculateEGHLServiceCharge(calculateEGHLServiceChargeSession);
                                    } else {
                                        showChargeDialogNew();
                                    }

                                } else {
                                    strtotalamount = String.valueOf((totalamounttopay + customerservicecharge - discount));
                                    if (Double.parseDouble(String.valueOf(totalamounttopay)) > 0) {
                                        hasdiscount = 1;
                                        if (mPaymentType.contains("CARD")) {
                                            calculateEGHLServiceCharge(calculateEGHLServiceChargeSession);
                                        } else {
                                            showChargeDialogNew();
                                        }
                                    }
                                }
                            } else if (response.body().getStatus().equals("005")) {
                                discountmessage = response.body().getMessage();
                                discount = response.body().getDiscount();
                                strdiscount = String.valueOf(discount);
                                strtotalamount = String.valueOf(totalamounttopay);
                                noDiscountResellerServiceArea();
                            } else {
                                showErrorGlobalDialogs(response.body().getMessage());
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                            }
                        } else {
                            showErrorGlobalDialogs();
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showErrorGlobalDialogs();
                        hideProgressDialog();
                        setUpProgressLoaderDismissDialog();
                    }
                }

                @Override
                public void onFailure(Call<DiscountResponse> call, Throwable t) {
                    showErrorGlobalDialogs();
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                }
            };

    //VOTING VIA GK
    private void prePurchase(Callback<String> prePurchaseCallback) {
        //Limits the two decimal places
        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(strtotalamount));
        strtotalamount = valuecheck;

        Call<String> prepurchase = RetrofitBuild.prePurchaseService(getViewContext())
                .prePurchaseCall(borrowerid,
                        strtotalamount,
                        userid,
                        imei,
                        sessionid,
                        authcode);
        prepurchase.enqueue(prePurchaseCallback);
    }

    private final Callback<String> prePurchaseSession = new Callback<String>() {

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {

                    vouchersession = response.body().toString();
                    String discountcheckifequal = String.valueOf(discount);
                    String discounttotalamounttopay = String.valueOf(totalamounttopay);

                    if (!vouchersession.isEmpty()) {
                        if (vouchersession.equals("001")) {
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                            showToast("Session: Invalid sessionid.", GlobalToastEnum.ERROR);
                        } else if (vouchersession.equals("error")) {
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.ERROR);
                        } else if (vouchersession.contains("<!DOCTYPE html>")) {
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.ERROR);
                        } else {
                            if (vouchersession.length() > 0) {
                                if (discounttotalamounttopay.equals(discountcheckifequal)) {
                                    showWarningGlobalDialogs("Invalid amount. Cannot Proceed.");
                                    hideProgressDialog();
                                    setUpProgressLoaderDismissDialog();
                                } else {
                                    totalamountcheck = Double.parseDouble(strtotalamount);
                                    proceedtoPayments();
                                }
                            } else {
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                showWarningGlobalDialogs("Invalid Voucher Session.");
                            }
                        }
                    } else {
                        hideProgressDialog();
                        setUpProgressLoaderDismissDialog();
                        showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            hideProgressDialog();
            setUpProgressLoaderDismissDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
        }
    };

    //VOTING VIA PAYMENT CHANNEL
    private void processVotingPaymentViaPartner(Callback<VotesPaymentResponse> processVotingPaymentViaPartnerCallBack) {
        Call<VotesPaymentResponse> processVotingPaymentViaPartner = RetrofitBuild.getVotesAPIService(getViewContext())
                .processVotingPaymentViaPartner(
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
                        String.valueOf(totalamounttopay),
                        ".",
                        strcustomerservicecharge
                );

        processVotingPaymentViaPartner.enqueue(processVotingPaymentViaPartnerCallBack);
    }

    private final Callback<VotesPaymentResponse> processVotingPaymentViaPartnerCallBack =
            new Callback<VotesPaymentResponse>() {

                @Override
                public void onResponse(Call<VotesPaymentResponse> call, Response<VotesPaymentResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                votespayment = response.body().getVotesPayment();
                                totalamountcheck = Double.parseDouble(strtotalamount);
                                PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_VOTES_FROM, true);
                                VotesBillingReferenceActivity.start(getViewContext(), votespayment, String.valueOf(totalamountcheck), "VotesPaymentOption");
                            } else if (response.body().getStatus().equals("307")) {
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                votespayment = response.body().getVotesPayment();
                                merchantreferenceno = votespayment.getMerchantReferenceNo();
                                paymentreferenceno = ".";
                                totalamountcheck = votespayment.getAmount();
                                txv_content.setText(response.body().getMessage());
                                layout_req_via_payment_channel.setVisibility(View.VISIBLE);
                                btn_paynow.setText("PAY NOW");
                            } else {
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                showErrorGlobalDialogs(response.body().getMessage());
                            }
                        } else {
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                            showErrorGlobalDialogs(response.body().getMessage());
                        }
                    } catch (Exception e) {
                        hideProgressDialog();
                        setUpProgressLoaderDismissDialog();
                        showErrorGlobalDialogs();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<VotesPaymentResponse> call, Throwable t) {
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                    showErrorGlobalDialogs();
                }
            };

    private void calculateEGHLServiceCharge(Callback<CalculateEGHLServiceChargeResponse> calculateEGHLServiceChargeCallback) {
        Call<CalculateEGHLServiceChargeResponse> calculatesc = RetrofitBuild.calculateEGHLServiceChargeService(getViewContext())
                .calculateEGHLServiceChargeCall(sessionid,
                        imei,
                        borrowerid,
                        userid,
                        authcode,
                        "ANDROID",
                        "Visa/Mastercard",
                        String.valueOf(totalamounttopay));
        calculatesc.enqueue(calculateEGHLServiceChargeCallback);
    }

    private final Callback<CalculateEGHLServiceChargeResponse> calculateEGHLServiceChargeSession = new Callback<CalculateEGHLServiceChargeResponse>() {

        @Override
        public void onResponse(Call<CalculateEGHLServiceChargeResponse> call, Response<CalculateEGHLServiceChargeResponse> response) {
            hideProgressDialog();
            setUpProgressLoaderDismissDialog();

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                    eghlservicecharge = Double.valueOf(response.body().getServiceCharge());
                    strcustomerservicecharge = String.valueOf(customerservicecharge + eghlservicecharge);
                    Double amountcheck = Double.parseDouble(strtotalamount);
                    strtotalamount = String.valueOf(amountcheck + eghlservicecharge);
                    showChargeDialogNew();
                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<CalculateEGHLServiceChargeResponse> call, Throwable t) {
            hideProgressDialog();
            setUpProgressLoaderDismissDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    //VOTING VIA CARD
    private void processVotingPaymentViaCard(Callback<RequestVoucherViaEghlPaymentResponse> processVotingPaymentViaCardCallBack) {
        Call<RequestVoucherViaEghlPaymentResponse> processVotingPaymentViaCard = RetrofitBuild.getVotesAPIService(getViewContext())
                .processVotingPaymentViaCard(
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
                        String.valueOf(totalamounttopay),
                        vouchersession,
                        strcustomerservicecharge,
                        "Visa/Mastercard"
                );

        processVotingPaymentViaCard.enqueue(processVotingPaymentViaCardCallBack);
    }

    private final Callback<RequestVoucherViaEghlPaymentResponse> processVotingPaymentViaCardCallBack =
            new Callback<RequestVoucherViaEghlPaymentResponse>() {

                @Override
                public void onResponse(Call<RequestVoucherViaEghlPaymentResponse> call, Response<RequestVoucherViaEghlPaymentResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();


                                eghlPayment = response.body().getEghlPayment();
                                PreferenceUtils.removePreference(getViewContext(), "EGHL_PAYMENT_TXN_NO");
                                PreferenceUtils.removePreference(getViewContext(), "EGHL_ORDER_TXN_NO");

                                PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_PAYMENT_TXN_NO", eghlPayment.getPaymenttxnno());
                                PreferenceUtils.saveStringPreference(getViewContext(), "EGHL_ORDER_TXN_NO", eghlPayment.getOrdertxnno());

                                merchantreferenceno = eghlPayment.getOrdertxnno();
                                paymentreferenceno = eghlPayment.getPaymenttxnno();


                                eghl = EGHL.getInstance();

                                String paymentMethod = "";

                                switch (mPaymentType) {
                                    case "BancNet": {
                                        paymentMethod = "DD";
                                        break;
                                    }
                                    default: {
                                        paymentMethod = "CC";
                                        break;
                                    }
                                }

                                params = new PaymentParams.Builder()
                                        .setTriggerReturnURL(true)
                                        .setMerchantCallbackUrl(eghlPayment.getMerchantReturnurl())
                                        .setMerchantReturnUrl(eghlPayment.getMerchantReturnurl())
                                        .setServiceId(eghlPayment.getServiceid())
                                        .setPassword(eghlPayment.getPassword())
                                        .setMerchantName(eghlPayment.getMerchantname())
                                        .setAmount(String.valueOf(strtotalamount))
                                        .setPaymentDesc("GoodKredit Voucher Payment")
                                        .setCustName(borrowername)
                                        .setCustEmail(borroweremail)
                                        .setCustPhone(borrowermobileno)
                                        .setPaymentId(eghlPayment.getPaymenttxnno())
                                        .setOrderNumber(eghlPayment.getOrdertxnno())
                                        .setCurrencyCode("PHP")
                                        .setLanguageCode("EN")
                                        .setPageTimeout("600")
                                        .setTransactionType("SALE")
                                        .setPaymentMethod(paymentMethod)
                                        .setPaymentGateway(eghlPayment.getPaymentgateway());

                                if (mPaymentType.equals("BancNet")) {
                                    final String[] url = {
                                            "secure2pay.ghl.com:8443",
                                            "bancnetonline.com"
                                    };
                                    params.setURlExclusion(url);
                                    params.setIssuingBank("BancNet");
                                }

                                Bundle paymentParams = params.build();
                                eghl.executePayment(paymentParams, VotesPaymentOptionActivity.this);

                            } else {
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                showErrorGlobalDialogs(response.body().getMessage());
                            }
                        } else {
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                            showErrorGlobalDialogs(response.body().getMessage());
                        }
                    } catch (Exception e) {
                        hideProgressDialog();
                        setUpProgressLoaderDismissDialog();
                        showErrorGlobalDialogs();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<RequestVoucherViaEghlPaymentResponse> call, Throwable t) {
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                    showErrorGlobalDialogs();
                }
            };

    //CANCELLATION OF VOTES
    private void cancelVotesRequest(Callback<GenericResponse> cancelVotesRequestCallBack) {
        Call<GenericResponse> cancelVotesRequest = RetrofitBuild.getVotesAPIService(getViewContext())
                .cancelVotesRequest(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        merchantreferenceno,
                        paymentreferenceno,
                        mPaymentType
                );

        cancelVotesRequest.enqueue(cancelVotesRequestCallBack);
    }

    private final Callback<GenericResponse> cancelVotesRequestCallBack =
            new Callback<GenericResponse>() {

                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                showCancellationSuccessfulDialog();
                                isEGHLCheckStatus = false;
                                isEGHLCancelPayment = false;
                                checkIfPaymenthasPending = false;
                            } else {
                                isEGHLCheckStatus = false;
                                isEGHLCancelPayment = false;
                                checkIfPaymenthasPending = false;
                                showErrorGlobalDialogs(response.body().getMessage());
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                            }
                        } else {
                            isEGHLCheckStatus = false;
                            isEGHLCancelPayment = false;
                            checkIfPaymenthasPending = false;
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                            showErrorGlobalDialogs();
                        }
                    } catch (Exception e) {
                        isEGHLCheckStatus = false;
                        isEGHLCancelPayment = false;
                        checkIfPaymenthasPending = false;
                        hideProgressDialog();
                        setUpProgressLoaderDismissDialog();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    isEGHLCheckStatus = false;
                    isEGHLCancelPayment = false;
                    checkIfPaymenthasPending = false;
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                    showErrorGlobalDialogs();
                }
            };

//    private void getTransactionSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            if (isEGHLCheckStatus) {
//                setUpProgressLoader();
//                setUpProgressLoaderMessageDialog("Processing request, Please wait...");
//            } else {
//                showProgressDialog(getViewContext(), "Processing request", " Please wait...");
//            }
//            createSession(getTransactionSessionCallBack);
//        } else {
//            hideProgressDialog();
//            showToast("Seems you are not connected to the internet. Please check your connection and try again. Thank you.", GlobalToastEnum.WARNING);
//        }
//    }
//
//    private Callback<String> getTransactionSessionCallBack = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            ResponseBody errBody = response.errorBody();
//
//            if (errBody == null) {
//                sessionid = response.body().toString();
//                authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                if (!sessionid.isEmpty()) {
//                    if (isEGHLCheckStatus) {
//                        validateVotingTransaction(validateVotingTransactionCallBack);
//                    } else if (isEGHLCancelPayment) {
//                        cancelVotesRequest(cancelVotesRequestCallBack);
//                    } else if (checkIfPaymenthasPending) {
//                        checkIfHasPendingVotingRequest(checkIfHasPendingVotingRequestCallBack);
//                    }
//                } else {
//                    showErrorGlobalDialogs();
//                    hideProgressDialog();
//                    setUpProgressLoaderDismissDialog();
//                }
//            } else {
//                showErrorGlobalDialogs();
//                hideProgressDialog();
//                setUpProgressLoaderDismissDialog();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//            hideProgressDialog();
//            setUpProgressLoaderDismissDialog();
//        }
//    };

    private void getTransactionSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            if (isEGHLCheckStatus) {
                setUpProgressLoader();
                setUpProgressLoaderMessageDialog("Processing request, Please wait...");
                validateVotingTransaction(validateVotingTransactionCallBack);
            } else if (isEGHLCancelPayment) {
                showProgressDialog(getViewContext(), "Processing request", " Please wait...");
                cancelVotesRequest(cancelVotesRequestCallBack);
            } else if (checkIfPaymenthasPending) {
                showProgressDialog(getViewContext(), "Processing request", " Please wait...");
                //checkIfHasPendingVotingRequest(checkIfHasPendingVotingRequestCallBack);
                checkIfHasPendingVotingRequestV2();
            }
        } else {
            setUpProgressLoaderDismissDialog();
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //CANCELLATION OF VOTES
    private void validateVotingTransaction(Callback<GenericResponse> validateVotingTransactionCallBack) {
        Call<GenericResponse> validateVotingTransaction = RetrofitBuild.getVotesAPIService(getViewContext())
                .validateVotingTransaction(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        merchantreferenceno,
                        paymentreferenceno,
                        mPaymentType
                );

        validateVotingTransaction.enqueue(validateVotingTransactionCallBack);
    }

    private final Callback<GenericResponse> validateVotingTransactionCallBack =
            new Callback<GenericResponse>() {

                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                isEGHLCheckStatus = false;
                                isEGHLCancelPayment = false;
                                checkIfPaymenthasPending = false;
                                currentdelaytime = 0;
                                String message = "";
                                if(participantid.trim().equals("DONATE")) {
                                    message = "You have donated succesfully with the reference no." + "\n" + response.body().getData();
                                } else {
                                    message = response.body().getMessage() + "\n" + response.body().getData();
                                }
                                if (distributorid.equals("") || distributorid.equals(".")
                                        || resellerid.equals("") || resellerid.equals(".")) {
                                    checkIfReseller = false;
                                } else {
                                    checkIfReseller = true;
                                }
                                showVotesEghlSuccessDialog(message);
                            } else if (response.body().getStatus().equals("005")) {
                                if (currentdelaytime < totaldelaytime) {
                                    //check transaction status here
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            currentdelaytime = currentdelaytime + 1000;
                                            isEGHLCheckStatus = true;
                                            getTransactionSession();
                                        }
                                    }, 1000);
                                } else {
                                    isEGHLCheckStatus = false;
                                    isEGHLCancelPayment = false;
                                    checkIfPaymenthasPending = false;
                                    currentdelaytime = 0;
                                    hideProgressDialog();
                                    setUpProgressLoaderDismissDialog();
                                    showVotesEghlOnProcessDialog(response.body().getMessage());
                                }
                            } else {
                                isEGHLCheckStatus = false;
                                isEGHLCancelPayment = false;
                                checkIfPaymenthasPending = false;
                                currentdelaytime = 0;
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                showErrorGlobalDialogs(response.body().getMessage());
                            }
                        } else {
                            isEGHLCheckStatus = false;
                            isEGHLCancelPayment = false;
                            checkIfPaymenthasPending = false;
                            currentdelaytime = 0;
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                            showErrorGlobalDialogs();
                        }
                    } catch (Exception e) {
                        isEGHLCheckStatus = false;
                        isEGHLCancelPayment = false;
                        checkIfPaymenthasPending = false;
                        currentdelaytime = 0;
                        hideProgressDialog();
                        setUpProgressLoaderDismissDialog();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    isEGHLCheckStatus = false;
                    isEGHLCancelPayment = false;
                    checkIfPaymenthasPending = false;
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                    showErrorGlobalDialogs();
                }
            };

    //CHECK IF HAS PENDING VOTES REQUEST
    private void checkIfHasPendingVotingRequest(Callback<VotesPaymentResponse> checkIfHasPendingVotingRequestCallBak) {
        Call<VotesPaymentResponse> checkIfHasPendingVotingRequest = RetrofitBuild.getVotesAPIService(getViewContext())
                .checkIfHasPendingVotingRequest(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        mPaymentType
                );

        checkIfHasPendingVotingRequest.enqueue(checkIfHasPendingVotingRequestCallBak);
    }

    private final Callback<VotesPaymentResponse> checkIfHasPendingVotingRequestCallBack =
            new Callback<VotesPaymentResponse>() {

                @Override
                public void onResponse(Call<VotesPaymentResponse> call, Response<VotesPaymentResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                checkIfPaymenthasPending = false;
                                if (participantid.trim().equals("DONATE")) {
                                    showDonateToPayValue();
                                } else {
                                    showVotesToPayValue();
                                }

                                //PAY VIA CARD
                            } else if (response.body().getStatus().equals("005")) {
                                checkIfPaymenthasPending = false;
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                votespayment = response.body().getVotesPayment();
                                merchantreferenceno = votespayment.getMerchantReferenceNo();
                                paymentreferenceno = votespayment.getPaymentTxnID();
                                totalamountcheck = votespayment.getAmount();
                                txv_content.setText(response.body().getMessage());
                                layout_req_via_payment_channel.setVisibility(View.VISIBLE);
                                btn_paynow.setText("OK");
                                //PAY VIA PARTNER
                            } else if (response.body().getStatus().equals("006")) {
                                checkIfPaymenthasPending = false;
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                votespayment = response.body().getVotesPayment();
                                merchantreferenceno = votespayment.getMerchantReferenceNo();
                                paymentreferenceno = ".";
                                totalamountcheck = votespayment.getAmount();
                                txv_content.setText(response.body().getMessage());
                                layout_req_via_payment_channel.setVisibility(View.VISIBLE);
                                btn_paynow.setText("PAYNOW");
                            } else {
                                checkIfPaymenthasPending = false;
                                hideProgressDialog();
                                setUpProgressLoaderDismissDialog();
                                showErrorGlobalDialogs(response.body().getMessage());
                            }
                        } else {
                            checkIfPaymenthasPending = false;
                            hideProgressDialog();
                            setUpProgressLoaderDismissDialog();
                            showErrorGlobalDialogs();
                        }
                    } catch (Exception e) {
                        checkIfPaymenthasPending = false;
                        hideProgressDialog();
                        setUpProgressLoaderDismissDialog();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<VotesPaymentResponse> call, Throwable t) {
                    checkIfPaymenthasPending = false;
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                    showErrorGlobalDialogs();
                }
            };

    //--------------------ON START AND OVERRIDE-----------------------------
    private TextWatcher numberofVotesTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            edtNumberofVotesString = s.toString();
            if (txvAmounttoPay != null) {
                if (!edtNumberofVotesString.trim().equals("")) {
                    Double numberofvote = Double.parseDouble(edtNumberofVotesString);
                    Double price = Double.parseDouble(pricepervote);
                    totalamount = numberofvote * price;
                    txvAmounttoPay.setText(CommonFunctions.currencyFormatter(String.valueOf(totalamount)));
                } else {
                    txvAmounttoPay.setText("");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher donateTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            edtNumberofVotesString = s.toString();
            if (!edtNumberofVotesString.trim().equals("")) {
                Double amount = Double.parseDouble(edtNumberofVotesString);
                totalamount = amount;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public static void start(Context context, int index, Bundle args) {
        Intent intent = new Intent(context, VotesPaymentOptionActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == EGHL.REQUEST_PAYMENT) {
            switch (resultCode) {
                case EGHL.TRANSACTION_SUCCESS:
                    String status = data.getStringExtra(EGHL.TXN_STATUS);
                    String message = data.getStringExtra(EGHL.TXN_MESSAGE);

                    //check transaction status here
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentdelaytime = currentdelaytime + 1000;
                            isEGHLCheckStatus = true;
                            isEGHLCancelPayment = false;
                            getTransactionSession();
                        }
                    }, 1000);

                    break;
                case EGHL.TRANSACTION_CANCELLED:
                    showVotesEghlFailedDialog("Payment Transaction Cancelled");

                    isEGHLCheckStatus = false;
                    isEGHLCancelPayment = true;
                    getTransactionSession();
                    break;
                case EGHL.TRANSACTION_FAILED:
                    showVotesEghlFailedDialog("Payment Transaction Failed");

                    isEGHLCheckStatus = false;
                    isEGHLCancelPayment = true;
                    getTransactionSession();
                    break;
                default:
                    showVotesEghlFailedDialog("Payment Transaction Failed");

                    isEGHLCheckStatus = false;
                    isEGHLCancelPayment = true;
                    getTransactionSession();
                    break;
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                try {
                    if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                    int selectedId = rg_payment_options.getCheckedRadioButtonId();
                    rb_choosed_option = findViewById(selectedId);
                    String strChoosedOption = rb_choosed_option.getText().toString().trim().toUpperCase();

                    if (!strChoosedOption.isEmpty()) {
                        if (strChoosedOption.contains("VOUCHER")) {
                            mPaymentType = "PAY VIA GK";
                            String checkamount = pricepervote;
                            if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                                showToast("Something went wrong. Please contact support.", GlobalToastEnum.WARNING);
                            } else {
                                totalamount = Double.parseDouble(checkamount);
                                checkIfPaymenthasPending = true;
                                getTransactionSession();
                            }
                        } else if (strChoosedOption.contains("PAYMENT")) {
                            mPaymentType = "PAY VIA PARTNER";
                            String checkamount = pricepervote;
                            if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                                showToast("Something went wrong. Please contact support.", GlobalToastEnum.WARNING);
                            } else {
                                totalamount = Double.parseDouble(checkamount);
                                checkIfPaymenthasPending = true;
                                getTransactionSession();
                            }
                        } else if (strChoosedOption.contains("CREDIT")) {
                            mPaymentType = "PAY VIA CARD";
                            String checkamount = pricepervote;
                            if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                                showToast("Something went wrong. Please contact support.", GlobalToastEnum.WARNING);
                            } else {
                                totalamount = Double.parseDouble(checkamount);
                                checkIfPaymenthasPending = true;
                                getTransactionSession();
                            }
                        } else {
                            showToast("Something went wrong. Please contact support.", GlobalToastEnum.WARNING);
                        }
                    } else {
                        showToast("Please choose a payment option", GlobalToastEnum.WARNING);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getLocalizedMessage();
                    e.getMessage();
                    String checkamount = pricepervote;
                    if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                        showToast("Something went wrong. Please contact support.", GlobalToastEnum.WARNING);
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
                if (mPaymentType.contains("PARTNER")) {
                    layout_req_via_payment_channel.setVisibility(View.GONE);
                    PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_VOTES_FROM, true);
                    VotesBillingReferenceActivity.start(getViewContext(), votespayment, String.valueOf(totalamountcheck), "VotesPaymentOption");
                } else if (mPaymentType.contains("CARD")) {
                    layout_req_via_payment_channel.setVisibility(View.GONE);
                }
                break;
            }
        }
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


    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    private void checkIfHasPendingVotingRequestV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("paymenttype",mPaymentType);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "checkIfHasPendingVotingRequestV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        checkIfHasPendingVotingRequestV2Object(checkIfHasPendingVotingRequestV2Callback);


    }
    private void checkIfHasPendingVotingRequestV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getVotesV2API(getViewContext())
                .checkIfHasPendingVotingRequestV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  checkIfHasPendingVotingRequestV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    checkIfPaymenthasPending = false;
                    if (participantid.trim().equals("DONATE")) {
                        showDonateToPayValue();
                    } else {
                        showVotesToPayValue();
                    }
                    //PAY VIA CARD
                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else if(response.body().getStatus().equals("005") && !message.contains("Unable")){

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    checkIfPaymenthasPending = false;
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                    votespayment = new Gson().fromJson(data,VotesPayment.class);
                    merchantreferenceno = votespayment.getMerchantReferenceNo();
                    paymentreferenceno = votespayment.getPaymentTxnID();
                    totalamountcheck = votespayment.getAmount();
                    txv_content.setText(message);
                    layout_req_via_payment_channel.setVisibility(View.VISIBLE);
                    btn_paynow.setText("OK");
                    //PAY VIA PARTNER
                }else if (response.body().getStatus().equals("006")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    checkIfPaymenthasPending = false;
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                    votespayment =  new Gson().fromJson(data,VotesPayment.class);
                    merchantreferenceno = votespayment.getMerchantReferenceNo();
                    paymentreferenceno = ".";
                    totalamountcheck = votespayment.getAmount();
                    txv_content.setText(message);
                    layout_req_via_payment_channel.setVisibility(View.VISIBLE);
                    btn_paynow.setText("PAYNOW");
                }
                else{
                    showErrorGlobalDialogs(message);
                    checkIfPaymenthasPending = false;
                    hideProgressDialog();
                    setUpProgressLoaderDismissDialog();
                }

            }else{
                checkIfPaymenthasPending = false;
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
                showErrorGlobalDialogs();
            }

        }
        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            checkIfPaymenthasPending = false;
            hideProgressDialog();
            setUpProgressLoaderDismissDialog();
            showErrorGlobalDialogs();
        }
    };



}
