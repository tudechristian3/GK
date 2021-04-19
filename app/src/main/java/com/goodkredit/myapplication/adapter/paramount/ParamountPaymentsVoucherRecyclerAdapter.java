package com.goodkredit.myapplication.adapter.paramount;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.ParamountVouchers;
import com.goodkredit.myapplication.common.CommonVariables;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 3/23/2018.
 */

public class ParamountPaymentsVoucherRecyclerAdapter extends RecyclerView.Adapter<ParamountPaymentsVoucherRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<ParamountVouchers> mGridData;

    public ParamountPaymentsVoucherRecyclerAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    public void setVouchersData(final List<ParamountVouchers> mGridData) {
        int startPos = this.mGridData.size() + 1;
        this.mGridData.clear();
        this.mGridData.addAll(mGridData);
        notifyItemRangeInserted(startPos, mGridData.size());
    }

    public void clear() {
        int startPos = this.mGridData.size();
        this.mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @Override
    public ParamountPaymentsVoucherRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consumed_details_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ParamountPaymentsVoucherRecyclerAdapter.MyViewHolder holder, int position) {
            ParamountVouchers paramountVouchers = mGridData.get(position);

            final String prodID = paramountVouchers.getProductID();
            final String voucherCode = paramountVouchers.getVoucherCode();
            final String voucherTrans = paramountVouchers.getTransactionNo();
            final String voucherSerial = paramountVouchers.getVoucherSeriesNo();
            final double voucherAmountConsumed = paramountVouchers.getAmountConsumed();
            final String vcode = voucherCode.substring(0, 2) + "-" + voucherCode.substring(2, 6) + "-" + voucherCode.substring(6, 11) + "-" + voucherCode.substring(11, 12);

            Glide.with(mContext)
                    .asBitmap()
                    .load(buildURL(prodID))
                    .apply(new RequestOptions())
                    .into(new BaseTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            holder.imgVproductID.setImageBitmap(resource);
                        }

                        @Override
                        public void getSize(SizeReadyCallback cb) {
                            cb.onSizeReady(200, 200);
                        }

                        @Override
                        public void removeCallback(SizeReadyCallback cb) {

                        }
                    });

            if (paramountVouchers.getExtra3().equals(".")) {
                holder.mVoucherTag.setVisibility(View.GONE);
            } else {
                holder.mVoucherTag.setVisibility(View.VISIBLE);
            }

            holder.tvVoucherCode.setText(vcode);
            holder.tvVoucherTransactionNumber.setText(voucherTrans);
            holder.tvVoucherSerial.setText(voucherSerial);
            DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
            holder.tvVoucherAmountConsumed.setText(formatter.format(voucherAmountConsumed));

    }

    //url builder for voucher image
    private String buildURL(String prodID) {
        String ext = prodID + "-voucher-design.jpg";
        String builtURL = CommonVariables.s3link + ext;
        return builtURL;
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgVproductID;
        private TextView tvVoucherCode;
        private TextView tvVoucherTransactionNumber;
        private TextView tvVoucherSerial;
        private TextView tvVoucherAmountConsumed;
        private ImageView mVoucherTag;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgVproductID = (ImageView) itemView.findViewById(R.id.detailsVoucherImage);
            tvVoucherCode = (TextView) itemView.findViewById(R.id.detailsCode);
            tvVoucherTransactionNumber = (TextView) itemView.findViewById(R.id.detailsTransNo);
            tvVoucherSerial = (TextView) itemView.findViewById(R.id.detailsSerial);
            tvVoucherAmountConsumed = (TextView) itemView.findViewById(R.id.detailsConsumed);
            mVoucherTag = (ImageView) itemView.findViewById(R.id.prepaid_tag);

        }
    }
}
