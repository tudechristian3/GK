package com.goodkredit.myapplication.adapter.gkearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.V2VirtualVoucherRequestQueue;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.List;

public class GKEarnBuyActivationHistoryAdapter extends RecyclerView.Adapter<GKEarnBuyActivationHistoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<V2VirtualVoucherRequestQueue> mVirtualVoucherRequestList;

    public GKEarnBuyActivationHistoryAdapter(Context context) {
        mContext = context;
        mVirtualVoucherRequestList = new ArrayList<>();
    }

    public void updateData(List<V2VirtualVoucherRequestQueue> arraylist) {
        mVirtualVoucherRequestList.clear();
        mVirtualVoucherRequestList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        mVirtualVoucherRequestList.clear();
        notifyDataSetChanged();
    }

    @Override
    public GKEarnBuyActivationHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_coopassistant_transactionhistory_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(GKEarnBuyActivationHistoryAdapter.MyViewHolder holder, int position) {
        try {
            V2VirtualVoucherRequestQueue v2VirtualVoucherRequestQueue = mVirtualVoucherRequestList.get(position);

            holder.txv_datetime.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(v2VirtualVoucherRequestQueue.getDateTimeRequested())));

            String transactionno = v2VirtualVoucherRequestQueue.getVoucherGenerationID();
            holder.txv_no.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, "Txn#:" + transactionno));

            String totalvoucheramount = CommonFunctions.currencyFormatter(String.valueOf(v2VirtualVoucherRequestQueue.getTotalAmount()));
            holder.txv_vouchersamount.setText(totalvoucheramount);

            holder.txv_status.setText(v2VirtualVoucherRequestQueue.getStatus());

            if (v2VirtualVoucherRequestQueue.getStatus().equalsIgnoreCase("DECLINED")
                    || v2VirtualVoucherRequestQueue.getStatus().equals("CANCELLED")
                    || v2VirtualVoucherRequestQueue.getStatus().equals("EXPIRED")
                    || v2VirtualVoucherRequestQueue.getStatus().equals("FAILED")) {
                holder.txv_status.setTextColor(ContextCompat.getColor(mContext, R.color.color_error_red));
            } else if (v2VirtualVoucherRequestQueue.getStatus().equalsIgnoreCase("COMPLETED")) {
                holder.txv_status.setTextColor(ContextCompat.getColor(mContext, R.color.coopassist_green));
            } else if (v2VirtualVoucherRequestQueue.getStatus().equalsIgnoreCase("FORPAYMENT")) {
                holder.txv_status.setTextColor(ContextCompat.getColor(mContext, R.color.color_gkearn_blue));
            } else {
                holder.txv_status.setTextColor(ContextCompat.getColor(mContext, R.color.coopassist_gray3));
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mVirtualVoucherRequestList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txv_no;
        private TextView txv_vouchersamount;
        private TextView txv_datetime;
        private TextView txv_status;

        public MyViewHolder(View itemView) {
            super(itemView);

            txv_no = (TextView) itemView.findViewById(R.id.txv_no);
            txv_vouchersamount = (TextView) itemView.findViewById(R.id.txv_vouchersamount);
            txv_datetime = (TextView) itemView.findViewById(R.id.txv_datetime);
            txv_status = (TextView) itemView.findViewById(R.id.txv_status);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
