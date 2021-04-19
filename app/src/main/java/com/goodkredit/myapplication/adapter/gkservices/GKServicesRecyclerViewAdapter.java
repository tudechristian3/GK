package com.goodkredit.myapplication.adapter.gkservices;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.airtime.AirtimePurchase;
import com.goodkredit.myapplication.activities.billspayment.BillsPaymentActivity;
import com.goodkredit.myapplication.activities.billspayment.BillsPaymentBillerDetailsActivity;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.activities.csbrewards.CSBRewardsActivity;
import com.goodkredit.myapplication.activities.dropoff.DropOffActivity;
import com.goodkredit.myapplication.activities.egame.EGameActivity;
import com.goodkredit.myapplication.activities.fairchild.FairChildAttendanceActivity;
import com.goodkredit.myapplication.activities.freesms.FreeSMSActivity;
import com.goodkredit.myapplication.activities.gkearn.GKEarnHomeActivity;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoDashboardActivity;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoRedirectionActivity;
import com.goodkredit.myapplication.activities.gkservices.AllServicesActivity;
import com.goodkredit.myapplication.activities.gkservices.medpadala.MedPadalaActivity;
import com.goodkredit.myapplication.activities.gkstore.GKStoreDetailsActivity;
import com.goodkredit.myapplication.activities.loadmessenger.ConnectedAccountsActivity;
import com.goodkredit.myapplication.activities.mdp.MDPActivity;
import com.goodkredit.myapplication.activities.paramount.ParamountInsuranceActivity;
import com.goodkredit.myapplication.activities.payviaqrcode.PayViaQRCodeActivity;
import com.goodkredit.myapplication.activities.petron.PetronValueCardActivity;
import com.goodkredit.myapplication.activities.prepaidrequest.VoucherPrepaidRequestActivity;
import com.goodkredit.myapplication.activities.projectcoop.ProjectCoopActivity;
import com.goodkredit.myapplication.activities.promo.ScanPromoActivity;
import com.goodkredit.myapplication.activities.reloadretailer.PurchaseRetailerLoadActivity;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniActivity;
import com.goodkredit.myapplication.activities.sendmoney.SendMoneyActivity;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.activities.uno.UnoRewardsActivity;
import com.goodkredit.myapplication.activities.voting.VotesActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.SectionOrRow;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.prepaidrequest.VirtualVoucherProductFragment;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import q.rorbin.badgeview.QBadgeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 11/29/2017.
 */

public class GKServicesRecyclerViewAdapter extends RecyclerView.Adapter<GKServicesRecyclerViewAdapter.GKSViewHolder> {

    Context context;
    private LayoutInflater inflater;
    private List<GKService> gkServiceList;
    private boolean mIsSearched;

    ArrayList<SectionOrRow> sectionOrRows;

    DatabaseHandler mDb;

    String title = "";
    String message = "";
    String niggaButton = "";
    String whiteButton = "";


    private String imei;
    private String sessionid;
    private String borrowerid;
    private String userid;
    private int adapterPosition;
    private int lastSelectedPosition = -1;
    private int count = 0;

    //NEW VARIABLES FOR SECURITY
    private String menuIndex;
    private String menuAuthenticatioid;
    private String menuKeyEncryption;
    private String menuParam;

    private String servicecode;
    private String action;

    private ViewGroup viewGroup;


//    private QBadgeView badgeeJump;

