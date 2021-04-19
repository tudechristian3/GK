package com.goodkredit.myapplication.adapter.gknegosyo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.GKNegosyoPackageVoucher;

import java.util.List;

public class GKNegosyoPackageVoucherInclusionsAdapter extends RecyclerView.Adapter<GKNegosyoPackageVoucherInclusionsAdapter.VoucherViewHolder> {

    private Context mContext;
    private List<GKNegosyoPackageVoucher> mVoucherInclusions;
    private LayoutInflater inflater;

    public GKNegosyoPackageVoucherInclusionsAdapter(Context context, List<GKNegosyoPackageVoucher> voucherInclusions) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mVoucherInclusions = voucherInclusions;
    }

    @NonNull
    @Override
    public GKNegosyoPackageVoucherInclusionsAdapter.VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gk_negosyo_voucherinclusions, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GKNegosyoPackageVoucherInclusionsAdapter.VoucherViewHolder holder, int position) {
        GKNegosyoPackageVoucher voucher = mVoucherInclusions.get(position);

        holder.tv_voucher_inclusion_denom.setText("₱" + CommonFunctions.commaFormatter(String.valueOf(voucher.getDenom())));
        holder.tv_voucher_inclusion_quantity.setText(String.valueOf(voucher.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mVoucherInclusions.size();
    }

    public class VoucherViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_voucher_inclusion_denom;
        private TextView tv_voucher_inclusion_quantity;

        public VoucherViewHolder(View itemView) {
            super(itemView);
            tv_voucher_inclusion_denom = itemView.findViewById(R.id.tv_voucher_inclusion_denom);
            tv_voucher_inclusion_quantity = itemView.findViewById(R.id.tv_voucher_inclusion_quantity);
        }
    }
}
