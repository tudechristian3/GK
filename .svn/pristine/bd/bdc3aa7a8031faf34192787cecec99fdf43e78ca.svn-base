package com.goodkredit.myapplication.adapter.projectcoop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.projectcoop.GameHistory;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class ProjectCoopPlayToSaveHistoryStickyHeaderAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private Context mContext;
    private List<GameHistory> mGridData;
    private LayoutInflater inflater;

    public ProjectCoopPlayToSaveHistoryStickyHeaderAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mGridData = new ArrayList<>();
    }

    public void update(List<GameHistory> data) {
        int startPos = mGridData.size() + 1;
        mGridData.clear();
        mGridData.addAll(data);
//        notifyItemRangeInserted(startPos, data.size());
        notifyDataSetChanged();
    }

    public void clear() {
        int startPos = mGridData.size();
        mGridData.clear();
//        notifyItemRangeRemoved(0, startPos);
        notifyDataSetChanged();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_project_coop_play_to_save_history_item, parent, false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        GameHistory gameHistory = mGridData.get(position);

        holder.txvTransactionNo.setText("#".concat(gameHistory.getTransactionNo()));
        holder.txvAccountNumber.setText(gameHistory.getAccountID());
        holder.txvName.setText(gameHistory.getName());
        holder.txvStatus.setText(gameHistory.getStatus());
        holder.txvDate.setText(CommonFunctions.convertDate(gameHistory.getDateTimeCompleted()));

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        MyHeaderViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.header, parent, false);
            holder = new MyHeaderViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyHeaderViewHolder) convertView.getTag();
        }

        GameHistory gameHistory = mGridData.get(position);

        holder.header.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(gameHistory.getDateTimeCompleted())));

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return CommonFunctions.getLongFromDate(CommonFunctions.convertDate(mGridData.get(position).getDateTimeCompleted()));
    }

    private class MyViewHolder {
        private TextView txvTransactionNo;
        private TextView txvAccountNumber;
        private TextView txvName;
        private TextView txvStatus;
        private TextView txvDate;
        private LinearLayout linearRegistrationItem;

        public MyViewHolder(View itemView) {
            txvTransactionNo = (TextView) itemView.findViewById(R.id.txvTransactionNo);
            txvAccountNumber = (TextView) itemView.findViewById(R.id.txvAccountNumber);
            txvName = (TextView) itemView.findViewById(R.id.txvName);
            txvStatus = (TextView) itemView.findViewById(R.id.txvStatus);
            txvDate = (TextView) itemView.findViewById(R.id.txvDate);
            linearRegistrationItem = (LinearLayout) itemView.findViewById(R.id.linearRegistrationItem);
        }
    }

    private class MyHeaderViewHolder {
        private TextView header;

        public MyHeaderViewHolder(View itemView) {
            header = (TextView) itemView.findViewById(R.id.header);
        }
    }
}
