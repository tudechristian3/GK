package com.goodkredit.myapplication.adapter.promo;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;

/**
 * Created by Ban_Lenovo on 2/9/2018.
 */

public class RedeemedPromoHistoryVoucherAdapter extends RecyclerView.Adapter<RedeemedPromoHistoryVoucherAdapter.RedeemedPromoVouchersViewHolder> {

    private Context mContext;
    private String mJSONVoucher = "";

    public RedeemedPromoHistoryVoucherAdapter(Context context, String json) {
        mContext = context;
        mJSONVoucher = json;
    }

    public void update(String json) {
        mJSONVoucher = "";
        mJSONVoucher = json;
        notifyDataSetChanged();
    }


    @Override
    public RedeemedPromoVouchersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_csb_redemption_history_voucher, parent, false);
        // Return a new holder instance
        RedeemedPromoVouchersViewHolder viewHolder = new RedeemedPromoVouchersViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RedeemedPromoVouchersViewHolder holder, int position) {
        try {
            String jsonVoucher = mJSONVoucher;

            ImageView imgVproductID = holder.imgVproductID;
            TextView tvVoucherDenom = holder.tvVoucherDenom;
            TextView tvVoucherQuantity = holder.tvVoucherQuantity;
            ImageView mVoucherTag = holder.mVoucherTag;

            mVoucherTag.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(CommonFunctions.buildVoucherURL(CommonFunctions.parseJSON(jsonVoucher, "VoucherProductID")))
                    .into(imgVproductID);

            tvVoucherDenom.setText(CommonFunctions.parseJSON(jsonVoucher, "VoucherDenom"));
            tvVoucherQuantity.setText(CommonFunctions.parseJSON(jsonVoucher, "VoucherQuantity"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class RedeemedPromoVouchersViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgVproductID;
        private TextView tvVoucherDenom;
        private TextView tvVoucherQuantity;
        private ImageView mVoucherTag;

        public RedeemedPromoVouchersViewHolder(View itemView) {
            super(itemView);
            imgVproductID = (ImageView) itemView.findViewById(R.id.voucherLogo);
            tvVoucherDenom = (TextView) itemView.findViewById(R.id.voucherdenom);
            tvVoucherQuantity = (TextView) itemView.findViewById(R.id.quantity);
            mVoucherTag = (ImageView) itemView.findViewById(R.id.prepaid_tag);
        }
    }
}
