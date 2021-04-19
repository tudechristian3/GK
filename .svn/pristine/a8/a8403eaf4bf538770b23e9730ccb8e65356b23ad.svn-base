package com.goodkredit.myapplication.adapter.coopassistant.nonmember;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.fragments.coopassistant.nonmember.CoopAssistantApplicationRequirementsFragment;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantMembershipType;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantMembershipAdapter extends RecyclerView.Adapter<CoopAssistantMembershipAdapter.MyViewHolder> {
    private Context mContext;
    private CoopAssistantApplicationRequirementsFragment fragment;
    private List<CoopAssistantMembershipType> coopAssistantMembershipTypeList;
    private ArrayList<CoopAssistantMembershipType> passedAssistantMembershipList;
    private int mSelectedItem = -1;

    public CoopAssistantMembershipAdapter(Context context, CoopAssistantApplicationRequirementsFragment fm) {
        mContext = context;
        fragment = fm;
        coopAssistantMembershipTypeList = new ArrayList<>();
        passedAssistantMembershipList = new ArrayList<>();
    }

    public void updateData(List<CoopAssistantMembershipType> arraylist) {
        coopAssistantMembershipTypeList.clear();
        coopAssistantMembershipTypeList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        coopAssistantMembershipTypeList.clear();
        notifyDataSetChanged();
    }

    @Override
    public CoopAssistantMembershipAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coopassistant_application_requirements_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(CoopAssistantMembershipAdapter.MyViewHolder holder, int position) {
        try {
            CoopAssistantMembershipType coopAssistantMembershipType = coopAssistantMembershipTypeList.get(position);

            holder.rb_membership.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, coopAssistantMembershipType.getName()));

            holder.txv_description.setText(coopAssistantMembershipType.getDescription());

            String notes1 = coopAssistantMembershipType.getNotes1();

            if(notes1 != null) {
                if (!notes1.equals("") && !notes1.equals(".") && !notes1.equals("null")) {
                    holder.txv_descriptionnote.setVisibility(View.VISIBLE);
                    holder.txv_descriptionnote.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_ITALIC, notes1));
                } else {
                    holder.txv_descriptionnote.setVisibility(View.GONE);
                }
            } else {
                holder.txv_descriptionnote.setVisibility(View.GONE);
            }

            String notes2 = coopAssistantMembershipType.getNotes2();

            if(notes2 != null) {
                if (!notes2.equals("") && !notes2.equals(".") && !notes1.equals("null")) {
                    holder.notes_container.setVisibility(View.VISIBLE);
                    holder.txv_note.setTextAppearance(mContext, R.style.roboto_bold);
                    holder.txv_notecontent.setText(notes2);
                } else {
                    holder.notes_container.setVisibility(View.GONE);
                }
            } else {
                holder.notes_container.setVisibility(View.GONE);
            }

            holder.rb_membership.setChecked(position == mSelectedItem);

            holder.main_container.setOnClickListener(selectMembershipListener);
            holder.main_container.setTag(holder);

            holder.rb_membership.setOnClickListener(selectMembershipListener);
            holder.rb_membership.setTag(holder);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //RADIO LISTENER
    private View.OnClickListener selectMembershipListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final CoopAssistantMembershipAdapter.MyViewHolder holder = (MyViewHolder) v.getTag();

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
        if (passedAssistantMembershipList != null) {
            CoopAssistantMembershipType coopAssistantMembershipType = coopAssistantMembershipTypeList.get(holder.getAdapterPosition());

            int id = coopAssistantMembershipType.getID();
            String coopid = coopAssistantMembershipType.getCoopID();
            String name = coopAssistantMembershipType.getName();
            String description = coopAssistantMembershipType.getDescription();
            String otherdetails = coopAssistantMembershipType.getOtherDetails();
            String kycotherinfo = coopAssistantMembershipType.getKYCOtherInfo();
            double amount = coopAssistantMembershipType.getAmount();
            String status = coopAssistantMembershipType.getStatus();
            String extra1 = coopAssistantMembershipType.getExtra1();
            String extra2 = coopAssistantMembershipType.getExtra2();
            String notes1 = coopAssistantMembershipType.getNotes1();
            String notes2 = coopAssistantMembershipType.getNotes2();

            passedAssistantMembershipList.add(new CoopAssistantMembershipType(id, coopid, name, description, otherdetails,
                    kycotherinfo, amount, status, extra1, extra2, notes1, notes2));

            fragment.selectCoopDetails(passedAssistantMembershipList);
        }
    }

    private void clearSelection(MyViewHolder holder) {
        if (passedAssistantMembershipList != null) {
            passedAssistantMembershipList.clear();
            fragment.selectCoopDetails(passedAssistantMembershipList);
        }
    }

    @Override
    public int getItemCount() {
        return coopAssistantMembershipTypeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RadioButton rb_membership;
        private TextView txv_description;
        private TextView txv_descriptionnote;
        private LinearLayout notes_container;
        private TextView txv_note;
        private TextView txv_notecontent;
        private LinearLayout main_container;

        public MyViewHolder(View itemView) {
            super(itemView);
            rb_membership = (RadioButton) itemView.findViewById(R.id.rb_membership);
            txv_description = (TextView) itemView.findViewById(R.id.txv_description);
            txv_descriptionnote = (TextView) itemView.findViewById(R.id.txv_descriptionnote);

            notes_container = (LinearLayout) itemView.findViewById(R.id.notes_container);
            txv_note = (TextView) itemView.findViewById(R.id.txv_note);
            txv_notecontent = (TextView) itemView.findViewById(R.id.txv_notecontent);

            main_container = (LinearLayout) itemView.findViewById(R.id.main_container);
        }

        @Override
        public void onClick(View view) {


        }
    }
}

