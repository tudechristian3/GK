package com.goodkredit.myapplication.adapter.schoolmini.tuitionfee;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniTuitionFragment;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniParticulars;

import java.util.ArrayList;
import java.util.List;

public class TuitionParticularsAdapter extends RecyclerView.Adapter<TuitionParticularsAdapter.MyViewHolder> {
    private Context mContext;
    private SchoolMiniTuitionFragment fragment;
    private DatabaseHandler mdb;
    private List<SchoolMiniParticulars> schoolMiniParticularsList;

    public TuitionParticularsAdapter(Context context, SchoolMiniTuitionFragment fm) {
        mContext = context;
        schoolMiniParticularsList = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
    }

    public void setParticulars(List<SchoolMiniParticulars> arrayList) {
        schoolMiniParticularsList.clear();
        schoolMiniParticularsList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public TuitionParticularsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.fragment_schoolmini_tuition_item_particulars, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(TuitionParticularsAdapter.MyViewHolder holder, int position) {
        try {
            SchoolMiniParticulars schoolminiparticulars = schoolMiniParticularsList.get(position);

            //THE HEADER ONLY DISPLAYS ON THE FIRST ITEM OF THE LIST
            if(position > 0) {
                holder.header_container.setVisibility(View.GONE);
            }
            else {
                holder.header_container.setVisibility(View.VISIBLE);
            }

            holder.txv_subject.setText(schoolminiparticulars.getSubjectID());
            holder.txv_charges.setText(CommonFunctions.currencyFormatter(String.valueOf(schoolminiparticulars.getCharges())));
            holder.txv_payments.setText(CommonFunctions.currencyFormatter(String.valueOf(schoolminiparticulars.getPayments())));
            holder.txv_balance.setText(CommonFunctions.currencyFormatter(String.valueOf(schoolminiparticulars.getBalance())));

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return schoolMiniParticularsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout header_container;

        TextView txv_subject;
        TextView txv_charges;
        TextView txv_payments;
        TextView txv_balance;

        public MyViewHolder(View itemView) {
            super(itemView);
            header_container = (LinearLayout) itemView.findViewById(R.id.header_container);

            txv_subject = (TextView) itemView.findViewById(R.id.txv_subject);
            txv_charges = (TextView) itemView.findViewById(R.id.txv_charges);
            txv_payments = (TextView) itemView.findViewById(R.id.txv_payments);
            txv_balance = (TextView) itemView.findViewById(R.id.txv_balance);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
