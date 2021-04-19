package com.goodkredit.myapplication.fragments.vouchers.payoutone;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.vouchers.payoutone.VoucherPayoutOneBankDepositHistoryQueueAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.vouchers.BankDepositHistoryQueue;
import com.goodkredit.myapplication.responses.vouchers.payoutone.BankDepositHistoryQueueResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherPayoutOneBankDepositQueueFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView rv_deposit_queue;
    private VoucherPayoutOneBankDepositHistoryQueueAdapter mAdapter;
    private List<BankDepositHistoryQueue> mGridData = new ArrayList<>();

    private String sessionid;
    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;
    private String year;

    private SwipeRefreshLayout swipe_container;
    private RelativeLayout emptyvoucher;
    private TextView txv_nodata;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private boolean isfirstload = true;

    public static VoucherPayoutOneBankDepositQueueFragment newInstance(String value) {
        VoucherPayoutOneBankDepositQueueFragment fragment = new VoucherPayoutOneBankDepositQueueFragment();
        Bundle b = new Bundle();
        b.putString("title", value);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voucher_payoutone_bank_deposit_history_queue, container, false);

        init(view);
        initData();

        return view;
    }

    private void init(View view) {
        rv_deposit_queue = view.findViewById(R.id.rv_deposit_history_queue);

        mAdapter = new VoucherPayoutOneBankDepositHistoryQueueAdapter(getViewContext(), "QUEUE");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rv_deposit_queue.setNestedScrollingEnabled(false);
        rv_deposit_queue.setLayoutManager(layoutManager);
        rv_deposit_queue.setAdapter(mAdapter);

        swipe_container = view.findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(this);
        emptyvoucher = (RelativeLayout) view.findViewById(R.id.emptyvoucher);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);

        txv_nodata = view.findViewById(R.id.txv_nodata);
        refresh.setOnClickListener(this);
        refreshnointernet.setOnClickListener(this);
    }

    private void initData() {
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
        year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        getSession();
    }

    private void getSession() {
        if(isfirstload) {
            showProgressDialog("Processing Request.", "Please wait...");
        }
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getBankDepositDetails(getBankDepositDetailsSession);
                }
            }, 1000);

        } else{
//            showNoInternetToast();
            showNoInternetConnection(true);
            swipe_container.setRefreshing(false);
            hideProgressDialog();
        }
    }

    private void getBankDepositDetails (Callback<BankDepositHistoryQueueResponse> getBankDepositDetailsCallback ){
        Call<BankDepositHistoryQueueResponse> getBankDepositDetails = RetrofitBuild.getVoucherV3Service(getViewContext())
                .getBankDepositDetailsCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        "DEPOSIT_QUEUE",
                        year,
                        CommonVariables.devicetype);
        getBankDepositDetails.enqueue(getBankDepositDetailsCallback);
    }

    private final Callback<BankDepositHistoryQueueResponse> getBankDepositDetailsSession = new Callback<BankDepositHistoryQueueResponse>() {
        @Override
        public void onResponse(Call<BankDepositHistoryQueueResponse> call, Response<BankDepositHistoryQueueResponse> response) {
            hideProgressDialog();
            ResponseBody errorBody = response.errorBody();
            swipe_container.setRefreshing(false);
            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    try{
                        isfirstload = false;
                        CacheManager.getInstance().saveBankDepositQueue(response.body().getBankDepositHistoryQueueList());
                        mGridData = CacheManager.getInstance().getBankDepositQueue();
                        updateList(mGridData);

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else if(response.body().getStatus().equals("104")) {
                    showAutoLogoutDialog(response.body().getMessage());
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }

            } else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<BankDepositHistoryQueueResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            swipe_container.setRefreshing(false);
            hideProgressDialog();
        }
    };

    private void updateList(List<BankDepositHistoryQueue> data){

        if (data.size() > 0){
            txv_nodata.setVisibility(View.GONE);
            emptyvoucher.setVisibility(View.GONE);
            mAdapter.updateList(data);
            mAdapter.notifyDataSetChanged();
            showNoInternetConnection(false);
        } else{
            txv_nodata.setText("No bank deposit queue yet.");
            txv_nodata.setVisibility(View.VISIBLE);
            emptyvoucher.setVisibility(View.VISIBLE);
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
            showNoInternetConnection(false);
        }
    }

    //show no internet connection page
    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyvoucher.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        swipe_container.setRefreshing(true);
        getSession();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.refreshnointernet:{
                onRefresh();
                break;
            }
            case R.id.refresh: {
                onRefresh();
                break;
            }
        }
    }
}
