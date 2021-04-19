package com.goodkredit.myapplication.adapter.transactions;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.TransactionsVouchers;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.v2Models.TransactionModel;
import com.goodkredit.myapplication.utilities.Logger;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by User-PC on 8/2/2017.
 */

public class TransferredVouchersRecyclerAdapter extends RecyclerView.Adapter<TransferredVouchersRecyclerAdapter.MyViewHolder> {

    private final Context mContext;
    private List<TransactionModel> mGridData;

    public TransferredVouchersRecyclerAdapter(Context context, List<TransactionModel> mGridData) {
        this.mContext = context;
        this.mGridData = mGridData;
    }

    private Context getContext() {
        return mContext;
    }

    public void setReceivedVouchersData(List<TransactionModel> mGridData) {
        this.mGridData.clear();
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_transferred_vouchers_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TransactionModel item = mGridData.get(position);

        Logger.debug("okhttp","ITEMS : "+new Gson().toJson(item));

        holder.txn_date.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(item.getDateTimeCompleted())));
        holder.txn_time.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(item.getDateTimeCompleted())));

        if (item.getTransferType().toUpperCase().equals("BORROWER"))
            holder.transfer_type.setText("SUBSCRIBER");
        else
            holder.transfer_type.setText(item.getTransferType());

        holder.sender.setText(item.getRecipientBorrowerName());
        holder.voucher_id.setText(item.getVoucherCode());
        holder.txn_amount.setText(CommonFunctions.currencyFormatter(item.getVoucherDenoms()));
        holder.transfertxnno.setText(item.getTransferTxnNo().split("-")[0]);
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView txn_date;
        private final TextView txn_time;
        private final TextView transfer_type;
        private final TextView sender;
        private final TextView voucher_id;
        private final TextView txn_amount;
        private final TextView transfertxnno;


        public MyViewHolder(View itemView) {
            super(itemView);
            txn_date = itemView.findViewById(R.id.txn_date);
            txn_time = itemView.findViewById(R.id.txn_time);
            transfer_type = itemView.findViewById(R.id.transfer_type);
            sender = itemView.findViewById(R.id.sender);
            voucher_id = itemView.findViewById(R.id.voucher_id);
            txn_amount = itemView.findViewById(R.id.txn_amount);
            transfertxnno = itemView.findViewById(R.id.transfertxnno);

        }
    }
}
