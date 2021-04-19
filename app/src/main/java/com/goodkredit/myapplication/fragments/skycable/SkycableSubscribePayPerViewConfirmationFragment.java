package com.goodkredit.myapplication.fragments.skycable;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.billspayment.BillsPaymentBillerDetailsActivity;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.adapter.paramount.ParamountPaymentsVoucherRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.ParamountVouchers;
import com.goodkredit.myapplication.bean.SkycablePPVHistory;
import com.goodkredit.myapplication.bean.SkycablePPVSubscription;
import com.goodkredit.myapplication.bean.SkycableSOA;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.responses.CheckTransactionStatusResponse;
import com.goodkredit.myapplication.responses.paramount.GetParamountPaymentVouchersResponse;
import com.goodkredit.myapplication.responses.skycable.CheckIfConfigIsAvailableResponse;
import com.goodkredit.myapplication.responses.skycable.SubscribeSkycablePpvResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkycableSubscribePayPerViewConfirmationFragment extends BaseFragment implements View.OnClickListener {

    private SkycablePPVSubscription skycablePPVSubscription = null;
    private V2Subscriber v2Subscriber = null;
    private String vouchersession = "";

    private TextView txvSkycableAccountNo;
    private TextView txvName;
    private TextView txvMobileNo;
    private TextView txvEmailAddress;
    private TextView txvAddress;
    private TextView txvCity;
    private ImageView ppvImage;
    private TextView ppvTitle;
    private TextView ppvDescription;
    private TextView ppvAmount;
    private LinearLayout linearApprovalCodeLayout;
    private TextView txvApprovalCode;

    private boolean isPaymentsCall = false;

    private MaterialDialog mDialog;
    private TextView txvMessage;

//    private MaterialDialog mLoaderDialog;
//    private TextView mLoaderDialogMessage;
//    private TextView mLoaderDialogTitle;
//    private ImageView mLoaderDialogImage;
//    private TextView mLoaderDialogClose;
//    private TextView mLoaderDialogRetry;
//    private RelativeLayout buttonLayout;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private DatabaseHandler mdb;
//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String guarantorid;

    private String ppvid;
    private String skycableaccountno;
    private String borrowername;
    private String customerfirstname;
    private String customerlastname;
    private String customermobilenumber;
    private String customeremailaddress;
    private String customeraddress;
    private String city;
    private String amount;
    private String merchantid;
    private String longitude;
    private String latitude;
    private String preconsummationsessionid;

    private int currentdelaytime = 0;
    private int totaldelaytime = 10000;
    private boolean isStatusChecked = false;
    private String transactionno = "";

    //PAYMENT DETAILS
    private String paymentAmount = "";
    private String paymentServiceCharge = "";
    private String paymentAmountPaid = "";
    private TextView txvAmount;
    private TextView txvServiceCharge;
    private TextView txvAmountPaid;

    private SkycablePPVHistory skycablePPVHistory = null;

    private LinearLayout noteLayout;

    private RecyclerView recyclerViewVouchers;
    private ProgressBar pbrecyclerProgress;
    private ParamountPaymentsVoucherRecyclerAdapter mVouchersAdapter;
    private LinearLayout voucher_details_layout;

    private TextView txvPaymentDetailsLabel;
    private LinearLayout linearPaymentDetailsLayout;
    private LinearLayout linearButtonsLayout;
    private Button btnPay;
    private MaterialDialog mConfirmDialog;

    private TextView txvConfirmAmount;
    private TextView txvConfirmTotalAmount;
    private TextView txvConfirmServiceCharge;

    private boolean isConfigAvailable = false;
    private String servicecode;

    //UNIFIED SESSION
    private String sessionid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_subscribe_pay_per_view_confirmation, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        if (isAdded()) {
            getActivity().setTitle("Subscribe");
        }

        noteLayout = (LinearLayout) view.findViewById(R.id.noteLayout);

        txvAmount = (TextView) view.findViewById(R.id.txvAmount);
        txvServiceCharge = (TextView) view.findViewById(R.id.txvServiceCharge);
        txvAmountPaid = (TextView) view.findViewById(R.id.txvAmountPaid);

        txvSkycableAccountNo = (TextView) view.findViewById(R.id.txvSkycableAccountNo);
        txvName = (TextView) view.findViewById(R.id.txvName);
        txvMobileNo = (TextView) view.findViewById(R.id.txvMobileNo);
        txvEmailAddress = (TextView) view.findViewById(R.id.txvEmailAddress);
        txvAddress = (TextView) view.findViewById(R.id.txvAddress);
        txvCity = (TextView) view.findViewById(R.id.txvCity);
        ppvImage = (ImageView) view.findViewById(R.id.ppvImage);
        ppvTitle = (TextView) view.findViewById(R.id.ppvTitle);
        ppvDescription = (TextView) view.findViewById(R.id.ppvDescription);
        ppvAmount = (TextView) view.findViewById(R.id.ppvAmount);

        linearApprovalCodeLayout = (LinearLayout) view.findViewById(R.id.approvalCodeLayout);
        linearApprovalCodeLayout.setVisibility(View.GONE);
        txvApprovalCode = (TextView) view.findViewById(R.id.txvApprovalCode);

        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        if (getArguments().getString("TYPE").equals("HISTORY") || getArguments().getString("TYPE").equals("ACTIONREQUIRED")) {
            recyclerViewVouchers = (RecyclerView) view.findViewById(R.id.recyclerViewVouchers);
            recyclerViewVouchers.setLayoutManager(new LinearLayoutManager(getViewContext()));
            recyclerViewVouchers.setNestedScrollingEnabled(false);
            mVouchersAdapter = new ParamountPaymentsVoucherRecyclerAdapter(getViewContext());
            recyclerViewVouchers.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null));
            recyclerViewVouchers.setAdapter(mVouchersAdapter);
            pbrecyclerProgress = (ProgressBar) view.findViewById(R.id.pbrecyclerProgress);
            voucher_details_layout = (LinearLayout) view.findViewById(R.id.voucher_details_layout);
        }

        txvPaymentDetailsLabel = (TextView) view.findViewById(R.id.txvPaymentDetailsLabel);
        linearPaymentDetailsLayout = (LinearLayout) view.findViewById(R.id.linearPaymentDetailsLayout);
        linearButtonsLayout = (LinearLayout) view.findViewById(R.id.linearButtonsLayout);
        btnPay = (Button) view.findViewById(R.id.btnPay);
        btnPay.setOnClickListener(this);

        //SETUP LOADER DIALOG
