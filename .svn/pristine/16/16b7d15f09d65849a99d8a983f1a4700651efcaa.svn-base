package com.goodkredit.myapplication.adapter.merchants;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.merchants.MerchantDetailsActivity;
import com.goodkredit.myapplication.bean.Merchant;
import com.goodkredit.myapplication.bean.Merchants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 11/8/2017.
 */

public class MerchantOrganizationTypeHorizontalRecyclerAdapter extends RecyclerView.Adapter<MerchantOrganizationTypeHorizontalRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<Merchants> mGridData;

    public MerchantOrganizationTypeHorizontalRecyclerAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    public void setMerchantsData(List<Merchants> mGridData) {
        int startPos = this.mGridData.size() + 1;
        this.mGridData.clear();
        this.mGridData.addAll(mGridData);
        notifyItemRangeInserted(startPos, mGridData.size());
    }

    public void clear() {
        int startPos = this.mGridData.size();
        this.mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @Override
    public MerchantOrganizationTypeHorizontalRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_merchants_recycler_horizontal_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MerchantOrganizationTypeHorizontalRecyclerAdapter.MyViewHolder holder, int position) {
        Merchants item = mGridData.get(position);
        String mLogo = item.getMerchantLogo();
        if (mLogo != null && !mLogo.equals(".")) {
            holder.merchantLogo.setVisibility(View.VISIBLE);
            holder.merchantDefaultLogo.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(mLogo)
                    .into(holder.merchantLogo);
        } else {
            holder.merchantLogo.setVisibility(View.GONE);
            holder.merchantDefaultLogo.setVisibility(View.VISIBLE);
            String initials = getNetworkInitials(item.getMerchantName());
            holder.merchantDefaultLogo.setText(initials);
        }
    }

    private String getNetworkInitials(String name) {
        String[] temp = name.split("\\s");
        String result = "";
        for (int x = 0; x < temp.length; x++) {
            if (temp[x] != null && !temp[x].equals("") && !temp[x].equals(" ")) {
                result += (temp[x].charAt(0));
            }
        }
        if (result.length() >= 2) {
            result = result.substring(0, 2);
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView merchantLogo;
        private TextView merchantDefaultLogo;

        public MyViewHolder(View itemView) {
            super(itemView);
            merchantLogo = (ImageView) itemView.findViewById(R.id.icon);
            merchantLogo.setOnClickListener(this);
            merchantDefaultLogo = (TextView) itemView.findViewById(R.id.merchantDefaultLogo);
            merchantDefaultLogo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Merchants merchants = mGridData.get(position);
            switch (v.getId()) {
                case R.id.icon: {
                    MerchantDetailsActivity.start(mContext, new Merchant(merchants.getMerchantID(), null, merchants.getMerchantName(), null, merchants.getMerchantLogo(), merchants.getStreetAddress(), merchants.getProvince(), merchants.getCountry(), merchants.getAuthorisedTelNo(), merchants.getAuthorisedMobileNo(), merchants.getNoOfBranches(), merchants.getCity(), merchants.getLongitude(), merchants.getLatitude(), merchants.getIsFeatured(), merchants.getFeatureAddsPath(), merchants.getMerchantGroup(), merchants.getZip(), merchants.getAuthorisedEmailAddress(), merchants.getNatureOfBusiness()));
                    break;
                }
                case R.id.merchantDefaultLogo: {
                    MerchantDetailsActivity.start(mContext, new Merchant(merchants.getMerchantID(), null, merchants.getMerchantName(), null, merchants.getMerchantLogo(), merchants.getStreetAddress(), merchants.getProvince(), merchants.getCountry(), merchants.getAuthorisedTelNo(), merchants.getAuthorisedMobileNo(), merchants.getNoOfBranches(), merchants.getCity(), merchants.getLongitude(), merchants.getLatitude(), merchants.getIsFeatured(), merchants.getFeatureAddsPath(), merchants.getMerchantGroup(), merchants.getZip(), merchants.getAuthorisedEmailAddress(), merchants.getNatureOfBusiness()));
                    break;
                }
            }
        }
    }
}
