package com.goodkredit.myapplication.adapter.gkstore.history;

import android.content.Context;
import android.content.res.ColorStateList;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gkstore.GKStoreHistoryDetailsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.FetchStoreOrderDetails;
import com.goodkredit.myapplication.model.FetchStoreOrderList;
import com.goodkredit.myapplication.model.GKStoreImage;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GKStoreChildHistoryAdapter extends RecyclerView.Adapter<GKStoreChildHistoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<FetchStoreOrderList> fetchorderlist = new ArrayList<>();
    private DatabaseHandler mdb;
    private List<GKStoreImage> fetchorderimagelist = new ArrayList<>();
    private String servicecode = "";
    private String borrowername = "";
    private String strmerchantlat = "";
    private String strmerchantlong = "";
    private String strmerchantaddress = "";

    public GKStoreChildHistoryAdapter(Context context, List<FetchStoreOrderList> arrayList) {
        mContext = context;
        fetchorderlist = arrayList;
        mdb = new DatabaseHandler(mContext);
    }

    public void updateData(List<FetchStoreOrderList> childlist) {
        fetchorderlist = childlist;
        notifyDataSetChanged();
    }

    public void clear() {
        fetchorderlist.clear();
        notifyDataSetChanged();
    }

    @Override
    public GKStoreChildHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gk_store_history,
                parent, false);
        GKStoreChildHistoryAdapter.MyViewHolder holder = new GKStoreChildHistoryAdapter.MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(GKStoreChildHistoryAdapter.MyViewHolder holder, int position) {
        FetchStoreOrderList fetchorderref = fetchorderlist.get(position);

        String strOrderTxnID = fetchorderref.getOrderTxnID();
        String strDate = fetchorderref.getDateTimeIN();
        String strTime = fetchorderref.getDateTimeIN();
        String strAddress = fetchorderref.getCustomerAddress();
        String strStatus = fetchorderref.getStatus();
        double totalAmount = fetchorderref.getTotalAmount();
        String orderdetails = fetchorderref.getOrderDetails();

        try {
            convertStringtoList(orderdetails);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setUpImageLayout(holder);

        holder.txtOrderTxnID.setText("Ord Txn# ".concat(strOrderTxnID));
        holder.txvDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(strDate)));
        holder.txtTime.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(strTime)));
        holder.txtaddress.setText(CommonFunctions.replaceEscapeData(strAddress));
        holder.txtStatus.setText(strStatus);

        if(strStatus.equals("COMPLETED")) {
            holder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_4A90E2));
            holder.mainitemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.color.colorwhite));
        } else if(strStatus.equals("FOR CANCELLATION") || strStatus.equals("CANCELLED")) {
            holder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorred));
            holder.mainitemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.color.colorwhite));
        } else if(strStatus.equals("FAILED") || strStatus.equals("TIMEOUT")) {
            holder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorred));
            holder.mainitemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.color.colorwhite));
        } else if(strStatus.equals("PENDING") || strStatus.equals("ON PROCESS")) {
            holder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_757575));
            holder.mainitemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.color.colorwhite));
        } else if(strStatus.equals("FOR PAYMENT")) {
            holder.txtOrderTxnID.setTextColor(ContextCompat.getColor(mContext, R.color.color_E0E0E0));
            holder.txvDate.setTextColor(ContextCompat.getColor(mContext, R.color.colorwhite));
            holder.txtTime.setTextColor(ContextCompat.getColor(mContext, R.color.colorwhite));
            holder.txtaddress.setTextColor(ContextCompat.getColor(mContext, R.color.color_E0E0E0));
            holder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_E0E0E0));
            holder.txttotalamount.setTextColor(ContextCompat.getColor(mContext, R.color.color_D3D3D3));

            ImageViewCompat.setImageTintList(holder.imv_arrow, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.color_E0E0E0)));

            holder.mainitemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_action_required_border));

        } else {
            holder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_757575));
            holder.mainitemscontainer.setBackground(ContextCompat.getDrawable(mContext, R.color.colorwhite));
        }

        holder.txttotalamount.setText("₱" + CommonFunctions.currencyFormatter(String.valueOf(totalAmount)));

        holder.mainitemscontainer.setOnClickListener(mainitemscontainerlistener);
        holder.mainitemscontainer.setTag(holder);
    }

    private View.OnClickListener mainitemscontainerlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final GKStoreChildHistoryAdapter.MyViewHolder holder = (MyViewHolder) v.getTag();

                FetchStoreOrderList fetchorderref = fetchorderlist.get(holder.getAdapterPosition());

                String ordertxnID = fetchorderref.getOrderTxnID();
                String firstname = fetchorderref.getCustomerFirstName();
                String lastname = fetchorderref.getCustomerLastName();
                String mobileno = fetchorderref.getCustomerMobileNumber();
                String deliverytype = fetchorderref.getExtra1();
                String address = fetchorderref.getCustomerAddress();
                String datetime = fetchorderref.getDateTimeIN();
                String grossamount = String.valueOf(fetchorderref.getTotalItemAmount());
                String totalamount = String.valueOf(fetchorderref.getTotalAmount());
                String status = String.valueOf(fetchorderref.getStatus());
                String customerservicecharge = String.valueOf(fetchorderref.getCustomerServiceCharge());
                String resellerdiscount = String.valueOf(fetchorderref.getResellerDiscount());
                String orderDetails = String.valueOf(fetchorderref.getOrderDetails());
                String gkstoreid = String.valueOf(fetchorderref.getGKStoreID());
                String messagefrompartner = String.valueOf(fetchorderref.getNotes2());
                String customeremailadd = String.valueOf(fetchorderref.getCustomerEmailAddress());
                String remarks = String.valueOf(fetchorderref.getNotes1());
                String merchantid = fetchorderref.getMerchantID();
                String latitude = fetchorderref.getLatitude();
                String longitude = fetchorderref.getLongitude();
                String orderdetails = fetchorderref.getOrderDetails();
                String otherdetails = fetchorderref.getCustomerOtherDetails();
                String strgkstoredeliverytype = fetchorderref.getExtra1();

                borrowername = PreferenceUtils.getStringPreference(mContext, "GKSTOREBORROWERNAME");
                strmerchantlat = PreferenceUtils.getStringPreference(mContext, "GKSTOREMERCHANTLAT");
                strmerchantlong = PreferenceUtils.getStringPreference(mContext, "GKSTOREMERCHANTLONG");
                strmerchantaddress = PreferenceUtils.getStringPreference(mContext, "GKSTOREMERCHANTADD");
                servicecode = PreferenceUtils.getStringPreference(mContext, "GKSTORESERVICECODE");

                GKStoreHistoryDetailsActivity.start(mContext, ordertxnID, firstname, lastname, mobileno, deliverytype,
                        address, datetime, grossamount, totalamount, status, customerservicecharge, resellerdiscount,
                        orderDetails, servicecode, gkstoreid, messagefrompartner, customeremailadd, remarks, merchantid,
                        latitude, longitude, borrowername, strgkstoredeliverytype, strmerchantlat, strmerchantlong,
                        strmerchantaddress, customerservicecharge, orderdetails, otherdetails
                );

            } catch(Exception e ) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public int getItemCount() {
        return fetchorderlist.size();
    }

    private void convertStringtoList(String orderdetails) throws JSONException {
        ArrayList<FetchStoreOrderDetails> fetchorderlist;
        if(orderdetails != null || !orderdetails.isEmpty()){
            List<GKStoreImage> photoobject = new ArrayList<>();

            fetchorderlist = new Gson().fromJson(orderdetails, new TypeToken<List<FetchStoreOrderDetails>>() {
            }.getType());

            if(fetchorderlist.size() > 0 ){
                for (FetchStoreOrderDetails gkstoreHistoryList : fetchorderlist) {
                    String productimage = gkstoreHistoryList.getProductImage();
                    photoobject.add(new GKStoreImage(productimage));
                }
            }
            fetchorderimagelist = photoobject;
        }

    }

    private void setUpImageLayout(GKStoreChildHistoryAdapter.MyViewHolder holder) {
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Container for Items
        TextView txtOrderTxnID;
        TextView txvDate;
        TextView txtTime;
        TextView txtaddress;
        TextView txtStatus;
        TextView txttotalamount;
        ImageView imv_arrow;

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


        public MyViewHolder(View view) {
            super(view);
            //Container for Items
            txtOrderTxnID = (TextView) view.findViewById(R.id.txtOrderTxnID);
            txvDate = (TextView) view.findViewById(R.id.txvDate);
            txtTime = (TextView) view.findViewById(R.id.txtTime);
            txtaddress = (TextView) view.findViewById(R.id.txtaddress);
            txtStatus = (TextView) view.findViewById(R.id.txtStatus);
            txttotalamount = (TextView) view.findViewById(R.id.txttotalamount);
            imv_arrow = (ImageView) view.findViewById(R.id.imv_arrow);

            //Single Photo
            singlephotolayout = (LinearLayout) view.findViewById(R.id.singlephotolayout);
            single_photo_one = (ImageView) view.findViewById(R.id.single_photo_one);

            //Double Photo
            doublephotolayout = (LinearLayout) view.findViewById(R.id.doublephotolayout);
            dbl_photo_one = (ImageView) view.findViewById(R.id.dbl_photo_one);
            dbl_photo_two = (ImageView) view.findViewById(R.id.dbl_photo_two);

            //Multi Photo
            multiphotolayout = (LinearLayout) view.findViewById(R.id.multiphotolayout);
            multi_photo_one = (ImageView) view.findViewById(R.id.multi_photo_one);
            multi_photo_two = (ImageView) view.findViewById(R.id.multi_photo_two);
            multi_photo_three = (ImageView) view.findViewById(R.id.multi_photo_three);
            multi_photo_four = (ImageView) view.findViewById(R.id.multi_photo_four);

            mainitemscontainer = (LinearLayout) view.findViewById(R.id.mainitemscontainer);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
