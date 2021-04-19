package com.goodkredit.myapplication.adapter.transactions;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.transactions.ViewOthersTransactionsActivity;

import java.util.ArrayList;

public class OthersRecyclerAdapter extends RecyclerView.Adapter<OthersRecyclerAdapter.MyViewHolder> {

    private final Context mContext;
    private ArrayList<String> mGridData = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView othersName;

        public MyViewHolder(View itemView) {
            super(itemView);
            othersName = (TextView) itemView.findViewById(R.id.othersName);
        }
    }

    public OthersRecyclerAdapter(Context context, ArrayList<String> mGridData) {
        this.mContext = context;
        this.mGridData = mGridData;
    }

    private Context getContext() {
        return mContext;
    }

    public void setSettingsData(ArrayList<String> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public OthersRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_others_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OthersRecyclerAdapter.MyViewHolder holder, int position) {
        holder.othersName.setText(mGridData.get(position));
        holder.itemView.setOnClickListener(onClickListener);
        holder.itemView.setTag(position);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewOthersTransactionsActivity.start(getContext(), (Integer) v.getTag());
        }
    };

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

}
