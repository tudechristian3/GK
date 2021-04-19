package com.goodkredit.myapplication.adapter.fairchild;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.fairchild.FairChildMembers;
import com.goodkredit.myapplication.utilities.PercentageCropImageView;
import com.goodkredit.myapplication.utilities.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class FairChildVotesAdapter extends RecyclerView.Adapter<FairChildVotesAdapter.ViewHolder>{

    private Context mContext;
    private List<FairChildMembers> mGridData;
    private LayoutInflater layoutInflater;
    private int voteLimit = 0;
    private String fairchildposition;

    private Boolean isCheck = false;
    private int countCheck = 0;

    private List<String> votesArrayList = new ArrayList<>();

    public FairChildVotesAdapter(List<FairChildMembers> mGridData, Context mContext, int voteLimit, String fairchildposition) {
        this.mGridData = mGridData;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.voteLimit = voteLimit;
        this.fairchildposition = fairchildposition;
    }

    public void updateList(List<FairChildMembers> mGridData) {
        this.mGridData = mGridData;
        this.notifyDataSetChanged();
    }

    public void clear() {
        countCheck = 0;
        this.mGridData.clear();
        votesArrayList.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FairChildVotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_fairchild_vote_candidates, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    public class MyCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        int position;
        ViewHolder holder;

        public MyCheckedChangeListener(int position, ViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            FairChildMembers members = mGridData.get(position);

            String participantid = members.getParticipantID();


            if(countCheck == voteLimit && buttonView.isChecked()){
                holder.checkBox.setChecked(false);
                ((BaseActivity) mContext).showToast("You can only vote " + voteLimit
                                + " candidates.", GlobalToastEnum.WARNING);
            } else if(buttonView.isChecked()){
                countCheck++;

                votesArrayList.add(participantid);

                switch (fairchildposition) {
                    case "boardofdirectors":
                        PreferenceUtils.removePreference(mContext, "fairchild_directors");
                        PreferenceUtils.saveStringListPreference(mContext, "fairchild_directors", votesArrayList);
                        break;
                    case "auditcommittee":
                        PreferenceUtils.removePreference(mContext, "fairchild_audit");
                        PreferenceUtils.saveStringListPreference(mContext, "fairchild_audit", votesArrayList);
                        break;
                    case "electioncommittee":
                        PreferenceUtils.removePreference(mContext, "fairchild_election");
                        PreferenceUtils.saveStringListPreference(mContext, "fairchild_election", votesArrayList);
                        break;
                }

            } else {
                countCheck--;
                for(String votesmembers : votesArrayList){
                    if(votesmembers.contains(participantid)) {
                        votesArrayList.remove(votesmembers);

                        switch (fairchildposition) {
                            case "boardofdirectors":
                                PreferenceUtils.removePreference(mContext, "fairchild_directors");
                                PreferenceUtils.saveStringListPreference(mContext, "fairchild_directors", votesArrayList);
                                break;
                            case "auditcommittee":
                                PreferenceUtils.removePreference(mContext, "fairchild_audit");
                                PreferenceUtils.saveStringListPreference(mContext, "fairchild_audit", votesArrayList);
                                break;
                            case "electioncommittee":
                                PreferenceUtils.removePreference(mContext, "fairchild_election");
                                PreferenceUtils.saveStringListPreference(mContext, "fairchild_election", votesArrayList);
                                break;
                        }

                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FairChildVotesAdapter.ViewHolder holder, int position) {
        FairChildMembers members = mGridData.get(position);

        Glide.with(mContext)
                .load(members.getImageUrl())
                .into(holder.img_photo);

        holder.img_photo.setCropYCenterOffsetPct(0.1f);
        holder.txv_name.setText(CommonFunctions.replaceEscapeData(members.getName()));
        holder.txv_no.setText("Candidate #" + members.getParticipantID());

        MyCheckedChangeListener myCheckedChangeListener = new MyCheckedChangeListener(position, holder);

        holder.checkBox.setOnCheckedChangeListener(myCheckedChangeListener);
        holder.checkBox.setTag(holder);

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private PercentageCropImageView img_photo;
        private TextView txv_name;
        private TextView txv_no;

        private CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);

            img_photo = itemView.findViewById(R.id.img_candidate_photo);
            txv_name = itemView.findViewById(R.id.txv_candidate_name);
            txv_no = itemView.findViewById(R.id.txv_candidate_no);

            checkBox = itemView.findViewById(R.id.chk_fairchild);

        }
    }
}
