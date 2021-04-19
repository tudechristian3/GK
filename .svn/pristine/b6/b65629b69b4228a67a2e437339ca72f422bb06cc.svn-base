package com.goodkredit.myapplication.adapter.coopassistant.bulletin;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.coopassistant.CoopAssistantEBulletinFragment;
import com.goodkredit.myapplication.model.GenericBulletin;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.utilities.Logger;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantEBulletinHeaderAdapter extends RecyclerView.Adapter<CoopAssistantEBulletinHeaderAdapter.MyViewHolder> {
    private Context mContext;
    private List<GenericBulletin> coopAssistantEBulletinList;
    private CoopAssistantEBulletinFragment fragment;
    private CoopAssistantEBulletinContentAdapter mAdapter;
    private DatabaseHandler mdb;

    //MEMBER LIST
    private List<CoopAssistantMembers> coopMembersList;

    public CoopAssistantEBulletinHeaderAdapter(Context context, CoopAssistantEBulletinFragment fm, List<CoopAssistantMembers> memberlist) {
        mContext = context;
        coopAssistantEBulletinList = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
        coopMembersList = memberlist;
    }

    public void updateData(List<GenericBulletin> arraylist) {
        Logger.debug("vanhttp", "Adapter ====== " + new Gson().toJson(arraylist));
        coopAssistantEBulletinList.clear();
        coopAssistantEBulletinList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        coopAssistantEBulletinList.clear();
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public CoopAssistantEBulletinHeaderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coopassistant_ebulletin_item_header, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NotNull CoopAssistantEBulletinHeaderAdapter.MyViewHolder holder, int position) {
        try {
            GenericBulletin genericBulletin = coopAssistantEBulletinList.get(position);

            String dayoftheweek = CommonFunctions.convertDateToDayoftheWeek(genericBulletin.getDateTimeIN());

            String datetime = CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(genericBulletin.getDateTimeIN()));

            holder.txv_header.setText(dayoftheweek + " | " + datetime);

            holder.rv_bulletin_content.setNestedScrollingEnabled(false);
            holder.rv_bulletin_content.setLayoutManager(new LinearLayoutManager(mContext));
            mAdapter = new CoopAssistantEBulletinContentAdapter(mContext, fragment);
            holder.rv_bulletin_content.setAdapter(mAdapter);

            String convertdate = CommonFunctions.convertDateWithoutTime(genericBulletin.getDateTimeIN());

            if(!coopMembersList.isEmpty()){
                mAdapter.updateDataContent(mdb.getCoopAssistantMiniBulletinGroupByDAY(mdb, convertdate));
            } else{
                Logger.debug("vanhttp", "mAdapter.updateDataContent: ====== " + new Gson().toJson(mdb.getCoopAssistantMiniBulletinGroupByDAYPublic(mdb, convertdate)));
                mAdapter.updateDataContent(mdb.getCoopAssistantMiniBulletinGroupByDAYPublic(mdb, convertdate));
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return coopAssistantEBulletinList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txv_header;
        private RecyclerView rv_bulletin_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            txv_header = (TextView) itemView.findViewById(R.id.txv_header);
            rv_bulletin_content = (RecyclerView) itemView.findViewById(R.id.rv_bulletin_content);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
