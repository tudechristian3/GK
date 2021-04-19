package com.goodkredit.myapplication.fragments.projectcoop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.projectcoop.ProjectCoopActivity;
import com.goodkredit.myapplication.adapter.paramount.ParamountPaymentsVoucherRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.ParamountVouchers;
import com.goodkredit.myapplication.bean.projectcoop.GameCutoverDetails;
import com.goodkredit.myapplication.bean.projectcoop.GameHistory;
import com.goodkredit.myapplication.bean.projectcoop.GameProduct;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.paramount.GetParamountPaymentVouchersResponse;
import com.goodkredit.myapplication.responses.projectcoop.GetGameP2SCutoverDetailsResponse;
import com.goodkredit.myapplication.responses.projectcoop.InsertEntryNumberP2SResponse;
import com.goodkredit.myapplication.utilities.InputFilterMinMax;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectCoopPlayToSaveConfirmationFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout linearDynamicDigits;
    private GameProduct gameProduct;
    private GameHistory gameHistory;

    private TextView txvName;
    private TextView txvAccountNumber;

    private TextView txvAmount;
    private TextView txvServiceCharge;
    private TextView txvAmountPaid;

    private DatabaseHandler mdb;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String cutoverid;

    //MATERIAL DIALOG
//    private TextView txvMessage;
//    private MaterialDialog mSuccessDialog;
//
//    private MaterialDialog mLoaderDialog;
//    private TextView mLoaderDialogMessage;
//    private TextView mLoaderDialogTitle;
//    private ImageView mLoaderDialogImage;
//    private TextView mLoaderDialogClose;
//    private TextView mLoaderDialogRetry;
//    private RelativeLayout buttonLayout;

    private int digit = 0;

    private RecyclerView recyclerViewVouchers;
    private ProgressBar pbrecyclerProgress;
    private ParamountPaymentsVoucherRecyclerAdapter mVouchersAdapter;
    private LinearLayout voucher_details_layout;

    private boolean isPaymentsCall = false;
    private boolean isCutoverDetails = false;

    private TextView txvCuttoffTimeLabel;
    private LinearLayout linearCuttoffTime;
    private TextView txvProductInformatin;
    private LinearLayout linearProductInformation;
    private TextView txvCutoffTimeStart;
    private TextView txvCutoffTimeEnd;
    private TextView txvProductName;
    private TextView txvProductDescription;
    private TextView txvDigits;
    private TextView txvDrawDay;
    private TextView txvEntryDate;

    private LinearLayout layoutEntryDate;

    //GKNEGOSYO
    private boolean checkIfReseller = false;
    private String distributorid;
    private String resellerid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_coop_play_to_save_confirmation, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        linearDynamicDigits = (LinearLayout) view.findViewById(R.id.linearDynamicDigits);
        txvName = (TextView) view.findViewById(R.id.txvName);
        txvAccountNumber = (TextView) view.findViewById(R.id.txvAccountNumber);
        txvAmount = (TextView) view.findViewById(R.id.txvAmount);
        txvServiceCharge = (TextView) view.findViewById(R.id.txvServiceCharge);
        txvAmountPaid = (TextView) view.findViewById(R.id.txvAmountPaid);
        txvEntryDate = (TextView) view.findViewById(R.id.txvEntryDate);

        if (getArguments().getString("TYPE").equals("HISTORY")) {
            recyclerViewVouchers = (RecyclerView) view.findViewById(R.id.recyclerViewVouchers);
            recyclerViewVouchers.setLayoutManager(new LinearLayoutManager(getViewContext()));
            recyclerViewVouchers.setNestedScrollingEnabled(false);
            mVouchersAdapter = new ParamountPaymentsVoucherRecyclerAdapter(getViewContext());
            recyclerViewVouchers.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null));
            recyclerViewVouchers.setAdapter(mVouchersAdapter);
            pbrecyclerProgress = (ProgressBar) view.findViewById(R.id.pbrecyclerProgress);
            voucher_details_layout = (LinearLayout) view.findViewById(R.id.voucher_details_layout);

            txvCuttoffTimeLabel = (TextView) view.findViewById(R.id.txvCuttoffTimeLabel);
            linearCuttoffTime = (LinearLayout) view.findViewById(R.id.linearCuttoffTime);
            txvProductInformatin = (TextView) view.findViewById(R.id.txvProductInformatin);
            linearProductInformation = (LinearLayout) view.findViewById(R.id.linearProductInformation);

            txvCuttoffTimeLabel.setVisibility(View.VISIBLE);
            linearCuttoffTime.setVisibility(View.VISIBLE);
            txvProductInformatin.setVisibility(View.VISIBLE);
            linearProductInformation.setVisibility(View.VISIBLE);

            txvCutoffTimeStart = (TextView) view.findViewById(R.id.txvCutoffTimeStart);
            txvCutoffTimeEnd = (TextView) view.findViewById(R.id.txvCutoffTimeEnd);
            txvDrawDay = (TextView) view.findViewById(R.id.txvDrawDay);
            txvProductName = (TextView) view.findViewById(R.id.txvProductName);
            txvProductDescription = (TextView) view.findViewById(R.id.txvProductDescription);
            txvDigits = (TextView) view.findViewById(R.id.txvDigits);

            layoutEntryDate = (LinearLayout) view.findViewById(R.id.layoutEntryDate);
            layoutEntryDate.setVisibility(View.VISIBLE);
        }

