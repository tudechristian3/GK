package com.goodkredit.myapplication.adapter.gkstore;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.GKStoreProducts;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoodApps on 26/02/2018.
 */

public class GKStoreConfirmationDetailsAdapter extends RecyclerView.Adapter<GKStoreConfirmationDetailsAdapter.MyViewHolder> {
    private final Context mContext;
    private List<GKStoreProducts> gkStoreProducts = new ArrayList<>();
    private String getstorefrom = "";


    public GKStoreConfirmationDetailsAdapter(Context context, List<GKStoreProducts> arrayList) {
        mContext = context;
        gkStoreProducts = arrayList;
        getstorefrom = PreferenceUtils.getStringPreference(mContext, PreferenceUtils.KEY_STORE_FROM);
    }

    private Context getContext() {
        return mContext;
    }

    public void updateData(List<GKStoreProducts> arraylist) {
        gkStoreProducts.clear();
        gkStoreProducts = arraylist;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public GKStoreConfirmationDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gk_store_confirmation_items, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(GKStoreConfirmationDetailsAdapter.MyViewHolder holder, int position) {
        try {
            GKStoreProducts gkstoreref = gkStoreProducts.get(position);

            double qty = 0.00;
            double price = 0.00;

            if(getstorefrom.trim().equals("HISTORY")) {
                //Normal Picture
                Glide.with(mContext)
                        .load(gkstoreref.getProductImage())
                        .apply(new RequestOptions()
                                .fitCenter())
                        .into(holder.mLogoPic);

                int quantity = gkstoreref.getOrderQuantity();
                qty = Double.parseDouble(String.valueOf(gkstoreref.getOrderQuantity()));
                price = gkstoreref.getActualPrice();

                if(quantity <= 1) {
                    holder.quantity.setText(String.format("%d pc", gkstoreref.getOrderQuantity()));
                } else {
                    holder.quantity.setText(String.format("%d pcs", gkstoreref.getOrderQuantity()));
                }
            }
            else {
                //Normal Picture
                Glide.with(mContext)
                        .load(gkstoreref.getProductImage())
                        .apply(new RequestOptions()
                                .fitCenter())
                        .into(holder.mLogoPic);

                int quantity = gkstoreref.getQuantity();
                qty = Double.parseDouble(String.valueOf(gkstoreref.getQuantity()));
                price = gkstoreref.getActualPrice();

                if(quantity <= 1) {
                    holder.quantity.setText(String.format("%d pc", gkstoreref.getQuantity()));
                } else {
                    holder.quantity.setText(String.format("%d pcs", gkstoreref.getQuantity()));
                }
            }

            holder.gkpersonalid.setText(CommonFunctions.replaceEscapeData(gkstoreref.getProductName()));

            holder.price.setText(CommonFunctions.currencyFormatter(String.valueOf(gkstoreref.getActualPrice())));

            holder.total.setText(String.format("₱%s", CommonFunctions.currencyFormatter(String.valueOf(price * qty))));

        } catch (NullPointerException e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }
    @Override
    public int getItemCount() {
       return gkStoreProducts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mLogoPic;
        TextView gkpersonalid;
        TextView quantity;
        TextView price;
        TextView total;


        public MyViewHolder(View itemView) {
            super(itemView);
            mLogoPic = itemView.findViewById(R.id.mLogoPic);
            gkpersonalid = itemView.findViewById(R.id.gkpersonalid);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            total = itemView.findViewById(R.id.total);
        }
    }
}
