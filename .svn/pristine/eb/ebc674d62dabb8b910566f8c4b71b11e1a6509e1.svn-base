package com.goodkredit.myapplication.adapter.coopassistant.member;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantBills;
import com.goodkredit.myapplication.utilities.CacheManager;

import java.util.List;

public class CoopAssistantSOABillsAdapter extends RecyclerView.Adapter<CoopAssistantSOABillsAdapter.MyViewHolder> {

    private List<CoopAssistantBills> mGridData;
    private LayoutInflater layoutInflater;
    private Context mContext;

    private DatabaseHandler mdb;

    public CoopAssistantSOABillsAdapter(Context context) {
        this.mGridData = CacheManager.getInstance().getCoopAssistantSOABills();
        this.layoutInflater = LayoutInflater.from(context);
        mContext = context;
        mdb = new DatabaseHandler(mContext);
    }

    public void updateList(List<CoopAssistantBills> mGridData) {
        this.mGridData = mGridData;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CoopAssistantSOABillsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.item_coopassistant_bills, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoopAssistantSOABillsAdapter.MyViewHolder holder, int position) {
        CoopAssistantBills coopbills = mGridData.get(position);

        V2Subscriber v2Subscriber = mdb.getSubscriber(mdb);

        try{
            String type = CommonFunctions.replaceEscapeData(coopbills.getAccountName());
            String name = CommonFunctions.replaceEscapeData(coopbills.getMemberName());
            String datetime = CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(coopbills.getDateTimeIN()));
            String billamount = CommonFunctions.currencyFormatter(String.valueOf(coopbills.getBillAmount()));

            holder.txv_type.setText(type);
            holder.txv_name.setText(name);
            holder.txv_datetime.setText("As of ".concat(datetime));
            holder.txv_billamount.setText(billamount);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txv_type;
        private TextView txv_name;
        private TextView txv_datetime;
        private TextView txv_billamount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txv_type = (TextView) itemView.findViewById(R.id.txv_coop_billtype);
            txv_name = (TextView) itemView.findViewById(R.id.txv_coop_borrowername);
            txv_datetime = (TextView) itemView.findViewById(R.id.txv_coop_datetime);
            txv_billamount = (TextView) itemView.findViewById(R.id.txv_coop_billamount);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position > -1) {
                Bundle args = new Bundle();
                CoopAssistantBills coopbills = mGridData.get(position);
                args.putParcelable(CoopAssistantBills.KEY_COOPBILLS, coopbills);
                CoopAssistantHomeActivity.start(mContext, 10, args);
            }
        }
    }
}
