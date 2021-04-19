package com.goodkredit.myapplication.adapter.gkearn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gkearn.GKEarnHomeActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.gkearn.GKEarnConversionPoints;
import com.goodkredit.myapplication.model.gkearn.GKEarnTopUpHistory;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.List;

public class GKEarnTopUpHistoryAdapter extends RecyclerView.Adapter<GKEarnTopUpHistoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<GKEarnTopUpHistory> mGridData;

    public GKEarnTopUpHistoryAdapter(Context mContext) {
        this.mContext = mContext;
        this.mGridData = CacheManager.getInstance().getGKEarnTopUpHistory();
    }

    public void updateData(List<GKEarnTopUpHistory> arraylist) {
        mGridData.clear();
        mGridData = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        mGridData.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GKEarnTopUpHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.fragment_gkearn_conversion_history_items, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GKEarnTopUpHistoryAdapter.MyViewHolder holder, int position) {
        GKEarnTopUpHistory gkEarnTopUpHistory = mGridData.get(position);

        holder.txv_datetime.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(gkEarnTopUpHistory.getDateTimeIN())));
        holder.txv_points.setText(CommonFunctions.commaFormatterWithDecimals(String.valueOf(gkEarnTopUpHistory.getAmount())));
        holder.txv_label.setText("TOTAL POINTS RECEIVED");
        holder.txv_points.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_BOLD));
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txv_datetime;
        private TextView txv_points;
        private TextView txv_label;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txv_datetime = itemView.findViewById(R.id.txv_datetime);
            txv_points = itemView.findViewById(R.id.txv_points);
            txv_label = itemView.findViewById(R.id.txv_label);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    GKEarnTopUpHistory gkEarnTopUpHistory = mGridData.get(position);

                    Bundle args = new Bundle();
                    args.putString(GKEarnHomeActivity.GKEARN_KEY_TOPUP_OR_CONVERSION_TO_DETAILS, GKEarnHomeActivity.GKEARN_VALUE_TOPUP_TO_DETAILS);
                    args.putParcelable(GKEarnHomeActivity.GKEARN_KEY_PARCELABLE_TOPUP, gkEarnTopUpHistory);
                    GKEarnHomeActivity.start(mContext, GKEarnHomeActivity.GKEARN_FRAGMENT_CONVERSION_AND_TOPUP_HISTORY_DETAILS, args);
                }
            });
        }
    }
}
