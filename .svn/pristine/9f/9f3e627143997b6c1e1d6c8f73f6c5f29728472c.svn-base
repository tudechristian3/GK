package com.goodkredit.myapplication.adapter.gkearn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gkearn.GKEarnHomeActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.gkearn.GKEarnConversionPoints;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.List;

public class GKEarnConversionAdapter extends RecyclerView.Adapter<GKEarnConversionAdapter.MyViewHolder> {
    private Context mContext;
    private List<GKEarnConversionPoints> gKEarnConversionPointsList;
    private DatabaseHandler mdb;

    public GKEarnConversionAdapter(Context context) {
        mContext = context;
        gKEarnConversionPointsList = new ArrayList<>();
        mdb = new DatabaseHandler(mContext);
    }

    public void updateData(List<GKEarnConversionPoints> arraylist) {
        gKEarnConversionPointsList.clear();
        gKEarnConversionPointsList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        gKEarnConversionPointsList.clear();
        notifyDataSetChanged();
    }

    @Override
    public GKEarnConversionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.fragment_gkearn_conversion_history_items, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(GKEarnConversionAdapter.MyViewHolder holder, int position) {
        try {
            GKEarnConversionPoints gKEarnConversionPoints = gKEarnConversionPointsList.get(position);

            holder.txv_datetime.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(gKEarnConversionPoints.getDateTimeIN())));

            holder.txv_points.setText(CommonFunctions.commaFormatterWithDecimals(String.valueOf(gKEarnConversionPoints.getBorrowerPointsDeducted())));
            holder.txv_points.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_BOLD));

            holder.txv_label.setText("TOTAL POINTS USED");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return gKEarnConversionPointsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txv_datetime;
        private TextView txv_points;
        private TextView txv_label;

        public MyViewHolder(View itemView) {
            super(itemView);
            txv_datetime = itemView.findViewById(R.id.txv_datetime);
            txv_points = itemView.findViewById(R.id.txv_points);
            txv_label = itemView.findViewById(R.id.txv_label);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position > -1) {
                GKEarnConversionPoints gKEarnConversionPoints = gKEarnConversionPointsList.get(position);

                Bundle args = new Bundle();
                args.putString(GKEarnHomeActivity.GKEARN_KEY_TOPUP_OR_CONVERSION_TO_DETAILS, GKEarnHomeActivity.GKEARN_VALUE_CONVERSION_TO_DETAILS);
                args.putParcelable(GKEarnHomeActivity.GKEARN_KEY_PARCELABLE_CONVERSION, gKEarnConversionPoints);
                GKEarnHomeActivity.start(mContext, GKEarnHomeActivity.GKEARN_FRAGMENT_CONVERSION_AND_TOPUP_HISTORY_DETAILS, args);
            }
        }
    }
}