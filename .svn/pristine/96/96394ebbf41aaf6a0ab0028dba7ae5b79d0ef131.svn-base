package com.goodkredit.myapplication.adapter.transactions;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Transaction;
import com.goodkredit.myapplication.common.CommonVariables;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by User-PC on 8/7/2017.
 */

public class ConsumedDetailsRecyclerAdapter extends RecyclerView.Adapter<ConsumedDetailsRecyclerAdapter.MyViewHolder> {

    private final Context mContext;
    private ArrayList<Transaction> mGridData;
    private AQuery aq;

    public ConsumedDetailsRecyclerAdapter(Context context, ArrayList<Transaction> mGridData) {
        this.mContext = context;
        this.mGridData = mGridData;
        aq = new AQuery(this.mContext);
    }

    private Context getContext() {
        return mContext;
    }

    public void setConsumedDetailsData(ArrayList<Transaction> mGridData) {
        this.mGridData.clear();
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.consumed_details_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Transaction item = mGridData.get(position);
        final String prodID = item.getProdID();
        final String voucherCode = item.getVoucherCode();
        final String voucherTrans = item.getTransactionNumber();
        final String voucherSerial = item.getSerial();
        final String voucherAmountConsumed = item.getAmount();
        final String vcode = voucherCode.substring(0, 2) + "-" + voucherCode.substring(2, 6) + "-" + voucherCode.substring(6, 11) + "-" + voucherCode.substring(11, 12);

        DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
        aq.id(holder.imgVproductID).image(buildURL(prodID), false, true);

        if (mGridData.get(position).getExtra3().equals(".")) {
            holder.mVoucherTag.setVisibility(View.GONE);
        } else {
            holder.mVoucherTag.setVisibility(View.VISIBLE);
        }

        holder.tvVoucherCode.setText(vcode);
        holder.tvVoucherTransactionNumber.setText(voucherTrans);
        holder.tvVoucherSerial.setText(voucherSerial);
        holder.tvVoucherAmountConsumed.setText(formatter.format(Double.parseDouble(voucherAmountConsumed)));

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

    //url builder for voucher image
    private String buildURL(String prodID) {
        String ext = prodID + "-voucher-design.jpg";
        String builtURL = CommonVariables.s3link + ext;
        return builtURL;
    }

}
