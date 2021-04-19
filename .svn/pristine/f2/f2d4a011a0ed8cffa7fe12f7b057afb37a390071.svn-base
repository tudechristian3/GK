package com.goodkredit.myapplication.adapter.billspayment;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.billspayment.ViewAllBillersByCategoryActivity;
import com.goodkredit.myapplication.bean.BillsPaymentCategories;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.billspayment.BillsPaymentCategoriesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoodApps on 08/02/2018.
 */

public class BillsPaymentCategoriesRecyclerHorizontalAdapter extends RecyclerView.Adapter<BillsPaymentCategoriesRecyclerHorizontalAdapter.MyViewHolder> {
    private Context mContext;
    private List<BillsPaymentCategories> mGridData;
    private BillsPaymentCategoriesFragment fragment;
    private DatabaseHandler mdb;
    private String servicecode;


    public BillsPaymentCategoriesRecyclerHorizontalAdapter(Context context, BillsPaymentCategoriesFragment fm) {
        mContext = context;
        mGridData = new ArrayList<>();
        mdb = new DatabaseHandler(mContext);
        fragment = fm;
    }

    public void setOrganizationHorizontalList(List<BillsPaymentCategories> mGridData) {
        this.mGridData.clear();
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    public void clear() {
        int startPos = this.mGridData.size();
        this.mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @Override
    public BillsPaymentCategoriesRecyclerHorizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bills_categories_horizontal_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BillsPaymentCategoriesRecyclerHorizontalAdapter.MyViewHolder holder, int position) {
        BillsPaymentCategories billspayitem = mGridData.get(position);
        String mLogo = billspayitem.getCategorylogo();
        String mLogoTitle = billspayitem.getCategory();
        if (mLogo != null && !mLogo.equals(".")) {
            Glide.with(mContext)
                    .load(mLogo)
                    .apply(new RequestOptions()
                        .fitCenter())
                    .into(holder.billslogo);
            holder.billslogotitle.setText(mLogoTitle);
        }

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView billslogo;
        private TextView billslogotitle;
        private TextView billsDefaultLogo;


        public MyViewHolder(View itemView) {
            super(itemView);
            billslogo = (ImageView) itemView.findViewById(R.id.icon);
            billslogo.setOnClickListener(this);
            billslogotitle = (TextView) itemView.findViewById(R.id.icontitle);
            billslogotitle.setOnClickListener(this);
            billsDefaultLogo = (TextView) itemView.findViewById(R.id.billsDefaultLogo);
            billsDefaultLogo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            BillsPaymentCategories billspayitem = mGridData.get(position);
            switch (v.getId()) {
                case R.id.icon:
                case R.id.icontitle: {
                    servicecode = fragment.getArguments().getString("ServiceCode");
                    Bundle b = new Bundle();
                    b.putSerializable("mcategory", billspayitem);
                    b.putSerializable("ServiceCode", servicecode);
                    ViewAllBillersByCategoryActivity.start(mContext,b);
                    break;
                }
            }
        }
    }
}
