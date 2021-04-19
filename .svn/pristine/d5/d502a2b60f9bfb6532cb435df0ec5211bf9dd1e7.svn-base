package com.goodkredit.myapplication.adapter.egame;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.egame.EGameTransactionDetailsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.egame.EGameTransactions;

import java.util.List;

public class EGameTransactionsAdapter extends RecyclerView.Adapter<EGameTransactionsAdapter.ViewHolder> {

    private List<EGameTransactions> mGridData;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public EGameTransactionsAdapter(List<EGameTransactions> mGridData, Context mContext) {
        this.mGridData = mGridData;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    public void updateList(List<EGameTransactions> mGridData) {
        this.mGridData = mGridData;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EGameTransactionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_egame_transactions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EGameTransactionsAdapter.ViewHolder holder, int position) {
        EGameTransactions eGameTransactions = mGridData.get(position);

        String status = eGameTransactions.getTxnStatus();

        holder.txv_productcode.setText(CommonFunctions.replaceEscapeData(eGameTransactions.getProductCode()));
        holder.txv_amount.setText(CommonFunctions.currencyFormatter(String.valueOf(eGameTransactions.getAmount())));
        holder.txv_transactionno.setText(CommonFunctions.replaceEscapeData(eGameTransactions.getTransactionNo()));
        holder.txv_datetime.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(eGameTransactions.getDateTimeIN())));
        holder.txv_status.setText(status);

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txv_productcode;
        private TextView txv_amount;
        private TextView txv_transactionno;
        private TextView txv_datetime;
        private TextView txv_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txv_productcode = itemView.findViewById(R.id.txv_productcode);
            txv_amount = itemView.findViewById(R.id.txv_amount);
            txv_transactionno = itemView.findViewById(R.id.txv_transactionno);
            txv_datetime = itemView.findViewById(R.id.txv_datetime);
            txv_status = itemView.findViewById(R.id.txv_status);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position > -1) {

                EGameTransactions egameProducts = mGridData.get(position);
                Intent intent = new Intent(mContext, EGameTransactionDetailsActivity.class);
                intent.putExtra(EGameTransactions.KEY_EGAMETRANSACTIONS, egameProducts);
                mContext.startActivity(intent);
            }

        }
    }
}
