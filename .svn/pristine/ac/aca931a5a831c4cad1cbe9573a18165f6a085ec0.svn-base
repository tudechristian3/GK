package com.goodkredit.myapplication.fragments.loadmessenger;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.loadmessenger.LoadTransactionsAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.loadmessenger.BorrowerFB;
import com.goodkredit.myapplication.bean.loadmessenger.LoadTransaction;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.LayoutVisibilityEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.EndlessRecyclerViewScrollListener;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.view.CustomNestedScrollView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.transform.sax.SAXResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoadTransactionsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView rvLoadTransactions;
    private SwipeRefreshLayout swiperefresh_loadTxn;
    private NestedScrollView loadtransactionsNested;
    private LinearLayout layout_lm_empty;

    private String authenticationid = "";
    private String index = "";
    private String keyEncryption = "";
    private String param = "";


    private BorrowerFB borrowerFB = null;
    private DatabaseHandler db;

    private int month;
    private int year;

    private int currentYear;
    private int currentMonth;

    private int limit = 0;
    private Calendar c;

    LoadTransactionsAdapter adapter = null;
    ArrayList<LoadTransaction> loadTransactions = new ArrayList<>();

    ProgressDialog progressDialog = null;
    private LinearLayout tv_view_archive;
    private MonthYearPickerDialogFragment dialogFragment = null;

    private boolean isbottomscroll = false;
    private boolean isloadmore = false;
    private boolean isfirstload = false;


    public LoadTransactionsFragment() {
        // Required empty public constructor
    }


    public static LoadTransactionsFragment newInstance(BorrowerFB borrowerFB) {
        LoadTransactionsFragment fragment = new LoadTransactionsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("borrowerFB",new Gson().toJson(borrowerFB));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHandler(requireContext());

        if(getArguments() != null){
            borrowerFB = new Gson().fromJson(getArguments().getString("borrowerFB"),BorrowerFB.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_load_transactions, container, false);

        init(view);
        initProgressDialog();
        initData();

        return view;
    }

    private void initProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(requireContext());
            progressDialog.setMessage("Loading Transactions....");
            progressDialog.setCancelable(false);
        }
    }

    private void initData() {

        imei = CommonFunctions.getImei(getViewContext());
        session = PreferenceUtils.getSessionID(getViewContext());

        //initialize date
        c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH) + 1;

        month = currentMonth;
        year = currentYear;

        //get account information
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));

            } while (cursor.moveToNext());
        }
        cursor.close();

        fetchLoadTxnFromServer();


    }

    private void init(View view) {
        rvLoadTransactions = view.findViewById(R.id.rv_loadtransactions);
        swiperefresh_loadTxn = view.findViewById(R.id.swiperefresh_loadTxn);
        loadtransactionsNested = view.findViewById(R.id.loadtransactionsNested);
        loadtransactionsNested.setOnScrollChangeListener(scrollOnChangedListener);
        layout_lm_empty = view.findViewById(R.id.layout_lm_empty);
        swiperefresh_loadTxn.setOnRefreshListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rvLoadTransactions.setLayoutManager(linearLayoutManager);
        rvLoadTransactions.addOnScrollListener(new EndlessRecyclerViewScrollListener(new LinearLayoutManager(getViewContext())) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Toast.makeText(getViewContext(), "LAST", Toast.LENGTH_SHORT).show();
            }
        });
        tv_view_archive = view.findViewById(R.id.tv_view_archive);
        tv_view_archive.setOnClickListener(view1 -> {
            Calendar minCal = Calendar.getInstance();
            minCal.set(2016, 0,1); // Set maximum date to show in dialog
            long minDate = minCal.getTimeInMillis();

            Calendar maxCal = Calendar.getInstance();
            maxCal.set(currentYear, currentMonth,31); // Set maximum date to show in dialog
            long maxDate = maxCal.getTimeInMillis(); // Get milliseconds of the modified date

            dialogFragment = MonthYearPickerDialogFragment.getInstance(month - 1, year,minDate,maxDate,"Filter Transactions");
            dialogFragment.setOnDateSetListener((y, monthOfYear) -> {
                if (year < Calendar.getInstance().get(Calendar.YEAR)) {
                    year = y;
                    month = monthOfYear + 1;
                    limit = 0 ;
                    isbottomscroll = false;
                    isloadmore = false;
                    adapter = null;
                    loadTransactions.clear();
                    initProgressDialog();
                    fetchLoadTxnFromServer();
                } else if (year == Calendar.getInstance().get(Calendar.YEAR)) {
                    if (monthOfYear + 1 <= Calendar.getInstance().get(Calendar.MONTH) + 1) {
                        year = y;
                        month = monthOfYear + 1;
                        limit = 0 ;
                        isbottomscroll = false;
                        isloadmore = false;
                        adapter = null;
                        loadTransactions.clear();
                        initProgressDialog();
                        fetchLoadTxnFromServer();
                    } else {
                        showError("Please check filter. Thanks.");
                    }
                } else if (year > Calendar.getInstance().get(Calendar.YEAR) || monthOfYear + 1 > Calendar.getInstance().get(Calendar.MONTH) + 1) {
                    showError("Please check filter. Thanks.");
                }
            });
            dialogFragment.show(getChildFragmentManager(), null);
        });

    }

    private void fetchLoadTxnFromServer(){
        layoutVisibilty(LayoutVisibilityEnum.HIDE);
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            progressDialog.show();
            loadTransactions();
        }else{
            layoutVisibilty(LayoutVisibilityEnum.NODATA);
            showNoInternetToast();
        }

    }


    //network call
    private void loadTransactions(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();


        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("memberBorrowerid",borrowerFB.getBorrowerID());
        parameters.put("senderID",borrowerFB.getSenderID());
        parameters.put("month", String.valueOf(month));
        parameters.put("year", String.valueOf(year));
        parameters.put("limit", String.valueOf(limit));
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, session);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + session + "loadTransactions");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        if(isloadmore){
            loadTransactionsObject(loadTransactionsMoreCallback);
        }else{
            loadTransactionsObject(loadTransactionsCallback);
        }


    }
    private void loadTransactionsObject(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getLoadMessengerV2API(getViewContext())
                .loadTransactions(authenticationid,session,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse> loadTransactionsCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            progressDialog.dismiss();
            progressDialog = null;

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    loadTransactions = new Gson().fromJson(data,new TypeToken<ArrayList<LoadTransaction>>(){}.getType());
                    if(loadTransactions.size() > 0 && loadTransactions != null){
                        isloadmore = true;
                        layoutVisibilty(LayoutVisibilityEnum.HASDATA);
                    }else{
                        layoutVisibilty(LayoutVisibilityEnum.NODATA);
                        isloadmore = false;
                        Toast.makeText(getViewContext(),"No data found.",Toast.LENGTH_SHORT).show();
                    }
                    adapter = new LoadTransactionsAdapter(loadTransactions);
                    rvLoadTransactions.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    layoutVisibilty(LayoutVisibilityEnum.NODATA);
                    showErrorGlobalDialogs(message);
                }

            }else{
                layoutVisibilty(LayoutVisibilityEnum.NODATA);
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(@NotNull Call<GenericResponse> call, Throwable t) {
            progressDialog.dismiss();
            progressDialog = null;
            showErrorToast();
            layoutVisibilty(LayoutVisibilityEnum.NODATA);
            t.printStackTrace();
        }
    };
    private final Callback<GenericResponse> loadTransactionsMoreCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {
            layoutVisibilty(LayoutVisibilityEnum.HASDATA);
            ResponseBody errorBody = response.errorBody();
            progressDialog.dismiss();
            progressDialog = null;

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    ArrayList<LoadTransaction> loadTransactionArrayList = new Gson().fromJson(data,new TypeToken<ArrayList<LoadTransaction>>(){}.getType());
                    if(loadTransactions.size() > 0 && loadTransactions != null){
                        isloadmore = true;
                        loadTransactions.addAll(loadTransactionArrayList);
                        adapter = new LoadTransactionsAdapter(loadTransactions);
                        rvLoadTransactions.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        isloadmore = false;
                    }


                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(@NotNull Call<GenericResponse> call, Throwable t) {
            layoutVisibilty(LayoutVisibilityEnum.NODATA);
            progressDialog.dismiss();
            progressDialog = null;
            showErrorToast();
            t.printStackTrace();
        }
    };

    @Override
    public void onRefresh() {
        swiperefresh_loadTxn.setRefreshing(false);
        limit = 0 ;
        isbottomscroll = false;
        isloadmore = false;
        adapter = null;
        c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH) + 1;
        month = currentMonth;
        year = currentYear;
        loadTransactions.clear();
        initProgressDialog();
        fetchLoadTxnFromServer();
    }

    private void layoutVisibilty(LayoutVisibilityEnum visibilityEnum){
        switch (visibilityEnum){
            case HASDATA:
                 loadtransactionsNested.setVisibility(View.VISIBLE);
                 layout_lm_empty.setVisibility(View.GONE);
                break;
            case NODATA:
                loadtransactionsNested.setVisibility(View.GONE);
                layout_lm_empty.setVisibility(View.VISIBLE);
                break;
            case HIDE:
                loadtransactionsNested.setVisibility(View.GONE);
                layout_lm_empty.setVisibility(View.GONE);
                break;
        }
    }


    public CustomNestedScrollView.OnScrollChangeListener scrollOnChangedListener = (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            isbottomscroll = true;
            if (isloadmore) {
                limit = limit + 30;
                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                    initProgressDialog();
                    fetchLoadTxnFromServer();
                } else {
                    showNoInternetToast();
                }
            }
        }else{
            isbottomscroll = false;
        }
    };


}