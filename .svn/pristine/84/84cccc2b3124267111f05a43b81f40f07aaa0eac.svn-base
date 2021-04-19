package com.goodkredit.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Transaction;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Ban_Lenovo on 12/28/2016.
 */

public class ConsumedAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    ArrayList<Transaction> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    CommonFunctions cf;

    public ConsumedAdapter(Context context, ArrayList<Transaction> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateList(ArrayList<Transaction> newlist) {
        this.arrayList = newlist;
        this.notifyDataSetChanged();
    }

    public void clearList() {
        this.arrayList.clear();
        this.notifyDataSetChanged();
    }

    static class HeaderViewHolder {
        TextView header;
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
            holder.header.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(date)));
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public long getHeaderId(int position) {
        return CommonFunctions.getLongFromDate(CommonFunctions.convertDate(arrayList.get(position).getDateCompleted()));
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
        TextView outlet;
        TextView amount;
        TextView merchantInitial;
        ImageView branchphoto;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        try {
            final String transactionNum = arrayList.get(position).getRefcode();
            final String branch = arrayList.get(position).getOutlet();
            final String totalAmount = arrayList.get(position).getAmount();
            final String time = arrayList.get(position).getDateCompleted();
            final String photo = arrayList.get(position).getMerchantLogo();


            if (view == null) {
                holder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.fragment_usage_item, parent, false);
                holder.date = (TextView) view.findViewById(R.id.date);
                holder.transno = (TextView) view.findViewById(R.id.transactionno);
                holder.outlet = (TextView) view.findViewById(R.id.branch);
                holder.amount = (TextView) view.findViewById(R.id.totalamount);
                holder.branchphoto = (ImageView) view.findViewById(R.id.branch_photo);
                holder.merchantInitial = (TextView) view.findViewById(R.id.merchantDefaultLogo);


                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.date.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(time)));
            holder.transno.setText("Txn# ".concat(transactionNum));
            holder.outlet.setText(CommonFunctions.replaceEscapeData(branch));
            holder.amount.setText(totalAmount);
            if (photo.contains("https")) {
                holder.branchphoto.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(photo)
                        .apply(RequestOptions
                                .fitCenterTransform())
                        .into(holder.branchphoto);
                holder.merchantInitial.setVisibility(View.GONE);
            } else {
                holder.branchphoto.setVisibility(View.GONE);
                holder.merchantInitial.setVisibility(View.VISIBLE);
                final String initials = arrayList.get(position).getMerchatInitials();
                holder.merchantInitial.setText(initials);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}
