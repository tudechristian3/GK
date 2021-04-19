package com.goodkredit.myapplication.adapter.processqueue;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.V2BillPaymentQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 7/31/2017.
 */

public class V2BillsPaymentProcessQueueAdapter extends RecyclerView.Adapter<V2BillsPaymentProcessQueueAdapter.PaymentViewHolder> {

    private Context mContext;
    private List<V2BillPaymentQueue> mArrayList = new ArrayList<>();

    public V2BillsPaymentProcessQueueAdapter(Context mContext, List<V2BillPaymentQueue> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    public void update(List<V2BillPaymentQueue> mArrayList) {
        this.mArrayList.clear();
        this.mArrayList = mArrayList;
        notifyDataSetChanged();

    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_process_queue_bills_payment, parent, false);
        // Return a new holder instance
        PaymentViewHolder viewHolder = new PaymentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(V2BillsPaymentProcessQueueAdapter.PaymentViewHolder holder, int position) {
        V2BillPaymentQueue payment = mArrayList.get(position);

        Glide
                .with(mContext)
                .load(payment.getBillerLogo())
                .apply(RequestOptions
                        .fitCenterTransform()
                        .placeholder(R.drawable.ic_default_biller)
                        .error(R.drawable.ic_default_biller))
                .into(holder.imgvBillerLogo);

        holder.tvBillerName.setText(payment.getBillerName());
        holder.tvAccountNumber.setText(payment.getAccountNo());
        holder.tvAccountName.setText(payment.getAccountName());
        holder.tvDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(payment.getDateTimeIN())));
        holder.tvAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(payment.getTotalAmountPaid())));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBillerName;
        private ImageView imgvBillerLogo;
        private TextView tvAccountNumber;
        private TextView tvAccountName;
        private TextView tvDate;
        private TextView tvAmount;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            tvBillerName = (TextView) itemView.findViewById(R.id.tv_biller_name);
            imgvBillerLogo = (ImageView) itemView.findViewById(R.id.imgv_biller_logo);
            tvAccountNumber = (TextView) itemView.findViewById(R.id.tv_account_number);
            tvAccountName = (TextView) itemView.findViewById(R.id.tv_account_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_amount);
        }
    }
}
