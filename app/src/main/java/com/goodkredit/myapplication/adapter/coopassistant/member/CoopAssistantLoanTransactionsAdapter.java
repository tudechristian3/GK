package com.goodkredit.myapplication.adapter.coopassistant.member;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantLoanTransactions;
import com.goodkredit.myapplication.utilities.CacheManager;

import java.util.List;

public class CoopAssistantLoanTransactionsAdapter extends RecyclerView.Adapter<CoopAssistantLoanTransactionsAdapter.ViewHolder> {

    private List<CoopAssistantLoanTransactions> mGridData;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public CoopAssistantLoanTransactionsAdapter(Context mContext) {
        this.mGridData = CacheManager.getInstance().getCoopAssistantLoanTransactions();
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    public void updateList(List<CoopAssistantLoanTransactions> mGridData) {
        this.mGridData = mGridData;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CoopAssistantLoanTransactionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.item_coopassistant_loan_transactions, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoopAssistantLoanTransactionsAdapter.ViewHolder holder, int position) {

        CoopAssistantLoanTransactions loanTransactions = mGridData.get(position);

        if(loanTransactions.getRequestStatus().equalsIgnoreCase("DECLINED")
                || loanTransactions.getRequestStatus().equals("CANCELLED")){
            holder.txv_loanstatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_error_red));
        } else if(loanTransactions.getRequestStatus().equalsIgnoreCase("APPROVED")){
            holder.txv_loanstatus.setTextColor(ContextCompat.getColor(mContext, R.color.coopassist_green));
        } else if(loanTransactions.getRequestStatus().equalsIgnoreCase("PENDING")
                || loanTransactions.getRequestStatus().equalsIgnoreCase("ON PROCESS")
                || loanTransactions.getRequestStatus().equalsIgnoreCase("ONPROCESS")){
            holder.txv_loanstatus.setTextColor(ContextCompat.getColor(mContext, R.color.coopassist_gray3));
        }

        holder.txv_loantype.setText(CommonFunctions.replaceEscapeData(loanTransactions.getLoanName()));

        String membername = loanTransactions.getMemberName();

        if(membername.contains(".")) {
            membername = CommonFunctions.replaceEscapeData(CommonFunctions.capitalizeWord(membername));
            membername = membername.replace(".", "");
        }
        
        holder.txv_membername.setText(membername);

        holder.txv_datetimerequested.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(loanTransactions.getDateTimeRequested())));
        holder.txv_loanamount.setText(CommonFunctions.currencyFormatter(loanTransactions.getLoanAmount()));
        holder.txv_loanstatus.setText(CommonFunctions.replaceEscapeData(loanTransactions.getRequestStatus()));

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txv_loantype;
        private TextView txv_membername;
        private TextView txv_datetimerequested;
        private TextView txv_loanamount;
        private TextView txv_loanstatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txv_loantype = (TextView) itemView.findViewById(R.id.txv_coop_loantype);
            txv_membername = (TextView) itemView.findViewById(R.id.txv_coop_membername);
            txv_datetimerequested = (TextView) itemView.findViewById(R.id.txv_coop_datetimerequested);
            txv_loanamount = (TextView) itemView.findViewById(R.id.txv_coop_loanamount);
            txv_loanstatus = (TextView) itemView.findViewById(R.id.txv_coop_loanstatus);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            if (position > -1) {
                Bundle args = new Bundle();
                CoopAssistantLoanTransactions cooppayments = mGridData.get(position);
                args.putParcelable(CoopAssistantLoanTransactions.KEY_COOP_LOANTRANSACTIONS, cooppayments);
                CoopAssistantHomeActivity.start(mContext, 15, args);
            }
        }
    }
}
