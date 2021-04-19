package com.goodkredit.myapplication.adapter.vouchers;

import android.content.ClipData;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.grouping.GroupVoucherActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonVariables;

import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 4/27/2017.
 */

public class HorizontalUnusedRVAdapter extends RecyclerView.Adapter<HorizontalUnusedRVAdapter.ViewHolder> {

    private ArrayList<Voucher> mArrayList;
    private GroupVoucherActivity mGroupVoucherActivity;
    private Context mContext;
    private AQuery aq;

    private int mPosition = 0;

    public HorizontalUnusedRVAdapter(GroupVoucherActivity activity, ArrayList<Voucher> arrayList) {
        mGroupVoucherActivity = activity;
        mArrayList = arrayList;
        mContext = activity.getViewContext();
        aq = new AQuery(mContext);
    }

    public void setData(ArrayList<Voucher> arrayList) {
        mArrayList = arrayList;
    }

    public ArrayList<Voucher> getCurrentData(int currentPos) {
        mArrayList.remove(currentPos);
        notifyDataSetChanged();
        return mArrayList;
    }

    public ArrayList<Voucher> getData() {
        return mArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_airtime_payement1_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            if (!mArrayList.isEmpty()) {
                Voucher voucher = mArrayList.get(position);
                String imageLink = CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg";
                String balance = "";
                aq.id(holder.mVoucherImage).image(imageLink, false, true);

                if (voucher.getExtra3().equals(".")) {
                    holder.mVoucherTag.setVisibility(View.GONE);
                } else {
                    holder.mVoucherTag.setVisibility(View.VISIBLE);
                }


                if (voucher.getRemainingBalance() % 1 == 0) {
                    balance = String.valueOf((int) voucher.getRemainingBalance());
                } else {
                    balance = String.valueOf(voucher.getRemainingBalance());
                }

                if (balance.length() < 5) {
                    balance = "BAL: " + balance;
                }

                holder.mVoucherBalance.setText(balance);

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
                        v.startDrag(data, shadow, null, 0);
                        setClickedPosition(holder.getPosition());
                        setClickedVoucher(mArrayList.get(getClickedPosition()));
                        return false;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    private void setClickedPosition(int pos) {
        mPosition = pos;
    }

    public int getClickedPosition() {
        return mPosition;
    }

    private void setClickedVoucher(Voucher voucher) {
        mVoucher = voucher;
    }

    public Voucher getClickedVoucher() {
        return mVoucher;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mVoucherImage;
        private TextView mVoucherBalance;
        private ImageView mVoucherTag;

        public ViewHolder(View view) {
            super(view);
            mVoucherImage = (ImageView) view.findViewById(R.id.txtView);
            mVoucherBalance = (TextView) view.findViewById(R.id.bal);
            mVoucherTag = (ImageView) view.findViewById(R.id.prepaid_tag);
        }
    }

    private Voucher mVoucher;
}
