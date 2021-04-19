package com.goodkredit.myapplication.adapter.transactions;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.billspayment.BillsPaymentBillerDetailsActivity;
import com.goodkredit.myapplication.bean.BillsPaymentLogs;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.transactions.LogsBillsPaymentFragment;

import java.util.List;

/**
 * Created by User-PC on 8/1/2017.
 */

public class LogsBillsPaymentRecyclerAdapter extends RecyclerView.Adapter<LogsBillsPaymentRecyclerAdapter.MyViewHolder> {

    private final Context mContext;
    private List<BillsPaymentLogs> mGridData;
    private LogsBillsPaymentFragment fragment;

    public LogsBillsPaymentRecyclerAdapter(Context context, List<BillsPaymentLogs> mGridData, LogsBillsPaymentFragment fragment) {
        this.mContext = context;
        this.mGridData = mGridData;
        this.fragment = fragment;
    }

    private Context getContext() {
        return mContext;
    }

    public void setBillsPaymentLogsData(List<BillsPaymentLogs> mGridData) {
        this.mGridData.clear();
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }


    @Override
    public LogsBillsPaymentRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_transactions_logs_bills_payment_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LogsBillsPaymentRecyclerAdapter.MyViewHolder holder, int position) {
        BillsPaymentLogs item = mGridData.get(position);
        if (item.getTxnStatus().equals("SUCCESS")) {
            holder.btn_retry.setVisibility(View.GONE);
            holder.btn_send_email.setVisibility(View.VISIBLE);
            holder.txn_status.setTextColor(ContextCompat.getColor(getContext(), R.color.color_4A90E2));
        } else if (item.getTxnStatus().equals("FAILED")) {
            holder.btn_retry.setVisibility(View.VISIBLE);
            holder.btn_send_email.setVisibility(View.GONE);
            holder.txn_status.setTextColor(ContextCompat.getColor(getContext(), R.color.color_E91E63));
        }

        holder.txn_date.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(item.getDateTimeCompleted())));
        holder.txn_status.setText(item.getTxnStatus());
        holder.approval_code.setText(item.getGKTransactionNo());
        holder.reference_number.setText(item.getBillerTransactionNo());
        holder.biller.setText(item.getBillerName());
        holder.account_no.setText(item.getAccountNo());
        holder.account_name.setText(item.getAccountName());
        holder.amountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(item.getAmountPaid())));
        holder.date_completed.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(item.getDateTimeCompleted())));
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Button btn_send_email;
        private final Button btn_retry;
        private final TextView approval_code;
        private final TextView txn_date;
        private final TextView txn_status;
        private final TextView reference_number;
        private final TextView biller;
        private final TextView account_no;
        private final TextView account_name;
        private final TextView amountpaid;
        private final TextView date_completed;


        public MyViewHolder(View itemView) {
            super(itemView);
            btn_send_email = (Button) itemView.findViewById(R.id.send_to_email);
            btn_retry = (Button) itemView.findViewById(R.id.btn_retry);
            approval_code = (TextView) itemView.findViewById(R.id.approval_code);
            txn_date = (TextView) itemView.findViewById(R.id.txn_date);
            txn_status = (TextView) itemView.findViewById(R.id.txn_status);
            reference_number = (TextView) itemView.findViewById(R.id.reference_number);
            biller = (TextView) itemView.findViewById(R.id.biller);
            account_no = (TextView) itemView.findViewById(R.id.account_no);
            account_name = (TextView) itemView.findViewById(R.id.account_name);
            amountpaid = (TextView) itemView.findViewById(R.id.amountpaid);
            date_completed = (TextView) itemView.findViewById(R.id.date_completed);

            btn_send_email.setOnClickListener(this);
            btn_retry.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            BillsPaymentLogs billitem = mGridData.get(position);
            switch (v.getId()) {
                case R.id.send_to_email: {
                    fragment.sendEmailForBillsPayment(billitem);
                    break;
                }
                case R.id.btn_retry: {
                    BillsPaymentBillerDetailsActivity.start(getContext(), billitem);
                    break;
                }
            }
        }
    }


}
