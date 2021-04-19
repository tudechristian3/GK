package com.goodkredit.myapplication.adapter.sendmoney;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;

public class ClaimCentersAdapter extends RecyclerView.Adapter<ClaimCentersAdapter.CenterViewHolder> {

    private Context mContext;

    public ClaimCentersAdapter(Context context) {
        mContext = context;
    }

    @Override
    public CenterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_claim_center, parent, false);
        // Return a new holder instance
        CenterViewHolder viewHolder = new CenterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CenterViewHolder holder, int position) {
        LinearLayout.LayoutParams lp;
        int itemSide;
        switch (CommonFunctions.getScreenWidthPixel(mContext)) {
            case 480: {
                itemSide = (int) ((float) CommonFunctions.getScreenWidthPixel(mContext) / (float) 3);
                break;
            }
            case 720: {
                itemSide = (int) ((float) CommonFunctions.getScreenWidthPixel(mContext) / (float) 3);
                break;
            }
            case 1080: {
                itemSide = (int) ((float) CommonFunctions.getScreenWidthPixel(mContext) / (float) 3.1);
                break;
            }
            default: {
                itemSide = (int) ((float) CommonFunctions.getScreenWidthPixel(mContext) / (float) 3);
                break;
            }
        }

        lp = new LinearLayout.LayoutParams(
                itemSide,
                itemSide
        );

        holder.itemView.setLayoutParams(lp);
    }

    @Override
    public int getItemCount() {
        return 21;
    }

    public class CenterViewHolder extends RecyclerView.ViewHolder {
        public CenterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
