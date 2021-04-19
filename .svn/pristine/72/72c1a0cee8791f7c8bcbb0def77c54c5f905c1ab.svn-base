package com.goodkredit.myapplication.adapter.rewards;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.CSBRedemption;
import com.goodkredit.myapplication.model.VoucherInfos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 1/10/2018.
 */

public class CSBRedemptionHistoryAdapter extends RecyclerView.Adapter<CSBRedemptionHistoryAdapter.RedemptionViewHolder> {

    private Context mContext;
    private List<CSBRedemption> mArrayList = new ArrayList<>();

    public CSBRedemptionHistoryAdapter(Context context, List<CSBRedemption> arrList) {
        mContext = context;
        mArrayList = arrList;
    }

    public void update(List<CSBRedemption> arrList) {
        try {
            mArrayList.clear();
            mArrayList = arrList;
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RedemptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.fragment_csb_redemption_history_item, parent, false);
        // Return a new holder instance
        RedemptionViewHolder viewHolder = new RedemptionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RedemptionViewHolder holder, int position) {
        CSBRedemption redemption = mArrayList.get(position);
        int mTotalAmount = 0;
        int mQty = 0;
        holder.borrowingsDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(redemption.getDateTimeCompleted())));
        holder.date.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(redemption.getDateTimeCompleted())));
        for (VoucherInfos vInfos : redemption.getSendDetails().getVoucherInfo().getVoucherInfos()) {
            mTotalAmount += (Integer.valueOf(vInfos.getDenom()) * Integer.valueOf(vInfos.getQuantity()));
            mQty += Integer.valueOf(vInfos.getQuantity());
        }
        holder.numVouchers.setText(String.valueOf(mQty));
        holder.totalAmount.setText(String.valueOf(mTotalAmount));
        if (null != redemption.getReceiveDetails())
            holder.transno.setText(redemption.getReceiveDetails().getTran_no());
        else
            holder.transno.setText(".");

        String resDesc = redemption.getResultDescription().substring(0, 1).toUpperCase() + redemption.getResultDescription().substring(1);
        holder.status.setText(resDesc);

        holder.recyclerView.setAdapter(new CSBRedemptionHistoryVoucherDetailsAdapter(mContext, redemption.getSendDetails().getVoucherInfo().getVoucherInfos()));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class RedemptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView date;
        private final TextView transno;
        private final TextView numVouchers;
        private final TextView totalAmount;
        private final TextView borrowingsDate;
        private final TextView status;

        private final Button btnViewDetails;
        private final Button btnHideDetails;

        private final LinearLayout vouchersLayout;
        private final RecyclerView recyclerView;


        public RedemptionViewHolder(View itemView) {
            super(itemView);
            borrowingsDate = (TextView) itemView.findViewById(R.id.borrowings_date);
            date = (TextView) itemView.findViewById(R.id.date);
            transno = (TextView) itemView.findViewById(R.id.transactionno);
            numVouchers = (TextView) itemView.findViewById(R.id.totalvoucher);
            totalAmount = (TextView) itemView.findViewById(R.id.totalamount);
            status = (TextView) itemView.findViewById(R.id.status);

            btnHideDetails = (Button) itemView.findViewById(R.id.btn_hide_voucher_details);
            btnViewDetails = (Button) itemView.findViewById(R.id.btn_view_voucher_details);
            btnViewDetails.setOnClickListener(this);
            btnHideDetails.setOnClickListener(this);

            vouchersLayout = (LinearLayout) itemView.findViewById(R.id.voucher_details_layout);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view_borrowings_details);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setNestedScrollingEnabled(false);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_view_voucher_details: {
                    btnViewDetails.setVisibility(View.GONE);
                    btnHideDetails.setVisibility(View.VISIBLE);
                    vouchersLayout.setVisibility(View.VISIBLE);
                    break;
                }
                case R.id.btn_hide_voucher_details: {
                    btnHideDetails.setVisibility(View.GONE);
                    btnViewDetails.setVisibility(View.VISIBLE);
                    vouchersLayout.setVisibility(View.GONE);
                    break;
                }
            }
        }
    }
}
