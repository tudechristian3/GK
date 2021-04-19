package com.goodkredit.myapplication.adapter.sponsor;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.PublicSponsorPromos;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

public class PublicSponsorPromoRecyclerAdapter extends RecyclerView.Adapter<PublicSponsorPromoRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<PublicSponsorPromos> mGridData;

    public PublicSponsorPromoRecyclerAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    public void update(List<PublicSponsorPromos> data) {
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

    @Override
    public PublicSponsorPromoRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recyclearview_sponsorpromo_item, parent, false);
        return new PublicSponsorPromoRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PublicSponsorPromoRecyclerAdapter.MyViewHolder holder, int position) {
        PublicSponsorPromos psPromos = mGridData.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(psPromos.getPSPromoImage())
                .apply(new RequestOptions()
                        .fitCenter())
                .into(new BaseTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.promoImage.setImageBitmap(resource);
                    }

                    @Override
                    public void getSize(SizeReadyCallback cb) {
                        cb.onSizeReady(CommonFunctions.getScreenWidthPixel(mContext), 150);
                    }

                    @Override
                    public void removeCallback(SizeReadyCallback cb) {

                    }
                });
        holder.promotitle.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Medium.ttf", CommonFunctions.replaceEscapeData(psPromos.getPSPromoTitle())));
        holder.promodescription.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", CommonFunctions.replaceEscapeData(psPromos.getPSPromoDescription())));


    }



    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView promoImage;
        private TextView promotitle;
        private TextView promodescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            promoImage = (ImageView) itemView.findViewById(R.id.promoImage);
            promotitle = (TextView) itemView.findViewById(R.id.promotitle);
            promodescription = (TextView) itemView.findViewById(R.id.promodescription);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
