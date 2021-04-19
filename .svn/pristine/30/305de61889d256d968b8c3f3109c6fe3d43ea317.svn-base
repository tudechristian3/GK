package com.goodkredit.myapplication.adapter.schoolmini.tuitionfee;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniTuitionFragment;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniTuitionFee;

import java.util.ArrayList;
import java.util.List;

public class TuitionHeaderAdapter extends RecyclerView.Adapter<TuitionHeaderAdapter.MyViewHolder> {
    private Context mContext;
    private List<SchoolMiniTuitionFee> schoolMiniTuitionFeeList;
    private SchoolMiniTuitionFragment fragment;
    private TuitionSubHeaderAdapter mAdapter;
    private DatabaseHandler mdb;


    public TuitionHeaderAdapter(Context context, SchoolMiniTuitionFragment fm) {
        mContext = context;
        schoolMiniTuitionFeeList = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
    }

    public void setSchoolTuitionYearData(List<SchoolMiniTuitionFee> arraylist) {
        schoolMiniTuitionFeeList.clear();
        schoolMiniTuitionFeeList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        schoolMiniTuitionFeeList.clear();
        notifyDataSetChanged();
    }

    @Override
    public TuitionHeaderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_schoolmini_tuition_item_header, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(TuitionHeaderAdapter.MyViewHolder holder, int position) {
        try {
            SchoolMiniTuitionFee schoolminituitionfee = schoolMiniTuitionFeeList.get(position);

            holder.txv_header.setText(schoolminituitionfee.getSchoolYear());

            if(schoolminituitionfee.getType().toUpperCase().trim().contains("COLLEGE")
                || schoolminituitionfee.getType().toUpperCase().contains("SENIOR HIGH")) {
                holder.rv_tuitionsubheader.setNestedScrollingEnabled(false);
                holder.rv_tuitionsubheader.setLayoutManager(new LinearLayoutManager(mContext));
                mAdapter = new TuitionSubHeaderAdapter(mContext, fragment);
                holder.rv_tuitionsubheader.setAdapter(mAdapter);

                mAdapter.setSemesterData(mdb.getSchoolMiniTuitionFeeGroupBySemester(mdb, schoolminituitionfee.getStudentID(),
                        schoolminituitionfee.getSchoolYear()));
            } else {
                holder.rv_tuitionsubheader.setNestedScrollingEnabled(false);
                holder.rv_tuitionsubheader.setLayoutManager(new LinearLayoutManager(mContext));
                mAdapter = new TuitionSubHeaderAdapter(mContext, fragment);
                holder.rv_tuitionsubheader.setAdapter(mAdapter);

                mAdapter.setSemesterData(mdb.getSchoolMiniTuitionFeeGroupByExamTerm(mdb, schoolminituitionfee.getStudentID(),
                        schoolminituitionfee.getSchoolYear()));
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return schoolMiniTuitionFeeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private TextView txv_header;
        private RecyclerView rv_tuitionsubheader;

        public MyViewHolder(View itemView) {
            super(itemView);
            txv_header = (TextView) itemView.findViewById(R.id.txv_header);
            rv_tuitionsubheader = (RecyclerView) itemView.findViewById(R.id.rv_tuitionsubheader);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
