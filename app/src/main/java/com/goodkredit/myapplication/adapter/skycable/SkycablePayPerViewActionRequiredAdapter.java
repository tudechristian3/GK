package com.goodkredit.myapplication.adapter.skycable;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.bean.SkycablePPVHistory;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

public class SkycablePayPerViewActionRequiredAdapter extends RecyclerView.Adapter<SkycablePayPerViewActionRequiredAdapter.MyViewHolder> {
    private Context mContext;
    private List<SkycablePPVHistory> mGridData;
    private boolean isActionRequired = false;

    public SkycablePayPerViewActionRequiredAdapter(Context context, boolean isActionRequired) {
        mContext = context;
        mGridData = new ArrayList<>();
        this.isActionRequired = isActionRequired;
    }

    public void update(List<SkycablePPVHistory> data) {
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

    @Override
    public SkycablePayPerViewActionRequiredAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_skycable_ppv_action_required_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SkycablePayPerViewActionRequiredAdapter.MyViewHolder holder, int position) {
        SkycablePPVHistory skycablePPVHistory = mGridData.get(position);

        holder.txvApprovalCode.setText(skycablePPVHistory.getSubscriptionTxnID());
        holder.txvAccountNo.setText(skycablePPVHistory.getSkyCableAccountNo());
        holder.txvPPV.setText(CommonFunctions.replaceEscapeData(skycablePPVHistory.getPPVName()));
        holder.txvName.setText(CommonFunctions.capitalizeWord(skycablePPVHistory.getCustomerFirstName() + " " + skycablePPVHistory.getCustomerLastName()));
        holder.txvStatus.setText(CommonFunctions.capitalizeWord(skycablePPVHistory.getStatus()));

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout linearRegistrationItem;
        private TextView txvApprovalCode;
        private TextView txvAccountNo;
        private TextView txvPPV;
        private TextView txvName;
        private TextView txvStatus;

        public MyViewHolder(View itemView) {
            super(itemView);

            linearRegistrationItem = (LinearLayout) itemView.findViewById(R.id.linearRegistrationItem);
            linearRegistrationItem.setOnClickListener(this);
            txvApprovalCode = (TextView) itemView.findViewById(R.id.txvApprovalCode);
            txvAccountNo = (TextView) itemView.findViewById(R.id.txvAccountNo);
            txvPPV = (TextView) itemView.findViewById(R.id.txvPPV);
            txvName = (TextView) itemView.findViewById(R.id.txvName);
            txvStatus = (TextView) itemView.findViewById(R.id.txvStatus);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            if (position > -1) {

                SkycablePPVHistory skycablePPVHistory = mGridData.get(position);

                switch (v.getId()){
                    case R.id.linearRegistrationItem:{

                        Bundle args = new Bundle();
                        if (isActionRequired) {
                            args.putString("TYPE", "ACTIONREQUIRED");
                        } else {
                            args.putString("TYPE", "HISTORY");
                        }
                        args.putParcelable("skycablePPVHistory", skycablePPVHistory);

                        ((SkyCableActivity) mContext).displayView(6, args);

                        break;
                    }
                }

//                SkycableRegistration skycableRegistration = mGridData.get(position);
//
//                switch (v.getId()) {
//                    case R.id.linearRegistrationItem: {
//                        Bundle args = new Bundle();
//                        if (isActionRequired) {
//                            args.putString("TYPE", "ACTIONREQUIRED");
//                        } else {
//                            args.putString("TYPE", "HISTORY");
//                        }
//                        args.putParcelable("skycableRegistration", skycableRegistration);
//
//                        ((SkyCableActivity) mContext).displayView(11, args);
//
//                        break;
//                    }
//                }

            }

        }
    }
}
