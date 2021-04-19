package com.goodkredit.myapplication.adapter.gknegosyo;

import android.content.Context;
import android.location.Address;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoPackageDetailsActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.GKNegosyPackage;
import com.goodkredit.myapplication.model.GKNegosyoPackagesAndDetails;
import com.goodkredit.myapplication.utilities.CacheManager;

import java.util.List;

public class GKNegosyoPackagesAdapter extends RecyclerView.Adapter<GKNegosyoPackagesAdapter.PackageViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private List<GKNegosyPackage> mGKNegosyPackages;
    private GKService mGKService;
    private GKNegosyoPackagesAndDetails mGKNegosyoPackagesAndDetails;

    private String mGKDistributor;
    private String mGKDistributorName;

    private Address mAddress;

    public GKNegosyoPackagesAdapter(Context context, GKService gkService, List<GKNegosyPackage> gkNegosyPackages, Address address) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mGKNegosyPackages = gkNegosyPackages;
        mGKService = gkService;
        mAddress = address;
    }

    public void updatePackages(List<GKNegosyPackage> gkNegosyPackages) {
        mGKNegosyPackages.clear();
        mGKNegosyPackages = gkNegosyPackages;
        notifyDataSetChanged();
    }

    public void updateDistributorID(String data) {
        mGKDistributor = data;
    }

    public void updateDistributorName(String data) {
        mGKDistributorName = data;
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gk_negosyo_package, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        GKNegosyPackage pckge = mGKNegosyPackages.get(position);
        String strPrice = "₱" + CommonFunctions.currencyFormatter(String.valueOf(pckge.getPrice()));

        holder.pckgeName.setText(pckge.getPackageName());
        holder.pckagePrice.setText(strPrice);
        holder.itemView.setTag(pckge);

        Glide.with(mContext)
                .load(pckge.getURL())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_gk_negosyo_package))
                .into(holder.pckgeImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGKNegosyoPackagesAndDetails = CacheManager.getInstance().getGKNegosyoPackagesAndDetails();
                GKNegosyoPackageDetailsActivity.start(mContext, mGKService, (GKNegosyPackage) v.getTag(), mGKDistributor, mAddress, mGKNegosyoPackagesAndDetails, mGKDistributorName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGKNegosyPackages.size();
    }

    public class PackageViewHolder extends ViewHolder {

        private ImageView pckgeImage;
        private TextView pckgeName;
        private TextView pckagePrice;

        public PackageViewHolder(View itemView) {
            super(itemView);
            pckgeImage = itemView.findViewById(R.id.gknp_imgv);
            pckgeName = itemView.findViewById(R.id.gknp_tv_pckgnme);
            pckagePrice = itemView.findViewById(R.id.gknp_tv_price);
        }
    }
}
