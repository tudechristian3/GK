package com.goodkredit.myapplication.adapter.dropoff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.dropoff.PaymentRequest;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class PaymentRequestAdapter extends BaseAdapter implements StickyListHeadersAdapter{

    private List<PaymentRequest> mGridData;
    private Context context;
    private LayoutInflater layoutInflater;

    public PaymentRequestAdapter(List<PaymentRequest> mGridData, Context context) {
        this.mGridData = mGridData;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void updateList (List<PaymentRequest> transactions) {
        this.mGridData = transactions;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    static class HeaderViewHolder {
        TextView header;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        HeaderViewHolder holder;

        final String date = mGridData.get(position).getDateTimeCompleted();
        if(view == null){
            holder = new HeaderViewHolder();
            view = layoutInflater.inflate(R.layout.header, parent, false);
            holder.header = (TextView) view.findViewById(R.id.header);

            view.setTag(holder);
        } else{
            holder = (HeaderViewHolder) view.getTag();
        }

        try{

            holder.header.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(date)));


        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public long getHeaderId(int position) {
        return CommonFunctions.getLongFromDate(CommonFunctions.convertDate(mGridData.get(position).getDateTimeCompleted()));
    }

    @Override
    public int getCount() {
        return mGridData.size();
    }

    @Override
    public Object getItem(int position) {
        return mGridData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mGridData.get(position).hashCode();
    }

    static class ViewHolder {
        private TextView txvtransactionno;
        private TextView txvtotalamount;
        private TextView txvdatetime;
        private TextView txvitemname;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        try{
            String time = CommonFunctions.getTimeFromDateTime(
                    CommonFunctions.convertDate(mGridData.get(position).getDateTimeCompleted()));
            String transactionno = mGridData.get(position).getOrderTxnID();
            double totalamount = mGridData.get(position).getTotalAmount();

            if(view == null){
                holder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.item_dropoff_paymentrequest, parent, false);

                holder.txvtransactionno = (TextView) view.findViewById(R.id.transactionno);
                holder.txvtotalamount = (TextView) view.findViewById(R.id.totalamount);
                holder.txvdatetime = (TextView) view.findViewById(R.id.date);
                holder.txvitemname = (TextView) view.findViewById(R.id.itemname);

                view.setTag(holder);
            } else{
                holder = (ViewHolder) view.getTag();
            }

            holder.txvtransactionno.setText("Txn#: ".concat(transactionno));
            holder.txvdatetime.setText(time);
            holder.txvitemname.setText(CommonFunctions.replaceEscapeData(mGridData.get(position).getMerchantName()));
            holder.txvtotalamount.setText("₱ ".concat(CommonFunctions.currencyFormatter(String.valueOf(totalamount))));

        } catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
}
