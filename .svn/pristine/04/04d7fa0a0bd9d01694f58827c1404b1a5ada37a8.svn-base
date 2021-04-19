package com.goodkredit.myapplication.adapter.gkstore;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.GKStoreProducts;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoodApps on 26/03/2018.
 */

public class GKStoreSummaryAdapter extends RecyclerView.Adapter<GKStoreSummaryAdapter.MyViewHolder> {
    private Context mContext;
    private List<GKStoreProducts> getsummarylist;
    private DatabaseHandler mdb;

    public GKStoreSummaryAdapter(Context context, List<GKStoreProducts> arrayList) {
        mContext = context;
        getsummarylist = new ArrayList<>();
        mdb = new DatabaseHandler(mContext);
    }

    public void updateSummary(List<GKStoreProducts> mGridData) {
        this.getsummarylist.clear();
        this.getsummarylist = mGridData;
        notifyDataSetChanged();
    }

    public void clear() {
        int startPos = this.getsummarylist.size();
        this.getsummarylist.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @Override
    public GKStoreSummaryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gk_store_summary, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GKStoreSummaryAdapter.MyViewHolder holder, int position) {
        GKStoreProducts fetchorderref = getsummarylist.get(position);

        holder.txtproduct.setText(CommonFunctions.replaceEscapeData(fetchorderref.getProductName()));
        holder.txtquatity.setText(String.valueOf(fetchorderref.getQuantity()));

        double qty = Double.parseDouble(String.valueOf(fetchorderref.getQuantity()));
        double price = fetchorderref.getActualPrice();
        holder.txtactualprice.setText(CommonFunctions.currencyFormatter(String.valueOf(price * qty)));

    }

    @Override
    public int getItemCount() {
        return getsummarylist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtproduct;
        TextView txtactualprice;
        TextView txtquatity;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtproduct = (TextView) itemView.findViewById(R.id.txtproduct);
            txtactualprice = (TextView) itemView.findViewById(R.id.txtactualprice);
            txtquatity = (TextView) itemView.findViewById(R.id.txtquatity);
        }
    }
}
