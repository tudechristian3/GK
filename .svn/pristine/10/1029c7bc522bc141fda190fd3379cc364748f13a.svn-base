package com.goodkredit.myapplication.adapter.vouchers;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.GroupedVouchersActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.activities.vouchers.AvailableVoucherDetails;
import com.google.gson.Gson;

import java.util.ArrayList;

public class VouchersRecyclerAdapter extends RecyclerView.Adapter<VouchersRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Voucher> arrayList;

    public VouchersRecyclerAdapter(Context context) {
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

//    public void updateData(ArrayList<Voucher> data) {
//        int startPos = arrayList.size() + 1;
//        arrayList.clear();
//        arrayList.addAll(data);
//        notifyItemRangeInserted(startPos, data.size());
//    }
//
//    public void clear() {
//        int startPos = arrayList.size();
//        arrayList.clear();
//        notifyItemRangeRemoved(0, startPos);
//    }

    @NonNull
    @Override
    public VouchersRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_available_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VouchersRecyclerAdapter.ViewHolder holder, int position) {
        String strBal;

        Voucher voucher = arrayList.get(position);

        if (voucher.getGroupVoucherCode().equals(".")) {

            Glide.with(context)
                    .load(CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg")
                    .into(holder.voucherImage);

            if (voucher.getExtra3().equals(".")) {
                holder.voucherTag.setVisibility(View.GONE);
            } else {
                holder.voucherTag.setVisibility(View.VISIBLE);
            }


            holder.voucherBalance.setVisibility(View.VISIBLE);
            holder.groupBalance.setVisibility(View.GONE);
            holder.groupName.setVisibility(View.GONE);

            if (voucher.getRemainingBalance() % 1 == 0)
                strBal = String.valueOf((int) voucher.getRemainingBalance());
            else
                strBal = String.valueOf(voucher.getRemainingBalance());

        } else {
            Glide.with(context)
                    .load(CommonVariables.s3link + "group-vouchers.png")
                    .into(holder.voucherImage);

            double doubleBalance = Double.parseDouble(voucher.getGroupBalance());

            if (doubleBalance % 1 == 0)
                strBal = String.valueOf((int) doubleBalance);
            else
                strBal = String.valueOf(doubleBalance);

            holder.groupBalance.setVisibility(View.VISIBLE);
            holder.groupName.setVisibility(View.VISIBLE);
            holder.voucherBalance.setVisibility(View.GONE);
            holder.voucherTag.setVisibility(View.GONE);
            holder.groupBalance.setText(CommonFunctions.currencyFormatter(voucher.getGroupBalance()));
            if (!voucher.getGroupName().equals("."))
                holder.groupName.setText(voucher.getGroupName());
        }

        if (strBal.length() < 5) {
            strBal = "BAL: " + strBal;
        }
        holder.voucherBalance.setText(strBal);
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
        private ImageView voucherTag;
        private ImageView voucherImage;
        private TextView voucherBalance;
        private AppCompatTextView groupBalance;
        private TextView groupName;
        private RelativeLayout relativeLayoutImage;

        public ViewHolder(View itemView) {
            super(itemView);
            voucherImage = itemView.findViewById(R.id.grid_item_image);
            voucherBalance = itemView.findViewById(R.id.grid_item_title);
            voucherTag = itemView.findViewById(R.id.prepaid_tag);
            groupBalance = itemView.findViewById(R.id.group_voucher_balance);
            groupName = itemView.findViewById(R.id.group_voucher_title);
            relativeLayoutImage = itemView.findViewById(R.id.relativeLayoutImage);
            relativeLayoutImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            if (position > -1) {
                switch (v.getId()) {
                    case R.id.relativeLayoutImage: {

                        Voucher voucher = arrayList.get(position);
                        if (voucher.getGroupVoucherCode().equals(".")) {
                            Intent intent = new Intent(context, AvailableVoucherDetails.class);
                            intent.putExtra("VOUCHER_OBJECT", voucher);
                            context.startActivity(intent);
                        } else {
                            showGroupVouchersDialog(voucher);
                        }

                        break;
                    }
                }
            }

        }
    }

    //original to open dialog, temporary plan b to open new activity
    private void showGroupVouchersDialog(Voucher voucher) {
        GroupedVouchersActivity.start(context, voucher);
    }
}
