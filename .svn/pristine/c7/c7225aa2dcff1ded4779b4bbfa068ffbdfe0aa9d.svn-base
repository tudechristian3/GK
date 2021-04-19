package com.goodkredit.myapplication.adapter.processqueue;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.model.V2PrepaidLoadQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 7/31/2017.
 */

public class V2PrepaidLoadProcessQueueAdapter extends RecyclerView.Adapter<V2PrepaidLoadProcessQueueAdapter.QueueViewHolder> {

    private Context mContext;
    private List<V2PrepaidLoadQueue> mArrayList = new ArrayList<>();

    public V2PrepaidLoadProcessQueueAdapter(Context mContext, List<V2PrepaidLoadQueue> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    public void update(List<V2PrepaidLoadQueue> mArrayList) {
        this.mArrayList.clear();
        this.mArrayList = mArrayList;
        notifyDataSetChanged();

    }

    @Override
    public QueueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_process_queue_prepaid_load, parent, false);
        // Return a new holder instance
        QueueViewHolder viewHolder = new QueueViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(QueueViewHolder holder, int position) {
        V2PrepaidLoadQueue load = mArrayList.get(position);

        holder.tvMobileNumber.setText(load.getMobileTarget());
        holder.tvLoad.setText(load.getAmount());
}

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class QueueViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMobileNumber;
        private TextView tvLoad;

        public QueueViewHolder(View itemView) {
            super(itemView);
            tvMobileNumber = (TextView) itemView.findViewById(R.id.tv_mobile_number);
            tvLoad = (TextView) itemView.findViewById(R.id.tv_load);
        }
    }
}
