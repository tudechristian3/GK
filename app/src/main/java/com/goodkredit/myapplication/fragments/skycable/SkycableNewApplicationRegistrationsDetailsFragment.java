package com.goodkredit.myapplication.fragments.skycable;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
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
import com.goodkredit.myapplication.bean.SkycableRegistration;
import com.goodkredit.myapplication.bean.SkycableSOA;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.responses.paramount.GetParamountPaymentVouchersResponse;
import com.goodkredit.myapplication.responses.skycable.CheckIfConfigIsAvailableResponse;
import com.goodkredit.myapplication.responses.skycable.SkycableProcessVoucherConsummationResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkycableNewApplicationRegistrationsDetailsFragment extends BaseFragment implements View.OnClickListener {

    private TextView txvRegistrationID;
    private TextView txvRegistrationsName;
    private TextView txvMobileNo;
    private TextView txvTelephoneNumber;
    private TextView txvEmailAddress;
    private TextView txvAddress;
    private TextView txvBillingAddress;
    private TextView txvInstallationAddress;
    private TextView txvGender;
    private TextView txvBirthDate;
    //    private TextView txvPlanName;
//    private TextView txvPlanType;
//    private TextView txvPlanAmount;
    private TextView txvDescription;
    private TextView txvInstallationFee;
    private TextView txvMonthlyFee;
    private TextView txvInitialCashout;
    private TextView txvDiscount;

    private ImageView imgIdentificationNumber;
    private ImageView imgProofOfBilling;

    private SkycableRegistration skycableRegistration = null;

    private LinearLayout linearButtonsLayout;
    private Button btnPay;

    //CONFIRM DIALOG
    private MaterialDialog mConfirmDialog;
    private TextView txvConfirmModalMonthlyFee;
    private TextView txvConfirmModalInstallationFee;
    private TextView txvConfirmModalServiceCharge;
    private TextView txvConfirmModalDiscount;
    private TextView txvConfirmModalTotalAmount;
    private TextView txvConfirmInitialCashout;

    private DatabaseHandler mdb;
    private String guarantorid;
//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String servicecode;

    private String vouchersessionid;
    private String merchantid;
    private String registrationid;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private int currentdelaytime = 0;
    private int totaldelaytime = 10000;
    private boolean isStatusChecked = false;
    private String transactionno = "";

    //PAYMENTS LAYOUT
    private LinearLayout linearApprovalCodeLayout;
    private TextView txvPaymentDetailsLabel;
    private LinearLayout linearPaymentDetailsLayout;
    private TextView txvConfirmAmount;
    private TextView txvConfirmServiceCharge;
    private TextView txvConfirmAmountPaid;
    private TextView txvApprovalCode;

    private boolean isConfirm = false;
    private boolean isProceed = false;
    private boolean isConfigAvailable = false;

    private MaterialDialog mLoaderDialog;
    private TextView mLoaderDialogMessage;
    private TextView mLoaderDialogTitle;
    private ImageView mLoaderDialogImage;
    private TextView mLoaderDialogClose;
    private TextView mLoaderDialogRetry;
    private RelativeLayout buttonLayout;

    private LinearLayout linearPlanItemLayout;
    private TextView txvName;
    private TextView txvAmount;
    private ImageView imgPlanItem;

    private RecyclerView recyclerViewVouchers;
    private ProgressBar pbrecyclerProgress;
    private ParamountPaymentsVoucherRecyclerAdapter mVouchersAdapter;
    private LinearLayout voucher_details_layout;
    private boolean isPaymentsCall = false;

    private V2Subscriber v2Subscriber = null;

    private LinearLayout linearDiscountlayout;

    private TextView txvDiscountConfirmAmount;
    private TextView txvInstallationFeeConfirmAmount;

    //UNIFIED SESSION
    private String sessionid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_new_application_registrations_details, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
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
                } else if (getArguments().getString("TYPE").equals("ACTIONREQUIRED")) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    ((SkyCableActivity) getViewContext()).displayView(16, null);
                } else {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    ((SkyCableActivity) getViewContext()).displayView(10, null);
                }
                return true;
            }
            case R.id.confirm: {

                MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                        .content("You are about to pay your request.")
                        .cancelable(false)
                        .negativeText("Close")
                        .positiveText("Proceed")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                isProceed = true;
                                currentdelaytime = 0;
                                isStatusChecked = false;
                                isConfigAvailable = false;
                                getSession();

                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        })
                        .show();

                V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (getArguments().getString("TYPE").equals("CONFIRM")) {
            inflater.inflate(R.menu.menu_confirm, menu);
        }
    }


    private void init(View view) {
        linearDiscountlayout = (LinearLayout) view.findViewById(R.id.linearDiscountlayout);

        txvDiscountConfirmAmount = (TextView) view.findViewById(R.id.txvDiscountConfirmAmount);
        txvInstallationFeeConfirmAmount = (TextView) view.findViewById(R.id.txvInstallationFeeConfirmAmount);

        linearPlanItemLayout = (LinearLayout) view.findViewById(R.id.linearPlanItemLayout);
        txvName = (TextView) view.findViewById(R.id.txvName);
        txvAmount = (TextView) view.findViewById(R.id.txvAmount);
        imgPlanItem = (ImageView) view.findViewById(R.id.imgPlanItem);

        txvRegistrationID = (TextView) view.findViewById(R.id.txvRegistrationID);
        txvRegistrationsName = (TextView) view.findViewById(R.id.txvRegistrationsName);
        txvMobileNo = (TextView) view.findViewById(R.id.txvMobileNo);
        txvTelephoneNumber = (TextView) view.findViewById(R.id.txvTelephoneNumber);
        txvEmailAddress = (TextView) view.findViewById(R.id.txvEmailAddress);
        txvAddress = (TextView) view.findViewById(R.id.txvAddress);
        txvBillingAddress = (TextView) view.findViewById(R.id.txvBillingAddress);
        txvInstallationAddress = (TextView) view.findViewById(R.id.txvInstallationAddress);
        txvGender = (TextView) view.findViewById(R.id.txvGender);
        txvBirthDate = (TextView) view.findViewById(R.id.txvBirthDate);
//        txvPlanName = (TextView) view.findViewById(R.id.txvPlanName);
//        txvPlanType = (TextView) view.findViewById(R.id.txvPlanType);
//        txvPlanAmount = (TextView) view.findViewById(R.id.txvPlanAmount);
        txvDescription = (TextView) view.findViewById(R.id.txvDescription);
        txvInstallationFee = (TextView) view.findViewById(R.id.txvInstallationFee);
        txvMonthlyFee = (TextView) view.findViewById(R.id.txvMonthlyFee);
        txvInitialCashout = (TextView) view.findViewById(R.id.txvInitialCashout);
        txvDiscount = (TextView) view.findViewById(R.id.txvDiscount);

        imgIdentificationNumber = (ImageView) view.findViewById(R.id.imgIdentificationNumber);
        imgProofOfBilling = (ImageView) view.findViewById(R.id.imgProofOfBilling);
        linearButtonsLayout = (LinearLayout) view.findViewById(R.id.linearButtonsLayout);
        btnPay = (Button) view.findViewById(R.id.btnPay);
        btnPay.setOnClickListener(this);

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        //PAYMENTS
        linearApprovalCodeLayout = (LinearLayout) view.findViewById(R.id.approvalCodeLayout);
        txvPaymentDetailsLabel = (TextView) view.findViewById(R.id.txvPaymentDetailsLabel);
        linearPaymentDetailsLayout = (LinearLayout) view.findViewById(R.id.linearPaymentDetailsLayout);

        txvApprovalCode = (TextView) view.findViewById(R.id.txvApprovalCode);
        txvConfirmAmount = (TextView) view.findViewById(R.id.txvConfirmAmount);
        txvConfirmServiceCharge = (TextView) view.findViewById(R.id.txvConfirmServiceCharge);
        txvConfirmAmountPaid = (TextView) view.findViewById(R.id.txvConfirmAmountPaid);

        recyclerViewVouchers = (RecyclerView) view.findViewById(R.id.recyclerViewVouchers);
        recyclerViewVouchers.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerViewVouchers.setNestedScrollingEnabled(false);
        mVouchersAdapter = new ParamountPaymentsVoucherRecyclerAdapter(getViewContext());
        recyclerViewVouchers.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null));
        recyclerViewVouchers.setAdapter(mVouchersAdapter);
        pbrecyclerProgress = (ProgressBar) view.findViewById(R.id.pbrecyclerProgress);
        voucher_details_layout = (LinearLayout) view.findViewById(R.id.voucher_details_layout);

        //SETUP DIALOGS
        setUpConfirmDialog();

        setupLoaderDialog();
    }

    private void initData() {
        skycableRegistration = getArguments().getParcelable("skycableRegistration");
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        guarantorid = mdb.getGuarantorID(mdb);
        v2Subscriber = mdb.getSubscriber(mdb);
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "skycablebillcode");

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        if (getArguments().getString("TYPE").equals("CONFIRM")) {
            isConfirm = true;

            vouchersessionid = getArguments().getString("vouchersession");
            merchantid = PreferenceUtils.getStringPreference(getViewContext(), "skycablemerchantidnewapplication");
            registrationid = skycableRegistration.getRegistrationID();

            linearApprovalCodeLayout.setVisibility(View.GONE);
            txvPaymentDetailsLabel.setVisibility(View.VISIBLE);
            linearPaymentDetailsLayout.setVisibility(View.VISIBLE);
            linearButtonsLayout.setVisibility(View.GONE);

        } else {
            isConfirm = false;

            if (skycableRegistration.getStatus().equals("FOR PAYMENT")) {
                linearButtonsLayout.setVisibility(View.VISIBLE);
            } else {
                linearButtonsLayout.setVisibility(View.GONE);
            }

            if (skycableRegistration.getStatus().equals("PAID") ||
                    skycableRegistration.getStatus().equals("FOR INSTALLATION") ||
                    skycableRegistration.getStatus().equals("INSTALLED")) {
                linearApprovalCodeLayout.setVisibility(View.VISIBLE);
                txvPaymentDetailsLabel.setVisibility(View.VISIBLE);
                linearPaymentDetailsLayout.setVisibility(View.VISIBLE);

                isPaymentsCall = true;

                List<ParamountVouchers> paramountVouchers = mdb.getSkycablePaymentVouchers(mdb, skycableRegistration.getGKPaymentReferenceNo());
                if (paramountVouchers.size() == 0) {
                    //call
                    getSession();
                } else {
                    //update adapter data here
                    updatePaymentsData(paramountVouchers);
                }

            } else {
                linearApprovalCodeLayout.setVisibility(View.GONE);
                txvPaymentDetailsLabel.setVisibility(View.GONE);
                linearPaymentDetailsLayout.setVisibility(View.GONE);
            }

        }

        double discount = (skycableRegistration.getMonthlyFee() * skycableRegistration.getDiscountPercentage()) + skycableRegistration.getDiscountBase();
        if (discount > 0) {
            linearDiscountlayout.setVisibility(View.VISIBLE);
        } else {
            linearDiscountlayout.setVisibility(View.GONE);
        }

        txvApprovalCode.setText(skycableRegistration.getGKPaymentReferenceNo());
        txvConfirmAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getMonthlyFee())));
        txvInstallationFeeConfirmAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getInstallationFee())));
        txvConfirmServiceCharge.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getServiceCharge())));
        txvDiscountConfirmAmount.setText("(" + CommonFunctions.currencyFormatter(String.valueOf(discount)) + ")");
        txvConfirmAmountPaid.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getTotalAmountPaid())));

