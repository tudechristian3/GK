package com.goodkredit.myapplication.adapter.paramount;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.ParamountVehicleSeriesMaker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 2/5/2018.
 */

public class ParamountVehicleDialogAdapter extends RecyclerView.Adapter<ParamountVehicleDialogAdapter.MyViewHolder> {
    private Context mContext;
    private List<ParamountVehicleSeriesMaker> mGridData;
    private List<ParamountVehicleSeriesMaker> newData;

    private Context getContext() {
        return mContext;
    }

    private boolean isMaker = false;
    private boolean isSeries = false;

    public ParamountVehicleDialogAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    public void setDialogData(final List<ParamountVehicleSeriesMaker> mGridData, boolean isMaker) {
        int startPos = this.mGridData.size() + 1;
        this.mGridData.clear();
        this.mGridData.addAll(mGridData);
        notifyItemRangeInserted(startPos, mGridData.size());
        this.isMaker = isMaker;
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
        ParamountVehicleSeriesMaker paramountVehicleSeriesMaker = mGridData.get(position);
        if (paramountVehicleSeriesMaker != null) {
            if (isMaker) {
                holder.txvName.setText(paramountVehicleSeriesMaker.getMaker());
            } else {
                holder.txvName.setText(paramountVehicleSeriesMaker.getSeries());
            }
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
                        if (position > -1) {
                            ParamountVehicleSeriesMaker paramountVehicleSeriesMaker = mGridData.get(position);
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemClick(itemView, paramountVehicleSeriesMaker);
                            }
                        }
                    }
                }
            });
        }
    }

    //Do search
    public void filter(final String searchText) {

        newData = new ArrayList<>();

        try{

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
                        for (ParamountVehicleSeriesMaker paramountVehicleSeriesMaker : mGridData) {
                            try {

                               if(paramountVehicleSeriesMaker != null){

                                   if (isMaker) {

                                       if (paramountVehicleSeriesMaker.getMaker() != null && paramountVehicleSeriesMaker.getMaker() != "" &&
                                               paramountVehicleSeriesMaker.getMaker().trim().substring(0, charString.length()).toLowerCase().equals(charString)) {
                                           newData.add(paramountVehicleSeriesMaker);
                                       }

                                   } else {

                                       if (paramountVehicleSeriesMaker.getSeries() != null && paramountVehicleSeriesMaker.getSeries() != "" &&
                                               paramountVehicleSeriesMaker.getSeries().trim().substring(0, charString.length()).toLowerCase().equals(charString)) {
                                           newData.add(paramountVehicleSeriesMaker);
                                       }

                                   }

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

        } catch (Exception e){
            e.printStackTrace();
        }

//        return newData;
    }

    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, ParamountVehicleSeriesMaker paramountVehicleSeriesMaker);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
