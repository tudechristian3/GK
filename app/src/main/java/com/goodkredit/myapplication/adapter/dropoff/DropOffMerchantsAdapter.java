package com.goodkredit.myapplication.adapter.dropoff;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.dropoff.DropOffMerchantsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.dropoff.DropOffMerchants;

import java.util.List;

public class DropOffMerchantsAdapter extends RecyclerView.Adapter<DropOffMerchantsAdapter.ViewHolder> {

    private List<DropOffMerchants> mGridData;
    private LayoutInflater layoutInflater;
    private Context mContext;
    private DropOffMerchantsActivity mActivity;

    public DropOffMerchantsAdapter(List<DropOffMerchants> mGridData, Context mContext, DropOffMerchantsActivity activity) {
        this.mGridData = mGridData;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        mActivity = activity;

    }

    public void updateList(List<DropOffMerchants> transactions) {
        this.mGridData = transactions;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DropOffMerchantsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_dropoff_merchants, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DropOffMerchantsAdapter.ViewHolder holder, int position) {
        DropOffMerchants dropOffMerchants = mGridData.get(position);

        String address = CommonFunctions.replaceEscapeData(dropOffMerchants.getStreetAddress())
                .concat(", ")
                .concat(CommonFunctions.replaceEscapeData(dropOffMerchants.getCity()))
                .concat(", ")
                .concat(CommonFunctions.replaceEscapeData(dropOffMerchants.getCountry()));

        holder.txvmerchantname.setText(CommonFunctions.replaceEscapeData(dropOffMerchants.getMerchantName()));
        holder.txvmerchantaddress.setText(address);

        Glide.with(mContext)
                .load(dropOffMerchants.getMerchantLogo())
                .into(holder.imgmerchantlogo);

        if(dropOffMerchants.getMerchantLogo().equals(".") || dropOffMerchants.getMerchantLogo() == null){
            Glide.with(mContext)
                    .load(R.drawable.dropoff_header)
                    .into(holder.imgmerchantlogo);
        } else{
            Glide.with(mContext)
                    .load(dropOffMerchants.getMerchantLogo())
                    .into(holder.imgmerchantlogo);
        }

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imgmerchantlogo;
        private TextView txvmerchantname;
        private TextView txvmerchantaddress;

        public ViewHolder(View itemView) {
            super(itemView);

            imgmerchantlogo = (ImageView) itemView.findViewById(R.id.img_merchantlogo);
            txvmerchantname = (TextView) itemView.findViewById(R.id.txv_merchantname);
            txvmerchantaddress = (TextView) itemView.findViewById(R.id.txv_merchantaddress);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position > -1) {

                DropOffMerchants merchants = mGridData.get(position);
                mActivity.setMerchant(merchants);
            }

        }
    }
}
