package com.goodkredit.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Merchant;

import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 1/17/2017.
 */

public class AllMerchantsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Merchant> arrayList;
    private LayoutInflater layoutInflater;
    private AQuery aq;

    public AllMerchantsAdapter(Context context, ArrayList<Merchant> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        aq = new AQuery(this.context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Merchant getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).hashCode();
    }

    static class ViewHolder {
        ImageView merchantLogo;
        TextView merchantDefault;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        layoutInflater = LayoutInflater.from(context);
        try {
            final String logo = arrayList.get(position).getMerchantLogo();
            final String initials = arrayList.get(position).getMerchatInitials();

            if (view == null) {
                holder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.fragment_merchant_item, parent, false);
                // holder.view – contains initailization of views
                holder.merchantLogo = (ImageView) view.findViewById(R.id.icon);
                holder.merchantDefault = (TextView) view.findViewById(R.id.merchantDefaultLogo);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if (logo.contains("https")) {
                if (!logo.contentEquals("."))
                    aq.id(holder.merchantLogo).image(logo, false, true);
                holder.merchantDefault.setVisibility(View.GONE);
                holder.merchantLogo.setBackgroundResource(R.color.zxing_transparent);
            } else if (logo.contentEquals(".")) {
                holder.merchantLogo.setImageDrawable(null);
                holder.merchantDefault.setVisibility(View.VISIBLE);
                holder.merchantLogo.setImageResource(R.drawable.merchant_background_without_image);
                holder.merchantDefault.setText(initials);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void updateList(ArrayList<Merchant> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
}
