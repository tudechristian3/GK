package com.goodkredit.myapplication.adapter.egame;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.egame.EGameProductsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.egame.EGameProducts;
import com.google.gson.Gson;

import java.util.List;

public class EGameProductsAdapter extends RecyclerView.Adapter<EGameProductsAdapter.ViewHolder> {

    private List<EGameProducts> mGridData;
    private LayoutInflater layoutInflater;
    private Context mContext;
    private EGameProductsActivity mActivity;
    private DatabaseHandler mdb;

    public EGameProductsAdapter(List<EGameProducts> mGridData, Context mContext, EGameProductsActivity activity, DatabaseHandler mdb) {
        this.mGridData = mGridData;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mActivity = activity;
        this.mdb = mdb;
    }

    public void updateList(List<EGameProducts> mGridData) {
        this.mGridData = mGridData;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EGameProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_egame_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EGameProductsAdapter.ViewHolder holder, int position) {
        EGameProducts productcodes = mGridData.get(position);

        holder.txv_productcode.setText(CommonFunctions.replaceEscapeData(productcodes.getProductCode()));
        holder.txv_amount.setText("PHP ".concat(CommonFunctions.currencyFormatter(String.valueOf(productcodes.getAmount()))));
        holder.txv_description.setText(CommonFunctions.replaceEscapeData(productcodes.getDescription()));
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txv_productcode;
        private TextView txv_amount;
        private TextView txv_description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txv_productcode = (TextView) itemView.findViewById(R.id.txv_productcode);
            txv_amount = (TextView) itemView.findViewById(R.id.txv_amount);
            txv_description = (TextView) itemView.findViewById(R.id.txv_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position > -1) {

                EGameProducts egameProducts = mGridData.get(position);
                mActivity.setEgameProductDetails(egameProducts);
            }

        }
    }
}
