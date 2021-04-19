package com.goodkredit.myapplication.fragments.rfid;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.rfid.RFIDAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.rfid.RFIDDBHelper;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.rfid.RFIDInfo;
import com.goodkredit.myapplication.responses.rfid.GetRFIDInformationResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RFIDFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private List<RFIDInfo> active_mGridData = new ArrayList<>();
    private RecyclerView rv_active_rfid;
    private RFIDAdapter activeAdapter;
    private List<RFIDInfo> inactive_mGridData = new ArrayList<>();
    private RecyclerView rv_inactive_rfid;
    private RFIDAdapter inactiveAdapter;

    private RelativeLayout emptyvoucher;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private DatabaseHandler mdb;
    private String imei;
    private String authcode;
    private String userid;
    private String sessionid;
    private String borrowerid;
    private String limit;

    private LinearLayout layout_active_rfid;
    private LinearLayout layout_inactive_rfid;
    private TextView txv_no_active_rfid;
    private TextView txv_no_inactive_rfid;

    private View view_rfid_active;
    private View view_rfid_inactive;

    //RFID NOT CONNECTED
    private RelativeLayout layout_rfid_not_connected;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_notification_0).setVisible(false);
        menu.findItem(R.id.action_notification_1).setVisible(false);
        menu.findItem(R.id.action_notification_2).setVisible(false);
        menu.findItem(R.id.action_notification_3).setVisible(false);
        menu.findItem(R.id.action_notification_4).setVisible(false);
        menu.findItem(R.id.action_notification_5).setVisible(false);
        menu.findItem(R.id.action_notification_6).setVisible(false);
        menu.findItem(R.id.action_notification_7).setVisible(false);
        menu.findItem(R.id.action_notification_8).setVisible(false);
        menu.findItem(R.id.action_notification_9).setVisible(false);
        menu.findItem(R.id.action_notification_9plus).setVisible(false);
        menu.findItem(R.id.action_process_queue).setVisible(false);
        menu.findItem(R.id.sortitem).setVisible(false);
        menu.findItem(R.id.summary).setVisible(false);
        menu.findItem(R.id.group_voucher).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rfid, container, false);

        init(view);
        initData();

        return view;
    }

    private void init(View view) {
        rv_active_rfid = view.findViewById(R.id.rv_active_rfid);
        activeAdapter = new RFIDAdapter(active_mGridData, getViewContext(), "activerfid");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rv_active_rfid.setNestedScrollingEnabled(false);
        rv_active_rfid.setLayoutManager(layoutManager);
        rv_active_rfid.setAdapter(activeAdapter);

        rv_inactive_rfid = view.findViewById(R.id.rv_inactive_rfid);
        inactiveAdapter = new RFIDAdapter(inactive_mGridData, getViewContext(), "inactiverfid");
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getViewContext());
        rv_inactive_rfid.setNestedScrollingEnabled(false);
        rv_inactive_rfid.setLayoutManager(layoutManager1);
        rv_inactive_rfid.setAdapter(inactiveAdapter);

        layout_active_rfid = view.findViewById(R.id.layout_active_rfid);
        layout_inactive_rfid = view.findViewById(R.id.layout_inactive_rfid);
        txv_no_active_rfid = view.findViewById(R.id.txv_no_active_rfid);
        txv_no_inactive_rfid = view.findViewById(R.id.txv_no_inactive_rfid);

        view_rfid_active = view.findViewById(R.id.view_rfid_active);
        view_rfid_inactive = view.findViewById(R.id.view_rfid_inactive);

        //initialize refresh
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        emptyvoucher = (RelativeLayout) view.findViewById(R.id.emptyvoucher);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshdisabled = (ImageView) view.findViewById(R.id.refreshdisabled);
        refreshdisabled1 = (ImageView) view.findViewById(R.id.refreshdisabled1);

        //process loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        refresh.setOnClickListener(this);
        refreshnointernet.setOnClickListener(this);

        //RFID NOT CONNECTED
        layout_rfid_not_connected = (RelativeLayout) view.findViewById(R.id.layout_rfid_not_connected);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        limit = "0";

        showNoRFID();

