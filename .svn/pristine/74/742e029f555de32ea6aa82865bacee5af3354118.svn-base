package com.goodkredit.myapplication.adapter.skycable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.bean.SkycableDictionary;
import com.goodkredit.myapplication.bean.SkycableServiceArea;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.skycable.SkycableNewApplicationPlansFragment;

import java.util.ArrayList;
import java.util.List;

public class SkycableNewApplicationPlansAdapter extends RecyclerView.Adapter<SkycableNewApplicationPlansAdapter.MyViewHolder> {

    private Context mContext;
    private List<SkycableDictionary> mGridData;
    private ArrayList<SkycableServiceArea> skycableServiceAreas;
    private SkycableNewApplicationPlansFragment mFragment;

    public SkycableNewApplicationPlansAdapter(Context context, SkycableNewApplicationPlansFragment fragment) {
        mContext = context;
        mGridData = new ArrayList<>();
        skycableServiceAreas = new ArrayList<>();
        mFragment = fragment;
    }

    public void setServiceArea(List<SkycableServiceArea> skycableServiceAreaList) {
        skycableServiceAreas.clear();
        skycableServiceAreas.addAll(skycableServiceAreaList);
    }

    public void update(List<SkycableDictionary> data) {
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
    public SkycableNewApplicationPlansAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_skycable_new_application_plans_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SkycableNewApplicationPlansAdapter.MyViewHolder holder, int position) {

        SkycableDictionary skycableDictionary = mGridData.get(position);

//        if (skycableDictionary.getPlanStyle().trim().length() > 0) {
//            holder.linearPlanItemLayout.setBackgroundColor(Color.parseColor(CommonFunctions.parseJSON(skycableDictionary.getPlanStyle(), "background-color")));
//            holder.txvName.setTextColor(Color.parseColor(CommonFunctions.parseJSON(skycableDictionary.getPlanStyle(), "color")));
//            holder.txvAmount.setTextColor(Color.parseColor(CommonFunctions.parseJSON(skycableDictionary.getPlanStyle(), "color")));
//        }
        holder.txvName.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", skycableDictionary.getPlanName()));
        holder.txvAmount.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", "₱" + CommonFunctions.currencyFormatter(String.valueOf(skycableDictionary.getMonthlyFee()))));

        double discount = (skycableDictionary.getMonthlyFee() * skycableDictionary.getDiscountPercentage()) + skycableDictionary.getDiscountBase();

        if (discount > 0) {
            holder.linearDiscountlayout.setVisibility(View.VISIBLE);
        } else {
            holder.linearDiscountlayout.setVisibility(View.GONE);
        }

        Glide.with(mContext)
                .asBitmap()
                .load(skycableDictionary.getPlanImgUrl())
                .apply(new RequestOptions())
                .into(new BaseTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.imgPlanItem.setImageBitmap(resource);
                    }

                    @Override
                    public void getSize(SizeReadyCallback cb) {
                        cb.onSizeReady(CommonFunctions.getScreenWidth(mContext), CommonFunctions.getScreenHeight(mContext));
                    }

                    @Override
                    public void removeCallback(SizeReadyCallback cb) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        holder.linearPlanItemLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorwhite));
                    }
                });

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txvName;
        private TextView txvAmount;
        private LinearLayout linearPlanItemLayout;
        //        private ImageView imgCentral;
        private LinearLayout linearDiscountlayout;
        private ImageView imgPlanItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            txvName = (TextView) itemView.findViewById(R.id.txvName);
            txvAmount = (TextView) itemView.findViewById(R.id.txvAmount);
            linearPlanItemLayout = (LinearLayout) itemView.findViewById(R.id.linearPlanItemLayout);
            linearPlanItemLayout.setOnClickListener(this);
//            imgCentral = (ImageView) itemView.findViewById(R.id.imgCentral);
            linearDiscountlayout = (LinearLayout) itemView.findViewById(R.id.linearDiscountlayout);
            imgPlanItem = (ImageView) itemView.findViewById(R.id.imgPlanItem);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position > -1) {

                SkycableDictionary skycableDictionary = mGridData.get(position);
                //SkycableServiceArea skycableServiceArea = skycableServiceAreas.get(0);

                switch (v.getId()) {
                    case R.id.linearPlanItemLayout: {

                        Bundle args = new Bundle();
                        args.putParcelableArrayList("SKYSERVICEAREA", skycableServiceAreas);
                        args.putParcelable("SKYDICTIONARY", skycableDictionary);
//                        args.putParcelable("SKYSERVICEAREA", skycableServiceArea);
                        ((SkyCableActivity) mContext).displayView(2, args);

                        break;
                    }
                }
            }
        }
    }
}
