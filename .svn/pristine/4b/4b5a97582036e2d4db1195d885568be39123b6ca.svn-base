package com.goodkredit.myapplication.adapter.gknegosyo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.GKNegosyoTransactionDetailPerServiceType;

import java.util.ArrayList;
import java.util.List;

public class GKNegosyoDiscountTransactionAdapter extends RecyclerView.Adapter<GKNegosyoDiscountTransactionAdapter.DiscountTransactionViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private List<GKNegosyoTransactionDetailPerServiceType> mData = new ArrayList<>();

    public GKNegosyoDiscountTransactionAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void update(List<GKNegosyoTransactionDetailPerServiceType> data) {
        if (getItemCount() < 30) {
            mData.clear();
            mData = data;
            notifyDataSetChanged();
        } else {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public DiscountTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gk_negosyo_earning_details, parent, false);
        return new DiscountTransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountTransactionViewHolder holder, int position) {
        GKNegosyoTransactionDetailPerServiceType details = mData.get(position);

        holder.datetime.setText(CommonFunctions.convertDate(details.getDateTimeCompleted()));
        holder.transactionno.setText(details.getMerchantTransactionNo());
        holder.merchantname.setText(details.getMerchantName());
        holder.totalamount.setText("₱" + CommonFunctions.currencyFormatter(String.valueOf(details.getAmount())));
        holder.discount.setText("₱" + CommonFunctions.currencyFormatter(String.valueOf(details.getResellerDiscount())));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class DiscountTransactionViewHolder extends RecyclerView.ViewHolder {

        private TextView datetime;
        private TextView transactionno;
        private TextView merchantname;
        private TextView totalamount;
        private TextView discount;


        public DiscountTransactionViewHolder(View itemView) {
            super(itemView);

            datetime = itemView.findViewById(R.id.datetime);
            transactionno = itemView.findViewById(R.id.transactionno);
            merchantname = itemView.findViewById(R.id.merchantname);
            totalamount = itemView.findViewById(R.id.totalamount);
            discount = itemView.findViewById(R.id.discount);
        }
    }
}
