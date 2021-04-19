package com.goodkredit.myapplication.adapter.transactions;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.PrepaidVoucherTransaction;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.transactions.PurchasesFragmentV2;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User-PC on 8/2/2017.
 */

public class PurchasesRecyclerAdapter extends RecyclerView.Adapter<PurchasesRecyclerAdapter.MyViewHolder> {

    private final Context mContext;
    private List<PrepaidVoucherTransaction> mGridData;

    private List<Voucher> mVoucherData;
    private PurchasesFragmentV2 fragment;
    //Voucher Variables
    private String borrowerid = "";
    private String imei = "";
//    private String sessionid = "";
    private String authcode = "";
    private String userid = "";
    private String transactionno = "";
    private String datetransaction = "";

    private DatabaseHandler db;

    //UNIFIED SESSION
    private String sessionid = "";

    public PurchasesRecyclerAdapter(Context context, List<PrepaidVoucherTransaction> mGridData, PurchasesFragmentV2 fragment) {
        this.mContext = context;
        this.mGridData = mGridData;
        this.fragment = fragment;
        //initialized local database
        db = new DatabaseHandler(getContext());
    }

    private Context getContext() {
        return mContext;
    }

    public void setPurchasesData(List<PrepaidVoucherTransaction> mGridData) {
        this.mGridData.clear();
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public PurchasesRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_transactions_purchases_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PurchasesRecyclerAdapter.MyViewHolder holder, int position) {
        PrepaidVoucherTransaction item = mGridData.get(position);

        final String transactionNum = item.getTransactionNo();
        final String numOfVouchers = String.valueOf(item.getTotalNoOfVouchers());
        final String totalAmount = String.valueOf(item.getTotalVoucherAmount());
        final String time = item.getDateTimeCompleted();
        final String date = item.getDateTimeCompleted();

        holder.borrowingsDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(date)));
        holder.date.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(time)));
        holder.transno.setText(transactionNum);
        holder.numVouchers.setText(numOfVouchers);
        holder.totalAmount.setText(CommonFunctions.currencyFormatter(totalAmount));

        if (item.getVoucherGenerationID() != null && item.getPaymentTxnNo() != null) {
            if (!item.getVoucherGenerationID().isEmpty() && !item.getPaymentTxnNo().isEmpty()) {
                holder.billingNoLayout.setVisibility(View.VISIBLE);
                holder.paymentTxnLayout.setVisibility(View.VISIBLE);
                holder.billingNo.setText(item.getVoucherGenerationID());
                holder.paymentTxnNo.setText(item.getPaymentTxnNo());
            } else {
                holder.billingNoLayout.setVisibility(View.GONE);
                holder.paymentTxnLayout.setVisibility(View.GONE);
            }
        } else {
            holder.billingNoLayout.setVisibility(View.GONE);
            holder.paymentTxnLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView date;
        private final TextView transno;
        private final TextView numVouchers;
        private final TextView totalAmount;
        private final TextView borrowingsDate;
        private final Button btnViewDetails;
        private final Button btnHideDetails;
        private final LinearLayout vouchersLayout;
        private final RecyclerView recyclerView;
        private BorrowingsVoucherDetailsAdapter mDetailsAdapter;
        private ProgressBar pbrecyclerProgress;

        private final LinearLayout billingNoLayout;
        private final LinearLayout paymentTxnLayout;
        private final TextView billingNo;
        private final TextView paymentTxnNo;

        public MyViewHolder(View itemView) {
            super(itemView);
            btnHideDetails = (Button) itemView.findViewById(R.id.btn_hide_voucher_details);
            btnViewDetails = (Button) itemView.findViewById(R.id.btn_view_voucher_details);
            borrowingsDate = (TextView) itemView.findViewById(R.id.borrowings_date);
            date = (TextView) itemView.findViewById(R.id.date);
            transno = (TextView) itemView.findViewById(R.id.transactionno);
            numVouchers = (TextView) itemView.findViewById(R.id.totalvoucher);
            totalAmount = (TextView) itemView.findViewById(R.id.totalamount);
            vouchersLayout = (LinearLayout) itemView.findViewById(R.id.voucher_details_layout);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view_borrowings_details);
            pbrecyclerProgress = (ProgressBar) itemView.findViewById(R.id.pbrecyclerProgress);
            mVoucherData = new ArrayList<>();

            billingNoLayout = (LinearLayout) itemView.findViewById(R.id.billingNoLayout);
            paymentTxnLayout = (LinearLayout) itemView.findViewById(R.id.paymentTxnLayout);
            billingNo = (TextView) itemView.findViewById(R.id.billingNo);
            paymentTxnNo = (TextView) itemView.findViewById(R.id.paymentTxnNo);

            btnViewDetails.setOnClickListener(this);
            btnHideDetails.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            switch (v.getId()) {
                case R.id.btn_view_voucher_details: {
                    btnViewDetails.setVisibility(View.GONE);
                    btnHideDetails.setVisibility(View.VISIBLE);
                    vouchersLayout.setVisibility(View.VISIBLE);

                    PrepaidVoucherTransaction item = mGridData.get(position);
                    transactionno = item.getTransactionNo();
                    datetransaction = item.getDateTimeCompleted();

                    if (db.getPurchasesVoucherDetailsData(db, transactionno).size() > 0) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setNestedScrollingEnabled(false);
                        mDetailsAdapter = new BorrowingsVoucherDetailsAdapter(getContext(), db.getPurchasesVoucherDetailsData(db, transactionno), true);
                        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(getContext(), null));
                        recyclerView.setAdapter(mDetailsAdapter);
                        recyclerView.setLayoutFrozen(true);
                    } else {
                        pbrecyclerProgress.setVisibility(View.VISIBLE);
//                        fragment.createSession(callsession);

                        getSession();
                    }

                    break;
                }
                case R.id.btn_hide_voucher_details: {
                    pbrecyclerProgress.setVisibility(View.GONE);
                    btnHideDetails.setVisibility(View.GONE);
                    btnViewDetails.setVisibility(View.VISIBLE);
                    vouchersLayout.setVisibility(View.GONE);

                    break;
                }
            }
        }

        private void getSession() {
            //get account information
            imei = PreferenceUtils.getImeiId(getContext());
            userid = PreferenceUtils.getUserId(getContext());
            borrowerid = PreferenceUtils.getBorrowerId(getContext());

            sessionid = PreferenceUtils.getSessionID(mContext);
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            getVoucherDetails(getVoucherDetailsSession);
        }

