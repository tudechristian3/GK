package com.goodkredit.myapplication.adapter.paramount;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 2/5/2018.
 */

public class ParamountDialogAdapter extends RecyclerView.Adapter<ParamountDialogAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mGridData;
    private List<String> newData;

    private Context getContext() {
        return mContext;
    }

    public ParamountDialogAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    public void setDialogData(final List<String> mGridData) {
        int startPos = this.mGridData.size() + 1;
        this.mGridData.clear();
        this.mGridData.addAll(mGridData);
        notifyItemRangeInserted(startPos, mGridData.size());
    }

    public void clear() {
        int startPos = this.mGridData.size();
        this.mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_vehicle_types_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String name = mGridData.get(position);
        if (name != null) {
            holder.txvName.setText(name);
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txvName;

        public MyViewHolder(final View itemView) {
            super(itemView);
            txvName = (TextView) itemView.findViewById(R.id.txvName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        String name = mGridData.get(position);
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, name);
                        }
                    }
                }
            });
        }
    }

    //Do search
    public void filter(final String searchText) {

        newData = new ArrayList<>();

        //dispatch searching to a different thread
        new Thread(new Runnable() {
            @Override
            public void run() {

                String charString = searchText.trim().toLowerCase();

                //clear filter list
                newData.clear();

                //if no search apply filter list to original list
                if (charString.length() > 0) {
                    //Iterate in the original list to find matches
                    for (String name : mGridData) {
                        try {
                            if (name != null && name != "" &&
                                    name.trim().substring(0, charString.length()).toLowerCase().equals(charString)) {
                                newData.add(name);
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                    mGridData = newData;
                }

                //Set on UI Thread
                ((Activity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

//        return newData;
    }

    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, String name);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
