package com.goodkredit.myapplication.adapter.schoolmini.bulletin;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniBulletinFragment;
import com.goodkredit.myapplication.model.GenericBulletin;

import java.util.ArrayList;
import java.util.List;

public class BulletinHeaderAdapter extends RecyclerView.Adapter<BulletinHeaderAdapter.MyViewHolder> {
    private Context mContext;
    private List<GenericBulletin> genericBulletinList;
    private SchoolMiniBulletinFragment fragment;
    private BulletinContentAdapter mAdapter;
    private DatabaseHandler mdb;
    private String schoolid = "";

    public BulletinHeaderAdapter(Context context, SchoolMiniBulletinFragment fm) {
        mContext = context;
        genericBulletinList = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
    }

    public void updateDataHeader(List<GenericBulletin> arraylist) {
        genericBulletinList.clear();
        genericBulletinList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        genericBulletinList.clear();
        notifyDataSetChanged();
    }

    @Override
    public BulletinHeaderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_schoolmini_bulletin_item_header, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(BulletinHeaderAdapter.MyViewHolder holder, int position) {
        try {
            GenericBulletin genericBulletin = genericBulletinList.get(position);

            String dayoftheweek = CommonFunctions.convertDateToDayoftheWeek(genericBulletin.getDateTimeIN());

            String datetime = CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(genericBulletin.getDateTimeIN()));

            holder.txv_header.setText(dayoftheweek + " | " + datetime);

            holder.rv_bulletin_content.setNestedScrollingEnabled(false);
            holder.rv_bulletin_content.setLayoutManager(new LinearLayoutManager(mContext));
            mAdapter = new BulletinContentAdapter(mContext, fragment);
            holder.rv_bulletin_content.setAdapter(mAdapter);

            schoolid = PreferenceUtils.getStringPreference(mContext, PreferenceUtils.KEY_SCSERVICECODE);

            String convertdate = CommonFunctions.convertDateWithoutTime(genericBulletin.getDateTimeIN());

            mAdapter.updateDataContent(mdb.getSchoolMiniBulletinGroupByDAY(mdb, convertdate));

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return genericBulletinList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
