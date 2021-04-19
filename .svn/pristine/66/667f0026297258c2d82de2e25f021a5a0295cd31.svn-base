package com.goodkredit.myapplication.adapter.transactions;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.prepaidload.BuyPrepaidLoadActivity;
import com.goodkredit.myapplication.activities.reloadretailer.PurchaseRetailerLoadActivity;
import com.goodkredit.myapplication.bean.PrepaidLogs;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.List;

/**
 * Created by User-PC on 8/1/2017.
 */

public class LogsPrepaidRecyclerAdapter extends RecyclerView.Adapter<LogsPrepaidRecyclerAdapter.MyViewHolder> {

    private final Context mContext;
    private List<PrepaidLogs> mGridData;
    private PrepaidLogs itemPrepaid;
    private String logsType = "";

    public LogsPrepaidRecyclerAdapter(Context context, List<PrepaidLogs> mGridData, String logsType) {
        this.mContext = context;
        this.mGridData = mGridData;
        this.logsType = logsType;
    }

    private Context getContext() {
        return mContext;
    }

    public void setPrepaidLogsData(List<PrepaidLogs> mGridData) {
        this.mGridData.clear();
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public LogsPrepaidRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_transactions_logs_prepaid_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LogsPrepaidRecyclerAdapter.MyViewHolder holder, int position) {
        PrepaidLogs item = mGridData.get(position);
        if (item.getTxnStatus().equals("SUCCESS")) {
            holder.retry.setVisibility(View.GONE);
            holder.load_again.setVisibility(View.VISIBLE);
            holder.txn_status.setTextColor(ContextCompat.getColor(getContext(), R.color.color_4A90E2));
        } else if (item.getTxnStatus().equals("FAILED")) {
            holder.retry.setVisibility(View.VISIBLE);
            holder.load_again.setVisibility(View.GONE);
            holder.txn_status.setTextColor(ContextCompat.getColor(getContext(), R.color.color_E91E63));
        }

        if (logsType.equals("PREPAIDLOGS")) {
            holder.discountLayout.setVisibility(View.GONE);
            holder.netamountLayout.setVisibility(View.GONE);
        } else if (logsType.equals("RELOADINGLOGS")) {
            holder.discountLayout.setVisibility(View.VISIBLE);
            holder.netamountLayout.setVisibility(View.VISIBLE);

            holder.discount.setText(CommonFunctions.currencyFormatter(String.valueOf(item.getDiscount())));
            holder.netamount.setText(CommonFunctions.currencyFormatter(String.valueOf(item.getNetAmount())));
        }

        holder.txn_date.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(item.getDateTimeCompleted())));
        holder.txn_status.setText(item.getTxnStatus());
        holder.date_completed.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(item.getDateTimeCompleted())));
        holder.approval_code.setText(item.getTransactionNo());
        holder.mobile_number.setText(item.getMobileTarget());
        holder.product_code.setText(item.getProductCode());
        holder.amount.setText(CommonFunctions.currencyFormatter(String.valueOf(item.getAmount())));
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Button load_again;
        private final Button retry;
        private final TextView date_completed;
        private final TextView txn_date;
        private final TextView txn_status;
        private final TextView approval_code;
        private final TextView mobile_number;
        private final TextView product_code;
        private final TextView amount;
        private final LinearLayout discountLayout;
        private final LinearLayout netamountLayout;
        private final TextView discount;
        private final TextView netamount;

        private MyViewHolder(View itemView) {
            super(itemView);
            load_again = itemView.findViewById(R.id.btn_load_again);
            retry = itemView.findViewById(R.id.btn_retry);
            date_completed = itemView.findViewById(R.id.date_completed);
            txn_date = itemView.findViewById(R.id.txn_date);
            txn_status = itemView.findViewById(R.id.txn_status);
            approval_code = itemView.findViewById(R.id.approval_code);
            mobile_number = itemView.findViewById(R.id.mobile_number);
            product_code = itemView.findViewById(R.id.product_code);
            amount = itemView.findViewById(R.id.amount);
            discountLayout = itemView.findViewById(R.id.discountLayout);
            netamountLayout = itemView.findViewById(R.id.netamountLayout);
            discount = itemView.findViewById(R.id.discount);
            netamount = itemView.findViewById(R.id.netamount);

            load_again.setOnClickListener(this);
            retry.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != -1) {
                itemPrepaid = mGridData.get(position);
                switch (v.getId()) {
                    case R.id.btn_load_again:
                    case R.id.btn_retry: {
                        if (logsType.equals("PREPAIDLOGS"))
                            BuyPrepaidLoadActivity.start(getContext(), itemPrepaid);
                        else if (logsType.equals("RELOADINGLOGS"))
                            PurchaseRetailerLoadActivity.start(getContext(), itemPrepaid);
                        break;
                    }
                }
            }
        }
    }

}
