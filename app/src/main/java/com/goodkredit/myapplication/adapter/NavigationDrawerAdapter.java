package com.goodkredit.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.model.NavDrawerItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by Mary ANn on 7/28/2016.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    public static int mSelectedPosition = 0;

    private MyViewHolder mHolder;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        mSelectedPosition = 0;
    }


    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        mHolder = holder;
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());

        Glide.with(context)
                .load(current.getIcon())
                .into(holder.imageView);

//        holder.imageView.setImageResource(current.getIcon());

        holder.itemView.setSelected(mSelectedPosition == position);

        if (position == mSelectedPosition) {
            holder.itemView.setBackgroundColor(Color.parseColor("#237d92"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#29bed7"));
        }

        if (position == 7) {
            holder.new_icon.setVisibility(View.VISIBLE);
        } else {
            holder.new_icon.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        ImageView new_icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.navtitle);
            imageView = (ImageView) itemView.findViewById(R.id.icon);
            new_icon = (ImageView) itemView.findViewById(R.id.new_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redraw the old selection and the new
                    notifyItemChanged(mSelectedPosition);
                    mSelectedPosition = getLayoutPosition();
                    notifyItemChanged(mSelectedPosition);
                }
            });

        }
    }
}
