package com.goodkredit.myapplication.adapter.gkservices;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.airtime.AirtimePurchase;
import com.goodkredit.myapplication.activities.billspayment.BillsPaymentActivity;
import com.goodkredit.myapplication.activities.billspayment.BillsPaymentBillerDetailsActivity;
import com.goodkredit.myapplication.activities.gkstore.GKStoreDetailsActivity;
import com.goodkredit.myapplication.activities.paramount.ParamountInsuranceActivity;
import com.goodkredit.myapplication.activities.payviaqrcode.PayViaQRCodeActivity;
import com.goodkredit.myapplication.activities.prepaidrequest.VoucherPrepaidRequestActivity;
import com.goodkredit.myapplication.activities.promo.ScanPromoActivity;
import com.goodkredit.myapplication.activities.reloadretailer.PurchaseRetailerLoadActivity;
import com.goodkredit.myapplication.activities.csbrewards.CSBRewardsActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.prepaidrequest.VirtualVoucherProductFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 11/2/2017.
 */

public class GKServicesSearchResultAdapter extends RecyclerView.Adapter<GKServicesSearchResultAdapter.GKSViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<GKService> gkServiceList = new ArrayList<>();
    private DatabaseHandler mDb;

    public GKServicesSearchResultAdapter(Context context, List<GKService> gkServiceList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.gkServiceList = gkServiceList;
        mDb = new DatabaseHandler(context);
    }

    @Override
    public GKSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_search_gk_services, parent, false);
        GKSViewHolder holder = new GKSViewHolder(view);
        return holder;
    }

    public void update(List<GKService> gkServiceList) {
        this.gkServiceList.clear();
        this.gkServiceList = gkServiceList;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(GKSViewHolder holder, int position) {
        try {
            GKService service = gkServiceList.get(position);

            Glide.with(context)
                    .load(service.getLogo())
                    .into(holder.service_logo);

            holder.service_name.setText(service.getServiceName());

            holder.itemView.setTag(service);
            holder.itemView.setOnClickListener(itemClick);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener itemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {

                GKService service = (GKService) v.getTag();
                switch (service.getServiceCode()) {
                    case "BUYPREPAIDLOAD": {
                        Intent intent = new Intent(context, AirtimePurchase.class);
                        intent.putExtra("keyName", "value");
                        context.startActivity(intent);
                        break;
                    }
                    case "PAYBILLS": {
                        Intent intent = new Intent(context, BillsPaymentActivity.class);
                        intent.putExtra("PROCESS", "");
                        context.startActivity(intent);
                        break;
                    }
                    case "SMARTWALLET": {
                        Intent intent = new Intent(context, PurchaseRetailerLoadActivity.class);
                        intent.putExtra("keyName", "value");
                        context.startActivity(intent);
                        break;
                    }
                    case "CSB": {
                        CSBRewardsActivity.start(context, CSBRewardsActivity.CSB_REWARDS, service);
                        break;
                    }

                    case "PARAMOUNT": {
                        ParamountInsuranceActivity.start(context, 1, null);
                        break;
                    }

                    case "PHILCARE": {
                        Intent intent = new Intent(context, GKStoreDetailsActivity.class);
                        intent.putExtra("PROCESS", "");
//                        intent.putExtra("GKSERVICE_OBJECT", service);
                        context.startActivity(intent);
                        break;
                    }
                    case "PAYVIAQR": {
                        Intent intent = new Intent(context, PayViaQRCodeActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "PROMO": {
                        ScanPromoActivity.start(context);
                        break;
                    }

                    case "BUY_VOUCHER": {
                        VoucherPrepaidRequestActivity.start(context, 0, VirtualVoucherProductFragment.BY_REQUEST_TO_SPONSOR, 0);
                        break;
                    }

                    default: {
                        Cursor cursor = mDb.getSingleBiller(mDb, service.getServiceCode().toUpperCase());
                        CommonFunctions.log(String.valueOf(cursor.getCount() + " cursor size BAN"));
                        if (cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {

                                String billcode = cursor.getString(cursor.getColumnIndex("BillerCode"));
                                String spbillcode = cursor.getString(cursor.getColumnIndex("ServiceProviderBillerCode"));
                                String billname = cursor.getString(cursor.getColumnIndex("BillerName"));

                                Intent intent = new Intent(context, BillsPaymentBillerDetailsActivity.class);
                                intent.putExtra("BILLCODE", billcode);
                                intent.putExtra("SPBILLCODE", spbillcode);
                                intent.putExtra("SPBILLERACCOUNTNO", "");
                                intent.putExtra("BILLNAME", billname);
                                intent.putExtra("FROM", "BILLERS");
                                context.startActivity(intent);
                            }
                        } else {
                            ((BaseActivity) context).showError();
                        }
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public int getItemCount() {
        return gkServiceList.size();
    }


    public class GKSViewHolder extends RecyclerView.ViewHolder {
        ImageView service_logo;
        TextView service_name;

        public GKSViewHolder(View itemView) {
            super(itemView);
            service_logo = (ImageView) itemView.findViewById(R.id.gk_services_logo);
            service_name = (TextView) itemView.findViewById(R.id.gk_services_name);
        }
    }
}
