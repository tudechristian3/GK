package com.goodkredit.myapplication.adapter.coopassistant.nonmember;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.activities.gkstore.GKStoreDetailsActivity;
import com.goodkredit.myapplication.activities.prepaidrequest.VoucherPrepaidRequestActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.fragments.coopassistant.CoopAssistantHomeFragment;
import com.goodkredit.myapplication.fragments.prepaidrequest.VirtualVoucherProductFragment;
import com.goodkredit.myapplication.model.GKConfigurations;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.utilities.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantMenuServicesAdapter extends RecyclerView.Adapter<CoopAssistantMenuServicesAdapter.MyViewHolder> {
    private Context mContext;
    private List<GKConfigurations> coopAssistantList;
    private GKService gkService;
    private CoopAssistantHomeFragment fragment;
//    PreferenceUtils.removePreference(context, "GKSERVICE_OBJECT");
//    PreferenceUtils.saveGKServicesPreference(context, "GKSERVICE_OBJECT", service);

    public CoopAssistantMenuServicesAdapter(Context context, CoopAssistantHomeFragment fm) {
        mContext = context;
        coopAssistantList = new ArrayList<>();
        fragment = fm;
        //gkService = fragment.getArguments().getParcelable("GKSERVICE_OBJECT");
        gkService = PreferenceUtils.getGKServicesPreference(mContext, "GKSERVICE_OBJECT");
    }

    public void updateData(List<GKConfigurations> arraylist) {
        coopAssistantList.clear();
        coopAssistantList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        coopAssistantList.clear();
        notifyDataSetChanged();
    }

    @Override
    public CoopAssistantMenuServicesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coopassistant_menuservices_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(CoopAssistantMenuServicesAdapter.MyViewHolder holder, int position) {
        try {
            GKConfigurations gkConfigurations = coopAssistantList.get(position);

            setImageView(holder);

            String servicename = gkConfigurations.getServiceConfigName();

            if (servicename.contains("Loan")) {
                List<CoopAssistantMembers> coopMembersList = PreferenceUtils.getCoopMembersListPreference(mContext, CoopAssistantMembers.KEY_COOPMEMBERINFORMATION);
                if (coopMembersList.size() > 0) {
                    holder.txv_content.setText("Loan Application");
                } else {
                    holder.txv_content.setText("Loan Services");
                }
            } else {
                holder.txv_content.setText(servicename);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void setImageView(MyViewHolder holder) {
        GKConfigurations gkConfigurations = coopAssistantList.get(holder.getAdapterPosition());

        String servicename = gkConfigurations.getServiceConfigName();

        if (servicename.contains("Loan")) {
            Glide.with(mContext)
                    .load(R.drawable.coopassistant_loan)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.generic_placeholder_gk_background)
                            .fitCenter())
                    .into(holder.imv_logo);
        } else if (servicename.contains("Loan Application")) {
            Glide.with(mContext)
                    .load(R.drawable.coopassistant_loan)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.generic_placeholder_gk_background)
                            .fitCenter())
                    .into(holder.imv_logo);
        } else if (servicename.contains("Bulletin")) {
            Glide.with(mContext)
                    .load(R.drawable.coopassistant_ebulletin)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.generic_placeholder_gk_background)
                            .fitCenter())
                    .into(holder.imv_logo);
        } else if (servicename.contains("Store")) {
            Glide.with(mContext)
                    .load(R.drawable.coopassitant_store)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.generic_placeholder_gk_background)
                            .fitCenter())
                    .into(holder.imv_logo);
        } else if (servicename.contains("Support")) {
            Glide.with(mContext)
                    .load(R.drawable.coopassistant_support)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.generic_placeholder_gk_background)
                            .fitCenter())
                    .into(holder.imv_logo);
        } else if (servicename.contains("Statement")) {
            Glide.with(mContext)
                    .load(R.drawable.coopassistant_soa)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.generic_placeholder_gk_background)
                            .fitCenter())
                    .into(holder.imv_logo);
        } else if (servicename.contains("Refer")) {
            Glide.with(mContext)
                    .load(R.drawable.coopassistant_referafriend)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.generic_placeholder_gk_background)
                            .fitCenter())
                    .into(holder.imv_logo);
        } else if (servicename.contains("Voucher")) {
            Glide.with(mContext)
                    .load(R.drawable.coopassistant_gkvoucher)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.generic_placeholder_gk_background)
                            .fitCenter())
                    .into(holder.imv_logo);
        }
        else {
            Glide.with(mContext)
                    .load("")
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.generic_placeholder_gk_background)
                            .fitCenter())
                    .into(holder.imv_logo);
        }
    }

    @Override
    public int getItemCount() {
        return coopAssistantList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout layout_container;
        private ImageView imv_logo;
        private TextView txv_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            layout_container = (LinearLayout) itemView.findViewById(R.id.layout_container);
            layout_container.setOnClickListener(this);

            imv_logo = (ImageView) itemView.findViewById(R.id.imv_logo);
            txv_content = (TextView) itemView.findViewById(R.id.txv_content);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position >= 0) {
                GKConfigurations gkConfigurations = coopAssistantList.get(position);

                if (view.getId() == R.id.layout_container) {
                    String servicename = gkConfigurations.getServiceConfigName();
                    if (servicename.contains("Loan")) {
                        CoopAssistantHomeActivity.start(mContext, 3, null);
                    } else if (servicename.contains("Bulletin")) {
                        CoopAssistantHomeActivity.start(mContext, 4, null);
                    } else if (servicename.contains("Store")) {
                        Intent intent = new Intent(mContext, GKStoreDetailsActivity.class);
                        intent.putExtra("GKSERVICE_OBJECT", gkService);
                        mContext.startActivity(intent);
                    } else if (servicename.contains("Support")) {
                        CoopAssistantHomeActivity.start(mContext, 6, null);
                    } else if (servicename.contains("Statement")) {
                        CoopAssistantHomeActivity.start(mContext, 9, null);
                    } else if (servicename.contains("Refer")) {
                        CoopAssistantHomeActivity.start(mContext, 11, null);
                    }
                    else if (servicename.contains("Voucher")) {
                        //CoopAssistantHomeActivity.start(mContext, 17, null);
                        VoucherPrepaidRequestActivity.start(mContext, 0, VirtualVoucherProductFragment.BY_COOPGETVOUCHER, 0.00);
                    }
                }
            }
        }
    }
}
