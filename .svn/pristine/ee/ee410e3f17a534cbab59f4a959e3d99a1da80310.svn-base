package com.goodkredit.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Transaction;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Ban_Lenovo on 12/28/2016.
 */

public class BorrowingsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    ArrayList<Transaction> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    CommonFunctions cf;

    public BorrowingsAdapter(Context context, ArrayList<Transaction> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Transaction getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).hashCode();
    }

    static class ViewHolder {
        TextView date;
        TextView transno;
        TextView numVouchers;
        TextView totalAmount;
    }

    static class HeaderViewHolder {
        TextView header;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        try {
            final String transactionNum = arrayList.get(position).getTransactionNumber();
            final String numOfVouchers = arrayList.get(position).getNumOfVoucher();
            final String totalAmount = arrayList.get(position).getTotalVoucherAmount();
            final String time = arrayList.get(position).getDateCompleted();

            if (view == null) {
                holder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.fragment_borrowings_item, parent, false);
                holder.date = (TextView) view.findViewById(R.id.date);
                holder.transno = (TextView) view.findViewById(R.id.transactionno);
                holder.numVouchers = (TextView) view.findViewById(R.id.totalvoucher);
                holder.totalAmount = (TextView) view.findViewById(R.id.totalamount);

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

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        HeaderViewHolder holder;
        try {
        final String date = arrayList.get(position).getDateCompleted();
        if (view == null) {
            holder = new HeaderViewHolder();
            view = layoutInflater.inflate(R.layout.header, parent, false);
            holder.header = (TextView) view.findViewById(R.id.header);

            view.setTag(holder);
        } else {
            holder = (HeaderViewHolder) view.getTag();
        }


            holder.header.setText(cf.getDateFromDateTime(cf.convertDate(date)));
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public long getHeaderId(int position) {
        return cf.getLongFromDate(cf.convertDate(arrayList.get(position).getDateCompleted()));
    }
}
