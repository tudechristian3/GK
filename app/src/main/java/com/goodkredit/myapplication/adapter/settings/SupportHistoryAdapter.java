package com.goodkredit.myapplication.adapter.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.responses.support.GetSupportMessagesResponseData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ban_Lenovo on 3/6/2017.
 */

public class SupportHistoryAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<GetSupportMessagesResponseData> mArrayList;

    private Gson gson;
    private AQuery aq;
    private DatabaseHandler db;
    private ProgressDialog mProgressDialog;
    private ArrayList<String> expandableListTitle;
    private HashMap<String, ArrayList<GetSupportMessagesResponseData>> expandableListDetail;

    public SupportHistoryAdapter(Context context, ArrayList<String> expandableListTitle, HashMap<String, ArrayList<GetSupportMessagesResponseData>> expandableListDetail) {
        mContext = context;
        this.expandableListDetail = expandableListDetail;
        this.expandableListTitle = expandableListTitle;
        aq = new AQuery(mContext);
        db = new DatabaseHandler(mContext);
        gson = new Gson();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setInverseBackgroundForced(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Fetching messages\nPlease wait...");
    }

    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return expandableListDetail.get(expandableListTitle.get(groupPosition)).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return expandableListDetail.get(expandableListTitle.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) mContext.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.header_support_history, null);
            }
            ExpandableListView eLV = (ExpandableListView) parent;
            eLV.expandGroup(groupPosition);

            TextView tvHeader = (TextView) convertView.findViewById(R.id.tv_header);
            String headerText = expandableListTitle.get(groupPosition) + " CASES";
            tvHeader.setText(headerText);

            if (groupPosition == 0) {
                tvHeader.setTextColor(mContext.getResources().getColor(R.color.colortoolbar));
                if (expandableListTitle.get(groupPosition).equals("CLOSED"))
                    tvHeader.setTextColor(mContext.getResources().getColor(R.color.colordarkgrey));
            } else {
                tvHeader.setTextColor(mContext.getResources().getColor(R.color.colordarkgrey));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.child_support_history, null);
            }

            GetSupportMessagesResponseData data = (GetSupportMessagesResponseData) getChild(groupPosition, childPosition);

            TextView ticket = (TextView) convertView.findViewById(R.id.tv_ticker_number);
            TextView topic = (TextView) convertView.findViewById(R.id.tv_topic);
            TextView subject = (TextView) convertView.findViewById(R.id.tv_subject);
            TextView timein = (TextView) convertView.findViewById(R.id.tv_last_update);

            LinearLayout itemView = (LinearLayout) convertView.findViewById(R.id.itemView);

            if (data.getStatus().equals("OPEN")) {
                ticket.setTextColor(mContext.getResources().getColor(R.color.colortoolbar));
                if (data.getNotificationStatus().equals("0"))
                    itemView.setBackgroundResource(R.color.colorsuperlightblue);
                else
                    itemView.setBackgroundResource(R.color.colorwhite);
            } else {
                ticket.setTextColor(mContext.getResources().getColor(R.color.colorTextGrey));
            }


            ticket.setText(data.getThreadID());
            topic.setText(data.getHelpTopic());
            subject.setText(data.getSubject());
            timein.setText(CommonFunctions.convertDate(data.getDateTimeIN()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void update(ArrayList<String> expandableListTitle, HashMap<String, ArrayList<GetSupportMessagesResponseData>> expandableListDetail) {
        this.expandableListDetail = expandableListDetail;
        this.expandableListTitle = expandableListTitle;
        notifyDataSetChanged();
    }

    public void clearData() {
        expandableListTitle.clear();
        expandableListDetail.clear();
        notifyDataSetChanged();
    }
}
