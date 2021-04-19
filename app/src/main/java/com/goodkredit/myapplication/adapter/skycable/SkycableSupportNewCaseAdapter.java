package com.goodkredit.myapplication.adapter.skycable;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.bean.SkycableSupportHelpTopics;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

public class SkycableSupportNewCaseAdapter extends RecyclerView.Adapter<SkycableSupportNewCaseAdapter.MyViewHolder> {
    private Context mContext;
    private List<SkycableSupportHelpTopics> mGridData;

    public SkycableSupportNewCaseAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    public void update(List<SkycableSupportHelpTopics> data) {
        int startPos = mGridData.size() + 1;
        mGridData.clear();
        mGridData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public void clear() {
        int startPos = mGridData.size();
        mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_help_topic_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position > -1) {
            SkycableSupportHelpTopics skycableSupportHelpTopics = mGridData.get(position);

            holder.tvTopic.setText(CommonFunctions.replaceEscapeData(skycableSupportHelpTopics.getHelpTopic()));
            holder.tvDescription.setText(CommonFunctions.replaceEscapeData(skycableSupportHelpTopics.getDescription()));

            Glide.with(mContext)
                    .load(skycableSupportHelpTopics.getLogo())
                    .apply(RequestOptions.fitCenterTransform())
                    .into(holder.imgVLogo);
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTopic;
        private TextView tvDescription;
        private ImageView imgVLogo;
        private LinearLayout linearSupportCaseLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTopic = (TextView) itemView.findViewById(R.id.helpTopicTopic);
            tvDescription = (TextView) itemView.findViewById(R.id.helpTopicDescription);
            imgVLogo = (ImageView) itemView.findViewById(R.id.helpTopicIcon);
            linearSupportCaseLayout = (LinearLayout) itemView.findViewById(R.id.linearSupportCaseLayout);
            linearSupportCaseLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            if (position > -1) {
                SkycableSupportHelpTopics skycableSupportHelpTopics = mGridData.get(position);

                switch (v.getId()) {
                    case R.id.linearSupportCaseLayout: {

                        Bundle args = new Bundle();
                        args.putString("HELPTOPIC", skycableSupportHelpTopics.getHelpTopic());

                        ((SkyCableActivity) mContext).displayView(14, args);

                        break;
                    }
                }
            }

        }
    }
}
