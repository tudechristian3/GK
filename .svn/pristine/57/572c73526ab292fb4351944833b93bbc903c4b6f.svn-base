package com.goodkredit.myapplication.adapter.schoolmini;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPaymentLogs;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class SchoolMiniPaymentsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context mContext;
    private List<SchoolMiniPaymentLogs> schoolpaymentlogslist = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public SchoolMiniPaymentsAdapter(Context context, List<SchoolMiniPaymentLogs> arrayList) {
        this.mContext = context;
        this.schoolpaymentlogslist = arrayList;
        layoutInflater = LayoutInflater.from(mContext);
    }

    public void updateData(List<SchoolMiniPaymentLogs> newlist) {
        this.schoolpaymentlogslist = newlist;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.schoolpaymentlogslist.clear();
        this.notifyDataSetChanged();
    }

    static class HeaderViewHolder {
        TextView header;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        SchoolMiniPaymentsAdapter.HeaderViewHolder holder;

        final String date = schoolpaymentlogslist.get(position).getDateTimeIN();
        if (view == null) {
            holder = new SchoolMiniPaymentsAdapter.HeaderViewHolder();
            view = layoutInflater.inflate(R.layout.header, parent, false);
            holder.header = (TextView) view.findViewById(R.id.header);

            view.setTag(holder);
        } else {
            holder = (SchoolMiniPaymentsAdapter.HeaderViewHolder) view.getTag();
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
        return CommonFunctions.getLongFromDate(CommonFunctions.convertDate(schoolpaymentlogslist.get(position).getDateTimeIN()));
    }

    @Override
    public int getCount() {
        return schoolpaymentlogslist.size();
    }

    @Override
    public SchoolMiniPaymentLogs getItem(int position) {
        return schoolpaymentlogslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return schoolpaymentlogslist.get(position).hashCode();
    }

    private class ViewHolder {
        TextView txv_transno;
        TextView txv_totalamount;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        SchoolMiniPaymentsAdapter.ViewHolder holder;
        try {

            String paymentTxnID = schoolpaymentlogslist.get(position).getPaymentTxnID();
            double totalAmount = schoolpaymentlogslist.get(position).getTotalAmount();

            if (view == null) {
                holder = new SchoolMiniPaymentsAdapter.ViewHolder();
                view = layoutInflater.inflate(R.layout.fragment_schoolmini_payments_item, parent, false);

                holder.txv_transno = (TextView) view.findViewById(R.id.txv_transno);
                holder.txv_totalamount = (TextView) view.findViewById(R.id.txv_totalamount);

                view.setTag(holder);
            } else {
                holder = (SchoolMiniPaymentsAdapter.ViewHolder) view.getTag();
            }

            holder.txv_transno.setText("Payment Txn# ".concat(paymentTxnID));
            holder.txv_totalamount.setText("₱" + CommonFunctions.currencyFormatter(String.valueOf(totalAmount)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