//        setupLoaderDialog();
//        setUpSuccessDialog();
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        guarantorid = mdb.getGuarantorID(mdb);
        v2Subscriber = mdb.getSubscriber(mdb);
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "skycablebillcode");

        sessionid = PreferenceUtils.getSessionID(getViewContext());

        if (getArguments().getString("TYPE").equals("CONFIRM")) {
            vouchersession = getArguments().getString("vouchersession");
            skycablePPVSubscription = getArguments().getParcelable("PPVSUBSCRIPTION");
            paymentAmount = getArguments().getString("SKYCABLEAMOUNT");
            paymentServiceCharge = getArguments().getString("SKYCABLESERVICECHARGE");
            paymentAmountPaid = getArguments().getString("SKYCABLEAMOUNTPAID");

            borrowername = v2Subscriber.getBorrowerName();

            preconsummationsessionid = vouchersession;

            linearApprovalCodeLayout.setVisibility(View.GONE);
            noteLayout.setVisibility(View.VISIBLE);
        } else if (getArguments().getString("TYPE").equals("HISTORY") || getArguments().getString("TYPE").equals("ACTIONREQUIRED")) {
            skycablePPVHistory = getArguments().getParcelable("skycablePPVHistory");

            paymentAmount = String.valueOf(skycablePPVHistory.getAmount());
            paymentServiceCharge = String.valueOf(skycablePPVHistory.getCustomerServiceCharge());
            paymentAmountPaid = String.valueOf(skycablePPVHistory.getTotalAmountPaid());

            Logger.debug("antonhttp", "skycablePPVHistory: " + new Gson().toJson(skycablePPVHistory));

            skycablePPVSubscription = new SkycablePPVSubscription(skycablePPVHistory.getSkyCableAccountNo(), skycablePPVHistory.getCustomerFirstName(), skycablePPVHistory.getCustomerLastName(), skycablePPVHistory.getCustomerMobileNumber(), skycablePPVHistory.getCustomerEmailAddress(), skycablePPVHistory.getCustomerAddress(), skycablePPVHistory.getCity(), skycablePPVHistory.getAmount(), skycablePPVHistory.getMerchantID(), skycablePPVHistory.getLongitude(), skycablePPVHistory.getLatitude(), skycablePPVHistory.getPPVID(), skycablePPVHistory.getPPVName(), skycablePPVHistory.getPPVDescription(), skycablePPVHistory.getImageURL());

            linearApprovalCodeLayout.setVisibility(View.VISIBLE);
            txvApprovalCode.setText(skycablePPVHistory.getGKPaymentReferenceNo());
            noteLayout.setVisibility(View.GONE);

            isPaymentsCall = true;

            List<ParamountVouchers> paramountVouchers = mdb.getSkycablePaymentVouchers(mdb, skycablePPVHistory.getGKPaymentReferenceNo());
            if (paramountVouchers.size() == 0) {
                //call
                getSession();
            } else {
                //update adapter data here
                updatePaymentsData(paramountVouchers);
            }

            if (skycablePPVHistory.getStatus().equals("PAID")) {
                txvPaymentDetailsLabel.setVisibility(View.VISIBLE);
                linearPaymentDetailsLayout.setVisibility(View.VISIBLE);
                linearButtonsLayout.setVisibility(View.GONE);
            } else if (skycablePPVHistory.getStatus().equals("FOR PAYMENT")) {
                setUpConfirmDialog();
                linearButtonsLayout.setVisibility(View.VISIBLE);
                txvPaymentDetailsLabel.setVisibility(View.GONE);
                linearPaymentDetailsLayout.setVisibility(View.GONE);
                btnPay.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "PAY " + CommonFunctions.currencyFormatter(String.valueOf(skycablePPVHistory.getTotalAmountPaid()))));
            } else {
                txvPaymentDetailsLabel.setVisibility(View.GONE);
                linearPaymentDetailsLayout.setVisibility(View.GONE);
                linearButtonsLayout.setVisibility(View.GONE);
            }

        }

        ppvid = skycablePPVSubscription.getPPVID();
        skycableaccountno = skycablePPVSubscription.getSkyCableAccountNo();
        customerfirstname = skycablePPVSubscription.getCustomerFirstName();
        customerlastname = skycablePPVSubscription.getCustomerLastName();
        customermobilenumber = skycablePPVSubscription.getCustomerMobileNumber();
        customeremailaddress = skycablePPVSubscription.getCustomerEmailAddress();
        customeraddress = skycablePPVSubscription.getCustomerAddress();
        city = skycablePPVSubscription.getCity();
        amount = String.valueOf(skycablePPVSubscription.getAmount());
        merchantid = skycablePPVSubscription.getMerchantID();
        longitude = skycablePPVSubscription.getLongitude();
        latitude = skycablePPVSubscription.getLatitude();


        //PAYMENT DETAILS
        txvAmount.setText(CommonFunctions.currencyFormatter(paymentAmount));
        txvServiceCharge.setText(CommonFunctions.currencyFormatter(paymentServiceCharge));
        txvAmountPaid.setText(CommonFunctions.currencyFormatter(paymentAmountPaid));

        txvSkycableAccountNo.setText(skycablePPVSubscription.getSkyCableAccountNo());
        txvName.setText(skycablePPVSubscription.getCustomerFirstName() + " " + skycablePPVSubscription.getCustomerLastName());
        txvMobileNo.setText(skycablePPVSubscription.getCustomerMobileNumber());
        txvEmailAddress.setText(skycablePPVSubscription.getCustomerEmailAddress());
        txvAddress.setText(skycablePPVSubscription.getCustomerAddress());
        txvCity.setText(skycablePPVSubscription.getCity());
        ppvTitle.setText(CommonFunctions.replaceEscapeData(skycablePPVSubscription.getPPVName()));
        ppvDescription.setText(CommonFunctions.replaceEscapeData(skycablePPVSubscription.getPPVDescription()));
        ppvAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(skycablePPVSubscription.getAmount())));

        Glide.with(getViewContext())
                .asBitmap()
                .load(skycablePPVSubscription.getImageURL())
                .apply(new RequestOptions()
                        .fitCenter())
                .into(new BaseTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        ppvImage.setImageBitmap(resource);
                    }

                    @Override
                    public void getSize(SizeReadyCallback cb) {
                        cb.onSizeReady(CommonFunctions.getScreenWidth(mContext), CommonFunctions.getScreenHeight(mContext));
                    }

                    @Override
                    public void removeCallback(SizeReadyCallback cb) {

                    }
                });

    }

