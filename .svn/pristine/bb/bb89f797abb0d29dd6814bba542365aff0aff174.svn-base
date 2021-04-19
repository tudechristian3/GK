package com.goodkredit.myapplication.adapter.soa;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.SubscriberBillSummary;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.soa.SettlementDetailsActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 10/9/2017.
 */

public class UnpaidBillsAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<SubscriberBillSummary> mArrayList;

    private Gson gson;
    private AQuery aq;
    private DatabaseHandler db;
    private ProgressDialog mProgressDialog;
    private ArrayList<String> expandableListTitle;
    private HashMap<String, ArrayList<SubscriberBillSummary>> expandableListDetail;

    public UnpaidBillsAdapter(Context context, ArrayList<String> expandableListTitle, HashMap<String, ArrayList<SubscriberBillSummary>> expandableListDetail) {
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
        mProgressDialog.setMessage("Fetching billings\nPlease wait...");
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
                convertView = layoutInflater.inflate(R.layout.fragment_unpaid_bills_header, null);
            }
            ExpandableListView eLV = (ExpandableListView) parent;
            eLV.expandGroup(groupPosition);

            TextView tvHeader = (TextView) convertView.findViewById(R.id.tv_header);
            String headerText = expandableListTitle.get(groupPosition);
            tvHeader.setText(headerText);
            tvHeader.setTextColor(mContext.getResources().getColor(R.color.colortoolbar));


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
                convertView = layoutInflater.inflate(R.layout.fragment_unpaid_bills_item, null);
            }

            SubscriberBillSummary data = (SubscriberBillSummary) getChild(groupPosition, childPosition);

            TextView amount = (TextView) convertView.findViewById(R.id.totalamount);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            RelativeLayout wrap = (RelativeLayout) convertView.findViewById(R.id.wrap);

            amount.setText(CommonFunctions.currencyFormatter(Double.toString(data.getAmount())));
            date.setText("Payment due on "+CommonFunctions.convertDate1(data.getDueDate()));
            wrap.setTag(data);
            wrap.setOnClickListener(itemViewClick);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private View.OnClickListener itemViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SubscriberBillSummary bill = (SubscriberBillSummary) v.getTag();
            SettlementDetailsActivity.start(mContext, bill);
        }
    };

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void update(ArrayList<String> expandableListTitle, HashMap<String, ArrayList<SubscriberBillSummary>> expandableListDetail) {
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
