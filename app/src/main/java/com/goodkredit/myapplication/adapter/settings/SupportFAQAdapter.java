package com.goodkredit.myapplication.adapter.settings;

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
import com.goodkredit.myapplication.responses.support.GetFAQResponseData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ban_Lenovo on 3/8/2017.
 */

public class SupportFAQAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<GetFAQResponseData> mArrayList;

    private ArrayList<String> expandableListTitle;
    private HashMap<String, ArrayList<GetFAQResponseData>> expandableListDetail;

    public SupportFAQAdapter(Context context, ArrayList<String> expandableListTitle, HashMap<String, ArrayList<GetFAQResponseData>> expandableListDetail) {
        mContext = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //  return expandableListDetail.get(expandableListTitle.get(groupPosition)).size();
        return 1;
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
            GetFAQResponseData data = (GetFAQResponseData) getChild(groupPosition, groupPosition);

            TextView answer = (TextView) convertView.findViewById(R.id.answer);
            answer.setText(data.getAnswer());

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

//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        View view = inflater.inflate(R.layout.activity_faq_item, parent, false);
//        MyViewHolder viewHolder = new MyViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        GetFAQResponseData obj = mArrayList.get(position);
//        TextView question = holder.tvQuestion;
//        TextView answer = holder.tvAnswer;
//
//        question.setText(obj.getQuestion());
//        answer.setText(obj.getAnswer());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mArrayList.size();
//        //return 5;
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView tvQuestion;
//        TextView tvAnswer;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            tvQuestion = (TextView) itemView.findViewById(R.id.faqQuestion);
//            tvAnswer = (TextView) itemView.findViewById(R.id.faqAnswer);
//        }
//    }
//
//    public void clear() {
//        mArrayList.clear();
//        notifyDataSetChanged();
//    }
//
//    public void update(ArrayList<GetFAQResponseData> arrlist) {
//        mArrayList = arrlist;
//        notifyDataSetChanged();
//    }

}
