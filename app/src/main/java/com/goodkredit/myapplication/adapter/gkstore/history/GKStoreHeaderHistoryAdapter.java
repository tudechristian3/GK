package com.goodkredit.myapplication.adapter.gkstore.history;

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
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.FetchStoreOrderList;

import java.util.ArrayList;
import java.util.List;

import com.goodkredit.myapplication.decoration.DividerItemDecoration;


public class GKStoreHeaderHistoryAdapter extends RecyclerView.Adapter<GKStoreHeaderHistoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<FetchStoreOrderList> fetchorderlist = new ArrayList<>();
    private GKStoreChildHistoryAdapter mAdapter;
    private DatabaseHandler mdb;

    public GKStoreHeaderHistoryAdapter(Context context, List<FetchStoreOrderList> arrayList) {
        this.mContext = context;
        this.fetchorderlist = arrayList;
        mdb = new DatabaseHandler(mContext);
    }

    public void updateData(List<FetchStoreOrderList> headerlist) {
        this.fetchorderlist = headerlist;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.fetchorderlist.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public GKStoreHeaderHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gk_store_header_summary, parent, false);
        GKStoreHeaderHistoryAdapter.MyViewHolder holder = new GKStoreHeaderHistoryAdapter.MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(GKStoreHeaderHistoryAdapter.MyViewHolder holder, int position) {
        FetchStoreOrderList fetchorderref = fetchorderlist.get(position);
        String status = fetchorderref.getStatus();

        holder.header.setText(status);

        holder.layout_subheader_child_container.setVisibility(View.VISIBLE);


        //HEADER COLORS
        if(status.equals("FOR PAYMENT")) {
            holder.header_container.setBackground(ContextCompat.getDrawable(mContext, R.color.color_stickylist_darkorange));
        } else if (status.equals("FOR CANCELLATION") || status.equals("CANCELLED") || status.equals("FAILED")) {
            holder.header_container.setBackground(ContextCompat.getDrawable(mContext, R.color.color_stickylist_lightred));
        } else {
            holder.header_container.setBackground(ContextCompat.getDrawable(mContext, R.color.color_stickylist_blue));
        }

        //ROTATION OF HEADER ARROW
        if(holder.header_container.getRotation() == 90) {
            holder.header_arrow.setRotation(360);
        }
        else if(holder.header_container.getRotation() == 360) {
            holder.header_arrow.setRotation(90);
        }
        else {
            holder.header_arrow.setRotation(90);
        }
        holder.rv_subheader_child.setNestedScrollingEnabled(false);
        holder.rv_subheader_child.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rv_subheader_child.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider)));
        mAdapter = new GKStoreChildHistoryAdapter(mContext, fetchorderlist);
        holder.rv_subheader_child.setAdapter(mAdapter);

        mAdapter.updateData(mdb.getAllGKStoreTransactionsChildStatus(mdb, status));
    }

    @Override
    public int getItemCount() {
        return fetchorderlist.size();
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
            switch (view.getId()) {
                case R.id.header_container:
                    if (layout_subheader_child_container.getVisibility() == View.VISIBLE) {
                        layout_subheader_child_container.setVisibility(View.GONE);
                        header_container.requestFocus();
                        header_arrow.setRotation(360);
                    } else {
                        layout_subheader_child_container.setVisibility(View.VISIBLE);
                        header_container.requestFocus();
                        header_arrow.setRotation(90);
                    }
                    break;
            }
        }
    }
}
