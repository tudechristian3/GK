package com.goodkredit.myapplication.fragments.dropoff;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.dropoff.DropOffOrderAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.dropoff.DropOffOrder;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.LayoutVisibilityEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.dropoff.GetDropOffPendingOrdersResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DropOffPendingFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private DropOffOrderAdapter mAdapter;
    private RecyclerView recyclerView;
    private DatabaseHandler mdb;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private boolean isLoading = false;
    private boolean isfirstload = true;
    private boolean isloadmore = false;
    private boolean isbottomscroll;
    private boolean isScroll = true;
    private List<DropOffOrder> dropOffOrderList;
    List<DropOffOrder> dropOffOrderArrayList = new ArrayList<>();

    private int limit = 0;

    //loader
    static  RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //swipe refresh
    static SwipeRefreshLayout mSwipeRefreshLayout;

    //no internet connection
    static  RelativeLayout nointernetconnection;
    private Button refreshnointernet;

    //empty layout
    static RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;
    private ImageView refreshdisabled;

    private NestedScrollView nested_scroll;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;


    public static DropOffPendingFragment newInstance(String value) {
        DropOffPendingFragment fragment = new DropOffPendingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", value);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dropoff_pending, container, false);
        try{
            init(view);
            initData();
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DropOffOrderAdapter(getViewContext());
//        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.ic_dropoff_divider), false, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);

        nested_scroll = view.findViewById(R.id.nested_scroll);
        nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll DOWN");
                }

                if (scrollY < oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll UP");
                }

                if (scrollY == 0) {
//                    Logger.debug("antonhttp", "TOP SCROLL");
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    Logger.debug("antonhttp", "======BOTTOM SCROLL=======");
                    isbottomscroll = true;
                    if (isloadmore) {
                        if (!isfirstload) {
                            limit = limit + 30;
                            getLoadMoreSession();
                        }
                    }
                }
            }
        });

        //swipe refresh
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        //empty layout
        emptyLayout = view.findViewById(R.id.emptyLayout);
        textView11 = view.findViewById(R.id.textView11);
        refresh = view.findViewById(R.id.refresh);
        refreshdisabled = view.findViewById(R.id.refreshdisabled);
        refresh.setOnClickListener(this);

        //no internet connection
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet = view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //loader
        mLlLoader = view.findViewById(R.id.loaderLayout);
        mTvFetching = view.findViewById(R.id.fetching);
        mTvWait = view.findViewById(R.id.wait);

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        dropOffOrderList = new ArrayList<>();

        new LongFirstOperation().execute();
    }

    private class LongFirstOperation extends AsyncTask<List<DropOffOrder>, Void, List<DropOffOrder>> {

        @Override
        protected void onPreExecute() {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Fetching pending request");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<DropOffOrder> doInBackground(List<DropOffOrder>... lists) {

            try {
                Thread.sleep(1000);
                Logger.debug("antonhttp", "FETCHING DATA IN ORDER PENDING");

            } catch (InterruptedException e) {
                Thread.interrupted();
            }

            return mdb.getDropOffOrderPending(mdb);
        }

        @Override
        protected void onPostExecute(List<DropOffOrder> dropOffOrders) {

            Logger.debug("antonhttp", "dropOffOrders: " + dropOffOrders.size());

            dropOffOrderList = dropOffOrders;
            limit = getLimit(dropOffOrderList.size(), 30);
            if (dropOffOrderList.size() == 0) {
                getSession();
            } else {
                new LongOperation().execute(dropOffOrderList);
            }
        }
    }

    public void getSession() {

        visibilityType(LayoutVisibilityEnum.HIDE);

        mLoaderTimer.cancel();
        mLoaderTimer.start();

        mTvFetching.setText("Fetching pending request");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            isLoading = true;
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getDropOffPendingOrders(getDropOffPendingOrdersSession);
            getDropOffPendingOrdersV2(getDropOffPendingOrdersSessionV2);
        } else {
           mSwipeRefreshLayout.setRefreshing(false);
           visibilityType(LayoutVisibilityEnum.NOINTERNET);
        }
    }
    public void getLoadMoreSession() {

        mLoaderTimer.cancel();
        mLoaderTimer.start();

        mTvFetching.setText("Fetching pending request");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            isLoading = true;
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getDropOffPendingOrders(getDropOffPendingOrdersSession);
            getDropOffPendingOrdersV2(getDropOffPendingOrdersLoadMoreSessionV2);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            visibilityType(LayoutVisibilityEnum.NOINTERNET);
        }
    }
    @Override
    public void onRefresh() {
        if (mdb != null) {
            mdb.truncateTable(mdb, DatabaseHandler.DROPOFF_ORDERS_PENDING);
        }
        isScroll = true;
        isfirstload = false;
        limit = 0;
        isbottomscroll = false;
        isloadmore = false;
        dropOffOrderList.clear();
        mAdapter.clear();
        mSwipeRefreshLayout.setRefreshing(true);
        getSession();
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshnointernet:
            case R.id.refresh: {
                mAdapter.clear();
                isScroll = true;
                isfirstload = false;
                limit = 0;
                isbottomscroll = false;
                isloadmore = false;
                getSession();
                break;
            }
        }
    }

    private void getDropOffPendingOrders(Callback<GetDropOffPendingOrdersResponse> getDropOffPendingOrdersCallback) {
        Call<GetDropOffPendingOrdersResponse> getdropoffpending = RetrofitBuild.getDropOffPendingOrdersService(getViewContext())
                .getDropOffPendingOrdersCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        ".",
                        String.valueOf(limit),
                        "ANDROID");
        getdropoffpending.enqueue(getDropOffPendingOrdersCallback);
    }

    private final Callback<GetDropOffPendingOrdersResponse> getDropOffPendingOrdersSession = new Callback<GetDropOffPendingOrdersResponse>() {

        @Override
        public void onResponse(Call<GetDropOffPendingOrdersResponse> call, Response<GetDropOffPendingOrdersResponse> response) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    isLoading = false;
                    isfirstload = false;

                    List<DropOffOrder> dropOffOrders = response.body().getPendingDropOffs();

                    isloadmore = dropOffOrders.size() > 0;

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.DROPOFF_ORDERS_PENDING);
                    }

                    new LongInsertOperation().execute(dropOffOrders);

                    if (dropOffOrders.size() > 0) {
                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                        emptyLayout.setVisibility(View.GONE);
                        dropOffOrderList.addAll(dropOffOrders);
                        new LongOperation().execute(dropOffOrderList);
                    } else {
                        if (dropOffOrderList.size() == 0) {
                            mSwipeRefreshLayout.setVisibility(View.GONE);
                            emptyLayout.setVisibility(View.VISIBLE);
                            mAdapter.clear();
                        }
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetDropOffPendingOrdersResponse> call, Throwable t) {
            isLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private class LongInsertOperation extends AsyncTask<List<DropOffOrder>, Void, List<DropOffOrder>> {

        @Override
        protected List<DropOffOrder> doInBackground(List<DropOffOrder>... lists) {
                if (mdb != null) {
                    for (DropOffOrder dropOffOrder : lists[0]) {
                        mdb.insertDropOffOrderPending(mdb, dropOffOrder);
                    }
                }
            return null;
        }

        @Override
        protected void onPostExecute(List<DropOffOrder> dropOffOrders) {
           super.onPostExecute(dropOffOrders);
        }
    }

    private class LongOperation extends AsyncTask<List<DropOffOrder>, Void, List<DropOffOrder>> {

        @Override
        protected List<DropOffOrder> doInBackground(List<DropOffOrder>... lists) {
            return lists[0];
        }

        @Override
        protected void onPostExecute(List<DropOffOrder> dropOffOrders) {
            if (dropOffOrders.size() > 0) {
                updateList(dropOffOrders);
            }
            isScroll = false;
        }
    }

    private void updateList(final List<DropOffOrder> data) {
        if (data.size() > 0) {
           visibilityType(LayoutVisibilityEnum.HASDATA);
            if (mAdapter != null) {
                if (!isLoading) {
                    mAdapter.addAll(data);
                }
            }
        } else {
            visibilityType(LayoutVisibilityEnum.NODATA);
            mAdapter.clear();
        }
    }


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getDropOffPendingOrdersV2(Callback<GenericResponse> session){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();

            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("merchantid", ".");
            parameters.put("limit", String.valueOf(limit));
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getDropOffPendingOrdersV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getDropOffPendingOrdersV2Object(session);

        }else{
            isLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            visibilityType(LayoutVisibilityEnum.NOINTERNET);
        }
    }
    private void getDropOffPendingOrdersV2Object(Callback<GenericResponse> getDropOffPendingOrdersCallback) {
        Call<GenericResponse> getdropoffpending = RetrofitBuilder.getDropOffV2API(getViewContext())
                .getDropOffPendingOrdersV2(authenticationid,sessionid,param);
        getdropoffpending.enqueue(getDropOffPendingOrdersCallback);
    }
    private final Callback<GenericResponse> getDropOffPendingOrdersSessionV2 = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    isLoading = false;
                    isfirstload = false;

                    mdb.truncateTable(mdb, DatabaseHandler.DROPOFF_ORDERS_PENDING);

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    dropOffOrderArrayList = new Gson().fromJson(data, new TypeToken<List<DropOffOrder>>(){}.getType());

                    if (dropOffOrderArrayList.size() >0 && dropOffOrderArrayList != null) {
                        isloadmore = true;
                        for (DropOffOrder dropOffOrder : dropOffOrderArrayList) {
                            mdb.insertDropOffOrderPending(mdb, dropOffOrder);
                        }
                    }
                    updateList(mdb.getDropOffOrderPending(mdb));

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                        showAutoLogoutDialog(message);
                }else {
                     visibilityType(LayoutVisibilityEnum.NODATA);
                     showError(message);
                }
            } else {
                visibilityType(LayoutVisibilityEnum.NODATA);
                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            visibilityType(LayoutVisibilityEnum.NODATA);
        }
    };
    private final Callback<GenericResponse> getDropOffPendingOrdersLoadMoreSessionV2 = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    isLoading = false;
                    isfirstload = false;

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    dropOffOrderArrayList = new Gson().fromJson(data, new TypeToken<List<DropOffOrder>>(){}.getType());
                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.DROPOFF_ORDERS_PENDING);
                    }

                    if (mdb != null) {
                        for (DropOffOrder dropOffOrder : dropOffOrderArrayList) {
                            mdb.insertDropOffOrderPending(mdb, dropOffOrder);
                        }
                    }

                    if (dropOffOrderArrayList.size() > 0 && dropOffOrderArrayList != null) {
                        isloadmore = true;
                    }

                    new LongInsertOperation().execute(dropOffOrderArrayList);

                    updateList(mdb.getDropOffOrderPending(mdb));
                    mAdapter.notifyDataSetChanged();

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                    showAutoLogoutDialog(message);
                }else {
                    visibilityType(LayoutVisibilityEnum.NODATA);
                    showError(message);
                }
            } else {
                visibilityType(LayoutVisibilityEnum.NODATA);
                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            visibilityType(LayoutVisibilityEnum.NODATA);
        }
    };


    private void enableRefresh(){
        refreshdisabled.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);
        mLlLoader.setVisibility(View.GONE);
    }
    private void disableRefresh(){
        refreshdisabled.setVisibility(View.VISIBLE);
        refresh.setVisibility(View.GONE);
    }

    private void shownoInternet(boolean show){
       if(show){
           nointernetconnection.setVisibility(View.VISIBLE);
           mSwipeRefreshLayout.setVisibility(View.GONE);
           emptyLayout.setVisibility(View.GONE);
       }else{
           nointernetconnection.setVisibility(View.GONE);
           mSwipeRefreshLayout.setVisibility(View.GONE);
           emptyLayout.setVisibility(View.VISIBLE);
       }
    }

    //layouts
    public static void visibilityType(LayoutVisibilityEnum visibilityEnum){
        switch (visibilityEnum){
            case HASDATA:
                nointernetconnection.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
                mLlLoader.setVisibility(View.GONE);
                break;
            case NOINTERNET:
                nointernetconnection.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.GONE);
                mLlLoader.setVisibility(View.GONE);

                break;
            case NODATA:
                nointernetconnection.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
                mLlLoader.setVisibility(View.GONE);
                break;

            case HIDE:
                nointernetconnection.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.GONE);
                break;
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        if (isVisibleToUser && !isfirstload){
           onRefresh();
        }
    }

}
