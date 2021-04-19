package com.goodkredit.myapplication.adapter.vouchers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.grouping.GroupVoucherActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GetValidateGroupVoucherResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 4/27/2017.
 */

public class HorizontalUsedRVAdapter extends RecyclerView.Adapter<HorizontalUsedRVAdapter.UsedViewHolder> implements View.OnLongClickListener {

    private ArrayList<Voucher> mArrayList;
    private GroupVoucherActivity mGroupVoucherActivity;
    private Context mContext;
    private AQuery aq;

    private int mPosition = 0;

    private ProgressDialog mProgressDialog;

    private String sessionidval;
    private String mImei;
    private String mUserId;
    private String mBorrowerId;
    private String mMobileNumber;
    private String mVoucherSession;
    private String mVoucherCode;
    private String mVoucherPin;

    private Gson gson;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String authenticationid;
    private String index;
    private String param;
    private String keyEncryption;

    public HorizontalUsedRVAdapter(GroupVoucherActivity activity, ArrayList<Voucher> arrayList) {
        mGroupVoucherActivity = activity;
        mArrayList = arrayList;
        mContext = activity.getViewContext();
        aq = new AQuery(mContext);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setInverseBackgroundForced(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Removing voucher" + "\n" + "Please wait ...");

        mImei = PreferenceUtils.getImeiId(mContext);
        mUserId = PreferenceUtils.getUserId(mContext);
        mBorrowerId = PreferenceUtils.getBorrowerId(mContext);

        gson = new Gson();

    }

    public void setData(ArrayList<Voucher> arrayList) {
        mArrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public UsedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_voucher, parent, false);

        return new HorizontalUsedRVAdapter.UsedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UsedViewHolder holder, int position) {
        Voucher voucher = mArrayList.get(position);
        String imageLink = CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg";
        String balance = "";

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(mContext);

        aq.id(holder.mVoucherImage).image(imageLink, false, true);
        if (voucher.getExtra3().equals(".")) {
            holder.mVoucherTag.setVisibility(View.GONE);
        } else {
            holder.mVoucherTag.setVisibility(View.VISIBLE);
        }

        if (voucher.getRemainingBalance() % 1 == 0) {
            balance = String.valueOf((int) voucher.getRemainingBalance());
        } else {
            balance = String.valueOf(voucher.getRemainingBalance());
        }

        if (balance.length() < 5) {
            balance = "BAL: " + balance;
        }

        holder.mVoucherBalance.setText(balance);
        holder.mRemoveVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirmationDialogRemoval(holder.getAdapterPosition());
                confirmationDialogRemovalNew(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(this);
    }

    private void confirmationDialogRemoval(final int position) {
        new MaterialDialog.Builder(mContext)
                .content("Are you sure you want to remove the voucher?")
                .title("Remove Voucher")
                .cancelable(true)
                .negativeText("Cancel")
                .positiveText("Remove")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        verifySession();
                        mPosition = position;
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();
    }

    private void confirmationDialogRemovalNew(final int position) {
        GlobalDialogs dialog = new GlobalDialogs(mContext);

        dialog.createDialog("Notice", "Are you sure you want to remove the voucher?",
                "Cancel", "Remove", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View btndoubleone = dialog.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View btndoubletwo = dialog.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                verifySession();
                mPosition = position;
            }
        });
    }

    private final View.OnClickListener onRemoveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Voucher voucher = (Voucher) v.getTag();
            for (Voucher vouch : mArrayList) {
                if (voucher.getVoucherCode().equals(voucher.getVoucherCode())) {
                    mArrayList.remove(vouch);
                    notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    public class UsedViewHolder extends RecyclerView.ViewHolder {
        private ImageView mVoucherImage;
        private TextView mVoucherBalance;
        private ImageView mRemoveVoucher;
        private ImageView mVoucherTag;

        public UsedViewHolder(View view) {
            super(view);
            mRemoveVoucher = view.findViewById(R.id.removeVoucher);
            mVoucherImage = view.findViewById(R.id.imgv_voucher_image);
            mVoucherBalance = view.findViewById(R.id.tv_voucher_balance);
            mVoucherTag = view.findViewById(R.id.imgv_prepaid_tag);
        }
    }

    public ArrayList<Voucher> getData() {
        return mArrayList;
    }

    private void verifySession() {

        try {
            int status = CommonFunctions.getConnectivityStatus(mContext);
            if (status == 0) { //no connection
                ((BaseActivity) mContext).showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {
                if (mProgressDialog != null && !mProgressDialog.isShowing())
                    mProgressDialog.show();

                //new HttpAsyncTask().execute(CommonVariables.REMOVE_VOUCHER_FROM_GROUP);
                unGroupVoucherV2();
            }
        } catch (Exception e) {
            hideProgressDialog();
            e.printStackTrace();
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {

            String json = "";

            try {
                String authcode = CommonFunctions.getSha1Hex(mImei + mUserId + sessionid);
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", mBorrowerId);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", mImei);
                jsonObject.accumulate("userid", mUserId);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("vouchercode", mArrayList.get(mPosition).getVoucherCode());
                jsonObject.accumulate("vouchersession", mGroupVoucherActivity.getVoucherSession());

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);
        }

        @Override
        protected void onPostExecute(String result) {
            hideProgressDialog();
            try {
                if (result == null) {
                    ((BaseActivity) mContext).showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("001")) {
                    ((BaseActivity) mContext).showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    ((BaseActivity) mContext).showToast("Invalid Session.", GlobalToastEnum.NOTICE);
                } else if (result.equals("003")) {
                    ((BaseActivity) mContext).showToast("Invalid Guarantor ID.", GlobalToastEnum.NOTICE);
                } else if (result.equals("error")) {
                    ((BaseActivity) mContext).showToast("Cannot connect to server. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    ((BaseActivity) mContext).showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    //processResult(result);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    private void hideProgressDialog() {
        if (mProgressDialog != null && !mGroupVoucherActivity.isFinishing())
            mProgressDialog.dismiss();
    }

    private void errorDialog(String content) {
        new MaterialDialog.Builder(mContext)
                .content(content)
                .positiveText("OK");
    }

    /**
     *  SECURITY UPDATE
     *  AS OF
     *  OCTOBER 2019
     * **/

    private void unGroupVoucherV2(){
        if(CommonFunctions.getConnectivityStatus(mContext) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();

            parameters.put("imei",mImei);
            parameters.put("userid",mUserId);
            parameters.put("borrowerid",mBorrowerId);
            parameters.put("vouchercode",mArrayList.get(mPosition).getVoucherCode());
            parameters.put("vouchersession", mGroupVoucherActivity.getVoucherSession());


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(mContext, parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "unGroupIndividualVoucher");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            unGroupVoucherV2Object(unGroupVoucherV2Callback);

        }else{
            CommonFunctions.hideDialog();
            mGroupVoucherActivity.showNoInternetToast();

        }
    }
    private void unGroupVoucherV2Object(Callback<GenericResponse> groupVoucher) {
        Call<GenericResponse> call = RetrofitBuilder.getVoucherV2API(mContext)
                .unGroupIndividualVoucherV2(
                        authenticationid,sessionid,param
                );
        call.enqueue(groupVoucher);
    }
    private Callback<GenericResponse> unGroupVoucherV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody =  response.errorBody();
            hideProgressDialog();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    ArrayList<Voucher> arr = mArrayList;
                    mGroupVoucherActivity.refreshIndivListAfterRemovel(arr.remove(mPosition));
                    setData(arr);
                    mGroupVoucherActivity.setSubTotal(arr);

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                    mGroupVoucherActivity.showAutoLogoutDialog(mContext.getString(R.string.logoutmessage));
                }else{
                    mGroupVoucherActivity.showErrorGlobalDialogs(message);
                }
            }else{
                mGroupVoucherActivity.showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            t.printStackTrace();
            mGroupVoucherActivity.showErrorGlobalDialogs();
        }
    };

}
