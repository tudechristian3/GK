package com.goodkredit.myapplication.adapter.gknegosyo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.merchants.MerchantDetailsActivity;
import com.goodkredit.myapplication.bean.Merchant;
import com.goodkredit.myapplication.bean.Merchants;
import com.goodkredit.myapplication.utilities.CacheManager;

import java.util.List;

public class GKNegosyoPackageMerchantsWithDiscountPQRAdapter extends RecyclerView.Adapter<GKNegosyoPackageMerchantsWithDiscountPQRAdapter.MerchantsWithDiscountViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;

    private List<Merchants> merchants;

    public GKNegosyoPackageMerchantsWithDiscountPQRAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        merchants = CacheManager.getInstance().getMerchantsWithDiscount();
    }

    @NonNull
    @Override
    public MerchantsWithDiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gk_negosyo_merchant_with_discount, parent, false);
        return new MerchantsWithDiscountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchantsWithDiscountViewHolder holder, int position) {
        Merchants merchant = merchants.get(position);
        if (merchant.getMerchantLogo().isEmpty() || merchant.getMerchantLogo().equals(".")) {
            holder.itemView.setBackgroundResource(R.color.colortoolbar);
            holder.tv_merchant_initials.setVisibility(View.VISIBLE);
            holder.imv_merchant_logo.setVisibility(View.GONE);
            holder.tv_merchant_initials.setText(getNetworkInitials(merchant.getMerchantName()));
        } else {
            holder.itemView.setBackgroundResource(R.color.whitePrimary);
            holder.tv_merchant_initials.setVisibility(View.GONE);
            holder.imv_merchant_logo.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(merchant.getMerchantLogo())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_ic_merchants))
                    .into(holder.imv_merchant_logo);
        }

        holder.itemView.setTag(new Merchant(
                merchant.getMerchantID(),
                null,
                merchant.getMerchantName(),
                null,
                merchant.getMerchantLogo(),
                merchant.getStreetAddress(),
                merchant.getProvince(),
                merchant.getCountry(),
                merchant.getAuthorisedTelNo(),
                merchant.getAuthorisedMobileNo(),
                merchant.getNoOfBranches(),
                merchant.getCity(),
                merchant.getLongitude(),
                merchant.getLatitude(),
                merchant.getIsFeatured(),
                merchant.getFeatureAddsPath(),
                merchant.getMerchantGroup(),
                merchant.getZip(),
                merchant.getAuthorisedEmailAddress(),
                merchant.getNatureOfBusiness()
        ));
        holder.itemView.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Merchant merchant = (Merchant) v.getTag();
            MerchantDetailsActivity.start(mContext, merchant);
        }
    };

    @Override
    public int getItemCount() {
        return merchants.size();
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

    public class MerchantsWithDiscountViewHolder extends RecyclerView.ViewHolder {

        private ImageView imv_merchant_logo;
        private TextView tv_merchant_initials;

        public MerchantsWithDiscountViewHolder(View itemView) {
            super(itemView);
            imv_merchant_logo = itemView.findViewById(R.id.imv_merchant_logo);
            tv_merchant_initials = itemView.findViewById(R.id.tv_merchant_initials);
        }
    }
}
