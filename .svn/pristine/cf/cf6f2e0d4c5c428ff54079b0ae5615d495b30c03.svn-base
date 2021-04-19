package com.goodkredit.myapplication.adapter.prepaidrequest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.prepaidrequest.ViewPaymentChannelsActivity;
import com.goodkredit.myapplication.bean.PaymentChannels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 5/25/2017.
 */

public class PaymentChannelsRecyclerAdapter extends RecyclerView.Adapter<PaymentChannelsRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<PaymentChannels> mGridData;

    public PaymentChannelsRecyclerAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    private Context getContext() {
        return mContext;
    }

    public void setPaymentsChannelsData(List<PaymentChannels> data) {
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

    private void setUpItemLayout(PaymentChannels paymentChannels, PaymentChannelsRecyclerAdapter.MyViewHolder holder) {
        String logo = paymentChannels.getLogo();
        Glide.with(getContext())
                .load(logo)
                .centerInside()
                .into(holder.item_photo);
    }

    public String getNetworkInitials(String name) {
        String[] temp = name.split("\\s");
        String result = "";
        for (int x = 0; x < temp.length; x++) {
            if (temp[x] != null && !temp[x].equals("") && !temp[x].equals(" ")) {
                result += (temp[x].charAt(0));
            }
        }
        if (result.length() >= 2) {
            result = result.substring(0, 2);
        }
        return result;
    }

    @Override
    public PaymentChannelsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_payment_channels_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaymentChannelsRecyclerAdapter.MyViewHolder holder, int position) {
        PaymentChannels paymentChannels = mGridData.get(position);
        setUpItemLayout(paymentChannels, holder);
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView item_photo;
        private final CardView payment_cardlayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_photo = itemView.findViewById(R.id.itemPhoto);
            payment_cardlayout = itemView.findViewById(R.id.payment_cardlayout);
            payment_cardlayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != -1) {
                PaymentChannels paymentChannels = mGridData.get(position);
                Bundle args = new Bundle();
                args.putParcelable("channel", paymentChannels);
                ViewPaymentChannelsActivity.start(getContext(), args);
            }
        }
    }
}
