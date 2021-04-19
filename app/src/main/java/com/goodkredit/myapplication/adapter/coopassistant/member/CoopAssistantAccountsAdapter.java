package com.goodkredit.myapplication.adapter.coopassistant.member;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantPaymentOptionsActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.fragments.coopassistant.CoopAssistantHomeFragment;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantAccounts;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantBills;
import com.goodkredit.myapplication.responses.coopassistant.CoopAssistantSOABillsResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantAccountsAdapter extends RecyclerView.Adapter<CoopAssistantAccountsAdapter.MyViewHolder> {

    private Context mContext;

    private CoopAssistantHomeFragment fragment;
    private List<CoopAssistantAccounts> coopAssistantAccountsList;
    private String txnno = "";
    private String amountaftervalidation = "";
    private String coopname = "";

    private List<CoopAssistantBills> cooplistvalidation = new ArrayList<>();
    private CoopAssistantAccounts coopAssistantAccounts = null;

    public CoopAssistantAccountsAdapter(Context context, CoopAssistantHomeFragment fm) {
        fragment = fm;
        mContext = context;
        coopAssistantAccountsList = new ArrayList<>();
    }

    public void updateData(List<CoopAssistantAccounts> arraylist) {
        coopAssistantAccountsList.clear();
        coopAssistantAccountsList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        coopAssistantAccountsList.clear();
        notifyDataSetChanged();
    }

    @Override
    public CoopAssistantAccountsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coopassistant_home_member_accounts_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(CoopAssistantAccountsAdapter.MyViewHolder holder, int position) {
        try {
            CoopAssistantAccounts coopAssistantAccounts = coopAssistantAccountsList.get(position);

            holder.txv_name.setText(coopAssistantAccounts.getAccountName());

            holder.txv_amount.setText(CommonFunctions.commaFormatterWithDecimals(String.valueOf(coopAssistantAccounts.getTotalAmount())));

            String date = "As of " + CommonFunctions.convertDateDDMMYYFormat(coopAssistantAccounts.getLastUpdatedDateTime());

            holder.txv_date.setText(date);

            if(coopAssistantAccounts.getIsAllowedTopup() > 0) {
              holder.btn_add_container.setVisibility(View.VISIBLE);
            } else {
              holder.btn_add_container.setVisibility(View.GONE);
            }

            holder.btn_add.setOnClickListener(paynowlistener);
            holder.btn_add.setTag(holder);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener paynowlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final CoopAssistantAccountsAdapter.MyViewHolder holder = (MyViewHolder) v.getTag();
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;

                coopAssistantAccounts = coopAssistantAccountsList.get(holder.getAdapterPosition());

                if(CommonFunctions.getConnectivityStatus(mContext) > 0){
//                    ((BaseActivity)mContext).showProgressDialog(mContext,"", "Please wait...");
                    validateIfHasPendingPaymentRequest();
                } else{
//                    ((BaseActivity)mContext).hideProgressDialog();
                    ((BaseActivity)mContext).showNoInternetToast();
                }

            } catch(Exception e ) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public int getItemCount() {
        return coopAssistantAccountsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout main_itemscontainer;

        private TextView txv_name;
        private TextView txv_amount;
        private TextView txv_date;

        private LinearLayout btn_add_container;
        private TextView btn_add;

        public MyViewHolder(View itemView) {
            super(itemView);

            main_itemscontainer = (LinearLayout) itemView.findViewById(R.id.main_itemscontainer);


            txv_name = (TextView) itemView.findViewById(R.id.txv_name);
            txv_amount = (TextView) itemView.findViewById(R.id.txv_amount);
            txv_date = (TextView) itemView.findViewById(R.id.txv_date);

            btn_add_container = (LinearLayout) itemView.findViewById(R.id.btn_add_container);
            btn_add = (TextView) itemView.findViewById(R.id.btn_add);
        }

        @Override
        public void onClick(View view) {

        }
    }

    private void validateIfHasPendingPaymentRequest() {

        Call<CoopAssistantSOABillsResponse> validateIfHasPendingPaymentRequest = RetrofitBuild.getCoopAssistantAPI(mContext)
                .validateIfHasPendingPaymentRequest(
                        PreferenceUtils.getSessionID(mContext),
                        PreferenceUtils.getImeiId(mContext),
                        PreferenceUtils.getUserId(mContext),
                        PreferenceUtils.getBorrowerId(mContext),
                        CommonFunctions.getSha1Hex(PreferenceUtils.getImeiId(mContext) + PreferenceUtils.getUserId(mContext) + PreferenceUtils.getSessionID(mContext)),
                        PreferenceUtils.getStringPreference(mContext, "GKServiceCode"),
                        "MEMBER ACCOUNT",
                        CommonVariables.devicetype
                );

        validateIfHasPendingPaymentRequest.enqueue(validateIfHasPendingPaymentRequestCallBack);
    }

    private final Callback<CoopAssistantSOABillsResponse> validateIfHasPendingPaymentRequestCallBack = new Callback<CoopAssistantSOABillsResponse>() {
        @Override
        public void onResponse(Call<CoopAssistantSOABillsResponse> call, Response<CoopAssistantSOABillsResponse> response) {
            try {
//                ((BaseActivity)mContext).hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {

                        cooplistvalidation = response.body().getCoopAssistantSOABillsList();

                        Logger.debug("vanhttp", "cooplistvalidation: ===" + new Gson().toJson(cooplistvalidation));

                        if(cooplistvalidation != null){

                            if(cooplistvalidation.isEmpty()){

                                Bundle args = new Bundle();
                                args.putParcelable("CoopAssistantAccounts", coopAssistantAccounts);
                                args.putString("FROM", "MemberShipAccounts");
                                CoopAssistantPaymentOptionsActivity.start(mContext,  args);

                            } else {
                                for(CoopAssistantBills requestinfo : cooplistvalidation){
                                    txnno = requestinfo.getBillingID();
                                    amountaftervalidation = String.valueOf(requestinfo.getTotalAmountToPay());
                                    coopname = CommonFunctions.replaceEscapeData(requestinfo.getCoopName());
                                }

                                CoopAssistantHomeFragment.showPendingLayout(cooplistvalidation, txnno, amountaftervalidation, coopname);
                            }
                        }

                    } else if (response.body().getStatus().equals("104")) {
                        ((BaseActivity)mContext).showAutoLogoutDialog(response.body().getMessage());
                    } else {
                        ((BaseActivity)mContext).showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    ((BaseActivity)mContext).showErrorGlobalDialogs();
                }
            } catch (Exception e) {
//                ((BaseActivity)mContext).hideProgressDialog();
                ((BaseActivity)mContext).showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<CoopAssistantSOABillsResponse> call, Throwable t) {
            ((BaseActivity)mContext).showErrorGlobalDialogs();
//            ((BaseActivity)mContext).hideProgressDialog();
        }
    };
}
