package com.goodkredit.myapplication.adapter.rewards;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.VoucherInfos;

import java.util.List;

/**
 * Created by Ban_Lenovo on 1/11/2018.
 */

public class CSBRedemptionHistoryVoucherDetailsAdapter extends RecyclerView.Adapter<CSBRedemptionHistoryVoucherDetailsAdapter.DetailsViewHolder> {

    private Context mContext;
    private List<VoucherInfos> mVouchers;

    public CSBRedemptionHistoryVoucherDetailsAdapter(Context context, List<VoucherInfos> vouchers) {
        mContext = context;
        mVouchers = vouchers;
    }

    public void update(List<VoucherInfos> vouchers) {
        mVouchers.clear();
        mVouchers = vouchers;
        notifyDataSetChanged();
    }

    @Override
    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_csb_redemption_history_voucher, parent, false);
        // Return a new holder instance
        DetailsViewHolder viewHolder = new DetailsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder holder, int position) {
        VoucherInfos voucher = mVouchers.get(position);

        holder.mVoucherTag.setVisibility(View.VISIBLE);
        Glide.with(mContext)
                .load(buildURL(voucher.getProductID()))
                .into(holder.imgVproductID);

        holder.tvVoucherDenom.setText(voucher.getDenom());
        holder.tvVoucherQuantity.setText(voucher.getQuantity());
    }

    @Override
    public int getItemCount() {
        return mVouchers.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgVproductID;
        private TextView tvVoucherDenom;
        private TextView tvVoucherQuantity;
        private ImageView mVoucherTag;


        public DetailsViewHolder(View itemView) {
            super(itemView);
            imgVproductID = (ImageView) itemView.findViewById(R.id.voucherLogo);
            tvVoucherDenom = (TextView) itemView.findViewById(R.id.voucherdenom);
            tvVoucherQuantity = (TextView) itemView.findViewById(R.id.quantity);
            mVoucherTag = (ImageView) itemView.findViewById(R.id.prepaid_tag);
        }
    }

    //url builder for voucher image
    private String buildURL(String prodID) {
        String ext = prodID + "-voucher-design.jpg";
        String builtURL = CommonVariables.s3link + ext;
        return builtURL;
    }
}
