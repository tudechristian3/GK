package com.goodkredit.myapplication.adapter.schoolmini.dtr;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniDtrFragment;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDtr;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniDtrContentAdapter extends RecyclerView.Adapter<SchoolMiniDtrContentAdapter.MyViewHolder> {
    private Context mContext;
    private List<SchoolMiniDtr> contentList;
    private SchoolMiniDtrFragment fragment;
    private DatabaseHandler mdb;

    public SchoolMiniDtrContentAdapter(Context context, SchoolMiniDtrFragment fm) {
        mContext = context;
        contentList = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
    }

    public void updateData(List<SchoolMiniDtr> arraylist) {
        contentList.clear();
        contentList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        contentList.clear();
        notifyDataSetChanged();
    }

    @Override
    public SchoolMiniDtrContentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_schoolmini_dtr_item_content, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(SchoolMiniDtrContentAdapter.MyViewHolder holder, int position) {
        try {
            SchoolMiniDtr schoolMiniDtr = contentList.get(position);

            String time = CommonFunctions.getTimeFromDateTime(CommonFunctions
                    .convertDate(schoolMiniDtr.getDateTimeIN()));

            holder.txv_datetime.setText(CommonFunctions.replaceEscapeData(time));

            String type = schoolMiniDtr.getType();

            if(type.trim().equalsIgnoreCase("Login")) {
                type = "Time-in";
            } else if(type.trim().equalsIgnoreCase("Logout")) {
                type = "Time-out";
            } else {
                type = schoolMiniDtr.getType();
            }

            holder.txv_datestatus.setText(CommonFunctions.replaceEscapeData(type));

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txv_datetime;
        private TextView txv_datestatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            txv_datetime = (TextView) itemView.findViewById(R.id.txv_datetime);
            txv_datestatus = (TextView) itemView.findViewById(R.id.txv_datestatus);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
