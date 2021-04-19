package com.goodkredit.myapplication.adapter.gkstore;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.FetchStoreOrderDetails;
import com.goodkredit.myapplication.model.FetchStoreOrderList;
import com.goodkredit.myapplication.model.GKStoreImage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class ViewGKStoreHistoryAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context mContext;
    private List<FetchStoreOrderList> fetchorderlist = new ArrayList<>();
    private List<FetchStoreOrderList> fetchorderdatelist = new ArrayList<>();
//    private List<FetchStoreOrderDetails> fetchorderimagelist = new ArrayList<>();
    private List<GKStoreImage> fetchorderimagelist = new ArrayList<>();
    private ViewGKStoreOrderDetailsHistoryAdapter mAdapter;
    private LayoutInflater layoutInflater;
    private DatabaseHandler mdb;
    private String passedsearchvalue = "";

    public ViewGKStoreHistoryAdapter(Context context, List<FetchStoreOrderList> arrayList) {
        this.mContext = context;
        this.fetchorderlist = arrayList;
        layoutInflater = LayoutInflater.from(mContext);
        mdb = new DatabaseHandler(mContext);
    }

    public void updateData(List<FetchStoreOrderList> newlist) {
        this.fetchorderlist = newlist;
        this.fetchorderdatelist = mdb.getAllGKStoreTransactionsStatus(mdb);
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.fetchorderlist.clear();
        this.notifyDataSetChanged();
    }

    static class HeaderViewHolder {
        TextView header;
        ImageView header_arrow;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        HeaderViewHolder holder;

//        final String date = fetchorderlist.get(position).getDateTimein();
        final String status =  "" + fetchorderlist.get(position).getStatus();

        if (view == null) {
            holder = new HeaderViewHolder();
            view = layoutInflater.inflate(R.layout.newstickylistheader, parent, false);
            holder.header = (TextView) view.findViewById(R.id.header);
            holder.header_arrow = (ImageView) view.findViewById(R.id.header_arrow);
            holder.header_arrow.setRotation(90);

            view.setTag(holder);
        } else {
            holder = (HeaderViewHolder) view.getTag();
        }

        try {
//            holder.header.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(date)));
            holder.header.setText(status);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public long getHeaderId(int position) {
    //  return CommonFunctions.getLongFromDate(CommonFunctions.convertDate(fetchorderlist.get(position).getDateTimein()));
    return fetchorderlist.get(position).getStatus().hashCode();
    }

    @Override
    public int getCount() {
        return fetchorderlist.size();
    }

    @Override
    public FetchStoreOrderList getItem(int position) {
        return fetchorderlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return fetchorderlist.get(position).hashCode();
    }

    private class ViewHolder {
        TextView txtOrderTxnID;
        TextView txvDate;
        TextView txtTime;
        TextView txtaddress;
        TextView txtStatus;
        TextView txttotalamount;

        //Single Photo
        LinearLayout singlephotolayout;
        ImageView single_photo_one;

        //Double Photo
        LinearLayout doublephotolayout;
        ImageView dbl_photo_one;
        ImageView dbl_photo_two;

        //Multi Photo
        LinearLayout multiphotolayout;
        ImageView multi_photo_one;
        ImageView multi_photo_two;
        ImageView multi_photo_three;
        ImageView multi_photo_four;

        //Container for border
        LinearLayout mainitemscontainer;
    }

    private void convertStringtoList(String orderdetails) throws JSONException {
        ArrayList<FetchStoreOrderDetails> fetchorderlist = new ArrayList<>();

        List<GKStoreImage> photoobject = new ArrayList<>();

        fetchorderlist = new Gson().fromJson(orderdetails, new TypeToken<List<FetchStoreOrderDetails>>() {
        }.getType());

        for (FetchStoreOrderDetails gkstoreHistoryList : fetchorderlist) {
            String productimage = gkstoreHistoryList.getProductImage();
            photoobject.add(new GKStoreImage(productimage));
        }

        fetchorderimagelist = photoobject;
    }

    private void setUpImageLayout(ViewHolder holder, View view) {
        //Single Photo
        holder.singlephotolayout = (LinearLayout) view.findViewById(R.id.singlephotolayout);
        holder.single_photo_one = (ImageView) view.findViewById(R.id.single_photo_one);

        //Double Photo
        holder.doublephotolayout = (LinearLayout) view.findViewById(R.id.doublephotolayout);
        holder.dbl_photo_one = (ImageView) view.findViewById(R.id.dbl_photo_one);
        holder.dbl_photo_two = (ImageView) view.findViewById(R.id.dbl_photo_two);

        //Multi Photo
        holder.multiphotolayout = (LinearLayout) view.findViewById(R.id.multiphotolayout);
        holder.multi_photo_one = (ImageView) view.findViewById(R.id.multi_photo_one);
        holder.multi_photo_two = (ImageView) view.findViewById(R.id.multi_photo_two);
        holder.multi_photo_three = (ImageView) view.findViewById(R.id.multi_photo_three);
        holder.multi_photo_four = (ImageView) view.findViewById(R.id.multi_photo_four);

        int total_items = fetchorderimagelist.size();

        if (total_items == 1) {
            holder.singlephotolayout.setVisibility(View.VISIBLE);
            holder.doublephotolayout.setVisibility(View.GONE);
            holder.multiphotolayout.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(fetchorderimagelist.get(0).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(holder.single_photo_one);
        } else if (total_items == 2) {
            holder.singlephotolayout.setVisibility(View.GONE);
            holder.doublephotolayout.setVisibility(View.VISIBLE);
            holder.multiphotolayout.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(fetchorderimagelist.get(0).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(holder.dbl_photo_one);
            Glide.with(mContext)
                    .load(fetchorderimagelist.get(1).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(holder.dbl_photo_two);
        } else if (total_items == 3) {
            holder.singlephotolayout.setVisibility(View.GONE);
            holder.doublephotolayout.setVisibility(View.VISIBLE);
            holder.multiphotolayout.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(fetchorderimagelist.get(0).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(holder.dbl_photo_one);
            Glide.with(mContext)
                    .load(fetchorderimagelist.get(1).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(holder.dbl_photo_two);
        } else if (total_items > 3) {
            holder.singlephotolayout.setVisibility(View.GONE);
            holder.doublephotolayout.setVisibility(View.GONE);
            holder.multiphotolayout.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(fetchorderimagelist.get(0).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(holder.multi_photo_one);
            Glide.with(mContext)
                    .load(fetchorderimagelist.get(1).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(holder.multi_photo_two);
            Glide.with(mContext)
                    .load(fetchorderimagelist.get(2).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(holder.multi_photo_three);
            Glide.with(mContext)
                    .load(fetchorderimagelist.get(3).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(holder.multi_photo_four);
        } else if (total_items == 0) {
            holder.singlephotolayout.setVisibility(View.VISIBLE);
            holder.doublephotolayout.setVisibility(View.GONE);
            holder.multiphotolayout.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load("")
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(holder.single_photo_one);
        }
    }

    //Change the gravity depending on the line
    private void setChangeGravity(final ViewHolder holder) {
        holder.txtStatus.post(new Runnable() {
            @Override
            public void run() {
                int lines = holder.txtStatus.getLineCount();
                if(lines >= 2) {
                    holder.txtStatus.setGravity(Gravity.CENTER);
                }
                else {
                    holder.txtStatus.setGravity(Gravity.END);
                }
                holder.txtStatus.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        try {
            String strOrderTxnID = fetchorderdatelist.get(position).getOrderTxnID();
            String strDate = fetchorderdatelist.get(position).getDateTimeIN();
            String strTime = fetchorderdatelist.get(position).getDateTimeIN();
            String strAddress = fetchorderdatelist.get(position).getCustomerAddress();
            String strStatus = fetchorderdatelist.get(position).getStatus();
            double totalAmount = fetchorderdatelist.get(position).getTotalAmount();
            String orderdetails = fetchorderdatelist.get(position).getOrderDetails();

            try {
                convertStringtoList(orderdetails);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (view == null) {
                holder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.item_gk_store_history, parent, false);

                holder.txtOrderTxnID = (TextView) view.findViewById(R.id.txtOrderTxnID);
                holder.txvDate = (TextView) view.findViewById(R.id.txvDate);
                holder.txtTime = (TextView) view.findViewById(R.id.txtTime);
                holder.txtaddress = (TextView) view.findViewById(R.id.txtaddress);
                holder.txtStatus = (TextView) view.findViewById(R.id.txtStatus);
                holder.txttotalamount = (TextView) view.findViewById(R.id.txttotalamount);


                //Single Photo
                holder.singlephotolayout = (LinearLayout) view.findViewById(R.id.singlephotolayout);
                holder.single_photo_one = (ImageView) view.findViewById(R.id.single_photo_one);

                //Double Photo
                holder.doublephotolayout = (LinearLayout) view.findViewById(R.id.doublephotolayout);
                holder.dbl_photo_one = (ImageView) view.findViewById(R.id.dbl_photo_one);
                holder.dbl_photo_two = (ImageView) view.findViewById(R.id.dbl_photo_two);

                //Multi Photo
                holder.multiphotolayout = (LinearLayout) view.findViewById(R.id.multiphotolayout);
                holder.multi_photo_one = (ImageView) view.findViewById(R.id.multi_photo_one);
                holder.multi_photo_two = (ImageView) view.findViewById(R.id.multi_photo_two);
                holder.multi_photo_three = (ImageView) view.findViewById(R.id.multi_photo_three);
                holder.multi_photo_four = (ImageView) view.findViewById(R.id.multi_photo_four);

                holder.mainitemscontainer = (LinearLayout) view.findViewById(R.id.mainitemscontainer);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            setUpImageLayout(holder, view);
//            setChangeGravity(holder);

            holder.txtOrderTxnID.setText("Ord Txn# ".concat(strOrderTxnID));
            holder.txvDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(strDate)));
            holder.txtTime.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(strTime)));
            holder.txtaddress.setText(CommonFunctions.replaceEscapeData(strAddress));
            holder.txtStatus.setText(strStatus);

            if(strStatus.equals("COMPLETED")) {
                holder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_4A90E2));
                holder.mainitemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.color.colorwhite));
            }
            else if(strStatus.equals("FOR CANCELLATION") || strStatus.equals("CANCELLED")) {
                holder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorred));
                holder.mainitemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.color.colorwhite));
            }
            else if(strStatus.equals("FAILED") || strStatus.equals("TIMEOUT")) {
                holder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorred));
                holder.mainitemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.color.colorwhite));
            }
            else if(strStatus.equals("PENDING") || strStatus.equals("ON PROCESS")) {
                holder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_757575));
//                holder.mainitemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.border_red));
                holder.mainitemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.color.colorwhite));
            }
            else {
                holder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_757575));
                holder.mainitemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.color.colorwhite));
            }

            holder.txttotalamount.setText("₱" + CommonFunctions.currencyFormatter(String.valueOf(totalAmount)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
