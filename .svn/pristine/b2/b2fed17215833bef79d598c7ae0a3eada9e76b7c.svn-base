package com.goodkredit.myapplication.adapter.whatsnew;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.whatsnew.GKWhatsNewCommonDetailsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.gkads.GKAds;

import java.util.ArrayList;
import java.util.List;

public class PromotionsAdapter extends RecyclerView.Adapter<PromotionsAdapter.PromotionsViewHolder> {

    private Context mContext;
    private List<GKAds> mAds = new ArrayList<>();

    public PromotionsAdapter(Context context) {
        mContext = context;
    }

    public void update(List<GKAds> ads) {
        mAds.clear();
        mAds = ads;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PromotionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_promotions, parent, false);
        return new PromotionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionsViewHolder holder, int position) {

        GKAds ads = mAds.get(position);

        holder.mTvAdTitle.setText(CommonFunctions.replaceEscapeData(ads.getName()));
        holder.mTvAdDescription.setText(CommonFunctions.replaceEscapeData(ads.getDescription()));
        holder.mTvDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(ads.getDurationFrom())));
        Glide.with(mContext)
                .load(ads.getURL())
                .apply(RequestOptions.placeholderOf(R.drawable.generic_placeholder_gk_background))
                .into(holder.mImgVAdImage);

        holder.itemView.setTag(ads);
        holder.itemView.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GKAds ads = (GKAds) v.getTag();
            GKWhatsNewCommonDetailsActivity.start(mContext, ads);
        }
    };

    @Override
    public int getItemCount() {
        return mAds.size();
    }

    public class PromotionsViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvAdTitle;
        private TextView mTvAdDescription;
        private TextView mTvDate;
        private ImageView mImgVAdImage;

        public PromotionsViewHolder(View itemView) {
            super(itemView);
            mTvAdTitle = itemView.findViewById(R.id.tv_ad_title);
            mTvAdDescription = itemView.findViewById(R.id.tv_ad_description);
            mTvDate = itemView.findViewById(R.id.tv_date);
            mImgVAdImage = itemView.findViewById(R.id.imgV_ad_image);
        }
    }
}
