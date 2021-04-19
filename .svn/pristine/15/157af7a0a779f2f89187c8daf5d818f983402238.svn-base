package com.goodkredit.myapplication.adapter.coopassistant.bulletin;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.coopassistant.CoopAssistantEBulletinFragment;
import com.goodkredit.myapplication.model.GenericBulletin;
import com.goodkredit.myapplication.utilities.Logger;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;

public class CoopAssistantEBulletinContentAdapter extends RecyclerView.Adapter<CoopAssistantEBulletinContentAdapter.MyViewHolder> {
    private Context mContext;
    private List<GenericBulletin> coopAssistantEBulletinList;
    private CoopAssistantEBulletinFragment fragment;
    private CoopAssistantEBulletinContentAdapter mAdapter;
    private DatabaseHandler mdb;

    public CoopAssistantEBulletinContentAdapter(Context context, CoopAssistantEBulletinFragment fm) {
        mContext = context;
        coopAssistantEBulletinList = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
    }

    public void updateDataContent(List<GenericBulletin> arraylist) {
        coopAssistantEBulletinList.clear();
        coopAssistantEBulletinList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        coopAssistantEBulletinList.clear();
        notifyDataSetChanged();
    }

    @Override
    public CoopAssistantEBulletinContentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coopassistant_bulletin_item_content, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(final CoopAssistantEBulletinContentAdapter.MyViewHolder holder, int position) {
        try {
            GenericBulletin genericBulletin = coopAssistantEBulletinList.get(position);

            holder.txv_title.setText(CommonFunctions.replaceEscapeData(genericBulletin.getTitle()));

            String periodstart = CommonFunctions.getDateFromDateTimeDDMMYYFormat(CommonFunctions.convertDateDDMMYYFormat(genericBulletin.getPeriodStart()));
            String periodend = CommonFunctions.getDateFromDateTimeDDMMYYFormat(CommonFunctions.convertDateDDMMYYFormat(genericBulletin.getPeriodEnd()));
            if(periodstart.trim().equals("") || periodstart.trim().equals(".") ||
                    periodend.trim().equals("") || periodend.trim().equals(".") ||
                    periodstart.trim().equals("01/01/1970") || periodend.trim().equals("01/01/1970")
            )
            {
                holder.txv_time.setVisibility(View.GONE);
            }
            else {
                holder.txv_time.setVisibility(View.VISIBLE);
                holder.txv_time.setText("Event Period: " + periodstart + " - " + periodend);
            }

            holder.txv_description.setExpandInterpolator(new OvershootInterpolator());
            holder.txv_description.setCollapseInterpolator(new OvershootInterpolator());

            holder.txv_description.setText(CommonFunctions.replaceEscapeData(genericBulletin.getDescription()));


            String pdfLink = genericBulletin.getNotes1();
            if(pdfLink.equals(".") || pdfLink.isEmpty() || pdfLink == null){
                holder.txv_link.setVisibility(View.GONE);
            }else{
                StringBuilder pdfContent = new StringBuilder();
                List<String> links = new ArrayList<>();
                int count = Integer.parseInt( CommonFunctions.parseXML(pdfLink,"count"));
                for(int i=1;i<=count;i++){
                    pdfContent.append("Link").append(i).append(": ").append(CommonFunctions.parseXML(pdfLink, "link" + i)).append("\n\n");
                }
                holder.txv_link.setVisibility(View.VISIBLE);
                holder.txv_link.setMovementMethod(LinkMovementMethod.getInstance());
                holder.txv_link.setHighlightColor(Color.BLUE);
                holder.txv_link.setText(pdfContent.toString());
            }

            String logo = genericBulletin.getImageURL();

            if(logo.trim().equals("") || logo.trim().equals(".")) {
                holder.imv_bulletin_logo.setVisibility(View.GONE);
            }
            else {
                holder.imv_bulletin_logo.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(genericBulletin.getImageURL())
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


            holder.layout_bulletin_container.setOnClickListener(bulletinlistener);
            holder.layout_bulletin_container.setTag(holder);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener bulletinlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final CoopAssistantEBulletinContentAdapter.MyViewHolder holder = (MyViewHolder) v.getTag();

                GenericBulletin genericBulletin = coopAssistantEBulletinList.get(holder.getAdapterPosition());

                String xmlredirectionlink = genericBulletin.getNotes1();

                //CHECKS IF EMPTY OR .
                if(!xmlredirectionlink.trim().equals("") && !xmlredirectionlink.trim().equals(".")) {
                    String redirectionlink = CommonFunctions.parseXML(xmlredirectionlink,"redirectionlink");
                    if(!redirectionlink.trim().equals("") && !redirectionlink.trim().equals(".")) {
                        Bundle args = new Bundle();
                        args.putString("redirectionlink",redirectionlink);
                        //SchoolMiniBulletinWebView.start(mContext, args);
                    }
                }

            } catch(Exception e ) {
                e.printStackTrace();
            }
        }
    };



    @Override
    public int getItemCount() {
        return coopAssistantEBulletinList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txv_title;
        private TextView txv_time;

        private ExpandableTextView txv_description;
        private TextView btnshow,txv_link;
        private ImageView imv_bulletin_logo;

        private LinearLayout layout_bulletin_container;

        public MyViewHolder(View itemView) {
            super(itemView);
            txv_title = itemView.findViewById(R.id.txv_title);
            txv_time = itemView.findViewById(R.id.txv_time);

            txv_description = itemView.findViewById(R.id.txv_description);
            btnshow = itemView.findViewById(R.id.btnshow);
            btnshow.setOnClickListener(this);
            imv_bulletin_logo = itemView.findViewById(R.id.imv_bulletin_logo);
            txv_link = itemView.findViewById(R.id.txv_link);

            layout_bulletin_container = itemView.findViewById(R.id.layout_bulletin_container);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnshow:
                    if (txv_description.isExpanded()) {
                        txv_description.collapse();
                        btnshow.setText("Show More...");
                        txv_description.requestFocus();
                    } else {
                        txv_description.expand();
                        btnshow.setText("Show Less...");
                        txv_description.requestFocus();
                    }
                    break;
            }
        }
    }
}
