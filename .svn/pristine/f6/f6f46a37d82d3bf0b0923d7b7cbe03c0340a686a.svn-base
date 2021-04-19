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
 * Created by Ban_Lenovo on 12/29/2016.
 */

public class TransferredAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    ArrayList<Transaction> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    CommonFunctions cf;

    public TransferredAdapter(Context context, ArrayList<Transaction> arrayList) {
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
        TextView tvdate;
        TextView tvsender;
        TextView tvreceiver;
        TextView tvamount;
    }

    static class HeaderViewHolder {
        TextView header;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        try {
            final String sender = arrayList.get(position).getSender();
            final String receiver = arrayList.get(position).getReceiver();
            final String amountTransferred = arrayList.get(position).getAmountTransferred();
            final String time = arrayList.get(position).getDateCompleted();

            if (view == null) {
                holder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.fragment_transfered_item, parent, false);
                holder.tvdate = (TextView) view.findViewById(R.id.date);
                holder.tvsender = (TextView) view.findViewById(R.id.senderval);
                holder.tvreceiver = (TextView) view.findViewById(R.id.receiverval);
                holder.tvamount = (TextView) view.findViewById(R.id.amount);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.tvdate.setText(cf.getTimeFromDateTime(cf.convertDate(time)));
            holder.tvsender.setText(sender);
            holder.tvreceiver.setText(receiver);
            holder.tvamount.setText(amountTransferred);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        HeaderViewHolder holder;

        final String date = arrayList.get(position).getDateCompleted();
        if (view == null) {
            holder = new HeaderViewHolder();
            view = layoutInflater.inflate(R.layout.header, parent, false);
            holder.header = (TextView) view.findViewById(R.id.header);

            view.setTag(holder);
        } else {
            holder = (HeaderViewHolder) view.getTag();
        }

        try {
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
