package com.goodkredit.myapplication.adapter.rfid;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.rfid.RFIDDetailsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.rfid.RFIDInfo;

import java.util.List;

public class RFIDAdapter extends RecyclerView.Adapter<RFIDAdapter.ViewHolder> {

    private List<RFIDInfo> mGridData;
    private LayoutInflater layoutInflater;
    private Context mContext;

    private String mKey;

    public RFIDAdapter(List<RFIDInfo> data, Context context, String key) {
        this.mGridData = data;
        this.layoutInflater = LayoutInflater.from(context);
        mContext = context;
        mKey = key;
    }

    public void updateList(List<RFIDInfo> mGridData) {
        this.mGridData = mGridData;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RFIDAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.item_rfid, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RFIDAdapter.ViewHolder holder, int position) {
        RFIDInfo rfid = mGridData.get(position);

        try{

            String cardno = rfid.getRFIDCardNumber();
            String spin = rfid.getRFIDPIN();
            double balance = rfid.getAmountLimitBalance();
            double minamount = rfid.getPINMinAmount();

            holder.txv_rfid_account_no.setText(CommonFunctions.replaceEscapeData(cardno));

            if (spin != null) {
                if(spin.trim().equals("") || spin.trim().equals(".")) {
                    holder.linear_rfid_spin_container.setVisibility(View.GONE);
                } else {
                    holder.linear_rfid_spin_container.setVisibility(View.VISIBLE);
                    holder.txv_rfid_spin.setText(CommonFunctions.replaceEscapeData(spin));
                }
            }

            holder.txv_rfid_balance.setText(CommonFunctions.currencyFormatter(String.valueOf(balance)));

            holder.txv_rfid_minamount.setText(CommonFunctions.currencyFormatter(String.valueOf(minamount)));

            if(mKey.equals("activerfid")){
                if(rfid.getIsWithLimit() == 1){
                    holder.layout_rfid_balance.setVisibility(View.VISIBLE);
                } else{
                    holder.layout_rfid_balance.setVisibility(View.GONE);
                }

            } else if(mKey.equals("inactiverfid")){
                holder.layout_rfid_balance.setVisibility(View.GONE);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txv_rfid_account_no;
        private TextView txv_rfid_balance;
        private LinearLayout linear_rfid_spin_container;
        private TextView txv_rfid_spin;
        private LinearLayout layout_rfid_balance;
        private LinearLayout layout_rfid_minamount;
        private TextView txv_rfid_minamount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txv_rfid_account_no = (TextView) itemView.findViewById(R.id.txv_rfid_accountno);
            txv_rfid_balance = (TextView) itemView.findViewById(R.id.txv_rfid_balance);
            linear_rfid_spin_container = (LinearLayout) itemView.findViewById(R.id.linear_rfid_spin_container);
            txv_rfid_spin = (TextView) itemView.findViewById(R.id.txv_rfid_spin);
            layout_rfid_balance = (LinearLayout) itemView.findViewById(R.id.layout_rfid_balance);
            layout_rfid_minamount = (LinearLayout) itemView.findViewById(R.id.layout_rfid_minamount);
            txv_rfid_minamount = (TextView) itemView.findViewById(R.id.txv_rfid_minamount);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position > -1) {
                RFIDInfo rfid = mGridData.get(position);

                Intent intent = new Intent(mContext, RFIDDetailsActivity.class);
                intent.putExtra(RFIDInfo.KEY_RFIDINFO, rfid);
                intent.putExtra(RFIDInfo.KEY_RFIDFROM, mKey);
                intent.putExtra(RFIDInfo.KEY_POSITION, position);
                if(mKey.equals("activerfid")) {
                    intent.putExtra(RFIDInfo.KEY_RFIDPOSITION, "Active".concat(String.valueOf(position)));
                    intent.putExtra(RFIDInfo.KEY_LIMITPOSITION, "Limit".concat(String.valueOf(position)));
                    intent.putExtra(RFIDInfo.KEY_ACTIVEBALANCE, "ActiveBalance".concat(String.valueOf(position)));
                    intent.putExtra(RFIDInfo.KEY_ACTIVESPIN, "ActiveSPIN".concat(String.valueOf(position)));
                    intent.putExtra("SPIN".concat(String.valueOf(position)), rfid.getRFIDPIN());

                    mContext.startActivity(intent);
                } else if(mKey.equals("inactiverfid")){
                    intent.putExtra(RFIDInfo.KEY_RFIDPOSITION, "Inactive".concat(String.valueOf(position)));
                    intent.putExtra(RFIDInfo.KEY_LIMITPOSITION, "Limit".concat(String.valueOf(position)));
                    mContext.startActivity(intent);
                }
            }
        }
    }

}
