package com.goodkredit.myapplication.adapter.whatsnew;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.whatsnew.GKWhatsNewCommonDetailsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.gkads.GKAds;

import java.util.ArrayList;
import java.util.List;

public class NewUpdatesAdapter extends RecyclerView.Adapter<NewUpdatesAdapter.UpdatesViewHolder> {

    private Context mContext;
    private List<GKAds> mAds = new ArrayList<>();

    public NewUpdatesAdapter(Context context) {
        mContext = context;
    }

    public void update(List<GKAds> ads) {
        mAds.clear();
        mAds = ads;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UpdatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_new_updates, parent, false);
        return new UpdatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdatesViewHolder holder, int position) {

        GKAds ads = mAds.get(position);

        holder.mTvTime.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(ads.getDurationFrom())));
        holder.mTvAdTitle.setText(CommonFunctions.replaceEscapeData(ads.getName()));
        holder.mTvAdDescription.setText(CommonFunctions.replaceEscapeData(ads.getDescription()));
        holder.mTvDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(ads.getDurationFrom())));

        holder.itemView.setTag(ads);
        holder.itemView.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GKAds ads = (GKAds) v.getTag();
            GKWhatsNewCommonDetailsActivity.start(mContext,ads);
        }
    };

    @Override
    public int getItemCount() {
        return mAds.size();
    }

    public class UpdatesViewHolder extends ViewHolder {

        private TextView mTvTime;
        private TextView mTvAdTitle;
        private TextView mTvAdDescription;
        private TextView mTvDate;

        public UpdatesViewHolder(View itemView) {
            super(itemView);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvAdTitle = itemView.findViewById(R.id.tv_ad_title);
            mTvAdDescription = itemView.findViewById(R.id.tv_ad_description);
            mTvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
