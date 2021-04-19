package com.goodkredit.myapplication.adapter.viewpendingorders;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.V2VirtualVoucherRequestQueue;

import java.util.List;

/**
 * Created by User-PC on 8/2/2017.
 */

public class ViewPendingOrdersRecyclerAdapter extends RecyclerView.Adapter<ViewPendingOrdersRecyclerAdapter.MyViewHolder> {

    private final Context mContext;
    private List<V2VirtualVoucherRequestQueue> mGridData;

    public ViewPendingOrdersRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

//    public void setPurchasesData(List<PrepaidVoucherTransaction> mGridData) {
//        this.mGridData.clear();
//        this.mGridData = mGridData;
//        notifyDataSetChanged();
//    }

    public void setPendingOrdersData(final List<V2VirtualVoucherRequestQueue> mGridData) {
        int startPos = this.mGridData.size() + 1;
        this.mGridData.clear();
        this.mGridData.addAll(mGridData);
        notifyItemRangeInserted(startPos, mGridData.size());
    }

    public void clear() {
        int startPos = this.mGridData.size();
        this.mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @Override
    public ViewPendingOrdersRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_transactions_purchases_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewPendingOrdersRecyclerAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MyViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            switch (v.getId()) {

            }
        }

    }


}