//        txvConfirmAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getMonthlyFee())));
//        txvConfirmServiceCharge.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getServiceCharge())));
//        txvConfirmAmountPaid.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getTotalAmountPaid())));

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        txvRegistrationID.setText(skycableRegistration.getRegistrationID());
        txvRegistrationsName.setText(skycableRegistration.getFirstName() + " " + skycableRegistration.getLastName());
        txvMobileNo.setText("+" + skycableRegistration.getMobileNumber());
        txvTelephoneNumber.setText(skycableRegistration.getTelephoneNumber());
        txvEmailAddress.setText(skycableRegistration.getEmailAddress());
        txvAddress.setText(skycableRegistration.getAddress());
        txvBillingAddress.setText(skycableRegistration.getBillingAddress());
        txvInstallationAddress.setText(skycableRegistration.getInstallationAddress());
        txvGender.setText(skycableRegistration.getGender());
        txvBirthDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(skycableRegistration.getBirthdate())));
//        txvPlanName.setText(CommonFunctions.replaceEscapeData(skycableRegistration.getPlanName()));
//        txvPlanType.setText(skycableRegistration.getPlanType());
//        txvPlanAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getMonthlyFee())));
        txvDescription.setText(CommonFunctions.replaceEscapeData(skycableRegistration.getDescription()));
        txvInstallationFee.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getInstallationFee())));
        txvMonthlyFee.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getMonthlyFee())));
        txvInitialCashout.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getInitialCashout())));
        txvDiscount.setText(CommonFunctions.currencyFormatter(String.valueOf((skycableRegistration.getMonthlyFee() * skycableRegistration.getDiscountPercentage()) + skycableRegistration.getDiscountBase())));

        btnPay.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "PAY " + CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getTotalAmountPaid()))));

