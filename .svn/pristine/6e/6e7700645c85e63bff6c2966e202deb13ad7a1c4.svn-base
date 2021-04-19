package com.goodkredit.myapplication.adapter.billspayment;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.BillsPaymentCategories;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.billspayment.BillsPaymentCategoriesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoodApps on 07/02/2018.
 */

public class BillsPaymentCategoriesRecyclerAdapter extends RecyclerView.Adapter<BillsPaymentCategoriesRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<BillsPaymentCategories> mGridData;
    private BillsPaymentCategoriesFragment fragment;
    private BillsPaymentCategoriesRecyclerHorizontalAdapter mAdapter;
    private DatabaseHandler mdb;
    private String passedsearchvalue = "";


    public BillsPaymentCategoriesRecyclerAdapter(Context context, BillsPaymentCategoriesFragment fm) {
        mContext = context;
        mGridData = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
    }

    public void setOrganizationList(List<BillsPaymentCategories> mGridData) {
        this.mGridData.clear();
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    public void setOrganizationList(List<BillsPaymentCategories> groupdata, List<BillsPaymentCategories> subdata, String searchvalue) {

        mAdapter.clear();
        mAdapter.setOrganizationHorizontalList(subdata);

        this.mGridData.clear();
        this.mGridData = groupdata;

        passedsearchvalue = searchvalue;
        notifyDataSetChanged();
    }


    public void clear() {
        int startPos = this.mGridData.size();
        this.mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @Override
    public BillsPaymentCategoriesRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bills_categories_items, parent, false);
        return new BillsPaymentCategoriesRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BillsPaymentCategoriesRecyclerAdapter.MyViewHolder holder, int position) {
        BillsPaymentCategories billerpaymentitem = mGridData.get(position);

        String groupcategory = billerpaymentitem.getCategorygroup();


        holder.txvOrganizationType.setText(CommonFunctions.replaceEscapeData
                (fragment.capitalizeWord(groupcategory)));

        holder.recycler_view_billers_item.setNestedScrollingEnabled(false);
        holder.recycler_view_billers_item.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new BillsPaymentCategoriesRecyclerHorizontalAdapter(mContext, fragment);
        holder.recycler_view_billers_item.setAdapter(mAdapter);
        mAdapter.setOrganizationHorizontalList(fragment.getAllBillersCategory(passedsearchvalue,groupcategory));

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView txvOrganizationType;
        private RecyclerView recycler_view_billers_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            txvOrganizationType = (TextView) itemView.findViewById(R.id.txvOrganizationType);
            recycler_view_billers_item = (RecyclerView) itemView.findViewById(R.id.recycler_view_billers_item);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
