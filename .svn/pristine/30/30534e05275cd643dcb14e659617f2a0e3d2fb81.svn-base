package com.goodkredit.myapplication.adapter.vouchers.payoutone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.payoutone.VoucherPayoutOneDepositToBankActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.vouchers.SubscriberBankAccounts;
import com.goodkredit.myapplication.utilities.CacheManager;

import java.util.ArrayList;
import java.util.List;

public class VoucherPayoutOneSubscriberBankAccountsAdapter extends RecyclerView.Adapter<VoucherPayoutOneSubscriberBankAccountsAdapter.ViewHolder>{

    private Context context;
    private List<SubscriberBankAccounts> mGridData;

    private VoucherPayoutOneDepositToBankActivity mActivity;
    private ArrayList<SubscriberBankAccounts> passedSubscriberBankAccount;
    private int mSelectedItem = -1;

    public VoucherPayoutOneSubscriberBankAccountsAdapter(Context context, VoucherPayoutOneDepositToBankActivity activity) {
        this.context = context;
        this.mGridData = CacheManager.getInstance().getSubscriberBankAccounts();
        this.mActivity = activity;
        this.passedSubscriberBankAccount = new ArrayList<>();
    }

    public void updateList(List<SubscriberBankAccounts> list) {
        this.mGridData = list;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherPayoutOneSubscriberBankAccountsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_payoutone_subscriber_bank_accounts, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherPayoutOneSubscriberBankAccountsAdapter.ViewHolder holder, int position) {
        SubscriberBankAccounts subscriberBankAccounts = mGridData.get(position);

        try{
            holder.txv_bankcode.setText(CommonFunctions.replaceEscapeData(subscriberBankAccounts.getBankCode()));
            holder.txv_bankname.setText(CommonFunctions.replaceEscapeData(subscriberBankAccounts.getBank()));
            holder.txv_accountname.setText(CommonFunctions.replaceEscapeData(subscriberBankAccounts.getAccountName()));
            holder.txv_accountno1.setText(CommonFunctions.replaceEscapeData(subscriberBankAccounts.getAccountNo()));
            holder.txv_accountno2.setText(CommonFunctions.replaceEscapeData(subscriberBankAccounts.getAccountNo()));

            holder.rb_selected.setChecked(position == mSelectedItem);

            holder.main_container.setOnClickListener(selectSubscriberBankAccount);
            holder.main_container.setTag(holder);

            holder.rb_selected.setOnClickListener(selectSubscriberBankAccount);
            holder.rb_selected.setTag(holder);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //RADIO LISTENER
    private View.OnClickListener selectSubscriberBankAccount = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final VoucherPayoutOneSubscriberBankAccountsAdapter.ViewHolder holder = (ViewHolder) v.getTag();

                singleSelectionRadio(holder);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void singleSelectionRadio(ViewHolder holder) {
        mSelectedItem = holder.getAdapterPosition();
        clearSelection(holder);
        addSelection(holder);
        notifyDataSetChanged();
    }

    private void addSelection(ViewHolder holder) {
        if (passedSubscriberBankAccount != null) {
            SubscriberBankAccounts subscriberBankAccounts = mGridData.get(holder.getAdapterPosition());

            int id = subscriberBankAccounts.getID();
            String borrowerid = subscriberBankAccounts.getBorrowerID();
            String bankcode = subscriberBankAccounts.getBankCode();
            String bank = subscriberBankAccounts.getBank();
            String accountno = subscriberBankAccounts.getAccountNo();
            String accountname = subscriberBankAccounts.getAccountName();
            String isverified = subscriberBankAccounts.getIsVerified();
            String status = subscriberBankAccounts.getStatus();
            String extra1 = subscriberBankAccounts.getExtra1();
            String extra2 = subscriberBankAccounts.getExtra2();
            String extra3 = subscriberBankAccounts.getExtra3();
            String extra4 = subscriberBankAccounts.getExtra4();
            String notes1 = subscriberBankAccounts.getNotes1();
            String notes2 = subscriberBankAccounts.getNotes2();

            passedSubscriberBankAccount.add(new SubscriberBankAccounts(id, borrowerid, bankcode, bank, accountno, accountname,
                    isverified, status,extra1, extra2, extra3, extra4, notes1, notes2));

            mActivity.selectAccreditedBanks(passedSubscriberBankAccount);
        }
    }

    private void clearSelection(ViewHolder holder) {
        if (passedSubscriberBankAccount != null) {
            passedSubscriberBankAccount.clear();
            mActivity.selectAccreditedBanks(passedSubscriberBankAccount);
        }
    }


    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout main_container;
        private RadioButton rb_selected;
        private TextView txv_bankcode;
        private TextView txv_bankname;
        private TextView txv_accountname;
        private TextView txv_accountno1;
        private TextView txv_accountno2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            main_container = (LinearLayout) itemView.findViewById(R.id.main_container);
            rb_selected = itemView.findViewById(R.id.rb_select_option);
            txv_bankcode = itemView.findViewById(R.id.txv_bankcode);
            txv_bankname = itemView.findViewById(R.id.txv_bankname);
            txv_accountname = itemView.findViewById(R.id.txv_accountname);
            txv_accountno1 = itemView.findViewById(R.id.txv_accountno1);
            txv_accountno2 = itemView.findViewById(R.id.txv_accountno2);
        }
    }
}