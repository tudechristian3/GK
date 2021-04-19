package com.goodkredit.myapplication.adapter.gknegosyo;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoDistributorDetailsActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.model.gknegosyo.Distributor;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class GKNegosyoDistributorsAdapter extends RecyclerView.Adapter<GKNegosyoDistributorsAdapter.DitributorViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private List<Distributor> mDistributors = new ArrayList<>();

    private Address mAddress;

    private Geocoder geocoder;
    private GKService mGKService;

    ProgressDialog progressDialog = null;


    public GKNegosyoDistributorsAdapter(Context context, GKService gkService) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        geocoder = new Geocoder(mContext);
        mGKService = gkService;
        mAddress = getAddressFromLatLng(new LatLng(
                Double.parseDouble(PreferenceUtils.getStringPreference(mContext, PreferenceUtils.KEY_LAST_KNOWN_LATITUDE)),
                Double.parseDouble(PreferenceUtils.getStringPreference(mContext, PreferenceUtils.KEY_LAST_KNOWN_LONGITUDE))));
    }

    public void setData(List<Distributor> distributors) {
        mDistributors.clear();
        mDistributors = distributors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DitributorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gk_negosyo_distributors, parent, false);
        return new DitributorViewHolder(view);
    }

    private Address getAddressFromLatLng(LatLng latLng) {
        Address address = null;
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList.size() > 0) {
                address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    @Override
    public void onBindViewHolder(@NonNull DitributorViewHolder holder, int position) {
        Distributor distributor = mDistributors.get(position);

        holder.name.setText(distributor.getFullName());
        holder.id.setText(distributor.getDistributorID());

        Glide.with(mContext)
                .load(R.drawable.ic_gknegosyo_distributor)
                .into(holder.icon);

        holder.itemView.setTag(distributor);
        holder.itemView.setOnClickListener(distroClickListener);

    }

    private View.OnClickListener distroClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Distributor distributor = (Distributor) v.getTag();
            if(progressDialog == null){
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if(progressDialog.isShowing() && progressDialog != null){
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                GKNegosyoDistributorDetailsActivity.start(mContext, distributor, mGKService, mAddress);
            }, 1000);

        }
    };

    @Override
    public int getItemCount() {
        return mDistributors.size();
    }

    public class DitributorViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView id;
        private ImageView icon;

        public DitributorViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            id = itemView.findViewById(R.id.tv_id);
            icon = itemView.findViewById(R.id.img_distributor_icon);
        }
    }



}
