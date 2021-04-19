package com.goodkredit.myapplication.adapter.gknegosyo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoPackageMerchantsWithDiscountPQR;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.model.GKNegosyoPackageDiscountedService;
import com.goodkredit.myapplication.responses.gknegosyo.GetDiscounterMerchantsResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKNegosyoPackageDiscountPerServiceAdapter extends RecyclerView.Adapter<GKNegosyoPackageDiscountPerServiceAdapter.DiscountPerServiceViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private List<GKNegosyoPackageDiscountedService> mGKNPDiscountPerService;

    private String imei;
    private String userid;
    private String borrowerid;
    private String sessionid;

    private GKNegosyoPackageDiscountedService mDiscount;

    public GKNegosyoPackageDiscountPerServiceAdapter(Context context, List<GKNegosyoPackageDiscountedService> gknpDiscountPerService) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mGKNPDiscountPerService = gknpDiscountPerService;

        imei = PreferenceUtils.getImeiId(mContext);
        userid = PreferenceUtils.getUserId(mContext);
        borrowerid = PreferenceUtils.getBorrowerId(mContext);
        sessionid = PreferenceUtils.getSessionID(mContext);

    }

    @NonNull
    @Override
    public GKNegosyoPackageDiscountPerServiceAdapter.DiscountPerServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gk_negosyo_discountperservices, parent, false);
        return new DiscountPerServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GKNegosyoPackageDiscountPerServiceAdapter.DiscountPerServiceViewHolder holder, int position) {
        GKNegosyoPackageDiscountedService discount = mGKNPDiscountPerService.get(position);
        String disc = "₱0.00";
        if (discount.getResellerBaseFee() > 0 && discount.getResellerVariableFee() > 0) {
            disc = "₱" + CommonFunctions.currencyFormatter(String.valueOf(discount.getResellerBaseFee())) + " + " + String.valueOf(discount.getResellerVariableFee() * 100) + "%";
        } else if (discount.getResellerBaseFee() == 0 && discount.getResellerVariableFee() > 0) {
            disc = String.valueOf(discount.getResellerVariableFee() * 100) + "%";
        } else if (discount.getResellerBaseFee() > 0 && discount.getResellerVariableFee() == 0) {
            disc = "₱" + CommonFunctions.currencyFormatter(String.valueOf(discount.getResellerBaseFee()));
        }
        holder.tv_base.setText(disc);
        holder.tv_service_name.setText(discount.getGKService());

        if (discount.getGKService().equals("Pay by QR") && discount.getGKService() != null) {
            holder.tv_service_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_info_outline_np, 0);
            holder.tv_service_name.setCompoundDrawablePadding(1);
            holder.tv_service_name.setTag(discount);
            holder.tv_service_name.setOnClickListener(paybyqrServiceOnClickListener);
            mDiscount = discount;
        }
    }

    private View.OnClickListener paybyqrServiceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createSession();
        }
    };

//    private void createSession() {
//        ((BaseActivity) mContext).showProgressDialog(mContext, "", "Please wait...");
//        Call<String> getSession = RetrofitBuild.getCommonApiService(mContext).getRegisteredSession(imei, userid);
//        getSession.enqueue(getSessionCallback);
//    }
//
//    private Callback<String> getSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            try {
//                //((BaseActivity) mContext).hideProgressDialog();
//                ResponseBody errBody = response.errorBody();
//                if (errBody == null) {
//                    String responseData = response.body();
//                    if (!responseData.isEmpty()) {
//                        if (responseData.equals("001") || responseData.equals("002")) {
//                            ((BaseActivity) mContext).hideProgressDialog();
//                            ((BaseActivity) mContext).showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                        } else if (responseData.equals("error")) {
//                            ((BaseActivity) mContext).hideProgressDialog();
//                            ((BaseActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                        } else if (responseData.contains("<!DOCTYPE html>")) {
//                            ((BaseActivity) mContext).hideProgressDialog();
//                            ((BaseActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                        } else {
//                            sessionid = responseData;
//                            getDiscounterMerchants();
//                        }
//                    } else {
//                        ((BaseActivity) mContext).hideProgressDialog();
//                        ((BaseActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    }
//                } else {
//                    ((BaseActivity) mContext).showError();
//                }
//            } catch (Exception e) {
//                ((BaseActivity) mContext).hideProgressDialog();
//                ((BaseActivity) mContext).showError();
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            ((BaseActivity) mContext).hideProgressDialog();
//            ((BaseActivity) mContext).showError();
//        }
//    };

    private void createSession() {
        if(CommonFunctions.getConnectivityStatus(mContext) > 0) {
            ((BaseActivity) mContext).showProgressDialog(mContext, "", "Please wait...");
            getDiscounterMerchants();
        } else {
            ((BaseActivity) mContext).hideProgressDialog();
            ((BaseActivity) mContext).showNoInternetToast();
        }
    }

    private void getDiscounterMerchants() {
        Call<GetDiscounterMerchantsResponse> call = RetrofitBuild.getGKNegosyoAPIService(mContext)
                .getDiscountedMerchants(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        sessionid,
                        mDiscount.getServiceName()
                );

        call.enqueue(getDiscounterMerchantsCallback);
    }

    private Callback<GetDiscounterMerchantsResponse> getDiscounterMerchantsCallback = new Callback<GetDiscounterMerchantsResponse>() {
        @Override
        public void onResponse(Call<GetDiscounterMerchantsResponse> call, Response<GetDiscounterMerchantsResponse> response) {
            ((BaseActivity) mContext).hideProgressDialog();
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        if (response.body().getData().size() > 0) {
                            CacheManager.getInstance().saveMerchantsWithDiscount(response.body().getData());
                            GKNegosyoPackageMerchantsWithDiscountPQR.start(mContext);
                        } else {
                            ((BaseActivity) mContext).showError("Not applicable to any merchant yet.");
                        }
                    } else {
                        ((BaseActivity) mContext).showError(response.body().getMessage());
                    }
                } else {
                    ((BaseActivity) mContext).showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetDiscounterMerchantsResponse> call, Throwable t) {
            ((BaseActivity) mContext).hideProgressDialog();
        }
    };

    @Override
    public int getItemCount() {
        return mGKNPDiscountPerService.size();
    }

    public class DiscountPerServiceViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_base;
        //private TextView tv_percentage;
        private TextView tv_service_name;

        public DiscountPerServiceViewHolder(View itemView) {
            super(itemView);
            tv_base = itemView.findViewById(R.id.tv_base);
            // tv_percentage = itemView.findViewById(R.id.tv_percentage);
            tv_service_name = itemView.findViewById(R.id.tv_service_name);
        }
    }
}
