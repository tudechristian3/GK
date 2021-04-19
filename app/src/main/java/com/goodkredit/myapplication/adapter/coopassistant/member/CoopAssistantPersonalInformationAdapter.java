package com.goodkredit.myapplication.adapter.coopassistant.member;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.utilities.PercentageCropImageView;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantPersonalInformationAdapter extends RecyclerView.Adapter<CoopAssistantPersonalInformationAdapter.MyViewHolder> {
    private Context mContext;
    private List<CoopAssistantMembers> coopAssistantMembersList;
    private DatabaseHandler mdb;

    public CoopAssistantPersonalInformationAdapter(Context context) {
        mContext = context;
        coopAssistantMembersList = new ArrayList<>();
        mdb = new DatabaseHandler(mContext);
    }

    public void updateData(List<CoopAssistantMembers> arraylist) {
        coopAssistantMembersList.clear();
        coopAssistantMembersList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        coopAssistantMembersList.clear();
        notifyDataSetChanged();
    }

    @Override
    public CoopAssistantPersonalInformationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coopassistant_home_member_personalinfo_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(CoopAssistantPersonalInformationAdapter.MyViewHolder holder, int position) {
        try {
            CoopAssistantMembers coopAssistantMembers = coopAssistantMembersList.get(position);

            V2Subscriber mSubscriber = mdb.getSubscriber(mdb);

            holder.imv_arrow_container.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(mSubscriber.getProfilePic())
                    .placeholder(R.drawable.emptyprofilepic)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .override(512, 512)
                            .fitCenter())
                    .into(holder.imv_logo);

            String firstname = coopAssistantMembers.getFirstName();
            String middleName = coopAssistantMembers.getMiddleName();
            String lastName = coopAssistantMembers.getLastName();

            if(firstname.trim().equals(".")) {
                firstname = "";
            } else if(middleName.trim().equals(".")) {
                middleName = "";
            } else if(middleName.trim().equals(".")) {
                lastName = "";
            }

            String name = firstname + " " + middleName + " " + lastName;

            holder.txv_name.setText(CommonFunctions.replaceEscapeData(name.toUpperCase()));

            holder.txv_address.setText(coopAssistantMembers.getCurrentAddress());

            holder.txv_mobileno.setText("+".concat(coopAssistantMembers.getMobileNumber()));

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return coopAssistantMembersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout main_itemscontainer;
        private PercentageCropImageView imv_logo;

        private TextView txv_name;
        private TextView txv_address;
        private TextView txv_mobileno;

        private LinearLayout imv_arrow_container;
        private ImageView imv_arrow;


        public MyViewHolder(View itemView) {
            super(itemView);

            main_itemscontainer = (LinearLayout) itemView.findViewById(R.id.main_itemscontainer);
            main_itemscontainer.setOnClickListener(this);

            imv_logo = (PercentageCropImageView) itemView.findViewById(R.id.imv_logo);

            txv_name = (TextView) itemView.findViewById(R.id.txv_name);
            txv_address = (TextView) itemView.findViewById(R.id.txv_address);
            txv_mobileno = (TextView) itemView.findViewById(R.id.txv_mobileno);

            imv_arrow_container = (LinearLayout) itemView.findViewById(R.id.imv_arrow_container);
            imv_arrow = (ImageView) itemView.findViewById(R.id.imv_arrow);
            imv_arrow.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position >= 0) {
                CoopAssistantMembers coopAssistantMembers = coopAssistantMembersList.get(position);

                switch (view.getId()) {
                    case R.id.main_itemscontainer: {
                        CoopAssistantHomeActivity.start(mContext, 5, null);
                        break;
                    }
                }
            }
        }
    }
}