//        setUpSuccessDialog();
//
//        setupLoaderDialog();
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        if (getArguments().getString("TYPE").equals("CONFIRM")) {
            gameProduct = getArguments().getParcelable("GAMEP2SOBJ");
            digit = gameProduct.getDigit();
            isPaymentsCall = false;
        } else {
            gameHistory = getArguments().getParcelable("GAMEP2SOBJ");
            digit = gameHistory.getDigit();
            isPaymentsCall = true;

            cutoverid = gameHistory.getCutOverID();

            List<ParamountVouchers> paramountVouchers = mdb.getSkycablePaymentVouchers(mdb, gameHistory.getTransactionNo());
            if (paramountVouchers.size() == 0) {
                //call

                getSession();
            } else {
                //update adapter data here
                updatePaymentsData(paramountVouchers);
                isPaymentsCall = false;
                isCutoverDetails = true;
                getSession();
            }

//            txvCutoffTimeStart.setText(convertTo12HourTime(gameHistory.getCutOverTimeFrom()));
//            txvCutoffTimeEnd.setText(convertTo12HourTime(gameHistory.getCutOverTimeTo()));
//            txvDrawDay.setText(gameHistory.getDrawDay());
            txvEntryDate.setText(CommonFunctions.convertDate(gameHistory.getDateTimeCompleted()));
            txvProductName.setText(gameHistory.getProductName());
            txvProductDescription.setText(gameHistory.getProductDesc());
            txvDigits.setText(String.valueOf(gameHistory.getDigit()));
        }

        String[] result = getArguments().getString("P2SCOMBINATION").split("-");

        txvName.setText(getArguments().getString("P2SNAME"));
        txvAccountNumber.setText(getArguments().getString("P2SACCOUNTNUMBER"));

        txvAmount.setText(CommonFunctions.currencyFormatter(getArguments().getString("AMOUNT")));
        txvServiceCharge.setText(CommonFunctions.currencyFormatter(getArguments().getString("SERVICECHARGE")));
        txvAmountPaid.setText(CommonFunctions.currencyFormatter(getArguments().getString("AMOUNTPAID")));

        for (int i = 0; i < digit; i++) {
            EditText editText = new EditText(mContext);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                editText.setId(editText.generateViewId());
            }

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

            if (i > 0) {
                lp.setMargins(10, 0, 0, 0);
            }

            editText.setBackgroundResource(R.drawable.cpmpc_p2s_circle);
            editText.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
            editText.setGravity(Gravity.CENTER);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setEnabled(false);
            editText.setText(result[i]);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
            editText.setPadding(10, 10, 10, 10);
            editText.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack));
            editText.setTextSize(16);
            editText.setFilters(new InputFilter[]{new InputFilterMinMax("1", "44")});
            editText.setLayoutParams(lp);
            linearDynamicDigits.addView(editText);
        }

        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

        if(distributorid.equals("") || distributorid.equals(".")
                || resellerid.equals("") || resellerid.equals(".")){
            checkIfReseller = false;
        } else{
            checkIfReseller = true;
        }
    }

    private String convertTo12HourTime(String time) {
        if (time == null) {
            return time;
        } else {
            if (time.length() == 0) {
                return time;
            }
        }

        DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
        Date d = null;
        try {
            d = f1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat f2 = new SimpleDateFormat("h:mm a");
        return f2.format(d).toLowerCase();
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
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    ((ProjectCoopActivity) getViewContext()).displayView(4, null);
                }
                return true;
            }
            case R.id.confirm: {

                MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                        .content("You are about to pay your request.")
                        .cancelable(false)
                        .negativeText("Close")
                        .positiveText("OK")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

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

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
//
//            if (!isPaymentsCall && !isCutoverDetails) {
////                mLoaderDialog.show();
//                ((BaseActivity)getViewContext()).setUpProgressLoader();
//            }
//
//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);
//
//        } else {
//            ((BaseActivity)getViewContext()).setUpProgressLoaderDismissDialog();
//            showError(getString(R.string.generic_internet_error_message));
//        }
//
//    }
//
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
//                    ((BaseActivity)getViewContext()).setUpProgressLoaderDismissDialog();
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
////                    if (mLoaderDialog != null) {
////                        mLoaderDialog.dismiss();
////                    }
//                    ((BaseActivity)getViewContext()).setUpProgressLoaderDismissDialog();
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
////                    if (mLoaderDialog != null) {
////                        mLoaderDialog.dismiss();
////                    }
//                    ((BaseActivity)getViewContext()).setUpProgressLoaderDismissDialog();
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                    if (isPaymentsCall) {
//                        getSkycableConsumedVouchersByTxnNo(getSkycableConsumedVouchersByTxnNoSession);
//                    } else if (isCutoverDetails) {
//                        getGameP2SCutoverDetails(getGameP2SCutoverDetailsSession);
//                    } else {
//                        insertEntryNumberP2S(insertEntryNumberP2SSession);
//                    }
//                }
//            } else {
////                if (mLoaderDialog != null) {
////                    mLoaderDialog.dismiss();
////                }
//                ((BaseActivity)getViewContext()).setUpProgressLoaderDismissDialog();
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
////            if (mLoaderDialog != null) {
////                mLoaderDialog.dismiss();
////            }
//            ((BaseActivity)getViewContext()).setUpProgressLoaderDismissDialog();
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isPaymentsCall) {
                getSkycableConsumedVouchersByTxnNo(getSkycableConsumedVouchersByTxnNoSession);
            } else if (isCutoverDetails) {
                getGameP2SCutoverDetails(getGameP2SCutoverDetailsSession);
            } else {
                ((BaseActivity)getViewContext()).setUpProgressLoader();
                insertEntryNumberP2S(insertEntryNumberP2SSession);
            }

        } else {
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }

    }


    private void insertEntryNumberP2S(Callback<InsertEntryNumberP2SResponse> GetGameP2SResultsCallback) {
        Call<InsertEntryNumberP2SResponse> getgamep2sresults = RetrofitBuild.insertEntryNumberP2SService(getViewContext())
                .insertEntryNumberP2SCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        getArguments().getString("P2SCOMBINATION"),
                        gameProduct.getProductID(),
                        getArguments().getString("AMOUNTPAID"),
                        getArguments().getString("AMOUNT"),
                        getArguments().getString("SERVICECHARGE"),
                        getArguments().getString("P2SACCOUNTNUMBER"),
                        getArguments().getString("P2SNAME"),
                        getArguments().getString("vouchersession"),
                        PreferenceUtils.getStringPreference(getViewContext(), "projectcoopmerchantid"));
        getgamep2sresults.enqueue(GetGameP2SResultsCallback);
    }

    private final Callback<InsertEntryNumberP2SResponse> insertEntryNumberP2SSession = new Callback<InsertEntryNumberP2SResponse>() {

        @Override
        public void onResponse(Call<InsertEntryNumberP2SResponse> call, Response<InsertEntryNumberP2SResponse> response) {
            ResponseBody errorBody = response.errorBody();

            ((BaseActivity)getViewContext()).setUpProgressLoaderDismissDialog();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    //mSuccessDialog.show();

//                    buttonLayout.setVisibility(View.VISIBLE);
//
//                    mLoaderDialogRetry.setVisibility(View.GONE);
//                    mLoaderDialogTitle.setText("SUCCESSFUL ENTRY");

                    String msg = "Your entry is now submitted with the ff. <b>Approval Code</b> " + response.body().getTransactionNo() + ". You can check the results via CPMPC P2S Play to Save after the cutoff time. <br><br>Thank you for using GoodKredit.";

                    ((BaseActivity)getViewContext()).showGlobalDialogs(msg, "close", ButtonTypeEnum.SINGLE,
                            checkIfReseller, true, DialogTypeEnum.SUCCESS);

//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////                        txvMessage.setText(Html.fromHtml(msg, Html.FROM_HTML_MODE_COMPACT));
////                        txvMessage.setText(Html.fromHtml(response.body().getMessage(), Html.FROM_HTML_MODE_COMPACT));
//                        mLoaderDialogMessage.setText(Html.fromHtml(response.body().getMessage(), Html.FROM_HTML_MODE_COMPACT));
//                    } else {
////                        txvMessage.setText(Html.fromHtml(msg));
////                        txvMessage.setText(Html.fromHtml(response.body().getMessage()));
//                        mLoaderDialogMessage.setText(Html.fromHtml(response.body().getMessage()));
//                    }
//
//                    mLoaderDialogImage.setVisibility(View.GONE);
//                    mLoaderDialogClose.setVisibility(View.VISIBLE);

                } else {
//                    if (mLoaderDialog != null) {
//                        mLoaderDialog.dismiss();
//                    }
                    ((BaseActivity)getViewContext()). showGlobalDialogs(response.body().getMessage(),
                            "retry", ButtonTypeEnum.SINGLE,
                            checkIfReseller, false, DialogTypeEnum.FAILED);
//                    showError(response.body().getMessage());
                }
            } else {
//                if (mLoaderDialog != null) {
//                    mLoaderDialog.dismiss();
//                }
                showError();
            }

        }

        @Override
        public void onFailure(Call<InsertEntryNumberP2SResponse> call, Throwable t) {
//            if (mLoaderDialog != null) {
//                mLoaderDialog.dismiss();
//            }
            ((BaseActivity)getViewContext()).setUpProgressLoaderDismissDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

//    private void setUpSuccessDialog() {
//        mSuccessDialog = new MaterialDialog.Builder(getViewContext())
//                .cancelable(false)
//                .customView(R.layout.dialog_success_order_payment, false)
//                .build();
//        View view = mSuccessDialog.getCustomView();
//
//        TextView txvTitle = (TextView) view.findViewById(R.id.title);
//        txvTitle.setText("SUCCESSFUL ENTRY");
//        TextView txvSubscribeAgain = (TextView) view.findViewById(R.id.txvSubscribeAgain);
//        txvSubscribeAgain.setOnClickListener(this);
//        TextView txvGoToHistory = (TextView) view.findViewById(R.id.txvGoToHistory);
//        txvGoToHistory.setOnClickListener(this);
//
//        txvMessage = (TextView) view.findViewById(R.id.message);
//
//    }

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

//                if (mSuccessDialog != null) {
//                    mSuccessDialog.dismiss();
//                }

                getActivity().finish();

                Intent intent = new Intent(getViewContext(), ProjectCoopActivity.class);
                intent.putExtra("index", 2);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;
            }
