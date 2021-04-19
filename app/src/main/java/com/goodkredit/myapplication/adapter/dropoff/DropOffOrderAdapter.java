package com.goodkredit.myapplication.adapter.dropoff;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.dropoff.DropOffActivity;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniBillingReferenceActivity;
import com.goodkredit.myapplication.bean.dropoff.DropOffOrder;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.enums.LayoutVisibilityEnum;
import com.goodkredit.myapplication.fragments.dropoff.DropOffPendingFragment;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.dropoff.CancelDropOffOrderResponse;
import com.goodkredit.myapplication.responses.dropoff.GetDropOffPendingOrdersResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DropOffOrderAdapter extends RecyclerView.Adapter<DropOffOrderAdapter.ViewHolder> implements View.OnClickListener {

    private List<DropOffOrder> mGridData;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private MaterialDialog mOrderDetailsDialog;

    private TextView txvDropoffDialogMerchantName;
    //    private TextView txvDropoffDialogAddress;
    private TextView txvDropoffDialogTxnNo;
    private TextView txvDropOffDialogRefNo;
    private TextView txvDropoffDialogAmount;
    private TextView txvDropoffDialogStatus;
    private ImageView imgDropoffDialogClose;
    private Button btnDropOffViewBiling;
    private Button btnDropOffCancel;
    private DropOffOrder dropOffOrderObj = null;
    private LinearLayout linearButtonActions;

    private MaterialDialog mDropOffDialogResult;
    private TextView txvMessage;

    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;

    private boolean isCancel = false;
    private boolean isRefreshData = false;

    private TextView text_dropoff_dialog_refno;

    private int year = 0;
    private int month = 0;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    //drop off pending
    private String pendingIndex;
    private String pendingAuth;
    private String pendingKey;
    private String pendingParam;
    private List<DropOffOrder> dropOffOrderArrayList = new ArrayList<>();

    public DropOffOrderAdapter(Context context) {
        this.mGridData = new ArrayList<>();
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        imei = PreferenceUtils.getImeiId(mContext);
        userid = PreferenceUtils.getUserId(mContext);
        borrowerid = PreferenceUtils.getBorrowerId(mContext);
        sessionid = PreferenceUtils.getSessionID(mContext);
        setUpDropOffOrderDetailsDialog();
        setUpDropOffDialogResult();
    }

    public void add(DropOffOrder item) {
        mGridData.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<DropOffOrder> mGridData) {
        clear();
        for (DropOffOrder item : mGridData) {
            add(item);
        }
    }

    public void clear() {
        this.mGridData.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DropOffOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_dropoff_pendingcompleted, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DropOffOrderAdapter.ViewHolder holder, int position) {

        DropOffOrder dropoff = mGridData.get(position);

        String merchant = CommonFunctions.replaceEscapeData(dropoff.getMerchantName());
        holder.txvDateTime.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Regular.ttf", CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(dropoff.getDateTimeCompleted()))));
        holder.txv_merchant.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Regular.ttf", merchant));
        holder.txv_totalamount.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", "PHP ".concat(CommonFunctions.currencyFormatter(String.valueOf(dropoff.getTotalAmount())))));
        holder.txv_purpose.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Regular.ttf", dropoff.getDropOffPurpose()));
        holder.txv_txnno.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Regular.ttf", dropoff.getBillingID()));
        holder.txvStatus.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", dropoff.getStatus().equals("FORPAYMENT") ? "FOR PAYMENT" : dropoff.getStatus()));

        switch (dropoff.getStatus()) {
            case "FORPAYMENT": {
                holder.txvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_9E9E9E));
                break;
            }
            case "CANCELLED": {
                holder.txvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_F33D2C));
                break;
            }
            case "COMPLETED": {
                holder.txvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_09B403));
                break;
            }
            default: {
                holder.txvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_9E9E9E));
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txv_merchant;
        private TextView txv_totalamount;
        private TextView txv_purpose;
        private TextView txv_txnno;
        private TextView txvDateTime;
        private TextView txvStatus;
        private LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            txv_merchant = itemView.findViewById(R.id.txv_merchant);
            txv_totalamount = itemView.findViewById(R.id.txv_totalamount);
            txv_purpose = itemView.findViewById(R.id.txv_purpose);
            txv_txnno = itemView.findViewById(R.id.txv_txnno);
            txvDateTime = itemView.findViewById(R.id.txvDateTime);
            txvStatus = itemView.findViewById(R.id.txvStatus);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            DropOffOrder dropOffOrder = mGridData.get(position);

            switch (v.getId()) {
                case R.id.linearLayout: {
                    dropOffOrderObj = dropOffOrder;
                    txvDropoffDialogMerchantName.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", CommonFunctions.replaceEscapeData(dropOffOrder.getMerchantName())));
//                    txvDropoffDialogAddress.setText(CommonFunctions.setTypeFace(mContext, "fonts/robotobold.ttf", "N/A"));
                    txvDropoffDialogTxnNo.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", dropOffOrder.getBillingID()));
                    txvDropoffDialogAmount.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", "PHP " + CommonFunctions.currencyFormatter(String.valueOf(dropOffOrder.getTotalAmount()))));
                    txvDropoffDialogStatus.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", dropOffOrder.getStatus()));

                    if (dropOffOrder.getExtra1().equals(".") || dropOffOrder.getExtra1().isEmpty()) {
                        text_dropoff_dialog_refno.setVisibility(View.GONE);
                        txvDropOffDialogRefNo.setVisibility(View.GONE);
                    } else {
                        text_dropoff_dialog_refno.setVisibility(View.VISIBLE);
                        txvDropOffDialogRefNo.setVisibility(View.VISIBLE);
                        txvDropOffDialogRefNo.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", CommonFunctions.replaceEscapeData(dropOffOrder.getExtra1())));
                    }
                    switch (dropOffOrderObj.getStatus()) {
                        case "FORPAYMENT": {
                            txvDropoffDialogStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_9E9E9E));
                            break;
                        }
                        case "CANCELLED": {
                            txvDropoffDialogStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_F33D2C));
                            break;
                        }
                        case "COMPLETED": {
                            txvDropoffDialogStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_09B403));
                            break;
                        }
                        default: {
                            txvDropoffDialogStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_9E9E9E));
                            break;
                        }
                    }

                    if (dropOffOrderObj.getStatus().equals("FORPAYMENT")) {
                        linearButtonActions.setVisibility(View.VISIBLE);
                    } else {
                        linearButtonActions.setVisibility(View.GONE);
                    }

                    mOrderDetailsDialog.show();
                    break;
                }
            }
        }
    }

    private void setUpDropOffOrderDetailsDialog() {
        mOrderDetailsDialog = new MaterialDialog.Builder(mContext)
                .cancelable(true)
                .customView(R.layout.dialog_dropoff_order_details, true)
                .build();
        View view = mOrderDetailsDialog.getCustomView();

        txvDropoffDialogMerchantName = view.findViewById(R.id.txvDropoffDialogMerchantName);
//        txvDropoffDialogAddress = (TextView) view.findViewById(R.id.txvDropoffDialogAddress);
        txvDropoffDialogTxnNo = view.findViewById(R.id.txvDropoffDialogTxnNo);
        txvDropOffDialogRefNo = view.findViewById(R.id.txvDropOffDialogRefNo);
        txvDropoffDialogAmount = view.findViewById(R.id.txvDropoffDialogAmount);
        txvDropoffDialogStatus = view.findViewById(R.id.txvDropoffDialogStatus);
        imgDropoffDialogClose = view.findViewById(R.id.imgDropoffDialogClose);
        imgDropoffDialogClose.setOnClickListener(this);
        btnDropOffViewBiling = view.findViewById(R.id.btnDropOffViewBiling);
        btnDropOffViewBiling.setOnClickListener(this);
        btnDropOffCancel = view.findViewById(R.id.btnDropOffCancel);
        btnDropOffCancel.setOnClickListener(this);
        linearButtonActions = view.findViewById(R.id.linearButtonActions);

        text_dropoff_dialog_refno = view.findViewById(R.id.text_dropoff_dialog_refno);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgDropoffDialogClose: {
                mOrderDetailsDialog.dismiss();
                break;
            }
            case R.id.btnDropOffViewBiling: {

                PreferenceUtils.saveStringPreference(mContext, PreferenceUtils.KEY_SCMERCHANTNAME, dropOffOrderObj.getMerchantName());
                SchoolMiniBillingReferenceActivity.start(mContext, new SchoolMiniPayment(".", dropOffOrderObj.getBillingID(), dropOffOrderObj.getTotalAmount()), String.valueOf(dropOffOrderObj.getTotalAmount()), "dropOffOrderAdapter");
                mOrderDetailsDialog.dismiss();
                break;
            }
            case R.id.btnDropOffCancel: {

                new MaterialDialog.Builder(mContext)
                        .content("Are you sure you want to cancel this request?")
                        .positiveText("Proceed")
                        .cancelable(false)
                        .negativeText("Close")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                mOrderDetailsDialog.dismiss();
                                isRefreshData = false;
                                isCancel = true;
                                getSession();
                            }
                        })
                        .show();

                break;
            }
            case R.id.txvCloseDialog: {
                mDropOffDialogResult.dismiss();
                isRefreshData = true;
                isCancel = false;
                getSession();
                break;
            }
        }
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(mContext) > 0) {
//            if (isCancel) {
//                ((DropOffActivity) mContext).showProgressDialog(mContext, "", "Processing Request. Please wait...");
//            }
//
//            Call<String> call = RetrofitBuild.getCommonApiService(mContext)
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);
//
//        } else {
//            isRefreshData = false;
//            isCancel = false;
//            ((DropOffActivity) mContext).showError(((DropOffActivity) mContext).getString(R.string.generic_internet_error_message));
//        }
//    }
//
//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    isRefreshData = false;
//                    isCancel = false;
//                    ((DropOffActivity) mContext).showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                    ((DropOffActivity) mContext).hideProgressDialog();
//                } else if (responseData.equals("error")) {
//                    isRefreshData = false;
//                    isCancel = false;
//                    ((DropOffActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    ((DropOffActivity) mContext).hideProgressDialog();
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    isRefreshData = false;
//                    isCancel = false;
//                    ((DropOffActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    ((DropOffActivity) mContext).hideProgressDialog();
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    if (isCancel) {
//                        cancelDropOffOrder(cancelDropOffOrderSession);
//                    } else if (isRefreshData) {
//                        year = Calendar.getInstance().get(Calendar.YEAR);
//                        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
//                        getDropOffPendingOrders(getDropOffPendingOrdersSession);
//                    } else {
//                        isRefreshData = false;
//                        isCancel = false;
//                        ((DropOffActivity) mContext).hideProgressDialog();
//                    }
//
//                }
//            } else {
//                isRefreshData = false;
//                isCancel = false;
//                ((DropOffActivity) mContext).hideProgressDialog();
//                ((DropOffActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            isRefreshData = false;
//            isCancel = false;
//            ((DropOffActivity) mContext).hideProgressDialog();
//            ((DropOffActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(mContext) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isCancel) {
                ((DropOffActivity) mContext).showProgressDialog(mContext, "", "Processing Request. Please wait...");
                //cancelDropOffOrder(cancelDropOffOrderSession);
                cancelDropOffOrderV2();
            } else if (isRefreshData) {
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                getDropOffPendingOrders(getDropOffPendingOrdersSession);
            } else {
                isRefreshData = false;
                isCancel = false;
                ((DropOffActivity) mContext).hideProgressDialog();
            }
        } else {
            isRefreshData = false;
            isCancel = false;
            ((DropOffActivity) mContext).hideProgressDialog();
            ((DropOffActivity) mContext).showNoInternetToast();
        }
    }

    private void cancelDropOffOrder(Callback<CancelDropOffOrderResponse> cancelDropOffOrderCallback) {
        Call<CancelDropOffOrderResponse> canceldropoff = RetrofitBuild.cancelDropOffOrderService(mContext)
                .cancelDropOffOrderCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        dropOffOrderObj.getGKTxnNo(),
                        "ANDROID");
        canceldropoff.enqueue(cancelDropOffOrderCallback);
    }

    private final Callback<CancelDropOffOrderResponse> cancelDropOffOrderSession = new Callback<CancelDropOffOrderResponse>() {

        @Override
        public void onResponse(Call<CancelDropOffOrderResponse> call, Response<CancelDropOffOrderResponse> response) {
            ResponseBody errorBody = response.errorBody();
            ((DropOffActivity) mContext).hideProgressDialog();

            isRefreshData = false;
            isCancel = false;

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    mDropOffDialogResult.show();
                    txvMessage.setText(response.body().getMessage());

                } else {
                    ((DropOffActivity) mContext).showError(response.body().getMessage());
                }
            } else {
                ((DropOffActivity) mContext).showError();
            }

        }

        @Override
        public void onFailure(Call<CancelDropOffOrderResponse> call, Throwable t) {
            isRefreshData = false;
            isCancel = false;
            ((DropOffActivity) mContext).hideProgressDialog();
            ((DropOffActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void setUpDropOffDialogResult() {
        mDropOffDialogResult = new MaterialDialog.Builder(mContext)
                .cancelable(true)
                .customView(R.layout.dialog_make_new_dropoff_result, false)
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        isRefreshData = true;
                        isCancel = false;
                        getSession();
                    }
                })
                .build();
        View view = mDropOffDialogResult.getCustomView();

        TextView txvCloseDialog = view.findViewById(R.id.txvCloseDialog);
        txvCloseDialog.setOnClickListener(this);
        txvMessage = view.findViewById(R.id.txvMessage);
    }

    private void getDropOffPendingOrders(Callback<GetDropOffPendingOrdersResponse> getDropOffPendingOrdersCallback) {
        Call<GetDropOffPendingOrdersResponse> getdropoffpending = RetrofitBuild.getDropOffPendingOrdersService(mContext)
                .getDropOffPendingOrdersCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        ".",
                        "0",
                        "ANDROID"
                );
        getdropoffpending.enqueue(getDropOffPendingOrdersCallback);
    }

    private final Callback<GetDropOffPendingOrdersResponse> getDropOffPendingOrdersSession = new Callback<GetDropOffPendingOrdersResponse>() {

        @Override
        public void onResponse(Call<GetDropOffPendingOrdersResponse> call, Response<GetDropOffPendingOrdersResponse> response) {
            ResponseBody errorBody = response.errorBody();

            isRefreshData = false;
            isCancel = false;

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    clear();
                    if(response.body().getPendingDropOffs().size() > 0){
                        DropOffPendingFragment.visibilityType(LayoutVisibilityEnum.HASDATA);
                        addAll(response.body().getPendingDropOffs());
                    }else{
                        DropOffPendingFragment.visibilityType(LayoutVisibilityEnum.NODATA);
                    }
                } else {
                    ((DropOffActivity) mContext).showError(response.body().getMessage());
                }
            } else {
                DropOffPendingFragment.visibilityType(LayoutVisibilityEnum.NODATA);
                ((DropOffActivity) mContext).showError();
            }

        }

        @Override
        public void onFailure(Call<GetDropOffPendingOrdersResponse> call, Throwable t) {
            isRefreshData = false;
            isCancel = false;
            DropOffPendingFragment.visibilityType(LayoutVisibilityEnum.NODATA);
            ((DropOffActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     */

    private void cancelDropOffOrderV2() {

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("gktxnno", dropOffOrderObj.getGKTxnNo());
        parameters.put("devicetype", CommonVariables.devicetype);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(mContext, parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "cancelDropOffOrderV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        cancelDropOffOrderObject(cancelDropOffOrderV2Session);

    }
    private void cancelDropOffOrderObject(Callback<GenericResponse> cancelDropOffOrderCallback) {
        Call<GenericResponse> canceldropoff = RetrofitBuilder.getDropOffV2API(mContext)
                .cancelDropOffOrderV2(authenticationid, sessionid, param);

        canceldropoff.enqueue(cancelDropOffOrderCallback);
    }
    private final Callback<GenericResponse> cancelDropOffOrderV2Session = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            ((DropOffActivity) mContext).hideProgressDialog();

            isRefreshData = false;
            isCancel = false;

            if (errorBody == null) {
                String message = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    mDropOffDialogResult.show();
                    txvMessage.setText(message);
                } else if (response.body().getStatus().equals("003")) {
                    ((DropOffActivity) mContext).showAutoLogoutDialog(message);
                } else {
                    ((DropOffActivity) mContext).showError(message);
                }
            } else {
                ((DropOffActivity) mContext).showError();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isRefreshData = false;
            isCancel = false;
            ((DropOffActivity) mContext).hideProgressDialog();
            ((DropOffActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };


}
