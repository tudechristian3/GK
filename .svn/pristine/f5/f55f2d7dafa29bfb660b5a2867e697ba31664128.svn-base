package com.goodkredit.myapplication.adapter.gkstore;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.FetchStoreOrderDetails;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoodApps on 08/03/2018.
 */

public class ViewGKStoreOrderDetailsHistoryAdapter extends RecyclerView.Adapter<ViewGKStoreOrderDetailsHistoryAdapter.MyViewHolder> {
    private Context mContext;
    private List<FetchStoreOrderDetails> fetchorderlist;
    private DatabaseHandler mdb;

    public ViewGKStoreOrderDetailsHistoryAdapter(Context context) {
        mContext = context;
        fetchorderlist = new ArrayList<>();
        mdb = new DatabaseHandler(mContext);
    }

    public void updateHistoryDetails(List<FetchStoreOrderDetails> mGridData) {
        this.fetchorderlist.clear();
        this.fetchorderlist = mGridData;
        notifyDataSetChanged();
    }

    public void clear() {
        int startPos = this.fetchorderlist.size();
        this.fetchorderlist.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @Override
    public ViewGKStoreOrderDetailsHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gk_store_history_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewGKStoreOrderDetailsHistoryAdapter.MyViewHolder holder, int position) {
        FetchStoreOrderDetails fetchorderref = fetchorderlist.get(position);
        //Normal Picture
        Glide.with(mContext)
                .load(fetchorderref.getProductImage())
                .apply(new RequestOptions()
                        .fitCenter())
                .into(holder.mLogoPic);

        //For XML Formatted Pictures
        try {
            convertXMLDetails(holder);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.txtProductName.setText(CommonFunctions.replaceEscapeData(fetchorderref.getProductName()));
        holder.txtPrice.setText("₱" + CommonFunctions.currencyFormatter(String.valueOf(fetchorderref.getActualPrice())));
        holder.txtQuantity.setText(String.valueOf(fetchorderref.getOrderQuantity()));
    }

    //Convert XML Details
    private void convertXMLDetails(MyViewHolder holder) throws JSONException {
        FetchStoreOrderDetails fetchorderref = fetchorderlist.get(holder.getAdapterPosition());
        String checkdata = fetchorderref.getProductImage();
        try {
            String count = parseXML(checkdata, "count");
            if (!count.equals("") && !count.equals(".") && !count.equals("NONE")) {
                for (int i = 0; i < Integer.parseInt(count); i++) {
                    String multiimageurl = parseXML(checkdata, String.valueOf(i));
                    if (i <= 0) {
                        //Normal Picture
                        Glide.with(mContext)
                                .load(multiimageurl)
                                .apply(new RequestOptions()
                                        .fitCenter())
                                .into(holder.mLogoPic);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Parse the XML Details
    private String parseXML(String data, String nametag) {
        String result = "";
        int startpoint;
        int endpoint;

        //getting the starting point
        startpoint = data.indexOf("<" + nametag + ">");
        //getting the endpoint
        endpoint = data.indexOf("</" + nametag + ">");
        if (startpoint == -1 || endpoint == -1) {
            //return empty
            result = "NONE";
        } else {
            int starttaglen = nametag.length() + 2;
            //returning the extracted data
            result = data.substring(startpoint + starttaglen, endpoint);
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return fetchorderlist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mLogoPic;
        TextView txtProductName;
        TextView txtPrice;
        TextView txtQuantity;

        public MyViewHolder(View itemView) {
            super(itemView);
            mLogoPic = itemView.findViewById(R.id.mLogoPic);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
        }
    }
}
