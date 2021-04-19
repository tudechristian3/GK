package com.goodkredit.myapplication.adapter.coopassistant.nonmember;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantLoanType;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantLoanServicesAdapter extends RecyclerView.Adapter<CoopAssistantLoanServicesAdapter.MyViewHolder> {
    private Context mContext;
    private List<CoopAssistantLoanType> coopLoanTypeList;

    public CoopAssistantLoanServicesAdapter(Context context) {
        mContext = context;
        coopLoanTypeList = new ArrayList<>();
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
    public CoopAssistantLoanServicesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coopassistant_loanservices_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(CoopAssistantLoanServicesAdapter.MyViewHolder holder, int position) {
        try {
            CoopAssistantLoanType coopAssistantLoanType = coopLoanTypeList.get(position);

            holder.txv_title.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, coopAssistantLoanType.getName()));

            holder.txv_description.setText(coopAssistantLoanType.getDescription());

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return coopLoanTypeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout main_container;

        private TextView txv_title;
        private TextView txv_description;
        private ImageView imv_arrow;

        public MyViewHolder(View itemView) {
            super(itemView);

            main_container = (LinearLayout) itemView.findViewById(R.id.main_container);
            main_container.setOnClickListener(this);

            txv_title = (TextView) itemView.findViewById(R.id.txv_title);
            txv_description = (TextView) itemView.findViewById(R.id.txv_description);

            imv_arrow = (ImageView) itemView.findViewById(R.id.imv_arrow);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position >= 0) {
                CoopAssistantLoanType coopAssistantLoanType = coopLoanTypeList.get(position);

                switch (view.getId()) {
                    case R.id.main_container: {
                        //ROTATION OF HEADER ARROW
                        if (imv_arrow.getRotation() == 90) {
                            imv_arrow.setRotation(360);
                            txv_description.setVisibility(View.VISIBLE);
                        } else if (imv_arrow.getRotation() == 360) {
                            imv_arrow.setRotation(90);
                            txv_description.setVisibility(View.GONE);
                        } else {
                            imv_arrow.setRotation(90);
                            txv_description.setVisibility(View.GONE);
                        }
                        break;
                    }
                }
            }
        }
    }
}
