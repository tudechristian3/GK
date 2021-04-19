package com.goodkredit.myapplication.adapter.gkearn;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.fragments.gkearn.GKEarnRegisterFragment;
import com.goodkredit.myapplication.model.gkearn.GKEarnReferralRandom;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GKEarnReferralRandomAdapter extends RecyclerView.Adapter<GKEarnReferralRandomAdapter.MyViewHolder> {

    private Context mContext;
    private List<GKEarnReferralRandom> gkEarnReferralRandomList;
    private GKEarnRegisterFragment fragment;

    public GKEarnReferralRandomAdapter(Context context) {
        mContext = context;
        gkEarnReferralRandomList = new ArrayList<>();
    }

    public GKEarnReferralRandomAdapter(Context context, GKEarnRegisterFragment fm) {
        mContext = context;
        gkEarnReferralRandomList = new ArrayList<>();
        fragment = fm;
    }

    public void updateData(List<GKEarnReferralRandom> arraylist) {
        gkEarnReferralRandomList.clear();
        gkEarnReferralRandomList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        gkEarnReferralRandomList.clear();
        notifyDataSetChanged();
    }

    @Override
    public GKEarnReferralRandomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gkearn_referral_random_dialog_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(GKEarnReferralRandomAdapter.MyViewHolder holder, int position) {
        try {
            GKEarnReferralRandom gKEarnReferralRandom = gkEarnReferralRandomList.get(position);

            holder.txv_referral.setText(gKEarnReferralRandom.getExtra3());

            holder.linear_referral_container.setTag(gKEarnReferralRandom);
            holder.linear_referral_container.setOnClickListener(referralListener);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener referralListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GKEarnReferralRandom gKEarnReferralRandom =  (GKEarnReferralRandom) v.getTag();

            fragment.referralRandomDialog.dismiss();
            fragment.edt_referredby.setText(gKEarnReferralRandom.getExtra3());
        }
    };

    @Override
    public int getItemCount() {
        return gkEarnReferralRandomList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txv_referral;
        private LinearLayout linear_referral_container;


        public MyViewHolder(View itemView) {
            super(itemView);

            txv_referral = (TextView) itemView.findViewById(R.id.txv_referral);
            linear_referral_container = itemView.findViewById(R.id.linear_referral_container);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
