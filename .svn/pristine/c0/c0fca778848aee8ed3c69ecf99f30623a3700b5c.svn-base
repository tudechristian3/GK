package com.goodkredit.myapplication.adapter.gknegosyo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoEarningDetailsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.DiscountPerTransactionType;

import java.util.ArrayList;
import java.util.List;

public class GKNegosyoDashboardEarningsAdapter extends RecyclerView.Adapter<GKNegosyoDashboardEarningsAdapter.EarningViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private List<DiscountPerTransactionType> mList = new ArrayList<>();

    private int mYear = 2018;
    private int mMonth = 8;

    public GKNegosyoDashboardEarningsAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void update(List<DiscountPerTransactionType> list, int year, int month) {
        mList.clear();
        mList = list;
        mYear = year;
        mMonth = month;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GKNegosyoDashboardEarningsAdapter.EarningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gk_negosyo_dashboard_earning_type, parent, false);
        return new EarningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GKNegosyoDashboardEarningsAdapter.EarningViewHolder holder, int position) {
        DiscountPerTransactionType discountPerTransactionType = mList.get(position);
        holder.tvEarning.setText("₱" + CommonFunctions.currencyFormatter(String.valueOf(discountPerTransactionType.getTotalDiscount())));
        holder.tvTransactionType.setText(discountPerTransactionType.getServiceName());
        holder.itemView.setTag(discountPerTransactionType);
        holder.itemView.setOnClickListener(itemClickListener);
    }

    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DiscountPerTransactionType discountPerTransactionType = (DiscountPerTransactionType) v.getTag();
            GKNegosyoEarningDetailsActivity.start(mContext, discountPerTransactionType, mYear, mMonth);
        }
    };

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class EarningViewHolder extends RecyclerView.ViewHolder {

        private TextView tvEarning;
        private TextView tvTransactionType;

        public EarningViewHolder(View itemView) {
            super(itemView);
            tvEarning = itemView.findViewById(R.id.tv_total_earnings_per_type);
            tvTransactionType = itemView.findViewById(R.id.tv_earning_source);
        }
    }
}