    public GKServicesRecyclerViewAdapter(Context context, boolean isSearched) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.gkServiceList = new ArrayList<>();
        mIsSearched = isSearched;
        mDb = new DatabaseHandler(this.context);
    }

    public GKServicesRecyclerViewAdapter(Context context, List<GKService> gkServiceList, boolean isSearched) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.gkServiceList = new ArrayList<>();
        this.gkServiceList = gkServiceList;
        mIsSearched = isSearched;
        mDb = new DatabaseHandler(this.context);
    }

    public void updateData(List<GKService> gkServiceList) {
        this.gkServiceList.clear();
        this.gkServiceList.addAll(gkServiceList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GKSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gk_services_new, parent, false);
        viewGroup = parent;
        return new GKSViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull GKSViewHolder holder, int position) {

        GKService service = gkServiceList.get(position);

      

        if (service.getServiceCode().equals("ALLSERVICES")) {
            Glide.with(context)
                    .load(R.drawable.allappslogos)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().override(80, 80))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.service_logo);

            holder.service_name.setVisibility(View.VISIBLE);
            holder.service_name.setText(CommonFunctions.replaceEscapeData(service.getServiceName()).toUpperCase());
            holder.service_name.setTextColor(Color.WHITE);

            V2Utils.overrideFonts(context, V2Utils.ROBOTO_BOLD, holder.itemView);
        } else {
            Glide.with(context)
                    .load(service.getLogo())
                    .override(80, 80)
                    .centerCrop()
                    .error(R.drawable.broken_image_placeholder)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.service_logo);

            holder.service_name.setVisibility(View.VISIBLE);
            holder.service_name.setText(CommonFunctions.replaceEscapeData(service.getServiceName()));
            holder.service_name.setTextColor(Color.WHITE);

        }

        if (service.getColor() != null) {
            if (service.getColor().equals("") || service.getColor().equals(".")) {
                holder.gks_item2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.service_name.setTextColor(Color.parseColor("#000000"));
            } else {

                if(service.getColor().equals("#FFFFFF")){
                    holder.service_name.setTextColor(Color.parseColor("#000000"));
                }else{
                    holder.service_name.setTextColor(Color.parseColor("#FFFFFF"));
                }
                holder.gks_item2.setBackgroundColor(Color.parseColor(service.getColor()));
                holder.gks_item_new_cardview.setCardBackgroundColor(Color.parseColor(service.getColor()));
            }
        } else {
            holder.gks_item2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.service_name.setTextColor(Color.parseColor("#000000"));
        }

        if (mDb.getBadgeForServiceCode(mDb, service.getServiceCode()) > 0) {
            holder.badgeeJump.setBadgeNumber(mDb.getBadgeForServiceCode(mDb, service.getServiceCode()));
        } else {
            holder.badgeeJump.setBadgeNumber(0);
        }

        holder.gks_item2.setOnClickListener(itemListener);
        holder.gks_item2.setTag(service);

        holder.gks_item2.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onLongClick(View v) {
                if (!mIsSearched) {
                    if (!service.getCategory().equals("MAIN")) {
                        if (count == 0) {
                            holder.item_custom_row_cancel.setVisibility(View.VISIBLE);
                            holder.badgeeJump.setVisibility(View.GONE);
                            count++;
                            lastSelectedPosition = holder.getAdapterPosition();

                        } else {
                            if (lastSelectedPosition == holder.getAdapterPosition()) {
                                if (holder.item_custom_row_cancel.isShown()) {
                                    holder.item_custom_row_cancel.setVisibility(View.GONE);
                                    holder.badgeeJump.setVisibility(View.VISIBLE);
                                    count = 0;
                                }
                            }

                        }

                    }
                }
                return true;
            }
        });
        holder.item_custom_row_cancel.setTag(holder.getAdapterPosition());
        holder.item_custom_row_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                 if(CommonFunctions.getConnectivityStatus(context) <= 0){
                     Toast.makeText(context, " No internet connection.", Toast.LENGTH_SHORT).show();
                 }else{
                     if (holder.item_custom_row_cancel.isShown()) {
                         title = "Remove " + service.getServiceName() + " " +
                                 "from Services Menu?";
                         message = "This will only be removed on the service menu but you can still add it and see it " +
                                 "by clicking the all apps.";
                         niggaButton = "Cancel";
                         whiteButton = "Remove";

                         showCustomDialog(context, title, message, niggaButton, whiteButton, service, pos);
                     } else {
                         holder.item_custom_row_cancel.setVisibility(View.GONE);
                     }
                 }
            }
        });


    }

    public void showCustomDialog(final Context context, String title, String message, String nigga, String white, GKService service, int position) {

        adapterPosition = position;

        View dialogView = LayoutInflater.from(context).inflate(R.layout.allservices_dialog, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        TextView titleTv = dialogView.findViewById(R.id.title_allservices);
        TextView messageTv = dialogView.findViewById(R.id.message_allservices);
        titleTv.setText(title);
        messageTv.setText(message);

        Button niggaB = dialogView.findViewById(R.id.cancelBtn_allservices);
        Button positiveB = dialogView.findViewById(R.id.positiveBtn_allservices);

        positiveB.setTextColor(Color.parseColor("#E25252"));

        niggaB.setText(nigga);
        positiveB.setText(white);

        AlertDialog alertDialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        } else {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        alertDialog.show();

        niggaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        positiveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                gkServicesMenu(service.getServiceCode(), "remove");

            }
        });

    }

    void removeItem(int position) {
        gkServiceList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, gkServiceList.size());
    }

    private View.OnClickListener itemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                GKService service = (GKService) v.getTag();

                if (service.getServiceType().equals("GK STORE")) {
                    switch (service.getServiceType()) {
                        case "GK STORE": {
                            Intent intent = new Intent(context, GKStoreDetailsActivity.class);
                            intent.putExtra("GKSERVICE_OBJECT", service);
                            context.startActivity(intent);
                            break;
                        }
                    }
                } else if (service.getServiceType().equals("SCHOOL MINI")) {
                    switch (service.getServiceType()) {
                        case "SCHOOL MINI": {
                            PreferenceUtils.removePreference(context, PreferenceUtils.KEY_SCSERVICECODE);
                            PreferenceUtils.removePreference(context, PreferenceUtils.KEY_SCMERCHANTID);
                            PreferenceUtils.removePreference(context, PreferenceUtils.KEY_SCMERCHANTSTATUS);

                            PreferenceUtils.saveStringPreference(context, PreferenceUtils.KEY_SCSERVICECODE, service.getServiceCode());
                            PreferenceUtils.saveStringPreference(context, PreferenceUtils.KEY_SCMERCHANTID, service.getMerchantID());
                            PreferenceUtils.saveStringPreference(context, PreferenceUtils.KEY_SCMERCHANTSTATUS, service.getGKStoreStatus());

                            Bundle args = new Bundle();
                            SchoolMiniActivity.start(context, 0, args);
                            break;
                        }
                    }
                } else if (service.getServiceType().equals("VOTES")) {
                    switch (service.getServiceType()) {
                        case "VOTES": {
                            PreferenceUtils.removePreference(context, "GKServiceCode");
                            PreferenceUtils.removePreference(context, "GKMerchantID");
                            PreferenceUtils.removePreference(context, "GKMerchantStatus");

                            PreferenceUtils.saveStringPreference(context, "GKServiceCode", service.getServiceCode());
                            PreferenceUtils.saveStringPreference(context, "GKMerchantID", service.getMerchantID());
                            PreferenceUtils.saveStringPreference(context, "GKMerchantStatus", service.getGKStoreStatus());

                            Bundle args = new Bundle();
                            VotesActivity.start(context, 0, args);
                            break;
                        }
                    }
                } else if (service.getServiceType().equals("COOP")) {
                    switch (service.getServiceType()) {
                        case "COOP": {
                            if (service.getServiceCode().equals("CPMPC")) {
                                PreferenceUtils.removePreference(context, "projectcoopservicecode");
                                PreferenceUtils.saveStringPreference(context, "projectcoopservicecode", service.getServiceCode());
                                PreferenceUtils.removePreference(context, "projectcoopmerchantid");
                                PreferenceUtils.saveStringPreference(context, "projectcoopmerchantid", service.getMerchantID());
                                ProjectCoopActivity.start(context, 1, null);
                            } else {
                                PreferenceUtils.removePreference(context, "GKServiceCode");
                                PreferenceUtils.removePreference(context, "GKMerchantID");
                                PreferenceUtils.removePreference(context, "GKMerchantStatus");

                                PreferenceUtils.saveStringPreference(context, "GKServiceCode", service.getServiceCode());
                                PreferenceUtils.saveStringPreference(context, "GKMerchantID", service.getMerchantID());
                                PreferenceUtils.saveStringPreference(context, "GKMerchantStatus", service.getGKStoreStatus());

                                PreferenceUtils.removePreference(context, "GKSERVICE_OBJECT");
                                PreferenceUtils.saveGKServicesPreference(context, "GKSERVICE_OBJECT", service);

                                CoopAssistantHomeActivity.start(context, 0, null);
                            }
                            break;
                        }
                    }
                } else {
                    switch (service.getServiceCode()) {
                        case "BUYPREPAIDLOAD": {
                            PreferenceUtils.removePreference(context, "prepaidloading_service_code");
                            PreferenceUtils.saveStringPreference(context, "prepaidloading_service_code", service.getServiceCode());

                            Intent intent = new Intent(context, AirtimePurchase.class);
                            intent.putExtra("keyName", "value");
                            context.startActivity(intent);
                            break;
                        }

                        case "PAYBILLS": {
                            Intent intent = new Intent(context, BillsPaymentActivity.class);
                            intent.putExtra("PROCESS", "");
                            intent.putExtra("GKSERVICE_OBJECT", service);
                            context.startActivity(intent);
                            break;
                        }

                        case "SMARTWALLET": {
                            Intent intent = new Intent(context, PurchaseRetailerLoadActivity.class);
                            intent.putExtra("keyName", "value");
                            intent.putExtra("GKSERVICE_OBJECT", service);
                            context.startActivity(intent);
                            break;
                        }

                        case "CSB": {
                            CSBRewardsActivity.start(context, CSBRewardsActivity.CSB_REWARDS, service);
                            break;
                        }
                        case "PARAMOUNT": {

                            if (mDb != null) {
                                mDb.truncateTable(mDb, DatabaseHandler.PARAMOUNT_QUEUE);
                            }

                            PreferenceUtils.removePreference(context, "paramount_service_code");
                            PreferenceUtils.saveStringPreference(context, "paramount_service_code", service.getServiceCode());

                            PreferenceUtils.removePreference(context, "paramount_merchantid_01");
                            PreferenceUtils.removePreference(context, "paramount_merchantid_02");
                            PreferenceUtils.removePreference(context, "paramount_merchantid_03");
                            PreferenceUtils.removePreference(context, "paramount_merchantid_04");

                            String[] paramountarr = service.getMerchantID().split(":::");

                            if (paramountarr.length == 4) {

                                PreferenceUtils.saveStringPreference(context, "paramount_merchantid_01", paramountarr[0]);
                                PreferenceUtils.saveStringPreference(context, "paramount_merchantid_02", paramountarr[1]);
                                PreferenceUtils.saveStringPreference(context, "paramount_merchantid_03", paramountarr[2]);
                                PreferenceUtils.saveStringPreference(context, "paramount_merchantid_04", paramountarr[3]);

                                ParamountInsuranceActivity.start(context, 1, null);

                            } else {
                                ((BaseActivity) context).showToast("Merchant Code for Paramount Services is INVALID. Please contact Administrator.", GlobalToastEnum.NOTICE);
                            }

                            break;
                        }
                        case "PETRON": {
                            PetronValueCardActivity.start(context, 1, null);
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
                        case "MEDPADALA": {
                            MedPadalaActivity.start(context, service);
                            break;
                        }
                        case "SKYCABLE": {

                            PreferenceUtils.removePreference(context, "skycableservicecode");
                            PreferenceUtils.saveStringPreference(context, "skycableservicecode", service.getServiceType());

                            PreferenceUtils.removePreference(context, "skycablemerchantidnewapplication");
                            PreferenceUtils.removePreference(context, "skycablemerchantidppv");

                            String[] skycablearr = service.getMerchantID().split(":::");

                            if (skycablearr.length == 2) {

                                PreferenceUtils.saveStringPreference(context, "skycablemerchantidnewapplication", skycablearr[0]);
                                PreferenceUtils.saveStringPreference(context, "skycablemerchantidppv", skycablearr[1]);

                                if (mDb != null) {

                                    Cursor cursor = mDb.getSingleBiller(mDb, service.getServiceCode().toUpperCase());
                                    if (cursor.getCount() > 0) {
                                        while (cursor.moveToNext()) {
                                            String billcode = cursor.getString(cursor.getColumnIndex("BillerCode"));
                                            String spbillcode = cursor.getString(cursor.getColumnIndex("ServiceProviderBillerCode"));
                                            String billname = cursor.getString(cursor.getColumnIndex("BillerName"));

                                            PreferenceUtils.removePreference(context, "skycablebillcode");
                                            PreferenceUtils.saveStringPreference(context, "skycablebillcode", billcode);
                                            PreferenceUtils.removePreference(context, "skycablespbillcode");
                                            PreferenceUtils.saveStringPreference(context, "skycablespbillcode", spbillcode);
                                            PreferenceUtils.removePreference(context, "skycablebillname");
                                            PreferenceUtils.saveStringPreference(context, "skycablebillname", billname);
                                        }
                                    }

                                }

                                SkyCableActivity.start(context, 1, null);
                            } else {
                                ((BaseActivity) context).showToast("Merchant Code for Skycable Services is INVALID. Please contact Administrator.", GlobalToastEnum.NOTICE);
                            }

                            break;
                        }
                        case "SENDMONEY": {
                            SendMoneyActivity.start(context, service);
                            break;
                        }
                        case "GKNEGOSYO": {
                            if (!PreferenceUtils.getStringPreference(context, PreferenceUtils.KEY_DISTRIBUTORID).equals(".") &&
                                    !PreferenceUtils.getStringPreference(context, PreferenceUtils.KEY_RESELLER).equals(".")) {

                                Logger.debug("okhttp","DISTRIBUTORID : ====================="+PreferenceUtils.getStringPreference(context, PreferenceUtils.KEY_DISTRIBUTORID));
                                Logger.debug("okhttp","RESELLER : ===================== "+PreferenceUtils.getStringPreference(context, PreferenceUtils.KEY_RESELLER));

                                GKNegosyoDashboardActivity.start(context, service);
                                Logger.debug("okhttp","====================="+1);
                            } else {
                                GKNegosyoRedirectionActivity.start(context, service);
                                Logger.debug("okhttp","====================="+2);
                            }
                            break;
                        }
                        case "CPMPC": {
                            PreferenceUtils.removePreference(context, "projectcoopservicecode");
                            PreferenceUtils.saveStringPreference(context, "projectcoopservicecode", service.getServiceCode());
                            PreferenceUtils.removePreference(context, "projectcoopmerchantid");
                            PreferenceUtils.saveStringPreference(context, "projectcoopmerchantid", service.getMerchantID());
                            ProjectCoopActivity.start(context, 1, null);
                            break;
                        }
                        case "UNO": {
                            UnoRewardsActivity.start(context, UnoRewardsActivity.UNO_REWARDS, service);
                            break;
                        }
                        case "DROPOFF": {
                            Bundle args = new Bundle();
//                            PreferenceUtils.removePreference(context, PreferenceUtils.KEY_MDPTYPE);
//                            PreferenceUtils.saveStringPreference(context, PreferenceUtils.KEY_MDPTYPE, "fromDROPOFF");
//                            PaymentRequestActivity.start(context, 1, service, args);

                            DropOffActivity.start(context, 1, service, args);
                            break;
                        }
                        case "MDP": {
                            PreferenceUtils.removePreference(context, PreferenceUtils.KEY_MDPTYPE);
                            PreferenceUtils.saveStringPreference(context, PreferenceUtils.KEY_MDPTYPE, "fromMDP");
                            Intent intent = new Intent(context, MDPActivity.class);
                            intent.putExtra(GKService.KEY_SERVICE_OBJ, service);
                            context.startActivity(intent);

                            break;
                        }
                        case "REFER_A_FRIEND": {
                            ((MainActivity) context).displayView(9);
                            break;
                        }
                        case "WHATS_NEW": {
                            ((MainActivity) context).displayView(3);
                            break;
                        }
                        case "FREESMS": {
                            Intent intent = new Intent(context, FreeSMSActivity.class);
                            context.startActivity(intent);
                            break;
                        }

                        case "FAIRCHILD": {
                            Bundle args = new Bundle();
                            PreferenceUtils.removePreference(context, "GKServiceName");
                            PreferenceUtils.saveStringPreference(context, "GKServiceName", service.getServiceName());
                            FairChildAttendanceActivity.start(context, args);
                            break;
                        }

                        case "GKEARN": {
                            PreferenceUtils.removePreference(context, "GKServiceCode");
                            PreferenceUtils.removePreference(context, "GKMerchantID");
                            PreferenceUtils.removePreference(context, "GKMerchantStatus");

                            PreferenceUtils.saveStringPreference(context, "GKServiceCode", service.getServiceCode());
                            PreferenceUtils.saveStringPreference(context, "GKMerchantID", service.getMerchantID());
                            PreferenceUtils.saveStringPreference(context, "GKMerchantStatus", service.getGKStoreStatus());

                            PreferenceUtils.removePreference(context, "GKSERVICE_OBJECT");
                            PreferenceUtils.saveGKServicesPreference(context, "GKSERVICE_OBJECT", service);

                            GKEarnHomeActivity.start(context, GKEarnHomeActivity.GKEARN_FRAGMENT_REGISTERED_MEMBER, null);
                            break;
                        }

                        case "ALLAPPS":
                        case "ALLSERVICES":

                            Intent allapps = new Intent(context, AllServicesActivity.class);
                            context.startActivity(allapps);

                            break;

                        case "EGAME":{
                            PreferenceUtils.saveStringPreference(context, EGameActivity.KEY_EGAME_SERVICE_CODE, CommonFunctions.replaceEscapeData(service.getServiceCode()));
                            PreferenceUtils.saveStringPreference(context,"EGameMerchantID", service.getMerchantID());
                            Intent intent = new Intent(context, EGameActivity.class);
                            context.startActivity(intent);
                            break;
                        }

                        case "LM":{
                            Intent intent = new Intent(context, ConnectedAccountsActivity.class);
                            context.startActivity(intent);
                            break;
                        }

                        default: {
                            Cursor cursor = mDb.getSingleBiller(mDb, service.getServiceCode().toUpperCase());
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
                                    intent.putExtra("ServiceCode", service.getServiceType().toUpperCase());

                                    context.startActivity(intent);
                                }
                            } else {
                                ((BaseActivity) context).showError("It seems you are not updated. Please update your app to use this service.");
                            }
                            break;
                        }
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

        private ImageView service_logo;
        private TextView service_name;
        private LinearLayout gks_item2;
        private QBadgeView badgeeJump;
        private ProgressBar progressBar;
        private ImageView item_custom_row_cancel;
        private CardView gks_item_new_cardview;


        public GKSViewHolder(View itemView) {
            super(itemView);
            service_logo = itemView.findViewById(R.id.imgv_gks_new);
            service_name = itemView.findViewById(R.id.tv_gks_lbl_new);
            gks_item2 = itemView.findViewById(R.id.gks_item_new);
            progressBar = itemView.findViewById(R.id.progress_new);
            item_custom_row_cancel = itemView.findViewById(R.id.item_custom_row_cancel);
            gks_item_new_cardview = itemView.findViewById(R.id.gks_item_new_cardview);

            item_custom_row_cancel.setVisibility(View.GONE);

            badgeeJump = new QBadgeView(context);
            badgeeJump.bindTarget(gks_item2);
            badgeeJump.setBadgePadding(5, true);
            badgeeJump.setBadgeTextSize(13, true);
            badgeeJump.setGravityOffset(2, true);
            badgeeJump.setBadgeGravity(Gravity.TOP | Gravity.END);

        }
    }


    private void gkServicesMenu(String serviceCode, String action) {

        imei = PreferenceUtils.getImeiId(context);
        userid = PreferenceUtils.getUserId(context);
        borrowerid = PreferenceUtils.getBorrowerId(context);
        sessionid = PreferenceUtils.getSessionID(context);

        this.servicecode = serviceCode;
        this.action = action;
//        Call<GenericResponse> call = RetrofitBuild.getGKSerivcesAPIService(context)
//                .gkServicesMenu(
//                        imei,
//                        userid,
//                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
//                        sessionid,
//                        borrowerid,
//                        serviceCode,
//                        action
//                );
//
//        call.enqueue(gkServicesMenuCallback);
        gkServicesMenuV2();
    }

    private Callback<GenericResponse> gkServicesMenuCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        Logger.debug("roneldayanan", "COLORS: " + response.body().getMessage());
                        Toast.makeText(context, " Removed from menu.", Toast.LENGTH_SHORT).show();

                        mDb.deleteMainMenu(mDb, gkServiceList.get(adapterPosition).getServiceCode());

                        removeItem(adapterPosition);
                        context.startActivity(new Intent(context, MainActivity.class));

                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            Logger.debug("roneldayanan", "ERROR : " + t.getMessage());
        }
    };

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void gkServicesMenuV2(){
        if (CommonFunctions.getConnectivityStatus(context) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("servicecode",servicecode);
            parameters.put("action",action);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(context, parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            menuIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", menuIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            menuAuthenticatioid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            menuKeyEncryption = CommonFunctions.getSha1Hex(menuAuthenticatioid + sessionid + "gkServicesMenuV2");
            menuParam = CommonFunctions.encryptAES256CBC(menuKeyEncryption, String.valueOf(paramJson));

            gkServicesMenuV2Object(gkServicesMenuV2Callback);

        } else {
            Toast.makeText(context, "No internet connection.", Toast.LENGTH_SHORT).show();
        }
    }
    private void gkServicesMenuV2Object(Callback<GenericResponse> getGKServicesMenu) {
        Call<GenericResponse> call = RetrofitBuilder.getGkServiceV2API(context)
                .gkServicesMenuV2(
                        menuAuthenticatioid, sessionid, menuParam
                );
        call.enqueue(getGKServicesMenu);
    }

    private Callback<GenericResponse> gkServicesMenuV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(menuKeyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        Toast.makeText(context, " Removed from menu.", Toast.LENGTH_SHORT).show();

                        mDb.deleteMainMenu(mDb, gkServiceList.get(adapterPosition).getServiceCode());

                        removeItem(adapterPosition);
                        context.startActivity(new Intent(context, MainActivity.class));

                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            Logger.debug("roneldayanan", "ERROR : " + t.getMessage());
        }
    };

}
