package com.goodkredit.myapplication.adapter.processqueue;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.prepaidrequest.VoucherPrepaidRequestActivity;
import com.goodkredit.myapplication.bean.V2VirtualVoucherRequestQueue;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 11/3/2017.
 */

public class V2VirtualVoucherRequestProcessQueueAdapter extends RecyclerView.Adapter<V2VirtualVoucherRequestProcessQueueAdapter.MyViewHolder> {
    private List<V2VirtualVoucherRequestQueue> mGridData;
    private Context mContext;

    public V2VirtualVoucherRequestProcessQueueAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    private Context getContext() {
        return mContext;
    }

    public void update(List<V2VirtualVoucherRequestQueue> data) {
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
    public V2VirtualVoucherRequestProcessQueueAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_virtual_voucher_request_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(V2VirtualVoucherRequestProcessQueueAdapter.MyViewHolder holder, int position) {
        V2VirtualVoucherRequestQueue voucher = mGridData.get(position);
        holder.txvdateTime.setText(CommonFunctions.convertDate(voucher.getDateTimeCompleted()));
        holder.txvbillingNo.setText(voucher.getVoucherGenerationID());
        holder.txvtotalVouchers.setText(String.valueOf(voucher.getTotalNoOfVouchers()));
        holder.txvtotalAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(voucher.getTotalAmount())));
        String status = "";

        switch (voucher.getStatus()) {
            case "FORPAYMENT": {
                status = "For Payment";
                break;
            }
            default: {
                status = voucher.getStatus();
                break;
            }
        }
        holder.txvstatus.setText(status);
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView txvdateTime;
        private final TextView txvbillingNo;
        private final TextView txvtotalVouchers;
        private final TextView txvtotalAmount;
        private final TextView txvstatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            txvdateTime = (TextView) itemView.findViewById(R.id.dateTime);
            txvbillingNo = (TextView) itemView.findViewById(R.id.billingNo);
            txvtotalVouchers = (TextView) itemView.findViewById(R.id.totalVouchers);
            txvtotalAmount = (TextView) itemView.findViewById(R.id.totalAmount);
            txvstatus = (TextView) itemView.findViewById(R.id.status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            V2VirtualVoucherRequestQueue voucher = mGridData.get(position);
            VoucherPrepaidRequestActivity.start(getContext(), 1, voucher.getVoucherGenerationID(), String.valueOf(voucher.getTotalAmount()));
        }
    }
}
