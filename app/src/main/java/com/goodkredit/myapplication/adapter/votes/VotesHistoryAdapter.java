package com.goodkredit.myapplication.adapter.votes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.votes.VotesHistory;
import com.goodkredit.myapplication.utilities.CacheManager;

import java.util.ArrayList;
import java.util.List;

public class VotesHistoryAdapter extends RecyclerView.Adapter<VotesHistoryAdapter.ViewHolder> {

    private List<VotesHistory> mGridData = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;

    public VotesHistoryAdapter(Context mContext) {
        this.layoutInflater = LayoutInflater.from(mContext);
        mGridData = CacheManager.getInstance().getVotesHistory();
    }

    public void updateList(List<VotesHistory> mGridData) {
        this.mGridData = mGridData;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VotesHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_votes_history, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VotesHistoryAdapter.ViewHolder holder, int position) {
        VotesHistory history = mGridData.get(position);

        holder.txv_datevoted.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(history.getDateTimeIN())));
        holder.txv_timevoted.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(history.getDateTimeIN())));
        holder.txv_eventname.setText(CommonFunctions.replaceEscapeData(history.getEventName()));
        holder.txv_participant.setText(CommonFunctions.replaceEscapeData(history.getParticipantName()));
        holder.txv_noofvotes.setText(String.valueOf(history.getNumberOfVotes()));
        holder.txv_status.setText(history.getStatus());

        String amount = CommonFunctions.currencyFormatter(String.valueOf(history.getTotalAmount()));
        holder.txv_paymentmethod.setText(history.getPaymentType());
        holder.txv_paymenttxnno.setText(history.getPaymentTxnID());
        holder.txv_totalamount.setText(amount);

        String participantid = history.getParticipantID();
        if(participantid.equals("DONATE")){
            holder.layout_participant.setVisibility(View.GONE);
            holder.layout_noofvotes.setVisibility(View.GONE);
        } else{
            holder.layout_participant.setVisibility(View.VISIBLE);
            holder.layout_noofvotes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txv_datevoted;
        private TextView txv_timevoted;
        private TextView txv_eventname;
        private TextView txv_participant;
        private TextView txv_noofvotes;
        private TextView txv_status;

        private Button btn_viewdetails;
        private Button btn_hidedetails;
        private TextView txv_paymentmethod;
        private TextView txv_paymenttxnno;
        private TextView txv_totalamount;
        private LinearLayout payment_details_layout;
        private LinearLayout layout_participant;
        private LinearLayout layout_noofvotes;

        public ViewHolder(View itemView) {
            super(itemView);

            txv_datevoted = (TextView) itemView.findViewById(R.id.txv_datevoted);
            txv_timevoted = (TextView) itemView.findViewById(R.id.txv_timevoted);
            txv_eventname = (TextView) itemView.findViewById(R.id.txv_eventname);
            txv_participant = (TextView) itemView.findViewById(R.id.txv_participant);
            txv_noofvotes = (TextView) itemView.findViewById(R.id.txv_noofvotes);
            txv_status = (TextView) itemView.findViewById(R.id.txv_status);
            btn_viewdetails = (Button) itemView.findViewById(R.id.btn_view_payment_details);
            btn_hidedetails = (Button) itemView.findViewById(R.id.btn_hide_payment_details);
            txv_paymentmethod = (TextView) itemView.findViewById(R.id.txv_paymentmethod);
            txv_paymenttxnno = (TextView) itemView.findViewById(R.id.txv_paymenttxnno);
            txv_totalamount = (TextView) itemView.findViewById(R.id.txv_totalamount);
            payment_details_layout = (LinearLayout) itemView.findViewById(R.id.payment_details_layout);
            layout_participant = (LinearLayout) itemView.findViewById(R.id.layout_participant);
            layout_noofvotes = (LinearLayout) itemView.findViewById(R.id.layout_noofvotes);

            btn_viewdetails.setOnClickListener(this);
            btn_hidedetails.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if(position > -1){
                VotesHistory history = mGridData.get(position);

                switch (view.getId()){
                    case R.id.btn_view_payment_details: {
                        payment_details_layout.setVisibility(View.VISIBLE);
                        btn_viewdetails.setVisibility(View.GONE);
                        btn_hidedetails.setVisibility(View.VISIBLE);
                        break;
                    }
                    case R.id.btn_hide_payment_details: {
                        payment_details_layout.setVisibility(View.GONE);
                        btn_hidedetails.setVisibility(View.GONE);
                        btn_viewdetails.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }
    }
}
