package com.goodkredit.myapplication.adapter.processqueue;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.model.V2SmartRetailWalletQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 8/23/2017.
 */

public class V2SmartRetailWalletProcessQueueAdapter extends RecyclerView.Adapter<V2SmartRetailWalletProcessQueueAdapter.SmartRetailWalletViewHolder> {

    private Context mContext;
    private List<V2SmartRetailWalletQueue> mArrayList = new ArrayList<>();

    public V2SmartRetailWalletProcessQueueAdapter(Context mContext, List<V2SmartRetailWalletQueue> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    public void update(List<V2SmartRetailWalletQueue> mArrayList) {
        this.mArrayList.clear();
        this.mArrayList = mArrayList;
        notifyDataSetChanged();
    }

    @Override
    public SmartRetailWalletViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_process_queue_prepaid_load, parent, false);
        // Return a new holder instance
        SmartRetailWalletViewHolder viewHolder = new SmartRetailWalletViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SmartRetailWalletViewHolder holder, int position) {
        V2SmartRetailWalletQueue load = mArrayList.get(position);

        holder.tvMobileNumber.setText(load.getMobileTarget());
        holder.tvLoad.setText(load.getAmount());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class SmartRetailWalletViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMobileNumber;
        private TextView tvLoad;

        public SmartRetailWalletViewHolder(View itemView) {
            super(itemView);
            tvMobileNumber = (TextView) itemView.findViewById(R.id.tv_mobile_number);
            tvLoad = (TextView) itemView.findViewById(R.id.tv_load);
        }
    }
}
