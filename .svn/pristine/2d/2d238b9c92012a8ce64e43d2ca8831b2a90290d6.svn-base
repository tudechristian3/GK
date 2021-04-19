package com.goodkredit.myapplication.adapter.coopassistant.member;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantPayments;
import com.goodkredit.myapplication.utilities.CacheManager;

import java.util.List;

public class CoopAssistantPaymentsAdapter extends RecyclerView.Adapter<CoopAssistantPaymentsAdapter.ViewHolder> {

    private List<CoopAssistantPayments> mGridData;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public CoopAssistantPaymentsAdapter(Context context) {
        this.mGridData = CacheManager.getInstance().getCoopAssistantPayments();
        this.layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void updateList(List<CoopAssistantPayments> mGridData) {
        this.mGridData = mGridData;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CoopAssistantPaymentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.item_coopassistant_bills, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoopAssistantPaymentsAdapter.ViewHolder holder, int position) {
        CoopAssistantPayments cooppayment = mGridData.get(position);

        try{
            String paymenttxnid = CommonFunctions.replaceEscapeData(cooppayment.getPaymentTxnID());
            String datetime = CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(cooppayment.getDateTimeIN()));
            String membername = CommonFunctions.replaceEscapeData(cooppayment.getMemberName());
            String amount = CommonFunctions.currencyFormatter(String.valueOf(cooppayment.getTotalAmount()));

            holder.txv_paymenttxnid.setText(paymenttxnid);
            holder.txv_datetime.setText(datetime);
            holder.txv_name.setText(membername);
            holder.txv_totalamount.setText(amount);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txv_paymenttxnid;
        private TextView txv_name;
        private TextView txv_datetime;
        private TextView txv_totalamount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txv_paymenttxnid= itemView.findViewById(R.id.txv_coop_billtype);
            txv_name = itemView.findViewById(R.id.txv_coop_borrowername);
            txv_datetime = itemView.findViewById(R.id.txv_coop_datetime);
            txv_totalamount = itemView.findViewById(R.id.txv_coop_billamount);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position > -1) {
                Bundle args = new Bundle();
                CoopAssistantPayments cooppayments = mGridData.get(position);
                args.putParcelable(CoopAssistantPayments.KEY_COOPPAYMENTS, cooppayments);
                CoopAssistantHomeActivity.start(mContext, 12, args);
            }
        }
    }
}
