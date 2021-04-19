package com.goodkredit.myapplication.adapter.merchants;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.merchants.FullMerchantGridActivity;
import com.goodkredit.myapplication.activities.merchants.ViewAllMerchantsByOrganizationTypeActivity;
import com.goodkredit.myapplication.bean.Merchants;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.MerchantsCategoryFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User-PC on 11/13/2017.
 */

public class MerchantCategoryRecyclerAdapter extends RecyclerView.Adapter<MerchantCategoryRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<Merchants> mGridData;
    private MerchantsCategoryFragment mFragment;

    public MerchantCategoryRecyclerAdapter(Context context, MerchantsCategoryFragment fragment) {
        mContext = context;
        mGridData = new ArrayList<>();
        mFragment = fragment;
    }

    public void setMerchantCategoryData(List<Merchants> mGridData) {
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_merchants_category_recycler_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Merchants merchant = mGridData.get(position);
        String mName = "";

        switch (merchant.getOrganizationType().toUpperCase()) {
            case "ALL": {

                Glide.with(mContext)
                        .load(merchant.getOrganizationLogo())
                        .apply(RequestOptions.circleCropTransform()
                                .placeholder(R.drawable.all))
                        .into(holder.organizatypeLogo);
                mName = "All Merchants";

                break;
            }
            case "NEAR": {

                Glide.with(mContext)
                        .load(merchant.getOrganizationLogo())
                        .apply(RequestOptions.circleCropTransform()
                                .placeholder(R.drawable.near))
                        .into(holder.organizatypeLogo);
                mName = "Nearest Merchants";

                break;
            }
            case "FEATURED": {

                Glide.with(mContext)
                        .load(merchant.getOrganizationLogo())
                        .apply(RequestOptions.circleCropTransform()
                                .placeholder(R.drawable.feature))
                        .into(holder.organizatypeLogo);
                mName = "Featured Merchants";

                break;
            }
            default: {

                Glide.with(mContext)
                        .load(merchant.getOrganizationLogo())
                        .apply(RequestOptions.circleCropTransform())
                        .into(holder.organizatypeLogo);
                mName = mFragment.capitalizeWord(CommonFunctions.replaceEscapeData(merchant.getOrganizationType()));

                break;
            }
        }
        holder.merchantName.setText(mName);
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
        private final ImageView organizatypeLogo;
        private final TextView merchantName;

        public MyViewHolder(View itemView) {
            super(itemView);
            organizatypeLogo = (ImageView) itemView.findViewById(R.id.organizatypeLogo);
            merchantName = (TextView) itemView.findViewById(R.id.merchantName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != -1) {
                Merchants merchants = mGridData.get(position);

                switch (merchants.getOrganizationType().toUpperCase()) {
                    case "ALL": {
                        FullMerchantGridActivity.start(mContext, 0);
                        break;
                    }
                    case "NEAR": {
                        FullMerchantGridActivity.start(mContext, 2);
                        break;
                    }
                    case "FEATURED": {
                        FullMerchantGridActivity.start(mContext, 1);
                        break;
                    }
                    default: {
                        Bundle b = new Bundle();
                        b.putParcelable("morganizations", merchants);
                        ViewAllMerchantsByOrganizationTypeActivity.start(mContext, b);
                        break;
                    }

                }
            }
        }
    }
}
