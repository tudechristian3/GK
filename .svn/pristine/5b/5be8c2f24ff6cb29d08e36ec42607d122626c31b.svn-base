package com.goodkredit.myapplication.adapter.gkservices.medpadala;

import android.content.Context;
import android.database.Cursor;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.MedPadalaTransaction;
import com.goodkredit.myapplication.model.MedPadalaVoucherUsed;
import com.goodkredit.myapplication.responses.medpadala.GetMedPadalaTransactionVoucherDetailsResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 2/27/2018.
 */

public class MedPadalaTransactionsHistoryAdapter extends RecyclerView.Adapter<MedPadalaTransactionsHistoryAdapter.TransactionViewHolder> {

    private Context mContext;
    private List<MedPadalaTransaction> mTransactions = new ArrayList<>();
    private DatabaseHandler db;

    private String borrowerid;
    private String userid;
    private String imei;
    private String sessionid;

    public MedPadalaTransactionsHistoryAdapter(Context context, List<MedPadalaTransaction> transactions) {
        mContext = context;
        mTransactions = transactions;
        db = new DatabaseHandler(mContext);
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
            } while (cursor.moveToNext());
        }
        //imei = V2Utils.getIMEI(mContext);
        imei = PreferenceUtils.getImeiId(mContext);
        sessionid = PreferenceUtils.getSessionID(mContext);
    }

    public void update(List<MedPadalaTransaction> transactions) {
        mTransactions.clear();
        notifyDataSetChanged();
        mTransactions = transactions;
        notifyDataSetChanged();
    }

    @Override

    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_medpadala_transaction_history, parent, false);
        // Return a new holder instance
        TransactionViewHolder viewHolder = new TransactionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        try {
            MedPadalaTransaction transaction = mTransactions.get(position);
            TextView date = holder.date;
            TextView time = holder.time;
            TextView transNo = holder.transNo;
            TextView recipientNo = holder.recipientNo;
            TextView amount = holder.amount;
            TextView status = holder.status;

            date.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(transaction.getDateTimeCompleted())));
            time.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(transaction.getDateTimeCompleted())));
            transNo.setText(transaction.getMedpadalaTxnNo());
            recipientNo.setText("+" + transaction.getRecipientMobileNo());
            amount.setText(CommonFunctions.currencyFormatter(String.valueOf(transaction.getAmount())));
            status.setText(transaction.getTxnStatus());

            if (transaction.getTxnStatus().equals("PENDING")) {
                holder.btnViewDetails.setVisibility(View.GONE);
                holder.btnHideDetails.setVisibility(View.GONE);
            } else {
                holder.btnViewDetails.setVisibility(View.VISIBLE);
                holder.btnHideDetails.setVisibility(View.GONE);
                holder.rvVoucherUsed.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date;
        TextView time;
        TextView transNo;
        TextView recipientNo;
        TextView amount;
        TextView status;

        Button btnViewDetails;
        Button btnHideDetails;

        RecyclerView rvVoucherUsed;
        MedPadalaTransactionVouchersUsedAdapter mAdapter;
        List<MedPadalaVoucherUsed> vouchers = new ArrayList<>();

        ProgressBar progressBar;

        public TransactionViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.medpadala_date_completed);
            time = (TextView) itemView.findViewById(R.id.medpadala_time_completed);
            transNo = (TextView) itemView.findViewById(R.id.tv_trans_no);
            recipientNo = (TextView) itemView.findViewById(R.id.tv_recipient_mobile);
            amount = (TextView) itemView.findViewById(R.id.amount);
            status = (TextView) itemView.findViewById(R.id.status);

            btnHideDetails = (Button) itemView.findViewById(R.id.btn_hide_voucher_details);
            btnViewDetails = (Button) itemView.findViewById(R.id.btn_view_voucher_details);
            btnViewDetails.setTag(itemView.getTag());
            btnViewDetails.setOnClickListener(this);
            btnHideDetails.setOnClickListener(this);

            rvVoucherUsed = (RecyclerView) itemView.findViewById(R.id.recycler_view_borrowings_details);
            rvVoucherUsed.setLayoutManager(new LinearLayoutManager(mContext));
            rvVoucherUsed.setNestedScrollingEnabled(false);
            mAdapter = new MedPadalaTransactionVouchersUsedAdapter(mContext, vouchers);
            rvVoucherUsed.setAdapter(mAdapter);

            progressBar = (ProgressBar) itemView.findViewById(R.id.pbrecyclerProgress);
            progressBar.setVisibility(View.GONE);

        }

        MedPadalaTransaction trans;

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            switch (v.getId()) {
                case R.id.btn_view_voucher_details: {
                    trans = mTransactions.get(position);
                    btnViewDetails.setVisibility(View.GONE);
                    btnHideDetails.setVisibility(View.VISIBLE);
                    rvVoucherUsed.setVisibility(View.VISIBLE);
                    if (db.getRowsWithTransactionNo(db, trans.getTransactionNo()).size() <= 0)
                        getSession();
                    else
                        mAdapter.update(db.getRowsWithTransactionNo(db, trans.getTransactionNo()));
                    break;
                }
                case R.id.btn_hide_voucher_details: {
                    btnHideDetails.setVisibility(View.GONE);
                    btnViewDetails.setVisibility(View.VISIBLE);
                    rvVoucherUsed.setVisibility(View.GONE);
                    break;
                }
            }
        }

