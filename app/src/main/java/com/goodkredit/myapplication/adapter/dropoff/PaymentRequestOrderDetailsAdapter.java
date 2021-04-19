package com.goodkredit.myapplication.adapter.dropoff;

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
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.dropoff.PaymentRequestOrderDetails;

import java.util.List;

public class PaymentRequestOrderDetailsAdapter extends RecyclerView.Adapter<PaymentRequestOrderDetailsAdapter.ViewHolder> {

    private List<PaymentRequestOrderDetails> mGridData;
    private LayoutInflater layoutInflater;
    private Context context;

    public PaymentRequestOrderDetailsAdapter(List<PaymentRequestOrderDetails> mGridData, Context context) {
        this.mGridData = mGridData;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void update(List<PaymentRequestOrderDetails> data) {
        int startPos = mGridData.size() + 1;
        mGridData.clear();
        mGridData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public void clear() {
        int startPos = mGridData.size();
        mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @NonNull
    @Override
    public PaymentRequestOrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_dropoff_paymentrequestorderdetails, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentRequestOrderDetailsAdapter.ViewHolder holder, int position) {
        PaymentRequestOrderDetails orders = mGridData.get(position);


        holder.txvitemid.setText(orders.getItemid());
        holder.txvitemname.setText(CommonFunctions.replaceEscapeData(orders.getItemname()));
        holder.txvquantity.setText(orders.getQuantity());
        holder.txvitemamount.setText("₱ ".concat(CommonFunctions.currencyFormatter(String.valueOf(orders.getItemamount()))));

        Glide.with(context)
                .load(orders.getItemlogo())
                .into(holder.imgitemlogo);

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txvitemid;
        private TextView txvitemname;
        private TextView txvquantity;
        private TextView txvitemamount;
        private ImageView imgitemlogo;

        public ViewHolder(View itemView) {
            super(itemView);

            txvitemid = (TextView) itemView.findViewById(R.id.txv_pr_itemid);
            txvitemname = (TextView) itemView.findViewById(R.id.txv_pr_itemname);
            txvquantity = (TextView) itemView.findViewById(R.id.txv_pr_quantity);
            txvitemamount = (TextView) itemView.findViewById(R.id.txv_pr_itemamount);
            imgitemlogo = (ImageView) itemView.findViewById(R.id.img_pr_itemlogo);
        }
    }
}
