package com.goodkredit.myapplication.adapter.schoolmini.grades;

import android.content.Context;
import androidx.core.content.ContextCompat;
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
import com.goodkredit.myapplication.decoration.DividerItemDecoration;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniGradesFragment;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniGrades;

import java.util.ArrayList;
import java.util.List;

public class GradesSubHeaderAdapter extends RecyclerView.Adapter<GradesSubHeaderAdapter.MyViewHolder> {
    private Context mContext;
    private List<SchoolMiniGrades> schoolMiniGradesList;
    private SchoolMiniGradesFragment fragment;
    private GradesSubjectChildAdapter mGradesAdapter;
    private GradesExamTermChildAdapter mExamTermAdapter;
    private DatabaseHandler mdb;

    public GradesSubHeaderAdapter(Context context, SchoolMiniGradesFragment fm) {
        mContext = context;
        schoolMiniGradesList = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
    }

    public void setGradesSemesterData(List<SchoolMiniGrades> arrayList) {
        schoolMiniGradesList.clear();
        schoolMiniGradesList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public GradesSubHeaderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.fragment_schoolmini_grades_item_subheader, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(GradesSubHeaderAdapter.MyViewHolder holder, int position) {
        try {
            SchoolMiniGrades schoolMiniGrades = schoolMiniGradesList.get(position);

            if(schoolMiniGrades.getType().toUpperCase().trim().contains("COLLEGE")
                || schoolMiniGrades.getType().toUpperCase().contains("SENIOR HIGH")) {

                if(schoolMiniGrades.getSemester().equals(".")) {
                    holder.txv_subheader.setText("N/A");
                } else {
                    holder.txv_subheader.setText(schoolMiniGrades.getSemester());
                }

                holder.rv_subheader_child.setNestedScrollingEnabled(false);
                holder.rv_subheader_child.setLayoutManager(new LinearLayoutManager(mContext));
                mExamTermAdapter = new GradesExamTermChildAdapter(mContext, fragment);
                holder.rv_subheader_child.setAdapter(mExamTermAdapter);

                mExamTermAdapter.setGradesExamTermData(mdb.getSchoolMiniGradesBySemesterGroupByExamTermChild(mdb,
                        schoolMiniGrades.getStudentID(),
                        schoolMiniGrades.getSchoolYear(), schoolMiniGrades.getSemester()));

            } else {
                if(schoolMiniGrades.getExamTerm().equals(".")) {
                    holder.txv_subheader.setText("N/A");
                } else {
                    holder.txv_subheader.setText(schoolMiniGrades.getExamTerm());
                }

                holder.rv_subheader_child.setNestedScrollingEnabled(false);
                holder.rv_subheader_child.setLayoutManager(new LinearLayoutManager(mContext));
                holder.rv_subheader_child.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider)));
                mGradesAdapter = new GradesSubjectChildAdapter(mContext, fragment);
                holder.rv_subheader_child.setAdapter(mGradesAdapter);

                mGradesAdapter.setSubjectGrade(mdb.getSchoolMiniGradesByExamTermChild(mdb,
                        schoolMiniGrades.getStudentID(),
                        schoolMiniGrades.getSchoolYear(), schoolMiniGrades.getExamTerm()));
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return schoolMiniGradesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //PARENT
        LinearLayout layout_subheader_parent_container;
        TextView txv_subheader;
        ImageView imv_arrow;
        //CHILD
        LinearLayout layout_subheader_child_container;
        RecyclerView rv_subheader_child;


        public MyViewHolder(View itemView)  {
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
