package com.goodkredit.myapplication.adapter.mdp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.activities.mdp.MDPBulletinActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.model.mdp.MDPBulletin;

import java.util.ArrayList;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;

public class MDPBulletinContentAdapter extends RecyclerView.Adapter<MDPBulletinContentAdapter.MyViewHolder>{

    private Context mContext;
    private List<MDPBulletin> mbulletin;
    private MDPBulletinActivity activity;
    private DatabaseHandler mdb;

    public MDPBulletinContentAdapter(Context mContext, MDPBulletinActivity activity) {
        this.mContext = mContext;
        this.activity = activity;
        this.mbulletin = new ArrayList<>();
        mdb = new DatabaseHandler(mContext);
    }

    public void updateDataContent(List<MDPBulletin> arraylist) {
        mbulletin.clear();
        mbulletin = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        mbulletin.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MDPBulletinContentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mdp_bulletincontent, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MDPBulletinContentAdapter.MyViewHolder holder, int position) {

        try{
            MDPBulletin bulletin = mbulletin.get(position);

            holder.txv_title.setText(bulletin.getTitle());

            String periodstart = CommonFunctions.getDateFromDateTimeDDMMYYFormat(CommonFunctions.convertDateDDMMYYFormat(bulletin.getPeriodStart()));
            String peridodend = CommonFunctions.getDateFromDateTimeDDMMYYFormat(CommonFunctions.convertDateDDMMYYFormat(bulletin.getPeriodEnd()));


            holder.txv_time.setText("Event Period: " + periodstart + " - " + peridodend);
            holder.txv_description.setAnimationDuration(750L);

            holder.txv_description.setInterpolator(new OvershootInterpolator());

            holder.txv_description.setText(CommonFunctions.replaceEscapeData(bulletin.getDescription()));

            if(bulletin.getImageURL().isEmpty()
                    || bulletin.getImageURL().equals(".")){

                holder.imv_bulletin_logo.setVisibility(View.GONE);
            } else{
                holder.imv_bulletin_logo.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(bulletin.getImageURL())
                        .apply(new RequestOptions()
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        )
                        .into(holder.imv_bulletin_logo);
            }

            holder.txv_description.post(new Runnable() {
                @Override
                public void run() {
                    int lines = holder.txv_description.getLineCount();
                    if (lines >= 5) {
                        holder.btnshow.setVisibility(View.VISIBLE);
                    } else {
                        holder.btnshow.setVisibility(View.GONE);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return mbulletin.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txv_title;
        private TextView txv_time;

        private ExpandableTextView txv_description;
        private TextView btnshow;
        private ImageView imv_bulletin_logo;

        public MyViewHolder(View itemView) {
            super(itemView);

            txv_title = (TextView) itemView.findViewById(R.id.txv_title);
            txv_time = (TextView) itemView.findViewById(R.id.txv_time);

            txv_description = (ExpandableTextView) itemView.findViewById(R.id.txv_description);
            btnshow = (TextView) itemView.findViewById(R.id.btnshow);
            btnshow.setOnClickListener(this);
            imv_bulletin_logo = (ImageView) itemView.findViewById(R.id.imv_bulletin_logo);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnshow:
                    if (txv_description.isExpanded()) {
                        btnshow.setText("Show More...");
                        btnshow.requestFocus();
                        txv_description.collapse();
                    } else {
                        txv_description.expand();
                        btnshow.setText("Show Less...");
                        btnshow.requestFocus();
                    }
                    break;
            }
        }
    }
}

