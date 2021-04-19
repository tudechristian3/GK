package com.goodkredit.myapplication.adapter.skycable;

import android.content.Context;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.bean.SkycablePPVHistory;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

public class ViewSkycablePPVHistoryAdapter extends RecyclerView.Adapter<ViewSkycablePPVHistoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<SkycablePPVHistory> mGridData;

    public ViewSkycablePPVHistoryAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    public void update(List<SkycablePPVHistory> data) {
        int startPos = mGridData.size() + 1;
        mGridData.clear();
        mGridData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public void clear() {
        int startPos = mGridData.size();
        mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @Override
    public ViewSkycablePPVHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.skycable_ppv_history_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewSkycablePPVHistoryAdapter.MyViewHolder holder, int position) {
        SkycablePPVHistory skycablePPVHistory = mGridData.get(position);

        if (skycablePPVHistory.getDateTimeCompleted().equals(".")) {
            holder.txvDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(skycablePPVHistory.getDateTimeIN())));
            holder.txvTime.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(skycablePPVHistory.getDateTimeIN())));
        } else {
            holder.txvDate.setText(CommonFunctions.getDateFromSkycableDateTime(skycablePPVHistory.getDateTimeCompleted()));
            holder.txvTime.setText(CommonFunctions.getTimeFromSkycableDateTime(skycablePPVHistory.getDateTimeCompleted()));
        }

        if (skycablePPVHistory.getStatus().equals("PAID")) {
            holder.txnStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_4A90E2));
        } else if (skycablePPVHistory.getStatus().equals("FOR PAYMENT")) {
            holder.txnStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_48B14C));
        } else if (skycablePPVHistory.getStatus().equals("DECLINED")) {
            holder.txnStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_E91E63));
        } else {
            holder.txnStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_676767));
        }

        holder.txnStatus.setText(skycablePPVHistory.getStatus());
        holder.txvApprovalCode.setText(skycablePPVHistory.getSubscriptionTxnID());
        holder.txvSkycableAccountNo.setText(skycablePPVHistory.getSkyCableAccountNo());
        holder.txvPPVName.setText(skycablePPVHistory.getPPVName());
        holder.txvAmountPaid.setText(CommonFunctions.currencyFormatter(String.valueOf(skycablePPVHistory.getTotalAmountPaid())));
        holder.txvName.setText(CommonFunctions.capitalizeWord(skycablePPVHistory.getCustomerFirstName() + " " + skycablePPVHistory.getCustomerLastName()));
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txvDate;
        private TextView txnStatus;
        private TextView txvTime;
        private TextView txvApprovalCode;
        private TextView txvSkycableAccountNo;
        private TextView txvPPVName;
        private TextView txvAmountPaid;
        private TextView txvName;
        private LinearLayout historyLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            txvDate = (TextView) itemView.findViewById(R.id.txvDate);
            txnStatus = (TextView) itemView.findViewById(R.id.txnStatus);
            txvTime = (TextView) itemView.findViewById(R.id.txvTime);
            txvApprovalCode = (TextView) itemView.findViewById(R.id.txvApprovalCode);
            txvSkycableAccountNo = (TextView) itemView.findViewById(R.id.txvSkycableAccountNo);
            txvPPVName = (TextView) itemView.findViewById(R.id.txvPPVName);
            txvAmountPaid = (TextView) itemView.findViewById(R.id.txvAmountPaid);
            txvName = (TextView) itemView.findViewById(R.id.txvName);
            historyLayout = (LinearLayout) itemView.findViewById(R.id.historyLayout);
            historyLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            if (position > -1) {

                SkycablePPVHistory skycablePPVHistory = mGridData.get(position);

                switch (v.getId()) {
                    case R.id.historyLayout: {
                        Bundle args = new Bundle();
                        args.putParcelable("skycablePPVHistory", skycablePPVHistory);
                        args.putString("TYPE","HISTORY");
                        ((SkyCableActivity) mContext).displayView(6, args);
                        break;
                    }
                }

            }

        }
    }
}
