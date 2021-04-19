package com.goodkredit.myapplication.adapter.gkearn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gkearn.stockist.GKEarnStockistPackagesDetailsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.gkearn.GKEarnStockistPackage;

import java.util.ArrayList;
import java.util.List;

public class GKEarnStockistPackagesAdapter extends RecyclerView.Adapter<GKEarnStockistPackagesAdapter.MyViewHolder> {
    private Context mContext;
    private List<GKEarnStockistPackage> gkEarnStockistPackageList;
    private ArrayList<GKEarnStockistPackage> passedgkEarnStockistPackageList;
    private int mSelectedItem = -1;

    public GKEarnStockistPackagesAdapter(Context context) {
        mContext = context;
        gkEarnStockistPackageList = new ArrayList<>();
        passedgkEarnStockistPackageList = new ArrayList<>();
    }

    public void updateData(List<GKEarnStockistPackage> arraylist) {
        gkEarnStockistPackageList.clear();
        gkEarnStockistPackageList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        gkEarnStockistPackageList.clear();
        notifyDataSetChanged();
    }

    @Override
    public GKEarnStockistPackagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.item_gkearn_stockist_packages, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(GKEarnStockistPackagesAdapter.MyViewHolder holder, int position) {
        try {
            GKEarnStockistPackage gKEarnStockistPackage = gkEarnStockistPackageList.get(position);

            String stockistpackage = "PACKAGE " + CommonFunctions.pointsFormatter(String.valueOf
                    (gKEarnStockistPackage.getPrice()));

            holder.txv_packages_content.setText(stockistpackage);

            holder.main_container.setOnClickListener(itemSelectedListener);
            holder.main_container.setTag(holder);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //RADIO LISTENER
    private View.OnClickListener itemSelectedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final GKEarnStockistPackagesAdapter.MyViewHolder holder = (MyViewHolder) v.getTag();

                singleSelection(holder);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void singleSelection(MyViewHolder holder) {
        mSelectedItem = holder.getAdapterPosition();
        clearSelection(holder);
        addSelection(holder);
        notifyDataSetChanged();
        proceedToPackageDetails(holder);
    }

    private void addSelection(MyViewHolder holder) {
        if (passedgkEarnStockistPackageList != null) {
            GKEarnStockistPackage gKEarnStockistPackage = gkEarnStockistPackageList.get(holder.getAdapterPosition());

            int id = gKEarnStockistPackage.getID();
            String packageID = gKEarnStockistPackage.getPackageID();
            String packageName = gKEarnStockistPackage.getPackageName();
            String packageDescription = gKEarnStockistPackage.getPackageDescription();
            double price = gKEarnStockistPackage.getPrice();
            double servicecharge = gKEarnStockistPackage.getServiceCharge();
            double reward = gKEarnStockistPackage.getReward();
            int numberMonthExpiry = gKEarnStockistPackage.getNumberMonthExpiry();
            String packageImageURL = gKEarnStockistPackage.getPackageImageURL();
            String xmlDetails = gKEarnStockistPackage.getXMLDetails();
            String status = gKEarnStockistPackage.getStatus();
            String extra1 = gKEarnStockistPackage.getExtra1();
            String extra2 = gKEarnStockistPackage.getExtra2();
            String extra3 = gKEarnStockistPackage.getExtra3();
            String extra4 = gKEarnStockistPackage.getExtra4();
            String notes1 = gKEarnStockistPackage.getNotes1();
            String notes2 = gKEarnStockistPackage.getNotes2();

            passedgkEarnStockistPackageList.add(new GKEarnStockistPackage(id, packageID, packageName, packageDescription,
                    price, servicecharge, reward, numberMonthExpiry, packageImageURL, xmlDetails, status, extra1, extra2, extra3, extra4,
                    notes1, notes2
            ));
        }
    }

    private void clearSelection(MyViewHolder holder) {
        if (passedgkEarnStockistPackageList != null) {
            passedgkEarnStockistPackageList.clear();
        }
    }

    private void proceedToPackageDetails(MyViewHolder holder) {
        if (passedgkEarnStockistPackageList != null) {
            GKEarnStockistPackage earnStockistPackage = null;

            for(GKEarnStockistPackage tempearnStockistPackage : passedgkEarnStockistPackageList) {
                earnStockistPackage = tempearnStockistPackage;
            }

            if(earnStockistPackage != null) {
                Bundle args = new Bundle();
                args.putParcelable(GKEarnStockistPackagesDetailsActivity.KEY_GKEARN_STOCKISTPACKAGE, earnStockistPackage);
                GKEarnStockistPackagesDetailsActivity.start(mContext, args);
            }
        }
    }

    @Override
    public int getItemCount() {
        return gkEarnStockistPackageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout main_container;
        private TextView txv_packages_content;
        private ImageView imv_arrow;


        public MyViewHolder(View itemView) {
            super(itemView);
            main_container = itemView.findViewById(R.id.main_container);
            txv_packages_content = itemView.findViewById(R.id.txv_packages_content);
            imv_arrow = itemView.findViewById(R.id.imv_arrow);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