//    private void setupLoaderDialog() {
//        mLoaderDialog = new MaterialDialog.Builder(getViewContext())
//                .cancelable(false)
//                .customView(R.layout.dialog_custom_animated, false)
//                .build();
//
//        View view = mLoaderDialog.getCustomView();
//        if (view != null) {
//            mLoaderDialogMessage = (TextView) view.findViewById(R.id.mLoaderDialogMessage);
//            mLoaderDialogTitle = (TextView) view.findViewById(R.id.mLoaderDialogTitle);
//            mLoaderDialogImage = (ImageView) view.findViewById(R.id.mLoaderDialogImage);
//            mLoaderDialogClose = (TextView) view.findViewById(R.id.mLoaderDialogClose);
//            mLoaderDialogClose.setOnClickListener(this);
//            mLoaderDialogRetry = (TextView) view.findViewById(R.id.mLoaderDialogRetry);
//            mLoaderDialogRetry.setVisibility(View.GONE);
//            mLoaderDialogRetry.setOnClickListener(this);
//            buttonLayout = (RelativeLayout) view.findViewById(R.id.buttonLayout);
//            buttonLayout.setVisibility(View.GONE);
//
//            mLoaderDialogTitle.setText("Processing...");
//
//            Glide.with(getViewContext())
//                    .load(R.drawable.progressloader)
//                    .into(mLoaderDialogImage);
//        }
//    }

    private void setUpSuccessDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_success_order_payment, false)
                .build();
        View view = mDialog.getCustomView();

        TextView txvSubscribeAgain = (TextView) view.findViewById(R.id.txvSubscribeAgain);
        txvSubscribeAgain.setOnClickListener(this);
        TextView txvGoToHistory = (TextView) view.findViewById(R.id.txvGoToHistory);
        txvGoToHistory.setOnClickListener(this);

        txvMessage = (TextView) view.findViewById(R.id.message);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (getArguments().getString("TYPE").equals("CONFIRM")) {
            inflater.inflate(R.menu.menu_confirm, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (getArguments().getString("TYPE").equals("CONFIRM")) {
                    getActivity().finish();
                } else {
                    if (getArguments().getString("TYPE").equals("HISTORY")) {
                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        ((SkyCableActivity) getViewContext()).displayView(8, null);
                    } else {
                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        ((SkyCableActivity) getViewContext()).displayView(4, null);
                    }

                }
                return true;
            }
            case R.id.confirm: {

                new MaterialDialog.Builder(getViewContext())
                        .content("You are about to pay your request.")
                        .cancelable(false)
                        .negativeText("Close")
                        .positiveText("Proceed")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                currentdelaytime = 0;
                                isPaymentsCall = false;
                                isStatusChecked = false;
                                getSession();

                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        })
                        .show();

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            if (!isPaymentsCall) {
//                mLoaderDialog.show();
                setUpProgressLoader();
            } else {
                pbrecyclerProgress.setVisibility(View.VISIBLE);
            }

