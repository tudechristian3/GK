package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.coopassistant.member.CoopAssistantLoanTransactionsAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantLoanTransactions;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.coopassistant.CoopAssistantLoanTransactionsResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantLoanTransactionsFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rv_coopassistant_loan_transactions;
    private CoopAssistantLoanTransactionsAdapter madapter;
    private List<CoopAssistantLoanTransactions> mGridData;

    private DatabaseHandler mdb;
    private String imei;
    private String authcode;
    private String userid;
    private String sessionid;
    private String borrowerid;
    private String servicecode;
    private String year;

    private Calendar c = Calendar.getInstance();

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

    //NEW VARIABLES FOR SECURITY
    private String authenticationid;
    private String index;
    private String keyEncryption;
    private String param;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_loan_transactions, container, false);


        init(view);
        initdata();

        return view;
    }

    private void init(View view) {

        rv_coopassistant_loan_transactions = view.findViewById(R.id.rv_coopassistant_loan_transactions);

        madapter = new CoopAssistantLoanTransactionsAdapter(getViewContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rv_coopassistant_loan_transactions.setNestedScrollingEnabled(false);
        rv_coopassistant_loan_transactions.addItemDecoration(new DividerItemDecoration(getViewContext(), LinearLayoutManager.VERTICAL));
        rv_coopassistant_loan_transactions.setLayoutManager(layoutManager);
        rv_coopassistant_loan_transactions.setAdapter(madapter);

        //initialize refresh
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        emptyvoucher = view.findViewById(R.id.emptyvoucher);
        refresh = view.findViewById(R.id.refresh);
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernet = view.findViewById(R.id.refreshnointernet);
        refreshdisabled = view.findViewById(R.id.refreshdisabled);
        refreshdisabled1 = view.findViewById(R.id.refreshdisabled1);

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

        refresh.setOnClickListener(this);
        refreshnointernet.setOnClickListener(this);
    }

    private void initdata() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        year = String.valueOf(c.get(Calendar.YEAR));

        getSession();

    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            mTvFetching.setText("Fetching Loan Transactions. ");
            mTvWait.setText("Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            //getCoopLoanTransactions(getCoopLoanTransactionsSession);
            getCoopLoanTransactionV2();
        } else{
            swipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
//            showNoInternetToast();
        }
    }

    private void getCoopLoanTransactions (Callback<CoopAssistantLoanTransactionsResponse> getCoopLoanTransactionsCallback ){
        Call<CoopAssistantLoanTransactionsResponse> getcooploantransactions = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .getCoopLoanTransactionsCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        servicecode,
                        year,
                        CommonVariables.devicetype);
        getcooploantransactions.enqueue(getCoopLoanTransactionsCallback);
    }

    private final Callback<CoopAssistantLoanTransactionsResponse> getCoopLoanTransactionsSession = new Callback<CoopAssistantLoanTransactionsResponse>() {
        @Override
        public void onResponse(Call<CoopAssistantLoanTransactionsResponse> call, Response<CoopAssistantLoanTransactionsResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){

                    try{
                        CacheManager.getInstance().saveCoopAssistantLoanTransactions(response.body().getCoopAssistantLoanTransactions());
                        mGridData = CacheManager.getInstance().getCoopAssistantLoanTransactions();
                        madapter.updateList(mGridData);

                        updateList(mGridData);
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
        public void onFailure(Call<CoopAssistantLoanTransactionsResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorGlobalDialogs();
        }
    };

    private void updateList(List<CoopAssistantLoanTransactions> data){
        if (data.size() > 0){
            emptyvoucher.setVisibility(View.GONE);
            madapter.updateList(data);
            madapter.notifyDataSetChanged();
        } else{
            emptyvoucher.setVisibility(View.VISIBLE);
            madapter.clear();
            madapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refreshnointernet:
            case R.id.refresh: {
                onRefresh();
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        getSession();
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
    public void onResume() {
        super.onResume();

//        boolean cooploantransactions = PreferenceUtils.getBooleanPreference(getViewContext(), "cooploantransactiondetails");
//
//        if(cooploantransactions){
//            swipeRefreshLayout.setRefreshing(true);
//            getSession();
//            PreferenceUtils.removePreference(getViewContext(), "cooploantransactiondetails");
//        }

        swipeRefreshLayout.setRefreshing(true);
        getSession();
        PreferenceUtils.removePreference(getViewContext(), "cooploantransactiondetails");

    }

    /**
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * **/

    private void getCoopLoanTransactionV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("year", year);
        parameters.put("devicetype", CommonVariables.devicetype);


        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getCoopLoanTransactionV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        getCoopLoanTransactionV2Object(getCoopLoanTransactionV2Callback);


    }

    private void getCoopLoanTransactionV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .getCoopLoanTransactionV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  getCoopLoanTransactionV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    try{
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                        List<CoopAssistantLoanTransactions> coopAssistantLoanTransactions = new ArrayList<>();
                        coopAssistantLoanTransactions = new Gson().fromJson(data,new TypeToken<List<CoopAssistantLoanTransactions>>(){}.getType());
                        CacheManager.getInstance().saveCoopAssistantLoanTransactions(coopAssistantLoanTransactions);
                        mGridData = CacheManager.getInstance().getCoopAssistantLoanTransactions();
                        madapter.updateList(mGridData);

                        updateList(mGridData);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            } else{
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showErrorGlobalDialogs();
            t.printStackTrace();
        }
    };


}
