package com.goodkredit.myapplication.adapter.schoolmini.grades;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniGradesFragment;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniGrades;

import java.util.ArrayList;
import java.util.List;

public class GradesSubjectChildAdapter extends RecyclerView.Adapter<GradesSubjectChildAdapter.MyViewHolder> {
    private Context mContext;
    private List<SchoolMiniGrades> schoolMiniGradesList;
    private SchoolMiniGradesFragment fragment;
    private DatabaseHandler mdb;

    public GradesSubjectChildAdapter(Context context, SchoolMiniGradesFragment fm) {
        mContext = context;
        schoolMiniGradesList = new ArrayList<>();
        fragment = fm;
    }

    public void setSubjectGrade(List<SchoolMiniGrades> arrayList) {
        schoolMiniGradesList.clear();
        schoolMiniGradesList = arrayList;
        notifyDataSetChanged();
    }


    @Override
    public GradesSubjectChildAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.fragment_schoolmini_grades_item_subject, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(GradesSubjectChildAdapter.MyViewHolder holder, int position) {
        try {
            SchoolMiniGrades schoolMiniGrades = schoolMiniGradesList.get(position);
            holder.txv_subject.setText(schoolMiniGrades.getSubjectDescription());

            if(schoolMiniGrades.getType().toUpperCase().trim().equals("COLLEGE")) {
                if(CommonFunctions.stringContainsNumberDecimal(schoolMiniGrades.getStudentGrade())) {
                    holder.txv_student_grade.setText(CommonFunctions.singleDigitFormatter(schoolMiniGrades.getStudentGrade()));
                } else {
                    holder.txv_student_grade.setText(schoolMiniGrades.getStudentGrade());
                }
            }
            else {
                holder.txv_student_grade.setText(schoolMiniGrades.getStudentGrade());
            }

            holder.txv_student_examterm.setText(schoolMiniGrades.getExamTerm());

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return schoolMiniGradesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txv_subject;
        TextView txv_student_grade;
        TextView txv_student_examterm;

        public MyViewHolder(View itemView) {
            super(itemView);
            txv_subject = itemView.findViewById(R.id.txv_subject);
            txv_student_grade = itemView.findViewById(R.id.txv_student_grade);
            txv_student_examterm = itemView.findViewById(R.id.txv_student_examterm);
        }
    }
}
