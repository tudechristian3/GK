package com.goodkredit.myapplication.fragments.schoolmini;

import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.schoolmini.MultipleStudentsAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniStudents;

import java.util.ArrayList;


public class SchoolMiniMultipleStudentsFragment extends BaseFragment implements View.OnClickListener {
    //COMMON
    private String session = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //MAIN CONTAINER
    private NestedScrollView home_body;
    private LinearLayout maincontainer;

    //SEARCH
    private LinearLayout edt_search_box_container;
    private EditText edt_search_box;
    private LinearLayout noresultsfound;
    private LinearLayout edt_search_icon_image_container;
    private ImageView edt_search_icon_image;
    private int searchiconselected = 0;

    //MULTI STUDENT
    private LinearLayout rv_multistudents_view_container;
    private RecyclerView rv_multistudents_view;
    private MultipleStudentsAdapter multiplestudentsadapter;

    private ArrayList<SchoolMiniStudents> schoolMiniStudentsList = new ArrayList<>();

    //DELAY ONCLICKS
    private long mLastClickTime = 0;
    private String type = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmini_multiple_students, container, false);
        try {
            init(view);
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        //MAIN CONTAINER
        home_body = (NestedScrollView) view.findViewById(R.id.home_body);
        maincontainer = (LinearLayout) view.findViewById(R.id.maincontainer);

        //SEARCH
        edt_search_box_container = (LinearLayout) view.findViewById(R.id.edt_search_box_container);
        edt_search_box = (EditText) view.findViewById(R.id.edt_search_box);
        noresultsfound = (LinearLayout) view.findViewById(R.id.noresultsfound);
        edt_search_icon_image_container = (LinearLayout) view.findViewById(R.id.edt_search_icon_image_container);
        edt_search_icon_image_container.setOnClickListener(this);
        edt_search_icon_image = (ImageView) view.findViewById(R.id.edt_search_icon_image);

        //MULTI STUDENT
        rv_multistudents_view_container = (LinearLayout) view.findViewById(R.id.rv_multistudents_view_container);
        rv_multistudents_view = (RecyclerView) view.findViewById(R.id.rv_multistudents_view);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());

        if (getArguments() != null) {
            schoolMiniStudentsList = getArguments().getParcelableArrayList("STUDENTSLIST");
            type = getArguments().getString("TYPE");
        }

        rv_multistudents_view.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_multistudents_view.setNestedScrollingEnabled(false);

        multiplestudentsadapter = new MultipleStudentsAdapter(getViewContext(), type);
        rv_multistudents_view.setAdapter(multiplestudentsadapter);

        if(schoolMiniStudentsList != null) {
            multiplestudentsadapter.updateSchoolData(schoolMiniStudentsList);
        }
        else {
            if (mdb.getSchoolMiniStudents(mdb).size() > 1) {
                multiplestudentsadapter.updateSchoolData(mdb.getSchoolMiniStudents(mdb));
            }
        }

        //SEARCH
        schoolMiniSearch();
    }

    //SEARCH LISTENER
    private void schoolMiniSearch() {
        //CLEARS THE FOCUS ON SEARCH ON START
        edt_search_box.clearFocus();

        edt_search_box.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    multiplestudentsadapter.clear();
                    schoolMiniStudentsList.clear();

                    multiplestudentsadapter.updateSchoolData(mdb.getSchoolMiniStudentsByKeyWord(mdb, s.toString().trim()));
                    schoolMiniStudentsList = mdb.getSchoolMiniStudentsByKeyWord(mdb, s.toString().trim());

                    edt_search_icon_image.setImageResource(R.drawable.ic_close_grey600_24dp);
                    searchiconselected = 1;

                    if (schoolMiniStudentsList.isEmpty()) {
                        noresultsfound.setVisibility(View.VISIBLE);
                        rv_multistudents_view.setVisibility(View.GONE);

                    } else {
                        noresultsfound.setVisibility(View.GONE);
                        rv_multistudents_view.setVisibility(View.VISIBLE);

                    }
                } else {
                    multiplestudentsadapter.clear();
                    schoolMiniStudentsList.clear();
                    edt_search_icon_image.setImageResource(R.drawable.ic_search);
                    multiplestudentsadapter.updateSchoolData(mdb.getSchoolMiniStudentsByKeyWord(mdb, ""));
                    searchiconselected = 0;
                    noresultsfound.setVisibility(View.GONE);
                    rv_multistudents_view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_search_icon_image_container: {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if (searchiconselected == 1) {
                    edt_search_icon_image.setImageResource(R.drawable.ic_search);
                    edt_search_box.setText("");
                    searchiconselected = 0;
                    hideKeyboard(getViewContext());
                }
                break;
            }
        }
    }
}
