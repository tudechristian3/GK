package com.goodkredit.myapplication.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.SubscriberBillSummary;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.soa.SettlementDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 7/24/2017.
 */

public class SubscriberBillsAdapter extends RecyclerView.Adapter<SubscriberBillsAdapter.BillsViewHolder> {


    private Context mContext;
    private List<SubscriberBillSummary> mArrayList = new ArrayList<>();


    @Override
    public BillsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.fragment_paid_bills_item, parent, false);
        // Return a new holder instance
        BillsViewHolder viewHolder = new BillsViewHolder(view);
        return viewHolder;
    }

    public SubscriberBillsAdapter(Context context, List<SubscriberBillSummary> arrayList) {
        mArrayList.clear();
        mContext = context;
        mArrayList = arrayList;
    }

    public void updateData(List<SubscriberBillSummary> arraylist) {
        mArrayList.clear();
        mArrayList = arraylist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BillsViewHolder holder, int position) {
        SubscriberBillSummary bill = mArrayList.get(position);

        TextView amount = holder.tvAmount;
        TextView date = holder.tvDueDate;

        amount.setText(CommonFunctions.currencyFormatter(String.valueOf(bill.getAmount())));
        date.setText("Payment due on " + CommonFunctions.convertDate1(bill.getDueDate()));

        holder.itemView.setTag(bill);
        holder.itemView.setOnClickListener(itemViewClick);
    }

    private View.OnClickListener itemViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SubscriberBillSummary bill = (SubscriberBillSummary) v.getTag();
            SettlementDetailsActivity.start(mContext, bill);
        }
    };

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class BillsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAmount;
        private TextView tvDueDate;

        public BillsViewHolder(View itemView) {
            super(itemView);
            tvAmount = (TextView) itemView.findViewById(R.id.totalamount);
            tvDueDate = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
