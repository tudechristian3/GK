package com.goodkredit.myapplication.adapter.coopassistant.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.prepaidrequest.VirtualVoucherProductFragment;
import com.goodkredit.myapplication.model.GKPaymentOptions;
import com.goodkredit.myapplication.utilities.NumberTextWatcherForThousand;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.List;

public class CoopAssistantMemberCreditsAdapter extends RecyclerView.Adapter<CoopAssistantMemberCreditsAdapter.MyViewHolder> {
    private Context mContext;
    private List<GKPaymentOptions> paymentOptionsList;
    private ArrayList<GKPaymentOptions> passedPaymentOptionsList;
    private int mSelectedItem = -1;
    private VirtualVoucherProductFragment fragment;

    public CoopAssistantMemberCreditsAdapter(Context context, VirtualVoucherProductFragment fm) {
        mContext = context;
        fragment = fm;
        paymentOptionsList = new ArrayList<>();
        passedPaymentOptionsList = new ArrayList<>();
    }

    public void updateData(List<GKPaymentOptions> arraylist) {
        paymentOptionsList.clear();
        paymentOptionsList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        paymentOptionsList.clear();
        notifyDataSetChanged();
    }

    @Override
    public CoopAssistantMemberCreditsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coopassistant_membercredits_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(CoopAssistantMemberCreditsAdapter.MyViewHolder holder, int position) {
        try {
            GKPaymentOptions gkPaymentOptions = paymentOptionsList.get(position);

            holder.txv_name.setText(CommonFunctions.replaceEscapeData(gkPaymentOptions.getPaymentName()));
            holder.txv_name.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_BOLD));

            String balance = "Balance: PHP " + CommonFunctions.currencyFormatter(String.valueOf(gkPaymentOptions.getPaymentPrice()));
            holder.txv_balance.setText(balance);

            holder.rb_paymentoptions.setChecked(position == mSelectedItem);

            holder.main_container.setOnClickListener(itemSelectedListener);
            holder.main_container.setTag(holder);

            holder.rb_paymentoptions.setOnClickListener(itemSelectedListener);
            holder.rb_paymentoptions.setTag(holder);

            setRadioToMyPaymentType(holder);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //RADIO LISTENER
    private View.OnClickListener itemSelectedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final CoopAssistantMemberCreditsAdapter.MyViewHolder holder = (MyViewHolder) v.getTag();

                singleSelectionRadio(holder);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void singleSelectionRadio(MyViewHolder holder) {
        mSelectedItem = holder.getAdapterPosition();
        clearSelection(holder);
        addSelection(holder);
        notifyDataSetChanged();
    }

    private void addSelection(MyViewHolder holder) {
        if (passedPaymentOptionsList != null) {
            GKPaymentOptions gKPaymentOptions = paymentOptionsList.get(holder.getAdapterPosition());

            String name = gKPaymentOptions.getPaymentName();
            double price = gKPaymentOptions.getPaymentPrice();
            String status = gKPaymentOptions.getPaymentStatus();

            passedPaymentOptionsList.add(new GKPaymentOptions(name, price, status));

            fragment.selectPaymentOptions(passedPaymentOptionsList);
        }
    }

    private void clearSelection(MyViewHolder holder) {
        if (passedPaymentOptionsList != null) {
            passedPaymentOptionsList.clear();
            fragment.selectPaymentOptions(passedPaymentOptionsList);
        }
    }

    private void setRadioToMyPaymentType(MyViewHolder holder) {
        GKPaymentOptions gKPaymentOptions = paymentOptionsList.get(holder.getAdapterPosition());

        holder.rb_paymentoptions.setChecked(true);
        clearSelection(holder);
        addSelection(holder);
    }

    @Override
    public int getItemCount() {
        return paymentOptionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RadioButton rb_paymentoptions;
        private LinearLayout main_container;
        private TextView txv_name;
        private TextView txv_balance;

        public MyViewHolder(View itemView) {
            super(itemView);
            main_container = (LinearLayout) itemView.findViewById(R.id.main_container);
            rb_paymentoptions = (RadioButton) itemView.findViewById(R.id.rb_paymentoptions);
            txv_name = (TextView) itemView.findViewById(R.id.txv_name);
            txv_balance = (TextView) itemView.findViewById(R.id.txv_balance);
        }

        @Override
        public void onClick(View view) {


        }
    }
}
