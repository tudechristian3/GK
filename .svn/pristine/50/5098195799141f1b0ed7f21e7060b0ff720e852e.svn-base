package com.goodkredit.myapplication.adapter.loadmessenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.loadmessenger.LoadTransaction;
import com.goodkredit.myapplication.bean.loadmessenger.ReplenishLogs;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;

public class ReplenishLogsAdapter extends RecyclerView.Adapter<ReplenishLogsAdapter.LoadTxnViewHolder> {

    private ArrayList<ReplenishLogs> replenishLogs = new ArrayList<>();
    public ReplenishLogsAdapter(ArrayList<ReplenishLogs> list) {
        replenishLogs = list;
    }


    @NonNull
    @Override
    public ReplenishLogsAdapter.LoadTxnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_replenish_logs,parent,false);
        return new ReplenishLogsAdapter.LoadTxnViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ReplenishLogsAdapter.LoadTxnViewHolder holder, int position) {
        ReplenishLogs logs = replenishLogs.get(position);
        holder.bind(logs);
    }

    @Override
    public int getItemCount() {
        return replenishLogs.size();
    }

    public static class LoadTxnViewHolder extends RecyclerView.ViewHolder{

        TextView item_replenish_amount,item_replenish_preAvailCredit,item_replenish_postAvailCredit,item_replenish_date,item_replenish_time;

        public LoadTxnViewHolder(@NonNull View itemView) {
            super(itemView);

            item_replenish_amount = itemView.findViewById(R.id.item_replenish_amount);
            item_replenish_preAvailCredit = itemView.findViewById(R.id.item_replenish_preAvailCredit);
            item_replenish_postAvailCredit = itemView.findViewById(R.id.item_replenish_postAvailCredit);
            item_replenish_date = itemView.findViewById(R.id.item_replenish_date);
            item_replenish_time = itemView.findViewById(R.id.item_replenish_time);

        }

        void bind(ReplenishLogs logs){

            item_replenish_amount.setText("Amount : "+CommonFunctions.currencyFormatter(logs.getAmount()));
            item_replenish_preAvailCredit.setText("PreAvailable Credits: "+CommonFunctions.currencyFormatter(logs.getPreAvailableCredits()));
            item_replenish_postAvailCredit.setText("PostAvailable Credits: "+CommonFunctions.currencyFormatter(logs.getPostAvailableCredits()));

            String date = CommonFunctions.convertDate(logs.getDateTimeCompleted());

            item_replenish_date.setText(CommonFunctions.getDateFromDateTime(date));
            item_replenish_time.setText(CommonFunctions.getTimeFromDateTime(date));


        }

    }


}

