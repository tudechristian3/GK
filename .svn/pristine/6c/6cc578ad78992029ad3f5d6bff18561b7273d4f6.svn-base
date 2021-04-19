package com.goodkredit.myapplication.adapter.vouchers.payoutone;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.GroupedVouchersActivity;
import com.goodkredit.myapplication.activities.vouchers.payoutone.VoucherPayoutOneDetailsActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;

import hk.ids.gws.android.sclick.SClick;

public class VoucherPayoutOneAdapter extends RecyclerView.Adapter<VoucherPayoutOneAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Voucher> arrayList;

    public VoucherPayoutOneAdapter(Context context){
        this.context = context;
        arrayList = new ArrayList<>();

    }

    public void add(Voucher item) {
        arrayList.add(item);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Voucher> mGridData) {
        for (Voucher item : mGridData) {
            add(item);
        }
    }

    public void clear() {
        this.arrayList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherPayoutOneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_payout_one, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherPayoutOneAdapter.ViewHolder holder, int position) {

        Voucher voucher = arrayList.get(position);

        Logger.debug("okhttp","LINK : "+CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg");

        Glide.with(context)
                .load(CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg")
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.generic_placeholder_gk_background)  )
                .into(holder.imageview);

        if(voucher.getProductID().contains("RFID")) {
            holder.txv_voucher_content_label.setText("CARD#");
            holder.txv_voucher_content_label.setTextColor(ContextCompat.getColor(context, R.color.whitePrimary));
            holder.txv_voucher_content_label.setTypeface(V2Utils.setFontInput(context, V2Utils.ROBOTO_BOLD));

            holder.txv_voucher_content.setText(CommonFunctions.replaceEscapeData(CommonFunctions.addDashIntervals(voucher.getRFIDCardNumber(), 4)));

            holder.txv_voucher_content.setTextColor(ContextCompat.getColor(context, R.color.whitePrimary));
            holder.txv_voucher_content.setTypeface(V2Utils.setFontInput(context, V2Utils.ROBOTO_BOLD));
        } else {
            holder.txv_voucher_content_label.setText("PAY TO THE ORDER OF:");
            holder.txv_voucher_content.setText(CommonFunctions.replaceEscapeData(voucher.getPayToTheOrderOf()));
        }

        holder.txv_cheque_amount.setText(CommonFunctions.currencyFormatter(String.valueOf(voucher.getRemainingBalance())));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageview;
        private TextView txv_voucher_content_label;
        private TextView txv_voucher_content;
        private TextView txv_cheque_amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageview = (ImageView) itemView.findViewById(R.id.img_payoutone_sponsor);
            txv_voucher_content_label = (TextView) itemView.findViewById(R.id.txv_voucher_content_label);
            txv_voucher_content = (TextView) itemView.findViewById(R.id.txv_voucher_content);
            txv_cheque_amount = (TextView) itemView.findViewById(R.id.txv_cheque_amount);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            if(position > -1){
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                Voucher voucher = arrayList.get(position);
                if(voucher.getGroupVoucherCode().equals(".")){
                    Intent intent = new Intent(context, VoucherPayoutOneDetailsActivity.class);
                    intent.putExtra(Voucher.KEY_VOUCHER_OBJECT, voucher).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(intent);

                } else{
                    showGroupVouchersDialog(voucher);
                }

            }
        }
    }

    //original to open dialog, temporary plan b to open new activity
    private void showGroupVouchersDialog(Voucher voucher) {
        GroupedVouchersActivity.start(context, voucher);
    }
}
