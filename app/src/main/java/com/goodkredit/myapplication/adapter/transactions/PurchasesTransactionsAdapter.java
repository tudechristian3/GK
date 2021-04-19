package com.goodkredit.myapplication.adapter.transactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.PrepaidVoucherTransaction;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Ban_Lenovo on 5/25/2017.
 */

public class PurchasesTransactionsAdapter extends BaseAdapter implements StickyListHeadersAdapter {


    private ArrayList<PrepaidVoucherTransaction> arrayList;
    private Context context;
    private LayoutInflater layoutInflater;
    private CommonFunctions cf;

    public PurchasesTransactionsAdapter(Context context, ArrayList<PrepaidVoucherTransaction> prepaidVoucherTransactionArrayList) {
        this.context = context;
        arrayList = prepaidVoucherTransactionArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        TextView date;
        TextView transno;
        TextView numVouchers;
        TextView totalAmount;
        TextView serialno;
    }

    static class HeaderViewHolder {
        TextView header;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        HeaderViewHolder holder;
        try {
            final String date = arrayList.get(position).getDateTimeCompleted();

            if (view == null) {
                holder = new HeaderViewHolder();
                view = layoutInflater.inflate(R.layout.header, parent, false);
                holder.header = (TextView) view.findViewById(R.id.header);

                view.setTag(holder);
            } else {
                holder = (HeaderViewHolder) view.getTag();
            }

            holder.header.setText(cf.getDateFromDateTime(cf.convertDate(date)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public long getHeaderId(int position) {
        return cf.getLongFromDate(cf.convertDate(arrayList.get(position).getDateTimeCompleted()));
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public PrepaidVoucherTransaction getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        try {

            final String transactionNum = arrayList.get(position).getTransactionNo();
            final String numOfVouchers = String.valueOf(arrayList.get(position).getTotalNoOfVouchers());
            final String totalAmount = String.valueOf(arrayList.get(position).getTotalVoucherAmount());
            final String time = arrayList.get(position).getDateTimeCompleted();
            //  final String serial = arrayList.get(position).getVoucher;

            if (view == null) {
                holder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.fragment_borrowings_item, parent, false);
                holder.date = (TextView) view.findViewById(R.id.date);
                holder.transno = (TextView) view.findViewById(R.id.transactionno);
                holder.numVouchers = (TextView) view.findViewById(R.id.totalvoucher);
                holder.totalAmount = (TextView) view.findViewById(R.id.totalamount);
                holder.serialno = (TextView) view.findViewById(R.id.serialNum);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.date.setText(cf.getTimeFromDateTime(cf.convertDate(time)));
            holder.transno.setText(transactionNum);
            holder.numVouchers.setText(numOfVouchers);
            holder.totalAmount.setText(totalAmount);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void update(ArrayList<PrepaidVoucherTransaction> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public void clearData() {
        this.arrayList.clear();
    }
}
