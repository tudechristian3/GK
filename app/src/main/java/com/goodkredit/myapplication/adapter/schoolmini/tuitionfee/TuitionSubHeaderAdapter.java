package com.goodkredit.myapplication.adapter.schoolmini.tuitionfee;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniTuitionFragment;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniTuitionFee;

import java.util.ArrayList;
import java.util.List;

public class TuitionSubHeaderAdapter extends RecyclerView.Adapter<TuitionSubHeaderAdapter.MyViewHolder> {
    private Context mContext;
    private List<SchoolMiniTuitionFee> schoolMiniTuitionFeeList;
    private SchoolMiniTuitionFragment fragment;
    private TuitionPayNowAdapter mAdapter;
    private DatabaseHandler mdb;

    public TuitionSubHeaderAdapter(Context context, SchoolMiniTuitionFragment fm) {
        mContext = context;
        schoolMiniTuitionFeeList = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
    }

    public void setSemesterData(List<SchoolMiniTuitionFee> arrayList) {
        schoolMiniTuitionFeeList.clear();
        schoolMiniTuitionFeeList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public TuitionSubHeaderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.fragment_schoolmini_tuition_item_subheader, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(TuitionSubHeaderAdapter.MyViewHolder holder, int position) {
        try {
            SchoolMiniTuitionFee schoolminituitionfee = schoolMiniTuitionFeeList.get(position);

            if(schoolminituitionfee.getType().toUpperCase().trim().contains("COLLEGE") ||
                    schoolminituitionfee.getType().toUpperCase().contains("SENIOR HIGH")) {
                if(schoolminituitionfee.getSemester().equals(".")) {
                    holder.txv_subheader.setText("N/A");
                }
                else {
                    holder.txv_subheader.setText(schoolminituitionfee.getSemester());
                }
            } else {
                if(schoolminituitionfee.getExamTerm().equals(".")) {
                    holder.txv_subheader.setText("N/A");
                }
                else {
                    holder.txv_subheader.setText(schoolminituitionfee.getExamTerm());
                }
            }

            holder.rv_subheader_child.setNestedScrollingEnabled(false);
            holder.rv_subheader_child.setLayoutManager(new LinearLayoutManager(mContext));
            mAdapter = new TuitionPayNowAdapter(mContext, fragment);
            holder.rv_subheader_child.setAdapter(mAdapter);

            mAdapter.setSemestralFee(mdb.getSchoolMiniTuitionFeeChild(mdb, schoolminituitionfee.getStudentID(), schoolminituitionfee.getSchoolYear(), schoolminituitionfee.getSemester()));

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return schoolMiniTuitionFeeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //PARENT
        LinearLayout layout_subheader_parent_container;
        TextView txv_subheader;
        ImageView imv_arrow;
        //CHILD
        LinearLayout layout_subheader_child_container;
        RecyclerView rv_subheader_child;


        public MyViewHolder(View itemView) {
            super(itemView);
            //PARENT
            layout_subheader_parent_container = (LinearLayout) itemView.findViewById(R.id.layout_subheader_parent_container);
            txv_subheader = (TextView) itemView.findViewById(R.id.txv_subheader);
            imv_arrow = (ImageView) itemView.findViewById(R.id.imv_arrow);
            imv_arrow.setRotation(90);
            //CHILD
            layout_subheader_child_container = itemView.findViewById(R.id.layout_subheader_child_container);
            layout_subheader_parent_container.setOnClickListener(this);
            layout_subheader_child_container.setVisibility(View.VISIBLE);
            rv_subheader_child = itemView.findViewById(R.id.rv_subheader_child);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.layout_subheader_parent_container:
                    if (layout_subheader_child_container.getVisibility() == View.VISIBLE) {
                        layout_subheader_child_container.setVisibility(View.GONE);
                        imv_arrow.setRotation(360);
                    } else {
                        layout_subheader_child_container.setVisibility(View.VISIBLE);
                        imv_arrow.setRotation(90);
                    }
                    break;
            }
        }
    }
}
