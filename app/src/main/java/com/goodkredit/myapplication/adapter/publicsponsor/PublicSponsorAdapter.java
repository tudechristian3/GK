package com.goodkredit.myapplication.adapter.publicsponsor;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.publicsponsor.PublicSponsorDetailsActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.publicsponsor.PublicSponsor;

import java.util.List;

import hk.ids.gws.android.sclick.SClick;

public class PublicSponsorAdapter extends RecyclerView.Adapter<PublicSponsorAdapter.ViewHolder> {

    private List<PublicSponsor> mGridData;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public PublicSponsorAdapter(List<PublicSponsor> mGridData, Context mContext) {
        this.mGridData = mGridData;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        setHasStableIds(true);
    }

    public void updateList(List<PublicSponsor> mGridData) {
        this.mGridData = mGridData;
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mGridData.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PublicSponsorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_guarantors_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PublicSponsorAdapter.ViewHolder holder, int position) {

        PublicSponsor sponsor = mGridData.get(position);

        String sponsorlogo = CommonFunctions.parseXML(sponsor.getNotes1(), "logo");
        String sponsoraddress = CommonFunctions.replaceEscapeData(sponsor.getStreetAddress()
                .concat(", ").concat(sponsor.getCity()).concat(", ").concat(sponsor.getCountry()));

        holder.txv_sponsorname.setText(CommonFunctions.replaceEscapeData(sponsor.getGuarantorName()));
        holder.txv_sponsoraddress.setText(sponsoraddress.toUpperCase());

        if(sponsorlogo == null || sponsorlogo.equals("") || sponsorlogo.equals("NONE")){
            Glide.with(mContext)
                    .load(R.drawable.emptylogdefault)
                    .into(holder.img_sponsorlogo);
        } else{
            Glide.with(mContext)
                    .load(CommonFunctions.parseXML(sponsor.getNotes1(), "logo"))
                    .into(holder.img_sponsorlogo);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img_sponsorlogo;
        private TextView txv_sponsorname;
        private TextView txv_sponsoraddress;

        public ViewHolder(View itemView) {
            super(itemView);

            img_sponsorlogo = itemView.findViewById(R.id.guarantorlogo);
            txv_sponsorname = itemView.findViewById(R.id.guarantorname);
            txv_sponsoraddress = itemView.findViewById(R.id.guarantoraddress);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            PublicSponsor sponsor = mGridData.get(position);

            if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
            Intent intent = new Intent(mContext, PublicSponsorDetailsActivity.class);
            intent.putExtra(PublicSponsor.KEY_PUBLICSPONSOR, sponsor);
            mContext.startActivity(intent);
        }
    }
}
