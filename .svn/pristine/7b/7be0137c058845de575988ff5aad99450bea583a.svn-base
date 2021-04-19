package com.goodkredit.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Sponsor;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.Logger;

import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 1/17/2017.
 */

public class SponsorAdapter extends BaseAdapter {

    private ArrayList<Sponsor> arrayList;
    private Context context;
    LayoutInflater layoutInflater;

    private CommonFunctions cf;


    public SponsorAdapter(Context context, ArrayList<Sponsor> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Sponsor getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).hashCode();
    }

    static class ViewHolder {
        //contains declaration of views e.g TextView,
        TextView sponsorName;
        TextView sponsorAddress;
        ImageView sponsorlogo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        layoutInflater = LayoutInflater.from(context);

        try {
            final String name = arrayList.get(position).getSponsorName();

            String getCity = arrayList.get(position).getCity();
            String getProvince = arrayList.get(position).getProvince();
            String address = "";
            String sponsorlogo = arrayList.get(position).getSponsorLogo();


            Logger.debug("ANNCCC", "SPONSOR LOGO" + sponsorlogo);

            if (getCity.equals("") && getProvince.equals("")) {
                address = arrayList.get(position).getStreet() + ", " + arrayList.get(position).getCity() + ", " + arrayList.get(position).getProvince();
            } else if (getProvince.equals("")) {
                address = arrayList.get(position).getStreet() + ", " + arrayList.get(position).getCity() + ", " + arrayList.get(position).getProvince();
            } else {
                address = arrayList.get(position).getStreet() + ", " + arrayList.get(position).getCity() + ", " + arrayList.get(position).getProvince();
            }

            if (view == null) {
                holder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.fragment_guarantors_item, parent, false);
                // holder.view – contains initailization of views
                holder.sponsorName = (TextView) view.findViewById(R.id.guarantorname);
                holder.sponsorAddress = (TextView) view.findViewById(R.id.guarantoraddress);
                holder.sponsorlogo = (ImageView) view.findViewById(R.id.guarantorlogo);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            //do your stuff here e.g setText setVisibility
            try {
                holder.sponsorAddress.setText(cf.replaceEscapeData(address));
                holder.sponsorName.setText(cf.replaceEscapeData(name));
                if(!sponsorlogo.equals("NONE")){
                    Glide.with(context)
                            .load(sponsorlogo)
                            .into(holder.sponsorlogo);
                }else{
                    Glide.with(context)
                            .load(R.drawable.emptylogdefault)
                            .into(holder.sponsorlogo);
                }


            } catch (Exception e) {
                e.printStackTrace();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void update(ArrayList<Sponsor> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
}
