package com.goodkredit.myapplication.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Promotions;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * Created by GoodApps on 07/11/2017.
 */

public class ProductionAdapter extends RecyclerView.Adapter<ProductionAdapter.ViewHolder> {

    private Context mContext;
    private List<Promotions> mArrayList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productlogo;

        public ViewHolder(View itemView) {
            super(itemView);
            productlogo = (ImageView) itemView.findViewById(R.id.mBranchProduct);
        }
    }

    public ProductionAdapter(Context context, List<Promotions> arrayList) {
        mContext = context;
        mArrayList = arrayList;
    }

    public void setPromotions(List<Promotions> data) {
        mArrayList.clear();
        mArrayList = data;
        notifyDataSetChanged();
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    @Override
    public void onBindViewHolder(ProductionAdapter.ViewHolder holder, int position) {

        Promotions obj = mArrayList.get(position);

        ImageView tvName = holder.productlogo;

        MultiTransformation<android.graphics.Bitmap> multi = new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL));

        Glide.with(getContext())
                .load(obj.getPromoLink())
                .apply(bitmapTransform(multi))
                .into((tvName));
    }

    @Override
    public ProductionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View view = inflater.inflate(R.layout.activity_merchant_details_products_items, parent, false);
        // Return a new holder instance
        ProductionAdapter.ViewHolder viewHolder = new ProductionAdapter.ViewHolder(view);
        return viewHolder;
    }

}
