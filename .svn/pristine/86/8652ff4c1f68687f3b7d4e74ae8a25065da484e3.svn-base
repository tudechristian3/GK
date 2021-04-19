package com.goodkredit.myapplication.adapter.uno;

import android.content.Context;
import androidx.annotation.NonNull;
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
import com.goodkredit.myapplication.model.UNORedemption;

import java.util.ArrayList;
import java.util.List;

public class UNORedemptionHistoryAdapter extends RecyclerView.Adapter<UNORedemptionHistoryAdapter.RedemptionViewHolder> {

    private Context mContext;
    private List<UNORedemption> mRedemptions = new ArrayList<>();

    public UNORedemptionHistoryAdapter(Context context) {
        mContext = context;
    }

    public void update(List<UNORedemption> data) {
        try {
            mRedemptions.clear();
            mRedemptions = data;
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public RedemptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.fragment_csb_redemption_history_item, parent, false);
        // Return a new holder instance
        RedemptionViewHolder viewHolder = new RedemptionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RedemptionViewHolder holder, int position) {
        UNORedemption redemption = mRedemptions.get(position);
        holder.borrowingsDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(redemption.getDateTimeCompleted())));
        holder.date.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(redemption.getDateTimeCompleted())));
        holder.numVouchers.setText(redemption.getTotalNoOfVoucher());
        holder.totalAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(redemption.getConversionAmount())));
        holder.transno.setText(redemption.getTransactionNo());
        holder.status.setText(redemption.getStatus());
        UNORedemptionHistoryVoucherDetailsAdapter adapter = new UNORedemptionHistoryVoucherDetailsAdapter(mContext);
        holder.recyclerView.setAdapter(adapter);
        adapter.update(redemption.getUNORedemptionVoucherDetails());
    }

    @Override
    public int getItemCount() {
        return mRedemptions.size();
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
