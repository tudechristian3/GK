package com.goodkredit.myapplication.adapter.gkservices.medpadala;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.MedPadalaVoucherUsed;
import com.goodkredit.myapplication.utilities.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban on 19/03/2018.
 */

public class MedPadalaTransactionVouchersUsedAdapter extends RecyclerView.Adapter<MedPadalaTransactionVouchersUsedAdapter.VoucherUsedViewHolder> {

    private Context mContext;
    private List<MedPadalaVoucherUsed> mList = new ArrayList<>();

    public MedPadalaTransactionVouchersUsedAdapter(Context context, List<MedPadalaVoucherUsed> data) {
        mContext = context;
        mList = data;
    }

    public void update(List<MedPadalaVoucherUsed> data) {
        Logger.debug("update", "update: " + String.valueOf(data));
        mList.clear();
        mList = data;
        notifyDataSetChanged();
    }


    @Override
    public VoucherUsedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_medpadala_history_voucher_used, viewGroup, false);
        // Return a new holder instance
        VoucherUsedViewHolder viewHolder = new VoucherUsedViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VoucherUsedViewHolder voucherUsedViewHolder, int i) {
        MedPadalaVoucherUsed voucher = mList.get(i);

        Glide.with(mContext)
                .load(CommonFunctions.buildVoucherURL(voucher.getProductID()))
                .into(voucherUsedViewHolder.imgVoucher);

        if (voucher.getVoucherType().equals("PREPAID"))
            voucherUsedViewHolder.imgTag.setVisibility(View.VISIBLE);

        String vcode = "";
        vcode = voucher.getVoucherCode().substring(0, 2) + "-" + voucher.getVoucherCode().substring(2, 6) + "-" + voucher.getVoucherCode().substring(6, 11) + "-" + voucher.getVoucherCode().substring(11, 12);

        voucherUsedViewHolder.tvVoucherCode.setText(vcode);
        voucherUsedViewHolder.tvAmountUsed.setText(CommonFunctions.currencyFormatter(String.valueOf(voucher.getAmountConsumed())));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class VoucherUsedViewHolder extends RecyclerView.ViewHolder {

        ImageView imgVoucher;
        ImageView imgTag;

        TextView tvVoucherCode;
        TextView tvAmountUsed;

        public VoucherUsedViewHolder(View itemView) {
            super(itemView);

            imgVoucher = (ImageView) itemView.findViewById(R.id.voucherLogo);
            imgTag = (ImageView) itemView.findViewById(R.id.prepaid_tag);

            tvVoucherCode = (TextView) itemView.findViewById(R.id.vouchercode);
            tvAmountUsed = (TextView) itemView.findViewById(R.id.amountused);
        }
    }
}