//        linearPlanItemLayout.setBackgroundColor(Color.parseColor(CommonFunctions.parseJSON(skycableRegistration.getPlanStyle(), "background-color")));
//        txvName.setTextColor(Color.parseColor(CommonFunctions.parseJSON(skycableRegistration.getPlanStyle(), "color")));
//        txvAmount.setTextColor(Color.parseColor(CommonFunctions.parseJSON(skycableRegistration.getPlanStyle(), "color")));

        Glide.with(mContext)
                .asBitmap()
                .load(skycableRegistration.getPlanImgUrl())
                .apply(new RequestOptions()
                        .fitCenter())
                .into(new BaseTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        imgPlanItem.setImageBitmap(resource);
                    }

                    @Override
                    public void getSize(SizeReadyCallback cb) {
                        cb.onSizeReady(CommonFunctions.getScreenWidth(mContext), CommonFunctions.getScreenHeight(mContext));
                    }

                    @Override
                    public void removeCallback(SizeReadyCallback cb) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        linearPlanItemLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorwhite));
                    }
                });

        txvName.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", skycableRegistration.getPlanName()));
        txvAmount.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", "₱" + CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getMonthlyFee()))));


        if (skycableRegistration.getNotes1() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(CommonFunctions.parseXML(skycableRegistration.getNotes1(), "skycableimageid"))
                    .apply(new RequestOptions()
                            .fitCenter())
                    .into(new BaseTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            imgIdentificationNumber.setImageBitmap(resource);
                        }

                        @Override
                        public void getSize(SizeReadyCallback cb) {
                            cb.onSizeReady(CommonFunctions.getScreenWidthPixel(mContext), 150);
                        }

                        @Override
                        public void removeCallback(SizeReadyCallback cb) {

                        }
                    });

            Glide.with(mContext)
                    .asBitmap()
                    .load(CommonFunctions.parseXML(skycableRegistration.getNotes1(), "skycableimagepob"))
                    .apply(new RequestOptions()
                            .fitCenter())
                    .into(new BaseTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            imgProofOfBilling.setImageBitmap(resource);
                        }

                        @Override
                        public void getSize(SizeReadyCallback cb) {
                            cb.onSizeReady(CommonFunctions.getScreenWidthPixel(mContext), 150);
                        }

                        @Override
                        public void removeCallback(SizeReadyCallback cb) {

                        }
                    });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPay: {

                isProceed = false;
                isPaymentsCall = false;
                isConfigAvailable = true;
                getSession();

                break;
            }
            case R.id.txvConfirmCloseDialog: {
                mConfirmDialog.dismiss();
                break;
            }
            case R.id.txvConfirmProceed: {

//                getSession();

                Intent intent = new Intent(getViewContext(), BillsPaymentBillerDetailsActivity.class);
                intent.putExtra("BILLCODE", PreferenceUtils.getStringPreference(getViewContext(), "skycablebillcode"));
                intent.putExtra("SPBILLCODE", PreferenceUtils.getStringPreference(getViewContext(), "skycablespbillcode"));
                intent.putExtra("SPBILLERACCOUNTNO", "");
                intent.putExtra("BILLNAME", PreferenceUtils.getStringPreference(getViewContext(), "skycablebillname"));
                intent.putExtra("FROM", "BILLERS");
                intent.putExtra("NEWAPPLICATION", true);
                intent.putExtra("skycableSOA", new SkycableSOA(null, null, skycableRegistration.getAccountNo(), skycableRegistration.getFirstName() + " " + skycableRegistration.getLastName(), null, 0, 0, skycableRegistration.getTotalAmountPaid(), null, null, null, null));
                getViewContext().startActivity(intent);

                break;
            }
            case R.id.mLoaderDialogClose: {

                if (mLoaderDialog != null) {
                    mLoaderDialog.dismiss();
                }

                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                Intent intent = new Intent(getViewContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                startActivity(intent);

                break;
            }
        }
    }

    private void setUpConfirmDialog() {
        mConfirmDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_confirm_skycable_new_application, false)
                .build();
        View view = mConfirmDialog.getCustomView();
        TextView txvCloseDialog = (TextView) view.findViewById(R.id.txvConfirmCloseDialog);
        txvCloseDialog.setOnClickListener(this);
        TextView txvProceed = (TextView) view.findViewById(R.id.txvConfirmProceed);
        txvProceed.setOnClickListener(this);

        txvConfirmModalMonthlyFee = (TextView) view.findViewById(R.id.txvConfirmMonthlyFee);
        txvConfirmModalInstallationFee = (TextView) view.findViewById(R.id.txvConfirmInstallationFee);
        txvConfirmModalServiceCharge = (TextView) view.findViewById(R.id.txvConfirmServiceCharge);
        txvConfirmModalDiscount = (TextView) view.findViewById(R.id.txvConfirmDiscount);
        txvConfirmModalTotalAmount = (TextView) view.findViewById(R.id.txvConfirmTotalAmount);
        txvConfirmInitialCashout = (TextView) view.findViewById(R.id.txvConfirmInitialCashout);
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            if (isConfirm) {
                mLoaderDialog.show();
            } else if (isPaymentsCall) {
                pbrecyclerProgress.setVisibility(View.VISIBLE);
            } else {
                if (isConfigAvailable) {
                    mTvFetching.setText("Checking Config..");
                } else {
                    mTvFetching.setText("Fetching Pre-Purchase Session..");
                }
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);
            }

