package com.goodkredit.myapplication.adapter.promo;

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
import com.goodkredit.myapplication.model.RedeemedPromo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 2/8/2018.
 */

public class RedeemedPromoHistoryAdapter extends RecyclerView.Adapter<RedeemedPromoHistoryAdapter.RedeemedPromoViewHolder> {

    private Context mContext;
    private List<RedeemedPromo> mRedeemedPromos = new ArrayList<>();

    public RedeemedPromoHistoryAdapter(Context context, List<RedeemedPromo> data) {
        mContext = context;
        mRedeemedPromos = data;
    }

    public void update(List<RedeemedPromo> data) {
        mRedeemedPromos.clear();
        mRedeemedPromos = data;
        notifyDataSetChanged();
    }

    @Override
    public RedeemedPromoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_scan_promo_history, parent, false);
        // Return a new holder instance
        RedeemedPromoViewHolder viewHolder = new RedeemedPromoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RedeemedPromoViewHolder holder, int position) {
        try {
            RedeemedPromo promo = mRedeemedPromos.get(position);

            TextView date = holder.date;
            TextView time = holder.time;
            TextView numVouchers = holder.numVouchers;
            TextView status = holder.status;
            TextView name = holder.name;
            RecyclerView recyclerView = holder.recyclerView;

            date.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(promo.getDateTimeRedeem())));
            time.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(promo.getDateTimeRedeem())));
            numVouchers.setText(CommonFunctions.parseJSON(promo.getXMLDetails(), "VoucherQuantity"));
            status.setText(promo.getStatus());
            name.setText(promo.getPromoName());
            recyclerView.setAdapter(new RedeemedPromoHistoryVoucherAdapter(mContext, promo.getXMLDetails()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mRedeemedPromos.size();
    }

    public class RedeemedPromoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView date;
        private TextView time;
        private TextView numVouchers;
        private TextView name;
        private TextView status;

        private Button btnViewDetails;
        private Button btnHideDetails;

        private LinearLayout vouchersLayout;
        private RecyclerView recyclerView;

        public RedeemedPromoViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.promo_date_redeemed);
            time = (TextView) itemView.findViewById(R.id.promo_time_redeemed);
            numVouchers = (TextView) itemView.findViewById(R.id.totalvoucher);
            name = (TextView) itemView.findViewById(R.id.promo_name);
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
