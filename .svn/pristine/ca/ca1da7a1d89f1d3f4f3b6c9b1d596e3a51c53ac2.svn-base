package com.goodkredit.myapplication.adapter.prepaidrequest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.PrepaidRequest;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.fragments.prepaidrequest.VirtualVoucherProductFragment;
import com.goodkredit.myapplication.utilities.InputFilterMinMax;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 5/25/2017.
 */

public class VirtualVoucherProductRecyclerAdapter extends RecyclerView.Adapter<VirtualVoucherProductRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<PrepaidRequest> mGridData;
    private VirtualVoucherProductFragment mFragment;

    private String MODE = "";
    private boolean isDisabled = false;

    public VirtualVoucherProductRecyclerAdapter(Context context, VirtualVoucherProductFragment fragment, String mode) {
        mContext = context;
        mGridData = new ArrayList<>();
        mFragment = fragment;
        MODE = mode;
    }

    private Context getContext() {
        return mContext;
    }

    public void setVirtualVoucher(List<PrepaidRequest> data) {
        int startPos = mGridData.size() + 1;
        mGridData.clear();
        mGridData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public void clear() {
        int startPos = mGridData.size();
        mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    private String buildURL(String prodID) {
        String ext = prodID + "-voucher-design.jpg";
        return CommonVariables.s3link + ext;
    }

    @Override
    public VirtualVoucherProductRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_virtual_voucher_product_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VirtualVoucherProductRecyclerAdapter.MyViewHolder holder, int position) {
        PrepaidRequest prepaidRequest = mGridData.get(position);

        holder.virtualVoucherQty.setTag(R.id.virtualVoucherQty, position);

        holder.virtualVoucherQty.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9), new InputFilterMinMax(0, 999999999)});

        holder.virtualVoucherLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_E0E1E2));

        Glide.with(getContext())
                .load(buildURL(prepaidRequest.getProductID()))
                .into(holder.virtualVoucherImage);

        holder.txvQtyLabel.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "QUANTITY"));
        holder.virtualVoucherQty.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "0"));

        if (position == 0) {
            if (MODE.equals(VirtualVoucherProductFragment.BY_CSB_REWARDS))
                holder.virtualVoucherHeader.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.order_voucher_csb));
            else if (MODE.equals(VirtualVoucherProductFragment.BY_UNO_REWARDS))
                holder.virtualVoucherHeader.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.uno_layer));
            else
                holder.virtualVoucherHeader.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.order_voucher_yello_bar));
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final FrameLayout virtualVoucherLayout;
        private final ImageView virtualVoucherHeader;
        private final ImageView virtualVoucherImage;
        private final TextView txvQtyLabel;
        private final Button btnQtyDecrease;
        private final EditText virtualVoucherQty;
        private final Button btnQtyIncrease;

        public MyViewHolder(View itemView) {
            super(itemView);

            virtualVoucherLayout = itemView.findViewById(R.id.virtualVoucherLayout);
            virtualVoucherHeader = itemView.findViewById(R.id.virtualVoucherHeader);
            virtualVoucherImage = itemView.findViewById(R.id.virtualVoucherImage);
            txvQtyLabel = itemView.findViewById(R.id.txvQtyLabel);
            btnQtyDecrease = itemView.findViewById(R.id.btnQtyDecrease);
            btnQtyDecrease.setOnClickListener(this);
            virtualVoucherQty = itemView.findViewById(R.id.virtualVoucherQty);
            btnQtyIncrease = itemView.findViewById(R.id.btnQtyIncrease);
            btnQtyIncrease.setOnClickListener(this);

            virtualVoucherQty.addTextChangedListener(new QuantityTextWatcher(virtualVoucherQty));
        }

        private class QuantityTextWatcher implements TextWatcher {
            private EditText editText;

            public QuantityTextWatcher(EditText editText) {
                this.editText = editText;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {

                    if (editText != null) {

                        String mQuantity = s.toString().trim();
                        if(mQuantity.matches("[0-9]+")){
                            //get edittext's position
                            int position = (int) editText.getTag(R.id.virtualVoucherQty);

                            // get the object data
                            PrepaidRequest prepaidRequest = mGridData.get(position);

                            //update total price
                            int qty = Integer.valueOf(mQuantity);

                            if (qty > 0) {

                                virtualVoucherQty.removeTextChangedListener(this);

                                virtualVoucherQty.setText(String.valueOf(qty));

                                virtualVoucherLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_26BCD5));

                                //update data in current data set object
                                prepaidRequest.setOrderQuantity(qty);

                                mGridData.set(position, prepaidRequest);

                                //update data displayed
                                mFragment.calculateVirtualVoucherSummary(mGridData);

                                virtualVoucherQty.addTextChangedListener(this);

                                if (qty <= prepaidRequest.getTotalNumberVoucher()) {

                                    virtualVoucherQty.setTextColor(ContextCompat.getColor(mContext, R.color.color_979797));

                                    disableAllButtons(false);

                                } else {

                                    virtualVoucherQty.setTextColor(ContextCompat.getColor(mContext, R.color.color_F83832));

                                    disableAllButtons(true);

                                    //Toast.makeText(mContext, "Sorry, there is not enough stock for ".concat(getVoucherName(prepaidRequest.getProductID())), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                virtualVoucherLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_E0E1E2));
                            }
                        }

                    }
                } else {

                    int position = (int) editText.getTag(R.id.virtualVoucherQty);

                    // get the object data
                    PrepaidRequest prepaidRequest = mGridData.get(position);

                    //update data in current data set object
                    prepaidRequest.setOrderQuantity(0);

                    mGridData.set(position, prepaidRequest);

                    mFragment.calculateVirtualVoucherSummary(mGridData);

                    //disableButtons(true);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {

                    int qty = Integer.valueOf(s.toString());
                    virtualVoucherQty.removeTextChangedListener(this);
                    virtualVoucherQty.setText(String.valueOf(qty));
                    virtualVoucherQty.setSelection(virtualVoucherQty.getText().length());
                    virtualVoucherQty.addTextChangedListener(this);
                    if (mFragment.getCurrentMODE().equals(VirtualVoucherProductFragment.BY_UNO_REWARDS)) {
                        if (qty <= 0) {
                            virtualVoucherQty.setBackgroundResource(R.drawable.border_shopping_cart_quantity_box);
                            virtualVoucherQty.setTextColor(ContextCompat.getColor(getContext(), R.color.color_CCCCCC));
                            virtualVoucherQty.setTypeface(null, Typeface.NORMAL);
                            btnQtyDecrease.setBackgroundResource(R.drawable.border_shopping_cart_quantity_box);
                            btnQtyIncrease.setBackgroundResource(R.drawable.border_shopping_cart_quantity_box);
                        } else {
                            virtualVoucherQty.setBackgroundResource(R.drawable.border_uno_rect);
                            virtualVoucherQty.setTextColor(Color.BLACK);
                            virtualVoucherQty.setTypeface(null, Typeface.BOLD);
                            btnQtyDecrease.setBackgroundResource(R.drawable.border_uno_rect);
                            btnQtyIncrease.setBackgroundResource(R.drawable.border_uno_rect);
                        }
                    }
                }else{
                    virtualVoucherQty.setText(String.valueOf(0));
                }
            }

            private void disableAllButtons(boolean isEnabled) {
                //btnQtyIncrease.setEnabled(!isEnabled);
                //btnQtyDecrease.setEnabled(!isEnabled);
                mFragment.disableBtnGenerate();
            }

            private void disableButtons(boolean isEnabled) {
                //btnQtyDecrease.setEnabled(!isEnabled);
                mFragment.disableBtnGenerate();
            }
        }

        @SuppressLint("ShowToast")
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            switch (v.getId()) {
                case R.id.btnQtyDecrease: {

                    PrepaidRequest prepaidRequest = mGridData.get(position);
                    int mQty = 0;
                    if (prepaidRequest.getOrderQuantity() > 0) {

                        mQty = prepaidRequest.getOrderQuantity() - 1;

                        prepaidRequest.setOrderQuantity(mQty);

                        mGridData.set(position, prepaidRequest);

                        virtualVoucherQty.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", String.valueOf(mQty)));

                        mFragment.calculateVirtualVoucherSummary(mGridData);

                    } else {

                    }

                    if (mQty <= 0) {
                        if (mFragment.getCurrentMODE().equals(VirtualVoucherProductFragment.BY_UNO_REWARDS)) {
                            virtualVoucherQty.setBackgroundResource(R.drawable.border_shopping_cart_quantity_box);
                            virtualVoucherQty.setTextColor(ContextCompat.getColor(getContext(), R.color.color_CCCCCC));
                            virtualVoucherQty.setTypeface(null, Typeface.NORMAL);
                            btnQtyDecrease.setBackgroundResource(R.drawable.border_shopping_cart_quantity_box);
                            btnQtyIncrease.setBackgroundResource(R.drawable.border_shopping_cart_quantity_box);
                        }
                    }


                    virtualVoucherQty.clearFocus();

                    break;
                }
                case R.id.btnQtyIncrease: {

                    PrepaidRequest prepaidRequest = mGridData.get(position);

                    if (prepaidRequest.getOrderQuantity() <= prepaidRequest.getTotalNumberVoucher()) {

                        int mQty = prepaidRequest.getOrderQuantity() + 1;

                        prepaidRequest.setOrderQuantity(mQty);

                        mGridData.set(position, prepaidRequest);

                        virtualVoucherQty.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", String.valueOf(mQty)));

                        mFragment.calculateVirtualVoucherSummary(mGridData);

                        if (mQty > 0) {
                            if (mFragment.getCurrentMODE().equals(VirtualVoucherProductFragment.BY_UNO_REWARDS)) {
                                virtualVoucherQty.setBackgroundResource(R.drawable.border_uno_rect);
                                virtualVoucherQty.setTextColor(Color.BLACK);
                                virtualVoucherQty.setTypeface(null, Typeface.BOLD);
                                btnQtyDecrease.setBackgroundResource(R.drawable.border_uno_rect);
                                btnQtyIncrease.setBackgroundResource(R.drawable.border_uno_rect);
                            }
                        }

                    } else {

                        //Toast.makeText(mContext, "Sorry, there is not enough stock for ".concat(getVoucherName(prepaidRequest.getProductID())), Toast.LENGTH_SHORT).show();
                        //mFragment.showSnackBar("Sorry, there is not enough stock for ".concat(getVoucherName(prepaidRequest.getProductID())));

                    }

                    virtualVoucherQty.clearFocus();

                    break;
                }
            }
        }

        private String getVoucherName(String productID) {
            String mName = "";
            switch (productID) {
                case "GK100": {
                    mName = "GK 100";
                    break;
                }
                case "GK500": {
                    mName = "GK 500";
                    break;
                }
                case "GK1000": {
                    mName = "GK 1000";
                    break;
                }
            }
            return mName;
        }
    }
}