//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isProceed) {
                skycableProcessVoucherConsummation(skycableProcessVoucherConsummationSession);
            } else if (isPaymentsCall) {
                getSkycableConsumedVouchersByTxnNo(getSkycableConsumedVouchersByTxnNoSession);
            } else if (isConfigAvailable) {
                checkIfConfigIsAvailable(checkIfConfigIsAvailableSession);
            } else {
                prePurchase(prePurchaseSession);
            }

        } else {
            isConfigAvailable = false;
            isPaymentsCall = false;
            isProceed = false;
            mLlLoader.setVisibility(View.GONE);
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
//                    if (mLoaderDialog != null) {
//                        mLoaderDialog.dismiss();
//                    }
//
//                    if (isPaymentsCall) {
//                        pbrecyclerProgress.setVisibility(View.GONE);
//                    }
//
//                    isProceed = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    if (mLoaderDialog != null) {
//                        mLoaderDialog.dismiss();
//                    }
//
//                    if (isPaymentsCall) {
//                        pbrecyclerProgress.setVisibility(View.GONE);
//                    }
//
//                    isProceed = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    if (mLoaderDialog != null) {
//                        mLoaderDialog.dismiss();
//                    }
//
//                    if (isPaymentsCall) {
//                        pbrecyclerProgress.setVisibility(View.GONE);
//                    }
//
//                    isProceed = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    if (isProceed) {
//                        skycableProcessVoucherConsummation(skycableProcessVoucherConsummationSession);
//                    } else if (isPaymentsCall) {
//                        getSkycableConsumedVouchersByTxnNo(getSkycableConsumedVouchersByTxnNoSession);
//                    } else if (isConfigAvailable) {
//                        checkIfConfigIsAvailable(checkIfConfigIsAvailableSession);
//                    } else {
//                        prePurchase(prePurchaseSession);
//                    }
//                }
//            } else {
//                if (mLoaderDialog != null) {
//                    mLoaderDialog.dismiss();
//                }
//
//                if (isPaymentsCall) {
//                    pbrecyclerProgress.setVisibility(View.GONE);
//                }
//
//                isProceed = false;
//                mLlLoader.setVisibility(View.GONE);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            if (mLoaderDialog != null) {
//                mLoaderDialog.dismiss();
//            }
//
//            if (isPaymentsCall) {
//                pbrecyclerProgress.setVisibility(View.GONE);
//            }
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void skycableProcessVoucherConsummation(Callback<SkycableProcessVoucherConsummationResponse> skycableProcessVoucherConsummationCallback) {
        Call<SkycableProcessVoucherConsummationResponse> skycableprocessvoucherconsummation = RetrofitBuild.skycableProcessVoucherConsummationService(getViewContext())
                .skycableProcessVoucherConsummationCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        vouchersessionid,
                        merchantid,
                        registrationid,
                        PreferenceUtils.getStringPreference(getViewContext(), "skycablebillcode"));
        skycableprocessvoucherconsummation.enqueue(skycableProcessVoucherConsummationCallback);
    }

    private final Callback<SkycableProcessVoucherConsummationResponse> skycableProcessVoucherConsummationSession = new Callback<SkycableProcessVoucherConsummationResponse>() {

        @Override
        public void onResponse(Call<SkycableProcessVoucherConsummationResponse> call, Response<SkycableProcessVoucherConsummationResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    buttonLayout.setVisibility(View.VISIBLE);

                    mLoaderDialogRetry.setVisibility(View.GONE);
                    mLoaderDialogTitle.setText("SUCCESSFUL TRANSACTION");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mLoaderDialogMessage.setText(Html.fromHtml(response.body().getMessage(), Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        mLoaderDialogMessage.setText(Html.fromHtml(response.body().getMessage()));
                    }

                    mLoaderDialogImage.setVisibility(View.GONE);
                    mLoaderDialogClose.setVisibility(View.VISIBLE);


                } else {

                    if (mLoaderDialog != null) {
                        mLoaderDialog.dismiss();
                    }

                    showError(response.body().getMessage());
                }
            } else {

                if (mLoaderDialog != null) {
                    mLoaderDialog.dismiss();
                }

                showError();
            }

        }

        @Override
        public void onFailure(Call<SkycableProcessVoucherConsummationResponse> call, Throwable t) {
            if (mLoaderDialog != null) {
                mLoaderDialog.dismiss();
            }
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void prePurchase(Callback<String> prePurchaseCallback) {
        Call<String> prepurchase = RetrofitBuild.prePurchaseService(getViewContext())
                .prePurchaseCall(borrowerid,
                        String.valueOf(skycableRegistration.getTotalAmountPaid()),
                        userid,
                        imei,
                        sessionid,
                        authcode);
        prepurchase.enqueue(prePurchaseCallback);
    }

    private final Callback<String> prePurchaseSession = new Callback<String>() {

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                mLlLoader.setVisibility(View.GONE);
                String responseData = response.body().toString();

                if (!responseData.isEmpty()) {
                    if (responseData.equals("001")) {
                        CommonFunctions.hideDialog();
                        showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
                    } else if (responseData.equals("error")) {
                        CommonFunctions.hideDialog();
                        showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                    } else if (responseData.contains("<!DOCTYPE html>")) {
                        CommonFunctions.hideDialog();
                        showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                    } else {
                        if (responseData.length() > 0) {

                            if (mConfirmDialog != null) {
                                mConfirmDialog.dismiss();
                            }

                            Intent intent = new Intent(getViewContext(), Payment.class);
                            intent.putExtra("AMOUNT", String.valueOf(skycableRegistration.getMonthlyFee()));
                            intent.putExtra("SERVICECHARGE", String.valueOf(skycableRegistration.getServiceCharge()));
                            intent.putExtra("AMOUNTPAID", String.valueOf(skycableRegistration.getTotalAmountPaid()));
                            intent.putExtra("SKYCABLEREGISTRATION", "true");
                            intent.putExtra("VOUCHERSESSION", responseData);
                            intent.putExtra("SKYCABLEREGISTRATIONDATA", skycableRegistration);
                            startActivity(intent);

                        } else {
                            showError("Invalid Voucher Session.");
                        }
                    }
                } else {
                    CommonFunctions.hideDialog();
                    showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                }

            }

        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            CommonFunctions.hideDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void setupLoaderDialog() {
        mLoaderDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_custom_animated, false)
                .build();

        View view = mLoaderDialog.getCustomView();
        if (view != null) {
            mLoaderDialogMessage = (TextView) view.findViewById(R.id.mLoaderDialogMessage);
            mLoaderDialogTitle = (TextView) view.findViewById(R.id.mLoaderDialogTitle);
            mLoaderDialogImage = (ImageView) view.findViewById(R.id.mLoaderDialogImage);
            mLoaderDialogClose = (TextView) view.findViewById(R.id.mLoaderDialogClose);
            mLoaderDialogClose.setOnClickListener(this);
            mLoaderDialogRetry = (TextView) view.findViewById(R.id.mLoaderDialogRetry);
            mLoaderDialogRetry.setVisibility(View.GONE);
            mLoaderDialogRetry.setOnClickListener(this);
            buttonLayout = (RelativeLayout) view.findViewById(R.id.buttonLayout);
            buttonLayout.setVisibility(View.GONE);

            mLoaderDialogTitle.setText("Processing...");

            Glide.with(getViewContext())
                    .load(R.drawable.progressloader)
                    .into(mLoaderDialogImage);
        }
    }

    private void getSkycableConsumedVouchersByTxnNo(Callback<GetParamountPaymentVouchersResponse> getParamountPaymentVouchersCallback) {

        Call<GetParamountPaymentVouchersResponse> paymentvouchers = RetrofitBuild.getParamountPaymentVouchersService(getViewContext())
                .getParamountPaymentVouchersCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        CommonFunctions.getYearFromDate(skycableRegistration.getDateTimeIN()),
                        CommonFunctions.getMonthFromDate(skycableRegistration.getDateTimeIN()),
                        skycableRegistration.getGKPaymentReferenceNo());
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

                            updatePaymentsData(mdb.getSkycablePaymentVouchers(mdb, skycableRegistration.getGKPaymentReferenceNo()));

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
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
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

    private void checkIfConfigIsAvailable(Callback<CheckIfConfigIsAvailableResponse> checkIfConfigIsAvailableCallback) {
        Call<CheckIfConfigIsAvailableResponse> checkconfig = RetrofitBuild.checkIfConfigIsAvailableService(getViewContext())
                .checkIfConfigIsAvailableCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        servicecode,
                        "New Application");
        checkconfig.enqueue(checkIfConfigIsAvailableCallback);
    }

    private final Callback<CheckIfConfigIsAvailableResponse> checkIfConfigIsAvailableSession = new Callback<CheckIfConfigIsAvailableResponse>() {

        @Override
        public void onResponse(Call<CheckIfConfigIsAvailableResponse> call, Response<CheckIfConfigIsAvailableResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            isConfigAvailable = false;

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    if (skycableRegistration != null) {

                        if (mConfirmDialog != null) {
                            mConfirmDialog.show();

                            //9-13-2018
                            //UPDATES
                            // *** Remove Monthly Fee in the computation as per miss Sheila

                            txvConfirmModalMonthlyFee.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getMonthlyFee())));
                            txvConfirmModalInstallationFee.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getInstallationFee())));
                            txvConfirmModalServiceCharge.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getServiceCharge())));

//                            double discount = (skycableRegistration.getMonthlyFee() * skycableRegistration.getDiscountPercentage()) + skycableRegistration.getDiscountBase();
                            double discount = skycableRegistration.getDiscountPercentage() + skycableRegistration.getDiscountBase();
                            txvConfirmModalDiscount.setText("(" + CommonFunctions.currencyFormatter(String.valueOf(discount)) + ")");

                            txvConfirmInitialCashout.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableRegistration.getInitialCashout())));

//                            double totalamount = (skycableRegistration.getMonthlyFee() + skycableRegistration.getInstallationFee() + skycableRegistration.getInitialCashout() + skycableRegistration.getServiceCharge()) - discount;
                            double totalamount = (skycableRegistration.getInstallationFee() + skycableRegistration.getServiceCharge()) - discount;
                            txvConfirmModalTotalAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(totalamount)));

                        }

                    } else {
                        showError("Something went wrong with the data. Please refresh the registrations page.");
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<CheckIfConfigIsAvailableResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            isConfigAvailable = false;
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };
}
