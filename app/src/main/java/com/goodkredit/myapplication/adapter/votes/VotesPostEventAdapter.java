package com.goodkredit.myapplication.adapter.votes;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.voting.VotesActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.votes.VotesPostEvent;
import com.goodkredit.myapplication.utilities.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class VotesPostEventAdapter extends RecyclerView.Adapter<VotesPostEventAdapter.MyViewHolder> {

    private Context mContext;
    private List<VotesPostEvent> votePostEventList;

    public VotesPostEventAdapter(Context context) {
        mContext = context;
        votePostEventList = new ArrayList<>();
    }

    public void update(List<VotesPostEvent> arraylist) {
        votePostEventList.clear();
        votePostEventList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        votePostEventList.clear();
        notifyDataSetChanged();
    }

    @Override
    public VotesPostEventAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_votes_post_event_item, parent, false);
        VotesPostEventAdapter.MyViewHolder holder = new VotesPostEventAdapter.MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(VotesPostEventAdapter.MyViewHolder holder, int position) {
        try {
            VotesPostEvent votePostEvent = votePostEventList.get(position);

            Glide.with(mContext)
                    .load(votePostEvent.getEventPictureURL())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.generic_placeholder_gk_background)
                            .fitCenter())
                    .into(holder.imv_logo);

            holder.txv_title.setText(CommonFunctions.replaceEscapeData(votePostEvent.getEventName()));
            holder.txv_description.setText(CommonFunctions.replaceEscapeData(votePostEvent.getEventDescription()));
            holder.txv_votecloses.setText("Voting closes on ".concat(CommonFunctions.getDateTimeFromDateTime(
                    CommonFunctions.convertDate(votePostEvent.getEventDateTimeEnd()))));

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return votePostEventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private ImageView imv_logo;
        private TextView txv_title;
        private TextView txv_description;
        private LinearLayout btn_castvotes;
        private TextView txv_castvotes;
        private TextView txv_votecloses;

        private LinearLayout layout_container;


        public MyViewHolder(View itemView) {
            super(itemView);
            imv_logo = (ImageView) itemView.findViewById(R.id.imv_logo);
            txv_title = (TextView) itemView.findViewById(R.id.txv_title);
            txv_description = (TextView) itemView.findViewById(R.id.txv_description);
            btn_castvotes = (LinearLayout) itemView.findViewById(R.id.btn_castvotes);
            txv_castvotes = (TextView) itemView.findViewById(R.id.txv_castvotes);
            txv_votecloses = (TextView) itemView.findViewById(R.id.txv_votecloses);

            layout_container = (LinearLayout) itemView.findViewById(R.id.layout_container);

            btn_castvotes.setOnClickListener(this);
            layout_container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            Bundle args = new Bundle();

            if(position > -1){
                VotesPostEvent votesPostEvent = votePostEventList.get(position);

                PreferenceUtils.removePreference(mContext, PreferenceUtils.KEY_MERCHANTNAME);
                PreferenceUtils.saveStringPreference(mContext, PreferenceUtils.KEY_MERCHANTNAME, votesPostEvent.getMerchantName());

                switch (view.getId()){
                    case R.id.btn_castvotes: {

                        args.putParcelable("VotesPostObject", votesPostEvent);
                        VotesActivity.start(mContext, 1, args);
                        break;
                    }
                    case R.id.layout_container: {
                        args.putParcelable("VotesPostObject", votesPostEvent);
                        VotesActivity.start(mContext, 2, args);
                        break;
                    }
                }
            }
        }
    }
}
