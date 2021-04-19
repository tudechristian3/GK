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
import com.goodkredit.myapplication.bean.Transaction;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.transactions.BorrowingsFragment;
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
 * Created by User-PC on 7/29/2017.
 */

public class BorrowingsRecyclerAdapter extends RecyclerView.Adapter<BorrowingsRecyclerAdapter.MyViewHolder> {

    private final Context mContext;
    private List<Transaction> mGridData;

    //Voucher Variables
    private String borrowerid = "";
    private String imei = "";
//    private String sessionid = "";
    private String authcode = "";
    private String userid = "";
    private String transactionno = "";
    private String datetransaction = "";
    private List<Voucher> arrayList;

    private DatabaseHandler db;
    private final BorrowingsFragment fragment;

    //UNIFIED SESSION
    private String sessionid = "";

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

        public MyViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view_borrowings_details);
            btnHideDetails = (Button) itemView.findViewById(R.id.btn_hide_voucher_details);
            btnViewDetails = (Button) itemView.findViewById(R.id.btn_view_voucher_details);
            vouchersLayout = (LinearLayout) itemView.findViewById(R.id.voucher_details_layout);
            borrowingsDate = (TextView) itemView.findViewById(R.id.borrowings_date);
            date = (TextView) itemView.findViewById(R.id.date);
            transno = (TextView) itemView.findViewById(R.id.transactionno);
            numVouchers = (TextView) itemView.findViewById(R.id.totalvoucher);
            totalAmount = (TextView) itemView.findViewById(R.id.totalamount);
            pbrecyclerProgress = (ProgressBar) itemView.findViewById(R.id.pbrecyclerProgress);


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
                    if (position != -1) {

                        arrayList = new ArrayList<>();

                        Transaction item = mGridData.get(position);
                        transactionno = item.getTransactionNumber();
                        datetransaction = item.getDateCompleted();

                        if (db.getBorrowingVoucherDetailsData(db, transactionno).size() > 0) {
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setNestedScrollingEnabled(false);
                            mDetailsAdapter = new BorrowingsVoucherDetailsAdapter(getContext(), db.getBorrowingVoucherDetailsData(db, transactionno));
                            recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(getContext(), null));
                            recyclerView.setAdapter(mDetailsAdapter);
                            recyclerView.setLayoutFrozen(true);
                        } else {
                            pbrecyclerProgress.setVisibility(View.VISIBLE);
//                            fragment.createSession(callsession);

                            getSession();
                        }

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
                            String.valueOf(CommonFunctions.getCalendarFromDate(datetransaction).get(Calendar.YEAR)), String.valueOf(CommonFunctions.getCalendarFromDate(datetransaction).get(Calendar.MONTH) + 1), transactionno);
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
                        ((BaseActivity)mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
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
                            db.insertBorrowingsVoucherDetailsData(db, voucher);
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setNestedScrollingEnabled(false);
                        mDetailsAdapter = new BorrowingsVoucherDetailsAdapter(getContext(), db.getBorrowingVoucherDetailsData(db, transactionno));
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

    public BorrowingsRecyclerAdapter(Context context, List<Transaction> mGridData, BorrowingsFragment fragment) {
        this.mContext = context;
        this.mGridData = mGridData;
        this.arrayList = new ArrayList<>();
        this.fragment = fragment;
        //initialized local database
        db = new DatabaseHandler(getContext());
    }

    private Context getContext() {
        return mContext;
    }

    public void setBorrowingsData(List<Transaction> mGridData) {
        this.mGridData.clear();
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public BorrowingsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_transactions_borrowings_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BorrowingsRecyclerAdapter.MyViewHolder holder, int position) {
        final String transactionNum = mGridData.get(position).getTransactionNumber();
        final String numOfVouchers = mGridData.get(position).getNumOfVoucher();
        final String totalAmount = mGridData.get(position).getTotalVoucherAmount();
        final String time = mGridData.get(position).getDateCompleted();
        final String date = mGridData.get(position).getDateCompleted();

        holder.borrowingsDate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(date)));
        holder.date.setText(CommonFunctions.getTimeFromDateTime(CommonFunctions.convertDate(time)));
        holder.transno.setText(transactionNum);
        holder.numVouchers.setText(numOfVouchers);
        holder.totalAmount.setText(totalAmount);
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

}
