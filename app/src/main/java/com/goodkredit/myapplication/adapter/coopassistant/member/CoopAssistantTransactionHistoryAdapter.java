package com.goodkredit.myapplication.adapter.coopassistant.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantTransactionHistory;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantTransactionHistoryAdapter extends RecyclerView.Adapter<CoopAssistantTransactionHistoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<CoopAssistantTransactionHistory> coopTransactionHistoryList;

    public CoopAssistantTransactionHistoryAdapter(Context context) {
        mContext = context;
        coopTransactionHistoryList = new ArrayList<>();
    }

    public void updateData(List<CoopAssistantTransactionHistory> arraylist) {
        coopTransactionHistoryList.clear();
        coopTransactionHistoryList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        coopTransactionHistoryList.clear();
        notifyDataSetChanged();
    }

    @Override
    public CoopAssistantTransactionHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_coopassistant_transactionhistory_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(CoopAssistantTransactionHistoryAdapter.MyViewHolder holder, int position) {
        try {
            CoopAssistantTransactionHistory coopAssistantTransactionHistory = coopTransactionHistoryList.get(position);

            holder.txv_datetime.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(coopAssistantTransactionHistory.getDateTimeIN())));

            String transactionno = coopAssistantTransactionHistory.getGetVoucherTxnNo();
            holder.txv_no.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, "Txn#:" + transactionno));

            String totalvoucheramount = CommonFunctions.currencyFormatter(String.valueOf(coopAssistantTransactionHistory.getTotalVoucherAmount()));
            holder.txv_vouchersamount.setText(totalvoucheramount);

            holder.txv_status.setText(coopAssistantTransactionHistory.getStatus());

            if(coopAssistantTransactionHistory.getStatus().equalsIgnoreCase("DECLINED")
                    || coopAssistantTransactionHistory.getStatus().equals("CANCELLED")
                    || coopAssistantTransactionHistory.getStatus().equals("FAILED")){
                holder.txv_status.setTextColor(ContextCompat.getColor(mContext, R.color.color_error_red));
            } else if(coopAssistantTransactionHistory.getStatus().equalsIgnoreCase("COMPLETED")){
                holder.txv_status.setTextColor(ContextCompat.getColor(mContext, R.color.coopassist_green));
            } else if(coopAssistantTransactionHistory.getStatus().equalsIgnoreCase("PENDING")
                    || coopAssistantTransactionHistory.getStatus().equalsIgnoreCase("ON PROCESS")
                    || coopAssistantTransactionHistory.getStatus().equalsIgnoreCase("ONPROCESS")){
                holder.txv_status.setTextColor(ContextCompat.getColor(mContext, R.color.coopassist_gray3));
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return coopTransactionHistoryList.size();
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
