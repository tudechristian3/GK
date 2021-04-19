package com.goodkredit.myapplication.adapter.rfid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.rfid.RFIDPaymentOptionsActvity;
import com.goodkredit.myapplication.model.GKPaymentOptions;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.List;

public class RFIDPaymentAdapter extends RecyclerView.Adapter<RFIDPaymentAdapter.MyViewHolder> {
    private Context mContext;
    private List<GKPaymentOptions> gkPaymentOptionsList;
    private ArrayList<GKPaymentOptions> passedgkPaymentOptionsList;
    private int mSelectedItem = -1;

    public RFIDPaymentAdapter(Context context) {
        mContext = context;
        gkPaymentOptionsList = new ArrayList<>();
        passedgkPaymentOptionsList = new ArrayList<>();
    }

    public void updateData(List<GKPaymentOptions> arraylist) {
        gkPaymentOptionsList.clear();
        gkPaymentOptionsList = arraylist;
        notifyDataSetChanged();
    }

    public void clear() {
        gkPaymentOptionsList.clear();
        notifyDataSetChanged();
    }

    @Override
    public RFIDPaymentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rfid_paymentoptions_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(RFIDPaymentAdapter.MyViewHolder holder, int position) {
        try {
            GKPaymentOptions gKPaymentOptions = gkPaymentOptionsList.get(position);

            holder.rb_paymentoptions.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, gKPaymentOptions.getPaymentDescription()));
            setRadioButtonImages(holder);

            holder.rb_paymentoptions.setChecked(position == mSelectedItem);

            holder.main_container.setOnClickListener(itemSelectedListener);
            holder.main_container.setTag(holder);

            holder.rb_paymentoptions.setOnClickListener(itemSelectedListener);
            holder.rb_paymentoptions.setTag(holder);

            //setRadioToMyPaymentType(holder);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void setRadioButtonImages(MyViewHolder holder) {
        GKPaymentOptions gKPaymentOptions = gkPaymentOptionsList.get(holder.getAdapterPosition());
        String title = gKPaymentOptions.getPaymentName();
        String imageurl = gKPaymentOptions.getPaymentImageUrl();

        switch (title) {
            case "GKVOUCHER":
                if (!imageurl.equals("") && !imageurl.equals("imageurl")) {
                    Glide.with(mContext)
                            .load(imageurl)
                            .into(new CustomTarget<Drawable>() {

                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    holder.rb_paymentoptions.setCompoundDrawablesWithIntrinsicBounds(null, null, resource, null);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                    holder.rb_paymentoptions.setCompoundDrawablesWithIntrinsicBounds(null, null, placeholder, null);
                                }
                            });
                } else {
                    holder.rb_paymentoptions.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_votes_voucher, 0);
                }

                break;

            case "PCP":
                if (!imageurl.equals("") && !imageurl.equals("imageurl")) {
                    Glide.with(mContext)
                            .load(imageurl)
                            .into(new CustomTarget<Drawable>() {

                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    holder.rb_paymentoptions.setCompoundDrawablesWithIntrinsicBounds(null, null, resource, null);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                    holder.rb_paymentoptions.setCompoundDrawablesWithIntrinsicBounds(null, null, placeholder, null);
                                }
                            });
                } else {
                    holder.rb_paymentoptions.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_votes_payment_channel, 0);
                }
                break;

            case "EGHL":
                if (!imageurl.equals("") && !imageurl.equals("imageurl")) {
                    Glide.with(mContext)
                            .load(imageurl)
                            .into(new CustomTarget<Drawable>() {

                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    holder.rb_paymentoptions.setCompoundDrawablesWithIntrinsicBounds(null, null, resource, null);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                    holder.rb_paymentoptions.setCompoundDrawablesWithIntrinsicBounds(null, null, placeholder, null);
                                }
                            });
                } else {
                    holder.rb_paymentoptions.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_votes_card, 0);
                }

                break;
        }
    }

    //RADIO LISTENER
    private View.OnClickListener itemSelectedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final RFIDPaymentAdapter.MyViewHolder holder = (MyViewHolder) v.getTag();

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
        if (passedgkPaymentOptionsList != null) {
            GKPaymentOptions gKPaymentOptions = gkPaymentOptionsList.get(holder.getAdapterPosition());

            String name = gKPaymentOptions.getPaymentName();
            String description = gKPaymentOptions.getPaymentDescription();
            String imageurl = gKPaymentOptions.getPaymentImageUrl();
            String status = gKPaymentOptions.getPaymentStatus();


            passedgkPaymentOptionsList.add(new GKPaymentOptions(name, description, imageurl, status));

            ((RFIDPaymentOptionsActvity) mContext).selectPaymentOptions(passedgkPaymentOptionsList);
        }
    }

    private void clearSelection(MyViewHolder holder) {
        if (passedgkPaymentOptionsList != null) {
            passedgkPaymentOptionsList.clear();
            ((RFIDPaymentOptionsActvity) mContext).selectPaymentOptions(passedgkPaymentOptionsList);
        }
    }

    private void setRadioToMyPaymentType(MyViewHolder holder) {
        holder.rb_paymentoptions.setChecked(true);
        clearSelection(holder);
        addSelection(holder);
    }

    @Override
    public int getItemCount() {
        return gkPaymentOptionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RadioButton rb_paymentoptions;
        private LinearLayout main_container;

        public MyViewHolder(View itemView) {
            super(itemView);
            main_container = (LinearLayout) itemView.findViewById(R.id.main_container);
            rb_paymentoptions = (RadioButton) itemView.findViewById(R.id.rb_paymentoptions);
        }

        @Override
        public void onClick(View view) {


        }
    }
}