//            case R.id.mLoaderDialogClose: {
//
////                if (mLoaderDialog != null) {
////                    mLoaderDialog.dismiss();
////                }
//
//                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
//                Intent intent = new Intent(getViewContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                CommonVariables.VOUCHERISFIRSTLOAD = true;
//                startActivity(intent);
//
//                break;
//            }
//            case R.id.cancelbtn: {
//
////                if (mLoaderDialog != null) {
////                    mLoaderDialog.dismiss();
////                }
//
//                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
//                Intent intent = new Intent(getViewContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                CommonVariables.VOUCHERISFIRSTLOAD = true;
//                startActivity(intent);
//
//                break;
//            }
        }
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
//            ImageView cancelbtn = (ImageView) view.findViewById(R.id.cancelbtn);
//            cancelbtn.setOnClickListener(this);
//
//            mLoaderDialogTitle.setText("Processing...");
//
//            Glide.with(getViewContext())
//                    .load(R.drawable.progressloader)
//                    .into(mLoaderDialogImage);
//        }
//    }

    private void getSkycableConsumedVouchersByTxnNo(Callback<GetParamountPaymentVouchersResponse> getParamountPaymentVouchersCallback) {

        Call<GetParamountPaymentVouchersResponse> paymentvouchers = RetrofitBuild.getParamountPaymentVouchersService(getViewContext())
                .getParamountPaymentVouchersCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        CommonFunctions.getYearFromDate(gameHistory.getDateTimeCompleted()),
                        CommonFunctions.getMonthFromDate(gameHistory.getDateTimeCompleted()),
                        gameHistory.getTransactionNo());
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

                            updatePaymentsData(mdb.getSkycablePaymentVouchers(mdb, gameHistory.getTransactionNo()));
                            isPaymentsCall = false;
                            isCutoverDetails = true;
                            getSession();
                        }

                    } else {
                        isPaymentsCall = false;
                    }

                } else {
                    isPaymentsCall = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<GetParamountPaymentVouchersResponse> call, Throwable t) {
            isPaymentsCall = false;
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

    private void getGameP2SCutoverDetails(Callback<GetGameP2SCutoverDetailsResponse> getParamountPaymentVouchersCallback) {

        Call<GetGameP2SCutoverDetailsResponse> paymentvouchers = RetrofitBuild.getGameP2SCutoverDetailsService(getViewContext())
                .getGameP2SCutoverDetailsCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        cutoverid);
        paymentvouchers.enqueue(getParamountPaymentVouchersCallback);
    }

    private final Callback<GetGameP2SCutoverDetailsResponse> getGameP2SCutoverDetailsSession = new Callback<GetGameP2SCutoverDetailsResponse>() {

        @Override
        public void onResponse(Call<GetGameP2SCutoverDetailsResponse> call, Response<GetGameP2SCutoverDetailsResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {

                if (response.body().getStatus().equals("000")) {

                    List<GameCutoverDetails> gameCutoverDetailsList = response.body().getGameCutoverDetailsList();

                    String cutOffTimeStart = convertTo12HourTime(gameCutoverDetailsList.get(0).getCutOverTimeFrom()) + " (" + gameCutoverDetailsList.get(0).getCutOverDay() + ")";
                    String cutOffTimeEnd = convertTo12HourTime(gameCutoverDetailsList.get(gameCutoverDetailsList.size() - 1).getCutOverTimeTo()) + " (" + gameCutoverDetailsList.get(gameCutoverDetailsList.size() - 1).getCutOverDay() + ")";
                    String drawDay = gameCutoverDetailsList.get(0).getDrawDay();

                    txvCutoffTimeStart.setText(cutOffTimeStart);
                    txvCutoffTimeEnd.setText(cutOffTimeEnd);
                    txvDrawDay.setText(drawDay);

                } else {
                    isCutoverDetails = false;
                }

            } else {
                isCutoverDetails = false;
            }

        }

        @Override
        public void onFailure(Call<GetGameP2SCutoverDetailsResponse> call, Throwable t) {
            isCutoverDetails = false;
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };
}
