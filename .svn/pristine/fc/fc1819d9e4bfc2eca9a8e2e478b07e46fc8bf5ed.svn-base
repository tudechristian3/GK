package com.goodkredit.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.notification.NotificationDetailsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;
import java.util.Random;

//import com.squareup.picasso.Picasso;

/**
 * Created by user on 10/5/2016.
 */
public class NotificationListAdapter extends BaseAdapter {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<NotificationItem> mListData = new ArrayList<>();
    AQuery aq;
    private CommonFunctions cf;

    public NotificationListAdapter(Context mContext, int layoutResourceId, ArrayList<NotificationItem> mListData) {

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mListData = mListData;
    }


    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public NotificationItem getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mListData.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        aq = new AQuery(mContext);
        try {
            int color[] = {R.drawable.circle, R.drawable.circle1, R.drawable.circle2, R.drawable.circle3, R.drawable.circle4};

            NotificationItem item = mListData.get(position);

            if (row == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();

                holder.txnno = (TextView) row.findViewById(R.id.txnno);
                holder.senderlogo = (TextView) row.findViewById(R.id.senderlogo);
                holder.sendericon = (ImageView) row.findViewById(R.id.sendericon);
                holder.sender = (TextView) row.findViewById(R.id.sender);
                holder.message = (TextView) row.findViewById(R.id.message);
                holder.date = (TextView) row.findViewById(R.id.date);
                holder.senderimage = (TextView) row.findViewById(R.id.senderimage);
                holder.notificationrow = (LinearLayout) row.findViewById(R.id.notificationrow);

                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            int min = 1;
            int max = color.length - 1;

            Random r = new Random();
            int i1 = r.nextInt(max - min + 1) + min;

            holder.sender.setText(cf.replaceEscapeData(item.getSender()));

            if(item.getMessage().contains("::::")){
                String[] messages  = item.getMessage().split("::::");
                holder.message.setText(cf.replaceEscapeData(messages[0]));
            }else{
                holder.message.setText(cf.replaceEscapeData(item.getMessage()));
            }

            holder.date.setText(item.getDate());
            holder.senderimage.setText(item.getSenderImage());
            holder.txnno.setText(item.getTxnNo());


            //for the notification color
            if (item.getStatus().equals("0")) {
                holder.notificationrow.setBackgroundResource(R.color.colorsuperlightblue);
            } else {
                holder.notificationrow.setBackgroundResource(R.color.colorwhite);
            }

            //for the logo here
            if (item.getSenderLogo().length() == 1) {
                holder.senderlogo.setText(item.getSenderLogo());
                holder.senderlogo.setBackgroundResource(R.color.colortoolbar);
                holder.sendericon.setVisibility(View.GONE);
                holder.senderlogo.setVisibility(View.VISIBLE);
            } else {
                // Picasso.with(mContext).load(item.getSenderLogo()).into(holder.sendericon);
                aq.id(holder.sendericon).image(item.getSenderLogo(), false, true);
                holder.sendericon.setVisibility(View.VISIBLE);
                holder.senderlogo.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return row;
    }

    static class ViewHolder {
        TextView txnno;
        TextView senderlogo;
        TextView sender;
        TextView message;
        TextView date;
        ImageView sendericon;
        TextView senderimage;
        LinearLayout notificationrow;
    }

    public void update(ArrayList<NotificationItem> arrayList) {
        this.mListData = arrayList;
        notifyDataSetChanged();
    }
}
