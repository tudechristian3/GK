package com.goodkredit.myapplication.adapter.billspayment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;

/**
 * Created by ann on 6/6/2017.
 */

public class BillsPaymentBillerAdapter extends BaseAdapter {

        private Context mContext;
        private int layoutResourceId;
        private ArrayList<BillsPaymentBillerItem> mListData = new ArrayList<>();
        AQuery aq;
        private CommonFunctions cf;

        public BillsPaymentBillerAdapter(Context mContext, int layoutResourceId, ArrayList<BillsPaymentBillerItem> mListData) {

            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;
            this.mListData = mListData;
        }


        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public BillsPaymentBillerItem getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mListData.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            BillsPaymentBillerAdapter.ViewHolder holder;
            aq = new AQuery(mContext);
            try {

                BillsPaymentBillerItem item = mListData.get(position);

                if (row == null) {
                    LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                    row = inflater.inflate(layoutResourceId, parent, false);
                    holder = new BillsPaymentBillerAdapter.ViewHolder();
                    holder.billerlogo = (ImageView) row.findViewById(R.id.billerlogo);
                    holder.biller = (TextView) row.findViewById(R.id.biller);
                    holder.billercode = (TextView) row.findViewById(R.id.billercode);
                    holder.category = (TextView) row.findViewById(R.id.category);
                    holder.accountno = (TextView) row.findViewById(R.id.accountno);

                    row.setTag(holder);
                } else {
                    holder = (ViewHolder) row.getTag();
                }


                holder.biller.setText(cf.replaceEscapeData(item.getBillerName()));
                holder.category.setText(cf.replaceEscapeData(item.getCategory()));
                aq.id(holder.billerlogo).image(item.getBillerLogo(), false, true);
                holder.billercode.setText(cf.replaceEscapeData(item.getServiceProviderCode()));
                holder.accountno.setText(cf.replaceEscapeData(item.getAccountno()));

                if(item.getAccountno().equals("")){
                    holder.accountno.setVisibility(View.GONE);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            return row;
        }

        static class ViewHolder {

            ImageView billerlogo;
            TextView biller;
            TextView category;
            TextView billercode;
            TextView accountno;



        }

        public void update(ArrayList<BillsPaymentBillerItem> arrayList) {
            this.mListData = arrayList;
            notifyDataSetChanged();
        }
}