//        private final Callback<String> callsession = new Callback<String>() {
//
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                String responseData = response.body().toString();
//                if (!responseData.isEmpty()) {
//                    if (responseData.equals("001")) {
//                        pbrecyclerProgress.setVisibility(View.GONE);
//                        ((BaseActivity) mContext).showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                    } else if (responseData.equals("error")) {
//                        pbrecyclerProgress.setVisibility(View.GONE);
//                        ((BaseActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    } else if (responseData.contains("<!DOCTYPE html>")) {
//                        pbrecyclerProgress.setVisibility(View.GONE);
//                        ((BaseActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    } else {
//                        //get account information
//                        imei = PreferenceUtils.getImeiId(getContext());
//                        userid = PreferenceUtils.getUserId(getContext());
//                        borrowerid = PreferenceUtils.getBorrowerId(getContext());
//
//                        sessionid = response.body().toString();
//                        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                        getVoucherDetails(getVoucherDetailsSession);
//                    }
//                } else {
//                    pbrecyclerProgress.setVisibility(View.GONE);
//                    ((BaseActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                pbrecyclerProgress.setVisibility(View.GONE);
//                ((BaseActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        };

        private void getVoucherDetails(Callback<String> getVoucherDetailsCallback) {
            Call<String> getvoucherdetails = RetrofitBuild.getVoucherDetailsService(getContext())
                    .getVoucherDetailsCall(sessionid,
                            imei,
                            userid,
                            borrowerid,
                            authcode,
                            String.valueOf(CommonFunctions.getCalendarFromDate(datetransaction).get(Calendar.YEAR)),
                            String.valueOf(CommonFunctions.getCalendarFromDate(datetransaction).get(Calendar.MONTH) + 1),
                            transactionno);
            getvoucherdetails.enqueue(getVoucherDetailsCallback);
        }

        private final Callback<String> getVoucherDetailsSession = new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                pbrecyclerProgress.setVisibility(View.GONE);
                String responseData = response.body().toString();

                if (!responseData.isEmpty()) {
                    if (responseData.equals("001")) {
                        ((BaseActivity) mContext).showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
                    } else if (responseData.equals("error")) {
                        ((BaseActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                    } else if (responseData.contains("<!DOCTYPE html>")) {
                        ((BaseActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                    } else {

                        List<Voucher> vouchers = new ArrayList<>();
                        JSONArray jsonArr = null;
                        try {
                            jsonArr = new JSONArray(responseData);
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);
                                String transactionno = jsonObj.getString("TransactionNo");
                                String productid = jsonObj.getString("ProductID");
                                String vouchercode = jsonObj.getString("VoucherCode");
                                String voucherdenom = jsonObj.getString("VoucherDenom");
                                String voucherserial = jsonObj.getString("VoucherSerialNo");

                                vouchers.add(new Voucher(transactionno, productid, vouchercode, Double.parseDouble(voucherdenom), voucherserial));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (Voucher voucher : vouchers) {
                            db.insertPurchasesVoucherDetailsData(db, voucher);
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setNestedScrollingEnabled(false);
                        mDetailsAdapter = new BorrowingsVoucherDetailsAdapter(getContext(), db.getPurchasesVoucherDetailsData(db, transactionno), true);
                        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(getContext(), null));
                        recyclerView.setAdapter(mDetailsAdapter);
                        recyclerView.setLayoutFrozen(true);
                    }
                } else {
                    pbrecyclerProgress.setVisibility(View.GONE);
                    ((BaseActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                pbrecyclerProgress.setVisibility(View.GONE);
                ((BaseActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        };

    }


}
