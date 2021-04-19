package com.goodkredit.myapplication.adapter.uno;

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
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.UNORedemptionVoucherDetails;

import java.util.ArrayList;
import java.util.List;

public class UNORedemptionHistoryVoucherDetailsAdapter extends RecyclerView.Adapter<UNORedemptionHistoryVoucherDetailsAdapter.VoucherDetailsViewHolder> {

    private Context mContext;
    private List<UNORedemptionVoucherDetails> mVoucherDetails = new ArrayList<>();

    public UNORedemptionHistoryVoucherDetailsAdapter(Context context) {
        mContext = context;
    }

    public void update(List<UNORedemptionVoucherDetails> data) {
        try {
            mVoucherDetails.clear();
            mVoucherDetails = data;
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public VoucherDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_csb_redemption_history_voucher, parent, false);
        // Return a new holder instance
        VoucherDetailsViewHolder viewHolder = new VoucherDetailsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherDetailsViewHolder holder, int position) {
        UNORedemptionVoucherDetails voucher = mVoucherDetails.get(position);

        holder.mVoucherTag.setVisibility(View.VISIBLE);
        Glide.with(mContext)
                .load(buildURL(voucher.getProductID()))
                .into(holder.imgVproductID);

        holder.tvVoucherDenom.setText(voucher.getDenom());
        holder.tvVoucherQuantity.setText(voucher.getQuantity());
    }

    @Override
    public int getItemCount() {
        return mVoucherDetails.size();
    }

    public class VoucherDetailsViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgVproductID;
        private TextView tvVoucherDenom;
        private TextView tvVoucherQuantity;
        private ImageView mVoucherTag;

        public VoucherDetailsViewHolder(View itemView) {
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
