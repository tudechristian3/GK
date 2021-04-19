package com.goodkredit.myapplication.adapter.freesms;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.freesms.FreeSMSHistory;

import java.util.List;

public class FreeSMSHistoryAdapter extends RecyclerView.Adapter<FreeSMSHistoryAdapter.ViewHolder>{

    private List<FreeSMSHistory> mGridData;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public FreeSMSHistoryAdapter(List<FreeSMSHistory> mGridData, Context mContext) {
        this.mGridData = mGridData;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    public void updateList(List<FreeSMSHistory> mGridData) {
        this.mGridData = mGridData;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FreeSMSHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_free_sms_history, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FreeSMSHistoryAdapter.ViewHolder holder, int position) {
        FreeSMSHistory freesmshistory = mGridData.get(position);

        String mobileno = freesmshistory.getRecipient().substring(1);
        String mobileno1 = mobileno.substring(1);
        holder.txv_mobileno.setText("0" + mobileno1);
        holder.txv_mobileno.setTextColor(Color.parseColor("#3B3B3B"));

        holder.txv_date.setText(CommonFunctions.getDateTimeFromDateTime(
                CommonFunctions.convertDate(freesmshistory.getDateTimeIN())));
        holder.txv_date.setTextColor(Color.parseColor("#9B9B9B"));

        holder.txv_message.setText(freesmshistory.getMessage());
        holder.txv_message.setTextColor(Color.parseColor("#4A4A4A"));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView txv_mobileno;
//        private TextView txv_date;
//        private TextView txv_message;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//        }
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txv_mobileno;
        private TextView txv_date;
        private TextView txv_message;

        public ViewHolder(View itemView) {
            super(itemView);

            txv_mobileno = (TextView) itemView.findViewById(R.id.txv_mobilenumber);
            txv_date = (TextView) itemView.findViewById(R.id.txv_date);
            txv_message = (TextView) itemView.findViewById(R.id.txv_message);

        }
    }
}
