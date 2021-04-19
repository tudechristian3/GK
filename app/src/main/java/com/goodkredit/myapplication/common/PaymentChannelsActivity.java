package com.goodkredit.myapplication.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.prepaidrequest.PaymentChannelsRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.PaymentChannels;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetPaymentPartnersResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
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

public class PaymentChannelsActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private TextView txvPaymentChannels;
    private RecyclerView recyclerView;
    private PaymentChannelsRecyclerAdapter mAdapter;

    private String totalamount;
    private String BillingID;
    private String sessionid;
    private String imei;
    private String authcode;
    private String userid;
    private String borrowerid;
    private int limit;

    private DatabaseHandler mdb;
    private List<PaymentChannels> paymentChannelsList;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private RelativeLayout mainLayout;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private NestedScrollView nested_scroll;
    private boolean isloadmore;
    private boolean isbottomscroll;

    private boolean isfirstload = true;

    //NEW VARIABLES FOR SECURITY
    private String authenticationid;
    private String keyEncryption;
    private String param;
    private String index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_channels);

        init();
        initData();
    }

    private void init() {
        setupToolbarWithTitle("Payment Channels");

        String bilingid = getIntent().getStringExtra("billingid");
        String amount = getIntent().getStringExtra("totalamount");

        mLlLoader = findViewById(R.id.loaderLayout);
        mTvFetching = findViewById(R.id.fetching);
        mTvWait = findViewById(R.id.wait);
        txvPaymentChannels = findViewById(R.id.txvPaymentChannels);
        txvPaymentChannels.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "GK Payment Channels"));
        recyclerView = findViewById(R.id.recycler_view_payment_channels);
        emptyLayout = findViewById(R.id.emptyLayout);
        textView11 = findViewById(R.id.textView11);
        textView11.setText("No Payment Channels yet.");
        refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        mainLayout = findViewById(R.id.mainLayout);
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        nested_scroll = findViewById(R.id.nested_scroll);
        nointernetconnection = findViewById(R.id.nointernetconnection);
        refreshnointernet = findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);
    }

    private void initData() {
        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        mdb = new DatabaseHandler(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        paymentChannelsList = new ArrayList<>();
        paymentChannelsList = mdb.getPaymentChannels(mdb);
        limit = getLimit(paymentChannelsList.size(), 30);
        isbottomscroll = false;
        isloadmore = true;

        recyclerView.setLayoutManager(new GridLayoutManager(getViewContext(), 3));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new PaymentChannelsRecyclerAdapter(getViewContext());
        recyclerView.setAdapter(mAdapter);

        if (paymentChannelsList.size() > 0) {
            mainLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            mAdapter.setPaymentsChannelsData(paymentChannelsList);
        } else {
            mainLayout.setVisibility(View.GONE);
            getSession();
        }

        nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    isbottomscroll = true;
                    if (isloadmore) {
                        if (!isfirstload) {
                            limit = limit + 30;
                        }
                        getSession();
                    }
                }
            }
        });
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Fetching payment channels.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getPaymentPartners(getPaymentPartnersSession);
            getPaymentPartnersV2();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }

    }

    private void getPaymentPartners(Callback<GetPaymentPartnersResponse> getPaymentPartnersCallback) {
        Call<GetPaymentPartnersResponse> getPaymentPartners = RetrofitBuild.getPaymentPartnersService(getViewContext())
                .getPaymentPartnersCall(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        String.valueOf(limit));
        getPaymentPartners.enqueue(getPaymentPartnersCallback);
    }

    private final Callback<GetPaymentPartnersResponse> getPaymentPartnersSession = new Callback<GetPaymentPartnersResponse>() {

        @Override
        public void onResponse(Call<GetPaymentPartnersResponse> call, Response<GetPaymentPartnersResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            isfirstload = false;

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    isloadmore = response.body().getPaymentChannels().size() > 0;
                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.PAYMENT_CHANNELS);
                    }

                    List<PaymentChannels> paymentChannels = response.body().getPaymentChannels();

                    for (PaymentChannels channel : paymentChannels) {
                        mdb.insertPaymentChannels(mdb, channel);
                    }

                    updateList(mdb.getPaymentChannels(mdb));
                } else {
                    showError(response.body().getMessage());
                }
            }
        }

        @Override
        public void onFailure(Call<GetPaymentPartnersResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    public static void start(Context context, String billingid, String amount) {
        Intent intent = new Intent(context, PaymentChannelsActivity.class);
        intent.putExtra("billingid", billingid);
        intent.putExtra("totalamount", amount);
        context.startActivity(intent);

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, PaymentChannelsActivity.class);
        context.startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh:
            case R.id.refreshnointernet: {
                if (mdb != null) {
                    isfirstload = false;
                    limit = getLimit(mdb.getPaymentChannels(mdb).size(), 30);
                    isbottomscroll = false;
                    isloadmore = true;
                    getSession();
                }
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        if (mdb != null) {
            if (mAdapter != null) {
                mdb.truncateTable(mdb, DatabaseHandler.PAYMENT_CHANNELS);
                mainLayout.setVisibility(View.GONE);
                mAdapter.clear();
            }
        }

        isfirstload = false;
        limit = 0;
        isbottomscroll = false;
        isloadmore = true;
        mSwipeRefreshLayout.setRefreshing(false);
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

    private void updateList(List<PaymentChannels> data) {
        showNoInternetConnection(false);
        if (data.size() > 0) {
            mainLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            if (mAdapter != null) {
                mAdapter.setPaymentsChannelsData(data);
            }
        } else {
            mainLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */
    private void getPaymentPartnersV2(){
        LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
        parameters.put("imei",imei);
        parameters.put("userid",userid);
        parameters.put("borrowerid",borrowerid);
        parameters.put("limit", String.valueOf(limit));

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getPaymentPartnersV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        getPaymentPartnersV2Object(getPaymentPartnersV2Callback);
    }
    private void getPaymentPartnersV2Object(Callback<GenericResponse> getPaymentPartners){
        Call<GenericResponse> call = RetrofitBuilder.getPaymentV2API(getViewContext())
                .getPaymentPartnersV2(authenticationid,sessionid,param);
        call.enqueue(getPaymentPartners);
    }
    private final Callback<GenericResponse> getPaymentPartnersV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            isfirstload = false;

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData  =  CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<PaymentChannels> paymentChannels = new Gson().fromJson(decryptedData, new TypeToken<List<PaymentChannels>>(){}.getType());

                    isloadmore = paymentChannels.size() > 0;
                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.PAYMENT_CHANNELS);
                    }

                    for (PaymentChannels channel : paymentChannels) {
                        mdb.insertPaymentChannels(mdb, channel);
                    }

                    updateList(mdb.getPaymentChannels(mdb));

                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }
                else {
                    showErrorGlobalDialogs(decryptedMessage);
                }
            }else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showErrorToast("Something went wrong.Please try again. ");
        }
    };

}
