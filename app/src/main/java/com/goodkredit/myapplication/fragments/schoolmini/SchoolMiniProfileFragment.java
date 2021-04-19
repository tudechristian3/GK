package com.goodkredit.myapplication.fragments.schoolmini;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;


public class SchoolMiniProfileFragment extends BaseFragment {

    private String session = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //SCHOOL
    private String schoolid = "";
    private String studentid = "";
    private String course = "";
    private String yearlevel = "";
    private String firstname = "";
    private String middlename = "";
    private String lastname = "";
    private String mobilenumber = "";
    private String emailaddress = "";
    private String address = "";

    //STUDENT INFORMATION
    private TextView txv_id;
    private TextView txv_name;
    private LinearLayout txv_course_container;
    private TextView txv_course;
    private TextView txv_yearlvl;
    private TextView txv_address;
    private TextView txv_mobilenumber;
    private TextView txv_emailaddress;

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmini_profile, container, false);
        try {
            init(view);

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        txv_id = (TextView) view.findViewById(R.id.txv_id);
        txv_name = (TextView) view.findViewById(R.id.txv_name);
        txv_course_container = (LinearLayout) view.findViewById(R.id.txv_course_container);
        txv_course = (TextView) view.findViewById(R.id.txv_course);
        txv_yearlvl = (TextView) view.findViewById(R.id.txv_yearlvl);
        txv_address = (TextView) view.findViewById(R.id.txv_address);
        txv_mobilenumber = (TextView) view.findViewById(R.id.txv_mobilenumber);
        txv_emailaddress = (TextView) view.findViewById(R.id.txv_emailaddress);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        mdb = new DatabaseHandler(getViewContext());

        schoolid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCSERVICECODE);
        if (getArguments() != null) {
            studentid = getArguments().getString("STUDENTID");
            course = getArguments().getString("COURSE");
            yearlevel = getArguments().getString("YEARLEVEL");
            firstname = getArguments().getString("FIRSTNAME");
            middlename = getArguments().getString("MIDDLENAME");
            lastname = getArguments().getString("LASTNAME");
            mobilenumber = getArguments().getString("MOBILENUMBER");
            emailaddress = getArguments().getString("EMAILADDRESS");
            address = getArguments().getString("ADDRESS");
        }

        //DISPLAYS STUDENT INFORMATION
        setStudentInformation();
    }

    private void setStudentInformation() {
        txv_id.setText(studentid);
        txv_name.setText(firstname + " " + middlename + " " + lastname);

        if(course.trim().equals(".") || course.trim().equals(".")) {
            txv_course_container.setVisibility(View.GONE);
        }
        else {
            txv_course_container.setVisibility(View.VISIBLE);
        }
        txv_course.setText(course);
        txv_yearlvl.setText(yearlevel);
        txv_address.setText(address);
        txv_mobilenumber.setText("+" + mobilenumber);
        txv_emailaddress.setText(emailaddress);
    }

}
