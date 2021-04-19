package com.goodkredit.myapplication.adapter.loadmessenger;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.loadmessenger.ViewLoadTransactionsActivity;
import com.goodkredit.myapplication.bean.loadmessenger.LoadTransaction;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.ViewLoadTxnDialogFragment;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LoadTransactionsAdapter extends RecyclerView.Adapter<LoadTransactionsAdapter.LoadTxnViewHolder> {

    private ArrayList<LoadTransaction> loadTransactions = new ArrayList<>();
    public LoadTransactionsAdapter(ArrayList<LoadTransaction> list) {
        loadTransactions = list;
    }


    @NonNull
    @Override
    public LoadTransactionsAdapter.LoadTxnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewload_txn_item,parent,false);
        return new LoadTransactionsAdapter.LoadTxnViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull LoadTransactionsAdapter.LoadTxnViewHolder holder, int position) {
        LoadTransaction loadTransaction = loadTransactions.get(position);
        holder.bind(loadTransaction);
    }

    @Override
    public int getItemCount() {
        return loadTransactions.size();
    }

    public static class LoadTxnViewHolder extends RecyclerView.ViewHolder{

        ImageView txnLoadType;
        ImageView txn_medium;
        TextView txn_no,txn_mobile,txn_loadDescription,txn_time,txnStatus;

        public LoadTxnViewHolder(@NonNull View itemView) {
            super(itemView);

            txnLoadType = itemView.findViewById(R.id.txnLoadType);
            txn_medium = itemView.findViewById(R.id.txn_medium);
            txn_no = itemView.findViewById(R.id.txn_no);
            txn_mobile = itemView.findViewById(R.id.txn_mobile);
            txn_loadDescription = itemView.findViewById(R.id.txn_loadDescription);
            txn_time = itemView.findViewById(R.id.txn_time);
            txnStatus = itemView.findViewById(R.id.txnStatus);

        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        void bind(LoadTransaction loadTransaction){

            Drawable drawable = null;
            switch (loadTransaction.getNetwork()){
                case "SUN":
                     drawable = itemView.getResources().getDrawable(R.drawable.sun);
                    break;
                case "SMART":
                    drawable = itemView.getResources().getDrawable(R.drawable.smart);
                    break;

                case "TNT":
                    drawable = itemView.getResources().getDrawable(R.drawable.tnt);
                    break;

                case "GLOBE":
                    drawable = itemView.getResources().getDrawable(R.drawable.globe);
                    break;
            }

            Glide.with(itemView.getContext())
                    .load(drawable)
                    .centerCrop()
                    .into(txnLoadType);


            txn_no.setText(loadTransaction.getTransactionNo());
            txn_mobile.setText(loadTransaction.getMobileTarget());
            txn_loadDescription.setText(String.format("%s , %s", loadTransaction.getProductCode(), CommonFunctions.currencyFormatter(loadTransaction.getAmount())));
            txnStatus.setText(loadTransaction.getTxnStatus());

            String time = CommonFunctions.convertDate(loadTransaction.getDateTimeCompleted());

            txn_time.setText(CommonFunctions.getTimeFromDateTime(time));
            txnStatus.setText(loadTransaction.getTxnStatus());

            if(loadTransaction.getTxnStatus().equalsIgnoreCase("SUCCESS")){
                txnStatus.setTextColor(Color.parseColor("#369E32"));
            }else{
                txnStatus.setTextColor(Color.parseColor("#EF5350"));
            }

            itemView.setOnClickListener(view -> {
                ViewLoadTxnDialogFragment viewLoadTxnDialogFragment = new ViewLoadTxnDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("loadInfo",new Gson().toJson(loadTransaction));
                viewLoadTxnDialogFragment.setArguments(bundle);
                viewLoadTxnDialogFragment.show(((ViewLoadTransactionsActivity)itemView.getContext()).getSupportFragmentManager(), viewLoadTxnDialogFragment.getTag());
            });

        }

    }


}

