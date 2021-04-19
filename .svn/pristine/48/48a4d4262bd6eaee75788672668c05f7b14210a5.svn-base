package com.goodkredit.myapplication.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Outlets;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 2/21/2017.
 */

public class OutletsAdapter extends RecyclerView.Adapter<OutletsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Outlets> mArrayList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView address;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.guarantorname);
            address = (TextView) itemView.findViewById(R.id.guarantoraddress);
        }
    }

    public OutletsAdapter(Context context, ArrayList<Outlets> arrayList) {
        mContext = context;
        mArrayList = arrayList;
    }

    private Context getContext() {
        return mContext;
    }

    public void updateData(ArrayList<Outlets> arraylist) {
        mArrayList = arraylist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Outlets obj = mArrayList.get(position);
        // Set item views based on your views and data model
        TextView tvName = holder.name;
        TextView tvAddress = holder.address;

        tvName.setText(CommonFunctions.replaceEscapeData(obj.getOutletName()));
        tvAddress.setText(CommonFunctions.replaceEscapeData(obj.getOutletStreetAddress()) + " "
                + CommonFunctions.replaceEscapeData(obj.getOutletCity()) + " "
                + CommonFunctions.replaceEscapeData(obj.getOutletProvince()));
    }

    @Override
    public OutletsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View view = inflater.inflate(R.layout.fragment_guarantors_item, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}
