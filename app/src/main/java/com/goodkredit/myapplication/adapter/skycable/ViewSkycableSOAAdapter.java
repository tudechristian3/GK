package com.goodkredit.myapplication.adapter.skycable;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.bean.SkycableSOA;

import java.util.ArrayList;
import java.util.List;

public class ViewSkycableSOAAdapter extends RecyclerView.Adapter<ViewSkycableSOAAdapter.MyViewHolder> {
    private Context mContext;
    private List<SkycableSOA> mGridData;

    public ViewSkycableSOAAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    public void update(List<SkycableSOA> data) {
        int startPos = mGridData.size() + 1;
        mGridData.clear();
        mGridData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public void clear() {
        int startPos = mGridData.size();
        mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_skycable_soa_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SkycableSOA skycableSOA = mGridData.get(position);
        holder.txvBillingID.setText(skycableSOA.getBillingID().concat(".soa"));
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txvBillingID;
        private LinearLayout soaLayoutItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            txvBillingID = (TextView) itemView.findViewById(R.id.txvBillingID);
            soaLayoutItem = (LinearLayout) itemView.findViewById(R.id.soaLayoutItem);
            soaLayoutItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position > -1) {

                SkycableSOA skycableSOA = mGridData.get(position);

                switch (v.getId()) {
                    case R.id.soaLayoutItem: {
                        Bundle args = new Bundle();
                        args.putParcelable("skycableSOA", skycableSOA);
                        ((SkyCableActivity) mContext).displayView(9, args);
                        break;
                    }
                }
            }

        }
    }
}
