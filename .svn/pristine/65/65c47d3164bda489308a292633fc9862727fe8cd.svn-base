package com.goodkredit.myapplication.adapter.vouchers.payoutone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.payoutone.VoucherPayoutOneBankDepositDetailsActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.vouchers.BankDepositHistoryQueue;
import com.goodkredit.myapplication.utilities.CacheManager;

import java.util.List;

import hk.ids.gws.android.sclick.SClick;

public class VoucherPayoutOneBankDepositHistoryQueueAdapter extends RecyclerView.Adapter<VoucherPayoutOneBankDepositHistoryQueueAdapter.ViewHolder>{

    private Context context;
    private List<BankDepositHistoryQueue> mGridData;
    private LayoutInflater layoutInflater;
    private String from;

    public VoucherPayoutOneBankDepositHistoryQueueAdapter(Context context, String from) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.from = from;

        if(from.equals("HISTORY")){
            this.mGridData = CacheManager.getInstance().getBankDepositHistory();
        } else {
            this.mGridData = CacheManager.getInstance().getBankDepositQueue();
        }
    }

    public void updateList(List<BankDepositHistoryQueue> list) {
        this.mGridData = list;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherPayoutOneBankDepositHistoryQueueAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_voucher_payoutone_bank_deposit_history_queue, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherPayoutOneBankDepositHistoryQueueAdapter.ViewHolder holder, int position) {
        BankDepositHistoryQueue deposit = mGridData.get(position);

        try{

            holder.txv_txnno.setText(CommonFunctions.replaceEscapeData(deposit.getDepositTxnNo()));
            holder.txv_amount.setText(CommonFunctions.currencyFormatter(String.valueOf(deposit.getTotalAmount())));
            holder.txv_datetime.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(deposit.getDateTimeIN())));

            String status = deposit.getStatus().trim();
            holder.txv_status.setText(status);
            if(status.equals("COMPLETED")){
                holder.txv_status.setTextColor(Color.parseColor("#09B403"));
            } else if(status.equals("DECLINED")){
                holder.txv_status.setTextColor(Color.parseColor("#FF0000"));
            } else if(status.equals("PENDING")){
                holder.txv_status.setTextColor(Color.parseColor("6B6B6B"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txv_txnno;
        private TextView txv_amount;
        private TextView txv_datetime;
        private TextView txv_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txv_txnno = itemView.findViewById(R.id.txv_txnno);
            txv_amount = itemView.findViewById(R.id.txv_amount);
            txv_datetime = itemView.findViewById(R.id.txv_datetime);
            txv_status = itemView.findViewById(R.id.txv_status);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if(position > -1){
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;

                BankDepositHistoryQueue bankDepositHistoryQueue = mGridData.get(position);

                Intent intent = new Intent(context, VoucherPayoutOneBankDepositDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("FROM", from);
                intent.putExtra(BankDepositHistoryQueue.KEY_BANKDEPOSITHISTORYQUEUE_OBJECT, bankDepositHistoryQueue);
                context.startActivity(intent);
            }
        }
    }
}
