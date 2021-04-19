package com.goodkredit.myapplication.adapter.loadmessenger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.loadmessenger.ConnectedAccountsActivity;
import com.goodkredit.myapplication.activities.loadmessenger.ViewFBDetailsActivity;
import com.goodkredit.myapplication.activities.loadmessenger.ViewLoadTransactionsActivity;
import com.goodkredit.myapplication.bean.loadmessenger.BorrowerFB;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ConnectedAccountsAdapter extends RecyclerView.Adapter<ConnectedAccountsAdapter.AccountViewHolder> {

    private ArrayList<BorrowerFB> borrowerFBS = new ArrayList<>();
    public ConnectedAccountsAdapter(ArrayList<BorrowerFB> list) {
        borrowerFBS = list;
    }


    public void updateData(ArrayList<BorrowerFB> borrowerFB){
        borrowerFBS.addAll(borrowerFB);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.connected_acc_item,parent,false);
        return new AccountViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {

        BorrowerFB borrowerFB = borrowerFBS.get(position);
        holder.bind(borrowerFB);

    }

    @Override
    public int getItemCount() {
        return borrowerFBS.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder{

        TextView accName,accBalance,accStatus;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);

            accName = itemView.findViewById(R.id.item_lm_accname);
            accBalance = itemView.findViewById(R.id.item_lm_accbalance);
            accStatus = itemView.findViewById(R.id.item_lm_accstatus);

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        void bind(BorrowerFB borrowerFB){
            accName.setText(borrowerFB.getFBName());
            accBalance.setText(CommonFunctions.currencyFormatter(borrowerFB.getAvailableCredit()));
            accStatus.setText(borrowerFB.getStatus());

            if(borrowerFB.getStatus().equalsIgnoreCase("active")){
                accStatus.setTextColor(Color.parseColor("#369E32"));
            }else{
                accStatus.setTextColor(Color.parseColor("#EF5350"));
            }

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), ViewFBDetailsActivity.class);
                intent.putExtra("borrowerFB",new Gson().toJson(borrowerFB));
                itemView.getContext().startActivity(intent);
                ((ConnectedAccountsActivity)itemView.getContext()).finish();
            });

        }

    }


}


