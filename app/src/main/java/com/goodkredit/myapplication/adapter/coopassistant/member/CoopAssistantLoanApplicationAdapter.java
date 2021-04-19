package com.goodkredit.myapplication.adapter.coopassistant.member;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.fragments.coopassistant.CoopAssistantLoanFragment;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantLoanType;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantLoanApplicationAdapter extends RecyclerView.Adapter<CoopAssistantLoanApplicationAdapter.MyViewHolder> {
    private Context mContext;
    private CoopAssistantLoanFragment fragment;
    private List<CoopAssistantLoanType> coopLoanTypeList;
    private ArrayList<CoopAssistantLoanType> passedAssistantLoanList;
    private int mSelectedItem = -1;

    public CoopAssistantLoanApplicationAdapter(Context context, CoopAssistantLoanFragment fm) {
        mContext = context;
        fragment = fm;
        coopLoanTypeList = new ArrayList<>();
        passedAssistantLoanList = new ArrayList<>();
    }

    public void updateData(List<CoopAssistantLoanType> arraylist) {
        coopLoanTypeList.clear();
        coopLoanTypeList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        coopLoanTypeList.clear();
        notifyDataSetChanged();
    }

    @Override
    public CoopAssistantLoanApplicationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coopassistant_loanapplication_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(CoopAssistantLoanApplicationAdapter.MyViewHolder holder, int position) {
        try {
            CoopAssistantLoanType coopAssistantLoanType = coopLoanTypeList.get(position);

            holder.txv_title.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, coopAssistantLoanType.getName()));

            holder.txv_description.setText(coopAssistantLoanType.getDescription());

            holder.rb_select_option.setChecked(position == mSelectedItem);

            holder.main_container.setOnClickListener(selectLoanServicesListener);
            holder.main_container.setTag(holder);

            holder.rb_select_option.setOnClickListener(selectLoanServicesListener);
            holder.rb_select_option.setTag(holder);

//            holder.main_container.setOnClickListener(selectLoanServicesListener);
//            holder.main_container.setTag(holder);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //RADIO LISTENER
    private View.OnClickListener selectLoanServicesListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final CoopAssistantLoanApplicationAdapter.MyViewHolder holder = (MyViewHolder) v.getTag();

                singleSelectionRadio(holder);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void singleSelectionRadio(MyViewHolder holder) {
        mSelectedItem = holder.getAdapterPosition();
        clearSelection(holder);
        addSelection(holder);
        notifyDataSetChanged();
    }

    private void addSelection(MyViewHolder holder) {
        if (passedAssistantLoanList != null) {
            CoopAssistantLoanType coopAssistantLoanType = coopLoanTypeList.get(holder.getAdapterPosition());

            int id = coopAssistantLoanType.getID();
            String coopid = coopAssistantLoanType.getCoopID();
            String loanid = coopAssistantLoanType.getLoanID();
            String name = coopAssistantLoanType.getName();
            String description = coopAssistantLoanType.getDescription();
            String otherdetails = coopAssistantLoanType.getOtherDetails();
            String kycotherinfo = coopAssistantLoanType.getKYCOtherInfo();
            double amount = coopAssistantLoanType.getAmount();
            String status = coopAssistantLoanType.getStatus();
            String extra1 = coopAssistantLoanType.getExtra1();
            String extra2 = coopAssistantLoanType.getExtra2();
            String notes1 = coopAssistantLoanType.getNotes1();
            String notes2 = coopAssistantLoanType.getNotes2();

            passedAssistantLoanList.add(new CoopAssistantLoanType(id, coopid, loanid, name, description, otherdetails,
                    kycotherinfo, amount, status, extra1, extra2, notes1, notes2));

            fragment.selectCoopDetails(passedAssistantLoanList);
        }
    }

    private void clearSelection(MyViewHolder holder) {
        if (passedAssistantLoanList != null) {
            passedAssistantLoanList.clear();
            fragment.selectCoopDetails(passedAssistantLoanList);
        }
    }


    @Override
    public int getItemCount() {
        return coopLoanTypeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout main_container;

        private RadioButton rb_select_option;

        private TextView txv_title;
        private TextView txv_description;

        public MyViewHolder(View itemView) {
            super(itemView);

            main_container = (LinearLayout) itemView.findViewById(R.id.main_container);

            rb_select_option = (RadioButton) itemView.findViewById(R.id.rb_select_option);

            txv_title = (TextView) itemView.findViewById(R.id.txv_title);
            txv_description = (TextView) itemView.findViewById(R.id.txv_description);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
