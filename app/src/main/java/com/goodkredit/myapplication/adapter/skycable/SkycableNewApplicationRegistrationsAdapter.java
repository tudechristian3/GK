package com.goodkredit.myapplication.adapter.skycable;

import android.content.Context;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.bean.SkycableRegistration;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.Logger;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SkycableNewApplicationRegistrationsAdapter extends RecyclerView.Adapter<SkycableNewApplicationRegistrationsAdapter.MyViewHolder> {
    private Context mContext;
    private List<SkycableRegistration> mGridData;
    private boolean isActionRequired = false;

    public SkycableNewApplicationRegistrationsAdapter(Context context, boolean isActionRequired) {
        mContext = context;
        mGridData = new ArrayList<>();
        this.isActionRequired = isActionRequired;
    }

    public void update(List<SkycableRegistration> data) {
        int startPos = mGridData.size() + 1;
        mGridData.clear();
        mGridData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public void clear() {
        int startPos = mGridData.size();
        mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @Override
    public SkycableNewApplicationRegistrationsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_skycable_new_application_registrations_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SkycableNewApplicationRegistrationsAdapter.MyViewHolder holder, int position) {
        SkycableRegistration skycableRegistration = mGridData.get(position);

        holder.txvApplicationID.setText(skycableRegistration.getRegistrationID());
        holder.txvPlan.setText(CommonFunctions.replaceEscapeData(skycableRegistration.getPlanName()));
        holder.txvName.setText(CommonFunctions.capitalizeWord(skycableRegistration.getFirstName() + " " + skycableRegistration.getLastName()));
        holder.txvStatus.setText(CommonFunctions.capitalizeWord(skycableRegistration.getStatus()));

        switch (skycableRegistration.getStatus()) {
            case "PENDING": {
                holder.txvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_9E9E9E));
                break;
            }
            case "FOR VISIT": {
                holder.txvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_FF5608));
                break;
            }
            case "FOR PAYMENT": {
                holder.txvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_FDD600));
                break;
            }
            case "PAID": {
                holder.txvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_1294F6));
                break;
            }
            case "DECLINED": {
                holder.txvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_F33D2C));
                break;
            }
            case "FOR INSTALLATION": {
                holder.txvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_EC1562));
                break;
            }
            case "INSTALLED": {
                holder.txvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_193B97));
                break;
            }
            default: {
                holder.txvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_9E9E9E));
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout linearRegistrationItem;
        private TextView txvApplicationID;
        private TextView txvPlan;
        private TextView txvName;
        private TextView txvStatus;

        public MyViewHolder(View itemView) {
            super(itemView);

            linearRegistrationItem = (LinearLayout) itemView.findViewById(R.id.linearRegistrationItem);
            linearRegistrationItem.setOnClickListener(this);
            txvApplicationID = (TextView) itemView.findViewById(R.id.txvApplicationID);
            txvPlan = (TextView) itemView.findViewById(R.id.txvPlan);
            txvName = (TextView) itemView.findViewById(R.id.txvName);
            txvStatus = (TextView) itemView.findViewById(R.id.txvStatus);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            if (position > -1) {

                SkycableRegistration skycableRegistration = mGridData.get(position);

                switch (v.getId()) {
                    case R.id.linearRegistrationItem: {
                        Bundle args = new Bundle();
                        if (isActionRequired) {
                            args.putString("TYPE", "ACTIONREQUIRED");
                        } else {
                            args.putString("TYPE", "HISTORY");
                        }
                        args.putParcelable("skycableRegistration", skycableRegistration);

                        Logger.debug("antonhttp", "skycableRegistration PLANS: " + new Gson().toJson(skycableRegistration));

                        ((SkyCableActivity) mContext).displayView(11, args);

                        break;
                    }
                }

            }

        }
    }
}