//        private void getSession() {
//            progressBar.setVisibility(View.VISIBLE);
//            Call<String> call = RetrofitBuild.getCommonApiService(mContext)
//                    .getRegisteredSession(
//                            imei,
//                            userid
//                    );
//
//            call.enqueue(getSessionCallback);
//        }
//
//        private Callback<String> getSessionCallback = new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                try {
//                    ResponseBody errBody = response.errorBody();
//                    if (errBody == null) {
//                        if (!response.body().isEmpty()
//                                && !response.body().contains("<!DOCTYPE html>")
//                                && !response.body().equals("001")
//                                && !response.body().equals("002")
//                                && !response.body().equals("003")
//                                && !response.body().equals("004")
//                                && !response.body().equals("error")) {
//                            sessionid = response.body();
//                            getVoucherDetailsForTransaction(trans);
//                        } else {
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    } else {
//                        progressBar.setVisibility(View.GONE);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable throwable) {
//                progressBar.setVisibility(View.GONE);
//            }
//        };

        private void getSession() {
            if(CommonFunctions.getConnectivityStatus(mContext) > 0) {
                progressBar.setVisibility(View.VISIBLE);
                getVoucherDetailsForTransaction(trans);
            } else {
                progressBar.setVisibility(View.GONE);
                ((BaseActivity) mContext).showNoInternetToast();
            }
        }

        private void getVoucherDetailsForTransaction(MedPadalaTransaction transaction) {
            Call<GetMedPadalaTransactionVoucherDetailsResponse> call = RetrofitBuild.getMedPadalaApiService(mContext)
                    .getMedPadalaTransactionVoucherDetails(
                            sessionid,
                            imei,
                            userid,
                            borrowerid,
                            CommonFunctions.getSha1Hex(imei + userid + sessionid),
                            transaction.getTransactionNo(),
                            CommonFunctions.getYearFromDate(transaction.getDateTimeCompleted()),
                            CommonFunctions.getMonthFromDate(transaction.getDateTimeCompleted())
                    );

            call.enqueue(getVoucherDetailsForTransactionCallback);
        }

        private Callback<GetMedPadalaTransactionVoucherDetailsResponse> getVoucherDetailsForTransactionCallback = new Callback<GetMedPadalaTransactionVoucherDetailsResponse>() {
            @Override
            public void onResponse(Call<GetMedPadalaTransactionVoucherDetailsResponse> call, Response<GetMedPadalaTransactionVoucherDetailsResponse> response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    ResponseBody errBody = response.errorBody();
                    if (errBody == null) {
                        if (response.body().getStatus().equals("000")) {
                            List<MedPadalaVoucherUsed> list = response.body().getData();
                            Logger.debug("update", "onResponse: " + String.valueOf(list.size()));
                            db.removeRowsWithTransactionNo(db, trans.getTransactionNo());
                            for (MedPadalaVoucherUsed voucher : list) {
                                db.insertMedPadalaTransactionVoucherUsed(db, voucher);
                            }
//                            mAdapter.update(db.getRowsWithTransactionNo(db, trans.getTransactionNo()));
                            mAdapter.update(list);
                        } else {
                            ((BaseActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                            btnHideDetails.setVisibility(View.GONE);
                            btnViewDetails.setVisibility(View.VISIBLE);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetMedPadalaTransactionVoucherDetailsResponse> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
            }
        };
    }


}
