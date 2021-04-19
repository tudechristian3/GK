package com.goodkredit.myapplication.adapter.schoolmini.dtr;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.decoration.DividerItemDecoration;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniDtrFragment;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDtr;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniDtrHeaderAdapter extends RecyclerView.Adapter<SchoolMiniDtrHeaderAdapter.MyViewHolder> {

    private Context mContext;
    private List<SchoolMiniDtr> schoolMiniDtrList = new ArrayList<>();
    private SchoolMiniDtrContentAdapter mAdapter;
    private SchoolMiniDtrFragment fragment;
    private DatabaseHandler mdb;

    public SchoolMiniDtrHeaderAdapter(Context context, SchoolMiniDtrFragment fm) {
        mContext = context;
        schoolMiniDtrList = new ArrayList<>();
        fragment = fm;
        mdb = new DatabaseHandler(mContext);
    }

    public void updateData(List<SchoolMiniDtr> headerlist) {
        this.schoolMiniDtrList = headerlist;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.schoolMiniDtrList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public SchoolMiniDtrHeaderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_schoolmini_dtr_item_header, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(SchoolMiniDtrHeaderAdapter.MyViewHolder holder, int position) {
        SchoolMiniDtr schoolMiniDtr = schoolMiniDtrList.get(position);

        //String dayoftheweek = CommonFunctions.convertDateToDayoftheWeek(schoolMiniDtr.getDateTimeIN());

        String datetime = CommonFunctions.getDateFromDateTime(CommonFunctions
                .convertDate(schoolMiniDtr.getDateTimeIN()));

        holder.header.setText(datetime);

        //ROTATION OF HEADER ARROW
        if(holder.header_container.getRotation() == 90) {
            holder.header_arrow.setRotation(360);
        } else if(holder.header_container.getRotation() == 360) {
            holder.header_arrow.setRotation(90);
        } else {
            holder.header_arrow.setRotation(90);
        }

        holder.layout_subheader_child_container.setVisibility(View.VISIBLE);

        holder.header_container.setBackground(ContextCompat.getDrawable(mContext, R.color.color_stickylist_gray));

        holder.rv_subheader_child.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rv_subheader_child.setNestedScrollingEnabled(false);
        holder.rv_subheader_child.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable
                (mContext, R.drawable.recycler_divider)));
        mAdapter = new SchoolMiniDtrContentAdapter(mContext, fragment);
        holder.rv_subheader_child.setAdapter(mAdapter);

        String convertdate = CommonFunctions.convertDateWithoutTime(schoolMiniDtr.getDateTimeIN());

        mAdapter.updateData(mdb.getSchoolMiniDtrGroupByDAY(mdb, convertdate));
    }

    @Override
    public int getItemCount() {
        return schoolMiniDtrList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout header_container;
        TextView header;
        ImageView header_arrow;

        //CHILD
        LinearLayout layout_subheader_child_container;
        RecyclerView rv_subheader_child;

        public MyViewHolder(View view) {
            super(view);

            header_container = (LinearLayout) view.findViewById(R.id.header_container);
            header = (TextView) view.findViewById(R.id.header);
            header_arrow = (ImageView) view.findViewById(R.id.header_arrow);


            //CHILD
            layout_subheader_child_container = itemView.findViewById(R.id.layout_subheader_child_container);
            header_container.setOnClickListener(this);

            layout_subheader_child_container.setVisibility(View.VISIBLE);
            rv_subheader_child = itemView.findViewById(R.id.rv_subheader_child);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.header_container) {
                if (layout_subheader_child_container.getVisibility() == View.VISIBLE) {
                    layout_subheader_child_container.setVisibility(View.GONE);
                    header_container.requestFocus();
                    header_arrow.setRotation(360);
                } else {
                    layout_subheader_child_container.setVisibility(View.VISIBLE);
                    header_container.requestFocus();
                    header_arrow.setRotation(90);
                }
            }
        }
    }
}
