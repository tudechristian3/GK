package com.goodkredit.myapplication.fragments.loadmessenger;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.loadmessenger.ReplenishLogsAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.loadmessenger.BorrowerFB;
import com.goodkredit.myapplication.bean.loadmessenger.ReplenishLogs;
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
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReplenishLogsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView rvReplenishLogs;
    private SwipeRefreshLayout swipeRefresh_replenishLogs;
    private NestedScrollView replenishNested;
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

    ReplenishLogsAdapter adapter = null;
    ArrayList<ReplenishLogs> replenishLogs = new ArrayList<>();
    private boolean isLoadMore = false;

    ProgressDialog progressDialog = null;
    private LinearLayout tv_view_archive;
    private MonthYearPickerDialogFragment dialogFragment = null;


    public ReplenishLogsFragment() {
        // Required empty public constructor
    }


    public static ReplenishLogsFragment newInstance(BorrowerFB borrowerFB) {
        ReplenishLogsFragment fragment = new ReplenishLogsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_replenish_logs, container, false);

        init(view);
        initProgressDialog();
        initData();

        return view;
    }

    private void initProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(requireContext());
            progressDialog.setMessage("Loading Replenish Logs....");
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

        fetchLogsFromServer();


    }

    private void init(View view) {
        rvReplenishLogs = view.findViewById(R.id.rv_replenishLogs);
        swipeRefresh_replenishLogs = view.findViewById(R.id.swiperefresh_replenish);
        replenishNested = view.findViewById(R.id.replenishLogsNested);
        replenishNested.setOnScrollChangeListener(scrollOnChangedListener);
        layout_lm_empty = view.findViewById(R.id.layout_lm_empty);
        swipeRefresh_replenishLogs.setOnRefreshListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rvReplenishLogs.setLayoutManager(linearLayoutManager);
        rvReplenishLogs.addOnScrollListener(new EndlessRecyclerViewScrollListener(new LinearLayoutManager(getViewContext())) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Toast.makeText(getViewContext(), "LAST", Toast.LENGTH_SHORT).show();
            }
        });
        tv_view_archive = view.findViewById(R.id.tv_view_archive);
        tv_view_archive.setOnClickListener(view1 -> {

            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(requireContext(), new MonthPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int selectedMonth, int selectedYear) {
                    initProgressDialog();
                    fetchLogsFromServer();
                }},currentYear,currentMonth);

            builder.setMinYear(2016)
                    .setActivatedYear(currentYear)
                    .setMaxYear(currentYear)
                    .setTitle("Filter by year")
                     .showYearOnly()
                     .setOnYearChangedListener(y -> year = y)
                    .build().show();
        });

    }

    private void fetchLogsFromServer(){
        layoutVisibilty(LayoutVisibilityEnum.HIDE);
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            progressDialog.show();
            replenishLogs();
        }else{
            layoutVisibilty(LayoutVisibilityEnum.NODATA);
            showNoInternetToast();
        }

    }


    //network call
    private void replenishLogs(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();


        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("memberBorrowerid",borrowerFB.getBorrowerID());
        parameters.put("senderID",borrowerFB.getSenderID());
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
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + session + "replenishLogs");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        if(isloadmore){
            replenishObject(replenishMoreCallback);
        }else{
            replenishObject(replenishCallback);
        }


    }
    private void replenishObject(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getLoadMessengerV2API(getViewContext())
                .replenishLogs(authenticationid,session,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse> replenishCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            progressDialog.dismiss();
            progressDialog = null;

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    replenishLogs = new Gson().fromJson(data,new TypeToken<ArrayList<ReplenishLogs>>(){}.getType());
                    if(replenishLogs.size() > 0 && replenishLogs != null){
                        isLoadMore = true;
                        layoutVisibilty(LayoutVisibilityEnum.HASDATA);
                    }else{
                        layoutVisibilty(LayoutVisibilityEnum.NODATA);
                        isLoadMore = false;
                        Toast.makeText(getViewContext(),"No logs found.",Toast.LENGTH_SHORT).show();
                    }
                    adapter = new ReplenishLogsAdapter(replenishLogs);
                    rvReplenishLogs.setAdapter(adapter);
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
    private final Callback<GenericResponse> replenishMoreCallback = new Callback<GenericResponse>() {
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

                    ArrayList<ReplenishLogs> replenishLogsArrayList = new Gson().fromJson(data,new TypeToken<ArrayList<ReplenishLogs>>(){}.getType());
                    if(replenishLogsArrayList.size() > 0 && replenishLogsArrayList != null){
                        isLoadMore = true;
                        replenishLogs.addAll(replenishLogsArrayList);
                    }else{
                        isLoadMore = false;
                    }
                    adapter = new ReplenishLogsAdapter(replenishLogs);
                    rvReplenishLogs.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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
        swipeRefresh_replenishLogs.setRefreshing(false);
        limit = 0 ;
        isLoadMore = false;
        adapter = null;
        c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH) + 1;
        month = currentMonth;
        year = currentYear;
        replenishLogs.clear();
        initProgressDialog();
        fetchLogsFromServer();
    }

    private void layoutVisibilty(LayoutVisibilityEnum visibilityEnum){
        switch (visibilityEnum){
            case HASDATA:
                 replenishNested.setVisibility(View.VISIBLE);
                 layout_lm_empty.setVisibility(View.GONE);
                break;
            case NODATA:
                replenishNested.setVisibility(View.GONE);
                layout_lm_empty.setVisibility(View.VISIBLE);
                break;
            case HIDE:
                replenishNested.setVisibility(View.GONE);
                layout_lm_empty.setVisibility(View.GONE);
                break;
        }
    }

    private boolean isbottomscroll =false;
    private boolean isloadmore = false;
    public CustomNestedScrollView.OnScrollChangeListener scrollOnChangedListener = (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            isbottomscroll = true;
            if (isloadmore) {
                limit = limit + 30;
                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                    initProgressDialog();
                    fetchLogsFromServer();
                } else {
                    showNoInternetToast();
                }
            }
        }else{
            isbottomscroll = false;
        }
    };


}