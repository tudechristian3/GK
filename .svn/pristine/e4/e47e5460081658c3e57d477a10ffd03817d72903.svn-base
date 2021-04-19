package com.goodkredit.myapplication.adapter.settings;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.settings.SupportFAQActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.responses.support.GetHelpTopicsResponseData;

import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 3/7/2017.
 */

public class SupportHelpTopicAdapter extends RecyclerView.Adapter<SupportHelpTopicAdapter.SupportHelpTopicViewHolder> {

    private Context mContext;
    private ArrayList<GetHelpTopicsResponseData> mArrayList;
    private LayoutInflater inflater;

    public SupportHelpTopicAdapter(Context mContext, ArrayList<GetHelpTopicsResponseData> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    @Override
    public SupportHelpTopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_help_topic_item, parent, false);

        SupportHelpTopicViewHolder viewHolder = new SupportHelpTopicViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SupportHelpTopicViewHolder holder, int position) {
        GetHelpTopicsResponseData obj = mArrayList.get(position);
        TextView topic = holder.tvTopic;
        TextView description = holder.tvDescription;
        ImageView logo = holder.imgVLogo;

        topic.setText(CommonFunctions.replaceEscapeData(obj.getHelpTopic()));
        description.setText(CommonFunctions.replaceEscapeData(obj.getDescription()));

        Glide.with(mContext)
                .load(obj.getLogo())
                .apply(RequestOptions.fitCenterTransform())
                .into(logo);

        holder.itemView.setTag(obj);
        holder.itemView.setOnClickListener(onTopicClickedListener);

    }

    private View.OnClickListener onTopicClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GetHelpTopicsResponseData obj = (GetHelpTopicsResponseData) view.getTag();
            SupportFAQActivity.start(mContext, obj);
        }
    };

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class SupportHelpTopicViewHolder extends RecyclerView.ViewHolder {

        TextView tvTopic;
        TextView tvDescription;
        ImageView imgVLogo;

        public SupportHelpTopicViewHolder(View itemView) {
            super(itemView);
            tvTopic = (TextView) itemView.findViewById(R.id.helpTopicTopic);
            tvDescription = (TextView) itemView.findViewById(R.id.helpTopicDescription);
            imgVLogo = (ImageView) itemView.findViewById(R.id.helpTopicIcon);
        }
    }

    public void update(ArrayList<GetHelpTopicsResponseData> arrayList) {
        mArrayList = arrayList;
        notifyDataSetChanged();
    }

    public void clearData() {
        mArrayList.clear();
        notifyDataSetChanged();
    }
}
