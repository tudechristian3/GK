package com.goodkredit.myapplication.adapter.schoolmini.tuitionfee;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniTuitionFragment;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniParticulars;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniTuitionFee;

import java.util.ArrayList;
import java.util.List;

public class TuitionPayNowAdapter extends RecyclerView.Adapter<TuitionPayNowAdapter.MyViewHolder> {
    private Context mContext;
    private List<SchoolMiniTuitionFee> schoolMiniTuitionFeeList;
    private SchoolMiniTuitionFragment fragment;
    private TuitionParticularsAdapter mAdapter;
    private DatabaseHandler mdb;
    private List<SchoolMiniParticulars> schoolMiniParticularsList;
//    private boolean proceedtoamount = false;

    public TuitionPayNowAdapter(Context context, SchoolMiniTuitionFragment fm) {
        mContext = context;
        schoolMiniTuitionFeeList = new ArrayList<>();
        schoolMiniParticularsList = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
    }

    public void setSemestralFee(List<SchoolMiniTuitionFee> arrayList) {
        schoolMiniTuitionFeeList.clear();
        schoolMiniTuitionFeeList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public TuitionPayNowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.fragment_schoolmini_tuition_item_paynow, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(TuitionPayNowAdapter.MyViewHolder holder, int position) {
        try {
            SchoolMiniTuitionFee schoolminituitionfee = schoolMiniTuitionFeeList.get(position);

            holder.txv_semesterfee.setText(CommonFunctions.currencyFormatter(String.valueOf(schoolminituitionfee.getTotalAmountToPay())));
            holder.txv_posted_on.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(schoolminituitionfee.getDateTimeIN())));

            holder.btn_paynow.setOnClickListener(paynowlistener);
            holder.btn_paynow.setTag(holder);

            holder.btn_payment_logs.setOnClickListener(paymentlogs);
            holder.btn_payment_logs.setTag(holder);

            LinearLayout layout_college_exam_term;
            TextView txv_college_exam_term;

            if(schoolminituitionfee.getType().toUpperCase().trim().contains("COLLEGE") ||
                    schoolminituitionfee.getType().toUpperCase().contains("SENIOR HIGH")) {
                holder.layout_college_exam_term.setVisibility(View.VISIBLE);
                if(schoolminituitionfee.getSemester().equals(".")) {
                    holder.txv_college_exam_term.setText("N/A");
                } else {
                    holder.txv_college_exam_term.setText(schoolminituitionfee.getExamTerm());
                }
            } else {
                holder.layout_college_exam_term.setVisibility(View.GONE);
            }

            String particulars = schoolminituitionfee.getParticulars();

            if(particulars.trim().equals(".") || particulars.trim().equals(".")) {
                holder.txv_notes_container.setVisibility(View.GONE);
            } else {
                holder.txv_notes_container.setVisibility(View.VISIBLE);
                holder.txv_notes.setText(particulars);
            }

//            schoolMiniParticularsList = new Gson().fromJson(particulars, new TypeToken<List<SchoolMiniParticulars>>() {
//            }.getType());
//            holder.rv_tuitionbalances.setNestedScrollingEnabled(false);
//            holder.rv_tuitionbalances.setLayoutManager(new LinearLayoutManager(mContext));
//            holder.rv_tuitionbalances.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider)));
//            mAdapter = new TuitionParticularsAdapter(mContext, fragment);
//            holder.rv_tuitionbalances.setAdapter(mAdapter);

            mAdapter.setParticulars(schoolMiniParticularsList);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return schoolMiniTuitionFeeList.size();
    }

    private View.OnClickListener paynowlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final TuitionPayNowAdapter.MyViewHolder holder = (MyViewHolder) v.getTag();

                SchoolMiniTuitionFee schoolminituitionfee = schoolMiniTuitionFeeList.get(holder.getAdapterPosition());

                String schoolyear = schoolminituitionfee.getSchoolYear();
                String semester = "";
                if(schoolminituitionfee.getType().toUpperCase().trim().contains("COLLEGE")) {
                    semester = schoolminituitionfee.getSemester();
                }
                else if(schoolminituitionfee.getType().toUpperCase().contains("SENIOR HIGH")) {
                    semester = schoolminituitionfee.getSemester();
                }
                else {
                    semester = schoolminituitionfee.getExamTerm();
                }
                String semestralfee = String.valueOf(schoolminituitionfee.getTotalAmountToPay());
                String soaid = schoolminituitionfee.getSOAID();

