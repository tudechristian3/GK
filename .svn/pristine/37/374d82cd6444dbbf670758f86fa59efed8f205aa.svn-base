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

public class GradesExamTermChildAdapter extends RecyclerView.Adapter<GradesExamTermChildAdapter.MyViewHolder> {
    private Context mContext;
    private List<SchoolMiniGrades> schoolMiniGradesList;
    private SchoolMiniGradesFragment fragment;
    private GradesSubjectChildAdapter mAdapter;
    private DatabaseHandler mdb;

    public GradesExamTermChildAdapter(Context context, SchoolMiniGradesFragment fm) {
        mContext = context;
        schoolMiniGradesList = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
    }

    public void setGradesExamTermData(List<SchoolMiniGrades> arrayList) {
        schoolMiniGradesList.clear();
        schoolMiniGradesList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public GradesExamTermChildAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.fragment_schoolmini_grades_item_examterm, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(GradesExamTermChildAdapter.MyViewHolder holder, int position) {
        try {
            SchoolMiniGrades schoolMiniGrades = schoolMiniGradesList.get(position);
            holder.txv_subheader.setText(schoolMiniGrades.getExamTerm());

            holder.rv_subheader_child.setNestedScrollingEnabled(false);
            holder.rv_subheader_child.setLayoutManager(new LinearLayoutManager(mContext));
            holder.rv_subheader_child.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider)));
            mAdapter = new GradesSubjectChildAdapter(mContext, fragment);
            holder.rv_subheader_child.setAdapter(mAdapter);
            mAdapter.setSubjectGrade(mdb.getSchoolMiniGradesBySemesterandExamTermChild(mdb, schoolMiniGrades.getStudentID(), schoolMiniGrades.getSchoolYear(), schoolMiniGrades.getSemester(), schoolMiniGrades.getExamTerm()));

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
            //CHILD
            layout_subheader_child_container = itemView.findViewById(R.id.layout_subheader_child_container);
            rv_subheader_child = itemView.findViewById(R.id.rv_subheader_child);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
