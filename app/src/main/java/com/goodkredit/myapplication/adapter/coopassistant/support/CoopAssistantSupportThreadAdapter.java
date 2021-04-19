package com.goodkredit.myapplication.adapter.coopassistant.support;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.SupportThread;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantSupportThreadAdapter extends RecyclerView.Adapter<CoopAssistantSupportThreadAdapter.MyViewHolder> {
    private Context mContext;
    private List<SupportThread> mGridData;
    private boolean isOpen;
    private boolean isClose;
    private boolean isDuplicate;

    public CoopAssistantSupportThreadAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
        isOpen = false;
        isClose = false;
        isDuplicate = false;
    }

    public void reset() {
        isOpen = false;
        isClose = false;
        isDuplicate = false;
    }

    public void update(List<SupportThread> data) {
        int startPos = mGridData.size() + 1;
        mGridData.clear();
        mGridData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public void clear() {
        int startPos = mGridData.size();
        mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_global_support_thread, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (position > -1) {
            SupportThread supportThread = mGridData.get(position);

            if (supportThread.getStatus().equals("CLOSED")) {

                holder.txvTicketNumber.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextGrey));

                if (isClose) {
                    holder.txvCloseCase.setVisibility(View.GONE);

                } else {
                    isClose = true;

                    holder.txvCloseCase.setVisibility(View.VISIBLE);
                }

                holder.txvOpenCase.setVisibility(View.GONE);
                holder.txvDuplicateCase.setVisibility(View.GONE);


            } else if (supportThread.getStatus().equals("DUPLICATE")) {

                holder.txvTicketNumber.setTextColor(ContextCompat.getColor(mContext, R.color.colortoolbar));

                if (isDuplicate) {
                    holder.txvDuplicateCase.setVisibility(View.GONE);

                } else {
                    isDuplicate = true;

                    holder.txvDuplicateCase.setVisibility(View.VISIBLE);
                }

                holder.txvCloseCase.setVisibility(View.GONE);
                holder.txvOpenCase.setVisibility(View.GONE);

            } else if (supportThread.getStatus().equals("OPEN")) {

                holder.txvTicketNumber.setTextColor(ContextCompat.getColor(mContext, R.color.colortoolbar));

                if (isOpen) {
                    holder.txvOpenCase.setVisibility(View.GONE);

                } else {
                    isOpen = true;

                    holder.txvOpenCase.setVisibility(View.VISIBLE);
                }

                holder.txvCloseCase.setVisibility(View.GONE);
                holder.txvDuplicateCase.setVisibility(View.GONE);
            }

            holder.txvTicketNumber.setText(supportThread.getThreadID());
            holder.txvTopic.setText(CommonFunctions.replaceEscapeData(supportThread.getHelpTopic()));
            holder.txvSubject.setText(CommonFunctions.replaceEscapeData(supportThread.getSubject()));
            holder.txvDate.setText(CommonFunctions.convertDate(supportThread.getDateTimeIN()));
        }

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txvOpenCase;
        private TextView txvCloseCase;
        private TextView txvDuplicateCase;
        private LinearLayout linearThreadLayout;

        private TextView txvTicketNumber;
        private TextView txvTopic;
        private TextView txvSubject;
        private TextView txvDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            txvOpenCase = (TextView) itemView.findViewById(R.id.txvOpenCase);
            txvCloseCase = (TextView) itemView.findViewById(R.id.txvCloseCase);
            txvDuplicateCase = (TextView) itemView.findViewById(R.id.txvDuplicateCase);
            linearThreadLayout = (LinearLayout) itemView.findViewById(R.id.linearThreadLayout);
            linearThreadLayout.setOnClickListener(this);

            txvTicketNumber = (TextView) itemView.findViewById(R.id.txvTicketNumber);
            txvTopic = (TextView) itemView.findViewById(R.id.txvTopic);
            txvSubject = (TextView) itemView.findViewById(R.id.txvSubject);
            txvDate = (TextView) itemView.findViewById(R.id.txvDate);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position > -1) {

                SupportThread schoolminiSupportThread = mGridData.get(position);

                switch (v.getId()) {
                    case R.id.linearThreadLayout: {
                        Bundle args = new Bundle();
                        args.putString("HELPTOPIC", schoolminiSupportThread.getHelpTopic());
                        args.putString("STATUS", schoolminiSupportThread.getStatus());
                        args.putString("THREADID", schoolminiSupportThread.getThreadID());
                        args.putString("SUBJECT", schoolminiSupportThread.getSubject());

                        //((SchoolMiniActivity) mContext).displayView(9, args);
                        ((CoopAssistantHomeActivity) mContext).displayView(8, args);

                        break;
                    }
                }

            }

        }
    }
}
