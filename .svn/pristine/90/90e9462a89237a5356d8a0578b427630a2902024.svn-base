package com.goodkredit.myapplication.adapter.schoolmini;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniStudents;

import java.util.ArrayList;

public class MultipleStudentsAdapter extends RecyclerView.Adapter<MultipleStudentsAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<SchoolMiniStudents> schoolMiniStudentsList;
    private String typeoffragment;


    public MultipleStudentsAdapter(Context context, String type) {
        mContext = context;
        schoolMiniStudentsList = new ArrayList<>();
        typeoffragment = type;

    }

    public void updateSchoolData(ArrayList<SchoolMiniStudents> arraylist) {
        schoolMiniStudentsList.clear();
        schoolMiniStudentsList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        schoolMiniStudentsList.clear();
        notifyDataSetChanged();
    }

    @Override
    public MultipleStudentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_schoolmini_multiple_students_item, parent, false);
        MultipleStudentsAdapter.MyViewHolder holder = new MultipleStudentsAdapter.MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(MultipleStudentsAdapter.MyViewHolder holder, int position) {
        try {
            SchoolMiniStudents schoolministudents = schoolMiniStudentsList.get(position);

            holder.txv_no.setText("Student ID: " + schoolministudents.getStudentID());

            holder.txv_name.setText(schoolministudents.getFirstName() + " " +
                    schoolministudents.getMiddleName() + " " + schoolministudents.getLastName());

            String course = schoolministudents.getCourse();
            if(course.trim().equals(".")) {
                holder.txv_courseyrlvl.setText(schoolministudents.getYearLevel());
            }
            else {
                holder.txv_courseyrlvl.setText(schoolministudents.getCourse() + " " + schoolministudents.getYearLevel());
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return schoolMiniStudentsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private RelativeLayout schoolmini_item_container;
        private TextView txv_no;
        private TextView txv_name;
        private TextView txv_courseyrlvl;

        public MyViewHolder(View itemView) {
            super(itemView);
            schoolmini_item_container = (RelativeLayout) itemView.findViewById(R.id.schoolmini_item_container);
            schoolmini_item_container.setOnClickListener(this);
            txv_no = (TextView) itemView.findViewById(R.id.txv_no);
            txv_name = (TextView) itemView.findViewById(R.id.txv_name);
            txv_courseyrlvl = (TextView) itemView.findViewById(R.id.txv_courseyrlvl);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position > -1) {

                SchoolMiniStudents schoolministudents = schoolMiniStudentsList.get(position);

                switch (view.getId()) {
                    case R.id.schoolmini_item_container: {
                        if(typeoffragment.trim().equals("Grades")) {
                            Bundle args = new Bundle();
                            args.putString("STUDENTID", schoolministudents.getStudentID());
                            args.putString("YEARLEVEL", schoolministudents.getYearLevel());
                            args.putString("COURSE", schoolministudents.getCourse());
                            args.putString("FIRSTNAME", schoolministudents.getFirstName());
                            args.putString("MIDDLENAME", schoolministudents.getMiddleName());
                            args.putString("LASTNAME", schoolministudents.getLastName());
                            args.putString("MOBILENUMBER", schoolministudents.getMobileNumber());
                            args.putString("EMAILADDRESS", schoolministudents.getEmailAddress());
                            SchoolMiniActivity.start(mContext, 1, args);
                            break;
                        }
                        else if (typeoffragment.trim().equals("Tuition")) {
                            Bundle args = new Bundle();
                            args.putString("STUDENTID", schoolministudents.getStudentID());
                            args.putString("YEARLEVEL", schoolministudents.getYearLevel());
                            args.putString("COURSE", schoolministudents.getCourse());
                            args.putString("FIRSTNAME", schoolministudents.getFirstName());
                            args.putString("MIDDLENAME", schoolministudents.getMiddleName());
                            args.putString("LASTNAME", schoolministudents.getLastName());
                            args.putString("MOBILENUMBER", schoolministudents.getMobileNumber());
                            args.putString("EMAILADDRESS", schoolministudents.getEmailAddress());
                            SchoolMiniActivity.start(mContext, 3, args);
                            break;
                        }

                        else if (typeoffragment.trim().equals("Profile")) {
                            Bundle args = new Bundle();
                            args.putString("STUDENTID", schoolministudents.getStudentID());
                            args.putString("YEARLEVEL", schoolministudents.getYearLevel());
                            args.putString("COURSE", schoolministudents.getCourse());
                            args.putString("FIRSTNAME", schoolministudents.getFirstName());
                            args.putString("MIDDLENAME", schoolministudents.getMiddleName());
                            args.putString("LASTNAME", schoolministudents.getLastName());
                            args.putString("MOBILENUMBER", schoolministudents.getMobileNumber());
                            args.putString("EMAILADDRESS", schoolministudents.getEmailAddress());
                            args.putString("ADDRESS", schoolministudents.getStreetAddress() + ", "
                                    + schoolministudents.getCity() + ", "
                                    + schoolministudents.getProvince() + ", "
                                    + schoolministudents.getCountry());
                            SchoolMiniActivity.start(mContext, 5, args);
                        }

                        else if (typeoffragment.trim().equals("Dtr")) {
                            Bundle args = new Bundle();
                            args.putString("STUDENTID", schoolministudents.getStudentID());
                            args.putString("YEARLEVEL", schoolministudents.getYearLevel());
                            args.putString("COURSE", schoolministudents.getCourse());
                            args.putString("FIRSTNAME", schoolministudents.getFirstName());
                            args.putString("MIDDLENAME", schoolministudents.getMiddleName());
                            args.putString("LASTNAME", schoolministudents.getLastName());
                            args.putString("MOBILENUMBER", schoolministudents.getMobileNumber());
                            args.putString("EMAILADDRESS", schoolministudents.getEmailAddress());
                            args.putString("DATETIMEUPLOAD", schoolministudents.getDateTimeUploaded());
                            SchoolMiniActivity.start(mContext, 12, args);
                            break;
                        } else {
                            ((BaseActivity)mContext).showToast("Something went wrong. " +
                                    "Please try again", GlobalToastEnum.WARNING);
                            break;
                        }
                    }
                }

            }

        }
    }
}
