package com.goodkredit.myapplication.adapter.transactions;

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
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonVariables;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by User-PC on 7/29/2017.
 */

public class BorrowingsVoucherDetailsAdapter extends RecyclerView.Adapter<BorrowingsVoucherDetailsAdapter.MyViewHolder> {

    private final Context mContext;
    private List<Voucher> mGridData;
    private boolean isPrepaid = false;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgVproductID;
        private TextView tvVoucherCode;
        private TextView tvVoucherBalance;
        private ImageView mVoucherTag;
        private TextView tvVoucherSerialNo;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgVproductID = (ImageView) itemView.findViewById(R.id.voucherLogo);
            tvVoucherCode = (TextView) itemView.findViewById(R.id.vouchercode);
            tvVoucherBalance = (TextView) itemView.findViewById(R.id.totalamount);
            mVoucherTag = (ImageView) itemView.findViewById(R.id.prepaid_tag);
            tvVoucherSerialNo = (TextView) itemView.findViewById(R.id.serialNum);
        }
    }

    public BorrowingsVoucherDetailsAdapter(Context context, List<Voucher> mGridData) {
        this.mContext = context;
        this.mGridData = mGridData;
    }

    public BorrowingsVoucherDetailsAdapter(Context context, List<Voucher> mGridData, boolean isPrepaid) {
        this.mContext = context;
        this.mGridData = mGridData;
        this.isPrepaid = isPrepaid;
    }

    private Context getContext() {
        return mContext;
    }

    public void setBorrowingsVoucherDetailsData(List<Voucher> mGridData){
        this.mGridData.clear();
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public BorrowingsVoucherDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_transactions_borrowings_details_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BorrowingsVoucherDetailsAdapter.MyViewHolder holder, int position) {
        Voucher item = mGridData.get(position);

        final String prodID = item.getProductID();
        final String voucherCode = item.getVoucherCode();
        final double voucherBalance = item.getVoucherDenom();
        final String voucherSerialNum = item.getVoucherSerialNo();
        final String vcode = voucherCode.substring(0, 2) + "-" + voucherCode.substring(2, 6) + "-" + voucherCode.substring(6, 11) + "-" + voucherCode.substring(11, 12);

        Glide.with(mContext)
                .asBitmap()
                .load(buildURL(prodID))
                .apply(new RequestOptions()
                        .fitCenter())
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

        holder.tvVoucherCode.setText(vcode);
        DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
        holder.tvVoucherBalance.setText(formatter.format((voucherBalance)));
        if (isPrepaid) {
            holder.mVoucherTag.setVisibility(View.VISIBLE);
        } else {
            holder.mVoucherTag.setVisibility(View.GONE);
        }

        holder.tvVoucherSerialNo.setText(voucherSerialNum);
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

}
