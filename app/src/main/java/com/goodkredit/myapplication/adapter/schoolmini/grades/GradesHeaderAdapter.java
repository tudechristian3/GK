package com.goodkredit.myapplication.adapter.schoolmini.grades;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniGradesFragment;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniGrades;

import java.util.ArrayList;
import java.util.List;


public class GradesHeaderAdapter extends RecyclerView.Adapter<GradesHeaderAdapter.MyViewHolder> {
    private Context mContext;
    private List<SchoolMiniGrades> schoolMiniGradesList;
    private SchoolMiniGradesFragment fragment;
    private GradesSubHeaderAdapter mAdapter;
    private DatabaseHandler mdb;


    public GradesHeaderAdapter(Context context, SchoolMiniGradesFragment fm) {
        mContext = context;
        schoolMiniGradesList = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
    }

    public void setSchoolGradesYearData(List<SchoolMiniGrades> arraylist) {
        schoolMiniGradesList.clear();
        schoolMiniGradesList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        schoolMiniGradesList.clear();
        notifyDataSetChanged();
    }

    @Override
    public GradesHeaderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_schoolmini_grades_item_header, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(GradesHeaderAdapter.MyViewHolder holder, int position) {
        try {
            SchoolMiniGrades schoolMiniGrades = schoolMiniGradesList.get(position);

            holder.txv_header.setText(schoolMiniGrades.getSchoolYear());

            holder.rv_gradersubheader.setNestedScrollingEnabled(false);
            holder.rv_gradersubheader.setLayoutManager(new LinearLayoutManager(mContext));
            mAdapter = new GradesSubHeaderAdapter(mContext, fragment);
            holder.rv_gradersubheader.setAdapter(mAdapter);

            if(schoolMiniGrades.getType().toUpperCase().trim().contains("COLLEGE") ||
                    schoolMiniGrades.getType().toUpperCase().contains("SENIOR HIGH")) {
                mAdapter.setGradesSemesterData(mdb.getSchoolMiniGradesGroupBySemester(mdb,
                        schoolMiniGrades.getStudentID(), schoolMiniGrades.getSchoolYear()));
            } else {
                mAdapter.setGradesSemesterData(mdb.getSchoolMiniGradesGroupByExamTerm(mdb,
                        schoolMiniGrades.getStudentID(), schoolMiniGrades.getSchoolYear()));
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return schoolMiniGradesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private TextView txv_header;
        private RecyclerView rv_gradersubheader;

        public MyViewHolder(View itemView) {
            super(itemView);
            txv_header = (TextView) itemView.findViewById(R.id.txv_header);
            rv_gradersubheader = (RecyclerView) itemView.findViewById(R.id.rv_gradersubheader);
        }

        @Override
        public void onClick(View view) {

        }
    }
}