//        getSession();
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mTvFetching.setText("Fetching Enrolled RFIDs. ");
            mTvWait.setText("Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getRFIDInformation(getRFIDInformationSession);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getRFIDInformation(Callback<GetRFIDInformationResponse> getRFIDInformationCallback){
        Call<GetRFIDInformationResponse> getrfidinformation = RetrofitBuild.getRFIDAPIService(getViewContext())
                .getRFIDInformationCall(sessionid,
                        imei,
                        borrowerid,
                        userid,
                        authcode,
                        limit,
                        CommonVariables.devicetype);
        getrfidinformation.enqueue(getRFIDInformationCallback);
    }

    private final Callback<GetRFIDInformationResponse> getRFIDInformationSession = new Callback<GetRFIDInformationResponse>() {
        @Override
        public void onResponse(Call<GetRFIDInformationResponse> call, Response<GetRFIDInformationResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){

                    try{
                        mdb.truncateTable(mdb, RFIDDBHelper.TABLE_NAME);

                        List<RFIDInfo> list;
                        list = response.body().getRfidInformationList();

                        for(RFIDInfo rfid : list){
                            mdb.insertRFIDInfo(mdb, rfid);
                        }

                        updateActiveList(mdb.getActiveRFID(mdb));
                        updateInactiveList(mdb.getInActiveRFID(mdb));

                        showNoRFID();

//                        if(list == null|| list.isEmpty()){
//                            layout_rfid_not_connected.setVisibility(View.VISIBLE);
//                        } else{
//                            layout_rfid_not_connected.setVisibility(View.GONE);
//                            for(RFIDInfo rfid : list){
//                                mdb.insertRFIDInfo(mdb, rfid);
//                            }
//
//                            updateActiveList(mdb.getActiveRFID(mdb));
//                            updateInactiveList(mdb.getInActiveRFID(mdb));
//
//                            showNoRFID();
//                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                } else if(response.body().getStatus().equals("104")) {
                    showAutoLogoutDialog(response.body().getMessage());
                } else{
                    showErrorGlobalDialogs(response.body().getMessage());
                }

            } else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GetRFIDInformationResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void updateActiveList(List<RFIDInfo> data){
        if (data.size() > 0){
            emptyvoucher.setVisibility(View.GONE);
            activeAdapter.updateList(data);
            activeAdapter.notifyDataSetChanged();
        } else{
//            emptyvoucher.setVisibility(View.VISIBLE);
            activeAdapter.clear();
            activeAdapter.notifyDataSetChanged();
        }
    }

    private void updateInactiveList(List<RFIDInfo> data){
        if (data.size() > 0){
            emptyvoucher.setVisibility(View.GONE);
            inactiveAdapter.updateList(data);
            inactiveAdapter.notifyDataSetChanged();
        } else{
//            emptyvoucher.setVisibility(View.VISIBLE);
            inactiveAdapter.clear();
            inactiveAdapter.notifyDataSetChanged();
        }
    }

    //show no internet connection page
    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyvoucher.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
            mLlLoader.setVisibility(View.GONE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.refreshnointernet: {
                onRefresh();
                break;
            }
            case R.id.refresh: {
                onRefresh();
                break;
            }
        }

    }

    @Override
    public void onRefresh() {
        try {
            if(mdb != null){
                mdb.truncateTable(mdb, RFIDDBHelper.TABLE_NAME);

                if(activeAdapter != null){
                    activeAdapter.clear();
                }

                swipeRefreshLayout.setRefreshing(true);
                getSession();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getSession();
    }

    private void showNoRFID(){
        if (activeAdapter.getItemCount() == 0) {
            txv_no_active_rfid.setVisibility(View.VISIBLE);
            view_rfid_active.setVisibility(View.VISIBLE);
        } else{
            txv_no_active_rfid.setVisibility(View.GONE);
            view_rfid_active.setVisibility(View.GONE);
        }

        if(inactiveAdapter.getItemCount() == 0){
            txv_no_inactive_rfid.setVisibility(View.VISIBLE);
            view_rfid_inactive.setVisibility(View.VISIBLE);
        } else{
            txv_no_inactive_rfid.setVisibility(View.GONE);
            view_rfid_inactive.setVisibility(View.GONE);
        }
    }
}