                PreferenceUtils.removePreference(mContext,  PreferenceUtils.KEY_PASSEDSOAID);
                PreferenceUtils.removePreference(mContext,  PreferenceUtils.KEY_PASSEDSCHOOLYEAR);
                PreferenceUtils.removePreference(mContext,  PreferenceUtils.KEY_PASSEDSEMESTER);
                PreferenceUtils.removePreference(mContext,  PreferenceUtils.KEY_PASSEDSEMESTRALFEE);

                PreferenceUtils.saveStringPreference(mContext , PreferenceUtils.KEY_PASSEDSOAID, soaid);
                PreferenceUtils.saveStringPreference(mContext , PreferenceUtils.KEY_PASSEDSCHOOLYEAR, schoolyear);
                PreferenceUtils.saveStringPreference(mContext , PreferenceUtils.KEY_PASSEDSEMESTER, semester);
                PreferenceUtils.saveStringPreference(mContext , PreferenceUtils.KEY_PASSEDSEMESTRALFEE, semestralfee);

                fragment.getSessionTuitionStatus();

            } catch(Exception e ) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener paymentlogs = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final TuitionPayNowAdapter.MyViewHolder holder = (MyViewHolder) v.getTag();

                SchoolMiniTuitionFee schoolminituitionfee = schoolMiniTuitionFeeList.get(holder.getAdapterPosition());

                String studentid = fragment.getArguments().getString("STUDENTID");
                String course = fragment.getArguments().getString("COURSE");
                String yearlevel = fragment.getArguments().getString("YEARLEVEL");
                String firstname = fragment.getArguments().getString("FIRSTNAME");
                String middlename = fragment.getArguments().getString("MIDDLENAME");
                String lastname = fragment.getArguments().getString("LASTNAME");
                String mobilenumber = fragment.getArguments().getString("MOBILENUMBER");
                String emailaddress = fragment.getArguments().getString("EMAILADDRESS");

                String schoolyear = schoolminituitionfee.getSchoolYear();
                String semester = "";
                if(schoolminituitionfee.getType().toUpperCase().trim().contains("COLLEGE")) {
                    semester = schoolminituitionfee.getSemester();
                }
                else if(schoolminituitionfee.getType().toUpperCase().contains("SENIOR HIGH")) {
                    semester = schoolminituitionfee.getSemester();
                }
                else {
                    semester = schoolminituitionfee.getExamTerm();
                }
                String semestralfee = String.valueOf(schoolminituitionfee.getTotalAmountToPay());
                String soaid = schoolminituitionfee.getSOAID();

                Bundle args = new Bundle();
                args.putString("STUDENTID", studentid);
                args.putString("COURSE", course);
                args.putString("YEARLEVEL", yearlevel);
                args.putString("FIRSTNAME", firstname);
                args.putString("MIDDLENAME", middlename);
                args.putString("LASTNAME", lastname);
                args.putString("MOBILENUMBER", mobilenumber);
                args.putString("EMAILADDRESS", emailaddress);
                args.putString("SCHOOLYEAR", schoolyear);
                args.putString("SEMESTER", semester);
                args.putString("SEMESTRALFEE", semestralfee);
                args.putString("SOAID", soaid);

                SchoolMiniActivity.start(mContext, 10, args);
            } catch(Exception e ) {
                e.printStackTrace();
            }
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //PARENT
        TextView txv_semesterfee;
        Button btn_paynow;
        Button btn_payment_logs;
        LinearLayout txv_notes_container;
        TextView txv_notes;
        TextView txv_posted_on;

        LinearLayout layout_college_exam_term;
        TextView txv_college_exam_term;

        //CHILD
        RecyclerView rv_tuitionbalances;

        public MyViewHolder(View itemView) {
            super(itemView);
            //PARENT
            txv_semesterfee = (TextView) itemView.findViewById(R.id.txv_semesterfee);
            btn_paynow = (Button) itemView.findViewById(R.id.btn_paynow);
            btn_payment_logs = (Button) itemView.findViewById(R.id.btn_payment_logs);
            txv_notes_container = (LinearLayout) itemView.findViewById(R.id.txv_notes_container);
            txv_notes = (TextView) itemView.findViewById(R.id.txv_notes);
            txv_posted_on = (TextView) itemView.findViewById(R.id.txv_posted_on);

            layout_college_exam_term = (LinearLayout) itemView.findViewById(R.id.layout_college_exam_term);
            txv_college_exam_term = (TextView) itemView.findViewById(R.id.txv_college_exam_term);

//            //CHILD
//            rv_tuitionbalances = (RecyclerView) itemView.findViewById(R.id.rv_tuitionbalances);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