//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isStatusChecked) {
//                        mLoaderDialogMessage.setText("Checking status, Please wait...");
                setUpProgressLoaderMessageDialog("Checking status, Please wait...");
                checkTransactionStatus(checkTransactionStatusSession);
            } else if (isPaymentsCall) {
                getSkycableConsumedVouchersByTxnNo(getSkycableConsumedVouchersByTxnNoSession);
            } else if (isConfigAvailable) {
                checkIfConfigIsAvailable(checkIfConfigIsAvailableSession);
            } else {
//                        mLoaderDialogMessage.setText("Processing request, Please wait...");
                setUpProgressLoaderMessageDialog("Processing request, Please wait...");
                subscribeSkycablePpv(subscribeSkycablePpvSession);
            }

        } else {
//            if (mLoaderDialog != null) {
//                mLoaderDialog.dismiss();
//            }
            setUpProgressLoaderDismissDialog();
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }

    }

//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
////                    if (mLoaderDialog != null) {
////                        mLoaderDialog.dismiss();
////                    }
//                    setUpProgressLoaderDismissDialog();
//
//                    if (isPaymentsCall) {
//                        pbrecyclerProgress.setVisibility(View.GONE);
//                    }
//
//                    hideProgressDialog();
//                    showToast("Session: Invalid session.", GlobalToastEnum.ERROR);
//                } else if (responseData.equals("error")) {
////                    if (mLoaderDialog != null) {
////                        mLoaderDialog.dismiss();
////                    }
//                    setUpProgressLoaderDismissDialog();
//
//                    if (isPaymentsCall) {
//                        pbrecyclerProgress.setVisibility(View.GONE);
//                    }
//
//                    hideProgressDialog();
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
////                    if (mLoaderDialog != null) {
////                        mLoaderDialog.dismiss();
////                    }
//                    setUpProgressLoaderDismissDialog();
//
//                    if (isPaymentsCall) {
//                        pbrecyclerProgress.setVisibility(View.GONE);
//                    }
//
//                    hideProgressDialog();
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    if (isStatusChecked) {
////                        mLoaderDialogMessage.setText("Checking status, Please wait...");
//                        setUpProgressLoaderMessageDialog("Checking status, Please wait...");
//                        checkTransactionStatus(checkTransactionStatusSession);
//                    } else if (isPaymentsCall) {
//                        getSkycableConsumedVouchersByTxnNo(getSkycableConsumedVouchersByTxnNoSession);
//                    } else if (isConfigAvailable) {
//                        checkIfConfigIsAvailable(checkIfConfigIsAvailableSession);
//                    } else {
////                        mLoaderDialogMessage.setText("Processing request, Please wait...");
//                        setUpProgressLoaderMessageDialog("Processing request, Please wait...");
//                        subscribeSkycablePpv(subscribeSkycablePpvSession);
//                    }
//                }
//            } else {
////                if (mLoaderDialog != null) {
////                    mLoaderDialog.dismiss();
////                }
//                setUpProgressLoaderDismissDialog();
//
//                if (isPaymentsCall) {
//                    pbrecyclerProgress.setVisibility(View.GONE);
//                }
//
//                hideProgressDialog();
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
////            if (mLoaderDialog != null) {
////                mLoaderDialog.dismiss();
////            }
//            setUpProgressLoaderDismissDialog();
//
//            if (isPaymentsCall) {
//                pbrecyclerProgress.setVisibility(View.GONE);
//            }
//
//            hideProgressDialog();
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//        }
//    };

    private void checkIfConfigIsAvailable(Callback<CheckIfConfigIsAvailableResponse> checkIfConfigIsAvailableCallback) {
        Call<CheckIfConfigIsAvailableResponse> checkconfig = RetrofitBuild.checkIfConfigIsAvailableService(getViewContext())
                .checkIfConfigIsAvailableCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        servicecode,
                        "Pay Per View");
        checkconfig.enqueue(checkIfConfigIsAvailableCallback);
    }

    private final Callback<CheckIfConfigIsAvailableResponse> checkIfConfigIsAvailableSession = new Callback<CheckIfConfigIsAvailableResponse>() {

        @Override
        public void onResponse(Call<CheckIfConfigIsAvailableResponse> call, Response<CheckIfConfigIsAvailableResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

//                    if (mLoaderDialog != null) {
//                        mLoaderDialog.dismiss();
//                    }
                    setUpProgressLoaderDismissDialog();

                    if (mConfirmDialog != null) {
                        mConfirmDialog.dismiss();
                    }

                    Logger.debug("antonhttp", "BILLER PPV: " + new Gson().toJson(skycablePPVHistory));

                    Intent intent = new Intent(getViewContext(), BillsPaymentBillerDetailsActivity.class);
                    intent.putExtra("BILLCODE", PreferenceUtils.getStringPreference(getViewContext(), "skycablebillcode"));
                    intent.putExtra("SPBILLCODE", PreferenceUtils.getStringPreference(getViewContext(), "skycablespbillcode"));
                    intent.putExtra("SPBILLERACCOUNTNO", "");
                    intent.putExtra("BILLNAME", PreferenceUtils.getStringPreference(getViewContext(), "skycablebillname"));
                    intent.putExtra("FROM", "BILLERS");
                    intent.putExtra("PPV", true);
                    intent.putExtra("skycableSOA", new SkycableSOA(null, null, skycablePPVHistory.getSkyCableAccountNo(), skycablePPVHistory.getCustomerFirstName() + " " + skycablePPVHistory.getCustomerLastName(), null, 0, 0, skycablePPVHistory.getTotalAmountPaid(), null, null, null, null));
                    getViewContext().startActivity(intent);

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<CheckIfConfigIsAvailableResponse> call, Throwable t) {
            isConfigAvailable = false;
            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
        }
    };

    private void subscribeSkycablePpv(Callback<SubscribeSkycablePpvResponse> addParamountQueueCallback) {
        Call<SubscribeSkycablePpvResponse> subscribeskycableppv = RetrofitBuild.subscribeSkycablePpvService(getViewContext())
                .subscribeSkycablePpvCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        ppvid,
                        skycableaccountno,
                        borrowername,
                        customerfirstname,
                        customerlastname,
                        customermobilenumber,
                        customeremailaddress,
                        customeraddress,
                        city,
                        amount,
                        merchantid,
                        longitude,
                        latitude,
                        preconsummationsessionid,
                        PreferenceUtils.getStringPreference(getViewContext(), "skycablebillcode"));
        subscribeskycableppv.enqueue(addParamountQueueCallback);
    }

    private final Callback<SubscribeSkycablePpvResponse> subscribeSkycablePpvSession = new Callback<SubscribeSkycablePpvResponse>() {

        @Override
        public void onResponse(Call<SubscribeSkycablePpvResponse> call, Response<SubscribeSkycablePpvResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    if (mdb != null) {

//                        mLoaderDialog.dismiss();
//                        transactionno = response.body().getSubscriptionTxnID();

//                        mDialog.show();
//
//                        String msg = "Your SKYCABLE Pay-Per-View subscription is now ON PROCESS with the <b>Approval Code</b> " + transactionno + ". You will receive a notification once it has been APPROVED. Please monitor your request in Usage Transactions. <br> <br> Thank you for using GoodKredit.";
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            txvMessage.setText(Html.fromHtml(msg, Html.FROM_HTML_MODE_COMPACT));
//                        } else {
//                            txvMessage.setText(Html.fromHtml(msg));
//                        }

                        //check transaction status here
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                currentdelaytime = currentdelaytime + 1000;
//                                isStatusChecked = true;
//                                getSession();
//                            }
//                        }, 1000);

                        setUpProgressLoaderDismissDialog();
                        transactionno = response.body().getSubscriptionTxnID();

                        String msg = "Your SKYCABLE Pay-Per-View subscription is now ON PROCESS with the <b>Approval Code</b> "
                                + transactionno + ". You will receive a notification once it has been APPROVED. " +
                                "Please monitor your request in Usage Transactions. <br> <br> Thank you for using GoodKredit.";

                        String btndoubletype = "GOTOHISTORY";
                        PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SKY_BUTTON, btndoubletype);

                        showGlobalDialogs(msg, "close",
                                ButtonTypeEnum.DOUBLE, true, true, DialogTypeEnum.NOTICE);
                    }

                } else {
//                    mLoaderDialog.dismiss();
                    setUpProgressLoaderDismissDialog();
                    showError(response.body().getMessage());
                }
            } else {
//                mLoaderDialog.dismiss();
                setUpProgressLoaderDismissDialog();
                showError();
            }

        }

        @Override
        public void onFailure(Call<SubscribeSkycablePpvResponse> call, Throwable t) {
//            mLoaderDialog.dismiss();
            setUpProgressLoaderDismissDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txvSubscribeAgain: {
                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                Intent intent = new Intent(getViewContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                startActivity(intent);
                break;
            }
            case R.id.txvGoToHistory: {
//                if (mDialog != null) {
//                    mDialog.dismiss();
//                }

                getActivity().finish();

                Intent intent = new Intent(getViewContext(), SkyCableActivity.class);
                intent.putExtra("index", 8);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;
            }
            case R.id.btnPay: {
                mConfirmDialog.show();
                txvConfirmAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(skycablePPVHistory.getAmount())));
                txvConfirmServiceCharge.setText(CommonFunctions.currencyFormatter(String.valueOf(skycablePPVHistory.getCustomerServiceCharge())));
                txvConfirmTotalAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(skycablePPVHistory.getTotalAmountPaid())));
                break;
            }
            case R.id.txvCloseDialog: {
                mConfirmDialog.dismiss();
                break;
            }
            case R.id.txvProceed: {
                isPaymentsCall = false;
                isStatusChecked = false;
                isConfigAvailable = true;
                getSession();
                break;
            }
        }
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    private void checkTransactionStatus(Callback<CheckTransactionStatusResponse> checkTransactionStatusCallback) {
        Call<CheckTransactionStatusResponse> checktxnstatus = RetrofitBuild.checkTransactionStatusService(getViewContext())
                .checkTransactionStatusCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        transactionno);
        checktxnstatus.enqueue(checkTransactionStatusCallback);
    }

    private final Callback<CheckTransactionStatusResponse> checkTransactionStatusSession = new Callback<CheckTransactionStatusResponse>() {

        @Override
        public void onResponse(Call<CheckTransactionStatusResponse> call, Response<CheckTransactionStatusResponse> response) {
            ResponseBody errorBody = response.errorBody();

            try {
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {

//                        buttonLayout.setVisibility(View.VISIBLE);
//
//                        if (response.body().getTxnStatus().equals("APPROVED")) {
//                            mLoaderDialogRetry.setVisibility(View.GONE);
//                            mLoaderDialogTitle.setText("SUCCESSFUL TRANSACTION");
//                        } else if (response.body().getTxnStatus().equals("TIMEOUT")) {
//                            mLoaderDialogRetry.setVisibility(View.GONE);
//                            mLoaderDialogTitle.setText("TIMEOUT");
//                        } else {
//                            mLoaderDialogRetry.setVisibility(View.VISIBLE);
//                            mLoaderDialogTitle.setText("DECLINED TRANSACTION");
//                        }
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            mLoaderDialogMessage.setText(Html.fromHtml(response.body().getRemarks(), Html.FROM_HTML_MODE_COMPACT));
//                        } else {
//                            mLoaderDialogMessage.setText(Html.fromHtml(response.body().getRemarks()));
//                        }
//
//                        mLoaderDialogImage.setVisibility(View.GONE);
//                        mLoaderDialogClose.setVisibility(View.VISIBLE);
                        setUpProgressLoaderDismissDialog();
                        if (response.body().getTxnStatus().equals("APPROVED")) {
                            showGlobalDialogs(response.body().getRemarks(), "close",
                                    ButtonTypeEnum.SINGLE, true, true, DialogTypeEnum.SUCCESS);
                        } else if (response.body().getTxnStatus().equals("TIMEOUT")) {
                            showGlobalDialogs(response.body().getRemarks(), "close",
                                    ButtonTypeEnum.SINGLE, true, true, DialogTypeEnum.TIMEOUT);
                        } else {
                            showGlobalDialogs(response.body().getRemarks(), "retry",
                                    ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);
                        }
                    } else if (response.body().getStatus().equals("005")) {

                        if (currentdelaytime < totaldelaytime) {
                            //check transaction status here
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    currentdelaytime = currentdelaytime + 1000;
                                    checkTransactionStatus(checkTransactionStatusSession);
                                }
                            }, 1000);

                        } else {

//                            buttonLayout.setVisibility(View.VISIBLE);
//                            mLoaderDialogRetry.setVisibility(View.GONE);
//                            mLoaderDialogImage.setVisibility(View.GONE);
//                            mLoaderDialogTitle.setText("TRANSACTION ON PROCESS");
//
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                mLoaderDialogMessage.setText(Html.fromHtml(response.body().getRemarks(), Html.FROM_HTML_MODE_COMPACT));
//                            } else {
//                                mLoaderDialogMessage.setText(Html.fromHtml(response.body().getRemarks()));
//                            }
                            setUpProgressLoaderDismissDialog();
                            showGlobalDialogs(response.body().getRemarks(), "close",
                                    ButtonTypeEnum.SINGLE, true, false, DialogTypeEnum.ONPROCESS);
                        }

                    } else {
//                        mLoaderDialog.dismiss();
                        setUpProgressLoaderDismissDialog();
                        showError(response.body().getMessage());

                    }
                } else {
//                    mLoaderDialog.dismiss();
                    setUpProgressLoaderDismissDialog();
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getLocalizedMessage();
            }

        }

        @Override
        public void onFailure(Call<CheckTransactionStatusResponse> call, Throwable t) {
//            mLoaderDialog.dismiss();
            setUpProgressLoaderDismissDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
        }
    };


    private void getSkycableConsumedVouchersByTxnNo(Callback<GetParamountPaymentVouchersResponse> getParamountPaymentVouchersCallback) {

        Call<GetParamountPaymentVouchersResponse> paymentvouchers = RetrofitBuild.getParamountPaymentVouchersService(getViewContext())
                .getParamountPaymentVouchersCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        CommonFunctions.getYearFromDate(skycablePPVHistory.getDateTimeIN()),
                        CommonFunctions.getMonthFromDate(skycablePPVHistory.getDateTimeIN()),
                        skycablePPVHistory.getGKPaymentReferenceNo());
        paymentvouchers.enqueue(getParamountPaymentVouchersCallback);
    }

    private final Callback<GetParamountPaymentVouchersResponse> getSkycableConsumedVouchersByTxnNoSession = new Callback<GetParamountPaymentVouchersResponse>() {

        @Override
        public void onResponse(Call<GetParamountPaymentVouchersResponse> call, Response<GetParamountPaymentVouchersResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (isPaymentsCall) {
                pbrecyclerProgress.setVisibility(View.GONE);
            }

            try {
                if (errorBody == null) {

                    if (response.body().getStatus().equals("000")) {

                        if (mdb != null) {

                            List<ParamountVouchers> paramountVouchersList = response.body().getParamountVouchers();
                            for (ParamountVouchers paramountVouchers : paramountVouchersList) {
                                mdb.insertSkycableVouchers(mdb, paramountVouchers);
                            }

                            updatePaymentsData(mdb.getSkycablePaymentVouchers(mdb, skycablePPVHistory.getGKPaymentReferenceNo()));

                        }

                    }

                } else {


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<GetParamountPaymentVouchersResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
        }
    };

    private void updatePaymentsData(List<ParamountVouchers> paramountVouchers) {
        if (paramountVouchers.size() > 0) {

            voucher_details_layout.setVisibility(View.VISIBLE);
            mVouchersAdapter.setVouchersData(paramountVouchers);

        } else {

            voucher_details_layout.setVisibility(View.GONE);
            mVouchersAdapter.clear();

        }
    }

    private void setUpConfirmDialog() {
        mConfirmDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_success_confirm_order_payment, false)
                .build();
        View view = mConfirmDialog.getCustomView();
        TextView txvCloseDialog = (TextView) view.findViewById(R.id.txvCloseDialog);
        txvCloseDialog.setOnClickListener(this);
        TextView txvProceed = (TextView) view.findViewById(R.id.txvProceed);
        txvProceed.setOnClickListener(this);

        txvConfirmAmount = (TextView) view.findViewById(R.id.txvAmount);
        txvConfirmTotalAmount = (TextView) view.findViewById(R.id.txvTotalAmount);
        txvConfirmServiceCharge = (TextView) view.findViewById(R.id.txvServiceCharge);
    }

}
