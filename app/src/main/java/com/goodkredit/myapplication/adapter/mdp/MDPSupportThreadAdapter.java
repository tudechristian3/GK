package com.goodkredit.myapplication.adapter.mdp;

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
import com.goodkredit.myapplication.activities.mdp.MDPSupportActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.mdp.MDPSupportThread;

import java.util.ArrayList;
import java.util.List;

public class MDPSupportThreadAdapter extends RecyclerView.Adapter<MDPSupportThreadAdapter.MyViewHolder> {

    private Context mContext;
    private List<MDPSupportThread> mGridData;
    private boolean isOpen;
    private boolean isClose;

    public MDPSupportThreadAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
        isOpen = false;
        isClose = false;
    }

    public void reset() {
        isOpen = false;
        isClose = false;
    }

    public void update(List<MDPSupportThread> data) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_skycable_support_thread, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (position > -1) {
            MDPSupportThread mdpsupportthread = mGridData.get(position);

            if (mdpsupportthread.getStatus().equals("CLOSED")) {

                holder.txvTicketNumber.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextGrey));

                if (isClose) {
                    holder.txvCloseCase.setVisibility(View.GONE);

                } else {
                    isClose = true;

                    holder.txvCloseCase.setVisibility(View.VISIBLE);
                }

                holder.txvOpenCase.setVisibility(View.GONE);

            } else if (mdpsupportthread.getStatus().equals("OPEN")) {

                holder.txvTicketNumber.setTextColor(ContextCompat.getColor(mContext, R.color.colortoolbar));

                if (isOpen) {
                    holder.txvOpenCase.setVisibility(View.GONE);

                } else {
                    isOpen = true;

                    holder.txvOpenCase.setVisibility(View.VISIBLE);
                }

                holder.txvCloseCase.setVisibility(View.GONE);
            }

            holder.txvTicketNumber.setText(mdpsupportthread.getThreadID());
            holder.txvTopic.setText(CommonFunctions.replaceEscapeData(mdpsupportthread.getHelpTopic()));
            holder.txvSubject.setText(CommonFunctions.replaceEscapeData(mdpsupportthread.getSubject()));
            holder.txvDate.setText(CommonFunctions.convertDate(mdpsupportthread.getDateTimeIN()));
        }

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txvOpenCase;
        private TextView txvCloseCase;
        private LinearLayout linearThreadLayout;

        private TextView txvTicketNumber;
        private TextView txvTopic;
        private TextView txvSubject;
        private TextView txvDate;

        public MyViewHolder(View itemView) {
            super(itemView);

            txvOpenCase = (TextView) itemView.findViewById(R.id.txvOpenCase);
            txvCloseCase = (TextView) itemView.findViewById(R.id.txvCloseCase);
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

                MDPSupportThread skycableSupportThread = mGridData.get(position);

                switch (v.getId()) {
                    case R.id.linearThreadLayout: {
                        Bundle args = new Bundle();
                        args.putString("HELPTOPIC", skycableSupportThread.getHelpTopic());
                        args.putString("STATUS", skycableSupportThread.getStatus());
                        args.putString("THREADID", skycableSupportThread.getThreadID());
                        args.putString("SUBJECT", skycableSupportThread.getSubject());

//                        ((SkyCableActivity) mContext).displayView(15, args);
                        ((MDPSupportActivity) mContext).displayView(3, args);
                        break;
                    }
                }

            }

        }
    }
}

