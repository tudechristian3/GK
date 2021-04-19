package com.goodkredit.myapplication.adapter.mdp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MDPSupportFAQAdapter extends BaseExpandableListAdapter{

    private Context mContext;
    private List<String> expandableListTitle;
    private HashMap<String, String> expandableListDetail;

    public MDPSupportFAQAdapter(Context context,List<String> expandableListTitle, HashMap<String, String> expandableListDetail) {
        mContext = context;

        this.expandableListTitle = new ArrayList<>();
        this.expandableListDetail = new HashMap<>();

        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    public void update(List<String> expandableListTitle, HashMap<String, String> expandableListDetail) {
//        int startPos = mGridData.size() + 1;
//        mGridData.clear();
//        mGridData.addAll(data);
//        notifyItemRangeInserted(startPos, data.size());

        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    //    public void clear() {
//        int startPos = mGridData.size();
//        mGridData.clear();
//        notifyItemRangeRemoved(0, startPos);
//    }

    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return expandableListDetail.get(expandableListTitle.get(groupPosition));
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
                convertView = layoutInflater.inflate(R.layout.activity_faq_group, null);
            }

            ExpandableListView eLV = (ExpandableListView) parent;

            TextView tvHeader = (TextView) convertView.findViewById(R.id.question);
            String headerText = expandableListTitle.get(groupPosition);
            tvHeader.setText(headerText);

            ImageView indicator = (ImageView) convertView.findViewById(R.id.indicator);

            if (isExpanded) {
                convertView.setBackgroundResource(R.color.faq_selected_background);
                tvHeader.setTextColor(mContext.getResources().getColor(R.color.colortoolbar));
                tvHeader.setTypeface(null, Typeface.BOLD);
                indicator.setImageResource(R.drawable.ic_up);
            } else {
                convertView.setBackgroundResource(R.color.superlightgray);
                tvHeader.setTextColor(mContext.getResources().getColor(R.color.colordarkgrey));
                tvHeader.setTypeface(null, Typeface.NORMAL);
                indicator.setImageResource(R.drawable.ic_down);
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
                convertView = layoutInflater.inflate(R.layout.activity_faq_item, null);
            }

            String answer = (String) getChild(groupPosition, groupPosition);

            TextView txvAnswer = (TextView) convertView.findViewById(R.id.answer);
            txvAnswer.setText(CommonFunctions.replaceEscapeData(answer));

            ExpandableListView eLV = (ExpandableListView) parent;

            if (eLV.isGroupExpanded(groupPosition))
                convertView.setBackgroundResource(R.color.faq_selected_background);
            else
                convertView.setBackgroundResource(R.color.superlightgray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
