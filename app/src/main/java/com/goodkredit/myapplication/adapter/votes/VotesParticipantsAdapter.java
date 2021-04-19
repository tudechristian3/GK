package com.goodkredit.myapplication.adapter.votes;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.voting.VotesParticipantDetailsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.voting.VotesParticipantsFragment;
import com.goodkredit.myapplication.model.votes.VotesParticipants;
import com.goodkredit.myapplication.model.votes.VotesPostEvent;
import com.goodkredit.myapplication.utilities.PercentageCropImageView;

import java.util.ArrayList;
import java.util.List;

public class VotesParticipantsAdapter extends RecyclerView.Adapter<VotesParticipantsAdapter.MyViewHolder> {

    private Context mContext;
    private List<VotesParticipants> mGridData;
    private VotesParticipantsFragment votesParticpantFragment;

    public VotesParticipantsAdapter(Context context, VotesParticipantsFragment fragment) {
        mContext = context;
        mGridData = new ArrayList<>();
        votesParticpantFragment = fragment;
    }

    public void update(List<VotesParticipants> arraylist) {
        mGridData.clear();
        mGridData = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        mGridData.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VotesParticipantsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_votes_participants, parent, false);
        VotesParticipantsAdapter.MyViewHolder holder = new VotesParticipantsAdapter.MyViewHolder(itemView);
        itemView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VotesParticipantsAdapter.MyViewHolder holder, int position) {
        try {
            VotesParticipants participants = mGridData.get(position);

            Glide.with(mContext)
                    .load(participants.getProfilePictureURL())
                    .into(holder.img_picture);

            holder.img_picture.setCropYCenterOffsetPct(0.1f);

            String participantnumber = (participants.getParticipantNumber() < 10 ? "0" : "") + participants.getParticipantNumber();

            holder.txv_numbering.setText(String.valueOf(participantnumber));

            holder.txv_candidateno.setText("Candidate # ".concat(String.valueOf(participants.getParticipantNumber())));
            holder.txv_candidateplace.setText(CommonFunctions.replaceEscapeData(participants.getAddress()));
            holder.txv_votes.setText(String.valueOf(participants.getCurrentNoVote()));

            switch (participants.getMiddleName()) {
                case "":
                    holder.txv_candidatename.setText(CommonFunctions.replaceEscapeData(participants.getFirstName()
                            + " " + participants.getLastName()));
                    break;
                case ".":
                    holder.txv_candidatename.setText(CommonFunctions.replaceEscapeData(participants.getFirstName()
                            + " " + participants.getLastName()));
                    break;
                default:
                    holder.txv_candidatename.setText(CommonFunctions.replaceEscapeData(participants.getFirstName() + " "
                            + participants.getMiddleName() + " " + participants.getLastName()));
                    break;
            }

            if (position == 0) {
                holder.main_itemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.border_top_item_darker));
            } else if (position == mGridData.size() - 1) {
                holder.main_itemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.border_bottom_item_darker));
            } else {
                holder.main_itemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.color.whitePrimary));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private PercentageCropImageView img_picture;
        private TextView txv_numbering;
        private TextView txv_candidateno;
        private TextView txv_candidatename;
        private TextView txv_candidateplace;
        private TextView txv_votes;
        private LinearLayout main_itemscontainer;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_picture = itemView.findViewById(R.id.img_picture);
            txv_numbering = itemView.findViewById(R.id.txv_numbering);
            txv_candidateno = itemView.findViewById(R.id.txv_candidate_no);
            txv_candidatename = itemView.findViewById(R.id.txv_candidate_name);
            txv_candidateplace = itemView.findViewById(R.id.txv_candidate_place);
            txv_votes = itemView.findViewById(R.id.txv_votes);
            main_itemscontainer = itemView.findViewById(R.id.main_itemscontainer);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position > -1) {
                VotesParticipants participants = mGridData.get(position);

                VotesPostEvent votesPostObject = null;
                if (votesParticpantFragment.getArguments() != null) {
                    votesPostObject = votesParticpantFragment.getArguments().getParcelable("VotesPostObject");
                    Intent intent = new Intent(mContext, VotesParticipantDetailsActivity.class);
                    intent.putExtra(VotesParticipants.KEY_VOTESPARTICIPANTS, participants);
                    intent.putExtra("VotesPostObject", votesPostObject);
                    mContext.startActivity(intent);
                } else {
                    votesParticpantFragment.showToast("Something went wrong. Please try again", GlobalToastEnum.ERROR);
                }
            }
        }
    }
}
