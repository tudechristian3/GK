package com.goodkredit.myapplication.adapter.billspayment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.billspayment.BillsPaymentBillerDetailsActivity;
import com.goodkredit.myapplication.activities.billspayment.ViewAllBillersByCategoryActivity;
import com.goodkredit.myapplication.bean.BillsPaymentCategories;
import com.goodkredit.myapplication.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoodApps on 14/02/2018.
 */

public class BillsPaymentCategoriesShowAdapter extends RecyclerView.Adapter<BillsPaymentCategoriesShowAdapter.MyViewHolder> {
    private Context mContext;
    private List<BillsPaymentCategories> billspaymentlist;
    private DatabaseHandler mdb;
    private String servicecode;

    public BillsPaymentCategoriesShowAdapter(Context context) {
        mContext = context;
        billspaymentlist = new ArrayList<>();
        mdb = new DatabaseHandler(mContext);
    }

    public void updateDataBillsList(List<BillsPaymentCategories> billspaymentlist) {
        this.billspaymentlist.clear();
        this.billspaymentlist = billspaymentlist;
        notifyDataSetChanged();
    }

    public void clear() {
        int startPos = this.billspaymentlist.size();
        this.billspaymentlist.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @Override
    public BillsPaymentCategoriesShowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_all_billers_category_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BillsPaymentCategoriesShowAdapter.MyViewHolder holder, int position) {
        BillsPaymentCategories billspayitem = billspaymentlist.get(position);
        String mLogo = billspayitem.getBillerLogo();
        if (mLogo != null && !mLogo.equals(".")) {
            holder.billerlogo.setVisibility(View.VISIBLE);
            holder.billsDefaultLogo.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(mLogo)
                    .apply(new RequestOptions()
                            .centerInside())
                    .into(holder.billerlogo);
        }
        else {
            holder.billerlogo.setVisibility(View.GONE);
            holder.billsDefaultLogo.setVisibility(View.VISIBLE);
            String initials = getNetworkInitials(billspayitem.getBillerName());
            holder.billsDefaultLogo.setText(initials);
        }

        holder.billername.setText(billspayitem.getBillerName());
        holder.billercategory.setText(billspayitem.getCategory());




    }

    @Override
    public int getItemCount() {
        return billspaymentlist.size();
    }

    private String getNetworkInitials(String name) {
        String[] temp = name.split("\\s");
        String result = "";
        for (int x = 0; x < temp.length; x++) {
            if (temp[x] != null && !temp[x].equals("") && !temp[x].equals(" ")) {
                result += (temp[x].charAt(0));
            }
        }
        if (result.length() >= 2) {
            result = result.substring(0, 2);
        }
        return result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView billerlogo;
        private TextView billsDefaultLogo;
        private TextView billername;
        private TextView billercategory;
        private LinearLayout containerbilleritem;


        public MyViewHolder(View itemView) {
            super(itemView);
            billerlogo = (ImageView) itemView.findViewById(R.id.billerlogo);
            billsDefaultLogo = (TextView) itemView.findViewById(R.id.billsDefaultLogo);
            billername = (TextView) itemView.findViewById(R.id.billername);
            billercategory = (TextView) itemView.findViewById(R.id.billercategory);
            containerbilleritem = (LinearLayout) itemView.findViewById(R.id.containerbilleritem);
            containerbilleritem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            BillsPaymentCategories billspayitem = billspaymentlist.get(position);

            String billername = billspayitem.getBillerName();
            String billercode = billspayitem.getBillercode();
            String spbillercode = billspayitem.getServiceProviderCode();

            Bundle args = ((ViewAllBillersByCategoryActivity) mContext).getIntent().getBundleExtra("args");
            servicecode = args.getString("ServiceCode");

            Intent intent = new Intent(mContext, BillsPaymentBillerDetailsActivity.class);
            intent.putExtra("BILLCODE", billercode);
            intent.putExtra("SPBILLCODE", spbillercode);
            intent.putExtra("SPBILLERACCOUNTNO", "");
            intent.putExtra("BILLNAME", billername);
            intent.putExtra("BILLNAME", billername);
            intent.putExtra("ServiceCode", servicecode);
            intent.putExtra("FROM","BILLERS");
            mContext.startActivity(intent);
        }
    }
}
