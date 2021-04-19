package com.goodkredit.myapplication.adapter.mdp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.activities.mdp.MDPBulletinActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.model.mdp.MDPBulletin;

import java.util.ArrayList;
import java.util.List;

public class MDPBulletinHeaderAdapter extends RecyclerView.Adapter<MDPBulletinHeaderAdapter.MyViewHolder>{

    private Context mContext;
    private List<MDPBulletin> mbulletin;
    private MDPBulletinActivity activity;
    private MDPBulletinContentAdapter madapter;
    private DatabaseHandler mdb;
    private String schoolid = "";

    public MDPBulletinHeaderAdapter(Context mContext, MDPBulletinActivity activity) {
        this.mContext = mContext;
        this.activity = activity;
        this.mbulletin = new ArrayList<>();
        this.mdb = new DatabaseHandler(mContext);
    }

    public void updateDataHeader(List<MDPBulletin> arraylist) {
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
    public MDPBulletinHeaderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mdp_bulletinheader, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MDPBulletinHeaderAdapter.MyViewHolder holder, int position) {
        try{
            MDPBulletin bulletin = mbulletin.get(position);

            String dayoftheweek = CommonFunctions.convertDateToDayoftheWeek(bulletin.getDateTimeIN());
            String datetime = CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(bulletin.getDateTimeIN()));

            holder.txv_header.setText(dayoftheweek.concat(" | ").concat(datetime));
            holder.rv_bulletin_content.setNestedScrollingEnabled(false);
            holder.rv_bulletin_content.setLayoutManager(new LinearLayoutManager(mContext));
            madapter = new MDPBulletinContentAdapter(mContext, activity);
            holder.rv_bulletin_content.setAdapter(madapter);

            String convertdate = CommonFunctions.convertDateWithoutTime(bulletin.getDateTimeIN());

            madapter.updateDataContent(mdb.getMDPBulletinGroupByDay(mdb, convertdate));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mbulletin.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txv_header;
        private RecyclerView rv_bulletin_content;

        public MyViewHolder(View itemView) {
            super(itemView);

            txv_header = (TextView) itemView.findViewById(R.id.txv_header);
            rv_bulletin_content = (RecyclerView) itemView.findViewById(R.id.rv_bulletin_content);
        }
    }
}

