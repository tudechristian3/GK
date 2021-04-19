package com.goodkredit.myapplication.adapter.prepaidrequest;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.prepaidrequest.ViewPaymentChannelsActivity;
import com.goodkredit.myapplication.bean.Branches;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.Logger;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 11/6/2017.
 */

public class BranchesRecyclerAdapter extends RecyclerView.Adapter<BranchesRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<Branches> mGridData;
    private int currentpos = -1;

    public BranchesRecyclerAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    public void setPaymentBranches(List<Branches> data) {
        Logger.debug("antonhttp", "data: " + new Gson().toJson(data));
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
    public BranchesRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_branches_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BranchesRecyclerAdapter.MyViewHolder holder, int position) {
        try {
            Branches branches = mGridData.get(position);
            holder.branch_name.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Regular.ttf", branches.getOutletName()));

            String detailedaddress = "";

            if (branches.getStreetAddress().trim().length() > 0) {
                detailedaddress = detailedaddress.concat(branches.getStreetAddress());
            }

            if (branches.getCity().trim().length() > 0) {
                detailedaddress = detailedaddress.concat(", " + branches.getCity());
            }

            if (branches.getProvince().trim().length() > 0) {
                detailedaddress = detailedaddress.concat(", " + branches.getProvince());
            }

            if (String.valueOf(branches.getZip()).trim().length() > 0) {
                detailedaddress = detailedaddress.concat(" " + String.valueOf(branches.getZip()));
            }

            if (branches.getCountry().trim().length() > 0) {
                detailedaddress = detailedaddress.concat(", " + branches.getCountry());
            }

            holder.branch_address.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Regular.ttf", detailedaddress));

            holder.branch_distance.setText(CommonFunctions.currencyFormatter(String.valueOf(branches.getCalculatedDistance())).concat(" km."));
            if (currentpos == position) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_E4E4E4));
            } else {
                holder.itemView.setBackgroundColor(Color.WHITE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView branch_name;
        private final TextView branch_address;
        private final TextView branch_distance;

        public MyViewHolder(View itemView) {
            super(itemView);
            branch_name = (TextView) itemView.findViewById(R.id.branch_name);
            branch_address = (TextView) itemView.findViewById(R.id.branch_address);
            branch_distance = (TextView) itemView.findViewById(R.id.branch_distance);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            currentpos = getAdapterPosition();
            Branches branches = mGridData.get(currentpos);
            ((ViewPaymentChannelsActivity) mContext).selectBranchMarker(branches);
            notifyDataSetChanged();
        }
    }
}
