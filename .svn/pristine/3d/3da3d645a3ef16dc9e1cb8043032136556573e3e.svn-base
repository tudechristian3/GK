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
import com.goodkredit.myapplication.activities.vouchers.payoutone.VoucherPayoutOneAccreditedBankActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.vouchers.AccreditedBanks;
import com.goodkredit.myapplication.utilities.CacheManager;

import java.util.ArrayList;
import java.util.List;

public class VoucherPayoutOneAccreditedBanksAdapter extends RecyclerView.Adapter<VoucherPayoutOneAccreditedBanksAdapter.ViewHolder> {

    private Context mContext;
    private List<AccreditedBanks> mGridData;

    private VoucherPayoutOneAccreditedBankActivity mActivity;
    private ArrayList<AccreditedBanks> passedAccreditedBanks;
    public int mSelectedItem = -1;

    public VoucherPayoutOneAccreditedBanksAdapter(Context context, VoucherPayoutOneAccreditedBankActivity activity){
        this.mContext = context;
        this.mGridData = CacheManager.getInstance().getAccreditedBanks();
        this.mActivity = activity;
        this.passedAccreditedBanks = new ArrayList<>();
    }

    public void updateList(List<AccreditedBanks> list) {
        this.mGridData = list;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherPayoutOneAccreditedBanksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_payoutone_accredited_banks, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherPayoutOneAccreditedBanksAdapter.ViewHolder holder, int position) {
        AccreditedBanks accreditedBanks = mGridData.get(position);

        holder.txv_bankname.setText(CommonFunctions.replaceEscapeData(accreditedBanks.getBank()));
        holder.rb_select.setChecked(position == mSelectedItem);

        holder.main_container.setOnClickListener(selectAccreditedBanksListener);
        holder.main_container.setTag(holder);

        holder.rb_select.setOnClickListener(selectAccreditedBanksListener);
        holder.rb_select.setTag(holder);
    }

    //RADIO LISTENER
    private View.OnClickListener selectAccreditedBanksListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final VoucherPayoutOneAccreditedBanksAdapter.ViewHolder holder = (ViewHolder) v.getTag();

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
        if (passedAccreditedBanks != null) {
            AccreditedBanks accreditedBanks = mGridData.get(holder.getAdapterPosition());

            int id = accreditedBanks.getID();
            String bankname = accreditedBanks.getBankCode();
            String bank = accreditedBanks.getBank();
            String extra1 = accreditedBanks.getExtra1();
            String extra2 = accreditedBanks.getExtra2();
            String extra3 = accreditedBanks.getExtra3();
            String extra4 = accreditedBanks.getExtra4();
            String notes1 = accreditedBanks.getNotes1();
            String notes2 = accreditedBanks.getNotes2();

            passedAccreditedBanks.add(new AccreditedBanks(id, bankname, bank, extra1, extra2, extra3,
                    extra4, notes1, notes2));

            mActivity.selectAccreditedBanks(passedAccreditedBanks);
        }
    }

    public void clearSelection(ViewHolder holder) {
        if (passedAccreditedBanks != null) {
            passedAccreditedBanks.clear();
            mActivity.selectAccreditedBanks(passedAccreditedBanks);
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout main_container;
        private TextView txv_bankname;
        private RadioButton rb_select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            main_container = (LinearLayout) itemView.findViewById(R.id.main_container);
            txv_bankname = itemView.findViewById(R.id.txv_bankname);
            rb_select = itemView.findViewById(R.id.rb_select_option);
        }
    }
}
