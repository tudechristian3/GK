package com.goodkredit.myapplication.adapter.votes;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.voting.VotesBillingReferenceActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.votes.VotesPending;
import com.goodkredit.myapplication.utilities.CacheManager;

import java.util.ArrayList;
import java.util.List;

public class VotesPendingAdapter extends RecyclerView.Adapter<VotesPendingAdapter.ViewHolder> {

    private List<VotesPending> mGridData = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;

    public VotesPendingAdapter(Context context) {
        mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        mGridData = CacheManager.getInstance().getVotesPending();
    }

    public void updateList(List<VotesPending> mGridData) {
        this.mGridData = mGridData;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VotesPendingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_votes_pending, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VotesPendingAdapter.ViewHolder holder, int position) {

        VotesPending pending = mGridData.get(position);

        holder.txv_datetime.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(
                pending.getDateTimeIN()
        )));

        String amount = CommonFunctions.currencyFormatter(String.valueOf(pending.getTotalAmount()));

        holder.txv_billingno.setText(pending.getBillingID());
        holder.txv_noofvotes.setText(String.valueOf(pending.getNumberOfVotes()));
        holder.txv_totalamount.setText(amount);
        holder.txv_status.setText(pending.getStatus());

        String participantid = pending.getParticipantID();
        if(participantid.equals("DONATE")){
            holder.layout_noofvotes.setVisibility(View.GONE);
        } else{
            holder.layout_noofvotes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txv_datetime;
        private TextView txv_billingno;
        private TextView txv_noofvotes;
        private TextView txv_totalamount;
        private TextView txv_status;
        private LinearLayout layout_noofvotes;

        public ViewHolder(View itemView) {
            super(itemView);

            txv_datetime = (TextView) itemView.findViewById(R.id.txv_datetime);
            txv_billingno = (TextView) itemView.findViewById(R.id.txv_billingno);
            txv_noofvotes = (TextView) itemView.findViewById(R.id.txv_noofvotes);
            txv_totalamount = (TextView) itemView.findViewById(R.id.txv_totalamount);
            txv_status = (TextView) itemView.findViewById(R.id.txv_status);
            layout_noofvotes = (LinearLayout) itemView.findViewById(R.id.layout_noofvotes);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position > -1) {
                VotesPending pending = mGridData.get(position);

                String amount = CommonFunctions.currencyFormatter(String.valueOf(pending.getTotalAmount()));

                VotesBillingReferenceActivity.start2(mContext, pending, amount, "VotesPending");

            }
        }
    }
}
