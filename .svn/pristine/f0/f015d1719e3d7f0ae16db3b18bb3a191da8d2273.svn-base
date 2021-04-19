package com.goodkredit.myapplication.adapter.paramount;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.paramount.ParamountInsuranceConfirmationActivity;
import com.goodkredit.myapplication.activities.paramount.ViewParamountHistoryActivity;
import com.goodkredit.myapplication.bean.ParamountQueue;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 2/7/2018.
 */

public class ViewParamountHistoryAdapter extends RecyclerView.Adapter<ViewParamountHistoryAdapter.MyViewHolder> {
    private Context mContext;
    private List<ParamountQueue> mGridData;

    public ViewParamountHistoryAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    private Context getContext() {
        return mContext;
    }

    public void update(List<ParamountQueue> data) {
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
    public ViewParamountHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_paramount_history_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewParamountHistoryAdapter.MyViewHolder holder, int position) {
        ParamountQueue paramountQueue = mGridData.get(position);
        holder.txvDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(paramountQueue.getDateTimeCompleted())));
        holder.txvTime.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(paramountQueue.getDateTimeCompleted())));
        holder.transactionNo.setText(paramountQueue.getTransactionNo());
        holder.amountPaid.setText(CommonFunctions.currencyFormatter(String.valueOf(paramountQueue.getAmountPaid())));
        //holder.registrationType.setText(paramountQueue.getType());
        holder.registrationType.setText("Renewal");
        holder.registrationID.setText(paramountQueue.getRegistrationID().equals(".") ? "" : paramountQueue.getRegistrationID());

        holder.txvAuthNo.setText(paramountQueue.getAuthNo().equals(".") ? "" : paramountQueue.getAuthNo());
        holder.txvCocNo.setText(paramountQueue.getCOCNo().equals(".") ? "" : paramountQueue.getCOCNo());

        String status = "";
        switch (paramountQueue.getTxnStatus()) {
            case "ONPROCESS": {

                status = "ON PROCESS";

                break;
            }
            default: {

                status = paramountQueue.getTxnStatus();

                break;
            }
        }

        if (paramountQueue.getTxnStatus().equals("SUCCESS")) {
            holder.btnSendEmailLayout.setVisibility(View.VISIBLE);
            holder.txnStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.color_4A90E2));
        } else if (paramountQueue.getTxnStatus().equals("FAILED")) {
            holder.btnSendEmailLayout.setVisibility(View.GONE);
            holder.txnStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.color_E91E63));
        } else {
            holder.btnSendEmailLayout.setVisibility(View.GONE);
            holder.txnStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.color_676767));
        }

        holder.txnStatus.setText(status);

        switch (paramountQueue.getType()) {
            case "Individual": {

                holder.corporateLayout.setVisibility(View.GONE);
                holder.nameLayout.setVisibility(View.VISIBLE);

                String name = paramountQueue.getMiddleName().isEmpty() ? paramountQueue.getFirstName() + " " + paramountQueue.getLastName() : paramountQueue.getFirstName() + " " + paramountQueue.getMiddleName() + " " + paramountQueue.getLastName();
                holder.name.setText(CommonFunctions.capitalizeWord(name));

                break;
            }
            case "Corporate without assignee": {

                holder.corporateLayout.setVisibility(View.VISIBLE);
                holder.nameLayout.setVisibility(View.GONE);

                holder.corporateName.setText(paramountQueue.getCorporateName());

                break;
            }
            case "Corporate with assignee": {

                holder.corporateLayout.setVisibility(View.VISIBLE);
                holder.nameLayout.setVisibility(View.VISIBLE);

                holder.corporateName.setText(paramountQueue.getCorporateName());

                String name = paramountQueue.getMiddleName().isEmpty() ? paramountQueue.getFirstName() + " " + paramountQueue.getLastName() : paramountQueue.getFirstName() + " " + paramountQueue.getMiddleName() + " " + paramountQueue.getLastName();
                holder.name.setText(CommonFunctions.capitalizeWord(name));

                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txvDate;
        private TextView txvTime;
        private TextView transactionNo;
        private TextView amountPaid;
        private TextView registrationType;
        private TextView corporateName;
        private TextView name;
        private TextView txnStatus;
        private TextView registrationID;
        private TextView txvAuthNo;
        private TextView txvCocNo;

        private LinearLayout corporateLayout;
        private LinearLayout nameLayout;
        private LinearLayout historyLayout;
        private LinearLayout btnSendEmailLayout;

        private Button btnSendToEmail;

        public MyViewHolder(View itemView) {
            super(itemView);
            txvDate = (TextView) itemView.findViewById(R.id.txvDate);
            txvTime = (TextView) itemView.findViewById(R.id.txvTime);
            transactionNo = (TextView) itemView.findViewById(R.id.transactionNo);
            amountPaid = (TextView) itemView.findViewById(R.id.amountPaid);
            registrationType = (TextView) itemView.findViewById(R.id.registrationType);
            corporateName = (TextView) itemView.findViewById(R.id.corporateName);
            name = (TextView) itemView.findViewById(R.id.name);
            txnStatus = (TextView) itemView.findViewById(R.id.txnStatus);
            registrationID = (TextView) itemView.findViewById(R.id.registrationID);

            txvAuthNo = (TextView) itemView.findViewById(R.id.txvAuthNo);
            txvCocNo = (TextView) itemView.findViewById(R.id.txvCocNo);

            btnSendEmailLayout = (LinearLayout) itemView.findViewById(R.id.btnSendEmailLayout);
            historyLayout = (LinearLayout) itemView.findViewById(R.id.historyLayout);
            corporateLayout = (LinearLayout) itemView.findViewById(R.id.corporateNameLayout);
            nameLayout = (LinearLayout) itemView.findViewById(R.id.nameLayout);

            btnSendToEmail = (Button) itemView.findViewById(R.id.send_to_email);
            btnSendToEmail.setOnClickListener(this);

            historyLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            if (position > -1) {

                ParamountQueue paramountQueue = mGridData.get(position);

                switch (v.getId()) {
                    case R.id.historyLayout: {

                        ParamountInsuranceConfirmationActivity.start(getContext(), paramountQueue, "HISTORY");

                        break;
                    }
                    case R.id.send_to_email: {

                        ((ViewParamountHistoryActivity) getContext()).sendParamountEmail(paramountQueue);

                        break;
                    }
                }

            }

        }
    }
}
