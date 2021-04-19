package com.goodkredit.myapplication.adapter.gkservices;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
import com.goodkredit.myapplication.activities.gkservices.GKSpecificServicesActivity;
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
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.prepaidrequest.VirtualVoucherProductFragment;
import com.goodkredit.myapplication.fragments.services.ServicesFragment;
import com.goodkredit.myapplication.model.EventItem;
import com.goodkredit.myapplication.model.HeaderItem;
import com.goodkredit.myapplication.model.ListItem;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetGoodKreditServicesResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllServicesItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private String imei;
    private String userid;
    private String borrowerid;
    private String sessionid;


    private RecyclerView.ViewHolder recyclerHolder;
    private int adapterPosition;

    //NEW VARIABLES FOR SECURITY
    private String menuIndex;
    private String menuAuthenticatioid;
    private String menuKeyEncryption;
    private String menuParam;

    private String servicecode;
    private String action;

    private ViewGroup viewGroup;

    AlertDialog alertDialog;
    boolean dialogIsShown =false;

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView txt_header;
        RelativeLayout section_item_mainLayout;
        ImageView gotocategory;

        HeaderViewHolder(View itemView) {
            super(itemView);
            txt_header = itemView.findViewById(R.id.section_item_title);
            section_item_mainLayout = itemView.findViewById(R.id.section_item_mainLayout);
            gotocategory = itemView.findViewById(R.id.section_item_imgbtn);
        }

    }

    private static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title;
        ImageView service_icon,service_star_icon;
        RelativeLayout all_services_item_mainLayout;
        CardView all_services_item_img_cardview;
        ProgressBar progress_image;
        EventViewHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.all_services_item_title);
            service_icon = itemView.findViewById(R.id.all_services_item_img);
            service_star_icon = itemView.findViewById(R.id.all_services_item_star);
            all_services_item_mainLayout = itemView.findViewById(R.id.all_services_item_mainLayout);
            all_services_item_img_cardview = itemView.findViewById(R.id.all_services_item_img_cardview);
            progress_image = itemView.findViewById(R.id.progress_image);
        }

    }

    @NonNull
    private List<ListItem> items = Collections.emptyList();
    private Context context;
    private DatabaseHandler mDb;


    public AllServicesItemAdapter(@NonNull List<ListItem> items, Context context) {
        this.items = items;
        this.context = context;
        mDb = new DatabaseHandler(this.context);
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        viewGroup = parent;
        switch (viewType) {
            case ListItem.TYPE_HEADER: {
                View itemView = inflater.inflate(R.layout.all_services_section_row, parent, false);
                return new HeaderViewHolder(itemView);
            }
            case ListItem.TYPE_EVENT: {
                View itemView = inflater.inflate(R.layout.all_services_section_item, parent, false);
                return new EventViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof HeaderViewHolder){
            ((HeaderViewHolder) viewHolder).txt_header.setText(((HeaderItem) items.get(position)).getCategory());

            ((HeaderViewHolder) viewHolder).section_item_mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent = new Intent(context, GKSpecificServicesActivity.class);
                   intent.putExtra("category",((HeaderItem) items.get(position)).getCategory());
                   context.startActivity(intent);
                }
            });

            ((HeaderViewHolder) viewHolder).gotocategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GKSpecificServicesActivity.class);
                    intent.putExtra("category",((HeaderItem) items.get(position)).getCategory());
                    context.startActivity(intent);
                }
            });

        }

        if(viewHolder instanceof EventViewHolder){

            Glide.with(context)
                    .load(((EventItem) items.get(position)).getEvent().getService().getLogo())
                    .override(65, 65)
                    .centerCrop()
                    .thumbnail(.2f)
                    .into(((EventViewHolder) viewHolder).service_icon);

            String color =  ((EventItem) items.get(position)).getEvent().getService().getColor();

            if(color != null) {
                if (((EventItem) items.get(position)).getEvent().getService().getColor().equals("") ||
                        ((EventItem) items.get(position)).getEvent().getService().getColor().equals(".")) {
                    ((EventViewHolder) viewHolder).all_services_item_img_cardview.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    ((EventViewHolder) viewHolder).all_services_item_img_cardview.setCardBackgroundColor(Color.parseColor(((EventItem) items.get(position)).getEvent().getService().getColor()));
                }
            } else {
                ((EventViewHolder) viewHolder).all_services_item_img_cardview.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            if(mDb.serviceExists(((EventItem) items.get(position)).getEvent().getService().getServiceCode())){
                Glide.with(context)
                        .load(R.drawable.ic_star_black_24dp)
                        .override(50, 50)
                        .into(((EventViewHolder) viewHolder).service_star_icon);
            }else{
                Glide.with(context)
                        .load(R.drawable.ic_star_border_black_36dp)
                        .override(50, 50)
                        .fitCenter()
                        .into(((EventViewHolder) viewHolder).service_star_icon);
            }
            ((EventViewHolder) viewHolder).progress_image.setVisibility(View.GONE);
            ((EventViewHolder) viewHolder).service_star_icon.setVisibility(View.VISIBLE);

            ((EventViewHolder) viewHolder).txt_title.setText(CommonFunctions.replaceEscapeData(((EventItem) items.get(position)).getEvent().getService().getServiceName()));
            V2Utils.setTypeFace(context,V2Utils.ROBOTO_CONDENSED_REGULAR,((EventViewHolder) viewHolder).txt_title.getText().toString());

            ((EventViewHolder) viewHolder).all_services_item_mainLayout.setTag(((EventItem) items.get(position)).getEvent().getService());
            ((EventViewHolder) viewHolder).all_services_item_mainLayout.setOnClickListener(itemListener);


//            Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
//            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);


            ((EventViewHolder) viewHolder).service_star_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = "";
                    String message = "";
                    String niggaButton = "";
                    String whiteButton = "";

                    if(!mDb.serviceExists(((EventItem) items.get(position)).getEvent().getService().getServiceCode())){
                        title = "Add "+((EventItem) items.get(position)).getEvent().getService().getServiceName()+" " +
                                "to Services Menu?";
                        message = "Adding this will create a shortcut for this service in the services menu.";
                        niggaButton = "Cancel";
                        whiteButton = "Add";

                    }else {
                        title = "Remove "+((EventItem) items.get(position)).getEvent().getService().getServiceName()+" " +
                                "from Services Menu?";
                        message = "This will only be removed on the service menu but you can still add it and see it " +
                                "by clicking the all apps.";
                        niggaButton = "Cancel";
                        whiteButton = "Remove";
                    }

                    if(!dialogIsShown){
                        showCustomDialog(context,title,message,niggaButton,whiteButton,items,viewHolder,position);
                    }
                }
            });

        }

    }

    public void showCustomDialog(final Context context, String title, String message, String nigga, String white, List<ListItem> items, RecyclerView.ViewHolder viewHolder,int position) {

        recyclerHolder = viewHolder;
        adapterPosition = position;

        View dialogView = LayoutInflater.from(context).inflate(R.layout.allservices_dialog,null , false);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        TextView titleTv = dialogView.findViewById(R.id.title_allservices);
        TextView messageTv = dialogView.findViewById(R.id.message_allservices);
        titleTv.setText(title);
        messageTv.setText(message);

        Button niggaB = dialogView.findViewById(R.id.cancelBtn_allservices);
        Button positiveB = dialogView.findViewById(R.id.positiveBtn_allservices);


        if(white.equalsIgnoreCase("add")){
            positiveB.setTextColor(Color.parseColor("#52B0E2"));
        }else{
            positiveB.setTextColor(Color.parseColor("#E25252"));
        }

        niggaB.setText(nigga);
        positiveB.setText(white);
        alertDialog = builder.create();
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
                dialogIsShown = false;
            }
        });
        positiveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CommonFunctions.getConnectivityStatus(context) > 0){
                    if(white.equalsIgnoreCase("add")){
                        gkServicesMenu(((EventItem)items.get(position)).getEvent().getService().getServiceCode(),"add");
                        alertDialog.dismiss();
                        dialogIsShown = false;

                    }else{

                        gkServicesMenu(((EventItem)items.get(position)).getEvent().getService().getServiceCode(),"remove");
                        alertDialog.dismiss();
                        dialogIsShown = false;
                    }
                }else{
                    Toast.makeText(context,"No internet connection.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(alertDialog.isShowing()){
            dialogIsShown = true;
        }

    }

    private void gkServicesMenu(String serviceCode,String action) {

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

        ((EventViewHolder) recyclerHolder).progress_image.setVisibility(View.VISIBLE);
        ((EventViewHolder) recyclerHolder).service_star_icon.setVisibility(View.GONE);

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
                        if (response.body().getMessage().contains("added")) {
                            Toast.makeText(context, " Added to menu.", Toast.LENGTH_SHORT).show();
                            addToMenu(recyclerHolder, adapterPosition, items);
                        } else if (response.body().getMessage().contains("removed")) {
                            Toast.makeText(context, " Removed from menu.", Toast.LENGTH_SHORT).show();
                            removeFromMenu(recyclerHolder, adapterPosition, items);
                        }
                    }else{
                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            Logger.debug("roneldayanan","ERROR : "+t.getMessage());
        }
    };
    
    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return items.get(position).getType();

    }


    private View.OnClickListener itemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                GKService service = (GKService) v.getTag();
                mDb.deleteRecordS(mDb, service.getServiceCode());
                mDb.insertRecentlyUsedGKS(mDb, service);

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
                }

                else if (service.getServiceType().equals("COOP")) {
                    switch (service.getServiceType()) {
                        case "COOP": {
                            if(service.getServiceCode().equals("CPMPC")) {
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
                }

                else {
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
                                GKNegosyoDashboardActivity.start(context, service);
                            } else {
                                GKNegosyoRedirectionActivity.start(context, service);
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
                            FairChildAttendanceActivity.start(context,  args);
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

    private void addToMenu(RecyclerView.ViewHolder viewHolder,int position,List<ListItem> items){
        Glide.with(context)
                .load(R.drawable.ic_star_black_24dp)
                .override(50, 50)
                .into(((EventViewHolder) viewHolder).service_star_icon);

        mDb.insertGKSERVICES_MAINMENU(mDb,((EventItem) items.get(position)).getEvent().getService());

    }
    private void removeFromMenu(RecyclerView.ViewHolder viewHolder,int position,List<ListItem> items){
        mDb.deleteMainMenu(mDb,((EventItem) items.get(position)).getEvent().getService().getServiceCode());
        Glide.with(context)
                .load(R.drawable.ic_star_border_black_36dp)
                .override(50, 50)
                .into(((EventViewHolder) viewHolder).service_star_icon);
    }

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
            ((EventViewHolder) recyclerHolder).progress_image.setVisibility(View.GONE);
            ((EventViewHolder) recyclerHolder).service_star_icon.setVisibility(View.VISIBLE);
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
            ((EventViewHolder) recyclerHolder).progress_image.setVisibility(View.GONE);
            ((EventViewHolder) recyclerHolder).service_star_icon.setVisibility(View.VISIBLE);
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(menuKeyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        Logger.debug("roneldayanan", "COLORS: " + response.body().getMessage());
                        if (message.contains("Added")) {
                            Toast.makeText(context, " Added to menu.", Toast.LENGTH_SHORT).show();
                            addToMenu(recyclerHolder, adapterPosition, items);
                        } else if (message.contains("Removed")) {
                            Toast.makeText(context, " Removed from menu.", Toast.LENGTH_SHORT).show();
                            removeFromMenu(recyclerHolder, adapterPosition, items);
                        }
                    }else{
                        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                    }
                }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            Toast.makeText(context,"Failed to "+action+" service menu. Please try again.",Toast.LENGTH_SHORT).show();
            ((EventViewHolder) recyclerHolder).progress_image.setVisibility(View.GONE);
            ((EventViewHolder) recyclerHolder).service_star_icon.setVisibility(View.VISIBLE);
        }
    };

}