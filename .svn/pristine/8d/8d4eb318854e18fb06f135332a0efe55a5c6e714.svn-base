package com.goodkredit.myapplication.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Outlets;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;

/**
 * Created by GoodApps on 06/11/2017.
 */

public class BranchesAdapter extends RecyclerView.Adapter<BranchesAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Outlets> mArrayList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView branchlogo;
        TextView name;
        TextView address;

        public ViewHolder(View itemView) {
            super(itemView);
            branchlogo = (ImageView) itemView.findViewById(R.id.mBranchPic);
            name = (TextView) itemView.findViewById(R.id.branchesname);
            address = (TextView) itemView.findViewById(R.id.branchesaddress);
        }
    }

    public BranchesAdapter(Context context, ArrayList<Outlets> arrayList) {
        mContext = context;
        mArrayList = arrayList;
    }

    private Context getContext() {
        return mContext;
    }

    public void updateData(ArrayList<Outlets> arraylist) {
        mArrayList.clear();
        mArrayList = arraylist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    @Override
    public void onBindViewHolder(BranchesAdapter.ViewHolder holder, int position) {
        Outlets obj = mArrayList.get(position);
        TextView tvName = holder.name;
        TextView tvAddress = holder.address;

        String branchname = CommonFunctions.replaceEscapeData(obj.getOutletBranchName());
        String branchadress = CommonFunctions.replaceEscapeData(obj.getOutletStreetAddress());

        if (branchname.equals("") || branchname.equals(".")) {
            tvName.setText("N/A");
        } else {
            tvName.setText(CommonFunctions.replaceEscapeData(obj.getOutletBranchName()));
        }

        if (branchadress.equals("") || branchadress.equals(".")) {
            tvAddress.setText("N/A");
        } else {
            tvAddress.setText(CommonFunctions.replaceEscapeData(obj.getOutletStreetAddress()));
        }
     }

    @Override
    public BranchesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View view = inflater.inflate(R.layout.activity_merchant_details_branches_items, parent, false);
        // Return a new holder instance
        BranchesAdapter.ViewHolder viewHolder = new BranchesAdapter.ViewHolder(view);
        return viewHolder;
    }
}
