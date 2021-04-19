package com.goodkredit.myapplication.adapter.prepaidrequest;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.PrepaidRequest;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.Logger;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 5/25/2017.
 */

public class VirtualVoucherProductDialogRecyclerAdapter extends RecyclerView.Adapter<VirtualVoucherProductDialogRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<PrepaidRequest> mGridData;

    public VirtualVoucherProductDialogRecyclerAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    private Context getContext() {
        return mContext;
    }

    public void setVirtualVoucher(List<PrepaidRequest> data) {
        mGridData.clear();

        for (PrepaidRequest req : data)
            if (req.getOrderQuantity() > 0)
                mGridData.add(req);

        notifyDataSetChanged();
    }

    public void clear() {
        int startPos = mGridData.size();
        mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @NotNull
    @Override
    public VirtualVoucherProductDialogRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_virtual_voucher_product_dialog_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VirtualVoucherProductDialogRecyclerAdapter.MyViewHolder holder, int position) {
        PrepaidRequest prepaidRequest = mGridData.get(position);

        Logger.debug("okhttp","VOUCHERS DATA DIALOG : "+ new Gson().toJson(prepaidRequest));

        holder.layoutVoucherDialog.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_E0E1E2));

        Glide.with(getContext())
                .load(buildURL(prepaidRequest.getProductID()))
                .into(holder.virtualVoucherImage);

        holder.txvVoucherQty.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", String.valueOf(prepaidRequest.getOrderQuantity())));

    }

    private String buildURL(String prodID) {
        String ext = prodID + "-voucher-design.jpg";
        return CommonVariables.s3link + ext;
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layoutVoucherDialog;
        //        private final TextView txvVoucher;
        private final TextView txvVoucherQty;

        private final ImageView virtualVoucherImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            layoutVoucherDialog = itemView.findViewById(R.id.layoutVoucherDialog);
            txvVoucherQty = itemView.findViewById(R.id.txvVoucherQty);
            virtualVoucherImage = itemView.findViewById(R.id.virtualVoucherImage);
        }

    }
}
