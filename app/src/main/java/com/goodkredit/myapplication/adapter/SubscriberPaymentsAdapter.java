package com.goodkredit.myapplication.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.PaymentSummary;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.soa.SettlementDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 7/25/2017.
 */

public class SubscriberPaymentsAdapter extends RecyclerView.Adapter<SubscriberPaymentsAdapter.PaymentsViewHolder> {
    private Context mContext;
    private List<PaymentSummary> mArrayList = new ArrayList<>();

    public SubscriberPaymentsAdapter(Context mContext, List<PaymentSummary> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    public void updateData(List<PaymentSummary> arraylist) {
        mArrayList.clear();
        mArrayList = arraylist;
        notifyDataSetChanged();
    }

    @Override
    public PaymentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.fragment_paid_bills_item, parent, false);
        // Return a new holder instance
        return new PaymentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentsViewHolder holder, int position) {
        PaymentSummary bill = mArrayList.get(position);

        TextView amount = holder.tvAmount;
        TextView date = holder.tvDueDate;

        holder.tvLbl.setText("TOTAL AMOUNT PAID");
        amount.setText(CommonFunctions.currencyFormatter(String.valueOf(bill.getAmount())));
        date.setText("Payment made on " + CommonFunctions.convertDate1(bill.getDateTimeIN()));

        holder.itemView.setTag(bill);
        holder.itemView.setOnClickListener(itemViewClick);
    }

    private View.OnClickListener itemViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PaymentSummary bill = (PaymentSummary) v.getTag();
            SettlementDetailsActivity.start(mContext, bill, 0);
        }
    };

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class PaymentsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAmount;
        private TextView tvDueDate;
        private TextView tvLbl;

        public PaymentsViewHolder(View itemView) {
            super(itemView);
            tvLbl = itemView.findViewById(R.id.transactionnolbl);
            tvAmount = itemView.findViewById(R.id.totalamount);
            tvDueDate = itemView.findViewById(R.id.date);
        }
    }
}
