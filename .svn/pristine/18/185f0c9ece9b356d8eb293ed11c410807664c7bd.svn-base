package com.goodkredit.myapplication.adapter.projectcoop;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.projectcoop.ProjectCoopActivity;
import com.goodkredit.myapplication.bean.projectcoop.GameHistory;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

public class ProjectCoopPlayToSaveHistoryRecyclerAdapter extends RecyclerView.Adapter<ProjectCoopPlayToSaveHistoryRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<GameHistory> mGridData;

    public ProjectCoopPlayToSaveHistoryRecyclerAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    public void update(List<GameHistory> data) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_project_coop_play_to_save_history_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        GameHistory gameHistory = mGridData.get(position);

        holder.txvTransactionNo.setText("#".concat(gameHistory.getTransactionNo()));
        holder.txvAccountNumber.setText(gameHistory.getAccountID());
        holder.txvName.setText(gameHistory.getName());
        holder.txvStatus.setText(gameHistory.getStatus());
        holder.txvDate.setText(CommonFunctions.convertDate(gameHistory.getDateTimeCompleted()));

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txvTransactionNo;
        private TextView txvAccountNumber;
        private TextView txvName;
        private TextView txvStatus;
        private TextView txvDate;
        private LinearLayout linearRegistrationItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            txvTransactionNo = (TextView) itemView.findViewById(R.id.txvTransactionNo);
            txvAccountNumber = (TextView) itemView.findViewById(R.id.txvAccountNumber);
            txvName = (TextView) itemView.findViewById(R.id.txvName);
            txvStatus = (TextView) itemView.findViewById(R.id.txvStatus);
            txvDate = (TextView) itemView.findViewById(R.id.txvDate);
            linearRegistrationItem = (LinearLayout) itemView.findViewById(R.id.linearRegistrationItem);
            linearRegistrationItem.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            GameHistory gameHistory = mGridData.get(getAdapterPosition());

            switch (v.getId()) {
                case R.id.linearRegistrationItem: {
                    Bundle args = new Bundle();
                    args.putParcelable("GAMEP2SOBJ", gameHistory);
                    args.putString("AMOUNT", String.valueOf(gameHistory.getAmount()));
                    args.putString("SERVICECHARGE", String.valueOf(gameHistory.getServiceCharge()));
                    args.putString("AMOUNTPAID", String.valueOf(gameHistory.getAmountPaid()));
                    args.putString("TYPE", "HISTORY");
                    args.putString("P2SCOMBINATION", String.valueOf(gameHistory.getEntryNumber()));
                    args.putString("P2SNAME", gameHistory.getName());
                    args.putString("P2SACCOUNTNUMBER", gameHistory.getAccountID());

                    ((ProjectCoopActivity) mContext).displayView(3, args);
                    break;
                }
            }
        }
    }
}
